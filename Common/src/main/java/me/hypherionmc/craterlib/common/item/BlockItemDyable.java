package me.hypherionmc.craterlib.common.item;

import me.hypherionmc.craterlib.api.rendering.DyableBlock;
import me.hypherionmc.craterlib.api.rendering.ItemDyable;
import me.hypherionmc.craterlib.platform.ClientPlatform;
import me.hypherionmc.craterlib.platform.Platform;
import me.hypherionmc.craterlib.platform.services.Environment;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import org.apache.commons.lang3.text.WordUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Base Item for Blocks that implement @link {DyableBlock}.
 */
public class BlockItemDyable extends BlockItem implements ItemDyable {

    public BlockItemDyable(Block block, Properties properties) {
        super(block, properties);

        if (Platform.LOADER.getEnvironment() == Environment.CLIENT) {
            ClientPlatform.CLIENT_HELPER.registerItemProperty(this, "color");
        }
    }

    /**
     * Get the Item Color from the block
     *
     * @return
     */
    @Override
    public DyeColor getColor(ItemStack stack) {
        return this.getColorFromNBT(stack);
    }

    @Override
    public @NotNull Component getName(ItemStack stack) {
        return Component.translatable(
                this.getDescriptionId(),
                WordUtils.capitalizeFully(getColorFromNBT(
                        stack).getName().replace("_", " ")
                )
        );
    }

    public DyeColor getColorFromNBT(ItemStack stack) {
        CompoundTag tag = stack.getOrCreateTag();
        if (tag.contains("color")) {
            return DyeColor.byName(tag.getString("color"), DyeColor.BLACK);
        } else {
            if (this.getBlock() instanceof DyableBlock dyableBlock) {
                return dyableBlock.defaultDyeColor();
            }
        }
        return DyeColor.BLACK;
    }

    @Nullable
    @Override
    protected BlockState getPlacementState(BlockPlaceContext ctx) {
        BlockState state = this.getBlock().getStateForPlacement(ctx);
        if (state != null && state.getBlock() instanceof DyableBlock) {
            Property property = state.getBlock().getStateDefinition().getProperty("color");
            if (property != null) {
                state = state.setValue(property, getColorFromNBT(ctx.getItemInHand()));
            }
        }
        return state != null && this.canPlace(ctx, state) ? state : null;
    }

}
