package com.hypherionmc.craterlib.core.abstraction.server;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;

public class AbstractFriendlyByteBuff {

    public static FriendlyByteBuf write(FriendlyByteBuf buf, CompoundTag tag) {
        buf.writeNbt(tag);
        return buf;
    }

}
