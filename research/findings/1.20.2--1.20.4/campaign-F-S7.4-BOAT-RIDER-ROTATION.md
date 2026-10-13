# F-S7.4-BOAT-RIDER-ROTATION: boat yaw is conditionally applied to passengers

- **1.20.2**, mojmap: `net/minecraft/world/entity/vehicle/Boat.java`; lines 700-709.
- **1.20.4**, mojmap: `net/minecraft/world/entity/vehicle/Boat.java`; lines 665-676.

## Difference

In 1.20.2, each Boat rider receives the boat's `deltaRotation` in both body yaw (`YRot`) and head yaw, followed by `clampRotation`.

In 1.20.4, those writes occur only when the rider entity type is not in `EntityTypeTags.CAN_TURN_IN_BOATS`.

The additional animal passenger rotation branch is inside the same new guard.

The direct x/y/z passenger placement call remains the unchanged shared superclass call.

## Movement meaning

Client packet handling can attach the local player from a `ClientboundSetPassengersPacket`: 1.20.2 `net.minecraft.client.multiplayer.ClientPacketListener.handleSetEntityPassengersPacket(ClientboundSetEntityPassengersPacket)` lines 923-950 1.20.4 same member lines 953-980 its brace-bounded method hash is identical, .

The player then follows `net.minecraft.world.entity.Entity.rideTick()` (1.20.2 lines 1827-1833 1.20.4 lines 1828-1834 that method dispatches to its vehicle's `positionRider`, so Boat's override receives the rider entity.

This callback directly writes the player's yaw/head yaw when its condition passes.

The 1.20.4 source declares `EntityTypeTags.CAN_TURN_IN_BOATS`, but the supplied exact-version source roots do not establish the default tag members.

Resolving the candidate requires exact 1.20.4 default tag membership; do not infer it from the tag name or the Java declaration.

Source-proven: 1.20.4 adds a type-tag condition around Boat rider yaw and head-yaw writes; the shared rider-position writer is called before that condition and is unchanged.

If a Player is absent from the 1.20.4 tag, those rotation writes remain reachable each Boat rider tick.

If Player is present, the writes are skipped.

Because player travel reads yaw to orient movement input, a changed yaw can change subsequent movement direction; this is a predicted consequence, not an observed trajectory.

[Archived report](../../archive/legacy-workflows/source-campaign-2026-10-07/1.20.2--1.20.4/findings/F-S7.4-BOAT-RIDER-ROTATION.md).
