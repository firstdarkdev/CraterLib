import com.hypherionmc.modpublisher.plugin.ModPublisherGradleExtension
import com.hypherionmc.orion.plugin.multimined.MultiMinedExtension

plugins {
    java
    id("maven-publish")
    alias(libs.plugins.unimined)
    alias(libs.plugins.shadow)
    alias(libs.plugins.modpublisher)
    alias(libs.plugins.orion)
    //alias(libs.plugins.origami)
    alias(libs.plugins.multimined)
}

group = orion.getProperty("project_group")

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

// Region Tooling
orion.setup {
    multiProject.set(true)
    enableMirrorMaven.set(true)
    enableReleasesMaven.set(true)
}

multimined.setup {
    multiLoader = true
    version(orion.getProperty("minecraft_version"))

    val commonShadow: MultiMinedExtension.ShadowJarConfig.() -> Unit = {
        exclude("com.google.code.gson:.*")
        relocate("me.hypherionmc.moonconfig" to "shadow.hypherionmc.moonconfig")
        relocate("me.hypherionmc.mcdiscordformatter" to "shadow.hypherionmc.mcdiscordformatter")
        mergeServiceFiles()
    }

    shadowJar {
        commonShadow()
        relocate("net.kyori" to "shadow.kyori")
    }

    fabric {
        version(orion.getProperty("fabric_loader"))

        shadowJar {
            commonShadow()
            relocate("net.kyori" to "shadow.kyori")
        }
    }

//    neoforge {
//        version(orion.getProperty("neoforge_version"))
//
//        shadowJar {
//            commonShadow()
//            exclude("META-INF/versions/**")
//            relocate("net.kyori" to "shadow.kyori")
//        }
//
//        mixinConfig("${orion.getProperty("mod_id")}.mixins.json", "${orion.getProperty("mod_id")}.neoforge.mixins.json")
//    }

//    paper {
//        version(orion.getProperty("paper_loader"))
//
//        shadowJar {
//            commonShadow()
//            exclude("net.kyori:.*", "linux-x86-64/**", "win32-x86/**", "win32-x86-64/**", "darwin/**")
//            mergeServiceFiles()
//        }
//    }

    forge {
        version(orion.getProperty("forge_version"))

        shadowJar {
            commonShadow()
            exclude("META-INF/versions/**")
            relocate("net.kyori" to "shadow.kyori")
        }

        mixinConfig("${orion.getProperty("mod_id")}.mixins.json", "${orion.getProperty("mod_id")}.forge.mixins.json")
    }
}

//origami {
//    sourceSet.set("paper")
//
//    // These classes cannot, and will not work on paper and will cause errors, so we exclude them
//    excludedPackages = listOf(
//        "com.hypherionmc.craterlib.client",
//        "com.hypherionmc.craterlib.mixin",
//        "com.hypherionmc.craterlib.nojang.client",
//        "com.hypherionmc.craterlib.core.rpcsdk",
//        "com.hypherionmc.craterlib.nojang.realmsclient"
//    )
//
//    // These resources cannot, and will not work on paper, so we exclude them
//    excludedResources = listOf(
//        "pack.mcmeta", "craterlib.mixins.json"
//    )
//}
// endregion

repositories {
    orion.unimaven()
}

