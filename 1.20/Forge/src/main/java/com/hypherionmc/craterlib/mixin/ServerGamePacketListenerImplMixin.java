package com.hypherionmc.craterlib.mixin;

import com.hypherionmc.craterlib.api.events.server.CraterServerChatEvent;
import com.hypherionmc.craterlib.core.event.CraterEventBus;
import com.hypherionmc.craterlib.nojang.world.entity.player.BridgedPlayer;
import com.hypherionmc.craterlib.utils.ChatUtils;
import net.minecraft.network.chat.PlayerChatMessage;
import net.minecraft.server.level.ServerPlayer;
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
    private void injectChatEvent(CompletableFuture completablefuture1, PlayerChatMessage arg, CompletableFuture completablefuture, Void p_248218_, CallbackInfo ci) {
        CraterServerChatEvent event = new CraterServerChatEvent(BridgedPlayer.of(this.player), arg.decoratedContent().getString(), ChatUtils.mojangToAdventure(arg.decoratedContent()));
        CraterEventBus.INSTANCE.postEvent(event);
        if (event.wasCancelled())
            ci.cancel();
    }

}
