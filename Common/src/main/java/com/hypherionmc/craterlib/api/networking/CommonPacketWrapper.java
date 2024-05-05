package com.hypherionmc.craterlib.api.networking;

import com.hypherionmc.craterlib.core.networking.data.PacketHolder;
import com.hypherionmc.craterlib.nojang.network.BridgedFriendlyByteBuf;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

public record CommonPacketWrapper<T>(PacketHolder<T> container, T packet) implements CustomPacketPayload
{
    public void encode(BridgedFriendlyByteBuf buf)
    {
        container().encoder().accept(packet(), buf);
    }

    @Override
    public Type<? extends CustomPacketPayload> type()
    {
        return container.type();
    }
}
