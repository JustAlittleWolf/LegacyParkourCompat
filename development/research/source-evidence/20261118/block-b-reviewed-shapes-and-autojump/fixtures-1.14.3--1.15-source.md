# COMPLETED — Source-only fixture: 1.14.3 → 1.14.4 → 1.15 stationary dry solids

## Scope and verdict

Compared Bell, Grindstone, Stonecutter, Campfire, Lectern, and Composter in the exact releases below, including registered state properties/defaults, material and collision flags, collision/outline shape selection, static shape-cache behavior, and the entity block-collision retrieval path.

**Per-edge/per-family verdict:** no player-movement collision-shape change found for any of the six families on either edge. No eligible movement finding draft.

| Family | 1.14.3 → 1.14.4 | 1.14.4 → 1.15 | Bounded evidence |
|---|---|---|---|
| Bell | No movement-shape change | No movement-shape change; new `POWERED` state does not enter shape dispatch | State definitions/defaults; shape fields, selector and explicit collision override |
| Grindstone | No movement-shape change | No movement-shape change | State definitions/defaults; all orientation shape fields and selector; explicit collision override |
| Stonecutter | No movement-shape change | No movement-shape change | FACING state; 0–9/16 outline shape inherited as collision |
| Campfire | No movement-shape change | No movement-shape change; `noOcclusion` and virtual fire post are outside player collision | LIT/SIGNAL_FIRE/WATERLOGGED/FACING state; 0–7/16 shape; inherited collision; fire-path use distinguished |
| Lectern | No movement-shape change | No movement-shape change | FACING/POWERED/HAS_BOOK state; state-independent collision versus facing outline |
| Composter | No movement-shape change | No movement-shape change | LEVEL 0–8; level-dependent outline versus state-independent level-0 collision |

## Exact evidence identity and integrity

- **1.14.3, Yarn:** `decompiled_minecraft/1.14.3/yarn`; Yarn `1.14.3+build.13` (mapping artifact SHA-256 `ad9e77f907cfc6780fb348ad4b3a948ffc8cea3bfe50abcc792428645e49e06e`); `.decompile-provenance.json` SHA-256 `81ea0021eb122d4e3340e20d47d6ecb7079365ba767a038d26a8264cddacc28a`. Verified the manifest-pinned hashes against the six block classes, `Blocks`, `Block`, `BlockState`, and `Entity`.
- **1.14.4, Mojmap:** `decompiled_minecraft/1.14.4/mojmap`; official client mappings artifact SHA-256 `2dd53a5e70ba493cf6e33c0fc52bbdf4c57f9429c7a13842565aa825fd44d910`; `mojmap.sources.sha256` SHA-256 `af98406f3d4ed31494fbdce1078d1f3df3f712501878f9a0e9ad5879bdf23c3b`. The ready marker identifies exact release 1.14.4/Mojmap and verification reports identical regenerated source and input artifacts. Verified manifest-pinned hashes against the six block classes, `Blocks`, `Block`, `BlockState`, and `Entity`.
- **1.15, Mojmap:** `decompiled_minecraft/1.15/mojmap`; official client mappings SHA-256 `11415bc25024658f0ef1c1a0028df282135299e526a689de2f29c4662edf931a`; `mojmap.sources.sha256` SHA-256 `251a3019ccc3fd067f66365f3545b69a983383a28b19a1038debf1791be29340`. Verified manifest-pinned hashes against the six block classes, `Blocks`, `Block`, `BlockState`, and `Entity`.

Names were paired by their mapped roles: Yarn `getOutlineShape` corresponds to Mojmap `getShape` in these block providers; Yarn `WallMountLocation` corresponds to Mojmap `AttachFace`; Yarn bell `Attachment` corresponds to `BellAttachType`. Equal names alone were not treated as evidence.

## State registration and material/collision flags

