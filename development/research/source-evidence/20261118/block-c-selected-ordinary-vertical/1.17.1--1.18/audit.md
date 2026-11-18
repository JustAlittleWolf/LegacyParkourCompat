# Independent source audit: ordinary vertical air integration

## Bounded verdict

**No movement difference found** in the ordinary, dry, nonvehicle, non-gliding and non-flying vertical integration path from 1.17.1 to 1.18. The passive gravity constant, travel/move order, vertical velocity drag, position integration through `Entity.move`, airborne fall-distance accumulation, and grounded reset condition/math are equivalent in the reviewed source. The reset call was factored into `Entity.resetFallDistance()` in 1.18; the method body is exactly `this.fallDistance = 0.0F` and no source override exists in the inspected entity tree.

This is a bounded slice verdict only. It is not a complete player tick audit or full-pair coverage audit.

## Exact source identity

Mapping: Mojmap. Source roots: `decompiled_minecraft/1.17.1/mojmap/` and `decompiled_minecraft/1.18/mojmap/`.

Release-level marker SHA-256 pins, checked against the assignment:

| Release | `mojmap.ready.json` | `mojmap.provenance.json` | `mojmap.sources.sha256` |
|---|---|---|---|
| 1.17.1 | `c7180dce94d7b57b53a964445b0c788c71f23ba3ba40eea593c11cc939185e9b` | `de197f77473955635e8c5730e113f227af2b8af9306b74e8a54a765eafc5a333` | `93270d229acfb751bf56daf1e7be26ce3dcff26b29e94aa157de621405a3463b` |
| 1.18 | `4537edf0e88c88631215beb1c14f1d67f5a5d6d021c982c0af549b7b27856e5f` | `e642f075b25d1b6e74bad05edacd740421c70f5d8e0c2a38767523cf864124c5` | `a43c61223ddedbdd8ede4d2daf6a750a58cbba324b30c025429ee1030cfc0d78` |

SHA-256 of every cited source file, verified against the respective source manifest:

| Source file | 1.17.1 | 1.18 |
|---|---|---|
| `net/minecraft/world/entity/Entity.java` | `ab28e1fba924771ec048140dfd293ee5a46a7dfe81f71a1a0b1aecc1927232de` | `69417675ae8e0a5bd88b7372baea1e73b1f5217154f39446c8ad5c92731437f1` |
| `net/minecraft/world/entity/LivingEntity.java` | `33fd081aadb2b6fdc9ebf487db6da5b38c54f4b8676572790ee2203690d15e6f` | `68fc9b15eb31690377afe92acfc783c86945fedec5739d28faacd96da4f9e0e7` |
| `net/minecraft/world/entity/player/Player.java` | `724bb298499dabe489dffd5ce7ec81c9773619a8d2c70044911eae4c9e8ff481` | `1fb9dfb9f700323d77f4196dbc3618d01a65e56438b9cce3ee38fa0b258bd7e2` |

## Tick path and compared evidence

- `Player.tick()` starts the player tick and calls inherited `LivingEntity.tick()` through the normal override chain (`Player.java:220+`; `LivingEntity.java:2218-2220` in 1.17.1, `2220-2222` in 1.18). The paired player tick and `Player.aiStep()` structure is unchanged in this slice. `Player.aiStep()` calls `super.aiStep()` at line 506 on both sides.
- `LivingEntity.aiStep()` reaches `travel(new Vec3(xxa, yya, zza))` after its jump/input work (`LivingEntity.java:2541-2547` in 1.17.1, `2543-2549` in 1.18). `Player.travel()` delegates to `super.travel()` unless abilities-flying is active (`Player.java:1424-1436` in 1.17.1, `1424-1436` in 1.18). For this assignment's non-flying player path, delegation is the same.
- `LivingEntity.travel()` enters its movement body only when `isEffectiveAi() || isControlledByLocalInstance()` is true (`LivingEntity.java:2003-2005` in 1.17.1; `2005-2007` in 1.18). The ordinary dry-air case falls through water, lava, and fall-flying branches into the same general branch. With Slow Falling and Levitation absent, no status override/reset applies. Both versions use local gravity `double d = 0.08` (`$$1` in 1.18).
- In that general branch, both versions get the movement-affecting block friction, calculate `u = onGround ? friction * 0.91F : 0.91F`, then call `handleRelativeFrictionAndCalculateMovement(input, friction)` (`LivingEntity.java:2112-2116` in 1.17.1; `2114-2117` in 1.18). This helper applies relative input, handles climbable logic, calls `move(SELF, current deltaMovement)`, and returns the resulting velocity (`LivingEntity.java:2155-2165` in 1.17.1; `2157-2167` in 1.18). In the bounded ordinary dry-air case the relevant returned vertical component is the post-move `Vec3.y`; there is no changed climbable action in scope.
- Both then use `v = vec38.y`, subtract gravity only if `!isNoGravity()`, and set the next velocity. With ordinary friction, the exact vertical write is `setDeltaMovement(vec38.x * u, v * 0.98F, vec38.z * u)` (`LivingEntity.java:2115-2133` in 1.17.1; `2117-2135` in 1.18). Thus gravity subtraction precedes drag, and the `0.98F` factor and its expression/order are unchanged. With no-gravity, both skip subtraction but retain the same drag gate/write. The `shouldDiscardFriction()` alternate also has the same direct `v` write in each release.
- `Entity.move()` uses the supplied velocity vector as requested displacement, applies collision resolution, and updates position by adding the resolved `vec32.y` to current Y (`Entity.java:534-566` in 1.17.1; `541-579` in 1.18). Under the no-contact dry-air assumptions, resolved Y equals requested Y. Both compute `onGround = verticalCollision && requestedY < 0.0` and call `checkFallDamage(resolvedY, onGround, ...)` in the same order. 1.18 additionally stores `minorHorizontalCollision` between `verticalCollision` and `onGround`; this is a horizontal-collision state and does not alter the ordinary vertical path under this assignment's no-collision boundary.
- `Entity.checkFallDamage()` has the same gates and float/double arithmetic: on ground, call `fallOn` only when `fallDistance > 0.0F`, then emit the vibration event subject to the same block-tag gate, then reset; otherwise, only for resolved displacement `< 0.0`, set `(float)(fallDistance - resolvedY)` (`Entity.java:998-1010` in 1.17.1; `969-981` in 1.18). 1.17.1 writes `fallDistance = 0.0F`; 1.18 calls `resetFallDistance()`, whose body at `Entity.java:2101-2103` assigns the same `0.0F`. No override of this method appears in the source tree.

