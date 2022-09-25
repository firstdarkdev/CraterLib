package me.hypherionmc.craterlib.platform.services;

import me.hypherionmc.craterlib.common.item.BlockItemDyable;
import me.hypherionmc.craterlib.network.CraterPacket;
import me.hypherionmc.craterlib.systems.reg.RegistryObject;
import net.minecraft.client.Minecraft;
import net.minecraft.network.Connection;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

import java.util.Collection;
import java.util.function.Function;

/**
 * @author HypherionSA
 * @date 16/06/2022
 */
public interface LibClientHelper {

    public void registerItemProperty(BlockItemDyable item, String property);

    public void registerCustomRenderTypes(
            Collection<RegistryObject<Block>> blocks,
            Collection<RegistryObject<Item>> items
    );

    public Minecraft getClientInstance();

    public Player getClientPlayer();

    public Level getClientLevel();

    public Connection getClientConnection();

    public void registerClientReceiver(ResourceLocation channelName, Function<FriendlyByteBuf, CraterPacket<?>> factory);

}
