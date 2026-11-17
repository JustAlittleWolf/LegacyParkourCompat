# Wall projection: sign, banner, and head families

## Bounded inputs

This source-only report closes only the sign/banner/floor-head/wall-head family named in `wall_remaining_face_shape_census/report.md` (SHA-256 `f1a12c7564f14c91c652bea6603e8ada8350b98c2892df05c591c0161d4e041d`). Endpoints are Minecraft 1.13.2 and 1.14.4, Ornithe Feather Gen2. Read-only endpoint source manifests match the assigned identities: 1.13.2 `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211`; 1.14.4 `717b468536348557f9dae8cf57edccf80ad16eb65e5bef6c60bdb5ea3c9f75bc`. Every source file listed below was rehashed and its entry matched the corresponding endpoint manifest.

Compared operation: the old wall generic-arm predicate (`WallBlock.shouldConnectTo`) requires the neighbor's directional `FaceShape` to be `SOLID`, after its exception gate; the target generic-arm predicate uses `!Block.isExceptionForAttachment(block) && Block.isFaceSolid(state, world, pos, face)`. The reused census/provider reports are not used to infer any result here.

## Key and class roster

**Signs:** exact IDs are not common across the endpoints. 1.13.2 registers `sign` as `StandingSignBlock` and `wall_sign` as `WallSignBlock` (`Block.java:1003,1008`). 1.14.4 replaces these IDs with six standing IDs and six wall IDs, each registered to the corresponding `StandingSignBlock` / `WallSignBlock` (`Blocks.java:429–488`):

- Standing: `oak_sign`, `spruce_sign`, `birch_sign`, `acacia_sign`, `jungle_sign`, `dark_oak_sign`.
- Wall: `oak_wall_sign`, `spruce_wall_sign`, `birch_wall_sign`, `acacia_wall_sign`, `jungle_wall_sign`, `dark_oak_wall_sign`.

The semantic predecessor crosswalk is `sign` → `oak_sign` and `wall_sign` → `oak_wall_sign`; the other five target wood variants per orientation have no exact old key. The projection below covers the predecessor crosswalk and confirms the same class-level result for the target wood variants, without treating their IDs as common keys.

**Banners:** all 32 IDs are common and registered to `StandingBannerBlock` (16 floor IDs) or `WallBannerBlock` (16 wall IDs) at both endpoints (`Block.java:1444–1542`; `Blocks.java:1178–1295`). For each orientation, the exact color suffix set is `white`, `orange`, `magenta`, `light_blue`, `yellow`, `lime`, `pink`, `gray`, `light_gray`, `cyan`, `purple`, `blue`, `brown`, `green`, `red`, `black`. IDs are each `<color>_banner` and `<color>_wall_banner` respectively.

**Heads/skulls:** all 12 IDs are common at both endpoints (`Block.java:1272–1283`; `Blocks.java:887–920`). The exact floor IDs/classes are `skeleton_skull` / `FloorSkullBlock`, `wither_skeleton_skull` / `WitherFloorSkullBlock`, `zombie_head` / `FloorSkullBlock`, `player_head` / `PlayerFloorSkullBlock`, `creeper_head` / `FloorSkullBlock`, `dragon_head` / `FloorSkullBlock`. The wall IDs/classes are the respective `skeleton_wall_skull` / `WallSkullBlock`, `wither_skeleton_wall_skull` / `WitherWallSkullBlock`, `zombie_wall_head` / `WallSkullBlock`, `player_wall_head` / `PlayerWallSkullBlock`, `creeper_wall_head` / `WallSkullBlock`, `dragon_wall_head` / `WallSkullBlock`. Type is fixed by the registered block instance (`FloorSkullBlock.Types`: skeleton, wither skeleton, zombie, player, creeper, dragon); it is not a state property.

## State domains and projection results

For each key in the roster, the old `SignBlock`, `BannerBlock`, or `SkullBlock` overrides `getFaceShape` to return `FaceShape.UNDEFINED` (`SignBlock.java:74–75`, `BannerBlock.java:48–49`, `SkullBlock.java:52–53`). No member in these families is an old connection exception, pole, or fence gate. Therefore the old generic wall bit is **false for north, east, south, and west in every legal state**.

