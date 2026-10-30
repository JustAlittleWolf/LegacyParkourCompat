# 1.11.2 → 1.12.2 block and provider matrix

## Inputs and integrity

- Pair: Minecraft 1.11.2 → 1.12.2; Ornithe Feather Gen2 build 2; Vineflower 1.12.0.
- Canonical source roots (read only): `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\1.11.2\ornithe-feather` and corresponding `1.12.2` root.
- Scope: every matrix path under `net/minecraft/block/**`; provider siblings `world/World.java`, `world/WorldView.java`, `world/WorldRegion.java`, `world/chunk/WorldChunk.java`. The block subtree includes block entities, state, fluid, collision, and piston classes.
- Procedure: `development/workflows/discovery/WORKFLOW.md` and `development/workflows/discovery/source/WORKFLOW.md`.
- Both ready metadata and provenance records identify the expected release, Feather mapping, and successful decompile. Recomputed SHA-256 for all 473 present owned files matches both the per-path matrix and the corresponding source-manifest entry; no mismatch. Absent-side entries match added status. The supplied inventory states all 3,971 source files were checked against their manifests and source counts/byte totals match ready metadata.
- 1.11.2 manifest SHA-256: `d908c2af76598ad26999563bfef0089f1d3c8c32483eee6b108f0defa2773df0`; provenance: `dcdb65e1361297a584c5b93f581b1b539911911c6cec1a203740baa5379732c6`; ready marker: `ceabfb266993567aab8c867525365e18aa0a0d7ba5a2cb7b6cfcc83d21318c40`.
- 1.12.2 manifest SHA-256: `b8a37ccfccd2aac5f40f5e34fec85873dbdfa93a266043c103e592e4a8c949da`; provenance: `747425a26da363d1abc8a2cecd83a74504aeb1f58be93aa53f48c690e35318ce`; ready marker: `b0aeec721e5c0af02b33d9e217cb8272889fa139ded1ecc2a31e42b930138f7a`.
- Inventory matrix SHA-256: `f41e7591f77d44268dc58d7b42a496b7385ffeb79992568c2dac50785d0f948c`.

## Ordered coverage

Owned union: 239 Java paths (116 unchanged, 118 hash-changed, 5 added, 0 removed). The table preserves inventory path order. “Method deltas” lists paired Java method bodies whose normalized bodies differ (`+` new, `-` removed, `*` changed); additions/removals use the same status semantics as the path matrix.

