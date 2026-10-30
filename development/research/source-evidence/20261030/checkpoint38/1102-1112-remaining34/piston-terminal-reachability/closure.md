# Piston-head source retraction: local-player collision closure

## Verdict and boundary

The corrected supplement's west-side `progress == 1.0F` witness has **no old/new player collision delta**. The old 1.10.2 ordinary block-collision path does not ask `PistonHeadBlock` for its collision shape during source retraction. It asks the moving block for `getCollisionShape`, which delegates to `MovingBlockEntity.getShape`; that shape is derived from the stored moved state and is swept across current and previous progress. For this source retract, the stored moved state is an unextended piston base, whose shape is the full cube.

There is a real provider-derived collision difference at stored progress `0.0F`, with an ordinary player box and falling request. At `0.5F` the collision sets also differ geometrically. The source closes the local-player caller and event-created block-entity path conditionally; it does not establish packet/tick interleaving for an arbitrary live session. The p0 witness therefore has the explicit dynamic premise that the client handles the source-retract block event against its piston-base state before the local player's next collision query, and that query occurs before the new moving block entity's first tick. Under that premise, p0 is queried through the ordinary local-player movement route and gives a different vertical clip. This is a bounded source result, not runtime confirmation or a complete trajectory claim.

## Old and new ordinary collision collectors

In 1.10.2, `PistonBaseBlock.doEvent` type 1 creates the source moving entity using `this.getStateFromMetadata(data)` and `extending=false` (`block/PistonBaseBlock.java:168–182`). The retraction event data is `direction.getId()` (`PistonBaseBlock.java:117–120`); `getStateFromMetadata` sets `EXTENDED` only if metadata bit 8 is set, so this stored moved state is unextended (`:347–350`). Its ordinary shape is the full cube (`PistonBaseBlock.java:48–68`; `Block.java:278–279` and `:355–359`). The legacy player path is `Entity.move` → `World.getCollisions` → `BlockState.addCollisions`; `MovingBlock.getCollisionShape` delegates to `MovingBlockEntity.getShape`, which unions the moved-state shape at `progress` and `lastProgress` (`entity/Entity.java:446–525`; `world/World.java:903–940`; `block/MovingBlock.java:132–143`; `block/entity/MovingBlockEntity.java:80–88`). The downward clip is `Box.intersectY` (`util/math/Box.java:163–183`). Thus its collision box is the full base cube translated to each of those two progress positions, then unioned. No old source-retraction branch substitutes a piston-head shape in this path.

In 1.11.2, the ordinary `Entity.move` query obtains boxes from `World.getCollisions`; the moving block's `addCollisions` delegates to `MovingBlockEntity.addCollisions` (`entity/Entity.java:459–585`; `world/World.java:960–1016`; `block/MovingBlock.java:135–152`). For `!extending && source`, the collector adds the extended piston-base box, then builds the facing `PistonHeadBlock` with its `SHORT` selector, translates its collected boxes by `getAmountExtended(progress)`, and returns those boxes (`block/entity/MovingBlockEntity.java:318–345`; `block/PistonHeadBlock.java:71–92`; `block/PistonBaseBlock.java:52–82`). The new path therefore combines base and head boxes at current progress; it is not the legacy swept full-cube `getShape` calculation.

For an east-facing source at `(0,0,0)`, Y/Z ranges omitted from the table are `[0,1]` for full blocks and `[.375,.625]` for the head arm stripe. The head plate is full-height/full-width in Y/Z. Retraction amount is `1-progress`.

| Stored progress (old `lastProgress`) | 1.10.2 old combined ordinary box | 1.11.2 source collector boxes at current progress | Consequence |
|---|---|---|---|
| `0.0` (`0.0`) | Full cube X `[1,2]` | Base full box `[0,.75]`; full arm stripe `[.75,1.75]`; plate full box `[1.75,2]` | A normal player can overlap the old full cube while missing all three new components; p0 witness below. |
| `.5` (`0.0`) | Full cubes `[.5,1.5] ∪ [1,2]`, union `[.5,2]` | Base `[0,.75]`; arm stripe `[.25,1.25]`; plate `[1.25,1.5]` | Sets differ. This table asserts geometry only; no independent p.5 reachable trajectory is relied upon. |
| `1.0` (`.5`) | Full cubes `[0,1] ∪ [.5,1.5]`, union `[0,1.5]` | Extended base `[0,.75]`; short arm stripe `[0,.75]` (contained in base); plate `[.75,1]` | The supplied west-side box ending at X `-.1` misses both old and new. Do not generalize that no-delta to every east-side pose: old swept geometry extends to X `1.5`. |

