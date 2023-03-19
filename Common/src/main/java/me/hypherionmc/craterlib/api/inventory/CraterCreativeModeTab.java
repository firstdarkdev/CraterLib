package me.hypherionmc.craterlib.api.inventory;

import me.hypherionmc.craterlib.systems.internal.CreativeTabRegistry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.ItemStack;

import java.util.function.Supplier;

/**
 * @author HypherionSA
 * Extention to allow mods to add their own creative tabs, without relying on loader events
 */
public class CraterCreativeModeTab implements Supplier<CreativeModeTab> {

    private final ResourceLocation resourceLocation;
    private final Supplier<ItemStack> icon;
    private final String backgroundSuffix;
    private CreativeModeTab tab;

    protected CraterCreativeModeTab(Builder builder) {
        this.resourceLocation = builder.location;
        this.icon = builder.stack;
        this.backgroundSuffix = builder.backgroundSuffix == null ? "" : builder.backgroundSuffix;

        CreativeTabRegistry.registerTab(this);
    }

    public ResourceLocation getResourceLocation() {
        return this.resourceLocation;
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

    public static class Builder {
        private final ResourceLocation location;
        private Supplier<ItemStack> stack;
        private String backgroundSuffix;

        public Builder(ResourceLocation location) {
            this.location = location;
        }

        public Builder setIcon(Supplier<ItemStack> icon) {
            stack = icon;
            return this;
        }

        public Builder backgroundSuffix(String backgroundSuffix) {
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
