package com.hypherionmc.craterlib.impl.api.client.multiplayer;

import com.hypherionmc.craterlib.api.game.client.multiplayer.CraterServerData;
import com.hypherionmc.craterlib.api.game.text.Text;
import lombok.RequiredArgsConstructor;
import net.minecraft.ChatFormatting;
import net.minecraft.client.multiplayer.ServerData;

@RequiredArgsConstructor(staticName = "wrap")
public class BridgedServerData implements CraterServerData {

    private final ServerData internal;

    @Override
    public String name() {
        return internal.name;
    }

    @Override
    public String ip() {
        return internal.ip;
    }

    @Override
    public Text motd() {
        return Text.fromGame(internal.motd);
    }

    @Override
    public int getMaxPlayers() {
        if (!internal.pinged || internal.status.getString() == null) {
            return internal.playerList.size() + 1;
        }

        try {
            return Integer.parseInt(ChatFormatting.stripFormatting(internal.status.getString()).split("/")[1]);
        } catch (Exception ignored) {}

        return 0;
    }

    @Override
    public ServerData unwrapInternal() {
        return internal;
    }

}
