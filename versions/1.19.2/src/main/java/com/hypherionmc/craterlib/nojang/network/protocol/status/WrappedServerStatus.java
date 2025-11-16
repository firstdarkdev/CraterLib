package com.hypherionmc.craterlib.nojang.network.protocol.status;

import net.minecraft.network.protocol.status.ServerStatus;
import org.jetbrains.annotations.ApiStatus;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public final class WrappedServerStatus {

    public static final class WrappedFavicon {

        private final String internal;
        private final byte[] iconBytes;

        public WrappedFavicon(byte[] iconBytes) {
            this.iconBytes = iconBytes;

            if (iconBytes != null) {
                byte[] encoded = Base64.getEncoder().encode(iconBytes);
                internal = "data:image/png;base64," + new String(encoded, StandardCharsets.UTF_8);
            } else {
                internal = null;
            }
        }

        @ApiStatus.Internal
        public WrappedFavicon(String internal) {
            this.internal = internal;
            this.iconBytes = new byte[0];
        }

        public byte[] iconBytes() {
            return iconBytes;
        }

        public String toMojang() {
            return internal;
        }

    }

}
