package com.hypherionmc.craterlib.nojang.client.multiplayer;

import lombok.RequiredArgsConstructor;
import net.kyori.adventure.text.Component;

// TODO: Implement if Possible
@RequiredArgsConstructor(staticName = "of")
public class BridgedServerData {

    //private final ServerData internal;

    public String name() {
        return "Not Implemented";
    }

    public String ip() {
        return "Not Implemented";
    }

    public Component motd() {
        return Component.text("Not Implemented");
    }

    public int getMaxPlayers() {
        return 0;
    }

//    public ServerData toMojang() {
//        return internal;
//    }

}
