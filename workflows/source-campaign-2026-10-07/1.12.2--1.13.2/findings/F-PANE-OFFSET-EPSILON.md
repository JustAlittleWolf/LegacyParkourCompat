# F-PANE-OFFSET-EPSILON — pane center collision clipped differently at an axis boundary

- Older version A: 1.12.2
- Newer version B: 1.13.2
- Mechanic / coverage slice IDs: PANE-COLLISION-AXIS-EPSILON, PANE-COLLISION-GEOMETRY, PANE-CONNECTION-EXCEPTION, WORLD-COLLISIONS, ENTITY-MOVE-AXES.
- Classification: changed behavior
- Confidence: source-confirmed for the exact geometry and input below; independent blind review pending.
- Applicability: historical player movement using an existing Glass Pane block
- First changed release: unknown within (1.12.2, 1.13.2]
- Runtime validation: not performed

## Paired source evidence

- A `PaneBlock.java#addCollisions` (lines 53-85) contributes a center box spanning X/Z `[7/16,9/16]`, Y `[0,1]`; the method uses `Block.addCollision`, which translates the box and collects it only when strict `Box.intersects` is true. `Block.java` SHA-256 `e4a90eca411e7b0e14f5018f7385ec29891f6cdf1a1e917fa648b5712f9b33a1`; `Box.java#intersects` SHA-256 `f788b8146b14f299ccb58ea0854609f845c298a963295d503de2e30e15d66f3a`; `PaneBlock.java` SHA-256 `83862f6633b8241c3788e9c9a7e4fd0275c010ec790019b8003908996f4814b3`.
- B `PaneBlock.java` inherits `IronBarsBlock`'s collision shape; `PaneBlock.java` SHA-256 `9422e3f2aa9843ecd041f5ced13d05a4c7472d697f7dfd838450e49969e5326c`, `IronBarsBlock.java` SHA-256 `df6d956bb2745241e59294056f31513151b976dbd9ceed3f5478fd72b66c68a0`. With all four connection bits false, its center voxel has the same `[7/16,9/16]` X/Z footprint and full-block Y.
- A `Entity.move(MoverType,DDD)` resolves Y, then X, then Z and clips X against collected boxes; SHA-256 `80f091bf32166c88cf8bbd31caf72d84fa16224410733c7d2a0f00563f294a0a`. B's paired `Entity.move` uses the collision-shape offset path; SHA-256 `1d6ec8b80f74635401745c2c027bf36555c85348ca5693764f2668363b17d269`.
- B `WorldView.getCollisions` builds a swept query with a `1.0E-7` expansion and delegates axis clipping to `VoxelShape.calculateMaxDistance`; `WorldView.java` SHA-256 `9577360b1a1bdde9280f0b72593e5e6dde45d9a54719cd7ff933f3ecd31b971f`, `VoxelShape.java` SHA-256 `d95b0607647da11595e24e1e26e9615cb7da7705ea7885908544362df62275c1`, `VoxelShapes.java` SHA-256 `313968d4e5855b5ec380272b6ababcc5849d478ab771926355912a8cb6aa9c95`.
- The named source files match their exact-version ready source manifests. Evidence artifacts are the immutable revised snapshots recorded in `run.md`; no original mapped-bytecode identity claim is made.

## Exact source-derived query

Place the corresponding historical `glass_pane` at `(0,64,0)` with AIR in all four horizontal neighbor positions. Both connection predicates yield four false flags, and both providers contribute the same center voxel. Use an airborne player box with X `[-0.16251,0.43749]`, Y `[64.2,66.0]`, and Z `[-0.16249995,0.43750005]`, requesting movement `(1.0,0,0)`. Assume no other collision shapes, entities, or world-border contact.

In A, the player box strictly intersects the pane center box because its Z maximum exceeds `0.4375`; positive-X clipping leaves a gap of `0.4375 - 0.43749 = 0.00001`, so the applied X displacement is `0.00001`. In B, the swept query includes the pane, but the X-axis shape clipping derives the orthogonal Z cell range from `box.maxZ - 1.0E-7 = 0.43749995`, which is below the pane interval's `0.4375` lower bound. The occupied center voxel is omitted from that axis range, so the pane does not clip X and the applied displacement is `1.0`.

This is a concrete source-derived difference for the stated old-block state and query. It is related to, but distinct from, `F-VOXEL-OFFSET-EPSILON`: this case is the orthogonal interval inset at a pane center-voxel boundary, not repeated-shape processing of a sub-epsilon residual distance. It does not assert that all pane queries differ.

## Scope and handoff

- State path: four AIR neighbors -> matching false pane connection flags -> matching center voxel -> A strict translated-box collection / B swept voxel-shape collection -> player X-axis collision resolution.
- `PANE-CONNECTION-EXCEPTION` compares the common historical neighbor predicate and block identities. A's query-time recomputation versus B's stored-state lifecycle is explicitly excluded by the user as a difference on its own.
- Related candidate: `F-COLLISION-REPRESENTATION`; related finding: `F-VOXEL-OFFSET-EPSILON`.
- Independent blind review: pending. This is source evidence, not a runtime measurement or pair-completion claim.
