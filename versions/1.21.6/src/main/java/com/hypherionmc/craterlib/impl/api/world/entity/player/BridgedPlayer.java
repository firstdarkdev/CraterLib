package com.hypherionmc.craterlib.impl.api.world.entity.player;

import com.hypherionmc.craterlib.api.game.text.Text;
import com.hypherionmc.craterlib.api.game.world.entity.player.CraterPlayer;
import com.hypherionmc.craterlib.api.game.world.level.CraterGameType;
import com.hypherionmc.craterlib.impl.api.authlib.BridgedGameProfile;
import com.hypherionmc.craterlib.impl.api.core.BridgedBlockPos;
import com.hypherionmc.craterlib.impl.api.world.level.BridgedGameType;
import lombok.RequiredArgsConstructor;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

@RequiredArgsConstructor(staticName = "wrap")
public class BridgedPlayer implements CraterPlayer {

    private final Player internal;

    @Override
    public Text getDisplayName() {
        return Text.fromGame(internal.getDisplayName());
    }

    @Override
    public Text getName() {
        return Text.fromGame(internal.getName());
    }

    @Override
    public UUID getUUID() {
        return internal.getUUID();
    }

    @Override
    public String getStringUUID() {
        return internal.getStringUUID();
    }

    @Override
    public BridgedGameProfile getGameProfile() {
        return BridgedGameProfile.wrap(internal.getGameProfile());
    }

    @Override
    public boolean isServerPlayer() {
        return internal instanceof ServerPlayer;
    }

    @Override
    public Player unwrapInternal() {
        return internal;
    }

    @Override
    public BridgedBlockPos getOnPos() {
        return BridgedBlockPos.wrap(internal.getOnPos());
    }

    @Override
    public float getHealth() {
        return internal.getHealth();
    }

    @Override
    public float getMaxHealth() {
        return internal.getMaxHealth();
    }

    @Override
    public String getHeldItemMainHand() {
        String value = "Nothing";

        if (!internal.getItemInHand(InteractionHand.MAIN_HAND).isEmpty()) {
            value = internal.getItemInHand(InteractionHand.MAIN_HAND).getDisplayName().getString();
        }

        return value;
    }

    @Override
    public String getHeldItemOffHand() {
        String value = "Nothing";

        if (!internal.getItemInHand(InteractionHand.OFF_HAND).isEmpty()) {
            value = internal.getItemInHand(InteractionHand.OFF_HAND).getDisplayName().getString();
        }

        return value;
    }

    @Override
    public CraterGameType getGameMode() {
        return BridgedGameType.wrap(internal.gameMode());
    }

    @Nullable
    public ServerGamePacketListenerImpl getConnection() {
        if (isServerPlayer()) {
            return ((ServerPlayer) internal).connection;
        }
        return null;
    }

    @Override
    public void disconnect(Text message) {
        if (isServerPlayer())
            toMojangServerPlayer().connection.disconnect((Component) message.toGame());
    }

    public ServerPlayer toMojangServerPlayer() {
        return (ServerPlayer) internal;
    }
}
