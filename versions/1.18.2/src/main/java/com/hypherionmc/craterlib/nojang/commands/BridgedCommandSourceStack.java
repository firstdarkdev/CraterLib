package com.hypherionmc.craterlib.nojang.commands;

import com.hypherionmc.craterlib.CraterConstants;
import com.hypherionmc.craterlib.nojang.world.entity.player.BridgedPlayer;
import com.hypherionmc.craterlib.utils.ChatUtils;
import lombok.RequiredArgsConstructor;
import net.kyori.adventure.text.Component;
import net.minecraft.commands.CommandSourceStack;

import java.util.function.Supplier;

import static net.minecraft.world.level.GameRules.RULE_SENDCOMMANDFEEDBACK;

@RequiredArgsConstructor(staticName = "of")
public class BridgedCommandSourceStack {

    private final CommandSourceStack internal;

    public void sendSuccess(Supplier<Component> supplier, boolean bl) {
        if (!internal.getServer().getGameRules().getBoolean(RULE_SENDCOMMANDFEEDBACK)) {
            try {
                internal.getPlayerOrException().displayClientMessage(ChatUtils.adventureToMojang(supplier.get()), false);
            } catch (Exception ignored) {}
        } else {
            internal.sendSuccess(ChatUtils.adventureToMojang(supplier.get()), bl);
        }
    }

    public void sendMessage(Component text) {
        try {
            internal.getPlayerOrException().displayClientMessage(ChatUtils.adventureToMojang(text), false);
        } catch (Exception ignored) {}
    }

    public void sendFailure(Component text) {
        internal.sendFailure(ChatUtils.adventureToMojang(text));
    }

    public boolean isPlayer() {
        try {
            internal.getPlayerOrException();
            return true;
        } catch (Exception ignored) {
            return false;
        }
    }

    public BridgedPlayer getPlayer() {
        try {
            return BridgedPlayer.of(internal.getPlayerOrException());
        } catch (Exception e) {
            CraterConstants.LOG.error("Failed to retrieve player", e);
        }

        return null;
    }

    public CommandSourceStack toMojang() {
        return internal;
    }
}
