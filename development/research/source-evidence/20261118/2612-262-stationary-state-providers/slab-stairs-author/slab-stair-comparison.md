# 26.1.2 → 26.2 stationary slab and stair collision-shape comparison

## Provenance

Canonical source roots consumed read-only:
- `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\26.1.2\unobfuscated`
- `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\26.2\unobfuscated`

Both `unobfuscated.ready.json` files declare `status=ready`, the exact version ID, mapping `unobfuscated`, and 6,882 / 7,055 source files respectively. Their declared `sourceManifestSha256` values were verified against the raw source-manifest bytes:
- 26.1.2 `unobfuscated.sources.sha256`: `54ae320660ca911a6d20514c965c33eb414fbedd1ad6727bbaa95fe906aa23c0`
- 26.2 `unobfuscated.sources.sha256`: `a7ad74fc712567eb87136afc1eafcfd602b4088e9869f3596331fd34faefe894`

Each relevant file SHA below matches its line in that version's verified manifest. Provenance manifests identify Java 25.0.3+9, Gradle 9.7.1, Vineflower 1.12.0, ASM 9.10.1, Mapping IO 0.9.1, Gson 2.14.0, and Tiny Remapper 0.14.1. The 26.1.2 provenance records an exact per-version run; 26.2 records an exact per-version run. Readiness markers and provenance/source manifests are at each source root above.

## Compared source roster and raw source hashes

| Relative source | 26.1.2 SHA-256 | 26.2 SHA-256 | Result |
|---|---|---|---|
| `net/minecraft/world/level/block/SlabBlock.java` | `883ac7561b118096d215def3e92bcc56336f4527d2a2587f2f4f9e948e162c8e` | `fd5f80e73fd577605ff2924adccfcd7ba5037bb400e481cd142363a385c45ba1` | File differs only in out-of-slice `isPathfindable` switch syntax; `getShape` is unchanged. |
| `net/minecraft/world/level/block/StairBlock.java` | `2a8ad6eedc5a151af7a739ccd476f18cab4fe78e9df7c1ec601cb9687a77630b` | same | Identical file. |
| `net/minecraft/world/level/block/Block.java` | `1693cfb7b84190a2fe66447056a59e78ed5bd7d722a2bd16888b036d4d8e977` | `cec6a05e644e4a7feb8253cc4ca772a98f0e116fb098a1b7ee7302984ac7ecab` | File differs elsewhere; voxel box/column helpers are unchanged. |
| `net/minecraft/world/level/block/state/BlockBehaviour.java` | `9db85de84e502903e6fe497f043b58620b92089236ffb213f0b93db979428d13` | `9c7a103492d0714c90397da88eb696912ff6a9ca1c005d984c4746d52637fd1e` | File differs elsewhere; `getCollisionShape`, state accessors and collision cache construction are unchanged. |
| `net/minecraft/world/phys/shapes/Shapes.java` | `cfa63cbed77c922a68e7e8a6a3b78b09a22c7de51e0146e481e9c9d26db66f58` | `6ed9460d58727770bdec59c4f3184f075644f3c411be1eeab2eae17e8ece1891` | File differs in test-helper visibility only; `box`, `or`, `rotate` and `rotateHorizontal` bodies are unchanged. |
| `net/minecraft/world/level/BlockCollisions.java` | `eb8a8f6f07b1d6384f17987818f056d41adfd6b2ad120d26774bfa550f4fc51e` | same | Identical common consumer through shape lookup and offset. |
| `net/minecraft/world/level/CollisionGetter.java` | `dddff4897e8d5d01e3ac132d85c474e6a3b9bae928e48b8c907aa9eabdb7fb01` | `8e7d1a54d27e0f187667439539c59b8892f966f1b9c8107eb162c6d43f830f6c` | `getBlockCollisionsFromContext` visibility changes private→default in 26.2; its body and shape-dispatch route are unchanged. Generic consumer ownership remains separate. |

## SlabBlock: bounded no difference

Source reference: both versions' `net/minecraft/world/level/block/SlabBlock.java`, fields and `getShape` around lines 35–65; raw hashes above. In both versions, `getShape` switches solely on `TYPE`: `BOTTOM` returns `Block.column(16.0, 0.0, 8.0)`, `TOP` returns `Block.column(16.0, 8.0, 16.0)`, and `DOUBLE` returns `Shapes.block()`.

