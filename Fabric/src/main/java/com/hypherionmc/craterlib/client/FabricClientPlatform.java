package com.hypherionmc.craterlib.client;

import com.hypherionmc.craterlib.api.rendering.CustomRenderType;
import com.hypherionmc.craterlib.common.item.BlockItemDyable;
import com.hypherionmc.craterlib.core.platform.ClientPlatform;
import com.hypherionmc.craterlib.core.systems.reg.RegistryObject;
import com.hypherionmc.craterlib.util.ColorPropertyFunction;
import net.fabricmc.api.EnvType;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.network.Connection;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

/**
 * @author HypherionSA
 */
public class FabricClientPlatform implements ClientPlatform {

    @Override
    public void registerItemProperty(@NotNull BlockItemDyable item, @NotNull String property) {
        if (FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT) {
            ItemProperties.register(item, new ResourceLocation(property), new ColorPropertyFunction(item));
        }
    }

    @Override
    public void registerCustomRenderTypes(Collection<RegistryObject<Block>> blocks) {
        blocks.forEach(blk -> {
            if (blk.get() instanceof CustomRenderType type) {
                BlockRenderLayerMap.INSTANCE.putBlock(blk.get(), type.getCustomRenderType());
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

    @Override
    public void registerBlockEntityRenderer(@NotNull BlockEntityType<? extends BlockEntity> blockEntityType, @NotNull BlockEntityRendererProvider blockEntityRendererFactory) {
        BlockEntityRenderers.register(blockEntityType, blockEntityRendererFactory);
    }
}
