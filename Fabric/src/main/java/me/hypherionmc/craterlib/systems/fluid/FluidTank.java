package me.hypherionmc.craterlib.systems.fluid;

import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.Storage;
import net.fabricmc.fabric.api.transfer.v1.storage.StoragePreconditions;
import net.fabricmc.fabric.api.transfer.v1.storage.StorageView;
import net.fabricmc.fabric.api.transfer.v1.transaction.TransactionContext;
import net.minecraft.nbt.CompoundTag;

import java.util.Iterator;
import java.util.function.Predicate;

public class FluidTank implements Storage<FluidVariant>, StorageView<FluidVariant> {

    private final long capacity;
    private final Predicate<FluidVariant> validFluid;
    private long level = 0;
    private FluidVariant fluid = FluidVariant.blank();

    public FluidTank(long capacity) {
        this(capacity, e -> true);
    }

    public FluidTank(long capacity, Predicate<FluidVariant> validFluid) {
        this.capacity = capacity;
        this.validFluid = validFluid;
    }

    public boolean isFluidValid(FluidVariant variant) {
        return validFluid.test(variant);
    }

    @Override
    public long insert(FluidVariant resource, long maxAmount, TransactionContext transaction) {
        StoragePreconditions.notBlankNotNegative(resource, maxAmount);
        if (this.fluid.isBlank() || this.fluid.equals(resource)) {
            long inserted = Math.min(maxAmount, capacity - level);
            if (inserted > 0) {
                level += inserted;
                this.fluid = resource;
            }
            return inserted;
        }
        return 0;
    }

    @Override
    public long extract(FluidVariant resource, long maxAmount, TransactionContext transaction) {
        StoragePreconditions.notBlankNotNegative(resource, maxAmount);
        if (resource.equals(fluid)) {
            long extracted = Math.min(maxAmount, level);
            if (extracted > 0) {
                level -= extracted;
                if (level == 0) {
                    this.fluid = FluidVariant.blank();
                }
            }
            return extracted;
        }
        return 0;
    }

    @Override
    public Iterator<StorageView<FluidVariant>> iterator() {
        // TODO: FIX THIS!
        return null;
    }

    @Override
    public boolean isResourceBlank() {
        return fluid.isBlank();
    }

    @Override
    public FluidVariant getResource() {
        return fluid;
    }

    @Override
    public long getAmount() {
        return level;
    }

    @Override
    public long getCapacity() {
        return capacity;
    }

    public CompoundTag writeNbt(CompoundTag compound) {
        FluidUtils.putFluid(compound, "fluid", getResource());
        compound.putLong("amt", level);
        return compound;
    }

    public void readNbt(CompoundTag nbtCompound) {
        fluid = FluidUtils.getFluidCompatible(nbtCompound, "fluid");
        level = nbtCompound.getLong("amt");
    }
}
