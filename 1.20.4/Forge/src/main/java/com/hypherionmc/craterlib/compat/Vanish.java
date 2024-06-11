package com.hypherionmc.craterlib.compat;

import com.hypherionmc.craterlib.api.events.server.CraterPlayerEvent;
import com.hypherionmc.craterlib.core.event.CraterEventBus;
import com.hypherionmc.craterlib.nojang.world.entity.player.BridgedPlayer;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import redstonedubstep.mods.vanishmod.api.PlayerVanishEvent;

public class Vanish {

    public Vanish() {

    }

    @SubscribeEvent
    public void vanishevent(PlayerVanishEvent event) {
        if (event.isVanished()) {
            CraterEventBus.INSTANCE.postEvent(new CraterPlayerEvent.PlayerLoggedOut(BridgedPlayer.of(event.getEntity())));
        } else {
            CraterEventBus.INSTANCE.postEvent(new CraterPlayerEvent.PlayerLoggedIn(BridgedPlayer.of(event.getEntity())));
        }
    }

}