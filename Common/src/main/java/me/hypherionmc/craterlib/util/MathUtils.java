package me.hypherionmc.craterlib.util;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class MathUtils {

    public static VoxelShape rotateShape(Direction from, Direction to, VoxelShape shape) {
        VoxelShape[] buffer = new VoxelShape[]{shape, Shapes.empty()};

        int times = (to.ordinal() - from.ordinal() + 4) % 4;
        for (int i = 0; i < times; i++) {
            buffer[0].forAllBoxes((minX, minY, minZ, maxX, maxY, maxZ) -> buffer[1] = Shapes.or(buffer[1], Shapes.box(1 - maxZ, minY, minX, 1 - minZ, maxY, maxX)));
            buffer[0] = buffer[1];
            buffer[1] = Shapes.empty();
        }
        return buffer[0];
    }

    public static void writeBlockPosToNBT(BlockPos pos, CompoundTag tag) {
        tag.putInt("block_x", pos.getX());
        tag.putInt("block_y", pos.getY());
        tag.putInt("block_z", pos.getZ());
    }

    public static BlockPos readBlockPosFromNBT(CompoundTag tag) {
        int x, y, z;
        x = tag.getInt("block_x");
        y = tag.getInt("block_y");
        z = tag.getInt("block_z");
        return new BlockPos(x, y, z);
    }

}
