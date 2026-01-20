package com.hypherionmc.craterlib.nojang.client.multiplayer;

import com.hypherionmc.craterlib.nojang.core.BridgedBlockPos;
import com.hypherionmc.craterlib.nojang.resources.ResourceIdentifier;
import lombok.RequiredArgsConstructor;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.Nullable;

// TODO: Implement if Possible
@RequiredArgsConstructor(staticName = "of")
public class BridgedClientLevel {

    //private final ClientLevel internal;

    public boolean isClientSide() {
        return false;
    }

    public long getGameTime() {
        return 0;
    }

    public long getDayTime() {
        return 0;
    }

    public long dayTime() {
        return 0;
    }

    public boolean isRaining() {
        return false;
    }

    public boolean isThundering() {
        return false;
    }

    @Nullable
    public ResourceIdentifier getDimensionKey() {
        return null;
    }

    @Nullable
    public ResourceIdentifier getBiomeIdentifier(BridgedBlockPos onPos) {
        return null;
    }

    @Nullable
    public Component getDifficulty() {
        return Component.text("Not Implemented");
    }

}
