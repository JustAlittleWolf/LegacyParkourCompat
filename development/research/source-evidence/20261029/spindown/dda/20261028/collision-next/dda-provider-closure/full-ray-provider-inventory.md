# 1.8.9 camera ray provider inventory

## Scope and bounded conclusion

This inventory is limited to the ordinary camera block-pick path in the exact 1.8.9 canonical export. It inventories dynamic `canRayTrace`, `rayTrace`, and `updateShape` methods in `net/minecraft/block`, plus the shared-bound consumer used by movement. It does not claim every mutable writer changes movement for every block state.

The camera path visits the starting cell and subsequent DDA cells in source order. For `Entity.rayTrace(range,tickDelta)`'s `world.rayTrace(from,to,false,false,true)` call, `allowLiquids=false`, `ignoreBlocksWithoutCollision=false`, and `allowHitInStartPosition=true`. Thus the optional collision-shape prefilter is skipped; a state must pass `canRayTrace`; each eligible state dispatches its block's virtual `rayTrace`; and the first non-null result stops the world traversal. `Block.rayTrace` calls virtual `updateShape(world,pos)` before intersecting the current bounds, so a miss can still be a mutable-bound write.

The accepted movement-consumer chain is closed for Anvil and Chest: their `updateShape` methods write shared `Block` bounds; default `Block.getCollisionShape` later reads those fields; entity movement asks `World.getCollisions`, which dispatches `Block.addCollisions` and reaches that getter. The exact source evidence also proves the camera pick is ordered before entity ticking. For every other listed mutable writer, this inventory identifies a possible provider/cache dependency, but does not assert that its state uses the default movement collision getter or that the writer changes a player trajectory. Those provider consumers remain open.

## Caller, gates, and stop semantics

- `net/minecraft/client/Minecraft.java:1303-1314`, `tick`, calls `gameRenderer.pick(1.0F)` before the later `world.tickEntities()` path. `net/minecraft/client/render/GameRenderer.java:318-325`, `pick`, requires a camera entity and world, then calls `entity.rayTrace`.
- `net/minecraft/entity/Entity.java:1029-1034`, `rayTrace`, forms the interpolated eye ray and calls `world.rayTrace(from,to,false,false,true)`.
- `net/minecraft/world/World.java:633-761`, `rayTrace`, probes the start cell, then the DDA cells; for each passing gate it calls `block.rayTrace`. A non-null result immediately returns. The accepted camera/DDA finding records the X-then-Y comparison with Z tie fallback, negative-zero adjustment, 201 post-start candidate cap, and first-hit behavior. This inventory makes no new traversal-boundary claim.
- `net/minecraft/block/Block.java:356-362`, `canRayTrace`, defaults to true. `AirBlock.java:30-32` returns false; `FireBlock.java:297-299` returns false; `LiquidBlock.java:69-71` requires `allowLiquids` and source level 0, so camera rays skip liquids. `StairsBlock.java:442-449` delegates both overloads to its base block. No collision-shape prefilter runs for the camera flags.
- `Block.java:456-516`, default `rayTrace`, invokes virtual `updateShape` before plane intersections and returns null on a miss. `Block.java:596-598`, default `updateShape`, is empty. The world-level early stop is only on a non-null result from the provider override.
- `Block.java:339-350`, default `addCollisions`/`getCollisionShape`, constructs collision geometry from current instance `min/max`; `Entity.java:439,469` requests collision lists during movement and `World.java:891-945` scans block states and dispatches `addCollisions`.

## Virtual provider overrides

### `canRayTrace`

| Class and exact method | Camera consequence |
|---|---|
| `Block.canRayTrace(BlockState,boolean)` and `Block.canRayTrace()` | Default allows the block. |
| `AirBlock.canRayTrace(BlockState,boolean)` | Rejects. |
| `FireBlock.canRayTrace()` | Rejects through the default state overload. |
| `LiquidBlock.canRayTrace(BlockState,boolean)` | Rejects for camera `allowLiquids=false`; with liquids allowed, only level 0 passes. |
| `StairsBlock.canRayTrace()` and `StairsBlock.canRayTrace(BlockState,boolean)` | Delegate to the wrapped base block; a stairs state can inherit that base's ray eligibility. |

