package me.hypherionmc.craterlib.systems;

import com.google.common.base.Preconditions;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.Clearable;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

/**
 * @author HypherionSA
 * @date 24/09/2022
 */
public class SimpleInventory implements Clearable {

    private final SimpleContainer itemHandler;
    private final int size;

    private final int stackSize;

    public SimpleInventory(int size, int maxStackSize) {
        itemHandler = new SimpleContainer(size) {
            @Override
            public int getMaxStackSize() {
                return maxStackSize;
            }
        };
        this.size = size;
        this.stackSize = maxStackSize;
    }

    private static void copyToInv(NonNullList<ItemStack> src, Container dest) {
        Preconditions.checkArgument(src.size() == dest.getContainerSize());
        for (int i = 0; i < src.size(); i++) {
            dest.setItem(i, src.get(i));
        }
    }

    private static NonNullList<ItemStack> copyFromInv(Container inv) {
        NonNullList<ItemStack> ret = NonNullList.withSize(inv.getContainerSize(), ItemStack.EMPTY);
        for (int i = 0; i < inv.getContainerSize(); i++) {
            ret.set(i, inv.getItem(i));
        }
        return ret;
    }

    public void readNBT(CompoundTag tag) {
        NonNullList<ItemStack> tmp = NonNullList.withSize(size, ItemStack.EMPTY);
        ContainerHelper.loadAllItems(tag, tmp);
        copyToInv(tmp, itemHandler);
    }

    public void writeNBT(CompoundTag tag) {
        ContainerHelper.saveAllItems(tag, copyFromInv(itemHandler));
    }

    public final int inventorySize() {
        return getItemHandler().getContainerSize();
    }

    public int getStackSize() {
        return stackSize;
    }

    @Override
    public void clearContent() {
        getItemHandler().clearContent();
    }

    public final Container getItemHandler() {
        return itemHandler;
    }

}
