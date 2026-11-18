# One-axis disposition: extending stone moving piston

Scope: ordinary Entity SELF collision collection for the initial eastward-extending non-source Stone moving block entity at block position `(2, 0, 0)`, plus the paired moving head at `(1, 0, 0)`. This closes only nonembedded one-axis collision collection/clipping. It does not cover direct piston pushing, diagonal movement, later block-entity phases, or a complete tick.

## Exact inputs

- Minecraft 1.8.9, Ornithe Feather `feather-gen2-1.8.9+build.2`, source root `decompiled_minecraft/1.8.9/ornithe-feather`. Ready marker reports 1,612 source files and source-manifest SHA-256 `9e75f46dc0ed43b6a355bd65db8a92c93a4dfeaecfa92284187c6fe9410d8004`; current manifest and every source file cited below match those recorded hashes.
- Minecraft 26.2, unobfuscated published names, source root `decompiled_minecraft/26.2/unobfuscated`. Ready marker reports 7,055 source files and source-manifest SHA-256 `a7ad74fc712567eb87136afc1eafcfd602b4088e9869f3596331fd34faefe894`; current manifest and every source file cited below match those recorded hashes.

## Geometry at progress 0

In 1.8.9, `MovingBlock.getCollisionShape` samples `getProgress(0)`, then replaces it with `1 - progress` for extension (`block/MovingBlock.java:118-129`). The shared shape routine adjusts only the forward bound and constructs a `Box` (`:162-191`). For a full Stone block at x=2 this produces the zero-width x-plane `[2,2]`. The extension also creates a moving piston-head BE at x=1 (`block/PistonBaseBlock.java:323-332`). Its east-facing head plate starts at local x `[0.75,1]`, with full y/z extent (`block/PistonHeadBlock.java:127-148`); the moving-shape calculation normalizes the adjusted bounds into world x `[1,1.75]`, y/z `[0,1]`.

In 26.2, `PistonMovingBlockEntity.getCollisionShape` uses `extendedProgress = progress - 1` for extension and moves the moved state’s shape by that offset (`world/level/block/piston/PistonMovingBlockEntity.java:106,366-393`). The non-source Stone shape at x=2 is therefore x `[1,2]`, y/z `[0,1]`. The source head BE at x=1 is separately created with `isSourcePiston=true` (`PistonBaseBlock.java:327-334`); at progress 0 its head plate is translated to x `[0.75,1]`. The shapes touch at x=1.

## Collection and one-axis resolution

The assertion that 1.8.9 `Box.intersects` rejects every zero-width box is false. Its comparisons are `box.maxX <= this.minX || box.minX >= this.maxX` (and equivalent y/z checks); it has no positive-dimension test (`util/math/Box.java:153-157`). With the swept box as receiver, plane `[2,2]` intersects whenever `swept.minX < 2 < swept.maxX` and y/z overlap. Equality at either x face rejects it. `World.getCollisions` scans x in `[floor(minX), floor(maxX + 1))` (`world/World.java:891-921`), so the x=2 cell is visited when the swept interval contains 2 strictly. `Block.addCollisions` applies that same `shape.intersects(box)` predicate (`block/Block.java:339-341`). If the plane is collected, `Box.intersectX` clips a positive request to `2 - player.maxX` and a negative request to `2 - player.minX` when the relevant starting face is at or beyond 2 (`util/math/Box.java:93-111`). For y/z clipping, the plane can act as a cross-axis contact when x=2 is strictly inside the player box; the clip predicates test the other two axes with strict inequalities.

The modern `Entity.collide` sends `boundingBox.expandTowards(movement)` through block-collision collection and shape clipping (`world/entity/Entity.java:1141-1144,1195-1199`). `BlockCollisions` scans an extra cell around that box (`world/level/BlockCollisions.java:54-56`), admits moving-piston blocks at its boundary, and for non-full shapes requires a nonempty Boolean-AND join with the swept AABB (`:84-101`). The current Stone shape has positive volume, so a sweep entering x `[1,2]` is collected; this is not an AABB-plane test. `VoxelShape.collide` clips positive motion at its minimum face and negative motion at its maximum face (`world/phys/shapes/VoxelShape.java:252-300`).

For a nonembedded player with horizontal width 0.6, the old head and Stone plane leave only a 0.25 x-gap between the head’s `[1,1.75]` interval and the plane at 2. A 0.6-wide x interval cannot fit wholly in that gap. Therefore, for one-axis moves with the necessary y/z overlap:

| Move | Modern Stone contact | 1.8.9 moving-head/Stone contact | Disposition |
| --- | --- | --- | --- |
| +X from the west | Stone’s x=1 face | Head plate’s x=1 face | Same first contact at x=1; the Stone plane at 2 is later and cannot create an earlier response. |
| -X from the east | Stone’s x=2 face | Stone plane `[2,2]`; it is collected when the sweep crosses x=2 strictly | Same first contact at x=2. At exact-touch equality, neither version clips beyond the requested contact. |
| ±Y | Stone’s y=0 or y=1 face | Head plate or Stone plane supplies the same y face whenever the player x interval overlaps the modern Stone cross-section | Same one-axis contact; width 0.6 prevents the interval from fitting the 0.25 gap. |
| ±Z | Stone’s z=0 or z=1 face | Head plate or Stone plane supplies the same z face under the same x-overlap condition | Same one-axis contact; width 0.6 prevents the interval from fitting the 0.25 gap. |

The 1.8 m player height does not change the x-gap proof; it only determines whether the player box actually overlaps the relevant y extent. If y/z overlap is absent, both releases lack that collision. The piston base and any other ordinary blocks can add an earlier contact, but cannot create a later modern-only contact in this bounded one-axis comparison.

## Disposition

At the individual moving-Stone collector, 26.2 can collect a positive-volume predecessor region that 1.8.9 does not enumerate as the x=2 Stone cell. That fact alone is not a player movement difference for the bounded nonembedded one-axis case: the 1.8.9 moving head supplies the same earlier west contact, and the degenerate Stone plane supplies the east and orthogonal contacts. No bounded one-axis movement counterexample is established here. No full-tick, direct-push, diagonal, or initial-embedded conclusion follows.