The old shape method is `getShape(world,pos,progress)` translating `movedState.getShape` by `getAmountExtended(progress) * facing`, and `getShape(world,pos)` unions both current and prior progress (`1.10.2 block/entity/MovingBlockEntity.java:80–88`). The new source selector is short at p1; its current geometry above follows the new collector and the east-facing head boxes (`1.11.2 block/entity/MovingBlockEntity.java:323–345`; `PistonHeadBlock.java:71–92`). This corrects the original p1-arm comparison: the old p1 west-side blocker is not an old head arm; it is the translated unextended-base full cube, and the supplied west-side player misses it.

## Local-player caller and p0 witness

On the 1.11.2 client, an ordinary unpaused `Minecraft.tick` enters `world.tickEntities` (`client/Minecraft.java:1395–1420`). In `World.tickEntities`, the entity pass precedes the block-entity pass (`world/World.java:1227–1325`). The local player is present in the client world (`Minecraft.java:1882–1895, 1910–1926`); `LocalClientPlayerEntity.tick` calls its superclass when its player chunk is loaded (`client/entity/living/player/LocalClientPlayerEntity.java:159–169`). A non-spectator player has `noClip=false` (`entity/living/player/PlayerEntity.java:174–177`). The ordinary dry-air living travel branch sends downward velocity through `move(MoverType.SELF,...)` (`entity/living/LivingEntity.java:1477–1499, 1798–1804`); `Entity.move` gathers the expanded movement query and clips Y through `Box.intersectY` (`entity/Entity.java:459–585`; `util/math/Box.java:205–225`). `World.getCollisions` has no logical-client exclusion and routes the actual block state to its `addCollisions` provider (`world/World.java:960–1016`).

Use an east-facing retracting source at block `(0,0,0)` and an awake, non-spectator, dry-air local player with normal `0.6 × 1.8` dimensions, AABB `X=[1.05,1.65], Y=[1.20,3.00], Z=[.70,1.30]`, and requested movement `(0,-.30,0)`. The normal dimensions follow `Entity`'s width/height and box construction (`entity/Entity.java:180–183,299–302`) and `PlayerEntity.resetPos` (`entity/living/player/PlayerEntity.java:402–406`).

Before the source retracts, a statically extended east piston does not already block this fall: its base ends at X `.75`; its arm lies at Z `[.375,.625]`, outside the player's Z interval; its plate begins at X `1.75`, beyond the player's maximum X `1.65`. If the client handles the retract event and the first local movement query runs while the new entity remains at p0, the new collector again misses: base ends at `.75`, arm misses in Z, and plate starts at `1.75`. The player therefore moves the full `-.30`, from feet Y `1.20` to `.90`.

At the corresponding old p0 state, the translated unextended-base box is full cube X `[1,2]`, Y/Z `[0,1]`. It overlaps the player's horizontal footprint and the expanded downward query. `Box.intersectY` clips the request to `1.0 - 1.20 = -.20`, leaving feet at `1.0`. Hence the p0 collision response differs by `.10` block in this query. This is caused by the new base/head provider geometry relative to the old moved-state-derived shape, not by an old PistonHead arm collision.

## Client event creation and lifetime premise

`ClientPlayNetworkHandler.handleBlockEvent` schedules client-thread handling and calls `world.addBlockEvent` (`client/network/handler/ClientPlayNetworkHandler.java:1079–1082`); `World.addBlockEvent` dispatches the current block state's event (`world/World.java:2553–2555`). When that state is the piston base, client-side `PistonBaseBlock.doEvent` skips server-only `shouldExtend` rejection, installs `MOVING_BLOCK`, and registers `MovingBlockEntity(..., extending=false, source=true)` at the piston position (`block/PistonBaseBlock.java:151–190`). For a non-sticky east piston, its old static head is removed (`:205–207`). `MovingBlock` is a `BlockWithBlockEntity`; `World.setBlockEntity` stores/registers it and `World.addBlockEntity` adds tickable entities without a client-side exclusion (`block/BlockWithBlockEntity.java:22–30`; `world/World.java:1735–1754,1379–1393`; `world/chunk/WorldChunk.java:620–631`).

The source MBE starts at progress zero. Its first block-entity tick saves `lastProgress=0`, advances progress by `.5`, and stays registered; subsequent ordinary player movement sees the updated value because the entity pass precedes block entities. It reaches one on the second such block-entity tick, and removal occurs only on the following block-entity tick (`block/entity/MovingBlockEntity.java:273–289`). Therefore the p0 query is available only if packet/event dispatch is early enough to precede the local-player query, before that first block-entity tick. The source establishes that a handled client event against the base creates the right entity, and that this entity is queryable while registered. It does not establish in this closure that network arrival always lands in that p0 window; that event-to-client-tick interleaving is the remaining dynamic premise. The supplied server path queues block events and emits `BlockEventS2CPacket` only after successful event handling (`server/world/ServerWorld.java:182–205,967–986`); client handling itself is conditional on the still-current state being the piston base.

## Scope and evidence identity

