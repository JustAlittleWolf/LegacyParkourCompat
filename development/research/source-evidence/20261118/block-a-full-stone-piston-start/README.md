# Full-stone first-tick piston witness: starting-state gate

## Scope and verdict

This checks only whether the accepted eastward full-stone first-tick witness can begin from an ordinary, non-clipping local-player state. It does not revise the abstract shape-sweep result, establish final tick parity, or examine other piston blocks/geometries.

**Verdict: the accepted raw-overlap witness is not ordinarily reachable under the stated assumptions.** For a full cube at moving-block entity position `(0,0,0)`, its required old-cube overlap is also overlap with the moving block's collision shape before the first block-entity tick. The collision scan includes that destination cell even though the carried block's original source cell is air. Ordinary collision-resolved player movement cannot produce the witness box. A player box X `[0,0.6]` legally touching the cube from the east is reachable, and the 1.10.2 and 1.11 calculations request the same `+0.51` X movement there.

## Exact inputs and integrity

Primary sources are the read-only published `decompiled_minecraft/<version>/ornithe-feather/` trees. Their exact release IDs, mapping names, Feather artifacts, input identities and source manifests are recorded in each release's `ornithe-feather.provenance.json` and `ornithe-feather.ready.json`.

| Source | Provenance highlights | Source manifest SHA-256 |
|---|---|---|
| 1.10.2 / Ornithe Feather | Feather `1.10.2+build.2`; exact run dated 2026-10-07; ready marker says version metadata `1.10.2` and client SHA-256 `7cdf7fcdc1c92584a233bf3c42bd7f0df1bdad3007d306831fe50410692be1e9` | `91b0f478acb7b6f13463c35b268a30d2806f583402631a56f54ce2eb70d1ec71` |
| 1.11 / Ornithe Feather | Feather `1.11+build.2`; exact resolved release `1.11`; client SHA-256 `f0d11987ea743f55a2c03bf77216e6dc9baea7201cc7bf498ceb18043e984634` | `8b3e5da58494f1dba77410960753b0bf646425a1ada491d8242cc71e6c7a59ca` |

Relevant source file SHA-256 values were checked against each sidecar `ornithe-feather.sources.sha256` and matched the on-disk files:

| File (under `net/minecraft/`) | 1.10.2 | 1.11 |
|---|---|---|
| `block/entity/MovingBlockEntity.java` | `720e1305c494a3a316b21beef823158c435826f50383778a5bd00ad0e4ff6ac1` | `0185504476d365cf05c91365e692497f618a1e536fc311d09211b7caefe8aa7a` |
| `block/MovingBlock.java` | `4f84cdf7c0d88c51ca065299c8755341d01b126d62c282c31fc0fa46bde1daf6` | `35799c009f0e01655622c1939e6b35342789c5abd19016c5a09528b3fac9e620` |
| `client/network/handler/ClientPlayNetworkHandler.java` | `af3efba0a1d5a0824c29c4af4ac7f8e4484d1787a82208e4d72bcc65f957e656` | `cef5d643eca0b32119571eff5201705cc8c99125f8266f763172202d89e3a40b` |
| `network/packet/s2c/play/BlockEventS2CPacket.java` | `c8ca00258bbf6df50e6e65d7293ee79b8d8d7735c555881a4092198a79d8ea42` | `c8ca00258bbf6df50e6e65d7293ee79b8d8d7735c555881a4092198a79d8ea42` |
| `server/world/ServerWorld.java` | `d7cab6f2117615943deb8ac28d981bc88bf288af1148268f501d6a59a4a489e3` | `e41a6335360eac61010834da4e8a62d388f743f3d73f1943b6807aeb5909c5a6` |
| `block/PistonBaseBlock.java` | `4ef15129e660397ba3a531c8ff4a4810393973ea56417d8830f4445d14035e36` | `9b08e98e0f194077e6d77168a7ce1a93503f4ace31429cbf42010083a89e19ea` |
| `block/Block.java` | `1971dbc284d511e2ed366f77bc77fd8cd07174baad3e7732e908d3daeb640c01` | `3fe8fdabe68da83fcfa01c0e6b8f5272fe81d71f6bf553506bdcef54757b0488` |
| `entity/Entity.java` | `05da145effa19a6ef7934cc276e89226373b67c12f4ce89a8ce2183f29039f77` | `3209feaacd1e798b521a193eeb081d92c96881a70a18254ef24a58e911a4fd97` |
| `world/World.java` | `888ed0e9de765def87b05c4126ecdf0b10e9dd448b4543dd1cb98211e9951646` | `f64905888809d5666a63c8e403f171c13907502b8d982c5b3b8fce192360ff47` |
| `client/Minecraft.java` | `0979d770e8742a07bb8c54cf7fd3449a8c43a4f48910600ffabf75f1f67ba2d9` | `1889e0f8d212d696798c5345bec6a0fea7ae0b576c7beeb96c3de04ef0acea30` |
| `util/math/Box.java` | `f529ef075bd933b88e3bace9020a5e23cb18b65e816154520f10bd2f26f61155` | `f788b8146b14f299ccb58ea0854609f845c298a963295d503de2e30e15d66f3a` |

The prior author input was read from immutable scratch-repo commit `ebe3088498db6afdd22dba7b941b4258ce28018f`, path `development/research/findings/1.10.2--1.11.2/piston-driven-player-movement.md`; its raw Git blob SHA-256 is `5b847cfefdea92dc75eea2347745df02f71794a418866589ff80afc56efe8629`. The exact blob is retained as `seed.md` beside this note.

## Ordinary piston transition and tick gates

