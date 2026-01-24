package com.hypherionmc.craterlib.mixin.events;

import com.hypherionmc.craterlib.api.events.server.CraterPlayerEvent;
import com.hypherionmc.craterlib.api.events.server.MessageBroadcastEvent;
import com.hypherionmc.craterlib.api.events.server.PlayerPreLoginEvent;
import com.hypherionmc.craterlib.api.game.text.Text;
import com.hypherionmc.craterlib.core.event.CraterEventBus;
import com.hypherionmc.craterlib.impl.api.authlib.BridgedGameProfile;
import com.hypherionmc.craterlib.impl.api.world.entity.player.BridgedPlayer;
import com.mojang.authlib.GameProfile;
import net.minecraft.network.Connection;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.CommonListenerCookie;
import net.minecraft.server.players.PlayerList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.net.SocketAddress;
import java.util.function.Function;

@Mixin(PlayerList.class)
public class PlayerListMixin {

    @Inject(method = "broadcastSystemMessage(Lnet/minecraft/network/chat/Component;Ljava/util/function/Function;Z)V", at = @At("HEAD"))
    private void injectBroadcastEvent(Component component, Function<ServerPlayer, Component> function, boolean bl, CallbackInfo ci) {
        try {
            String thread = Thread.currentThread().getStackTrace()[3].getClassName();
            MessageBroadcastEvent event = new MessageBroadcastEvent(Text.fromGame(component), (f) -> Text.fromGame(component), bl, thread);
            CraterEventBus.INSTANCE.postEvent(event);
        } catch (Exception ignored) {}
    }

    @Inject(method = "placeNewPlayer", at = @At("TAIL"))
    private void injectPlayerLoginEvent(Connection connection, ServerPlayer serverPlayer, CommonListenerCookie commonListenerCookie, CallbackInfo ci) {
        try {
            CraterEventBus.INSTANCE.postEvent(new CraterPlayerEvent.PlayerLoggedIn(BridgedPlayer.wrap(serverPlayer)));
        } catch (Exception ignored) {}
    }

    @Inject(method = "remove", at = @At("HEAD"))
    private void injectPlayerLogoutEvent(ServerPlayer player, CallbackInfo ci) {
        try {
            CraterEventBus.INSTANCE.postEvent(new CraterPlayerEvent.PlayerLoggedOut(BridgedPlayer.wrap(player)));
        } catch (Exception ignored) {}
    }

    @Inject(method = "canPlayerLogin", at = @At("HEAD"), cancellable = true)
    private void injectPreLoginEvent(SocketAddress socketAddress, GameProfile arg, CallbackInfoReturnable<Component> cir) {
        try {
            PlayerPreLoginEvent event = new PlayerPreLoginEvent(socketAddress, BridgedGameProfile.wrap(arg));
            CraterEventBus.INSTANCE.postEvent(event);
            if (event.getMessage() != null) {
                cir.setReturnValue(event.getMessage().toGame());
            }
        } catch (Exception ignored) {}
    }
}