| Family / state domain | Old effective horizontal result | Target collision shape / full-face result | Wall-bit verdict |
|---|---|---|---|
| Standing sign predecessor (`sign`) / target sign IDs: `ROTATION=0..15`; old and target also permit `WATERLOGGED=false/true` | `UNDEFINED`, hence false in all 16 × 2 states and all four faces. | Every target sign registration uses `.noCollision()`. `Block.getCollisionShape` therefore returns `VoxelShapes.empty()` before consulting the state outline; `Block.isFaceSolid` is false for all rotations, fluid bits, and faces. | **No difference** for semantic predecessor `sign` → `oak_sign`; target-only wood IDs use the same no-collision class path. |
| Wall sign predecessor (`wall_sign`) / target wall-sign IDs: `FACING=N/E/S/W × WATERLOGGED=false/true` | `UNDEFINED`, hence false in all 4 × 2 states and all four faces. | Every target wall-sign registration uses `.noCollision()`; collision shape is empty regardless of `FACING`, `WATERLOGGED`, world, or context. Target predicate false. | **No difference** for semantic predecessor `wall_sign` → `oak_wall_sign`; target-only wood IDs use the same path. |
| Standing banners, 16 color IDs: `ROTATION=0..15` | `UNDEFINED`, hence false for all 16 rotations and all four faces. | Every target standing banner registration uses `.noCollision()`; empty collision shape, so no full face. | **No difference** for all 16 common keys / 64 directional-state projections. |
| Wall banners, 16 color IDs: `FACING=N/E/S/W` | `UNDEFINED`, hence false for all four facings and all four projected neighbor directions. | Every target wall banner registration uses `.noCollision()`; empty collision shape, so no full face. | **No difference** for all 16 common keys / 16 directional-state projections. |
| Floor heads/skulls, six keys/types: `ROTATION=0..15` | `UNDEFINED`, hence false for all rotations and four faces. | Target `FloorSkullBlock.getShape` is the fixed `Block.block(4,0,4,12,8,12)` prism. Its horizontal face projections are inset and cannot be a full 16×16 face; target full-face predicate false for all directions. | **No difference** for all six common keys / 96 directional-state projections. |
| Wall heads/skulls, six keys/types: `FACING=N/E/S/W` | `UNDEFINED`, hence false for all facings and four faces. | Target `WallSkullBlock.getShape` is fixed by facing. Its prism has a 4..12 vertical span and 4..12 inset span along the wall, so even the touching horizontal side projects only an 8×8 area; the other horizontal sides do not fill a face. Target full-face predicate false for all four directions/facings. | **No difference** for all six common keys / 24 directional-state projections. |

In 1.14.4 `Block.isFaceSolid` delegates to `state.getCollisionShape(world,pos)` and `Block.isFaceFull` tests the directional projection against a full block (`Block.java:376–378,402–410`). For signs and banners the `.noCollision()` property makes the collision shape empty. For heads, collision defaults to `state.getSHape(world,pos)`; the floor/wall skull shape overrides are fixed constants selected only by `FACING` on wall skulls. `CollisionContext` is not read by these shape methods. Although all of these blocks have block entities, this collision projection does not read block-entity state or data; player-head profile and banner pattern data cannot alter the result. Wall placements expose four horizontal `FACING` values: sign/banner survival checks require a solid supporting neighbor opposite `FACING`; skull placement selects a horizontal direction whose offset neighbor is not replaceable. This placement eligibility does not narrow the state-space verdict once a legal state exists.

## Evidence binding and limits

