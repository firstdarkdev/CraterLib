package me.hypherionmc.craterlib.common;

import me.hypherionmc.craterlib.CraterConstants;
import me.hypherionmc.craterlib.systems.internal.CreativeTabRegistry;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraftforge.event.CreativeModeTabEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = CraterConstants.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ForgeCommonEvents {

    @SubscribeEvent
    public static void registerTabs(CreativeModeTabEvent.Register event) {
        CraterConstants.LOG.info("Registering Creative Tabs");

        CreativeTabRegistry.getTABS().forEach(tab -> {
            CreativeModeTab creativeModeTab = event.registerCreativeModeTab(tab.getResourceLocation(), builder -> {
                builder.title(
                        Component.translatable("itemGroup." +
                                tab.getResourceLocation().toString().replace(":", ".")
                        )
                );

                builder.icon(tab.getIcon());

               if (!tab.getBackgroundSuffix().isEmpty()) {
                   builder.backgroundSuffix(tab.getBackgroundSuffix());
               }
            });
            tab.setTab(creativeModeTab);
        });
    }

    @SubscribeEvent
    public static void registerTabs(CreativeModeTabEvent.BuildContents event) {
        CreativeModeTab tab = event.getTab();

        CreativeTabRegistry.getTabItems().stream()
                .filter(p -> p.getLeft().get() == tab && p.getRight() != null)
                .forEach(itemPair -> event.accept(itemPair.getRight()));
    }

}
