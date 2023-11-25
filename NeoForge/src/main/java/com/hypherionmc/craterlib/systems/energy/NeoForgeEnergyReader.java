package com.hypherionmc.craterlib.systems.energy;

import com.hypherionmc.craterlib.core.systems.energy.ICraterEnergyStorage;
import net.neoforged.neoforge.energy.IEnergyStorage;

public record NeoForgeEnergyReader(IEnergyStorage forgeStorage) implements ICraterEnergyStorage {

    @Override
    public int receiveEnergy(int toReceive, boolean test) {
        return forgeStorage.receiveEnergy(toReceive, test);
    }

    @Override
    public int extractEnergy(int toExtract, boolean test) {
        return forgeStorage.extractEnergy(toExtract, test);
    }

    @Override
    public int getPowerLevel() {
        return forgeStorage.getEnergyStored();
    }

    @Override
    public int getMaxInput() {
        return 0;
    }

    @Override
    public int getMaxOutput() {
        return 0;
    }

    @Override
    public int getPowerCapacity() {
        return forgeStorage().getMaxEnergyStored();
    }
}
