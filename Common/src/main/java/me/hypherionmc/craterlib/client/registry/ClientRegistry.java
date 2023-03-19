package me.hypherionmc.craterlib.client.registry;

import me.hypherionmc.craterlib.api.rendering.DyableBlock;
import me.hypherionmc.craterlib.api.rendering.ItemDyable;
import me.hypherionmc.craterlib.client.rendering.ItemColorHandler;
import me.hypherionmc.craterlib.platform.ClientPlatform;
import me.hypherionmc.craterlib.systems.reg.RegistrationProvider;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.color.item.ItemColors;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;

/**
 * Helper for registering Block and Item color handlers
 */
public class ClientRegistry {

    /**
     * Register Block Color Handlers
     *
     * @param colors
     * @param blocks
     */
    public static void registerBlockColors(BlockColors colors, RegistrationProvider<Block> blocks) {
        blocks.getEntries().forEach(blockRegistryObject -> {
            if (blockRegistryObject.get() instanceof DyableBlock dyableBlock) {
                colors.register(dyableBlock.dyeHandler(), (Block) dyableBlock);
            }
        });
    }

    /**
     * Register Item Color Handlers
     *
     * @param colors
     * @param items
     */
    public static void registerItemColors(ItemColors colors, RegistrationProvider<Item> items) {
        items.getEntries().forEach(itemRegistryObject -> {
            if (itemRegistryObject.get() instanceof ItemDyable itemDyable) {
                colors.register(new ItemColorHandler(), (Item) itemDyable);
            }
        });
    }

    public static void registerBlockEntityRenderer(BlockEntityType<? extends BlockEntity> blockEntityType, BlockEntityRendererProvider blockEntityRendererFactory) {
        ClientPlatform.CLIENT_HELPER.registerBlockEntityRenderer(blockEntityType, blockEntityRendererFactory);
    }

}
