package com.hypherionmc.craterlib.impl.api.commands;

import com.hypherionmc.craterlib.api.commands.CommandExecutorWithArgs;
import com.hypherionmc.craterlib.api.commands.CraterCommand;
import com.hypherionmc.craterlib.api.commands.SingleCommandExecutor;
import com.hypherionmc.craterlib.api.game.authlib.CraterGameProfile;
import com.hypherionmc.craterlib.api.game.commands.CraterCommandSourceStack;
import com.hypherionmc.craterlib.api.loader.CraterLoader;
import com.hypherionmc.craterlib.api.loader.LoaderType;
import com.hypherionmc.craterlib.impl.api.authlib.BridgedGameProfile;
import com.hypherionmc.craterlib.impl.api.world.entity.player.BridgedPlayer;
import com.hypherionmc.craterlib.impl.compat.LuckPermsCompatImpl;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.GameProfileArgument;
import org.jetbrains.annotations.ApiStatus;

import java.util.List;

public class CraterCommandImpl implements CraterCommand {

    private final LiteralArgumentBuilder<CommandSourceStack> mojangCommand;
    private int permLevel = 4;
    private String luckPermNode = "";

    CraterCommandImpl(LiteralArgumentBuilder<CommandSourceStack> cmd) {
        this.mojangCommand = cmd;
    }

    @Override
    public CraterCommandImpl requiresPermission(int perm) {
        this.permLevel = perm;
        this.mojangCommand.requires(this::checkPermission);
        return this;
    }

    @Override
    public CraterCommandImpl withNode(String key) {
        this.luckPermNode = key;
        return this;
    }

    @SuppressWarnings("unchecked")
    @Override
    public CraterCommandImpl then(CraterCommand child) {
        this.mojangCommand.then((ArgumentBuilder<CommandSourceStack, ?>) child.unwrapInternal());
        return this;
    }

    @Override
    public CraterCommand withGameProfilesArgument(String key, CommandExecutorWithArgs<List<? extends CraterGameProfile>> executor) {
        this.mojangCommand.then(Commands.argument(key, GameProfileArgument.gameProfile())
                .executes(context -> executor.run(
                        BridgedPlayer.wrap(context.getSource().getPlayer()),
                        GameProfileArgument.getGameProfiles(context, key).stream().map(BridgedGameProfile::of).toList(),
                        BridgedCommandSourceStack.wrap(context.getSource()))
                ));
        return this;
    }

    @Override
    public CraterCommandImpl withBoolArgument(String key, CommandExecutorWithArgs<Boolean> executor) {
        this.mojangCommand.then(Commands.argument(key, BoolArgumentType.bool())
                .executes(context -> executor.run(
                        BridgedPlayer.wrap(context.getSource().getPlayer()),
                        BoolArgumentType.getBool(context, key),
                        BridgedCommandSourceStack.wrap(context.getSource())
                )));
        return this;
    }

    @Override
    public CraterCommandImpl withWordArgument(String key, CommandExecutorWithArgs<String> executor) {
        this.mojangCommand.then(Commands.argument(key, StringArgumentType.word())
                .executes(context -> executor.run(
                        BridgedPlayer.wrap(context.getSource().getPlayer()),
                        StringArgumentType.getString(context, key),
                        BridgedCommandSourceStack.wrap(context.getSource())
                )));
        return this;
    }

    @Override
    public CraterCommandImpl withStringArgument(String key, CommandExecutorWithArgs<String> executor) {
        this.mojangCommand.then(Commands.argument(key, StringArgumentType.string())
                .executes(context -> executor.run(
                        BridgedPlayer.wrap(context.getSource().getPlayer()),
                        StringArgumentType.getString(context, key),
                        BridgedCommandSourceStack.wrap(context.getSource())
                )));
        return this;
    }

    @Override
    public CraterCommandImpl withPhraseArgument(String key, CommandExecutorWithArgs<String> executor) {
        this.mojangCommand.then(Commands.argument(key, StringArgumentType.greedyString())
                .executes(context -> executor.run(
                        BridgedPlayer.wrap(context.getSource().getPlayer()),
                        StringArgumentType.getString(context, key),
                        BridgedCommandSourceStack.wrap(context.getSource())
                )));
        return this;
    }

    @Override
    public CraterCommandImpl withIntegerArgument(String key, CommandExecutorWithArgs<Integer> executor) {
        this.mojangCommand.then(Commands.argument(key, IntegerArgumentType.integer())
                .executes(context -> executor.run(
                        BridgedPlayer.wrap(context.getSource().getPlayer()),
                        IntegerArgumentType.getInteger(context, key),
                        BridgedCommandSourceStack.wrap(context.getSource())
                )));
        return this;
    }

    @Override
    public CraterCommandImpl execute(SingleCommandExecutor<CraterCommandSourceStack> executor) {
        this.mojangCommand.executes(context -> executor.run(BridgedCommandSourceStack.wrap(context.getSource())));
        return this;
    }

    @ApiStatus.Internal
    public void register(CommandDispatcher<CommandSourceStack> stack) {
        stack.register(this.mojangCommand);
    }

    private boolean checkPermission(CommandSourceStack stack) {
        try {
            if (!CraterLoader.isModLoaded("luckperms") || CraterLoader.getLoaderType() == LoaderType.PAPER || !stack.isPlayer() || luckPermNode.isEmpty())
                return stack.hasPermission(this.permLevel);

            return LuckPermsCompatImpl.INSTANCE.hasPermission(stack.getPlayer(), this.luckPermNode) || stack.hasPermission(this.permLevel);
        } catch (Exception e) {
            return stack.hasPermission(this.permLevel);
        }
    }

    @Override
    public LiteralArgumentBuilder<CommandSourceStack> unwrapInternal() {
        return mojangCommand;
    }
}
