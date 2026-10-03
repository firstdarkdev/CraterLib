package com.hypherionmc.craterlib.mixin.events;

import com.hypherionmc.craterlib.api.events.common.CraterPlayerDeathEvent;
import com.hypherionmc.craterlib.api.game.text.Text;
import com.hypherionmc.craterlib.core.event.CraterEventBus;
import com.hypherionmc.craterlib.impl.api.world.entity.player.BridgedPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Player.class)
public class PlayerMixin {

    @Inject(method = "die", at = @At("HEAD"))
    private void injectPlayerDeathEvent(DamageSource damageSource, CallbackInfo ci) {
        try {
            CraterEventBus.INSTANCE.postEvent(new CraterPlayerDeathEvent(
                    BridgedPlayer.wrap(castToPlayer()),
                    Text.fromGame(damageSource.getLocalizedDeathMessage(castToPlayer())))
            );
        } catch (Exception ignored) {}
    }

    private Player castToPlayer() {
        return ((Player) (Object) this);
    }

}
