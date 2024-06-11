package com.hypherionmc.craterlib.nojang.client.multiplayer;

import com.hypherionmc.craterlib.utils.ChatUtils;
import lombok.RequiredArgsConstructor;
import net.kyori.adventure.text.Component;
import net.minecraft.ChatFormatting;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.multiplayer.ServerStatusPinger;

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
        if (!internal.pinged || internal.status.getString() == null) {
            try {
                new ServerStatusPinger().pingServer(internal, () -> {});
            } catch (Exception ignored) {}
        }

        try {
            return Integer.parseInt(ChatFormatting.stripFormatting(internal.status.getString()).split("/")[1]);
        } catch (Exception ignored) {}

        return 0;
    }

    public ServerData toMojang() {
        return internal;
    }

}
