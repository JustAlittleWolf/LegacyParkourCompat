# Player correction detaches the local player before applying position

1.15.2 (Mojmap): `net/minecraft/client/multiplayer/ClientPacketListener.java`, `handleMovePlayer` (669 onward) applies relative/absolute coordinates without removing an attached vehicle.
1.16.5 (Mojmap): same class and method (671 onward) adds `if (player.tickCount > 0 && player.getVehicle() != null) player.removeVehicle()` after old-coordinate bookkeeping and before correction position, velocity and rotation writes.

Difference: A clientbound position correction processed for the receiving local player detaches that player from its vehicle in 1.16.5 when tickCount is positive and a vehicle is attached; the 1.15.2 receiver has no corresponding gate. This is a direct client receiver operation. Server-side teleport production and packet frequency do not establish this delta and are not required for the claim.

Movement meaning: Clearing the local player's vehicle/passenger state before applying the correction changes subsequent passenger, input and movement branches. It does not claim a difference in the authoritative server-side placement algorithm. The exact intermediate release introducing this receiver branch was not checked.
