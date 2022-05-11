package me.hypherionmc.craterlib.client.rendering;

import me.hypherionmc.craterlib.api.rendering.DyableBlock;
import me.hypherionmc.craterlib.api.rendering.ItemDyable;
import me.hypherionmc.craterlib.common.item.BlockItemDyable;
import net.minecraft.client.color.item.ItemColors;
import net.minecraft.world.item.ItemStack;

public class ItemColorHandler extends ItemColors {

    @Override
    public int getColor(ItemStack stack, int tintIndex) {
        return this.getColorFromStack(stack);
    }

    private int getColorFromStack(ItemStack stack) {
        if (stack.getItem() instanceof ItemDyable itemDyable) {
            return itemDyable.getColor().getMaterialColor().col;
        }
        return 0;
    }
}
