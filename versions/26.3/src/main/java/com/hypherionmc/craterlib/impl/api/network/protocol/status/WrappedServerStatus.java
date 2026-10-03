package com.hypherionmc.craterlib.impl.api.network.protocol.status;

import com.hypherionmc.craterlib.api.game.network.protocol.status.CraterServerStatus;
import net.minecraft.network.protocol.status.ServerStatus;
import org.jetbrains.annotations.ApiStatus;

public final class WrappedServerStatus {

    public static final class WrappedFavicon implements CraterServerStatus.CraterFavIcon {

        private final ServerStatus.Favicon internal;

        public WrappedFavicon(byte[] iconBytes) {
            internal = new ServerStatus.Favicon(iconBytes);
        }

        @ApiStatus.Internal
        public WrappedFavicon(ServerStatus.Favicon internal) {
            this.internal = internal;
        }

        @Override
        public byte[] iconBytes() {
            return internal.iconBytes();
        }

        @Override
        public ServerStatus.Favicon unwrapInternal() {
            return internal;
        }

    }

}
