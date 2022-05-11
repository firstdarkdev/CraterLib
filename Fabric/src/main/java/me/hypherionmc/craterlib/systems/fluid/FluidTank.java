package me.hypherionmc.hyperlighting.api.fluid;

import me.hypherionmc.hyperlighting.utils.ModUtils;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.Storage;
import net.fabricmc.fabric.api.transfer.v1.storage.StoragePreconditions;
import net.fabricmc.fabric.api.transfer.v1.storage.StorageView;
import net.fabricmc.fabric.api.transfer.v1.storage.base.SingleViewIterator;
import net.fabricmc.fabric.api.transfer.v1.transaction.TransactionContext;
import net.minecraft.nbt.NbtCompound;

import java.util.Iterator;
import java.util.function.Predicate;

public class FluidStorageTank implements Storage<FluidVariant>, StorageView<FluidVariant> {

    private final long capacity;
    private final Predicate<FluidVariant> validFluid;
    private long level = 0;
    private FluidVariant fluid = FluidVariant.blank();

    public FluidStorageTank(long capacity) {
        this(capacity, e -> true);
    }

    public FluidStorageTank(long capacity, Predicate<FluidVariant> validFluid) {
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

    @Override
    public Iterator<StorageView<FluidVariant>> iterator(TransactionContext transaction) {
        return SingleViewIterator.create(this, transaction);
    }

    public NbtCompound writeNbt(NbtCompound compound) {
        ModUtils.putFluid(compound, "fluid", getResource());
        compound.putLong("amt", level);
        return compound;
    }

    public void readNbt(NbtCompound nbtCompound) {
        fluid = ModUtils.getFluidCompatible(nbtCompound, "fluid");
        level = nbtCompound.getLong("amt");
    }
}
