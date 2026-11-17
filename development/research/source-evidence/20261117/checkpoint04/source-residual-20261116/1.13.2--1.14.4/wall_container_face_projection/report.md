# Wall face projection: residual container and utility keys

## Scope and evidence identity

This source-only projection closes only the common keys assigned from `wall_remaining_face_shape_census/report.md` (SHA-256 `f1a12c7564f14c91c652bea6603e8ada8350b98c2892df05c591c0161d4e041d`): `chest`, `trapped_chest`, `ender_chest`, `hopper`, `anvil`, `chipped_anvil`, `damaged_anvil`, `daylight_detector`, and `enchanting_table`. It compares each old effective horizontal `FaceShape` and wall connection exception against the target collision shape and `isFaceSolid` in north, east, south, and west. It is not a global registration census or pair freeze.

Both exact source sets are Ornithe Feather Gen2 build 2. The 1.13.2 manifest `decompiled_minecraft/1.13.2/ornithe-feather.sources.sha256` hashes to `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211`; the 1.14.4 manifest `decompiled_minecraft/1.14.4/ornithe-feather.sources.sha256` hashes to `717b468536348557f9dae8cf57edccf80ad16eb65e5bef6c60bdb5ea3c9f75bc`. These match the ready records. Every source file in the hash table below was checked against its endpoint manifest entry.

## Predicate comparison

At 1.13.2, `WallBlock.shouldConnectTo` accepts a generic neighbor only when the block is not `isExceptionForConnection` and its queried side is `FaceShape.SOLID`; only separate pole / fence-gate arms bypass that condition (`WallBlock.java:51–69`). Each queried horizontal side in this assignment is `UNDEFINED`, so the generic arm is false regardless of exception membership. Chest and anvil explicitly return `UNDEFINED`; Ender Chest does too; Hopper returns `BOWL` only for UP and `UNDEFINED` otherwise; daylight detector and enchanting table return `SOLID` only for DOWN and `UNDEFINED` otherwise (`ChestBlock.java:318–320`, `AnvilBlock.java:50–52`, `EnderChestBlock.java:140–142`, `HopperBlock.java:205–207`, `DaylightDetectorBlock.java:104–106`, `EnchantingTableBlock.java:108–110`). None qualifies for the separate fence-gate/pole arm.

At 1.14.4, the generic wall arm uses `!Block.isExceptionForAttachment(block) && Block.isFaceSolid(state, world, pos, direction)` (`WallBlock.java:45–52`). `isFaceSolid` rejects leaf-tagged blocks and otherwise tests `isFaceFull` on the state's collision shape (`Block.java:260–267, 402–409`). Every assigned target shape fails full-face coverage:

- Chest and trapped chest all end at 14/16 height. Paired shapes vary by `TYPE` and connected direction but keep that height. Pairing/block entity does not fill the gap; the chest provider report closes the selector and context claim.
- Ender chest has a fixed box inset to 1..15 horizontally and ending at 14/16 height; facing and waterlogged state do not affect it.
- Hopper's facing-selected body/outlet leaves parts of each horizontal face uncovered; its outlet occupies only a 4/16-wide strip. `ENABLED` and block entity do not affect the shape.
- Anvil's orientation-selected base, legs and top leave vertical gaps or partial widths on each horizontal projection.
- Daylight detector is a full-footprint 6/16-high slab; enchanting table is a full-footprint 12/16-high shape. Neither covers the complete vertical projection of any horizontal face.

Thus the old exception clause cannot change the outcome: old queries fail `SOLID`, while target queries fail `isFaceFull`. These are source-derived wall-bit results, not measured player trajectories.

## Exact key/class/state crosswalk and outcomes

All declared states are covered. Directions are the neighbor faces queried by the wall.

| Common key(s) | Joined class and declared state domain | 1.13.2 | 1.14.4 | N / E / S / W |
|---|---|---|---|---|
| `chest`, `trapped_chest` | `ChestBlock` / subclass `TrappedChestBlock`; `FACING` 4 × `TYPE` SINGLE/LEFT/RIGHT × `WATERLOGGED` 2 = 24 states per key | Horizontal `UNDEFINED`; false | State-selected shape, all variants 14/16 high; `isFaceSolid=false` | false → false each |
| `ender_chest` | `EnderChestBlock`; `FACING` 4 × `WATERLOGGED` 2 = 8 | `UNDEFINED`; false | Fixed inset box; `isFaceSolid=false` | false → false each |
| `hopper` | `HopperBlock`; `FACING` DOWN/NORTH/EAST/SOUTH/WEST × `ENABLED` 2 = 10 | `UNDEFINED`; false | Facing-selected union, no full horizontal face | false → false each |
| `anvil`, `chipped_anvil`, `damaged_anvil` | Each key constructs `AnvilBlock`; `FACING` 4 = 4 per key | `UNDEFINED`; false | Axis-selected union, no full horizontal face | false → false each |
| `daylight_detector` | `DaylightDetectorBlock`; `POWER` 0..15 × `INVERTED` 2 = 32 | Horizontal `UNDEFINED`; false | Constant 16 × 6 × 16/16 slab; `isFaceSolid=false` | false → false each |
| `enchanting_table` | `EnchantingTableBlock`; no declared properties = 1 | Horizontal `UNDEFINED`; false | Constant 16 × 12 × 16/16 shape; `isFaceSolid=false` | false → false each |

