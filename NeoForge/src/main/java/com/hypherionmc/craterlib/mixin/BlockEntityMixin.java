package com.hypherionmc.craterlib.mixin;

import com.hypherionmc.craterlib.common.blockentity.CraterBlockEntity;
import org.spongepowered.asm.mixin.Mixin;

/**
 * @author HypherionSA
 */
// TODO Fix This
@Mixin(CraterBlockEntity.class)
public class BlockEntityMixin /*implements ICapabilityProvider*/ {

    /*@Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        ICraterCapProvider capProvider = (ICraterCapProvider) this;

        if (cap == Capabilities.ENERGY) {
            Optional<CustomEnergyStorage> forgeCap = capProvider.getCapability(CraterCapabilityHandler.ENERGY, side);
            if (forgeCap.isPresent()) {
                return LazyOptional.of(() -> new NeoForgeEnergyWrapper(forgeCap.get())).cast();
            }
        }

        if (cap == Capabilities.ITEM_HANDLER) {
            Optional<SimpleInventory> inventory = capProvider.getCapability(CraterCapabilityHandler.ITEM, side);
            if (inventory.isPresent()) {
                return LazyOptional.of(() -> new SidedInvWrapper(new NeoForgeInventoryWrapper(inventory.get()), side)).cast();
            }
        }

        if (cap == Capabilities.FLUID_HANDLER) {
            Optional<ICraterFluidHandler> fluidTank = capProvider.getCapability(CraterCapabilityHandler.FLUID, side);
            if (fluidTank.isPresent()) {
                return LazyOptional.of(() -> (NeoForgeWrappedFluidTank)fluidTank.get()).cast();
            }
        }

        return LazyOptional.empty();
    }*/
}
