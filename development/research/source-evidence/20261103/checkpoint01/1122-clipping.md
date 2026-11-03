# 1.12.2 → 1.13.2 local-player move clipping comparison

## Inputs and provenance

Canonical source root: `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft` (read-only).

| Release | Namespace / mapping identity | Source manifest SHA-256 |
|---|---|---|
| 1.12.2 | Ornithe Feather; provenance names `feather-gen2-1.12.2+build.2-mergedv2.jar` and `feather-gen2-1.12.2+build.2.tiny` | `b8a37ccfccd2aac5f40f5e34fec85873dbdfa93a266043c103e592e4a8c949da` |
| 1.13.2 | Ornithe Feather; provenance names `feather-gen2-1.13.2+build.2-mergedv2.jar` and `feather-gen2-1.13.2+build.2.tiny` | `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211` |

Both `ornithe-feather.provenance.json` records identify exact `decompileMinecraft` runs and Feather mapping builds. Recomputed hashes for every source file below match that release's `ornithe-feather.sources.sha256` entry.

| Source | 1.12.2 SHA-256 | 1.13.2 SHA-256 |
|---|---|---|
| `entity/Entity.java` | `80f091bf32166c88cf8bbd31caf72d84fa16224410733c7d2a0f00563f294a0a` | `1d6ec8b80f74635401745c2c027bf36555c85348ca5693764f2668363b17d269` |
| `entity/living/player/PlayerEntity.java` | `e4e0fdbbe07a0d0ae4a70cbb6739a287c9d045a4a12b409895c220b5d91fe1e` | `4ed22f6c5a3c55d67eed782070ac722201df4d624adbc90779f1fd29c2876633` |
| `world/World.java` | `e9fa9b8d6d31ad57a5b876f5f63a5e3c554a23437decf1845a493daf48233594` | `0023de30da608db73c6c6188334d2bb19039d86fe4d6f5e4ad79d732a5d2cd1a` |
| `world/WorldView.java` | `628ce75d2c76ed025b62e905da8030024e1b89b145445fd6971bfef798bba7cc` | `9577360b1a1bdde9280f0b72593e5e6dde45d9a54719cd7ff933f3ecd31b971f` |
| `util/math/Box.java` | `f788b8146b14f299ccb58ea0854609f845c298a963295d503de2e30e15d66f3a` | `0f573d00cc10386f94cf51671c62815979973053c9e67a806e9fee98e55a4d8d` |
| `block/shape/VoxelShape.java` | — | `d95b0607647da11595e24e1e26e9615cb7da7705ea7885908544362df62275c1` |
| `block/shape/VoxelShapes.java` | — | `313968d4e5855b5ec380272b6ababcc5849d478ab771926355912a8cb6aa9c95` |

## Compared terminal slices

### Ordinary `Entity.move` collision resolution

1.12.2 `entity/Entity.java:462-699` and 1.13.2 `entity/Entity.java:473-679` retain the same ordinary movement sequence and gates: no-clip handling; piston delta limiting; cobweb scaling; sneaking edge backoff; initial vertical then X then Z clipping; step eligibility (`stepHeight > 0`, grounded or descending collision, horizontal clipping); two step candidates; squared-horizontal-distance selection; fallback to the non-step result; collision flags and velocity zeroing. No difference found in those gates or in their axis/candidate order.

The scalar clipping terminal changed. In 1.12.2, `Entity.move` obtains `List<Box>` from `World.getCollisions` and folds `Box.intersectY/X/Z` over it (`Entity.java:567-600,613-685`). `Box.intersectX/Y/Z` uses strict overlap on the other two axes and clips to the exact face gap without an epsilon or small-distance cutoff (`util/math/Box.java:185-243`). In 1.13.2, `Entity.move` asks for a swept `Stream<VoxelShape>` only when at least one component is nonzero and calls `VoxelShapes.calculateMaxOffset` for each axis (`Entity.java:578-599,611-664`). `VoxelShapes.calculateMaxOffset` returns zero when the current absolute requested offset is below `1.0E-7` (`block/shape/VoxelShapes.java:194-205`); `VoxelShape.calculateMaxDistance` also applies `1.0E-7` offsets when selecting occupied grid cells and accepts face gaps down to `-1.0E-7` (`block/shape/VoxelShape.java:188-235`). A sub-`1e-7` positive face gap is reachable after a normal-sized movement request: the supplementary local-tick/network trace gives an equal `+0.8F` received explosion impulse, which survives the `<0.003` pre-travel zeroing, and a stone-block-plus-Shulker collision witness. The stone first clips to the tiny gap; the subsequent entity shape causes 1.13.2's stream fold to zero the offset while old `Box` clipping keeps the positive gap. See `tiny-offset-reachability.md` and the finding draft.

