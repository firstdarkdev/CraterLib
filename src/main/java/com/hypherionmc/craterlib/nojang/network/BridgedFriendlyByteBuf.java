package com.hypherionmc.craterlib.nojang.network;

import com.hypherionmc.craterlib.nojang.nbt.BridgedCompoundTag;
import lombok.RequiredArgsConstructor;
import io.netty.buffer.ByteBuf;

// TODO: Implement if Possible
@RequiredArgsConstructor(staticName = "of")
public class BridgedFriendlyByteBuf {

    private final ByteBuf internal;

    public BridgedCompoundTag readNbt() {
        return BridgedCompoundTag.of();
    }

    public BridgedFriendlyByteBuf writeNbt(BridgedCompoundTag tag) {
        //internal.writeNbt(tag.toMojang());
        return BridgedFriendlyByteBuf.of(internal);
    }

    public BridgedFriendlyByteBuf writeUtf(String value) {
        //internal.writeUtf(value);
        return BridgedFriendlyByteBuf.of(internal);
    }

    public String readUtf() {
        return "Not Implemented";
    }

    public ByteBuf toMojang() {
        return internal;
    }

}
