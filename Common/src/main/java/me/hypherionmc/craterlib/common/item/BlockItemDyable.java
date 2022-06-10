package me.hypherionmc.craterlib.common.item;

import me.hypherionmc.craterlib.api.rendering.DyableBlock;
import me.hypherionmc.craterlib.api.rendering.ItemDyable;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.Block;

/**
 * Base Item for Blocks that implement @link {DyableBlock}.
 */
public class BlockItemDyable extends BlockItem implements ItemDyable {

    public BlockItemDyable(Block block, Properties properties) {
        super(block, properties);
    }

    /**
     * Get the Item Color from the block
     *
     * @return
     */
    @Override
    public DyeColor getColor() {
        if (this.getBlock() instanceof DyableBlock dyableBlock) {
            return dyableBlock.defaultDyeColor();
        }
        return DyeColor.BLACK;
    }
}
