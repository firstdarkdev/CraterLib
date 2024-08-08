package com.hypherionmc.craterlib.mixin;

import com.hypherionmc.craterlib.api.events.server.CraterServerChatEvent;
import com.hypherionmc.craterlib.core.event.CraterEventBus;
import com.hypherionmc.craterlib.nojang.world.entity.player.BridgedPlayer;
import com.hypherionmc.craterlib.utils.ChatUtils;
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
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

@Mixin(value = ServerGamePacketListenerImpl.class, priority = Integer.MIN_VALUE)
public class ServerGamePacketListenerImplMixin {

    @Shadow
    public ServerPlayer player;

    @Inject(
            method = "lambda$handleChat$9",
            at = @At("HEAD"),
            cancellable = true
    )
    private void injectChatEvent(CompletableFuture<FilteredText> completableFuture, CompletableFuture<Component> completableFuture2, PlayerChatMessage arg, Executor executor, CallbackInfoReturnable<CompletableFuture> cir) {
        Component comp2 = completableFuture2.join();
        Component finalArg = comp2 == null ? arg.decoratedContent() : comp2;
        CraterServerChatEvent event = new CraterServerChatEvent(BridgedPlayer.of(this.player), finalArg.getString(), ChatUtils.mojangToAdventure(finalArg));
        CraterEventBus.INSTANCE.postEvent(event);
        if (event.wasCancelled())
            cir.cancel();
    }

}
