# Water travel math comparison (corrected revision 1)

## Scope and conclusion

Assigned slice: ordinary, unmounted local-player water travel, limited to the living-entity water branch’s vertical damping/gravity and sprint-dependent horizontal drag with Depth Strider movement-input dosage. Exact local decompiled sources were read only. The first observed changed endpoint is 1.13 after 1.12.2; 1.13.1 and 1.13.2 retain the 1.13 `LivingEntity.java` body. The 1.14.4 Mojmap control confirms continued horizontal expressions and vertical damping / `d/16.0` arithmetic, but its clamp gate snapshots the sign before movement and therefore does not establish exact clamp-predicate continuity. No exact patch boundary inside 1.12.2–1.13 is claimed.

## Reachability and wrapper gates

Feather 1.12.2 / 1.13 player chain: `LocalClientPlayerEntity -> ClientPlayerEntity -> PlayerEntity -> LivingEntity`. `PlayerEntity.moveRelative` is a wrapper that delegates to `LivingEntity.moveRelative`; in 1.13, before delegation, it adjusts Y only if `isSwimming() && !isRiding()` (1.13 `PlayerEntity.java` lines 1434–1458). Mojmap 1.14.4 chain: `LocalPlayer -> AbstractClientPlayer -> Player -> LivingEntity`; `Player.travel` similarly applies a swimming-only Y adjustment when `isSwimming() && !isPassenger()` before `super.travel` (lines 1380–1405). The source-derived clamp witness below therefore explicitly uses non-swimming state. The relevant living-entity methods execute under their local/logical movement-control predicate and select the water branch when the player is in water and not flying.

## Vertical math

- **1.12.2 Feather** `LivingEntity.moveRelative(float,float,float)`, lines 1555–1584: in the water branch, save `y`; after move, multiply X and Z by `f`, multiply Y by `0.8F`, then subtract fixed `0.02` if `!isNoGravity()`. This branch has no sprint gate or small-value downward clamp.
- **1.13 Feather** `LivingEntity.moveRelative`, lines 1461–1465 and 1598–1635: set gravity scalar `d=0.08`, or `0.01` if pre-movement Y is nonpositive and Slow Falling is active. In water, move, apply horizontal drag and `velocityY *= 0.8F`, then enter gravity only if `!isNoGravity() && !isSprinting()`. Gravity is `d/16.0`. It sets Y to `-0.003` if post-damping Y is nonpositive, `abs(Y - 0.005) >= 0.003`, and `abs(Y - d/16.0) < 0.003`; otherwise it subtracts `d/16.0`. Thus ordinary non-sprinting water gravity is `0.005` rather than `0.02`; sprinting suppresses the 1.13 gravity branch.
- **Reachable 1.13 clamp witness:** local player is unmounted, non-swimming, in water and non-sprinting; gravity is enabled; Slow Falling is active; pre-movement Y is `-0.002`; movement/collision does not alter Y. `PlayerEntity.moveRelative` skips its swim Y adjustment and delegates. LivingEntity damping yields `-0.0016`; `d/16.0=0.000625`; the comparisons pass and Y becomes `-0.003`. This is direct evaluation of the source operation, not a simulated trajectory.
- **1.13.1 / 1.13.2 Feather:** corresponding `LivingEntity.moveRelative` water branch, lines 1617–1654, matches; both files have identical SHA-256.
- **1.14.4 Mojmap control:** `LivingEntity.travel(Vec3)`, lines 1782–1789 and 1878–1923, retains vertical `0.8F` damping and `d/16.0` subtraction. It snapshots `bl = getDeltaMovement().y <= 0.0` at travel entry (line 1785) and later uses `bl` in the clamp at line 1911, instead of checking post-damping Y as 1.13 does. It corroborates the broad arithmetic, not an identical clamp predicate.

Vertical operation order is movement, Y damping by `0.8F`, conditional clamp-or-gravity subtraction, then the separate horizontal-collision response that can set Y to `0.3F`. Velocity and gravity intermediates are doubles; `0.8F` widens for multiplication with double velocity.

## Horizontal drag and input dosage

