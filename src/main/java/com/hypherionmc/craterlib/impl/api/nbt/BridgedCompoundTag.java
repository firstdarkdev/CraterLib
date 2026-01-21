package com.hypherionmc.craterlib.impl.api.nbt;

import com.hypherionmc.craterlib.api.game.nbt.CraterDataTag;
import lombok.RequiredArgsConstructor;
import net.minecraft.nbt.CompoundTag;

import java.util.Set;

@RequiredArgsConstructor(staticName = "wrap")
public class BridgedCompoundTag implements CraterDataTag {

    private final CompoundTag internal;

    public static BridgedCompoundTag empty() {
        return new BridgedCompoundTag(new CompoundTag());
    }

    @Override
    public BridgedCompoundTag getCompound(String key) {
        return BridgedCompoundTag.wrap(internal.getCompound(key).orElse(new CompoundTag()));
    }

    @Override
    public Set<String> getAllKeys() {
        return internal.keySet();
    }

    @Override
    public String getString(String key) {
        return internal.getStringOr(key, "");
    }

    @Override
    public boolean getBoolean(String key) {
        return internal.getBooleanOr(key, false);
    }

    @Override
    public void putString(String key, String value) {
        internal.putString(key, value);
    }

    @Override
    public void put(String key, CraterDataTag value) {
        internal.put(key, value.unwrap());
    }

    @Override
    public void putBoolean(String key, boolean value) {
        internal.putBoolean(key, value);
    }

    @Override
    public CompoundTag unwrapInternal() {
        return internal;
    }

}
