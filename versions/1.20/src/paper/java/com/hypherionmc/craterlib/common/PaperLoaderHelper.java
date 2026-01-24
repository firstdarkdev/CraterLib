package com.hypherionmc.craterlib.common;

import com.google.auto.service.AutoService;
import com.hypherionmc.craterlib.api.loader.Environment;
import com.hypherionmc.craterlib.api.loader.LoaderType;
import com.hypherionmc.craterlib.core.services.CraterLoaderEnvironment;
import net.minecraft.SharedConstants;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.util.Arrays;

/**
 * @author HypherionSA
 */
@AutoService(CraterLoaderEnvironment.class)
public class PaperLoaderHelper implements CraterLoaderEnvironment {

    public PaperLoaderHelper() {
    }

    @Override
    public LoaderType getLoaderType() {
        return LoaderType.PAPER;
    }

    @Override
    public String getGameVersion() {
        return SharedConstants.getCurrentVersion().name();
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

    @Override
    public int getDataVersion() {
        return SharedConstants.getCurrentVersion().dataVersion().version();
    }
}
