package com.hypherionmc.craterlib.nojang.commands;

import com.hypherionmc.craterlib.nojang.world.entity.player.BridgedPlayer;
import com.hypherionmc.craterlib.utils.ChatUtils;
import lombok.RequiredArgsConstructor;
import net.kyori.adventure.text.Component;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.world.level.gamerules.GameRules;

import java.util.function.Supplier;

@RequiredArgsConstructor(staticName = "of")
public class BridgedCommandSourceStack {

    private final CommandSourceStack internal;

    public void sendSuccess(Supplier<Component> supplier, boolean bl) {
        if (!internal.getLevel().getGameRules().get(GameRules.SEND_COMMAND_FEEDBACK)) {
            internal.sendSystemMessage(ChatUtils.adventureToMojang(supplier.get()));
        } else {
            internal.sendSuccess(() -> ChatUtils.adventureToMojang(supplier.get()), bl);
        }
    }

    public void sendMessage(Component text) {
        internal.sendSystemMessage(ChatUtils.adventureToMojang(text));
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
