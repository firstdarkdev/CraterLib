package com.hypherionmc.craterlib;

import com.hypherionmc.craterlib.common.ForgeCommonEvents;
import com.hypherionmc.craterlib.core.event.CraterEventBus;
import net.minecraftforge.fml.common.Mod;

@Mod(CraterConstants.MOD_ID)
public class CraterLib {

    public CraterLib() {
        CraterEventBus.INSTANCE.registerEventListener(ForgeCommonEvents.class);
    }
}
