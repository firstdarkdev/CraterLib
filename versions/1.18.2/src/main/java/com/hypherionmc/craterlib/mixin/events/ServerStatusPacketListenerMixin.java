package com.hypherionmc.craterlib.mixin.events;

import com.hypherionmc.craterlib.api.events.server.ServerStatusEvent;
import com.hypherionmc.craterlib.api.game.text.Text;
import com.hypherionmc.craterlib.core.event.CraterEventBus;
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
        try {
            ServerStatusEvent.StatusRequestEvent event = new ServerStatusEvent.StatusRequestEvent(Text.fromGame(server.getStatus().getDescription()));
            CraterEventBus.INSTANCE.postEvent(event);

            if (event.getNewStatus() != null) {
                ci.cancel();
                ServerStatus serverStatus = this.server.getStatus();
                serverStatus.setDescription(event.getNewStatus().toGame());

                this.connection.send(new ClientboundStatusResponsePacket(serverStatus));
            }
        } catch (Exception ignored) { }
    }

}