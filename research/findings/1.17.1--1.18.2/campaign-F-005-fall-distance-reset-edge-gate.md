# F-005: Long movement can reset fallDistance before later edge-backoff checks

- **1.17.1**, mojmap: [source locations in preserved report](../../archive/legacy-workflows/source-campaign-2026-10-07/1.17.1--1.18.2/findings/F-005-fall-distance-reset-edge-gate.md).
- **1.18.2**, mojmap: [source locations in preserved report](../../archive/legacy-workflows/source-campaign-2026-10-07/1.17.1--1.18.2/findings/F-005-fall-distance-reset-edge-gate.md).

## Difference

1.18.2 adds a fallDistance writer after edge-backoff/collision; unchanged Player gate reads it later.

## Movement meaning

Player movement -> Entity#move clip/reset -> later Player#isAboveGround. 1.18.2 clip-filter closure is resolved: ClipContext#Block.FALLDAMAGE_RESETTING checks the new block tag, ClipContext#Fluid.WATER checks the water tag present in both versions, and Entity#resetFallDistance writes zero on a non-MISS hit.

The reset is after the current edge-backoff check and can feed a later Player gate; damage remains excluded.

1.17.1 qualifying reset can change a later edge gate; the current move's gate runs first.

The qualifying path is bounded by nonzero fallDistance, resolved movement length squared>=1, and a clip hit in the 1.18.2 reset block tag or water; exact player outcomes remain dependent on open collision-shape providers.

[Archived report](../../archive/legacy-workflows/source-campaign-2026-10-07/1.17.1--1.18.2/findings/F-005-fall-distance-reset-edge-gate.md).
