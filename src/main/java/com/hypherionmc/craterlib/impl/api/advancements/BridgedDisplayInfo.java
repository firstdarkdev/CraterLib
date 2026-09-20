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
        return internal.announceToChat();
    }

    @Override
    public boolean isHidden() {
        return internal.hidden();
    }

    @Override
    public Text displayName() {
        return Text.fromGame(internal.title());
    }

    @Override
    public Text description() {
        return Text.fromGame(internal.description());
    }

    @Override
    public DisplayInfo unwrapInternal() {
        return internal;
    }
}
