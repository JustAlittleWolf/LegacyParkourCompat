# Bounded source comparison: ordinary player core, 1.12.2 to 1.13.2

## Evidence identity

Canonical source roots are `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\1.12.2\ornithe-feather` and `...\1.13.2\ornithe-feather`. Read-only provenance markers identify releases exactly as 1.12.2 and 1.13.2, namespace `ornithe-feather`, mapping option `feather`; mapping artifacts are `feather-gen2-1.12.2+build.2-mergedv2.jar` / `.tiny` and `feather-gen2-1.13.2+build.2-mergedv2.jar` / `.tiny`. Both provenance files record Java 25.0.3, Vineflower 1.12.0, mapping-io 0.9.1, TinyRemapper 0.14.1. The separate ready markers and source manifests are present. No source regeneration was performed.

SHA-256 of examined Java files:

| Release | Relative source file | SHA-256 |
|---|---|---|
| 1.12.2 | `net/minecraft/entity/living/LivingEntity.java` | `190E9AC551538E015D9E4D6C42856E5BA32B593131CF6D93895E7B29533F1EE6` |
| 1.13.2 | `net/minecraft/entity/living/LivingEntity.java` | `BB691358C9A43C9F46E85575BF4D0A4AD671D0EB912502ACC3A6A3F625E42F1C` |
| 1.12.2 | `net/minecraft/entity/Entity.java` | `80F091BF32166C88CF8BBD31CAF72D84FA16224410733C7D2A0F00563F294A0A` |
| 1.13.2 | `net/minecraft/entity/Entity.java` | `1D6EC8B80F74635401745C2C027BF36555C85348CA5693764F2668363B17D269` |
| 1.12.2 | `net/minecraft/entity/living/player/PlayerEntity.java` | `E4E0FDBE07A7D0A0AE4A70CBB6739A287C9D045A4A12B409895C220B5D91FE1E` |
| 1.13.2 | `net/minecraft/entity/living/player/PlayerEntity.java` | `4ED22F6C5A3C55D67EED782070AC722201DF4D624ADBC90779F1FD29C2876633` |

## Confirmed independent movement differences

### Sprint swimming and fluid jump response

1.12.2 `LivingEntity.java`, `mobTick()` lines 1827-1839: while `jumping`, any `isInWater()` calls `jumpInWater()`; lava similarly calls `jumpInLava()`. Ground jump is the remaining branch. Its generic water travel path (`moveRelative`, lines 1556-1585) has no sprint-specific drag, gravity, or speed multiplier. The 1.12.2 `jumpInWater()` implementation is at lines 1413-1423.

1.13.2 `LivingEntity.java`, `mobTick()` lines 1903-1917: jumping uses stored fluid surface height `f_85121000`; above the surface, it dispatches `m_74407200(FluidTags.WATER)`, whereas lava dispatches the same helper with LAVA. At/below surface or on ground, jump is gated by `jumpingCooldown == 0`, with the shallow-fluid condition `0 < f_85121000 <= 0.4` enabling ordinary jump. The helper body at lines 1470-1472 adds `0.04F` to vertical velocity. Water travel at lines 1617-1655 sets horizontal drag to `0.9F` when sprinting, overrides it to `0.96F` with Dolphin's Grace, and applies gravity only when neither no-gravity nor sprinting; non-sprint water travel keeps `getBaseMovementSpeedMultiplier()` and `d/16` gravity.

Movement meaning: held jump while partly submerged now distinguishes fluid surface depth; in sufficiently shallow water a grounded or cooldown-ready player takes the normal jump branch, while submerged jump adds a fixed upward impulse. Sprinting also materially changes horizontal water drag and suppresses the ordinary water gravity term. Exact insertion boundary is unknown within the endpoint interval; no intermediate versions were inspected. Dependency closure for historical implementation requires the exact fluid-height producer and its update timing, plus `PlayerEntity.isSwimming`/pose and `PlayerEntity.moveRelative` consumers; see open dependencies below.

