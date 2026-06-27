package com.hypherionmc.craterlib.compat;

import com.hypherionmc.craterlib.api.loader.CraterLoader;
import net.minecraft.server.level.ServerPlayer;
import org.samo_lego.fabrictailor.casts.TailoredPlayer;

public class FabricTailor {

    public static String getTailorSkin(ServerPlayer player) {
        if (!CraterLoader.isModLoaded("fabrictailor"))
            return player.getStringUUID();

        try {
           if (player instanceof TailoredPlayer tp) {
               return tp.getSkinId();
           }
        } catch (Exception e) {}

        return player.getStringUUID();
    }

}