# WORLD-08: End portal overlap gate

1.8.9, ornithe-feather; feather-gen2-1.8.9+build.2-mergedv2.jar, feather-gen2-1.8.9+build.2.tiny: `1.8.9/ornithe-feather/net/minecraft/block/EndPortalBlock.java`, `public void onEntityCollision(World world, BlockPos pos, BlockState state, Entity entity) {`, lines 61-65.
1.9.4, ornithe-feather; feather-gen2-1.9.4+build.2-mergedv2.jar, feather-gen2-1.9.4+build.2.tiny: `1.9.4/ornithe-feather/net/minecraft/block/EndPortalBlock.java`, `public void onEntityCollision(World world, BlockPos pos, BlockState state, Entity entity) {`, lines 63-71.

Difference: - 1.8.9 source: 1.8.9/ornithe-feather/net/minecraft/block/EndPortalBlock.java, onEntityCollision lines 61-64 On the server it calls entity.changeDimension(1) whenever vehicle == null && rider == null.

- 1.9.4 source: 1.9.4/ornithe-feather/net/minecraft/block/EndPortalBlock.java, onEntityCollision lines 63-71 It additionally requires !isRiding(), !hasPassengers(), canUsePortals(), and intersection between the entity shape and the block state's translated shape.

Movement meaning: - Scope: End Portal block player callback eligibility predicate.

Records the direct dimension-transition call condition only; destination selection and post-transfer positioning are not compared.

- Reachability: Entity.checkBlockCollisions() dispatches callbacks for every overlapped block cell, independent of collision-box intersection; local players reach this through Entity.move as recorded in COLL-01.

Therefore 1.9.4's 0.75-high shape test is distinct from 1.8.9's block-cell callback guard.

Original evidence: [WORLD-08-end-portal-overlap-gate.md](../../archive/legacy-workflows/source-campaign-2026-10-07/1.8.9--1.9.4/findings/WORLD-08-end-portal-overlap-gate.md).
