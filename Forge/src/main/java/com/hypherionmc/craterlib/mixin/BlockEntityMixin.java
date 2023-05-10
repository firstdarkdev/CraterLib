package com.hypherionmc.craterlib.mixin;

import com.hypherionmc.craterlib.api.blockentity.caps.CraterCapabilityHandler;
import com.hypherionmc.craterlib.api.blockentity.caps.ICraterCapProvider;
import com.hypherionmc.craterlib.common.blockentity.CraterBlockEntity;
import com.hypherionmc.craterlib.core.systems.energy.CustomEnergyStorage;
import com.hypherionmc.craterlib.core.systems.fluid.ICraterFluidHandler;
import com.hypherionmc.craterlib.core.systems.inventory.SimpleInventory;
import com.hypherionmc.craterlib.systems.energy.ForgeEnergyWrapper;
import com.hypherionmc.craterlib.systems.fluid.ForgeWrappedFluidTank;
import com.hypherionmc.craterlib.systems.inventory.ForgeInventoryWrapper;
import net.minecraft.core.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.wrapper.SidedInvWrapper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;

import java.util.Optional;

/**
 * @author HypherionSA
 */
@Mixin(CraterBlockEntity.class)
public class BlockEntityMixin implements ICapabilityProvider {

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        ICraterCapProvider capProvider = (ICraterCapProvider) this;

        if (cap == ForgeCapabilities.ENERGY) {
            Optional<CustomEnergyStorage> forgeCap = capProvider.getCapability(CraterCapabilityHandler.ENERGY, side);
            if (forgeCap.isPresent()) {
                return LazyOptional.of(() -> new ForgeEnergyWrapper(forgeCap.get())).cast();
            }
        }

        if (cap == ForgeCapabilities.ITEM_HANDLER) {
            Optional<SimpleInventory> inventory = capProvider.getCapability(CraterCapabilityHandler.ITEM, side);
            if (inventory.isPresent()) {
                return LazyOptional.of(() -> new SidedInvWrapper(new ForgeInventoryWrapper(inventory.get()), side)).cast();
            }
        }

        if (cap == ForgeCapabilities.FLUID_HANDLER) {
            Optional<ICraterFluidHandler> fluidTank = capProvider.getCapability(CraterCapabilityHandler.FLUID, side);
            if (fluidTank.isPresent()) {
                return LazyOptional.of(() -> (ForgeWrappedFluidTank)fluidTank.get()).cast();
            }
        }

        return LazyOptional.empty();
    }
}
