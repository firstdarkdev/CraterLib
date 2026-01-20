package com.hypherionmc.craterlib.nojang.commands;

import com.hypherionmc.craterlib.nojang.world.entity.player.BridgedPlayer;
import com.hypherionmc.craterlib.utils.ChatUtils;
import com.hypixel.hytale.server.core.command.system.CommandContext;
import lombok.RequiredArgsConstructor;
import net.kyori.adventure.text.Component;

import java.util.function.Supplier;

@RequiredArgsConstructor(staticName = "of")
public class BridgedCommandSourceStack {

    private final CommandContext internal;

    public void sendSuccess(Supplier<Component> supplier, boolean bl) {
        internal.sendMessage(ChatUtils.adventureToMojang(supplier.get()));
    }

    public void sendMessage(Component text) {
        internal.sendMessage(ChatUtils.adventureToMojang(text));
    }

    public void sendFailure(Component text) {
        internal.sendMessage(ChatUtils.adventureToMojang(text));
    }

    public boolean isPlayer() {
        return internal.isPlayer();
    }

    public BridgedPlayer getPlayer() {
        return null;
        //return BridgedPlayer.of(internal);
    }

    public CommandContext toHytale() {
        return internal;
    }
}
