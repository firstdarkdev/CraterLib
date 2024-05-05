package com.hypherionmc.craterlib.network;

import com.hypherionmc.craterlib.CraterConstants;
import com.hypherionmc.craterlib.api.networking.CommonPacketWrapper;
import com.hypherionmc.craterlib.core.networking.PacketRegistry;
import com.hypherionmc.craterlib.core.networking.data.PacketContext;
import com.hypherionmc.craterlib.core.networking.data.PacketHolder;
import com.hypherionmc.craterlib.core.networking.data.PacketSide;
import com.hypherionmc.craterlib.nojang.world.entity.player.BridgedPlayer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.LogicalSide;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.handling.IPayloadHandler;

import java.util.function.Consumer;

/**
 * Based on https://github.com/mysticdrew/common-networking/tree/1.20.4
 */
public class CraterNeoForgeNetworkHandler extends PacketRegistry {

    public CraterNeoForgeNetworkHandler(PacketSide side) {
        super(side);
    }

    @SubscribeEvent
    public void register(final RegisterPayloadHandlersEvent event) {
        if (!PACKET_MAP.isEmpty()) {
            PACKET_MAP.forEach((type, container) -> event.registrar(container.getType().id().getNamespace())
                    .optional().commonBidirectional(container.getType(), container.getCodec(), buildHandler(container.handler())));
        }
    }

    @Override
    protected <T> void registerPacket(PacketHolder<T> container) {

    }

    public <T> void sendToServer(T packet) {
        this.sendToServer(packet, false);
    }

    public <T> void sendToServer(T packet, boolean ignoreCheck) {
        PacketHolder<T> container = (PacketHolder<T>) PACKET_MAP.get(packet.getClass());
        if (container != null) {
            PacketDistributor.sendToServer(new CommonPacketWrapper<>(container, packet));
        }
    }

    public <T> void sendToClient(T packet, BridgedPlayer player) {
        PacketHolder<T> container = (PacketHolder<T>) PACKET_MAP.get(packet.getClass());
        if (container != null) {
            if (player.getConnection().hasChannel(container.type())) {
                PacketDistributor.sendToPlayer(player.toMojangServerPlayer(), new CommonPacketWrapper<>(container, packet));
            }
        }
    }

    private <T, K extends CommonPacketWrapper<T>> IPayloadHandler<K> buildHandler(Consumer<PacketContext<T>> handler) {
        return (payload, ctx) -> {
            try
            {
                PacketSide side = ctx.flow().getReceptionSide().equals(LogicalSide.SERVER) ? PacketSide.SERVER : PacketSide.CLIENT;
                if (PacketSide.SERVER.equals(side)) {
                    handler.accept(new PacketContext<>(BridgedPlayer.of(ctx.player()), payload.packet(), side));
                } else {
                    handler.accept(new PacketContext<>(payload.packet(), side));
                }

            }
            catch (Throwable t) {
                CraterConstants.LOG.error("Error handling packet: {} -> ", payload.packet().getClass(), t);
            }
        };
    }
}