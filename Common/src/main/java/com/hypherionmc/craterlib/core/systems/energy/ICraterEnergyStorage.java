package com.hypherionmc.craterlib.core.systems.energy;

import net.minecraft.nbt.CompoundTag;

/**
 * @author HypherionSA
 */
public interface ICraterEnergyStorage {

    default CompoundTag writeNBT(CompoundTag tag) { return tag; }
    default void readNBT(CompoundTag tag) {}

    int receiveEnergy(int toReceive, boolean test);
    int extractEnergy(int toExtract, boolean test);

    int getPowerLevel();

    int getMaxInput();

    int getMaxOutput();

    int getPowerCapacity();

}
