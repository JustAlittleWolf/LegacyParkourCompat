# WORLD-07: Nether portal entity eligibility

- **1.8.9**, ornithe-feather: `1.8.9/ornithe-feather/net/minecraft/block/PortalBlock.java`; lines 162-165.
- **1.9.4**, ornithe-feather: `1.9.4/ornithe-feather/net/minecraft/block/PortalBlock.java`; lines 170-173.

## Difference

- 1.8.9 source: 1.8.9/ornithe-feather/net/minecraft/block/PortalBlock.java, onEntityCollision lines 162-165 Calls entity.onPortalCollision(pos) when vehicle == null && rider == null.
- 1.9.4 source: 1.9.4/ornithe-feather/net/minecraft/block/PortalBlock.java, onEntityCollision lines 170-173 Calls only when !isRiding() && !hasPassengers() && canUsePortals().

## Movement meaning

- Scope: paired Nether PortalBlock.onEntityCollision guard and portal-state consumer.

No travel trajectory or non-player behavior is inferred.
- Disposition: predicate delta established.

Keep this conditional source finding until passenger-state production/reachability is closed.

[Archived report](../../archive/legacy-workflows/source-campaign-2026-10-07/1.8.9--1.9.4/findings/WORLD-07-nether-portal-entity-eligibility.md).
