# F-WATER-STATE — 1.13.2 adds submerged-depth and swimming state to player movement

1.12.2, ornithe-feather; feather-gen2-1.12.2+build.2-mergedv2.jar, feather-gen2-1.12.2+build.2.tiny: `1.12.2/ornithe-feather/net/minecraft/entity/Entity.java`, `public boolean checkWaterCollisions() {`, lines 948-964.
1.13.2, ornithe-feather; feather-gen2-1.13.2+build.2-mergedv2.jar, feather-gen2-1.13.2+build.2.tiny: `1.13.2/ornithe-feather/net/minecraft/entity/Entity.java`, `public void updateSwimming() {`, lines 953-959.
1.13.2, ornithe-feather; feather-gen2-1.13.2+build.2-mergedv2.jar, feather-gen2-1.13.2+build.2.tiny: `1.13.2/ornithe-feather/net/minecraft/entity/Entity.java`, `public boolean isSubmergedInWater() {`, lines 943-945.
1.13.2, ornithe-feather; feather-gen2-1.13.2+build.2-mergedv2.jar, feather-gen2-1.13.2+build.2.tiny: `1.13.2/ornithe-feather/net/minecraft/entity/Entity.java`, `private void m_03231680() {`, lines 947-951.
1.13.2, ornithe-feather; feather-gen2-1.13.2+build.2-mergedv2.jar, feather-gen2-1.13.2+build.2.tiny: `1.13.2/ornithe-feather/net/minecraft/entity/Entity.java`, `public boolean m_69693160(Tag<Fluid> tag) {`, lines 2509-2571.

Difference: 1.13.2 adds a sampled fluid depth and a swimming flag transition.

Already-swimming transition uses sprint + contact with water + not riding; entry uses sprint + submerged-in-water + not riding.

PlayerEntity suppresses swimming while flying. 1.12.2 has no submerged-depth/swimming state.

Movement meaning: - Reachability and dependencies: 1.13.2 base-tick water update runs before LivingEntity.mobTick input/jump/travel route.

State is consumed by local sprint/flight gates, PlayerEntity pose/moveRelative, and LivingEntity water jump decisions.

Source difference does not establish exact gameplay trajectories.

## Exact chest-edge body-contact witness

In Ornithe Feather 1.12.2, `Entity.checkWaterCollisions` shrinks both Y faces by `0.4F` and then contracts the box by `0.001` for `World.applyLiquidDrag`. Its floor/ceil scan tests the visited water surface against the integer upper Y bound. Exact 1.13 contracts the original box by `0.001`, admits tagged water whose surface reaches the contracted lower face, and records maximum depth from that face. The 1.13.1 and 1.13.2 sources retain this sampler.

For an ordinary non-flying, unmounted standing player at `(0.8,0.875,0.5)` supported by a chest at `(0,0,0)`, the old Y range is approximately `[1.276,2.274]` and misses source water at `(1,0,0)`. The 1.13 range is approximately `[0.876,2.674]` and admits the source surface near `0.8888889`, producing depth near `0.0128889`. With the sampled area loaded, this changes the body-water predicate read by local travel. The player's eyes remain above the surface, and the grounded sample stays below the `0.4` jump threshold. Eye submersion, swimming and current impulse remain separate from this body-contact and depth witness. [Exact finding](../../source-evidence/20261117/block-a-body-water/body-water-contact-and-immersion-height.corrected-r1.md), [source comparison](../../source-evidence/20261117/block-a-body-water/comparison.corrected-r1.md), [independent verdict](../../source-evidence/20261117/block-a-body-water/verdict.md).
