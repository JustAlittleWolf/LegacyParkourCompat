# Independent verdict: AutoJump border applicability (1.14.4–1.15.2)

Reviewed scratch finding SHA-256: `8161F41F5B750BC63DDB6D1AE2D4A6012A62D1E71FE3E79D43CC5BBC4112D1F7` (`source-1144-1152-autojump-border-applicability/finding.md`). This verdict was independently derived from the exact version-root Mojmap sources before opening that finding. No product, wiki, backlog, or Git inputs were used.

## Verdict

**Accept the bounded border-applicability conclusion; do not accept any unconditional AutoJump-timer claim.** The checked source supports no distinct border-gate or border-shape delta between 1.14.4 and 1.15.2. A swept AutoJump probe reaching across the border is insufficient by itself: `getBlockCollisions` tests the collision shape against the entity's *current* bounding box deflated and inflated by exactly `1.0E-7`, emitting the border only for `!deflatedOverlap && inflatedOverlap`. This check occurs at the start of the first stream advance, before cursor/block enumeration, and emits the un-clipped border shape. Both versions use the same strict shape overlap and border construction, whose outside shape is `Shapes.INFINITY` minus the floor/ceil-bounded interior, with infinite Y extent.

The AutoJump consumer does flatten the collision stream, in order, into AABBs and stops at the first AABB hit by either broad segment/AABB probe. For an emitted border AABB, `maxY` is positive infinity; however, the same branch then obtains its center, converts it to `BlockPos`, and reads the block one block above that sampled position. Infinite/NaN coordinates and `Mth.floor`'s double-to-int conversion make that sampled position dependent on which border AABB was first intersected. A nonempty sampled collision shape can replace the infinite height with a finite top and may trigger the existing early-return or final rise/timer gates. If it is empty, height remains infinite and the `w > q` gate prevents setting `autoJumpTime`. The available source does not establish the sampled chunk/state and subsequent branch outcome for this bounded ordinary loaded case; a lawful timer witness or unconditional result is therefore not established.

`Level.getBlockState` reads the selected block/chunk state (or void air outside build height); it does not synthesize a border block state. Border collision enumeration and the later block-state lookup are separate paths. The distinction is material to the unresolved gate.

## Exact evidence retraced

- Canonical roots: `decompiled_minecraft/1.14.4/mojmap` and `decompiled_minecraft/1.15.2/mojmap`. Version IDs and mappings were checked in each root's provenance and ready metadata; relevant source hashes match each `*.sources.sha256` manifest. The 1.14.4 Mojmap run records the bundled Feather mapping artifacts; 1.15.2 records Mojmap. Both roots record the exact releases and Vineflower/Java 25 pipeline.
- 1.14.4: `client/player/LocalPlayer.java` (move/updateAutoJump and timer/height gates), `world/level/LevelReader.java` (ordered stream and border-before-cursor gate), `world/level/border/WorldBorder.java` (moving/static outside shape), `world/phys/AABB.java`, `world/phys/shapes/{VoxelShape,Shapes}.java`, and `core/{BlockPos,Vec3i}.java` / `util/Mth.java`.
- 1.15.2: corresponding `client/player/LocalPlayer.java`, `world/level/CollisionGetter.java`, `world/level/Level.java`, `world/level/border/WorldBorder.java`, and the corresponding geometry/position helpers.

The independent code read agrees with the scratch's core disposition. Its source-derived limitation on the timer claim is appropriate. I did not run builds, tests, decompilation, or gameplay.
