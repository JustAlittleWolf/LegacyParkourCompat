# Short-pose movement-query closure (bounded)

Pair: 1.16.5 → 1.17, checked again against 1.17.1; exact Mojmap sources only. The independently frozen query roster is `frozen-roster.md` (SHA-256 in its adjacent sidecar). This result is bounded to `Player`'s FALL_FLYING, SWIMMING, SPIN_ATTACK and DYING dimensions and the ordinary `Entity.collide(Vec3)` / movement axis-clipping path. It is not a full collision-provider or pair-wide coverage claim.

## Result

No source-level collision-axis difference is admitted from the `Shapes.create(AABB)`/`findBits` candidate within this bounded LocalPlayer query graph.

`Player.POSES` gives FALL_FLYING, SWIMMING and SPIN_ATTACK dimensions as `0.6F × 0.6F`; DYING is `0.2F × 0.2F`, in all three endpoint trees. Java promotes these float dimensions to double for the entity box, so the extents are approximately 0.6000000238418579 and 0.20000000298023224. Neither extent is a grid width `n / 2^i` for `i ∈ [0,3]`.

In `Entity.collide`, the movement-provider queries are raw `AABB` arguments: entity collisions receive `aABB.expandTowards(vec3)`; the normal axis clip consumes provider VoxelShapes against the current/moved AABB. `CollisionGetter.getBlockCollisions` wraps the same AABB in `CollisionSpliterator`; it does not encode that expanded query with `Shapes.create`. For one- and two-axis displacement, at least one unexpanded short-pose axis retains a non-grid extent, so even a hypothetical `Shapes.create(expandedBox)` would take the continuous-array fallback in both versions. With all three components nonzero, the dimensions can be selected so all three expanded extents land exactly on dyadic widths (for example 0.625 for a 0.6F pose, or 0.25 for a 0.2F pose); exact grid endpoints satisfy both old and new findBits and quantize to the same occupied bounds. The versions' differing below-grid tolerance can admit other expanded dimensions in 1.17 only, but the actual movement graph does not pass those expanded query boxes through `Shapes.create`, so that hypothetical encoding cannot alter provider selection or axis clipping here.

The actual `Shapes.create` call in this `Entity.collide` path is `Shapes.create(aABB.deflate(1.0E-7))` for the world-border inclusion predicate. It uses the current, not movement-expanded, LocalPlayer box. Deflation leaves each short-pose extent approximately 0.5999998 or 0.1999998; neither is a dyadic grid width. Thus old and new both construct the continuous `ArrayVoxelShape` with those same endpoints (assuming the same finite in-range position; out-of-range coordinates reject grid encoding in both). The Boolean AND result used to include/exclude the border is therefore unchanged for equal player box and equal border shape, and no changed border stream reaches the axis clip from this candidate.

This closes only the assigned short-pose `Entity.collide`/`Shapes.create` query concern. It does not audit other callers of `Shapes.create`, all pose transitions, every collision provider, or the full movement pair.

## Exact source anchors

- `Player.java`: pose map and `getDimensions` in 1.16.5, 1.17, 1.17.1.
- `Shapes.java`: `create(AABB)`, scalar `create`, `findBits` in all three versions.
- `Entity.java`: private `collide(Vec3)`, especially border test, expanded entity query, and `collideBoundingBoxHeuristically` / axis collision calls.
- 1.17 `CollisionGetter.java`: `getBlockCollisions` forwards the AABB to `CollisionSpliterator`.

Exact full-file SHA-256 values and their matching source-manifest entries are in `source-hashes.tsv`. 1.16.5 and 1.17.1 use their parent `mojmap.sources.sha256` sidecars; 1.17 uses the `sources` object in `mojmap/.decompile-provenance.json`.