| Path | Matrix status | Method deltas |
|---|---|---|
| `net/minecraft/block/AbstractLeavesBlock.java` | unchanged | — |
| `net/minecraft/block/AbstractLogBlock.java` | unchanged | — |
| `net/minecraft/block/AbstractPressurePlateBlock.java` | hash_changed | getFaceShape+ |
| `net/minecraft/block/AbstractRailBlock.java` | hash_changed | getFaceShape+ |
| `net/minecraft/block/AirBlock.java` | hash_changed | getFaceShape+ |
| `net/minecraft/block/AnvilBlock.java` | hash_changed | addToCreativeMenu*, getFaceShape+, land+, onTickFallingBlockEntity- |
| `net/minecraft/block/AxisBlock.java` | unchanged | — |
| `net/minecraft/block/banner/BannerPattern.java` | unchanged | — |
| `net/minecraft/block/BannerBlock.java` | hash_changed | getFaceShape+ |
| `net/minecraft/block/BarrierBlock.java` | unchanged | — |
| `net/minecraft/block/BeaconBlock.java` | unchanged | — |
| `net/minecraft/block/BedBlock.java` | hash_changed | afterMinedByPlayer+, beforeCollision+, beforeMinedByPlayer*, createBlockEntity+, dropItems*, getDropItem*, getFaceShape+, getMapColor+, getPickItem*, getRenderType+, hasCustomMiningProgress+, isHead+, neighborChanged*, onFallenOn+, onRemoved+ |
| `net/minecraft/block/BedrockBlock.java` | unchanged | — |
| `net/minecraft/block/BeetrootsBlock.java` | unchanged | — |
| `net/minecraft/block/Block.java` | hash_changed | addToCreativeMenu*, getFaceShape+, init*, isExceptionForAttachment+, isExceptionForPlacement+, isFaceSolid- |
| `net/minecraft/block/BlockMirror.java` | unchanged | — |
| `net/minecraft/block/BlockRotation.java` | unchanged | — |
| `net/minecraft/block/Blocks.java` | hash_changed | — |
| `net/minecraft/block/BlockWithBlockEntity.java` | unchanged | — |
| `net/minecraft/block/BoneBlock.java` | unchanged | — |
| `net/minecraft/block/BookshelfBlock.java` | unchanged | — |
| `net/minecraft/block/BrewingStandBlock.java` | hash_changed | getFaceShape+ |
| `net/minecraft/block/ButtonBlock.java` | hash_changed | canBePlaced*, canSurvive*, getFaceShape+, getPlacementState*, neighborChanged* |
| `net/minecraft/block/CactusBlock.java` | hash_changed | getFaceShape+ |
| `net/minecraft/block/CakeBlock.java` | hash_changed | getFaceShape+ |
| `net/minecraft/block/CarpetBlock.java` | hash_changed | addToCreativeMenu*, getFaceShape+, getMapColor* |
| `net/minecraft/block/CarrotsBlock.java` | unchanged | — |
| `net/minecraft/block/CauldronBlock.java` | hash_changed | getFaceShape+ |
| `net/minecraft/block/ChestBlock.java` | hash_changed | getFaceShape+ |
| `net/minecraft/block/ChorusFlowerBlock.java` | hash_changed | getFaceShape+ |
| `net/minecraft/block/ChorusPlantBlock.java` | hash_changed | getFaceShape+ |
| `net/minecraft/block/ClayBlock.java` | unchanged | — |
| `net/minecraft/block/CobwebBlock.java` | hash_changed | getFaceShape+ |
| `net/minecraft/block/CocoaBlock.java` | hash_changed | getFaceShape+ |
| `net/minecraft/block/ColoredBlock.java` | hash_changed | addToCreativeMenu*, getMapColor* |
| `net/minecraft/block/CommandBlock.java` | hash_changed | isConditionMet-, neighborChanged*, run+, runChain*, tick* |
| `net/minecraft/block/ComparatorBlock.java` | unchanged | — |
| `net/minecraft/block/ConcretePowderBlock.java` | added | addToCreativeMenu+, convertToConcrete+, createStateDefinition+, getDropItemMetadata+, getMapColor+, getMetadataFromState+, getStateFromMetadata+, land+, neighborChanged+, onAdded+ |
| `net/minecraft/block/CraftingTableBlock.java` | unchanged | — |
| `net/minecraft/block/DaylightDetectorBlock.java` | hash_changed | addToCreativeMenu*, getFaceShape+ |
| `net/minecraft/block/DeadBushBlock.java` | hash_changed | — |
| `net/minecraft/block/DetectorRailBlock.java` | unchanged | — |
| `net/minecraft/block/DiodeBlock.java` | hash_changed | getFaceShape+ |
| `net/minecraft/block/DirtBlock.java` | hash_changed | addToCreativeMenu* |
| `net/minecraft/block/dispenser/DispenseBehavior.java` | unchanged | — |
| `net/minecraft/block/dispenser/DispenseItemBehavior.java` | unchanged | — |
| `net/minecraft/block/dispenser/DispenseProjectileBehavior.java` | unchanged | — |
| `net/minecraft/block/DispenserBlock.java` | unchanged | — |
| `net/minecraft/block/DoorBlock.java` | hash_changed | getFaceShape+, getMapColor* |
| `net/minecraft/block/DoublePlantBlock.java` | hash_changed | addToCreativeMenu* |
| `net/minecraft/block/DoubleRedSandstoneSlabBlock.java` | unchanged | — |
| `net/minecraft/block/DoubleStoneSlabBlock.java` | hash_changed | getFaceShape+ |
| `net/minecraft/block/DoubleWoodenSlabBlock.java` | unchanged | — |
| `net/minecraft/block/DragonEggBlock.java` | hash_changed | getFaceShape+ |
| `net/minecraft/block/DropperBlock.java` | unchanged | — |
| `net/minecraft/block/EnchantingTableBlock.java` | hash_changed | getFaceShape+ |
| `net/minecraft/block/EnderChestBlock.java` | hash_changed | getFaceShape+ |
| `net/minecraft/block/EndGatewayBlock.java` | hash_changed | getFaceShape+ |
| `net/minecraft/block/EndPortalBlock.java` | hash_changed | getFaceShape+, onEntityCollision* |
| `net/minecraft/block/EndPortalFrameBlock.java` | hash_changed | getFaceShape+ |
| `net/minecraft/block/EndRodBlock.java` | hash_changed | getFaceShape+ |
| `net/minecraft/block/entity/BannerBlockEntity.java` | unchanged | — |
| `net/minecraft/block/entity/BeaconBlockEntity.java` | hash_changed | getLevel+, updateLevels* |
| `net/minecraft/block/entity/BedBlockEntity.java` | added | createUpdatePacket+, getColor+, getDropItem+, isHead+, readNbt+, setColor+, toNbt+, writeNbt+ |
| `net/minecraft/block/entity/BlockEntity.java` | hash_changed | fromNbt* |
| `net/minecraft/block/entity/BlockEntityProvider.java` | unchanged | — |
| `net/minecraft/block/entity/BrewingStandBlockEntity.java` | hash_changed | isItemAllowed* |
| `net/minecraft/block/entity/ChestBlockEntity.java` | unchanged | — |
| `net/minecraft/block/entity/CommandBlockBlockEntity.java` | hash_changed | asEntity-, markConditionMet*, readNbt*, setAuto* |
| `net/minecraft/block/entity/ComparatorBlockEntity.java` | unchanged | — |
| `net/minecraft/block/entity/DaylightDetectorBlockEntity.java` | unchanged | — |
| `net/minecraft/block/entity/DispenserBlockEntity.java` | unchanged | — |
| `net/minecraft/block/entity/DropperBlockEntity.java` | unchanged | — |
| `net/minecraft/block/entity/EnchantingTableBlockEntity.java` | unchanged | — |
| `net/minecraft/block/entity/EnderChestBlockEntity.java` | unchanged | — |
| `net/minecraft/block/entity/EndGatewayBlockEntity.java` | hash_changed | findExitPortal*, getBeamAngle-, getBeamAngleWhileSpawning-, getCooldownBeamHeight+, getSpawningBeamHeight+, placeExitPortal* |
| `net/minecraft/block/entity/EndPortalBlockEntity.java` | unchanged | — |
| `net/minecraft/block/entity/FlowerPotBlockEntity.java` | unchanged | — |
| `net/minecraft/block/entity/FurnaceBlockEntity.java` | unchanged | — |
| `net/minecraft/block/entity/HopperBlockEntity.java` | unchanged | — |
| `net/minecraft/block/entity/InventoryBlockEntity.java` | unchanged | — |
| `net/minecraft/block/entity/LootInventoryBlockEntity.java` | unchanged | — |
| `net/minecraft/block/entity/MobSpawnerBlockEntity.java` | unchanged | — |
| `net/minecraft/block/entity/MovingBlockEntity.java` | unchanged | — |
| `net/minecraft/block/entity/NoteBlockBlockEntity.java` | hash_changed | playNote* |
| `net/minecraft/block/entity/ShulkerBoxBlockEntity.java` | unchanged | — |
| `net/minecraft/block/entity/SignBlockEntity.java` | hash_changed | addResult*, asEntity*, getDisplayName*, readNbt*, sendCommandSuccessToOps*, sendMessage* |
| `net/minecraft/block/entity/SkullBlockEntity.java` | hash_changed | getAnimationProgress+, getAnimationTicks- |
| `net/minecraft/block/entity/StructureBlockEntity.java` | unchanged | — |
| `net/minecraft/block/FaceShape.java` | added | — |
| `net/minecraft/block/FacingBlock.java` | unchanged | — |
| `net/minecraft/block/FallingBlock.java` | hash_changed | land+, onTickFallingBlockEntity- |
| `net/minecraft/block/FarmlandBlock.java` | hash_changed | getFaceShape+, neighborChanged*, onAdded*, onFallenOn*, setDirt*, tick* |
| `net/minecraft/block/FenceBlock.java` | hash_changed | getFaceShape+, resolveVirtualProperties*, shouldConnectTo* |
| `net/minecraft/block/FenceGateBlock.java` | hash_changed | getFaceShape+ |
| `net/minecraft/block/Fertilizable.java` | unchanged | — |
| `net/minecraft/block/FireBlock.java` | hash_changed | getFaceShape+, tick* |
| `net/minecraft/block/FlowerBlock.java` | hash_changed | addToCreativeMenu* |
| `net/minecraft/block/FlowerPotBlock.java` | hash_changed | getFaceShape+ |
| `net/minecraft/block/FlowingLiquidBlock.java` | unchanged | — |
| `net/minecraft/block/FrostedIceBlock.java` | unchanged | — |
| `net/minecraft/block/FurnaceBlock.java` | unchanged | — |
| `net/minecraft/block/GlassBlock.java` | unchanged | — |
| `net/minecraft/block/GlazedTerracottaBlock.java` | added | createStateDefinition+, getMetadataFromState+, getPistonMoveBehavior+, getPlacementState+, getStateFromMetadata+, mirror+, rotate+ |
| `net/minecraft/block/GlowstoneBlock.java` | hash_changed | — |
| `net/minecraft/block/GrassBlock.java` | unchanged | — |
| `net/minecraft/block/GrassPathBlock.java` | hash_changed | getFaceShape+, setDirtIfBlockAbove* |
| `net/minecraft/block/GravelBlock.java` | hash_changed | — |
| `net/minecraft/block/HardenedClayBlock.java` | hash_changed | — |
| `net/minecraft/block/HayBlock.java` | unchanged | — |
| `net/minecraft/block/HopperBlock.java` | hash_changed | getFaceShape+ |
| `net/minecraft/block/HorizontalFacingBlock.java` | unchanged | — |
| `net/minecraft/block/IceBlock.java` | unchanged | — |
| `net/minecraft/block/InfestedBlock.java` | hash_changed | addToCreativeMenu* |
| `net/minecraft/block/JukeboxBlock.java` | hash_changed | setRecord* |
| `net/minecraft/block/LadderBlock.java` | hash_changed | canAttach+, canBePlaced*, canSurvive-, getFaceShape+, getPlacementState*, neighborChanged* |
| `net/minecraft/block/Leaves2Block.java` | hash_changed | addToCreativeMenu* |
| `net/minecraft/block/LeavesBlock.java` | hash_changed | addToCreativeMenu* |
| `net/minecraft/block/LeverBlock.java` | hash_changed | canBePlaced*, getFaceShape+, getPlacementState*, neighborChanged* |
| `net/minecraft/block/LilyPadBlock.java` | unchanged | — |
| `net/minecraft/block/LiquidBlock.java` | hash_changed | getFaceShape+, isFaceSolid* |
| `net/minecraft/block/LiquidSourceBlock.java` | unchanged | — |
| `net/minecraft/block/Log2Block.java` | hash_changed | addToCreativeMenu* |
| `net/minecraft/block/LogBlock.java` | hash_changed | addToCreativeMenu* |
| `net/minecraft/block/MagmaBlock.java` | hash_changed | — |
| `net/minecraft/block/material/AirMaterial.java` | unchanged | — |
| `net/minecraft/block/material/LiquidMaterial.java` | unchanged | — |
| `net/minecraft/block/material/MapColor.java` | hash_changed | of+ |
| `net/minecraft/block/material/Material.java` | unchanged | — |
| `net/minecraft/block/material/PlantMaterial.java` | unchanged | — |
| `net/minecraft/block/material/PortalMaterial.java` | unchanged | — |
| `net/minecraft/block/MelonBlock.java` | unchanged | — |
| `net/minecraft/block/MobSpawnerBlock.java` | unchanged | — |
| `net/minecraft/block/MovingBlock.java` | hash_changed | getFaceShape+ |
| `net/minecraft/block/MushroomBlock.java` | hash_changed | getMapColor* |
| `net/minecraft/block/MushroomPlantBlock.java` | unchanged | — |
| `net/minecraft/block/MyceliumBlock.java` | unchanged | — |
| `net/minecraft/block/NetherBrickBlock.java` | hash_changed | — |
| `net/minecraft/block/NetherrackBlock.java` | hash_changed | — |
| `net/minecraft/block/NetherWartBlock.java` | unchanged | — |
| `net/minecraft/block/NoteBlock.java` | hash_changed | — |
| `net/minecraft/block/ObserverBlock.java` | unchanged | — |
| `net/minecraft/block/ObsidianBlock.java` | hash_changed | — |
| `net/minecraft/block/OreBlock.java` | unchanged | — |
| `net/minecraft/block/PackedIceBlock.java` | unchanged | — |
| `net/minecraft/block/PaneBlock.java` | hash_changed | getFaceShape+, resolveVirtualProperties*, shouldConnectTo* |
| `net/minecraft/block/pattern/BlockPattern.java` | hash_changed | toString* |
| `net/minecraft/block/pattern/BlockPatternBuilder.java` | unchanged | — |
| `net/minecraft/block/pattern/BlockPointer.java` | unchanged | — |
| `net/minecraft/block/piston/PistonMoveBehavior.java` | hash_changed | — |
| `net/minecraft/block/piston/PistonMoveStructureResolver.java` | hash_changed | addColumn*, addNeighborColumns*, resolve* |
| `net/minecraft/block/PistonBaseBlock.java` | hash_changed | canMoveBlock*, doEvent*, getFaceShape+ |
| `net/minecraft/block/PistonHeadBlock.java` | hash_changed | getFaceShape+ |
| `net/minecraft/block/PlanksBlock.java` | hash_changed | addToCreativeMenu* |
| `net/minecraft/block/PlantBlock.java` | hash_changed | getFaceShape+ |
| `net/minecraft/block/PortalBlock.java` | hash_changed | getFaceShape+ |
| `net/minecraft/block/PotatoesBlock.java` | unchanged | — |
| `net/minecraft/block/PoweredRailBlock.java` | unchanged | — |
| `net/minecraft/block/PressurePlateBlock.java` | unchanged | — |
| `net/minecraft/block/PrismarineBlock.java` | hash_changed | addToCreativeMenu* |
| `net/minecraft/block/PumpkinBlock.java` | hash_changed | trySpawnGolem* |
| `net/minecraft/block/PurpurSlabBlock.java` | unchanged | — |
| `net/minecraft/block/QuartzBlock.java` | hash_changed | addToCreativeMenu* |
| `net/minecraft/block/RailBlock.java` | unchanged | — |
| `net/minecraft/block/RedFlowerBlock.java` | unchanged | — |
| `net/minecraft/block/RedSandstoneBlock.java` | hash_changed | addToCreativeMenu* |
| `net/minecraft/block/RedSandstoneSlabBlock.java` | hash_changed | addToCreativeMenu* |
| `net/minecraft/block/RedstoneBlock.java` | unchanged | — |
| `net/minecraft/block/RedstoneLampBlock.java` | unchanged | — |
| `net/minecraft/block/RedstoneOreBlock.java` | unchanged | — |
| `net/minecraft/block/RedstoneTorchBlock.java` | unchanged | — |
| `net/minecraft/block/RedstoneWireBlock.java` | hash_changed | getFaceShape+ |
| `net/minecraft/block/RepeaterBlock.java` | unchanged | — |
| `net/minecraft/block/SandBlock.java` | hash_changed | addToCreativeMenu* |
| `net/minecraft/block/SandstoneBlock.java` | hash_changed | addToCreativeMenu* |
| `net/minecraft/block/SaplingBlock.java` | hash_changed | addToCreativeMenu* |
| `net/minecraft/block/SeaLanternBlock.java` | hash_changed | — |
| `net/minecraft/block/ShulkerBoxBlock.java` | hash_changed | addHoverText*, beforeMinedByPlayer*, getFaceShape+ |
| `net/minecraft/block/SignBlock.java` | hash_changed | getFaceShape+ |
| `net/minecraft/block/SingleRedSandstoneSlabBlock.java` | unchanged | — |
| `net/minecraft/block/SingleStoneSlabBlock.java` | unchanged | — |
| `net/minecraft/block/SingleWoodenSlabBlock.java` | unchanged | — |
| `net/minecraft/block/SkullBlock.java` | hash_changed | getFaceShape+, trySpawn* |
| `net/minecraft/block/SlabBlock.java` | hash_changed | getFaceShape+ |
| `net/minecraft/block/SlimeBlock.java` | unchanged | — |
| `net/minecraft/block/SnowBlock.java` | unchanged | — |
| `net/minecraft/block/SnowLayerBlock.java` | hash_changed | canBePlaced*, getFaceShape+ |
| `net/minecraft/block/SoulSandBlock.java` | unchanged | — |
| `net/minecraft/block/sound/BlockSounds.java` | unchanged | — |
| `net/minecraft/block/spawner/MobSpawner.java` | unchanged | — |
| `net/minecraft/block/spawner/MobSpawnerEntry.java` | unchanged | — |
| `net/minecraft/block/SpongeBlock.java` | hash_changed | addToCreativeMenu* |
| `net/minecraft/block/StainedGlassBlock.java` | hash_changed | addToCreativeMenu*, getMapColor* |
| `net/minecraft/block/StainedGlassPaneBlock.java` | hash_changed | addToCreativeMenu*, getMapColor* |
| `net/minecraft/block/StainedHardenedClayBlock.java` | added | getMapColor+ |
| `net/minecraft/block/StairsBlock.java` | hash_changed | getFaceShape+, getMapColor* |
| `net/minecraft/block/StandingSignBlock.java` | unchanged | — |
| `net/minecraft/block/state/AbstractBlockState.java` | unchanged | — |
| `net/minecraft/block/state/BlockBehaviors.java` | unchanged | — |
| `net/minecraft/block/state/BlockProperties.java` | hash_changed | — |
| `net/minecraft/block/state/BlockState.java` | unchanged | — |
| `net/minecraft/block/state/predicate/BlockPredicate.java` | unchanged | — |
| `net/minecraft/block/state/predicate/BlockStatePredicate.java` | hash_changed | apply* |
| `net/minecraft/block/state/predicate/MaterialPredicate.java` | unchanged | — |
| `net/minecraft/block/state/property/AbstractProperty.java` | hash_changed | toString* |
| `net/minecraft/block/state/property/BooleanProperty.java` | unchanged | — |
| `net/minecraft/block/state/property/DirectionProperty.java` | unchanged | — |
| `net/minecraft/block/state/property/EnumProperty.java` | unchanged | — |
| `net/minecraft/block/state/property/IntegerProperty.java` | unchanged | — |
| `net/minecraft/block/state/property/Property.java` | unchanged | — |
| `net/minecraft/block/state/property/StringSerializable.java` | unchanged | — |
| `net/minecraft/block/state/StateDefinition.java` | hash_changed | getFaceShape+, getMapColor*, toString* |
| `net/minecraft/block/StemBlock.java` | unchanged | — |
| `net/minecraft/block/StoneBlock.java` | hash_changed | addToCreativeMenu* |
| `net/minecraft/block/StonebrickBlock.java` | hash_changed | addToCreativeMenu* |
| `net/minecraft/block/StoneButtonBlock.java` | unchanged | — |
| `net/minecraft/block/StoneSlabBlock.java` | hash_changed | addToCreativeMenu* |
| `net/minecraft/block/StructureBlock.java` | unchanged | — |
| `net/minecraft/block/StructureVoidBlock.java` | hash_changed | getFaceShape+ |
| `net/minecraft/block/SugarCaneBlock.java` | hash_changed | getFaceShape+ |
| `net/minecraft/block/TallPlantBlock.java` | hash_changed | addToCreativeMenu* |
| `net/minecraft/block/TntBlock.java` | unchanged | — |
| `net/minecraft/block/TorchBlock.java` | hash_changed | canAttach*, canSitOnTop*, getFaceShape+, getPlacementState*, tryBreak* |
| `net/minecraft/block/TransparentBlock.java` | unchanged | — |
| `net/minecraft/block/TrapdoorBlock.java` | hash_changed | getFaceShape+ |
| `net/minecraft/block/TripwireBlock.java` | hash_changed | getFaceShape+ |
| `net/minecraft/block/TripwireHookBlock.java` | hash_changed | canBePlaced*, getFaceShape+, neighborChanged*, updateState* |
| `net/minecraft/block/VineBlock.java` | hash_changed | canBePlaced*, canGrowOn*, canSurvive+, getFaceShape+, getPropertyForDirection*, hasConnections*, resolveVirtualProperties*, tick* |
| `net/minecraft/block/WallBlock.java` | hash_changed | addToCreativeMenu*, getFaceShape+, isExceptionForConnection+, resolveVirtualProperties*, shouldConnectTo* |
| `net/minecraft/block/WallSignBlock.java` | unchanged | — |
| `net/minecraft/block/WeightedPressurePlateBlock.java` | unchanged | — |
| `net/minecraft/block/WheatBlock.java` | unchanged | — |
| `net/minecraft/block/WoodenButtonBlock.java` | unchanged | — |
| `net/minecraft/block/WoodenSlabBlock.java` | hash_changed | addToCreativeMenu* |
| `net/minecraft/block/YellowFlowerBlock.java` | unchanged | — |
| `net/minecraft/world/chunk/WorldChunk.java` | unchanged | — |
| `net/minecraft/world/World.java` | hash_changed | findLight*, getNearestPlayer*, tickEntities*, tickEntity* |
| `net/minecraft/world/WorldRegion.java` | unchanged | — |
| `net/minecraft/world/WorldView.java` | unchanged | — |

