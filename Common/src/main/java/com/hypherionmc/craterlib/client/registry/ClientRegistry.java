package com.hypherionmc.craterlib.client.registry;

import com.hypherionmc.craterlib.api.rendering.DyableBlock;
import com.hypherionmc.craterlib.api.rendering.ItemDyable;
import com.hypherionmc.craterlib.client.rendering.ItemColorHandler;
import com.hypherionmc.craterlib.core.platform.ClientPlatform;
import me.hypherionmc.craterlib.systems.reg.RegistrationProvider;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.color.item.ItemColors;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import org.jetbrains.annotations.NotNull;

/**
 * @author HypherionSA
 * Helper for registering Block and Item color handlers
 */
public class ClientRegistry {

    /**
     * Register Block Color Handlers
     *
     * @param colors Existing block colors obtained from {@link com.hypherionmc.craterlib.api.event.client.ColorRegistrationEvent}
     * @param blocks The blocks registered for the module
     */
    public static void registerBlockColors(@NotNull BlockColors colors, @NotNull RegistrationProvider<Block> blocks) {
        blocks.getEntries().forEach(blockRegistryObject -> {
            if (blockRegistryObject.get() instanceof DyableBlock dyableBlock) {
                colors.register(dyableBlock.dyeHandler(), (Block) dyableBlock);
            }
        });
    }

    /**
     * Register Item Color Handlers
     *
     * @param colors Existing item colors obtained from {@link com.hypherionmc.craterlib.api.event.client.ColorRegistrationEvent}
     * @param items The items registered for the module
     */
    public static void registerItemColors(@NotNull ItemColors colors, @NotNull RegistrationProvider<Item> items) {
        items.getEntries().forEach(itemRegistryObject -> {
            if (itemRegistryObject.get() instanceof ItemDyable itemDyable) {
                colors.register(new ItemColorHandler(), (Item) itemDyable);
            }
        });
    }

    /**
     * Register a {@link net.minecraft.client.renderer.blockentity.BlockEntityRenderer} for a BlockEntity
     * @param blockEntityType The BlockEntity the renderer belongs to
     * @param blockEntityRendererFactory The renderer factory
     */
    public static void registerBlockEntityRenderer(@NotNull BlockEntityType<? extends BlockEntity> blockEntityType, @NotNull BlockEntityRendererProvider blockEntityRendererFactory) {
        ClientPlatform.CLIENT_HELPER.registerBlockEntityRenderer(blockEntityType, blockEntityRendererFactory);
    }

}
