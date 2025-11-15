package com.hypherionmc.craterlib.nojang.client.multiplayer;

import com.hypherionmc.craterlib.utils.ChatUtils;
import lombok.RequiredArgsConstructor;
import net.kyori.adventure.text.Component;
import net.minecraft.client.multiplayer.ServerData;

@RequiredArgsConstructor(staticName = "of")
public class BridgedServerData {

    private final ServerData internal;

    public String name() {
        return internal.name;
    }

    public String ip() {
        return internal.ip;
    }

    public Component motd() {
        return ChatUtils.mojangToAdventure(internal.motd);
    }

    public int getMaxPlayers() {
        if (internal.players == null) {
            return internal.playerList.size() + 1;
        }

        return internal.players.max();
    }

    public ServerData toMojang() {
        return internal;
    }

}
