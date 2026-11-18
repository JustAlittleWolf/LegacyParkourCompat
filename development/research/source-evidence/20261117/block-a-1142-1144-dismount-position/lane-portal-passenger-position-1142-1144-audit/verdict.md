# Independent bounded source verdict — portal/passenger position, 1.14.2 → 1.14.4

## Verdict

**Agrees with the author's bounded no-difference conclusion for the inspected dismount/correction operation.** The vehicle relation, server-side stand-up placement, portal-contact exclusion, absolute dismount correction, and client coordinate application have equivalent gates and order in the inspected endpoints. The direct stand-up search contains no portal-block-specific test. No movement finding is supported by this slice. This does not establish the exact patch boundary or full 1.14.2 → 1.14.4 pair coverage.

## Evidence identity and independence boundary

- My pre-access source roster: roster.md, SHA-256 629d0d53b1bf1189c341861b476bee985d0c7769c99727432d07d96277bad9ab.
- Author report inspected only after that roster was frozen: report.md, SHA-256 749cdd64f3dffb726d1f12fb6cb4e5bd575d4895598d344a162b2fe0c5e7b889; author roster SHA-256 7831bb830ac1157d8d2279d3c2263635ccc3bd85ea85a2c7594904336ae7d60c.
- Exact source pair: 1.14.2 Yarn 1.14.2+build.7 at decompiled_minecraft/1.14.2/yarn, and 1.14.4 Mojmap at decompiled_minecraft/1.14.4/mojmap. Canonical provenance/source manifests and mapping/client pins are recorded in roster.md; all checked source hashes match their manifests.
- Limitation: my frozen roster captured the core Entity, LivingEntity, network handler and local-player slices, but omitted some supporting caller/shape files used in the report (server-player overrides, portal callback/registration and client inbound handler). I checked those exact sources and manifest hashes after opening the report, within the same assigned topic. The result below is a bounded source verdict, not an independently frozen full dependency inventory.

## Claim-by-claim audit

### Vehicle/passenger relationship and portal gate

The base relationship is the same: 1.14.2 Entity.hasVehicle() is getVehicle() != null (Entity.java:1735-1737); 1.14.4 Entity.isPassenger() is also getVehicle() != null (Entity.java:1800-1802). Dimension-transfer cleanup likewise ejects passengers and stops the entity riding: old detach() (Entity.java:215-223) and new unRide() (Entity.java:219-227).

The Nether-portal callback is separate from stand-up placement. Old PortalBlock.onEntityCollision requires no vehicle, no passengers and canUsePortals() (PortalBlock.java:100-105); new NetherPortalBlock.entityInside requires no passenger, no vehicle and canChangeDimensions() (NetherPortalBlock.java:106-111). The base capability predicates both return true. Thus the callback does not mark portal contact for a mounted entity or an entity carrying passengers. This predicate is not present in the dismount helper.

### Server-only LivingEntity dismount placement

Both LivingEntity.stopRiding methods save the old vehicle, call the base stop-riding method, then run stand-up placement only if the vehicle changed and execution is server-side: old (LivingEntity.java:2311-2317), new (LivingEntity.java:2324-2330). The helper itself checks no portal block, portal tag, portal state or portal location.

The non-boat/horse branch uses the same ordered 3×3 candidate array (with the repeated final {0, 1}), mount movement direction, player-centered width/depth box, collision-first test, current-y support test, below-position support-or-water fallback, raised collision/support fallback and final teleport. Compare old method_6038 (LivingEntity.java:1656-1705) with new findStandUpPosition (LivingEntity.java:1664-1718). The boat/horse branch uses the same width formula, yaw/main-side offset, 0.001 then 1.001 vertical trials, union collision test and mount-centered fallback (LivingEntity.java:1706-1726; LivingEntity.java:1719-1740).

The support predicate is a context-sensitive full-UP-face collision shape on both sides: old BlockState.hasSolidTopSurface (BlockState.java:208-210) and new BlockState.entityCanStandOn (BlockState.java:210-212). Nether portal blocks are registered with noCollision() in both versions (old Blocks.java:596-601, new Blocks.java:583-586); the helper does not test for them, and their empty collision shape cannot satisfy that full-face test. These are general placement/support checks, not portal-specific placement logic.

### Server correction, connection role and client XYZ application

The server-player override sends a correction after the LivingEntity superclass has selected the dismount position, but only when the vehicle relation changed and a connection exists: old ServerPlayerEntity.stopRiding (ServerPlayerEntity.java:758-765), new ServerPlayer.stopRiding (ServerPlayer.java:763-770). Both use the no-relative-fields overload: old requestTeleport supplies Collections.emptySet() (ServerPlayNetworkHandler.java:804-821); new teleport supplies Collections.emptySet() (ServerGamePacketListenerImpl.java:860-877). These packets carry absolute x/y/z and rotation for dismount correction. The packet serializers retain double x/y/z followed by float yaw/pitch and relative-field metadata (PlayerPositionLookS2CPacket.java:10-51; ClientboundPlayerPositionPacket.java:9-50).

The ordinary player movement path distinguishes passenger from on-foot. The server accepts vehicle movement only when the player's topmost/root vehicle is the tracked vehicle and the player is its selected primary/controlling passenger (old ServerPlayNetworkHandler.java:196-203, 297-315; new ServerGamePacketListenerImpl.java:198-205, 308-339). For ordinary player movement, the passenger branch keeps the player's current XYZ and updates look; the on-foot branch consumes XYZ. The client movement writer sends current x, bounding-box minY and z on foot, while a passenger sends velocity x/z with y sentinel -999.0: old ClientPlayerEntity.sendMovementPackets (ClientPlayerEntity.java:207-245), new LocalPlayer.sendPosition (LocalPlayer.java:217-245).

On receiving a server player-position correction, both clients add flagged-relative axes, otherwise accept absolute axes and zero velocity for those axes, then call the direct XYZ writer and send the acknowledgment plus a position/look packet using current x, bounding-box minY and z: old ClientPlayNetworkHandler.onPlayerPositionLook (ClientPlayNetworkHandler.java:642-690; Entity.setPositionAnglesAndUpdate, Entity.java:1047-1058); new ClientPacketListener.handleMovePlayer (ClientPacketListener.java:658-706; Entity.absMoveTo, Entity.java:1116-1127). With the empty relative-field set used for dismount, the writer receives the absolute post-placement coordinates. No riding or portal gate surrounds this receive/write operation.

Vehicle rollback packets are separately absolute vehicle-state corrections: their constructors copy vehicle x/y/z and rotation, and serializers write those values directly (old VehicleMoveS2CPacket.java:19-42; new ClientboundMoveVehiclePacket.java:18-41). This corrects the vehicle when the server rejects a vehicle move; it is not the player dismount-position correction.

## Coverage limit

Reviewed only the named portal/passenger dismount and connection-position operation at these two endpoints. Not reviewed: other movement slices, intermediate releases, unrelated portal behavior, or runtime parity.
