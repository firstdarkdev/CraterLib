package me.hypherionmc.craterlib.api.blockentities;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

/**
 * @author HypherionSA
 * Helper Interface for BlockEntities that tick both Client and Server Side
 */
public interface ISidedTickable {

    /**
     * Server Tick Event
     */
    public void serverTick(Level level, BlockPos pos, BlockState state, BlockEntity blockEntity);

    /**
     * Client Tick Event
     */
    public void clientTick(Level level, BlockPos pos, BlockState state, BlockEntity blockEntity);

}
