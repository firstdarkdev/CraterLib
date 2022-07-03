package me.hypherionmc.craterlib.client.events;

import me.hypherionmc.craterlib.events.Event;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.color.item.ItemColors;

/**
 * @author HypherionSA
 * @date 17/06/2022
 * A wrapped event to allow Block and Item Color Registration
 */
public class ColorRegistrationEvent {

    public static class BLOCKS extends Event {

        private BlockColors colors;

        public BLOCKS() {}

        public BLOCKS(BlockColors colors) {
            this.colors = colors;
        }

        public BlockColors getColors() {
            return colors;
        }
    }

    public static class ITEMS extends Event {

        private ItemColors colors;

        public ITEMS() {}

        public ITEMS(ItemColors colors) {
            this.colors = colors;
        }

        public ItemColors getColors() {
            return colors;
        }
    }

}
