package com.hypherionmc.craterlib.mixin.events.client;

import com.hypherionmc.craterlib.api.events.client.ScreenEvent;
import com.hypherionmc.craterlib.core.event.CraterEventBus;
import com.hypherionmc.craterlib.nojang.client.gui.BridgedScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public class MinecraftMixin {

    @Shadow
    @Nullable
    public Screen screen;

    @Inject(method = "setScreen", at = @At(value = "TAIL"))
    private void injectScreenOpeningEvent(Screen screen, CallbackInfo ci) {
        Screen old = this.screen;
        if (screen != null) {
            ScreenEvent.Opening opening = new ScreenEvent.Opening(BridgedScreen.of(old), BridgedScreen.of(screen));
            CraterEventBus.INSTANCE.postEvent(opening);
        }
    }

}
