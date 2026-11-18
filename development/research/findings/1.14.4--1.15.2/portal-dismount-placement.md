# Portal-aware dismount placement

1.14.4, mojmap: `1.14.4/mojmap/net/minecraft/world/entity/LivingEntity.java`, `stopRiding`, lines 2325–2331.
1.15.2, mojmap: `1.15.2/mojmap/net/minecraft/world/entity/LivingEntity.java`, `stopRiding`, lines 2389–2395.

Difference: For a passenger dismounting while the vehicle occupies a portal-tagged cell, 1.15.2's portal branch runs even for Boat/Horse and places the passenger just above the vehicle. 1.14.4 excludes Boat/Horse before any stand-up repositioning and contains no portal-tagged branch. For non-Boat/non-Horse vehicles the portal branch also takes precedence over 1.14.4's nearby stand-position search.

Movement meaning: The change is reached through a player living-entity dismount and writes player position on the server.

## Earlier local passenger-packet route

In exact 1.13.2 and 1.14 Ornithe Feather sources, a passenger-list packet clears the player mount link through `stopRiding` and reattaches the packet-listed passengers. The separate `dismountRider` callback that writes XYZ runs behind a logical-server gate. Still-mounted `rideTick` positioning and server-authoritative corrections remain separate position writers. [Exact source evidence](../../source-evidence/20261118/block-a-earlier-portal-detach/finding.md), [independent verdict](../../source-evidence/20261118/block-a-earlier-portal-detach/verdict.md).
