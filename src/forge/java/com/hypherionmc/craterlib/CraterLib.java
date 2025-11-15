package com.hypherionmc.craterlib;

import net.minecraftforge.fml.common.Mod;

@Mod(CraterConstants.MOD_ID)
public class CraterLib {

    public CraterLib() {
        /*MinecraftForge.EVENT_BUS.register(new ForgeServerEvents());
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::commonSetup);*/
    }

    /*public void commonSetup(FMLCommonSetupEvent evt) {
        new CraterPacketNetwork(new CraterForgeNetworkHandler(FMLLoader.getDist().isClient() ? PacketSide.CLIENT : PacketSide.SERVER));
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> {
            LateInitEvent event = new LateInitEvent(new BridgedMinecraft(), BridgedOptions.of(Minecraft.getInstance().options));
            CraterEventBus.INSTANCE.postEvent(event);
        });
    }*/
}
