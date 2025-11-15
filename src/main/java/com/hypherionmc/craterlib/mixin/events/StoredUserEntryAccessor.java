package com.hypherionmc.craterlib.mixin.events;

import net.minecraft.server.players.StoredUserEntry;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(StoredUserEntry.class)
public interface StoredUserEntryAccessor<T> {

    @Accessor("user")
    @Nullable T getUser();

}
