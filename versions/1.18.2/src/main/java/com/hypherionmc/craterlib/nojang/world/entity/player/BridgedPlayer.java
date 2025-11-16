package com.hypherionmc.craterlib.nojang.world.entity.player;

import com.hypherionmc.craterlib.nojang.authlib.BridgedGameProfile;
import com.hypherionmc.craterlib.nojang.core.BridgedBlockPos;
import com.hypherionmc.craterlib.nojang.world.level.BridgedGameType;
import com.hypherionmc.craterlib.utils.ChatUtils;
import lombok.RequiredArgsConstructor;
import net.kyori.adventure.text.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

@RequiredArgsConstructor(staticName = "of")
public class BridgedPlayer {

    private final Player internal;

    public Component getDisplayName() {
        return ChatUtils.mojangToAdventure(internal.getDisplayName());
    }

    public Component getName() {
        return ChatUtils.mojangToAdventure(internal.getName());
    }

    public UUID getUUID() {
        return internal.getUUID();
    }

    public String getStringUUID() {
        return internal.getStringUUID();
    }

    public BridgedGameProfile getGameProfile() {
        return BridgedGameProfile.of(internal.getGameProfile());
    }

    public boolean isServerPlayer() {
        return internal instanceof ServerPlayer;
    }

    public Player toMojang() {
        return internal;
    }

    public BridgedBlockPos getOnPos() {
        return BridgedBlockPos.of(internal.getOnPos());
    }

    public float getHealth() {
        return internal.getHealth();
    }

    public float getMaxHealth() {
        return internal.getMaxHealth();
    }

    public String getHeldItemMainHand() {
        String value = "Nothing";

        if (!internal.getItemInHand(InteractionHand.MAIN_HAND).isEmpty()) {
            value = internal.getItemInHand(InteractionHand.MAIN_HAND).getDisplayName().getString();
        }

        return value;
    }

    public String getHeldItemOffHand() {
        String value = "Nothing";

        if (!internal.getItemInHand(InteractionHand.OFF_HAND).isEmpty()) {
            value = internal.getItemInHand(InteractionHand.OFF_HAND).getDisplayName().getString();
        }

        return value;
    }

    public BridgedGameType getGameMode() {
        return BridgedGameType.fromMojang(toMojangServerPlayer().gameMode.getGameModeForPlayer());
    }

    @Nullable
    public ServerGamePacketListenerImpl getConnection() {
        if (isServerPlayer()) {
            return ((ServerPlayer) internal).connection;
        }
        return null;
    }

    public void disconnect(Component message) {
        if (isServerPlayer())
            toMojangServerPlayer().connection.disconnect(ChatUtils.adventureToMojang(message));
    }

    public ServerPlayer toMojangServerPlayer() {
        return (ServerPlayer) internal;
    }
}
