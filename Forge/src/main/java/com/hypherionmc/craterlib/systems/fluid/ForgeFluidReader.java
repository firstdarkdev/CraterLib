package com.hypherionmc.craterlib.systems.fluid;

import com.hypherionmc.craterlib.core.systems.fluid.FluidHolder;
import com.hypherionmc.craterlib.core.systems.fluid.ICraterFluidHandler;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;

public record ForgeFluidReader(IFluidHandler fluidHandler) implements ICraterFluidHandler {

    @Override
    public int insert(FluidHolder fluidHolder, FluidAction action) {
        return fluidHandler.fill(new FluidStack(fluidHolder.getFluid(), fluidHolder.getAmount()), action.simulate() ? IFluidHandler.FluidAction.SIMULATE : IFluidHandler.FluidAction.EXECUTE);
    }

    @Override
    public FluidHolder extract(FluidHolder fluidHolder, FluidAction action) {
        FluidStack extracted = fluidHandler.drain(new FluidStack(fluidHolder.getFluid(), fluidHolder.getAmount()), action.simulate() ? IFluidHandler.FluidAction.SIMULATE : IFluidHandler.FluidAction.EXECUTE);
        return new FluidHolder(extracted.getFluid(), extracted.getAmount());
    }

    @Override
    public FluidHolder extract(int amount, FluidAction action) {
        FluidStack extracted = fluidHandler.drain(amount, action.simulate() ? IFluidHandler.FluidAction.SIMULATE : IFluidHandler.FluidAction.EXECUTE);
        return new FluidHolder(extracted.getFluid(), extracted.getAmount());
    }

    @Override
    public boolean isTankEmpty() {
        return false;
    }

    @Override
    public FluidHolder getFluidInTank() {
        return FluidHolder.EMPTY;
    }

    @Override
    public int getTankLevel() {
        return 0;
    }

    @Override
    public int getTankCapacity() {
        return 0;
    }
}
