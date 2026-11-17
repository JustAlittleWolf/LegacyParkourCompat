# Source lane revision 2: 1.21.10 → 1.21.11 ordinary entity block clipping core

This revision corrects the rotated-shape witness in `source-report.md` and withdraws the accompanying voxel-rotation movement delta. The earlier report and finding draft are preserved unchanged for audit; their claim is superseded by this report.

## Inputs and review identity

- Exact pair: Minecraft 1.21.10 Mojmap → 1.21.11 Mojmap, from the published `decompiled_minecraft/<version>/mojmap/` trees in `D:\Javastuff\LegacyParkourCompat`.
- Both ready markers bind source-manifest SHA-256 values verified against the manifests: 1.21.10 `4be26049350c1b314a0b198022cb1e7ab1e745e104047de7a5c7d099a9d7b7e1`; 1.21.11 `0c4d83fef84c101d9db88f331acec7960f3c8346228c28995e6cf57bbfcae555`. Recomputed hashes for the cited Entity, AABB, VoxelShape, Shapes, DiscreteVoxelShape, CollisionGetter, BlockCollisions, Cursor3D, Direction, OctahedralGroup, SymmetricGroup3 and DoorBlock files match their source-manifest entries as recorded in revision 1.
- Independent reviewer verdict: `review-12110-12111-voxel-rotation-projection/verdict.md`, SHA-256 `2a6e4803d8fb93f02518bbaf85ecd82143ac4e7d844547edeff2e9eed828c8bf` (the file's actual digest has no embedded space: `2a6e4803d8fb93f02518bbaf85ecd82143ac4e7d844547edeff2e9eed828c8bf`). The reviewed draft was `rotated-voxel-shape-coordinate-mapping.md`, SHA-256 `165530233bc4ee562f0e334d12a8f85b449933ba17a6cd82621736582d694547`.

## Independent re-derivation and corrected verdict

The previous `(2,1,1)` / one-cell / Y-quarter-turn witness was wrong. For `ROT_90_Y_NEG` (P321, permutation `(Z,Y,X)`, X inversion), the 1.21.10 method in `world/phys/shapes/DiscreteVoxelShape.java:23–51` sets `permX=Z`, `permY=Y`, `permZ=X`, sizes `(1,1,2)`, and remapped inversion flags as follows:

- `invertX = permX.choose(I(permX), I(permY), I(permZ)) = Z.choose(false,false,true) = true`;
- `invertY = false`; `invertZ = X.choose(false,false,true) = false`.

The source cell `(0,0,0)` therefore maps to `(sourceZ, sourceY, 1-sourceX) = (0,0,0)`, not `(0,0,1)`. In 1.21.11, `OctahedralGroup.rotate(Vector3i)` maps `(x,y,z)` to `(-z,y,x)`; rotating dimensions `(2,1,1)` gives `(-1,1,2)`, whose X fixup offset is `-(-1)-1=0`. The same cell consequently maps to `(0,0,0)` in `(1,1,2)`. The claimed occupancy delta does not exist.

The same axis convention also refutes the original DoorBlock projection claim for the reviewed rotations. In 1.21.10 `Shapes.rotate` (`world/phys/shapes/Shapes.java:296–325`), the coordinate list for destination X is taken from source Z and the old remapped flag selects `I(X)` for P321; destination Z takes source X and selects `I(Z)`. This equals the 1.21.11 direct target-axis flips for that rotation. The supplied independent review additionally retraced `Shapes.rotateHorizontal`, the Y quarter-turn aliases, and the DoorBlock facing/open/hinge selection (`DoorBlock.java:52,79–83`): all reviewed shapes are identical. Therefore the old source-level rewrite is not an accepted movement finding on this witness or DoorBlock path. **Withdraw the rotated voxel-shape delta and its predicted player-collision consequence.**

This correction does not assert that every arbitrary `OctahedralGroup` input to every shape factory was exhaustively proven equal. This bounded pass covers the ordinary axis clipping path and the reviewed quarter-turn/DoorBlock projection only; other shape-provider compositions remain outside the closed review claim.

## Ordinary clipping-core coverage: no difference found

Paired Mojmap evidence:

- `world/entity/Entity.java`: `move` (1.21.10:670; 1.21.11:685), `collide` (1025; 1044), `collideBoundingBox` / `collectColliders` / `collideWithShapes` (1077–1114; 1096–1133). Under the assigned ordinary, no-entity, interior, loaded, non-riding branch, operation order through requested-axis clipping and final `position + resolved movement` output is unchanged. Step-up, edge support, piston, and moving-border branches are excluded.
- `world/phys/AABB.java`: `expandTowards` (155–178), movement and strict-overlap predicates (217–251); formulas and operation order match.
- `world/level/CollisionGetter.java`: the block-shape context iterator (95–105 / 96–106) matches. `world/level/BlockCollisions.java`: scan bounds, cached full-chunk lookup, candidate gates and shape intersection are executable-body identical apart from nullability annotation updates. `Cursor3D.java` is identical: x-fastest, then y, then z; corner candidates are skipped. `Level.getChunkForCollisions` uses `FULL,false` on both sides. Out-of-world validity changes are excluded by the loaded-interior premise.
- `world/phys/shapes/VoxelShape.java`: box component enumeration, coordinate lookup, `min`/`max`, axis normalization and `collideX` (246–302 / 247–303) match, including `1e-7` edge thresholds, cell-index padding, travel-direction scans, first occupied component selection and min/max clipping. `Shapes.collide` (211–219 / 210–218) preserves sequential shape order and early-zero return.
- `core/Direction.java`: `axisStepOrder(Vec3)` (375 / 372) matches: YZX only if `abs(x) < abs(z)`, otherwise YXZ. A horizontal magnitude tie chooses YXZ; y is first in both lists. Shape order and equal-distance nearest-gap behavior are unchanged.

## Scope and remaining limits

This is source/static coverage for the assigned 1.21.10→1.21.11 ordinary loaded interior block-only, non-riding Entity clipping slice. It is not full-pair coverage or an independent full-tick audit. This pass does not close step-up, edge-support, velocity-collision responses, moving world-border timing, entity collision, unloaded/out-of-world behavior, or all block shape-provider compositions. No runtime trajectory claim is made. No Git, build, tests, game/TAS/Gym/server launch, or decompilation was performed.
