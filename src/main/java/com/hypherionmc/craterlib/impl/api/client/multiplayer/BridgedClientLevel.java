package com.hypherionmc.craterlib.impl.api.client.multiplayer;

import com.hypherionmc.craterlib.api.game.client.multiplayer.CraterClientLevel;
import com.hypherionmc.craterlib.api.game.core.CraterBlockPos;
import com.hypherionmc.craterlib.api.game.text.Text;
import com.hypherionmc.craterlib.impl.api.resources.ResourceIdentifier;
import lombok.RequiredArgsConstructor;
import net.minecraft.client.multiplayer.ClientLevel;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.atomic.AtomicReference;

@RequiredArgsConstructor(staticName = "wrap")
public class BridgedClientLevel implements CraterClientLevel {

    private final ClientLevel internal;

    @Override
    public boolean isClientSide() {
        return internal.isClientSide();
    }

    @Override
    public long getGameTime() {
        return internal.getGameTime();
    }

    @Override
    public long getDayTime() {
        return internal.getDayTime();
    }

    @Override
    public long dayTime() {
        return internal.getDayTime();
    }

    @Override
    public boolean isRaining() {
        return internal.isRaining();
    }

    @Override
    public boolean isThundering() {
        return internal.isThundering();
    }

    @Override
    @Nullable
    public ResourceIdentifier getDimensionKey() {
        return ResourceIdentifier.fromMojang(internal.dimension().identifier());
    }

    @Override
    @Nullable
    public ResourceIdentifier getBiomeIdentifier(CraterBlockPos onPos) {
        AtomicReference<ResourceIdentifier> identifier = new AtomicReference<>(null);
        internal.getBiome(onPos.unwrap()).unwrap().ifLeft(b -> identifier.set(ResourceIdentifier.fromMojang(b.identifier())));
        return identifier.get();
    }

    @Override
    @Nullable
    public Text getDifficulty() {
        return Text.fromGame(internal.getDifficulty().getDisplayName());
    }

    @Override
    public ClientLevel unwrapInternal() {
        return internal;
    }
}
