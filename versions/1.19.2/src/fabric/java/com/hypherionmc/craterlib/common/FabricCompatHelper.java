package com.hypherionmc.craterlib.common;

import com.google.auto.service.AutoService;
import com.hypherionmc.craterlib.api.compat.LuckPermsCompat;
import com.hypherionmc.craterlib.api.compat.ftbranks.FTBRanks;
import com.hypherionmc.craterlib.api.game.text.Text;
import com.hypherionmc.craterlib.api.game.world.entity.player.CraterPlayer;
import com.hypherionmc.craterlib.api.loader.CraterLoader;
import com.hypherionmc.craterlib.compat.FabricTailor;
import com.hypherionmc.craterlib.compat.Vanish;
import com.hypherionmc.craterlib.core.services.CraterCompatUtils;
import com.hypherionmc.craterlib.impl.compat.LuckPermsCompatImpl;
import dev.ftb.mods.ftbessentials.util.FTBEPlayerData;
import me.wesley1808.advancedchat.api.AdvancedChatAPI;
import net.minecraft.world.entity.player.Player;

import java.util.Optional;

@AutoService(CraterCompatUtils.class)
public class FabricCompatHelper implements CraterCompatUtils {

    @Override
    public boolean isPlayerActive(CraterPlayer player) {
        if (!CraterLoader.isModLoaded("melius-vanish"))
            return true;

        return !Vanish.isPlayerVanished(player.unwrap());
    }

    @Override
    public String getSkinUUID(CraterPlayer player) {
        return FabricTailor.getTailorSkin(player.unwrap());
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
        return !AdvancedChatAPI.isPublicChat(player.unwrap());
    }

    @Override
    public Text getChannelPrefix(CraterPlayer player) {
        net.minecraft.network.chat.Component c = AdvancedChatAPI.getChannelPrefix(player.unwrap());
        return c.getString().isBlank() ? Text.empty() : Text.literal(c.getString());
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
        return FTBRanks.getInstance();
    }

    @Override
    public LuckPermsCompat getLuckperms() {
        return LuckPermsCompatImpl.INSTANCE;
    }
}
