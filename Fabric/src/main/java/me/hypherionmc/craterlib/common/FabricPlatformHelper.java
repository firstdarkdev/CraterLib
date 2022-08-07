package me.hypherionmc.craterlib.common;

import me.hypherionmc.craterlib.platform.services.IPlatformHelper;
import net.fabricmc.loader.api.FabricLoader;

/**
 * @author HypherionSA
 * @date 07/08/2022
 */
public class FabricPlatformHelper implements IPlatformHelper {

    @Override
    public String getPlatformName() {
        return "Fabric";
    }

    @Override
    public boolean isModLoaded(String modId) {
        return FabricLoader.getInstance().isModLoaded(modId);
    }

    @Override
    public boolean isDevelopmentEnvironment() {
        return FabricLoader.getInstance().isDevelopmentEnvironment();
    }
}
