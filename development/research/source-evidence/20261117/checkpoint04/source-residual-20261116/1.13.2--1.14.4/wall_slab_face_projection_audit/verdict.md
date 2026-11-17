# Independent source audit verdict

**Verdict: ACCEPT**

Reviewed immutable report `build/source-residual-20261116/1.13.2--1.14.4/wall_slab_face_projection/report.md`, SHA-256 `593cb79845085d5d1d22b96ea3c3dc966aaf939e0dd45ccae4d967177a212542`. No corrections are required for the bounded slab-to-wall projection claim.

## Evidence identity

The 1.13.2 and 1.14.4 Feather Gen2 source-manifest byte hashes match the assigned identities: `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211` and `717b468536348557f9dae8cf57edccf80ad16eb65e5bef6c60bdbb5ea3c9f75bc`. Each endpoint ready marker identifies its exact version and `ornithe-feather` mapping and binds that manifest hash. All cited source-file digests in the report match both local bytes and manifest entries: old `SlabBlock`, `Block`, `WallBlock`; target `SlabBlock`, `Block`, `Blocks`, `WallBlock`, and `VoxelShape`.

## Claim audit

- Registration joins are correct: `stone_slab`, `brick_slab`, `quartz_slab`, and `purpur_slab` instantiate `SlabBlock` in both endpoint registries. The cited target `WALLS` payload hash is correct and has no such keys.
- Both slab definitions expose the Cartesian state domain `HALF={TOP,BOTTOM,DOUBLE}` × `WATERLOGGED={false,true}`. The enum has exactly those three values; `buildStateDefinition` adds both properties without a constraint. All six combinations are therefore valid block states, even though ordinary placement of a double slab resets waterlogging. The compared face/shape gates read `HALF`, not `WATERLOGGED`.
- The old slab `getFaceShape` returns `SOLID` on every face for `DOUBLE`, only `UP` for `TOP`, and only `DOWN` for `BOTTOM`. Old `WallBlock.shouldConnectTo` requires a non-excepted block and `SOLID` for the generic arm. These four slab blocks are not in the old attachment/connection exception lists.
- Target `WallBlock` calls `Block.isFaceSolid` for the generic arm. That gate rejects leaves and checks whether the queried collision-shape face is full. The slab keys are not leaves, walls-tag members, or fence gates. For each horizontal face, the full-cube `DOUBLE` has a full face; `TOP` and `BOTTOM` expose only a half-height boundary slice, which is not a full block. The target attachment exception gate does not exclude these slabs.
- Thus the report's projection is supported in all four horizontal directions: `DOUBLE` true→true; `TOP` and `BOTTOM` false→false, for each key and either waterlogged value.
- Reused geometry evidence is appropriately bounded. The cited stair coverage report has the stated hash and records matching slab-shape bounds. I also confirmed the endpoint base collision dispatch: both `Block.getCollisionShape` implementations return the state shape when collision is enabled, and both slab registrations use ordinary stone collision properties. The 1.14.4 `VoxelShape` face-slice code supports the half-height projection. This confirms only the slab geometry needed for this wall-face comparison; it does not extend the reused report into a general geometry or movement-parity claim.

This verdict accepts only the bounded source projection. It does not establish full-pair coverage, a patch-introduction boundary, or runtime movement parity.
