# Wall non-gate neighbor predicate closure

## Scope and source identity

Bounded follow-up to `wall_shape_provider/coverage.md` (SHA-256 `477a4ec3fd5179fc36a78100633d3c189ca19203252aaa333309e4af1c4a61f1`) and its audit. Exact sources are the assigned Feather manifests: 1.13.2 `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211`; 1.14.4 `717b468536348557f9dae8cf57edccf80ad16eb65e5bef6c60bdbb5ea3c9f75bc`.

Relevant source file hashes:

- 1.13.2 `WallBlock.java`: `aeb67adf292a3b750e72a7d2fdc13f7b42f8633c6183964d00dfe8d6960e07bf`.
- 1.13.2 `Block.java`: `735030e8bb5fc7ead2d6dbcb1b262a36a414b4349daf197456337f460b444cd4`.
- 1.14.4 `WallBlock.java`: `033da2cef0973f5d380a81b53ee76e23324fffece72edc74e12fcee0d8764be7`.
- 1.14.4 `Block.java`: `1f8ec3628f08e196c2793e91ca0b94feacea6bc3939e7fb770f0682a6e66f2c5`.
- 1.14.4 `BlockState.java`: `6c6703c7c2f7203a7507d8a621bc83e1dad613b32089394f5af4fbd8636b165f`.
- 1.14.4 `TntBlock.java`: `95d549d2d39a1ad3e5c65d8a261eba86ac98c7888c5d5634fbb6a821f858535e`.
- 1.14.4 `Blocks.java`: `9c0323497b31f8a284456e5692898897b92b6f0817d0371fae9a8afb54c638f8`.

## Bounded verdict: concrete TNT neighbor changes the wall bit

A concrete common non-gate neighbor produces a differing horizontal wall bit: TNT.

In 1.13.2 `WallBlock.shouldConnectTo` (`WallBlock.java:51–65`) accepts a `FaceShape.SOLID` neighbor only when it is not `isExceptionForConnection`; that exception explicitly includes TNT. TNT inherits `Block.getFaceShape == FaceShape.SOLID` (`Block.java:383`), so the solid-face branch rejects it; it is not a fence gate and does not meet the `MIDDLE_POLE_THICK` alternative. The old result is false.

In 1.14.4 `WallBlock.m_71853799` (`WallBlock.java:48–52`) accepts `!isExceptionForAttachment(block) && bl` independently of the WALLS tag. TNT is not explicitly in `isExceptionForAttachment` (`Block.java:260–267`). It inherits `Block`'s default full collision shape (`Block.java:372, 376–377`) and collision-enabled property (`Block.java:811`); the target `Block.isFaceSolid` excludes LEAVES-tagged states and otherwise checks for a full collision face (`Block.java:402–410`). Exact 1.14.4 `leaves.json` membership excludes `minecraft:tnt`, so TNT's face is solid. Exact `walls.json` membership also excludes `minecraft:tnt`, but that does not affect this result: the face-solid disjunct independently evaluates true. Target result is true.

Both endpoint registrations include TNT as a common block (1.13.2 `Block.java:982`; 1.14.4 `Blocks.java:390`). Both wall `getPlacementState` methods compute the four horizontal neighbor bits (1.13.2 `WallBlock.java:69–91`; 1.14.4 `:55–80`); both `updateShape` methods recompute the changed horizontal neighbor bit, while DOWN delegates to the superclass (1.13.2 `:96–116`; 1.14.4 `:82–104`). Placing a wall next to TNT, or updating the wall after a horizontal TNT neighbor change, therefore sets that arm bit in 1.14.4 and leaves it unset in 1.13.2. The stored bit changes the wall collision shape; this is a concrete player-collision-reachable state-production difference, not a measured trajectory.

## Exact target resource evidence

The raw 1.14.4 client JAR SHA-256 `b3b2a798e2d67b566008fe4a03767ae2c7ff3f8c7ba6751e7b71fc7299672d0a` matches the pinned source-evidence client identity. The exact target payloads were extracted from that JAR and verified in `wall_resource_membership/extraction-record.md`, SHA-256 `f26f84c7221f667cd84ff4e048d06b0fd3c3aec52779f3fbd0a8f4e2afe64fea`:

- `data/minecraft/tags/blocks/leaves.json`: SHA-256 `c09bb9af30197720b232b9c84079039d4f344bfc2970fa0d02a1b43bc091b991`; TNT absent (6 values).
- `data/minecraft/tags/blocks/walls.json`: SHA-256 `f20174f51f66d27681fd9d469a77c86cae82012c0d9125e7d246c426b772391c`; TNT absent (14 values).

The resource hashes also match `development/research/source-evidence/20261103/checkpoint03/1.14.4-resource-manifest.json`. The earlier missing-resource limitation is resolved for this bounded TNT case. This does not establish a complete neighbor inventory, pair freeze, or measured movement trajectory.
