# Chest collision geometry, 26.1.2 to 26.2

## Verdict

No source difference in collision geometry contributed by ordinary and trapped chests to the local-player movement query. This covers SINGLE and both paired chest states in every horizontal facing, including waterlogged states. The comparison uses native fixed-version source and the same vanilla block state as input.

Both releases use a centered single-chest column with X and Z in [1, 15]/16 and Y in [0, 14]/16, in block-local coordinates. Paired halves use the same height. The north template has X in [1, 15]/16 and Z in [0, 15]/16, and the horizontal rotation moves the zero-inset face to the connected side. LEFT uses clockwise from FACING and RIGHT uses counterclockwise, so two correctly paired halves are flush at the join. The shape does not read waterlogging, block entities, lid position, neighbors or collision context. The default SINGLE state and the placement and neighbor pairing predicates also match.

## Exact paired source

All Minecraft paths below are relative to net/minecraft/ within decompiled_minecraft/<version>/unobfuscated/, except where a full package path is shown.

| Operation | 26.1.2 | 26.2 |
| --- | --- | --- |
| Geometry constants and state selection | world/level/block/ChestBlock.java 72–76, 191–202 | 73–77, 192–203 |
| Default state, pairing and state properties | world/level/block/ChestBlock.java 136–188, 210–249, 381–383 | 137–189, 211–250, 382–384 |
| Coordinate normalization and primitive bounds | world/level/block/Block.java 164–203 | 160–199 |
| Shape construction and rotation | world/phys/shapes/Shapes.java 52–119, 322–425 | 52–119, 323–426 |
| Direction selection and rotation definitions | core/Direction.java 195–203 and 245–253, com/mojang/math/OctahedralGroup.java 68–70 | same |
| Ordinary and trapped chest registration | world/level/block/Blocks.java 1378–1382, 2936–2940 | 1274–1278, 2883–2887 and references/BlockItemIds.java 312, 571 |
| Trapped chest inherits chest shape and ordinary chest uses the block collision default via AbstractChestBlock and BaseEntityBlock | world/level/block/TrappedChestBlock.java 19–29, AbstractChestBlock.java 13–20, BaseEntityBlock.java 14–17 | same |
| Collision enabled by default, shape delegated through block state and large-shape cache derived from collision bounds | world/level/block/state/BlockBehaviour.java 103, 333–335, 575–577, 672–685, 925–947, 979 | 104, 327–329, 577–579, 678–691, 931–953, 985 |
| Local player delegates move to inherited entity movement | client/player/LocalPlayer.java 986–994 | same |
| Entity movement invokes collision with physics enabled and collects shapes for ordinary and step-up sweeps | world/entity/Entity.java 704–732, 1077–1106, 1131–1177 | 711–739, 1141–1170, 1195–1199, 1214–1229, 1244–1260 |
| Entity-context query constructs block collision iterator | world/level/CollisionGetter.java 96–106, world/phys/shapes/CollisionContext.java 24–31 | same |
| Loaded chunk state, cursor gates, translated shape and swept-box intersection | world/level/BlockCollisions.java 64–109 | same, byte-identical file |
| Collision context delegates to the block state | world/phys/shapes/EntityCollisionContext.java 68–70 | same, byte-identical file |

In the traced path, both versions pass the local player as the entity context. The 26.2 entity overload uses a helper whose name references ignoring the world border but retains the old border insertion check. The new context-taking overload omits that insertion and the ordinary player path uses the entity overload. BlockCollisions uses the same intersection for partial shapes and the same cursor and null-chunk gates. The chest provider and its immediate dependencies have no remaining evidence gaps. Other block shapes, player travel branches and effects beyond collision geometry are outside this slice. No runtime verification was performed.

## Source identity

Exact published client releases, unobfuscated namespace, Vineflower 1.12.0. Ready and provenance markers identify the corresponding release on each side. The actual source and artifact manifest SHA-256 values match the ready markers. Source files relied on in the comparison were individually hashed and matched against their source manifest entries.

| SHA-256 | 26.1.2 | 26.2 |
| --- | --- | --- |
| Source manifest | 54ae320660ca911a6d20514c965c33eb414fbedd1ad6727bbaa95fe906aa23c0 | a7ad74fc712567eb87136afc1eafcfd602b4088e9869f3596331fd34faefe894 |
| Artifact manifest | 89f648229de83b0109460695c9f1f3bed7ef36efb84ede9224e80cce87118f92 | ba9dc53a41bca9744ec1c8ab2ce9363764a22333c39ef64d5105f4e3bf5c98ba |
| Client JAR (artifact manifest entry) | b1b3158572666445eff01e82fad8c7de2e4953db6d354f311730d77a8359d0b0 | 40896ee9f1e2bec3c934daac7e93d41e9e3d9c2f8ae0ca366d52ffbfd1afa290 |
| ChestBlock.java | 952a85dfac87f50d0dc054487e4b7d215c8eeb02304eb5bb8001a31bfbd3dc56 | 8be5c44fe3e8b5c9f529f58e0e3694a7ce1590a1957c31b72cbec7b97e061d22 |
| BlockCollisions.java | eb8a8f6f07b1d6384f17987818f056d41adfd6b2ad120d26774bfa550f4fc51e | eb8a8f6f07b1d6384f17987818f056d41adfd6b2ad120d26774bfa550f4fc51e |
| EntityCollisionContext.java | 2f0a2bfd72a3d35121e3ef4ab80755f04cefaa7dd933988e2246b39ff5c39291 | 2f0a2bfd72a3d35121e3ef4ab80755f04cefaa7dd933988e2246b39ff5c39291 |
