////package com.hypherionmc.craterlib.mixin;
////
////import com.hypherionmc.craterlib.api.events.server.CraterServerChatEvent;
////import com.hypherionmc.craterlib.api.game.text.Text;
////import com.hypherionmc.craterlib.core.event.CraterEventBus;
////import com.hypherionmc.craterlib.impl.api.world.entity.player.BridgedPlayer;
////import net.minecraft.network.chat.Component;
////import net.minecraft.network.chat.PlayerChatMessage;
////import net.minecraft.server.level.ServerPlayer;
////import net.minecraft.server.network.FilteredText;
////import net.minecraft.server.network.ServerGamePacketListenerImpl;
//import net.minecraftforge.common.ForgeHooks;
////import org.spongepowered.asm.mixin.Mixin;
////import org.spongepowered.asm.mixin.Shadow;
////import org.spongepowered.asm.mixin.injection.At;
////import org.spongepowered.asm.mixin.injection.Inject;
////import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
//import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
////
//@Mixin(value = ForgeHooks.class, priority = Integer.MIN_VALUE)
////public class ServerGamePacketListenerImplMixin {
////
////    @Inject(
//            method = "onServerChatSubmittedEvent",
////            at = @At("HEAD"),
////            cancellable = true
////    )
//    private static void injectChatEvent(ServerPlayer player, Component message, CallbackInfoReturnable<Component> ci) {
//        CraterServerChatEvent event = new CraterServerChatEvent(BridgedPlayer.wrap(player), message.getString(), Text.fromGame(message));
////        CraterEventBus.INSTANCE.postEvent(event);
////        if (event.wasCancelled())
////            ci.cancel();
////    }
////
////}
