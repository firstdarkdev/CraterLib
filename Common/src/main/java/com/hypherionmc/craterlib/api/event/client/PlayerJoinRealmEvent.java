package com.hypherionmc.craterlib.api.event.client;

import com.hypherionmc.craterlib.core.event.CraterEvent;
import com.mojang.realmsclient.dto.RealmsServer;

public class PlayerJoinRealmEvent extends CraterEvent {

        private final RealmsServer server;

        public PlayerJoinRealmEvent(RealmsServer server) {
            this.server = server;
        }

        public RealmsServer getServer() {
            return server;
        }

    @Override
    public boolean canCancel() {
        return false;
    }
}