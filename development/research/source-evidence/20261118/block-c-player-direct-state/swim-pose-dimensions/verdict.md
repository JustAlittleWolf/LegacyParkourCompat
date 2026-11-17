# Swim pose and dimensions audit

TERMINAL: COMPLETED — the bounded claim is independently confirmed against canonical decompiled sources. A swimming-driven player dimension and eye-height branch is absent in 1.12.2 and present in 1.13; the checked 1.13.1 and 1.13.2 aliases retain it, and 1.14.4 confirms the same swimming dimension and eye-height values through its pose map.

## Frozen input and source identity

- Frozen comparison: `swim-pose-dimensions-source/comparison-r2.md`
- Frozen comparison SHA-256: `3a4dbdc621baba520fd6aca43da8ce13d087e31a355ddaefa5c1f403348c7f83` (verified against the assigned digest).
- Canonical source root: `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft`; read-only.
- For each listed version/mapping, the sibling `.ready.json` names the exact release and mapping and its source-manifest digest matches the SHA-256 of the sibling `.sources.sha256`. Each bounded Java file hash below matches its source-manifest path entry. No source was regenerated.

| Exact version / mapping | Relevant source file | SHA-256 |
|---|---|---|
| 1.12.2 / ornithe-feather | `net/minecraft/entity/living/player/PlayerEntity.java` | `e4e0fdbe07a7d0a0ae4a70cbb6739a287c9d045a4a12b409895c220b5d91fe1e` |
| 1.13 / ornithe-feather | `net/minecraft/entity/living/player/PlayerEntity.java` | `4a57ce65405436aeb211d7a04a9a9f6ae1a661d033fd3ba2424909bfb42b8acd` |
| 1.13.1 / ornithe-feather | `net/minecraft/entity/living/player/PlayerEntity.java` | `4c1b14b4727116071b1ba9e3eeae84b8077f3d767e10e49b6a68a2556da32272` |
| 1.13.2 / ornithe-feather | `net/minecraft/entity/living/player/PlayerEntity.java` | `4ed22f6c5a3c55d67eed782070ac722201df4d624adbc90779f1fd29c2876633` |
| 1.13.2 / ornithe-feather | `net/minecraft/entity/Entity.java` | `1d6ec8b80f74635401745c2c027bf36555c85348ca5693764f2668363b17d269` |
| 1.14.4 / mojmap | `net/minecraft/world/entity/player/Player.java` | `e9ce5eb18c5581ffe2b610b273ffd85786587a00ba853e5dbac96593622b12` |
| 1.14.4 / mojmap | `net/minecraft/world/entity/Entity.java` | `31c6be42d165102d3e6dc4295fdfef6e8971fc3aea13f938a5b3a33b91d524a7` |

## Independent verdict

**Confirmed claim:** 1.13 adds swimming as a player dimension/eye-height input. This is not a claim about the production of the swimming flag or about water travel.

- **1.12.2 absence and prior eye-height behavior:** `PlayerEntity.updatePlayerPose()` (291–315) selects fall-flying `0.6F × 0.6F`, sleeping `0.2F × 0.2F`, sneaking `0.6F × 1.65F`, otherwise `0.6F × 1.8F`; it applies the candidate dimensions only if `world.getCollisions(box)` is false. The method has no swimming/spin branch. `PlayerEntity.getEyeHeight()` (1756–1766) prioritizes sleeping `0.2F`, then sneaking or `height == 1.65F` as `1.62F - 0.08F`, then fall-flying or `height == 0.6F` as `0.4F`, otherwise `1.62F`. Thus `0.4F` already existed for the old fall-flying/height condition, but no explicit swim-state eye-height input exists here. A case-insensitive search of the bounded 1.12.2 `PlayerEntity`, `Entity`, and `LivingEntity` files found no `isSwimming`, `setSwimming`, or spin-attack state identifier.
- **1.13 introduction:** `PlayerEntity.updatePlayerPose()` (333–360) preserves fall-flying and sleeping priority, then adds `isSwimming() || isDoingSpinAttack()` at `0.6F × 0.6F`. It uses `world.hasNoCollisions(null, box)` as the size-change gate before `setSize`. `getEyeHeight()` (1854–1865) checks sleeping first, then swimming/fall-flying/current `height == 0.6F` for `0.4F`, then crouching/current `height == 1.65F` for `1.62F - 0.08F`, otherwise `1.62F`. `PlayerEntity.isSwimming()` (1814–1816) reads `super.isSwimming()` behind flying and spectator checks; the swim flag is treated as supplied input. If the collision gate declines the size update while swimming is true, the existing height remains, but the getter still returns `0.4F` through its direct swimming condition.
- **1.13 aliases:** the corresponding player methods in 1.13.1 (334–362, 1859–1872) and 1.13.2 (334–362, 1859–1872) retain the same swim/spin size branch, collision gate, constants and eye-height priority. The 1.13.1 and 1.13.2 checks therefore corroborate the 1.13 behavior for these members; they do not establish intermediate patch history beyond the examined releases.
- **1.14.4 confirmation:** `Player`'s `POSES` map (110–119) assigns `Pose.SWIMMING` `0.6F × 0.6F`, and `getStandingEyeHeight()` (1801–1812) returns `0.4F` for swimming, fall-flying and spin attack. `getDimensions(Pose)` (1963–1965) retrieves the mapped dimensions. This confirms the swimming dimensions/eye value after the structural move to pose-indexed dimensions. It does not show identical transition gates: `updatePlayerPose()` (344–372) now selects and enters poses through pose checks.

## Limits

- Flag production, fluid sensing, fluid movement/travel and world-collision query behavior are outside this audit. Swimming/spin flags are supplied inputs.
- The audited scope is the swim-driven dimension/eye-height contract. Crouching dimension and eye-height changes visible by 1.14.4 are a separate contract and receive no duplicate finding or acceptance here.
- This pair of endpoints and the 1.13.x witnesses do not identify the exact patch-level introduction between 1.12.2 and 1.13, establish full tick-graph coverage, prove runtime parity, or authorize implementation.