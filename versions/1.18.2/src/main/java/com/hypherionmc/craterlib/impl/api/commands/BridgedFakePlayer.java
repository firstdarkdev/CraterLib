package com.hypherionmc.craterlib.impl.api.commands;

import com.hypherionmc.craterlib.api.game.commands.CraterFakePlayer;
import com.hypherionmc.craterlib.api.game.text.Text;
import com.hypherionmc.craterlib.impl.api.server.BridgedMinecraftServer;
import net.minecraft.commands.CommandSource;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NonNull;

import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

public abstract class BridgedFakePlayer implements CraterFakePlayer {

    final MojangBridge internal;

    public BridgedFakePlayer(BridgedMinecraftServer server, int perm, String name) {
        internal = new MojangBridge(server.unwrap(), perm, name, this::onSuccess, this::onError);
    }

    public abstract void onSuccess(Supplier<Text> supplier, Boolean aBoolean);

    @Override
    public CommandSourceStack unwrapInternal() {
        return internal;
    }

    @ApiStatus.Internal
    static class MojangBridge extends CommandSourceStack {

        private final BiConsumer<Supplier<Text>, Boolean> successCallback;
        public final Consumer<Text> errorCallback;

        MojangBridge(MinecraftServer server, int perm, String name, BiConsumer<Supplier<Text>, Boolean> successCallback, Consumer<Text> errorCallback) {
            super(CommandSource.NULL, Vec3.ZERO, Vec2.ZERO, server.overworld(), perm, name, new TextComponent(name), server, null);
            this.successCallback = successCallback;
            this.errorCallback = errorCallback;
        }

        @Override
        public void sendSuccess(@NonNull Component supplier, boolean bl) {
            successCallback.accept(() -> Text.fromGame(supplier), bl);
        }

        @Override
        public void sendFailure(Component arg) {
            errorCallback.accept(Text.fromGame(arg));
        }
    }

}
