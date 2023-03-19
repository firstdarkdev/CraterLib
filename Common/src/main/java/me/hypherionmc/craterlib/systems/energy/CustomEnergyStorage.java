package me.hypherionmc.craterlib.systems.energy;

import net.minecraft.nbt.CompoundTag;

/***
 * Loosely based on the Forge Energy System
 */
public class CustomEnergyStorage implements ICraterEnergyStorage {

    protected int powerLevel;
    protected int powerCapacity;
    protected int maxInput;
    protected int maxOutput;

    public CustomEnergyStorage(int capacity) {
        this(capacity, capacity, capacity, 0);
    }

    public CustomEnergyStorage(int powerCapacity, int maxTransfer) {
        this(powerCapacity, maxTransfer, maxTransfer, 0);
    }

    public CustomEnergyStorage(int powerCapacity, int maxInput, int maxOutput) {
        this(powerCapacity, maxInput, maxOutput, 0);
    }

    public CustomEnergyStorage(int capacity, int maxInput, int maxOutput, int initialPower) {
        this.powerLevel = initialPower;
        this.maxInput = maxInput;
        this.maxOutput = maxOutput;
        this.powerCapacity = capacity;
    }

    @Override
    public CompoundTag writeNBT(CompoundTag compoundTag) {
        compoundTag.putInt("powerLevel", this.powerLevel);
        return compoundTag;
    }

    @Override
    public void readNBT(CompoundTag compoundTag) {
        if (compoundTag.contains("powerLevel")) {
            this.powerLevel = compoundTag.getInt("powerLevel");
        }
    }

    public int receiveEnergyInternal(int toReceive, boolean test) {
        int energyReceived = Math.min(this.powerCapacity - this.powerLevel, Math.min(this.maxInput, toReceive));
        if (!test)
            this.powerLevel += energyReceived;
        return energyReceived;
    }

    @Override
    public int receiveEnergy(int toReceive, boolean test) {
        if (this.maxInput < 1) {
            return 0;
        }
        return this.receiveEnergyInternal(toReceive, test);
    }

    public int extractEnergyInternal(int toExtract, boolean test) {
        int energyExtracted = Math.min(this.powerLevel, Math.min(this.powerCapacity, toExtract));
        if (!test)
            this.powerLevel -= energyExtracted;
        return energyExtracted;
    }

    @Override
    public int extractEnergy(int toExtract, boolean test) {
        if (this.maxOutput < 1) {
            return 0;
        }
        int energyExtracted = Math.min(this.powerLevel, Math.min(this.maxOutput, toExtract));
        if (!test)
            this.powerLevel -= energyExtracted;
        return energyExtracted;
    }

    @Override
    public int getPowerLevel() {
        return powerLevel;
    }

    @Override
    public int getMaxInput() {
        return maxInput;
    }

    @Override
    public int getMaxOutput() {
        return maxOutput;
    }

    @Override
    public int getPowerCapacity() {
        return powerCapacity;
    }
}
