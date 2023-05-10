package com.hypherionmc.craterlib.api.blockentity.caps;

import net.minecraft.core.Direction;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

/**
 * @author HypherionSA
 * Interface for BlockEntities to expose "capabilities" across fabric/forge
 */
public interface ICraterCapProvider {

    <T> Optional<T> getCapability(CraterCapabilityHandler handler, @Nullable Direction side);

}
