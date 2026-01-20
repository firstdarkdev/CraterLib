package com.hypherionmc.craterlib.hytale;

import com.hypherionmc.craterlib.api.events.common.CraterPlayerDeathEvent;
import com.hypherionmc.craterlib.core.event.CraterEventBus;
import com.hypherionmc.craterlib.nojang.world.entity.player.BridgedPlayer;
import com.hypixel.hytale.component.CommandBuffer;
import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.component.query.Query;
import com.hypixel.hytale.server.core.entity.entities.Player;
import com.hypixel.hytale.server.core.modules.entity.damage.Damage;
import com.hypixel.hytale.server.core.modules.entity.damage.DeathComponent;
import com.hypixel.hytale.server.core.modules.entity.damage.DeathSystems;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

public class HytaleDeathSystem extends DeathSystems.OnDeathSystem {

    @Override
    public void onComponentAdded(@NonNull Ref<EntityStore> ref, @NonNull DeathComponent deathComponent, @NonNull Store<EntityStore> store, @NonNull CommandBuffer<EntityStore> commandBuffer) {
        Player playerComponent = (Player) store.getComponent(ref, Player.getComponentType());
        if (playerComponent == null) return;

        Damage deathInfo = deathComponent.getDeathInfo();
        if (deathInfo == null) return;

        try {
            CraterEventBus.INSTANCE.postEvent(new CraterPlayerDeathEvent(BridgedPlayer.of(commandBuffer.getComponent(ref, PlayerRef.getComponentType())), deathInfo.getDeathMessage(ref, commandBuffer)));
        } catch (Exception ignored) {}
    }

    @Override
    public @Nullable Query<EntityStore> getQuery() {
        return Query.and(Player.getComponentType());
    }
}
