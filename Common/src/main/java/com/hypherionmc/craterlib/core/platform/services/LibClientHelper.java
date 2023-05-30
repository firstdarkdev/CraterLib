package com.hypherionmc.craterlib.core.platform.services;

import com.hypherionmc.craterlib.common.item.BlockItemDyable;
import com.hypherionmc.craterlib.core.systems.reg.RegistryObject;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.network.Connection;
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
public interface LibClientHelper {

    void registerItemProperty(@NotNull BlockItemDyable item, @NotNull String property);

    void registerCustomRenderTypes(
            @NotNull Collection<RegistryObject<Block>> blocks,
            @NotNull Collection<RegistryObject<Item>> items
    );

    Minecraft getClientInstance();

    Player getClientPlayer();

    Level getClientLevel();

    Connection getClientConnection();

    void registerBlockEntityRenderer(@NotNull BlockEntityType<? extends BlockEntity> blockEntityType, @NotNull BlockEntityRendererProvider blockEntityRendererFactory);

}