### Ground acceleration constant precision

`LivingEntity.moveRelative()` ground branch changes the literal from `0.16277136F` (1.12.2 line 1485) to `0.16277137F` (1.13.2 line 1545), preserving the formula `constant/(friction*friction*friction)`. This is a one-ULP float input change to ground acceleration scaling whenever `onGround` selects `getSpeed()*u`; it can perturb subsequent velocity/position. Separate operation from the fluid behavior. Boundary only established between the named endpoints.

## Ordered coverage inventory

1. **Local client player root**: inspected corresponding `LocalClientPlayerEntity` and `ClientPlayerEntity` diff. The visible differences were UI/network/stat plumbing, skin texture identifier construction, and other non-movement effects; no player movement update/travel override found in the reviewed diff. This is a bounded source diff check, not exhaustive call graph closure.
2. **Player root**: inspected `PlayerEntity` changes and located `tick`, `updatePlayerPose`, `mobTick`, `moveRelative`, and `isSwimming`. Added tick refresh `updateWaterSubmersion()` before `super.tick()` and pose update; pose now has swimming/spin-attack branches. `moveRelative()` gains a swimming-specific pitch-oriented movement path and adjusts water-related responses. These areas are movement relevant and not fully traced here; keep open as separate dependency/operation candidates pending paired exact bodies and fluid-tick ordering. `PlayerEntity.updateWaterSubmersion()` 1.13.2 lines 257-259 caches `isSubmergedIn(FluidTags.WATER)`.
3. **LivingEntity tick/decision**: compared `tick`, `mobTick`, `moveRelative` broad method diff. Confirmed the sprint swim/fluid-jump change above. Other edits in these methods include new swimming animation/update state and non-movement bookkeeping; not treated as findings.
4. **Entity state/fluid refresh**: located 1.12.2 `checkWaterState`/`checkWaterCollisions` and 1.13.2 `checkWaterState`, `isSubmergedInWater`, `isSubmergedIn`, `isSwimming`, and fluid-height field use. Compared class-level diff only. Fluid producer/update and refresh gate/order require a dedicated pass; unresolved.
5. **Pose/dimensions and refresh timing**: located `PlayerEntity.updatePlayerPose` in both releases; inspected signatures and 1.13.2 call site. 1.13.2 pose chooses swimming when `isSwimming()` or spin attack; body around lines 334-350 not fully paired against 1.12.2 line 291 onward. Exact dimensions resize/collision recheck ordering remains open.
6. **Modifiers/effects/equipment**: the examined travel slice confirms the Dolphin's Grace water drag branch and unchanged depth-strider-shaped interpolation. Did not exhaustively compare all effect/equipment attribute producers or item hooks reachable on tick.
7. **Input**: no input class traversal performed in this bounded pass; open.
8. **Fluid contact consumers**: only LivingEntity/PlayerEntity/Entity consumer locations were inventoried; block collision/shape providers and broader fluid-state/tag definitions intentionally not inspected. No collision collectors or shape-provider search was made.
9. **Externally triggered direct player movement responses**: unexamined.

## Remaining dependencies and limits

This report is a source-worker bounded report, not full-pair freeze. To close ordinary-core coverage, pair the full bodies and call order for `Entity.checkWaterState`/fluid-height update, `LivingEntity.tick` and `PlayerEntity.tick` around water refresh, `PlayerEntity.updatePlayerPose` plus dimensions/box setters, swimming `PlayerEntity.moveRelative` and `LivingEntity.moveRelative`, and the input tick path. Resolve the exact fluid-height variable producer (`f_85121000`) from source before implementing; the name is unmapped in this 1.13.2 decompilation and must not be guessed. Also inspect effect/equipment movement attribute updates and eligible direct player responses. Do not infer intermediate patch cutovers from this endpoint comparison.
