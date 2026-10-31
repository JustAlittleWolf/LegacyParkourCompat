# Roster erratum 2 — close the selected route dependencies

The source comparison now includes these direct dependencies required by the assigned operation trace and the AND/index-merge admission claim:

- `net/minecraft/world/phys/AABB.java` — `expandTowards` used to create the swept query.
- `net/minecraft/world/phys/shapes/BooleanOp.java`, `IndexMerger.java`, and `BitSetDiscreteVoxelShape.java` — Boolean AND application, merged index traversal and discrete cell membership used by the admission witness.
- `net/minecraft/world/level/block/state/BlockState.java` and `BlockBehaviour.java` — state collision-shape delegation from `CollisionSpliterator` to `MovingPistonBlock`.

These are route dependencies, not additional mechanics. All releases and exclusions remain unchanged. The released source rows will be hash-checked against each release's sidecar source manifest or embedded source map.
