# Collision/query closure supplement: 1.12.2 -> 1.13.2

Source-only supplement to the existing lane artifacts. It does not edit or replace them.

## Bound artifacts and source identity

Prior immutable artifact hashes:

- `../coverage.md`: `2cb4040f8e2aad534d2ca9a60380a97b8e1bd28ddf7dfed903b9daf1b24bf888`
- `../water-contact-and-current-query.md`: `391d448311d3719bc42776861da02c59f34221e7fbaec3d6200ad70670cede70`
- `../correction-supplement.md`: `1895e10caf739762a66323bcd901ddac25e391b3949cfc5b7518744671e72b71`
- `../correction-supplement-2.md`: `22d70fddce221f362a6e41b27fd469d098d5630feeeee38338347723523c247b`

The paired exact inputs remain the verified Feather Gen2 build 2 sources identified in `../coverage.md`: 1.12.2 source manifest SHA-256 `b8a37ccfccd2aac5f40f5e34fec85873dbdfa93a266043c103e592e4a8c949da`, 1.13.2 source manifest SHA-256 `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211`. Every source hash listed below matched its row in its release's manifest; the intact files include the enclosing methods cited here.

## Movement candidate collection and axis solving

### 1.12.2

`Entity.move` (`net/minecraft/entity/Entity.java:462-699`) preserves the requested deltas in `n/o/p`, handles player sneak-edge reduction, then unconditionally obtains a `List<Box>` from `World.getCollisions(this, getShape().expanded(x,y,z))` (`Entity.java:567-568`). It clips Y, then X, then Z against that same list, updating the player box after each axis (`Entity.java:569-600`). Each `Box.intersectY/X/Z` (`net/minecraft/util/math/Box.java:185-243`) first requires strict positive overlap on the other two axes; for a positive request it lowers the limit to `block.min - player.max` when that gap is smaller, and for a negative request raises the limit to `block.max - player.min` when that gap is greater. There is no `1e-7` tolerance in those methods.

The step alternative retains the original ordered list and computes two candidate paths in order Y-X-Z, compares squared horizontal distances, and restores the original route if the stepped path does not improve it (`Entity.java:603-692`). The ordinary path's `World.getCollisions` visits candidate block positions with X outer, Z middle, Y inner loops (`world/World.java:978-984`), invokes the state delegate `addCollisions` (`block/state/StateDefinition.java:368-376`), and includes each translated provider box only if it intersects the swept input (`block/Block.java:371-388`). Entity collision boxes are appended after block boxes in entity-list order (`World.java:1017-1039`).

### 1.13.2

`Entity.move` (`net/minecraft/entity/Entity.java:473-680`) keeps the same broad Y-X-Z clipping order and step-route comparison, but candidate collection runs only when at least one requested delta is nonzero (`Entity.java:578-599`). It constructs a memoizing `C_70593968` over the ordered `Stream<VoxelShape>` (`Entity.java:580-598`; `unmapped/C_70593968.java:11-40`) and calls `VoxelShapes.calculateMaxOffset` after each axis box update. The step alternatives use the same cached candidate stream for both Y-X-Z routes (`Entity.java:611-664`), then retain the same squared-horizontal comparison shape as 1.12.2.

`WorldView` (which extends `BlockView`; the 1.13.2 source has no `BlockGetter` type) supplies the block collector and query overloads (`world/WorldView.java:112-207`; `world/BlockView.java:9-18`). The movement overload builds a swept VoxelShape with a `1e-7` expansion and a direction-dependent `1e-7` offset of the starting box, then subtracts the offset start shape (`WorldView.java:154-164`). The default shape overload uses the query box as the overlap shape (`WorldView.java:166-168`). Collection enumerates the one-block-padded coordinate range through `BlockPos.Mutable.iterateRegionMutable`; its iterator advances X, then Y, then Z (`util/math/BlockPos.java:212-239`). For each loaded, non-skipped position it asks `BlockState.getCollisionShape`; it filters shapes by nonempty overlap, translates ordinary shapes by block coordinates, and accumulates unit `VoxelShapes.block()` providers into one `BitSetVoxelSet`-backed `IntVoxelShape` emitted after the ordinary stream (`WorldView.java:112-152`; `block/shape/BitSetVoxelSet.java:6-75`; `block/shape/IntVoxelShape.java:6-19`). The 1.13.2 `World` override first delegates to the interface collector, then concatenates entity shapes when `entity != null` (`world/World.java:1754-1758`). `EntityView.getEntityCollisions` queries entities from the VoxelShape bounds expanded by `0.25`, filters exclusions/same-vehicle pairs, and adapts their collision boxes to VoxelShapes (`world/EntityView.java:21-36`).

