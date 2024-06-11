package com.hypherionmc.craterlib.mixin.events.client;

import com.hypherionmc.craterlib.api.events.client.PlayerJoinRealmEvent;
import com.hypherionmc.craterlib.core.event.CraterEventBus;
import com.hypherionmc.craterlib.nojang.realmsclient.dto.BridgedRealmsServer;
import com.mojang.realmsclient.RealmsMainScreen;
import com.mojang.realmsclient.dto.RealmsServer;
import net.minecraft.client.gui.screens.Screen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(RealmsMainScreen.class)
public class RealmsMainScreenMixin {

    @Inject(at = @At("HEAD"), method = "play")
    private void play(RealmsServer serverData, Screen arg2, CallbackInfo ci) {
        PlayerJoinRealmEvent playerJoinRealm = new PlayerJoinRealmEvent(BridgedRealmsServer.of(serverData));
        CraterEventBus.INSTANCE.postEvent(playerJoinRealm);
    }

}