- **Bell:** FACING + ATTACHMENT (NORTH/FLOOR default) in 1.14.3/1.14.4; 1.15 adds POWERED, default false. Attachment values are FLOOR, CEILING, SINGLE_WALL, DOUBLE_WALL. The collision selector reads only FACING and ATTACHMENT on all three sides; POWERED does not affect its result.
- **Grindstone:** FACING + FACE, default NORTH/WALL; FACE values FLOOR, WALL, CEILING. Selector and registered domains are equivalent across mapping aliases.
- **Stonecutter:** FACING, default NORTH; shape is orientation-independent.
- **Campfire (dry subset):** LIT, SIGNAL_FIRE, WATERLOGGED, FACING; default true/false/false/NORTH. The compared dry states have WATERLOGGED=false; none of these state properties changes the collision shape.
- **Lectern:** FACING, POWERED, HAS_BOOK; default NORTH/false/false. Facing selects outline only, not collision.
- **Composter:** LEVEL 0–8, default 0. LEVEL selects outline; collision always returns shape-array element 0.

All six registrations use movement-blocking materials and the default collision-enabled block properties: no `noCollision`/`noCollission` or dynamic-bounds/dynamic-shape override is applied. Yarn 1.14.3 Grindstone uses `Material.ANVIL`; the later mapped registration uses `Material.HEAVY_METAL`. Their material builders retain the defaults for blocking motion and solid blocking; the later `notPushable` flag concerns piston behavior. Campfire's registration color changes from SPRUCE to PODZOL, and 1.15 adds `.noOcclusion()`; neither changes collision enablement or collision geometry.

## Shape/collision comparison

- **Bell:** all 11 cuboid coordinate tuples match in each release. FLOOR selects the two perpendicular full-height narrow strips; CEILING, DOUBLE_WALL, and SINGLE_WALL select the same respective bell/crossbar shapes by the same direction predicates. `getCollisionShape` explicitly delegates to the same private selector; outline delegates there too.
- **Grindstone:** all 40 cuboid coordinate tuples match in each release. The FLOOR/WALL/CEILING and cardinal-direction selector returns the same orientation geometry. `getCollisionShape` explicitly delegates to it; outline does too.
- **Stonecutter:** the sole cuboid is `(0,0,0)-(16,9,16)` in every release. Yarn overrides outline; Mojmap overrides `getShape`; inherited collision obtains that shape.
- **Campfire:** the collision/outline slab remains `(0,0,0)-(16,7,16)`. In 1.15 `VIRTUAL_FENCE_POST` `(6,0,6)-(10,16,10)` is consulted in a fire/smoke related check, not returned by `getShape`/collision; excluded effects do not alter the stationary collision solid.
- **Lectern:** all 15 cuboid coordinate tuples match. The collision is the common base `(0,0,0)-(16,2,16)`, post `(4,2,4)-(12,14,12)`, and top plate `(0,15,0)-(16,15,16)` (zero Y thickness). Facing-dependent projecting book/lectern outline pieces are not used by collision.
- **Composter:** the full-cube minus inner box `(2,max(2,1+2*level),2)-(14,16,14)` outline array is state-dependent, with entry 8 reusing entry 7; the collision override always returns `SHAPES[0]`. 1.14.3's `combineAndSimplify` is `combine(...).simplify()`. 1.14.4/1.15's `join` is `joinUnoptimized(...).optimize()`. The compared `simplify`/`optimize` bodies both rebuild by unioning each box from empty, so this naming/API transition does not change the shape region used by movement. The interaction shape remains full cube and is not the movement collision shape.

## Inherited defaults, cache, and local movement retrieval

