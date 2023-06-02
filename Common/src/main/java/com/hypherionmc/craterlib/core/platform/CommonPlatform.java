package com.hypherionmc.craterlib.core.platform;

import com.hypherionmc.craterlib.api.blockentity.caps.CraterCapabilityHandler;
import com.hypherionmc.craterlib.core.network.CraterNetworkHandler;
import com.hypherionmc.craterlib.util.ServiceUtil;
import net.minecraft.core.Direction;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.apache.commons.lang3.function.TriFunction;

import javax.annotation.Nullable;
import java.util.Optional;
import java.util.function.Consumer;

/**
 * @author HypherionSA
 */
public interface CommonPlatform {

    public CommonPlatform INSTANCE = ServiceUtil.load(CommonPlatform.class);

    default CraterNetworkHandler createPacketHandler(String modid) {
        return this.createPacketHandler(modid, true, true);
    }

    CraterNetworkHandler createPacketHandler(String modid, boolean requiredClient, boolean requiredServer);

    MinecraftServer getMCServer();

    default void openMenu(ServerPlayer player, MenuProvider menuProvider) {
        this.openMenu(player, menuProvider, null);
    }

    void openMenu(ServerPlayer player, MenuProvider menu, @Nullable Consumer<FriendlyByteBuf> initialData);

    <T extends AbstractContainerMenu> MenuType<T> createMenuType(TriFunction<Integer, Inventory, FriendlyByteBuf, T> constructor);

    <T> Optional<T> getCapabilityHandler(BlockEntity entity, Direction side, CraterCapabilityHandler capability);
}
