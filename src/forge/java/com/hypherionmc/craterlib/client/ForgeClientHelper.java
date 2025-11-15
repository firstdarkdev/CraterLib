package com.hypherionmc.craterlib.client;

import com.hypherionmc.craterlib.core.platform.ClientPlatform;
import com.hypherionmc.craterlib.nojang.client.BridgedMinecraft;
import com.hypherionmc.craterlib.nojang.client.multiplayer.BridgedClientLevel;
import com.hypherionmc.craterlib.nojang.world.entity.player.BridgedPlayer;
import net.minecraft.client.Minecraft;
import net.minecraft.network.Connection;

import java.util.Objects;

/**
 * @author HypherionSA
 * @date 16/06/2022
 */
public class ForgeClientHelper implements ClientPlatform {

    public ForgeClientHelper() {
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
