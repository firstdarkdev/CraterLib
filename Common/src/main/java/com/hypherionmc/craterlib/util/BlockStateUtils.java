package com.hypherionmc.craterlib.util;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

import java.util.function.ToIntFunction;

/**
 * @author HypherionSA
 * Helper class to create light levels from BlockState values
 */
public class BlockStateUtils {

    public static ToIntFunction<BlockState> lightLevelFromLitBlockState(int litLevel) {
        return state -> state.getValue(BlockStateProperties.LIT) ? litLevel : 0;
    }

    public static ToIntFunction<BlockState> lightLevelFromPoweredBlockState(int litLevel) {
        return state -> state.getValue(BlockStateProperties.POWERED) ? litLevel : 0;
    }

}
