# Hopper collision geometry, 26.1.2 → 26.2

## Scope and evidence

Exact vanilla Java endpoints, published unobfuscated namespace, Vineflower 1.12.0 on Java 25. Sources are read-only under `decompiled_minecraft/<version>/unobfuscated/`. The two source and artifact manifests rehashed to their ready-marker digests. Rehashed relevant source files matched their published source-manifest entries. The client hashes below identify the entries in the published artifact manifests.

| SHA-256 | 26.1.2 | 26.2 |
| --- | --- | --- |
| Source manifest | `54ae320660ca911a6d20514c965c33eb414fbedd1ad6727bbaa95fe906aa23c0` | `a7ad74fc712567eb87136afc1eafcfd602b4088e9869f3596331fd34faefe894` |
| Artifact manifest | `89f648229de83b0109460695c9f1f3bed7ef36efb84ede9224e80cce87118f92` | `ba9dc53a41bca9744ec1c8ab2ce9363764a22333c39ef64d5105f4e3bf5c98ba` |
| Client jar manifest entry | `b1b3158572666445eff01e82fad8c7de2e4953db6d354f311730d77a8359d0b0` | `40896ee9f1e2bec3c934daac7e93d41e9e3d9c2f8ae0ca366d52ffbfd1afa290` |
| HopperBlock.java | `7a4e5afa06b90cac7800ab0984799d7933dea9954373653ac43c4697f075b1b5` | `e6687c0f3a34a796e5faf7bb04004c9b3cadc700119f417087eae4eb6aeb298a` |
| BlockCollisions.java | `eb8a8f6f07b1d6384f17987818f056d41adfd6b2ad120d26774bfa550f4fc51e` | `eb8a8f6f07b1d6384f17987818f056d41adfd6b2ad120d26774bfa550f4fc51e` |
| EntityCollisionContext.java | `2f0a2bfd72a3d35121e3ef4ab80755f04cefaa7dd933988e2246b39ff5c39291` | `2f0a2bfd72a3d35121e3ef4ab80755f04cefaa7dd933988e2246b39ff5c39291` |
| BlockBehaviour.java | `9db85de84e502903e6fe497f043b58620b92089236ffb213f0b93db979428d13` | `9c7a103492d0714c90397da88eb696912ff6a9ca1c005d984c4746d52637fd1e` |
| Shapes.java | `cfa63cbed77c922a68e7e8a6a3b78b09a22c7de51e0146e481e9c9d26db66f58` | `6ed9460d58727770bdec59c4f3184f075644f3c411be1eeab2eae17e8ece1891` |
| VoxelShape.java | `419a46bbcc8b48e1c075127954eb243dcc60d10d4087bf256d895cf91a2ff854` | `419a46bbcc8b48e1c075127954eb243dcc60d10d4087bf256d895cf91a2ff854` |

## Paired terminal comparison

Paths below are relative to `net/minecraft/` within each version's unobfuscated source directory. The first range is 26.1.2 and the second is 26.2.

| Checked operation | Source ranges | Comparison |
| --- | --- | --- |
| Registry and state | `world/level/block/Blocks.java` 2979–2983 / 2926–2930, `references/BlockItemIds.java` 561 in 26.2, `world/level/block/state/properties/BlockStateProperties.java` 56 in both, `world/level/block/HopperBlock.java` 40–55 and 157–159 / 41–56 and 158–160 | Both register `HopperBlock::new` with the same properties. `BlockItemIds.HOPPER` wraps the same `hopper` identifier used by 26.1.2. Both default to DOWN and ENABLED true. Facing admits DOWN and all horizontal directions. |
| Geometry and state lookup | `world/level/block/HopperBlock.java` 52–80 / 53–81, `world/level/block/Block.java` 164–203 and 592–613 / 160–199 and 588–609 | Both union centered 16-wide Y=10–16 and 8-wide Y=4–10 columns, subtract the centered 12-wide Y=11–16 inside volume using `ONLY_FIRST`, then union the facing's rotated spout intersected with the unit block using `AND`. Both rotate `boxZ(4.0,4.0,8.0,0.0,8.0)` around `(8.0,6.0,8.0) * 0.0625`. Both normalize ENABLED before map lookup, and their shape ignores level, position, and context. Box normalization divides coordinates by 16.0 in both. `getInteractionShape` exposes a separate map. |
| Inherited collision dispatch | `world/level/block/state/BlockBehaviour.java` 333–335, 672–686, 1086–1095 / 327–329, 678–692, 1094–1103 | `noOcclusion()` clears occlusion only. The context overload delegates to the block, and inherited collision returns `state.getShape(level,pos)` when `hasCollision` is true. This shape call uses the empty context and dispatches to the hopper's precomputed lookup. No hopper override intercepts it. |
| Ordinary movement and step-up | `client/player/LocalPlayer.java` 986–989 in both, `world/entity/Entity.java` 731, 1077–1106, 1131–1176 / 738, 1141–1170, 1195–1229, 1244–1260, `core/Direction.java` 378–380 in both | LocalPlayer delegates to Entity. Ordinary collision gathers block shapes against the bounding box expanded toward movement and recollects for a gated step-up. The ordinary entity collector retains entity shapes, optional border shape, then block shapes in that order. Both resolve Y first, followed by the larger-magnitude horizontal component, and X first on horizontal ties. Each nonzero axis clips the AABB translated by movement resolved so far. |
| Block enumeration and support | `world/level/CollisionGetter.java` 96–106 and 138–153 in both, `world/level/Level.java` 764–766 / 769–771, `world/level/BlockCollisions.java` 31–110 in both, `world/phys/shapes/EntityCollisionContext.java` 68–70 in both | Both create an ordinary entity context, read states from a FULL chunk requested without creating it, then scan the same cursor bounds and filters. The iterator obtains the state's shape, translates it to world position and checks overlap before yielding it. Supporting-block queries use the same iterator and identical distance and tie-break logic. The 26.2 context helper's visibility and new explicit-context caller leave the ordinary entity path unchanged. |
| Shape math | `world/phys/shapes/Shapes.java` 233–241 and 403–446 / 234–242 and 404–447, paired `world/phys/shapes/VoxelShape.java` | Compared voxel box creation, boolean combinations, rotation and clipping helpers. Changes in `Shapes` concern visibility or annotations. `VoxelShape` is byte-identical across the two source files. |
| Direct inside callback | `world/level/block/HopperBlock.java` 162–175 / 163–175, `world/level/block/entity/HopperBlockEntity.java` 448–454 / 446–452 | The lookup still checks for the hopper block entity before forwarding. The consumer requires an `ItemEntity`, so it adds no direct player movement effect. |

## Disposition

For the same vanilla hopper state and query, the bowl floor and rim, body and facing-specific spout contribute identical collision geometry. This is source continuity for ordinary local-player hopper geometry. The bounded slice has no remaining hopper-specific dependency. General movement changes, use of the new explicit-position context, and runtime parity sit outside this slice. No finding draft was produced.

