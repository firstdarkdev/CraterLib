package com.hypherionmc.craterlib.hytale;

import com.hypherionmc.craterlib.CraterConstants;
import com.hypherionmc.craterlib.api.events.server.CraterServerLifecycleEvent;
import com.hypherionmc.craterlib.core.event.CraterEventBus;
import com.hypherionmc.craterlib.nojang.server.BridgedMinecraftServer;
import com.hypherionmc.craterlib.utils.InternalServiceUtil;
import com.hypherionmc.craterlib.utils.ReflectionUtil;
import com.hypixel.hytale.server.core.HytaleServer;
import com.hypixel.hytale.server.core.event.events.BootEvent;
import com.hypixel.hytale.server.core.event.events.ShutdownEvent;
import com.hypixel.hytale.server.core.event.events.player.PlayerChatEvent;
import com.hypixel.hytale.server.core.event.events.player.PlayerConnectEvent;
import com.hypixel.hytale.server.core.event.events.player.PlayerDisconnectEvent;
import com.hypixel.hytale.server.core.event.events.player.PlayerSetupConnectEvent;
import com.hypixel.hytale.server.core.modules.accesscontrol.AccessControlModule;
import com.hypixel.hytale.server.core.modules.accesscontrol.provider.HytaleBanProvider;
import com.hypixel.hytale.server.core.modules.accesscontrol.provider.HytaleWhitelistProvider;
import com.hypixel.hytale.server.core.plugin.JavaPlugin;
import com.hypixel.hytale.server.core.plugin.JavaPluginInit;
import org.jetbrains.annotations.NotNull;

public class CraterHytalePlugin extends JavaPlugin {

    public static HytaleWhitelistProvider whitelistProvider;
    public static HytaleBanProvider banProvider;

    public CraterHytalePlugin(@NotNull JavaPluginInit init) {
        super(init);
        InternalServiceUtil.loader = getClassLoader();
        CraterConstants.LOG.info("Hello from CraterLib and EmberLoader");
    }

    @Override
    protected void setup() {
        super.setup();

        whitelistProvider = ReflectionUtil.getPublic(HytaleWhitelistProvider.class, AccessControlModule.get(), "whitelistProvider");
        banProvider = ReflectionUtil.getPublic(HytaleBanProvider.class, AccessControlModule.get(), "banProvider");
        if (whitelistProvider == null || banProvider == null) throw new RuntimeException("Could not find Hytale Access Control Module");

        CraterEventBus.INSTANCE.postEvent(new CraterServerLifecycleEvent.Starting(BridgedMinecraftServer.of(HytaleServer.get())));

        getEventRegistry().registerGlobal(PlayerConnectEvent.class, CraterHytaleEvents::playerJoinedEvent);
        getEventRegistry().registerGlobal(PlayerDisconnectEvent.class, CraterHytaleEvents::playerLeaveEvent);
        getEventRegistry().registerGlobal(PlayerSetupConnectEvent.class, CraterHytaleEvents::playerPreLoginEvent);
        getEventRegistry().registerGlobal(BootEvent.class, CraterHytaleEvents::serverBootEvent);
        getEventRegistry().registerGlobal(ShutdownEvent.class, CraterHytaleEvents::serverShutdown);
        getEventRegistry().registerGlobal(PlayerChatEvent.class, CraterHytaleEvents::serverChatEvent);
        getEntityStoreRegistry().registerSystem(new HytaleDeathSystem());


    }

    @Override
    protected void shutdown() {
        super.shutdown();
        CraterEventBus.INSTANCE.postEvent(new CraterServerLifecycleEvent.Stopping(BridgedMinecraftServer.of(HytaleServer.get())));
    }
}
