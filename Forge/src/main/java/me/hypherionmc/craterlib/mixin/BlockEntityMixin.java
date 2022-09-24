package me.hypherionmc.craterlib.mixin;

import me.hypherionmc.craterlib.api.blockentities.caps.ForgeCapability;
import me.hypherionmc.craterlib.api.blockentities.caps.IForgeCapProvider;
import me.hypherionmc.craterlib.common.blockentity.CraterBlockEntity;
import me.hypherionmc.craterlib.systems.SimpleInventory;
import me.hypherionmc.craterlib.systems.energy.CustomEnergyStorage;
import me.hypherionmc.craterlib.systems.energy.ForgeEnergyWrapper;
import me.hypherionmc.craterlib.systems.inventory.ForgeInventoryWrapper;
import net.minecraft.core.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.wrapper.SidedInvWrapper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;

import java.util.Optional;

/**
 * @author HypherionSA
 * @date 24/09/2022
 */
@Mixin(CraterBlockEntity.class)
public class BlockEntityMixin implements ICapabilityProvider {

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        IForgeCapProvider capProvider = (IForgeCapProvider) this;

        if (cap == ForgeCapabilities.ENERGY) {
            Optional<CustomEnergyStorage> forgeCap = capProvider.getForgeCapability(ForgeCapability.ENERGY, side);
            if (forgeCap.isPresent()) {
                return LazyOptional.of(() -> new ForgeEnergyWrapper(forgeCap.get())).cast();
            }
        }

        if (cap == ForgeCapabilities.ITEM_HANDLER) {
            Optional<SimpleInventory> inventory = capProvider.getForgeCapability(ForgeCapability.ITEM, side);
            if (inventory.isPresent()) {
                return LazyOptional.of(() -> new SidedInvWrapper(new ForgeInventoryWrapper(inventory.get()), side)).cast();
            }
        }

        return LazyOptional.empty();
    }
}
