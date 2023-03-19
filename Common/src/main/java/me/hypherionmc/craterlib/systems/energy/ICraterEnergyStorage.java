package me.hypherionmc.craterlib.systems.energy;

import net.minecraft.nbt.CompoundTag;

public interface ICraterEnergyStorage {

    public default CompoundTag writeNBT(CompoundTag tag) { return tag; }
    public default void readNBT(CompoundTag tag) {}

    public int receiveEnergy(int toReceive, boolean test);
    public int extractEnergy(int toExtract, boolean test);

    public int getPowerLevel();

    public int getMaxInput();

    public int getMaxOutput();

    public int getPowerCapacity();

}
