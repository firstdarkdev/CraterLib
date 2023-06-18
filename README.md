# CraterLib

![badge-snapshot](https://maven.firstdarkdev.xyz/api/badge/latest/snapshots/me/hypherionmc/craterlib/CraterLib-common-1.20-pre6?color=40c14a&name=CraterLib-Snapshot)

***

A library mod used by HypherionSA and First Dark Development mods to make porting and multiple modloader support easier

***

## Library Features

* Universal Config System (TOML Based)
* Easy Cross Mod-Loader registration System
* Built in Helper Classes for Various minecraft features
* Built in FluidTank and Energy systems for Forge/Fabric (Forge versions are just wrappers).
* Built in Optifine-Compat utilities
* Various utilities for Blockstates, LANG, Math and Rendering
* Cross Mod-Loader Events - Based on [Acara](https://github.com/Keksuccino/acara)
* Cross Mod-Loader Config Screens (Based on [Cloth Config Lite](https://github.com/shedaniel/cloth-config-lite))
* Automatic ModMenu and Forge Config screen registration
* Built in Cross Mod-Loader Network system
* WIP: Various GUI widgets and Utilities
* WIP: Texture Utils
* TODO: Sync Config From Server to Client

***

## Setup Instructions

There's a **wiki coming soon**, but for now, here's some basic instructions for building the project:

1. `git clone` the project to a safe spot.
2. Install Java's JDK 17. Make sure you have the development version explicitly:
    * Fedora: `sudo dnf install java-17-openjdk-devel`
    * Ubuntu: `sudo apt install openjdk-17-jdk`
    * macOS: `brew install openjdk@17`
3. Set it accordingly:
    * Windows/macOS: Set the `JAVA_HOME` environment variable or use system settings
    * Linux: `sudo update-alternatives --config java`
4. Navigate to the CraterLib folder, then run a `gradlew` file depending on your operating system:
    * Windows: `.\gradlew.bat build`
    * macOS/Linux/BSD: `chmod +x gradlew` and `./gradlew`
