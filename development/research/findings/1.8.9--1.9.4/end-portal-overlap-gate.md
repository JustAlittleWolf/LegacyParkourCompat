# End portal overlap gate

1.8.9, ornithe-feather; feather-gen2-1.8.9+build.2-mergedv2.jar, feather-gen2-1.8.9+build.2.tiny: `1.8.9/ornithe-feather/net/minecraft/block/EndPortalBlock.java`, `public void onEntityCollision(World world, BlockPos pos, BlockState state, Entity entity) {`, lines 61-65.
1.9.4, ornithe-feather; feather-gen2-1.9.4+build.2-mergedv2.jar, feather-gen2-1.9.4+build.2.tiny: `1.9.4/ornithe-feather/net/minecraft/block/EndPortalBlock.java`, `public void onEntityCollision(World world, BlockPos pos, BlockState state, Entity entity) {`, lines 63-71.

Difference: - 1.8.9 source: 1.8.9/ornithe-feather/net/minecraft/block/EndPortalBlock.java, onEntityCollision lines 61-64 On the server it calls entity.changeDimension(1) whenever vehicle == null && rider == null.

- 1.9.4 source: 1.9.4/ornithe-feather/net/minecraft/block/EndPortalBlock.java, onEntityCollision lines 63-71 It additionally requires !isRiding(), !hasPassengers(), canUsePortals(), and intersection between the entity shape and the block state's translated shape.

Movement meaning: - Scope: End Portal block player callback eligibility predicate.

Records the direct dimension-transition call condition only; destination selection and post-transfer positioning are not compared.

- Reachability: Entity.checkBlockCollisions() dispatches callbacks for every overlapped block cell, independent of collision-box intersection; local players reach this through Entity.move as recorded in COLL-01.

Therefore 1.9.4's 0.75-high shape test is distinct from 1.8.9's block-cell callback guard.

## Exact boundary and client authority

Exact 1.9 is the first checked release with the `canUsePortals()` and translated-shape intersection checks in `EndPortalBlock.onEntityCollision`. They remain in 1.9.1–1.9.4. All six checked releases gate `changeDimension(1)` on the server. In 1.9–1.9.4 the local player inherits a read-only `canUsePortals()` and the client-side callback short-circuits before the intersection. Across the reviewed releases the callback makes no direct local position, velocity, dimension, or portal-state write. Historical client movement requires no activation hook for this callback.

The independent verdict covers the presence and order of the callback checks and the client authority boundary. Detailed shape and reachability, registration, and equivalence of the riding and passenger conditions remain outside that verdict. [Source comparison](../../source-evidence/20261117/block-a-early-end-portal/report.md), [independent verdict](../../source-evidence/20261117/block-a-early-end-portal/verdict.md).
