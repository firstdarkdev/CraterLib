package me.hypherionmc.craterlib.mixin;

import me.hypherionmc.craterlib.api.blockentities.caps.CapabilityHandler;
import me.hypherionmc.craterlib.api.blockentities.caps.ICraterCapProvider;
import me.hypherionmc.craterlib.common.blockentity.CraterBlockEntity;
import me.hypherionmc.craterlib.systems.energy.CustomEnergyStorage;
import me.hypherionmc.craterlib.systems.energy.ForgeEnergyWrapper;
import me.hypherionmc.craterlib.systems.fluid.CraterFluidTank;
import me.hypherionmc.craterlib.systems.fluid.ForgeWrappedFluidTank;
import me.hypherionmc.craterlib.systems.fluid.ICraterFluidHandler;
import me.hypherionmc.craterlib.systems.inventory.ForgeInventoryWrapper;
import me.hypherionmc.craterlib.systems.inventory.SimpleInventory;
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
 * @date 24/09/2022
 */
@Mixin(CraterBlockEntity.class)
public class BlockEntityMixin implements ICapabilityProvider {

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        ICraterCapProvider capProvider = (ICraterCapProvider) this;

        if (cap == ForgeCapabilities.ENERGY) {
            Optional<CustomEnergyStorage> forgeCap = capProvider.getCapability(CapabilityHandler.ENERGY, side);
            if (forgeCap.isPresent()) {
                return LazyOptional.of(() -> new ForgeEnergyWrapper(forgeCap.get())).cast();
            }
        }

        if (cap == ForgeCapabilities.ITEM_HANDLER) {
            Optional<SimpleInventory> inventory = capProvider.getCapability(CapabilityHandler.ITEM, side);
            if (inventory.isPresent()) {
                return LazyOptional.of(() -> new SidedInvWrapper(new ForgeInventoryWrapper(inventory.get()), side)).cast();
            }
        }

        if (cap == ForgeCapabilities.FLUID_HANDLER) {
            Optional<ICraterFluidHandler> fluidTank = capProvider.getCapability(CapabilityHandler.FLUID, side);
            if (fluidTank.isPresent()) {
                return LazyOptional.of(() -> (ForgeWrappedFluidTank)fluidTank.get()).cast();
            }
        }

        return LazyOptional.empty();
    }
}
