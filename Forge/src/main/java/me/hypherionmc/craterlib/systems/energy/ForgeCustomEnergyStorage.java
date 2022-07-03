package me.hypherionmc.craterlib.systems.energy;

import net.minecraftforge.energy.IEnergyStorage;

/**
 * @author HypherionSA
 * @date 03/07/2022
 * Forge Energy Support on top of the custom system
 */
public class ForgeCustomEnergyStorage extends CustomEnergyStorage implements IEnergyStorage {


    public ForgeCustomEnergyStorage(int capacity) {
        super(capacity);
    }

    public ForgeCustomEnergyStorage(int powerCapacity, int maxTransfer) {
        super(powerCapacity, maxTransfer);
    }

    public ForgeCustomEnergyStorage(int powerCapacity, int maxInput, int maxOutput) {
        super(powerCapacity, maxInput, maxOutput);
    }

    public ForgeCustomEnergyStorage(int capacity, int maxInput, int maxOutput, int initialPower) {
        super(capacity, maxInput, maxOutput, initialPower);
    }

    @Override
    public int getEnergyStored() {
        return this.getPowerLevel();
    }

    @Override
    public int getMaxEnergyStored() {
        return this.getPowerCapacity();
    }

    @Override
    public boolean canExtract() {
        return this.maxOutput > 0;
    }

    @Override
    public boolean canReceive() {
        return this.maxInput > 0;
    }
}
