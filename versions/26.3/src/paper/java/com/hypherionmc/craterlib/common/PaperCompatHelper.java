package com.hypherionmc.craterlib.common;

import com.google.auto.service.AutoService;
import com.hypherionmc.craterlib.api.compat.LuckPermsCompat;
import com.hypherionmc.craterlib.api.compat.ftbranks.FTBRanks;
import com.hypherionmc.craterlib.api.game.text.Text;
import com.hypherionmc.craterlib.api.game.world.entity.player.CraterPlayer;
import com.hypherionmc.craterlib.api.loader.CraterLoader;
import com.hypherionmc.craterlib.core.services.CraterCompatUtils;
import com.hypherionmc.craterlib.impl.compat.ftb.FTBRanksImpl;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;

import java.lang.reflect.Method;

@AutoService(CraterCompatUtils.class)
public class PaperCompatHelper implements CraterCompatUtils {

    @Override
    public boolean isPlayerActive(CraterPlayer player) {
        // Essentials Vanish
        if (CraterLoader.isModLoaded("Essentials")) {
            return !isEssentialsVanished(player);
        }

        // PhantomAdmin Vanish
        if (CraterLoader.isModLoaded("PhantomAdmin"))
            return !isPhantomVanished(player);

        // Other vanish mods
        try {
            Player p = player.unwrap();
            for (MetadataValue meta : p.getMetadata("vanished")) {
                if (meta.asBoolean()) return true;
            }
        } catch (Exception ignored) {}

        return true;
    }

    @Override
    public String getSkinUUID(CraterPlayer player) {
        return player.getStringUUID();
    }

    private boolean isEssentialsVanished(CraterPlayer player) {
        try {
            Plugin p = Bukkit.getPluginManager().getPlugin("Essentials");
            if (p == null)
                return false;

            Method getUser = p.getClass().getMethod("getUser", String.class);
            Object essentialsPlayer = getUser.invoke(p, player.getName().asString());

            if (essentialsPlayer != null) {
                Method isVanished = essentialsPlayer.getClass().getMethod("isVanished");
                return (boolean) isVanished.invoke(essentialsPlayer);
            }
        } catch (Exception ignored) {}

        return false;
    }

    private boolean isPhantomVanished(CraterPlayer player) {
        try {
            Plugin p = Bukkit.getPluginManager().getPlugin("PhantomAdmin");
            if (p == null)
                return false;

            Method isInvisible = p.getClass().getDeclaredMethod("isInvisible", Player.class);
            isInvisible.setAccessible(true);

            return (boolean) isInvisible.invoke(p, (Player) player.unwrap());
        } catch (Exception ignored) {
            ignored.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean isPlayerBleeding(CraterPlayer player) {
        return false;
    }

    @Override
    public boolean playerBledOut(CraterPlayer player) {
        return false;
    }

    @Override
    public boolean playerRevived(CraterPlayer player) {
        return false;
    }

    @Override
    public boolean isPrivateMessage(CraterPlayer player) {
        return false;
    }

    @Override
    public Text getChannelPrefix(CraterPlayer player) {
        return Text.empty();
    }

    @Override
    public boolean isPlayerMuted(CraterPlayer player) {
        return false;
    }

    @Override
    public FTBRanks getFTBRanks() {
        return FTBRanksImpl.INSTANCE;
    }

    @Override
    public LuckPermsCompat getLuckperms() {
        return LuckPermsCompat.getInstance();
    }
}
