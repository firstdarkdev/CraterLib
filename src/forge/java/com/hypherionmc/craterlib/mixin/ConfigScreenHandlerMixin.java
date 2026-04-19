package com.hypherionmc.craterlib.mixin;

import com.hypherionmc.craterlib.api.game.text.Text;
import com.hypherionmc.craterlib.api.loader.CraterLoader;
import com.hypherionmc.craterlib.client.gui.config.ClothConfigScreenBuilder;
import com.hypherionmc.craterlib.core.config.ConfigController;
import com.hypherionmc.craterlib.core.config.annotations.ClothScreen;
import com.hypherionmc.craterlib.impl.api.client.BridgedMinecraft;
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
        ConfigController.getWatchedConfigs().forEach((conf, config) -> {
            if (!config.getClass().isAnnotationPresent(ClothScreen.class))
                return;

            if ((CraterLoader.isModLoaded("cloth_config") || CraterLoader.isModLoaded("cloth-config") || CraterLoader.isModLoaded("clothconfig"))) {
                ModList
                        .getModContainerById(config.getModId())
                        .ifPresent(c -> c.registerExtensionPoint(ConfigScreenHandler.ConfigScreenFactory.class, () -> new ConfigScreenHandler.ConfigScreenFactory((minecraft, screen) -> ClothConfigScreenBuilder.buildConfigScreen(config, screen))));
            } else {
                ModList.getModContainerById(config.getModId()).ifPresent(c -> c.registerExtensionPoint(
                        ConfigScreenHandler.ConfigScreenFactory.class,
                        (() -> new ConfigScreenHandler.ConfigScreenFactory((minecraft, screen) -> ((BridgedMinecraft) CraterLoader.getClient()).buildWarningScreen(
                                Text.literal("Missing Cloth Config"),
                                Text.literal("This config screen requires Cloth Config to be installed"),
                                screen
                        )))
                ));
            }
        });
    }

}
