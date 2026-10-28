# Source contract: movement collision shape collection and axis clipping

## Scope and conclusion

This report compares the accepted 1.13 slab audit with the canonical local decompilations for 1.13.0, 1.13.1, 1.13.2, and 26.2. It covers the normal movement collision path and the source-defined shape collection/order/clipping behavior. It does not inspect product code or infer a product hook design.

**A neutral axis operation that receives the complete `Iterable<VoxelShape>` can reproduce the 1.13.0 clip for the accepted two-bottom-slab witness if it joins the two translated slab shapes into one shape and applies the 1.13.0 single-shape axis routine. The accepted audit records the exact result: `4.9999999918171056e-8`. That witness does not establish general equivalence.**

For common-era collision geometry with a known and identical eligible shape set, geometric union can recover the essential joined geometry. The iterable by itself does not define that identical eligible set or preserve the 1.13.0 collector's construction history. Therefore it is insufficient to reproduce the *full 1.13.0 collection-plus-clip operation* generally from source-defined iterable data alone. In particular, it does not encode old block positions/states, collector query/exclusion and loading decisions, identity classification as the canonical full block, the old per-column/per-slice accumulator boundaries, or the 1.13.0 entity filter inputs. A shape-only operation also cannot establish that modern shapes were produced from the same era-existing state and query semantics.

The important behavioral distinction is between one joined `VoxelShape` passed once to the axis clipper (1.13.0) and an ordered sequence of shapes passed through sequential clipping with an early sub-`1.0E-7` return (1.13.1/.2 and 26.2). The audit's named witness is an exact result for its stated bottom/bottom setup, not proof that all partial shapes or arrangements diverge.

## Contracts by release

### 1.13.0: world-wide join, full-cell accumulator, joined entities

`WorldView.getBlockCollisions(VoxelShape bounds, VoxelShape exclude, boolean onlyFirst, boolean checkBorder)` computes a padded integer region from the supplied bounds (`WorldView.java:113-120`). Its traversal is nested X outer, Z middle, Y inner (`:124-135`). It skips prescribed perimeter combinations and unloaded chunks (`:129-142`). For each collision shape, exact object identity with `VoxelShapes.block()` selects the full-cell path; if not returning early for `onlyFirst`, full cells are recorded in a `BitSetVoxelSet` (`:144-154`). Non-full shapes are translated to world coordinates, rejected when intersecting the exclusion shape or not intersecting the query bounds, and OR-joined into a per-X/Z partial shape (`:155-165`), then into the per-X and whole-region partial shapes (`:168-175`). The collector optimizes that joined partial shape and OR-joins it with one `IntVoxelShape` made from the accumulated full cells (`:178-184`). Thus the block result is one shape, not one result per block.

`WorldView.getCollisions` creates the padded sweep and starting-box exclusion (`WorldView.java:187-200`), updates the border state when needed, obtains the joined block shape, and, when eligible, OR-joins the entity collision shape into that block result (`:203-218`). The entity collector queries entities in the collision bounds expanded by `0.25`, applies excluded-entity and same-vehicle filters, considers both each other entity's collision shape and the actor's collision-against shape, and OR-joins their box shapes (`:220-246`). This loses entity candidate identity/order after the union has been formed.

`Entity.move` obtains that single collision shape once and applies it to Y, then X, then Z, moving its box after each resolved axis (`Entity.java:578-598`). `VoxelShapes.m_84966497` dispatches to the axis-cycle routine (`VoxelShapes.java:207-209`); that routine returns unchanged for an empty shape, has a strict `abs(d) < 1.0E-7` entry gate, then scans voxel cells along the requested axis and returns the first applicable boundary distance (`:211-245`, with the negative-distance branch continuing through `:246-266`). There is no outer iteration between separate shape elements.

### 1.13.1 and 1.13.2: ordered partial stream and full-cell tail

The 1.13.1 and 1.13.2 `WorldView.getBlockCollisions` implementations are hash-identical. They calculate the same padded region and create a `BitSetVoxelSet` for full cells (`WorldView.java:112-122`). The serial stream walks `BlockPos.Mutable.iterateRegionMutable` and, for each visited position, either returns empty, records a canonical full-block collision in the accumulator and returns empty, or returns that block's translated partial shape (`:123-150`). It then concatenates exactly one generated `IntVoxelShape` tail, filtered against the query (`:151`). `BlockPos.iterateRegionMutable` increments X first, then resets X and increments Y, then resets X/Y and increments Z (`BlockPos.java:212-238`): X is the fastest-changing coordinate, then Y, then Z. This order differs from 1.13.0's collector loop nesting.

