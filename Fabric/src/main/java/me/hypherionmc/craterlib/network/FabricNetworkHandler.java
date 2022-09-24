package me.hypherionmc.craterlib.network;

import com.google.common.collect.Maps;
import me.hypherionmc.craterlib.platform.Platform;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.Util;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.resources.ResourceLocation;

import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * @author HypherionSA
 * @date 24/09/2022
 */
public class FabricNetworkHandler implements CraterNetworkHandler {

    private static final Map<String, FabricNetworkHandler> NETWORK_HANDLERS = Maps.newConcurrentMap();
    private final Map<Class<? extends CraterPacket<?>>, PacketData> packets = Maps.newIdentityHashMap();

    private final String modid;
    private final AtomicInteger packetID = new AtomicInteger();

    private FabricNetworkHandler(String modid) {
        this.modid = modid;
    }

    @Override
    public <T extends CraterPacket<T>> void registerPacket(Class<? extends T> clazz, Supplier<T> supplier, PacketDirection packetDirection) {
        ResourceLocation channelName = this.nextId();
        this.packets.put(clazz, new PacketData(clazz, channelName, packetDirection));

        final Function<FriendlyByteBuf, CraterPacket<?>> decoder = buf -> Util.make(supplier.get(), message -> message.read(buf));

        switch (packetDirection) {
            case TO_CLIENT -> Platform.COMMON_HELPER.registerClientReceiver(channelName, decoder);
            case TO_SERVER -> Platform.COMMON_HELPER.registerServerReceiver(channelName, decoder);
        }
    }

    private ResourceLocation nextId() {
        return new ResourceLocation(this.modid, "play/" + this.packetID.getAndIncrement());
    }

    @Override
    public Packet<?> toServerBound(CraterPacket<?> packet) {
        if (this.packets.get(packet.getClass()).direction() != PacketDirection.TO_SERVER) throw new IllegalStateException("Attempted sending message to wrong side, expected %s, was %s".formatted(PacketDirection.TO_SERVER, PacketDirection.TO_CLIENT));
        return this.toPacket(ClientPlayNetworking::createC2SPacket, packet);
    }

    @Override
    public Packet<?> toClientBound(CraterPacket<?> packet) {
        if (this.packets.get(packet.getClass()).direction() != PacketDirection.TO_CLIENT) throw new IllegalStateException("Attempted sending message to wrong side, expected %s, was %s".formatted(PacketDirection.TO_CLIENT, PacketDirection.TO_SERVER));
        return this.toPacket(ServerPlayNetworking::createS2CPacket, packet);
    }

    private Packet<?> toPacket(BiFunction<ResourceLocation, FriendlyByteBuf, Packet<?>> packetFactory, CraterPacket<?> message) {
        ResourceLocation identifier = this.packets.get(message.getClass()).identifier();
        FriendlyByteBuf byteBuf = PacketByteBufs.create();
        message.write(byteBuf);
        return packetFactory.apply(identifier, byteBuf);
    }

    public synchronized static CraterNetworkHandler of(String modId) {
        return NETWORK_HANDLERS.computeIfAbsent(modId, FabricNetworkHandler::new);
    }

    private record PacketData(Class<? extends CraterPacket<?>> clazz, ResourceLocation identifier, PacketDirection direction) {

    }
}
