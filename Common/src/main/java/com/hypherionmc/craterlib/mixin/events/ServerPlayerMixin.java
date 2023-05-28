package com.hypherionmc.craterlib.mixin.events;

import com.hypherionmc.craterlib.api.event.common.CraterLivingDeathEvent;
import com.hypherionmc.craterlib.core.event.CraterEventBus;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayer.class)
public class ServerPlayerMixin {

    @Inject(method = "die", at = @At("HEAD"), cancellable = true)
    private void injectPlayerDeathEvent(DamageSource damageSource, CallbackInfo ci) {
        CraterLivingDeathEvent event = new CraterLivingDeathEvent(((ServerPlayer)(Object) this), damageSource);
        CraterEventBus.INSTANCE.postEvent(event);
        if (event.wasCancelled())
            ci.cancel();
    }

}
