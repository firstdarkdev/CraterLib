package me.hypherionmc.craterlib.common.blockentity;

import me.hypherionmc.craterlib.api.blockentities.caps.ForgeCapability;
import me.hypherionmc.craterlib.api.blockentities.caps.IForgeCapProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Optional;

/**
 * @author HypherionSA
 * @date 24/09/2022
 * A Wrapped Block Entity to incorporate CraterLib's universal capability provider
 */
public class CraterBlockEntity extends BlockEntity implements IForgeCapProvider {

    public CraterBlockEntity(BlockEntityType<?> blockEntityType, BlockPos pos, BlockState state) {
        super(blockEntityType, pos, state);
    }

    public void sendUpdates() {
        level.blockEntityChanged(this.getBlockPos());
        level.sendBlockUpdated(this.getBlockPos(), level.getBlockState(this.getBlockPos()), level.getBlockState(this.getBlockPos()), 3);
        setChanged();
    }

    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag() {
        CompoundTag compoundTag = new CompoundTag();
        saveAdditional(compoundTag);
        return compoundTag;
    }

    @Override
    public <T> Optional<T> getForgeCapability(ForgeCapability capability, Direction side) {
        return Optional.empty();
    }
}
