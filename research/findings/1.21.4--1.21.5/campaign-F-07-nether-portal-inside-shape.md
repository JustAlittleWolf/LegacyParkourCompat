# F-07: Nether portal entry callbacks use the portal plane shape in 1.21.5

1.21.5, mojmap: `1.21.5/mojmap/net/minecraft/world/entity/Entity.java`, checkInsideBlocks 1086-1098.
1.21.4, mojmap: `1.21.4/mojmap/net/minecraft/world/level/block/state/BlockBehaviour.java`, getEntityInsideCollisionShape 353-355.
1.21.4, mojmap: `1.21.4/mojmap/net/minecraft/world/level/block/NetherPortalBlock.java`, entityInside 103-108.
1.21.4, mojmap: `1.21.4/mojmap/net/minecraft/world/phys/shapes/Shapes.java`, block 42-44.
1.21.5, mojmap: `1.21.5/mojmap/net/minecraft/world/level/block/NetherPortalBlock.java`, getEntityInsideCollisionShape 66-69.
1.21.5, mojmap: `1.21.5/mojmap/net/minecraft/world/level/block/Block.java`, class declaration / field initialization 49.

Difference: The 1.21.4 movement-contact scan sees the default full-block entity-inside shape for a Nether portal block. 1.21.5 adds an override that returns the portal's axis-oriented shape, which is 4/16 of a block thick. Under the same portal state and player movement, 1.21.5 invokes the inside-portal callback only when the movement intersects that portal plane shape; 1.21.4's full block shape can invoke it elsewhere in the same cell.

Movement meaning: Player movement -> `Entity#applyEffectsFromBlocks` -> `checkInsideBlocks` -> NetherPortalBlock entity-inside shape -> `entityInside` -> `Entity#setAsInsidePortal`. The state reaches portal transition processing on later player ticks. 1.21.4's checked replacement path is the inherited BlockBehaviour default shape; 1.21.5's shape uses the existing AXIS blockstate and portal column geometry. The game mode/portal cooldown gate remains part of the player callback's `canUsePortal(false)` condition. The source proves a thinner contact volume for initiating inside-portal state. This changes where along movement through a portal cell the portal callback can be triggered, which may shift portal-processing timing. No teleport timing or trajectory was measured; cooldown, portal state and subsequent player ticks remain dependencies.

Original evidence: [archived report](../../archive/legacy-workflows/source-campaign-2026-10-07/1.21.4--1.21.5/findings/F-07-nether-portal-inside-shape.md).