## Movement dispositions and dependency closure

### Direct player response: bed bounce

- 1.11.2 `BedBlock` has no `beforeCollision` override. 1.12.2 adds it at `net/minecraft/block/BedBlock.java:141-149`: sneaking delegates to the base callback; otherwise when `velocityY < 0.0`, it assigns `velocityY = -velocityY * 0.66F`, multiplying by `0.8` for non-`LivingEntity` instances. It also halves `fallDistance` in `onFallenOn` (`:136-138`); fall damage calculation is outside scope.
- `Entity.move` in both exact trees invokes the selected block’s `beforeCollision` after collision resolution when vertical position changed (`entity/Entity.java:724` in 1.11.2 and `:727` in 1.12.2). Thus a non-sneaking player whose resolved support state is a bed and whose pre-callback vertical velocity is negative gets a direct vertical velocity response in 1.12.2. This is a source-level reachable gate, not a trajectory claim.
- The same callback caller exists both releases; the changed provider is the new BedBlock override. Bed material and block existed in 1.11.2 (`BedBlock` constructor passes `Material.WOOL`). Bed’s new block entity/color/render/mining behavior has no additional movement path.
- Key file SHA-256 (1.11.2 / 1.12.2): `BedBlock.java` `84f56aab19adae4b953a27040e5f431f65c8923d6bd169995ac5a5f629a024e1` / `48c127b2df425cb8d7ff60a284207de10a8408e90ee18a8449522a004a0b4369`.

