package com.hypherionmc.craterlib.compat;

import com.hypherionmc.craterlib.core.platform.ModloaderEnvironment;
import net.minecraft.server.level.ServerPlayer;
import org.samo_lego.fabrictailor.casts.TailoredPlayer;
public class FabricTailor {

    public static String getTailorSkin(ServerPlayer player) {
        if (!ModloaderEnvironment.INSTANCE.isModLoaded("fabrictailor"))
            return player.getStringUUID();

        try {
           if (player instanceof TailoredPlayer tp) {
               return tp.getSkinId();
           }
        } catch (Exception e) {
           e.printStackTrace();
        }

        return player.getStringUUID();
    }

}