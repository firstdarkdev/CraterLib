package com.hypherionmc.craterlib.paper;

import com.hypherionmc.craterlib.CraterConstants;
import com.hypherionmc.craterlib.api.loader.CraterLoader;
import com.hypherionmc.craterlib.api.util.CraterServiceLoader;
import io.papermc.paper.plugin.bootstrap.BootstrapContext;
import io.papermc.paper.plugin.bootstrap.PluginBootstrap;

public class CraterLibBootstrap implements PluginBootstrap {

    @Override
    public void bootstrap(BootstrapContext bootstrapContext) {
        CraterServiceLoader.loader = this.getClass().getClassLoader();
        CraterConstants.setupLibrary();
        CraterLoader.LOGGER.info("Hello from CraterLib");
    }
}
