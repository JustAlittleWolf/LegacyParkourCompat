# 1.21.4 → 1.21.5 pose-priority source reachability

## Scope and input

This is a bounded source-only closure for the pose-priority reachability question in `1214-1215-local-tick-response/coverage.md` (SHA-256 `ad1a38270a10d43be3a0824a874ebd3486bb79974de87043929552b1192dd8a2`). I retraced the exact player pose selectors, their local state writers and tick order. I did not inspect collision-query implementations/shapes, rendering, server packet producers, product code, history, or wiki material. It establishes a conditional client-source state path, not a trajectory or pair freeze.

## Source identity

Both canonical Mojmap trees are read-only under `D:/Javastuff/LegacyParkourCompat/decompiled_minecraft/{1.21.4,1.21.5}/mojmap/`. The mapping-root `.decompile-provenance.json` is absent for both endpoints; the release-parent records bind the exact sources:

| Release | Parent provenance SHA-256 | Ready marker SHA-256 | Source manifest SHA-256 | Artifact manifest SHA-256 | Ready identity |
|---|---|---|---|---|---|
| 1.21.4 | `ab0439864c89f4e0dab09daae7b57ab973368eeb0320626822e06f5a9bce9fb9` | `1c6617b7acf8bb357f77c6c6c276881c73d882bda1f93f6dd217bb54eb8aa4be` | `f90b61197928632e061ea877955a19055c92ae6f357c2daf1bc646172c6f51f0` | `1a0929ca8c88cfe7874f323918dfa3b044964007d3ff0d3943317bdb35caf841` | ready, 5,744 sources, client `c17c450c…d326d894e` |
| 1.21.5 | `b0832a4e1507bd74ac300fae2fca78668964b21ef2d05be8cff01b9d1d60da8e` | `5f65fb143209abcc6e28e77a7150aecefdbc9de00b6ef3fc924e53bc0e1a667e` | `365cc2d22446ceba0e36f46aa1c95ce4cc31514dbd657505680fb660f3adefd9` | `d35b1b9389d8958e33893faa63d94c86f22af30385233ca947fd410aa5c36656` | ready, 5,921 sources, client `522672ad…d395a8c11` |

The ready markers declare the respective version, `mojmap`, source counts, source-manifest and artifact-manifest hashes, and client artifact hashes. The parent provenance files identify the same exact batch (`1.21.1`, `.3`, `.4`, `.5`) and toolchain. I rehashed the selected Java files and confirmed each against its release-parent source manifest:

| Source | 1.21.4 SHA-256 | 1.21.5 SHA-256 |
|---|---|---|
| `net/minecraft/world/entity/player/Player.java` | `c45f41b9784ce50a88a498e84a13edcef0a23e175233d726ed7f90940f19ebc1` | `8fc187f33999db9dfc49251e95e92a17645a50adab16ca0f93f4948209f62036` |
| `net/minecraft/world/entity/LivingEntity.java` | `e62ce650af0a5ac97e4d0a2ba7cad4ba68a8d098525609f8b5b6976a8ab0ae30` | `a8aed863d4fdc515c751dd2878a8bb9c13179228cb8dbb50edf1d19cd5404271` |
| `net/minecraft/world/entity/Entity.java` | `05f18ef2ec0413fc010230407c812a11553eb5123b68d21b5d7b2c0c175698ad` | `fc177733e9cc4b2d5cf2562e5529d0f4e0b9c3690f081fbd58aa9119eb173dc4` |
| `net/minecraft/client/player/LocalPlayer.java` | `145686ebdc7f0d12a64070309073665eb8695b7e09911a723e86113a77d04611` | `f1fcfed4a938732361e7ad951f93e9b73b02320ee56f0b219e2b3c7acdbfa2ef` |

## State definitions and reachable overlap

The changed priority affects a local tick when `isFallFlying()` overlaps either `isSleeping()` or `isSwimming()`. The swimming overlap is source-reachable without relying on a server packet producer:

