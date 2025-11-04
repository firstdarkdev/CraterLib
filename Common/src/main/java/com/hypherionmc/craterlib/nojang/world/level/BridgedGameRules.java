package com.hypherionmc.craterlib.nojang.world.level;

import lombok.RequiredArgsConstructor;
import net.minecraft.world.level.gamerules.GameRule;
import net.minecraft.world.level.gamerules.GameRules;
import org.jetbrains.annotations.NotNull;

@RequiredArgsConstructor(staticName = "bridge")
public class BridgedGameRules {

    private final GameRules internal;

    // Wrapped Mojang Rules for convenience
    public static final WrappedBooleanKey RULE_DOFIRETICK = WrappedBooleanKey.wrap(null);
    public static final WrappedBooleanKey RULE_ALLOWFIRETICKAWAYFROMPLAYERS = WrappedBooleanKey.wrap(null);
    public static final WrappedBooleanKey RULE_MOBGRIEFING = WrappedBooleanKey.wrap(GameRules.MOB_GRIEFING);
    public static final WrappedBooleanKey RULE_KEEPINVENTORY = WrappedBooleanKey.wrap(GameRules.KEEP_INVENTORY);
    public static final WrappedBooleanKey RULE_DOMOBSPAWNING = WrappedBooleanKey.wrap(GameRules.SPAWN_MOBS);
    public static final WrappedBooleanKey RULE_DOMOBLOOT = WrappedBooleanKey.wrap(null);
    public static final WrappedBooleanKey RULE_PROJECTILESCANBREAKBLOCKS = WrappedBooleanKey.wrap(GameRules.PROJECTILES_CAN_BREAK_BLOCKS);
    public static final WrappedBooleanKey RULE_DOBLOCKDROPS = WrappedBooleanKey.wrap(GameRules.BLOCK_DROPS);
    public static final WrappedBooleanKey RULE_DOENTITYDROPS = WrappedBooleanKey.wrap(GameRules.ENTITY_DROPS);
    public static final WrappedBooleanKey RULE_COMMANDBLOCKOUTPUT = WrappedBooleanKey.wrap(GameRules.COMMAND_BLOCK_OUTPUT);
    public static final WrappedBooleanKey RULE_NATURAL_REGENERATION = WrappedBooleanKey.wrap(GameRules.NATURAL_HEALTH_REGENERATION);
    public static final WrappedBooleanKey RULE_DAYLIGHT = WrappedBooleanKey.wrap(null);
    public static final WrappedBooleanKey RULE_LOGADMINCOMMANDS = WrappedBooleanKey.wrap(GameRules.LOG_ADMIN_COMMANDS);
    public static final WrappedBooleanKey RULE_SHOWDEATHMESSAGES = WrappedBooleanKey.wrap(GameRules.SHOW_DEATH_MESSAGES);
    public static final WrappedIntegerKey RULE_RANDOMTICKING = WrappedIntegerKey.wrap(GameRules.RANDOM_TICK_SPEED);
    public static final WrappedBooleanKey RULE_SENDCOMMANDFEEDBACK = WrappedBooleanKey.wrap(GameRules.SEND_COMMAND_FEEDBACK);
    public static final WrappedBooleanKey RULE_REDUCEDDEBUGINFO = WrappedBooleanKey.wrap(GameRules.REDUCED_DEBUG_INFO);
    public static final WrappedBooleanKey RULE_SPECTATORSGENERATECHUNKS = WrappedBooleanKey.wrap(null);
    public static final WrappedIntegerKey RULE_SPAWN_RADIUS = WrappedIntegerKey.wrap(GameRules.RESPAWN_RADIUS);
    public static final WrappedBooleanKey RULE_DISABLE_PLAYER_MOVEMENT_CHECK = WrappedBooleanKey.wrap(GameRules.PLAYER_MOVEMENT_CHECK);
    public static final WrappedBooleanKey RULE_DISABLE_ELYTRA_MOVEMENT_CHECK = WrappedBooleanKey.wrap(GameRules.ELYTRA_MOVEMENT_CHECK);
    public static final WrappedIntegerKey RULE_MAX_ENTITY_CRAMMING = WrappedIntegerKey.wrap(GameRules.MAX_ENTITY_CRAMMING);
    public static final WrappedBooleanKey RULE_WEATHER_CYCLE = WrappedBooleanKey.wrap(GameRules.ADVANCE_WEATHER);
    public static final WrappedBooleanKey RULE_LIMITED_CRAFTING = WrappedBooleanKey.wrap(GameRules.LIMITED_CRAFTING);
    public static final WrappedIntegerKey RULE_MAX_COMMAND_CHAIN_LENGTH = WrappedIntegerKey.wrap(GameRules.MAX_COMMAND_SEQUENCE_LENGTH);
    public static final WrappedIntegerKey RULE_MAX_COMMAND_FORK_COUNT = WrappedIntegerKey.wrap(GameRules.MAX_COMMAND_FORKS);
    public static final WrappedIntegerKey RULE_COMMAND_MODIFICATION_BLOCK_LIMIT = WrappedIntegerKey.wrap(GameRules.MAX_BLOCK_MODIFICATIONS);
    public static final WrappedBooleanKey RULE_ANNOUNCE_ADVANCEMENTS = WrappedBooleanKey.wrap(GameRules.SHOW_ADVANCEMENT_MESSAGES);
    public static final WrappedBooleanKey RULE_DISABLE_RAIDS = WrappedBooleanKey.wrap(GameRules.RAIDS);
    public static final WrappedBooleanKey RULE_DOINSOMNIA = WrappedBooleanKey.wrap(null);
    public static final WrappedBooleanKey RULE_DO_IMMEDIATE_RESPAWN = WrappedBooleanKey.wrap(GameRules.IMMEDIATE_RESPAWN);
    public static final WrappedIntegerKey RULE_PLAYERS_NETHER_PORTAL_DEFAULT_DELAY = WrappedIntegerKey.wrap(GameRules.PLAYERS_NETHER_PORTAL_DEFAULT_DELAY);
    public static final WrappedIntegerKey RULE_PLAYERS_NETHER_PORTAL_CREATIVE_DELAY = WrappedIntegerKey.wrap(GameRules.PLAYERS_NETHER_PORTAL_CREATIVE_DELAY);
    public static final WrappedBooleanKey RULE_DROWNING_DAMAGE = WrappedBooleanKey.wrap(GameRules.DROWNING_DAMAGE);
    public static final WrappedBooleanKey RULE_FALL_DAMAGE = WrappedBooleanKey.wrap(GameRules.FALL_DAMAGE);
    public static final WrappedBooleanKey RULE_FIRE_DAMAGE = WrappedBooleanKey.wrap(GameRules.FIRE_DAMAGE);
    public static final WrappedBooleanKey RULE_FREEZE_DAMAGE = WrappedBooleanKey.wrap(GameRules.FREEZE_DAMAGE);
    public static final WrappedBooleanKey RULE_DO_PATROL_SPAWNING = WrappedBooleanKey.wrap(GameRules.SPAWN_PATROLS);
    public static final WrappedBooleanKey RULE_DO_TRADER_SPAWNING = WrappedBooleanKey.wrap(GameRules.SPAWN_WANDERING_TRADERS);
    public static final WrappedBooleanKey RULE_DO_WARDEN_SPAWNING = WrappedBooleanKey.wrap(GameRules.SPAWN_WARDENS);
    public static final WrappedBooleanKey RULE_FORGIVE_DEAD_PLAYERS = WrappedBooleanKey.wrap(GameRules.FORGIVE_DEAD_PLAYERS);
    public static final WrappedBooleanKey RULE_UNIVERSAL_ANGER = WrappedBooleanKey.wrap(GameRules.UNIVERSAL_ANGER);
    public static final WrappedIntegerKey RULE_PLAYERS_SLEEPING_PERCENTAGE = WrappedIntegerKey.wrap(GameRules.PLAYERS_SLEEPING_PERCENTAGE);
    public static final WrappedBooleanKey RULE_BLOCK_EXPLOSION_DROP_DECAY = WrappedBooleanKey.wrap(GameRules.BLOCK_EXPLOSION_DROP_DECAY);
    public static final WrappedBooleanKey RULE_MOB_EXPLOSION_DROP_DECAY = WrappedBooleanKey.wrap(GameRules.MOB_EXPLOSION_DROP_DECAY);
    public static final WrappedBooleanKey RULE_TNT_EXPLOSION_DROP_DECAY = WrappedBooleanKey.wrap(GameRules.TNT_EXPLOSION_DROP_DECAY);
    public static final WrappedIntegerKey RULE_SNOW_ACCUMULATION_HEIGHT = WrappedIntegerKey.wrap(GameRules.MAX_SNOW_ACCUMULATION_HEIGHT);
    public static final WrappedBooleanKey RULE_WATER_SOURCE_CONVERSION = WrappedBooleanKey.wrap(GameRules.WATER_SOURCE_CONVERSION);
    public static final WrappedBooleanKey RULE_LAVA_SOURCE_CONVERSION = WrappedBooleanKey.wrap(GameRules.LAVA_SOURCE_CONVERSION);
    public static final WrappedBooleanKey RULE_GLOBAL_SOUND_EVENTS = WrappedBooleanKey.wrap(GameRules.GLOBAL_SOUND_EVENTS);
    public static final WrappedBooleanKey RULE_DO_VINES_SPREAD = WrappedBooleanKey.wrap(GameRules.SPREAD_VINES);
    public static final WrappedBooleanKey RULE_ENDER_PEARLS_VANISH_ON_DEATH = WrappedBooleanKey.wrap(GameRules.ENDER_PEARLS_VANISH_ON_DEATH);
    public static final WrappedIntegerKey RULE_MINECART_MAX_SPEED = WrappedIntegerKey.wrap(GameRules.MAX_MINECART_SPEED);
    public static final WrappedIntegerKey RULE_SPAWN_CHUNK_RADIUS = WrappedIntegerKey.wrap(null);
    public static final WrappedBooleanKey RULE_TNT_EXPLODES = WrappedBooleanKey.wrap(GameRules.TNT_EXPLODES);

    public boolean getBoolean(WrappedBooleanKey key) {
        if (key.toMojang() == null) {
            return false;
        }

        return internal.get(key.toMojang());
    }

    public int getInt(WrappedIntegerKey key) {
        if (key.toMojang() == null) {
            return 0;
        }
        return internal.get(key.toMojang());
    }

    public GameRules toMojang() {
        return internal;
    }

    @RequiredArgsConstructor(staticName = "wrap")
    public static final class WrappedBooleanKey {
        private final GameRule<@NotNull Boolean> internal;

        public GameRule<@NotNull Boolean> toMojang() {
            return internal;
        }
    }

    @RequiredArgsConstructor(staticName = "wrap")
    public static final class WrappedIntegerKey {
        private final GameRule<@NotNull Integer> internal;

        public GameRule<@NotNull Integer> toMojang() {
            return internal;
        }
    }
}
