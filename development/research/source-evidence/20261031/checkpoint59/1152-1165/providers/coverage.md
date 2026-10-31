# Residual legacy block providers: 1.15.2 → 1.16.5 Mojmap

## Scope and source identity

This closes the 81 class names listed as unexamined in `.native-source/20261030/1152-1165-pool33/legacy-block-provider-closure/coverage.md` for their fixed-state, local-player movement providers and directly relevant callers. Each candidate maps to exactly one source path on both sides; full-file SHA-256 values are in `source-hashes.tsv`. Supporting caller/provider hashes are in `dependency-hashes.tsv`.

Exact roots are `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\1.15.2\mojmap` and `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\1.16.5\mojmap`; the pair manifest pins are respectively `cb7fd93f8730d8b2813857744f8572060535c7de44fcee86222acd1ceb6f43e7` and `9499f2611d0e6dde37cb635f1a28a591416382d6bc188b37d5f99d2b2a20823b`. The corrected ordinal path inventory pin is `835ba455d3dd4825f01a74f282eee21c42254cee3870a400ee7f96d9824fbcd5`. All 81 candidate file hashes and all supporting Java-source hashes were checked against the corrected direct manifests with zero missing paths or hash mismatches. No wiki, product implementation/history, gameplay, build, tests, decompilation, or canonical research/backlog writes were used.

## Source-confirmed movement deltas

### Moving source-piston head collision shape

`PistonMovingBlockEntity.getCollisionShape` (older lines 333–352; newer 335–354) builds a `PISTON_HEAD` state for a source piston and selects `SHORT` with a changed predicate:

- 1.15.2: `this.extending != 1.0F - this.progress < 4.0F`
- 1.16.5: `this.extending != 1.0F - this.progress < 0.25F`

At ordinary progress `p ∈ [0,1]`, the older inner comparison is always true. The selected `SHORT` boolean differs for `p ≤ 0.75`; for `p > 0.75`, both predicates select the same boolean. `PistonHeadBlock` uses cached shapes in 1.16.5, but its per-facing `calculateShape` unions the same base boxes with the same selected `SHORT_*_ARM_AABB` or longer arm as the 1.15.2 `getShape`/`getArmShape` methods. `MovingPistonBlock.getCollisionShape` delegates to the resident moving block entity on both sides.

This reaches local-player collision clipping: `LivingEntity.travel` makes `MoverType.SELF` moves; `Entity.move` gathers block collisions over the swept box; the 1.15.2 `CollisionGetter` and the 1.16.5 `CollisionSpliterator` both include moving pistons in their extra cursor layer. Consequently, a local player sweep that intersects only the arm volume present in one selected shape can be clipped differently. The claim is conditional on a loaded source moving piston, a progress sample in the differing regime, and a player sweep through the differing arm volume. It does not claim a changed displacement for every piston movement.

### Retracting source-piston push volume

The same block entity has a second movement route through `getCollisionRelatedBlockState` (older lines 89–96; newer 92–99) and `moveCollidedEntities` (older lines 98–154; newer 102–171). In 1.15.2 the retraction collision-related piston-head state leaves `SHORT` at its default `false`; 1.16.5 sets `SHORT` to `this.progress > 0.25F`. The selected boolean therefore differs for retraction progress `p > 0.25F`. `moveCollidedEntities` uses that state’s collision shape to choose intersecting entities and invokes `moveEntityByPiston`, which calls `Entity.move(MoverType.PISTON, ...)`. On the client this entity list can contain the local `Player`; the newer `ServerPlayer` special case does not match a `LocalPlayer`.

For a retracting source piston after progress exceeds `0.25F`, the old push-volume head arm remains long and the new one is short. A player intersecting only the arm segment removed by the newer shape can therefore receive a different piston displacement. This is a distinct direct-push operation from ordinary `SELF` collision clipping above. The comparison of candidate broad-phase boxes (`getMinMaxPiecesAABB` removed; `voxelShape.bounds()` used) does not itself omit a collision candidate: each moved component box lies within the new shape bounds, and each candidate is still checked against the component sweep before movement.

These are source-level movement findings for the endpoint pair, not exact patch-introduction boundaries or runtime parity results.

## Checked no-delta / excluded inventory

The other 80 listed classes have no changed fixed-state local-player movement operation in this slice:

`AirBlock`, `AttachedStemBlock`, `BambooBlock`, `BambooSaplingBlock`, `BannerBlock`, `BaseCoralFanBlock`, `BaseCoralPlantBlock`, `BaseCoralPlantTypeBlock`, `BaseCoralWallFanBlock`, `BasePressurePlateBlock`, `BaseRailBlock`, `BeetrootBlock`, `BellBlock`, `BrewingStandBlock`, `ButtonBlock`, `CactusBlock`, `CakeBlock`, `CampfireBlock`, `CarrotBlock`, `CauldronBlock`, `ChestBlock`, `CocoaBlock`, `ComposterBlock`, `ConduitBlock`, `CoralPlantBlock`, `CropBlock`, `DaylightDetectorBlock`, `DeadBushBlock`, `DetectorRailBlock`, `DiodeBlock`, `DoorBlock`, `DragonEggBlock`, `EnchantmentTableBlock`, `EnderChestBlock`, `EndPortalBlock`, `EndPortalFrameBlock`, `EndRodBlock`, `HopperBlockEntity`, `FireBlock`, `FlowerBlock`, `FlowerPotBlock`, `GrindstoneBlock`, `HayBlock`, `HopperBlock`, `KelpBlock`, `Lantern`, `LecternBlock`, `LeverBlock`, `MushroomBlock`, `NetherPortalBlock`, `NetherWartBlock`, `PipeBlock`, `MovingPistonBlock`, `PistonBaseBlock`, `PotatoBlock`, `RedStoneOreBlock`, `RedstoneWallTorchBlock`, `RedStoneWireBlock`, `SaplingBlock`, `Seagrass`, `SeaPickleBlock`, `SignBlock`, `SkullBlock`, `StemBlock`, `StonecutterBlock`, `StructureVoidBlock`, `SugarCaneBlock`, `SweetBerryBushBlock`, `TallGrassBlock`, `TallSeagrass`, `TorchBlock`, `TripWireBlock`, `TripWireHookBlock`, `TurtleEggBlock`, `WallBannerBlock`, `WallSignBlock`, `WallSkullBlock`, `WallTorchBlock`, `WaterlilyBlock`, `WitherRoseBlock`.