## Numeric/state summary

- Position and `Vec3` velocity components are `double`; `fallDistance` is `float`.
- Per normal dry movement, position consumes the current velocity before the travel branch computes the next vertical velocity. The moved, collision-resolved Y displacement (not the post-gravity next velocity) is passed to fall-distance accounting.
- The ordinary next-Y-velocity calculation is the same ordered sequence in both releases: retain returned `y`; subtract `0.08` when gravity is enabled; multiply by `0.98F` unless friction is discarded.
- Downward movement adds the negated resolved displacement to float fall distance via a double subtraction followed by explicit float cast. A downward collision sets on-ground and resets after the fall callback/event gates; a nonnegative resolved displacement does not accumulate fall distance.

## Coverage boundary and exclusions

Examined: normal player tick/aiStep/travel delegation; `LivingEntity.travel()` ordinary dry-air branch and its movement helper; `Entity.move()` position/on-ground/fall dispatch; base `Entity.checkFallDamage()` reset/accumulation; relevant `Entity.baseTick()` fall-distance mutation reviewed for pairing (lava-only half-distance path is excluded by dry assumption); reset helper and source-tree override search.

Not examined as behavior claims: jump causation/input production, statuses/effects (including Slow Falling and Levitation), equipment/modifiers, fluid or climbable behavior, blocks/support/collision geometry/provider differences, wall/edge/piston mechanics, movement after impacts, fall damage calculation, flying/fall-flying, vehicles, packets/remote interpolation, server-only behavior, and other movement axes. These are outside the assigned vertical integration slice or are explicit exclusions.

No independent difference is submitted for this bounded operation.

## Phase 2 independent comparison addendum

Reviewer sign-off: `/root/c_vertical_117_1194_scheduler/audit_1171_118`, 2026-11-18. The frozen blind-audit prefix was rehashed before author access and matched `21cef77d6567d96ddb5ebb1da1b56743010d87b70ea2ceea6b1d25f66928c1d8`. The author source report was rehashed before reading and matched `503ed9d3a4122877989648f6ba8ef964fb33830eee9e700f45cab9ff1b0e7963`.

**Terminal disposition: agree; no source-confirmed difference in this bounded slice.** I found no disagreement in the qualifying gates, ordinary Y-position/velocity order, gravity and drag arithmetic, fall-distance threshold/reset behavior, numeric types/cast, or stated exclusions. The author's added local-player route is consistent with source: `LocalPlayer.tick()` delegates to `super.tick()`, `LocalPlayer.aiStep()` calls `super.aiStep()`, `Player.travel()` delegates to `LivingEntity.travel()` outside flying, and the ordinary dry-air travel branch is reached. The author's LocalPlayer source-file hashes match their exact manifest entries; phase-1 checks also established the actual Entity, LivingEntity, and Player files match their manifest entries. The 1.18 reset helper equivalence is confirmed (`resetFallDistance()` only assigns `0.0F` in `Entity`; no client player override was found). Extra 1.18 horizontal minor-collision bookkeeping and LocalPlayer block-space calls remain outside this no-contact vertical verdict.
