package me.hypherionmc.craterlib.api.blockentities.caps;

import net.minecraft.core.Direction;

import java.util.Optional;

/**
 * @author HypherionSA
 * @date 24/09/2022
 */
public interface IForgeCapProvider {

    <T> Optional<T> getForgeCapability(ForgeCapability capability, Direction side);

}
