# Independent product review: full-cube overlap

## Verdict

**REJECT integration commit `54dd6f7a9c016d9d1e19035a8810770747884da3` against base `b10821b0b4aca4bb1df0f09c7b82262e22871d27`**, pending correction of its lower version boundary. The accepted candidate's 1.15.2 overlap algorithm is reconciled into the existing product operation and is source-consistent in its assigned 1.15.2 domain. The added strict `V1_15` implementation is not source-consistent for profiles 1.15 and 1.15.1.

## Blocking finding: strict `V1_15` branch selects the wrong historical behavior

`ParkourVersion.V1_15` covers both 1.15 and 1.15.1. In both exact local Mojmap sources, `CollisionGetter.getBlockCollisions` creates `Shapes.create(aABB)` (line 77) and admits each translated collision shape using `Shapes.joinIsNotEmpty(voxelShape, voxelShape3, BooleanOp.AND)` (line 108). The 1.15 and 1.15.1 `CollisionGetter.java` files have the same SHA-256, `63daec0623c816d53f5dbdde407a27222fa21e62680c1096b63cf9082b1c25fd`. Their `Shapes.java` files also match 1.15.2 exactly (SHA-256 `81bf70d2c8a1d1336df72beedb30b0c9504f14decc9a06814a794aabf38203c2`). The 1.15.2 source uses the same query-shape AND path. By contrast, 1.16.5 `CollisionSpliterator.collisionCheck` and 26.2 `BlockCollisions.computeNext` use strict raw-AABB intersection for the canonical full cube.

The product adds `NativeFullCubeOverlap` annotated `emulates = V1_15`, and it evaluates `queryBox.intersects(block cell)` ([NativeFullCubeOverlap.java](../../../src/client/java/me/wolfii/legacyparkourcompat/change/v1_15/NativeFullCubeOverlap.java):15-20). Because the resolver chooses the closest change with `emulates >= selected` ([ChangeResolver.java](../../../src/main/java/me/wolfii/legacyparkourcompat/impl/ChangeResolver.java):30-31), profiles 1.15 and 1.15.1 select this strict branch. The accepted snapped-boundary witness therefore distinguishes this strict predicate from the exact 1.15/1.15.1 query-shape predicate. The lower branch cannot be described as a strict-native baseline across the claimed range.

The accepted candidate review's claim that earlier selections resolve to a strict baseline is not supported for 1.15/1.15.1 by the exact sources. The range needs to be narrowed or the branch behavior corrected. Do not treat 1.15.2 as the oldest applicable old-query profile until the earlier profile behavior is reconciled; `FullCubeOverlap` at `V1_15_2` also resolves for earlier selections when no nearer hook is available.

## Integration checks that passed

- The product updates the pre-existing `FullCubeOverlapHook`, `FullCubeOverlap` change, and `BlockCollisionsMixin` callsite. It does not add the candidate's separately named hook/change, so there is one `collision.full-cube-overlap` operation rather than duplicated resolution.
- The `V1_15_2` helper matches the accepted candidate commit `eb9b2834dd787bd86c6944f2d8aa6fdafbd2ab21` in its snapped-grid, all-zero-origin, collapsed-axis, and `IndirectMerger` array-fallback logic. This agrees with 1.15.2 `Shapes.create` / `joinIsNotEmpty` and the accepted final candidate review.
- The mixin wraps `BlockCollisions.computeNext`'s `AABB.intersects(DDDDDD)Z` invocation, the canonical-full-cube branch used by the 26.2 source. The cast inputs are the cursor's integer block coordinates; the native fallback preserves the original call.
- `MovementRuntime.find` rejects non-player entities, non-client logical sides, and disabled movement profiles through `appliesTo`; the `orElseGet` path keeps native behavior for those cases. The change is in client sources and does not run historical movement on an integrated server.
- No code-level conflict is introduced by incoming commit `6e978039`; it adds research/backlog documentation. This report reviews the specified product commit, before integration of that incoming documentation commit.

## Evidence and limits

Primary sources were read from the read-only canonical `D:/Javastuff/LegacyParkourCompat/decompiled_minecraft` checkout. Whole-file SHA-256 values: 1.15 and 1.15.1 `CollisionGetter.java`, `63daec0623c816d53f5dbdde407a27222fa21e62680c1096b63cf9082b1c25fd`; 1.15.2 `CollisionGetter.java`, `63daec0623c816d53f5dbdde407a27222fa21e62680c1096b63cf9082b1c25fd`; 1.16.5 `CollisionSpliterator.java`, `19ab959c469b8edb5c371ff737a59a61b8a80bf397eff1db280064342094d297`; 26.2 `BlockCollisions.java`, `eb8a8f6f07b1d6384f17987818f056d41adfd6b2ad120d26774bfa550f4fc51e`.

This is a source and integration review only. No product source was edited; no build, tests, or runtime launch was performed. Compilation and runtime parity remain unreviewed here.
