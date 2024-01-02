package com.hypherionmc.craterlib.mixin.events;

import com.hypherionmc.craterlib.api.event.server.CraterPlayerEvent;
import com.hypherionmc.craterlib.api.event.server.MessageBroadcastEvent;
import com.hypherionmc.craterlib.api.event.server.PlayerPreLoginEvent;
import com.hypherionmc.craterlib.core.event.CraterEventBus;
import com.mojang.authlib.GameProfile;
import net.minecraft.network.Connection;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
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
        MessageBroadcastEvent event = new MessageBroadcastEvent(component, function, bl, threadName);
        CraterEventBus.INSTANCE.postEvent(event);
    }

    @Inject(method = "placeNewPlayer", at = @At("TAIL"))
    private void injectPlayerLoginEvent(Connection connection, ServerPlayer serverPlayer, CallbackInfo ci) {
        CraterPlayerEvent.PlayerLoggedIn loggedIn = new CraterPlayerEvent.PlayerLoggedIn(serverPlayer);
        CraterEventBus.INSTANCE.postEvent(loggedIn);
    }

    @Inject(method = "remove", at = @At("HEAD"))
    private void injectPlayerLogoutEvent(ServerPlayer player, CallbackInfo ci) {
        CraterPlayerEvent.PlayerLoggedOut loggedOut = new CraterPlayerEvent.PlayerLoggedOut(player);
        CraterEventBus.INSTANCE.postEvent(loggedOut);
    }

    @Inject(method = "canPlayerLogin", at = @At("HEAD"), cancellable = true)
    private void injectPreLoginEvent(SocketAddress address, GameProfile gameProfile, CallbackInfoReturnable<Component> cir) {
        PlayerPreLoginEvent event = new PlayerPreLoginEvent(address, gameProfile);
        CraterEventBus.INSTANCE.postEvent(event);
        if (event.getMessage() != null) {
            cir.setReturnValue(event.getMessage());
        }
    }
}
