package com.hypherionmc.craterlib.api.game.world.level;

import com.hypherionmc.craterlib.api.game.text.Text;

public interface CraterGameType {

    String getName();
    String getSerializedName();
    Text getLongDisplayName();
    Text getShortDisplayName();
    boolean isBlockPlacingRestricted();
    boolean isCreative();
    boolean isSurvival();
    Type getMode();

    enum Type {
        SURVIVAL("survival"),
        CREATIVE("creative"),
        ADVENTURE("adventure"),
        SPECTATOR("spectator");

        private final String name;

        Type(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return this.name;
        }
    }
}
