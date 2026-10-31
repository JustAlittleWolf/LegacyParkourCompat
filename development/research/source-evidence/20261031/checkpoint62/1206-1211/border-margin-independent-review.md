# Border lower-margin equality candidate review

## Verdict

**Accepted as a movement-visible difference at the `1.20.6` → `1.21` source boundary.** The strict-to-inclusive lower X/Z margin comparison changes whether `Entity` collects the world-border collision shape. A valid movement request at a lower-border corner can then resolve to a different vector. `1.21` → `1.21.1` has no further change in the inspected methods or collision helpers.

## Source identity and provenance

Inspected Mojmap trees: `D:/Javastuff/LegacyParkourCompat/decompiled_minecraft/{1.20.6,1.21,1.21.1}/mojmap/`. The endpoint `mojmap.ready.json` markers identify versions `1.20.6` and `1.21.1`, namespace `mojmap`, status `ready`; their `mojmap.provenance.json` records the exact decompile runs. The ready markers bind `mojmap.sources.sha256` by SHA-256: `1.20.6` manifest `56aae10684471d7abb1c366bd5dd431ab976112a87e6cc5c687a68f1eff06311`, `1.21.1` manifest `900f956e00f6fc1300bb3d689ea49df2b1a57bcaa54617344ef456d95d47cb48`. Both bindings were recomputed and match. `WorldBorder.java`, `Entity.java`, and `VoxelShape.java` hashes match the corresponding entries in each endpoint source manifest. The 1.20.6 `mojmap.provenance.json` records exact version `1.20.6`, namespace/mapping option `mojmap`, its successful batch run and pinned build tools; the 1.21.1 file records the corresponding exact `1.21.1` Mojmap run.

The `1.21/mojmap/.decompile-provenance.json` also pins its `1.21.json`, client JAR and Mojang `client.txt` mapping inputs. Its source hashes for `WorldBorder.java`, `Entity.java`, `VoxelShape.java`, and `Shapes.java` match the files on disk. The corresponding `1.21.1` `WorldBorder.java`, `Entity.java`, `VoxelShape.java`, and `Shapes.java` are byte-identical to the manifest-verified 1.21 files. The earlier note that the endpoint trees lacked manifests was incorrect: the manifests are version-level files at `decompiled_minecraft/<version>/mojmap.*`, outside the nested source folder.

Verified endpoint and embedded source hashes:

| Mojmap source | 1.20.6 | 1.21 (embedded manifest) | 1.21.1 |
|---|---|---|---|
| `WorldBorder.java` | `5ff25236e831248a715c1275ef56ec15ef4efa68580b485fbfd92a3b6a857eac` | `b552c94af6790a24aae44c9ea02624dfb6b12dee27e490bdbd618b7352e24e75` | same as 1.21 |
| `Entity.java` | `71cd6b9f6c002684154dce11d3745e8714d82f13c8e1b3aa56743930131c18f3` | `b81905c7879e2cc5c5063a41d865c4164ad919f156306705be791d1017b99850` | same as 1.21 |
| `VoxelShape.java` | `106eb81ee1b2b0f7aa22c225524d4ada28cb28906ffffc9f7e790e4ffefbe9ed` | `0d4e5c8989e6851816a70c31a32f38071744495e4ee446b7dc5165443884a802` | same as 1.21 |

## Compared source

- `1.20.6`, Mojmap: `net/minecraft/world/level/border/WorldBorder.java:46-48` uses `x > minX-margin` and `z > minZ-margin`; `:76-79` computes `margin = max(absMax(sweptBox.xSize, sweptBox.zSize), 1.0)`, then gates with `distanceToBorder < 2*margin` and `isWithinBounds(entityX, entityZ, margin)`.
- `1.21`, Mojmap: `WorldBorder.java:56-58,90-93` preserves the same margin and distance gates but changes only the lower comparisons to `x >= minX-margin` and `z >= minZ-margin`. Upper comparisons remain strict. `1.21.1` has identical bytes for these methods.
- `1.20.6`, Mojmap: `net/minecraft/world/entity/Entity.java:900-912` expands the current AABB toward the requested vector, conditionally adds `WorldBorder.getCollisionShape()`, then appends block collision shapes. `Entity.java:597,617` routes `move` through `collide`.
- `1.21`, Mojmap: `Entity.java:927-945` does the same collection through `collectColliders`; the world-border shape is inserted before block collisions. `Entity.java:598,618` retains the `move` → `collide` route. `1.21.1` is byte-identical in these files.
- Border geometry: `WorldBorder.java:536-545` in 1.21 (1.20.6 `:522-531`) forms `Shapes.INFINITY ONLY_FIRST Shapes.box(floor(minX), -∞, floor(minZ), ceil(maxX), +∞, ceil(maxZ))`: the collision shape is the exterior of the floored/ceiled interior rectangle. The shape clipping used below is in `net/minecraft/world/phys/shapes/VoxelShape.java:222-269` and `Entity.java:963-982`; `1.21.1` matches the 1.21 source hashes.

The `isWithinBounds(AABB)` overload also changes shape in 1.21: 1.20.6 `WorldBorder.java:50-52` tests strict AABB/border overlap; 1.21 `:44-50` checks the AABB min corner and its max corner inset by `1.0E-5F`. `CollisionGetter.java:129` uses this overload while filtering block shapes. This is a separate filter change; it is not the gate responsible for the movement witness below, which uses an empty block-collision set.

## Exact movement witness

Use an otherwise empty area, a regular player AABB of width `0.6`, an airborne player (so the step-up branch is not entered), and a static border whose X and Z minima are `0.9` and `0.5` respectively (for example size `1000`, center `(500.9, 500.5)`). Put the player center at `(x,z)=(-0.1,0.5)` and request movement `(0,0,-0.39)`.

The swept AABB has X size `0.6`, Z size `0.99`, so `isInsideCloseToBorder` uses margin `1.0`. The player X equals `minX-margin` (`-0.1`); `getDistanceToBorder` is `-1.0`, which passes the unchanged `< 2.0` gate. The old strict lower-X comparison therefore rejects shape collection, while 1.21's inclusive comparison accepts it. The Z coordinate passes both versions' bounds checks.

The border collision interior starts at `floor(minZ)=0`. The player's current Z interval starts at `0.2`; the requested `-0.39` crosses that collision plane. The exterior border shape occupies the slab below Z=0, and `VoxelShape.collide`'s negative-axis branch caps motion at `shapeBoundary - box.minZ = 0 - 0.2 = -0.2` (within its `1.0E-7` tolerance). With no block/entity shapes, old collection returns Z `-0.39`; 1.21 collects the border shape and resolves Z to `-0.2`. The changed resolved vector is therefore `(0,0,-0.39)` → `(0,0,-0.2)` for equal state and input. This is a local-player `move`/`collide` path, not a proposed trajectory.

The exact intermediate patch that introduced the comparison change is not established by this endpoint comparison.

## Local-player state assumption

The witness is an equal valid position/border state with the local player outside the current border along X: its X center is exactly one margin west of the border's actual minimum. A border can have that state while the player is there; this review makes no claim about a trajectory, producer, or timing that placed the player there.