### Fluid contact current: face-solid predicate

- In 1.11.2 `LiquidBlock.isFaceSolid` (`:83-91`) returns false for same-liquid material, true for UP, false for ICE, otherwise delegates to `Block.isFaceSolid`, whose exact body is `world.getBlockState(pos).getMaterial().isSolid()` (`Block.java:343-345`). `Material.isSolid()` defaults to true; 1.11.2 `BedBlock` is `Material.WOOL` and that material has no non-solid override.
- In 1.12.2 `LiquidBlock.isFaceSolid` (`:82-100`) preserves same-liquid, UP and ICE gates, then excludes `Block.isExceptionForAttachment(block)` and every `StairsBlock`, and requires `blockState.getFaceShape(world,pos,face) == FaceShape.SOLID`. Bed’s new override (`BedBlock.java:327-329`) returns `UNDEFINED`, so an adjacent existing bed is solid by the older material predicate and non-solid to this 1.12 flow-wall predicate.
- Exact flow gate: unchanged `LiquidBlock.getFlow` checks side and side-above cells only when `(Integer)state.get(LEVEL) >= 8` (`1.11.2:131-165`, `1.12.2:139-179`). If either is face-solid, it adds `(0,-6,0)` to the normalized accumulated horizontal flow before final normalization. Otherwise no downward term is added. Level 8 is source-reachable in unchanged `FlowingLiquidBlock.tick`: if upper fluid has state `n` in `[0,7]`, it assigns `m=n+8` (`1.12.2:45-50`); when `i>=8`, the flowing block spreads `i` downward (`:85-95`).
- Concrete in-scope witness: an existing bed is east of an isolated level-8 water cell; the cell’s other horizontal neighbors and the above-east cell are air, and the below-east cell is air. The player is centered in the water cell so the contact AABB intersects that cell only, is non-flying, and is in water. In 1.11.2 the zero depth-gradient vector normalizes to `ZERO` (`Vec3d.normalize`, both versions line 37), the east bed is recognized as solid and adds `(0,-6,0)`, producing a downward unit current. In 1.12.2 the bed returns UNDEFINED, so with no other side-wall cells the flow contribution remains zero. This is source-level reachability and a velocity-input difference, not a player trajectory claim.
- Caller closure: unchanged `LiquidBlock.applyMaterialDrag` adds `getFlow` to the per-cell vector. `World.applyLiquidDrag` iterates intersecting liquid cells whose fluid surface reaches the supplied AABB, accumulates each block’s vector, normalizes the sum once, and adds `0.014 * normalizedVector` to entity velocity when `entity.hasLiquidCollision()` (`world/World.java:1571-1613` in 1.11.2; `:1579-1621` in 1.12.2). `Entity.checkWaterCollisions` (`entity/Entity.java:927` / `:948`) supplies `getShape().grown(0.0,-0.4F,0.0).contract(0.001)` in both versions; player `hasLiquidCollision()` is `!abilities.flying` in both (`PlayerEntity.java:1736` / `:1733`). Thus current enters a direct player-velocity write.
- Key file SHA-256 (1.11.2 / 1.12.2): `LiquidBlock.java` `c91618c8445dd488dd61f4a01aed0799dae2faaf3585bc6f2d5164891e988244` / `00f7995dd49a48bf97316e30b3d6e41d8ee1e65dd212f3dc64891883621c4260`; `Block.java` `ab873436b24487ab0ee8055bf7a478349c7f7978398aea8e387796a6c820996a` / `e4a90eca411e7b0e14f5018f7385ec29891f6cdf1a1e917fa648b5712f9b33a1`.

