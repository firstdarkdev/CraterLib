package me.hypherionmc.craterlib.client;

import me.hypherionmc.craterlib.common.item.BlockItemDyable;
import me.hypherionmc.craterlib.platform.services.LibClientHelper;
import me.hypherionmc.craterlib.util.ColorPropertyFunction;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fml.loading.FMLEnvironment;

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

    @Override
    public void registerItemProperty(BlockItemDyable item, String property) {
        if (FMLEnvironment.dist.isClient()) {
            ItemProperties.register(item, new ResourceLocation(property), new ColorPropertyFunction(item));
        }
    }
}