### `rayTrace`

| Class and exact method | Provider behavior and order dependency |
|---|---|
| `Block.rayTrace(World,BlockPos,Vec3d,Vec3d)` | Default writer-then-clip path; applies the block's virtual `updateShape`, then clips against the shared bounds. |
| `AbstractRailBlock.rayTrace(...)` (`AbstractRailBlock.java:49-52`) | Calls `updateShape`, then `super.rayTrace`, whose default calls it again before clipping. Inherited by rail subclasses; preserve this dispatch and repeated writer if emulating source order. |
| `DoorBlock.rayTrace(...)` (`DoorBlock.java:207-210`) | Calls `updateShape`, then `super.rayTrace`, which calls it again. |
| `TrapdoorBlock.rayTrace(...)` (`TrapdoorBlock.java:139-142`) | Calls `updateShape`, then `super.rayTrace`, which calls it again. |
| `TorchBlock.rayTrace(...)` (`TorchBlock.java:138-155`) | Selects facing-dependent shared bounds directly, then delegates to default clip. |
| `StairsBlock.rayTrace(...)` (`StairsBlock.java:502-535`) | Reads state facing/half; sets per-instance corner decomposition state; calls `super.rayTrace` for each non-excluded sub-box (each call runs stair `updateShape` then default clip); clears excluded results and returns its selected non-null sub-box result. This provider has internal sub-box iteration before the world-level first-hit stop. |
| `MovingBlock.rayTrace(...)` (`MovingBlock.java:106-108`) | Always returns null and does not delegate to default clipping. Its `updateShape` is therefore not reached through this camera ray override. |

The applicable writer adapter must preserve virtual provider dispatch, including custom ray results, no-hit writes, and the special repeated/sub-box paths above. Replacing every provider with a single current-state box clip would not preserve these semantics.

## `updateShape` shared-bound writers

All entries below override `updateShape(WorldView,BlockPos)` and mutate the block instance's `min/max` bounds, either with `setShape`, direct field assignments, or a same-class helper that does so. For camera-eligible states, the default ray provider invokes the override before clipping. `Block.java:292-298` defines `setShape` as assignment to all six shared fields.

| Class / method start | Writer path (state or neighbor input) |
|---|---|
| `AbstractPressurePlateBlock.java:27` | Delegates to state overload; pressed/unpressed bound helper. |
| `AbstractRailBlock.java:55` | Current rail shape; ascending rails raise maxY. |
| `AnvilBlock.java:67` | Queried `FACING` axis. |
| `BannerBlock.java:159 (nested Wall.updateShape)` | Facing-dependent banner bounds; confirmed writer in the nested wall-banner provider. |
| `BedBlock.java:104` | Delegates to default-shape helper. |
| `ButtonBlock.java:101` | Delegates to state helper; facing and powered state. |
| `CakeBlock.java:29` | Bite count. |
| `CarpetBlock.java:51` | Delegates to metadata helper; fixed thin carpet bounds. |
| `ChestBlock.java:56` | Neighbor arrangement and priority. |
| `CocoaBlock.java:70` | Facing and age. |
| `DaylightDetectorBlock.java:38` | Fixed 3/8-height bounds. |
| `DoublePlantBlock.java:40` | Full-cube bounds. |
| `DoorBlock.java:77` | Delegates to combined-metadata helper; facing/open/hinge/half. |
| `EndPortalBlock.java:31` | Thin lower bounds. |
| `FenceBlock.java:85` | Horizontal neighbor connections. |
| `FenceGateBlock.java:64` | Facing axis. |
| `HopperBlock.java:44` | Full-cube bounds for ray geometry. |
| `LadderBlock.java:38` | Facing. |
| `LeverBlock.java:121` | Attachment/facing. |
| `MovingBlock.java:133` | If a moving block entity supplies a non-air moved state, delegates to that block's `updateShape`, then directly copies/translates its six bounds using progress and facing. The camera `MovingBlock.rayTrace` override above never calls this method. |
| `PaneBlock.java:100` | Neighbor connections. |
| `PistonBaseBlock.java:174` | Extended state. |
| `PistonHeadBlock.java:123` | Delegates to head-shape-for-collisions helper. |
| `PortalBlock.java:57` | Portal axis. |
| `SkullBlock.java:70` | Facing. |
| `SlabBlock.java:39` | Double/single and slab half. |
| `SnowLayerBlock.java:64` | Delegates to layer-count helper. |
| `StairsBlock.java:49` | Uses internal `isCorner`/`stairType` set by its custom ray provider; sets each sub-box bounds. |
| `StemBlock.java:114` | Age-derived maxY and stem bounds. |
| `TrapdoorBlock.java:64` | Delegates to state helper; facing/open/half. |
| `TripwireBlock.java:97` | Attached/suspended state. |
| `TripwireHookBlock.java:205` | Facing. |
| `VineBlock.java:65` | Connected-face state. |
| `WallBlock.java:67` | Neighbor connections; also raises maxY to 1.5. |
| `WallSignBlock.java:19` | Facing. |

