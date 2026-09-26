# F06: Portal-aware dismount placement

- Older version A: 1.14.4
- Newer version B: 1.15.2
- Mechanic / coverage slice IDs: stages 3, 7; player state and server-originated position
- Classification: changed mechanic
- Confidence: source-confirmed
- Applicability: player dismount placement when a living passenger exits a vehicle while its position is inside a portal-tagged block
- First changed release: unknown within (1.14.4, 1.15.2]
- Runtime validation: not performed

## Paired evidence

- In both endpoints `LivingEntity.stopRiding()` saves the vehicle, calls `super.stopRiding()`, then invokes `findStandUpPosition(vehicle)` only on the server. A lines 2325–2329; B lines 2389–2393. `LivingEntity.java` SHA-256: A `428762178A876EFD4069086E6B7D51F571EA0401F44E7EFF0927B7D26D9EF681`; B `46D243BB7E51F7B54404AA1D7D6E6B827682D0F5925D02847C4193306C4D5E54`.
- A `findStandUpPosition` first returns for Boat or AbstractHorse, otherwise probes a safe nearby stand position (lines 1664 onward). B first checks whether the vehicle's block is in `BlockTags.PORTALS`; that branch sets the passenger position to vehicle X/Z and `vehicle.getY(1.0) + 0.001`, before the Boat/Horse exclusion (lines 1697 onward). B's portal tag contains Nether Portal, End Portal, and End Gateway; A has no corresponding `portals` tag. Resource values and hashes are indexed in `resources-paired-tags.md`.
- B tracked entities publish changed positions through `ServerEntity.sendChanges`, using relative movement or `ClientboundTeleportEntityPacket` as required (lines 70–145). The client packet handler applies tracked entity position updates in the normal entity replication path. For a local player, `ClientPacketListener.handleMovePlayer(ClientboundPlayerPositionPacket)` applies authoritative absolute/relative coordinates with `setPosRaw` and `absMoveTo` (lines 669–734); this handler establishes the correction boundary, not proof that this specific `stopRiding` call always emits that packet type. B `ServerEntity.java` SHA-256 `DC5892E3B2D7C6A141F235053C414284A6C77876981DB3BA1BA8419AF08D5527`; B `ClientPacketListener.java` SHA-256 `D806D286AF4B93D7C61C3C884F344A5870CE1871FB0A811506031ADF78FAFC30`.

## Source-level difference

For a passenger dismounting while the vehicle occupies a portal-tagged cell, B's portal branch runs even for Boat/Horse and places the passenger just above the vehicle. A excludes Boat/Horse before any stand-up repositioning and contains no portal-tagged branch. For non-Boat/non-Horse vehicles the portal branch also takes precedence over A's nearby stand-position search.

## Reachability and boundary

The change is reached through a player living-entity dismount and writes player position on the server. It is an externally initiated, server-authoritative position change, not a local movement-loop or collision calculation. Entity tracking / teleport correction is the synchronization boundary; exact local prediction is outside this finding.

## Consequence and uncertainty

This can change the position after dismount in portal blocks. No trajectory was observed. Portal blocks are B-era resources, but Boat/Horse passenger dismount remains a reachable player interaction.
