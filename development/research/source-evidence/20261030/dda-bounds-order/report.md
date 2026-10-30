# 1.8.9 shared bounds lifecycle: chest, trapped chest, anvil

## Scope and result

This source-only closure traces the ordinary local-player movement reader for numeric block IDs 54 (Chest), 146 (Trapped Chest), and 145 (Anvil), and the client camera/render paths that can write their shared `Block` bounds. It reuses the accepted 14-store scan and accepted caller inventories rather than repeating their whole-export search.

The relevant bounds live on three registered singleton objects: ID 54's `ChestBlock(0)`, ID 146's separate `ChestBlock(1)`, and ID 145's `AnvilBlock`. A shape refresh writes the object fields for one world position. The inherited movement collision path later reads those fields without refreshing them. Therefore stale bounds can survive across positions, across frames, and into a later local-player movement query; source defines no automatic invalidation or universal reset for these three classes. A fresh `updateShape(world, pos)` on the same instance immediately before reading that same position is the condition that makes the cached values correspond to that position's present state/topology. Ordinary movement collision collection does not supply that condition.

## Exact ordinary tick order

In 1.8.9, `Minecraft.tick()` calls `gameRenderer.pick(1.0F)` at `Minecraft.java:1314`, before `world.tickEntities()` at `:1635`. The pick dispatches through `GameRenderer.pick` to the active camera's `Entity.rayTrace` (`GameRenderer.java:318-325`; `Entity.java:1029-1034`), which calls `World.rayTrace` with flags `false,false,true`. For each eligible traversed cell, `World.rayTrace` dispatches to `Block.rayTrace`; `Block.rayTrace` calls virtual `updateShape(world,pos)` before reading the instance bounds for its plane intersections (`World.java:633-761`; `Block.java:456-465`). The ray can stop at the first hit, so it does not refresh same-type blocks beyond the visited prefix. A miss still leaves the last visited provider's fields written.

`world.tickEntities()` runs later in that same tick (`World.java:1159-1168`), including the local player. `LocalClientPlayerEntity.tick()` delegates to its superclass (`LocalClientPlayerEntity.java:105+`); ordinary entity movement reaches `Entity.move`. In `Entity.move`, sneak-edge checks may query collisions first, the main collision list is collected at `Entity.java:439`, and a step-up candidate can collect another list at `:469`. These calls are after the tick camera pick. `Entity.move` applies collision list boxes to the Y, X, and Z components (`:442-459`) and reuses each returned list without shape refresh.

For this pair of block providers, `World.getCollisions` scans the requested voxel range and calls `blockState2.getBlock().addCollisions(...)` (`World.java:891-923`). Neither `ChestBlock` nor `AnvilBlock` overrides the inherited `Block.addCollisions` or `Block.getCollisionShape` methods. The base provider obtains one box from the current object's fields and intersects it with the query (`Block.java:339-350`); the getter does not call `updateShape`. Thus, if the camera ray last wrote the chest/anvil singleton at position P, and movement then reads that same singleton at position Q, the movement query sees P's cached local bounds while translating them by Q's coordinates. The world iteration itself causes no invalidation or per-position rewrite.

The local-player's four `pushAwayFrom` calls are not stale-bound readers: its override checks `canSurvive`/`Block.isSolid()` and does not call `World.getBlockCollisions` or `World.isFullCube` (`LocalClientPlayerEntity.java:292-345,546-549`). The movement reader in scope is `Entity.move` → `World.getCollisions` → `Block.addCollisions` → `Block.getCollisionShape`.

## Render and camera writers between movement reads

The normal render path adds refreshes, but not a validity boundary:

- `GameRenderer.renderWorld` calls `pick(tickDelta)` (`GameRenderer.java:1012-1019`), which uses the same camera ray path and can write bounds. When this render call occurs relative to successive client ticks is not ordered by `Minecraft.tick()` itself.
- Third-person camera obstruction performs up to eight `World.rayTrace` calls (`GameRenderer.java:521-536`). Each traversed block ray can call `updateShape` and leave the last write on the registered instance.
- The selected block outline directly calls `block.updateShape(world,pos)` and then reads `getOutlineShape` (`WorldRenderer.java:1684-1691`), independent of `World.rayTrace`.
- Ordinary block-model rendering calls `block.updateShape(world,pos)` (`BlockModelRenderer.java:25-29`). Its other `render(BakedModel, BlockState, ...)` overload calls `block.resetShape()` (`:282-285`), but `Block.resetShape()` is empty and neither `ChestBlock` nor `AnvilBlock` overrides it. It is not restoration for these instances.

