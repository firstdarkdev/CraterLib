package com.hypherionmc.craterlib;


import com.hypherionmc.craterlib.api.game.text.Text;
import com.hypherionmc.craterlib.impl.MojangTextAdapter;

public class CraterConstants {
    public static final String MOD_ID = "craterlib";

    public static void setupLibrary() {
        Text.setTextAdapter(new MojangTextAdapter());
    }

}
