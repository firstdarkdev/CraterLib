package com.hypherionmc.craterlib.network;

import com.hypherionmc.craterlib.core.network.CraterNetworkHandler;
import com.hypherionmc.craterlib.core.network.CraterPacket;
import com.hypherionmc.craterlib.core.network.PacketDirection;
import net.minecraft.network.protocol.Packet;

import java.util.function.Supplier;

/**
 * @author HypherionSA
 * Partly inspired by and based on <a href="https://github.com/Fuzss/puzzleslib/blob/1.19/Forge/src/main/java/fuzs/puzzleslib/network/ForgeNetworkHandler.java">...</a>
 */
@Deprecated(forRemoval = true)
public class NeoForgeNetworkHandler implements CraterNetworkHandler {


    public NeoForgeNetworkHandler(String modid, boolean requiredClient, boolean requiredServer) {
    }

    @Override
    public <T extends CraterPacket<T>> void registerPacket(Class<T> clazz, Supplier<T> supplier, PacketDirection packetDirection) {

    }

    @Override
    public Packet<?> toServerBound(CraterPacket<?> packet) {
        return null;
    }

    @Override
    public Packet<?> toClientBound(CraterPacket<?> packet) {
        return null;
    }
}
