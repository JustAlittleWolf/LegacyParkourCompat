# Independent source audit verdict: wall redstone/fire/tripwire projection r2

**Verdict: ACCEPT**

Reviewed immutable report: `build/source-residual-20261116/1.13.2--1.14.4/wall_redstone_fire_tripwire_projection_r2/report.md`  
SHA-256: `9fe282743610f7d3cc4ed7c22574699756dbb3234e238b7836234b1934cf43d2`

This verdict applies to r2 only. The report bytes match the digest above. The exact Ornithe Feather Gen2 manifests also match the assigned identities: 1.13.2 `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211`; 1.14.4 `717b468536348557f9dae8cf57edccf80ad16eb65e5bef6c60bdbb5ea3c9f75bc`.

## Accepted correction: ladder face projection

The corrected ladder delta is source-confirmed. In 1.14.4 `LadderBlock.java:23–26`, the facing-selected collision slab spans the complete two coordinates parallel to its outer face and reaches the boundary at the other side of `FACING`: NORTH uses z=13..16 (outer SOUTH), EAST x=0..3 (outer WEST), SOUTH z=0..3 (outer NORTH), WEST x=13..16 (outer EAST). `LadderBlock.getShape` selects those shapes directly from `FACING` (`:33–45`); WATERLOGGED is not consulted.

The full projection path supports the report's result: `Block.isFaceSolid` calls `isFaceFull` on the collision shape (`Block.java:402–408`), and its full-block cache checks equality of occupied volume with `VoxelShapes.block()` (`:84–90`). The target face-slice operation first requires the shape to reach the requested outer coordinate (`VoxelShapes.java:317–334`); `VoxelShape` obtains/caches that directional slice (`:170–200`); `SliceShape.makeSlice` crops only the boundary voxel in the normal axis while retaining the delegate's complete parallel extents (`SliceShape.java:17–26`, with coordinate forwarding in `:29–32`; `CroppedVoxelSet.java:14–27`). The resulting ladder face is a full unit face despite the slab's 3/16 thickness. Thus target face-solid is true in the direction opposite FACING: NORTH→SOUTH, EAST→WEST, SOUTH→NORTH, WEST→EAST. The 1.13.2 ladder returns `FaceShape.UNDEFINED` unconditionally (`LadderBlock.java:126–128`), giving four false old wall bits. The ladder row is a genuine projection difference.

## Remaining rows and wall predicate

The other eight rows are unchanged: tripwire, tripwire hook, vine, torch, redstone wall torch, lever, redstone wire, and fire each return `FaceShape.UNDEFINED` unconditionally on the old side. Their target collision registrations are empty (`noCollision()`), so every target horizontal collision-face projection is false, including facing-dependent outlines. The listed old keys are absent from the old connection-exception additions and target attachment-exception predicate; none is a fence gate or wall. Old `WallBlock.shouldConnectTo` therefore rejects their UNDEFINED shapes (`WallBlock.java:51–65`). Target `WallBlock` requires the supplied face-solid boolean unless the block is wall-tagged or the fence-gate case applies (`WallBlock.java:48–52`); its placement path supplies `BlockState.m_87223014` for the corresponding outward face (`:63–70`). This establishes the reported all-false result for those eight rows and the ladder target bit described above.

## Identity checks

I independently recomputed every source hash listed in r2's source-hash tables for the ten provider classes, old and target `Block.java`/`WallBlock.java`, target `BlockState.java`, target `Blocks.java`, and all six target projection-path classes. Each matches the value reported. Both endpoint manifest hashes match. In particular the exact ladder/projection-path hashes are:

- 1.13.2 `LadderBlock.java`: `c89dea1d7648bf5908e9d925b31b1ad6ead25bea3784838ae3d0fb505cfb9ab5`.
- 1.14.4 `LadderBlock.java`: `12dc5ef28f2db058969218573f5d605b94cab4cc36b67b2d46e82aaf39fc767f`.
- 1.14.4 `Block.java`: `1f8ec3628f08e196c2793e91ca0b94feacea6bc3939e7fb770f0682a6e66f2c5`.
- 1.14.4 `VoxelShape.java`: `8efb68bf9d5efdb0ec48e401d1852e90458c7094f8fa0c8e9ae418f268c40fdd`.
- 1.14.4 `VoxelShapes.java`: `51a09c297c7f01c8bad9b66af2a80900e95f33c6e3a250ce599f4817433587d9`.
- 1.14.4 `SliceShape.java`: `cdc2794ebcf185410c9171ce72ce9a63a8d1f32c067496c8624125ef77861455`.
- 1.14.4 `CroppedVoxelSet.java`: `d9a71b7c5c98329606e1744ee7544dfafaa403c2bb9250c670866ba4d83d2621`.

No source-generation, product/wiki/backlog/history edits, tests, builds, or game launches were performed. This accepts only the bounded r2 source claims; it does not close the full wall census or establish pair freeze, runtime movement effect, or an intermediate patch boundary.
