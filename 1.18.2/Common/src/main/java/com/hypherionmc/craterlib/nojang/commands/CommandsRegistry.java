package com.hypherionmc.craterlib.nojang.commands;

import com.hypherionmc.craterlib.api.commands.CraterCommand;
import com.hypherionmc.craterlib.nojang.authlib.BridgedGameProfile;
import com.hypherionmc.craterlib.nojang.world.entity.player.BridgedPlayer;
import com.hypherionmc.craterlib.utils.TriConsumer;
import com.mojang.authlib.GameProfile;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.GameProfileArgument;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CommandsRegistry {

    public static final CommandsRegistry INSTANCE = new CommandsRegistry();

    private final List<CraterCommand> commands = new ArrayList<>();

    public void registerCommand(CraterCommand cmd) {
        commands.add(cmd);
    }

    public void registerCommands(CommandDispatcher<CommandSourceStack> stack) {
        commands.forEach(cmd -> {
            if (cmd.hasArguments()) {
                CommandWithArguments.register(cmd, stack);
            } else {
                CommandWithoutArguments.register(cmd, stack);
            }
        });
    }

    static class CommandWithoutArguments {

        public static void register(CraterCommand cmd, CommandDispatcher<CommandSourceStack> dispatcher) {
            LiteralArgumentBuilder<CommandSourceStack> command = Commands.literal(cmd.getCommandName())
                    .requires(source -> source.hasPermission(cmd.getPermissionLevel()))
                            .executes(context -> {
                               cmd.getExecutor().accept(BridgedCommandSourceStack.of(context.getSource()));
                               return 1;
                            });

            dispatcher.register(command);
        }

    }

    @SuppressWarnings("unchecked")
    static class CommandWithArguments {

        public static void register(CraterCommand cmd, CommandDispatcher<CommandSourceStack> dispatcher) {
            LiteralArgumentBuilder<CommandSourceStack> command = Commands.literal(cmd.getCommandName())
                    .requires(source -> source.hasPermission(cmd.getPermissionLevel()));

            cmd.getArguments().forEach((key, pair) -> command.then(Commands.argument(key, pair.getLeft()).executes(context -> {

                // This is FUCKING UGLY.... Need to improve this in the future
                if (pair.getLeft() instanceof GameProfileArgument) {
                    Collection<GameProfile> profiles = GameProfileArgument.getGameProfiles(context, key);
                    List<BridgedGameProfile> bridgedGameProfiles = new ArrayList<>();

                    profiles.forEach(p -> bridgedGameProfiles.add(BridgedGameProfile.of(p)));

                    ((TriConsumer<BridgedPlayer, List<BridgedGameProfile>, BridgedCommandSourceStack>) pair.getRight())
                            .accept(BridgedPlayer.of(context.getSource().getPlayerOrException()), bridgedGameProfiles, BridgedCommandSourceStack.of(context.getSource()));
                    return 1;
                }

                return 1;
            })));

            dispatcher.register(command);
        }

    }

}
