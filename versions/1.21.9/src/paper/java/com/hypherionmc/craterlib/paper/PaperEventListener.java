package com.hypherionmc.craterlib.paper;

import com.hypherionmc.craterlib.api.events.common.CraterPlayerDeathEvent;
import com.hypherionmc.craterlib.api.events.server.*;
import com.hypherionmc.craterlib.api.events.server.PlayerPreLoginEvent;
import com.hypherionmc.craterlib.api.game.text.Text;
import com.hypherionmc.craterlib.api.game.world.entity.player.CraterPlayer;
import com.hypherionmc.craterlib.core.event.CraterEventBus;
import com.hypherionmc.craterlib.impl.api.advancements.BridgedAdvancement;
import com.hypherionmc.craterlib.impl.api.authlib.BridgedGameProfile;
import com.hypherionmc.craterlib.impl.api.server.BridgedMinecraftServer;
import com.hypherionmc.craterlib.impl.api.world.entity.player.BridgedPlayer;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.ParseResults;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.context.StringRange;
import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.arguments.ComponentArgument;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.players.NameAndId;
import net.minecraft.world.entity.player.Player;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.craftbukkit.advancement.CraftAdvancement;
import org.bukkit.craftbukkit.damage.CraftDamageSource;
import org.bukkit.craftbukkit.entity.CraftPlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.*;
import org.bukkit.event.server.ServerCommandEvent;
import org.jetbrains.annotations.Nullable;

public class PaperEventListener implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerDeath(PlayerDeathEvent event) {
        CraterEventBus.INSTANCE.postEvent(
                new CraterPlayerDeathEvent(BridgedPlayer
                        .wrap(((CraftPlayer) event.getPlayer()).getHandle()),
                        Text.fromGame(((CraftDamageSource) event.getDamageSource()).getHandle().getLocalizedDeathMessage(((CraftPlayer) event.getPlayer()).getHandle())))
        );
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onAdvancement(PlayerAdvancementDoneEvent event) {
        if (((CraftAdvancement) event.getAdvancement()).getHandle().value().display().isEmpty() || !((CraftAdvancement) event.getAdvancement()).getHandle().value().display().get().shouldAnnounceChat())
            return;

        CraterEventBus.INSTANCE.postEvent(
                new CraterAdvancementEvent(BridgedPlayer.wrap(((CraftPlayer) event.getPlayer()).getHandle()), BridgedAdvancement.wrap(((CraftAdvancement) event.getAdvancement()).getHandle().value()))
        );
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerJoin(PlayerJoinEvent event) {
        CraterEventBus.INSTANCE.postEvent(new CraterPlayerEvent.PlayerLoggedIn(BridgedPlayer.wrap(((CraftPlayer) event.getPlayer()).getHandle())));
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerLeave(PlayerQuitEvent event) {
        CraterEventBus.INSTANCE.postEvent(new CraterPlayerEvent.PlayerLoggedOut(BridgedPlayer.wrap(((CraftPlayer) event.getPlayer()).getHandle())));
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onServerChat(AsyncChatEvent event) {
        CraterServerChatEvent evt = new CraterServerChatEvent(
                BridgedPlayer.wrap(((CraftPlayer) event.getPlayer()).getHandle()),
                PlainTextComponentSerializer.plainText().serialize(event.message()), Text.from(event.message())
        );

        evt.setUpstreamCancelled(event.isCancelled());

        CraterEventBus.INSTANCE.postEvent(evt);
    }

    public void onServerStarting(MinecraftServer server) {
        CraterEventBus.INSTANCE.postEvent(new CraterServerLifecycleEvent.Starting(BridgedMinecraftServer.wrap(server)));
    }

    public void onServerStarted() {
        CraterEventBus.INSTANCE.postEvent(new CraterServerLifecycleEvent.Started(BridgedMinecraftServer.wrap(MinecraftServer.getServer())));
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onCommandEvent(PlayerCommandPreprocessEvent event) {
        try {
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
            CraterCommandEvent commandEvent = CraterCommandEvent.of(cmd, getCommandString(parseResults), getPlayer(parseResults), getTarget(parseResults), getMessage(parseResults));
            CraterEventBus.INSTANCE.postEvent(commandEvent);
        } catch (Exception ignored) {}
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onServerCommandEvent(ServerCommandEvent event) {
        try {
            CommandSourceStack stack = MinecraftServer.getServer().createCommandSourceStack();

            String cmd = event.getCommand();

            CommandDispatcher<CommandSourceStack> dispatcher = MinecraftServer.getServer().getCommands().getDispatcher();
            ParseResults<CommandSourceStack> parseResults = dispatcher.parse(cmd, stack);
            CraterCommandEvent commandEvent = CraterCommandEvent.of(cmd, getCommandString(parseResults), getPlayer(parseResults), getTarget(parseResults), getMessage(parseResults));
            CraterEventBus.INSTANCE.postEvent(commandEvent);
        } catch (Exception ignored) {}
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPreLogin(AsyncPlayerPreLoginEvent event) {
        PlayerPreLoginEvent playerPreLoginEvent = new PlayerPreLoginEvent(null,
                BridgedGameProfile.of(new NameAndId(event.getUniqueId(), event.getName())));
        CraterEventBus.INSTANCE.postEvent(playerPreLoginEvent);

        if (playerPreLoginEvent.wasCancelled() || playerPreLoginEvent.getMessage() != null) {
            event.disallow(AsyncPlayerPreLoginEvent.Result.KICK_OTHER, playerPreLoginEvent.getMessage().getComponent());
        }
    }

    public String getCommandString(ParseResults<CommandSourceStack> stackParseResults) {
        return stackParseResults.getReader().getString();
    }

    @Nullable
    public CraterPlayer getPlayer(ParseResults<CommandSourceStack> stackParseResults) {
        try {
            Player p = stackParseResults.getContext().getLastChild().getSource().getPlayer();

            if (p != null)
                return BridgedPlayer.wrap(p);
        } catch (Exception ignored) {}

        return null;
    }

    public String getTarget(ParseResults<CommandSourceStack> stackParseResults) {
        CommandContext<CommandSourceStack> context = stackParseResults.getContext().build(stackParseResults.getReader().getString());
        StringRange selector_range = stackParseResults.getContext().getArguments().get("targets").getRange();
        return context.getInput().substring(selector_range.getStart(), selector_range.getEnd());
    }

    public Text getMessage(ParseResults<CommandSourceStack> stackParseResults) {
        return Text.fromGame(ComponentArgument.getRawComponent(stackParseResults.getContext().build(stackParseResults.getReader().getString()), "message"));
    }
}