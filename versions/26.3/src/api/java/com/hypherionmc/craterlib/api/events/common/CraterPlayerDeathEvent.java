package com.hypherionmc.craterlib.api.events.common;

import com.hypherionmc.craterlib.api.game.text.Text;
import com.hypherionmc.craterlib.api.game.world.entity.player.CraterPlayer;
import com.hypherionmc.craterlib.core.event.CraterEvent;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class CraterPlayerDeathEvent extends CraterEvent {

    private final CraterPlayer player;
    private final Text damageSource;

}