The concrete 1.13.1 `World.getCollisions` appends entity collision shapes only after the superclass block stream for a non-null actor (`World.java:1755-1758`). The 1.13.2 override has the same relevant source body. The inherited block stream therefore precedes entities in this path; each of these is still an element rather than a combined world-wide union. Entity candidate and per-entity shape ordering is supplied by the entity collision query and its returned list.

`Entity.move` wraps and reuses the stream for Y, X, and Z clipping (`Entity.java:578-598`). `VoxelShapes.calculateMaxOffset` obtains an iterator and, before consuming each next shape, returns zero when `abs(maxDist) < 1.0E-7`; otherwise it applies that shape's `calculateMaxDistance` and continues (`VoxelShapes.java:194-205`). The threshold is strict. For the audit's witness, the first slab leaves a positive gap below the threshold and the next stream element causes the outer routine to return zero. This is the exact accepted witness outcome, not a universal statement about slab collision.

### 26.2: current iterable and movement ordering

The default `CollisionGetter.getCollisions(source, box)` gets entity collisions first and block collisions second, concatenating in that order when entities exist (`CollisionGetter.java:84-88`). Its block-collision iterable is lazily backed by `BlockCollisions` (`:96-106`).

The ordinary `Entity.collide` movement route first gets the entity candidate list for the swept box (`Entity.java:1141-1144`). `collideBoundingBox` then builds the list consumed by `collideWithShapes`: entity candidates in their supplied list order, a world-border shape when applicable, then all block collision shapes (`Entity.java:1195-1200, 1214-1230`). The axis loop uses `Direction.axisStepOrder(movement)` and applies `Shapes.collide` to this same list for each nonzero axis (`:1244-1259`). Thus the complete normal-movement list order is **entities, optional border, blocks**. The generic `CollisionGetter.getCollisions` order is **entities, blocks**; it is not the same API route as the entity movement builder.

`BlockCollisions.computeNext` traverses its `Cursor3D` and returns a world-translated shape for each accepted block (`BlockCollisions.java:79-109`). Its cursor advances X fastest, then Y, then Z (`Cursor3D.java:31-45`); face/edge/corner classification and skip rules are applied before block state lookup (`:47-73`, and `BlockCollisions.java:80-105`). Canonical full blocks are also returned as individual translated shapes when the query box intersects them (`BlockCollisions.java:93-103`); there is no accumulated full-block tail in this path.

`Shapes.collide` walks the iterable in its given order and uses the same strict `abs(distance) < 1.0E-7` pre-consumption early return (`Shapes.java:234-244`). Each element invokes `VoxelShape.collide`; that routine has its own strict small-distance gate and searches for the first relevant voxel boundary in its axis direction (`VoxelShape.java:252-310`).

## What an axis input can and cannot recover

### Exact witness capability

The accepted audit's inputs include two bottom `stone_slab` collision regions, at X=2 and X=3, and no other collision geometry. Both are partial shapes, and the accepted audit records their common translated half-height geometry and the exact 1.13.0 result. If an axis input receives those complete two shapes and combines them as one OR-union before applying the 1.13.0 single-shape clipping routine, the source geometry is sufficient to express the witness's joined obstacle and to avoid the later stream-element epsilon gate. This is a bounded geometric reconstruction of the named witness.

### Missing source information for general reproduction

The iterable contains collision geometry and sequence order, but the source contracts above show that the full 1.13.0 operation also depends on information or decisions made upstream:

