package com.hypherionmc.craterlib.mixin.events;

import com.hypherionmc.craterlib.api.events.server.ServerStatusEvent;
import com.hypherionmc.craterlib.core.event.CraterEventBus;
import com.hypherionmc.craterlib.nojang.network.protocol.status.WrappedServerStatus;
import net.minecraft.network.protocol.status.ServerStatus;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

@Mixin(ServerStatus.class)
public class ServerStatusMixin {

    @Inject(method = "getFavicon", at = @At("RETURN"), cancellable = true)
    private void injectIconEvent(CallbackInfoReturnable<String> cir) {
        ServerStatusEvent.FaviconRequestEvent event = new ServerStatusEvent.FaviconRequestEvent(cir.getReturnValue().isEmpty() ? Optional.empty() : Optional.of(new WrappedServerStatus.WrappedFavicon(cir.getReturnValue())));
        CraterEventBus.INSTANCE.postEvent(event);

        if (event.getNewIcon().isPresent()) {
            cir.setReturnValue(event.getNewIcon().get().toMojang());
        }
    }

}
