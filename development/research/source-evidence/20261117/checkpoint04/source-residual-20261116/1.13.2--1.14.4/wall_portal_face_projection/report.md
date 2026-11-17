# Portal and End-block wall face projection

## Scope and evidence identity

This closes only the five common keys assigned from `wall_remaining_face_shape_census/report.md` (SHA-256 `f1a12c7564f14c91c652bea6603e8ada8350b98c2892df05c591c0161d4e041d`): `minecraft:end_portal_frame`, `minecraft:end_gateway`, `minecraft:end_portal`, `minecraft:end_rod`, and `minecraft:nether_portal`. It compares Minecraft 1.13.2 with 1.14.4 using Ornithe Feather Gen2 build 2. The read-only source manifest hashes are 1.13.2 `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211` and 1.14.4 `717b468536348557f9dae8cf57edccf80ad16eb65e5bef6c60bdb5ea3c9f75bc`; both recomputed hashes match.

The target wall query is `Block.isFaceSolid` → registered state's collision shape → directional face projection → `Block.isShapeFullBlock`; `isFaceSolid` also rejects leaves. The target wall connection predicate additionally requires the block not be an attachment exception, or separately be tagged `WALLS` / be a directionally compatible fence gate. None of these five classes is a wall or fence gate, so only the full-face branch can connect them. In 1.13.2, `WallBlock.shouldConnectTo` accepts a non-exception only with old `FaceShape.SOLID`, or its separate middle-pole/fence-gate case. All five old horizontal face results below are `UNDEFINED`, so their old cardinal wall bits are false regardless of the exception predicate.

## Per-key state and four-direction result

| Key / class | Registered state domain (both endpoints) | 1.13.2 old horizontal FaceShape and wall bit | 1.14.4 effective collision face / wall bit, N/E/S/W | Relevant gates and conclusion |
|---|---|---|---|---|
| `end_portal_frame` / `EndPortalFrameBlock` | `FACING` = north/east/south/west; `EYE` = false/true (8 states). | `getFaceShape` returns `UNDEFINED` for every horizontal face; old bits all false. | Collision is enabled by default. `getShape` is the full-footprint base from y=0 to 13/16; with an eye it adds only a centered 8/16-by-8/16 upper box. For every state, each horizontal projection lacks full vertical coverage or full horizontal width. `isFaceFull` is false N/E/S/W; target bits all false. | Facing does not alter the shape. Eye state cannot fill the missing extent. The target shape provider ignores world, position, and collision context; no block entity. No delta in any state/direction.
| `end_gateway` / `EndGatewayBlock` | No declared block-state properties (1 state). | `getFaceShape` returns `UNDEFINED` for every face; old cardinal bits all false. | Registration sets `noCollision`; base `getCollisionShape` therefore returns the empty shape before consulting state shape or world/position. Its directional projections are not full; target bits all false N/E/S/W. | It creates an `EndGatewayBlockEntity`, but neither that entity nor its world data can affect the collision result: the `noCollision` early return dominates. No delta in any direction.
| `end_portal` / `EndPortalBlock` | No declared block-state properties (1 state). | `getFaceShape` returns `UNDEFINED` for every face; old cardinal bits all false. | Registration sets `noCollision`; base `getCollisionShape` returns empty, so all four projections fail and target bits are all false. The class's 12/16-high `getShape` is bypassed for collision. | It creates an `EndPortalBlockEntity`, but the entity, world, and position do not enter the collision result because of the registration gate. No delta in any direction.
| `end_rod` / `EndRodBlock` | `FACING` = down/up/north/south/west/east (6 states). | `getFaceShape` returns `UNDEFINED` for every face; old cardinal bits all false. | Collision is enabled by default and uses the unchanged state-selected shape: X-aligned rod has 4/16 height and 4/16 Z width; Z-aligned has 4/16 height and 4/16 X width; Y-aligned has 4/16 X and Z widths. Every horizontal face projection is deficient in at least one dimension for all six facing states. Target bits all false N/E/S/W. | Facing chooses only the three axis shapes; world, position, and collision context are not read by the shape provider. No block entity. No delta in any state/direction.
| `nether_portal` / `PortalBlock` | `AXIS` = X/Z (the `HORIZONTAL_AXIS` property; 2 states). | `PortalBlock.getFaceShape` returns `UNDEFINED` for every face, inherited by the registered nether portal; old cardinal bits all false. | Registration sets `noCollision`; base collision shape is empty for both axis states, so all four projections fail and target bits are all false. The axis-specific 4/16-thick outline is bypassed for collision. | Neither axis nor world/position affects the no-collision result. No block entity. No delta in any state/direction.

