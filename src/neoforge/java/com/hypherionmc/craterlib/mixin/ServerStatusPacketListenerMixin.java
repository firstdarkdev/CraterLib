package com.hypherionmc.craterlib.mixin;

import com.hypherionmc.craterlib.api.events.server.ServerStatusEvent;
import com.hypherionmc.craterlib.core.event.CraterEventBus;
import com.hypherionmc.craterlib.utils.ChatUtils;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.status.ClientboundStatusResponsePacket;
import net.minecraft.network.protocol.status.ServerStatus;
import net.minecraft.network.protocol.status.ServerboundStatusRequestPacket;
import net.minecraft.server.network.ServerStatusPacketListenerImpl;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerStatusPacketListenerImpl.class)
public class ServerStatusPacketListenerMixin {

    @Shadow
    @Final
    private ServerStatus status;

    @Shadow @Final private Connection connection;

    @Inject(method = "handleStatusRequest",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/network/Connection;send(Lnet/minecraft/network/protocol/Packet;)V",
                    shift = At.Shift.BEFORE),
            cancellable = true
    )
    private void injectHandleStatusRequest(ServerboundStatusRequestPacket arg, CallbackInfo ci) {
        ServerStatusEvent.StatusRequestEvent event = new ServerStatusEvent.StatusRequestEvent(ChatUtils.mojangToAdventure(status.description()));
        CraterEventBus.INSTANCE.postEvent(event);

        if (event.getNewStatus() != null) {
            ci.cancel();
            this.connection.send(new ClientboundStatusResponsePacket(
                    new ServerStatus(ChatUtils.adventureToMojang(
                            event.getNewStatus()),
                            status.players(),
                            status.version(),
                            status.favicon(),
                            status.enforcesSecureChat(),
                            status.isModded()
                    )
            ));
        }
    }

}