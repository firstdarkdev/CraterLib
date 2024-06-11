package com.hypherionmc.craterlib.core.platform;

import com.hypherionmc.craterlib.nojang.world.entity.player.BridgedPlayer;
import com.hypherionmc.craterlib.utils.InternalServiceUtil;

public interface CompatUtils {

    public static final CompatUtils INSTANCE = InternalServiceUtil.load(CompatUtils.class);

    boolean isPlayerActive(BridgedPlayer player);
    String getSkinUUID(BridgedPlayer player);

}
