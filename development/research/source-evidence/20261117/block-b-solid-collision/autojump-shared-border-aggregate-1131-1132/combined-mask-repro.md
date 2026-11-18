# Finite border aggregate with the physical Stone floor

Scope: scratch-only correction pending fresh independent reread. This rechecks the exact Speed II east probe on the two plain Stone cells (8,63,0)/(9,63,0), and separately flags a narrower correction to the earlier tangent witness. No product or canonical evidence work was released.

## Exact collector contract

The named 1.13.2 AutoJump call is `LocalClientPlayerEntity.autoJump` (the `getCollisions(this, box)` call near line 935). `WorldView.java:166–177` passes the probe voxel shape and an empty exclusion. `World.java:1755–1757` requests the `WorldAccess` super block stream and appends entity collisions afterward. `WorldView.java:112–151` sends every admitted canonical `VoxelShapes.block()` to the single `BitSetVoxelSet`, whether obtained from the physical Stone state or from outside-border substitution. It returns empty from each such cell and appends one `IntVoxelShape` filtered for overlap only after those cells have populated its mask. There is no per-cell positive-overlap filter before set(). The dry Stone floor touches query.minY, but its occupancy still participates in the shared greedy decomposition.

`VoxelShape.java:109–112` visits the aggregate boxes. `VoxelSet.java:200–251` scans X, Y, Z, clears its first contiguous Z run, extends X, then extends Y. `IntVoxelShape` applies the integer scan origin. Exact 1.13.1 and 1.13.2 targeted files were checked against their manifests. The collector, source shapes and AutoJump files named here are byte-identical. World.java forwarding was read at the named 1.13.2 location.

## Eroded center fixture

Scan X [7,12), Y [63,67), Z [-2,3). A cell passes if it lies on fewer than two scan edges. Include only real Stone (8,63,0)/(9,63,0) and synthetic x>=10 cells. At y=63,z=0, x=10 is admitted while x=11 is a simultaneous X/Y edge and omitted. At y=64,z=0 both x=10 and x=11 are admitted. The first floor run therefore consumes x=8, 9 and 10 at y=63,z=0. Its emitted box only touches the query bottom and misses both raised rays. The two resulting adjacent Z runs at x=10 continue through y=66; neither contains either ray at z≈.2/.8. The remaining x10 y64 z0 run extends into x11, then to y65. At y66 the x11 cell is excluded, so the run stops.

| X | Y | Z | Raised ray hit |
|---|---|---|---|
| (8, 11) | (63, 64) | (0, 1) | False |
| (10, 11) | (63, 67) | (-1, 0) | False |
| (10, 11) | (63, 67) | (1, 2) | False |
| (10, 11) | (64, 66) | (-2, -1) | False |
| (10, 12) | (64, 66) | (0, 1) | True |
| (10, 11) | (64, 66) | (2, 3) | False |
| (10, 11) | (66, 67) | (0, 1) | False |
| (11, 12) | (64, 66) | (-1, 0) | False |
| (11, 12) | (64, 66) | (1, 2) | False |


The first hit is [10,12] × [64,66] × [0,1], center (11,65,.5), which floors to (11,65,0). With no Jump Boost, rise 2 exceeds 1.2. Jump Boost I (1.95) still fails. Jump Boost II cap 2.7 succeeds given AIR at the exact t=1,2 reads (11,66,0)/(11,67,0) and the x8 clearance cells y65–67. For Jump Boost III cap 3.45, also require (11,68,0) and (8,68,0). The setup premise is unchanged. The earlier x10 sample or height 67 result does not bind this combined mask.

The prior tangent fixture is also sensitive to the floor. Its original scan X [8,12), Y [63,67), Z [-2,2) with real Stone at (9,63,0) has first qualifying box [10,11] × [63,67] × [-1,0] (negative Z ray), not the border-only [-1,1] span. The height 67 and suppression at empty clearance remain; the sampled cell moves to (10,66,-1). Canonical prior evidence is held unchanged pending independent review. The implemented 1.12 per-cell eroded branch does not combine occupied cells and is unaffected.

## Exact rehashed 1.13.2 files

- world/World.java: `0023de30da608db73c6c6188334d2bb19039d86fe4d6f5e4ad79d732a5d2cd1a`.
- world/WorldView.java: `9577360b1a1bdde9280f0b72593e5e6dde45d9a54719cd7ff933f3ecd31b971f`.
- block/shape/VoxelSet.java: `fe39ea60ac27e595280627a4d15ea93a69292da5870b5ef34a1d7d7283a267c6`.
- block/shape/VoxelShape.java: `d95b0607647da11595e24e1e26e9615cb7da7705ea7885908544362df62275c1`.
- block/shape/IntVoxelShape.java: `e563f5530907a5cd0ecc07bd79473d378c8c1aeeaba1dba4ac6f703de7d7b3ca`.
- block/shape/BitSetVoxelSet.java: `2048daa3a95ddd567a70476862e9b2dde135b91f49b61d22a4ae3aeed98322aa`.
- client/entity/living/player/LocalClientPlayerEntity.java: `2583495f3a02b4791aa036e6a8d354d7596c4984761969a0f29b29d8d9bf42bf`.
- block/Block.java: `735030e8bb5fc7ead2d6dbcb1b262a36a414b4349daf197456337f460b444cd4`.
- block/StoneBlock.java: `4a85d515f1195600042d7e67de00767c77c91c2e74aa00ac7796ceb7ea78c906`.
