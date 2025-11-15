package com.hypherionmc.craterlib.common;

import com.hypherionmc.craterlib.compat.FabricTailor;
import com.hypherionmc.craterlib.compat.Vanish;
import com.hypherionmc.craterlib.core.platform.CompatUtils;
import com.hypherionmc.craterlib.core.platform.ModloaderEnvironment;
import com.hypherionmc.craterlib.nojang.world.entity.player.BridgedPlayer;
import me.wesley1808.advancedchat.api.AdvancedChatAPI;
import net.kyori.adventure.text.Component;

public class FabricCompatHelper implements CompatUtils {

    @Override
    public boolean isPlayerActive(BridgedPlayer player) {
        if (!ModloaderEnvironment.INSTANCE.isModLoaded("melius-vanish"))
            return true;

        return !Vanish.isPlayerVanished(player.toMojangServerPlayer());
    }

    @Override
    public String getSkinUUID(BridgedPlayer player) {
        return FabricTailor.getTailorSkin(player.toMojangServerPlayer());
    }

    @Override
    public boolean isPlayerBleeding(BridgedPlayer player) {
        return false;
    }

    @Override
    public boolean playerBledOut(BridgedPlayer player) {
        return false;
    }

    @Override
    public boolean playerRevived(BridgedPlayer player) {
        return false;
    }

    @Override
    public boolean isPrivateMessage(BridgedPlayer player) {
        return !AdvancedChatAPI.isPublicChat(player.toMojangServerPlayer());
    }

    @Override
    public Component getChannelPrefix(BridgedPlayer player) {
        net.minecraft.network.chat.Component c = AdvancedChatAPI.getChannelPrefix(player.toMojangServerPlayer());
        return c.getString().isBlank() ? Component.empty() : Component.text(c.getString());
    }
}
