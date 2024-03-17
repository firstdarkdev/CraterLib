package com.hypherionmc.craterlib.network;

import com.hypherionmc.craterlib.core.network.CraterPacket;
import com.hypherionmc.craterlib.network.impl.FabricClientNetworkHelper;
import com.hypherionmc.craterlib.network.impl.FabricServerNetworkHelper;
import net.fabricmc.api.EnvType;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

import java.util.function.Function;

@Deprecated(forRemoval = true)
public interface FabricNetworkHelper {

    /* FABRIC ONLY */
    void registerClientReceiver(@NotNull ResourceLocation channelName, @NotNull Function<FriendlyByteBuf, @NotNull CraterPacket<?>> factory);
    void registerServerReceiver(ResourceLocation channelName, Function<FriendlyByteBuf, CraterPacket<?>> factory);

    public static FabricNetworkHelper getForDist(EnvType dist) {
        switch (dist) {
            case CLIENT -> {
                return new FabricClientNetworkHelper();
            }
            case SERVER -> {
                return new FabricServerNetworkHelper();
            }
        }
        return null;
    }

}
