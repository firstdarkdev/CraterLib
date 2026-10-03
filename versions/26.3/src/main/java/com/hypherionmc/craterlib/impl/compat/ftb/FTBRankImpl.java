package com.hypherionmc.craterlib.impl.compat.ftb;

import com.hypherionmc.craterlib.api.compat.ftbranks.CraterFTBRank;
import dev.ftb.mods.ftbranks.api.Rank;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(staticName = "wrap")
public class FTBRankImpl implements CraterFTBRank {

    private final Rank internal;

    @Override
    public String name() {
        return internal.getName();
    }

    @Override
    public String id() {
        return internal.getId();
    }

    @Override
    public Rank unwrapInternal() {
        return internal;
    }
}
