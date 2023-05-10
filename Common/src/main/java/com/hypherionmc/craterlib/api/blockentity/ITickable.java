package com.hypherionmc.craterlib.api.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

/**
 * @author HypherionSA
 * Helper Interface for BlockEntities that only tick on a single side
 */
public interface ITickable {

    void tick(Level level, BlockPos pos, BlockState state, BlockEntity blockEntity);

}
