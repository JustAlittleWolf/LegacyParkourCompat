# Independent review — moving-piston head threshold scope

## Decision

**Source finding and bounded player-query scope: ACCEPT. Implementation handoff: REQUEST CHANGES before production code.** No source-author proof was edited and no production code was changed.

| Claim | Verdict | Review note |
| --- | --- | --- |
| F-S5 source delta at the accepted endpoints | ACCEPT | Exact selector and relevant source hashes agree with the immutable finding and independent source acceptance. |
| Ordinary player collision-shape query is eligible | ACCEPT | Player movement passes an entity-derived `CollisionContext` through block collision queries to the moving-piston shape; this is a conditional collision-shape input, not a trajectory claim. |
| `PistonMovement` cap is unrelated; moving-piston shape behavior is unimplemented | ACCEPT | The cap changes a player displacement vector in `Entity.limitPistonMovement`; no `minecraft:moving_piston` shape registration exists. |
| Exact transition / 1.15.1 boundary | ACCEPT AS OPEN | Exact 1.16.0 source now confirms the replacement at the first 1.16 release; 1.15.1 remains unavailable, so do not extend the 4.0F behavior to the `V1_15` profile. |
| Version range and hook plan as written | REQUEST CHANGES | Older representatives prove both 4.0F and 0.25F behavior, while the resolver propagates a V1_15_2 change to older targets. The plan must specify an exact target-profile gate and carry player context to the selector before `SHORT` is chosen. |

## Bindings and review base

- Immutable finding: `58e56ee59d7cc4015d499e3e2a37ec1d45049d56:workflows/source-campaign-2026-10-07/1.15.2--1.16.5/findings/F-S5-PISTON-HEAD-THRESHOLD.md`; blob `54def810126026ecb51f389c346b564f4b7503d3`; raw SHA-256 `58cd05981150b0c162ecd10ea16560e7cfbb50cdf5027cb57577cf32a4c30163`.
- Finding-level independent source acceptance: `c2a57d1415919265ba93209109f5c679932b8304`; pair remains `PARTIAL`.
- Scope adjudication reviewed: `39c0679beec89bcf59fc95aa83b0a10477d057d7:workflows/fix-implementation/2026-10-08-piston-head-threshold-scope-adjudication.md`. Final scope branch tip supplied for review: `8d300b4f5902cb4741193a09d4a95b5bdbb7087a`, parent scope commit `39c0679beec89bcf59fc95aa83b0a10477d057d7`.
- New exact 1.16 source publication: primary-main commit `95107011b19de53b8d4540a77cfe3d34a7771b68`; prep record `workflows/source-campaign-2026-10-07/preparations/1.16-mojmap-2026-10-08.md`.
- Review branch started from primary local `main` `3a60fe735560e478bf0aa0d05f5e306c74800f6a` in isolated worktree `D:/Javastuff/LegacyParkourCompat/.task-worktrees/piston-head-scope-review-2026-10-08`.
- Latest-main integration before handoff: merged through `3b2b4c0a3fc309f5ddcc8661ff5d736fbe0c989f`. Incoming commits after the 1.16 publication were inspected; they add the separate MC1204 sprint-precision change/reconciliations and source-preparation records, with no piston shape hook, piston mechanic, resolver, or collision-query changes.

## Exact-source cross-check

I read the already-published ready sources read-only; I did not run decompilation. The relevant file hashes match their source-manifest rows. The selectors are in `MovingBlockEntity.getCollisionShape` for Feather releases and `PistonMovingBlockEntity.getCollisionShape` for Mojmap releases.

