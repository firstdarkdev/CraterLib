package com.hypherionmc.craterlib.client;

import com.hypherionmc.craterlib.core.platform.ClientPlatform;
import com.hypherionmc.craterlib.nojang.client.BridgedMinecraft;
import com.hypherionmc.craterlib.nojang.client.multiplayer.BridgedClientLevel;
import com.hypherionmc.craterlib.nojang.world.entity.player.BridgedPlayer;
import net.minecraft.client.Minecraft;
import net.minecraft.network.Connection;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

import java.util.Objects;

/**
 * @author HypherionSA
 */
public class NeoForgeClientHelper implements ClientPlatform {

    public NeoForgeClientHelper() {
    }

    @Override
    public BridgedMinecraft getClientInstance() {
        return new BridgedMinecraft();
    }

    @Override
    public BridgedPlayer getClientPlayer() {
        return BridgedPlayer.of(Minecraft.getInstance().player);
    }

    @Override
    public BridgedClientLevel getClientLevel() {
        return BridgedClientLevel.of(Minecraft.getInstance().level);
    }

    @Override
    public Connection getClientConnection() {
        Objects.requireNonNull(Minecraft.getInstance().getConnection(), "Cannot send packets when not in game!");
        return Minecraft.getInstance().getConnection().getConnection();
    }
}
