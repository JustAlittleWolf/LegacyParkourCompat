# Player correction detaches the local player before applying position

1.15.2 (Mojmap): `net/minecraft/client/multiplayer/ClientPacketListener.java`, `handleMovePlayer` (669–727) applies relative/absolute coordinates without removing an attached vehicle.
1.16 (Mojmap): same class and method adds `if (player.tickCount > 0 && player.getVehicle() != null) player.removeVehicle()` (726–727), after old-coordinate bookkeeping and before correction position, velocity and rotation writes. `net/minecraft/world/entity/Entity.java`, `removeVehicle` clears the local passenger relationship (1630 onward). This receiver gate persists unchanged through 1.16.5. Boundary: 1.15.2 → 1.16.

Difference: A clientbound position correction processed for the receiving local player detaches that player from its vehicle in 1.16 when tickCount is positive and a vehicle is attached; the 1.15.2 receiver has no corresponding gate. This is a direct client receiver operation. Server-side teleport production and packet frequency do not establish this delta and are not required for the claim.

Movement meaning: Clearing the local player's vehicle/passenger state before applying the correction changes subsequent passenger, input and movement branches. It does not claim a difference in the authoritative server-side placement algorithm.