### Collision providers: fences, panes, walls

- The three blocks retain their collision box constants and `addCollisions`/`getShape` selection logic, but their `resolveVirtualProperties` connection predicates changed. `World.getCollisions` in both versions sends intersecting block states to `BlockState.addCollisions`; the fence/pane/wall implementations resolve neighbor bits before selecting/adding their multi-box collision shapes (`FenceBlock.java:59-84`, `PaneBlock.java:54-83`, `WallBlock.java:90-102` in 1.12.2; equivalent provider chain in 1.11.2).
- Old neighbor gates: fence connected to same-material fences/gates, else required `material.isSolidBlocking() && state.isCube()` (with barrier/pumpkin exceptions); pane required a cube or glass/pane-family class; wall required a solid-blocking cube (plus fence gates), with barrier/pumpkin exceptions. New gates use the neighbor’s directional `FaceShape` (`SOLID`, `MIDDLE_POLE`, or `MIDDLE_POLE_THIN`) plus exception lists (`FenceBlock.java:124-138` (older `:124-133`), `PaneBlock.java:136-161` (older `:136-146`), `WallBlock.java:146-160` (older `:148-158`)); the `resolveVirtualProperties` methods query the neighbor-facing direction (`FenceBlock.java:163-167`, `PaneBlock.java:109-113`, `WallBlock.java:190-194`).
- Concrete era-existing witness: place a fence at P, with a straight stone stair immediately south whose `FACING=NORTH` and whose resolved stair `SHAPE=STRAIGHT`. 1.11.2 checks the south neighbor without a directional parameter and rejects it because `StairsBlock.isCube()` is false. 1.12.2 asks for that stair’s NORTH face; `StairsBlock.getFaceShape` returns `SOLID` when straight and `FACING==face` (`StairsBlock.java:140-163`), and the fence’s exception predicate does not include stairs, so its SOUTH connection becomes true. For the same state, 1.11.2 `FenceBlock.addCollisions` emits only the center post `Box(0.375,0,0.375,0.625,1.5,0.625)`; 1.12.2 also emits the south arm `Box(0.375,0,0.625,0.625,1.5,1.0)` (`FenceBlock.java:24-55`). This is an exact collision-provider difference with an era-existing stair, without predicting a trajectory.
- Pane and wall use the same new directional face classification; their corresponding south arms are selected from the unchanged multi-box arrays, so they share the neighbor-gate dependency. Key file SHA-256 (1.11.2 / 1.12.2): `FenceBlock.java` `856cb92d4613289747a34a6bc85f306ca9d44b7057a45c79f2020d13025d1cf7` / `633ad3b20250bd010a28d0b1362b24c81a5508be8a81a9d826953a3c9c4a703e`; `PaneBlock.java` `1e8968e7d642358cce90f0d3156f07f19c5bca28d2008d5d2d4bf0eccc7266a9` / `83862f6633b8241c3788e9c9a7e4fd0275c010ec790019b8003908996f4814b3`; `WallBlock.java` `17da6d881365e4dce5134f88cb9a9e5273d1fa1734812d13410dc6924a3acbfd` / `7e4378c46e367c32deca14475e5161b6e4223947d83153ce3e7ba5c8a0d18e50`.

