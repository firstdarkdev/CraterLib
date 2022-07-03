package me.hypherionmc.craterlib.platform.services;

import me.hypherionmc.craterlib.common.item.BlockItemDyable;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

import java.util.function.Supplier;

/**
 * @author HypherionSA
 * @date 16/06/2022
 */
public interface LibClientHelper {

    public CreativeModeTab tabBuilder(String modid, String tabid, Supplier<ItemStack> icon, String backgroundSuf);

    public void registerItemProperty(BlockItemDyable item, String property);

}
