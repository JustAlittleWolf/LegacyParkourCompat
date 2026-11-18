# 1.13.2 → 26.2 swimming vertical-look adjustment witness

## Bounded verdict

No differing ordinary tick was found for the requested clean, non-riding local-player witness in supported vanilla water. The player-specific vertical correction was moved from `PlayerEntity.moveRelative` (1.13.2) to `Player.travel` (26.2), but the reachable swimming branch retains the same look component, thresholds, short-circuit gates, arithmetic, and position immediately before the inherited movement operation. This establishes source parity for this bounded witness; it does not claim complete movement-pair coverage or runtime parity.

## Concrete reachable witness

Take a normally controlled local player who was dry, entered a two-block-deep vanilla source-water pool during an ordinary tick, and is now on the next tick with feet centered in the bottom water block near its lower edge, sprinting, submerged, not riding, not flying, not spectating, and able to assume the swimming pose in the open space. At this vertical position, the sample at feet-y + 0.9 is in that same bottom water block. The prior dry-to-water transition is an ordinary reachable setup; the correction is evaluated on the first ordinary swimming tick once the swimming state is active. No collision or pose-blocking behavior is needed for this witness.

On both releases, player `isSwimming()` is false while flying or spectating and otherwise delegates to the swimming flag. The generic swim update enables the flag for a sprinting, submerged, non-riding entity (1.13.2 `Entity.updateSwimming`; 26.2 `Entity.updateSwimming`, with `Player.updateSwimming` disabling it for flight). A locally controlled player reaches movement through the normal living-entity tick path. In 1.13.2 `LocalClientPlayerEntity.tick` calls `super.tick`; `LivingEntity` reaches `moveRelative` in its travel portion. In 26.2 the living-entity tick invokes virtual `travel(input)`; `Player.travel` is therefore reached for the local player's ordinary movement. The clean open-water pose assumption avoids an unrelated pose-fit branch.

## Exact source comparison

- 1.13.2 Ornithe Feather, `net/minecraft/entity/living/player/PlayerEntity.java`, `moveRelative`, lines 1443–1469: if `isSwimming() && !isRiding()`, read `double g = getLookVector().y`; choose `double h = g < -0.2 ? 0.085 : 0.06`; then, when `g <= 0.0 || jumping || sampledBlockHasFluid`, add `(g - velocityY) * h` to `velocityY`. Only after this correction does the override call `super.moveRelative(...)`.
- 26.2 unobfuscated, `net/minecraft/world/entity/player/Player.java`, `travel`, lines 1404–1423: passengers go directly to `super.travel`. Otherwise, if swimming, read `double lookAngleY = getLookAngle().y`; choose `double multiplier = lookAngleY < -0.2 ? 0.085 : 0.06`; under the same `lookAngleY <= 0.0 || jumping || sampledFluidNonempty` gate, replace delta movement with `movement.add(0.0, (lookAngleY - movement.y) * multiplier, 0.0)`. The non-flying path then calls `super.travel(input)`.
- For the witness, the non-riding/non-passenger gate is satisfied. The inherited 1.13.2 living movement calls `moveRelative` after jump handling; modern `LivingEntity.travelInWater` calls `moveRelative` before its real `move`. Thus the adjustment is before the same horizontal/vertical input acceleration and real move in the selected ordinary swimming path. The 26.2 passenger branch corresponds to the old `!isRiding()` guard for the excluded mounted case.

Both forms use double `lookY`, threshold `-0.2`, multipliers `0.085` and `0.06`, gate threshold `0.0`, and left-to-right double expression `(lookY - velocityY) * multiplier`, added to the existing vertical velocity. The fluid sample uses the same coordinate expression `x, y + 1.0 - 0.1, z`, with floor-to-block semantics: old `new BlockPos(double, double, double)` followed by block state's fluid state; new `BlockPos.containing(double, double, double)` followed by level fluid state. For the witness both sample the same vanilla water block. A water block at that sample makes the final gate true, without changing the preceding arithmetic.

The look-vector vertical component is also source-equivalent: 1.13.2 `Entity.getLookVector()` uses current `pitch,yaw` through `getRotationVector`; 26.2 `Entity.getLookAngle()` uses current `getXRot(),getYRot()` through `calculateViewVector`. The two helper bodies perform the same float radians conversion, cosine/sine calls and `-sin(pitchRadians)` y component. This is the pitch-directed term above. Other look-vector uses, including elytra steering, are separate operations and are not evidence for this correction; water acceleration, drag and fluid-falling rules are also out of scope here.

## Pinned source evidence

Exact local sources were consumed read-only. Each ready marker is `status: ready`; the source-manifest digest below matches its ready marker, and each cited file's SHA-256 matches its line in that manifest.

- 1.13.2 / Ornithe Feather: `decompiled_minecraft/1.13.2/ornithe-feather.ready.json`; exact-run provenance `ornithe-feather.provenance.json`; source manifest SHA-256 `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211`.
  - `PlayerEntity.java`: `4ed22f6c5a3c55d67eed782070ac722201df4d624adbc90779f1fd29c2876633`
  - `LivingEntity.java`: `bb691358c9a43c9f46e85575bf4d0a4ad671d0eb912502acc3a6a3f625e42f1c`
  - `Entity.java`: `1d6ec8b80f74635401745c2c027bf36555c85348ca5693764f2668363b17d269`
  - `LocalClientPlayerEntity.java`: `2583495f3a02b4791aa036e6a8d354d7596c4984761969a0f29b29d8d9bf42bf`
- 26.2 / unobfuscated: `decompiled_minecraft/26.2/unobfuscated.ready.json`; exact-run provenance `unobfuscated.provenance.json`; source manifest SHA-256 `a7ad74fc712567eb87136afc1eafcfd602b4088e9869f3596331fd34faefe894`.
  - `Player.java`: `8decc71b9c780664578ddb14591db2a2f207c72c05b676edded6f8e964576531`
  - `LivingEntity.java`: `7ffd9c70966edc50c9cb4d9a8fe17a518e2678ff44c8026e763d0b94ac0ae51a`
  - `Entity.java`: `7afb9c1294893ffe73e3b1acffcad41c648f15de8378bff3dffaff869bb811d5`
  - `LocalPlayer.java`: `8d089aa09217e3607b38590f7c1623385562800943ac6dfd3d17804e041da6d6`

## Boundaries

This report covers only the direct player look/pitch-directed vertical swimming correction and its necessary local tick, swimming-state, fluid-sample, and look-vector dependencies. It does not audit fluid acceleration/drag, collision/shape behavior, other movement branches, or the full 1.13.2–26.2 pair.

