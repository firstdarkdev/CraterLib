package com.hypherionmc.craterlib.nojang.commands;

import com.hypherionmc.craterlib.nojang.server.BridgedMinecraftServer;
import com.hypherionmc.craterlib.utils.ChatUtils;
import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.command.system.CommandSender;
import com.hypixel.hytale.server.core.console.ConsoleSender;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

public abstract class BridgedFakePlayer {

    final HytaleBridge internal;

    public BridgedFakePlayer(BridgedMinecraftServer server, int perm, String name) {
        internal = new HytaleBridge(name, this::onSuccess, this::onError);
    }

    public abstract void onSuccess(Supplier<net.kyori.adventure.text.Component> supplier, Boolean aBoolean);

    public void onError(net.kyori.adventure.text.Component component) {
        this.onSuccess(() -> component, false);
    }

    public CommandSender toHytale() {
        return internal;
    }

    static class HytaleBridge implements CommandSender {

        private final BiConsumer<Supplier<net.kyori.adventure.text.Component>, Boolean> successCallback;
        public final Consumer<net.kyori.adventure.text.Component> errorCallback;

        private final CommandSender delegate = ConsoleSender.INSTANCE;
        private final String name;

        public HytaleBridge(String name, BiConsumer<Supplier<net.kyori.adventure.text.Component>, Boolean> successCallback, Consumer<net.kyori.adventure.text.Component> errorCallback) {
            this.name = name;
            this.successCallback = successCallback;
            this.errorCallback = errorCallback;
        }

        @Override
        public String getDisplayName() {
            return name;
        }

        @Override
        public UUID getUuid() {
            return delegate.getUuid();
        }

        @Override
        public boolean hasPermission(@NotNull String s) {
            // TODO: Implement Proper Command Permissions
            return delegate.hasPermission(s);
        }

        @Override
        public boolean hasPermission(@NotNull String s, boolean b) {
            // TODO: Implement Proper Command Permissions
            return delegate.hasPermission(s, b);
        }

        @Override
        public void sendMessage(@NotNull Message message) {
            this.delegate.sendMessage(message);
            successCallback.accept(() -> ChatUtils.mojangToAdventure(message), false);
        }
    }
}
