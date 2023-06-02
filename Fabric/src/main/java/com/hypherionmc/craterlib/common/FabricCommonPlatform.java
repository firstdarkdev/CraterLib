package com.hypherionmc.craterlib.common;

import com.hypherionmc.craterlib.api.blockentity.caps.CraterCapabilityHandler;
import com.hypherionmc.craterlib.api.blockentity.caps.ICraterCapProvider;
import com.hypherionmc.craterlib.core.network.CraterNetworkHandler;
import com.hypherionmc.craterlib.core.platform.CommonPlatform;
import com.hypherionmc.craterlib.network.FabricNetworkHandler;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.minecraft.core.Direction;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.apache.commons.lang3.function.TriFunction;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.function.Consumer;

/**
 * @author HypherionSA
 */
public class FabricCommonPlatform implements CommonPlatform {

    public static MinecraftServer server;

    @Override
    public CraterNetworkHandler createPacketHandler(String modid, boolean requireClient, boolean requireServer) {
        return FabricNetworkHandler.of(modid);
    }

    @Override
    public MinecraftServer getMCServer() {
        return server;
    }

    @Override
    public void openMenu(ServerPlayer player, MenuProvider menu, Consumer<FriendlyByteBuf> initialData) {
        ExtendedScreenHandlerFactory factory = new ExtendedScreenHandlerFactory() {
            @Override
            public void writeScreenOpeningData(ServerPlayer player, FriendlyByteBuf buf) {
                initialData.accept(buf);
            }

            @Override
            public @NotNull Component getDisplayName() {
                return menu.getDisplayName();
            }

            @Nullable
            @Override
            public AbstractContainerMenu createMenu(int i, @NotNull Inventory inventory, @NotNull Player player) {
                return menu.createMenu(i, inventory, player);
            }
        };

        player.openMenu(factory);
    }

    @Override
    public <T extends AbstractContainerMenu> MenuType<T> createMenuType(TriFunction<Integer, Inventory, FriendlyByteBuf, T> constructor) {
        return new ExtendedScreenHandlerType<>(constructor::apply);
    }

    @Override
    public <T> Optional<T> getCapabilityHandler(BlockEntity entity, Direction side, CraterCapabilityHandler capability) {
        if (entity instanceof ICraterCapProvider capProvider) {
            return capProvider.getCapability(capability, side);
        }
        return Optional.empty();
    }
}
