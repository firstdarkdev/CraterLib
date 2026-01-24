package com.hypherionmc.craterlib.impl.api.commands;

import com.hypherionmc.craterlib.api.game.commands.CraterFakePlayer;
import com.hypherionmc.craterlib.api.game.text.Text;
import com.hypherionmc.craterlib.impl.api.server.BridgedMinecraftServer;
import net.minecraft.commands.CommandSource;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.permissions.LevelBasedPermissionSet;
import net.minecraft.server.permissions.PermissionLevel;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.ApiStatus;

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
            super(CommandSource.NULL, Vec3.ZERO, Vec2.ZERO, server.overworld(), forLevel(perm), name, Component.literal(name), server, null);
            this.successCallback = successCallback;
            this.errorCallback = errorCallback;
        }

        @Override
        public void sendSuccess(Supplier<Component> supplier, boolean bl) {
            successCallback.accept(() -> Text.fromGame(supplier.get()), bl);
        }

        @Override
        public void sendFailure(Component arg) {
            errorCallback.accept(Text.fromGame(arg));
        }
    }

    static LevelBasedPermissionSet forLevel(int perm) {
        perm = Math.max(0, Math.min(4, perm));
        PermissionLevel level = PermissionLevel.byId(perm);
        return LevelBasedPermissionSet.forLevel(level);
    }

}