1. **State and block position.** The 1.13.0 collector asks `getBlockState(pos).getCollisionShape(world, pos)` and classifies a full block by reference identity with `VoxelShapes.block()` (`WorldView.java:134-154`). A translated shape sequence does not carry the original state or the exact source identity/classification. In particular, source `isFullBlock` properties are not the collector predicate.
2. **Old collector envelope and exclusion.** 1.13.0 derives padded scan bounds, checks the starting-box exclusion, current sweep overlap, border rules, perimeter rules, and chunk-loaded status while collecting (`WorldView.java:113-142, 155-165, 187-216`). The 26.2 entity movement builder instead queries the swept AABB through current collision-context and block-cursor rules (`CollisionGetter.java:96-106`; `BlockCollisions.java:31-62, 79-105`). A complete result list cannot reveal omitted candidates or why a shape was omitted.
3. **Accumulator provenance and order.** The 1.13.0 collector groups partial block shapes within its nested X/Z/Y loops, optimizes the joined partial union, separately fills the full-cell voxel set, then joins the two (`WorldView.java:124-184`). The iterable does not identify which elements were old partial blocks, which belong in the old full-cell accumulator, or the per-loop group boundaries. A plain geometric union can represent the same occupied set when the eligible inputs are known, but it does not prove identical old voxel-grid coordinates, optimization, or order of floating-point shape construction for arbitrary inputs.
4. **Entity filtering and source geometry.** 1.13.0 excludes supplied entities and same-vehicle entities and may union two distinct collision relations per candidate (`WorldView.java:220-246`). A current iterable does not carry those entity identities, vehicle relations, exclusion set, or whether a shape came from the actor-versus-other relation. Current movement does preserve entity shapes as a preceding list (`Entity.java:1214-1229`), but that is not enough to reconstruct absent historical candidates or filter decisions.
5. **Query equivalence and shape availability.** Even common-era block types can produce state- and world-dependent shapes. The available iterable is only complete for its own source version, box, collision context, loaded region, and border conditions. It does not establish that the exact same 1.13-era shapes were queried. The old source's shape provider and gates must be equivalent before shape-only union is a valid general reduction.

Accordingly, **complete iterable input is sufficient for the named witness after the explicit join operation, and is sufficient for a general 1.13.0 clip only under an external invariant that the iterable contains exactly the same eligible collision geometry and supports the same join semantics. The iterable alone does not establish that invariant or the complete historical collector operation.**

## Exact witness provenance

The accepted audit provides the witness values and its declared limits. Its SHA-256 is `1e4df50816d7efd03057a2f77f89deae22f2646cb7c40cdbfb01f2c101b2497d`. This report does not independently rerun the numeric witness; it records it as an accepted source-audit result. The audit's stated values are: bottom slabs at `(2,64,0)` and `(3,64,0)`, actor `minY=64.1`, `maxY=65.9`, overlap with Z `[0,1]`, `maxX=1.99999995`, request `(2,0,0)`, no other colliders/entities, loaded cells, distant world border; 1.13.0 result `4.9999999918171056e-8` and 1.13.1/.2 result `0.0`.

The audit itself pins `SlabBlock` shape geometry and the exact bottom state in its own cited source ranges. This report relies on that accepted witness for the named slab-state facts and limits, and does not broaden them to all slab combinations.

## Source citations

Line numbers below refer to the local canonical files whose SHA-256 values appear in the manifest. They are exact method ranges; identity and behavior statements above are tied to these ranges.

