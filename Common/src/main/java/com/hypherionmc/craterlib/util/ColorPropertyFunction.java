package com.hypherionmc.craterlib.util;

import com.hypherionmc.craterlib.common.item.BlockItemDyable;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.item.ClampedItemPropertyFunction;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

/**
 * @author HypherionSA
 */
public class ColorPropertyFunction implements ClampedItemPropertyFunction {

    private final BlockItemDyable item;

    public ColorPropertyFunction(BlockItemDyable item) {
        this.item = item;
    }

    @Override
    public float call(ItemStack itemStack, @Nullable ClientLevel clientLevel, @Nullable LivingEntity livingEntity, int i) {
        return Mth.clamp(this.unclampedCall(itemStack, clientLevel, livingEntity, i), 0.0F, 15.0F);
    }

    @Override
    public float unclampedCall(ItemStack itemStack, @Nullable ClientLevel clientLevel, @Nullable LivingEntity livingEntity, int i) {
        DyeColor color = item.getColorFromNBT(itemStack);
        return color.getId();
    }

}
