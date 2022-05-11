package me.hypherionmc.craterlib.util;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

import java.util.function.ToIntFunction;

public class BlockStateUtils {

    public static ToIntFunction<BlockState> createLightLevelFromLitBlockState(int litLevel) {
        return state -> state.getValue(BlockStateProperties.LIT) ? litLevel : 0;
    }

    public static ToIntFunction<BlockState> createLightLevelFromPoweredBlockState(int litLevel) {
        return state -> state.getValue(BlockStateProperties.POWERED) ? litLevel : 0;
    }

}
