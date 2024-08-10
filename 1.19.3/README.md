# CraterLib

![badge-snapshot](https://maven.firstdarkdev.xyz/api/badge/latest/snapshots/me/hypherionmc/craterlib/CraterLib-common-1.20-pre6?color=40c14a&name=CraterLib-Snapshot)

***

A Library mod and modding api for easier multi-version minecraft and mod loader development

***

### Supported Minecraft Versions

| Minecraft Version | Support Status |
|-------------------| -------------- |
| < 1.18.2          | ❌              |
| 1.18.2-1.20.2     | ✳️             |
| 1.20.4            | ✳️             |
| 1.21.x            | ✳️             |


- ❌ - Not Supported; no bug fixes or new features.
- 🚧 - Work in Progress; not ready for release.
- ✳️ - Long Term Support; receives changes through backports only.
- ✅ - In Support; the active version, receiving all bugfixes and features directly.

***

## Library Features

* Universal Config System (TOML Based)
* Built in Helper Classes for Various minecraft features
* Built in Optifine-Compat utilities
* Various utilities for Blockstates, LANG, Math and Rendering
* Cross Mod-Loader Events - Based on [Acara](https://github.com/Keksuccino/acara)
* Cross Mod-Loader Config Screens (Based on [Cloth Config Lite](https://github.com/shedaniel/cloth-config-lite))
* Automatic ModMenu and Forge Config screen registration
* Built in Cross Mod-Loader Network system
* Nojang Modding API

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
