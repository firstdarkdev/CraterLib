package com.hypherionmc.craterlib.compat;

import com.hypherionmc.craterlib.api.events.server.CraterPlayerEvent;
import com.hypherionmc.craterlib.core.event.CraterEventBus;
import com.hypherionmc.craterlib.impl.api.world.entity.player.BridgedPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import redstonedubstep.mods.vanishmod.api.PlayerVanishEvent;

public class Vanish {

    public Vanish() {

    }

    @SubscribeEvent
    public void vanishevent(PlayerVanishEvent event) {
        if (event.getEntity() instanceof Player p) {
            if (event.isVanished()) {
                CraterEventBus.INSTANCE.postEvent(new CraterPlayerEvent.PlayerLoggedOut(BridgedPlayer.wrap(p), true));
            } else {
                CraterEventBus.INSTANCE.postEvent(new CraterPlayerEvent.PlayerLoggedIn(BridgedPlayer.wrap(p), true));
            }
        }
    }

}