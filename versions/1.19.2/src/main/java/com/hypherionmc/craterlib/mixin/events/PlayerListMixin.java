package com.hypherionmc.craterlib.mixin.events;

import com.hypherionmc.craterlib.api.events.server.CraterPlayerEvent;
import com.hypherionmc.craterlib.api.events.server.CraterServerChatEvent;
import com.hypherionmc.craterlib.api.events.server.MessageBroadcastEvent;
import com.hypherionmc.craterlib.api.events.server.PlayerPreLoginEvent;
import com.hypherionmc.craterlib.api.game.text.Text;
import com.hypherionmc.craterlib.core.event.CraterEventBus;
import com.hypherionmc.craterlib.impl.api.authlib.BridgedGameProfile;
import com.hypherionmc.craterlib.impl.api.world.entity.player.BridgedPlayer;
import com.mojang.authlib.GameProfile;
import net.minecraft.network.Connection;
import net.minecraft.network.chat.*;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.PlayerList;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.net.SocketAddress;
import java.util.UUID;
import java.util.function.Function;
import java.util.function.Predicate;

@Mixin(PlayerList.class)
public class PlayerListMixin {

    @Inject(method = "broadcastChatMessage(Lnet/minecraft/network/chat/PlayerChatMessage;Ljava/util/function/Predicate;Lnet/minecraft/server/level/ServerPlayer;Lnet/minecraft/network/chat/ChatSender;Lnet/minecraft/network/chat/ChatType$Bound;)V", at = @At("HEAD"))
    private void injectBroadcast(PlayerChatMessage playerChatMessage, Predicate<ServerPlayer> predicate, ServerPlayer serverPlayer, ChatSender chatSender, ChatType.Bound bound, CallbackInfo ci) {
        try {
            String thread = Thread.currentThread().getStackTrace()[3].getClassName();
            MessageBroadcastEvent event = new MessageBroadcastEvent(Text.fromGame(playerChatMessage.serverContent()), (f) -> Text.fromGame(playerChatMessage.serverContent()), false, thread);
            CraterEventBus.INSTANCE.postEvent(event);
        } catch (Exception ignored) {}

        if (serverPlayer != null) {
            CraterServerChatEvent event = new CraterServerChatEvent(BridgedPlayer.wrap(serverPlayer), playerChatMessage.serverContent().getString(), Text.fromGame(playerChatMessage.serverContent()), CraterServerChatEvent.MessageSource.BACKUP);
            CraterEventBus.INSTANCE.postEvent(event);
        }
    }

    @Inject(method = "placeNewPlayer", at = @At("TAIL"))
    private void injectPlayerLoginEvent(Connection arg, ServerPlayer serverPlayer, CallbackInfo ci) {
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
    private void injectPreLoginEvent(SocketAddress socketAddress, GameProfile gameProfile, CallbackInfoReturnable<Component> cir) {
        try {
            PlayerPreLoginEvent event = new PlayerPreLoginEvent(socketAddress, BridgedGameProfile.wrap(gameProfile));
            CraterEventBus.INSTANCE.postEvent(event);
            if (event.getMessage() != null) {
                cir.setReturnValue(event.getMessage().toGame());
            }
        } catch (Exception ignored) {}
    }
}
