package me.hypherionmc.craterlib.client;

import me.hypherionmc.craterlib.api.rendering.CustomRenderType;
import me.hypherionmc.craterlib.common.item.BlockItemDyable;
import me.hypherionmc.craterlib.platform.services.LibClientHelper;
import me.hypherionmc.craterlib.systems.reg.RegistryObject;
import me.hypherionmc.craterlib.util.ColorPropertyFunction;
import net.fabricmc.api.EnvType;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.object.builder.v1.client.model.FabricModelPredicateProviderRegistry;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Fluid;

import java.util.Collection;
import java.util.function.Supplier;

/**
 * @author HypherionSA
 * @date 16/06/2022
 */
public class FabricClientHelper implements LibClientHelper {

    @Override
    public CreativeModeTab tabBuilder(String modid, String tabid, Supplier<ItemStack> icon, String backgroundSuf) {
        FabricItemGroupBuilder tab = FabricItemGroupBuilder.create(new ResourceLocation(modid, tabid));

        if (icon != null) {
            tab.icon(icon);
        }

        CreativeModeTab tab1 = tab.build();

        if (backgroundSuf != null && !backgroundSuf.isEmpty()) {
            tab1.setBackgroundSuffix(backgroundSuf);
        }
        return tab1;
    }

    @Override
    public void registerItemProperty(BlockItemDyable item, String property) {
        if (FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT) {
            FabricModelPredicateProviderRegistry.register(item, new ResourceLocation(property), new ColorPropertyFunction(item));
        }
    }

    @Override
    public void registerCustomRenderTypes(Collection<RegistryObject<Block>> blocks, Collection<RegistryObject<Item>> items) {
        blocks.forEach(blk -> {
            if (blk.get() instanceof CustomRenderType type) {
                BlockRenderLayerMap.INSTANCE.putBlock(blk.get(), type.getCustomRenderType());
            }
        });

        items.forEach(itm -> {
            if (itm.get() instanceof BlockItemDyable dyable && dyable.getBlock() instanceof CustomRenderType customRenderType) {
                BlockRenderLayerMap.INSTANCE.putItem(itm.get(), customRenderType.getCustomRenderType());
            }

            if (itm.get() instanceof CustomRenderType customRenderType) {
                BlockRenderLayerMap.INSTANCE.putItem(itm.get(), customRenderType.getCustomRenderType());
            }
        });
    }
}
