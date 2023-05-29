package com.hypherionmc.craterlib.mixin.events.client;

import com.hypherionmc.craterlib.api.event.client.EarlyInitEvent;
import com.hypherionmc.craterlib.core.event.CraterEventBus;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Options;
import net.minecraft.client.tutorial.Tutorial;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Tutorial.class)
public class TutorialMixin {

    @Inject(method = "<init>", at = @At("RETURN"))
    private void injectEarlyInitEvent(Minecraft minecraft, Options options, CallbackInfo ci) {
        EarlyInitEvent event = new EarlyInitEvent(minecraft, options);
        CraterEventBus.INSTANCE.postEvent(event);
    }

}
