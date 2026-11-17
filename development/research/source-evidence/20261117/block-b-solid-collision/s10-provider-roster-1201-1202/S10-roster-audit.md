# 1.20.1–1.20.2 S10 block-shape roster

Source-only Mojmap exact-version roster and partial audit. Frozen by matching the sorted `rg -l 'getCollisionShape|getShape\('` path inventories under each release `net/minecraft/world/level/block/`; both inventories contain 135 identical relative paths. This roster does not imply full audit.

## Audited collision-provider subset (18/135 block-tree source files)

State/neighbor geometry: `SlabBlock.java`, `StairBlock.java`, `DoorBlock.java`, `TrapDoorBlock.java`, `FenceGateBlock.java`, `FenceBlock.java`, `WallBlock.java`, `ButtonBlock.java`, `LeverBlock.java`, `BasePressurePlateBlock.java`.

Context-dependent: `ScaffoldingBlock.java`, `PowderSnowBlock.java`.

Piston provider chain: `piston/PistonBaseBlock.java`, `piston/PistonHeadBlock.java`, `piston/MovingPistonBlock.java`, `piston/PistonMovingBlockEntity.java`.

Provider/cache root: `Block.java`, `state/BlockBehaviour.java`.

## Reviewed shape-only change excluded from player movement (1/135)

`PinkPetalsBlock.java`: 1.20.2 changes only its general `getShape` outline to a four-piece facing/amount-derived box envelope (`singleEncompassing`). The block is registered with `.noCollission()` in both versions, and unchanged `BlockBehaviour.getCollisionShape` returns `Shapes.empty()` when `hasCollision` is false. The player solid-collision consumer therefore cannot use this changed outline. `singleEncompassing` has no other 1.20.2 call site in the decompiled tree.

## Explicit unexamined remainder (116/135)

- `AbstractCauldronBlock.java`
- `AirBlock.java`
- `AmethystClusterBlock.java`
- `AnvilBlock.java`
- `AttachedStemBlock.java`
- `AzaleaBlock.java`
- `BambooSaplingBlock.java`
- `BambooStalkBlock.java`
- `BannerBlock.java`
- `BaseCoralFanBlock.java`
- `BaseCoralPlantBlock.java`
- `BaseCoralPlantTypeBlock.java`
- `BaseCoralWallFanBlock.java`
- `BaseFireBlock.java`
- `BaseRailBlock.java`
- `BedBlock.java`
- `BeehiveBlock.java`
- `BeetrootBlock.java`
- `BellBlock.java`
- `BigDripleafBlock.java`
- `BigDripleafStemBlock.java`
- `BrewingStandBlock.java`
- `BubbleColumnBlock.java`
- `CactusBlock.java`
- `CakeBlock.java`
- `CampfireBlock.java`
- `CandleBlock.java`
- `CandleCakeBlock.java`
- `CarpetBlock.java`
- `CarrotBlock.java`
- `CeilingHangingSignBlock.java`
- `ChainBlock.java`
- `CherryLeavesBlock.java`
- `ChestBlock.java`
- `CocoaBlock.java`
- `ComposterBlock.java`
- `ConduitBlock.java`
- `CoralPlantBlock.java`
- `CropBlock.java`
- `CrossCollisionBlock.java`
- `DaylightDetectorBlock.java`
- `DeadBushBlock.java`
- `DecoratedPotBlock.java`
- `DiodeBlock.java`
- `DirtPathBlock.java`
- `DragonEggBlock.java`
- `EnchantmentTableBlock.java`
- `EnderChestBlock.java`
- `EndPortalBlock.java`
- `EndPortalFrameBlock.java`
- `entity/BeehiveBlockEntity.java`
- `FarmBlock.java`
- `FireBlock.java`
- `FlowerBlock.java`
- `FlowerPotBlock.java`
- `FrogspawnBlock.java`
- `FungusBlock.java`
- `GrindstoneBlock.java`
- `GrowingPlantBlock.java`
- `HangingRootsBlock.java`
- `HoneyBlock.java`
- `HopperBlock.java`
- `LadderBlock.java`
- `LanternBlock.java`
- `LecternBlock.java`
- `LightBlock.java`
- `LiquidBlock.java`
- `MangrovePropaguleBlock.java`
- `MudBlock.java`
- `MultifaceBlock.java`
- `MushroomBlock.java`
- `NetherPortalBlock.java`
- `NetherSproutsBlock.java`
- `NetherWartBlock.java`
- `PiglinWallSkullBlock.java`
- `PipeBlock.java`
- `PitcherCropBlock.java`
- `PointedDripstoneBlock.java`
- `PotatoBlock.java`
- `RedstoneWallTorchBlock.java`
- `RedStoneWireBlock.java`
- `RodBlock.java`
- `RootsBlock.java`
- `SaplingBlock.java`
- `SculkSensorBlock.java`
- `SculkShriekerBlock.java`
- `SeagrassBlock.java`
- `SeaPickleBlock.java`
- `ShulkerBoxBlock.java`
- `SignBlock.java`
- `SkullBlock.java`
- `SmallDripleafBlock.java`
- `SnifferEggBlock.java`
- `SnowLayerBlock.java`
- `SoulSandBlock.java`
- `SporeBlossomBlock.java`
- `StemBlock.java`
- `StonecutterBlock.java`
- `StructureVoidBlock.java`
- `SugarCaneBlock.java`
- `SweetBerryBushBlock.java`
- `TallGrassBlock.java`
- `TallSeagrassBlock.java`
- `TorchBlock.java`
- `TorchflowerCropBlock.java`
- `TripWireBlock.java`
- `TripWireHookBlock.java`
- `TurtleEggBlock.java`
- `VineBlock.java`
- `WallBannerBlock.java`
- `WallHangingSignBlock.java`
- `WallSignBlock.java`
- `WallSkullBlock.java`
- `WallTorchBlock.java`
- `WaterlilyBlock.java`
- `WitherRoseBlock.java`

No player-movement finding was authored: the audited collision-shape operations are source-identical or have unrelated hunks; the sole changed outline is excluded by the block's no-collision flag. Remaining provider files are open coverage gaps and require assigned follow-up.
