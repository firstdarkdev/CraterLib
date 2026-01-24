package com.hypherionmc.craterlib.api.game.achievements;

import com.hypherionmc.craterlib.api.game.CraterWrappedAPI;
import com.hypherionmc.craterlib.api.game.text.Text;

public interface CraterDisplayInfo extends CraterWrappedAPI {

    boolean shouldDisplay();
    boolean isHidden();
    Text displayName();
    Text description();

}
