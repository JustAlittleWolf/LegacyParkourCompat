# Adjudication: F-S5 piston-head threshold

## Disposition

- **Source finding:** accepted bounded source difference; pair status remains partial.
- **Movement scope:** eligible only for the ordinary player collision-shape query described below. Do not emulate piston block-state lifecycle, non-player movement, or the piston push producer.
- **Current implementation coverage:** open. `PistonMovementBehavior` changes the player piston-displacement vector cap; it does not change piston-head shape selection. The generic `BlockCollisionShape` hook is player-context-aware infrastructure, but no `minecraft:moving_piston` change is registered.
- **Boundary:** source supports the `V1_15_2` representative's `4.0F` selector and the `V1_16`/`V1_16_2` representatives' `0.25F` selector. The first changed exact release remains unknown in `(1.15.2, 1.16.1]`; no `1.16.0` source is locally ready, so this record does not assign a 1.16.0 cutover.
- **Production change:** none. This is a proof-only adjudication; implementation remains open pending the boundary and context-scope follow-up below.

## Immutable finding and independent acceptance

- Finding snapshot commit/path: `58e56ee59d7cc4015d499e3e2a37ec1d45049d56:workflows/source-campaign-2026-10-07/1.15.2--1.16.5/findings/F-S5-PISTON-HEAD-THRESHOLD.md`.
- Snapshot blob: `54def810126026ecb51f389c346b564f4b7503d3`; raw SHA-256: `58cd05981150b0c162ecd10ea16560e7cfbb50cdf5027cb57577cf32a4c30163`. The raw hash was verified by hashing the bytes from `git show` at the exact commit/path.
- Independent source review: ACCEPT, commit `c2a57d1415919265ba93209109f5c679932b8304`, file `workflows/source-campaign-2026-10-07/independent-review-F-S5-PISTON-HEAD-THRESHOLD-2026-10-08.md`. It confirms the stated player collision path and expressly leaves the 1.15.2–1.16.5 pair PARTIAL. This is finding-level acceptance, not pair completion or runtime validation.

## Exact-source evidence and profile boundary

The shared ready trees are read-only under `build/movement-campaign-2026-10-07/ready/` in the primary checkout. The exact target body hashes were checked against their Mojmap source manifests. No decompilation was run.

| Exact release | `PistonMovingBlockEntity.getCollisionShape` selector | Source SHA-256 | Ready source-manifest SHA-256 |
| --- | --- | --- | --- |
| 1.15.2 | `this.extending != 1.0F - this.progress < 4.0F` | `43ef9a249446cac093eb1f755bd55c4db5416474fa1668772f0f1257672e57a` | `cb7fd93f8730d8b2813857744f8572060535c7de44fcee86222acd1ceb6f43e7` |
| 1.16.1 | `this.extending != 1.0F - this.progress < 0.25F` | `c65374aa5b38bb5c34aefd652ce26aa44beb391f1ded6d57d87f7e33882b89c` | `76fdd121070c2012f48cbdf5cf0c78490116afc94117c30b0858eedcd6ab9754` |
| 1.16.2 | `this.extending != 1.0F - this.progress < 0.25F` | `75d58043d5a9951f9b505a42b7656a5f01028633d971ba948167a9a95729a8b5` | `10d312ca29ee48e25f4727adafb9fcfbd741dcebe81b343d3dcffa071952a7a7` |
| 1.16.5 | `this.extending != 1.0F - this.progress < 0.25F` | `75d58043d5a9951f9b505a42b7656a5f01028633d971ba948167a9a95729a8b5` | `9499f2611d0e6dde37cb635f1a28a591416382d6bc188b37d5f99e2b2a20823b` |

At `progress == 0.5F` in the retracting source-piston branch, the A comparison is `0.5F < 4.0F` and selects `SHORT`; the later sources evaluate `0.5F < 0.25F` and select the long head. This is a shape-selection difference, not a claimed displacement or trajectory.

`ParkourVersion` groups `1.15.2` as `V1_15_2`, `1.16`/`1.16.1` as `V1_16`, and `1.16.2` through `1.16.5` as `V1_16_2`. The ready representative for `V1_16` (1.16.1) and the representative for `V1_16_2` (1.16.5) both match the current 26.2 selector (`0.25F`). `1.16.2` independently matches the latter group start. Thus a future `V1_15_2` historical delta would not be needed for either newer representative. This profile mapping does not prove the actual first changed patch: `1.16.0` is absent from the local ready set.

The repository's resolver applies a change to its emulated version and older selected versions, choosing the closest change. Therefore an unguarded `@MovementChange(emulates = V1_15_2)` would also resolve for `V1_15` and older. Their matching piston threshold is not established by this finding. Keep an eventual implementation exact-profile-scoped to `V1_15_2` unless the missing older representative source supports widening it; do not refactor `ChangeResolver`.

