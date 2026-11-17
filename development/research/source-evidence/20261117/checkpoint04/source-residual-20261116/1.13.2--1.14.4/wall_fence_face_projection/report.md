# Fence neighbor face projection into 1.14 wall arms

## Scope and identities

This is a bounded source comparison for the seven common fence registry keys `oak_fence`, `spruce_fence`, `birch_fence`, `jungle_fence`, `acacia_fence`, `dark_oak_fence`, and `nether_brick_fence`, across all horizontal neighbor directions and all fence connection / waterlogged property combinations. It addresses only whether these neighbors set a WallBlock directional arm. It makes no movement-trajectory or pair-freeze claim.

The compared endpoints are exact Feather Gen2 build.2 sources:

- 1.13.2 source manifest SHA-256: `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211`.
- 1.14.4 source manifest SHA-256: `717b468536348557f9dae8cf57edccf80ad16eb65e5bef6c60bdb5ea3c9f75bc`.

Both ready identities were checked against their source manifests. The files used and their SHA-256 hashes are:

| Endpoint | File | SHA-256 |
| --- | --- | --- |
| 1.13.2 | `net/minecraft/block/FenceBlock.java` | `abefbedea10306abf602d4e379a4793b415ca7635231752cd9f18a80aced5fc2` |
| 1.13.2 | `net/minecraft/block/PaneBlock.java` | `9422e3f2aa9843ecd041f5ced13d05a4c7472d697f7dfd838450e49969e5326c` |
| 1.13.2 | `net/minecraft/block/WallBlock.java` | `aeb67adf292a3b750e72a7d2fdc13f7b42f8633c6183964d00dfe8d6960e07bf` |
| 1.13.2 | `net/minecraft/block/Block.java` | `735030e8bb5fc7ead2d6dbcb1b262a36a414b4349daf197456337f460b444cd4` |
| 1.13.2 | `net/minecraft/block/PipeBlock.java` | `4c56867771e5848fec41d7ccbce5339af3dfefa7f98afb4712dfd0d5abd8f9a9` |
| 1.13.2 | `net/minecraft/state/property/Properties.java` | `3882c1b832ac8b2b16f5a83978da441ab03cdfe66f053a873342758045827f4c` |
| 1.13.2 | `net/minecraft/block/Blocks.java` | `3e128c9a4f6b53e1037fb85269c4d00bc85112ae52fe48f9534232e13ee2a303` |
| 1.14.4 | `net/minecraft/block/FenceBlock.java` | `4319ffb95ad81e969b11b39a8e6361d678b5fb5f3ad210d9834ce5e30d5f45d8` |
| 1.14.4 | `net/minecraft/block/PaneBlock.java` | `7150e89268a84113453cd5416ffe25e1ca7d42a39f264f7ae989878a10ee2158` |
| 1.14.4 | `net/minecraft/block/WallBlock.java` | `033da2cef0973f5d380a81b53ee76e23324fffece72edc74e12fcee0d8764be7` |
| 1.14.4 | `net/minecraft/block/Block.java` | `1f8ec3628f08e196c2793e91ca0b94feacea6bc3939e7fb770f0682a6e66f2c5` |
| 1.14.4 | `net/minecraft/block/Blocks.java` | `9c0323497b31f8a284456e5692898897b92b6f0817d0371fae9a8afb54c638f8` |
| 1.14.4 | `net/minecraft/block/PipeBlock.java` | `50974f5a929e70166c8edbac17dd22b295ceb50bacd345d2b89a25bb150740b6` |
| 1.14.4 | `net/minecraft/tag/BlockTags.java` | `dc59d07204b25e5d852d0384e849975367590a3cbc2b9a293396d0bf5637e52a` |
| 1.14.4 | `net/minecraft/block/state/BlockState.java` | `6c6703c7c2f7203a7507d8a621bc83e1dad613b32089394f5af4fbd8636b165f` |
| 1.14.4 | `net/minecraft/block/shape/VoxelShape.java` | `8efb68bf9d5efdb0ec48e401d1852e90458c7094f8fa0c8e9ae418f268c40fdd` |
| 1.14.4 | `net/minecraft/block/shape/VoxelShapes.java` | `51a09c297c7f01c8bad9b66af2a80900e95f33c6e3a250ce599f4817433587d9` |

