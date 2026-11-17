# Barrier neighbor-face projection for walls

## Scope

This packet resolves only the minecraft:barrier row marked unexamined in the audited registered-key crosswalk. It compares the old wall connection predicate with its 1.14.4 replacement and reports the four horizontal neighbor bits for every legal barrier state. It does not claim a movement trajectory, wall-shape parity, full pair coverage, or pair freeze.

## Exact source identities

Both endpoint sources are canonical read-only Ornithe Feather Gen2 build.2 outputs. The ready markers bind source manifest SHA-256 1.13.2 2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211 and 1.14.4 717b468536348557f9dae8cf57edccf80ad16eb65e5bef6c60bdbb5ea3c9f75bc.

Relevant source-file hashes, each checked against its endpoint ornithe-feather.sources.sha256 manifest and local bytes:

- 1.13.2 net/minecraft/block/WallBlock.java: aeb67adf292a3b750e72a7d2fdc13f7b42f8633c6183964d00dfe8d6960e07bf; Block.java: 735030e8bb5fc7ead2d6dbcb1b262a36a414b4349daf197456337f460b444cd4; BarrierBlock.java: 4cc8dc003dc1484994082fdd383e7d5cd0b300cc0b42b3b34f0462c2b3b58eb6; Blocks.java: 3e128c9a4f6b53e1037fb85269c4d00bc85112ae52fe48f9534232e13ee2a303.
- 1.14.4 net/minecraft/block/WallBlock.java: 033da2cef0973f5d380a81b53ee76e23324fffece72edc74e12fcee0d8764be7; Block.java: 1f8ec3628f08e196c2793e91ca0b94feacea6bc3939e7fb770f0682a6e66f2c5; BarrierBlock.java: 02c096fe4eca6424de9d35a4e24b481371b6934236e727eb390031250be59efe; Blocks.java: 9c0323497b31f8a284456e5692898897b92b6f0817d0371fae9a8afb54c638f8.

The exact row is minecraft:barrier → BarrierBlock → Block collision-shape owner, marked <singleton>; product=1 at both endpoints in wall_full_registry_owner_crosswalk/unexamined-wall-projection-keys.tsv.

## Registration, state, and shape

- 1.13.2 Block.java registers "barrier" as new BarrierBlock(Block.Properties.of(Material.BARRIER).strength(-1.0F, 3600000.8F)); Blocks.java resolves BARRIER from the "barrier" registry key. 1.14.4 Blocks.java registers "barrier" as new BarrierBlock(...).noDrops().
- BarrierBlock in both releases adds no state properties and overrides no collision or face-shape method. Its inherited base state definition is empty, so the legal state domain is exactly one state at each endpoint.
- In both releases, base collision is enabled by default (Properties.hasCollision = true); base getShape is the full unit cube. Thus old getCollisionShape and new getCollisionShape resolve to a full cube. Target Block.isFaceSolid tests the collision-shape face using isFaceFull, so the raw target face-solid input is true in all four horizontal directions. No state or context gate changes this result for this block.

## Predicate comparison

In 1.13.2 WallBlock.shouldConnectTo receives BlockState.getFaceShape(...). For BarrierBlock, inherited Block.getFaceShape returns FaceShape.SOLID. However, WallBlock.isExceptionForConnection explicitly returns true for Blocks.BARRIER (in addition to Block.isExceptionForAttachment and other exclusions). The remaining pole exception cannot apply: barrier is not a FenceGateBlock and its face is not a middle-pole shape. Consequently the old predicate is false for each horizontal direction.

In 1.14.4 WallBlock.m_71853799 receives BlockState.m_87223014(...), the state face-solid predicate. Block.isExceptionForAttachment explicitly excludes Blocks.BARRIER; therefore the generic !exception && faceSolid clause is false. The fallback block.is(BlockTags.WALLS) || block instanceof FenceGateBlock && ... is also false: barrier is not a fence gate, and the exact 1.14.4 vanilla data/minecraft/tags/blocks/walls.json payload has no minecraft:barrier entry. Its data/minecraft/tags/blocks/leaves.json payload likewise has no barrier entry; the independent LeavesBlock exception is inapplicable by class. The exact extracted 1.14.4 raw-client resource artifact SHA-256 is b3b2a798e2d67b566008fe4a03767ae2c7ff3f8c7ba6751e7b71fc7299672d0a; the walls/leaves payload hashes are f20174f51f66d27681fd9d469a77c86cae82012c0d9125e7d246c426b772391c and c09bb9af30197720b232b9c84079039d4f344bfc2970fa0d02a1b43bc091b991 respectively. Resource extraction identity and provenance are recorded in wall_resource_membership/extraction-record.md.

| Neighbor direction | Legal barrier states | 1.13.2 face input / exception / wall bit | 1.14.4 face input / exception / WALLS-or-gate fallback / wall bit |
|---|---:|---|---|
| NORTH | singleton | SOLID / true / false | full / true / false / false |
| EAST | singleton | SOLID / true / false | full / true / false / false |
| SOUTH | singleton | SOLID / true / false | full / true / false / false |
| WEST | singleton | SOLID / true / false | full / true / false / false |

## Disposition

The barrier neighbor connection bit is unchanged: false in all four horizontal directions for the only legal barrier state. The raw face test changes representation from legacy FaceShape.SOLID to a full collision face, but both wall predicates exclude the barrier before that raw solidity can enable a connection. This closes only the minecraft:barrier neighbor-predicate row. The result is not a movement finding and does not establish full wall/provider, tick, or pair coverage.
