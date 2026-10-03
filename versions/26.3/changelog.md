**Bug Fixes**:

- Fix Paper bootstrap crash due to class loading on modern mc versions - VelizarBG
- Fix Paper command errors when it tries to handle non player commands - VelizarBG

**API Changes**:

- Add active FTB Ranks lookup for online players. Fixes bug in SDLink where condition ranks in FTB Teams do not work. - John Clardy

**New Features**:

- Added noSave() api to CraterServer for reasons that are none of your concern (possible new mod) - HypherionSA
- Added `getHunger`, `getArmor()` and `getSaturation()` api's to CraterPlayer - HypherionSA
- Added dual fired chat events, that mods like Simple Discord Link can use to determine if it needs to ignore a chat message - HypherionSA
- Probably added more bugs to fix later - HypherionSA