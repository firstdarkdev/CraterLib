package com.hypherionmc.craterlib.paper;

import com.hypherionmc.craterlib.CraterConstants;
import io.papermc.paper.plugin.bootstrap.BootstrapContext;
import io.papermc.paper.plugin.bootstrap.PluginBootstrap;

public class CraterLibBootstrap implements PluginBootstrap {

    @Override
    public void bootstrap(BootstrapContext bootstrapContext) {
        CraterConstants.LOG.info("Hello from CraterLib");
    }
}
