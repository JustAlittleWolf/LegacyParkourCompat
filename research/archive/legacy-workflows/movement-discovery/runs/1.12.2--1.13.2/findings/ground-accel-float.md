# 1.13.2 changes the on-ground acceleration coefficient by one float step

- Older version A: 1.12.2
- Newer version B: 1.13.2
- Mechanic / coverage slice IDs: 3.3 (ground relative acceleration)
- Classification: changed behavior
- Confidence: source-confirmed
- Applicability: historical player movement on ordinary solid ground
- First changed release: unknown within (1.12.2, 1.13.2]
- Runtime validation: not performed

## Paired evidence

- A: `decompiled_minecraft/1.12.2/ornithe-feather/net/minecraft/entity/living/LivingEntity.java`, `moveRelative(FFF)V`, line 1485; SHA-256 `190e9ac551538e015d9e4d6c42856e5ba32b593131cf6d93895e7b29533f1ee6`: `0.16277136F / (t * t * t)`.
- B: corresponding method, line 1545; SHA-256 `bb691358c9a43c9f46e85575bf4d0a4ad671d0eb912502acc3a6a3f625e42f1c`: `0.16277137F / (t * t * t)`.
- The quotient is consumed as the on-ground acceleration multiplier: when `onGround`, the branch sets `v = getSpeed() * u`, then passes `v` to `updateVelocity(forwards, sideways, upwards, v)` (A lines 1487-1495; B lines 1547-1555). For the default block slipperiness `0.6F` (A `Block.java:70`; B `Block.Properties` default at `Block.java:1907`) and the shared `* 0.91F` friction expression, binary32 arithmetic yields `u = 0.9999998211860657F` in A and `u = 0.9999998807907104F` in B. Block source hashes: A `e4a90eca411e7b0e14f5018f7385ec29891f6cdf1a1e917fa648b5712f9b33a1`; B `735030e8bb5fc7ead2d6dbcb1b262a36a414b4349daf197456337f460b444cd4`.
- Reachability: `LivingEntity.mobTick()` calls `moveRelative()` during travel (A lines 1842-1848; B lines 1920-1926); the local player follows this path.

## Source-level difference

The input acceleration coefficient uses adjacent binary32 literals. For the default `0.6F` slipperiness and `0.91F` factor, the computed coefficient differs by one binary32 step before it scales movement input. Other slipperiness values should be resolved from their exact block source in stage 5; no general claim is made that every denominator preserves a distinct quotient.

## Reachability and dependencies

The branch runs for local movement outside water, lava and fall-flying; when `onGround`, it uses the block beneath the player to calculate friction. The player speed attribute and input values supply the remaining multiplier inputs. Block-specific slipperiness and any version differences in those values remain in stage 5.

## Consequence and uncertainty

Source and the exact default-block float calculation prove different acceleration coefficients under the stated precondition. This predicts a small difference in velocity input each eligible tick; no trajectory was measured. The full interaction with block-specific friction and accumulated rounding remains open.

## Handoff

One-ULP on-ground acceleration constant change. Related to ground friction inputs, independently describable from water and swim movement.
