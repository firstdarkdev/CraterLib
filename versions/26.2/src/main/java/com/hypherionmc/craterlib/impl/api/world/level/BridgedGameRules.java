package com.hypherionmc.craterlib.impl.api.world.level;

import com.hypherionmc.craterlib.api.game.world.level.CraterCommonGameRules;
import com.hypherionmc.craterlib.api.game.world.level.CraterGameRules;
import lombok.RequiredArgsConstructor;
import net.minecraft.world.level.gamerules.GameRule;
import net.minecraft.world.level.gamerules.GameRules;

import java.util.HashMap;

@SuppressWarnings("unchecked")
@RequiredArgsConstructor(staticName = "bridge")
public class BridgedGameRules implements CraterGameRules {

    private final GameRules internal;
    private static final HashMap<CraterCommonGameRules.GameRuleKey<?>, GameRule<?>> KEYS = new HashMap<>();

    // Wrapped Mojang Rules for convenience
    static {
        register(CraterCommonGameRules.RULE_DOFIRETICK, null);
        register(CraterCommonGameRules.RULE_ALLOWFIRETICKAWAYFROMPLAYERS, null);
        register(CraterCommonGameRules.RULE_MOBGRIEFING, GameRules.MOB_GRIEFING);
        register(CraterCommonGameRules.RULE_KEEPINVENTORY, GameRules.KEEP_INVENTORY);
        register(CraterCommonGameRules.RULE_DOMOBSPAWNING, GameRules.SPAWN_MOBS);
        register(CraterCommonGameRules.RULE_DOMOBLOOT, null);
        register(CraterCommonGameRules.RULE_PROJECTILESCANBREAKBLOCKS, GameRules.PROJECTILES_CAN_BREAK_BLOCKS);
        register(CraterCommonGameRules.RULE_DOBLOCKDROPS, GameRules.BLOCK_DROPS);
        register(CraterCommonGameRules.RULE_DOENTITYDROPS, GameRules.ENTITY_DROPS);
        register(CraterCommonGameRules.RULE_COMMANDBLOCKOUTPUT, GameRules.COMMAND_BLOCK_OUTPUT);
        register(CraterCommonGameRules.RULE_NATURAL_REGENERATION, GameRules.NATURAL_HEALTH_REGENERATION);
        register(CraterCommonGameRules.RULE_DAYLIGHT, null);
        register(CraterCommonGameRules.RULE_LOGADMINCOMMANDS, GameRules.LOG_ADMIN_COMMANDS);
        register(CraterCommonGameRules.RULE_SHOWDEATHMESSAGES, GameRules.SHOW_DEATH_MESSAGES);
        register(CraterCommonGameRules.RULE_RANDOMTICKING, GameRules.RANDOM_TICK_SPEED);
        register(CraterCommonGameRules.RULE_SENDCOMMANDFEEDBACK, GameRules.SEND_COMMAND_FEEDBACK);
        register(CraterCommonGameRules.RULE_REDUCEDDEBUGINFO, GameRules.REDUCED_DEBUG_INFO);
        register(CraterCommonGameRules.RULE_SPECTATORSGENERATECHUNKS, null);
        register(CraterCommonGameRules.RULE_SPAWN_RADIUS, GameRules.RESPAWN_RADIUS);
        register(CraterCommonGameRules.RULE_DISABLE_PLAYER_MOVEMENT_CHECK, GameRules.PLAYER_MOVEMENT_CHECK);
        register(CraterCommonGameRules.RULE_DISABLE_ELYTRA_MOVEMENT_CHECK, GameRules.ELYTRA_MOVEMENT_CHECK);
        register(CraterCommonGameRules.RULE_MAX_ENTITY_CRAMMING, GameRules.MAX_ENTITY_CRAMMING);
        register(CraterCommonGameRules.RULE_WEATHER_CYCLE, GameRules.ADVANCE_WEATHER);
        register(CraterCommonGameRules.RULE_LIMITED_CRAFTING, GameRules.LIMITED_CRAFTING);
        register(CraterCommonGameRules.RULE_MAX_COMMAND_CHAIN_LENGTH, GameRules.MAX_COMMAND_SEQUENCE_LENGTH);
        register(CraterCommonGameRules.RULE_MAX_COMMAND_FORK_COUNT, GameRules.MAX_COMMAND_FORKS);
        register(CraterCommonGameRules.RULE_COMMAND_MODIFICATION_BLOCK_LIMIT, GameRules.MAX_BLOCK_MODIFICATIONS);
        register(CraterCommonGameRules.RULE_ANNOUNCE_ADVANCEMENTS, GameRules.SHOW_ADVANCEMENT_MESSAGES);
        register(CraterCommonGameRules.RULE_DISABLE_RAIDS, GameRules.RAIDS);
        register(CraterCommonGameRules.RULE_DOINSOMNIA, null);
        register(CraterCommonGameRules.RULE_DO_IMMEDIATE_RESPAWN, GameRules.IMMEDIATE_RESPAWN);
        register(CraterCommonGameRules.RULE_PLAYERS_NETHER_PORTAL_DEFAULT_DELAY, GameRules.PLAYERS_NETHER_PORTAL_DEFAULT_DELAY);
        register(CraterCommonGameRules.RULE_PLAYERS_NETHER_PORTAL_CREATIVE_DELAY, GameRules.PLAYERS_NETHER_PORTAL_CREATIVE_DELAY);
        register(CraterCommonGameRules.RULE_DROWNING_DAMAGE, GameRules.DROWNING_DAMAGE);
        register(CraterCommonGameRules.RULE_FALL_DAMAGE, GameRules.FALL_DAMAGE);
        register(CraterCommonGameRules.RULE_FIRE_DAMAGE, GameRules.FIRE_DAMAGE);
        register(CraterCommonGameRules.RULE_FREEZE_DAMAGE, GameRules.FREEZE_DAMAGE);
        register(CraterCommonGameRules.RULE_DO_PATROL_SPAWNING, GameRules.SPAWN_PATROLS);
        register(CraterCommonGameRules.RULE_DO_TRADER_SPAWNING, GameRules.SPAWN_WANDERING_TRADERS);
        register(CraterCommonGameRules.RULE_DO_WARDEN_SPAWNING, GameRules.SPAWN_WARDENS);
        register(CraterCommonGameRules.RULE_FORGIVE_DEAD_PLAYERS, GameRules.FORGIVE_DEAD_PLAYERS);
        register(CraterCommonGameRules.RULE_UNIVERSAL_ANGER, GameRules.UNIVERSAL_ANGER);
        register(CraterCommonGameRules.RULE_PLAYERS_SLEEPING_PERCENTAGE, GameRules.PLAYERS_SLEEPING_PERCENTAGE);
        register(CraterCommonGameRules.RULE_BLOCK_EXPLOSION_DROP_DECAY, GameRules.BLOCK_EXPLOSION_DROP_DECAY);
        register(CraterCommonGameRules.RULE_MOB_EXPLOSION_DROP_DECAY, GameRules.MOB_EXPLOSION_DROP_DECAY);
        register(CraterCommonGameRules.RULE_TNT_EXPLOSION_DROP_DECAY, GameRules.TNT_EXPLOSION_DROP_DECAY);
        register(CraterCommonGameRules.RULE_SNOW_ACCUMULATION_HEIGHT, GameRules.MAX_SNOW_ACCUMULATION_HEIGHT);
        register(CraterCommonGameRules.RULE_WATER_SOURCE_CONVERSION, GameRules.WATER_SOURCE_CONVERSION);
        register(CraterCommonGameRules.RULE_LAVA_SOURCE_CONVERSION, GameRules.LAVA_SOURCE_CONVERSION);
        register(CraterCommonGameRules.RULE_GLOBAL_SOUND_EVENTS, GameRules.GLOBAL_SOUND_EVENTS);
        register(CraterCommonGameRules.RULE_DO_VINES_SPREAD, GameRules.SPREAD_VINES);
        register(CraterCommonGameRules.RULE_ENDER_PEARLS_VANISH_ON_DEATH, GameRules.ENDER_PEARLS_VANISH_ON_DEATH);
        register(CraterCommonGameRules.RULE_MINECART_MAX_SPEED, GameRules.MAX_MINECART_SPEED);
        register(CraterCommonGameRules.RULE_SPAWN_CHUNK_RADIUS, null);
        register(CraterCommonGameRules.RULE_TNT_EXPLODES, GameRules.TNT_EXPLODES);
    }

    @Override
    public boolean getBoolean(CraterCommonGameRules.GameRuleKey<Boolean> key) {
        var kk = KEYS.get(key);

        if (kk == null)
            return false;

        return internal.get(((GameRule<Boolean>) kk));
    }

    @Override
    public int getInt(CraterCommonGameRules.GameRuleKey<Integer> key) {
        var kk = KEYS.get(key);

        if (kk == null)
            return 0;

        return internal.get(((GameRule<Integer>) kk));
    }

    private static void register(CraterCommonGameRules.GameRuleKey<?> key, GameRule<?> resolver) {
        KEYS.put(key, resolver);
    }
}
