package com.hypherionmc.craterlib.mixin;

import com.hypherionmc.craterlib.api.events.server.CraterServerChatEvent;
import com.hypherionmc.craterlib.api.game.text.Text;
import com.hypherionmc.craterlib.core.event.CraterEventBus;
import com.hypherionmc.craterlib.impl.api.world.entity.player.BridgedPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.common.ForgeHooks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ForgeHooks.class)
public class CommonHooksMixin {

    @Inject(method = "onServerChatSubmittedEvent", at = @At("RETURN"), cancellable = true)
    private static void injectServerChatEvent(ServerPlayer player, String plain, Component decorated, CallbackInfoReturnable<Component> cir) {
        Component finalComp = cir.getReturnValue() == null ? decorated : cir.getReturnValue();
        CraterServerChatEvent event = new CraterServerChatEvent(BridgedPlayer.wrap(player), finalComp.getString(), Text.fromGame(finalComp));
        event.setUpstreamCancelled(cir.getReturnValue() == null);
        CraterEventBus.INSTANCE.postEvent(event);

        if (event.wasCancelled())
            cir.setReturnValue(null);
    }

}
