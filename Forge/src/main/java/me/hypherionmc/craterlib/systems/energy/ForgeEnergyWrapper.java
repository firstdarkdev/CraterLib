package me.hypherionmc.craterlib.systems.energy;

import net.minecraftforge.energy.IEnergyStorage;

/**
 * @author HypherionSA
 * @date 03/07/2022
 * Forge Energy Support on top of the custom system
 */
public record ForgeEnergyWrapper(CustomEnergyStorage storage) implements IEnergyStorage {

    @Override
    public int receiveEnergy(int maxReceive, boolean simulate) {
        return storage.receiveEnergy(maxReceive, simulate);
    }

    @Override
    public int extractEnergy(int maxExtract, boolean simulate) {
        return storage.extractEnergy(maxExtract, simulate);
    }

    @Override
    public int getEnergyStored() {
        return storage.getPowerLevel();
    }

    @Override
    public int getMaxEnergyStored() {
        return storage.getPowerCapacity();
    }

    @Override
    public boolean canExtract() {
        return storage.getMaxOutput() > 0;
    }

    @Override
    public boolean canReceive() {
        return storage.getMaxInput() > 0;
    }
}
