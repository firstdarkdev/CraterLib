package com.hypherionmc.craterlib.api.event.common;

import com.hypherionmc.craterlib.core.event.CraterEvent;
import com.hypherionmc.craterlib.core.event.CraterEventBus;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;

public class CraterLivingDeathEvent extends CraterEvent {

    private final DamageSource damageSource;
    private final LivingEntity entity;

    public CraterLivingDeathEvent(LivingEntity entity, DamageSource source) {
        this.entity = entity;
        this.damageSource = source;
    }

    public LivingEntity getEntity() {
        return entity;
    }

    public DamageSource getDamageSource() {
        return damageSource;
    }

    @Override
    public boolean canCancel() {
        return true;
    }
}
