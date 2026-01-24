package com.hypherionmc.craterlib.paper;

import com.hypherionmc.craterlib.CraterConstants;
import com.hypherionmc.craterlib.api.loader.CraterLoader;
import io.papermc.paper.plugin.bootstrap.BootstrapContext;
import io.papermc.paper.plugin.bootstrap.PluginBootstrap;

public class CraterLibBootstrap implements PluginBootstrap {

    @Override
    public void bootstrap(BootstrapContext bootstrapContext) {
        CraterConstants.setupLibrary();
        CraterLoader.LOGGER.info("Hello from CraterLib");
    }
}
