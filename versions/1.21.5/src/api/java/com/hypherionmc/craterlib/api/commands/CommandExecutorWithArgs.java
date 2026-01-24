package com.hypherionmc.craterlib.api.commands;

import com.hypherionmc.craterlib.api.game.commands.CraterCommandSourceStack;
import com.hypherionmc.craterlib.api.game.world.entity.player.CraterPlayer;

@FunctionalInterface
    public interface CommandExecutorWithArgs<S> {
        int run(CraterPlayer player, S argument, CraterCommandSourceStack stack);
    }