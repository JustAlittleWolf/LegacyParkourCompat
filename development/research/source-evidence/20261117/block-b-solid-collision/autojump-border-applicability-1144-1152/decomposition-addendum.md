# Addendum: border AABB decomposition and AutoJump above-sample gate

This addendum preserves the original finding and verdict. It supersedes only their open question about what the first synthetic border AABB samples. Reviewed finding SHA-256: `8161F41F5B750BC63DDB6D1AE2D4A6012A62D1E71FE3E79D43CC5BBC4112D1F7`. Reviewed verdict SHA-256: `C1353FF8232AAAAA74A0D418390F9DDEF7D25DBF343DB296DB50EC1E9E124566`.

## Exact greedy decomposition

Both `WorldBorder` implementations form the collision shape as `Shapes.INFINITY` minus a box from `floor(minX/minZ)` to `ceil(maxX/maxZ)`, with Y spanning negative to positive infinity. For a normal finite border, the joined voxel coordinates are X and Z `[-Infinity, floor(min), ceil(max), +Infinity]` and Y `[-Infinity, +Infinity]`. Every occupied voxel in the difference is on an outer X or Z interval.

`VoxelShape.toAabbs()` calls `forAllBoxes(..., true)`. The shared `DiscreteVoxelShape.forAllBoxes` algorithm scans X, then Y, then contiguous Z strips. For the outside-rectangle occupancy, it first emits/clears the left X strip; the two middle-X Z strips then greedily extend right across the remaining right strip; the last remaining interior-Z strip is the right X strip. Result order and bounds are:

| Order | X interval | Y interval | Z interval |
|---|---|---|---|
| 1 | `[-∞, floor(minX)]` | `[-∞, +∞]` | `[-∞, +∞]` |
| 2 | `[floor(minX), +∞]` | `[-∞, +∞]` | `[-∞, floor(minZ)]` |
| 3 | `[floor(minX), +∞]` | `[-∞, +∞]` | `[ceil(maxZ), +∞]` |
| 4 | `[ceil(maxX), +∞]` | `[-∞, +∞]` | `[floor(minZ), ceil(maxZ)]` |

Thus all emitted AABBs have at least one unbounded horizontal interval; there is no finite middle/lateral AABB. The claim follows from both the exact three-cell-per-horizontal-axis grid and the greedy strip clearing/extension algorithm, not from a hand-picked four-wall approximation. The list order matters: an outward west probe intersects box 1 first; a centered south, north, or east probe intersects its corresponding later box, assuming it does not also reach the west strip.

## Center and block lookup

Both versions implement `AABB.getCenter()` with `Mth.lerp(0.5, min, max)`, and `Mth.lerp(t,a,b)` as `a + t * (b - a)`. Therefore:

- `[-∞, finite]` and `[-∞,+∞]` centers are NaN (`-∞ + +∞`); `[finite,+∞]` centers are `+∞`.
- `Mth.floor(NaN)` is `0` because `(int)NaN` is `0` and the comparison is false.
- Directly, `Mth.floor(+∞)` is `Integer.MAX_VALUE`. `Mth.floor(-∞)` first casts to `Integer.MIN_VALUE`; `-∞ < Integer.MIN_VALUE` is true, and subtracting one wraps to `Integer.MAX_VALUE`. The emitted box centers do not produce `-∞` for the intervals above, but the exact negative-infinity cast path also resolves to MAX, not MIN.
- Y center is NaN for every border AABB, so `new BlockPos(center)` samples Y `0`; AutoJump's first loop iteration is `v=1` at normal `q=1.2F`, and asks for the block at Y `1`.

Applying those operations to the ordered AABBs gives the above-sample positions:

| AABB | Center X/Z | `BlockPos` X/Z | Ordinary client lookup result |
|---|---|---|---|
| 1, left | NaN / NaN | `0 / 0` | In-world origin chunk; actual loaded state can be read |
| 2, south | `+∞` / NaN | `MAX / 0` | X is out of world bounds |
| 3, north | `+∞` / `+∞` | `MAX / MAX` | X and Z are out of world bounds |
| 4, east | `+∞` / finite | `MAX / floor(finite)` | X is out of world bounds |

