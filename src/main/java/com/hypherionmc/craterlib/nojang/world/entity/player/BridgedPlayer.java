package com.hypherionmc.craterlib.nojang.world.entity.player;

import com.hypherionmc.craterlib.nojang.authlib.BridgedGameProfile;
import com.hypherionmc.craterlib.nojang.core.BridgedBlockPos;
import com.hypherionmc.craterlib.nojang.world.level.BridgedGameType;
import com.hypherionmc.craterlib.utils.ChatUtils;
import com.hypixel.hytale.protocol.Position;
import com.hypixel.hytale.server.core.entity.entities.Player;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import lombok.RequiredArgsConstructor;
import net.kyori.adventure.text.Component;

import java.util.UUID;

@RequiredArgsConstructor(staticName = "of")
public class BridgedPlayer {

    private final PlayerRef internal;

    public Component getDisplayName() {
        return Component.text(internal.getUsername());
    }

    public Component getName() {
        return getDisplayName();
    }

    public UUID getUUID() {
        return internal.getUuid();
    }

    public String getStringUUID() {
        return getUUID().toString();
    }

    public BridgedGameProfile getGameProfile() {
        return BridgedGameProfile.of(internal.getPacketHandler().getAuth());
    }

    public boolean isServerPlayer() {
        return true;
    }

    public PlayerRef toHytale() {
        return internal;
    }

    public BridgedBlockPos getOnPos() {
        // TODO: Fix implementation
        return BridgedBlockPos.of(new Position());
    }

    public float getHealth() {
        // TODO: Implement
        return 0;
    }

    public float getMaxHealth() {
        // TODO: Implement
        return 0;
    }

    public String getHeldItemMainHand() {
        String value = "Nothing";

        // TODO: Implement
//        if (!internal.getItemInHand(InteractionHand.MAIN_HAND).isEmpty()) {
//            value = internal.getItemInHand(InteractionHand.MAIN_HAND).getDisplayName().getString();
//        }

        return value;
    }

    public String getHeldItemOffHand() {
        String value = "Nothing";

        // TODO: Implement
//        if (!internal.getItemInHand(InteractionHand.OFF_HAND).isEmpty()) {
//            value = internal.getItemInHand(InteractionHand.OFF_HAND).getDisplayName().getString();
//        }

        return value;
    }

    public BridgedGameType getGameMode() {
        return BridgedGameType.fromHytale(asPlayer().getGameMode());
    }

    private Player asPlayer() {
        Player p = new Player();
        p.init(internal.getUuid(), internal);
        return p;
    }

    public void disconnect(Component message) {
        internal.getPacketHandler().disconnect(ChatUtils.getString(message));
    }
}
