# F-WATER-GRAVITY — ordinary 1.13.2 water travel uses a smaller downward adjustment

1.12.2, ornithe-feather; feather-gen2-1.12.2+build.2-mergedv2.jar, feather-gen2-1.12.2+build.2.tiny: `1.12.2/ornithe-feather/net/minecraft/entity/living/LivingEntity.java`, `public void moveRelative(float forwards, float sideways, float upwards) {`, lines 1425-1599.
1.13.2, ornithe-feather; feather-gen2-1.13.2+build.2-mergedv2.jar, feather-gen2-1.13.2+build.2.tiny: `1.13.2/ornithe-feather/net/minecraft/entity/living/LivingEntity.java`, `public void moveRelative(float forwards, float sideways, float upwards) {`, lines 1478-1669.

Difference: under those conditions 1.12.2's adjustment is `-0.02`; 1.13.2's ordinary adjustment is `-0.005`, subject to the explicit clamp branch.

Movement meaning: under those conditions 1.12.2's adjustment is `-0.02`; 1.13.2's ordinary adjustment is `-0.005`, subject to the explicit clamp branch.

## Exact 1.13 source comparison

After movement, 1.12.2 and 1.13 both multiply the vertical component by `0.8F`. With gravity enabled, 1.12.2 subtracts `0.02`. Exact 1.13 ordinarily subtracts `d / 16.0` when the player is not sprinting. This is `0.005` with ordinary gravity and `0.000625` when the pre-movement Y is nonpositive and Slow Falling sets `d` to `0.01`. For a non-swimming, unmounted local player with pre-movement Y `-0.002` under Slow Falling and no vertical collision, the 1.13 post-damping sign and absolute-value checks instead assign `-0.003`.

The 1.13.1 and 1.13.2 sources retain the 1.13 behavior. The 1.14.4 control retains the damping and gravity arithmetic but reads the sign snapshotted at travel entry for its later clamp. The separate horizontal-collision response follows damping and gravity. [Exact finding](../../source-evidence/20261117/block-a-water-travel-math/water-gravity-damping.corrected-r1.md), [source comparison](../../source-evidence/20261117/block-a-water-travel-math/comparison.corrected-r1.md), [independent verdict](../../source-evidence/20261117/block-a-water-travel-math/verdict-r1.md).
