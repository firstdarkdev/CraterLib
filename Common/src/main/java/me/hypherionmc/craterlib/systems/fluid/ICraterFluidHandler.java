package me.hypherionmc.craterlib.systems.fluid;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.material.Fluid;

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

    public int insert(FluidHolder fluidHolder, CraterFluidTank.FluidAction action);
    public FluidHolder extract(FluidHolder fluidHolder, CraterFluidTank.FluidAction action);
    public FluidHolder extract(int amount, CraterFluidTank.FluidAction action);
    public boolean isTankEmpty();
    public FluidHolder getFluidInTank();
    public int getTankLevel();
    public int getTankCapacity();

    public default CompoundTag writeToNBT(CompoundTag tag) { return tag; }

    public default void readFromNBT(CompoundTag tag) {};
}
