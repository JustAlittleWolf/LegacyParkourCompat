# Queued inside-effect commit order

1.21.4, mojmap: `1.21.4/mojmap/net/minecraft/world/entity/Entity.java`, move 627-712; applyEffectsFromBlocks 747-778; checkInsideBlocks 1050-1090.
1.21.5, mojmap: `1.21.5/mojmap/net/minecraft/world/entity/Entity.java`, move 636-733; applyEffectsFromBlocks 768-815; checkInsideBlocks 1079-1122.
1.21.5, mojmap: `1.21.5/mojmap/net/minecraft/world/level/block/HoneyBlock.java`, entityInside 63-71.
1.21.5, mojmap: `1.21.5/mojmap/net/minecraft/world/level/block/PowderSnowBlock.java`, entityInside 62-96.

Difference: 1.21.5 queues enumerated block effects and commits them in movement-step order after scanning; 1.21.4 applies those callback effects immediately. Direct callback writes may still occur during the 1.21.5 scan.

Movement meaning: The player contact-effect application point/order is separate from movement path segmentation. Added fire/damage-only fluid effects are excluded; no independent numeric movement consequence is asserted for those.

Original evidence: [archived report](../../archive/legacy-workflows/source-campaign-2026-10-07/1.21.4--1.21.5/findings/F-01-movement-callback-path.md).
