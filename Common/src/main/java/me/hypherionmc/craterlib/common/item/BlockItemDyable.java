package me.hypherionmc.craterlib.common.item;

import me.hypherionmc.craterlib.api.rendering.DyableBlock;
import me.hypherionmc.craterlib.api.rendering.ItemDyable;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.Block;

public class BlockItemDyable extends BlockItem implements ItemDyable {

    public BlockItemDyable(Block block, Properties properties) {
        super(block, properties);
    }

    @Override
    public DyeColor getColor() {
        if (this.getBlock() instanceof DyableBlock dyableBlock) {
            return dyableBlock.defaultDyeColor();
        }
        return DyeColor.BLACK;
    }
}
