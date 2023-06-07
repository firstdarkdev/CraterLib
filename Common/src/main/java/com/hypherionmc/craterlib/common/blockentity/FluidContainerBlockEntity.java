package com.hypherionmc.craterlib.common.blockentity;

import com.hypherionmc.craterlib.api.blockentity.caps.CraterCapabilityHandler;
import com.hypherionmc.craterlib.core.platform.CraterFluidHelper;
import com.hypherionmc.craterlib.core.systems.fluid.CraterFluidTank;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

/**
 * @author HypherionSA
 * A wrapped BlockEntity containing a fluid tank
 */
public class FluidContainerBlockEntity extends CraterBlockEntity {

    public final CraterFluidTank fluidTank;

    public FluidContainerBlockEntity(BlockEntityType<?> blockEntityType, BlockPos pos, BlockState state, int capacity) {
        super(blockEntityType, pos, state);
        fluidTank = CraterFluidHelper.INSTANCE.createFluidTank(capacity);
    }

    public FluidContainerBlockEntity(BlockEntityType<?> blockEntityType, BlockPos pos, BlockState state, int capacity, Fluid... validFluids) {
        super(blockEntityType, pos, state);
        fluidTank = CraterFluidHelper.INSTANCE.createFluidTank(capacity, validFluids);
        fluidTank.setChangeListener(this::sendUpdates);
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        fluidTank.writeToNBT(tag);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        fluidTank.readFromNBT(tag);
    }

    @Override
    public <T> Optional<T> getCapability(CraterCapabilityHandler handler, @Nullable Direction side) {
        if (handler == CraterCapabilityHandler.FLUID) {
            return (Optional<T>) Optional.of(fluidTank);
        }
        return super.getCapability(handler, side);
    }

    public CraterFluidTank getFluidTank() {
        return fluidTank;
    }
}
