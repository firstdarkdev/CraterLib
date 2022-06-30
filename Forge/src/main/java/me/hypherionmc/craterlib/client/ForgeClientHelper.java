package me.hypherionmc.craterlib.client;

import me.hypherionmc.craterlib.platform.services.LibClientHelper;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

import java.util.function.Supplier;

/**
 * @author HypherionSA
 * @date 16/06/2022
 */
class ForgeClientHelper implements LibClientHelper {

    @Override
    public CreativeModeTab tabBuilder(String modid, String tabid, Supplier<ItemStack> icon, String backgroundSuf) {
        CreativeModeTab tab = new CreativeModeTab(modid + "." + tabid) {
            @Override
            public ItemStack makeIcon() {
                if (icon != null) {
                    return icon.get();
                } else {
                    return ItemStack.EMPTY;
                }
            }
        };

        if (backgroundSuf != null && !backgroundSuf.isEmpty()) {
            tab.setBackgroundSuffix(backgroundSuf);
        }
        return tab;
    }

}
