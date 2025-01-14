package com.hypherionmc.craterlib.mixin;

import com.hypherionmc.craterlib.client.gui.config.CraterConfigScreen;
import com.hypherionmc.craterlib.core.config.ConfigController;
import com.hypherionmc.craterlib.core.config.ModuleConfig;
import com.hypherionmc.craterlib.core.config.annotations.NoConfigScreen;
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
 */
@Mixin(ConfigScreenHandler.class)
public class ConfigScreenHandlerMixin {

    /**
     * Inject Auto Generated config Screens into forge
     *
     */
    @Inject(at = @At("RETURN"), method = "getScreenFactoryFor", cancellable = true, remap = false)
    private static void injectConfigScreen(IModInfo selectedMod, CallbackInfoReturnable<Optional<BiFunction<Minecraft, Screen, Screen>>> cir) {
        ConfigController.getMonitoredConfigs().forEach((conf, watcher) -> {
            AbstractConfig config = watcher.getLeft();
            if (config.getClass().isAnnotationPresent(NoConfigScreen.class))
                return;

            if (watcher.getLeft().getClass().isAnnotationPresent(ClothScreen.class) && (ModloaderEnvironment.INSTANCE.isModLoaded("cloth_config") || ModloaderEnvironment.INSTANCE.isModLoaded("cloth-config") || ModloaderEnvironment.INSTANCE.isModLoaded("clothconfig"))) {
                ModList.get().getModContainerById(config.getModId()).ifPresent(c -> c.registerExtensionPoint(IConfigScreenFactory.class, ((minecraft, screen) -> ClothConfigScreenBuilder.buildConfigScreen(config, screen))));
            } else {
                //ModList.get().getModContainerById(config.getModId()).ifPresent(c -> c.registerExtensionPoint(IConfigScreenFactory.class, ((minecraft, screen) -> new CraterConfigScreen(config, screen))));
            }
        });
    }

}
