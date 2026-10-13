# F06: Portal-aware dismount placement

1.14.4, mojmap: `1.14.4/mojmap/net/minecraft/world/entity/LivingEntity.java`, `stopRiding`, lines 2325–2331.
1.15.2, mojmap: `1.15.2/mojmap/net/minecraft/world/entity/LivingEntity.java`, `stopRiding`, lines 2389–2395.

Difference: For a passenger dismounting while the vehicle occupies a portal-tagged cell, 1.15.2's portal branch runs even for Boat/Horse and places the passenger just above the vehicle. 1.14.4 excludes Boat/Horse before any stand-up repositioning and contains no portal-tagged branch. For non-Boat/non-Horse vehicles the portal branch also takes precedence over 1.14.4's nearby stand-position search.

Movement meaning: The change is reached through a player living-entity dismount and writes player position on the server.

Original evidence: [archived report](../../archive/legacy-workflows/movement-discovery/runs/1.14.4--1.15.2/findings/F06-portal-dismount-placement.md).
