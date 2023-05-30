package com.hypherionmc.craterlib.network.impl;

import com.hypherionmc.craterlib.core.network.CraterPacket;
import com.hypherionmc.craterlib.network.FabricNetworkHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

import java.util.function.Function;

public class FabricClientNetworkHelper implements FabricNetworkHelper {

    @Override
    public void registerClientReceiver(ResourceLocation channelName, Function<FriendlyByteBuf, CraterPacket<?>> factory) {
        ClientPlayNetworking.registerGlobalReceiver(channelName, (Minecraft client, ClientPacketListener handler, FriendlyByteBuf buf, PacketSender responseSender) -> {
            CraterPacket<?> packet = factory.apply(buf);
            client.execute(() -> packet.handle(client.player, client));
        });
    }

    @Override
    public void registerServerReceiver(ResourceLocation channelName, Function<FriendlyByteBuf, CraterPacket<?>> factory) {

    }
}
