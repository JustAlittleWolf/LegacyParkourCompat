# F-WATER-JUMP — 1.13 first selects water jumps by fluid depth

1.12.2, ornithe-feather; feather-gen2-1.12.2+build.2-mergedv2.jar, feather-gen2-1.12.2+build.2.tiny: `1.12.2/ornithe-feather/net/minecraft/entity/living/LivingEntity.java`, `public void mobTick() {`, lines 1781-1852.
1.13.2, ornithe-feather; feather-gen2-1.13.2+build.2-mergedv2.jar, feather-gen2-1.13.2+build.2.tiny: `1.13.2/ornithe-feather/net/minecraft/entity/living/LivingEntity.java`, `public void mobTick() {`, lines 1852-1936.

Difference: 1.12.2 applies the water or lava jump impulse whenever `isInWater()` or `isInLava()` is true, otherwise a grounded jump is allowed when cooldown is zero. 1.13.2 uses sampled fluid depth: above 0.4 routes to water impulse; shallow positive depth through 0.4 allows a grounded ordinary jump when cooldown is zero; lava is checked in the alternate branch.

Movement meaning: - Reachability and dependencies: local input copies jumping into the LivingEntity field; 1.13.2's fluid-depth field is sampled by Entity's fluid-grid calculation before LivingEntity.mobTick.

### Exact 1.12.2 → 1.13 water boundary

With jump held and body water contact in 1.12.2, the water branch adds the widened `0.04F` to double Y velocity without checking depth, grounding or the ground-jump cooldown. In 1.13, an ordinary grounded player with `0 < waterHeight <= 0.4` takes the ground jump only when the cooldown is zero, assigning the default widened `0.42F` and setting cooldown to 10. A nonzero cooldown gives that shallow grounded branch no impulse. Airborne positive depths through 0.4 and depths above 0.4 use the water impulse. The cooldown decrements before selection and resets when jump is released. This concerns selection using sampled water depth. [Exact source finding](../../source-evidence/20261117/block-a-water-vertical-input/held-jump-water-depth.md) · [Independent verdict](../../source-evidence/20261117/block-a-water-vertical-input/verdict.md)