Manifest-verified source SHA-256 values (path relative to each version's `ornithe-feather` root):

| File | 1.13.2 | 1.14.4 |
|---|---|---|
| `net/minecraft/block/Block.java` (old registrations / old face default) | `735030e8bb5fc7ead2d6dbcb1b262a36a414b4349daf197456337f460b444cd4` | `1f8ec3628f08e196c2793e91ca0b94feacea6bc3939e7fb770f0682a6e66f2c5` |
| `net/minecraft/block/WallBlock.java` (old and target wall-bit predicates) | `aeb67adf292a3b750e72a7d2fdc13f7b42f8633c6183964d00dfe8d6960e07bf` | `033da2cef0973f5d380a81b53ee76e23324fffece72edc74e12fcee0d8764be7` |
| `net/minecraft/block/Blocks.java` (target registrations) | — | `9c0323497b31f8a284456e5692898897b92b6f0817d0371fae9a8afb54c638f8` |
| `net/minecraft/block/SignBlock.java` | `64d61e8c4758d6f1d13d92e56a25f7599736e625fcfaa980fcb1f8bebfe5ad39` | `0094a15d80c84593ebff08e68f0f0d5b9983ead9e289940b1dbdde734929c51c` |
| `net/minecraft/block/StandingSignBlock.java` | `b05236bb5ce89e48cfaede3b20c6a71b21c8a00eae5fd975d98962455b0446a8` | `b05236bb5ce89e48cfaede3b20c6a71b21c8a00eae5fd975d98962455b0446a8` |
| `net/minecraft/block/WallSignBlock.java` | `d2040a687cb404d5c8679f79f65491ff829d3d83ef0c555155c0b41748d97b9b` | `627ac5d3b3eff0ce9bce86ea8e0df288ade8b1b95348d6a8c746b8d59359d566` |
| `net/minecraft/block/BannerBlock.java` | `21384a99f500bbedcac1e36e4639cb27bf7a7bdeb4c349661b13137facf18e07` | `dcdfec684cf0a929a1acfad2597f99f52f2cbe26415b2acd25309e215c2b127b` |
| `net/minecraft/block/StandingBannerBlock.java` | `bc8a64d47d7ed894288c7923f7e6feb1c9c1a0b58ad961c194fc04dbf7793c6d` | `b5631687ac64a18de1973d56a8f67c6446718b38674966b68f8ef74323d1ca31` |
| `net/minecraft/block/WallBannerBlock.java` | `70d1fb6b45df1bd86b6a7276cab817472c2c974cad31cb7ac041da3224f04296` | `78ab22665aaf9c4700d20b885cc2a640915bf8e59fe98ee58fba7d2d5d0b6930` |
| `net/minecraft/block/SkullBlock.java` | `cb23466df16370da0f1e708210af9bca0f42e40270afa9d7eef21abc3254ac0c` | — |
| `net/minecraft/block/FloorSkullBlock.java` | `a23ac417451ead612e2a34185700c0631bdf75f7c6b6f7e1639b4624b8b12fc7` | `017f758d85d37d7d4bc86506e2c3e03fd7ff4f2027e9e8fa3d1b8abe8e9060a8` |
| `net/minecraft/block/WallSkullBlock.java` | `d7ab74c4f259b2a6c742580a381f1f87a49862512d9324562f4b01679ef988dd` | `e5cedfc3f01dac16915c82f88bb620da894562f9994c25a6f84aae223975f179` |
| `net/minecraft/block/PlayerFloorSkullBlock.java` | `570ebc51290361eb011d6acc263440c64cfeaa84bc8e173a81f250cc60da97cc` | `570ebc51290361eb011d6acc263440c64cfeaa84bc8e173a81f250cc60da97cc` |
| `net/minecraft/block/PlayerWallSkullBlock.java` | `ac754abc6f248b33580b68b1945e0a86d8b660954495f84c66ff9bb937593e87` | `4a2834a3bfd100ca76e24c65b94e3741aa67f5b70d81b47b619daf5e9c34e5a9` |
| `net/minecraft/block/WitherFloorSkullBlock.java` | `9cbd5eefa9d783c22280c7f2460925d4d607af53d3b1afddb684c9170d6eb4f4` | `e68d278ebad32cef761e7dc72aa12b79f7c8860bd2487b89729420191a40f59f` |
| `net/minecraft/block/WitherWallSkullBlock.java` | `0107bb72c51e7236cee8b856f7feb7fdf449f6e4f31f7254a0ae9b94af386d9c` | `0107bb72c51e7236cee8b856f7feb7fdf449f6e4f31f7254a0ae9b94af386d9c` |
| `net/minecraft/block/state/BlockState.java` | `0496fda381628e90b3b3ff8ae376a5bae24cbd662366f34d70991252fab2fe70` | `6c6703c7c2f7203a7507d8a621bc83e1dad613b32089394f5af4fbd8636b165f` |

Old key registration facts are in 1.13.2 `Block.java:1003,1008,1272–1283,1444–1542`; target registration facts are in 1.14.4 `Blocks.java:429–488,887–920,1178–1295`. Old effective face values come from the three base-class overrides above. Target collision facts come from `Block.java:260–267,371–378,402–410`, `SignBlock.java:24,40`, `StandingBannerBlock.java:23,37–38`, `WallBannerBlock.java:22,58–59`, `FloorSkullBlock.java:16,24–25`, and `WallSkullBlock.java:18,42–43` plus the registration property chains.

Result: **no wall-bit difference is established for this bounded family.** This report closes neither other neighbor classes nor any pair-level inventory, dependencies, audit, movement finding, or freeze. No provider geometry acceptance is duplicated. No wiki, product implementation, backlog, Git history, tests, build, game, network access, source generation, or shared-source writes were used.