Normalized occupied sets are exactly `[0,1]×[0,1/2]×[0,1]`, `[0,1]×[1/2,1]×[0,1]`, and `[0,1]^3`, respectively. This follows identically from `Block.column` (`double` inputs; `8.0 - size/2.0` and `8.0 + size/2.0`) and `Block.box`, which divides each `double` coordinate by `16.0` before `Shapes.box`; these helper bodies are unchanged (`Block.java` around lines 164–189 in both, with `Shapes.box` at `Shapes.java` around line 52). The `SlabType` cast and switch selection are unchanged. Selector coverage is all three `SlabType` values. `WATERLOGGED` is registered but not read by `getShape`, so dry/wet state produces the same geometry; only dry states are in assigned scope. Level, position and collision context parameters are unused. There is no neighbor lookup in shape retrieval.

The changed `isPathfindable` source is only a switch-statement-to-expression syntax rewrite; it is outside the assigned collision operation and introduces no geometry delta.

## StairBlock: bounded no difference

Source reference: both versions' `net/minecraft/world/level/block/StairBlock.java`, shape definitions and `getShape` around lines 33–89; raw file hashes are identical. `SHAPE_OUTER` is the union of a full-footprint lower half (`Block.column(16,0,8)`) and one upper quarter (`Block.box(0,8,0,8,16,8)`). `SHAPE_STRAIGHT` unions that with a 90° horizontal rotation; `SHAPE_INNER` unions the straight set with its 90° rotation. The six cached maps rotate each base set for four horizontal facings, with `INVERT_Y` for top-half maps.

For every legal selector tuple, `FACING ∈ {N,E,S,W}`, `HALF ∈ {BOTTOM,TOP}`, and `SHAPE ∈ {STRAIGHT,OUTER_LEFT,OUTER_RIGHT,INNER_LEFT,INNER_RIGHT}`, selection is identical: the shape code picks the straight/outer/inner map, half picks bottom/top, and a second shape-code switch chooses facing, counter-clockwise facing for `INNER_LEFT`, or clockwise facing for `OUTER_RIGHT`. Thus the registered selector domain (4 facings × 2 halves × 5 shape values = 40 state tuples) maps to the same cached occupied voxel set in both versions. The raw `Map` local, `(StairsShape)` selector casts and `(VoxelShape)` return cast are unchanged. In canonical north/bottom orientation the unions are: outer = lower full slab ∪ one upper quarter; straight = lower full slab ∪ two upper quarters; inner = lower full slab ∪ all four upper quarters. Rotation and Y inversion preserve those normalized set operations. `WATERLOGGED` is registered but not read by `getShape`; context, level and position are also unused. Neighbor state matters only when placement/update computes the stored `SHAPE`; collision shape lookup reads that state value and does not re-query neighbors. Dynamic neighbor-driven shape updates are not shape-provider dependencies within this assignment.

## State cache and common collision boundary

`BlockBehaviour.java` has changed content in both releases, but the relevant methods are identical: `BlockBehaviour.getCollisionShape` returns `state.getShape(level,pos)` when `hasCollision`, else empty; state `getShape` dispatches to the block's provider; cached state `getCollisionShape(level,pos)` returns `cache.collisionShape` when present; cache construction calls the block collision provider with `EmptyBlockGetter`, zero position and empty context. Neither slab nor stair provider uses world, position or context, so this cached empty-context calculation is equivalent to ordinary provider lookup for the assigned dry solid states. No exceptional context/world-sensitive cache dependency was found for these two families.

Trace stops at the common block-collision consumer: `CollisionGetter.getBlockCollisions` creates `BlockCollisions`; `BlockCollisions` obtains `context.getCollisionShape(blockState, collisionGetter, pos)` and offsets the returned shape by block position. `BlockCollisions.java` is byte-identical. `CollisionGetter.getBlockCollisionsFromContext` changes visibility in 26.2 but keeps the same returned iterator and provider. Per assignment, generic tick routing and later collision math are left to their separate owner.

## Verdict and terminal coverage

- Slabs: bounded no difference, covering all three type selectors with dry waterlogging default; no positive delta witness.
- Stairs: bounded no difference, covering four facings × two halves × five shapes, dry; no positive delta witness.
- Relevant property dependency: only slab `TYPE`; stair `FACING`, `HALF`, `SHAPE`; waterlogging is irrelevant to these provider geometries. Stair neighbor-derived shape is already state, not a geometry-time world dependency.
- Shape cache/context dependency: no exception for these providers; relevant cache and dispatch methods have unchanged behavior. The generic caller trace ends at `BlockCollisions`.
- Residuals: no positive family delta to draft. This closes only the assigned stationary slab/stair provider slices for 26.1.2→26.2; it is not full pair coverage or an independent audit.