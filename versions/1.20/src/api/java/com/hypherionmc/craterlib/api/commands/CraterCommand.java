package com.hypherionmc.craterlib.api.commands;

import com.hypherionmc.craterlib.api.game.CraterWrappedAPI;
import com.hypherionmc.craterlib.api.game.authlib.CraterGameProfile;
import com.hypherionmc.craterlib.api.game.commands.CraterCommandSourceStack;
import com.hypherionmc.craterlib.core.services.CraterServices;

import java.util.List;

public interface CraterCommand extends CraterWrappedAPI {

    static CraterCommand literal(String name) {
        return CraterServices.UTILS.createCommand(name);
    }

    CraterCommand requiresPermission(int perm);
    CraterCommand withNode(String node);
    CraterCommand then(CraterCommand child);

    CraterCommand withGameProfilesArgument(String key, CommandExecutorWithArgs<List<? extends CraterGameProfile>> executor);
    CraterCommand withBoolArgument(String key, CommandExecutorWithArgs<Boolean> executor);
    CraterCommand withWordArgument(String key, CommandExecutorWithArgs<String> executor);
    CraterCommand withStringArgument(String key, CommandExecutorWithArgs<String> executor);
    CraterCommand withPhraseArgument(String key, CommandExecutorWithArgs<String> executor);
    CraterCommand withIntegerArgument(String key, CommandExecutorWithArgs<Integer> executor);
    CraterCommand execute(SingleCommandExecutor<CraterCommandSourceStack> executor);

}
