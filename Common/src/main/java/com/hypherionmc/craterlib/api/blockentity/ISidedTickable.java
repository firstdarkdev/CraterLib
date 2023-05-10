package com.hypherionmc.craterlib.api.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

/**
 * @author HypherionSA
 * Helper Interface for BlockEntities that tick both Client and Server Side
 */
public interface ISidedTickable {

    void serverTick(Level level, BlockPos pos, BlockState state, BlockEntity blockEntity);
    void clientTick(Level level, BlockPos pos, BlockState state, BlockEntity blockEntity);

}
