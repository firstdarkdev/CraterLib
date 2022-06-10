package me.hypherionmc.craterlib.common.network;

import me.hypherionmc.craterlib.Constants;
import net.minecraft.Util;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.Level;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;

// TODO: FINISH NETWORK IMPLEMENTATION
public interface BaseNetworkPacket {

    Map<String, Handler<?>> PACKETS = Util.make(new HashMap<>(), map -> {
        Constants.LOG.info("Registering Config Packets");
    });

    void write(FriendlyByteBuf buf);

    void handle(Level level);

    record Handler<T extends BaseNetworkPacket>(Class<T> clazz, BiConsumer<T, FriendlyByteBuf> write,
                                                Function<FriendlyByteBuf, T> read,
                                                BiConsumer<T, Level> handle) {
    }
}