The numeric clipping implementation is not just a changed container type: `VoxelShapes.calculateMaxOffset` consumes shapes sequentially and returns `0.0` as soon as it encounters any stream element while `abs(maxDist) < 1e-7` (`block/shape/VoxelShapes.java:194-206`). Otherwise each shape's `calculateMaxDistance` uses indexed voxel boundaries, `±1e-7` coordinate probes, and directional scans to adjust the offset (`block/shape/VoxelShape.java:184-243`). Thus the endpoint source has distinct tolerance gates and provider traversal; representation alone does not establish parity.

### Narrow source-level witness for the tolerance gate

For a reachable non-sneaking, airborne local-player `SELF` move, take `x=5e-8`, `y=z=0`, and a collidable block face with positive X gap `1e-8` from the player's box, with strict overlap on Y/Z. The 1.12.2 expanded query intersects that box; `Box.intersectX` clips the request to `1e-8` (it is below the `5e-8` request). On the 1.13.2 swept VoxelShape query, the same obstacle is within the `1e-7` swept padding and is emitted as a candidate; on the first shape in `calculateMaxOffset`, `abs(5e-8) < 1e-7` returns zero before inspecting that shape. The old solver therefore permits the `1e-8` gap travel, while the new solver clips this axis to zero. This is a source-arithmetic witness for the solver contract, not a runtime recording. It depends on a nonempty candidate stream; with no candidate shape the new empty stream leaves `maxDist` unchanged. It does not depend on any new block or block-specific geometry.

## Relevant no-collision and auto-jump query paths

- `Entity.canMove`/`doesNotCollide` remains a reached player helper: 1.12.2 uses `getCollisions(this, box).isEmpty() && !containsLiquid(box)` (`Entity.java:453-460`); 1.13.2 uses `hasNoCollisions(this, box) && !containsFluid(box)` (`Entity.java:465-470`). This delegates to the paired collector paths above and to the separate fluid-query evidence in the bound water draft.
- The ground+sneaking player edge-reduction checks in `Entity.move` call the simple entity-aware collision query for three prospective boxes before clipping (1.12.2 `Entity.java:522-564`; 1.13.2 `Entity.java:533-576`). Old checks list emptiness; new checks `hasNoCollisions`.
- 1.12.2 `LocalClientPlayerEntity.autoJump` first uses `getCollisionShape(...) == null` for the two immediate headroom states, gets a list of boxes, and examines boxes in list order, breaking on the first that crosses one of two probe segments (`LocalClientPlayerEntity.java:884-943`). It then checks per-block headroom with null checks and sets `ticksToNextAutojump` from the first support top (`:922-949`). 1.13.2 uses `isEmpty()` for shape checks, obtains a shape stream, flattens each shape with `getBoundingBoxes()`, and loops/breaks on the first intersecting box (`LocalClientPlayerEntity.java:909-973`). The `1.13.0 -> 1.13.1` first-stable auto-jump aggregation boundary supplied to this lane is treated as separately covered; this endpoint comparison does not claim that boundary or infer its exact introduction.
- The local player reachability through the client auto-jump path is established by the paired LocalClientPlayerEntity methods. Other `LivingEntity` no-collision consumers observed at 1.12.2 `LivingEntity.java:1342-1382` and 1.13.2 `LivingEntity.java:1396-1435` sit in dismount/vehicle positioning; they are excluded here under the non-player/vehicle movement boundary. `Entity.pushAwayFrom` no-entity tests (`Entity.java:1863` and `1899`) were noted but not treated as local-player move resolution.
- 1.12.2 `World.getCollisions(Box)` explicitly calls the private collector with `enforceWorldBorder=true` (`World.java:1062-1063`). 1.13.2 `hasNoCollisions(entity,Box)` uses the `WorldView` stream overload and `.allMatch(VoxelShape::isEmpty)` (`WorldView.java:201-207`); the `World` override appends entity collision shapes only for non-null entities. This source-level no-collision contract was traced; no independent result claim about null-entity border callers is made.

## Provider boundary and remaining block inventory

In 1.12.2, the per-state wrapper delegates both `getCollisionShape` and virtual `addCollisions` to its block (`block/state/StateDefinition.java:368-376`); the base collision hook forwards the state's single shape to `addCollision`, which moves and intersects it (`block/Block.java:371-388`). `Block.EMPTY_BLOCK_SHAPE` is `null` (`Block.java:54`), and the base `getShape` returns `FULL_BLOCK_SHAPE` (`Block.java:306-309`). In 1.13.2, `BlockState` delegates `getCollisionShape` and `getShape` separately (`block/state/BlockState.java:205-210`), while the base collision provider maps `hasCollision` to either the state VoxelShape or `VoxelShapes.empty()` (`block/Block.java:388-395`).

