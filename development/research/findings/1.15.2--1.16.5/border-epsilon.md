# lazy collision query drops the border shape within epsilon of its integer boundary

1.15.2, mojmap: `1.15.2/mojmap/net/minecraft/world/level/CollisionGetter.java`, `getBlockCollisions`, lines 78–90.
1.16.5, mojmap: `1.16.5/mojmap/net/minecraft/world/level/CollisionGetter.java`, `noCollision`, lines 36–42.
1.16.5, mojmap: `1.16.5/mojmap/net/minecraft/world/level/CollisionSpliterator.java`, `getChunk`, lines 102–133.

Difference: Let a collision-border face be at an integer coordinate and place the entity AABB strictly inside that face, with its nearest face less than `1.0E-7` from the integer boundary. The AABB and its deflated form remain strictly inside the border collision box, while the inflated form intersects the border shape. 1.15.2 therefore emits the nonempty border shape. In 1.16.5, `isBoxFullyWithinWorldBorder` is true because every AABB bound remains strictly between the same floor/ceiling coordinates used to construct the collision shape; the new guard skips both later tests and emits no border shape. The two `noCollision` helpers reduce this stream directly to a boolean. With no other intersecting nonempty shapes, the same supplied entity/query state can therefore produce `false` in 1.15.2 and `true` in 1.16.5.

Movement meaning: The exact endpoint sources show the query reachable from Player edge-support collision probes.
