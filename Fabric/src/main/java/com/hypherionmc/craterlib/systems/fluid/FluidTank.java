package com.hypherionmc.craterlib.systems.fluid;

import com.hypherionmc.craterlib.core.systems.fluid.CraterFluidTank;
import com.hypherionmc.craterlib.core.systems.fluid.FluidHolder;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.Storage;
import net.fabricmc.fabric.api.transfer.v1.storage.StoragePreconditions;
import net.fabricmc.fabric.api.transfer.v1.storage.StorageView;
import net.fabricmc.fabric.api.transfer.v1.transaction.TransactionContext;

import java.util.Iterator;
import java.util.function.Predicate;

public class FluidTank extends CraterFluidTank implements Storage<FluidVariant>, StorageView<FluidVariant> {

    public FluidTank(long capacity) {
        this(capacity, e -> true);
    }

    public FluidTank(long capacity, Predicate<FluidVariant> validFluid) {
        super((int) capacity, (p) -> validFluid.test(FluidVariant.of(p.getFluid())));
    }

    public boolean isFluidValid(FluidVariant variant) {
        return isValidFluid(new FluidHolder(variant.getFluid(), 0));
    }

    @Override
    public long insert(FluidVariant resource, long maxAmount, TransactionContext transaction) {
        StoragePreconditions.notBlankNotNegative(resource, maxAmount);
        return insert(new FluidHolder(resource.getFluid(), (int) maxAmount), FluidAction.EXECUTE);
    }

    @Override
    public long extract(FluidVariant resource, long maxAmount, TransactionContext transaction) {
        StoragePreconditions.notBlankNotNegative(resource, maxAmount);
        FluidHolder extracted = extract(new FluidHolder(resource.getFluid(), (int) maxAmount), FluidAction.EXECUTE);
        return extracted.getAmount();
    }

    @Override
    public Iterator<StorageView<FluidVariant>> iterator() {
        // TODO: FIX THIS!
        return null;
    }

    @Override
    public boolean isResourceBlank() {
        return isTankEmpty();
    }

    @Override
    public FluidVariant getResource() {
        return FluidVariant.of(getFluidInTank().getFluid());
    }

    @Override
    public long getAmount() {
        return getTankLevel();
    }

    @Override
    public long getCapacity() {
        return getTankCapacity();
    }
}
