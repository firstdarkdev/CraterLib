package com.hypherionmc.craterlib.nojang.nbt;

import lombok.RequiredArgsConstructor;

import java.util.HashSet;
import java.util.Set;

// TODO: Implement if Possible
@RequiredArgsConstructor(staticName = "of")
public class BridgedCompoundTag {

    //private final CompoundTag internal;

    public static BridgedCompoundTag empty() {
        return new BridgedCompoundTag();
    }

    public BridgedCompoundTag getCompound(String key) {
        return BridgedCompoundTag.of();
    }

    public Set<String> getAllKeys() {
        return new HashSet<>();
    }

    public String getString(String key) {
        return "Not Implemented";
    }

    public boolean getBoolean(String key) {
        return false;
    }

    public void putString(String key, String value) {
        //internal.putString(key, value);
    }

    public void put(String key, BridgedCompoundTag value) {
        //internal.put(key, value.toMojang());
    }

    public void putBoolean(String key, boolean value) {
        //internal.putBoolean(key, value);
    }

//    public CompoundTag toMojang() {
//        return internal;
//    }

}