dependencies {
    // Region All Projects
    multimined.shade("me.hypherionmc.moon-config:core:${orion.getProperty("moon_config")}")
    multimined.shade("me.hypherionmc.moon-config:toml:${orion.getProperty("moon_config")}")
    multimined.shade("me.hypherionmc.moon-config:json:${orion.getProperty("moon_config")}")
    multimined.shade("me.hypherionmc.sdlink:mcdiscordformatter-1.20.3:${orion.getProperty("discord_formatter")}")
    multimined.shade("net.kyori:adventure-api:${orion.getProperty("adventure")}")
    multimined.shade("net.kyori:adventure-text-serializer-gson:${orion.getProperty("adventure")}")
    multimined.shade("net.kyori:adventure-text-minimessage:${orion.getProperty("adventure")}")

    compileOnly("net.luckperms:api:5.4")
    compileOnly("org.projectlombok:lombok:${orion.getProperty("lombok")}")
    annotationProcessor("org.projectlombok:lombok:${orion.getProperty("lombok")}")
    // endregion

    // region Common
    multimined.main("dev.ftb.mods:ftb-essentials:${orion.getProperty("ftb_essentials")}")
    multimined.main("dev.ftb.mods:ftb-ranks:${orion.getProperty("ftb_ranks")}")
    multimined.main("me.shedaniel.cloth:cloth-config:${orion.getProperty("cloth_config")}")
    // endregion

    // region Fabric
    multimined.fabricApi()

    multimined.fabric("com.terraformersmc:modmenu:${orion.getProperty("mod_menu_version")}")// { exclude(group = "net.fabricmc.fabric-api") }
    multimined.fabric("dev.ftb.mods:ftb-essentials:${orion.getProperty("ftb_essentials")}")
    multimined.fabric("dev.ftb.mods:ftb-ranks:${orion.getProperty("ftb_ranks")}")
    multimined.fabric("me.shedaniel.cloth:cloth-config-fabric:${orion.getProperty("cloth_config")}")
    multimined.fabric("unimaven.modrinth:fabrictailor:${orion.getProperty("fabrictailor")}")
    multimined.fabric("unimaven.modrinth:vanish:${orion.getProperty("vanish")}")
    multimined.fabric("unimaven.modrinth:advanced-chat:${orion.getProperty("advanced_chat")}")
    multimined.fabric("unimaven.modrinth:player-roles:1.6.1")
    multimined.fabric("dev.gegy:player-roles-api:1.6.1")
    // endregion

    // region NeoForge
    multimined.neoforge("unimaven.modrinth:vanishmod:${orion.getProperty("vanishmod_neo")}")
    multimined.neoforge("dev.ftb.mods:ftb-essentials-neoforge:${orion.getProperty("ftb_essentials")}")
    multimined.neoforge("dev.ftb.mods:ftb-ranks-neoforge:${orion.getProperty("ftb_ranks")}")
    multimined.neoforge("me.shedaniel.cloth:cloth-config-neoforge:${orion.getProperty("cloth_config")}")
    multimined.neoforge("unimaven.curseforge:playerrevive-266890:${orion.getProperty("player_revive")}")
    multimined.neoforge("unimaven.curseforge:creativecore-257814:${orion.getProperty("creative_core")}")
    // endregion

    // region Forge
    multimined.forge("unimaven.modrinth:vanishmod:${orion.getProperty("vanishmod")}") // Not Available
    multimined.forge("dev.ftb.mods:ftb-essentials-forge:${orion.getProperty("ftb_essentials")}") // Not Available
    multimined.forge("dev.ftb.mods:ftb-ranks-forge:${orion.getProperty("ftb_ranks")}") // Not Available
    multimined.forge("me.shedaniel.cloth:cloth-config-forge:${orion.getProperty("cloth_config")}") // Not Available
    multimined.forge("unimaven.curseforge:playerrevive-266890:${orion.getProperty("player_revive")}") // Not Available
    multimined.forge("unimaven.curseforge:creativecore-257814:${orion.getProperty("creative_core")}") // Not Available
    // endregion
}

// region ModPublisher

// Main Setup (shared across projects)
publisher {
    apiKeys {
        modrinth(System.getenv("MODRINTH_TOKEN"))
        curseforge(System.getenv("CURSE_TOKEN"))
        nightbloom(System.getenv("PLATFORM_KEY"))
    }

    debug.set(true)
    curseID.set(orion.getProperty("curse_id"))
    modrinthID.set(orion.getProperty("modrinth_id"))
    nightbloomID.set("craterlib")
    versionType.set("release")
    changelog.set(rootProject.file("changelog.md"))
    projectVersion.set("${orion.getProperty("minecraft_version")}-${project.version}")
    gameVersions.set(orion.getProperty("supported_mc").split(",").toList())
    setCurseEnvironment("both")
    isManualRelease.set(true)

    curseDepends {
        optional("cloth-config")
    }

    modrinthDepends {
        optional("cloth-config")
    }
}

// Fabric Setup
(extensions.getByName("publisherFabric") as ModPublisherGradleExtension).apply {
    projectName = "Fabric"
    displayName.set("[Fabric ${orion.getProperty("displayed_versions")}] CraterLib - ${project.version}")
    artifact.set(tasks.named("remapFabricJar").get())
    loaders.set(listOf("fabric", "quilt"))

    curseDepends {
        required("fabric-api")
    }

    modrinthDepends {
        required("fabric-api")
    }
}

// NeoForge Setup
if (multimined.platformEnabled("neoforge")) {
    (extensions.getByName("publisherNeoforge") as ModPublisherGradleExtension).apply {
        projectName = "NeoForge"
        displayName.set("[NeoForge ${orion.getProperty("displayed_versions")}] CraterLib - ${project.version}")
        artifact.set(tasks.named("remapNeoforgeJar").get())
        loaders.set(listOf("neoforge"))
    }
}

// Forge Setup
if (multimined.platformEnabled("forge")) {
    (extensions.getByName("publisherForge") as ModPublisherGradleExtension).apply {
        projectName = "Forge"
        displayName.set("[Forge ${orion.getProperty("displayed_versions")}] CraterLib - ${project.version}")
        artifact.set(tasks.named("remapForgeJar").get())
        loaders.set(listOf("forge"))
    }
}

// Paper Setup
if (multimined.platformEnabled("paper")) {
    (extensions.getByName("publisherPaper") as ModPublisherGradleExtension).apply {
        projectName = "Paper"
        displayName.set("[Paper ${orion.getProperty("displayed_versions")}] CraterLib - ${project.version}")
        artifact.set(tasks.named("remapPaperJar").get())
        loaders.set(listOf("paper"))
    }
}

tasks.named("publishCurseforgePaper") { enabled = false }
tasks.named("publishModrinthPaper") { enabled = false }
// endregion

// region Maven Publishing
publishing {
    // The rest of this is automatically configured by Orion
    repositories {
        maven {
            orion.getPublishingMaven()
        }
    }
}
// endregion
