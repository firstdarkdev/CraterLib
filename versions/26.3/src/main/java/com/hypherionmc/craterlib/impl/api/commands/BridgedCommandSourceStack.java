package com.hypherionmc.craterlib.impl.api.commands;

import com.hypherionmc.craterlib.api.game.commands.CraterCommandSourceStack;
import com.hypherionmc.craterlib.api.game.text.Text;
import com.hypherionmc.craterlib.impl.api.world.entity.player.BridgedPlayer;
import lombok.RequiredArgsConstructor;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.world.level.gamerules.GameRules;

import java.util.function.Supplier;

@RequiredArgsConstructor(staticName = "wrap")
public class BridgedCommandSourceStack implements CraterCommandSourceStack {

    private final CommandSourceStack internal;

    @Override
    public void sendSuccess(Supplier<Text> supplier, boolean bl) {
        if (!internal.getLevel().getGameRules().get(GameRules.SEND_COMMAND_FEEDBACK)) {
            internal.sendSystemMessage(supplier.get().toGame());
        } else {
            internal.sendSuccess(() -> supplier.get().toGame(), bl);
        }
    }

    @Override
    public void sendMessage(Text text) {
        internal.sendSystemMessage(text.toGame());
    }

    @Override
    public void sendFailure(Text text) {
        internal.sendFailure(text.toGame());
    }

    @Override
    public boolean isPlayer() {
        return internal.isPlayer();
    }

    @Override
    public BridgedPlayer getPlayer() {
        return BridgedPlayer.wrap(internal.getPlayer());
    }

    @Override
    public CommandSourceStack unwrapInternal() {
        return internal;
    }
}
