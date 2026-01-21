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

@AutoService(CraterCompatUtils.class)
public class ForgeCompatHelper implements CraterCompatUtils {

    @Override
    public boolean isPlayerActive(CraterPlayer player) {
        return true;
    }

    @Override
    public String getSkinUUID(CraterPlayer player) {
        return player.getStringUUID();
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
        if (!CraterLoader.isModLoaded("ftbessentials"))
            return false;

        return false;
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