Registration was joined from 1.13.2 `Block.java` registrations plus `Blocks.java` aliases, and 1.14.4 `Blocks.java`: chest/trapped chest use `ChestBlock`/its subclass; the other keys construct their named providers, with all three anvil keys constructing `AnvilBlock`. These definitions retain collision (no `.noCollision()` on assigned rows). The default collision route delegates to each state shape (`Block.java:393–394` old; `:376–377` target).

## Reused bounded provider claims

Reused only for their stated provider claims, not as wall-predicate evidence:

- `chest_shape_provider/report.md`: equal boxes and selector; `TYPE` adjacency/pairing; shape getter's block-entity independence.
- `ender_chest_shape_provider/report.md`: equal fixed box, state inputs and registration identity.
- `hopper_shape_provider/coverage.md`: equal facing-selected geometry, facing/enabled domain and block-entity independence.

Anvil, daylight detector, enchanting table, and this wall projection were inspected directly on both endpoints.

## Source hashes

Exact source-byte SHA-256; each matches its source manifest.

| Source file | 1.13.2 | 1.14.4 |
|---|---|---|
| `net/minecraft/block/Block.java` | `735030e8bb5fc7ead2d6dbcb1b262a36a414b4349daf197456337f460b444cd4` | `1f8ec3628f08e196c2793e91ca0b94feacea6bc3939e7fb770f0682a6e66f2c5` |
| `net/minecraft/block/WallBlock.java` | `aeb67adf292a3b750e72a7d2fdc13f7b42f8633c6183964d00dfe8d6960e07bf` | `033da2cef0973f5d380a81b53ee76e23324fffece72edc74e12fcee0d8764be7` |
| `net/minecraft/block/Blocks.java` | `3e128c9a4f6b53e1037fb85269c4d00bc85112ae52fe48f9534232e13ee2a303` | `9c0323497b31f8a284456e5692898897b92b6f0817d0371fae9a8afb54c638f8` |
| `net/minecraft/block/ChestBlock.java` | `483ed7dc673f8acb33fbac6d5d883c652db05de2314931c522e0bee482792541` | `0103ffd23c63903f57efb76de25caae999eb06fc840f363afaaa4fcb6b54a5e5` |
| `net/minecraft/block/TrappedChestBlock.java` | `ce2db33ed4b4e5dec67d757ad0211b78c9f92d406bee833485961a3a3ff611e8` | `ce2db33ed4b4e5dec67d757ad0211b78c9f92d406bee833485961a3a3ff611e8` |
| `net/minecraft/block/EnderChestBlock.java` | `12642f9af345dd70a0a1787af22c48f7ce9ae0f0c2ded1ae0fe6ba009bbff431` | `7c2ff5ddb905e1c3bd2a44b06d1579a6c532e7fa3598fa39c212f0c81601e010` |
| `net/minecraft/block/HopperBlock.java` | `c43588fd0bdf0c17ec7356090e67af8101335b5ee2a565088837d6d75a3a73ae` | `179c954e0c484ec1c613c9273b679725f5f352081a141540c31d8b20d6b03859` |
| `net/minecraft/block/AnvilBlock.java` | `156505373606a0cf2c0df329e170465c207b72b2ab59798272cbec389373092e` | `5edacda442a01ef963826463c4d5918eb699697e47eec4a073aed54c65fb918c` |
| `net/minecraft/block/DaylightDetectorBlock.java` | `9abc985cb0fd300cc26edf1a0ec0343911354e3ba4b59d0e823531274e23c49a` | `e7be527301144649bfb10929304e624d9cf9697af521a5fd8e416f15078c94a5` |
| `net/minecraft/block/EnchantingTableBlock.java` | `db240cb9087fa871f964df7ded66581c567b41240ecd8d517b02295329a3f69d` | `ef5fe857df45dc69c7ef67f75d5f397c92bc312cf1a5c5e3b8dfcae5e2739c88` |

## Bounded result and residuals

No wall-neighbor bit difference was found for the nine requested keys, their declared states, and four horizontal directions: every old result is false from `FaceShape.UNDEFINED`, and every target result is false from an incomplete collision face. Chest pairing preserves the 14/16 height ceiling. No finding is proposed for this slice.

Only the assigned keys are closed. Other keys, inherited subclasses, and the remaining state/registration census in `wall_remaining_face_shape_census/report.md` stay open. This endpoint comparison does not establish an intermediate patch boundary, complete wall crosswalk, independent audit, or pair freeze.
