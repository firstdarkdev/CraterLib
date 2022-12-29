package me.hypherionmc.craterlib.client;

import me.hypherionmc.craterlib.api.rendering.CustomRenderType;
import me.hypherionmc.craterlib.common.item.BlockItemDyable;
import me.hypherionmc.craterlib.network.CraterPacket;
import me.hypherionmc.craterlib.platform.services.LibClientHelper;
import me.hypherionmc.craterlib.systems.reg.RegistryObject;
import me.hypherionmc.craterlib.util.ColorPropertyFunction;
import net.fabricmc.api.EnvType;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.object.builder.v1.client.model.FabricModelPredicateProviderRegistry;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.network.Connection;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

import java.util.Collection;
import java.util.function.Function;

/**
 * @author HypherionSA
 * @date 16/06/2022
 */
public class FabricClientHelper implements LibClientHelper {

    @Override
    public void registerItemProperty(BlockItemDyable item, String property) {
        if (FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT) {
            FabricModelPredicateProviderRegistry.register(item, new ResourceLocation(property), new ColorPropertyFunction(item));
        }
    }

    @Override
    public void registerCustomRenderTypes(Collection<RegistryObject<Block>> blocks, Collection<RegistryObject<Item>> items) {
        blocks.forEach(blk -> {
            if (blk.get() instanceof CustomRenderType type) {
                BlockRenderLayerMap.INSTANCE.putBlock(blk.get(), type.getCustomRenderType());
            }
        });

        items.forEach(itm -> {
            if (itm.get() instanceof BlockItemDyable dyable && dyable.getBlock() instanceof CustomRenderType customRenderType) {
                BlockRenderLayerMap.INSTANCE.putItem(itm.get(), customRenderType.getCustomRenderType());
            }

            if (itm.get() instanceof CustomRenderType customRenderType) {
                BlockRenderLayerMap.INSTANCE.putItem(itm.get(), customRenderType.getCustomRenderType());
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
        return Minecraft.getInstance().getConnection().getConnection();
    }

    @Override
    public void registerClientReceiver(ResourceLocation channelName, Function<FriendlyByteBuf, CraterPacket<?>> factory) {
        ClientPlayNetworking.registerGlobalReceiver(channelName, (Minecraft client, ClientPacketListener handler, FriendlyByteBuf buf, PacketSender responseSender) -> {
            CraterPacket<?> packet = factory.apply(buf);
            client.execute(() -> packet.handle(client.player, client));
        });
    }

    public static void registerCreativeItems(CreativeModeTab tab, FabricItemGroupEntries entries) {

    }
}
