# Final independent product review: full-cube overlap

## Verdict

**ACCEPT integration commit `7bcbfb0c882b8483484df24350a52288b39c2f6d` against base `b10821b0b4aca4bb1df0f09c7b82262e22871d27`.** The prior lower-boundary blocker is corrected. The 1.15.2 implementation matches the accepted candidate's source-level behavior; the product retains its previous query-shape behavior for lower selections without claiming source parity for every earlier release.

This acceptance is for code and source integration only. It does not establish runtime movement parity. Build verification remains with the integration owner.

## Version routing and preserved behavior

- The strict `NativeFullCubeOverlap` at `V1_15` is removed. Fresh alias evidence confirms the 1.15, 1.15.1, and 1.15.2 `CollisionGetter`/`Shapes`/`IndirectMerger` operation is unchanged: each constructs `Shapes.create(query)` and tests the translated shape using `joinIsNotEmpty(queryShape, translatedShape, AND)`. The source alias review is `.native-source/20261031/fullcube-alias63/review.md`, SHA-256 `373a79bd085cb4749e3ffb290692c033e6aede41409b5b64c5165bb451978e33`.
- `FullCubeOverlap @V1_15_2` now resolves that old query-shape predicate for profiles 1.15, 1.15.1, and 1.15.2. Its snapped-grid, all-zero-origin, collapsed-axis, and `IndirectMerger` fallback logic matches accepted candidate `eb9b2834dd787bd86c6944f2d8aa6fdafbd2ab21`; the bound final candidate review is `.native-source/20261031/full-cube-final-review61/final-eb9b2834.md`, SHA-256 `b6f148916c070d489c81665323a9edcd90eaa202e86aa2cb4586d7d1d447561d`.
- `PreservedFullCubeOverlap @V1_14_2` exactly retains the prior `b10821b0` product operation: `Shapes.joinIsNotEmpty(Shapes.create(query), Shapes.block().move(x, y, z), AND)`. The new integer block coordinates equal the old cursor coordinates passed as doubles by `BlockCollisions`; this is behavior preservation, not a parity claim for every lower profile. The alias audit additionally verifies exact 1.14.4 uses the same query-shape operation.
- With the closest-applicable `emulates >= selected` resolver, selections before 1.15 resolve to the preservation entry, 1.15 through 1.15.2 resolve to the source-backed old operation, and 1.16+ have no applicable change and retain the native strict full-cube AABB check. This fixes the former mismatch at 1.15 and 1.15.1.

## Integration and fallback checks

- The change consolidates into the existing `FullCubeOverlapHook` and its `collision.full-cube-overlap` mechanic key; no duplicate candidate hook or operation was added.
- `BlockCollisionsMixin` wraps `computeNext`'s `AABB.intersects(DDDDDD)Z` call in the 26.2 canonical-full-cube branch. Its cursor coordinates are integral, the helper uses those cell coordinates, and `orElseGet` retains the native invocation when no hook resolves.
- `MovementRuntime.find` gates on a player, client logical side, and enabled movement profile. Non-player, logical-server (including integrated-server), and disabled/native-profile paths therefore use the original strict predicate.
- The final correction commit changes only the full-cube change classes. Merge `3e9497874ad0b0dad764c7c267a6cd2b43aef491` brings in default-branch commit `7b95511ecbe924ae11b0ca465744ab022c7e11a2`; its direct changes are research and backlog documentation. The respawn implementation in ancestor commit `71245cd3a49106252cf98423049c25fde6f1ed07` touches separate hook/mixin/change paths, so there is no overlapping source change or semantic conflict with this collision correction.

## Evidence and process

Canonical read-only source files were reviewed from `D:/Javastuff/LegacyParkourCompat/decompiled_minecraft`: the 1.15–1.15.2 aliases and the 1.14.4 fence are bound in the source alias report; 1.16.5 `CollisionSpliterator.java` SHA-256 is `19ab959c469b8edb5c371ff737a59a61b8a80bf397eff1db280064342094d297`; 26.2 `BlockCollisions.java` SHA-256 is `eb8a8f6f07b1d6384f17987818f056d41adfd6b2ad120d26774bfa550f4fc51e`. `git diff --check` over the base-to-final product range completed with no output. No product files were edited for this review; no build, tests, or game/runtime launch were performed.
