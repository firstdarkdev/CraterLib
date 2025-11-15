package com.hypherionmc.craterlib.nojang.network.protocol.status;

import net.minecraft.network.protocol.status.ServerStatus;
import org.jetbrains.annotations.ApiStatus;

public final class WrappedServerStatus {

    public static final class WrappedFavicon {

        private final ServerStatus.Favicon internal;

        public WrappedFavicon(byte[] iconBytes) {
            internal = new ServerStatus.Favicon(iconBytes);
        }

        @ApiStatus.Internal
        public WrappedFavicon(ServerStatus.Favicon internal) {
            this.internal = internal;
        }

        public byte[] iconBytes() {
            return internal.iconBytes();
        }

        public ServerStatus.Favicon toMojang() {
            return internal;
        }

    }

}
