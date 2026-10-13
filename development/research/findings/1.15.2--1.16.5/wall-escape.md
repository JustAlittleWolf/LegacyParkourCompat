# S1-02: Change local-player suffocation escape probes

1.15.2, mojmap: `1.15.2/mojmap/net/minecraft/client/player/LocalPlayer.java`, `checkInBlock`, lines 385–424.
1.16.5, mojmap: `1.16.5/mojmap/net/minecraft/client/player/LocalPlayer.java`, `moveTowardsClosestSpace`, lines 414–440.

Difference: 1.15.2 tests whether each of four sampled block columns is suffocating across the entity's vertical range. 1.16.5 checks each sampled block against suffocating collision shapes intersecting a horizontal slice of the full bounding box. Both choose the nearest unblocked cardinal neighbor in west/east/north/south order and set the selected horizontal velocity component to magnitude `0.1`; the occupancy query is different.

Movement meaning: This runs from `LocalPlayer.aiStep()` while `noPhysics` is false, before the parent living movement step.
