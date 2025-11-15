package com.hypherionmc.craterlib.core.platform;

import com.hypherionmc.craterlib.nojang.client.BridgedMinecraft;
import com.hypherionmc.craterlib.nojang.client.multiplayer.BridgedClientLevel;
import com.hypherionmc.craterlib.nojang.world.entity.player.BridgedPlayer;
import com.hypherionmc.craterlib.utils.InternalServiceUtil;
import net.minecraft.network.Connection;

/**
 * @author HypherionSA
 */
public interface ClientPlatform {

    public final ClientPlatform INSTANCE = InternalServiceUtil.load(ClientPlatform.class);

    BridgedMinecraft getClientInstance();

    BridgedPlayer getClientPlayer();

    BridgedClientLevel getClientLevel();

    Connection getClientConnection();
}