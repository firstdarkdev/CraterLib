package me.hypherionmc.craterlib;

import me.hypherionmc.craterlib.common.FabricCommonHelper;
import me.hypherionmc.craterlib.systems.internal.CreativeTabRegistry;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import org.apache.commons.lang3.tuple.Pair;

import java.util.stream.Collectors;

public class CraterLibInitializer implements ModInitializer {

    @Override
    public void onInitialize() {
        ServerLifecycleEvents.SERVER_STARTING.register(server -> FabricCommonHelper.server = server);

        CreativeTabRegistry.getTABS().forEach(tab -> {
            CreativeModeTab finalTab = FabricItemGroup.builder(tab.getResourceLocation())
                    .title(Component.translatable("itemGroup." +
                            tab.getResourceLocation().toString().replace(":", ".")
                    ))
                    .displayItems((featureFlagSet, output) -> {
                        CreativeTabRegistry
                                .getTabItems()
                                .stream().filter(t -> t.getLeft() == tab)
                                .map(Pair::getRight).forEach(itm -> output.accept(itm.get()));
                    })
                    .icon(tab.getIcon())
                    .build();

            tab.setTab(finalTab);
        });
    }
}
