# F-FLUID-DRAG — 1.13.2 replaces material drag sampling with fluid-state flow sampling

- **1.12.2**, ornithe-feather: `1.12.2/ornithe-feather/net/minecraft/entity/Entity.java`; lines 944-946.
- **1.13.2**, ornithe-feather: `1.13.2/ornithe-feather/net/minecraft/entity/Entity.java`; lines 961-975.

## Difference

1.12.2 delegates water contact/drag to the World material callback. 1.13.2 samples fluid states intersecting the contracted player box, accumulates fluid flow vectors and adds `0.014`-scaled flow components to velocity; it stores maximum fluid depth for other movement gates.

## Movement meaning

- Reachability and dependencies: Entity fluid check is inherited by the player base tick and feeds water contact plus F-WATER-STATE.

This is not a block-state historical emulation claim.

[Archived report](../../archive/legacy-workflows/source-campaign-2026-10-07/1.12.2--1.13.2/findings/F-FLUID-DRAG.md).
