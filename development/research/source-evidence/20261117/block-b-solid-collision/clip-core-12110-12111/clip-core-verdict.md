# Independent source verdict: ordinary Entity block clipping core

## Bound claim and verdict

Reviewed claim: source-lane revision 2, `source-12110-12111-clip-core/source-report-r2.md`, SHA-256 `0077f6feac7a15ff1f9829c4ff9197be664f07763dc365bc1082ae2116ef6e4c`.

Verdict: **Agree: no source difference found in the bounded ordinary loaded-interior, block-only, non-riding Entity clipping path.** This is an independent static source review of the listed operation slices. It is not full-pair coverage and makes no runtime trajectory claim.

## Exact evidence identity

- Older input: Minecraft 1.21.10 Mojmap, canonical `decompiled_minecraft/1.21.10/mojmap/`.
- Newer input: Minecraft 1.21.11 Mojmap, canonical `decompiled_minecraft/1.21.11/mojmap/`.
- `mojmap.ready.json` identifies the matching exact version and `mojmap` mapping on each side. Its `sourceManifestSha256` equals the actual SHA-256 of `mojmap.sources.sha256`: 1.21.10 `4be26049350c1b314a0b198022cb1e7ab1e745e104047de7a5c7d099a9d7b7e1`; 1.21.11 `0c4d83fef84c101d9db88f331acec7960f3c8346228c28995e6cf57bbfcae555`. The corresponding provenance sidecars identify the exact-version Mojmap Vineflower run.
- Recomputed source hashes match each manifest entry for `Entity.java`, `AABB.java`, `CollisionGetter.java`, `BlockCollisions.java`, `Cursor3D.java`, `VoxelShape.java`, `Shapes.java`, `Direction.java`, and `Level.java` on both sides.

## Retraced operations

For these paired executable bodies, I compared the source bodies directly. Each listed body is identical across the pair; source line movement is due to surrounding source layout/annotation changes.

| Operation | 1.21.10 Mojmap | 1.21.11 Mojmap | Verdict |
|---|---|---|---|
| Entity movement entry and ordinary collision path: `Entity.move`, `Entity.collide`, `collideBoundingBox`, `collectColliders`, `collideWithShapes` | `world/entity/Entity.java:670-766, 1025-1054, 1077-1119` | `world/entity/Entity.java:685-781, 1044-1073, 1096-1138` | Same gates, call order, requested/resolved movement handling, expanded-box broadphase and axis clipping |
| AABB expansion, translation and strict overlap predicates: `expandTowards`, `move`, `intersects` | `world/phys/AABB.java:155-178, 217-251` | `world/phys/AABB.java:155-178, 217-251` | Same signed expansion, coordinate additions, and strict comparisons |
| Block-collision iterator construction/context | `world/level/CollisionGetter.java:95-105` | `world/level/CollisionGetter.java:96-106` | Same block-only iterator construction and arguments |
| Block scan setup, cached loaded-chunk lookup and candidate handling: `BlockCollisions` constructors, `getChunk`, `computeNext` | `world/level/BlockCollisions.java:35-100` | `world/level/BlockCollisions.java:34-98` | Same floor bounds with `1.0E-7` padding and extra cell, cache key/lookup, candidate gates, shape translation/intersection and yield order |
| Collision chunk access | `world/level/Level.java:668-670` | `world/level/Level.java:656-658` | Same `getChunk(..., ChunkStatus.FULL, false)` call; newer nullable annotation does not alter the body |
| Cursor enumeration and type | `core/Cursor3D.java:19-41` | `core/Cursor3D.java:19-41` | Same x-fastest, then y, then z enumeration and boundary-count classification; the corner type is skipped by `BlockCollisions` |
| Shape clipping/indexing | `world/phys/shapes/VoxelShape.java:247-302` | `world/phys/shapes/VoxelShape.java:248-303` | Same `AxisCycle`, `1.0E-7` near-zero cutoff and coordinate/index padding, scan limits, movement-sign traversal, first occupied-cell handling and min/max gap clipping |
| Sequential shape resolution and axis tie order | `world/phys/shapes/Shapes.java:211-219`; `core/Direction.java:375-377` | `world/phys/shapes/Shapes.java:210-218`; `core/Direction.java:372-374` | Same ordered iteration/early-zero return and `abs(x) < abs(z)` branch (horizontal tie selects YXZ) |

The ordinary block path therefore preserves the same requested displacement expansion, scanned candidate volume and visitation order, loaded chunk selection, shape overlap filtering, per-axis voxel clipping and sequential axis/shape resolution in both versions. The ordinary, loaded-interior premise removes unloaded and world-boundary behavior from this verdict.

## Limits

Step-up, edge support, piston responses, velocity collision flags/responses, moving-border timing, entity-vs-entity collision, unloaded/out-of-world behavior, and arbitrary block shape-provider compositions are not reviewed here. This verdict does not assert pair freeze, intermediate patch boundaries, or runtime parity.

### Shape-overlap helper dependency

The non-canonical-shape branch of `BlockCollisions.computeNext` calls `Shapes.joinIsNotEmpty(..., BooleanOp.AND)`. I also compared its direct geometry helpers and the discrete occupancy traversal they use: `Shapes.create(AABB)`, `joinUnoptimized`, both `joinIsNotEmpty` overloads, `createIndexMerger`, `DiscreteVoxelShape.isFullWide/getSize`, `BitSetDiscreteVoxelShape.isFull/forAllBoxes`, `CubeVoxelShape.findIndex`, and the `forMergedIndexes` implementations in `IdenticalMerger`, `DiscreteCubeMerger`, `IndirectMerger`, and `NonOverlappingMerger`. Their executable bodies match. The relevant line spans are unchanged by the newer source's small line shifts; for example, `Shapes.joinIsNotEmpty` is `Shapes.java:166-209` / `165-208`, and `createIndexMerger` is `Shapes.java:273-290` / `272-289`. All additional files' recomputed source hashes match their respective manifest entries. This closes the overlap-filter dependency for the generic shape math while leaving per-block shape-provider construction/composition outside this verdict.