## Scope and current-hook adjudication

- Source path: `PistonMovingBlockEntity.getCollisionShape(BlockGetter, BlockPos)` derives a `PistonHeadBlock.SHORT` state only in the moving source-piston collision-shape branch. `MovingPistonBlock.getCollisionShape(BlockState, BlockGetter, BlockPos, CollisionContext)` dispatches to that block-entity method.
- Reachable player consumer: the independent review verified that the ordinary player `Entity.move` block-collision query reaches the moving block's collision shape. Matching `NOCLIP` direction exits before the `SHORT` selector; the accepted witness is the non-matching direction at progress `0.5F`.
- Excluded producer path: `PistonMovingBlockEntity.moveCollidedEntities` obtains a separate shape from `getCollisionRelatedBlockState()`. In 1.16.1 that helper selects its push shape with `progress > 0.25F`, while the 1.15.2 helper does not set `SHORT` there. That path belongs to piston-push producer physics and is explicitly excluded; do not change this helper or its `moveCollidedEntities` callers. Keep vanilla block states unchanged.
- Existing current hooks: [BlockCollisionShape](../../src/main/java/me/wolfii/legacyparkourcompat/mechanic/hook/BlockCollisionShape.java) is keyed per block. [BlockStateCollisionShapeMixin](../../src/main/java/me/wolfii/legacyparkourcompat/mixin/BlockStateCollisionShapeMixin.java) dispatches only when `CollisionContext` resolves to a player, after the block's virtual shape implementation returns. There is no piston-head-threshold mechanic or moving-piston shape registration. [PistonMovement](../../src/main/java/me/wolfii/legacyparkourcompat/change/v1_11/PistonMovement.java) is a different player movement-vector cap.
- Current target source: Minecraft 26.2, ready source manifest SHA-256 `a7ad74fc712567eb87136afc1eafcfd602b4088e9869f3596331fd34faefe894`; `PistonMovingBlockEntity.java` SHA-256 `706bab1a13ba99bbd334aad853b99f72df58fce5b6b67d30d41f902b63dd8e06`, `MovingPistonBlock.java` SHA-256 `9a8d9b03be6866b1e5a2d237f8cb635b89c08e63a241db2d32338448240102d1`. `PistonMovingBlockEntity` uses `0.25F` in both the dynamic push helper and the `getCollisionShape` selector. Only the latter selector is in this finding's scope.

### Minimal eligible hook plan

Use a player-context-aware bridge at the moving-block shape query and a narrow versioned threshold decision for `PistonMovingBlockEntity.getCollisionShape`:

1. Dispatch only for the `minecraft:moving_piston` shape query with a player `CollisionContext`; retain vanilla for other entities and no active profile.
2. Alter only the `getCollisionShape` selector for a retracting source piston, after preserving its matching-`NOCLIP` early return. Return the historical `4.0F` threshold for the exact `V1_15_2` target profile.
3. Do not alter `getCollisionRelatedBlockState`, the push/move producer path, piston state, or the `0.25F` current selector for newer profiles.

The current `BlockStateCollisionShapeMixin` is a viable player-only exit point, but it runs after `SHORT` has already been selected and does not pass the vanilla shape into the hook. A plain global `@ModifyConstant` in `PistonMovingBlockEntity.getCollisionShape` would also affect non-player queries and is too broad. Implementation should either carry the caller's player context to a threshold-specific hook or replace the returned player shape without duplicating unrelated piston logic. The source review does not validate a particular mixin bridge, so no code was added here.

## Exact missing-source request

Request the exclusive shared-source preparation owner publish read-only Mojmap ready artifacts for:

- **1.16.0**: exact `PistonMovingBlockEntity.java` (`getCollisionShape`, `getCollisionRelatedBlockState`, progress tick/writers), `MovingPistonBlock.java`, and `PistonHeadBlock.java`, with matching ready JSON, source/artifact manifests and provenance. This identifies whether the transition was already present in 1.16.0; do not infer it from 1.16.1.
- **1.15.1**: the same piston shape and producer methods plus `MovingPistonBlock.java`/`PistonHeadBlock.java`, with matching ready records. This determines whether the older `V1_15` profile can inherit the `V1_15_2` threshold without an unsupported assumption.

No request was sent and no decompilation was run. Until these exact sources are published, retain the known `V1_15_2` profile scope and leave broader older-profile applicability and the exact first changed release open.

## Validation and handoff

- Current code coverage disposition: open / missing behavior implementation.
- Source discovery: bounded finding accepted; 1.15.2–1.16.5 pair partial.
- Runtime validation: not performed; no trajectory is claimed.
- Builds, tests, decompilation, clients, servers, TAS/Gym, Docker and push: not run, per assignment.
