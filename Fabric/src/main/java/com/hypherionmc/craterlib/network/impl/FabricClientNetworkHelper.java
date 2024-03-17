package com.hypherionmc.craterlib.network.impl;

import com.hypherionmc.craterlib.core.network.CraterPacket;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

import java.util.function.Function;

@Deprecated(forRemoval = true)
public class FabricClientNetworkHelper extends FabricServerNetworkHelper {

    @Override
    public void registerClientReceiver(@NotNull ResourceLocation channelName, @NotNull Function<FriendlyByteBuf, CraterPacket<?>> factory) {
        ClientPlayNetworking.registerGlobalReceiver(channelName, (Minecraft client, ClientPacketListener handler, FriendlyByteBuf buf, PacketSender responseSender) -> {
            CraterPacket<?> packet = factory.apply(buf);
            client.execute(() -> packet.handle(client.player, client));
        });
    }
}
