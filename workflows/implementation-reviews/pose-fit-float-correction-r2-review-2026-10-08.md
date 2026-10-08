# Independent re-review: pose fit float correction

**Decision: ACCEPT**
**Reviewed branch:** `fix/movement-pose-fit-float-state-2026-10-08`
**Exact reviewed tip:** `ea62550bbecc8233917fb293dbe3ee627529f621`
**Feature base / merge base:** `1ea23480755a2574abd5b5855827ded5d5f50702`
**Current `main` merged before handoff:** `64895d266fcee5da774c3236295ba949045200c7`

## Summary and scope

Reviewed the six-file feature diff from `1ea23480755a2574abd5b5855827ded5d5f50702` to `ea62550bbecc8233917fb293dbe3ee627529f621`: 97 insertions and 3 deletions. The focused changes are the V1_13 pose-fit implementation, V1_8/V1_12 false-returning resolver markers, the shared player pose injection and the F002 implementation record. **No functional findings.** `git diff --check` is clean.

The accepted source finding remains commit `448934e826fb41266dc79a313ef1187899d76382`, path `workflows/source-campaign-2026-10-07/1.13.2--1.14.4/findings/F002-pose-selection-and-collision-aware-resize.md`, SHA-256 `baa5c6b30167eaa8024d34b39186440be944115061c8f39fdf5e17555b8ec67e`. Its blind acceptance commit remains `10aa24e1114200397ed2d042a33f269113faf179`; the file hash was rechecked and is unchanged.

## Verification

### Collision query and exact resize math

The reviewed V1_13 implementation at `src/main/java/me/wolfii/legacyparkourcompat/change/v1_13/SneakingDimensions.java:16-39` compares the requested `EntityDimensions` float width and height with `getBbWidth()` and `getBbHeight()`, which return the retained float dimensions in 26.2 `Entity.java:3691-3697`. This matches 1.13.2 `PlayerEntity.updatePlayerPose()` at lines 334-360, which tests `f != this.width || g != this.height`.

The requested AABB uses the current box's `minX`, `minY`, and `minZ`, then adds width, height, and width in the same order as the 1.13.2 `new Box(box.minX, box.minY, box.minZ, box.minX + f, box.minY + g, box.minZ + f)`. Float operands are widened for the double-coordinate addition in both versions; the correction preserves the source arithmetic and anchor.

The query is now `player.level().noBlockCollision(null, requested)`. In 1.13.2, `WorldView.hasNoCollisions(null, box)` at lines 201-206 checks the block collision stream, while `World.getCollisions` at lines 1755-1758 adds entity collisions only for a non-null entity. In 26.2, `CollisionGetter.noBlockCollision` at lines 55-67 uses `getBlockCollisions` alone; with null, that call uses `CollisionContext.empty()` (lines 96-98). It does not add entity or world-border collision checks. This reproduces the historical null-source block-fit query; the prior entity-inclusive mismatch is corrected.

When the candidate dimensions differ and the block query fails, the implementation leaves pose and dimensions untouched and returns `true`. `PlayerMixin` then cancels native `updatePlayerPose()V`; the rejected enlargement therefore retains the previous size, matching the historical `setSize`-only-on-fit behavior. On success, it sets the mapped pose; when that pose is already set, it explicitly refreshes dimensions. In current 26.2, pose data updates refresh dimensions, and `LivingEntity.refreshDimensions()` recomputes the dimensions and bounding box. No partial resize path was found.

### Resolver coverage and bindings

`ChangeResolver.resolve` returns no changes for `CURRENT`, then selects the closest registered change whose `emulates` version is greater than or equal to the selected profile. The registry creates an independent key for each hook implemented by a registered change. Searching the full source tree found exactly three `PlayerPoseBehavior` implementations:

- V1_8 `SneakingDimensions`, whose marker returns `false`.
- V1_12 `SwimmingDimensions`, whose marker returns `false`.
- V1_13 `SneakingDimensions`, whose marker performs the corrected historical update.

Both older classes are registered by their `MovementChanges` providers. Thus V1_8 selects its V1_8 marker; V1_9 through V1_12 select the nearer V1_12 marker; V1_13 selects the active handler; V1_14 and later have no eligible pose hook; and `CURRENT` resolves an empty profile. This closes the earlier-profile fall-forward.

The exact 26.2 `Player.updatePlayerPose()V` method exists and matches the `@Inject` descriptor in `PlayerMixin.java:28`. `PlayerMixin` remains in the common mixin list. `LivingEntityMixin` continues to dispatch `PlayerDimensionsBehavior` through `player.getDimensions(pose)`, so the pose handler's dimension lookup reaches the intended V1_13 dimensions. Current-main swimming-pitch handling and sneak-edge injections are in separate `travel`, `maybeBackOffFromEdge`, and `canFallAtLeast` hooks; no binding or semantic overlap was found.

## Six review angles

1. **Requirement fit — PASS.** Float state, historical block-fit semantics, rejected-resize retention, and profile boundaries are implemented.
2. **Correctness and edge cases — PASS.** Query context, AABB anchor and operation order, and success/failure state changes match the checked source behavior.
3. **Missing coverage — PASS.** The false markers cover V1_8 through V1_12, V1_13 has its active handler, and later/current profiles retain native behavior.
4. **Conventions and duplication — PASS.** The change uses the existing shared mixin and versioned hook; it does not duplicate movement or physics loops.
5. **Permissions and visibility — PASS.** No permissions, configuration, or user-visible access changed.
6. **Security — PASS.** No new trust boundary, input handling, or resource access was introduced.

## Main comparison and limitations

The current `main` tip `64895d266fcee5da774c3236295ba949045200c7` was merged into this isolated review branch. Changes since the feature base are four documentation files only; no code changed in that interval. The existing swimming-pitch redirect and sneak-edge hooks were checked against the new pose hook and remain method-distinct.

The available 1.13.2 source is the accepted campaign artifact, but the original derived artifact's provenance is still unproven; this review does not resolve that limitation. Source files checked included the 1.13.2 `PlayerEntity.java`, `World.java`, and `WorldView.java`, and the ready 26.2 `Player.java`, `Entity.java`, `LivingEntity.java`, and `CollisionGetter.java`. The ready 26.2 manifest SHA-256 is `a7ad74fc712567eb87136afc1eafcfd602b4088e9869f3596331fd34faefe894`.

No build, tests, client, TAS, server, or runtime checks were run, as requested. Runtime collision-provider outcomes remain outside this static re-review.

**Next step:** no code correction is requested by this re-review; the corrected patch is ready for the implementation owner to accept.
