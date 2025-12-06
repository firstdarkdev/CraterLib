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
import net.minecraftforge.common.ForgeHooks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = ForgeHooks.class, priority = Integer.MIN_VALUE)
public class ServerGamePacketListenerImplMixin {

    @Inject(
            method = "onServerChatSubmittedEvent",
            at = @At("HEAD"),
            cancellable = true
    )
    private static void injectChatEvent(ServerPlayer player, Component message, CallbackInfoReturnable<Component> cir) {
        CraterServerChatEvent event = new CraterServerChatEvent(BridgedPlayer.of(player), message.getString(), ChatUtils.mojangToAdventure(message));
        CraterEventBus.INSTANCE.postEvent(event);
        if (event.wasCancelled())
            cir.cancel();
    }

}
