package me.hypherionmc.craterlib.api.blockentities;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public interface ISidedTickable {

    public void serverTick(Level level, BlockPos pos, BlockState state, BlockEntity blockEntity);
    public void clientTick(Level level, BlockPos pos, BlockState state, BlockEntity blockEntity);

}
