# 1.19.4 to 1.20 fluid movement source comparison

## Bounded scope and conclusion

Compared normal, non-riding local-player body fluid admission and stored water/lava heights, fluid-current push/normalization, eye-fluid state order, and the ordinary first water/lava `LivingEntity.travel` response. Assumed the effective water/lava fluid-tag membership and world inputs are matching, as assigned. Under that bound, all four operations preserve the same fluid-contact predicates and the same position/velocity response. No in-scope player movement difference was established; no canonical movement finding is included.

One adjacent source change is recorded separately: 1.19.4 resets fall-distance tracking when downward-or-level travel has Slow Falling, while 1.20 does not. The reset is not read by either fluid movement branch and does not change its immediate position or velocity operations. It affects fall-distance bookkeeping; its later damage use is outside this assignment's scope, so it is not promoted as a movement finding.

## Exact source identity and integrity

Both sides are Mojmap client decompilations. 1.19.4 ready metadata identifies version `1.19.4`, mapping `mojmap`, client JAR SHA-256 `0e79cf7f07c107e9a1fe22ed703e472372b44149e64114944f0811abbd25f3ec`, and source manifest SHA-256 `6286e325371e085dfee1ab7987e66d4b6d1a984a49b6ec8ea730045cd24cecb3`; the manifest file itself hashes to that value. The artifact manifest SHA-256 is `e84b8441615fd386ccdd3bfde71b7b1d658061e9383daffe41842e9acb7afb72`, also matching its ready metadata. The exact run records Mojmap, Java 25, Vineflower 1.12.0, Tiny Remapper 0.14.1, Gradle 9.7.1, and ASM 9.10.1.

The 1.20 embedded provenance records the official `1.20.json` input URL and SHA-256 `24f4700f86471b6f3d3e4d68fefcd4177820dade5ce003c095fcf0e429abbc5f`, Mojmap client mapping input SHA-256 `fdd921d6bcc7657850f78514d36bb57af0b8a340786d12c46ec18d9c46c039de`, and client JAR input SHA-256 `5ac9ead135746f5f27048f2e01d8d5bdc2dabc4d91422f73ef2435f4c3d24aff`. The embedded pipeline/tool identity matches the recorded 1.19.4 pipeline and tool versions. Each examined source file's SHA-256 was checked against its side's source manifest/provenance entry:

| Source | 1.19.4 SHA-256 | 1.20 SHA-256 |
| --- | --- | --- |
| `net/minecraft/world/entity/Entity.java` | `3667fee610cbc5f58012e3a8fb8d6c4849f649fb7fe5300595158112d8b4b58b` | `94b9c3656715de2d61fa9a02ccef164261e50eb13d6090a6f07e58fe9c5b0759` |
| `net/minecraft/world/entity/LivingEntity.java` | `c8d91af61f87aaa1666de79696d7cd9d4a8212d05f873bdaf28d7bb2926bf165` | `decf8cd70d194098ad51d0c82e5a0087e0687881e0a2dc66456f2ba6ea76676f` |
| `net/minecraft/client/player/LocalPlayer.java` | `8e7da18f42d09fbb994f522c2b0e65fcb2bb83cabb21024360299d44d9674c58` | `69a2d043d2c0595bd364d625a5445e4934d9e07fef020cd79fafb42f59952ac2` |
| `net/minecraft/world/level/material/FluidState.java` | `0d6d1d24deb58f056163acc3ae8976d2c7b3394aca2ae37fa43bc560c9d33b7c` | `0d6d1d24deb58f056163acc3ae8976d2c7b3394aca2ae37fa43bc560c9d33b7c` |
| `net/minecraft/tags/FluidTags.java` | `df79262149bf0e684d877ca5d2bb87f02d8b66d55ed123d4c2e93bfa6462396f` | `df79262149bf0e684d877ca5d2bb87f02d8b66d55ed123d4c2e93bfa6462396f` |

## 1. Body fluid admission and stored heights

1.19.4 `Entity.baseTick` calls `updateInWaterStateAndDoFluidPushing()` before `updateFluidOnEyes()` (`Entity.java:409-432`). The 1.20 order is identical (`Entity.java:413-436`). The contact updater clears `fluidHeight`, checks water first, then computes the dimension-dependent lava strength (`0.007` in ultra-warm dimensions, otherwise `0.0023333333333333335`) and updates lava height (`1.19.4 Entity.java:1072-1077`; `1.20 Entity.java:1176-1181`). For ordinary non-riding players, water admission sets `wasTouchingWater` from the result of the water scan; absent admission it clears that flag (`1.19.4 Entity.java:1080-1093`; `1.20 Entity.java:1184-1197`). `isInWater()` reads this stored flag in both releases (`1.19.4 Entity.java:1033-1035`; `1.20 Entity.java:1137-1139`), while `isInLava()` reads positive stored lava height and rejects `firstTick` in both (`1.19.4 Entity.java:1177-1179`; `1.20 Entity.java:1280-1282`).