One inherited-provider check illustrates why class names alone do not close this dependency: old `ChorusPlantBlock.addCollisions` resolves virtual connection properties when `forceShape == false` and emits a center plus connection AABBs (`1.12.2 block/ChorusPlantBlock.java:65-97`). The 1.13.2 class extends `PipeBlock`; `PipeBlock` precomputes shapes and chooses by state bits (`1.13.2 block/PipeBlock.java:31-87`). This was inspected only to trace the provider boundary; its per-block geometry is not assessed here.

The following legacy collision-shape/provider declarations were inventoried by signature only; their bodies and the corresponding registered-state cases were **not individually compared**. They remain dependencies for movement queries when their states occur. The inventory is limited to old source declarations, not a sweep of post-1.12 blocks:

- 1.12.2 `getShape` / `getCollisionShape` declarations not body-audited: `AbstractPressurePlateBlock`, `AbstractRailBlock`, `AirBlock`, `AnvilBlock`, `BannerBlock`, `BedBlock`, `BeetrootsBlock`, `Block`, `BrewingStandBlock`, `ButtonBlock`, `CactusBlock`, `CakeBlock`, `CarpetBlock`, `CarrotsBlock`, `CauldronBlock`, `ChestBlock`, `ChorusPlantBlock`, `CobwebBlock`, `CocoaBlock`, `DaylightDetectorBlock`, `DeadBushBlock`, `DiodeBlock`, `DoorBlock`, `DoublePlantBlock`, `DragonEggBlock`, `EnchantingTableBlock`, `EnderChestBlock`, `EndGatewayBlock`, `EndPortalBlock`, `EndPortalFrameBlock`, `EndRodBlock`, `FarmlandBlock`, `FenceBlock`, `FenceGateBlock`, `FireBlock`, `FlowerBlock`, `FlowerPotBlock`, `GrassPathBlock`, `HopperBlock`, `LadderBlock`, `LeverBlock`, `LilyPadBlock`, `LiquidBlock`, `MovingBlock`, `MushroomPlantBlock`, `NetherWartBlock`, `PaneBlock`, `PistonBaseBlock`, `PistonHeadBlock`, `PlantBlock`, `PortalBlock`, `PotatoesBlock`, `RedstoneWireBlock`, `SaplingBlock`, `ShulkerBoxBlock`, `ShulkerBoxBlockEntity`, `SignBlock`, `SkullBlock`, `SlabBlock`, `SnowLayerBlock`, `SoulSandBlock`, `StateDefinition`, `StemBlock`, `StructureVoidBlock`, `SugarCaneBlock`, `TallPlantBlock`, `TorchBlock`, `TrapdoorBlock`, `TripwireBlock`, `TripwireHookBlock`, `VineBlock`, `WallBlock`, `WallSignBlock`, `WheatBlock`.
- 1.12.2 custom `addCollisions` declarations not body-audited beyond the base hook: `BrewingStandBlock`, `CauldronBlock`, `ChorusPlantBlock`, `EndPortalBlock`, `EndPortalFrameBlock`, `FenceBlock`, `HopperBlock`, `LilyPadBlock`, `MovingBlock`, `PaneBlock`, `PistonBaseBlock`, `PistonHeadBlock`, `StairsBlock`, `WallBlock`; `MovingBlockEntity` is outside the player movement scope. Moving-fence geometry is excluded by assignment.
- The 1.12.2 and 1.13.2 `Blocks` / provider registration entries were not compared individually for these shape providers. The already-recorded water/lava registration check is separate. A post-1.12.2 registration absent in 1.12.2 is outside this pair's historical-block set.

## Closure status

This closes the source call path through local-player movement clipping, collector traversal, block-state delegation, candidate aggregation, nonempty-query gates, and auto-jump consumption. The small-offset witness demonstrates a source-level solver divergence. It does **not** close provider geometry for existing blocks, state-dependent provider registrations, world-border edge cases, a full-pair inventory, or the independent coverage audit. No runtime behavior was inferred beyond the explicit source-arithmetic witness; no pair freeze is claimed.

## Directly checked source hashes

All entries matched the corresponding exact source-manifest row.

