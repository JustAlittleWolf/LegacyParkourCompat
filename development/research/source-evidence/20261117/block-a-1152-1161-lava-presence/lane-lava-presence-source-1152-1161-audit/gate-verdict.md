# Independent bounded source gate verdict

## Disposition

**Behavioral comparison: accepted for the stated bounded source lane. Source-input identity: qualified, with a raw-artifact verification gate still open.** This does not freeze the whole 1.15.2–1.16.1 pair, establish intermediate release boundaries, or establish runtime parity.

## Compared immutable author artifact

The author comparison was opened only after the independent source roster was frozen. Its SHA-256 matched the assigned identity exactly: `ea24239644b5bb918ba266e0574611a460ba9e0400d48924ca85887f058599fa`.

## Independent source result

The hash-verified 1.15.2/1.16.1 source bodies support the comparison's bounded claims:

- 1.15.2 `LiquidBlock.entityInside` sets lava contact for every lava callback from `Entity.checkInsideBlocks`; the callback itself has no fluid-surface test.
- 1.16.1 preserves callback-driven contact but applies `aabb.minY < fluidSurface || fluidSurface > aabb.maxY`. For an ordinary non-inverted player AABB, the second disjunct is subsumed by the first, so contact requires the lava surface to be strictly above the AABB bottom.
- `Entity.move` clears this latch immediately before `checkInsideBlocks`. `LivingEntity.aiStep` consumes the latched state for jump selection before invoking travel; travel movement can refresh the latch for a later jump step.
- 1.16.1 separately clears and refreshes the tag-keyed fluid-height map in `baseTick`, before the later LivingEntity AI step. The lava scan uses the 0.001-deflated bounding box and stores the maximum admitted surface-minus-bottom depth; water detection is processed first and suppresses the lava scan when the entity is in water.
- For a local non-flying player, jump and travel have a depth split at `getFluidJumpThreshold()` (0.4 for ordinary player eye height); the 1.15.2 lava path has no cached-depth split. A complete travel witness also requires the lava branch to be selected: not in water, fluid-affected, and unable to stand on the current fluid.

The comparison's distinguishing callback witness is valid when a player AABB intersects a lava block but its bottom is at or above that cell's fluid surface, with no other lava callback setting the latch. The 1.15.2 callback sets contact; the 1.16.1 callback does not. The source inspection confirms source-level reachability, not an executed trajectory.

## Remaining gate and limits

The source manifests and relevant source-file hashes match their ready markers and sidecars. The author’s requested hash is exact. The provenance files also identify the exact release IDs, Mojmap family and decompiler/tool run. But the original artifact cache path recorded in provenance is missing, so the 38 pinned client/mapping/dependency input bytes on each side could not be rehashed. This blocks an unqualified exact raw-input provenance verdict; it does not change the direct comparison of the locally hash-verified source methods. Reopen this gate only if the raw pinned artifacts or a separately verified exact publication becomes available.

No intermediate release was inspected. No source-resource equality was inferred. No primary checkout source, product, wiki, backlog, build, test, game/runtime, Docker, network or Git state was changed or used.