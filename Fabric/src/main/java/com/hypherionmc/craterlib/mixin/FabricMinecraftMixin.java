package com.hypherionmc.craterlib.mixin;

import com.hypherionmc.craterlib.core.systems.internal.CreativeTabRegistry;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.client.Minecraft;
import net.minecraft.client.main.GameConfig;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import org.apache.commons.lang3.tuple.Pair;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public class FabricMinecraftMixin {

    @Inject(method = "<init>", at = @At("RETURN"))
    private void injectCraterLateInit(GameConfig gameConfig, CallbackInfo ci) {
        CreativeTabRegistry.getTabs().forEach(tab -> {
            CreativeModeTab finalTab = FabricItemGroup.builder()
                    .title(Component.translatable("itemGroup." +
                            tab.getResourceLocation().toString().replace(":", ".")
                    ))
                    .icon(tab.getIcon())
                    .build();

            tab.setTab(finalTab);

            ItemGroupEvents.modifyEntriesEvent(tab.getResourceKey()).register(entries -> CreativeTabRegistry
                    .getTabItems()
                    .stream().filter(t -> t.getLeft().get() == finalTab && t.getRight() != null)
                    .map(Pair::getRight).forEach(itm -> entries.accept(itm.get())));
        });
    }

}
