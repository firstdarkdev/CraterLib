package com.hypherionmc.craterlib.mixin.events;

import com.hypherionmc.craterlib.api.events.server.CraterPlayerEvent;
import com.hypherionmc.craterlib.api.events.server.MessageBroadcastEvent;
import com.hypherionmc.craterlib.api.events.server.PlayerPreLoginEvent;
import com.hypherionmc.craterlib.core.event.CraterEventBus;
import com.hypherionmc.craterlib.nojang.authlib.BridgedGameProfile;
import com.hypherionmc.craterlib.nojang.world.entity.player.BridgedPlayer;
import com.hypherionmc.craterlib.utils.ChatUtils;
import com.mojang.authlib.GameProfile;
import net.minecraft.network.Connection;
import net.minecraft.network.chat.ChatType;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.PlayerList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.net.SocketAddress;
import java.util.UUID;

@Mixin(PlayerList.class)
public class PlayerListMixin {

    @Inject(method = "broadcastMessage(Lnet/minecraft/network/chat/Component;Lnet/minecraft/network/chat/ChatType;Ljava/util/UUID;)V", at = @At("HEAD"))
    private void injectBroadcast(Component component, ChatType chatType, UUID uUID, CallbackInfo ci) {
        String thread = Thread.currentThread().getStackTrace()[3].getClassName();
        MessageBroadcastEvent event = new MessageBroadcastEvent(ChatUtils.mojangToAdventure(component), (s) -> null, false, thread);
        CraterEventBus.INSTANCE.postEvent(event);
    }

    @Inject(method = "placeNewPlayer", at = @At("TAIL"))
    private void injectPlayerLoginEvent(Connection arg, ServerPlayer serverPlayer, CallbackInfo ci) {
        CraterEventBus.INSTANCE.postEvent(new CraterPlayerEvent.PlayerLoggedIn(BridgedPlayer.of(serverPlayer)));
    }

    @Inject(method = "remove", at = @At("HEAD"))
    private void injectPlayerLogoutEvent(ServerPlayer player, CallbackInfo ci) {
        CraterEventBus.INSTANCE.postEvent(new CraterPlayerEvent.PlayerLoggedOut(BridgedPlayer.of(player)));
    }

    @Inject(method = "canPlayerLogin", at = @At("HEAD"), cancellable = true)
    private void injectPreLoginEvent(SocketAddress address, GameProfile gameProfile, CallbackInfoReturnable<Component> cir) {
        PlayerPreLoginEvent event = new PlayerPreLoginEvent(address, BridgedGameProfile.of(gameProfile));
        CraterEventBus.INSTANCE.postEvent(event);
        if (event.getMessage() != null) {
            cir.setReturnValue(ChatUtils.adventureToMojang(event.getMessage()));
        }
    }
}
