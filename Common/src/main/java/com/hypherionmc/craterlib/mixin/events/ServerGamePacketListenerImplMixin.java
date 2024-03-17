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
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.concurrent.CompletableFuture;

@Mixin(value = ServerGamePacketListenerImpl.class, priority = Integer.MIN_VALUE)
public class ServerGamePacketListenerImplMixin {

    @Shadow
    public ServerPlayer player;

    @Inject(
            method = "lambda$handleChat$9",
            at = @At("HEAD"),
            cancellable = true
    )
    private void injectChatEvent(PlayerChatMessage arg, CallbackInfoReturnable<CompletableFuture> ci) {
        CraterServerChatEvent event = new CraterServerChatEvent(this.player, arg.serverContent().getString(), arg.serverContent());
        CraterEventBus.INSTANCE.postEvent(event);
        if (event.wasCancelled())
            ci.cancel();
    }

}
