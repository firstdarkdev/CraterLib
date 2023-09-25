package com.hypherionmc.craterlib.mixin.events.client;

import com.hypherionmc.craterlib.api.event.client.CraterSinglePlayerEvent;
import com.hypherionmc.craterlib.core.event.CraterEventBus;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientLevel.class)
public class ClientLevelMixin {

    @Inject(method = "addEntity", at = @At("HEAD"))
    private void injectSinglePlayerJoinEvent(Entity entity, CallbackInfo ci) {
        if (entity instanceof Player player) {
            CraterSinglePlayerEvent.PlayerLogin playerLogin = new CraterSinglePlayerEvent.PlayerLogin(player);
            CraterEventBus.INSTANCE.postEvent(playerLogin);
        }
    }

}
