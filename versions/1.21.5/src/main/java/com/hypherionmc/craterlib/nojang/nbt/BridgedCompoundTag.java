package com.hypherionmc.craterlib.nojang.nbt;

import lombok.RequiredArgsConstructor;
import net.minecraft.nbt.CompoundTag;

import java.util.Set;

@RequiredArgsConstructor(staticName = "of")
public class BridgedCompoundTag {

    private final CompoundTag internal;

    public static BridgedCompoundTag empty() {
        return new BridgedCompoundTag(new CompoundTag());
    }

    public BridgedCompoundTag getCompound(String key) {
        return BridgedCompoundTag.of(internal.getCompound(key).orElse(new CompoundTag()));
    }

    public Set<String> getAllKeys() {
        return internal.keySet();
    }

    public String getString(String key) {
        return internal.getStringOr(key, "");
    }

    public boolean getBoolean(String key) {
        return internal.getBooleanOr(key, false);
    }

    public void putString(String key, String value) {
        internal.putString(key, value);
    }

    public void put(String key, BridgedCompoundTag value) {
        internal.put(key, value.toMojang());
    }

    public void putBoolean(String key, boolean value) {
        internal.putBoolean(key, value);
    }

    public CompoundTag toMojang() {
        return internal;
    }

}
