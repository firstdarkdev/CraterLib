package com.hypherionmc.craterlib.compat;

import com.hypherionmc.craterlib.core.event.CraterEventBus;
import com.hypherionmc.craterlib.impl.api.world.entity.player.BridgedPlayer;
import lombok.NoArgsConstructor;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import team.creative.playerrevive.api.event.PlayerBleedOutEvent;
import team.creative.playerrevive.api.event.PlayerRevivedEvent;

@NoArgsConstructor
public class PlayerReviveEvents {

    @SubscribeEvent
    public void playerRevived(PlayerRevivedEvent event) {
        CraterEventBus.INSTANCE.postEvent(com.hypherionmc.craterlib.api.events.compat.PlayerRevivedEvent.of(BridgedPlayer.wrap(event.getEntity())));
    }

    @SubscribeEvent
    public void playerBledOutEvent(PlayerBleedOutEvent event) {
        CraterEventBus.INSTANCE.postEvent(com.hypherionmc.craterlib.api.events.compat.PlayerRevivedEvent.of(BridgedPlayer.wrap(event.getEntity())));
    }

}