| Release / mapping | Ready marker SHA-256 | Source-manifest SHA-256 | Relevant class SHA-256 | Selector |
| --- | --- | --- | --- | --- |
| 1.12.2 / Ornithe Feather | `b0aeec721e5c0af02b33d9e217cb8272889fa139ded1ecc2a31e42b930138f7a` | `b8a37ccfccd2aac5f40f5e34fec85873dbdfa93a266043c103e592e4a8c949da` | `MovingBlockEntity.java` `a55ee14227fe6ac0932283cba0414e946ba8e2637f543b3a3fe13d4e45b7b16e` | `this.extending != 1.0F - this.progress < 0.25F` (line 331) |
| 1.13.2 / Ornithe Feather | `1d1c644c1deb05c02d11dc575ad834f4f72489124701d9b2b90357f43dc37b38` | `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211` | `MovingBlockEntity.java` `6684ccc05adf8b29cac55f9796133405c6d4f6853051e651895303b657cd1c46` | `this.extending != 1.0F - this.progress < 4.0F` (line 342) |
| 1.14.4 / Mojmap | `ec7b6a6d9ba72f8a19908527977c970a8e2b3007b0e0b4323a725fb5943d3043` | `af98406f3d4ed31494fbdce1078d1f3df3f712501878f9a0e9ad5879bdf23c3b` | `PistonMovingBlockEntity.java` `05c9880861b5a620614fd916dbfe6a02730eb1f0f4244e7e41a908846e4fd5e5` | `this.extending != 1.0F - this.progress < 4.0F` (line 352) |
| 1.15.2 / Mojmap | ready source row rechecked | manifest-bound in finding | `PistonMovingBlockEntity.java` `43ef9a249446cac093eb1f7555bd55c4db5416474fa1668772f0f1257672e57a` | `... < 4.0F` (line 351) |
| 1.16 / Mojmap | `5b6cb24f95e83b856c53f646d6477f2e46dae2276737dee56dd07c5bd745e4a2` | `e3c23e94ac985c25dec0c768aed17b443ba4823662cf1e152235c99756ae5d4a` | `PistonMovingBlockEntity.java` `c65374aa5b38bb5c34aefd652ce26aa44beb391f1ded6d57d87f7e33882b89c` | `... < 0.25F` (line 337) |
| 1.16.1 / Mojmap | ready source row rechecked | manifest-bound in adjudication | `PistonMovingBlockEntity.java` `c65374aa5b38bb5c34aefd652ce26aa44beb391f1ded6d57d87f7e33882b89c` | `... < 0.25F` (line 337) |
| 1.16.2 / Mojmap | ready source row rechecked | manifest-bound in adjudication | `PistonMovingBlockEntity.java` `75d58043d5a9951f9b505a42b7656a5f01028633d971ba948167a9a95729a8b5` | `... < 0.25F` |
| 1.16.5 / Mojmap | ready source row rechecked | manifest-bound in finding | same as 1.16.2 | `... < 0.25F` |

The 1.12.2, 1.13.2, 1.14.4, and 1.16 marker and manifest digests above are raw-byte rehashes of the shared ready records. Their source class hashes match their manifest entries. The 1.15.2 and 1.16.1/.2/.5 exact source identities are also bound in the accepted finding/adjudication. No 1.15.1 ready source exists in the inspected set.

## Source behavior and limits

Java parses the selector as `this.extending != (1.0F - this.progress < threshold)`: subtraction and threshold comparison are float operations, and the relational comparison is evaluated before the boolean inequality. At progress `0.5F`, `1.0F - progress` is exactly `0.5F`. For a retracting source piston (`extending == false`):

- `0.5F < 4.0F` is true, so `false != true` sets `PistonHeadBlock.SHORT` true in 1.13.2, 1.14.4, and 1.15.2.
- `0.5F < 0.25F` is false, so `false != false` sets `SHORT` false in 1.12.2 and 1.16/1.16.1/1.16.2/1.16.5.

The 1.15.2 `tick()` advances `progress` by `0.5F` while below `1.0F`, so `0.5F` is source-reachable. `PistonHeadBlock.getShape()` uses the local `SHORT` property to choose the short or long arm shape; the property is part of a temporary block state used for the collision shape. No world block state is changed by this selector.

The player path is source-supported: 1.15.2 `Entity.collide()` creates `CollisionContext.of(this)` and queries `Level.getBlockCollisions`; `CollisionGetter.getBlockCollisions` creates the context from the entity and calls `BlockState.getCollisionShape(level, pos, context)`. `MovingPistonBlock.getCollisionShape(..., CollisionContext)` dispatches to `PistonMovingBlockEntity.getCollisionShape(BlockGetter, BlockPos)`. That last method does not receive the context, so the `BlockStateCollisionShapeMixin` return hook sees a player but only after the threshold has already selected `SHORT` and the composite shape has been built.

