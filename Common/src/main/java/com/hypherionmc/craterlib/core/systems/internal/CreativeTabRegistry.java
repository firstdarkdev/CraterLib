package com.hypherionmc.craterlib.core.systems.internal;

import com.hypherionmc.craterlib.api.creativetab.CraterCreativeModeTab;
import net.minecraft.world.level.ItemLike;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

/**
 * @author HypherionSA
 * A helper class to make registering creative tabs easier across modloaders
 */
public class CreativeTabRegistry {

    private static final List<CraterCreativeModeTab> TABS = new ArrayList<>();
    private static final List<Pair<CraterCreativeModeTab, Supplier<? extends ItemLike>>> TAB_ITEMS = new ArrayList<>();

    public static void setCreativeTab(CraterCreativeModeTab tab, Supplier<? extends ItemLike> item) {
        if (item != null) {
            TAB_ITEMS.add(Pair.of(tab, item));
        }
    }

    public static void registerTab(CraterCreativeModeTab tab) {
        TABS.add(tab);
    }

    public static List<CraterCreativeModeTab> getTabs() {
        return TABS;
    }

    public static List<Pair<CraterCreativeModeTab, Supplier<? extends ItemLike>>> getTabItems() {
        return TAB_ITEMS;
    }
}
