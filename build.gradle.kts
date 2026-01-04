import codechicken.diffpatch.util.PatchMode

plugins {
    alias(libs.plugins.orion)
}

orionporting {
    upstreamBranch = "3.0.0/dev"
    patchMode = PatchMode.FUZZY
    porting(
        // 1.21.x
        "1.21.11",
        "1.21.9",
        "1.21.6",
        "1.21.5",
        "1.21.3",
        "1.21",

        // 1.20.x
        "1.20",

        // 1.19.x
        "1.19.2",

        // 1.18.x
        "1.18.2"
        )
}