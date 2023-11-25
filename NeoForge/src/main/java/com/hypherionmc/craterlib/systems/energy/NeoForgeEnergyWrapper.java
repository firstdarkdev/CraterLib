package com.hypherionmc.craterlib.systems.energy;

import com.hypherionmc.craterlib.core.systems.energy.CustomEnergyStorage;
import net.neoforged.neoforge.energy.IEnergyStorage;

/**
 * @author HypherionSA
 * Forge Energy Support on top of the custom system
 */
public record NeoForgeEnergyWrapper(CustomEnergyStorage storage) implements IEnergyStorage {

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