Specific checks on movement-sensitive members and exceptions:

- State-specific `getShape`, `getCollisionShape`, `getOcclusionShape`, `getInteractionShape`, `entityInside`, `stepOn`, and `fallOn` members were compared across the candidate set. Their bodies are unchanged where present except the noted piston members, the outline-only `BaseRailBlock` / `FireBlock` / `RedStoneWireBlock` cases, and the inherited Kelp shape route described below. `SweetBerryBushBlock.entityInside` still sets the same stuck vector `(0.8F, 0.75, 0.8)`; the shared `Entity.makeStuckInBlock` still writes the same fall-distance reset and stuck multiplier.
- `AirBlock.isAir` was removed from the subclass, but 1.16.5 marks AIR, CAVE_AIR and VOID_AIR with `Properties.air()`; 1.15.2's `AirBlock.isAir` returns true. The source registration preserves the predicate used by movement/state queries.
- `BaseRailBlock.getShape` changes `getBlock() == this` to `BlockState.is(this)` with the same ascending/flat shapes. Rail registrations remain `noCollission()` on both sides. `FireBlock.getShape` now returns a state-cached outline instead of empty, and `RedStoneWireBlock.getShape` now returns a state-cached outline; both registrations remain `noCollission()` and neither provider overrides collision shape, so these changed outlines do not enter `Entity.move` block collision clipping.
- `KelpBlock.getShape` moves into the 1.16.5 `GrowingPlantHeadBlock` superclass; its head outline remains the same 9/16 shape. `KELP` and `KELP_PLANT` remain `noCollission()`, and the water source `getFluidState` response is unchanged. The new body superclass outline likewise does not become player collision.
- `PistonBaseBlock.isPushable` was independently compared for shared 1.15.2 blocks: the shared bounds, height, destroy-speed, push-reaction, extended-piston and entity-block gates correspond. Crying Obsidian and Respawn Anchor are the only named immovable additions and did not exist in 1.15.2. Other piston-base state/update and notification changes are server-side state production; client arrival/timing is outside this fixed-state closure.
- `CampfireBlock.entityInside` changes its damage argument to a constructor field; the existing `CAMPFIRE` registration supplies `1`, matching 1.15.2, and damage calculation is excluded. `EndPortalBlock.entityInside` performs its dimension change only on a server level. `WaterlilyBlock.entityInside` is boat-only. The other inspected block contact callbacks either match or only update block/redstone state; damage, world-state production and non-player/vehicle physics are outside this slice.

## Tags, resources, and adjacent-state dependencies

Changed provider bodies also refer to `BlockTags.MUSHROOM_GROW_BLOCK`, `BlockTags.SAND`, and `BlockTags.CAMPFIRES`; existing fluid predicates include `FluidTags.WATER`. These usages control plant support/growth, turtle-egg eligibility or campfire utility predicates, not collision/contact response for an already fixed block state. Both permitted exact source roots contain zero JSON files and no `data/` directory, so tag payload membership cannot be verified from these inputs. Those state-generation/tag memberships remain unclosed if later work expands from fixed states to world-state evolution.

Neighbor-dependent placement/support paths such as `BambooBlock.getStateForPlacement` / `updateShape`, `BellBlock.canSurvive`, `MushroomBlock.canSurvive`, `KelpBlock` / `KelpPlantBlock` growth and shape updates, `TurtleEggBlock.isSand`, `WaterlilyBlock.mayPlaceOn`, and the `PistonBaseBlock` state/update path were not audited as alternative world-state evolution. They do not alter the paired movement providers for the same supplied vanilla state.

## Exact still-open members / limits

- `PistonMovingBlockEntity.tick` adds client `deathTicks` retention at completed progress while the server removes the block entity immediately. `MovingPistonBlock.onRemove` reaches `finalTick`, whose new client-side gate forces cleanup when the moving block state is replaced. Whether a stale client moving-piston state can survive long enough between those updates to affect a local-player collision query depends on block-update arrival/order; this lifecycle timing was not closed here.
- `PistonBaseBlock`'s changed block update flags/neighbour notifications and all piston state-production timing are not closed; this report only accepts shape/push differences when the corresponding moving-piston state is present.
- Tag JSON payloads are absent from the permitted roots as noted above. No claim is made about tag-driven world-state evolution.
- This bounded slice does not close the pair's full tick graph, all other changed paths, independent pair audit, patch introduction version or runtime parity.

## Evidence files

- `source-hashes.tsv`: exact hashes for all 81 candidate classes.
- `dependency-hashes.tsv`: exact hashes for additional caller, base provider, tag-key and Kelp superclass sources used here.
- `diffs.txt`: paired unified diffs for all 81 candidate classes.
- `movement-methods.txt`: paired comparison inventory of the state-shape/contact members screened.
