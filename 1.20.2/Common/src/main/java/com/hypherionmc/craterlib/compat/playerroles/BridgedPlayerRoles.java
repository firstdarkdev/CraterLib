package com.hypherionmc.craterlib.compat.playerroles;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(staticName = "of")
@Getter
public class BridgedPlayerRoles {

    private final String id;
    private final int index;

}
