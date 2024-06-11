package com.hypherionmc.craterlib.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.handling.IPayloadHandler;

import java.util.function.BiConsumer;

/**
 * Based on https://github.com/mysticdrew/common-networking/tree/1.20.4
 */
public record NeoForgePacketContainer<T>(Class<T> messageType,
                                         ResourceLocation packetId,
                                         BiConsumer<T, FriendlyByteBuf> encoder,
                                         FriendlyByteBuf.Reader<NeoForgePacket<T>> decoder,
                                         IPayloadHandler<NeoForgePacket<T>> handler) { }