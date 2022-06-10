package me.hypherionmc.craterlib.api.rendering;

import net.minecraft.world.item.DyeColor;

/**
 * Helper Interface for Dyable Items
 */
public interface ItemDyable {

    /**
     * Get the DyeColor of the Item
     *
     * @return
     */
    public DyeColor getColor();

}
