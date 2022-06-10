package me.hypherionmc.craterlib.common.item;

import me.hypherionmc.craterlib.api.rendering.ItemDyable;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.material.Fluid;

/**
 * A custom water bucket that supports Dye Colors
 */
public class DyableWaterBucket extends BucketItem implements ItemDyable {

    private final DyeColor color;
    private final boolean isGlowing;

    public DyableWaterBucket(Fluid fluid, Properties properties, DyeColor color, boolean isGlowing) {
        super(fluid, properties);
        this.color = color;
        this.isGlowing = isGlowing;
    }

    /**
     * Normally this is used for enchanted items, in this case, it's used to check if the fluid is a glowing fluid
     *
     * @param stack
     * @return
     */
    @Override
    public boolean isFoil(ItemStack stack) {
        return this.isGlowing;
    }

    @Override
    public DyeColor getColor() {
        return this.color;
    }

    public boolean isGlowing() {
        return isGlowing;
    }
}
