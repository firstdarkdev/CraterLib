package com.hypherionmc.craterlib.impl.api.advancements;

import com.hypherionmc.craterlib.api.game.achievements.CraterDisplayInfo;
import com.hypherionmc.craterlib.api.game.text.Text;
import lombok.RequiredArgsConstructor;
import net.minecraft.advancements.DisplayInfo;

@RequiredArgsConstructor(staticName = "wrap")
public class BridgedDisplayInfo implements CraterDisplayInfo {

    private final DisplayInfo internal;

    @Override
    public boolean shouldDisplay() {
        return internal.shouldAnnounceChat();
    }

    @Override
    public boolean isHidden() {
        return internal.isHidden();
    }

    @Override
    public Text displayName() {
        return Text.fromGame(internal.getTitle());
    }

    @Override
    public Text description() {
        return Text.fromGame(internal.getDescription());
    }

    @Override
    public DisplayInfo unwrapInternal() {
        return internal;
    }
}
