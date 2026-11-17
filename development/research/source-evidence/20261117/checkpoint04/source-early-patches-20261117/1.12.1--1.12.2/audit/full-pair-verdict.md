# Independent full-pair source audit: 1.12.1 → 1.12.2

**Verdict: ACCEPT the report as accurately bounded PARTIAL source coverage; the pair is not frozen.**

## Bound evidence

- Pair: exact `decompiled_minecraft/1.12.1/ornithe-feather/` → `decompiled_minecraft/1.12.2/ornithe-feather/` sources.
- Coverage report: `build/source-early-patches-20261117/1.12.1--1.12.2/coverage.md`, SHA-256 `04822D49DAD2A4B6DE9C552C89E3626873D1F91CA470A29E7695B0776F079F0A`.
- 1.12.1 mapping-local provenance SHA-256 `0FF5246E8D29C04554CC9EC93AE9B4ABEF539652325B6C0AC822851D7186A9C8`.
- 1.12.2 root readiness SHA-256 `B0AEEC721E5C0AF02B33D9E217CB8272889FA139DED1ECC2A31E42B930138F7A`; source manifest SHA-256 `B8A37CCFCCD2AAC5F40F5E34FEC85873DBDFA93A266043C103E592E4A8C949DA`.

## Independent checks

The 1.12.1 and 1.12.2 source inventories each contain 2,050 entries; every source entry matches its assigned manifest. Their path sets are equal, with 37 changed Java paths and no additions/removals. The complete changed-path set matches the report. Core entity/player tick and movement bodies remain byte-identical. The report’s classifications for DataVersion-only migration changes, EndPortal’s equivalent server gate, the server/projectile-only EnderPearl path, the inventory-screen closure, and client ambient-audio behavior are supported; no additional player movement operation is established by those rows. Farmland’s new direct position adjustment is not a local-client movement route: scheduled block ticks are dispatched by `ServerWorld`, `FarmlandBlock.onFallenOn` begins with `!world.isClient`, and block `onAdded`/neighbor update routes are server-gated.

## Remaining conditional dependency

The `World.tickEntities()` cleanup-order difference is source-supported. In 1.12.1 the `removedBlockEntities` list is cleared after the block-entity loop; in 1.12.2 it is cleared before that loop. `WorldChunk.unload()` sets `loaded=false` and calls `World.unloadBlockEntity()`, which only queues the block entity and does not mark it removed. The loop otherwise requires `!isRemoved()`, `hasWorld()`, `isChunkLoaded(pos)`, and world-border containment. `World.isChunkLoaded(pos)` passes `allowEmpty=true`, and client `ClientWorld.isChunkLoadedAt(..., true)` returns true by the allow-empty branch. Thus an unloaded chunk’s queued, still-attached block entity can pass those checks once under the old order and be skipped under the new order.

The caller is reachable on the client: `Minecraft.tick()` invokes `world.tickEntities()` before `ClientWorld.tick()`. `MovingBlockEntity.java` is byte-identical across the pair; its tick can call `moveEntities()`, which can move an intersecting player through `Entity.move`. This is only a conditional source route. The exact ordinary client chunk-unload schedule does not establish that such a still-attached moving block entity overlaps the local player at the extra tick. No movement finding is accepted from this possibility.

## Acceptance boundary

The report correctly leaves this one dependency open and the exact pair **PARTIAL / NOT FROZEN**. The source inventory otherwise closes the listed changed paths and exclusions. The report is accepted as a partial coverage snapshot only; ordinary-player reachability and resulting trajectory effect remain unproven. No implementation, build, test, game, TAS, Gym, server, or runtime check was performed.
