package com.hypherionmc.craterlib.nojang.network.protocol.status;

// TODO: Implement if Possible
public final class WrappedServerStatus {

    public static final class WrappedFavicon {

        //private final ServerStatus.Favicon internal;

        public WrappedFavicon(byte[] iconBytes) {
            //internal = new ServerStatus.Favicon(iconBytes);
        }

//        @ApiStatus.Internal
//        public WrappedFavicon(ServerStatus.Favicon internal) {
//            this.internal = internal;
//        }

        public byte[] iconBytes() {
            return new byte[0];
        }

//        public ServerStatus.Favicon toMojang() {
//            return internal;
//        }

    }

}