`Block.updateShape` itself (`Block.java:596`) is empty. Other helpers above are included because the virtual world-aware override calls them; their bound writes are part of that provider call. Constructor-only initializers are not listed as ray-time writers.

## Movement and adapter closure

- **Accepted movement-consumer closure:** `AnvilBlock.updateShape` and `ChestBlock.updateShape` produce the bounds later read by default `Block.getCollisionShape`; `Entity.move -> World.getCollisions -> Block.addCollisions -> getCollisionShape` consumes them. Their state-specific writer formulas, miss-before-return behavior, and camera-before-movement ordering are accepted by `.native-source/20261028/collision-dependencies/camera-tick-raytrace/camera-tick-raytrace.md` (finding SHA-256 `C928918BABC2C7C748EB2B4A0F62C711961BE226BDB8965917D71B156BB7D728`) and its audit. The DDA correction/audit accepts that provider visits can alter last-writer order and a miss still performs the old write.
- **Additional cache-sensitive source candidate:** `StemBlock.updateShape` changes `maxY` from queried `AGE`; it inherits default `Block.getCollisionShape` and default `Block.addCollisions`, so the movement collision path can read that shared bound without refreshing it. This is a source-traced candidate for provider/consumer closure, not a separately accepted finding here. Other writer classes in this list that inherit the default getter set a state-invariant box; some others override collision collection/getters with null, state geometry, or a movement-time shape refresh. `SnowLayerBlock`'s getter reads shared horizontal bounds, but its writer always sets those to the same full horizontal extent and the getter derives height from the queried state, so this particular cache reuse does not produce an age-like bound difference.
- **Adapter scope still open:** all other mutable writer classes in the inventory require provider/consumer composition before asserting movement relevance. In particular, exact `rayTrace` override behavior must be routed as above; provider presence alone does not establish that the writer's bounds are consumed by that block's movement collision implementation.
- **Moving block exception:** its camera provider returns null, so its `updateShape` is not reached by `World.rayTrace` through that override. Its collision shape is separately custom; do not attribute its writer to ordinary camera visitation without another caller path.
- **Potential order controls:** source DDA ordering, first non-null stop, per-instance block identity, and intervening tick/render picks determine which writes can remain. This inventory does not prove a particular ray or last writer for unbounded endpoints, or the compatibility observer's actual implementation.
- **Modern endpoint:** this note does not inspect or claim modern provider composition. The accepted Anvil/Chest endpoint finding says the 26.2 outline/collision paths for those two providers are state-resolved and do not write these old shared bounds.

## Exact source identity and fingerprints

