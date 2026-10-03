package com.hypherionmc.craterlib.api.events.server;

import com.hypherionmc.craterlib.api.game.achievements.CraterAchievement;
import com.hypherionmc.craterlib.api.game.achievements.CraterDisplayInfo;
import com.hypherionmc.craterlib.api.game.text.Text;
import com.hypherionmc.craterlib.api.game.world.entity.player.CraterPlayer;
import com.hypherionmc.craterlib.core.event.CraterEvent;
import lombok.Getter;

import java.util.Optional;

@Getter
public class CraterAdvancementEvent extends CraterEvent {

    private final CraterAchievement advancement;
    private final CraterPlayer player;
    private final Text title;
    private final Text description;

    public CraterAdvancementEvent(CraterPlayer player, CraterAchievement advancement) {
        this.advancement = advancement;
        this.player = player;

        Optional<CraterDisplayInfo> displayInfo = advancement.displayInfo();

        if (displayInfo.isPresent()) {
            this.title = displayInfo.get().displayName();
            this.description = displayInfo.get().description();
        } else {
            this.title = Text.literal("Unknown");
            this.description = Text.literal("Unknown");
        }
    }
}
