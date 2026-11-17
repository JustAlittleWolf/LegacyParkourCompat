# B-lane exact source roster: 1.16.1 → 1.16.2

## Evidence identity and integrity

- Host/execution: native Windows, PowerShell (not WSL).
- Canonical Mojmap sources: `decompiled_minecraft/1.16.1/mojmap/` and `decompiled_minecraft/1.16.2/mojmap/`.
- Both provenance manifests identify one exact batch run: `decompileMinecraft --versions=1.16.1,1.16.2 --mappings=mojmap --decompiler-heap=4G`; Vineflower 1.12.0, Java 25.0.3+9-LTS, Gradle 9.7.1, TinyRemapper 0.14.1, Mapping IO 0.9.1. Provenance `createdAtUtc`: 2026-10-07T14:54:18.0204070Z (1.16.1) and 2026-10-07T14:54:34.0271731Z (1.16.2). Ready markers say exact `versionId` and `mapping=mojmap`; counts 3,572 / 3,516.
- Source-manifest SHA-256, verified against ready markers and actual manifest files: 1.16.1 `76fdd121070c2012f48cbdf5cf0c78490116afc94117c30b0858eedcd6ab9754`; 1.16.2 `10d312ca29ee48e25f4727adafb9fcfbd741dcebe81b343d3dcffa071952a7a7`.
- Mapping-file hashes from `artifacts.sha256`: 1.16.1 `client_mappings.txt` `6cc374f819bca44d940f8912044b07bdac3e240a02e37117ae1d4ad63c21409b`; 1.16.2 `client_mappings.txt` `c95a7046ab43371cc476b8b51978c6f2155c62f4cc5756b05c8ef81e0f11691f`. Client hashes also match ready markers: 1.16.1 `b4831e7b63b10588ff06ea86322108d916c536446f3aea3ae988b7fe3e1f66ef`; 1.16.2 `19960f4e3c60f2723f361844d2b4020b8bf2160bd91a1e1efde8ce5aea4ff73d`.
- Hashes for all source files named below were recomputed and match their entries in each `mojmap.sources.sha256`. Relevant class hashes are listed in the companion `candidate-hashes.txt`. No source generation or mutation was performed.

## Frozen collision/support cohort

Full source-manifest comparison found 1,147 changed/added/deleted source paths overall and 122 changed paths under `net/minecraft/world/level/block/` (including block entities). The complete shape/support method-provider scan over changed block sources leaves this bounded cohort:

1. **Support framework/API:** `Block.java`, `state/BlockBehaviour.java`, and new `SupportType.java` (no 1.16.1 counterpart). `Block.canSupportRigidBlock`, `canSupportCenter`, the default `BlockState.isFaceSturdy`, and cached face-support values changed from inline predicates to `SupportType.RIGID/CENTER/FULL`. The RIGID and CENTER shapes and `BooleanOp.ONLY_SECOND` containment checks are moved unchanged into `SupportType`; FULL is the old `Block.isFaceFull(blockState.getBlockSupportShape(...), direction)`. The old RIGID extra full-collision/face-sturdy clause is redundant with the ring-support test: full collision plus a full sturdy face already covers the RIGID ring. BlockBehaviour's cache now evaluates all three support types over the same directions using `EmptyBlockGetter`/`BlockPos.ZERO`; default block support shape remains collision shape. This is a representation/API and caching refactor, with no meaningful support-result delta found for unchanged inputs.
2. **Piston head shape provider:** `block/piston/PistonHeadBlock.java`. Per-call `Shapes.or(base, arm)` was replaced by two precomputed arrays indexed by `Direction.values()` and SHORT. `calculateShape` preserves the same `Shapes.or(base, arm)` order and uses the same base/arm constants for all six facings and both lengths. No shape geometry or state dependency change found. The inherited collision provider remains `BlockBehaviour.getCollisionShape` → `getShape` because the block has collision enabled.
3. **Fire outline provider:** `FireBlock.java`. Shape construction is precomputed by state (normalizing AGE to zero) and changes the sequence of union terms; the same side/top AABBs are selected for each property and the same DOWN fallback is used. The altered sequence has no player collision effect: `Blocks.FIRE` inherits `BaseFireBlock` configured `.noCollission()`, so `getCollisionShape` returns empty through BlockBehaviour. Fire attachment/support checks still read neighboring `isFaceSturdy`, not this outline shape.
4. **Vine outline provider:** `VineBlock.java`. It now precomputes by state. The AABB constants were renamed/redeclared with conventional directional names, and property-to-AABB references were rewritten; the evaluated AABB for each of NORTH/EAST/SOUTH/WEST/UP is geometrically the same as before. Registered VINE has `.noCollission()`, so its inherited physical collision/support shape remains empty. `isAcceptableNeighbour` still tests the neighboring block's collision face; multi-face persistence still depends on state above. No ordinary player collision delta found.

