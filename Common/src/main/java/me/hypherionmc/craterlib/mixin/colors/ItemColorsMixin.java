package me.hypherionmc.craterlib.mixin.colors;

import me.hypherionmc.craterlib.client.events.ColorRegistrationEvent;
import me.hypherionmc.craterlib.events.CraterEventBus;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.color.item.ItemColors;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * @author HypherionSA
 * @date 17/06/2022
 * Mixin to accommodate Item Color Registration across multiple Modloaders
 */
@Mixin(ItemColors.class)
public class ItemColorsMixin {

    /**
     * Inject into Vanilla code to fire off our event
     * @param cir
     */
    @Inject(method = "createDefault", at = @At("RETURN"))
    private static void injectItemColors(BlockColors $$0, CallbackInfoReturnable<ItemColors> cir) {
        CraterEventBus.post(new ColorRegistrationEvent.ITEMS(cir.getReturnValue()));
    }

}
