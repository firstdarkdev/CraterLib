package com.hypherionmc.craterlib.core.platform;

import com.hypherionmc.craterlib.nojang.world.entity.player.BridgedPlayer;
import com.hypherionmc.craterlib.utils.InternalServiceUtil;
import net.kyori.adventure.text.Component;

public interface CompatUtils {

    public static final CompatUtils INSTANCE = InternalServiceUtil.load(CompatUtils.class);

    boolean isPlayerActive(BridgedPlayer player);
    String getSkinUUID(BridgedPlayer player);
    boolean isPlayerBleeding(BridgedPlayer player);
    boolean playerBledOut(BridgedPlayer player);
    boolean playerRevived(BridgedPlayer player);
    boolean isPrivateMessage(BridgedPlayer player);
    Component getChannelPrefix(BridgedPlayer player);

}
