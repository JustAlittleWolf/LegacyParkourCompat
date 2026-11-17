# MovingBlock payload and local-player collision caller: 1.10.2 → 1.11

**Disposition: bounded dynamic payload and caller dependency source-closed.** This closes how a client-side moving-block state reaches the collision callback used by player movement, including the queried-entity propagation and `PistonHeadBlock.SHORT` state construction. It reuses the accepted provider inventory for nested provider behavior; it does not repeat piston displacement formulas or establish a particular map trajectory. The pair remains partial under its broader coverage report.

## Exact source identity

The pair coverage record binds `decompiled_minecraft/1.10.2/ornithe-feather/` to ready marker `4c6d236e1d06ffc365452c48e73dfa2bf2414bbf4ea67a24a7010c5583906a2d` and source manifest `91b0f478acb7b6f13463c35b268a30d2806f583402631a56f54ce2eb70d1ec71` (1,845/1,845 manifest rows match). It binds `decompiled_minecraft/1.11/ornithe-feather/` to ready marker `3c4102230dba0f9d2d07f752bbd2a19d2717375f6e31fb618c0450b7fd82bd45` and source manifest `8b3e5da58494f1dba77410960753b0bf646425a1ada491d8242cc71e6c7a59ca` (1,918/1,918 rows match). Each source hash below was recomputed from the exact file and checked against its release manifest row.

| Source file | 1.10.2 SHA-256 | 1.11 SHA-256 |
| --- | --- | --- |
| `net/minecraft/block/PistonBaseBlock.java` | `4ef15129e660397ba3a531c8ff4a4810393973ea56417d8830f4445d14035e36` | `9b08e98e0f194077e6d77168a7ce1a93503f4ace31429cbf42010083a89e19ea` |
| `net/minecraft/block/MovingBlock.java` | `4f84cdf7c0d88c51ca065299c8755341d01b126d62c282c31fc0fa46bde1daf6` | `35799c009f0e01655622c1939e6b35342789c5abd19016c5a09528b3fac9e620` |
| `net/minecraft/block/entity/MovingBlockEntity.java` | `720e1305c494a3a316b21beef823158c435826f50383778a5bd00ad0e4ff6ac1` | `0185504476d365cf05c91365e692497f618a1e536fc311d09211b7caefe8aa7a` |
| `net/minecraft/world/World.java` | `888ed0e9de765def87b05c4126ecdf0b10e9dd448b4543dd1cb98211e9951646` | `f64905888809d5666a63c8e403f171c13907502b8d982c5b3b8fce192360ff47` |
| `net/minecraft/entity/Entity.java` | `05da145effa19a6ef7934cc276e89226373b67c12f4ce89a8ce2183f29039f77` | `3209feaacd1e798b521a193eeb081d92c96881a70a18254ef24a58e911a4fd97` |
| `net/minecraft/entity/EntityFilter.java` | `91158a5477935c911178433e1b2628d1d063604a23b08baee4706dd286e7e813` | `49f2c3cbeea0bb9421dfaad49a7742394f69cd73981a9cdd51e1badcf30c629` |
| `net/minecraft/client/Minecraft.java` | `0979d770e8742a07bb8c54cf7fd3449a8c43a4f48910600ffabf75f1f67ba2d9` | `1889e0f8d212d696798c5345bec6a0fea7ae0b576c7beeb96c3de04ef0acea30` |

`World.java` collision-boundary and `MovingBlockEntity.java` pair hashes also appear in the audited provider inventory and pair coverage report. The bounded provider inventory verdict is `ACCEPT`; that audit explicitly left this payload/caller gate open.

## Payload acquisition and client ticking

In both releases, `PistonBaseBlock.doEvent` (1.10.2 line 147; 1.11 line 150) guards the server-only event validation with `if (!world.isClient)`, then executes the piston event branch in either world side. Its `move` method (1.10.2 line 278; 1.11 line 267) takes the resolver's ordered movable positions, reads each source `BlockState`, resolves its virtual properties at that position, and stores that resolved state in a parallel list. As each position is replaced by `MOVING_BLOCK`, `MovingBlock.createMovingBlockEntity` constructs a `MovingBlockEntity` with that captured `movedState`, facing, extension flag, and source flag. Extension also creates a moving piston-head entity; retraction creates a source entity carrying the piston base state. This producer and state transfer are the same in the compared method bodies; the patch changes the consumers below.

