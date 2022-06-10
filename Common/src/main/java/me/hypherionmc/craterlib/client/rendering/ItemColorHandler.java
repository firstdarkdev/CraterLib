package me.hypherionmc.craterlib.client.rendering;

import me.hypherionmc.craterlib.api.rendering.ItemDyable;
import net.minecraft.client.color.item.ItemColor;
import net.minecraft.world.item.ItemStack;

/**
 * Helper Class for Dyable Items implementing a simple color handler
 */
public class ItemColorHandler implements ItemColor {

    /***
     * Get the color for the Item/ItemStack
     * @param stack
     * @param tintIndex
     * @return
     */
    @Override
    public int getColor(ItemStack stack, int tintIndex) {
        return this.getColorFromStack(stack);
    }

    /**
     * Get the color for the specific items stack, or return BLACK (0)
     *
     * @param stack
     * @return
     */
    private int getColorFromStack(ItemStack stack) {
        if (stack.getItem() instanceof ItemDyable itemDyable) {
            return itemDyable.getColor().getMaterialColor().col;
        }
        return 0;
    }
}
