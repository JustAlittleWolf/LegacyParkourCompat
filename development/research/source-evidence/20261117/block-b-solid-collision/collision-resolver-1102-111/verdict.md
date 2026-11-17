# Independent review: 1.10.2–1.11 collision resolver

Reviewed immutable author report: `source-1102-111-collision-resolver/report.md`, SHA-256 `d6e4dd9e2e17d9b422fd51553b1b48f002e935003e71b5da43cf3d5c4b35fcd9`.

## Verdict

I independently compared the bounded ordinary collision resolver in the exact canonical `1.10.2` and `1.11` Ornithe Feather sources before opening the author report. I agree with its bounded conclusion: block collision collection/order, the X/Y/Z clip predicates, both step candidates, candidate selection, and normal-result fallback have the same movement math and tie behavior. The report correctly identifies the zero-component control-flow change and does not claim a proven reachable player-state consequence from signed zero alone.

## Evidence checked

- Source identities are the ready/provenance manifests for `1.10.2` and `1.11`, mapping artifacts `feather-gen2-1.10.2+build.2` and `feather-gen2-1.11+build.2`. Both ready markers' source-manifest and artifact-manifest SHA-256 values match the files on disk. The `1.10.2` verification record reports an identical published source tree and explains its derived remapped-JAR difference; `1.11` has a ready marker and provenance record.
- I recomputed the manifest-listed hashes for `Entity.java`, `World.java`, `Box.java`, `Block.java`, `LivingEntity.java`, and `LocalClientPlayerEntity.java` on both sides; each matches its source manifest.
- `World.getCollisions` at `1.10.2 World.java:903–965` and `1.11 World.java:960–1022` is text-identical. It scans candidate blocks in X, then Z, then Y order. The entity-box append is outside this block-only review.
- Default block collision dispatch is equivalent: `Block.addCollisions`/`addCollision`, `1.10.2 Block.java:342–353` and `1.11 Block.java:353–364`. The world-parameter type for `getCollisionShape` changes, but the dispatch and append predicate do not. Per-block shape overrides remain outside this bounded audit.

## Resolver findings

- The ordinary path collects one expanded query, clips Y then X then Z against the same ordered list, and feeds the resulting shape and values to the step resolver. `Box.intersectX/Y/Z` use the same strict orthogonal-overlap gates, directional face tests, and `<`/`>` limit tightening. Edge-only contact is not an overlap; a zero limit is unchanged.
- `1.10.2 Entity.move`, lines 518–537, always runs each axis fold and writes a moved box even when the request or clipped component is zero. `1.11 Entity.move`, lines 525–557, skips Y when requested Y compares equal to `0.0`; it skips X/Z folds for zero requests and skips their box write when the clipped result compares equal to zero. A nonzero requested Y clipped to zero still writes a moved box. This accurately describes the source difference.
- The first step candidate is Y-up, X, Z; the second is independently Y-up, X, Z from the original shape. Both sources choose candidate A only when its horizontal squared distance is strictly greater; equality chooses B (`1.10.2 Entity.java:599–611`, `1.11:621–633`). The chosen step result is reverted to the saved ordinary result on `>=`, so equality reverts (`1.10.2:619–625`, `1.11:641–647`). The report's tie descriptions are correct.
- `Box.moved` adds all three deltas to each stored coordinate and constructs a new normalized box (`1.10.2 Box.java:128–130`; `1.11:166–168`). Therefore unconditional zero writes can alter a `-0.0` coordinate's sign bit. The later position writer reads box minima and averages (`Entity.setPositionFromShape`, `1.10.2:727–732`, `1.11:749–754`), so a signed-zero difference in a relevant minimum could reach entity Y. I found no source evidence here that establishes a reachable ordinary-player shape with such a signed-zero minimum; this remains a possible representation-level consequence, not a confirmed movement finding.

## Scope limits

This review does not cover sneak-edge probing, cobweb scaling, entity collision boxes, per-block shape overrides, fluids, the complete tick graph, intermediate patches, or full-pair coverage. It establishes neither runtime parity nor pair freeze.
