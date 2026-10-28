# Collector closure for the repeated-axis collision primitive

Scope: exact Minecraft 1.13, 1.13.1, and 1.13.2 `ornithe-feather` source. This closes only whether collision collection changes the applicability boundary of the already accepted axis primitive. It does not claim collector parity, trajectory parity, pair coverage, or runtime parity.

## Identity checks

Canonical source root: `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\<version>\ornithe-feather\`. For each release, the ready marker and `*.sources.sha256` manifest hashes below were recomputed. Relevant Java source hashes were recomputed and match their manifest entries.

| Release | Ready marker SHA-256 | Source manifest SHA-256 |
|---|---|---|
| 1.13 | `82a4a8f6a03fbd99a7b8577b1ae5980d95af07c7ded0da284106d16eb1e01a99` | `0509c2614b0bb0e616e2ebc6382f8eb5d031b5d86b834a4a6ed7fd96b6d68f1a` |
| 1.13.1 | `f098b3a08b37276cc1a465a8a122df8de76d68786c854b92bac1ca51a79670de` | `21770b01ff3bf443db2e9e1b6df7db25d2fd6b7a72a86228259269c932d3abba` |
| 1.13.2 | `1d1c644c1deb05c02d11dc575ad834f4f72489124701d9b2b90357f43dc37b38` | `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211` |

| Source file | 1.13 SHA-256 | 1.13.1 SHA-256 | 1.13.2 SHA-256 |
|---|---|---|---|
| `net/minecraft/entity/Entity.java` | `09514488d9055449c47e2e4bcd23b5773203ca634b7ecba2323a99d0ae016642` | `1d6ec8b80f74635401745c2c027bf36555c85348ca5693764f2668363b17d269` | `1d6ec8b80f74635401745c2c027bf36555c85348ca5693764f2668363b17d269` |
| `net/minecraft/block/shape/VoxelShapes.java` | `6ae1a6fa0ab76098252208fad00a6d4f3d3f1cf2023d76f63d450855df00a330` | `313968d4e5855b5ec380272b6ababcc5849d478ab771926355912a8cb6aa9c95` | `313968d4e5855b5ec380272b6ababcc5849d478ab771926355912a8cb6aa9c95` |
| `net/minecraft/block/shape/VoxelShape.java` | `3a085bf46c35869851c1db0966d596c90ec3949532a361bb90c78191a7b76f0c` | `d95b0607647da11595e24e1e26e9615cb7da7705ea7885908544362df62275c1` | `d95b0607647da11595e24e1e26e9615cb7da7705ea7885908544362df62275c1` |
| `net/minecraft/world/WorldView.java` | `b0d1406fb04eacbc644a78548f858d5ff6c14ad781927832cb374e985f32a543` | `9577360b1a1bdde9280f0b72593e5e6dde45d9a54719cd7ff933f3ecd31b971f` | `9577360b1a1bdde9280f0b72593e5e6dde45d9a54719cd7ff933f3ecd31b971f` |
| `net/minecraft/world/World.java` | `6e3d94e6cdcfbb15472c4ee981a1c12500eaf7cd5d5dc20d75bcde91174c2eb5` | `119ac6f61b93cf352b2026934e9f95877f55dc2f582b45d75c2bdae3652532b6` | `0023de30da608db73c6c6188334d2bb19039d86fe4d6f5e4ad79d732a5d2cd1a` |
| `net/minecraft/world/EntityView.java` | not present in this tree | `2b5b59eef6efa93bb51c1d838c7a1cf64725be96c55c47f76894fde11e13d6de` | `2b5b59eef6efa93bb51c1d838c7a1cf64725be96c55c47f76894fde11e13d6de` |

## Exact collector and movement path

### 1.13: joined shape in `WorldView`

`net/minecraft/world/WorldView.java:113-185` builds the block-collision result as one `VoxelShape`; it accumulates full-block occupancy into the collector's voxel set and joins colliding partial shapes. `getCollisions` at `:203-218` obtains this one block shape, then joins entity shapes with `BooleanOp.OR` if the query is not early-existence mode or if the block result is empty, provided the entity argument is non-null. The helper at `:220-247` gets nearby entities from the query bounds expanded by `0.25`, excludes the supplied entities and the caller's shared-vehicle entities, and considers both the other entity's collision box and the caller's reciprocal response box. Each candidate must pass strict `Box.intersects`; early-existence mode stops on its first accepted candidate. `hasNoCollisions` at `:270-275` uses that same collector with early-existence mode.

In `net/minecraft/entity/Entity.java:578-598`, ordinary movement calls `world.getCollisions(this, shape, x, y, z)` behind the any-nonzero-delta gate and passes the returned joined shape through the axis primitive sequentially for Y, X, and Z when each corresponding delta is nonzero. Step-height candidates use `hasNoCollisions(this, candidate)` at `:536,548,558`, so their entity parameter is also non-null. The helper receives a non-null entity on the player movement paths named here.

### 1.13.1 and 1.13.2: streamed block shapes plus concrete-`World` entity stream

`net/minecraft/world/WorldView.java:112-152` emits block collisions as a stream. Partial block shapes are emitted individually after overlap checks; occupied full cubes are accumulated in a voxel set and emitted as one generated `IntVoxelShape` at the stream tail. The epsilon-expanded query and exclusion shape are built at `:154-164`. The `WorldView.getCollisions` default at `:170-178` returns this block stream only.

For the concrete release `World`, `net/minecraft/world/World.java:1755-1758` appends `getEntityCollisions` only when the entity is non-null. `net/minecraft/world/EntityView.java:21-35` returns an empty stream for empty query shape; otherwise it searches the expanded bounds, filters excluded/shared-vehicle entities, supplies both the other entity's collision shape and the caller's reciprocal response shape, filters nulls, requires strict `Box.intersects`, then voxelizes each surviving box. The `WorldView.hasNoCollisions` default at `:201-206` calls `getCollisions`; interface dispatch on the concrete `World` therefore includes its entity stream as well. Movement and step-height call sites pass `this` in `Entity.java:536,548,558,578-598,611-612`.

The `Entity.java` file bytes are identical for 1.13.1 and 1.13.2; so are the listed `WorldView.java`, `EntityView.java`, `VoxelShape.java`, and `VoxelShapes.java` bytes. The 1.13.2 `World.java` differs in whole-file hash, but its collector override at `:1755-1758` has the same block-first/conditional-entity concatenation as 1.13.1. `net/minecraft/unmapped/C_70593968.java:11-37` backs the one-shot collision stream with a shared iterator and a cache; each `m_11038035()` stream replays cached entries before consuming additional ones. This is how the Y/X/Z folds in `Entity.move` can revisit the collected shapes without re-running a consumed Java stream.

## Primitive applicability conclusion

The collector correction changes which shapes reach movement and how they are represented, but does not make the axis primitive inapplicable to any of these three versions' ordinary player-movement queries:

- In 1.13, `VoxelShapes.m_84966497` / `m_14725303` (`net/minecraft/block/shape/VoxelShapes.java:207-235`) clamps against the one joined block-plus-entity shape returned by `WorldView.getCollisions`.
- In 1.13.1 and 1.13.2, `VoxelShapes.calculateMaxOffset` (`net/minecraft/block/shape/VoxelShapes.java:194-205`) folds a `Stream<VoxelShape>` by invoking `VoxelShape.calculateMaxDistance` for each emitted shape. `Entity.move` reuses the collision iterator wrapper as it applies Y, then X, then Z clipping, under the same per-axis nonzero checks as the 1.13 path.
- Thus the accepted per-axis clipping behavior remains reachable in 1.13, 1.13.1, and 1.13.2. Implementation must preserve the release-specific input contract: 1.13 receives the unioned shape; 1.13.1/1.13.2 receive the stream fold, including the concrete `World` entity stream. The collector evidence does not prove these aggregations interchangeable, and it does not authorize replacing them with a shared collector or claiming identical trajectories.

Boundaries: this conclusion covers only the cited movement and step-height call sites and the concrete collector behavior. Entity inclusion depends on a non-null caller; 1.13 early-existence queries include entity shapes only when the block result is empty, whereas ordinary movement queries include them. In 1.13.1/1.13.2 the base `WorldView` default has block shapes only; entity inclusion is established for the concrete `World` override. No conclusion is made for other `WorldView` implementations, unrelated collision callers, or full movement parity.
