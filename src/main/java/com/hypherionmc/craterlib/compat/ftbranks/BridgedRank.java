// @excludeplugin
package com.hypherionmc.craterlib.compat.ftbranks;

import dev.ftb.mods.ftbranks.api.Rank;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(staticName = "of")
public class BridgedRank {

    private final Rank internal;

    public String name() {
        return internal.getName();
    }

    public String id() {
        return internal.getId();
    }

    public Rank toFtb() {
        return internal;
    }

}