No wiki, product implementation/history, backlog, tests, build, game/runtime evidence, or canonical source was used or changed. The shared discovery, source procedure, and supplied discovery inputs remain immutable. The source trees are the exact supplied Ornithe Feather trees under `D:/Javastuff/LegacyParkourCompat/decompiled_minecraft/{1.10.2,1.11.2}/ornithe-feather`; manifest hashes in the supplied report are `91b0f478acb7b6f13463c35b268a30d2806f583402631a56f54ce2eb70d1ec71` and `d908c2af76598ad26999563bfef0089f1d3c8c32483eee6b108f0defa2773df0`.

The supplied report, audit, and correction supplement were read as inputs and not modified; their SHA-256 values are respectively `40ca4e06976857f9fdc9ed4d2e72bf5364b7fa6e980bdba57edd57de7fbd12e5`, `50b3aca791d39646125b476e7886a5f84b8a818ba4ba62b3da6331d192447596`, and `2deaea0c3c28fda56e546cda662435cd159368da9c34621c6435747269b1eefd`.

Per-source SHA-256 (direct hashes of cited files):

| Exact source | SHA-256 |
|---|---|
| 1.10.2 `block/PistonBaseBlock.java` | `4ef15129e660397ba3a531c8ff4a4810393973ea56417d8830f4445d14035e36` |
| 1.10.2 `block/MovingBlock.java` | `4f84cdf7c0d88c51ca065299c8755341d01b126d62c282c31fc0fa46bde1daf6` |
| 1.10.2 `block/entity/MovingBlockEntity.java` | `720e1305c494a3a316b21beef823158c435826f50383778a5bd00ad0e4ff6ac1` |
| 1.10.2 `block/Block.java` | `1971dbc284d511e2ed366f77bc77fd8cd07174baad3e7732e908d3daeb640c01` |
| 1.10.2 `world/World.java` | `888ed0e9de765def87b05c4126ecdf0b10e9dd448b4543dd1cb98211e9951646` |
| 1.10.2 `entity/Entity.java` | `05da145effa19a6ef7934cc276e89226373b67c12f4ce89a8ce2183f29039f77` |
| 1.10.2 `util/math/Box.java` | `f529ef075bd933b88e3bace9020a5e23cb18b65e816154520f10bd2f26f61155` |
| 1.11.2 `block/PistonBaseBlock.java` | `3a055f33949305827c3159bb5fbddf8bf51d0ec942f1b30f1fa59ff1bab9be64` |
| 1.11.2 `block/PistonHeadBlock.java` | `da30307abf1e88698a220ab3b61538be9b2f5f16d6648c8f41a2671dc06b7ed9` |
| 1.11.2 `block/MovingBlock.java` | `f2f99070cad0b64d8ecede16b44e0d0383e11c5b4e0c787edeaa2b8fc5c8af18` |
| 1.11.2 `block/entity/MovingBlockEntity.java` | `a55ee14227fe6ac0932283cba0414e946ba8e2637f543b3a3fe13d4e45b7b16e` |
| 1.11.2 `block/BlockWithBlockEntity.java` | `f62677bf00ae213f20fdd5c3b11faabc27f451c86cd2766ff5062fdbe0760cf8` |
| 1.11.2 `world/World.java` | `27cfaa5ff45c2d88c492fc5dae3ea4a2bb4536fd64bff8498a9a7d0cb82efb58` |
| 1.11.2 `world/chunk/WorldChunk.java` | `b80c4962661b31c9015faa9bb3d42e390528b59473956ba20e424c76625b844f` |
| 1.11.2 `entity/Entity.java` | `ce8104a17ce783df639cf9726e7b1cd0936563eaa7ba308603335bbe05d49440` |
| 1.11.2 `entity/living/LivingEntity.java` | `bb7dc6c9e423a9568d6433d51bba12e7aee4555fbf3fb3e2b87f618382279f2f` |
| 1.11.2 `entity/living/player/PlayerEntity.java` | `87fe94fa6cbf7aba18b9a5e3401664439eb8eba9958173da8fbcd05cc7ad948b` |
| 1.11.2 `client/Minecraft.java` | `3ca93038adf4cea512c21ae86d653376e963396640a02eb50cb42a67302f6236` |
| 1.11.2 `client/entity/living/player/LocalClientPlayerEntity.java` | `65c2747bd8c70def6be7f41f624d4c9493342b39ae7bed7967f9ff63608f59ed` |
| 1.11.2 `client/network/handler/ClientPlayNetworkHandler.java` | `65d362c00de1adfd55fae5f858b91bf9b227525b987c09a2d223279376a66175` |
| 1.11.2 `server/world/ServerWorld.java` | `e41a6335360eac61010834da4e8a62d388f743f3d73f1943b6807aeb5909c5a6` |
| 1.11.2 `util/math/Box.java` | `f788b8146b14f299ccb58ea0854609f845c298a963295d503de2e30e15d66f3a` |
