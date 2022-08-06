package me.hypherionmc.craterlib.mixin;

import me.hypherionmc.craterlib.client.gui.config.CraterConfigScreen;
import me.hypherionmc.craterlib.common.config.ConfigController;
import me.hypherionmc.craterlib.common.config.ModuleConfig;
import me.hypherionmc.craterlib.common.config.annotations.NoConfigScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraftforge.client.ConfigScreenHandler;
import net.minecraftforge.forgespi.language.IModInfo;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;
import java.util.function.BiFunction;

/**
 * @author HypherionSA
 * @date 06/08/2022
 */
@Mixin(ConfigScreenHandler.class)
public class ConfigScreenHandlerMixin {

    /**
     * Inject Auto Generated config Screens into forge
     * @param selectedMod
     * @param cir
     */
    @Inject(at = @At("RETURN"), method = "getScreenFactoryFor", cancellable = true, remap = false)
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
