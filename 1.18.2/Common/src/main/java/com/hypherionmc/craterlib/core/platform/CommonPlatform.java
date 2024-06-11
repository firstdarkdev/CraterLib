package com.hypherionmc.craterlib.core.platform;

import com.hypherionmc.craterlib.nojang.server.BridgedMinecraftServer;
import com.hypherionmc.craterlib.utils.InternalServiceUtil;

/**
 * @author HypherionSA
 */
public interface CommonPlatform {

    public CommonPlatform INSTANCE = InternalServiceUtil.load(CommonPlatform.class);

    BridgedMinecraftServer getMCServer();

}