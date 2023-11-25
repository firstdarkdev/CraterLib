package com.hypherionmc.craterlib.systems.inventory;

import com.google.common.base.Suppliers;
import com.hypherionmc.craterlib.core.systems.inventory.SimpleInventory;
import net.minecraft.core.Direction;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;
import java.util.stream.IntStream;

/**
 * @author HypherionSA
 */
public class NeoForgeInventoryWrapper implements WorldlyContainer {

    private final SimpleInventory inventory;
    private final Supplier<int[]> slots = Suppliers.memoize(() -> IntStream.range(0, getContainerSize()).toArray());

    public NeoForgeInventoryWrapper(SimpleInventory inventory) {
        this.inventory = inventory;
    }

    @Override
    public int[] getSlotsForFace(Direction side) {
        return slots.get();
    }

    @Override
    public boolean canPlaceItemThroughFace(int slot, ItemStack stack, @Nullable Direction side) {
        if (canPlaceItem(slot, stack)) {
            ItemStack existing = getItem(slot);
            return existing.getCount() < getMaxStackSize();
        }

        return false;
    }

    @Override
    public boolean canTakeItemThroughFace(int slot, ItemStack stack, Direction side) {
        return true;
    }

    @Override
    public int getContainerSize() {
        return inventory.getItemHandler().getContainerSize();
    }

    @Override
    public boolean isEmpty() {
        return inventory.getItemHandler().isEmpty();
    }

    @Override
    public ItemStack getItem(int slot) {
        return inventory.getItemHandler().getItem(slot);
    }

    @Override
    public ItemStack removeItem(int p_18942_, int p_18943_) {
        return inventory.getItemHandler().removeItem(p_18942_, p_18943_);
    }

    @Override
    public ItemStack removeItemNoUpdate(int slot) {
        return inventory.getItemHandler().removeItemNoUpdate(slot);
    }

    @Override
    public void setItem(int slot, ItemStack stack) {
        inventory.getItemHandler().setItem(slot, stack);
    }

    @Override
    public void setChanged() {
        inventory.getItemHandler().setChanged();
    }

    @Override
    public boolean stillValid(Player player) {
        return inventory.getItemHandler().stillValid(player);
    }

    @Override
    public void clearContent() {
        inventory.getItemHandler().clearContent();
    }
}
