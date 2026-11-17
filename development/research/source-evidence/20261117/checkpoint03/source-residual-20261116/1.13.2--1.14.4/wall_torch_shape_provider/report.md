# Wall torch direct shape provider — bounded source slice

## Inputs and source identity

Pair: Minecraft 1.13.2 → 1.14.4, Ornithe Feather mappings on both sides. The exact source-manifest SHA-256 values match the assignment: 1.13.2 `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211`; 1.14.4 `717b468536348557f9dae8cf57edccf80ad16eb65e5bef6c60bdbb5ea3c9f75bc`. Each provenance manifest names the exact version and Feather build. Each verification record says `verified-identical-source-and-input-artifacts`, zero source differences, and repeats the relevant source-manifest identity.

Relevant decompiled source SHA-256 values:

- 1.13.2 `WallTorchBlock.java`: `f06b2a546ba8465627511969efc1203e654a1af3ccf61d27aed9b28b00c77462`
- 1.13.2 `RedstoneWallTorchBlock.java`: `14551c617a070a3591bfc3fac571ade37bd7f2324c6360e3936ee792a8f9d8da`
- 1.13.2 `Block.java` (registry, base shape and collision behavior): `735030e8bb5fc7ead2d6dbcb1b262a36a414b4349daf197456337f460b444cd4`
- 1.13.2 `BlockState.java`: `0496fda381628e90b3b3ff8ae376a5bae24cbd662366f34d70991252fab2fe70`
- 1.13.2 `Entity.java`: `1d6ec8b80f74635401745c2c027bf36555c85348ca5693764f2668363b17d269`
- 1.14.4 `WallTorchBlock.java`: `ff6966b0f5236251c86776c156f5aa57fa214ef50a0a78509a126d916b92db5f`
- 1.14.4 `RedstoneWallTorchBlock.java`: `237ba36f03e040c4ff1531d46887bef719698fe07d580f375947cdf7c4243c16`
- 1.14.4 `Blocks.java` (registry): `9c0323497b31f8a284456e5692898897b92b6f0817d0371fae9a8afb54c638f8`
- 1.14.4 `Block.java` (base shape and collision behavior): `1f8ec3628f08e196c2793e91ca0b94feacea6bc3939e7fb770f0682a6e66f2c5`
- 1.14.4 `BlockState.java`: `6c6703c7c2f7203a7507d8a621bc83e1dad613b32089394f5af4fbd8636b165f`
- 1.14.4 `Entity.java`: `7315a496c195da767de9d4936d3adb6efc3c419dc0f0e95d6f32781b0da1ba55`
- 1.14.4 `WorldView.java`: `fcdf8dc98f97781f4639c4b8cda8a238edf834f3963e401e6eeef6cb475c4909`

## Compared owner and registrations

1.13.2 `net/minecraft/block/Block.java:987` registers key `wall_torch` with `new WallTorchBlock(...)`; line 1064 registers `redstone_wall_torch` with `new RedstoneWallTorchBlock(...)`. Both property chains call `noCollision()`. The old `WallTorchBlock.java:47-48` returns the `SHAPES` entry selected by `state.get(FACING)`. Old `RedstoneWallTorchBlock.java:34-35` delegates its outline shape to `Blocks.WALL_TORCH.getShape(state, world, pos)`.

1.14.4 `net/minecraft/block/Blocks.java:397-399` registers `WALL_TORCH` under `wall_torch` with `new WallTorchBlock(...)` and `noCollision()`; lines 549-553 register `redstone_wall_torch` with `new RedstoneWallTorchBlock(...)` and `noCollision()`. `WallTorchBlock.java:48-53` returns the same facing-selected `SHAPES` entry through `m_68832158`; unlike the old indirection, target `RedstoneWallTorchBlock.java:35-36` calls `WallTorchBlock.m_68832158(state)` directly. Thus the same concrete owner/key pairing is source-confirmed on both sides; this conclusion is not inferred from class names alone.

## Direct geometry/provider comparison

Both `WallTorchBlock.java` files define the same four `Block.block` boxes (coordinates are sixteenths of a block):

- NORTH: `(5.5, 3, 11)` to `(10.5, 13, 16)`
- SOUTH: `(5.5, 3, 0)` to `(10.5, 13, 5)`
- WEST: `(11, 3, 5.5)` to `(16, 13, 10.5)`
- EAST: `(0, 3, 5.5)` to `(5, 13, 10.5)`

Both declare `FACING = HorizontalFacingBlock.FACING`, default it to NORTH, and look up the same facing-indexed shape map. In 1.14.4 the new `CollisionContext` parameter is ignored by the override; the helper performs the same map lookup. Redstone wall torches use that same geometry in both versions. No state-dependent geometry delta was found in this slice.

## Ordinary player collision reachability

In 1.13.2, `Entity.java:580` supplies block collisions to movement through `world.getCollisions(...)`; the base `Block.getCollisionShape` at `Block.java:393-394` returns `VoxelShapes.empty()` when `hasCollision` is false. The wall torch registrations set that flag false through `Properties.noCollision()` (`Block.java:1943`).

In 1.14.4, `Entity.java:464,656` routes movement through the collision resolver; `WorldView.java:133-172` gathers block collisions by calling `BlockState.getCollisionShape(..., collisionContext)`. `Block.getCollisionShape` at `Block.java:376-377` likewise returns `VoxelShapes.empty()` when `hasCollision` is false, and `Properties.noCollision()` sets that flag false at line 853. The state’s nonempty outline `getShape` therefore does not become an ordinary movement obstacle for either registered wall torch in either release.

## Bounded verdict and dependencies

Verdict: no source-confirmed movement difference in the direct wall-torch shape provider, facing geometry, direct wall-torch registry wiring, or ordinary-player collision reachability between the two exact endpoints. This slice yields no movement finding. This does not establish complete pair coverage, any intermediate patch boundary, or parity for other collision owners.

Dependencies checked for this claim: paired `WallTorchBlock` and `RedstoneWallTorchBlock`; exact old and target registry sites; `Block.Properties.noCollision`; base `Block.getCollisionShape`; `BlockState` collision forwarding/cache behavior; ordinary entity movement collision path (old `Entity` → `World.getCollisions`; target `Entity` → `WorldView.getBlockCollisions`). Placement/attachment and other torch behavior were outside this assignment.

