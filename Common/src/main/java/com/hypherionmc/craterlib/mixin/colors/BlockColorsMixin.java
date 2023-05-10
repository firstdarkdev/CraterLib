package com.hypherionmc.craterlib.mixin.colors;

import com.hypherionmc.craterlib.api.event.client.ColorRegistrationEvent;
import com.hypherionmc.craterlib.core.event.CraterEventBus;
import net.minecraft.client.color.block.BlockColors;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * @author HypherionSA
 * Mixin to accommodate Block Color Registration across multiple Modloaders
 */
@Mixin(BlockColors.class)
public class BlockColorsMixin {

    /**
     * Inject into Vanilla code to fire off our event
     */
    @Inject(method = "createDefault", at = @At("RETURN"))
    private static void injectBlockColors(CallbackInfoReturnable<BlockColors> cir) {
        ColorRegistrationEvent.Blocks blockEvent = new ColorRegistrationEvent.Blocks(cir.getReturnValue());
        CraterEventBus.INSTANCE.postEvent(blockEvent);
    }

}
