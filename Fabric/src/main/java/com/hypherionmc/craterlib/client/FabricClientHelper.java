package com.hypherionmc.craterlib.client;

import com.hypherionmc.craterlib.api.rendering.CustomRenderType;
import com.hypherionmc.craterlib.common.item.BlockItemDyable;
import com.hypherionmc.craterlib.core.platform.services.LibClientHelper;
import com.hypherionmc.craterlib.core.systems.reg.RegistryObject;
import com.hypherionmc.craterlib.util.ColorPropertyFunction;
import net.fabricmc.api.EnvType;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.BlockEntityRendererRegistry;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.object.builder.v1.client.model.FabricModelPredicateProviderRegistry;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.network.Connection;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;

import java.util.Collection;

/**
 * @author HypherionSA
 */
public class FabricClientHelper implements LibClientHelper {

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

    @Override
    public Minecraft getClientInstance() {
        return Minecraft.getInstance();
    }

    @Override
    public Player getClientPlayer() {
        return Minecraft.getInstance().player;
    }

    @Override
    public Level getClientLevel() {
        return Minecraft.getInstance().level;
    }

    @Override
    public Connection getClientConnection() {
        return Minecraft.getInstance().getConnection().getConnection();
    }

    public static void registerCreativeItems(CreativeModeTab tab, FabricItemGroupEntries entries) {

    }

    @Override
    public void registerBlockEntityRenderer(BlockEntityType<? extends BlockEntity> blockEntityType, BlockEntityRendererProvider blockEntityRendererFactory) {
        BlockEntityRendererRegistry.register(blockEntityType, blockEntityRendererFactory);
    }
}
