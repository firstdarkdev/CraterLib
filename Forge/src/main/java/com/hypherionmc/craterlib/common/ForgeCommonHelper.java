package com.hypherionmc.craterlib.common;

import com.hypherionmc.craterlib.api.blockentity.caps.CraterCapabilityHandler;
import com.hypherionmc.craterlib.api.blockentity.caps.ICraterCapProvider;
import com.hypherionmc.craterlib.core.network.CraterNetworkHandler;
import com.hypherionmc.craterlib.core.platform.CommonPlatform;
import com.hypherionmc.craterlib.core.systems.fluid.ICraterFluidHandler;
import com.hypherionmc.craterlib.network.ForgeNetworkHandler;
import com.hypherionmc.craterlib.systems.energy.ForgeEnergyReader;
import com.hypherionmc.craterlib.systems.fluid.ForgeFluidReader;
import net.minecraft.core.Direction;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.server.ServerLifecycleHooks;
import org.apache.commons.lang3.function.TriFunction;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

/**
 * @author HypherionSA
 */
public class ForgeCommonHelper implements CommonPlatform {

    public static Map<ResourceLocation, CreativeModeTab> TABS = new HashMap<>();

    public ForgeCommonHelper() {}

    @Override
    public CraterNetworkHandler createPacketHandler(String modid, boolean requiredClient, boolean requiredServer) {
        return ForgeNetworkHandler.of(modid, requiredClient, requiredServer);
    }

    @Override
    public MinecraftServer getMCServer() {
        return ServerLifecycleHooks.getCurrentServer();
    }

    @Override
    public void openMenu(ServerPlayer player, MenuProvider menu, @Nullable Consumer<FriendlyByteBuf> initialData) {
        if (initialData != null) {
            NetworkHooks.openScreen(player, menu, initialData);
        } else {
            NetworkHooks.openScreen(player, menu, player.getOnPos());
        }
    }

    @Override
    public <T extends AbstractContainerMenu> MenuType<T> createMenuType(TriFunction<Integer, Inventory, FriendlyByteBuf, T> constructor) {
        return IForgeMenuType.create(constructor::apply);
    }

    @Override
    public <T> Optional<T> getCapabilityHandler(BlockEntity entity, Direction side, CraterCapabilityHandler capability) {
        if (capability == CraterCapabilityHandler.ENERGY) {
            AtomicReference<ForgeEnergyReader> energyReference = new AtomicReference<>();
            entity.getCapability(ForgeCapabilities.ENERGY, side).ifPresent(storage -> energyReference.set(new ForgeEnergyReader(storage)));

            return energyReference.get() != null ? (Optional<T>) Optional.of(energyReference.get()) : Optional.empty();
        }

        if (capability == CraterCapabilityHandler.FLUID) {
            AtomicReference<ICraterFluidHandler> craterFluidHandler = new AtomicReference<>();
            entity.getCapability(ForgeCapabilities.FLUID_HANDLER, side).ifPresent(iFluidHandler -> craterFluidHandler.set(new ForgeFluidReader(iFluidHandler)));

            return craterFluidHandler.get() != null ? (Optional<T>) Optional.of(craterFluidHandler.get()) : Optional.empty();
        }

        if (entity instanceof ICraterCapProvider capProvider) {
            return capProvider.getCapability(capability, side);
        }

        return Optional.empty();
    }
}
