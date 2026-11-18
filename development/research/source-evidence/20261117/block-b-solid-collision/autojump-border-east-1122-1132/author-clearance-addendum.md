# East-border AutoJump clearance addendum

This standalone addendum refines the east-border fixture in `analysis.md` without editing that report. It supersedes the interim `analysis.md` statement that the first 1.13.2 border box reaches maxY=67: the exact corner-filtered box that first intersects this ray reaches maxY=66. Exact sources are Minecraft 1.12.2 and 1.13.2, Ornithe Feather Gen2 build.2; ready source-manifest hashes are 1.12.2 `b8a37ccfccd2aac5f40f5e34fec85873dbdfa93a266043c103e592e4a8c949da` and 1.13.2 `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211`. The relevant files and hashes match those manifests in the exact source tree.

## Exact physical clearance samples

Use the prior fixture: stationary border centered `(0,0)`, size 20 (`maxX=10`); player feet `(9.7,64,0)`, width `.6`, height `1.8`, grounded, timer 0 at the AutoJump call, zero actual horizontal delta, forward input east, yaw `-90°`, speed `.1`, no jump boost. Put physical AIR at all of these exact coordinates:

- Player-top clearance before the scan: `(9,65,0)` and `(9,66,0)`. `BlockPos` floors x=9.7 and y=`getShape().maxY`=65.8, then checks that position and `.up()`.
- 1.12.2 per-cell border-hit clearance: `(10,65,-1)`. The x-outer/z-next collision scan reaches outside cell x=10,z=-1 before z=0; its raised ray lane z=-.3 intersects synthetic cell `(10,64,-1)`, center `(10.5,64.5,-0.5)`. `BlockPos` floors to `(10,64,-1)`, and the sole `t=1` sample (`o=1.2`) is `(10,65,-1)`. The z=+.3 lane also hits z=0 later, but the list loop has already broken on z=-1.
- 1.13.2 aggregate-border first-hit clearance: `(10,66,0)`. For this finite scan, the first ray-intersecting `getBoundingBoxes()` result from the border `VoxelSet` is the box `[10,11] x [64,66] x [-2,2]`. Its center `(10.5,65,0)` floors to `(10,65,0)`; its sole `t=1` sample is `(10,66,0)`. Using the source single-precision path for yaw -90° gives `p=0.699999988...`, direction x≈0.9982505, endpoint x≈10.398775, and query x `[9.099999976,10.998775]`, z approximately `[-.600000024,.600000024]`, y `[64,65.8]`. Thus scan bounds are x `[8,12)`, z `[-2,2)`, y `[63,67)` (x cells 8..11, z -2..1, y 63..66), making x=11 and y=63/66 the scan edges. The x=10 interior column has y=63..66 fake cells, but the voxel box decomposition does not produce one y=63..67 box through that ray: at y=63 and y=66 the z-edge cells are removed by the two-edge/corner filter, while at y=64 and y=65 the full z run remains. The first raised-ray-intersecting box thus has maxY=66, not 67. It still gives `u=66-64=2 > 1.2`; with the stated AIR at `(10,66,0)`, no above-shape adjustment occurs and the timer remains 0.

The sample sets do not alter the border result: old has `s=65`, `u=1` and sets timer `0 -> 1`; new has `s=66`, `u=2` and does not set it. There is no timer reversal under these clearance gates. If a nonempty collision shape occupied `(10,66,0)`, the 1.13 branch would raise `s` to at least 66 and return over the 1.2 cap; it still would not set the timer, but AIR keeps the witness's no-adjustment path explicit.

## Support and loading

Place a full solid support block at `(9,63,0)`. The player AABB touches its top at y=64 and overlaps it in x/z; it is inside the east border cell range and is not part of the AutoJump raised rays (`y=64.51`). Keep `(10,64,-1)`, `(10,65,-1)`, `(10,66,-1)`, `(10,64,0)`, `(10,65,0)`, and `(10,66,0)` physically AIR as well, so both raised lanes and both clearance columns have explicit native states. x=10 is outside the continuous border but lies in the same loaded x-chunk (0) as x=9. Load chunks `(0,-1)` and `(0,0)`: scan z cells -2 and -1 are in z-chunk -1, while z cells 0 and 1 are in z-chunk 0. All scan x cells 8..11 are in x-chunk 0; both chunks make the full finite scan and relevant synthetic cells available.

The source calls that substitute synthetic border collision geometry do not mutate block states. In 1.12.2, `World.getCollisions` substitutes a stone `BlockState` only in its local collision-list variable when `!worldBorder.contains(pos) && bl2`; `World.getBlockState` independently fetches the loaded chunk's actual state. In 1.13.2, `WorldView.getBlockCollisions` selects a synthetic `VoxelShapes.block()` only for the collision stream/voxel set; direct `World.getBlockState` still reads the loaded chunk. Thus physical AIR at outside-border x=10 can satisfy AutoJump's native above-block query while the probe simultaneously receives a synthetic border collision box.

## Source anchors

- 1.12.2 `LocalClientPlayerEntity.java`: AutoJump clearance and per-box sample, lines 884–949; 1.13.2 same, lines 909–972.
- 1.12.2 `World.java`: local border-to-stone substitution, lines 964–1010; direct `getBlockState`, lines 680–685. 1.13.2 `WorldView.java`: cell border substitution and VoxelSet population, lines 112–152; direct `World.getBlockState`, lines 519–526.
- 1.12.2 / 1.13.2 `WorldBorder.java`: overlap-based `contains(BlockPos)`, lines 24–26 / 22–24.
- 1.13.2 `VoxelSet.java`: scan-box merging, lines 200–255; `VoxelShape.java`: `getBoundingBoxes`, lines 94–112; `BlockPos.java` lines 212–235: inclusive scan iteration; `Vec3i.java` lines 19–20: double-to-block floor conversion. `MathHelper.java` provides the sine/cosine lookup and fast inverse square root used in the stated query arithmetic (lines 15–20 and 360 in 1.12.2; lines 24–29 and 386 in 1.13.2).

No product/wiki/backlog/Git/build/test/runtime/decompile or primary-source write was performed.




