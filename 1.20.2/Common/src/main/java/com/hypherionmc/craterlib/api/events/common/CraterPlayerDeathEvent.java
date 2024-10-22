package com.hypherionmc.craterlib.api.events.common;

import com.hypherionmc.craterlib.core.event.CraterEvent;
import com.hypherionmc.craterlib.nojang.world.entity.player.BridgedPlayer;
import com.hypherionmc.craterlib.utils.ChatUtils;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.kyori.adventure.text.Component;
import net.minecraft.world.damagesource.DamageSource;

@RequiredArgsConstructor
@Getter
public class CraterPlayerDeathEvent extends CraterEvent {

    private final BridgedPlayer player;
    private final DamageSource damageSource;
    private Component deathMessage;

    public CraterPlayerDeathEvent(BridgedPlayer player, DamageSource damageSource, Component deathMessage) {
        this(player, null);
        this.deathMessage = deathMessage;
    }

    public Component getDeathMessage() {
        return deathMessage != null ? deathMessage : ChatUtils.mojangToAdventure(damageSource.getLocalizedDeathMessage(player.toMojang()));
    }
}
