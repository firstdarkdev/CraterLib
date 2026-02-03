package com.hypherionmc.craterlib.impl.api.commands;

import com.hypherionmc.craterlib.api.game.commands.CraterCommandSourceStack;
import com.hypherionmc.craterlib.api.game.text.Text;
import com.hypherionmc.craterlib.api.loader.CraterLoader;
import com.hypherionmc.craterlib.impl.api.world.entity.player.BridgedPlayer;
import lombok.RequiredArgsConstructor;
import net.minecraft.commands.CommandSourceStack;

import java.util.function.Supplier;

import static net.minecraft.world.level.GameRules.RULE_SENDCOMMANDFEEDBACK;

@RequiredArgsConstructor(staticName = "wrap")
public class BridgedCommandSourceStack implements CraterCommandSourceStack {

    private final CommandSourceStack internal;

    @Override
    public void sendSuccess(Supplier<Text> supplier, boolean bl) {
        if (!internal.getServer().getGameRules().getBoolean(RULE_SENDCOMMANDFEEDBACK)) {
            try {
                internal.getPlayerOrException().displayClientMessage(supplier.get().toGame(), bl);
            } catch (Exception ignored) {}
        } else {
            internal.sendSuccess(() -> supplier.get().toGame(), bl);
        }
    }

    @Override
    public void sendMessage(Text text) {
        try {
            internal.getPlayerOrException().displayClientMessage(text.toGame(), false);
        } catch (Exception ignored) {}
    }

    @Override
    public void sendFailure(Text text) {
        internal.sendFailure(text.toGame());
    }

    @Override
    public boolean isPlayer() {
        try {
            internal.getPlayerOrException();
            return true;
        } catch (Exception ignored) {
            return false;
        }
    }

    @Override
    public BridgedPlayer getPlayer() {
        try {
            return BridgedPlayer.wrap(internal.getPlayerOrException());
        } catch (Exception e) {
            CraterLoader.LOGGER.error("Failed to retrieve player", e);
        }

        return null;
    }

    @Override
    public CommandSourceStack unwrapInternal() {
        return internal;
    }
}
