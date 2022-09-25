package me.hypherionmc.craterlib.client;

import me.hypherionmc.craterlib.api.rendering.CustomRenderType;
import me.hypherionmc.craterlib.common.item.BlockItemDyable;
import me.hypherionmc.craterlib.network.CraterPacket;
import me.hypherionmc.craterlib.platform.services.LibClientHelper;
import me.hypherionmc.craterlib.systems.reg.RegistryObject;
import me.hypherionmc.craterlib.util.ColorPropertyFunction;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.network.Connection;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.fml.loading.FMLEnvironment;

import java.util.Collection;
import java.util.Objects;
import java.util.function.Function;

/**
 * @author HypherionSA
 * @date 16/06/2022
 */
public class ForgeClientHelper implements LibClientHelper {

    public ForgeClientHelper() {}

    @Override
    public void registerItemProperty(BlockItemDyable item, String property) {
        if (FMLEnvironment.dist.isClient()) {
            ItemProperties.register(item, new ResourceLocation(property), new ColorPropertyFunction(item));
        }
    }

    @Override
    public void registerCustomRenderTypes(Collection<RegistryObject<Block>> blocks, Collection<RegistryObject<Item>> items) {
        blocks.forEach(blk -> {
            if (blk.get() instanceof CustomRenderType type) {
                ItemBlockRenderTypes.setRenderLayer(blk.get(), type.getCustomRenderType());
            }
        });
    }

    @Override
    public Minecraft getClientInstance() {
        return Minecraft.getInstance();
    }

    @Override
    public Player getClientPlayer() {
        return Minecraft.getInstance().player;
    }

    @Override
    public Level getClientLevel() {
        return Minecraft.getInstance().level;
    }

    @Override
    public Connection getClientConnection() {
        Objects.requireNonNull(Minecraft.getInstance().getConnection(), "Cannot send packets when not in game!");
        return Minecraft.getInstance().getConnection().getConnection();
    }

    @Override
    public void registerClientReceiver(ResourceLocation channelName, Function<FriendlyByteBuf, CraterPacket<?>> factory) {
        // UNUSED
    }
}
