package com.hypherionmc.craterlib.api.game.world.level;

import lombok.RequiredArgsConstructor;

public final class CraterCommonGameRules {

    public static final GameRuleKey<Boolean> RULE_DOFIRETICK = new GameRuleKey<>();
    public static final GameRuleKey<Boolean> RULE_ALLOWFIRETICKAWAYFROMPLAYERS = new GameRuleKey<>();
    public static final GameRuleKey<Boolean> RULE_MOBGRIEFING = new GameRuleKey<>();
    public static final GameRuleKey<Boolean> RULE_KEEPINVENTORY = new GameRuleKey<>();
    public static final GameRuleKey<Boolean> RULE_DOMOBSPAWNING = new GameRuleKey<>();
    public static final GameRuleKey<Boolean> RULE_DOMOBLOOT = new GameRuleKey<>();
    public static final GameRuleKey<Boolean> RULE_PROJECTILESCANBREAKBLOCKS = new GameRuleKey<>();
    public static final GameRuleKey<Boolean> RULE_DOBLOCKDROPS = new GameRuleKey<>();
    public static final GameRuleKey<Boolean> RULE_DOENTITYDROPS = new GameRuleKey<>();
    public static final GameRuleKey<Boolean> RULE_COMMANDBLOCKOUTPUT = new GameRuleKey<>();
    public static final GameRuleKey<Boolean> RULE_NATURAL_REGENERATION = new GameRuleKey<>();
    public static final GameRuleKey<Boolean> RULE_DAYLIGHT = new GameRuleKey<>();
    public static final GameRuleKey<Boolean> RULE_LOGADMINCOMMANDS = new GameRuleKey<>();
    public static final GameRuleKey<Boolean> RULE_SHOWDEATHMESSAGES = new GameRuleKey<>();
    public static final GameRuleKey<Integer> RULE_RANDOMTICKING = new GameRuleKey<>();
    public static final GameRuleKey<Boolean> RULE_SENDCOMMANDFEEDBACK = new GameRuleKey<>();
    public static final GameRuleKey<Boolean> RULE_REDUCEDDEBUGINFO = new GameRuleKey<>();
    public static final GameRuleKey<Boolean> RULE_SPECTATORSGENERATECHUNKS = new GameRuleKey<>();
    public static final GameRuleKey<Integer> RULE_SPAWN_RADIUS = new GameRuleKey<>();
    public static final GameRuleKey<Boolean> RULE_DISABLE_PLAYER_MOVEMENT_CHECK = new GameRuleKey<>();
    public static final GameRuleKey<Boolean> RULE_DISABLE_ELYTRA_MOVEMENT_CHECK = new GameRuleKey<>();
    public static final GameRuleKey<Integer> RULE_MAX_ENTITY_CRAMMING = new GameRuleKey<>();
    public static final GameRuleKey<Boolean> RULE_WEATHER_CYCLE = new GameRuleKey<>();
    public static final GameRuleKey<Boolean> RULE_LIMITED_CRAFTING = new GameRuleKey<>();
    public static final GameRuleKey<Integer> RULE_MAX_COMMAND_CHAIN_LENGTH = new GameRuleKey<>();
    public static final GameRuleKey<Integer> RULE_MAX_COMMAND_FORK_COUNT = new GameRuleKey<>();
    public static final GameRuleKey<Integer> RULE_COMMAND_MODIFICATION_BLOCK_LIMIT = new GameRuleKey<>();
    public static final GameRuleKey<Boolean> RULE_ANNOUNCE_ADVANCEMENTS = new GameRuleKey<>();
    public static final GameRuleKey<Boolean> RULE_DISABLE_RAIDS = new GameRuleKey<>();
    public static final GameRuleKey<Boolean> RULE_DOINSOMNIA = new GameRuleKey<>();
    public static final GameRuleKey<Boolean> RULE_DO_IMMEDIATE_RESPAWN = new GameRuleKey<>();
    public static final GameRuleKey<Integer> RULE_PLAYERS_NETHER_PORTAL_DEFAULT_DELAY = new GameRuleKey<>();
    public static final GameRuleKey<Integer> RULE_PLAYERS_NETHER_PORTAL_CREATIVE_DELAY = new GameRuleKey<>();
    public static final GameRuleKey<Boolean> RULE_DROWNING_DAMAGE = new GameRuleKey<>();
    public static final GameRuleKey<Boolean> RULE_FALL_DAMAGE = new GameRuleKey<>();
    public static final GameRuleKey<Boolean> RULE_FIRE_DAMAGE = new GameRuleKey<>();
    public static final GameRuleKey<Boolean> RULE_FREEZE_DAMAGE = new GameRuleKey<>();
    public static final GameRuleKey<Boolean> RULE_DO_PATROL_SPAWNING = new GameRuleKey<>();
    public static final GameRuleKey<Boolean> RULE_DO_TRADER_SPAWNING = new GameRuleKey<>();
    public static final GameRuleKey<Boolean> RULE_DO_WARDEN_SPAWNING = new GameRuleKey<>();
    public static final GameRuleKey<Boolean> RULE_FORGIVE_DEAD_PLAYERS = new GameRuleKey<>();
    public static final GameRuleKey<Boolean> RULE_UNIVERSAL_ANGER = new GameRuleKey<>();
    public static final GameRuleKey<Integer> RULE_PLAYERS_SLEEPING_PERCENTAGE = new GameRuleKey<>();
    public static final GameRuleKey<Boolean> RULE_BLOCK_EXPLOSION_DROP_DECAY = new GameRuleKey<>();
    public static final GameRuleKey<Boolean> RULE_MOB_EXPLOSION_DROP_DECAY = new GameRuleKey<>();
    public static final GameRuleKey<Boolean> RULE_TNT_EXPLOSION_DROP_DECAY = new GameRuleKey<>();
    public static final GameRuleKey<Integer> RULE_SNOW_ACCUMULATION_HEIGHT = new GameRuleKey<>();
    public static final GameRuleKey<Boolean> RULE_WATER_SOURCE_CONVERSION = new GameRuleKey<>();
    public static final GameRuleKey<Boolean> RULE_LAVA_SOURCE_CONVERSION = new GameRuleKey<>();
    public static final GameRuleKey<Boolean> RULE_GLOBAL_SOUND_EVENTS = new GameRuleKey<>();
    public static final GameRuleKey<Boolean> RULE_DO_VINES_SPREAD = new GameRuleKey<>();
    public static final GameRuleKey<Boolean> RULE_ENDER_PEARLS_VANISH_ON_DEATH = new GameRuleKey<>();
    public static final GameRuleKey<Integer> RULE_MINECART_MAX_SPEED = new GameRuleKey<>();
    public static final GameRuleKey<Integer> RULE_SPAWN_CHUNK_RADIUS = new GameRuleKey<>();
    public static final GameRuleKey<Boolean> RULE_TNT_EXPLODES = new GameRuleKey<>();
    
    @RequiredArgsConstructor
    public static final class GameRuleKey<T> {}
}
