# S-EDGE-PROBE — edge backoff and support query

Status: partial endpoint comparison; no implementation, acceptance, pair-freeze, or runtime-parity claim.

## Source identity

Both endpoints are exact `unobfuscated` decompilations. Each ready marker's `versionId`/`mapping` matches its provenance `versionId`/`namespace`; both marker-declared artifact manifest hashes and source manifest hashes match the files on disk. The relevant source-file hashes below also match their entries in `unobfuscated.sources.sha256` and the files themselves.

| Endpoint | Source manifest SHA-256 | `artifacts.sha256` SHA-256 |
|---|---|---|
| 26.1.2 / unobfuscated | `54ae320660ca911a6d20514c965c33eb414fbedd1ad6727bbaa95fe906aa23c0` | `89f648229de83b0109460695c9f1f3bed7ef36efb84ede9224e80cce87118f92` |
| 26.2 / unobfuscated | `a7ad74fc712567eb87136afc1eafcfd602b4088e9869f3596331fd34faefe894` | `ba9dc53a41bca9744ec1c8ab2ce9363764a22333c39ef64d5105f4e3bf5c98ba` |

Relevant source hashes (26.1.2; 26.2):

- `world/entity/Entity.java`: `8b83b1f036aabbd13d990897c540c993f7120f02955486cfcf229517d4097ccf`; `7afb9c1294893ffe73e3b1acffcad41c648f15de8378bff3dffaff869bb811d5`
- `world/entity/player/Player.java`: `44cf28e0c64e78d39fd13368e9991381dbebab67029070cb9ddc43f09d45d14d`; `8decc71b9c780664578ddb14591db2a2f207c72c05b676edded6f8e964576531`
- `client/player/LocalPlayer.java`: `433fd995ad317af0f6ef0e50c1e8e3483cb8f00e0e327d4edf27a4dd99666ebe`; `8d089aa09217e3607b38590f7c1623385562800943ac6dfd3d17804e041da6d6`
- `world/level/CollisionGetter.java`: `dddff4897e8d5d01e3ac132d85c474e6a3b9bae928e48b8c907aa9eabdb7fb01`; `8e7d1a54d27e0f187667439539c59b8892f966f1b9c8107eb162c6d43f830f6c`
- `world/level/BlockCollisions.java`: `eb8a8f6f07b1d6384f17987818f056d41adfd6b2ad120d26774bfa550f4fc51e` at both endpoints.

## Ordered coverage

1. **Ordinary move gate and LocalPlayer forwarding:** `Entity.move` 26.1.2:704–731 and 26.2:711–738; `LocalPlayer.move` 26.1.2/26.2:986–994. The `noPhysics` fast path bypasses the edge hook; otherwise piston limiting/zero return and stuck-speed handling precede the same `maybeBackOffFromEdge` call. LocalPlayer's override calls `super.move` unconditionally, then computes float X/Z deltas for auto-jump and walked distance. No endpoint difference found in these checked gates.
2. **Player edge-backoff predicate and loop:** `Player.maybeBackOffFromEdge` 26.1.2/26.2:880–929. Both require not flying, nonpositive Y delta, SELF or PLAYER mover, staying on ground surface (shift key), and `isAboveGround(maxUpStep)`. Both use 0.05 increments, X then Z then diagonal backoff loops and return the modified horizontal delta with Y unchanged. No endpoint difference found. `LivingEntity.maxUpStep` 26.1.2:3918–3922 and 26.2:3984–3988 also matches: read the STEP_HEIGHT attribute as `float`, then raise it to at least `1.0F` when the controlling passenger is a Player. `Entity.maxUpStep` defaults to `0.0F` in both.
3. **Support-distance / fall-height predicate:** `Player.isAboveGround` and `canFallAtLeast`, 26.1.2/26.2:931–949. Both short-circuit on `onGround()`, otherwise compare `fallDistance < maxUpStep` and query with `maxUpStep - fallDistance`. The query insets horizontal bounds by `1.0E-7`, extends down by `minHeight + 1.0E-7`, and calls `level().noCollision(this, box)`. No endpoint difference found.
4. **Support state and query bounds:** `Entity.setOnGround`, `setOnGroundWithMovement`, `isSupportedBy`, and `checkSupportingBlock`, 26.1.2:659–698 and 26.2:666–705. Both construct a support slab with bottom `boundingBox.minY - 1.0E-6` and top `minY`; they query once at current bounds, and on empty support (unless `onGroundNoBlocks`) query again translated by negative movement X/Z. Grounded state updates `mainSupportingBlockPos`/`onGroundNoBlocks`; airborne state clears support state. No endpoint difference found. `CollisionGetter.findSupportingBlock`, 26.1.2/26.2:138–153, selects the intersecting block position nearest `source.position()` by squared center distance, with the same `BlockPos.compareTo` tie-break. No endpoint difference found.

## Dependency checks and open closure

For the edge probe, `CollisionGetter.noCollision(entity, box)` at 26.1.2/26.2:47–80 requires no block collision, no entity collision, and no world-border collision. Thus edge support probing is affected by all three collision sources. In contrast, `findSupportingBlock` constructs `BlockCollisions` directly (same `BlockCollisions.java` SHA at both endpoints): its scan bounds add a one-block broad-phase margin around the slab, but returned supports must intersect the exact slab and are selected from block collision shapes; this query itself does not include entity or world-border collisions. This confirms the support-query assumption requested here.

Open: complete block-shape/state/provider and neighboring-block dependency closure for these collision queries, plus any upstream changes outside this slice. The identical `BlockCollisions` implementation was checked only for query-bound/assumption context; full collision/provider behavior is not closed. S-EDGE-PROBE and the pair remain partial.
