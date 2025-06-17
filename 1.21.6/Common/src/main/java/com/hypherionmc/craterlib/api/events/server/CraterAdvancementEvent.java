package com.hypherionmc.craterlib.api.events.server;

import com.hypherionmc.craterlib.core.event.CraterEvent;
import com.hypherionmc.craterlib.nojang.advancements.BridgedAdvancement;
import com.hypherionmc.craterlib.nojang.advancements.BridgedDisplayInfo;
import com.hypherionmc.craterlib.nojang.world.entity.player.BridgedPlayer;
import lombok.Getter;
import net.kyori.adventure.text.Component;

import java.util.Optional;

@Getter
public class CraterAdvancementEvent extends CraterEvent {

    private final BridgedAdvancement advancement;
    private final BridgedPlayer player;
    private final Component title;
    private final Component description;

    public CraterAdvancementEvent(BridgedPlayer player, BridgedAdvancement advancement) {
        this.advancement = advancement;
        this.player = player;

        Optional<BridgedDisplayInfo> displayInfo = advancement.displayInfo();

        if (displayInfo.isPresent()) {
            this.title = displayInfo.get().displayName();
            this.description = displayInfo.get().description();
        } else {
            this.title = Component.text("Unknown");
            this.description = Component.text("Unknown");
        }
    }
}
