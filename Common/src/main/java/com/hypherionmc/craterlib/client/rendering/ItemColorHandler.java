package com.hypherionmc.craterlib.client.rendering;

import com.hypherionmc.craterlib.api.rendering.ItemDyable;
import com.hypherionmc.craterlib.util.RenderUtils;
import net.minecraft.client.color.item.ItemColor;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

/**
 * @author HypherionSA
 * Helper Class for Dyable Items implementing a simple color handler
 */
public class ItemColorHandler implements ItemColor {

    /***
     * Get the color for the Item/ItemStack
     * @param stack The ItemStack to read the color from
     * @param tintIndex No Comment
     * @return Integer value of the color
     */
    @Override
    public int getColor(@NotNull ItemStack stack, int tintIndex) {
        return this.getColorFromStack(stack);
    }

    /**
     * Get the color for the specific items stack, or return BLACK (0)
     *
     * @param stack The ItemStack to read the color from
     * @return Integer value of the color
     */
    private int getColorFromStack(@NotNull ItemStack stack) {
        if (stack.getItem() instanceof ItemDyable itemDyable) {
            return RenderUtils.renderColorFromDye(itemDyable.getColor(stack));
        }
        return 0;
    }
}
