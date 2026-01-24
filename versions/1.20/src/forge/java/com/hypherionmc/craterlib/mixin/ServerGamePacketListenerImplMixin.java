package com.hypherionmc.craterlib.mixin;

import com.hypherionmc.craterlib.api.events.server.CraterServerChatEvent;
import com.hypherionmc.craterlib.api.game.text.Text;
import com.hypherionmc.craterlib.core.event.CraterEventBus;
import com.hypherionmc.craterlib.impl.api.world.entity.player.BridgedPlayer;
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

@Mixin(value = ServerGamePacketListenerImpl.class, priority = Integer.MIN_VALUE)
public class ServerGamePacketListenerImplMixin {

    @Shadow
    public ServerPlayer player;

    @Inject(
            method = "lambda$handleChat$8",
            at = @At("HEAD"),
            cancellable = true
    )
    private void injectChatEvent(CompletableFuture<Component> completablefuture1, PlayerChatMessage arg, CompletableFuture<FilteredText> completablefuture, Void p_248218_, CallbackInfo ci) {
        Component component = completablefuture1.join();
        Component finalArg = component == null ? arg.decoratedContent() : component;
        CraterServerChatEvent event = new CraterServerChatEvent(BridgedPlayer.wrap(this.player), finalArg.getString(), Text.fromGame(finalArg));
        CraterEventBus.INSTANCE.postEvent(event);
        if (event.wasCancelled())
            ci.cancel();
    }

}
