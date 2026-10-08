# F-FARMLAND-PLAYER-RELOCATION: farmland conversion relocates a landed player differently

- Older version A: 1.11.2
- Newer version B: 1.12.2
- Mechanic / coverage slice IDs: S4.4a (landed-player block callback relocation); parent S4.4 and block callback S5.2
- Classification: changed behavior
- Confidence: source-confirmed by discovery author; independent blind review pending
- Applicability: historical player behavior
- First changed release: unknown within (1.11.2, 1.12.2]
- Runtime validation: not performed

## Paired evidence

### A artifact identity

- Evidence artifact record ID: `EA-FEATHER-R1-1.11.2`
- Publication status: revised-derived
- Revision ID: `feather-r1-2026-10-07`
- Immutable evidence path: `build/movement-campaign-2026-10-07/revisions/derived-artifact-snapshots/feather-r1-2026-10-07/1.11.2/ornithe-feather/client-ornithe-feather.jar`
- Evidence artifact SHA-256: `ba1872d5fd341770a45aeeb8d372776a1b89f0b88a11a72f15aa0fe879b6a29f`
- Evidence manifest path / SHA-256: `.../revision.json`, `49fca091d3ef83551745119f740d7a66a2773e81262db137bfc747369e8f61ac`; `.../artifact.sha256` records the JAR hash
- Original artifact-manifest path / SHA-256: `build/movement-campaign-2026-10-07/ready/1.11.2/artifacts.sha256`, `69327982116b0ce1efe32e13031cb83a93dc5f30dcd1b936bf6af003a584ac5f`
- Original derived-artifact availability and expected SHA-256: unavailable; expected `356d8efb64df6c89ab74e69b34718d5b79cbfbbc28b744913ce943d9679566b3`
- Source/raw-input hash relation and verification reference: revision record says source tree and raw inputs are identical to their original manifests; source manifest `d908c2af76598ad26999563bfef0089f1d3c8c32483eee6b108f0defa2773df0`; both source body and immutable snapshot hashes cited below were rechecked against the published pair.
- Revised-to-original equivalence and provenance limitations: unverified because the original derived JAR is unavailable; the revision does not prove identity or a metadata-only change.
- Source: `1.11.2/ornithe-feather/net/minecraft/block/FarmlandBlock.java`, `FarmlandBlock::onFallenOn(World,BlockPos,Entity,float)` lines 59-69 and `setDirt(World,BlockPos)` lines 71-79, SHA-256 `09e260528fd78b4bbdbc302726c7ae937fbc49f17b674c4a7ad802e0f7b6f5e8`.
- Player path: `1.11.2/ornithe-feather/net/minecraft/entity/Entity.java`, `Entity::move(MoverType,DDD)V` landing-state selection and callback at lines 687-713; `checkFallDamage(double,boolean,BlockState,BlockPos)` lines 873-885; `setPosition(double,double,double)` lines 295-302; SHA-256 `ce8104a17ce783df639cf9726e7b1cd0936563ea7ba308603335bbe05d49440`.
- Entity query: `1.11.2/ornithe-feather/net/minecraft/world/World.java`, `World::getEntities(Entity,Box)` lines 2127-2147, SHA-256 `27cfaa5ff45c2d88c492fc5dae3ea4a2bb4536fd64bff8498a9a7d0cb82efb58`.
- Dirt collision shape: `1.11.2/ornithe-feather/net/minecraft/block/Block.java`, inherited `getShape(BlockState,WorldView,BlockPos)` lines 289-291 returns `FULL_BLOCK_SHAPE`, SHA-256 `ab873436b24487ab0ee8055bf7a478349c7f7978398aea8e387796a6c820996a1`.

### B artifact identity

