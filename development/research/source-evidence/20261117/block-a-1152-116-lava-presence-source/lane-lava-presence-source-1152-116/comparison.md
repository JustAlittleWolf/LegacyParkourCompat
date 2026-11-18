# 1.15.2 → 1.16 source comparison: lava presence and consumers

## Inputs and scope
Exact pair: Minecraft 1.15.2 → 1.16, both canonical local Mojmap roots. Source identities and programmatically verified file hashes are in `source-roster.json`. The examined shared movement slices are (1) the lava callback/cache reset producer and (2) the player travel / held-jump consumers. The three independent source deltas are in the adjacent finding drafts. LocalPlayer's input handoff is recorded where it gates the held-jump path.

## Bounded coverage
- Examined: LiquidBlock.entityInside in both releases; Entity.move's lava-cache reset before checkInsideBlocks; Entity lava setter/getter; old water-only height cache update versus newer per-tag fluid-height update and threshold; LivingEntity lava travel admission and velocity math; LivingEntity held-jump branch; LocalPlayer input-to-jumping assignment and super.aiStep call.
- Findings: `lava-presence-surface-gate.md`, `lava-travel-admission-velocity.md`, `held-jump-lava-immersion.md`.
- Not examined: other movement areas, alternate callback implementations, comprehensive checkInsideBlocks traversal and block callback reachability, fluid tag contents/membership, all neighboring-block dependencies, other patch versions, runtime trajectories, complete pair inventory and independent pair audit. These bounded slices do not freeze or accept the full pair.
- Separate dependency: fluid tag membership is not claimed or reconciled here.

## Source-confirmed effects
The 1.16 producer adds a fluid-surface/AABB test before setting the lava-touching cache; reset-before-callback order remains. The 1.16 travel branch uses fluid-effects/standability admission and selects lava damping/falling behavior by immersion threshold. The 1.16 held-jump section adds an affected-by-fluids gate and uses selected fluid height plus the eye-height threshold and grounded state. These are exact source differences, not runtime parity results.
