package com.hypherionmc.craterlib.nojang.client.multiplayer;

import com.hypherionmc.craterlib.nojang.core.BridgedBlockPos;
import com.hypherionmc.craterlib.nojang.resources.ResourceIdentifier;
import com.hypherionmc.craterlib.utils.ChatUtils;
import lombok.RequiredArgsConstructor;
import net.kyori.adventure.text.Component;
import net.minecraft.client.multiplayer.ClientLevel;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.atomic.AtomicReference;

@RequiredArgsConstructor(staticName = "of")
public class BridgedClientLevel {

    private final ClientLevel internal;

    public boolean isClientSide() {
        return internal.isClientSide();
    }

    public long getGameTime() {
        return internal.getGameTime();
    }

    public long getDayTime() {
        return internal.getDayTime();
    }

    public long dayTime() {
        return internal.dayTime();
    }

    public boolean isRaining() {
        return internal.isRaining();
    }

    public boolean isThundering() {
        return internal.isThundering();
    }

    @Nullable
    public ResourceIdentifier getDimensionKey() {
        return ResourceIdentifier.fromMojang(internal.dimension().location());
    }

    @Nullable
    public ResourceIdentifier getBiomeIdentifier(BridgedBlockPos onPos) {
        AtomicReference<ResourceIdentifier> identifier = new AtomicReference<>(null);
        internal.getBiome(onPos.toMojang()).unwrap().ifLeft(b -> identifier.set(ResourceIdentifier.fromMojang(b.location())));
        return identifier.get();
    }

    @Nullable
    public Component getDifficulty() {
        return ChatUtils.mojangToAdventure(internal.getDifficulty().getDisplayName());
    }

}
