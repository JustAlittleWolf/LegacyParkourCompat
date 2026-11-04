# LilyPad effective collision provider: 1.12.2 → 1.13.2

Bounded source-only provider audit using exact local Ornithe Feather Gen2 build.2 endpoints and the independently reviewed PlantBlock dependency checklist SHA `1D5F3142CB9411E6C508D8EB945749ED1934654E85B5ADD4D4735DEDC6711003`. This note does not claim whole-movement or collector parity.

## Exact source identities

- 1.12.2 ready source manifest: `b8a37ccfccd2aac5f40f5e34fec85873dbdfa93a266043c103e592e4a8c949da`.
- 1.13.2 ready source manifest: `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211`.

Relevant manifest source hashes: 1.12.2 `World.java` `e9fa9b8d6d31ad57a5b876f5f63a5e3c554a23437decf1845a493daf48233594`, `LilyPadBlock.java` `873b93062318b692cae5c071edb6489cbac6621bddaa4b139f08df493b075fb9`, `Block.java` `e4a90eca411e7b0e14f5018f7385ec29891f6cdf1a1e917fa648b5712f9b33a1`, `Entity.java` `80f091bf32166c88cf8bbd31caf72d84fa16224410733c7d2a0f00563f294a0a`; 1.13.2 `WorldView.java` `9577360b1a1bdde9280f0b72593e5e6dde45d9a54719cd7ff933f3ecd31b971f`, `LilyPadBlock.java` `741784cff408619ec5f262366b481c92bd5892d329fc2f77393e3faeae6397eb`, `Block.java` `735030e8bb5fc7ead2d6dbcb1b262a36a414b4349daf197456337f460b444cd4`, `BlockState.java` `0496fda381628e90b3b3ff8ae376a5bae24cbd662366f34d70991252fab2fe70`, `Entity.java` `1d6ec8b80f74635401745c2c027bf36555c85348ca5693764f2668363b17d269`.

## Paired provider path

1.12.2 `Entity` movement queries `World.getCollisions(this, sweptBox)` (`entity/Entity.java:567`); `World.getCollisions` calls each block state's `addCollisions(world,pos,sweptBox,collisions,entity,false)` (`world/World.java:964,1001`). `LilyPadBlock.addCollisions` (`block/LilyPadBlock.java:23-28`) adds its translated static `SHAPE` unless `entity instanceof BoatEntity`. It ignores `state`, `world`, and `forceShape`; `pos` is only used by `Block.addCollision` to translate the box and test intersection. `LilyPadBlock.getShape` also returns that same static shape (`:38`), but the callback directly chooses `SHAPE` and bypasses `PlantBlock.getCollisionShape`.

1.13.2 `Entity` movement queries `WorldView.getCollisions(this,shape,dx,dy,dz)` (`entity/Entity.java:580,612`). `WorldView.getBlockCollisions` obtains the state collision shape (`world/WorldView.java:135`); `BlockState.getCollisionShape` forwards to the block (`block/state/BlockState.java:209-210`); base `Block.getCollisionShape` applies `hasCollision` and dispatches to `state.getShape` (`block/Block.java:393-394`). The LilyPad registration at `block/Block.java:1208` does not call `.noCollision()`, and `hasCollision` defaults true (`:1901`). `LilyPadBlock.getShape` (`block/LilyPadBlock.java:30`) returns its static `SHAPE`, ignoring `state`, `world`, and `pos`. No collision-shape callback receives an entity/boat argument on this path.

## Provider result and limits

The old static `Box` is `(0.0625, 0, 0.0625)` to `(0.9375, 0.09375, 0.9375)`. The new `Block.block(1,0,1,15,1.5,15)` helper divides each coordinate by 16 (`block/Block.java:136`), yielding the same local bounds. Thus both endpoints supply the same fixed LilyPad collision geometry for ordinary player movement; the new base gate is enabled for the registered LilyPad.

Old `PlayerEntity` extends `LivingEntity` while `BoatEntity` extends `Entity` (`entity/living/player/PlayerEntity.java:94`; `entity/vehicle/BoatEntity.java:38`), so ordinary players do not meet the old boat exclusion. The new provider has no per-entity exception. The source-level boat-specific suppression therefore differs only for boats, outside the ordinary player movement scope.

Shape-provider dependencies are closed for this block: no neighbor, fluid, block-entity, state-property, or entity-shape input selects the LilyPad box. Placement/survival predicates read water/ice state, but they do not participate in the collision-shape query. World scan traversal, shape-query exclusion semantics, and collision-axis resolution are consumer behavior and are not closed by this provider note.

## Bounded verdict

Provider-level ordinary-player result: fixed collision bounds and enabled collision gate match between the endpoints. This is not a verdict on complete 1.12.2/1.13.2 collision collection or movement parity. The only observed provider gate difference is old BoatEntity exclusion versus the entity-independent new getter, which is out of scope for parkour player physics.
