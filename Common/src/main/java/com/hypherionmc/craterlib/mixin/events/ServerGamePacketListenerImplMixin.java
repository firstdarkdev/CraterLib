package com.hypherionmc.craterlib.mixin.events;

import com.hypherionmc.craterlib.api.event.server.CraterServerChatEvent;
import com.hypherionmc.craterlib.core.event.CraterEventBus;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.PlayerChatMessage;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.FilteredText;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.concurrent.CompletableFuture;

@Mixin(value = ServerGamePacketListenerImpl.class, priority = Integer.MIN_VALUE)
public class ServerGamePacketListenerImplMixin {

    @Shadow
    public ServerPlayer player;

    @Inject(
            method = "lambda$handleChat$8",
            at = @At("HEAD"),
            cancellable = true
    )
    private void injectChatEvent(PlayerChatMessage arg, CompletableFuture completableFuture, CompletableFuture completableFuture2, Void void_, CallbackInfo ci) {
        CraterServerChatEvent event = new CraterServerChatEvent(this.player, arg.decoratedContent().getString(), arg.decoratedContent());
        CraterEventBus.INSTANCE.postEvent(event);
        if (event.wasCancelled())
            ci.cancel();
    }

}
