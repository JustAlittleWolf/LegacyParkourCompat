# BlockGetter member route audit — Mojmap 1.15.2 → 1.16.5

## Verdict

The added `BlockGetter.getBlockStates(AABB)` is **out of the LocalPlayer movement route**. Its exact consumers do not feed `Entity.move` collision iteration or any LocalPlayer position/velocity/bounds writer. The added `getBlockFloorHeight(BlockPos)` is also out of that client movement route: its direct consumers are vehicle/animal dismount-location methods reached through the server-only `LivingEntity.dismountVehicle` branch.

Do **not** close the whole `BlockGetter.java` row as out of movement. The added `getBlockFloorHeight(VoxelShape, Supplier<VoxelShape>)` has a direct, conditional LocalPlayer position-response route on bed wake in 1.16.5: client packet event 2 → `Player.stopSleepInBed(false, false)` → `LivingEntity.stopSleeping()` → `BedBlock.findStandUpPosition` → `DismountHelper.findSafeDismountLocation` → this overload → returned `Vec3` → `LivingEntity.setPos`. This is outside ordinary `Entity.move` collision iteration, but it writes the local player's position and also gates a dismount collision query. Keep this member movement-reachable/open pending its own paired behavior review; it is distinct from bed-entry eligibility.

## Exact inputs and integrity

Pair: exact Minecraft 1.15.2 Mojmap → 1.16.5 Mojmap. Read-only canonical source roots: `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\1.15.2\mojmap` and `...\1.16.5\mojmap`. The 1.15.2 and 1.16.5 `mojmap.ready.json` metadata identify those releases and Mojmap namespace; their ready records bind the source-manifest SHA-256 values `cb7fd93f8730d8b2813857744f8572060535c7de44fcee86222acd1ceb6f43e7` and `9499f2611d0e6dde37cb635f1a28a591416382d6bc188b37d5f99e2b2a20823b`, respectively. I rehashed each `mojmap.sources.sha256` to the corresponding value. `source-hashes.tsv` records every exact Java file used here. `input-hashes.tsv` pins the inherited route notes/reports and ready/source-manifest metadata.

## Changed members

The old `BlockGetter` has none of these methods. The 1.16.5 interface adds:

- `getBlockStates(AABB)` (`BlockGetter.java:39`): `BlockPos.betweenClosedStream(aABB).map(this::getBlockState)`.
- `getBlockFloorHeight(VoxelShape, Supplier<VoxelShape>)` (`:75–82`): return the current nonempty shape's Y maximum; otherwise lazily get the supplied shape and return its Y maximum minus 1 when at least 1, or negative infinity.
- `getBlockFloorHeight(BlockPos)` (`:84–88`): use that current block's collision shape and lazily use the block below's collision shape as fallback.

No existing `BlockGetter` member body changes between the endpoints. The 1.16.5 `Entity.move` collision collector calls `CollisionGetter.getBlockCollisions` (`Entity.java:713–723`; old `:686–696`). That collector is implemented in `CollisionGetter.java` (new `:58–70`, old `:67–78`) and queries per-position collision shapes; neither `getBlockStates(AABB)` nor either floor-height overload is in that call chain. This agrees with the existing hash-bound collision-collection residual, which closes the collector/order/default-predicate slice separately and leaves only its canonical full-block fast-path witness open.

## Member caller closure

### `getBlockStates(AABB)` — closed out of movement

Exact 1.16.5 direct/forward consumers found and inspected:

- `LevelReader.getBlockStatesIfLoaded(AABB)` (`LevelReader.java:35–42`) bounds-checks chunks, then delegates to `BlockGetter.getBlockStates`.
- `Entity.tick` (`Entity.java:579–582`) uses that stream to ask whether any covered state is in the fire tag or is lava; when none are and `remainingFireTicks <= 0`, it resets `remainingFireTicks`. This writes fire-time state only. It does not alter position, velocity, dimensions/bounds, collision selection, or a movement predicate. Its eventual burn/damage path is excluded; execution in a LocalPlayer tick does not make this read a movement sink.
- `BubbleColumnAmbientSoundHandler.tick` (`BubbleColumnAmbientSoundHandler.java:22–35`) uses the `LevelReader` wrapper to decide whether to play bubble-column ambient audio and update its sound-handler flags.
- `LlamaSpit.tick` (`LlamaSpit.java:61–63`) uses the stream in non-player projectile logic.
- `ServerGamePacketListenerImpl.handleMovePlayer` (`ServerGamePacketListenerImpl.java:425–426`) uses it in a server-side moved-too-quickly check. Historical player physics never runs on the logical server.

