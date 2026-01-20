package com.hypherionmc.craterlib.hytale;

import com.hypherionmc.craterlib.api.events.server.CraterPlayerEvent;
import com.hypherionmc.craterlib.api.events.server.CraterServerChatEvent;
import com.hypherionmc.craterlib.api.events.server.CraterServerLifecycleEvent;
import com.hypherionmc.craterlib.api.events.server.PlayerPreLoginEvent;
import com.hypherionmc.craterlib.core.event.CraterEventBus;
import com.hypherionmc.craterlib.nojang.authlib.BridgedGameProfile;
import com.hypherionmc.craterlib.nojang.server.BridgedMinecraftServer;
import com.hypherionmc.craterlib.nojang.world.entity.player.BridgedPlayer;
import com.hypherionmc.craterlib.utils.ChatUtils;
import com.hypixel.hytale.server.core.HytaleServer;
import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.event.events.BootEvent;
import com.hypixel.hytale.server.core.event.events.ShutdownEvent;
import com.hypixel.hytale.server.core.event.events.entity.EntityRemoveEvent;
import com.hypixel.hytale.server.core.event.events.player.PlayerChatEvent;
import com.hypixel.hytale.server.core.event.events.player.PlayerConnectEvent;
import com.hypixel.hytale.server.core.event.events.player.PlayerDisconnectEvent;
import com.hypixel.hytale.server.core.event.events.player.PlayerSetupConnectEvent;
import com.hypixel.hytale.server.core.modules.entity.damage.DeathSystems;

public class CraterHytaleEvents {

    public static void playerJoinedEvent(PlayerConnectEvent event) {
        CraterEventBus.INSTANCE.postEvent(new CraterPlayerEvent.PlayerLoggedIn(BridgedPlayer.of(event.getPlayerRef())));
    }

    public static void playerLeaveEvent(PlayerDisconnectEvent event) {
        CraterEventBus.INSTANCE.postEvent(new CraterPlayerEvent.PlayerLoggedOut(BridgedPlayer.of(event.getPlayerRef())));
    }

    public static void playerPreLoginEvent(PlayerSetupConnectEvent event) {
        try {
            PlayerPreLoginEvent evt = new PlayerPreLoginEvent(event.getPacketHandler().getChannel().remoteAddress(), BridgedGameProfile.of(event.getAuth()));
            CraterEventBus.INSTANCE.postEvent(evt);
            if (evt.getMessage() != null) {
                event.setCancelled(true);
                event.setReason(ChatUtils.getString(evt.getMessage()));
            }
        } catch (Exception ignored) {}
    }

    public static void serverBootEvent(BootEvent event) {
        CraterEventBus.INSTANCE.postEvent(new CraterServerLifecycleEvent.Started(BridgedMinecraftServer.of(HytaleServer.get())));
    }

    public static void serverShutdown(ShutdownEvent event) {
        CraterEventBus.INSTANCE.postEvent(new CraterServerLifecycleEvent.Stopped(BridgedMinecraftServer.of(HytaleServer.get())));
    }

    public static void serverChatEvent(PlayerChatEvent event) {
        Message m = event.getFormatter().format(event.getSender(), event.getContent());

        if (m.getFormattedMessage().rawText == null || m.getFormattedMessage().rawText.isBlank()) {
            m.getFormattedMessage().rawText = event.getContent();
        }

        CraterEventBus.INSTANCE.postEvent(
                new CraterServerChatEvent(BridgedPlayer.of(event.getSender()), event.getContent(), ChatUtils.mojangToAdventure(m))
        );
    }
}
