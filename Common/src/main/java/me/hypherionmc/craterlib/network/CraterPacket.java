package me.hypherionmc.craterlib.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;

/**
 * @author HypherionSA
 * @date 24/09/2022
 */
public interface CraterPacket<T extends CraterPacket<T>> {

    void write(final FriendlyByteBuf buf);

    void read(final FriendlyByteBuf buf);

    default void handle(Player player, Object minecraft) {
        this.createHandler().handle((T) this, player, minecraft);
    }

    PacketHandler<T> createHandler();

    abstract class PacketHandler<T extends CraterPacket<T>> {
        public abstract void handle(T packet, Player player, Object minecraft);
    }
}
