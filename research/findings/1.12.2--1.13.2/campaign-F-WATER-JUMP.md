# F-WATER-JUMP — 1.13.2 selects jumps by fluid depth

1.12.2, ornithe-feather; feather-gen2-1.12.2+build.2-mergedv2.jar, feather-gen2-1.12.2+build.2.tiny: `1.12.2/ornithe-feather/net/minecraft/entity/living/LivingEntity.java`, `public void mobTick() {`, lines 1781-1852.
1.13.2, ornithe-feather; feather-gen2-1.13.2+build.2-mergedv2.jar, feather-gen2-1.13.2+build.2.tiny: `1.13.2/ornithe-feather/net/minecraft/entity/living/LivingEntity.java`, `public void mobTick() {`, lines 1852-1936.

Difference: 1.12.2 applies the water or lava jump impulse whenever `isInWater()` or `isInLava()` is true, otherwise a grounded jump is allowed when cooldown is zero. 1.13.2 uses sampled fluid depth: above 0.4 routes to water impulse; shallow positive depth through 0.4 allows a grounded ordinary jump when cooldown is zero; lava is checked in the alternate branch.

Movement meaning: - Reachability and dependencies: local input copies jumping into the LivingEntity field; 1.13.2's fluid-depth field is sampled by Entity's fluid-grid calculation before LivingEntity.mobTick.

Exact source-to-bytecode mapping for the raw sampler helper is still under review.

Original evidence: [F-WATER-JUMP.md](../../archive/legacy-workflows/source-campaign-2026-10-07/1.12.2--1.13.2/findings/F-WATER-JUMP.md).