`MovingBlockEntity` retains the payload in `movedState`; its constructor assigns the state directly. `readNbt`/`writeNbt` serialize its block ID and metadata together with facing and progress flags. The client tick loop calls `world.tickEntities()` (Minecraft.java:1438 in 1.10.2; :1417 in 1.11). `World.tickEntities` iterates `tickingBlockEntities` and invokes `Tickable.tick()` for loaded, in-border entities on either world side; the examined loop has no server-only gate. Thus a client-created or loaded moving block entity can run the dynamic provider and entity response path. This establishes the source caller, not that any chosen world actually produced the payload.

## Collision query and changed dispatch

The shared movement caller is `Entity.move`: it queries `World.getCollisions(this, queryBox)` for its collision tests (1.10.2 `Entity.java`:446 and subsequent clipping calls; 1.11 `Entity.java`:455 and subsequent clipping calls). The independently audited `World.getCollisions(Entity, Box)` loop is unchanged in its bounds, chunk/border selection, state callback and entity-vs-entity append behavior (1.10.2 `World.java`:903; 1.11 :960). It passes the query entity to the block-state collision callback.

At the provider boundary, 1.10.2 `MovingBlock.getCollisionShape` (line 134) fetches the block entity and returns null if absent, otherwise `MovingBlockEntity.getShape`; it inherits the base single-shape `addCollisions` behavior. That shape unions the moved state's provider box sampled at current and last progress, translating each along facing. 1.11 retains the lookup/fallback on `getCollisionShape`, but adds `MovingBlock.addCollisions` (line 135), which dispatches to `MovingBlockEntity.addCollisions(world,pos,query,output,entity)` when the entity exists. The entity parameter reaches the nested `BlockState.addCollisions` calls, so the dynamic payload uses each selected state's own provider callback/filter and not a render outline or one flattened `MovingBlock` box.

For a non-source moving entity, 1.11 selects its stored `movedState`. For a retracting source it synthesizes a piston-head state from the moved piston kind and facing in `getStateForShape`; the provider callback separately emits the extended piston base and a piston-head state, including `SHORT` computed from extension/progress. The nested state collision boxes are queried in coordinates translated opposite the movement offset and appended boxes are then translated back. During the entity's own push call, the thread-local no-clip gate suppresses its moving-head shape while progress is below 1.0; other world collision boxes remain in the query. This is a collision-query gate, not a state-unavailability rule for ordinary player movement calls.

Both entity-response queries pass `null` as the excluded entity to `World.getEntities`. That overload defaults to `EntityFilter.NOT_SPECTATOR`; a player in spectator mode is excluded, while ordinary players are eligible. The existing candidate correction binds its further `noClip == false` requirement to the movement response. This report does not repeat the push-distance or movement-source formulas.

## Reachable state domain and disposition

The client producer stores resolver-selected vanilla states after `resolveVirtualProperties`; the synthetic moving-head/source states are additional dynamic cases on 1.11. Consequently the changed callback can route into the old-era provider families already covered in `dependencies/provider-inventory.md`, including state-dependent shapes and multipart providers. The player query supplies a non-null `Entity`; 1.11's internal precomputation for its own piston push supplies null. No evidence here widens the provider inventory to visual-only outlines or modern-only providers. The already accepted provider inventory bounds per-provider changes (including cocoa and the `SHORT` arm distinction), while this report closes how those stored/provider states reach local-player collision queries.

**Changed:** 1.11 has the entity-aware dynamic dispatch and per-state translated collision boxes, plus the source-retraction/head state selection described above; 1.10.2 uses the inherited flattened shape path. **Unchanged:** client piston-event branching, resolver-state capture, tick registration/loaded-chunk gate, entity-query non-spectator filter, and the `World.getCollisions` caller boundary are unchanged in the examined methods.

The dynamic payload/player-query dependency is source-closed for this pair. Still open outside this bounded dependency: exact ordinary map trajectory and collision clipping result, other pair tick/response/effect/fluid gates, and full-pair independent coverage freeze. No claim is made about net player displacement from an arbitrary piston setup.

