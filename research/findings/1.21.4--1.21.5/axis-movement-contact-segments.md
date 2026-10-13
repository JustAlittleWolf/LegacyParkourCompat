# Block contact follows accepted axis movement segments

1.21.4, mojmap: `1.21.4/mojmap/net/minecraft/world/entity/Entity.java`, move 627-712; applyEffectsFromBlocks 747-778; checkInsideBlocks 1050-1090.
1.21.5, mojmap: `1.21.5/mojmap/net/minecraft/world/entity/Entity.java`, move 636-733; applyEffectsFromBlocks 768-815; checkInsideBlocks 1079-1122.
1.21.5, mojmap: `1.21.5/mojmap/net/minecraft/world/level/block/HoneyBlock.java`, entityInside 63-71.
1.21.5, mojmap: `1.21.5/mojmap/net/minecraft/world/level/block/PowderSnowBlock.java`, entityInside 62-96.

Difference: 1.21.5 records accepted displacement as sequential axis-aligned segments in collision order; 1.21.4 supplies its endpoint movement segment to the end-of-travel scan.

Movement meaning: Multi-axis player movement can visit a different path/contact set before direct honey/powder-snow callbacks.

Original evidence: [archived report](../../archive/legacy-workflows/source-campaign-2026-10-07/1.21.4--1.21.5/findings/F-01-movement-callback-path.md).
