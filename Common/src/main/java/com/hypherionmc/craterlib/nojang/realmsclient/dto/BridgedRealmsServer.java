package com.hypherionmc.craterlib.nojang.realmsclient.dto;

import com.mojang.realmsclient.dto.RealmsServer;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(staticName = "of")
public class BridgedRealmsServer {

    private final RealmsServer internal;

}