- **1.12.2 Feather** `LivingEntity.moveRelative`, lines 1555–1579: water drag `f` starts from `getBaseMovementSpeedMultiplier()`, which returns `0.8F` (lines 1421–1422), independent of sprinting. The separate input scale `g` starts at `0.02F`; for positive Depth Strider `h`, it is adjusted by `g += (getSpeed() - g) * h / 3.0F`.
- **1.13 Feather** `LivingEntity.moveRelative`, lines 1598–1629: `f` starts at `0.9F` when sprinting, otherwise the same `0.8F` base. Depth Strider `h` is capped at `3.0F`, halved when airborne, and for positive `h` updates drag with `f += (0.54600006F - f) * h / 3.0F`; input scale uses the separate formula `g += (getSpeed() - g) * h / 3.0F`. Dolphin’s Grace then assigns `f=0.96F`. Movement uses `g`; after moving, double X and Z velocity components are multiplied by float `f`, widened to double. Y damping is separately `0.8F`.
- **1.13.1 / 1.13.2 Feather and 1.14.4 Mojmap:** retain these horizontal formulas and gates. Mojmap uses `LivingEntity.travel` lines 1880–1907. `getSpeed()` is a direct player movement-speed attribute read in Feather 1.12.2 / 1.13 `PlayerEntity.java` lines 1407–1409 / 1483–1486; no attribute producer behavior is asserted.

With no Depth Strider and no Dolphin’s Grace, a post-move X component of double `0.1` is multiplied by widened `0.8F` or `0.9F`: exact double results are `0.0800000011920929` and `0.08999999761581422`. With Depth Strider level 3 on ground, binary32 source operations produce drag `0.54600006F` nonsprinting and `0.546F` sprinting. At level 3 while airborne (`h=1.5F`) they produce `0.67300004F` nonsprinting and `0.723F` sprinting. Dolphin’s Grace subsequently sets either drag to `0.96F`. These exact float outcomes use Java float operation order and rounding; the level, speed attribute, sprint state, and effect are treated as direct inputs only.

## Source identity and integrity

Exact releases and mapping namespaces/builds were checked in local readiness/provenance sidecars: 1.12.2, 1.13, 1.13.1, 1.13.2 Ornithe Feather build.2; 1.14.4 Mojmap. For Mojmap, the ready marker and artifact manifest identify the Mojmap variant; a legacy provenance `mappingArtifacts` field is inconsistent and is not treated as an unambiguous Mojmap artifact list. Each compared `LivingEntity.java` hash matched its adjacent source-manifest entry; recorded whole-manifest digests matched their ready/provenance sidecars. 1.13.1 and 1.13.2 `LivingEntity.java` hashes are identical.

| Release / mapping | `LivingEntity.java` SHA-256 | Source manifest SHA-256 |
|---|---|---|
| 1.12.2 / ornithe-feather | `190e9ac551538e015d9e4d6c42856e5ba32b593131cf6d93895e7b29533f1ee6` | `b8a37ccfccd2aac5f40f5e34fec85873dbdfa93a266043c103e592e4a8c949da` |
| 1.13 / ornithe-feather | `c38e95c2c13e088b03d77fc2c6b98d4edd7e6ca5aef12b187c8e3f3ea782e864` | `0509c2614b0bb0e616e2ebc6382f8eb5d031b5d86b834a4a6ed7fd96b6d68f1a` |
| 1.13.1 / ornithe-feather | `bb691358c9a43c9f46e85575bf4d0a4ad671d0eb912502acc3a6a3f625e42f1c` | `21770b01ff3bf443db2e9e1b6df7db25d2fd6b7a72a86228259269c932d3abba` |
| 1.13.2 / ornithe-feather | `bb691358c9a43c9f46e85575bf4d0a4ad671d0eb912502acc3a6a3f625e42f1c` | `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211` |
| 1.14.4 / mojmap | `428762178a876efd4069086e6b7d51f571ea0401f44e7eff0927b7d26d9ef681` | `af98406f3d4ed31494fbdce1078d1f3df3f712501878f9a0e9ad5879bdf23c3b` |

## Coverage and boundaries

Compared the water branches, movement-control and water/flying gates, player wrapper swim adjustment, gravity initialization and direct Slow Falling predicate, Depth Strider cap/airborne scaling, Dolphin’s Grace drag override, and Mojmap clamp snapshot distinction. Excludes body-water/eye sampling, climb impulse, sprint-entry policy, status/equipment producers, mounted movement, and other travel branches. No source-integrity blocker found.