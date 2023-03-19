package me.hypherionmc.craterlib.systems.fluid;

import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import org.jetbrains.annotations.NotNull;

public record ForgeFluidTankInteractor(ICraterFluidHandler fluidHandler) implements IFluidHandler {

    @Override
    public int getTanks() {
        return 1;
    }

    @Override
    public @NotNull FluidStack getFluidInTank(int tank) {
        return new FluidStack(fluidHandler.getFluidInTank().getFluid(), fluidHandler.getFluidInTank().getAmount());
    }

    @Override
    public int getTankCapacity(int tank) {
        return fluidHandler.getTankCapacity();
    }

    @Override
    public boolean isFluidValid(int tank, @NotNull FluidStack stack) {
        return true;
    }

    @Override
    public int fill(FluidStack resource, FluidAction action) {
        return fluidHandler.insert(new FluidHolder(resource.getFluid(), resource.getAmount()), action.simulate() ? ICraterFluidHandler.FluidAction.SIMULATE : ICraterFluidHandler.FluidAction.EXECUTE);
    }

    @Override
    public @NotNull FluidStack drain(FluidStack resource, FluidAction action) {
        FluidHolder drained = fluidHandler.extract(new FluidHolder(resource.getFluid(), resource.getAmount()), action.simulate() ? ICraterFluidHandler.FluidAction.SIMULATE : ICraterFluidHandler.FluidAction.EXECUTE);
        return new FluidStack(drained.getFluid(), drained.getAmount());
    }

    @Override
    public @NotNull FluidStack drain(int maxDrain, FluidAction action) {
        FluidHolder drained = fluidHandler.extract(maxDrain, action.simulate() ? ICraterFluidHandler.FluidAction.SIMULATE : ICraterFluidHandler.FluidAction.EXECUTE);
        return new FluidStack(drained.getFluid(), drained.getAmount());
    }
}
