package com.hypherionmc.craterlib.mixin;

import com.hypherionmc.craterlib.api.events.server.CraterServerChatEvent;
import com.hypherionmc.craterlib.api.game.text.Text;
import com.hypherionmc.craterlib.core.event.CraterEventBus;
import com.hypherionmc.craterlib.impl.api.world.entity.player.BridgedPlayer;
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

@Mixin(value = ServerGamePacketListenerImpl.class, priority = Integer.MIN_VALUE)
public class ServerGamePacketListenerImplMixin {

    @Shadow
    public ServerPlayer player;

    @Inject(
            method = "handleChat(Lnet/minecraft/server/network/TextFilter$FilteredText;)V",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/server/network/TextFilter$FilteredText;getFiltered()Ljava/lang/String;"),
            cancellable = true
    )
    private void injectChatEvent(TextFilter.FilteredText arg, CallbackInfo ci) {
        Component message = new TextComponent(arg.getRaw());
        if (message.getString().startsWith("/"))
           return;

        CraterServerChatEvent event = new CraterServerChatEvent(BridgedPlayer.wrap(this.player), message.getString(), Text.fromGame(message));
        CraterEventBus.INSTANCE.postEvent(event);
        if (event.wasCancelled())
            ci.cancel();
    }

}
