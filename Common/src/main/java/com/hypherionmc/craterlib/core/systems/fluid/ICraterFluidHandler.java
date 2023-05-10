package com.hypherionmc.craterlib.core.systems.fluid;

import net.minecraft.nbt.CompoundTag;

/**
 * @author HypherionSA
 * Cross modloader fluid handler implementation
 */
public interface ICraterFluidHandler {

    enum FluidAction {
        EXECUTE,
        SIMULATE;

        public boolean simulate() {
            return this == SIMULATE;
        }

        public boolean execute() {
            return this == EXECUTE;
        }
    }

    int insert(FluidHolder fluidHolder, CraterFluidTank.FluidAction action);
    FluidHolder extract(FluidHolder fluidHolder, CraterFluidTank.FluidAction action);
    FluidHolder extract(int amount, CraterFluidTank.FluidAction action);
    boolean isTankEmpty();
    FluidHolder getFluidInTank();
    int getTankLevel();
    int getTankCapacity();

    default CompoundTag writeToNBT(CompoundTag tag) { return tag; }

    default void readFromNBT(CompoundTag tag) {};
}