`Level.isInWorldBounds` uses the `[-30,000,000, 30,000,000)` X/Z bounds, but `Level.getBlockState` does not call that predicate. It returns void air only for Y outside `[0,256)`; otherwise it calls `getChunk(x >> 4, z >> 4)`. The two-argument/`ChunkStatus` `LevelReader` overload requests `load=true`. For the concrete client, an out-of-range request reaches `ClientChunkCache.getChunk`, which returns its `EmptyLevelChunk` when `load` is true; that chunk returns `Blocks.VOID_AIR`. `AirBlock` has an empty shape. Therefore the centered south/north/east first-hit candidates leave `u=+∞`; the final `w > q` gate suppresses setting the AutoJump timer. This conclusion uses the exact client fallback, without assuming an out-of-world chunk exists or is legal.

## West-face loaded witness

The west-face case does not share that suppression: box 1 centers at `(NaN,NaN,NaN)` and samples `(0,0,0)`, then block `(0,1,0)`. A source-reachable ordinary loaded configuration can make that lookup a full cube while keeping the player clear of it:

- Set an integer-aligned west border at X `0`, with its Z span covering the player. Put the local player's 0.6-wide AABB at `minX=0`, `z=-0.4` (its Z interval is `[-0.7,-0.1]`) and feet at Y `1`. A support block at `(0,0,-1)` grounds the player; a full block at `(0,1,0)` is adjacent in Z and does not overlap the player.
- Keep the origin chunk loaded, leave the player's head-clearance cells empty, use no jump effect (so `q=1.2F`), and provide outward input. The current AABB's `1.0E-7` inflation intersects the border while its deflation does not, so the border is yielded before cursor blocks. Collision clipping may leave the actual horizontal delta near zero; both AutoJump implementations retain their input-direction fallback under that condition.
- The first border AABB intersects an outward west side probe and wins the ordered AABB scan. Its `maxY` starts `u=+∞`; its center samples `(0,0,0)`, and the above-state lookup reaches the full cube at `(0,1,0)`. The collision shape top gives `u=2`. With player Y/bounding-box minY `1`, `u - Y = 1`: it does not trip the `>1.2F` early return, and the final checks satisfy `w>0.5F && w<=q`, setting `autoJumpTime=1` in source. The witness is geometric/source reachability, not a runtime replay.

**Updated disposition:** no border-specific 1.14.4/1.15.2 source delta is supported. First-hit timer suppression is source-established for centered south/north/east contact cases with normal jump range. It is not universal: a loaded west-face case can arm the timer through the origin sample. This closes the earlier unresolved sample gate for the stated cases while keeping the source comparison bounded.

## Exact source identity and references

Canonical roots remain `decompiled_minecraft/1.14.4/mojmap` and `decompiled_minecraft/1.15.2/mojmap`, with exact version-root provenance documented in the original finding. Every source hash below matches the respective `*.sources.sha256` entry.

- Both versions: `world/level/border/WorldBorder.java` moving/static collision-shape methods at lines 330–343 and 409–422 (1.14.4 SHA-256 `8fde5c95c74676020882be72011d8adb92787c8375ca1aa21ea1c52d1ebf2207`; 1.15.2 `5030e47d299d2741339dae0274dab41e63ae1eb1468171ef14f181a71a03086f`).
- Both versions: `world/phys/shapes/VoxelShape.java:92–114` and `DiscreteVoxelShape.java:202–257` for coordinate recovery, `toAabbs`, and greedy box extraction (same `DiscreteVoxelShape` SHA-256 `1b518953ec1a8145ebb6c2e2033b2a9c171aa2c30b834196f6e883371cf5fefa`; VoxelShape SHA-256 1.14.4 `6a49550faa944196e16d55a8d28ca8b6cd49f1b75bb491ddc345b935ea0651b4`, 1.15.2 `5faceea0e4ce9ad0f2a1a2c72a89d9a768ab877c8728ae2968fe34aa1e03d3ea`).
- Both versions: `world/phys/AABB.java:348` and `util/Mth.java:50,482` (1.14.4) / `50,497` (1.15.2); `core/Vec3i.java:20–21` (1.14.4) / `23–24` (1.15.2). The shared AABB source SHA-256 is `82324c0e6a3d69e80424656e6098b39ce41f0f17a5e6efbe6542fe8eaca24a18`.
- Both versions: `world/level/Level.java` `isInWorldBounds` and `getBlockState`; `world/level/LevelReader.java:89–94` for `load=true` defaults; `client/multiplayer/ClientChunkCache.java:35–37,65–75` (1.14.4) / `35,65–73` (1.15.2); `world/level/chunk/EmptyLevelChunk.java:31–32`; `world/level/block/AirBlock.java:19–23`.
- Both versions: `client/player/LocalPlayer.java` AutoJump loop/height/timer logic (file hashes and precise slices are listed in the original finding).

No product, wiki, backlog, Git, build, test, decompile, or gameplay input was used.