### Remaining changed paths

- New `FaceShape` and the `BlockProperties`/`StateDefinition` accessor form a new provider API. Besides the fluid-current method above and fence/pane/wall collision connectors, other new per-block face classifications are consumed by attachment/support/placement/update logic (`VineBlock`, `TorchBlock`, `SnowLayerBlock`, `TripwireHookBlock`, and related). Their observed calls mutate environmental block placement/support/growth states; they do not supply player collision boxes or a direct player movement response. World block-state update simulation is not part of this owned player-movement slice.
- `EndPortalBlock.onEntityCollision` keeps the same conjunction and eventual `changeDimension(1)` call; only the client-side guard moved to the first short-circuit term, so no player-visible result changes. Portal transfer eligibility is also outside movement shape/current providers.
- `PistonMoveBehavior` gains `PUSH_ONLY`; resolver/base direction plumbing changes to honor it. The only assigned 1.12 block class that returns `PUSH_ONLY` is newly added `GlazedTerracottaBlock`; no block existing in the 1.11.2 registry returns it. Piston block-entity entity pushing (`MovingBlockEntity.moveEntities`) is hash-unchanged; therefore no behavior difference is reachable for the 1.11.2 block set through this change.
- Added `ConcretePowderBlock`, `GlazedTerracottaBlock`, `StainedHardenedClayBlock`, and `BedBlockEntity` are 1.12-only content/colored-bed backing and out of historical-block scope. Added `FaceShape` is covered above as an API dependency.
- Other paired method changes in the inventory are creative-menu/map-color/UI/rendering/text/block-entity storage or ticking/placement/support/world-state effects, non-player falling-block/piston resolution for new content, or nonmovement server/environment mechanics. They do not modify an existing block’s player collision shape except the three connector classes already closed; do not infer mod movement changes from their source-hash differences.
- `World.java` is the only changed world-provider sibling in the selected set. Paired method-body comparison finds changes only in `findLight`, `getNearestPlayer`, `tickEntities`, and `tickEntity`; `getCollisions` and `applyLiquidDrag` bodies are unchanged. `WorldView`, `WorldRegion`, and `WorldChunk` are unchanged and included as provider closure. No separate collision/fluid package exists in this Feather source layout.

