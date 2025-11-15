package com.hypherionmc.craterlib.nojang.advancements;

import com.hypherionmc.craterlib.utils.ChatUtils;
import lombok.RequiredArgsConstructor;
import net.kyori.adventure.text.Component;
import net.minecraft.advancements.DisplayInfo;

@RequiredArgsConstructor(staticName = "of")
public class BridgedDisplayInfo {

    private final DisplayInfo internal;

    public boolean shouldDisplay() {
        return internal.shouldAnnounceChat();
    }

    public boolean isHidden() {
        return internal.isHidden();
    }

    public Component displayName() {
        return ChatUtils.mojangToAdventure(internal.getTitle());
    }

    public Component description() {
        return ChatUtils.mojangToAdventure(internal.getDescription());
    }

}