1. `LocalPlayer.aiStep` attempts fall-flying on the jump-input path when its movement gates pass (`LocalPlayer.java:749` in 1.21.4; `:770` in 1.21.5). `Player.tryToStartFallFlying` additionally requires `!isFallFlying()`, `canGlide()`, and `!isInWater()` (`Player.java:1528` / `:1507`). `Player.startFallFlying` sets shared flag 7 (`Player.java:1537` / `:1516`), which is what `LivingEntity.isFallFlying()` reads.
2. A gliding player can subsequently enter water. The ordinary `LivingEntity.travel(Vec3)` branch tests water/lava before fall-flying (`LivingEntity.java:2189` / `:2213`), so this state follows `travelInFluid`; it does not enter the fall-flying travel branch that could stop flight on a climbable. The same fluids-first order exists at both endpoints.
3. `Entity.baseTick` refreshes fluid state and then calls `updateSwimming` before `Player.tick` reaches its pose update (`Entity.java:455` / `:463`; `Player.tick` calls `super.tick()` at `:283` / `:298` and then `updatePlayerPose()` at `:329` / `:344`). `Entity.updateSwimming` sets shared flag 4 for a sprinting, submerged, non-passenger in water (`Entity.java:1306` / `:1336`). `Player.updateSwimming` only forces this flag false for ability-flight; otherwise it delegates to that method (`Player.java:1479` / `:1457`). A non-flying, non-spectator player can therefore have both flag 7 and flag 4 true when `updatePlayerPose` runs.
4. The water path does not clear flag 7. `LivingEntity.updateFallFlying` clears it only inside the `!level().isClientSide` branch when `!canGlide()` (`LivingEntity.java:2825` / `:2815`); it has no water condition. With a usable glider and no ground/passenger/levitation gate, the flag remains. The fluids-first travel branch likewise does not call `stopFallFlying`. The concrete overlap is conditional on entering water while still able to glide, sprinting underwater, and not being a passenger, spectator, or ability-flying player.

`isSwimming()` here is the shared swimming flag, not `Pose.SWIMMING`: `Entity.isSwimming` reads flag 4 (`Entity.java:2358` / `:2326`) and `Player.isSwimming` adds the ability-flight and spectator gates (`Player.java:1818` / `:1759`). `isSleeping()` is the presence of `SLEEPING_POS_ID` (`LivingEntity.java:3391` / `:3382`), while `isFallFlying()` reads flag 7 (`LivingEntity.java:3277` / `:3268`). These predicates are independent state inputs. `startSleeping` sets sleeping pose and sleeping position but does not clear flag 7 (`LivingEntity.java:3395` / `:3386`); however, this report does not trace server sleep-entry or packet-production reachability, so it makes no claim that sleep/fall-flight overlap is produced in an ordinary session.

## Selector and fallback consequence

At `Player.updatePlayerPose`, both versions first require the existing `canPlayerFitWithinBlocksAndEntitiesWhen(Pose.SWIMMING)` guard (`Player.java:435` / `:450`). If it fails, neither version changes pose. With the reachable flag overlap above, 1.21.4 then chooses `Pose.FALL_FLYING` before checking swimming; 1.21.5's `getDesiredPose` checks sleeping and swimming before fall-flying (`Player.java:437–443` versus `:465–472`). For a non-spectator, non-passenger player, both final fit/fallback branches then use the selected desired pose, otherwise crouching, otherwise swimming.

The `Player.POSES` maps declare identical dimensions for `FALL_FLYING` and `SWIMMING` at both endpoints: `EntityDimensions.scalable(0.6F, 0.6F).withEyeHeight(0.4F)` (`Player.java:142–143` / `:143–144`). The common outer swimming-fit check and the old fall-flying desired-pose fit check therefore pass the same `canPlayerFit...` arguments at the same position; no collision internals were inspected. When the outer guard passes, the old candidate also passes its desired-pose check. Thus the fallback cannot collapse this overlap to the same pose: old selects `FALL_FLYING`, new selects `SWIMMING` (or `SLEEPING` if that predicate is also true). This is a source-confirmed pose-state difference under the stated local input/state gates.

## Disposition

**Positive, conditional source finding:** on a local player tick after fall-flying has been set and the player becomes sprinting/submerged in water while retaining the usable glider and satisfying the gates above, both `isFallFlying` and `isSwimming` are true at pose selection; the 1.21.4 and 1.21.5 selector order chooses different poses. The exact result is still subject to the common swimming-fit outer gate. The fit checks use the same old/new pose dimensions for the two candidates; collision-provider behavior was not audited. No sleep-packet producer, runtime trajectory, general parity, or pair-freeze claim is made.
