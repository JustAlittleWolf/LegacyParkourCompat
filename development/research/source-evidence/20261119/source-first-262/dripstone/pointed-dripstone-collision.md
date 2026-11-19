# Pointed dripstone collision geometry in 26.1.2 and 26.2

Both endpoints return the same local and world-space collision geometry for pointed dripstone at the same position and corresponding vanilla state. The 26.2 class inherits the selector and maximum horizontal offset from `SpeleothemBlock`. No independent movement finding arose from this slice.

## Paired source

Paths below start at `decompiled_minecraft/<version>/unobfuscated/net/minecraft/`. Both sources use the published unobfuscated names, without an external mapping artifact.

| Operation | 26.1.2 | 26.2 |
| --- | --- | --- |
| Registration | `world/level/block/Blocks.java:6671–6687` | `world/level/block/Blocks.java:5455–5470` and `world/level/block/PointedDripstoneBlock.java:34–56` |
| State keys and defaults | `world/level/block/PointedDripstoneBlock.java:48–52,84–94` and `world/level/block/state/properties/DripstoneThickness.java:5–10` | `world/level/block/SpeleothemBlock.java:39–42,64–75` and `world/level/block/state/properties/SpeleothemThickness.java:5–10` |
| Columns, selection, local translation, horizontal cap | `world/level/block/PointedDripstoneBlock.java:69–76,265–284` | `world/level/block/SpeleothemBlock.java:48–54,264–283` |
| Column and box construction | `world/level/block/Block.java:164–166,181–189` and `world/phys/shapes/Shapes.java:52–119` | `world/level/block/Block.java:160–162,177–185` and `world/phys/shapes/Shapes.java:52–119` |
| Collision dispatch and XZ offset | `world/level/block/state/BlockBehaviour.java:333–335,512–514,575–577,672–686,712–719,979,1235–1245` | `world/level/block/state/BlockBehaviour.java:327–329,514–516,577–579,678–692,718–725,985,1248–1258` |
| Seed and double clamp | `util/Mth.java:105–107,314–318` | `util/Mth.java:106–108,332–336` |

- Both registrations keep collision enabled and use `dynamicShape()` with `OffsetType.XZ`. The offset shape bypasses the per-state collision cache. For both enums, `BlockStateProperties.java:140–141` declares the same serialized direction and thickness property names and both enums retain all five serialized thickness values. The default remains up, tip, and dry.
- The column arguments are width and Y endpoints in sixteenths. Both select `TIP_MERGE (6,0,16)`, `TIP (6,5,16)` when down or `(6,0,11)` when up, `FRUSTUM (8,0,16)`, `MIDDLE (10,0,16)`, and `BASE (12,0,16)`. The selector reads the direction solely for a tip. Waterlogging, neighbors and collision context do not enter the selector.
- Both first select a centered column, then translate by the block state offset. The cap is `(float)SHAPE_BASE.min(X)`, exactly `0.125F`. Both get the seed from `(pos.x,0,pos.z)` with the same int product for X, long arithmetic and right shift. X and Z extract bits 0–3 and 8–11 respectively, convert to float, divide by `15.0F`, subtract double `0.5`, multiply by double `0.5` and clamp as a double against the widened float cap. Offset Y is double zero. Waterlogging and block Y do not influence that offset.

## Client collision path

`client/player/LocalPlayer.java:986–994` calls super before using measured horizontal displacement for auto-jump and walking distance. `world/entity/Entity.java` calls `collide(delta)` at 731 and 738 respectively, and the regular `collide` path at 1077–1097 and 1141–1161 uses the expanded movement box for nonzero movement, with block collision collection at 1131–1158 and 1195–1199,1214–1229. The step-up branch collects block shapes over a separate expanded box when enabled, grounded and clipped horizontally.

In each version `world/level/CollisionGetter.java:96–106` selects the entity collision context for ordinary non-null sources. `world/phys/shapes/CollisionContext.java:24–31` creates the ordinary entity context for a player. `world/phys/shapes/EntityCollisionContext.java:68–70` passes the block query through to the state. `world/level/BlockCollisions.java:78–109` applies the same chunk and cursor gates, calls the shape selector, then moves the selected and offset shape by the integer block position and tests the non-full-block shape against the swept box before adding it as a collider. A dynamic shape qualifies for the single-face cursor gate in either release. `world/phys/shapes/VoxelShape.java:77–94` performs both translations, in that order, using the same offset lists.

The covered claim is the shape of an existing pointed-dripstone state and its collection along ordinary player movement, including the selector used in step-up. This slice does not cover how world generation, growth, falling-block updates, projectile interactions or placement produce that state, or broader changes to movement callers, collision contexts and world borders. Runtime player parity and the overall version-pair inventory are unverified.

## Evidence identities

Ready markers and provenance identify the exact versions, `unobfuscated`, Vineflower 1.12.0 and Java 25.0.3+9-LTS. Local SHA-256 values matched the source and artifact manifest values in both ready markers. The primary source files and traced support files matched the source manifest entries. Client JAR hashes below are the artifact manifest entries.

| Evidence | 26.1.2 SHA-256 | 26.2 SHA-256 |
| --- | --- | --- |
| Client JAR | `b1b3158572666445eff01e82fad8c7de2e4953db6d354f311730d77a8359d0b0` | `40896ee9f1e2bec3c934daac7e93d41e9e3d9c2f8ae0ca366d52ffbfd1afa290` |
| Source manifest | `54ae320660ca911a6d20514c965c33eb414fbedd1ad6727bbaa95fe906aa23c0` | `a7ad74fc712567eb87136afc1eafcfd602b4088e9869f3596331fd34faefe894` |
| Artifact manifest | `89f648229de83b0109460695c9f1f3bed7ef36efb84ede9224e80cce87118f92` | `ba9dc53a41bca9744ec1c8ab2ce9363764a22333c39ef64d5105f4e3bf5c98ba` |
| `PointedDripstoneBlock.java` | `c07a7c56ef2920e32c2c72bd7cd18396e604e17ff77ceccfe8eb627d5dadf65f` | `5878fe1b45b4cdddedc8fc1b158d6630a3718d2d7f7e7f010c1816fd60fa5718` |
| `SpeleothemBlock.java` | — | `69119f43e1a7086bb4d36c8b00a2b6ceccfb04399f8f38f838717449b1ccb66a` |
| `BlockCollisions.java` | `eb8a8f6f07b1d6384f17987818f056d41adfd6b2ad120d26774bfa550f4fc51e` | `eb8a8f6f07b1d6384f17987818f056d41adfd6b2ad120d26774bfa550f4fc51e` |
