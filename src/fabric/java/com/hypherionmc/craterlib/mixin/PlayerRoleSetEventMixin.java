package com.hypherionmc.craterlib.mixin;

import com.hypherionmc.craterlib.api.events.compat.PlayerRolesEvents;
import com.hypherionmc.craterlib.compat.playerroles.BridgedPlayerRoles;
import com.hypherionmc.craterlib.core.event.CraterEventBus;
import com.hypherionmc.craterlib.nojang.authlib.BridgedGameProfile;
import dev.gegy.roles.api.Role;
import dev.gegy.roles.store.PlayerRoleSet;
import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerRoleSet.class)
public class PlayerRoleSetEventMixin {

    @Shadow
    @Final
    private @Nullable ServerPlayer player;

    @Inject(method = "add", at = @At("RETURN"))
    private void injectRoleAddedEvent(Role role, CallbackInfoReturnable<Boolean> cir) {
        try {
            var success = cir.getReturnValue();
            if (success) {
                CraterEventBus.INSTANCE.postEvent(PlayerRolesEvents.RoleAddedEvent.of(BridgedPlayerRoles.of(role.getId(), role.getIndex()), BridgedGameProfile.of(player.getGameProfile())));
            }
        } catch (Exception ignored) {}
    }

    @Inject(method = "remove", at = @At("RETURN"))
    private void injectRoleRemovedEvent(Role role, CallbackInfoReturnable<Boolean> cir) {
        try {
            var success = cir.getReturnValue();
            if (success) {
                CraterEventBus.INSTANCE.postEvent(PlayerRolesEvents.RoleRemovedEvent.of(BridgedPlayerRoles.of(role.getId(), role.getIndex()), BridgedGameProfile.of(player.getGameProfile())));
            }
        } catch (Exception ignored) {}
    }

}
