package com.hypherionmc.craterlib.impl.api.world.level;

import com.hypherionmc.craterlib.api.game.world.level.CraterCommonGameRules;
import com.hypherionmc.craterlib.api.game.world.level.CraterGameRules;
import lombok.RequiredArgsConstructor;
import java.util.HashMap;
import net.minecraft.world.level.GameRules;

@SuppressWarnings("unchecked")
@RequiredArgsConstructor(staticName = "bridge")
public class BridgedGameRules implements CraterGameRules {

    private final GameRules internal;
    private static final HashMap<CraterCommonGameRules.GameRuleKey<?>, GameRules.Key<?>> KEYS = new HashMap<>();

    // Wrapped Mojang Rules for convenience
    static {
        register(CraterCommonGameRules.RULE_DOFIRETICK, GameRules.RULE_DOFIRETICK);
        register(CraterCommonGameRules.RULE_ALLOWFIRETICKAWAYFROMPLAYERS, GameRules.RULE_ALLOWFIRETICKAWAYFROMPLAYERS);
        register(CraterCommonGameRules.RULE_MOBGRIEFING, GameRules.RULE_MOBGRIEFING);
        register(CraterCommonGameRules.RULE_KEEPINVENTORY, GameRules.RULE_KEEPINVENTORY);
        register(CraterCommonGameRules.RULE_DOMOBSPAWNING, GameRules.RULE_DOMOBSPAWNING);
        register(CraterCommonGameRules.RULE_DOMOBLOOT, GameRules.RULE_DOMOBLOOT);
        register(CraterCommonGameRules.RULE_PROJECTILESCANBREAKBLOCKS, GameRules.RULE_PROJECTILESCANBREAKBLOCKS);
        register(CraterCommonGameRules.RULE_DOBLOCKDROPS, GameRules.RULE_DOBLOCKDROPS);
        register(CraterCommonGameRules.RULE_DOENTITYDROPS, GameRules.RULE_DOENTITYDROPS);
        register(CraterCommonGameRules.RULE_COMMANDBLOCKOUTPUT, GameRules.RULE_COMMANDBLOCKOUTPUT);
        register(CraterCommonGameRules.RULE_NATURAL_REGENERATION, GameRules.RULE_NATURAL_REGENERATION);
        register(CraterCommonGameRules.RULE_DAYLIGHT, GameRules.RULE_DAYLIGHT);
        register(CraterCommonGameRules.RULE_LOGADMINCOMMANDS, GameRules.RULE_LOGADMINCOMMANDS);
        register(CraterCommonGameRules.RULE_SHOWDEATHMESSAGES, GameRules.RULE_SHOWDEATHMESSAGES);
        register(CraterCommonGameRules.RULE_RANDOMTICKING, GameRules.RULE_RANDOMTICKING);
        register(CraterCommonGameRules.RULE_SENDCOMMANDFEEDBACK, GameRules.RULE_SENDCOMMANDFEEDBACK);
        register(CraterCommonGameRules.RULE_REDUCEDDEBUGINFO, GameRules.RULE_REDUCEDDEBUGINFO);
        register(CraterCommonGameRules.RULE_SPECTATORSGENERATECHUNKS, GameRules.RULE_SPECTATORSGENERATECHUNKS);
        register(CraterCommonGameRules.RULE_SPAWN_RADIUS, GameRules.RULE_SPAWN_RADIUS);
        register(CraterCommonGameRules.RULE_DISABLE_PLAYER_MOVEMENT_CHECK, GameRules.RULE_DISABLE_PLAYER_MOVEMENT_CHECK);
        register(CraterCommonGameRules.RULE_DISABLE_ELYTRA_MOVEMENT_CHECK, GameRules.RULE_DISABLE_ELYTRA_MOVEMENT_CHECK);
        register(CraterCommonGameRules.RULE_MAX_ENTITY_CRAMMING, GameRules.RULE_MAX_ENTITY_CRAMMING);
        register(CraterCommonGameRules.RULE_WEATHER_CYCLE, GameRules.RULE_WEATHER_CYCLE);
        register(CraterCommonGameRules.RULE_LIMITED_CRAFTING, GameRules.RULE_LIMITED_CRAFTING);
        register(CraterCommonGameRules.RULE_MAX_COMMAND_CHAIN_LENGTH, GameRules.RULE_MAX_COMMAND_CHAIN_LENGTH);
        register(CraterCommonGameRules.RULE_MAX_COMMAND_FORK_COUNT, GameRules.RULE_MAX_COMMAND_FORK_COUNT);
        register(CraterCommonGameRules.RULE_COMMAND_MODIFICATION_BLOCK_LIMIT, GameRules.RULE_COMMAND_MODIFICATION_BLOCK_LIMIT);
        register(CraterCommonGameRules.RULE_ANNOUNCE_ADVANCEMENTS, GameRules.RULE_ANNOUNCE_ADVANCEMENTS);
        register(CraterCommonGameRules.RULE_DISABLE_RAIDS, GameRules.RULE_DISABLE_RAIDS);
        register(CraterCommonGameRules.RULE_DOINSOMNIA, GameRules.RULE_DOINSOMNIA);
        register(CraterCommonGameRules.RULE_DO_IMMEDIATE_RESPAWN, GameRules.RULE_DO_IMMEDIATE_RESPAWN);
        register(CraterCommonGameRules.RULE_PLAYERS_NETHER_PORTAL_DEFAULT_DELAY, GameRules.RULE_PLAYERS_NETHER_PORTAL_DEFAULT_DELAY);
        register(CraterCommonGameRules.RULE_PLAYERS_NETHER_PORTAL_CREATIVE_DELAY, GameRules.RULE_PLAYERS_NETHER_PORTAL_CREATIVE_DELAY);
        register(CraterCommonGameRules.RULE_DROWNING_DAMAGE, GameRules.RULE_DROWNING_DAMAGE);
        register(CraterCommonGameRules.RULE_FALL_DAMAGE, GameRules.RULE_FALL_DAMAGE);
        register(CraterCommonGameRules.RULE_FIRE_DAMAGE, GameRules.RULE_FIRE_DAMAGE);
        register(CraterCommonGameRules.RULE_FREEZE_DAMAGE, GameRules.RULE_FREEZE_DAMAGE);
        register(CraterCommonGameRules.RULE_DO_PATROL_SPAWNING, GameRules.RULE_DO_PATROL_SPAWNING);
        register(CraterCommonGameRules.RULE_DO_TRADER_SPAWNING, GameRules.RULE_DO_TRADER_SPAWNING);
        register(CraterCommonGameRules.RULE_DO_WARDEN_SPAWNING, GameRules.RULE_DO_WARDEN_SPAWNING);
        register(CraterCommonGameRules.RULE_FORGIVE_DEAD_PLAYERS, GameRules.RULE_FORGIVE_DEAD_PLAYERS);
        register(CraterCommonGameRules.RULE_UNIVERSAL_ANGER, GameRules.RULE_UNIVERSAL_ANGER);
        register(CraterCommonGameRules.RULE_PLAYERS_SLEEPING_PERCENTAGE, GameRules.RULE_PLAYERS_SLEEPING_PERCENTAGE);
        register(CraterCommonGameRules.RULE_BLOCK_EXPLOSION_DROP_DECAY, GameRules.RULE_BLOCK_EXPLOSION_DROP_DECAY);
        register(CraterCommonGameRules.RULE_MOB_EXPLOSION_DROP_DECAY, GameRules.RULE_MOB_EXPLOSION_DROP_DECAY);
        register(CraterCommonGameRules.RULE_TNT_EXPLOSION_DROP_DECAY, GameRules.RULE_TNT_EXPLOSION_DROP_DECAY);
        register(CraterCommonGameRules.RULE_SNOW_ACCUMULATION_HEIGHT, GameRules.RULE_SNOW_ACCUMULATION_HEIGHT);
        register(CraterCommonGameRules.RULE_WATER_SOURCE_CONVERSION, GameRules.RULE_WATER_SOURCE_CONVERSION);
        register(CraterCommonGameRules.RULE_LAVA_SOURCE_CONVERSION, GameRules.RULE_LAVA_SOURCE_CONVERSION);
        register(CraterCommonGameRules.RULE_GLOBAL_SOUND_EVENTS, GameRules.RULE_GLOBAL_SOUND_EVENTS);
        register(CraterCommonGameRules.RULE_DO_VINES_SPREAD, GameRules.RULE_DO_VINES_SPREAD);
        register(CraterCommonGameRules.RULE_ENDER_PEARLS_VANISH_ON_DEATH, GameRules.RULE_ENDER_PEARLS_VANISH_ON_DEATH);
        register(CraterCommonGameRules.RULE_MINECART_MAX_SPEED, GameRules.RULE_MINECART_MAX_SPEED);
        register(CraterCommonGameRules.RULE_SPAWN_CHUNK_RADIUS, GameRules.RULE_SPAWN_CHUNK_RADIUS);
        register(CraterCommonGameRules.RULE_TNT_EXPLODES, GameRules.RULE_TNT_EXPLODES);
    }

    @Override
    public boolean getBoolean(CraterCommonGameRules.GameRuleKey<Boolean> key) {
        var kk = KEYS.get(key);

        if (kk == null)
            return false;

        return internal.getBoolean(((GameRules.Key<GameRules.BooleanValue>) kk));
    }

    @Override
    public int getInt(CraterCommonGameRules.GameRuleKey<Integer> key) {
        var kk = KEYS.get(key);

        if (kk == null)
            return 0;

        return internal.getInt(((GameRules.Key<GameRules.IntegerValue>) kk));
    }

    private static void register(CraterCommonGameRules.GameRuleKey<?> key, GameRules.Key<?> resolver) {
        KEYS.put(key, resolver);
    }
}
