package com.hypherionmc.craterlib.api.event.client;

import com.hypherionmc.craterlib.core.event.CraterEvent;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.color.item.ItemColors;

/**
 * @author HypherionSA
 * A wrapped event to allow Block and Item Color Registration
 */
public class ColorRegistrationEvent {

    public static class Blocks extends CraterEvent {

        private final BlockColors colors;

        public Blocks(BlockColors colors) {
            this.colors = colors;
        }

        public BlockColors getColors() {
            return colors;
        }

        @Override
        public boolean canCancel() {
            return false;
        }
    }

    public static class Items extends CraterEvent {

        private final ItemColors colors;

        public Items(ItemColors colors) {
            this.colors = colors;
        }

        public ItemColors getColors() {
            return colors;
        }

        @Override
        public boolean canCancel() {
            return false;
        }
    }

}
