package com.hypherionmc.craterlib.common;

import com.hypherionmc.craterlib.api.blockentity.caps.CraterCapabilityHandler;
import com.hypherionmc.craterlib.core.network.CraterNetworkHandler;
import com.hypherionmc.craterlib.core.platform.CommonPlatform;
import com.hypherionmc.craterlib.network.NeoForgeNetworkHandler;
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
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.server.ServerLifecycleHooks;
import org.apache.commons.lang3.function.TriFunction;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;

/**
 * @author HypherionSA
 */
public class NeoForgeCommonHelper implements CommonPlatform {

    public static Map<ResourceLocation, CreativeModeTab> TABS = new HashMap<>();

    public NeoForgeCommonHelper() {}

    @Override
    public CraterNetworkHandler createPacketHandler(String modid, boolean requiredClient, boolean requiredServer) {
        return NeoForgeNetworkHandler.of(modid, requiredClient, requiredServer);
    }

    @Override
    public MinecraftServer getMCServer() {
        return ServerLifecycleHooks.getCurrentServer();
    }

    @Override
    public void openMenu(ServerPlayer player, MenuProvider menu, @Nullable Consumer<FriendlyByteBuf> initialData) {
        // TODO Fix Menus
        /*if (initialData != null) {
            player.openMenu(menu, initialData);
        } else {
            player.openMenu(menu, player.getOnPos());
        }*/
    }

    @Override
    public <T extends AbstractContainerMenu> MenuType<T> createMenuType(TriFunction<Integer, Inventory, FriendlyByteBuf, T> constructor) {
        return IMenuTypeExtension.create(constructor::apply);
    }

    @Override
    public <T> Optional<T> getCapabilityHandler(BlockEntity entity, Direction side, CraterCapabilityHandler capability) {
        // TODO Fix This
        /*if (capability == CraterCapabilityHandler.ENERGY) {
            AtomicReference<NeoForgeEnergyReader> energyReference = new AtomicReference<>();
            entity.getCapability(Capabilities.ENERGY, side).ifPresent(storage -> energyReference.set(new NeoForgeEnergyReader(storage)));

            return energyReference.get() != null ? (Optional<T>) Optional.of(energyReference.get()) : Optional.empty();
        }

        if (capability == CraterCapabilityHandler.FLUID) {
            AtomicReference<ICraterFluidHandler> craterFluidHandler = new AtomicReference<>();
            entity.getCapability(Capabilities.FLUID_HANDLER, side).ifPresent(iFluidHandler -> craterFluidHandler.set(new NeoForgeFluidReader(iFluidHandler)));

            return craterFluidHandler.get() != null ? (Optional<T>) Optional.of(craterFluidHandler.get()) : Optional.empty();
        }

        if (entity instanceof ICraterCapProvider capProvider) {
            return capProvider.getCapability(capability, side);
        }*/

        return Optional.empty();
    }
}
