package com.hypherionmc.craterlib.api.commands;

import com.hypherionmc.craterlib.compat.LuckPermsCompat;
import com.hypherionmc.craterlib.core.platform.ModloaderEnvironment;
import com.hypherionmc.craterlib.nojang.authlib.BridgedGameProfile;
import com.hypherionmc.craterlib.nojang.commands.BridgedCommandSourceStack;
import com.hypherionmc.craterlib.nojang.world.entity.player.BridgedPlayer;
import com.hypherionmc.craterlib.utils.TriConsumer;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.GameProfileArgument;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.ApiStatus;

import java.util.List;
import java.util.function.Consumer;

public class CraterCommand {

    private final LiteralArgumentBuilder<CommandSourceStack> mojangCommand;
    private int permLevel = 4;
    private String luckPermNode = "";

    CraterCommand(LiteralArgumentBuilder<CommandSourceStack> cmd) {
        this.mojangCommand = cmd;
    }

    public static CraterCommand literal(String commandName) {
        return new CraterCommand(Commands.literal(commandName));
    }

    public CraterCommand requiresPermission(int perm) {
        this.permLevel = perm;
        this.mojangCommand.requires(this::checkPermission);
        return this;
    }

    public CraterCommand withNode(String key) {
        this.luckPermNode = key;
        return this;
    }

    public CraterCommand then(CraterCommand child) {
        this.mojangCommand.then(child.mojangCommand);
        return this;
    }

    public CraterCommand withGameProfilesArgument(String key, CommandExecutorWithArgs<List<BridgedGameProfile>> executor) {
        this.mojangCommand.then(Commands.argument(key, GameProfileArgument.gameProfile())
                .executes(context -> executor.run(
                        BridgedPlayer.of(context.getSource().getPlayerOrException()),
                        GameProfileArgument.getGameProfiles(context, key).stream().map(BridgedGameProfile::of).toList(),
                        BridgedCommandSourceStack.of(context.getSource()))
                ));
        return this;
    }

    public CraterCommand withBoolArgument(String key, CommandExecutorWithArgs<Boolean> executor) {
        this.mojangCommand.then(Commands.argument(key, BoolArgumentType.bool())
                .executes(context -> executor.run(
                        BridgedPlayer.of(context.getSource().getPlayerOrException()),
                        BoolArgumentType.getBool(context, key),
                        BridgedCommandSourceStack.of(context.getSource())
                )));
        return this;
    }

    public CraterCommand withWordArgument(String key, CommandExecutorWithArgs<String> executor) {
        this.mojangCommand.then(Commands.argument(key, StringArgumentType.word())
                .executes(context -> executor.run(
                        BridgedPlayer.of(context.getSource().getPlayerOrException()),
                        StringArgumentType.getString(context, key),
                        BridgedCommandSourceStack.of(context.getSource())
                )));
        return this;
    }

    public CraterCommand withStringArgument(String key, CommandExecutorWithArgs<String> executor) {
        this.mojangCommand.then(Commands.argument(key, StringArgumentType.string())
                .executes(context -> executor.run(
                        BridgedPlayer.of(context.getSource().getPlayerOrException()),
                        StringArgumentType.getString(context, key),
                        BridgedCommandSourceStack.of(context.getSource())
                )));
        return this;
    }

    public CraterCommand withPhraseArgument(String key, CommandExecutorWithArgs<String> executor) {
        this.mojangCommand.then(Commands.argument(key, StringArgumentType.greedyString())
                .executes(context -> executor.run(
                        BridgedPlayer.of(context.getSource().getPlayerOrException()),
                        StringArgumentType.getString(context, key),
                        BridgedCommandSourceStack.of(context.getSource())
                )));
        return this;
    }

    public CraterCommand withIntegerArgument(String key, CommandExecutorWithArgs<Integer> executor) {
        this.mojangCommand.then(Commands.argument(key, IntegerArgumentType.integer())
                .executes(context -> executor.run(
                        BridgedPlayer.of(context.getSource().getPlayerOrException()),
                        IntegerArgumentType.getInteger(context, key),
                        BridgedCommandSourceStack.of(context.getSource())
                )));
        return this;
    }

    public CraterCommand execute(SingleCommandExecutor<BridgedCommandSourceStack> executor) {
        this.mojangCommand.executes(context -> executor.run(BridgedCommandSourceStack.of(context.getSource())));
        return this;
    }

    @Deprecated(forRemoval = true)
    public CraterCommand executes(Consumer<BridgedCommandSourceStack> ctx) {
        return this.execute(stack -> {
            ctx.accept(stack);
            return 1;
        });
    }

    @Deprecated(forRemoval = true)
    public CraterCommand withGameProfileArgument(String key, TriConsumer<BridgedPlayer, List<BridgedGameProfile>, BridgedCommandSourceStack> executor) {
        return this.withGameProfilesArgument(key, (player, argument, stack) -> {
            executor.accept(player, argument, stack);
            return 1;
        });
    }

    @ApiStatus.Internal
    public void register(CommandDispatcher<CommandSourceStack> stack) {
        stack.register(this.mojangCommand);
    }

    private boolean checkPermission(CommandSourceStack stack) {
        if (!ModloaderEnvironment.INSTANCE.isModLoaded("luckperms") || !(stack.getEntity() instanceof Player) || luckPermNode.isEmpty())
            return stack.hasPermission(this.permLevel);

        return LuckPermsCompat.INSTANCE.hasPermission((ServerPlayer) stack.getEntity(), this.luckPermNode) || stack.hasPermission(this.permLevel);
    }

    @FunctionalInterface
    public interface CommandExecutorWithArgs<S> {
        int run(BridgedPlayer player, S argument, BridgedCommandSourceStack stack);
    }

    @FunctionalInterface
    public interface SingleCommandExecutor<S> {
        int run(S stack);
    }
}