| Source | Cited range | Contract used |
|---|---:|---|
| 1.13.0 `net/minecraft/world/WorldView.java` | 113-185 | Padded world scan, full-cell identity route, partial shape grouping/join, final optimized partial plus full-cell shape |
| 1.13.0 `net/minecraft/world/WorldView.java` | 187-218 | Sweep/exclusion construction, border handling, block-shape plus entity-shape union |
| 1.13.0 `net/minecraft/world/WorldView.java` | 220-246 | Entity filters and joined entity collision geometry |
| 1.13.0 `net/minecraft/entity/Entity.java` | 578-598 | One collected shape reused for Y, X, Z clipping, with box moves between axes |
| 1.13.0 `net/minecraft/block/shape/VoxelShapes.java` | 207-245 | Single-shape axis dispatch and strict entry epsilon; positive scan |
| 1.13.0 `net/minecraft/block/shape/VoxelShapes.java` | 246-266 | Negative scan branch |
| 1.13.0 `net/minecraft/util/math/BlockPos.java` | 212-238 | Mutable scan order: X, then Y, then Z |
| 1.13.1/.2 `net/minecraft/world/WorldView.java` | 112-152 | Stream of moved partial shapes followed by one full-cell accumulator tail |
| 1.13.1/.2 `net/minecraft/util/math/BlockPos.java` | 212-238 | Mutable scan order: X, then Y, then Z |
| 1.13.1/.2 `net/minecraft/world/World.java` | 1755-1758 | Concrete block stream followed by entity stream |
| 1.13.1/.2 `net/minecraft/entity/Entity.java` | 578-598 | Cached collision stream reused for Y, X, Z |
| 1.13.1/.2 `net/minecraft/block/shape/VoxelShapes.java` | 194-205 | Ordered stream iteration and strict pre-next epsilon gate |
| 26.2 `net/minecraft/world/level/CollisionGetter.java` | 84-106 | Generic entity-before-block iterable and lazy block iterable |
| 26.2 `net/minecraft/world/level/BlockCollisions.java` | 31-62, 79-109 | Collision cursor setup, context/state shape lookup and per-block output/filtering |
| 26.2 `net/minecraft/core/Cursor3D.java` | 31-73 | Cursor order and position classification |
| 26.2 `net/minecraft/world/entity/Entity.java` | 1141-1144, 1195-1230 | Movement query and construction order: entity candidates, optional border, blocks |
| 26.2 `net/minecraft/world/entity/Entity.java` | 1244-1259 | Ordered shape list reused for movement axes |
| 26.2 `net/minecraft/world/phys/shapes/Shapes.java` | 234-244 | Ordered iterable clipping and strict pre-element epsilon gate |
| 26.2 `net/minecraft/world/phys/shapes/VoxelShape.java` | 252-310 | Per-shape gate and axis clip scan |

## Provenance and hash manifest

Canonical source roots are under `D:/Javastuff/LegacyParkourCompat/decompiled_minecraft/`. The verified ready markers report `status: ready`, mapping `ornithe-feather` for 1.13/1.13.1/1.13.2 and `unobfuscated` for 26.2, all decompiled with Vineflower. Each marker's `sourceManifestSha256` matched the SHA-256 of its named `*.sources.sha256` file. The exact ready-marker and source-manifest hashes are recorded below. The accepted audit states that its own accepted artifacts matched their supplied identities; its file hash is also recorded below.

| Release | Canonical root / mapping | Ready marker SHA-256 | Source manifest SHA-256 |
|---|---|---|---|
| 1.13.0 (`versionId: 1.13`) | `1.13/ornithe-feather` | `82a4a8f6a03fbd99a7b8577b1ae5980d95af07c7ded0da284106d16eb1e01a99` | `0509c2614b0bb0e616e2ebc6382f8eb5d031b5d86b834a4a6ed7fd96b6d68f1a` |
| 1.13.1 | `1.13.1/ornithe-feather` | `f098b3a08b37276cc1a465a8a122df8de76d68786c854b92bac1ca51a79670de` | `21770b01ff3bf443db2e9e1b6df7db25d2fd6b7a72a86228259269c932d3abba` |
| 1.13.2 | `1.13.2/ornithe-feather` | `1d1c644c1deb05c02d11dc575ad834f4f72489124701d9b2b90357f43dc37b38` | `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211` |
| 26.2 | `26.2/unobfuscated` | `f9406adb6bf7cb4c1ab0792a798ab2cb90e082ad4c2eee032c9f674d0ea8070f` | `a7ad74fc712567eb87136afc1eafcfd602b4088e9869f3596331fd34faefe894` |

### Source-file SHA-256 values

