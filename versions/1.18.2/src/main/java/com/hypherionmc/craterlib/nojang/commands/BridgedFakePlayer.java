package com.hypherionmc.craterlib.nojang.commands;

import com.hypherionmc.craterlib.nojang.server.BridgedMinecraftServer;
import com.hypherionmc.craterlib.utils.ChatUtils;
import net.minecraft.commands.CommandSource;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;

import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

public abstract class BridgedFakePlayer {

    final MojangBridge internal;

    public BridgedFakePlayer(BridgedMinecraftServer server, int perm, String name) {
        internal = new MojangBridge(server.toMojang(), perm, name, this::onSuccess, this::onError);
    }

    public abstract void onSuccess(Supplier<net.kyori.adventure.text.Component> supplier, Boolean aBoolean);

    public void onError(net.kyori.adventure.text.Component component) {
        this.onSuccess(() -> component, false);
    }

    public CommandSourceStack toMojang() {
        return internal;
    }

    static class MojangBridge extends CommandSourceStack {

        private final BiConsumer<Supplier<net.kyori.adventure.text.Component>, Boolean> successCallback;
        public final Consumer<net.kyori.adventure.text.Component> errorCallback;

        MojangBridge(MinecraftServer server, int perm, String name, BiConsumer<Supplier<net.kyori.adventure.text.Component>, Boolean> successCallback, Consumer<net.kyori.adventure.text.Component> errorCallback) {
            super(CommandSource.NULL, Vec3.ZERO, Vec2.ZERO, server.overworld(), perm, name, new TextComponent(name), server, null);
            this.successCallback = successCallback;
            this.errorCallback = errorCallback;
        }

        @Override
        public void sendSuccess(Component supplier, boolean bl) {
            successCallback.accept(() -> ChatUtils.mojangToAdventure(supplier), bl);
        }

        @Override
        public void sendFailure(Component arg) {
            errorCallback.accept(ChatUtils.mojangToAdventure(arg));
        }
    }
}
