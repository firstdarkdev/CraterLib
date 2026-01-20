package com.hypherionmc.craterlib.api.commands;

import com.hypherionmc.craterlib.CraterConstants;
import com.hypherionmc.craterlib.compat.LuckPermsCompat;
import com.hypherionmc.craterlib.core.platform.LoaderType;
import com.hypherionmc.craterlib.core.platform.ModloaderEnvironment;
import com.hypherionmc.craterlib.nojang.authlib.BridgedGameProfile;
import com.hypherionmc.craterlib.nojang.commands.BridgedCommandSourceStack;
import com.hypherionmc.craterlib.nojang.world.entity.player.BridgedPlayer;
import com.hypherionmc.craterlib.utils.TriConsumer;
import com.hypixel.hytale.server.core.command.system.CommandContext;
import com.hypixel.hytale.server.core.command.system.CommandRegistry;
import com.hypixel.hytale.server.core.command.system.basecommands.CommandBase;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.Consumer;

// TODO: Implement this
public class CraterCommand {

    private final CommandBase hytaleCommand;
    private int permLevel = 4;
    private String luckPermNode = "";

    CraterCommand(CommandBase bridge) {
        this.hytaleCommand = bridge;
    }

    public static CraterCommand literal(String commandName) {
        return new CraterCommand(
                new HytaleCommandBridge(commandName, "commands.ctlib." + commandName.toLowerCase(), false)
        );
    }

    public CraterCommand requiresPermission(int perm) {
        this.permLevel = perm;
        // TODO: Implement Permissions
        return this;
    }

    public CraterCommand withNode(String key) {
        this.luckPermNode = key;
        return this;
    }

    public CraterCommand then(CraterCommand child) {
        //this.mojangCommand.then(child.mojangCommand);
        return this;
    }

    public CraterCommand withGameProfilesArgument(String key, CommandExecutorWithArgs<List<BridgedGameProfile>> executor) {
        /*this.mojangCommand.then(Commands.argument(key, GameProfileArgument.gameProfile())
                .executes(context -> executor.run(
                        BridgedPlayer.of(context.getSource().getPlayer()),
                        GameProfileArgument.getGameProfiles(context, key).stream().map(BridgedGameProfile::of).toList(),
                        BridgedCommandSourceStack.of(context.getSource()))
                ));*/
        return this;
    }

    public CraterCommand withBoolArgument(String key, CommandExecutorWithArgs<Boolean> executor) {
        /*this.mojangCommand.then(Commands.argument(key, BoolArgumentType.bool())
                .executes(context -> executor.run(
                        BridgedPlayer.of(context.getSource().getPlayer()),
                        BoolArgumentType.getBool(context, key),
                        BridgedCommandSourceStack.of(context.getSource())
                )));*/
        return this;
    }

    public CraterCommand withWordArgument(String key, CommandExecutorWithArgs<String> executor) {
        /*this.mojangCommand.then(Commands.argument(key, StringArgumentType.word())
                .executes(context -> executor.run(
                        BridgedPlayer.of(context.getSource().getPlayer()),
                        StringArgumentType.getString(context, key),
                        BridgedCommandSourceStack.of(context.getSource())
                )));*/
        return this;
    }

    public CraterCommand withStringArgument(String key, CommandExecutorWithArgs<String> executor) {
        /*this.mojangCommand.then(Commands.argument(key, StringArgumentType.string())
                .executes(context -> executor.run(
                        BridgedPlayer.of(context.getSource().getPlayer()),
                        StringArgumentType.getString(context, key),
                        BridgedCommandSourceStack.of(context.getSource())
                )));*/
        return this;
    }

    public CraterCommand withPhraseArgument(String key, CommandExecutorWithArgs<String> executor) {
        /*this.mojangCommand.then(Commands.argument(key, StringArgumentType.greedyString())
                .executes(context -> executor.run(
                        BridgedPlayer.of(context.getSource().getPlayer()),
                        StringArgumentType.getString(context, key),
                        BridgedCommandSourceStack.of(context.getSource())
                )));*/
        return this;
    }

    public CraterCommand withIntegerArgument(String key, CommandExecutorWithArgs<Integer> executor) {
        /*this.mojangCommand.then(Commands.argument(key, IntegerArgumentType.integer())
                .executes(context -> executor.run(
                        BridgedPlayer.of(context.getSource().getPlayer()),
                        IntegerArgumentType.getInteger(context, key),
                        BridgedCommandSourceStack.of(context.getSource())
                )));*/
        return this;
    }

    public CraterCommand execute(SingleCommandExecutor<BridgedCommandSourceStack> executor) {
        //this.mojangCommand.executes(context -> executor.run(BridgedCommandSourceStack.of(context.getSource())));
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
    public void register(CommandRegistry stack) {
        stack.registerCommand(this.hytaleCommand);
    }

//    private boolean checkPermission(CommandSourceStack stack) {
//        try {
//            if (!ModloaderEnvironment.INSTANCE.isModLoaded("luckperms") || ModloaderEnvironment.INSTANCE.getLoaderType() == LoaderType.PAPER || !stack.isPlayer() || luckPermNode.isEmpty())
//                return stack.permissions().hasPermission(new Permission.HasCommandLevel(PermissionLevel.byId(this.permLevel)));
//
//            return LuckPermsCompat.INSTANCE.hasPermission(stack.getPlayer(), this.luckPermNode) || stack.permissions().hasPermission(new Permission.HasCommandLevel(PermissionLevel.byId(this.permLevel)));
//        } catch (Exception e) {
//            CraterConstants.LOG.error("Failed to check luckperms permissions", e);
//            return stack.permissions().hasPermission(new Permission.HasCommandLevel(PermissionLevel.byId(this.permLevel)));
//        }
//    }

    @FunctionalInterface
    public interface CommandExecutorWithArgs<S> {
        int run(BridgedPlayer player, S argument, BridgedCommandSourceStack stack);
    }

    @FunctionalInterface
    public interface SingleCommandExecutor<S> {
        int run(S stack);
    }

    private static class HytaleCommandBridge extends CommandBase {

        public HytaleCommandBridge(@NotNull String name, @NotNull String description, boolean requiresConfirmation) {
            super(name, description, requiresConfirmation);
        }

        @Override
        protected void executeSync(@NotNull CommandContext commandContext) {

        }
    }
}
