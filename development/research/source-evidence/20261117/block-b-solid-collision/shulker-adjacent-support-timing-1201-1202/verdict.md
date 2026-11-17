# Independent exact-source review: Shulker adjacent support timing

**Review target:** source finding revision SHA-256 `4ccb4c3da8862948ee7cd74c0d4437e40c2ccd5749540e1128ebde89294d8749` (identity supplied by the assigning orchestrator; review scope is the bounded claim in the assignment).

**Verdict:** Accept the bounded claim that opening a Shulker Box changes adjacent support-derived block state at different times in 1.20.1 and 1.20.2. This is a vanilla world-state/collision-shape timing difference, not a movement-equation difference for identical world states. The east-facing shulker / north oak fence witness is valid. Fence `SOUTH` recomputation is caused by the neighbor-shape route. The separately added `Level.updateNeighborsAt` call is a generic neighbor-change route and is not the route that flips this fence bit; its effects on other blocks are outside this finding's bounded review.

## Source identity and integrity

Both exact source sets are ready Mojmap/Vineflower outputs for the named releases. Their ready markers’ source-manifest hashes match the actual manifest files, and targeted source file hashes match their manifest entries.

- 1.20.1: `decompiled_minecraft/1.20.1/mojmap`, ready marker manifest SHA-256 `858b56764113e591c60d09bc63e4d90f9c1d67a705645f0c793e8b28f2378465`.
- 1.20.2: `decompiled_minecraft/1.20.2/mojmap`, ready marker manifest SHA-256 `e439ff7e1d3f1d31e01be1edd141f94d917a9dc7fa0de5f69904544cbbe62887`.
- Both are `mojmap`; provenance records the exact version IDs and shared decompiler pipeline. The complete targeted source-file SHA-256 set was checked against each version’s manifest during this review.

## Derivation

`ShulkerBoxBlock.getBlockSupportShape` is unchanged in the pair (`1.20.1`, lines 229–235; `1.20.2`, lines 225–231). It returns `Shapes.block()` only while the block entity `isClosed()`. Both `isClosed` methods test exactly `animationStatus == CLOSED` (`1.20.1` ShulkerBoxBlockEntity lines 252–254; `1.20.2` lines 258–260). For every other status, the support shape is selected by `FACING.getOpposite()`. East-facing therefore selects the west-face slab (`WES_OPEN_AABB`, `Block.box(0,0,0,1,16,16)`), which leaves the west face full but does not provide a full north face.

An oak fence north of the shulker computes its `SOUTH` property from the south neighbor’s north-face `isFaceSturdy` result. Its `updateShape` repeats that computation when the changed neighbor is south (`FenceBlock` lines 53–58, 87–103 in both versions). `SupportType.FULL` delegates to `Block.isFaceFull` on the neighbor’s block-support shape (`SupportType` lines 11–16). Consequently, the closed full-cube support connects the fence’s `SOUTH` arm; after the shulker enters `OPENING`, the west-only support does not, and the neighbor shape callback sets `SOUTH=false`.

The timing differs at the event and block-entity phases:

- **1.20.1:** `triggerEvent(1, 1)` sets status to `OPENING` and immediately calls `doNeighborUpdates`; that helper calls `updateNeighbourShapes` (`ShulkerBoxBlockEntity` lines 130–151). The fence therefore loses `SOUTH` as the open block event is handled. The animation tick also updates neighbors at the `OPENED` endpoint. For close event `1, 0`, status becomes `CLOSING` and a neighbor update runs immediately, but support remains partial because `CLOSING` is not `CLOSED`; the endpoint update reconnects the fence only when status becomes `CLOSED`.
- **1.20.2:** the event handler only sets `OPENING` or `CLOSING` (`ShulkerBoxBlockEntity` lines 137–157). On opening, the first block-entity animation tick increments progress, then calls `doNeighborUpdates` when `progressOld == 0.0F`; this occurs before the same tick’s `moveCollidedEntities` call (lines 66–100, 111–128). It is therefore after the world’s entity phase. On closing from fully open, it updates at `progressOld == 1.0F`, while status is still `CLOSING` and support remains partial; at the endpoint it sets `CLOSED` and updates again, reconnecting the fence. A close-event update does not reconnect the fence in either release.

In both releases, `BlockBehaviour.BlockState.updateNeighbourShapes` visits adjacent positions and calls `neighborShapeChanged` (lines 656–667); `Level.neighborShapeChanged` enters the shape updater, which calls the adjacent state’s `updateShape` (`Level` lines 314–317; `NeighborUpdater` lines 33–36). That is the route that updates the fence property. 1.20.2 additionally calls `Level.updateNeighborsAt` from `doNeighborUpdates`; `ServerLevel` forwards this through generic `neighborChanged` callbacks. It is a separate route and is not needed for the fence’s `SOUTH` recomputation.

## Tick and packet ordering

On the server, `ServerLevel.tick` drains block events before ticking entities and calls `tickBlockEntities` after the entity phase (`1.20.1` lines 331–375; `1.20.2` lines 330–374). A server block event is queued by `ServerLevel.blockEvent`, processed by `runBlockEvents`, and broadcast after a successful `triggerEvent` (`1.20.1` lines 1040–1073; `1.20.2` lines 1046–1078). Thus, the 1.20.1 support update happens in the block-event phase before server entities; in 1.20.2 the event changes animation status before entities, but the support-derived fence state changes in the subsequent block-entity phase, after that entity pass.