| Release | Relative source path under canonical mapped root | SHA-256 |
|---|---|---|
| 1.13 | `net/minecraft/world/WorldView.java` | `b0d1406fb04eacbc644a78548f858d5ff6c14ad781927832cb374e985f32a543` |
| 1.13 | `net/minecraft/entity/Entity.java` | `09514488d9055449c47e2e4bcd23b5773203ca634b7ecba2323a99d0ae016642` |
| 1.13 | `net/minecraft/block/shape/VoxelShapes.java` | `6ae1a6fa0ab76098252208fad00a6d4f3d3f1cf2023d76f63d450855df00a330` |
| 1.13 | `net/minecraft/util/math/BlockPos.java` | `ee71cfdedcaa27458f48168ab761b435cf9c878b04f21e971ddbafb6254aa3fd` |
| 1.13.1 | `net/minecraft/world/WorldView.java` | `9577360b1a1bdde9280f0b72593e5e6dde45d9a54719cd7ff933f3ecd31b971f` |
| 1.13.1 | `net/minecraft/world/World.java` | `119ac6f61b93cf352b2026934e9f95877f55dc2f582b45d75c2bdae3652532b6` |
| 1.13.1 | `net/minecraft/entity/Entity.java` | `1d6ec8b80f74635401745c2c027bf36555c85348ca5693764f2668363b17d269` |
| 1.13.1 | `net/minecraft/block/shape/VoxelShapes.java` | `313968d4e5855b5ec380272b6ababcc5849d478ab771926355912a8cb6aa9c95` |
| 1.13.1 | `net/minecraft/util/math/BlockPos.java` | `ee71cfdedcaa27458f48168ab761b435cf9c878b04f21e971ddbafb6254aa3fd` |
| 1.13.2 | `net/minecraft/world/WorldView.java` | `9577360b1a1bdde9280f0b72593e5e6dde45d9a54719cd7ff933f3ecd31b971f` |
| 1.13.2 | `net/minecraft/world/World.java` | `0023de30da608db73c6c6188334d2bb19039d86fe4d6f5e4ad79d732a5d2cd1a` |
| 1.13.2 | `net/minecraft/entity/Entity.java` | `1d6ec8b80f74635401745c2c027bf36555c85348ca5693764f2668363b17d269` |
| 1.13.2 | `net/minecraft/block/shape/VoxelShapes.java` | `313968d4e5855b5ec380272b6ababcc5849d478ab771926355912a8cb6aa9c95` |
| 1.13.2 | `net/minecraft/util/math/BlockPos.java` | `ee71cfdedcaa27458f48168ab761b435cf9c878b04f21e971ddbafb6254aa3fd` |
| 26.2 | `net/minecraft/world/level/CollisionGetter.java` | `8e7d1a54d27e0f187667439539c59b8892f966f1b9c8107eb162c6d43f830f6c` |
| 26.2 | `net/minecraft/world/level/BlockCollisions.java` | `eb8a8f6f07b1d6384f17987818f056d41adfd6b2ad120d26774bfa550f4fc51e` |
| 26.2 | `net/minecraft/core/Cursor3D.java` | `9a1fdc92bb95013c7e70da467a0b5e5b1c9787daafcb0e7e3a8c950dd0e7287f` |
| 26.2 | `net/minecraft/world/entity/Entity.java` | `7afb9c1294893ffe73e3b1acffcad41c648f15de8378bff3dffaff869bb811d5` |
| 26.2 | `net/minecraft/world/phys/shapes/Shapes.java` | `6ed9460d58727770bdec59c4f3184f075644f3c411be1eeab2eae17e8ece1891` |
| 26.2 | `net/minecraft/world/phys/shapes/VoxelShape.java` | `419a46bbcc8b48e1c075127954eb243dcc60d10d4087bf256d895cf91a2ff854` |

Accepted audit SHA-256: `1e4df50816d7efd03057a2f77f89deae22f2646cb7c40cdbfb01f2c101b2497d`.

## Unresolved gates

- The exact accepted numerical witness is bounded to its stated bottom/bottom slabs, actor box, displacement, loaded cells, absent other collision geometry/entities, and distant world border. It does not establish separate numeric outcomes for top/top, bottom/top, double slabs, or arbitrary slab arrangements.
- Source behavior for 1.13.1 and 1.13.2 is hash-identical for `WorldView`, `Entity`, `VoxelShapes`, and `BlockPos`; the concrete `World` source differs as a whole, so only the cited relevant block-first/entity-tail collector method is claimed equivalent.
- 26.2's source supports the current movement iterable and order described above. The report does not claim compatibility of 26.2 block providers, collision contexts, world-border handling, or newly introduced blocks with the 1.13.0 collector.
- A proof that a modern complete iterable contains the identical historical eligible geometry requires the exact historical states/world view and equal old collector gates. Those inputs are absent from a `VoxelShape` iterable and remain an unresolved dependency for general parity.
- No implementation, build, test, runtime, or game execution was performed.
