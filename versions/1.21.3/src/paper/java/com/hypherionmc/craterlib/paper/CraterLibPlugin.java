package com.hypherionmc.craterlib.paper;

import com.hypherionmc.craterlib.api.events.server.CraterRegisterCommandEvent;
import com.hypherionmc.craterlib.api.util.CraterServiceLoader;
import com.hypherionmc.craterlib.core.event.CraterEventBus;
import com.hypherionmc.craterlib.core.loader.plugins.CraterPluginLoader;
import net.minecraft.server.MinecraftServer;
import org.bukkit.plugin.java.JavaPlugin;

public class CraterLibPlugin extends JavaPlugin {

    private final PaperEventListener listener = new PaperEventListener();

    public CraterLibPlugin() {
        super();
        CraterServiceLoader.loader = getClassLoader();
        CraterPluginLoader.loadIfNotLoaded();
        CraterPluginLoader.initializeEarly();
        CraterPluginLoader.initializeServerPlugins();
    }

    @Override
    public void onLoad() {
        listener.onServerStarting(MinecraftServer.getServer());
    }

    @Override
    public void onEnable() {
        CraterEventBus.INSTANCE.postEvent(new CraterRegisterCommandEvent(
                (cmd) -> MinecraftServer.getServer().createCommandSourceStack().dispatcher().register(cmd.unwrap())
        ));
        getServer().getPluginManager().registerEvents(listener, this);
        getServer().getScheduler().scheduleSyncDelayedTask(this, listener::onServerStarted);
    }

}