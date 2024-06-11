package com.hypherionmc.craterlib.mixin;

import com.hypherionmc.craterlib.client.gui.config.CraterConfigScreen;
import com.hypherionmc.craterlib.core.config.ConfigController;
import com.hypherionmc.craterlib.core.config.ModuleConfig;
import com.hypherionmc.craterlib.core.config.annotations.NoConfigScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraftforge.client.ConfigGuiHandler;
import net.minecraftforge.forgespi.language.IModInfo;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;
import java.util.function.BiFunction;

/**
 * @author HypherionSA
 */
@Mixin(ConfigGuiHandler.class)
public class ConfigScreenHandlerMixin {

    /**
     * Inject Auto Generated config Screens into forge
     *
     */
    @Inject(at = @At("RETURN"), method = "getGuiFactoryFor", cancellable = true, remap = false)
    private static void injectConfigScreen(IModInfo selectedMod, CallbackInfoReturnable<Optional<BiFunction<Minecraft, Screen, Screen>>> cir) {
        ConfigController.getMonitoredConfigs().forEach((conf, watcher) -> {
            if (!conf.getClass().isAnnotationPresent(NoConfigScreen.class)) {
                ModuleConfig config = (ModuleConfig) conf;
                if (config.getModId().equals(selectedMod.getModId())) {
                    cir.setReturnValue(
                            Optional.of((minecraft, screen) -> new CraterConfigScreen(config, screen))
                    );
                }
            }
        });
    }

}
