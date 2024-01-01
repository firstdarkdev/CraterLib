package com.hypherionmc.craterlib.core.platform;

import com.hypherionmc.craterlib.core.network.CraterNetworkHandler;
import com.hypherionmc.craterlib.util.ServiceUtil;
import net.minecraft.server.MinecraftServer;

/**
 * @author HypherionSA
 */
public interface CommonPlatform {

    public CommonPlatform INSTANCE = ServiceUtil.load(CommonPlatform.class);

    default CraterNetworkHandler createPacketHandler(String modid) {
        return this.createPacketHandler(modid, true, true);
    }

    CraterNetworkHandler createPacketHandler(String modid, boolean requiredClient, boolean requiredServer);

    MinecraftServer getMCServer();

}
