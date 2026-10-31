# Addendum — short-pose merger counterexample (2026-10-31)

This addendum supersedes the short-pose uncertainty in `review.md` for the bounded `CollisionSpliterator` block-query route. The parent review confirmed the exact graph: `CollisionGetter.getBlockCollisions(entity,aABB)` constructs `CollisionSpliterator`, and that constructor calls `Shapes.create(aABB)`. The earlier broad review already records the standing/crouching proof and is retained unchanged.

## Erratum review

The general claim in `short-pose-collision-audit/result.erratum-1.md` that endpoint movement strictly below `1.0E-7` always suppresses a changed AND admission is false. `IndirectMerger` uses a `1.0E-7` coalescing tolerance, but a query endpoint can move by less than that tolerance and change an overlap interval from just above the tolerance to just below it. The merger then returns a true AND cell in one endpoint and no AND cell in the other. This is not merely an arbitrary interval-list counterexample: the exact 1.16.5 piston provider source supplies a conditional vanilla provider state for it.

## Conditional source witness

Use a `Player` `SWIMMING` pose (`0.6F × 0.6F`), LocalPlayer position that yields the following current box, equal block/entity state inputs across versions, a clear border, and an otherwise empty collision stream:

- Current player AABB: X `[0.27499991, 0.8749999338418579]`, Y/Z `[0.125, 0.7250000238418579]`. The X width and Y/Z widths are `0.6F` (`0.6000000238418579`). These values are obtainable from `EntityDimensions.makeBoundingBox` by setting the LocalPlayer position to the corresponding center coordinates; that method uses `width / 2.0F` and creates `[center-halfWidth, center+halfWidth]`.
- Same resolved `Entity.move` request: `(-0.15, +0.02499997615814209, +0.02499997615814209)`. All three components are nonzero. `Entity.collideBoundingBoxHeuristically` therefore uses its block-query branch and asks for `getBlockCollisions(entity, aABB.expandTowards(vec3))`.
- Expanded query AABB: X `[0.12499991, 0.8749999338418579]`, Y/Z `[0.125, 0.75]`. In 1.16.5, X fails `findBits` at every denominator because the scaled endpoints lie just below integers and the old test compares to `floor`. In 1.17, denominator 8 passes: each X endpoint rounds to the nearest eighth (`[0.125,0.875]`); Y/Z are exact eighth-grid intervals. The resulting `entityShape` is discrete in 1.17 and continuous with exact query endpoints in 1.16.5.
- At block position `(0,0,0)`, use a vanilla `MOVING_PISTON` with `movedState=STONE`, `source=false`, `extending=false`, `direction=WEST`, and stored `progress=0.12500004470348358F`. `PistonMovingBlockEntity.load` reads the progress float directly and sets `progressO=progress`; `getExtendedProgress` computes `1.0F-progress`, which is `0.8749999403953552F`. With the default null `NOCLIP` thread-local, `getCollisionShape` returns the moved full cube shifted WEST by that amount, with X bounds `[-0.8749999403953552, 0.12500005960464478]` and full Y/Z bounds `[0,1]`. It is a non-singleton translated shape, so it takes the Boolean AND branch in `CollisionSpliterator`.

On X, the old continuous query overlaps the piston shape by `0.00000014960464476965196` (> `1.0E-7`). Its retained merger coordinates produce a cell whose query and provider voxel indices are both full, so `joinIsNotEmpty(...,AND)` admits the shape. In 1.17, the discrete query starts at `0.125`; its overlap with the same provider ends at `0.12500005960464478`, only `0.0000000596046448` wide. The query-min and provider-max coordinates coalesce in `IndirectMerger`; their updated indices leave the query voxel outside, so no AND cell is admitted. Y/Z have broad overlap in both versions. `CollisionSpliterator` therefore emits the moving-piston collision shape only in 1.16.5.

This also changes the resolved X axis. The current player X-min is `0.27499991`, strictly to the right of the piston shape's X-max `0.12500005960464478`; its gap is `0.1499998503953552`. The Y/Z passes do not collide with the piston because the current X intervals are disjoint. In 1.16.5 the admitted shape clips the requested `-0.15` X movement to `-0.1499998503953552`. In 1.17 the query shape does not admit it and, with the other collision streams clear, X remains `-0.15`. The player box is not initially intersecting the piston: the piston lies ahead in the negative-X travel direction, so this is an ordinary approach-to-collision axis clip, not depenetration.

The provider progress value is a conditional persisted input, not a claim that a normal piston tick sequence naturally reaches that fraction. The paired comparison holds that saved block-entity input fixed. No server/state-production difference is used. This is a static exact-source witness, not a runtime trajectory proof.

## Exact paired-source anchors

`source-hashes.tsv` and `publication-identities.tsv` in this review bind the files to their release manifests/provenance. Relevant `source-hashes.tsv` rows include `Shapes.java`, `Entity.java`, `Player.java`, `CollisionGetter.java`, `CollisionSpliterator.java`, and `PistonMovingBlockEntity.java`; all listed rows match their declared source-manifest/provenance hashes. The precise pair behavior is in these source members:

- 1.16.5 `CollisionGetter.getBlockCollisions` → `CollisionSpliterator.<init>` / `collisionCheck`.
- 1.16.5 `Entity.collide`, `collideBoundingBoxHeuristically`, `collideBoundingBoxLegacy`, and `Shapes.collide`.
- 1.16.5 `Player` swimming dimensions; `EntityDimensions.makeBoundingBox`.
- 1.16.5 `PistonMovingBlockEntity.load`, `getMovementDirection`, `getExtendedProgress`, and `getCollisionShape`; `MovingPistonBlock.getCollisionShape` delegates to the block entity.
- 1.16.5 versus 1.17 `Shapes.create(AABB)` / `findBits` and `IndirectMerger`.

The 1.17 and 1.17.1 `Shapes.java`, `CollisionGetter.java`, and `CollisionSpliterator.java` bytes are identical per the exact source ledger. The witness therefore marks a 1.16.5 → 1.17 query-admission and collision-axis candidate, not a separate 1.17 → 1.17.1 change.

## Independent exact-merger replay note

The replay distinguishes the two cases rather than treating every sub-tolerance overlap alike. Old X lists are provider [-0.8749999403953552, 0.12500005960464478] and query [0.12499991, 0.8749999338418579]; after skipping the provider's exterior minimum, the retained query-minimum and provider-maximum boundaries are 1.4960464476965196E-7 apart. IndirectMerger.forMergedIndexes therefore emits the cell index pair (provider cell 0, query cell 0), both full, and joinIsNotEmpty is true. New X lists use the same provider and a discrete query with eighth coordinates and filled cells 1 through 6. The query minimum .125 and provider maximum .12500005960464478 are coalesced; the retained pair is (provider cell 1, query cell 1), with the provider index outside its sole filled cell, so joinIsNotEmpty is false. This checks the specific source-style replay requested by the parent and explains why a generic statement that all sub-1.0E-7 slivers disappear is insufficient.
