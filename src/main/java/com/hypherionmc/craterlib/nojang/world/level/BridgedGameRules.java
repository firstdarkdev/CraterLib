package com.hypherionmc.craterlib.nojang.world.level;

import lombok.RequiredArgsConstructor;

// TODO: Implement if possible
@RequiredArgsConstructor(staticName = "bridge")
public class BridgedGameRules {

    //private final GameRules internal;

    // Wrapped Mojang Rules for convenience
    public static final WrappedBooleanKey RULE_DOFIRETICK = WrappedBooleanKey.wrap(null);
    public static final WrappedBooleanKey RULE_ALLOWFIRETICKAWAYFROMPLAYERS = WrappedBooleanKey.wrap(null);
    public static final WrappedBooleanKey RULE_MOBGRIEFING = WrappedBooleanKey.wrap(null);
    public static final WrappedBooleanKey RULE_KEEPINVENTORY = WrappedBooleanKey.wrap(null);
    public static final WrappedBooleanKey RULE_DOMOBSPAWNING = WrappedBooleanKey.wrap(null);
    public static final WrappedBooleanKey RULE_DOMOBLOOT = WrappedBooleanKey.wrap(null);
    public static final WrappedBooleanKey RULE_PROJECTILESCANBREAKBLOCKS = WrappedBooleanKey.wrap(null);
    public static final WrappedBooleanKey RULE_DOBLOCKDROPS = WrappedBooleanKey.wrap(null);
    public static final WrappedBooleanKey RULE_DOENTITYDROPS = WrappedBooleanKey.wrap(null);
    public static final WrappedBooleanKey RULE_COMMANDBLOCKOUTPUT = WrappedBooleanKey.wrap(null);
    public static final WrappedBooleanKey RULE_NATURAL_REGENERATION = WrappedBooleanKey.wrap(null);
    public static final WrappedBooleanKey RULE_DAYLIGHT = WrappedBooleanKey.wrap(null);
    public static final WrappedBooleanKey RULE_LOGADMINCOMMANDS = WrappedBooleanKey.wrap(null);
    public static final WrappedBooleanKey RULE_SHOWDEATHMESSAGES = WrappedBooleanKey.wrap(null);
    public static final WrappedIntegerKey RULE_RANDOMTICKING = WrappedIntegerKey.wrap(null);
    public static final WrappedBooleanKey RULE_SENDCOMMANDFEEDBACK = WrappedBooleanKey.wrap(null);
    public static final WrappedBooleanKey RULE_REDUCEDDEBUGINFO = WrappedBooleanKey.wrap(null);
    public static final WrappedBooleanKey RULE_SPECTATORSGENERATECHUNKS = WrappedBooleanKey.wrap(null);
    public static final WrappedIntegerKey RULE_SPAWN_RADIUS = WrappedIntegerKey.wrap(null);
    public static final WrappedBooleanKey RULE_DISABLE_PLAYER_MOVEMENT_CHECK = WrappedBooleanKey.wrap(null);
    public static final WrappedBooleanKey RULE_DISABLE_ELYTRA_MOVEMENT_CHECK = WrappedBooleanKey.wrap(null);
    public static final WrappedIntegerKey RULE_MAX_ENTITY_CRAMMING = WrappedIntegerKey.wrap(null);
    public static final WrappedBooleanKey RULE_WEATHER_CYCLE = WrappedBooleanKey.wrap(null);
    public static final WrappedBooleanKey RULE_LIMITED_CRAFTING = WrappedBooleanKey.wrap(null);
    public static final WrappedIntegerKey RULE_MAX_COMMAND_CHAIN_LENGTH = WrappedIntegerKey.wrap(null);
    public static final WrappedIntegerKey RULE_MAX_COMMAND_FORK_COUNT = WrappedIntegerKey.wrap(null);
    public static final WrappedIntegerKey RULE_COMMAND_MODIFICATION_BLOCK_LIMIT = WrappedIntegerKey.wrap(null);
    public static final WrappedBooleanKey RULE_ANNOUNCE_ADVANCEMENTS = WrappedBooleanKey.wrap(null);
    public static final WrappedBooleanKey RULE_DISABLE_RAIDS = WrappedBooleanKey.wrap(null);
    public static final WrappedBooleanKey RULE_DOINSOMNIA = WrappedBooleanKey.wrap(null);
    public static final WrappedBooleanKey RULE_DO_IMMEDIATE_RESPAWN = WrappedBooleanKey.wrap(null);
    public static final WrappedIntegerKey RULE_PLAYERS_NETHER_PORTAL_DEFAULT_DELAY = WrappedIntegerKey.wrap(null);
    public static final WrappedIntegerKey RULE_PLAYERS_NETHER_PORTAL_CREATIVE_DELAY = WrappedIntegerKey.wrap(null);
    public static final WrappedBooleanKey RULE_DROWNING_DAMAGE = WrappedBooleanKey.wrap(null);
    public static final WrappedBooleanKey RULE_FALL_DAMAGE = WrappedBooleanKey.wrap(null);
    public static final WrappedBooleanKey RULE_FIRE_DAMAGE = WrappedBooleanKey.wrap(null);
    public static final WrappedBooleanKey RULE_FREEZE_DAMAGE = WrappedBooleanKey.wrap(null);
    public static final WrappedBooleanKey RULE_DO_PATROL_SPAWNING = WrappedBooleanKey.wrap(null);
    public static final WrappedBooleanKey RULE_DO_TRADER_SPAWNING = WrappedBooleanKey.wrap(null);
    public static final WrappedBooleanKey RULE_DO_WARDEN_SPAWNING = WrappedBooleanKey.wrap(null);
    public static final WrappedBooleanKey RULE_FORGIVE_DEAD_PLAYERS = WrappedBooleanKey.wrap(null);
    public static final WrappedBooleanKey RULE_UNIVERSAL_ANGER = WrappedBooleanKey.wrap(null);
    public static final WrappedIntegerKey RULE_PLAYERS_SLEEPING_PERCENTAGE = WrappedIntegerKey.wrap(null);
    public static final WrappedBooleanKey RULE_BLOCK_EXPLOSION_DROP_DECAY = WrappedBooleanKey.wrap(null);
    public static final WrappedBooleanKey RULE_MOB_EXPLOSION_DROP_DECAY = WrappedBooleanKey.wrap(null);
    public static final WrappedBooleanKey RULE_TNT_EXPLOSION_DROP_DECAY = WrappedBooleanKey.wrap(null);
    public static final WrappedIntegerKey RULE_SNOW_ACCUMULATION_HEIGHT = WrappedIntegerKey.wrap(null);
    public static final WrappedBooleanKey RULE_WATER_SOURCE_CONVERSION = WrappedBooleanKey.wrap(null);
    public static final WrappedBooleanKey RULE_LAVA_SOURCE_CONVERSION = WrappedBooleanKey.wrap(null);
    public static final WrappedBooleanKey RULE_GLOBAL_SOUND_EVENTS = WrappedBooleanKey.wrap(null);
    public static final WrappedBooleanKey RULE_DO_VINES_SPREAD = WrappedBooleanKey.wrap(null);
    public static final WrappedBooleanKey RULE_ENDER_PEARLS_VANISH_ON_DEATH = WrappedBooleanKey.wrap(null);
    public static final WrappedIntegerKey RULE_MINECART_MAX_SPEED = WrappedIntegerKey.wrap(null);
    public static final WrappedIntegerKey RULE_SPAWN_CHUNK_RADIUS = WrappedIntegerKey.wrap(null);
    public static final WrappedBooleanKey RULE_TNT_EXPLODES = WrappedBooleanKey.wrap(null);

    public boolean getBoolean(WrappedBooleanKey key) {
       return false;
    }

    public int getInt(WrappedIntegerKey key) {
        return 0;
    }

    @RequiredArgsConstructor(staticName = "wrap")
    public static final class WrappedBooleanKey {
        private final Object internal;
    }

    @RequiredArgsConstructor(staticName = "wrap")
    public static final class WrappedIntegerKey {
        private final Object internal;
    }
}
