# Six-leaf wall exception projection

Scope: compare only the wall connection boolean for the six common leaf block keys `acacia_leaves`, `birch_leaves`, `dark_oak_leaves`, `jungle_leaves`, `oak_leaves`, and `spruce_leaves` between exact 1.13.2 and 1.14.4 Feather sources. This is a bounded predicate/provider slice; it does not establish player trajectory, full provider inventory, intermediate-release onset, or pair freeze.

## Exact source identities

- 1.13.2 Feather Gen2 build.2 ready source manifest: `decompiled_minecraft/1.13.2/ornithe-feather.ready.json`, source manifest SHA-256 `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211`. `decompiled_minecraft/1.13.2/ornithe-feather.sources.sha256` binds these files:
  - `net/minecraft/block/Block.java` `735030e8bb5fc7ead2d6dbcb1b262a36a414b4349daf197456337f460b444cd4`
  - `net/minecraft/block/WallBlock.java` `aeb67adf292a3b750e72a7d2fdc13f7b42f8633c6183964d00dfe8d6960e07bf`
  - `net/minecraft/block/LeavesBlock.java` `8f209a4fcf08dbb4570df915795931f3f876abad8659ded4b17df6e535432006`
  - `net/minecraft/state/property/Properties.java` `3882c1b832ac8b2b16f5a83978da441ab03cdfe66f053a873342758045827f4c`
- 1.14.4 Feather Gen2 build.2 ready source manifest: `decompiled_minecraft/1.14.4/ornithe-feather.ready.json`, source manifest SHA-256 `717b468536348557f9dae8cf57edccf80ad16eb65e5bef6c60bdb5ea3c9f75bc`. `decompiled_minecraft/1.14.4/ornithe-feather.sources.sha256` binds:
  - `net/minecraft/block/Block.java` `1f8ec3628f08e196c2793e91ca0b94feacea6bc3939e7fb770f0682a6e66f2c5`
  - `net/minecraft/block/WallBlock.java` `033da2cef0973f5d380a81b53ee76e23324fffece72edc74e12fcee0d8764be7`
  - `net/minecraft/block/Blocks.java` `9c0323497b31f8a284456e5692898897b92b6f0817d0371fae9a8afb54c638f8`
  - `net/minecraft/block/LeavesBlock.java` `7df0d5e941aa34ccd67ebcf9fec95af2af73cd157ae6e5441a30c8a0dd6aedcd`
  - `net/minecraft/state/property/Properties.java` `9b524af5065100919c60667778e38986380c9df954ef70a9d0ac0a739132ddca`
  - `net/minecraft/tag/BlockTags.java` `dc59d07204b25e5d852d0384e849975367590a3cbc2b9a293396d0bf5637e52a`

## Registered keys and state domains

In 1.13.2 `Block.java` static registration lines 884–889 register each of the six named keys with `new LeavesBlock(...)`. In 1.14.4 `Blocks.java` lines 182–198 register the same six keys with `new LeavesBlock(...)`.

Both `LeavesBlock.buildStateDefinition` methods add exactly `DISTANCE, PERSISTENT`; in each version `Properties.java` defines `DISTANCE` as integer 1 through 7 and `PERSISTENT` as boolean. Thus each of the six keys has the same complete 14-state Cartesian domain in both endpoints. Default state in both constructors is distance 7, persistent false. No per-key subclass or key-specific state override is registered.

## Wall-bit evaluation

1.13.2 `WallBlock.shouldConnectTo` returns `(!isExceptionForConnection(block) && shape == FaceShape.SOLID) || bl`, where `bl` is true only for `MIDDLE_POLE_THICK`, or `MIDDLE_POLE` when the block is a `FenceGateBlock`. `isExceptionForConnection` delegates to `Block.isExceptionForAttachment`; old `Block.isExceptionForPlacement` includes `block instanceof LeavesBlock`, and `isExceptionForAttachment` includes all placement exceptions. Therefore each leaf registration is an attachment/connection exception. The generic solid-face branch is false for all leaf states; the sole escape is false because none is a fence gate and inherited `Block.getFaceShape` returns `SOLID` for leaves.

1.14.4 `WallBlock` computes `(!isExceptionForAttachment(block) && faceSolid) || (block.is(BlockTags.WALLS) || gateCondition)`. `Block.isExceptionForAttachment` explicitly includes `block instanceof LeavesBlock`, so the generic face-solid branch is false for each leaf state regardless of collision shape. Independently, the exact pinned `data/minecraft/tags/blocks/leaves.json` payload lists all six keys, and `Block.isFaceSolid` first returns false for a state in `BlockTags.LEAVES`. The wall-tag fallback is false: the exact pinned `data/minecraft/tags/blocks/walls.json` payload lists 14 wall keys and none of the six leaves; the gate fallback is false because these registrations are `LeavesBlock`, not `FenceGateBlock`.

Resource evidence: `build/source-residual-20261116/1.13.2--1.14.4/wall_resource_membership/payloads/data/minecraft/tags/blocks/leaves.json`, SHA-256 `c09bb9af30197720b232b9c84079039d4f344bfc2970fa0d02a1b43bc091b991`; `.../walls.json`, SHA-256 `f20174f51f66d27681fd9d469a77c86cae82012c0d9125e7d246c426b772391c`. The accepted resource extraction record is `.../wall_resource_membership/extraction-record.md` SHA-256 `f26f84c7221f667cd84ff4e048d06b0fd3c3aec52779f3fbd0a8f4e2afe64fea`; its independent resource verdict is `.../wall_resource_membership_audit/verdict-r2.md` SHA-256 `6402c6af692b3f198c853d3558b91d4de7c215db793e8847e66e45f08f4d8898`.

## Verdict

For all 6 keys × all 14 registered states, both source predicates produce wall connection bit `false`. This is a no-difference result for this exact bounded family. This result closes only these six leaf keys within the wall neighbor predicate; it does not close remaining wall providers, other neighbor predicates, state/resource families, movement callback dependencies, or either pair's full coverage/audit gates.
