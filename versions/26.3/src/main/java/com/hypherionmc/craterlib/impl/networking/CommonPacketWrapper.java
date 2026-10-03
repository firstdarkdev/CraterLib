package com.hypherionmc.craterlib.impl.networking;

import com.hypherionmc.craterlib.api.game.network.CraterFriendlyByteBuf;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

public record CommonPacketWrapper<T>(CommonPacketHolder<T> container, T packet) implements CustomPacketPayload {

    public void encode(CraterFriendlyByteBuf buf) {
        container().getEncoder().accept(packet(), buf);
    }

    @Override
    public Type<? extends CustomPacketPayload> type()
    {
        return container.getType();
    }
}
