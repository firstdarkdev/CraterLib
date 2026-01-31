package com.hypherionmc.craterlib.impl.api.commands;

import com.hypherionmc.craterlib.api.game.commands.CraterFakePlayer;
import com.hypherionmc.craterlib.api.game.server.CraterGameServer;
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

public final class BridgedFakePlayer implements CraterFakePlayer {

    final MojangBridge internal;

    public BridgedFakePlayer(CraterGameServer server, int perm, String name, CraterFakePlayer delegate) {
        internal = new MojangBridge(server.unwrap(), perm, name, delegate);
    }

    @Override
    public CommandSourceStack unwrapInternal() {
        return internal;
    }

    @Override
    public void onSuccess(Supplier<Text> message, boolean bl) {
        internal.sendSuccess(() -> message.get().toGame(), bl);
    }

    @ApiStatus.Internal
    static class MojangBridge extends CommandSourceStack {

        private final CraterFakePlayer delegate;

        MojangBridge(MinecraftServer server, int perm, String name, CraterFakePlayer delegate) {
            super(CommandSource.NULL, Vec3.ZERO, Vec2.ZERO, server.overworld(), forLevel(perm), name, Component.literal(name), server, null);
            this.delegate = delegate;
        }

        @Override
        public void sendSuccess(Supplier<Component> supplier, boolean bl) {
            delegate.onSuccess(() -> Text.fromGame(supplier.get()), bl);
        }

        @Override
        public void sendFailure(Component arg) {
            delegate.onError(Text.fromGame(arg));
        }
    }

    static LevelBasedPermissionSet forLevel(int perm) {
        perm = Math.max(0, Math.min(4, perm));
        PermissionLevel level = PermissionLevel.byId(perm);
        return LevelBasedPermissionSet.forLevel(level);
    }

}