| Release | Relative source path | SHA-256 |
|---|---|---|
| 1.12.2 | `net/minecraft/entity/Entity.java` | `80f091bf32166c88cf8bbd31caf72d84fa16224410733c7d2a0f00563f294a0a` |
| 1.12.2 | `net/minecraft/entity/living/LivingEntity.java` | `190e9ac551538e015d9e4d6c42856e5ba32b593131cf6d93895e7b29533f1ee6` |
| 1.12.2 | `net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java` | `01a58e94d8c6ff98a8e3794227cdc76a5fcbdbad795c70c9cf28854aff9823cc` |
| 1.12.2 | `net/minecraft/world/World.java` | `e9fa9b8d6d31ad57a5b876f5f63a5e3c554a23437decf1845a493daf48233594` |
| 1.12.2 | `net/minecraft/world/WorldView.java` | `628ce75d2c76ed025b62e905da8030024e1b89b145445fd6971bfef798bba7cc` |
| 1.12.2 | `net/minecraft/block/Block.java` | `e4a90eca411e7b0e14f5018f7385ec29891f6cdf1a1e917fa648b5712f9b33a1` |
| 1.12.2 | `net/minecraft/block/state/BlockState.java` | `3fb6f06df86c3b89716b5efaed5d97eee670d007d3c4535e47eb0fdd993e92eb` |
| 1.12.2 | `net/minecraft/block/state/StateDefinition.java` | `934493deb8dad66936edf94264bda735ae8062c6ef091b1ff6a1501eed2b8d37` |
| 1.12.2 | `net/minecraft/block/ChorusPlantBlock.java` | `e726a991d9a1f9d3fbc14dadcff261bd2780920f8e75a4eeccd128f9cc69e336` |
| 1.12.2 | `net/minecraft/util/math/Box.java` | `f788b8146b14f299ccb58ea0854609f845c298a963295d503de2e30e15d66f3a` |
| 1.13.2 | `net/minecraft/entity/Entity.java` | `1d6ec8b80f74635401745c2c027bf36555c85348ca5693764f2668363b17d269` |
| 1.13.2 | `net/minecraft/entity/living/LivingEntity.java` | `bb691358c9a43c9f46e85575bf4d0a4ad671d0eb912502acc3a6a3f625e42f1c` |
| 1.13.2 | `net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java` | `2583495f3a02b4791aa036e6a8d354d7596c4984761969a0f29b29d8d9bf42bf` |
| 1.13.2 | `net/minecraft/world/World.java` | `0023de30da608db73c6c6188334d2bb19039d86fe4d6f5e4ad79d732a5d2cd1a` |
| 1.13.2 | `net/minecraft/world/WorldView.java` | `9577360b1a1bdde9280f0b72593e5e6dde45d9a54719cd7ff933f3ecd31b971f` |
| 1.13.2 | `net/minecraft/world/WorldAccess.java` | `fc75177d629ef8d0d491821fe557e21606e4cb381164f1ca41b2763073f854be` |
| 1.13.2 | `net/minecraft/world/EntityView.java` | `2b5b59eef6efa93bb51c1d838c7a1cf64725be96c55c47f76894fde11e13d6de` |
| 1.13.2 | `net/minecraft/world/BlockView.java` | `11fe9281078247e04cf5e01c56f47ad3f597bcc40877e217f92a5f3766ffd23f` |
| 1.13.2 | `net/minecraft/block/Block.java` | `735030e8bb5fc7ead2d6dbcb1b262a36a414b4349daf197456337f460b444cd4` |
| 1.13.2 | `net/minecraft/block/state/BlockState.java` | `0496fda381628e90b3b3ff8ae376a5bae24cbd662366f34d70991252fab2fe70` |
| 1.13.2 | `net/minecraft/block/ChorusPlantBlock.java` | `299acaed9644e0837a29f282e830dc7582b8d8f14a1c904147c034518e474433` |
| 1.13.2 | `net/minecraft/block/PipeBlock.java` | `4c56867771e5848fec41d7ccbce5339af3dfefa7f98afb4712dfd0d5abd8f9a9` |
| 1.13.2 | `net/minecraft/util/math/Box.java` | `0f573d00cc10386f94cf51671c62815979973053c9e67a806e9fee98e55a4d8d` |
| 1.13.2 | `net/minecraft/util/math/BlockPos.java` | `ee71cfdedcaa27458f48168ab761b435cf9c878b04f21e971ddbafb6254aa3fd` |
| 1.13.2 | `net/minecraft/block/shape/VoxelShapes.java` | `313968d4e5855b5ec380272b6ababcc5849d478ab771926355912a8cb6aa9c95` |
| 1.13.2 | `net/minecraft/block/shape/VoxelShape.java` | `d95b0607647da11595e24e1e26e9615cb7da7705ea7885908544362df62275c1` |
| 1.13.2 | `net/minecraft/block/shape/VoxelSet.java` | `fe39ea60ac27e595280627a4d15ea93a69292da5870b5ef34a1d7d7283a267c6` |
| 1.13.2 | `net/minecraft/block/shape/BitSetVoxelSet.java` | `2048daa3a95ddd567a70476862e9b2dde135b91f49b61d22a4ae3aeed98322aa` |
| 1.13.2 | `net/minecraft/block/shape/IntVoxelShape.java` | `e563f5530907a5cd0ecc07bd79473d378c8c1aeeaba1dba4ac6f703de7d7b3ca` |
| 1.13.2 | `net/minecraft/unmapped/C_70593968.java` | `6efb9fbc71bca9d0c49a8ed265721874a264fb51233c8ba5f547bb0e5e98d974` |
