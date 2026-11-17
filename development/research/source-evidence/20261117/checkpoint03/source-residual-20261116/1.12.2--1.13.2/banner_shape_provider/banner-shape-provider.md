# BannerBlock collision and shape provider comparison

## Bounded result

For the registered standing and wall banner provider families, the examined collision and outline-shape behavior is equivalent between the two exact source releases, conditional on pairing old standing with new standing and old wall with new wall. Both eras return no block collision for banners. Their non-collision outline boxes have the same dimensions; wall boxes are selected by the horizontal facing state. This establishes no BannerBlock collision/shape movement delta within this bounded family comparison. It does not close the registry-key alias dependency or any other pair coverage.

## Exact source identity

- 1.12.2, Ornithe Feather Gen2 build 2: ready marker `decompiled_minecraft/1.12.2/ornithe-feather.ready.json`, source manifest identity `b8a37ccfccd2aac5f40f5e34fec85873dbdfa93a266043c103e592e4a8c949da`.
- 1.13.2, Ornithe Feather Gen2 build 2: ready marker `decompiled_minecraft/1.13.2/ornithe-feather.ready.json`, source manifest identity `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211`.
- Source family: published Feather mappings on both sides; no online mappings used.

## Provider behavior and inputs

**1.12.2.** `net/minecraft/block/BannerBlock.java` SHA-256 `cd6793dcf6344fe613e1245a299424bd5bd0f4a1e34f3b4ca72f774d6ab5192d`: `BannerBlock.getCollisionShape` (lines 37–41) unconditionally returns `EMPTY_BLOCK_SHAPE`. `Standing.getShape` (115–123) returns the shared box `(0.25, 0, 0.25)–(0.75, 1, 0.75)` and does not read `ROTATION`. `Wall.getShape` (161–194) switches on `FACING` and returns the north/south/west/east boxes, each with height `0.78125` and thickness `0.125`. Its only shape state input is facing; `world` and `pos` are unused. The state definitions establish standing `ROTATION` and wall `FACING` (156–158, 222–225).

**1.13.2.** `net/minecraft/block/Block.java` SHA-256 `735030e8bb5fc7ead2d6dbcb1b262a36a414b4349daf197456337f460b444cd4`: `Block.getCollisionShape` (392–395) returns `state.getShape(world, pos)` only if `hasCollision`; otherwise it returns `VoxelShapes.empty()`. The constructor copies `properties.hasCollision` into `hasCollision` (224–232), and `Properties.noCollision()` clears it (1898–1902, 1943–1945). Registration constructs every standing and wall banner with `.noCollision()` (1444–1543), so the collision provider returns empty for every registered banner state, independent of shape or facing.

`net/minecraft/block/StandingBannerBlock.java` SHA-256 `bc8a64d47d7ed894288c7923f7e6feb1c9c1a0b58ad961c194fc04dbf7793c6d`: `getShape` returns `Block.block(4, 0, 4, 12, 16, 12)` (18–37), the same normalized bounds as 1.12.2. State includes `ROTATION` (19, 23–26, 61–64), but the shape method does not read it. `net/minecraft/block/WallBannerBlock.java` SHA-256 `70d1fb6b45df1bd86b6a7276cab817472c2c974cad31cb7ac041da3224f04296`: the facing-to-shape map (18–31) has north/south/west/east bounds with height `12.5/16 = 0.78125` and thickness `2/16 = 0.125`; `getShape` indexes it by `FACING` (55–58), and the state definition adds only `FACING` (90–93). The geometry and state selector match the old wall provider.

## Registration keys and unresolved join

The 1.12.2 `net/minecraft/block/Block.java` registration rows (1159–1160) register `minecraft:standing_banner` as `BannerBlock.Standing` and `minecraft:wall_banner` as `BannerBlock.Wall` (file SHA-256 `e4a90eca411e7b0e14f5018f7385ec29891f6cdf1a1e917fa648b5712f9b33a1`). In 1.13.2, `Block.java` registers sixteen dye-specific standing keys (`*_banner`) to `StandingBannerBlock` and sixteen dye-specific wall keys (`*_wall_banner`) to `WallBannerBlock` (1444–1543). Thus the provider families and geometry can be compared by mounting subtype, but source evidence here does not establish an exact old-key to new-key alias or dye expansion mapping. The source-only registry crosswalk SHA `bbb1c099dc384489dbb219daa90f797649fc0c8094e5931a68a0c2997f32280a` explicitly marks the old standing and wall keys `UNMATCHED_ALIAS_UNRESOLVED`; that dependency remains open.

## Ordinary player reachability

The operation is on the normal entity movement collision path. `PlayerEntity` extends `LivingEntity`, which extends `Entity` on both sides (1.12.2 declarations at `entity/living/player/PlayerEntity.java:94` and `entity/living/LivingEntity.java:84`; 1.13.2 at lines 98 and 94). In 1.12.2, `Entity.move` calls `World.getCollisions` for its swept player box (`entity/Entity.java:462, 567`); `World.getCollisions` iterates covered block states and calls `BlockState.addCollisions` (`world/World.java:964–1014`); base `Block.addCollisions` obtains `state.getCollisionShape` and adds it only when it is not the empty sentinel (`block/Block.java:372–383`). In 1.13.2, `Entity.move` queries `world.getCollisions` for movement (`entity/Entity.java:473, 578–598`); `World` delegates block shapes through `WorldView.getCollisions` (`world/World.java:1754–1757`), whose `getBlockCollisions` obtains `BlockState.getCollisionShape` and filters empty shapes (`world/WorldView.java:112–151`; `block/state/BlockState.java:209–210`). Banner collision is therefore reachable during ordinary player movement, but the provider contributes no obstacle in either version.

## Dependencies and closure

- **Closed for this packet:** old and new provider method behavior, all provider state inputs affecting the queried shapes, and ordinary entity/player collision reachability.
- **Open:** exact registry-key alias/remap from the two 1.12.2 keys to the 1.13.2 dye-specific keys. The family comparison is conditional on standing-to-standing and wall-to-wall pairing.
- **Not claimed:** complete 1.12.2-to-1.13.2 source coverage, full provider/state/context closure outside banners, independent audit, pair freeze, or runtime parity.
