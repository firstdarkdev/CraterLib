package com.hypherionmc.craterlib.mixin.events;

import com.hypherionmc.craterlib.api.events.server.WhitelistChangedEvent;
import com.hypherionmc.craterlib.core.event.CraterEventBus;
import com.hypherionmc.craterlib.impl.api.authlib.BridgedGameProfile;
import com.mojang.authlib.GameProfile;
import net.minecraft.server.players.StoredUserEntry;
import net.minecraft.server.players.StoredUserList;
import net.minecraft.server.players.UserWhiteListEntry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@SuppressWarnings({"unchecked", "rawtypes"})
@Mixin(StoredUserList.class)
public abstract class WhitelistMixin<K, V extends StoredUserEntry<K>> {

    @Shadow protected abstract boolean contains(K k0);

    @Inject(method = "add", at = @At("HEAD"))
    private void injectAddEvent(V arg, CallbackInfo ci) {
        try {
            if (arg instanceof UserWhiteListEntry entry) {
                StoredUserEntryAccessor entryAccessor = (StoredUserEntryAccessor) entry;
                if (entryAccessor.getUser() != null && !contains((K) entryAccessor.getUser())) {
                    CraterEventBus.INSTANCE.postEvent(new WhitelistChangedEvent.EntryAdded(BridgedGameProfile.wrap((GameProfile) entryAccessor.getUser())));
                }
            }
        } catch (Exception ignored) {}
    }

    @Inject(method = "remove(Lnet/minecraft/server/players/StoredUserEntry;)V", at = @At("HEAD"))
    private void injectRemoveEvent(StoredUserEntry<K> arg, CallbackInfo ci) {
        try {
            if (arg instanceof UserWhiteListEntry entry) {
                StoredUserEntryAccessor entryAccessor = (StoredUserEntryAccessor) entry;
                if (entryAccessor.getUser() != null && contains((K) entryAccessor.getUser())) {
                    CraterEventBus.INSTANCE.postEvent(new WhitelistChangedEvent.EntryRemoved(BridgedGameProfile.wrap((GameProfile) entryAccessor.getUser())));
                }
            }
        } catch (Exception ignored) {}
    }

}
