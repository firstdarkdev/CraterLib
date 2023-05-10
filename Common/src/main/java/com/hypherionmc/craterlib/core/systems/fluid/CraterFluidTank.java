package com.hypherionmc.craterlib.core.systems.fluid;

import com.hypherionmc.craterlib.util.FluidUtils;
import net.minecraft.nbt.CompoundTag;

import java.util.function.Predicate;

/**
 * @author HypherionSA
 * A cross Modloader FluidTank implementation
 */
public class CraterFluidTank implements ICraterFluidHandler {

    private final int capacity;
    private final Predicate<FluidHolder> validFluid;
    private FluidHolder fluid = FluidHolder.EMPTY;

    private ChangeListener contentsChanged;

    public CraterFluidTank(int capacity) {
        this(capacity, e -> true);
    }

    public CraterFluidTank(int capacity, Predicate<FluidHolder> validFluid) {
        this.capacity = capacity;
        this.validFluid = validFluid;
    }

    public boolean isValidFluid(FluidHolder variant) {
        return validFluid.test(variant);
    }

    @Override
    public int insert(FluidHolder fluidHolder, FluidAction action) {
        if (fluidHolder.isEmpty() || !isValidFluid(fluidHolder)) {
            return 0;
        }

        if (action.simulate()) {
            if (fluid.isEmpty()) {
                return Math.min(capacity, fluidHolder.getAmount());
            }
            if (!fluid.isFluidEqual(fluidHolder)) {
                return 0;
            }
            return Math.min(capacity - fluid.getAmount(), fluidHolder.getAmount());
        }

        if (fluid.isEmpty()) {
            fluid = new FluidHolder(fluidHolder.getFluid(), Math.min(capacity, fluidHolder.getAmount()));
            return fluid.getAmount();
        }
        if (!fluid.isFluidEqual(fluidHolder)) {
            return 0;
        }

        int filled = capacity - fluid.getAmount();

        if (fluidHolder.getAmount() < filled) {
            fluid.grow(fluidHolder.getAmount());
            filled = fluidHolder.getAmount();
        } else {
            fluid.setAmount(capacity);
            filled = capacity;
        }

        if (filled > 0) {
            if (contentsChanged != null) {
                contentsChanged.onContentsChanged();
            }
        }

        return filled;
    }

    @Override
    public FluidHolder extract(FluidHolder resource, FluidAction action) {
        if (resource.isEmpty() || !resource.isFluidEqual(fluid)) {
            return FluidHolder.EMPTY;
        }
        return extract(resource.getAmount(), action);
    }

    @Override
    public FluidHolder extract(int amount, FluidAction action) {
        int drained = amount;
        if (fluid.getAmount() < drained) {
            drained = fluid.getAmount();
        }

        FluidHolder holder = new FluidHolder(fluid, drained);

        if (action.execute() && drained > 0) {
            fluid.shrink(drained);
        }

        if (contentsChanged != null) {
            contentsChanged.onContentsChanged();
        }
        return holder;
    }

    public void setContainedFluid(FluidHolder fluid) {
        this.fluid = fluid;
    }

    @Override
    public boolean isTankEmpty() {
        return fluid.isEmpty();
    }

    public int getSpace()
    {
        return Math.max(0, capacity - fluid.getAmount());
    }

    @Override
    public FluidHolder getFluidInTank() {
        return fluid;
    }

    @Override
    public int getTankLevel() {
        return fluid.getAmount();
    }

    @Override
    public int getTankCapacity() {
        return capacity;
    }

    @Override
    public CompoundTag writeToNBT(CompoundTag tag) {
        FluidUtils.putFluid(tag, "fluid", fluid.getFluid());
        tag.putInt("tankLevel", fluid.getAmount());
        return tag;
    }

    @Override
    public void readFromNBT(CompoundTag tag) {
        fluid = new FluidHolder(FluidUtils.getFluidCompatible(tag), tag.getInt("tankLevel"));
    }

    public void setChangeListener(ChangeListener contentsChanged) {
        this.contentsChanged = contentsChanged;
    }

    public interface ChangeListener {
        void onContentsChanged();
    }
}
