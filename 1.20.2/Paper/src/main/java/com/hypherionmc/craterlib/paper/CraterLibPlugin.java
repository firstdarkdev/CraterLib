package com.hypherionmc.craterlib.paper;

import com.hypherionmc.craterlib.api.events.server.CraterRegisterCommandEvent;
import com.hypherionmc.craterlib.core.event.CraterEventBus;
import com.hypherionmc.craterlib.core.platform.CommonPlatform;
import com.hypherionmc.craterlib.utils.InternalServiceUtil;
import net.minecraft.server.MinecraftServer;
import org.bukkit.plugin.java.JavaPlugin;

public class CraterLibPlugin extends JavaPlugin {

    private final PaperEventListener listener = new PaperEventListener();

    public CraterLibPlugin() {
        super();
        InternalServiceUtil.loader = getClassLoader();
    }

    @Override
    public void onLoad() {
        listener.onServerStarting(MinecraftServer.getServer());
    }

    @Override
    public void onEnable() {
        CraterEventBus.INSTANCE.postEvent(new CraterRegisterCommandEvent(MinecraftServer.getServer().getCommands().getDispatcher()));
        getServer().getPluginManager().registerEvents(listener, this);
        getServer().getScheduler().scheduleSyncDelayedTask(this, listener::onServerStarted);
    }

}
