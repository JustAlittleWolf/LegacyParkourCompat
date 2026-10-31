# Independent receiver review: mounted player correction (1.15.2 → 1.16.5)

## Verdict

**ACCEPT**, with a wording clarification for operation order. The retained receiver delta is source-confirmed, reachable under its own receiver gate, directly changes the local player's mounted state, and is in movement scope. This is an endpoint finding only; it does not identify the intermediate patch boundary or establish runtime parity.

## Exact evidence identity

Read-only exact sources are under `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\{1.15.2,1.16.5}\mojmap`. Both parent `mojmap.ready.json` markers identify the expected exact `versionId`, `mapping=mojmap`, `status=ready`, Vineflower, and `mojmap.sources.sha256`. The source manifest hashes independently recomputed to the values recorded in the disposition report: 1.15.2 `cb7fd93f8730d8b2813857744f8572060535c7de44fcee86222acd1ceb6f43e7`; 1.16.5 `9499f2611d0e6dde37cb635f1a28a591416382d6bc188b37d5f99e2b2a20823b`.

I recomputed SHA-256 for `ClientPacketListener.java`, `Entity.java` and `LocalPlayer.java` on both sides; all six match their corresponding manifest entries. The ready/provenance metadata does not embed mapping artifact coordinates (`mappingArtifacts` is null per the disposition report), so this review confirms the local Mojmap source identity, not external mapping publication provenance.

## Receiver comparison

- 1.15.2 `net/minecraft/client/multiplayer/ClientPacketListener.java`, `handleMovePlayer`, lines 669–727: the receiver binds `Player player = this.minecraft.player`, derives relative/absolute correction coordinates and velocity, then applies `setPosRaw`, previous-position fields and delta movement. There is no vehicle-detach gate or call in the body.
- 1.16.5 same path/member, lines 671–733: after the same relative-coordinate bookkeeping (including `xOld/yOld/zOld` writes), the new lines 714–716 test exactly `player.tickCount > 0 && player.getVehicle() != null` and call `player.removeVehicle()`. This precedes `setPosRaw`, `xo/yo/zo`, `setDeltaMovement` and the final `absMoveTo` correction. Thus “pre-write” is accurate for the correction's current position/velocity writes, but should not imply the detach runs before *all* receiver writes; the old-coordinate bookkeeping has already happened.
- 1.16.5 `net/minecraft/world/entity/Entity.java`, `removeVehicle`, lines 1650–1656: when the vehicle exists it stores it, clears the player's `vehicle` field, and removes the player from that entity's passenger list. `isPassenger()` is defined as `getVehicle() != null` at lines 1793–1795. This is an actual local player state transition, not a server-only placement computation.
- The state is movement-relevant: 1.16.5 `client/player/LocalPlayer.java` uses `isPassenger()` in local tick and movement-input handling (lines 187–191, 242–246, 643–647, 725–729), and `rideTick()` routes boat input through the current vehicle (837–845). Clearing the vehicle can therefore change subsequent local player movement/tick branches. The finding claims changed state/branch eligibility, not a particular trajectory.

The route is the direct client receiver: `ClientboundPlayerPositionPacket.handle(ClientGamePacketListener)` calls `clientGamePacketListener.handleMovePlayer(this)` in both exact sources (`ClientboundPlayerPositionPacket.java:53–55`); `ClientPacketListener.handleMovePlayer` applies it to `this.minecraft.player`. For a same-client correction of that player's position, a reachable state with positive `tickCount` and a vehicle enters the new branch. This conclusion needs no premise about server producer frequency, network cadence, or packet timing. The server-side producer remains excluded; the direct player response is the in-scope operation.

## Route spot check

The sibling remote-entity receiver exclusions remain supported by direct gates: in both versions `ClientPacketListener.handleMoveEntity` wraps interpolation in `if (!entity.isControlledByLocalInstance())` (1.15.2 lines 624–628; 1.16.5 lines 628–632). Likewise `handleTeleportEntity` applies interpolation only under that negative gate (1.15.2 lines 593–601; 1.16.5 lines 602–610). The 1.16.5 `Entity.isControlledByLocalInstance()` implementation returns a controlling player's `isLocalPlayer()` value when controlled by a player (Entity.java:2557–2560). These checks support closing those entity-interpolation rows for local-player movement; they do not weaken the distinct player-position receiver finding.

## Limitations

This is a bounded source review of the retained receiver claim and the two sibling remote-entity gates, not the full 1.15.2/1.16.5 movement inventory or pair-freeze audit. It does not verify intermediate releases, server production, gameplay behavior or runtime parity. Source comments above use the local Mojmap member names; exact mapping artifact coordinates were unavailable in the ready metadata.
