package com.hypherionmc.craterlib.impl.api.network.protocol.status;

import com.hypherionmc.craterlib.api.game.network.protocol.status.CraterServerStatus;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public final class WrappedServerStatus {

    public static final class WrappedFavicon implements CraterServerStatus.CraterFavIcon {

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

        @Override
        public byte[] iconBytes() {
            return iconBytes;
        }

        @Override
        public String unwrapInternal() {
            return internal;
        }

    }

}
