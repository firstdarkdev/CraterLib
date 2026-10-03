package com.hypherionmc.craterlib.api.compat.playerroles;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(staticName = "wrap")
@Getter
public class BridgedPlayerRoles {

    private final String id;
    private final int index;

}
