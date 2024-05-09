package com.hypherionmc.craterlib.api.commands;

import com.hypherionmc.craterlib.nojang.authlib.BridgedGameProfile;
import com.hypherionmc.craterlib.nojang.commands.BridgedCommandSourceStack;
import com.hypherionmc.craterlib.nojang.world.entity.player.BridgedPlayer;
import com.hypherionmc.craterlib.utils.TriConsumer;
import com.mojang.brigadier.arguments.ArgumentType;
import lombok.Getter;
import net.minecraft.commands.arguments.GameProfileArgument;
import org.apache.commons.lang3.tuple.Pair;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.function.Consumer;

@Getter
public class CraterCommand {

    private final HashMap<String, Pair<ArgumentType<?>, TriConsumer<?, ?, BridgedCommandSourceStack>>> arguments = new LinkedHashMap<>();
    private Consumer<BridgedCommandSourceStack> executor;

    private final String commandName;
    private int permissionLevel = 4;

    CraterCommand(String commandName) {
        this.commandName = commandName;
    }

    public static CraterCommand literal(String commandName) {
        return new CraterCommand(commandName);
    }

    public CraterCommand requiresPermission(int perm) {
        this.permissionLevel = perm;
        return this;
    }

    public CraterCommand withGameProfileArgument(String key, TriConsumer<BridgedPlayer, List<BridgedGameProfile>, BridgedCommandSourceStack> executor) {
        arguments.put(key, Pair.of(GameProfileArgument.gameProfile(), executor));
        return this;
    }

    public CraterCommand executes(Consumer<BridgedCommandSourceStack> ctx) {
        executor = ctx;
        return this;
    }

    public boolean hasArguments() {
        return !arguments.isEmpty();
    }

}
