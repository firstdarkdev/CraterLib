package me.hypherionmc.craterlib.api.rendering;

import net.minecraft.client.color.block.BlockColors;
import net.minecraft.world.item.DyeColor;

public interface DyeAble {

    BlockColors dyeHandler();

    DyeColor defaultDyeColor();

}