### Sneak-edge and collision-query graph

The pre-move edge guard is textually equivalent at 1.12.2 `Entity.java:522-565` and 1.13.2 `Entity.java:533-576`: same mover types, on-ground/sneaking/player gates, axis probe order, `0.05` decrement and asymmetric inclusive bounds `x < 0.05 && x >= -0.05` (same for Z), and diagonal condition. The query adapter differs: old probes call `World.getCollisions(entity, movedBox).isEmpty()`; new probes call `WorldView.hasNoCollisions(entity, movedBox)`. Both include blocks and eligible entity collisions for non-null entity, excluding same-vehicle entities. Old block collection is `World.java:964-1014`; entity collisions append at `World.java:1017-1039`. New block collection is `WorldView.java:112-152`; entity collisions append through `World.java:1755-1758` and `EntityView.java:21-35`. I found no independent semantic difference in the sneak-edge loop itself. The newer stream uses voxel collision shapes, so it shares the unresolved geometry/query differences described above.

### Broad-phase collision query

Old `World.getCollisions(entity, box)` enumerates the expanded integer block bounds in nested X/Z/Y loops, checks chunk loading, collects block collision AABBs, then checks nearby entities from `shape.expand(0.25)` (`World.java:964-1039`). New `WorldView.getCollisions(entity, box, dx,dy,dz)` builds a voxel swept region with a `1.0E-7` expansion and a shifted exclusion shape (`WorldView.java:154-164`), then routes to block and entity streams. New `getBlockCollisions` enumerates an expanded voxel bound, excludes intersections with the starting box, skips unloaded chunks and returns per-block VoxelShapes or an aggregated integer-grid shape (`WorldView.java:112-152`). This is a changed collision-query implementation, not evidence by itself that ordinary player trajectories differ; correspondence for shape unions, boundary-touch cases, overlapping starting shapes and iteration-order ties remains open.

### Player pose collision gate

1.12.2 `PlayerEntity.updatePlayerPose` selects fall-flying, sleeping, sneaking, or standing dimensions; it grows the current box at its minimum corner and applies dimensions only when `!world.getCollisions(box)` (`PlayerEntity.java:291-315`). 1.13.2 adds swimming/spin-attack as a newer-only `0.6 x 0.6` pose branch, then uses the same box construction and applies dimensions when `world.hasNoCollisions(null, box)` (`PlayerEntity.java:334-361`). The old overload calls `World.getCollisions(null, box, enforceWorldBorder=true)` (`World.java:1062-1063`); the new overload calls `WorldView.getCollisions(null, VoxelShapes.block(box), VoxelShapes.empty(), emptySet)` through `hasNoCollisions` (`WorldView.java:166-167,201-206`), therefore neither checks entities. No separate historical player-pose gate delta is established for shared poses. The new swimming/spin pose is out of the old-feature scope. Exact equivalence still depends on voxel-shape collision geometry and hard-coordinate boundary behavior.

## Source conclusion and unresolved gates

Established: the ordinary move/step/sneak branch structure and scalar order are paired; collision query and clipping terminals were replaced, including an explicit sub-`1e-7` offset cutoff in the new VoxelShape math.

Not established: full equivalence of swept voxel query results to old block/entity AABB lists for touching and overlapping boxes; whether any block's old collision boxes versus 1.13.2 shapes interact with this path beyond separately assigned provider review; collision ordering consequences in equal-distance step candidates; all other direct player move callers; and complete pair coverage/freeze.

The player-reachable precondition and concrete one-tick consequence are now closed for the ordered block-then-entity case. The source-only finding draft is `.native-source/20261103/1122-clip-new/findings/move-collision-offset-threshold.md`; broader query equivalence and pair freeze remain open.
