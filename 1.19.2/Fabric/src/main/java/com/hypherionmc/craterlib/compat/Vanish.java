package com.hypherionmc.craterlib.compat;

import com.hypherionmc.craterlib.api.events.server.CraterPlayerEvent;
import com.hypherionmc.craterlib.core.event.CraterEventBus;
import com.hypherionmc.craterlib.nojang.world.entity.player.BridgedPlayer;
import me.drex.vanish.api.VanishAPI;
import me.drex.vanish.api.VanishEvents;
import net.minecraft.server.level.ServerPlayer;

public class Vanish {

    public static void register() {
        VanishEvents.VANISH_EVENT.register((serverPlayer, b) -> {
            if (b) {
                CraterEventBus.INSTANCE.postEvent(new CraterPlayerEvent.PlayerLoggedOut(BridgedPlayer.of(serverPlayer)));
            } else {
                CraterEventBus.INSTANCE.postEvent(new CraterPlayerEvent.PlayerLoggedIn(BridgedPlayer.of(serverPlayer)));
            }
        });
    }

    public static boolean isPlayerVanished(ServerPlayer player) {
        return VanishAPI.isVanished(player);
    }
}
