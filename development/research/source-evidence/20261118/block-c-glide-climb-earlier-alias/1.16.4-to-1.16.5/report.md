# 1.16.4–1.16.5 Elytra versus climbable route

## Exact source identity

This is a bounded Mojmap comparison of the adjacent releases `1.16.4` and `1.16.5`, using only their exact local decompilations under `decompiled_minecraft/<version>/mojmap/`.

- `1.16.4`: `mojmap/.decompile-provenance.json` identifies the exact release metadata URL ending in `/1.16.4.json` (SHA-256 `667fff4ca7fd0f56ab78b757224515e8e64375868e8d1ce6040091d7b63a0cb5`), client JAR SHA-256 `d6740464ee2bdf37d285c5d368d2e16497492427fb5783e2db3482bbf0079ed0`, Mojang mapping text SHA-256 `93f188a0038fec8c2402d78e7acca983680978d6a9237c1fab80f22caddb00fe`, and Vineflower `1.12.0` (pinned tool SHA-256 `1dfcfe974395734fa467ce620661c7623d05ba83670de0529b1fbd63ff548b9d`).
- `1.16.5`: `mojmap.provenance.json`, `mojmap.ready.json`, and `artifacts.sha256` identify exact `versionId: 1.16.5`, namespace `mojmap`, successful exact decompile, client JAR SHA-256 `00b5ebbc33e95ea88c1ab80601599c9827e9aa861d93ccc2cfcbbfd996e86263`, and client Mojmap JAR SHA-256 `7c1a9f30983fa3c777d00116bdf3f3cef97c1c1aaf8294027052f5337fcde940`. Its source-manifest SHA-256 is `9499f2611d0e6dde37cb635f1a28a591416382d6bc188b37d5f99e2b2a20823b`; the ready marker names exact `1.16.5` output and 3,523 source files. The marker records Vineflower `1.12.0`.

Tool-computed SHA-256 values for each complete source file (the relevant entries also appear in the source manifests/provenance):

| Source | 1.16.4 and 1.16.5 SHA-256 |
| --- | --- |
| `net/minecraft/world/entity/LivingEntity.java` | `b5d8a1a3c80f85d5d545b5a777e9e2a915dc002a7e31b5f5ad12bf7e285d7a88` |
| `net/minecraft/world/entity/player/Player.java` | `d2e26589bdb6a20dc914266db06aa48f50811efc792d6e63b3008c9199914960` |
| `net/minecraft/world/entity/Entity.java` | `f9a9a073fe3105a0aa53d0f21ec72e59084e8d21a14c1cd3be75703865ee2666` |
| `net/minecraft/world/item/ElytraItem.java` | `d20a679b8822eaa57e5216ce14ed755c1f86a160eb5dad0dc9e3d8f4b5ea018d` |

The exact-source checksums establish byte-identical complete classes for both endpoints. No other version, mapping family, source lineage, wiki, product implementation, or Git history was consulted.

## Compared bounded path

- `LivingEntity.java`, `1.16.4` and `1.16.5`: `tick()` calls `aiStep()` at 2124–2170; the end-of-tick fall-flight counter increments or resets at 2238–2242. In `aiStep()`, input scaling precedes `updateFallFlying()`, which precedes virtual `travel(...)` at 2453–2459. `updateFallFlying()` reads shared flag 7; while active it requires airborne, not riding, no Levitation, chest-slot Elytra, and `ElytraItem.isFlyEnabled`; only the server writes the checked flag, and server durability damage is gated by `(fallFlyTicks + 1) % 20 == 0` (2478–2497).
- `Player.java`, both releases: `tryToStartFallFlying()` requires airborne, not already flying, not in water, and no Levitation, then a fly-enabled chest Elytra; `startFallFlying()` writes flag 7 true. `stopFallFlying()` writes true then false, in that order (1520–1538). For an ordinary player without ability flight, `Player.travel()` delegates to `super.travel()`; its ability-flight branch instead clears the fall-flying flag after travel (1387–1417).
- `LivingEntity.java`, both releases: dry travel selects `isFallFlying()` before the ordinary friction route (1914–2045). In that branch, `deltaY > -0.5` strictly writes `fallDistance = 1.0F` before glide calculation and `move(...)` (1979–2008). The ladder/vine `onClimbable()` response is used by `handleRelativeFrictionAndCalculateMovement()` in the later ordinary branch (2062–2071; fall-distance reset/clamp at 2089–2103), so an already-active Elytra player does not take that climbable movement route. The post-movement landing clear is server-gated (2019–2021); it does not clear flag 7 in the local client path.
- `Entity.java`, both releases: `move()` calls `checkFallDamage()` after collision resolution (485–518). `checkFallDamage()` clears fall distance on landing, otherwise adds the magnitude of a negative actual Y displacement (894–904). Thus the pre-move `1.0F` is a starting write, not a guaranteed final value: a landing can replace it with `0.0F`, and downward movement immediately accumulates onto it.
- `Player.java`, both releases: after travel, movement statistics test `onClimbable()` before `isFallFlying()` (1437–1485). This can classify the distance as climb statistics while the actual movement already used the Elytra branch; it is not the climbable movement route.
- `ElytraItem.java`, both releases: the fly-enabled gate is `damageValue < maxDamage - 1` (line 17).

## Result and bounds

No source difference was found for this operation across `1.16.4 → 1.16.5`: the complete `LivingEntity`, `Player`, `Entity`, and `ElytraItem` source hashes match byte-for-byte. The ordered flag lifecycle, dry travel branch precedence, strict pre-move threshold/write, collision-time fall-distance overwrite, and end-of-tick counter boundaries are therefore identical in this bounded pair.

Coverage is limited to the already-active, ordinary local player, dry air, default enabled chest Elytra, on-foot state, and ladder/vine contact, including the immediately enclosing tick/travel/landing gates above. It does not establish behavior for launch-tick edge cases beyond the listed `tryToStartFallFlying` guard, fluids, passengers, ability flight, Levitation, damaged/disabled Elytra, other entity classes, other operations, or any intermediate/non-adjacent release boundary. No runtime parity claim is made.
