package com.hypherionmc.craterlib.impl.api.network;

import com.hypherionmc.craterlib.api.game.nbt.CraterDataTag;
import com.hypherionmc.craterlib.api.game.network.CraterFriendlyByteBuf;
import com.hypherionmc.craterlib.impl.api.nbt.BridgedCompoundTag;
import lombok.RequiredArgsConstructor;
import net.minecraft.network.FriendlyByteBuf;

@RequiredArgsConstructor(staticName = "wrap")
public class BridgedFriendlyByteBuf implements CraterFriendlyByteBuf {

    private final FriendlyByteBuf internal;

    @Override
    public BridgedCompoundTag readNbt() {
        return BridgedCompoundTag.wrap(internal.readNbt());
    }

    @Override
    public BridgedFriendlyByteBuf writeNbt(CraterDataTag tag) {
        internal.writeNbt(tag.unwrap());
        return BridgedFriendlyByteBuf.wrap(internal);
    }

    @Override
    public BridgedFriendlyByteBuf writeUtf(String value) {
        internal.writeUtf(value);
        return BridgedFriendlyByteBuf.wrap(internal);
    }

    @Override
    public String readUtf() {
        return internal.readUtf();
    }

    @Override
    public FriendlyByteBuf unwrapInternal() {
        return internal;
    }

}
