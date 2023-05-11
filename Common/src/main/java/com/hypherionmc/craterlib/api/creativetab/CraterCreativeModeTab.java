package com.hypherionmc.craterlib.api.creativetab;

import com.hypherionmc.craterlib.core.systems.internal.CreativeTabRegistry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.ItemStack;

import java.util.function.Supplier;

public class CraterCreativeModeTab implements Supplier<CreativeModeTab> {

    private final ResourceLocation resourceLocation;
    private final Supplier<ItemStack> icon;
    private final String backgroundSuffix;
    private CreativeModeTab tab;
    private final ResourceKey<CreativeModeTab> resourceKey;

    protected CraterCreativeModeTab(Builder builder) {
        this.resourceLocation = builder.location;
        this.icon = builder.stack;
        this.backgroundSuffix = builder.backgroundSuffix == null ? "" : builder.backgroundSuffix;
        this.resourceKey = ResourceKey.create(Registries.CREATIVE_MODE_TAB, this.resourceLocation);

        CreativeTabRegistry.registerTab(this);
    }

    public ResourceLocation getResourceLocation() {
        return resourceLocation;
    }

    public Supplier<ItemStack> getIcon() {
        return icon;
    }

    public String getBackgroundSuffix() {
        return backgroundSuffix;
    }

    public void setTab(CreativeModeTab tab) {
        this.tab = tab;
    }

    public ResourceKey<CreativeModeTab> getResourceKey() {
        return resourceKey;
    }

    public static class Builder {
        private final ResourceLocation location;
        private Supplier<ItemStack> stack;
        private String backgroundSuffix;

        public Builder(ResourceLocation location) {
            this.location = location;
        }

        public CraterCreativeModeTab.Builder setIcon(Supplier<ItemStack> icon) {
            stack = icon;
            return this;
        }

        public CraterCreativeModeTab.Builder backgroundSuffix(String backgroundSuffix) {
            this.backgroundSuffix = backgroundSuffix;
            return this;
        }

        public CraterCreativeModeTab build() {
            return new CraterCreativeModeTab(this);
        }

    }

    @Override
    public CreativeModeTab get() {
        return tab == null ? CreativeModeTabs.getDefaultTab() : tab;
    }
}
