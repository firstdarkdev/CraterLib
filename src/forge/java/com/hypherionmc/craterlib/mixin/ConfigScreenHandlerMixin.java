package com.hypherionmc.craterlib.mixin;

import com.hypherionmc.craterlib.client.gui.config.ClothConfigScreenBuilder;
import com.hypherionmc.craterlib.core.config.ConfigController;
import com.hypherionmc.craterlib.core.config.annotations.ClothScreen;
import com.hypherionmc.craterlib.core.platform.ModloaderEnvironment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraftforge.client.ConfigScreenHandler;
import net.minecraftforge.fml.ModList;
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
        // TODO: This needs to be fixed. IConfigScreenFactory is not available
        /*ConfigController.getWatchedConfigs().forEach((conf, config) -> {
            if (!config.getClass().isAnnotationPresent(ClothScreen.class))
                return;

            if ((ModloaderEnvironment.INSTANCE.isModLoaded("cloth_config") || ModloaderEnvironment.INSTANCE.isModLoaded("cloth-config") || ModloaderEnvironment.INSTANCE.isModLoaded("clothconfig"))) {
                ModList.get()
                        .getModContainerById(config.getModId())
                        .ifPresent(c -> c.registerExtensionPoint(IConfigScreenFactory.class, ((minecraft, screen) -> ClothConfigScreenBuilder.buildConfigScreen(config, screen))));
            } else {
                //ModList.get().getModContainerById(config.getModId()).ifPresent(c -> c.registerExtensionPoint(IConfigScreenFactory.class, ((minecraft, screen) -> new CraterConfigScreen(config, screen))));
            }
        });*/
    }

}
