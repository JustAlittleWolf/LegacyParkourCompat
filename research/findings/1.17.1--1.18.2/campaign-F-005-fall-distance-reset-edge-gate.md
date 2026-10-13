# F-005: Long movement can reset fallDistance before later edge-backoff checks

1.17.1, mojmap: `1.17.1/mojmap/net/minecraft/world/entity/Entity.java`, `move`, lines 553–566.
1.18.2, mojmap: `1.18.2/mojmap/net/minecraft/world/entity/Entity.java`, `move`, lines 562–575.

Difference: 1.18.2 adds a fallDistance writer after edge-backoff/collision; unchanged Player gate reads it later.

Movement meaning: Player movement -> Entity#move clip/reset -> later Player#isAboveGround. 1.18.2 clip-filter closure is resolved: ClipContext#Block.FALLDAMAGE_RESETTING checks the new block tag, ClipContext#Fluid.WATER checks the water tag present in both versions, and Entity#resetFallDistance writes zero on a non-MISS hit.

Original evidence: [archived report](../../archive/legacy-workflows/source-campaign-2026-10-07/1.17.1--1.18.2/findings/F-005-fall-distance-reset-edge-gate.md).