- Evidence artifact record ID: `EA-FEATHER-R1-1.12.2`
- Publication status: revised-derived
- Revision ID: `feather-r1-2026-10-07`
- Immutable evidence path: `build/movement-campaign-2026-10-07/revisions/derived-artifact-snapshots/feather-r1-2026-10-07/1.12.2/ornithe-feather/client-ornithe-feather.jar`
- Evidence artifact SHA-256: `fcc17537a14a423e2086f600047725ec1fcfd4c7fcf5c0d1a5bda491966c1b87`
- Evidence manifest path / SHA-256: `.../revision.json`, `2be8645d57ca5c00411b037e7617f9860ac700f7fa8ef50d28220c2e1b0c60dc`; `.../artifact.sha256` records the JAR hash
- Original artifact-manifest path / SHA-256: `build/movement-campaign-2026-10-07/ready/1.12.2/artifacts.sha256`, `8171a095a5ee74cebbd12b3d76983fc01796c0c23c962076d1e235cd0f80fa8c`
- Original derived-artifact availability and expected SHA-256: unavailable; expected `65a08f15d18c4ec2bd0f05b89dfc1ba7ea6b8280ba93ed136245f39e63ec8a2b`
- Source/raw-input hash relation and verification reference: revision record says source tree and raw inputs are identical to their original manifests; source manifest `b8a37ccfccd2aac5f40f5e34fec85873dbdfa93a266043c103e592e4a8c949da`; both source body and immutable snapshot hashes cited below were rechecked against the published pair.
- Revised-to-original equivalence and provenance limitations: unverified because the original derived JAR is unavailable; the revision does not prove identity or a metadata-only change.
- Source: `1.12.2/ornithe-feather/net/minecraft/block/FarmlandBlock.java`, `FarmlandBlock::onFallenOn(World,BlockPos,Entity,float)` lines 60-70 and `setDirt(World,BlockPos)` lines 72-80, SHA-256 `d84c75dae139181b5d58cfa34f3cb77c5b2d4bda63d6ad9c3b52a47350bd0b98`.
- Player path: `1.12.2/ornithe-feather/net/minecraft/entity/Entity.java`, `Entity::move(MoverType,DDD)V` landing-state selection and callback at lines 690-716; `checkFallDamage(double,boolean,BlockState,BlockPos)` lines 890-902; `teleport(double,double,double)` lines 2181-2185; `setPositionAndAngles(double,double,double,float,float)` lines 1122-1135; SHA-256 `80f091bf32166c88cf8b8bd31caf72d84fa16224410733c7d2a0f00563f294a0a`.
- Entity query: `1.12.2/ornithe-feather/net/minecraft/world/World.java`, `World::getEntities(Entity,Box)` lines 2138-2158 and `tickEntity(Entity,boolean)` lines 1412-1482, SHA-256 `e9fa9b8d6d31ad57a5b876f5f63a5e3c554a23437decf1845a493daf48233594`.
- Dirt collision shape: `1.12.2/ornithe-feather/net/minecraft/block/Block.java`, inherited `getShape(BlockState,WorldView,BlockPos)` lines 307-309 returns `FULL_BLOCK_SHAPE`, SHA-256 `e4a90eca411e7b0e14f5018f7385ec29891f6cdf1a1e917fa648b5712f9b33a1`.
- Farmland's `SHAPE_ABOVE` is declared at line 21 as `(0.0,0.9375,0.0)-(1.0,1.0,1.0)` in the cited `FarmlandBlock.java`.

## Source-level difference

Both versions route a landed entity through `Entity.move` to `checkFallDamage`; with `landed=true` and positive `fallDistance`, the selected support block receives `onFallenOn`. For farmland, a server-side callback with `fallDistance > 1.5F` passes the random condition for every `nextFloat()` result, and a standing player passes the remaining checks: it is a `LivingEntity`, is a `PlayerEntity`, and its `0.6 x 0.6 x 1.8` volume is `0.648`, greater than `0.512`. The helper first converts the farmland block to dirt; that block-state transition remains vanilla.

In A, `setDirt` queries the dirt block's full collision box and calls `entity.setPosition(entity.x, box.maxY, entity.z)` for returned entities. Dirt inherits `Block.getShape`, whose `FULL_BLOCK_SHAPE` reaches `pos.y + 1.0`. In B, `setDirt` queries only `SHAPE_ABOVE`, the top `0.0625` of the former farmland block, and computes `d = min(box.maxY - box.minY, box.maxY - entity.getShape().minY)` before calling `entity.teleport(entity.x, entity.y + d + 0.001, entity.z)`.

For a non-spectating standing player whose feet are at the farmland surface (`pos.y + 0.9375`), both entity queries include the player's box. A writes player Y as `pos.y + 1.0`; B computes `d = 0.0625` and writes Y as `pos.y + 1.001`. B also enters the inherited teleport path: it sets `teleported`, resets current/previous/last position through `setPositionAndAngles`, and calls `World.tickEntity(this,false)`. With `requireLoaded=false`, that call skips `entity.tick()` while running the remaining world/chunk update path. A's `setPosition` changes current coordinates and bounding box without those teleport updates.

## Reachability and dependencies

The player movement path is `PlayerEntity` (inheriting `Entity.move`) -> landing support `BlockState` selection -> `Entity.checkFallDamage` -> `FarmlandBlock.onFallenOn` -> `FarmlandBlock.setDirt` -> `World.getEntities(null,box)` -> player position update. `World.getEntities(Entity,Box)` defaults to `EntityFilter.NOT_SPECTATOR` and does not restrict the query to a non-player entity type; the stated player is non-spectating. The eligible direct player movement consequence is the position/position-history write. Other entities returned by the same query are outside this finding's player-only conclusion. The call can also be reached by farmland random ticks and neighbor/on-added callbacks; this finding is bounded to the landing path above.

## Consequence and uncertainty

Source proves different server-side player position operations under the stated landing and callback guards, including a `0.001` Y offset for the concrete standing-player case and B's teleport bookkeeping path. It does not establish a measured trajectory or downstream client correction. The farmland-to-dirt state change itself and non-player relocation are not movement behaviors claimed by this finding. First-changed release within the pair is unknown; runtime validation was not performed.

## Handoff

This finding is one player-position transition through farmland's landing callback. Independent blind review must accept this exact immutable snapshot before implementation handoff. The finding depends on revised Feather evidence artifacts `EA-FEATHER-R1-1.11.2` and `EA-FEATHER-R1-1.12.2`; original derived JAR equivalence remains unverified. Related coverage includes parent S4.4 and block callback slice S5.2; full collision, callback and pair inventories remain open.