Read-only canonical source root: `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\1.8.9\ornithe-feather`. The ready marker identifies exact release `1.8.9`, Feather Gen2 build.2 merged-v2, and 1,612 source files. Provenance SHA-256: `BA066FC8F1C7CEB9819D2106338D9E9F2C2B96EB5A8329EB40E171B1F9889185`; ready marker SHA-256 `FE0EC792349AE43C91D0A30F23660C94B2F5C8D31A29643F73ED0DE367110B95`; source-manifest SHA-256 `9E75F46DC0ED43B6A355BD65DB8A92C93A4DFEAECFA92284187C6FE9410D8004`. The following source-file SHA-256 values were recomputed from the canonical export. Paths are relative to `ornithe-feather/net/minecraft/block/` unless a package is shown.

| Source file | SHA-256 |
|---|---|
| `Block.java` | `EA10F05106A3CF7189AEC85236A7ECF9C7106A717F37BED583B5EA4ADEFFA528` |
| `AirBlock.java` | `19626B89ECC4A776B74DEC89C3743D61ABE3CA813C3E178DBFEAC33513824CA7` |
| `FireBlock.java` | `EE65EB7B3256346282E3D17D51D8A92E6ABB5AC08EF02B8A1E533989E056429D` |
| `LiquidBlock.java` | `1537FD6B1BCA65E0F910F9A12BCD3BB27F5A86BE7F13A1CAA380C5E66544F1D5` |
| `AbstractPressurePlateBlock.java` | `85896B070BE577D228E8A660F1F95CAE8956467A957463C4FC839080D2E5B1CB` |
| `AbstractRailBlock.java` | `743E8D1B8ACF24ACA8E297A2906D78B010960DAE21B0BE5A4C803AA20038111D` |
| `AnvilBlock.java` | `7E52C4A86C29548D8DC491FE7D4FF17A79C1DC9F426F538A6C19E161F99F013E` |
| `BannerBlock.java` | `6918850C398314D113F9E7D21E361D19C9AF1FDB74977465CA3223FC3F52DBC4` |
| `BedBlock.java` | `4F66DCF2DC770239FC94AE78BF68C642BD7632E3B1618CE32FD2E63E1F51B1FA` |
| `ButtonBlock.java` | `E79F9805438FC92233E709FEFFA83091FEBDE8DE2A3DAAE8280183EF251E5681` |
| `CakeBlock.java` | `297DEFF656DFF4F9D91913E54AD6179F5AE21C0C7B21C9BCE86DA289E8A882CC` |
| `CarpetBlock.java` | `7B3F5725008EDE42FD23D5FAB0CBDCFC27736CE9C71094BD56CF9D03C2458A01` |
| `ChestBlock.java` | `8072BC317398297D1327A3BE4EDD8A66381A1A1021A6C4A8F3FBEEBCA2891D51` |
| `CocoaBlock.java` | `D4E3C6B53FD33B3FF5E433B333511D93CBB83DBC0BDFB5690EFBEF734C5542E9` |
| `DaylightDetectorBlock.java` | `2810C80B4EDFEF9D50AC5193161550DA4B25A98E05DD7E7DF5F8C2FAA1F9E822` |
| `DoublePlantBlock.java` | `F48EC69BAFF5EEA4BC6A28D456DF525410DA43642B5AEF952E6574E9C8D1B49E` |
| `DoorBlock.java` | `51B93FCB01758E1B809A503A9E63AAB6D781DBD9A0E4D3B95823168E8958128D` |
| `EndPortalBlock.java` | `1CB419F44592E68244E06B34799C6354A2BCC3D08BD849E0709FCA36B54E1E40` |
| `FenceBlock.java` | `8D6A803988D77FC51F364520F2E0CD5567B1D5C29A4D28D01CDCF93E6D5890D9` |
| `FenceGateBlock.java` | `43749004A2826051C627C794F72DB705283DF26E1810D8B4A7A78205AC974D2C` |
| `HopperBlock.java` | `A7DD1EF92B4729A98877CF800E6D728BFAB0EF32E58B1EF575A84809E465BE1F` |
| `LadderBlock.java` | `C13B123F8FD7427D7F646DA3B789EB97D4E54AA2FE2860F66CF4FD1302865106` |
| `LeverBlock.java` | `D8359D88616D0BD09462F90E98D5F4BDB76BF30B991CE841FAF068424F0B5881` |
| `MovingBlock.java` | `700010B071738738F04E0508050590D8D625F01115FC4886FEF6E262F85C6E85` |
| `PaneBlock.java` | `F099CDC306C365B73632FC703F72B61BC6B304CB5DF06C43234965F89E28DE61` |
| `PistonBaseBlock.java` | `3C96698A674714446F9FD0D6CC1C4D5EB72937D9A2F396516DDB2461C3EA4929` |
| `PistonHeadBlock.java` | `C5515744CE27521195EC802E2BD89AE5327448302542436576BCACF19F245FA4` |
| `PortalBlock.java` | `BB9D2CBA26DF71C8383408FCCCC93B849EEBECD3D36924A63EE80CBA8734867E` |
| `SkullBlock.java` | `D5F90D6A355B261127FF6BFAA36ABD64A4C5982CAE75641DF2FC970B217299AC` |
| `SlabBlock.java` | `35BE26D8419F707CA36F1B3FC38676624193B5635250C57347CD2CD6D8357004` |
| `SnowLayerBlock.java` | `D922F1F9A8D6F543959835E8B21E800ECF5CD9C7C60816B9152D873CA132A3ED` |
| `StairsBlock.java` | `FADC964480227F6FFD5756471212B18D0172BFFBCD0866EE0C278454AB079409` |
| `StemBlock.java` | `006B3C179E5BB22720CBC729FF1B1BCD63B700D88D34099F93D6F7BB1D134CC2` |
| `TrapdoorBlock.java` | `E00C4FDFA234AC5CF265816FBBE088709668CD5A44EB537A1F391CE2F5F795BA` |
| `TripwireBlock.java` | `CAAD055A848650E80F73074B758222CFB72D9B928C38769A29038C5D11764BE7` |
| `TripwireHookBlock.java` | `616C122A5CF5458C667188B04614C72488F298000C95835A5C4D239B39C6A67A` |
| `VineBlock.java` | `C5052B5D6B70598C06ECB351FE2E4FCF5D2674BDA6FB2AD1E9D0BF4AEAB816B6` |
| `WallBlock.java` | `E1FCA4E4DBB7736D17116E4DEE92064D35F7BF135DA374DAE0615E89216ACA5E` |
| `WallSignBlock.java` | `5FA003C5CA40BFA2C901E35F8FA21FDE096264038C8150780A2E49F8F24E97B2` |
| `TorchBlock.java` | `20AC458631CDF68B24E739AC100616BB024EC2200262A4DE66AD5C59E7967A5B` |
| `net/minecraft/entity/Entity.java` | `D4C10932CB5BB1067A5A58BE4E1BB1B42BDDE3B6FC893D88178CC07435A1696B` |
| `net/minecraft/client/Minecraft.java` | `1181BB145EB52ECBA28FCE3DB96FCA1E639C85012472D96EC0BA71BD02E15020` |
| `net/minecraft/client/render/GameRenderer.java` | `69B0007074901E5BD617F35039F122F21C67402340FE08B3DAEA1B316C6E7940` |
| `net/minecraft/world/World.java` | `3C04F5B874FBB6692039164C882A3C47FE28C7ECA864E91C9AC57798153888EE` |

## Inputs and limits

This inventory used the canonical read-only 1.8.9 source export and the accepted source-only camera/DDA finding, correction, and audit under `.native-source/20261028/collision-dependencies/camera-tick-raytrace/` and `.native-source/20261028/collision-next/dda-provider-closure/`. No product implementation, product history, backlog, wiki material, other source release, tests, build, game/runtime, or source generation was used. Modern provider semantics and the non-Anvil/Chest movement consumer closure remain unexamined here.
