# Independent collision-query boundary review

## Verdict

**REVISE the broad candidate; ACCEPT a bounded no-difference result for ordinary standing/crouching LocalPlayer movement. Keep the global 1.16.5 → 1.17 coverage gate open.** This static review does not establish runtime trajectory parity.

The earlier `0.87500005` fixture is not a sufficient general bound. I independently traced the caller and found a stronger scoped fact: for standing and crouching players, the query's vertical interval is too tall to pass `Shapes.findBits`'s closed-world-coordinate guard in either implementation. The entity query shape therefore remains the exact continuous AABB on this ordinary on-foot route. The same applies after directional expansion and step-path movement. The singleton full-block collision path bypasses the query shape entirely and tests the same source AABB directly.

That proof does not cover all LocalPlayer poses. Swimming, fall-flying, spin-attack, and dying dimensions are at most 0.6 high, and the three-axis displacement-expanded query can in principle grow its 0.6 dimensions to grid widths while remaining in `[0,1]`. Then the changed nearest-rounding rule can encode slightly shifted query coordinates. I found no exact reachable partial-provider witness whose AND result changes, but the ordinary-height proof cannot close that possibility. No exhaustive bound over all such AABBs and provider coordinates is established here.

## Exact route and consequences

`Entity.collide` constructs block collision queries from `aABB.expandTowards(vec3)` through `collideBoundingBoxHeuristically`; the step branches pass the original or moved AABB and their subsequent query expansion. `CollisionGetter.getBlockCollisions` constructs `CollisionSpliterator`, whose constructor stores `entityShape = Shapes.create(aABB)`. The spliterator then has two distinct branches:

- If the provider returns the `Shapes.block()` singleton, admission uses `this.box.intersects(block bounds)`; it does not use `entityShape` or Boolean AND.
- Otherwise, it moves the provider shape to world coordinates and admits it only if `Shapes.joinIsNotEmpty(providerShape, entityShape, AND)` succeeds. The resulting shapes are later fed to the axis clipping in `Entity.collide`.

For the ordinary player dimensions in `Player.java`, standing height is `1.8F` and crouching height is `1.5F`. `AABB.expandTowards` can only grow an interval, and later `move` preserves its dimensions. The world-border gate uses `aABB.deflate(1.0E-7)`, which reduces height by only `2.0E-7`; both ordinary heights remain greater than one. `findBits(d,e)` first requires `d >= -1.0E-7` and `e <= 1.0000001`; an interval longer than `1.0000002` cannot satisfy both inequalities. Thus at least the Y result is `-1` for every standing/crouching collision query, whether original, displacement-expanded, step-expanded, or border-deflated. Both versions take their continuous `ArrayVoxelShape` fallback with the same exact AABB endpoints. This excludes a changed AND answer and hence a changed axis-clipped displacement caused by this query encoding on the ordinary standing/crouching route.

For shorter poses, the query's unexpanded X/Z widths are `0.6F`, which are not multiples of the finest `1/8` grid. If any axis is not displacement-expanded, that unchanged width prevents all-three-axis discrete encoding. A three-nonzero-component swimming/flying query can expand all three axes, however. Its widths can be near grid values, and its coordinates can lie wholly inside the unit interval around the origin. The old `findBits` tests `abs(v-floor(v)) < 1.0E-7`; the new one tests nearest-integer distance with `1.0E-7 * scale`. For endpoints just below a grid coordinate, the new rule can therefore snap while the old rule falls back to exact coordinates. The new coordinate displacement is strictly less than `1.0E-7` per endpoint. This is a precise conditional query-shape difference, not proof of a collision-result difference.

For the fallback-vs-discrete conditional, both `IndirectMerger` versions use the same `1.0E-7` ordering/coalescing thresholds for valid finite sorted coordinates; the endpoint shift is below that threshold. This explains the reported tiny sliver fixture but does not, by itself, establish equivalence for every possible nearby provider interval: an overlap interval can be near the merge threshold. The collision39 fixture-specific conclusion remains valid, while extrapolating it to every generated query/provider pair would be unsound.

I checked exact provider paths included in the frozen roster, including End Rod and moving piston. Their concrete examples do not supply a new changed collision result in this review. This is not a complete census of every provider/state coordinate coupled to every short-pose expanded query.

## Source publication identity and mechanical hashes

The blind source roster was frozen before prior collision reports were read. Its exact SHA-256 is mechanically recorded in `roster.md.sha256`. `source-hashes.tsv` rehashes all 25 rostered Java files for 1.16.5, 1.17, and 1.17.1 against the release publication source manifests; its ledger has no mismatching rows. `publication-identities.tsv` binds the exact 1.16.5 and 1.17.1 parent-sidecar provenance/ready/source/artifact manifests and the 1.17 embedded `.decompile-provenance.json`.

The task-relevant exact hashes (from `source-hashes.tsv`) are:

| File | 1.16.5 | 1.17 | 1.17.1 |
|---|---|---|---|
| `world/phys/shapes/Shapes.java` | `ec218dca68cad3525364967aadfbb8c0bead0cc44aa3ee2beba31e00e55ae2d4` | `8249d3e406ba400bd81258aa19be1fad4f78b451b2add85eea4f4b2209649a9e` | `8249d3e406ba400bd81258aa19be1fad4f78b451b2add85eea4f4b2209649a9e` |
| `world/level/CollisionSpliterator.java` | `19ab959c469b8edb5c371ff737a59a61b8a80bf397eff1db280064342094d297` | see ledger | see ledger |
| `world/entity/Entity.java` | `f9a9a073fe3105a0aa53d0f21ec72e59084e8d21a14c1cd3be75703865ee2666` | see ledger | see ledger |
| `world/entity/player/Player.java` | see ledger | see ledger | see ledger |

Mechanical ledger hashes are adjacent: `source-hashes.tsv.sha256`, `publication-identities.tsv.sha256`, and `roster.md.sha256`. Parent-sidecar digests are listed row-by-row in `publication-identities.tsv`; those are exact D-drive source publication identities, not inferred from missing embedded sidecars.

## Scope limit

This review closes the query-encoding dependency only for ordinary standing/crouching movement AABBs and the singleton-block fast path. Short-pose, three-axis expanded queries remain the unresolved eligible portion. I did not read product implementation, wiki/backlog, server state production, or runtime outputs; I did not build, test, launch, or regenerate sources.
