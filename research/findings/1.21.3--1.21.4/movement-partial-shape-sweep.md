# Partial inside-shape swept geometry

1.21.3, mojmap: `1.21.3/mojmap/net/minecraft/world/entity/Entity.java`, applyEffectsFromBlocks 740-784; setAsInsidePortal 2214-2225.
1.21.4, mojmap: `1.21.4/mojmap/net/minecraft/world/entity/Entity.java`, applyEffectsFromBlocks 751-797.
1.21.3, mojmap: `1.21.3/mojmap/net/minecraft/world/entity/LivingEntity.java`, aiStep 2775-2777.
1.21.3, mojmap: `1.21.3/mojmap/net/minecraft/world/level/block/state/BlockBehaviour.java`, getEntityInsideCollisionShape 353-355.
1.21.4, mojmap: `1.21.4/mojmap/net/minecraft/world/level/block/state/BlockBehaviour.java`, getEntityInsideCollisionShape 353-355.
1.21.3, mojmap: `1.21.3/mojmap/net/minecraft/world/level/BlockGetter.java`, boxTraverseBlocks 172-190.
1.21.3, mojmap: `1.21.3/mojmap/net/minecraft/world/phys/shapes/Shapes.java`, block 42-44.
1.21.4, mojmap: `1.21.4/mojmap/net/minecraft/world/phys/shapes/Shapes.java`, block 42-44.
1.21.3, mojmap: `1.21.3/mojmap/net/minecraft/world/level/block/EndPortalBlock.java`, class declaration / field initialization 25-63.

Difference: 1.21.3 uses a recentered final player box and reverse displacement; 1.21.4 uses the segment-start box and forward displacement for partial entity-inside shapes.

Movement meaning: An existing partial shape such as End Portal may be intersected differently and write portal processing state. Block-cell enumeration is a separate operation.

Original evidence: [archived report](../../archive/legacy-workflows/source-campaign-2026-10-07/1.21.3--1.21.4/findings/F-S3-02-movement-block-shape-sweep.md).
