package com.hypherionmc.craterlib.nojang.commands;

import com.hypherionmc.craterlib.nojang.world.entity.player.BridgedPlayer;
import com.hypherionmc.craterlib.utils.ChatUtils;
import lombok.RequiredArgsConstructor;
import net.kyori.adventure.text.Component;
import net.minecraft.commands.CommandSourceStack;

import java.util.function.Supplier;

@RequiredArgsConstructor(staticName = "of")
public class BridgedCommandSourceStack {

    private final CommandSourceStack internal;

    public void sendSuccess(Supplier<Component> supplier, boolean bl) {
        internal.sendSuccess(() -> ChatUtils.adventureToMojang(supplier.get()), bl);
    }

    public void sendFailure(Component text) {
        internal.sendFailure(ChatUtils.adventureToMojang(text));
    }

    public boolean isPlayer() {
        return internal.isPlayer();
    }

    public BridgedPlayer getPlayer() {
        return BridgedPlayer.of(internal.getPlayer());
    }

    public CommandSourceStack toMojang() {
        return internal;
    }
}
