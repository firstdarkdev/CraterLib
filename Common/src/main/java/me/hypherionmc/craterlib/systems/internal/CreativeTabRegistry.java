package me.hypherionmc.craterlib.systems.internal;

import me.hypherionmc.craterlib.api.inventory.CraterCreativeModeTab;
import org.jetbrains.annotations.ApiStatus;

import java.util.ArrayList;
import java.util.List;

@ApiStatus.Internal
public class CreativeTabRegistry {

    private static final List<CraterCreativeModeTab> TABS = new ArrayList<>();
    public static void registerTab(CraterCreativeModeTab tab) {
        TABS.add(tab);
    }

    public static List<CraterCreativeModeTab> getTABS() {
        return TABS;
    }

}
