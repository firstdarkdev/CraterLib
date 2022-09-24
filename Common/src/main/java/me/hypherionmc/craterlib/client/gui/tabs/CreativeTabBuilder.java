package me.hypherionmc.craterlib.client.gui.tabs;

import me.hypherionmc.craterlib.platform.Platform;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

import java.util.function.Supplier;

/**
 * @author HypherionSA
 * @date 16/06/2022
 * Provides a wrapper around Forge/Fabric to create a new Creative Tab
 */
public class CreativeTabBuilder {

    public static Builder builder(String modid, String tabid) {
        return new Builder(modid, tabid);
    }

    public static final class Builder {

        private Supplier<ItemStack> tabIcon;
        private final String modid;
        private final String tabid;
        private String backgroundPrefix;

        public Builder(String modid, String tabid) {
            this.modid = modid;
            this.tabid = tabid;
        }

        public Builder setIcon(Supplier<ItemStack> stack) {
            this.tabIcon = stack;
            return this;
        }

        public Builder setBackgroundPrefix(String prefix) {
            this.backgroundPrefix = prefix;
            return this;
        }

        public CreativeModeTab build() {
            return Platform.CLIENT_HELPER.tabBuilder(this.modid, this.tabid, this.tabIcon, this.backgroundPrefix);
        }

    }

}
