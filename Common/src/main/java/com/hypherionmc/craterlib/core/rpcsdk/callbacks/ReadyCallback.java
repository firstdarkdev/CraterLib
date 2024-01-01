package com.hypherionmc.craterlib.core.rpcsdk.callbacks;

import com.hypherionmc.craterlib.core.rpcsdk.DiscordUser;
import com.sun.jna.Callback;

/**
 * @author HypherionSA
 * Callback for when the RPC has connected successfully
 */
public interface ReadyCallback extends Callback {

    /**
     * Called when the RPC is connected and ready to be used
     *
     * @param user The user the RPC is displayed on
     * @see DiscordUser
     */
    void apply(DiscordUser user);
}
