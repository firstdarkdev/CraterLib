package me.hypherionmc.craterlib.platform.services;

import me.hypherionmc.craterlib.network.CraterNetworkHandler;
import me.hypherionmc.craterlib.network.CraterPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.network.Connection;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

import java.util.function.Function;

/**
 * @author HypherionSA
 * @date 24/09/2022
 */
public interface LibCommonHelper {

    public CraterNetworkHandler createPacketHandler(String modid);

    public Minecraft getClientInstance();

    public Player getClientPlayer();

    public Level getClientLevel();

    public Connection getClientConnection();

    public MinecraftServer getMCServer();

    /* FABRIC ONLY */
    public void registerClientReceiver(ResourceLocation channelName, Function<FriendlyByteBuf, CraterPacket<?>> factory);
    public void registerServerReceiver(ResourceLocation channelName, Function<FriendlyByteBuf, CraterPacket<?>> factory);
}
