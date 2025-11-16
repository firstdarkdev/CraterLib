package com.hypherionmc.craterlib.mixin;

import com.hypherionmc.craterlib.client.gui.config.ClothConfigScreenBuilder;
import com.hypherionmc.craterlib.core.config.AbstractConfig;
import com.hypherionmc.craterlib.core.config.ConfigController;
import com.hypherionmc.craterlib.core.config.annotations.ClothScreen;
import com.hypherionmc.craterlib.core.platform.ModloaderEnvironment;
import com.hypherionmc.craterlib.nojang.client.BridgedMinecraft;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.Style;
import net.kyori.adventure.text.format.TextDecoration;
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
        ConfigController.getWatchedConfigs().forEach((conf, config) -> {
            if (!config.getClass().isAnnotationPresent(ClothScreen.class))
                return;

            if (config.getModId().equals(selectedMod.getModId())) {
                if (config.getClass().isAnnotationPresent(ClothScreen.class) && ModloaderEnvironment.INSTANCE.isModLoaded("cloth_config")) {
                    cir.setReturnValue(
                            Optional.of((minecraft, screen) -> ClothConfigScreenBuilder.buildConfigScreen(config, screen))
                    );
                } else {
                    cir.setReturnValue(
                            Optional.of((minecraft, screen) -> BridgedMinecraft.getInstance().buildWarningScreen(
                                    Component.text("Notice").style(Style.style(NamedTextColor.YELLOW).decorate(TextDecoration.BOLD)),
                                    Component.text("This mod does have a config screen, but requires Cloth Config to be installed."),
                                    screen
                            ))
                    );
                }
            }
        });
    }

}
