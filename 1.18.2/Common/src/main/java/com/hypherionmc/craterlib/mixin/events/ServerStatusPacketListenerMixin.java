package com.hypherionmc.craterlib.mixin.events;

import com.hypherionmc.craterlib.api.events.server.ServerStatusEvent;
import com.hypherionmc.craterlib.core.event.CraterEventBus;
import com.hypherionmc.craterlib.utils.ChatUtils;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.status.ClientboundStatusResponsePacket;
import net.minecraft.network.protocol.status.ServerStatus;
import net.minecraft.network.protocol.status.ServerboundStatusRequestPacket;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerStatusPacketListenerImpl;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerStatusPacketListenerImpl.class)
public class ServerStatusPacketListenerMixin {

    @Shadow @Final private Connection connection;

    @Shadow @Final private MinecraftServer server;

    @Inject(method = "handleStatusRequest",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/network/Connection;send(Lnet/minecraft/network/protocol/Packet;)V",
                    shift = At.Shift.BEFORE),
            cancellable = true
    )
    private void injectHandleStatusRequest(ServerboundStatusRequestPacket arg, CallbackInfo ci) {
        ServerStatusEvent.StatusRequestEvent event = new ServerStatusEvent.StatusRequestEvent(ChatUtils.mojangToAdventure(server.getStatus().getDescription()));
        CraterEventBus.INSTANCE.postEvent(event);

        if (event.getNewStatus() != null) {
            ci.cancel();
            ServerStatus serverStatus = this.server.getStatus();
            serverStatus.setDescription(ChatUtils.adventureToMojang(event.getNewStatus()));

            this.connection.send(new ClientboundStatusResponsePacket(serverStatus));
        }
    }

}