## Exact paired method-body hashes

Hashes below are SHA-256 of each exact decompiled method declaration and body, including whitespace. “Absent” means no method exists in that endpoint source. File hashes for all these methods are listed above and also bound by the ordered inventory’s matrix hashes.

| Class / method | 1.11.2 method SHA-256 | 1.12.2 method SHA-256 |
|---|---|---|
| `BedBlock.beforeCollision` | absent | `739b89bf36e5f9ce4c66a6f4768909c5b5ed54da616bf54facdb4d6eb3df6487` |
| `BedBlock.getFaceShape` | absent | `dbcf05dce055d7f1f5ebe70c49006bebd6b44cdb8373b6499cc767638c6297a9` |
| `Block.isFaceSolid` | `c073abf574bc1cc7096ade06e328a427bcfd555933fcf4e52179806b7150c632` | absent |
| `Block.getFaceShape` | absent | `b8b84c6b586440ee776d2f2df4e49c4133c4b5e9a4f9f7fdf92cb1dcbd2c2bb7` |
| `LiquidBlock.isFaceSolid` | `70058c87053e682696aad7a8d6b7909e749d4b3c3d52b3892be8cb5375f7eeba` | `8961cd9680dd239c577e206a828aaee54d083318a01b5fe8fdd45ed65feb855c` |
| `LiquidBlock.getFlow` | `42369b8dc66c8a87471f174f1ff3de8e0ad17df691ec6c38a8ecada0351552dd` | `42369b8dc66c8a87471f174f1ff3de8e0ad17df691ec6c38a8ecada0351552dd` |
| `LiquidBlock.applyMaterialDrag` | `5b10311c226e2cc53c92697bfd678ce000bda0e9b78eb1b88a7ac0fcc5003764` | `5b10311c226e2cc53c92697bfd678ce000bda0e9b78eb1b88a7ac0fcc5003764` |
| `World.applyLiquidDrag` | `b9f4a3e6bd03a18e82dc809354f721633542d9b8cbca516206c1e0374a42fe41` | `b9f4a3e6bd03a18e82dc809354f721633542d9b8cbca516206c1e0374a42fe41` |
| `Entity.checkWaterCollisions` | `24601849a87c1f368870ae087859bf23cf686035d91e60bd01a48b5f624b2810` | `24601849a87c1f368870ae087859bf23cf686035d91e60bd01a48b5f624b2810` |
| `PlayerEntity.hasLiquidCollision` | `674a7567d6bb0afdd9f36555cdbfcc57f42055fc5c397aaff170c00672312de5` | `674a7567d6bb0afdd9f36555cdbfcc57f42055fc5c397aaff170c00672312de5` |
| `Vec3d.normalize` | `87600ad6411ca1e8513d5381a8d2cc25fd4ace85f7c1f35bb07c65cc157a7341` | `87600ad6411ca1e8513d5381a8d2cc25fd4ace85f7c1f35bb07c65cc157a7341` |
| `FenceBlock.shouldConnectTo` | `6013c52358fe635774fbe83448d7775c7697a1a01c28f3ffe9fd10c0d3f646df` | `a84c8ff3638e75a9022cce80d51d1cbb484deb7c069b49d4ac0007a32d2690ba` |
| `FenceBlock.addCollisions` | `b43668b59ddc239f60e4cd1eb4d2e426ee7810875f3702cdca33e2bd45fd5381` | `b43668b59ddc239f60e4cd1eb4d2e426ee7810875f3702cdca33e2bd45fd5381` |
| `FenceBlock.resolveVirtualProperties` | `64c290d0a7b9b62ec7cd40f08e5165c83f19db023806892778f922cf79388467` | `37c3b311ab1a3a35fc2ed81a9ca43017edf032f607e5db1c2ad1fd97290f879c` |
| `PaneBlock.shouldConnectTo` | `cfa6886cfa55369c6e04d885fba5c67324ce5ba4950da542f7b79bda839836d8` | `2621b57179b810253eb3e9191428e32f430b088a5b89735dacc8ea684ad3e87b` |
| `PaneBlock.addCollisions` | `ce23f64bec49e2f15a9e1d211eb88f2b6f1fd307f202c66a6d5421e045e6ebb2` | `ce23f64bec49e2f15a9e1d211eb88f2b6f1fd307f202c66a6d5421e045e6ebb2` |
| `PaneBlock.resolveVirtualProperties` | `84fb5e1431395690e6b3c94f7a267ed9a4fb8277b7677c8e5f8d63d06f2e8068` | `3d7e8c91c2b3d48be8718636432285e6ccc538c887276a6077c2d0724ef91796` |
| `WallBlock.shouldConnectTo` | `aebe8dc11f8df77b7b606120c07dbb59491350d5f5ecc9ce695d5f32fccc1449` | `2daf3ff2aff7b673fb3a0d95f1722a0a965fb7496040c89e4ab646c3582ecfdf` |
| `WallBlock.addCollisions` | `df54595640be6c81d86163716034f173ba17d5c6f05ed31011dba72661d1c38b` | `df54595640be6c81d86163716034f173ba17d5c6f05ed31011dba72661d1c38b` |
| `WallBlock.resolveVirtualProperties` | `0be10e85f9d00d4a60176e25fedb51cce3416a300ad9f6ee99c033eb59e5d9c9` | `4afe7529301c40ae991865ae4396f359913b5451fe46e04f0ec39831844dc1e2` |
| `StairsBlock.isCube` | `1b65fae3a2209800c098ea8f213b171560d654fd8a65cace1210397810afa597` | `1b65fae3a2209800c098ea8f213b171560d654fd8a65cace1210397810afa597` |
| `StairsBlock.getFaceShape` | absent | `d09b7a7b75bd985c4590ebdbb64cd1ada0c9e9d2b4e9a0f90dceca2cb93d6171` |

## Slice completion / next action

No unresolved source dependency remains in this owned block/provider slice for player collision boxes, bed collision callback, or fluid current/contact. The master can reconcile the three concrete movement candidates above against the other lanes and decide which differences become findings. Full-pair coverage and independent audit remain outside this assignment. No tests, build, game launch, source generation, wiki or product inspection was performed.
