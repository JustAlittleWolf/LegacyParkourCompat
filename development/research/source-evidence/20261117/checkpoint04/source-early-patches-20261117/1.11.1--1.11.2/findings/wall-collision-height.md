# Wall collision height correction

1.11.1, Ornithe Feather: `net/minecraft/block/WallBlock.java`, `addCollisions` (92–97), appends `SHAPES[getShapeIndex(state)]`, whose highest Y is 1.0.
1.11.2, Ornithe Feather: the same method appends `COLLISION_SHAPES[getShapeIndex(state)]`; the static collision boxes are `SHAPES` extended to maxY 1.5 (array at 49–66).

Difference: both methods first resolve virtual connection properties from the same world neighbors when `forceShape` is false. The old collision callback selects the one-block outline boxes even though `getCollisionShape` already returns the 1.5-high collision boxes; the new callback uses those taller boxes. `World.getCollisions(Entity, Box)` reaches this callback for the queried player collision volume, and `Entity.move` sequentially clips movement against the resulting boxes.

Movement meaning: when the player's box overlaps only the wall's upper interval (1.0–1.5), the 1.11.2 collision query can prevent motion that 1.11.1 allowed. For example, a player moving sideways at the top of a full block adjacent to a wall has feet at y=1, so this vertical overlap is reachable. Exact trajectory consequences are not measured.
