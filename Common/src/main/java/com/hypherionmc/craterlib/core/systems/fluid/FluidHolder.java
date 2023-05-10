package com.hypherionmc.craterlib.core.systems.fluid;

import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import org.jetbrains.annotations.NotNull;

/**
 * @author HypherionSA
 * Cross Modloader "FluidStack" implementation
 */
public class FluidHolder {

    private final Fluid fluid;
    private int amount;

    public FluidHolder(FluidHolder holder) {
        this(holder.getFluid(), holder.getAmount());
    }

    public FluidHolder(Fluid fluid, int amount) {
        this.fluid = fluid;
        this.amount = amount;
    }

    public FluidHolder(FluidHolder fluid, int amount) {
        this.fluid = fluid.getFluid();
        this.amount = amount;
    }

    public static FluidHolder EMPTY = new FluidHolder(Fluids.EMPTY, 0);

    public boolean isEmpty() {
        return amount == 0 || fluid.isSame(Fluids.EMPTY);
    }

    public Fluid getFluid() {
        return fluid;
    }

    public int getAmount() {
        return amount;
    }

    public boolean isFluidEqual(@NotNull FluidHolder other)
    {
        return this.getFluid() == other.getFluid();
    }

    public void grow(int amount) {
        this.amount += amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void shrink(int amount) {
        this.amount -= amount;
    }

    public FluidHolder copy() {
        return new FluidHolder(getFluid(), getAmount());
    }

}
