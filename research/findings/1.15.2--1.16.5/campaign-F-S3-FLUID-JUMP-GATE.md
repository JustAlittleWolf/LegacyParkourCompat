# F-S3-FLUID-JUMP-GATE: flying players skip liquid jump impulses

- **1.15.2**, mojmap: `net/minecraft/world/entity/LivingEntity.java`; lines 2265-2279.
- **1.16.5**, mojmap: [source locations in preserved report](../../archive/legacy-workflows/source-campaign-2026-10-07/1.15.2--1.16.5/findings/F-S3-FLUID-JUMP-GATE.md); lines 2427-2451.

## Difference

For a flying player who is jumping and otherwise satisfies 1.15.2's liquid-jump condition, 1.15.2 enters the liquid-jump decision and can invoke its water or lava impulse. 1.16.5 rejects the whole branch before reading fluid height because its Player override returns false while flying. 1.16.5 also reaches the `noJumpDelay = 0` path in that case.

This slice is distinct from LocalPlayer's crouch-to-descend input, covered by F-S1-WATER-DESCENT.

## Movement meaning

The jump flag is sampled in the living movement step; 1.15.2's liquid branch is controlled by water height, lava contact, onGround and noJumpDelay. 1.16.5 measures the matching fluid height and additionally gates through the Player flying ability.

The finding only concerns the liquid jump path, not creative flight toggle input or ground jumping outside the liquid conditions.

The source proves that the liquid jump helper is reachable in 1.15.2 and skipped in 1.16.5 under the stated predicates.

[Archived report](../../archive/legacy-workflows/source-campaign-2026-10-07/1.15.2--1.16.5/findings/F-S3-FLUID-JUMP-GATE.md).
