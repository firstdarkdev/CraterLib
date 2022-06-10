package me.hypherionmc.craterlib.api.rendering;

import net.minecraft.client.color.block.BlockColor;
import net.minecraft.world.item.DyeColor;

/**
 * Helper Interface for Dyable Blocks
 */
public interface DyableBlock {

    /**
     * Get the BlockColor handler for the block
     *
     * @return
     */
    BlockColor dyeHandler();

    /**
     * Get the default Dye Color for Un-dyed states
     *
     * @return
     */
    DyeColor defaultDyeColor();

}