In both releases, an unextended piston with a successful `shouldExtend`/structure-resolution check queues block event type `0` (`PistonBaseBlock.java`, 1.10.2 lines 112–118; 1.11 lines 116–122). On the server, `ServerWorld` processes the queued event, calls the block state's event handler, then sends a `BlockEventS2CPacket` after success (`ServerWorld.java`, both releases around lines 954–995). The packet's `handle` calls `ClientPlayPacketHandler.handleBlockEvent` (`BlockEventS2CPacket.java`, lines 42–43); the concrete client handler runs `PacketUtils.ensureOnSameThread` and calls `minecraft.world.addBlockEvent` (`ClientPlayNetworkHandler.java`, 1.10.2 lines 1048–1050; 1.11 lines 1065–1067). Client `World.addBlockEvent` immediately calls `doEvent` on the block state (`World.java`, 1.10.2 lines 2577–2578; 1.11 lines 2574–2575), which takes the type-0 piston path and creates the moving block/entity. Thus the ordinary server piston event has an exact client dispatch path; the handler confines world mutation to the client main thread.

The piston move sets the carried block's source cell to air, then installs a `MovingBlock` plus `MovingBlockEntity` at destination X=0 (`PistonBaseBlock.java`, 1.10.2 around lines 278–318; 1.11 lines 267–318). The entity constructor initializes both progress fields to Java's default `0.0F`; it stores the moved state and eastward facing (`MovingBlockEntity.java`, 1.10.2 lines 20–33; 1.11 lines 23–44).

A client world tick calls `World.tickEntities` from `Minecraft` (`Minecraft.java`, 1.10.2 around line 1438; 1.11 around line 1417). `World.tickEntities` processes regular entities/player ticks before its `blockEntities` loop (`World.java`, 1.10.2 lines 1270–1323; 1.11 lines 1292–1345). A nonspectator `PlayerEntity.tick` sets `noClip` to `isSpectator()` (`PlayerEntity.java`, both releases line 175), then its normal inherited tick/travel happens before the moving block entity's first tick. The ordering admits ordinary player motion up to the existing collision surface before the first piston displacement call; it does not permit crossing that surface.

At the start of the first moving-block tick, the stone's progress and last progress are both zero. The default stone/full-block shape is `[0,1]` on each axis (`Block.java`, 1.10.2 lines 47, 277–279; 1.11 lines 51, 288–290). `MovingBlockEntity.getShape` unions the current- and last-progress shapes (1.10.2 lines 80–89; 1.11 lines 96–105). The extending offset is `progress - 1`, so at zero that union is exactly the old cube, translated to the entity's position: X `[-1,0]`, Y/Z `[0,1]`.

Crucially, `MovingBlock.getCollisionShape` returns that same `MovingBlockEntity.getShape` in both versions (1.10.2 lines 134–142; 1.11 lines 129–145). The moved source cell at X=-1 is now air, but the collision roster still reaches the moving entity at destination cell X=0: `World.getCollisions` scans X cells from `floor(shape.minX)-1` inclusive to `ceil(shape.maxX)+1` exclusive (1.10.2 line 906 onward; 1.11 line 963 onward). Any queried movement box that intersects the old cube `[-1,0]` therefore includes X=0 in this scan. For that cell, `Block.addCollisions` gets the `MovingBlock` collision shape, translates its local box by block position, and adds it if it intersects the queried box (`Block.java`, 1.10.2 lines 342–352; 1.11 lines 353–363). At progress zero the resulting world collision box is exactly `[-1,0] × [0,1] × [0,1]`. `Entity.move` uses `World.getCollisions` on its swept shape and clips movement against returned boxes (`Entity.java`, 1.10.2 around lines 446–548; 1.11 lines 455–747); the scan is direction-independent for any swept shape reaching that volume. `Box.intersects` is strict on all faces (1.10.2 lines 203–208; 1.11 lines 245–250). So ordinary non-clipping movement may touch the old cube boundary, but cannot move the player box into its interior before the first moving-block tick.

## Concrete comparison and exclusion

The accepted abstract witness box X `[-0.8,-0.2]` overlaps the old cube X `[-1,0]`. Before the moving-block tick it would overlap the moving block's collision shape, so it cannot be reached by ordinary non-clipping motion. This is not cured by the source block having become air: the moving-block entity at the destination supplies collision for the old cube until it advances.

For a reachable boundary case, let the player's box have X `[0,0.6]` and Y/Z intervals that overlap `[0,1]` (the player touches the moving block's east face without intersecting it). On the first extension tick:

- 1.10.2 advances progress to `0.5` before `moveEntities`, whose broad union has max X `0.5`; its eastward request is `0.5 - 0 + 0.01 = +0.51` (`MovingBlockEntity.java` lines 91–146, 166–183).
- 1.11 calls `moveEntities(f)` with `f=0.5` while `progress` is still `0`, builds the eastward movement area `[0,0.5]`, intersects the same player box, and requests `min(0.5,0.5)+0.01 = +0.51`; it then stores progress `0.5` (`MovingBlockEntity.java` lines 116–169, 183–208, 252–268).

This is a legal starting state, but it produces no difference in requested displacement. The abstract 1.10.2-only request requires overlap in the old/trailing part of the full cube, which ordinary collision-resolved movement excludes at progress zero. No source evidence here supports treating that request as an ordinary player path under the assignment's no-clipping/no-inside-spawn assumptions.

## Limits

This is source-reachability analysis plus the accepted box-sweep arithmetic, not runtime parity. It assumes the listed ordinary state excludes preexisting penetration, spawn/teleport insertion into the cube, clipping, and any external position writer that bypasses normal collision. Those exceptional paths were not pursued. It does not prove reachability or absence for non-full-cube blocks, other facings, a later progress tick, or a distinct starting-state witness.
