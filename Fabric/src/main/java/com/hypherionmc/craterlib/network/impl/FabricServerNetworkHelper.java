package com.hypherionmc.craterlib.network.impl;

import com.hypherionmc.craterlib.core.network.CraterPacket;
import com.hypherionmc.craterlib.network.FabricNetworkHelper;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import org.jetbrains.annotations.NotNull;

import java.util.function.Function;

public class FabricServerNetworkHelper implements FabricNetworkHelper {
    @Override
    public void registerClientReceiver(@NotNull ResourceLocation channelName, @NotNull Function<FriendlyByteBuf, @NotNull CraterPacket<?>> factory) {

    }

    @Override
    public void registerServerReceiver(ResourceLocation channelName, Function<FriendlyByteBuf, CraterPacket<?>> factory) {
        ServerPlayNetworking.registerGlobalReceiver(channelName, (MinecraftServer server, ServerPlayer player, ServerGamePacketListenerImpl handler, FriendlyByteBuf buf, PacketSender responseSender) -> {
            CraterPacket<?> packet = factory.apply(buf);
            server.execute(() -> packet.handle(player, server));
        });
    }
}