The scan algorithm is operation-order equivalent: return false on touching an unloaded chunk; deflate the bounding box by `0.001`; floor minima and ceil maxima; traverse X, then Y, then Z with exclusive upper bounds; for matching fluid, compute the surface and admit it when `surface >= deflatedBox.minY`; retain the maximum surface-to-bottom height; and store it after applying current pushing (`1.19.4 Entity.java:2887-2951`; `1.20 Entity.java:3001-3065`). Water-before-lava ordering and the water flag write are unchanged. Scope assumes the matching effective tag/world inputs specified above; providers and datapack membership are not inferred here.

## 2. Current push and normalization

The same scan accumulates each admitted cell's flow, scales the current flow by the running maximum height when that height is below `0.4`, and increments the flow count (`1.19.4 Entity.java:2911-2924`; `1.20 Entity.java:3025-3038`). After the scan, a nonzero total is averaged by count; normalization is skipped for `Player` instances and applied only to non-players; then the average is scaled by the supplied push strength. When horizontal speed is below `0.003` on both axes and push length is below `0.0045000000000000005`, the push is normalized to that minimum length before being added to delta movement (`1.19.4 Entity.java:2931-2948`; `1.20 Entity.java:3045-3062`). The player type gate, constants, arithmetic order, and `fluidHeight.put`/return order match. The 1.20 `level()` accessor spelling replaces 1.19.4's `level` field access without changing the operation.

## 3. Eye-fluid state order

The eye update first copies `isEyeInFluid(WATER)` from the existing `fluidOnEyes` set into `wasEyeInWater`, then clears the set. It samples at `getEyeY() - 0.11111111F`, skips sampling when the bounding box of an applicable boat covers that Y, obtains the fluid at the eye block, computes `blockY + getHeight`, and adds the fluid's tags only when that surface is strictly above the sample (`1.19.4 Entity.java:1096-1108`; `1.20 Entity.java:1200-1212`). `isEyeInFluid` remains set membership and `isUnderWater` combines the saved eye flag with current body-water state (`1.19.4 Entity.java:1058-1060,1173-1175`; `1.20 Entity.java:1162-1164,1276-1278`). Thus the prior-set read, clear, sample offset, strict comparison, and tag write have identical order. No eye-state delta was found.

## 4. Ordinary first water/lava travel response

`LocalPlayer` does not override `LivingEntity.travel`; its `aiStep` calls `super.aiStep()` in both releases (`LocalPlayer.java:645-795` in 1.19.4; `648-801` in 1.20). The inherited ordinary travel path is entered only when `isControlledByLocalInstance()`; the analyzed branch selection uses the stored water/lava states from the preceding base tick and current `isAffectedByFluids()` / `canStandOnFluid` predicates.

The water path is equivalent: start gravity at `0.08`; for vertical velocity `<= 0` with Slow Falling, use `0.01`; require body water, fluid effect eligibility, and inability to stand on the sampled block fluid; select sprint slowdown `0.9F` or configured water slowdown and input acceleration `0.02F`; cap Depth Strider at `3.0F`, halve it when airborne, interpolate slowdown toward `0.54600006F` and acceleration toward player speed; set slowdown to `0.96F` with Dolphin's Grace; add relative input and move by SELF; apply climbable collision Y `0.2`; apply `(slowdown, 0.8F, slowdown)` drag; then apply fluid-falling adjustment and the horizontal-collision `0.3F` pop-up check (`1.19.4 LivingEntity.java:1983-2027`; `1.20 LivingEntity.java:2004-2047`).

The lava path is equivalent: require lava, fluid effect eligibility, and inability to stand on the sampled fluid; add relative input at `0.02F`; move by SELF; at/below fluid jump threshold apply `(0.5, 0.8F, 0.5)` drag then fluid-falling adjustment, otherwise scale the whole vector by `0.5`; subtract gravity/4 only when gravity is enabled; and perform the same horizontal-collision pop-up test (`1.19.4 LivingEntity.java:2028-2047`; `1.20 LivingEntity.java:2048-2067`). `getFluidFallingAdjustedMovement` has the same no-gravity/sprinting gate, `0.005`, `0.003`, and `/16.0` comparisons and calculations (`1.19.4 LivingEntity.java:2165-2178`; `1.20 LivingEntity.java:2184-2197`).

The only adjacent fluid-travel difference is the Slow Falling bookkeeping noted above: after choosing `0.01` gravity for downward-or-level movement, 1.19.4 calls `resetFallDistance()` (`LivingEntity.java:1985-1990`) and 1.20 does not (`LivingEntity.java:2006-2010`). Neither water nor lava movement reads fall distance in the compared branch; the branch's immediate position and velocity math remains the same.

## Coverage boundary

Examined the requested normal non-riding local-player body contact states, water/lava height scan, current pushing, eye-state update order, and first ordinary water/lava travel response with adjacent fluid-fall adjustment. Not assessed: boat behavior, other entity types, riding, Elytra/creative travel, effects or equipment changes outside the captured branch predicates, solid collision geometry/providers, fluid/tag resource membership beyond the assigned matching-input assumption, damage/fall-damage outcomes, custom datapacks, complete pair coverage, and runtime parity.
