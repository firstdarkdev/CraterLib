package com.hypherionmc.craterlib.api.game.commands;

import com.hypherionmc.craterlib.api.game.CraterWrappedAPI;
import com.hypherionmc.craterlib.api.game.text.Text;
import com.hypherionmc.craterlib.api.game.world.entity.player.CraterPlayer;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

public interface CraterCommandSourceStack extends CraterWrappedAPI {

    void sendSuccess(Supplier<Text> messageSupplier, boolean isTitle);
    void sendMessage(Text message);
    void sendFailure(Text message);
    boolean isPlayer();
    @Nullable CraterPlayer getPlayer();

}
