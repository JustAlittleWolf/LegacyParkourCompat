# 1.17.1 → 1.18.2 direct client player receipt closure

## Scope and input binding

This bounded source-only closure covers clientbound local-player position corrections, entity-motion and explosion-impulse application, entity teleport/relative-move guards, vehicle correction, and passenger-list attachment effects that directly write local-player movement orientation. It reuses the exact source path/hash ledger and identity verification in `.native-source/20261028/frontier-c/1171-1182-full-audit/audit.md` (canonical pair roots are `D:/Javastuff/LegacyParkourCompat/decompiled_minecraft/1.17.1/mojmap` and `D:/Javastuff/LegacyParkourCompat/decompiled_minecraft/1.18.2/mojmap`). Its pair-wide freeze verdict is **NOT READY**. This report does not freeze the pair.

The two canonical `mojmap.sources.sha256` manifests were checked for the selected files. Manifest SHA-256: 1.17.1 `93270d229acfb751bf56daf1e7be26ce3dcff26b29e94aa157de621405a3463b`; 1.18.2 `aea0cb9c6fc8f7a46f0eb82b0388ad58a4659f513be6c0a2be06c0df0c1eb07a`. Each listed source hash below equals its exact manifest entry. The reused full-pair audit binds the complete Java inventory; this closure does not repeat that inventory.

## Findings

### Local player position correction: same response

`ClientPacketListener.handleMovePlayer(ClientboundPlayerPositionPacket)` is semantically the same in both endpoints (`1.17.1` lines 549–613; `1.18.2` lines 541–605), apart from local-variable names and an old UI-only tail in 1.17.1. After the client-thread guard it optionally removes the local player's vehicle when the packet's dismount flag is set. It snapshots current delta movement and checks X/Y/Z relative bits independently. For a relative axis it adds the packet offset to current position and preserves that velocity component; for an absolute axis it uses the packet coordinate and sets the corresponding velocity component to zero. It updates `xOld/yOld/zOld`, then `setPosRaw`, `xo/yo/zo`, and delta movement in the same order. It adds current pitch/yaw only for X_ROT/Y_ROT relative bits, then calls `absMoveTo`. It sends teleport acceptance followed by a position/rotation acknowledgement with `onGround=false` in both releases.

In 1.17.1 only, after the two sends, `!started` sets `started=true` and closes the loading screen (`setScreen(null)`). This is a client UI transition and does not alter movement state. The packet class remains the source of the coordinate/rotation/relative-mask/id/dismount payload in this comparison; server producer selection and packet scheduling are outside this closure.

The common `Entity.absMoveTo(x,y,z,yaw,pitch)` clamps X/Z to ±3.0E7, sets position and previous position, wraps yaw modulo 360, clamps pitch to [-90,90] then wraps modulo 360, and copies current yaw/pitch to previous yaw/pitch. The two bodies match (`Entity.java`, 1.17.1 lines 1199–1214; 1.18.2 lines 1174–1189).

### Entity-motion receipt: same response, target conditional

`ClientPacketListener.handleSetEntityMotion(ClientboundSetEntityMotionPacket)` matches: thread guard; resolve `level.getEntity(packet.id)`; if non-null call `lerpMotion(xa/8000.0, ya/8000.0, za/8000.0)`. `ClientLevel.addPlayer` routes players through the same entity storage as other entities and `getEntity` reads that storage in both endpoints (`ClientLevel.java`, 1.17.1 lines 223–247; 1.18.2 lines 262–286). Thus the local player is a possible recipient when its entity id resolves there. `Entity.lerpMotion` delegates to `setDeltaMovement` in both versions (1.17.1 line 1890; 1.18.2 line 1885). Equivalent received integer payloads therefore produce the same scaled velocity assignment. The packet encoder/server producer and possible upstream payload differences are not evaluated.

### Explosion impulse: same response

`ClientPacketListener.handleExplosion(ClientboundExplodePacket)` in both versions runs on the client thread, constructs/finalizes the client explosion from packet fields, then adds the three supplied knockback components to the local player's existing delta movement and assigns the sum. Order and float-valued knockback use are the same (`1.17.1` lines 973–993; `1.18.2` lines 972–977). The client explosion's block/particle/sound work and the server's impulse calculation/producer are outside this direct player-response slice; this finding assumes equivalent received packet fields.