No other changed shape/collision/support provider body in the changed block-source inventory produced a meaningful eligible shape/support delta. Shape/support overrides for `LeavesBlock`, `SnowLayerBlock`, and `SoulSandBlock` retain their relevant bodies: Leaves support is empty; Snow's layer-indexed collision/support shape formulas are the same; Soul Sand retains its collision shape and full support shape (its full file hash is identical in the pair). General inherited provider code is unchanged apart from the support API/cache refactor above.

## Registrations and additions/deletions

`Blocks.java` has no added or deleted block registration between these releases; all common registered block fields remain common. The only changed initializers touching possible movement-adjacent properties are:

- FARMLAND, GRASS_PATH, SOUL_SAND add `.isSuffocating(Blocks::always)`. This changes the suffocation predicate (`Entity.isInWall` input), not collision/support shape or player movement math; damage/suffocation calculation is excluded. SOUL_SAND's `.speedFactor(0.4F)` is unchanged.
- CRIMSON_TRAPDOOR, IRON_TRAPDOOR, WARPED_TRAPDOOR add `.isValidSpawn(Blocks::never)`, a spawn eligibility property, not player movement/collision.
- Crimson/warped fences, fence gates, pressure plates, signs, slabs and trapdoors change material-color source only.
- CRIMSON_FUNGUS/WARPED_FUNGUS change configured-feature reference; CRIMSON_ROOTS/WARPED_ROOTS and NETHER_SPROUTS change material classification. These are not shape/support-property changes.

`SupportType.java` is the sole added source under `world/level/block`; no deleted block class. `Blocks.CHAIN` is registered in both releases. Per instruction, I did not evaluate CHAIN's shape transition, cauldron, hopper, or directional Shulker shape changes.

## Neighbor and ordinary player consumer closure

- Collision consumer path: `Entity.move`/`Entity.collide` → `CollisionGetter.getBlockCollisions` → `BlockState.getCollisionShape(level, pos, CollisionContext.of(entity))`. The ordinary path uses the context-sensitive collision shape, not outline shape. Fire and Vine return empty because `hasCollision` is false; PistonHead's state/context-independent getShape geometry is preserved. No other eligible provider change enters this path.
- Entity standing-face query `BlockBehaviour.State.entityCanStandOnFace` derives from the collision shape, not SupportType; its code is unchanged. The support API is used by neighboring block attachment/support logic (e.g. rails, torches/plates and face-attached blocks), while affected support-shape providers above are unchanged.
- **Separate movement-response candidates outside the shape-provider cohort:** `BedBlock.findStandUpPosition` changes from the old square scan/`getStandingLocationAtOrBelow` routine to orientation-ordered `DismountHelper.findSafeDismountLocation`, with a new bunk-bed fallback order. `LivingEntity.stopSleeping` now supplies `yRot` instead of `0`; ordinary player's respawn lookup also supplies rotation. `RespawnAnchorBlock.findStandUpPosition` changes from delegating to BedBlock's old search to trying `RESPAWN_OFFSETS` through DismountHelper with safe then fallback placement. These are direct player relocation/dismount outputs and merit separate movement findings; they're not collision-shape changes.
- `PistonMovingBlockEntity` has a changed entity iteration block inside piston pushing, a separate direct player-response candidate, not a shape-provider delta. Other changed neighbor/update methods surfaced in the method scan: GrowingPlantHeadBlock and Lantern add water tick scheduling; NetherPortalBlock changes portal-shape helper organization. They are not part of the frozen collision/support cohort and remain for separately assigned operation review.

## Coverage boundary

This report freezes and fully audits the changed block collision/outline/support provider cohort plus block registrations relevant to that cohort. It does not claim full-pair tick-graph closure or audit unrelated state-transition, piston-response, bed/anchor relocation, or the explicitly omitted classes. No repo findings, backlog, tests, decompile, build, game launch, or Git write were performed.

