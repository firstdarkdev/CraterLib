package me.hypherionmc.craterlib.mixin.colors;

import me.hypherionmc.craterlib.client.events.ColorRegistrationEvent;
import me.hypherionmc.craterlib.events.CraterEventBus;
import net.minecraft.client.color.block.BlockColors;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * @author HypherionSA
 * @date 17/06/2022
 * Mixin to accommodate Block Color Registration across multiple Modloaders
 */
@Mixin(BlockColors.class)
public class BlockColorsMixin {

    /**
     * Inject into Vanilla code to fire off our event
     * @param cir
     */
    @Inject(method = "createDefault", at = @At("RETURN"))
    private static void injectBlockColors(CallbackInfoReturnable<BlockColors> cir) {
        CraterEventBus.post(new ColorRegistrationEvent.BLOCKS(cir.getReturnValue()));
    }

}