The seven 1.13.2 keys register as `FenceBlock` in `net/minecraft/block/Block.java`; in 1.14.4 they register as `FenceBlock` in `net/minecraft/block/Blocks.java`. These registration sources are hash-bound in the table below. The wall predicate's tag/resource dependency is from the pinned 1.14.4 raw-client extraction record `build/source-residual-20261116/1.13.2--1.14.4/wall_resource_membership/extraction-record.md`; it identifies the official client and artifact pin. Extracted `data/minecraft/tags/blocks/walls.json` SHA-256 is `f20174f51f66d27681fd9d469a77c86cae82012c0d9125e7d246c426b772391c`. The complete payload lists 14 wall block keys and none of the seven fences. The extraction record SHA-256 is `f26f84c7221f667cd84ff4e048d06b0fd3c3aec52779f3fbd0a8f4e2afe64fea`; its audited resource verdict is `build/source-residual-20261116/1.13.2--1.14.4/wall_resource_membership_audit/verdict-r2.md`, SHA-256 `6402c6af692b3f198c853d3558b91d4de7c215db793e8847e66e45f08f4d8898`.

## State and direction domain

Each registered fence exposes the four horizontal directional booleans plus `WATERLOGGED`. The full state domain is therefore 2^5 = 32 states per key. The comparison evaluates each of the four horizontal directions, for 7 × 32 × 4 = 896 key/state/direction cases. The wall-arm predicate reads the neighbor block/state and queried horizontal direction; no neighbor-neighbor/world-context variation is introduced by this fence projection.

## 1.13.2 result

`FenceBlock` extends `PaneBlock`, uses the same pane shape construction with fence dimensions (`super(2, 2, 16, 16, 24, ...)`), and overrides `getFaceShape` to return `MIDDLE_POLE` for horizontal faces. In 1.13.2 `WallBlock`'s generic arm branch accepts a `SOLID` face only when the neighbor is not an attachment exception; its separate fence-gate clause accepts the relevant middle-pole shapes only for a `FenceGateBlock`. A registered `FenceBlock` is not a fence gate, so its horizontal `MIDDLE_POLE` face is rejected in all four directions, independent of the fence's directional and waterlogged properties.

## 1.14.4 result

`FenceBlock` still extends `PaneBlock` and uses fence dimensions `super(2, 2, 16, 16, 24, ...)`, with the same four directional properties and `WATERLOGGED`. It no longer supplies the old `MIDDLE_POLE` face-shape override. `PaneBlock.getCollisionShape` selects one of its shapes from the four directional booleans; the waterlogged property does not enter that shape index. The shape contains a central post plus only the arms enabled in the state. For any horizontal boundary face, an enabled arm occupies only the central 2/16-width strip in the tangent axis (the central post itself is 4/16 wide); a disabled arm contributes no boundary intersection. Thus no direction's collision-shape slice is a full face. `Block.isFaceSolid` requires a full face of the collision shape (and excludes leaves), so each fence is rejected by the generic solid-face branch. The other target WallBlock route admits registered `WALLS` members or eligible fence gates; the extracted 14-key `WALLS` payload contains no fence key, and these registrations are not fence gates. Therefore all target wall-arm results are false as well.

## Verdict

For every member of the complete 7 × 32 × 4 domain, the source result is **false in 1.13.2 and false in 1.14.4**. This family produces no wall directional-bit delta. The result relies on the exact endpoint fence/shape/wall implementations, registrations, and pinned target `WALLS` resource payload identified above. It does not close other wall-neighbor families, all block shape providers, the complete pair inventory, or movement trajectories.





