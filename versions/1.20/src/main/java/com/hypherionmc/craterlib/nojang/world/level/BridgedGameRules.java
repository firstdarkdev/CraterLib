package com.hypherionmc.craterlib.nojang.world.level;

import lombok.RequiredArgsConstructor;
import net.minecraft.world.level.GameRules;

@RequiredArgsConstructor(staticName = "bridge")
public class BridgedGameRules {

    private final GameRules internal;

    // Wrapped Mojang Rules for convenience
    public static final WrappedBooleanKey RULE_DOFIRETICK = WrappedBooleanKey.wrap(GameRules.RULE_DOFIRETICK);
    public static final WrappedBooleanKey RULE_ALLOWFIRETICKAWAYFROMPLAYERS = WrappedBooleanKey.wrap(null);
    public static final WrappedBooleanKey RULE_MOBGRIEFING = WrappedBooleanKey.wrap(GameRules.RULE_MOBGRIEFING);
    public static final WrappedBooleanKey RULE_KEEPINVENTORY = WrappedBooleanKey.wrap(GameRules.RULE_KEEPINVENTORY);
    public static final WrappedBooleanKey RULE_DOMOBSPAWNING = WrappedBooleanKey.wrap(GameRules.RULE_DOMOBSPAWNING);
    public static final WrappedBooleanKey RULE_DOMOBLOOT = WrappedBooleanKey.wrap(GameRules.RULE_DOMOBLOOT);
    public static final WrappedBooleanKey RULE_PROJECTILESCANBREAKBLOCKS = WrappedBooleanKey.wrap(null);
    public static final WrappedBooleanKey RULE_DOBLOCKDROPS = WrappedBooleanKey.wrap(GameRules.RULE_DOBLOCKDROPS);
    public static final WrappedBooleanKey RULE_DOENTITYDROPS = WrappedBooleanKey.wrap(GameRules.RULE_DOENTITYDROPS);
    public static final WrappedBooleanKey RULE_COMMANDBLOCKOUTPUT = WrappedBooleanKey.wrap(GameRules.RULE_COMMANDBLOCKOUTPUT);
    public static final WrappedBooleanKey RULE_NATURAL_REGENERATION = WrappedBooleanKey.wrap(GameRules.RULE_NATURAL_REGENERATION);
    public static final WrappedBooleanKey RULE_DAYLIGHT = WrappedBooleanKey.wrap(GameRules.RULE_DAYLIGHT);
    public static final WrappedBooleanKey RULE_LOGADMINCOMMANDS = WrappedBooleanKey.wrap(GameRules.RULE_LOGADMINCOMMANDS);
    public static final WrappedBooleanKey RULE_SHOWDEATHMESSAGES = WrappedBooleanKey.wrap(GameRules.RULE_SHOWDEATHMESSAGES);
    public static final WrappedIntegerKey RULE_RANDOMTICKING = WrappedIntegerKey.wrap(GameRules.RULE_RANDOMTICKING);
    public static final WrappedBooleanKey RULE_SENDCOMMANDFEEDBACK = WrappedBooleanKey.wrap(GameRules.RULE_SENDCOMMANDFEEDBACK);
    public static final WrappedBooleanKey RULE_REDUCEDDEBUGINFO = WrappedBooleanKey.wrap(GameRules.RULE_REDUCEDDEBUGINFO);
    public static final WrappedBooleanKey RULE_SPECTATORSGENERATECHUNKS = WrappedBooleanKey.wrap(GameRules.RULE_SPECTATORSGENERATECHUNKS);
    public static final WrappedIntegerKey RULE_SPAWN_RADIUS = WrappedIntegerKey.wrap(GameRules.RULE_SPAWN_RADIUS);
    public static final WrappedBooleanKey RULE_DISABLE_PLAYER_MOVEMENT_CHECK = WrappedBooleanKey.wrap(null);
    public static final WrappedBooleanKey RULE_DISABLE_ELYTRA_MOVEMENT_CHECK = WrappedBooleanKey.wrap(GameRules.RULE_DISABLE_ELYTRA_MOVEMENT_CHECK);
    public static final WrappedIntegerKey RULE_MAX_ENTITY_CRAMMING = WrappedIntegerKey.wrap(GameRules.RULE_MAX_ENTITY_CRAMMING);
    public static final WrappedBooleanKey RULE_WEATHER_CYCLE = WrappedBooleanKey.wrap(GameRules.RULE_WEATHER_CYCLE);
    public static final WrappedBooleanKey RULE_LIMITED_CRAFTING = WrappedBooleanKey.wrap(GameRules.RULE_LIMITED_CRAFTING);
    public static final WrappedIntegerKey RULE_MAX_COMMAND_CHAIN_LENGTH = WrappedIntegerKey.wrap(GameRules.RULE_MAX_COMMAND_CHAIN_LENGTH);
    public static final WrappedIntegerKey RULE_MAX_COMMAND_FORK_COUNT = WrappedIntegerKey.wrap(null);
    public static final WrappedIntegerKey RULE_COMMAND_MODIFICATION_BLOCK_LIMIT = WrappedIntegerKey.wrap(GameRules.RULE_COMMAND_MODIFICATION_BLOCK_LIMIT);
    public static final WrappedBooleanKey RULE_ANNOUNCE_ADVANCEMENTS = WrappedBooleanKey.wrap(GameRules.RULE_ANNOUNCE_ADVANCEMENTS);
    public static final WrappedBooleanKey RULE_DISABLE_RAIDS = WrappedBooleanKey.wrap(GameRules.RULE_DISABLE_RAIDS);
    public static final WrappedBooleanKey RULE_DOINSOMNIA = WrappedBooleanKey.wrap(GameRules.RULE_DOINSOMNIA);
    public static final WrappedBooleanKey RULE_DO_IMMEDIATE_RESPAWN = WrappedBooleanKey.wrap(GameRules.RULE_DO_IMMEDIATE_RESPAWN);
    public static final WrappedIntegerKey RULE_PLAYERS_NETHER_PORTAL_DEFAULT_DELAY = WrappedIntegerKey.wrap(null);
    public static final WrappedIntegerKey RULE_PLAYERS_NETHER_PORTAL_CREATIVE_DELAY = WrappedIntegerKey.wrap(null);
    public static final WrappedBooleanKey RULE_DROWNING_DAMAGE = WrappedBooleanKey.wrap(GameRules.RULE_DROWNING_DAMAGE);
    public static final WrappedBooleanKey RULE_FALL_DAMAGE = WrappedBooleanKey.wrap(GameRules.RULE_FALL_DAMAGE);
    public static final WrappedBooleanKey RULE_FIRE_DAMAGE = WrappedBooleanKey.wrap(GameRules.RULE_FIRE_DAMAGE);
    public static final WrappedBooleanKey RULE_FREEZE_DAMAGE = WrappedBooleanKey.wrap(GameRules.RULE_FREEZE_DAMAGE);
    public static final WrappedBooleanKey RULE_DO_PATROL_SPAWNING = WrappedBooleanKey.wrap(GameRules.RULE_DO_PATROL_SPAWNING);
    public static final WrappedBooleanKey RULE_DO_TRADER_SPAWNING = WrappedBooleanKey.wrap(GameRules.RULE_DO_TRADER_SPAWNING);
    public static final WrappedBooleanKey RULE_DO_WARDEN_SPAWNING = WrappedBooleanKey.wrap(GameRules.RULE_DO_WARDEN_SPAWNING);
    public static final WrappedBooleanKey RULE_FORGIVE_DEAD_PLAYERS = WrappedBooleanKey.wrap(GameRules.RULE_FORGIVE_DEAD_PLAYERS);
    public static final WrappedBooleanKey RULE_UNIVERSAL_ANGER = WrappedBooleanKey.wrap(GameRules.RULE_UNIVERSAL_ANGER);
    public static final WrappedIntegerKey RULE_PLAYERS_SLEEPING_PERCENTAGE = WrappedIntegerKey.wrap(GameRules.RULE_PLAYERS_SLEEPING_PERCENTAGE);
    public static final WrappedBooleanKey RULE_BLOCK_EXPLOSION_DROP_DECAY = WrappedBooleanKey.wrap(GameRules.RULE_BLOCK_EXPLOSION_DROP_DECAY);
    public static final WrappedBooleanKey RULE_MOB_EXPLOSION_DROP_DECAY = WrappedBooleanKey.wrap(GameRules.RULE_MOB_EXPLOSION_DROP_DECAY);
    public static final WrappedBooleanKey RULE_TNT_EXPLOSION_DROP_DECAY = WrappedBooleanKey.wrap(GameRules.RULE_TNT_EXPLOSION_DROP_DECAY);
    public static final WrappedIntegerKey RULE_SNOW_ACCUMULATION_HEIGHT = WrappedIntegerKey.wrap(GameRules.RULE_SNOW_ACCUMULATION_HEIGHT);
    public static final WrappedBooleanKey RULE_WATER_SOURCE_CONVERSION = WrappedBooleanKey.wrap(GameRules.RULE_WATER_SOURCE_CONVERSION);
    public static final WrappedBooleanKey RULE_LAVA_SOURCE_CONVERSION = WrappedBooleanKey.wrap(GameRules.RULE_LAVA_SOURCE_CONVERSION);
    public static final WrappedBooleanKey RULE_GLOBAL_SOUND_EVENTS = WrappedBooleanKey.wrap(GameRules.RULE_GLOBAL_SOUND_EVENTS);
    public static final WrappedBooleanKey RULE_DO_VINES_SPREAD = WrappedBooleanKey.wrap(GameRules.RULE_DO_VINES_SPREAD);
    public static final WrappedBooleanKey RULE_ENDER_PEARLS_VANISH_ON_DEATH = WrappedBooleanKey.wrap(null);
    public static final WrappedIntegerKey RULE_MINECART_MAX_SPEED = WrappedIntegerKey.wrap(null);
    public static final WrappedIntegerKey RULE_SPAWN_CHUNK_RADIUS = WrappedIntegerKey.wrap(null);
    public static final WrappedBooleanKey RULE_TNT_EXPLODES = WrappedBooleanKey.wrap(null);

    public boolean getBoolean(WrappedBooleanKey key) {
        if (key.toMojang() == null)
            return false;

        return internal.getBoolean(key.toMojang());
    }

    public int getInt(WrappedIntegerKey key) {
        if (key.toMojang() == null)
            return 0;

        return internal.getInt(key.toMojang());
    }

    public GameRules toMojang() {
        return internal;
    }

    @RequiredArgsConstructor(staticName = "wrap")
    public static final class WrappedBooleanKey {
        private final GameRules.Key<GameRules.BooleanValue> internal;

        public GameRules.Key<GameRules.BooleanValue> toMojang() {
            return internal;
        }
    }

    @RequiredArgsConstructor(staticName = "wrap")
    public static final class WrappedIntegerKey {
        private final GameRules.Key<GameRules.IntegerValue> internal;

        public GameRules.Key<GameRules.IntegerValue> toMojang() {
            return internal;
        }
    }
}