The matching `NOCLIP` direction returns the source moved-block shape before the `SHORT` selector. The accepted witness therefore requires the non-matching direction, retracting source piston, progress `0.5F`, and a player query that intersects the moving piston. Its exact resolved displacement depends on the rest of the collision set; none is claimed.

`getCollisionRelatedBlockState()` is a separate `moveCollidedEntities`/piston-push producer path. In 1.15.2 it builds a piston head without setting `SHORT`; in 1.16.1 it uses `progress > 0.25F`. This review accepts its exclusion. The mod's `PistonMovementBehavior` instead changes the player displacement-vector cap in `Entity.limitPistonMovement`; it neither supplies the piston shape nor implements this selector. Keep both paths unchanged for this finding.

## Resolver consequence and required implementation plan

`ChangeResolver` keeps changes where `emulates >= selected` and chooses the closest eligible change for a mechanic. An unguarded `@MovementChange(emulates = V1_15_2)` therefore applies to `V1_15` and older selections when no nearer change exists. Read-only source comparison now bounds the representative behavior as follows:

- `V1_13` representative (1.13.2), `V1_14` representative (1.14.4), and `V1_15_2` (1.15.2) use `4.0F`.
- `V1_12` representative (1.12.2) uses `0.25F`.
- `V1_15` (1.15/1.15.1) remains unverified; 1.15.1 is absent. Do not extend the 4.0F behavior to that profile yet.
- `V1_16` (1.16/1.16.1) now has both exact representatives: the newly published 1.16 file hash is byte-identical to the already checked 1.16.1 class, and both use `0.25F`.
- The first release with the 4.0F selector remains unknown within `(1.12.2, 1.13.2]`. The replacement boundary is now exact in the inspected release sequence: 1.15.2 has `4.0F`; 1.16.0 has `0.25F`, so the new selector is present at 1.16.0.

Exact 1.16.0 source now shows `0.25F`; with 1.15.2 at `4.0F`, this closes the endpoint transition at 1.16.0 and verifies the `V1_16` representatives. The 1.15.1 source is still needed before assigning `4.0F` to the `V1_15` group. If the current enum groups remain, the narrow emulation can be registered at `V1_15_2`, but it must be profile-gated so the resolver's older-profile fallback applies 4.0F only to verified representatives (`V1_13`, `V1_14`, `V1_15_2`); retain native 0.25F for `V1_12` and leave `V1_15` unresolved until its source is checked. The first introduction of 4.0F remains unknown within `(1.12.2, 1.13.2]`; do not infer it from group representatives. If source later shows patch differences inside one `ParkourVersion` group, split that enum group only through the repository's normal minor-version process; do not refactor `ChangeResolver` here.

The existing `BlockStateCollisionShapeMixin` is too late for an exact selector edit. The general mixin plan should capture `CollisionContext` at `MovingPistonBlock.getCollisionShape` and scope it around the `PistonMovingBlockEntity.getCollisionShape` call, then modify only the `0.25F` selector inside that latter method when a player profile resolves the versioned behavior. Scope/restore the context exception-safely for nested queries. Preserve the existing `NOCLIP` early return. Do not modify `getCollisionRelatedBlockState`, tick/progress/state lifecycle, non-player queries, piston push production, or piston block states. A global constant change without player context is too broad; rebuilding the composite shape after return duplicates more vanilla piston logic than this one-expression delta requires.

The exact-profile check belongs in the versioned behavior, not in a piston-specific version check in the mixin. `MovementRuntime.profile(player).target()` is already used for exact profile gating in `change/v1_13/SprintInputStart`; maintain the existing pattern or pass the selected profile through the hook contract so the change owns the decision. The current `MovementRuntime` documentation says change classes should not call its mixin-facing lookup, so the implementation owner should resolve that API tension explicitly without changing `ChangeResolver`.

## Remaining limits and action

- Pair discovery remains `PARTIAL`; this review does not close S5 or claim full source-pair coverage.
- Runtime validation remains not performed. No movement trajectory or displacement outcome is claimed.
- No tests, build, decompilation, client, server, TAS, Gym, Docker, push, or source-owner message was used for this review.
- Recommended next step: obtain ready source/manifests for 1.15.1, then implement the profile-aware context bridge above in a separate authorized implementation task.
