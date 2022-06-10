package me.hypherionmc.craterlib.common.particles;

import net.minecraft.core.particles.SimpleParticleType;

/**
 * Helper Class for exposing a hidden constructor in the vanilla particle type
 */
public class WrappedSimpleParticleType extends SimpleParticleType {

    public WrappedSimpleParticleType(boolean alwaysShow) {
        super(alwaysShow);
    }
}
