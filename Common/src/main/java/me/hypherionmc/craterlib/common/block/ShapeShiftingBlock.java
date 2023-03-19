package me.hypherionmc.craterlib.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public abstract class ShapeShiftingBlock extends Block {

    public ShapeShiftingBlock(Properties properties) {
        super(properties);
    }

    abstract protected VoxelShape getVoxelShape(BlockState state);

    protected static VoxelShape mergeShapes(VoxelShape shape, VoxelShape shape2) {
        return Shapes.or(shape, shape2);
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockGetter levelReader, BlockPos blockPos, CollisionContext collisionContext) {
        return getVoxelShape(state);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter levelReader, BlockPos blockPos, CollisionContext collisionContext) {
        return getVoxelShape(state);
    }

    @Override
    public VoxelShape getInteractionShape(BlockState state, BlockGetter levelReader, BlockPos blockPos) {
        return getVoxelShape(state);
    }
}
