# Applicability review — expanded short-pose piston query

## Verdict

The accepted multi-axis `Shapes.create(AABB)` query difference applies to all six exact 1.16 releases inspected: 1.16.0, 1.16.1, 1.16.2, 1.16.3, 1.16.4 and 1.16.5. The first inspected newer release is 1.17, where the query encoding, Boolean join/index-merge path and result differ; 1.17.1 retains that same new path. The relevant old source bodies are byte-identical across the six patch releases, while the corresponding new query bodies are identical in 1.17 and 1.17.1.

For this Shapes operation, the newest old implementation group is therefore `V1_16_2`; the version resolver can select it for 1.16.0 and 1.16.1 as the closest later change. Keep the existing lower `V1_15_2` group in place. This assignment did not audit 1.15.2 or its implementation.

## Exact sources and identity checks

Read-only source root: `D:/Javastuff/LegacyParkourCompat/decompiled_minecraft/<version>/mojmap/`.

The checked roster is frozen in `roster.md` with six narrow errata. Root ready markers and source sidecars identify 1.16, 1.16.1, 1.16.2, 1.16.5 and 1.17.1. The 1.16.3, 1.16.4 and 1.17 trees use their embedded `.decompile-provenance.json` source maps. All 144 selected Java files across the eight release trees match the corresponding per-release sidecar or embedded source map; `integrity-check.txt` records the result. Full file hashes are in `source-hashes.tsv`, release/map identities in `publication-identities.tsv`, and extracted operation-member hashes and source line starts in `operation-method-hashes.tsv`.

## Call route and gates

The bound route is the block-query branch used by `Entity.move(MoverType.SELF, vec3)` for a LocalPlayer with physics enabled, through `Entity.collide(Vec3)` and `Entity.collideBoundingBoxHeuristically`. The 1.16.0/1.16.1, 1.16.2–1.16.5, 1.17 and 1.17.1 `Entity.move` bodies differ elsewhere, but all contain the same ordered `maybeBackOffFromEdge` → `collide` call. `Entity.collide` also enters the heuristic for a nonzero vector. In the heuristic the Boolean condition is true when at least two vector components are nonzero; the assigned witness has X, Y and Z all nonzero. That branch appends `level.getBlockCollisions(entity, aABB.expandTowards(vec3))` and calls `collideBoundingBoxLegacy`. These extracted bodies have one common hash across all eight releases. `AABB.expandTowards(Vec3)` is likewise identical.

For an unchanged SELF vector to reach `Entity.collide` in every release, the fixture must bypass the LocalPlayer edge correction. `Player.maybeBackOffFromEdge` differs between 1.16/1.16.1 and 1.16.2 onward: all versions return the vector unchanged when `isStayingOnGroundSurface()` is false, and that predicate is `isShiftKeyDown()`. Hold shift false (or otherwise provide the already-corrected same vector). `noPhysics` must be false, since the move method skips collision when it is true. To keep the subsequent step-up search outside this query witness, set `onGround=false`; the 1.17 `Entity.collide` body changes a horizontal-distance comparison inside that later step-up branch, while its initial heuristic call is unchanged. This is a conditional direct collision-response fixture, not a whole-tick or movement-input claim.

The 0.6F × 0.6F `Pose.SWIMMING` dimensions remain in the Player pose table throughout 1.16.0–1.17.1. The box construction helper differs in form: 1.16.0/1.16.1 build the six AABB endpoints in `Entity.setPos`, while 1.16.2 onward delegates to `EntityDimensions.makeBoundingBox`; both use `width / 2.0F`, `height`, and the same endpoint equations. The equal swimming-sized AABB from the accepted witness is therefore representable across this range. The finding is bound to equal current AABB inputs, not a claim about pose production in an ordinary tick.

`CollisionGetter.getBlockCollisions(entity, AABB)` streams a new `CollisionSpliterator`; its 3-argument constructor delegates to the 4-argument constructor, which sets `entityShape = Shapes.create(aABB)`. That exact overload, both constructors and `collisionCheck` have identical extracted method hashes in every checked release. The candidate gate admits `Blocks.MOVING_PISTON` for the moving-piston cursor type. The full-cube exception is separate: only `voxelShape == Shapes.block()` takes the block-AABB intersection fast path; translated moving-piston shapes enter the Boolean AND branch with `entityShape`.

The MOVING_PISTON registration initializer is identical across all eight releases (member hash in `operation-method-hashes.tsv`) and uses `.dynamicShape()`. `BlockStateBase.initCache` skips cache creation for dynamic shapes and its collision-shape getter delegates to the block, so `MovingPistonBlock.getCollisionShape` reaches the block entity. The moving-piston block and block-entity collision-shape members, `getExtendedProgress`, and movement-direction member have identical extracted hashes across the eight versions. `PistonMovingBlockEntity.load` consumes `compoundTag.getFloat("progress")` directly and copies it to `progressO` in all six old releases and both new releases; the load signature changes at 1.17, but the equal raw persisted input is not transformed. This report does not claim that ordinary piston ticks produce the selected progress.

This is the **three-axis CollisionSpliterator path**. It does not apply to the one-axis ShulkerBox direct block-state scan corrected in the earlier audit erratum.

## Query encoding and admission

For each old 1.16 source, `Shapes.create(AABB)` checks the six endpoints with floor-based `findBits`; when an endpoint pair fails the grid test it preserves the exact AABB endpoints through `ArrayVoxelShape`. Its `create(AABB)`, `findBits`, `joinIsNotEmpty`, `joinUnoptimized` and index-merger factory bodies are identical across 1.16.0–1.16.5.

In 1.17, `Shapes.create(AABB)` delegates to the six-coordinate `create` overload. Its `findBits` uses nearest-integer `Math.round` with a tolerance multiplied by the denominator. `joinIsNotEmpty`, `joinUnoptimized`, the index-merger factory and `IndirectMerger` also change at this boundary. They are identical between 1.17 and 1.17.1. `BooleanOp.java` remains identical across all eight, so `BooleanOp.AND` itself is not the change; the query encoding and merged coordinate/index lists change.

With the accepted swimming AABB and movement vector, the X query endpoints are `[0.12499991, 0.8749999338418579]` in 1.16.5 and snap to `[0.125, 0.875]` in 1.17. The same MOVING_PISTON block entity state (moved STONE, source false, extending false, stored direction WEST, raw progress `0.12500004470348358F`) yields the same translated non-full collision shape in each release. The old overlap survives the `1.0E-7` merger threshold at about `1.496e-7`; the new overlap is about `5.9605e-8` and its AND cell is omitted. With Y/Z clear, the old initial collision response clips X while the new response leaves it at the requested value. The precise equal-input witness arithmetic and replay are retained in the accepted source-bound addendum; this audit extends its exact old-side applicability and confirms no 1.17.1 query reversion.

## Boundaries and limits

- Old query/source group: 1.16.0 through 1.16.5.
- First new query/source group: 1.17 through 1.17.1.
- This verifies the source operation and its conditional resolved-vector response. It does not establish persisted-progress generation, ordinary tick reachability, runtime trajectories, or tick-level parity.
- The ordinary 1.17 `Entity.move` position/box write and `Entity.collide` step-up comparison have separate changes; they are not evidence for or against this equal-input Shapes query difference.
- No wiki, product code/history, builds, tests, gameplay, source generation or shared staging was consulted or performed.
