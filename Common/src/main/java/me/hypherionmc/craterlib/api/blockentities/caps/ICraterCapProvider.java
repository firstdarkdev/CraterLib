package me.hypherionmc.craterlib.api.blockentities.caps;

import net.minecraft.core.Direction;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

/**
 * @author HypherionSA
 * Interface for BlockEntities to expose "capabilities" across fabric/forge
 */
public interface ICraterCapProvider {

    public <T> Optional<T> getCapability(CapabilityHandler handler, @Nullable Direction side);

}