- In 1.14.3, `Block.getCollisionShape` returns empty only when `collidable` is false; otherwise it asks the state's outline shape. Default `collidable=true`; `dynamicBounds=false` absent an explicit override. `BlockState.initShapeCache` caches states when bounds are not dynamic; its collision cache is initialized with `EntityContext.absent()` and an empty block view.
- In 1.14.4 and 1.15, `Block.getCollisionShape` returns empty only when `hasCollision` is false; otherwise it asks the state's `getShape`. Default `hasCollision=true`; `dynamicShape=false`. Static state caches are initialized on that basis and use empty collision context for their cached collision shape.
- For all six classes the block source does not set the dynamic flag, and the context does not select another player-dependent geometry. The actual entity movement route is the contextual overload: `Entity.move` → collision response → world `getBlockCollisions`; the block iterator constructs `CollisionContext.of(entity)` and calls `BlockState.getCollisionShape(world,pos,context)`. That overload dispatches directly to the block provider rather than returning the empty-context cache. The local player class chain reaches this implementation through `LocalPlayer → AbstractClientPlayer → Player → LivingEntity → Entity`. In 1.14.3, `Entity.move`/`handleCollisions` uses `method_20743` for entity collisions, then obtains block geometry contextually: axis-only soft-offset movement walks candidate states in `VoxelShapes.calculateSoftOffset` and calls `getCollisionShape(world,pos,EntityContext)`; the diagonal route appends `ViewableWorld.method_20812`, which constructs `EntityContext.of(entity)` and calls the same contextual state collision overload. `ViewableWorld.method_20743` is entity-collision retrieval, not the block-shape iterator.

## Compared source members

For each edge the complete six block class bodies were inspected for shape declarations/selectors, default state, state definition, and collision/outline methods; paired registration lines were checked in `Blocks.java`. The bounded inherited/runtime slices were `Block` collision/default property implementations, `BlockState` cache initialization/accessors, and the entity/world collision retrieval members:

- **1.14.3:** `net/minecraft/block/{BellBlock,GrindstoneBlock,StonecutterBlock,CampfireBlock,LecternBlock,ComposterBlock}.java`; `Block.java` (147–149, 353–376, 830–916); `BlockState.java` (60–64, 184–197, 369–400); `Blocks.java` (1993–2031); `entity/Entity.java` (441–466, 653–699, 734–764); `util/shape/VoxelShapes.java` (224–287); `world/ViewableWorld.java` (121–176); `client/network/ClientPlayerEntity.java`, `client/network/AbstractClientPlayerEntity.java`, `entity/player/PlayerEntity.java`, `entity/LivingEntity.java` (class ancestry).
- **1.14.4:** `net/minecraft/world/level/block/{BellBlock,GrindstoneBlock,StonecutterBlock,CampfireBlock,LecternBlock,ComposterBlock}.java`; `Block.java` (151–152, 370–380, 842–928); `block/state/BlockState.java` (62–66, 192–199, 392–424); `Blocks.java` (1992–2028); `world/entity/Entity.java` (446–468, 658–715); `world/level/LevelReader.java` (134–175); `client/player/LocalPlayer.java`, `client/player/AbstractClientPlayer.java`, `world/entity/player/Player.java`, `world/entity/LivingEntity.java` (class ancestry).
- **1.15:** `net/minecraft/world/level/block/{BellBlock,GrindstoneBlock,StonecutterBlock,CampfireBlock,LecternBlock,ComposterBlock}.java`; `Block.java` (152–153, 382–392, 845–952); `block/state/BlockState.java` (64–68, 192–199, 398–424); `Blocks.java` (2086–2135); `world/entity/Entity.java` (450–472, 641–698); `world/level/CollisionGetter.java` (67–108); `client/player/LocalPlayer.java`, `client/player/AbstractClientPlayer.java`, `world/entity/player/Player.java`, `world/entity/LivingEntity.java` (class ancestry).

## Residuals and handoff

This result closes only the assigned source slice. Placement and state writers, survival/support/step behavior, callbacks/contact effects, fire behavior, unrelated state predicates, other blocks, and tick-loop coverage were not reviewed. The dry Campfire verdict does not cover waterlogged states. Shape-box comparison is source-static and makes no runtime parity claim.


