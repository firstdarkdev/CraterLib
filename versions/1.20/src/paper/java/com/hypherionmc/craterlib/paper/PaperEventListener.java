package com.hypherionmc.craterlib.paper;

import com.hypherionmc.craterlib.api.events.common.CraterPlayerDeathEvent;
import com.hypherionmc.craterlib.api.events.server.PlayerPreLoginEvent;
import com.hypherionmc.craterlib.api.events.server.*;
import com.hypherionmc.craterlib.core.event.CraterEventBus;
import com.hypherionmc.craterlib.nojang.advancements.BridgedAdvancement;
import com.hypherionmc.craterlib.nojang.authlib.BridgedGameProfile;
import com.hypherionmc.craterlib.nojang.server.BridgedMinecraftServer;
import com.hypherionmc.craterlib.nojang.world.entity.player.BridgedPlayer;
import com.mojang.authlib.GameProfile;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.ParseResults;
import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.server.MinecraftServer;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.craftbukkit.v1_20_R1.advancement.CraftAdvancement;
import org.bukkit.craftbukkit.v1_20_R1.entity.CraftPlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.*;
import org.bukkit.event.server.ServerCommandEvent;

public class PaperEventListener implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerDeath(PlayerDeathEvent event) {
        CraterEventBus.INSTANCE.postEvent(
                new CraterPlayerDeathEvent(BridgedPlayer.of(((CraftPlayer) event.getPlayer()).getHandle()), null, event.deathMessage())
        );
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onAdvancement(PlayerAdvancementDoneEvent event) {
        if (((CraftAdvancement) event.getAdvancement()).getHandle() == null || ((CraftAdvancement) event.getAdvancement()).getHandle().getDisplay() == null || !((CraftAdvancement) event.getAdvancement()).getHandle().getDisplay().shouldAnnounceChat())
            return;

        CraterEventBus.INSTANCE.postEvent(
                new CraterAdvancementEvent(BridgedPlayer.of(((CraftPlayer) event.getPlayer()).getHandle()), BridgedAdvancement.of(((CraftAdvancement) event.getAdvancement()).getHandle()))
        );
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerJoin(PlayerJoinEvent event) {
        CraterEventBus.INSTANCE.postEvent(new CraterPlayerEvent.PlayerLoggedIn(BridgedPlayer.of(((CraftPlayer) event.getPlayer()).getHandle())));
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerLeave(PlayerQuitEvent event) {
        CraterEventBus.INSTANCE.postEvent(new CraterPlayerEvent.PlayerLoggedOut(BridgedPlayer.of(((CraftPlayer) event.getPlayer()).getHandle())));
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onServerChat(AsyncChatEvent event) {
        CraterEventBus.INSTANCE.postEvent(
                new CraterServerChatEvent(BridgedPlayer.of(((CraftPlayer) event.getPlayer()).getHandle()), PlainTextComponentSerializer.plainText().serialize(event.message()), event.message())
        );
    }

    public void onServerStarting(MinecraftServer server) {
        CraterEventBus.INSTANCE.postEvent(new CraterServerLifecycleEvent.Starting(BridgedMinecraftServer.of(server)));
    }

    public void onServerStarted() {
        CraterEventBus.INSTANCE.postEvent(new CraterServerLifecycleEvent.Started(BridgedMinecraftServer.of(MinecraftServer.getServer())));
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onCommandEvent(PlayerCommandPreprocessEvent event) {
        CommandSourceStack stack = null;

        if (event.getPlayer() instanceof CraftPlayer craftPlayer) {
            stack = craftPlayer.getHandle().createCommandSourceStack();
        } else if (event.getPlayer() instanceof ConsoleCommandSender) {
            stack = MinecraftServer.getServer().createCommandSourceStack();
        }

        if (stack == null)
            return;

        String cmd = event.getMessage().substring(1);

        CommandDispatcher<CommandSourceStack> dispatcher = MinecraftServer.getServer().getCommands().getDispatcher();
        ParseResults<CommandSourceStack> parseResults = dispatcher.parse(cmd, stack);
        CraterEventBus.INSTANCE.postEvent(CraterCommandEvent.of(parseResults, cmd));
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onServerCommandEvent(ServerCommandEvent event) {
        CommandSourceStack stack = MinecraftServer.getServer().createCommandSourceStack();

        String cmd = event.getCommand();

        CommandDispatcher<CommandSourceStack> dispatcher = MinecraftServer.getServer().getCommands().getDispatcher();
        ParseResults<CommandSourceStack> parseResults = dispatcher.parse(cmd, stack);
        CraterEventBus.INSTANCE.postEvent(CraterCommandEvent.of(parseResults, cmd));
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPreLogin(AsyncPlayerPreLoginEvent event) {
        PlayerPreLoginEvent playerPreLoginEvent = new PlayerPreLoginEvent(null, BridgedGameProfile.of(new GameProfile(event.getUniqueId(), event.getName())));
        CraterEventBus.INSTANCE.postEvent(playerPreLoginEvent);

        if (playerPreLoginEvent.wasCancelled() || playerPreLoginEvent.getMessage() != null) {
            event.disallow(AsyncPlayerPreLoginEvent.Result.KICK_OTHER, playerPreLoginEvent.getMessage());
        }
    }
}
