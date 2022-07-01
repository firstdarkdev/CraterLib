package me.hypherionmc.craterlib.api.rendering;

import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;

/**
 * Helper Interface for Dyable Items
 */
public interface ItemDyable {

    /**
     * Get the DyeColor of the Item
     *
     * @return
     */
    public DyeColor getColor(ItemStack stack);

}