These usages are separate from `Entity.move`'s block collision stream. No exact consumer routes this helper's yielded block-state stream into LocalPlayer collision/query movement or a movement writer.

### `getBlockFloorHeight(BlockPos)` — closed out of client movement

All direct calls in this source pair are in 1.16.5 `Boat.getDismountLocationForPassenger` (`Boat.java:677–702`, calls at `:684–685`), `Pig.getDismountLocationForPassenger` (`Pig.java:189–215`, call at `:204`), `AbstractHorse` dismount placement (`AbstractHorse.java:1002`), and `Strider.getDismountLocationForPassenger` (`Strider.java:206–230`, call at `:230`). Each call is part of a vehicle/animal dismount-location calculation. The relevant common passenger dispatch is `LivingEntity.dismountVehicle` (`LivingEntity.java:1862–1870`), called by `LivingEntity.stopRiding` only under `!level.isClientSide` (`:2568–2574`). Consequently these direct uses run on the logical server, including when the passenger is a player; they do not execute historical local-player movement. `AbstractMinecart.getDismountLocationForPassenger` (`AbstractMinecart.java:154–204`) calls the shape/supplier overload instead and has the same vehicle/server dismount boundary.

### `getBlockFloorHeight(VoxelShape, Supplier<VoxelShape>)` — retain as movement-reachable/open

The local bed-wake route is explicit in both layers of the packet path:

1. Server `ServerPlayer.stopSleepInBed` broadcasts `ClientboundAnimatePacket(this, 2)` when sleeping (`ServerPlayer.java:849–855`); its `broadcastAndSend` call includes the originating player.
2. Client `ClientPacketListener.handleAnimate` resolves the packet entity and, for action 2, calls `player.stopSleepInBed(false, false)` (`ClientPacketListener.java:897–905`). The server sends the event to the originating player connection via `ChunkMap.TrackedEntity.broadcastAndSend` (`ChunkMap.java:1139–1143`). On that client the local entity is `LocalPlayer`, which extends `AbstractClientPlayer`, which extends `Player` (`LocalPlayer.java:79`; `AbstractClientPlayer.java:21`), so the `Player` cast reaches the local instance.
3. `Player.stopSleepInBed` calls `super.stopSleeping()` (`Player.java:1296–1302`). `LivingEntity.stopSleeping` finds the bed's stand-up position and commits it with `this.setPos(vec3x.x, vec3x.y, vec3x.z)` (`LivingEntity.java:3032–3046`).
4. `BedBlock.findStandUpPosition` calls `findStandUpPositionAtOffset`; that calls `DismountHelper.findSafeDismountLocation` (`BedBlock.java:233–284`). `DismountHelper.findSafeDismountLocation` calls the audited overload at `DismountHelper.java:82–94`. It then tests the candidate bounding box with `getBlockCollisions` before returning it. Thus the helper output participates in candidate position selection and collision-query eligibility before a direct local position write.

The older bed implementation has a different direct helper (`BedBlock.getStandingLocationAtOrBelow`, 1.15.2 `BedBlock.java:267–302`): it checks the current shape's max-Y cutoff, searches downward through empty shapes, computes support height with `+2.0E-7`, then calls `noCollision`. The 1.16.5 path instead routes through `DismountHelper` and the newly added floor-height overload. This establishes a changed local-player response route, not the exact trajectory or a finalized finding. The source audit should preserve it for paired review. Respawn-anchor use of `DismountHelper` is also present (`RespawnAnchorBlock.java:222–232`), but its player stand-up lookup is `Player.findRespawnPositionAndUseSpawnBlock(ServerLevel, ...)` (`Player.java:1314–1322`), a server-side respawn-placement path; it is not an additional client movement sink.

## Route disposition for parent reconciliation

- Close `BlockGetter.getBlockStates(AABB)` as out of movement route, with the exact consumer reasons above.
- Close `BlockGetter.getBlockFloorHeight(BlockPos)` as out of client movement route; its vehicle/animal dismount callers are behind server-only passenger dismount dispatch.
- Keep `BlockGetter.getBlockFloorHeight(VoxelShape, Supplier<VoxelShape>)` movement-reachable/open through bed-wake stand-up placement. It is not part of `Entity.move`'s normal swept-block collector, but it directly contributes to a LocalPlayer position write and candidate collision query.
- The whole `BlockGetter.java` route therefore cannot be globally closed as out of movement. No finding acceptance, pair freeze, implementation, or runtime parity is claimed. This audit made no product/wiki/backlog/build/test/game/decompile changes.
