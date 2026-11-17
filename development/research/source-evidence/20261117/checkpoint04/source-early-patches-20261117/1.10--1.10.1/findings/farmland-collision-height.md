# Farmland collision height

1.10, Ornithe Feather: `net/minecraft/block/FarmlandBlock.java`, `getCollisionShape` (36–40) returns `FULL_BLOCK_SHAPE`; its outline `SHAPE` is `(0, 0, 0)–(1, 0.9375, 1)` (22, 31–34).

1.10.1, Ornithe Feather: `net/minecraft/block/FarmlandBlock.java`, `getCollisionShape` override is removed; inherited `net/minecraft/block/Block.java` `getCollisionShape` (357–359) returns `state.getShape(world, pos)`, yielding the unchanged 15/16-high farmland shape.

Difference: the collision box top changes from block Y + 1.0 to block Y + 0.9375. `World.getCollisions` dispatches the block-state collision provider; `Entity.move` clips player movement against the returned boxes.

Movement meaning: when a local player’s vertical movement is clipped by farmland, the movement result can differ by 1/16 block. The provider is constant and does not depend on farmland moisture, neighboring blocks or entity type.
