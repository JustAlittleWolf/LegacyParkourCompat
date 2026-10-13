# Movement block-cell traversal

1.21.3, mojmap: `1.21.3/mojmap/net/minecraft/world/entity/Entity.java`, applyEffectsFromBlocks 740-784; setAsInsidePortal 2214-2225.
1.21.4, mojmap: `1.21.4/mojmap/net/minecraft/world/entity/Entity.java`, applyEffectsFromBlocks 751-797.
1.21.3, mojmap: `1.21.3/mojmap/net/minecraft/world/entity/LivingEntity.java`, aiStep 2775-2777.
1.21.3, mojmap: `1.21.3/mojmap/net/minecraft/world/level/block/state/BlockBehaviour.java`, getEntityInsideCollisionShape 353-355.
1.21.4, mojmap: `1.21.4/mojmap/net/minecraft/world/level/block/state/BlockBehaviour.java`, getEntityInsideCollisionShape 353-355.
1.21.3, mojmap: `1.21.3/mojmap/net/minecraft/world/level/BlockGetter.java`, boxTraverseBlocks 172-190.
1.21.3, mojmap: `1.21.3/mojmap/net/minecraft/world/phys/shapes/Shapes.java`, block 42-44.
1.21.4, mojmap: `1.21.4/mojmap/net/minecraft/world/phys/shapes/Shapes.java`, block 42-44.
1.21.3, mojmap: `1.21.3/mojmap/net/minecraft/world/level/block/EndPortalBlock.java`, class declaration / field initialization 25-63.

Difference: 1.21.4 changes which cells near grid boundaries are yielded for the recorded player movement segment compared with 1.21.3.

Movement meaning: Different candidate block callbacks can be reached; world contact and provider dependency closure are separate from segment enumeration.
