# 1.12.2 → 1.13.2 local writer and eye/pose audit

## Bounded scope

Read the assigned `1122-1132-finish58` source-only appendices first, then re-opened paired exact Feather sources for `LocalClientPlayerEntity`, `PlayerEntity`, and the relevant eye-height caller references. Scope here is local-player ordinary movement/direct writer terminals, packet response consumers, and eye/pose priority. `Entity.move` clipping/step behavior remains assigned elsewhere. This is a bounded audit, not pair coverage or freeze.

## Exact source identity

Both canonical trees are read-only at `D:/Javastuff/LegacyParkourCompat/decompiled_minecraft/{1.12.2,1.13.2}/ornithe-feather`. Provenance identifies namespace `ornithe-feather`, generated with Feather Gen2 mappings `feather-gen2-1.12.2+build.2` and `feather-gen2-1.13.2+build.2`, respectively; provenance records the exact `decompileMinecraft --mappings=feather` runs on 2026-10-07. Each direct source SHA-256 below matches its corresponding `ornithe-feather.sources.sha256` row.

| Release | Source | SHA-256 |
|---|---|---|
| 1.12.2 | `net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java` | `01a58e94d8c6ff98a8e3794227cdc76a5fcbdbad795c70c9cf28854aff9823cc` |
| 1.13.2 | `net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java` | `2583495f3a02b4791aa036e6a8d354d7596c4984761969a0f29b29d8d9bf42bf` |
| 1.12.2 | `net/minecraft/entity/living/player/PlayerEntity.java` | `e4e0fdbe07a0d0ae4a70cbb6739a287c9d045a4a12b409895c220b5d91fe1e` |
| 1.13.2 | `net/minecraft/entity/living/player/PlayerEntity.java` | `4ed22f6c5a3c55d67eed782070ac722201df4d624adbc90779f1fd29c2876633` |

## Direct-writer census checked in LocalClientPlayerEntity

The direct assignment scan found local-player velocity writes only at the four push-out assignments and ability-flight vertical assignments in both endpoint files, plus the 1.13.2 call to inherited `knockDownwards()` at `LocalClientPlayerEntity.java:786`.

- Push-out writes remain the same ±`0.1F` X/Z writes, with the same four call points in `mobTick` (old `:714-717`, new `:718-721`). Difference in the method's source position is surrounding insertions. Its equal-provider qualification remains open as already recorded in `local-tick-writer-residual/coverage.md`.
- Ability flight writes `velocityY ±= abilities.getFlySpeed()*3.0F` in both endpoints (old `:776,780`; new `:801,805`); ability flight is out of scope.
- The 1.13 wet-sneak `knockDownwards()` call (`:786`) is already represented by the prior `wet-sneak-knock-down.md` candidate. No additional ordinary local velocity writer was found in this class.
- No direct local `x/y/z`, position setter, dimensions, box/shape, or velocity packet writer exists in either `LocalClientPlayerEntity` body outside those listed. The prior bounded packet-response report covers the correction/velocity handlers; this check did not expand it into server packet production.

## Existing pose outputs and eye-height precedence

`PlayerEntity.updatePlayerPose()` runs from `PlayerEntity.tick()` before inherited movement (old `:245`, modern `:254`). For shared old poses, chosen dimensions are unchanged: fall-flying `(0.6F,0.6F)`, sleeping `(0.2F,0.2F)`, sneaking `(0.6F,1.65F)`, standing `(0.6F,1.8F)`. Modern adds swimming/spin attack at `(0.6F,0.6F)` and changes the collision-gate API; the prior coverage note correctly leaves its output conditional on collision-query equality. No new pose dimension delta established here.

`PlayerEntity.getEyeHeight()` has a concrete precedence reorder: old `:1756-1765` checks sleeping, then `isSneaking() || height == 1.65F` (subtract `0.08F`), then `isFallFlying() || height == 0.6F` (`0.4F`). Modern `:1859-1868` checks sleeping, then `isSwimming() || isFallFlying() || height == 0.6F` (`0.4F`), then sneaking/height (`1.54F`). For the overlapping old pose flags `isFallFlying()==true && isSneaking()==true`, dimensions are the fall-flying `(0.6F,0.6F)` in both pose setters, but eye height evaluates to `1.54F` old and `0.4F` modern. Both flags are stored in the living-entity shared flag byte (old `LivingEntity.java:2184` fall-flying reader plus inherited `Entity` sneaking flag; modern `LivingEntity.java:2314` plus inherited `Entity` flag accessors), so the raw equal metadata state can be supplied as a comparison precondition without relying on an excluded item/server producer.

The paired source trace closes the movement-consumer question negatively. Old `Entity.isSubmergedIn(Material)` (`Entity.java:1027-1044`) does read `y + getEyeHeight()` and resolve the containing liquid surface, but its local-player call in `LivingEntity.baseTick` (`:243-278`) only branches breath/drowning state; `tickNonRidingMovementRelatedStats` also reads it only for stat/fatigue accounting (`PlayerEntity.java:1412-1423`). Ordinary old `LivingEntity.travel` selects water movement by `isInWater()` and otherwise its fall-flying branch (`LivingEntity.java:1427-1434`); it does not use `isSubmergedIn` to choose movement. Old sprint start/stop likewise has no eye-submersion condition (`LocalClientPlayerEntity.java:730-745`). Other old eye-height uses found in these player/entity files serve eye positions/rays or non-player/projectile origins, not local player movement. The old movement path therefore does not consume this eye-height output as a movement predicate.

Modern `Entity.isSubmergedIn(Tag)` (`Entity.java:1042-1052`) feeds `checkWaterState`/`updateSwimming` (`Entity.java:943-981`) and `PlayerEntity.updateWaterSubmersion` (`PlayerEntity.java:257-259`). The resulting swim state is then used by modern local sprint transitions (`LocalClientPlayerEntity.java:732-758`) and pose selection (`PlayerEntity.java:334-350`); those swimming/submerged branches are new-feature consumers. Modern living travel still gives the wet branch priority over fall-flying when `isInWater()` is true (`LivingEntity.java:1486-1493`), but its eye-height-derived swim flag has no old counterpart, and the dimension result for this simultaneous fall-flying pose remains `(0.6F,0.6F)`. The eye-height precedence change therefore has no established old-feature movement output to preserve. **Disposition: close this eye-priority terminal as out of movement scope; do not create a permanent finding.** The separate existing wet-sprint predicate finding remains responsible for the 1.13-added sprint-water gate on ordinary shared states; this audit does not alter it.

## Disposition and remaining gates

No novel permanent movement finding produced. Existing candidates confirmed in this bounded recheck: wet sprint, wet sneak knock-down, and the conditional push-out writer. Remaining gates: establish old-feature reachability and movement use for the eye-height overlap; close the pose collision-query output against shapes/providers; and retain the prior listed ordinary `jump`/`moveRelative`, position/pose transition, callback and collision-residual terminals. No source generation, implementation, test, build, or runtime work was performed.
