package com.hypherionmc.craterlib.core.platform;

import com.hypherionmc.craterlib.common.item.BlockItemDyable;
import com.hypherionmc.craterlib.util.ServiceUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.network.Connection;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import org.jetbrains.annotations.NotNull;

/**
 * @author HypherionSA
 */
public interface ClientPlatform {

    public final ClientPlatform INSTANCE = ServiceUtil.load(ClientPlatform.class);

    void registerItemProperty(@NotNull BlockItemDyable item, @NotNull String property);

    //void registerCustomRenderTypes(@NotNull Collection<RegistryObject<Block>> blocks);

    Minecraft getClientInstance();

    Player getClientPlayer();

    Level getClientLevel();

    Connection getClientConnection();

    void registerBlockEntityRenderer(@NotNull BlockEntityType<? extends BlockEntity> blockEntityType, @NotNull BlockEntityRendererProvider blockEntityRendererFactory);

}
