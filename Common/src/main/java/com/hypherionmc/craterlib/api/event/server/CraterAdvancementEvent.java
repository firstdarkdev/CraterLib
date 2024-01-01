package com.hypherionmc.craterlib.api.event.server;

import com.hypherionmc.craterlib.core.event.CraterEvent;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.DisplayInfo;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;

import java.util.Optional;

public class CraterAdvancementEvent extends CraterEvent {

    private final Advancement advancement;
    private final Player player;
    private final Component title;
    private final Component description;

    public CraterAdvancementEvent(Player player, Advancement advancement) {
        this.player = player;
        this.advancement = advancement;

        this.title = advancement.getDisplay().getTitle();
        this.description = advancement.getDisplay().getDescription();
    }

    public Advancement getAdvancement() {
        return advancement;
    }

    public Component getDescription() {
        return description;
    }

    public Component getTitle() {
        return title;
    }

    public Player getPlayer() {
        return player;
    }

    @Override
    public boolean canCancel() {
        return false;
    }
}
