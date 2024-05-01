package com.hypherionmc.craterlib.nojang.network;

import com.hypherionmc.craterlib.nojang.nbt.BridgedCompoundTag;
import lombok.RequiredArgsConstructor;
import net.minecraft.network.FriendlyByteBuf;

@RequiredArgsConstructor(staticName = "of")
public class BridgedFriendlyByteBuf {

    private final FriendlyByteBuf internal;

    public BridgedCompoundTag readNbt() {
        return BridgedCompoundTag.of(internal.readNbt());
    }

    public BridgedFriendlyByteBuf writeNbt(BridgedCompoundTag tag) {
        internal.writeNbt(tag.toMojang());
        return BridgedFriendlyByteBuf.of(internal);
    }

    public FriendlyByteBuf toMojang() {
        return internal;
    }

}
