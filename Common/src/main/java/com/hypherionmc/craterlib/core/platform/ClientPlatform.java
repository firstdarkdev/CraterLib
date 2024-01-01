package com.hypherionmc.craterlib.core.platform;

import com.hypherionmc.craterlib.util.ServiceUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.network.Connection;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

/**
 * @author HypherionSA
 */
public interface ClientPlatform {

    public final ClientPlatform INSTANCE = ServiceUtil.load(ClientPlatform.class);

    Minecraft getClientInstance();

    Player getClientPlayer();

    Level getClientLevel();

    Connection getClientConnection();
}