For the frame and rod, the result follows from their target registered collision shapes; for gateway, portal, and nether portal, it follows from the registration-level collision disable. The target leaves exclusion cannot turn any result positive: the three enabled-shape states have no full face, and the other classes have empty collision shapes.

## Exact source files

All paths are relative to `decompiled_minecraft/<version>/ornithe-feather/`; hashes are SHA-256 of source bytes and match the endpoint manifest entries.

| File | 1.13.2 SHA-256 | 1.14.4 SHA-256 |
|---|---|---|
| `net/minecraft/block/Blocks.java` | `3e128c9a4f6b53e1037fb85269c4d00bc85112ae52fe48f9534232e13ee2a303` | `9c0323497b31f8a284456e5692898897b92b6f0817d0371fae9a8afb54c638f8` |
| `net/minecraft/block/Block.java` | `735030e8bb5fc7ead2d6dbcb1b262a36a414b4349daf197456337f460b444cd4` | `1f8ec3628f08e196c2793e91ca0b94feacea6bc3939e7fb770f0682a6e66f2c5` |
| `net/minecraft/block/WallBlock.java` | `aeb67adf292a3b750e72a7d2fdc13f7b42f8633c6183964d00dfe8d6960e07bf` | `033da2cef0973f5d380a81b53ee76e23324fffece72edc74e12fcee0d8764be7` |
| `net/minecraft/block/EndPortalFrameBlock.java` | `c196aeBA3dd6c9683659ab08b71a759b448709e8cc45e68aca448a7e6e470523` | `4346ef0aa80c4d40edc7f33c65a72cc4428190c02af16516d9df5084ed2be4db` |
| `net/minecraft/block/EndGatewayBlock.java` | `f02bf4b06ab7bf2de97dac396f14a1e8d03d32bf386f3ef8676402d6baf05023` | `2bf5e035c7c770617c5156fe49ba120d44e1ce7f6815bf78c96efac16f60bb8c` |
| `net/minecraft/block/EndPortalBlock.java` | `9541e703d04bf9148cb324741de73c4f3ce76021bc358f22308c946bccb72ad3` | `02c5ed627568172fc2dcb0a6b2f3263bebf7a975ff71bcbebe6067124bb6a050` |
| `net/minecraft/block/EndRodBlock.java` | `e7ba3191e3303d94987648d57384bab787226c5a8914cc5f72e92fc2ffb8545d` | `e876070e1ed5bded108d67d9d641e1152e2393bbeee71867a672c8920e17490e` |
| `net/minecraft/block/PortalBlock.java` | `075976960fdb4518ec5ee70443d8bfd175981aeb01b71aec9bc8ad5d957ccee9` | `4e350efb356a684dfb554a83c06c1d138ac075e67cb6a9f9afa72881e17996f5` |
| `net/minecraft/block/shape/VoxelShape.java` | — | `8efb68bf9d5efdb0ec48e401d1852e90458c7094f8fa0c8e9ae418f268c40fdd` |

## Bounded closure

For these five keys, all registered state variants and all four horizontal projections are closed: old wall-bit input is false, and target effective full-face input is false. No provider report was reused; previously audited End Gateway and portal materials compare different endpoints and do not establish this pair's face projection. No conclusion is made for other keys, undiscovered aliases, other wall behavior, or global pair coverage. No movement trajectory, producer, implementation, or runtime claim is made. States/data not established here remain unproven outside this bounded key list.
