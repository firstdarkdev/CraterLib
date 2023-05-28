package com.hypherionmc.craterlib.api.event.server;

import com.hypherionmc.craterlib.core.event.CraterEvent;
import net.minecraft.advancements.Advancement;
import net.minecraft.world.entity.player.Player;

public class CraterAdvancementEvent extends CraterEvent {

    private final Advancement advancement;
    private final Player player;

    public CraterAdvancementEvent(Player player, Advancement advancement) {
        this.player = player;
        this.advancement = advancement;
    }

    public Advancement getAdvancement() {
        return advancement;
    }

    public Player getPlayer() {
        return player;
    }

    @Override
    public boolean canCancel() {
        return false;
    }
}
