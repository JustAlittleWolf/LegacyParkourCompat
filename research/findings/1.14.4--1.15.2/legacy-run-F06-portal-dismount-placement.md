# F06: Portal-aware dismount placement

- **1.14.4**, mojmap: [source locations in preserved report](../../archive/legacy-workflows/movement-discovery/runs/1.14.4--1.15.2/findings/F06-portal-dismount-placement.md); lines 1664.
- **1.15.2**, mojmap: `ServerEntity.java`; lines 70–145.

## Difference

For a passenger dismounting while the vehicle occupies a portal-tagged cell, 1.15.2's portal branch runs even for Boat/Horse and places the passenger just above the vehicle. 1.14.4 excludes Boat/Horse before any stand-up repositioning and contains no portal-tagged branch.

For non-Boat/non-Horse vehicles the portal branch also takes precedence over 1.14.4's nearby stand-position search.

## Movement meaning

The change is reached through a player living-entity dismount and writes player position on the server.

It is an externally initiated, server-authoritative position change, not a local movement-loop or collision calculation.

Entity tracking / teleport correction is the synchronization boundary; exact local prediction is outside this finding.

This can change the position after dismount in portal blocks.

Portal blocks are 1.15.2-era resources, but Boat/Horse passenger dismount remains a reachable player interaction.

[Archived report](../../archive/legacy-workflows/movement-discovery/runs/1.14.4--1.15.2/findings/F06-portal-dismount-placement.md).