### Entity teleport and relative-move packets: no locally controlled transform write

`handleTeleportEntity` in both releases writes packet coordinates to the entity's interpolation target first. It performs `lerpTo(..., 3, true)` and sets on-ground only when `!entity.isControlledByLocalInstance()`. `handleMoveEntity` similarly guards the position/rotation interpolation and on-ground assignment behind `!isControlledByLocalInstance()`, with the same position/rotation sub-gates and interpolation steps. `Entity.isControlledByLocalInstance` returns true when the controlling passenger is a local player, so the guards also cover a locally controlled root vehicle. Thus these packets do not directly apply position/rotation/on-ground to the local player or a locally controlled vehicle through these handlers in either release. They do still update `packetCoordinates` in the teleport handler before the guard, which is a distinct interpolation bookkeeping field; its downstream behavior is not expanded here.

`handleMoveVehicle` is also the same: obtain the local player's root vehicle; if it differs from the player and is locally controlled, apply `absMoveTo` from packet coordinates and angles, then send `ServerboundMoveVehiclePacket`. This moves the vehicle state and can affect the riding context; it does not directly call a local-player position/velocity setter. The packet producer and vehicle passenger position/transform consumers remain upstream/downstream dependencies.

### Boat passenger list: accepted direct local-yaw difference

The passenger-list handler computes `wasPassenger = vehicle.hasIndirectPassenger(localPlayer)`, ejects all passengers, then loops the packet's passenger IDs, resolves each entity, and calls `startRiding(vehicle, true)` in both versions (`ClientPacketListener.handleSetEntityPassengersPacket`, 1.17.1 lines 825–845; 1.18.2 lines 826–852). The forced path permits the same player to reattach after ejection, assuming the referenced entities resolve and no cycle check rejects it.

In 1.17.1, every successful `LocalPlayer.startRiding(boat, ...)` writes `yRotO`, current yaw, and head yaw from the boat yaw (`LocalPlayer.java` lines 155–169). In 1.18.2 that override retains the successful-start and minecart sound logic but no longer writes boat yaw (`LocalPlayer.java` lines 153–164). Instead `handleSetEntityPassengersPacket` performs the three yaw writes only if the direct passenger being attached is the local player, the vehicle is a `Boat`, and the pre-ejection `wasPassenger` value is false (`ClientPacketListener.java` lines 839–849). The writes are: previous yaw = boat yaw, current yaw = boat yaw, head yaw = boat yaw.

Therefore, for an equivalent passenger-list packet that directly re-adds the local player to the same boat after it was already an indirect passenger before ejection, and where `startRiding` succeeds, 1.17.1 realigns those three yaw values while 1.18.2 does not. This is a direct source-confirmed local player orientation response difference. For a first-time direct local-player attachment (`wasPassenger == false`), both versions realign the same values after successful `startRiding`, although the write moved from the `LocalPlayer` override to the packet handler. No claim is made about server packet frequency, tick timing, or resulting movement trajectory.

## Checked methods and remaining dependencies

Directly checked paired methods: `ClientPacketListener.handleMovePlayer`, `handleSetEntityMotion`, `handleExplosion`, `handleTeleportEntity`, `handleMoveEntity`, `handleMoveVehicle`, `handleSetEntityPassengersPacket`; `LocalPlayer.startRiding`; `ClientLevel.addPlayer/addEntity/getEntity`; `Entity.absMoveTo`, `lerpMotion`, `isControlledByLocalInstance`, `startRiding`, `ejectPassengers`.

This closes only the consumer-side direct response under equal received packet payloads. Server-side packet producers and quantization inputs, packet delivery/order relative to ticks, other writers (especially effect/attribute/equipment, entity-data and world/block state updates assigned elsewhere), vehicle passenger transform consumers, and collision/fluid/block-shape dependencies remain open. The pair-wide fall-flying numeric delta and broader coverage gaps called out in the existing full audit remain open. No runtime parity or pair freeze is asserted.

