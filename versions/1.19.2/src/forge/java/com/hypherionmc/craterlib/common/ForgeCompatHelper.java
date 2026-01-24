package com.hypherionmc.craterlib.common;

import com.google.auto.service.AutoService;
import com.hypherionmc.craterlib.api.compat.LuckPermsCompat;
import com.hypherionmc.craterlib.api.compat.ftbranks.FTBRanks;
import com.hypherionmc.craterlib.api.game.text.Text;
import com.hypherionmc.craterlib.api.game.world.entity.player.CraterPlayer;
import com.hypherionmc.craterlib.api.loader.CraterLoader;
import com.hypherionmc.craterlib.core.services.CraterCompatUtils;
import com.hypherionmc.craterlib.impl.compat.LuckPermsCompatImpl;
import com.hypherionmc.craterlib.impl.compat.ftb.FTBRanksImpl;
import dev.ftb.mods.ftbessentials.util.FTBEPlayerData;
import net.minecraft.world.entity.player.Player;
import redstonedubstep.mods.vanishmod.VanishUtil;
import team.creative.playerrevive.api.IBleeding;
import team.creative.playerrevive.server.PlayerReviveServer;

@AutoService(CraterCompatUtils.class)
public class ForgeCompatHelper implements CraterCompatUtils {

    @Override
    public boolean isPlayerActive(CraterPlayer player) {
        if (!CraterLoader.isModLoaded("vmod"))
            return true;

        return !VanishUtil.isVanished(player.unwrap());
    }

    @Override
    public String getSkinUUID(CraterPlayer player) {
        return player.getStringUUID();
    }

    @Override
    public boolean isPlayerBleeding(CraterPlayer player) {
        if (!CraterLoader.isModLoaded("playerrevive"))
            return false;

        return PlayerReviveServer.isBleeding(player.unwrap());
    }

    @Override
    public boolean playerBledOut(CraterPlayer player) {
        if (!CraterLoader.isModLoaded("playerrevive"))
            return false;

        IBleeding bleeding = PlayerReviveServer.getBleeding(player.unwrap());
        return bleeding != null && bleeding.bledOut();
    }

    @Override
    public boolean playerRevived(CraterPlayer player) {
        if (!CraterLoader.isModLoaded("playerrevive"))
            return false;

        IBleeding bleeding = PlayerReviveServer.getBleeding(player.unwrap());
        return bleeding != null && bleeding.revived();
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
        if (!CraterLoader.isModLoaded("ftbessentials"))
            return false;

        FTBEPlayerData data = FTBEPlayerData.get((Player) player.unwrap());
        return data != null && data.muted;
    }

    @Override
    public FTBRanks getFTBRanks() {
        return FTBRanksImpl.INSTANCE;
    }

    @Override
    public LuckPermsCompat getLuckperms() {
        return LuckPermsCompatImpl.INSTANCE;
    }
}
