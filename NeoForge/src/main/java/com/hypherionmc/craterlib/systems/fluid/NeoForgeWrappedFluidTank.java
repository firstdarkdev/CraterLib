package com.hypherionmc.craterlib.systems.fluid;

import com.hypherionmc.craterlib.core.systems.fluid.CraterFluidTank;
import com.hypherionmc.craterlib.core.systems.fluid.FluidHolder;
import com.hypherionmc.craterlib.core.systems.fluid.ICraterFluidHandler;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.IFluidTank;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import org.jetbrains.annotations.NotNull;

import java.util.function.Predicate;

public class NeoForgeWrappedFluidTank extends CraterFluidTank implements IFluidTank, IFluidHandler {

    public NeoForgeWrappedFluidTank(int capacity) {
        super(capacity);
    }

    public NeoForgeWrappedFluidTank(int capacity, Predicate<FluidStack> predicate) {
        super(capacity, p -> predicate.test(new FluidStack(p.getFluid(), p.getAmount())));
    }

    @Override
    public @NotNull FluidStack getFluid() {
        return new FluidStack(this.getFluidInTank().getFluid(), this.getTankLevel());
    }

    @Override
    public int getFluidAmount() {
        return this.getTankLevel();
    }

    @Override
    public int getCapacity() {
        return this.getTankCapacity();
    }

    @Override
    public boolean isFluidValid(FluidStack stack) {
        return this.isValidFluid(new FluidHolder(stack.getFluid(), stack.getAmount()));
    }

    @Override
    public int getTanks() {
        return 1;
    }

    @Override
    public @NotNull FluidStack getFluidInTank(int tankInt) {
        return new FluidStack(this.getFluidInTank().getFluid(), this.getTankLevel());
    }

    @Override
    public int getTankCapacity(int tankInt) {
        return this.getTankCapacity();
    }

    @Override
    public boolean isFluidValid(int tankInt, @NotNull FluidStack stack) {
        return this.isValidFluid(new FluidHolder(stack.getFluid(), stack.getAmount()));
    }

    @Override
    public int fill(FluidStack resource, IFluidHandler.FluidAction action) {
        return this.insert(new FluidHolder(resource.getFluid(), resource.getAmount()), action.simulate() ? ICraterFluidHandler.FluidAction.SIMULATE : ICraterFluidHandler.FluidAction.EXECUTE);
    }

    @Override
    public @NotNull FluidStack drain(int maxDrain, IFluidHandler.FluidAction action) {
        FluidHolder extracted = this.extract(maxDrain, action.simulate() ? ICraterFluidHandler.FluidAction.SIMULATE : ICraterFluidHandler.FluidAction.EXECUTE);
        return new FluidStack(extracted.getFluid(), extracted.getAmount());
    }

    @Override
    public @NotNull FluidStack drain(FluidStack resource, IFluidHandler.FluidAction action) {
        FluidHolder holder = this.extract(new FluidHolder(resource.getFluid(), resource.getAmount()), action.simulate() ? ICraterFluidHandler.FluidAction.SIMULATE : ICraterFluidHandler.FluidAction.EXECUTE);
        return new FluidStack(holder.getFluid(), holder.getAmount());
    }
}