These calls are additional writes, not movement-time reads with guaranteed position alignment. For example, outline/render of a different block position using the same singleton can replace a previously correct write; if the next movement collision query reads a different position before that position is refreshed, the cached shape remains stale. Camera picking before entity ticks may refresh a position in the ray's visited prefix, but it does not prove that every movement candidate position was visited or that the last writer position equals every later collision position.

## Per-instance writer semantics

- ID 54 and ID 146 register different chest objects (`Block.java:942,1142`). `ChestBlock.updateShape` uses first-match neighbor identity in NORTH, SOUTH, WEST, EAST order and writes its `this` bounds; the fallback is the single-chest bounds (`ChestBlock.java:30-68`). The update is position/topology-dependent, so a write for a neighboring pair or single at P can differ from Q. `Block.rayTrace` refreshes it before ray clipping, while movement collision does not.
- ID 145 is a separate `AnvilBlock` singleton (`Block.java:1141`). Its `updateShape` reads the current state's FACING axis at the supplied position and writes one of two horizontal boxes (`AnvilBlock.java:67-73`). It does not depend on damage. A ray/render refresh for an X-axis anvil can therefore remain stored when movement later reads a Z-axis anvil of the same registered block.
- The chest constructor seeds single-chest bounds (`ChestBlock.java:30-37`); the base `Block` constructor seeds a full cube (`Block.java:197-205`), which is the anvil's initial instance bounds. These are construction-time defaults, not a per-query recovery protocol.

The accepted writer choke-point audit found only 14 vanilla source stores to the six fields (six `Block.setShape`, six `MovingBlock`, one `StemBlock`, one `WallBlock`), but it also proves a `setShape`-only observer is incomplete and the base reset is empty. Those facts bound potential instrumentation; this report does not claim a cache observer exists.

## Current-source schedule read contract

Only the scheduling contract was compared in 26.2. `Minecraft.tick()` calls `pick(1.0F)` at line 1787 before `level.tickEntities()` at line 1809. This matches the old tick-level order relevant here. It is not evidence that current collision shapes use the old mutable-bounds cache, nor does it establish a cross-version adapter.

## Closed dependencies and limits

Closed: exact 1.8.9 singleton identity for IDs 54/146/145; camera tick pick before entity-tick movement; ordinary movement collision collection and inherited base shape read; rendering/camera refresh paths that can write the same fields; absence of a class-specific reset for the two chest classes and anvil; the stale-survival condition (a last write for P persists when a later collision read at Q occurs without intervening `updateShape(world,Q)`).

Limits: source reachability/order only; no runtime observation, schedule of asynchronous chunk/render work, exact camera ray visitation, guaranteed rendered-block coverage, all possible modded/transformed writers, or whole-session cache validity is claimed. The source proves stale bounds **can** persist under the stated P-to-Q ordering; it does not claim that every ordinary player tick or a particular trajectory necessarily encounters such a mismatch. Current 26.2 evidence is limited to the matching tick schedule read contract. No product files/history, wiki/backlog, tests, build, game launch, or runtime were inspected or used.

## Exact source identities

The 1.8.9 source is the read-only canonical Ornithe Feather Gen2 build 2 export at `D:/Javastuff/LegacyParkourCompat/decompiled_minecraft/1.8.9/ornithe-feather`; its source manifest SHA-256 is `9E75F46DC0ED43B6A355BD65DB8A92C93A4DFEAECFA92284187C6FE9410D8004`. The 26.2 source is the read-only canonical unobfuscated export at `D:/Javastuff/LegacyParkourCompat/decompiled_minecraft/26.2/unobfuscated`; the compared `Minecraft.java` digest is in `source-hashes.sha256`. The evidence hashes for this report, accepted inventories and independent reviews are in `accepted-input-hashes.sha256`.
