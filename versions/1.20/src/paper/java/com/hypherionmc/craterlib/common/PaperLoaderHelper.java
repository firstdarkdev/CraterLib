package com.hypherionmc.craterlib.common;

import com.hypherionmc.craterlib.core.platform.Environment;
import com.hypherionmc.craterlib.core.platform.LoaderType;
import com.hypherionmc.craterlib.core.platform.ModloaderEnvironment;
import net.minecraft.SharedConstants;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.util.Arrays;

/**
 * @author HypherionSA
 */
public class PaperLoaderHelper implements ModloaderEnvironment {

    public PaperLoaderHelper() {
    }

    @Override
    public LoaderType getLoaderType() {
        return LoaderType.PAPER;
    }

    @Override
    public String getGameVersion() {
        return SharedConstants.getCurrentVersion().getName();
    }

    @Override
    public File getGameFolder() {
        return new File(".");
    }

    @Override
    public File getConfigFolder() {
        return new File("config");
    }

    @Override
    public File getModsFolder() {
        return Bukkit.getPluginsFolder();
    }

    @Override
    public Environment getEnvironment() {
        return Environment.SERVER;
    }

    @Override
    public boolean isModLoaded(String modid) {
        return Bukkit.getPluginManager().isPluginEnabled(modid);
    }

    @Override
    public boolean isDevEnv() {
        return false;
    }

    @Override
    public int getModCount() {
        return (int) Arrays.stream(Bukkit.getPluginManager().getPlugins()).filter(Plugin::isEnabled).count();
    }
}
