package com.hypherionmc.craterlib.mixin;

import com.hypherionmc.craterlib.api.events.server.CraterServerChatEvent;
import com.hypherionmc.craterlib.api.game.text.Text;
import com.hypherionmc.craterlib.core.event.CraterEventBus;
import com.hypherionmc.craterlib.impl.api.world.entity.player.BridgedPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.event.ServerChatEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ForgeHooks.class)
public class CommonHooksMixin {

        event.setUpstreamCancelled(cir.getReturnValue() == null);
    @Inject(method = "onServerChatEvent(Lnet/minecraft/server/network/ServerGamePacketListenerImpl;Ljava/lang/String;Lnet/minecraft/network/chat/Component;Ljava/lang/String;Lnet/minecraft/network/chat/Component;)Lnet/minecraftforge/event/ServerChatEvent;", at = @At("RETURN"), cancellable = true)
    private static void injectServerChatEvent(ServerGamePacketListenerImpl net, String raw, Component comp, String filtered, Component filteredComp, CallbackInfoReturnable<ServerChatEvent> cir) {
        Component finalComp = cir.getReturnValue() == null || cir.getReturnValue().getComponent() == null ? comp : cir.getReturnValue().getComponent();
        CraterServerChatEvent event = new CraterServerChatEvent(BridgedPlayer.wrap(net.player), finalComp.getString(), Text.fromGame(finalComp));
        event.setWasUpstreamCancelled(cir.getReturnValue() == null || cir.getReturnValue().getComponent() == null);
        CraterEventBus.INSTANCE.postEvent(event);

        if (event.wasCancelled())
            cir.setReturnValue(null);
    }

}
