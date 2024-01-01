package com.hypherionmc.craterlib.mixin.events;

import com.hypherionmc.craterlib.api.event.server.CraterServerChatEvent;
import com.hypherionmc.craterlib.core.event.CraterEventBus;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraft.server.network.TextFilter;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerGamePacketListenerImpl.class)
public class ServerGamePacketListenerImplMixin {

    @Shadow public ServerPlayer player;

    @Inject(method = "handleChat(Lnet/minecraft/server/network/TextFilter$FilteredText;)V", at = @At("HEAD"), cancellable = true)
    private void injectChatEvent(TextFilter.FilteredText filteredText, CallbackInfo ci) {
        Component message = new TextComponent(filteredText.getRaw());
        if (message.getString().startsWith("/"))
            return;

        CraterServerChatEvent event = new CraterServerChatEvent(this.player, message.getString(), message);
        CraterEventBus.INSTANCE.postEvent(event);
        if (event.wasCancelled())
            ci.cancel();
    }

}