On the client, `ClientPacketListener.handleBlockEvent` first uses `PacketUtils.ensureRunningOnSameThread`, then calls `ClientLevel.blockEvent` (`1.20.1` lines 1274–1277; `1.20.2` lines 1227–1230). The level implementation calls the block state’s `triggerEvent` synchronously. When a queued packet handler runs before the client tick, `Minecraft.runTick` drains scheduled tasks before `Minecraft.tick` (`1.20.1` lines 1060–1070; `1.20.2` lines 1113–1123), and `Minecraft.tick` calls `ClientLevel.tickEntities`; that method ticks entities first and block entities second (`ClientLevel` lines 244–254 in both releases). This gives the local client a stale connected fence for the entity pass after a pre-pass 1.20.2 event, until that local shulker block entity’s first opening tick. In 1.20.1 the event handler itself updates the fence before that entity pass. If packet handling occurs after a local entity pass, that pass cannot be retroactively affected.

There is also server-state reconciliation: neighbor shape updates that change the fence state mark the block changed; the chunk holder broadcasts changed states during `ServerChunkCache.tick` (`ChunkHolder` lines 207–245; `ServerChunkCache` line 390). That chunk-source tick runs before server block events, entities, and block entities. The event packet is therefore broadcast in the event phase, while a fence-state packet resulting from that phase’s or later BE phase’s shape update is sent through the next chunk broadcast. `handleBlockUpdate` applies the server-verified state on the client (`ClientPacketListener` lines 771–774 in 1.20.1; 726–729 in 1.20.2). Exact receipt relative to an individual local entity pass depends on when the queued network task runs; it is not source-supported to claim a fixed wall-clock stale duration. The event-first, local-entity-before-local-BE ordering is source-established.

## Movement relevance and witness

The fence’s connection properties select its collision shape: `FenceBlock` extends `CrossCollisionBlock`, whose collision shape is assembled from the central post plus the enabled direction arms (`CrossCollisionBlock` lines 28–35, 64–75). With `SOUTH=true`, the south arm has a collision volume outside the central post. A stable loaded witness is a full floor at y=-1; an east-facing shulker at `(0,0,0)`; a dry oak fence at `(0,0,-1)` with `SOUTH=true`; air to the east for the lid; and a normal player with feet at `(0.5,1.5,-0.05)`. The player footprint overlaps the exposed south-arm top at y=1.5 but not the central post. When `SOUTH` becomes false, that supporting collision portion disappears; a subsequent player movement tick can lose ground contact/fall. The player is vertically clear of the shulker’s box/lid volume, so this witness isolates the fence collision change rather than Shulker push displacement.

The Shulker’s `moveCollidedEntities` math is present in both versions with the same progress-delta AABB, eligibility check, direction, and displacement expression (`1.20.1` lines 103–121; `1.20.2` lines 111–128). It does not explain the finding’s difference. The supported player-movement consequence is contingent on contact with the affected fence arm; this review does not claim a trajectory change for players outside that collision region, nor that historical movement runs on a server.

## Exact source references

All paths below are relative to each version’s `mojmap/` source root.

- `net/minecraft/world/level/block/ShulkerBoxBlock.java`: 1.20.1 `getBlockSupportShape` 229–235; 1.20.2 225–231.
- `net/minecraft/world/level/block/entity/ShulkerBoxBlockEntity.java`: 1.20.1 animation/event/helper 62–93, 130–151, `isClosed` 252–254; 1.20.2 animation/event/helper 62–100, 137–157, `isClosed` 258–260.
- `net/minecraft/world/level/block/FenceBlock.java`: connection rules and shape update 53–58, 87–103 (unchanged pair).
- `net/minecraft/world/level/block/CrossCollisionBlock.java`: collision shape construction and state-indexed selection 28–35, 43–75 (unchanged pair).
- `net/minecraft/world/level/block/SupportType.java`: FULL support test 11–16 (unchanged pair).
- `net/minecraft/world/level/block/state/BlockBehaviour.java`: neighbor shape walk 656–667 (unchanged pair).
- `net/minecraft/world/level/redstone/NeighborUpdater.java`: shape callback and generic callback execution 33–42 (unchanged pair).
- `net/minecraft/world/level/Level.java`: `neighborShapeChanged` 314–317; direct `blockEvent` 718–720; BE tick dispatch 424–440 (unchanged pair).
- `net/minecraft/server/level/ServerLevel.java`: world phase order 1.20.1 331–375, 1.20.2 330–374; queued block event and broadcast 1.20.1 1040–1073, 1.20.2 1046–1078.
- `net/minecraft/client/multiplayer/ClientPacketListener.java`: block-event handlers 1.20.1 1274–1277, 1.20.2 1227–1230; block-state reconciliation handlers 1.20.1 771–774, 1.20.2 726–729.
- `net/minecraft/client/multiplayer/ClientLevel.java`: entity then BE phase 244–254 (unchanged pair).
- `net/minecraft/client/Minecraft.java`: scheduled-task drain before client tick 1.20.1 1060–1070, 1.20.2 1113–1123; world entity phase before level block tick 1.20.1 1746–1760, 1783–1785; 1.20.2 1792–1806, 1827–1831.
- `net/minecraft/server/level/ChunkHolder.java`: changed-block packet publication 1.20.1 197–235, 1.20.2 207–245.
- `net/minecraft/server/level/ServerChunkCache.java`: `broadcastChanges` dispatch line 390 in both; `ServerLevel.tick` invokes chunk source before block-event/entity/BE phases.
- `net/minecraft/world/entity/monster/Shulker.java`: progress-delta AABB 250–255 (unchanged pair).
