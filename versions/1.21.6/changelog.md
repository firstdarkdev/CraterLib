**New Features**

- Added API for Player Roles (https://modrinth.com/mod/player-roles) - HypherionSA
- Added GameMode API to Player - HypherionSA
- Added API for Advancement Progress - KarmaAlex

**This is a re-release of 3.0.0 with the following fixes**:

- Fixes various startup crashes and mixin crashes across all versions and loader
- Thou shalt not release to prod on a Friday night.........

My apologies to everyone for this very disastrous release.


**Bug Fixes**

- Fixed Events fired from mixins that error out breaking the game - HypherionSA

**Changes**

- Forge is back (for now. If the amount of users aren't worth it, it's gone for good) - HypherionSA/Kaleidio
- Removed all deprecated code - HypherionSA
- Remove Legacy Embedded RPC SDK (Breaks Simple RPC 3. If you still need it, please open an issue) - HypherionSA
- Removed 1.19.4, 1.20.2-1.20.4 from support - These versions hardly have any users left. We still provide tech support for it - HypherionSA

**Technical Changes**

- Switched to a new build system - HypherionSA
- Moved from Groovy to Kotlin - HypherionSA
