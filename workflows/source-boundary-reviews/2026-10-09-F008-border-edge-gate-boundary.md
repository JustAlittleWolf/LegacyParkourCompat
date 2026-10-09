# F-008 exact boundary and implementation disposition

Date: 2026-10-09  
Pair: Minecraft 1.17.1 → 1.18.2  
Scope: accepted world-border admission difference in the crouch edge-backoff candidate query only.

## Immutable finding and review bindings

- Finding snapshot: commit `e531086eecc778432b40b2b9ef39499b8a7f0dba`, path `workflows/source-campaign-2026-10-07/1.17.1--1.18.2/findings/F-008-border-edge-gate.md`, Git blob `58ebc5b2dbff5f0e409fdceb38b0466e89322ce4`, raw SHA-256 `3a8a5258cb741497a52085f76688df7ca32161dcbd0e068ffe5ee70b3a271afd`.
- Independent finding review: commit `2fd121e116d7185165c76aea1c3708273afc7ea1`, path `workflows/source-boundary-reviews/2026-10-08-mc1171-1182-snapshot-review.md`, Git blob `871ad5bccde2194ccc733ed8fef92472a1c96c2a`, raw SHA-256 `ee96f6564b366b9622bfed23bb31331018613d64589ee1215d842a2165307d3e`. Decision: ACCEPT, limited to the edge-gate world-border query.
- The accepted claim is that A and B can return different pre-solver horizontal components from `Player.maybeBackOffFromEdge` for the documented grounded, crouching, non-flying, SELF/PLAYER outward probe near a border, when no block/entity collision blocks that candidate. It does not claim final coordinates or a final tick trajectory.

## Exact source artifacts and release boundary

All source trees below are the exact Mojmap ready outputs under `D:\Javastuff\LegacyParkourCompat\build\movement-campaign-2026-10-07\ready\<version>\mojmap`. Their source and artifact manifest files were rehashed against each `mojmap.ready.json` marker.

| Release | Source manifest SHA-256 | Artifact manifest SHA-256 | Relevant source SHA-256 |
|---|---|---|---|
| 1.17.1 | `93270d229acfb751bf56daf1e7be26ce3dcff26b29e94aa157de621405a3463b` | `e52c5dbae7d9663190ccc55a4f9b44a8e0615fb1fbbd8280811df43f70ec8aaa` | Player `724bb298499dabe489dffd5ce7ec81c9773619a8d2c70044911eae4c9e8ff481`; CollisionSpliterator `c4f8158bd6778159946ebf16c22a6c12f5a4bccb40e2f1215f4fc930bb218bd0`; CollisionGetter `eb707479e75cf200731df4546a546fb984be33cf3e4a8e17d3065a916497f747`; WorldBorder `b848dd0dc6043d6c9012726844bea53cf4558c0135a08fa3be55960b0e557df9`; WorldBorderCommand `209c81da8ee929ef6163dfd4fbecd302934f3515360792588d20c8870f23129d`. |
| 1.18 | `a43c61223ddedbdd8ede4d2daf6a750a58cbba324b30c025429ee1030cfc0d78` | `7ca2b4da88c215e52924d277b257c4631f9664f5def2fb2b7ef25479ec330234` | Player `1fb9dfb9f700323d77f4196dbc3618d01a65e56438b9cce3ee38fa0b258bd7e2`; CollisionGetter `ed62e6b800ec057c1269d3cc4e2fb7c0cc2037370223fac99f41371e000c6b5c`; WorldBorder `ade6bcb18ae0b7a2c6e9978295288bfac6fbb759a0f8a2dfb9d3983788d332fb`; WorldBorderCommand `4df4bb3b589e3c1b77ce48c670d0429ded0e4b62def6b61b7616fc2108fdd021`. |
| 1.18.1 | `52aa98450b5cfa55ac2bd2054f108e16df7fa939a3f061991b6a9adb434d8d2d` | `b8237a01cdebe7d784caae113886f42565677ce6897ea960a49223cfec4c00ec` | Player `1fb9dfb9f700323d77f4196dbc3618d01a65e56438b9cce3ee38fa0b258bd7e2`; CollisionGetter `ed62e6b800ec057c1269d3cc4e2fb7c0cc2037370223fac99f41371e000c6b5c`; WorldBorder `ade6bcb18ae0b7a2c6e9978295288bfac6fbb759a0f8a2dfb9d3983788d332fb`; WorldBorderCommand `4df4bb3b589e3c1b77ce48c670d0429ded0e4b62def6b61b7616fc2108fdd021`. |
| 1.18.2 | `aea0cb9c6fc8f7a46f0eb82b0388ad58a4659f513be6c0a2be06c0df0c1eb07a` | `a1507e4875faee892aca4c59686bbd68933db21a2274559e81eea8a32b021036` | Player `bf639c1962ff90d69e4569b2b18f6fcf57ac46ef80b19686f0fbc1687fca744a`; CollisionGetter `ed62e6b800ec057c1269d3cc4e2fb7c0cc2037370223fac99f41371e000c6b5c`; WorldBorder `ade6bcb18ae0b7a2c6e9978295288bfac6fbb759a0f8a2dfb9d3983788d332fb`; WorldBorderCommand `4df4bb3b589e3c1b77ce48c670d0429ded0e4b62def6b61b7616fc2108fdd021`. |

The first changed release is **1.18**. The 1.17.1 Player path sends candidate AABBs to `Level.noCollision`, but its `CollisionSpliterator.worldBorderCheck` at lines 103–135 admits border shape using `source.getBoundingBox()`—the player's current box. `isBoxFullyWithinWorldBorder` compares all four current-box horizontal bounds with `floor(minX/minZ)` and `ceil(maxX/maxZ)`; when that current box is fully inside, the border shape is skipped even if the candidate AABB crosses it. In 1.18, `CollisionGetter.noCollision` at lines 46–62 checks blocks and entities, then `borderCollision(entity, candidate)` at lines 77–80. `WorldBorder.isInsideCloseToBorder` at lines 76–79 derives a margin from the queried AABB's horizontal size, checks entity-center distance to the border, and checks the entity center against expanded bounds. The 1.18, 1.18.1 and 1.18.2 source hashes for these query bodies match; the 1.17.1 implementation is different. `CollisionSpliterator.java` is absent in 1.18 onward; the shared `CollisionGetter`/`BlockCollisions` query is the replacement path.

The Player consumer is present at 1.17.1 lines 1017–1067, 1.18 and 1.18.1 lines 1019 onward, and 1.18.2 lines 1032 onward. Each calls `noCollision(this, getBoundingBox().move(horizontalCandidate, -maxUpStep, ...))` in the X, Z and combined reduction loops after the same relevant non-flying, SELF/PLAYER, staying-on-ground-surface and above-ground conditions. The edge loop may therefore receive a different answer before the normal movement solver runs. `isStayingOnGroundSurface()` reads the shift-key state in the paired sources. No block state is read or changed by the border predicate.

## Border state producer and movement consumer

`WorldBorder` is environmental world state. Its `setCenter`, `setSize` and `lerpSizeBetween` methods update center/extent and notify listeners; `applySettings` restores the same values from `WorldBorder.Settings` (1.17.1 WorldBorder lines 98–145 and 217–227; 1.18+ lines 109–151 and 228–239). `WorldBorderCommand` routes `/worldborder center`, immediate size changes and timed size changes to those setters (1.17.1 lines 92–95, 200–240; 1.18+ lines 95–98, 193–235). The edge-gate consumer reads the resulting border through the level's `noCollision` query as described above. This is not a historical block-state producer and does not expand the finding to border damage, warnings, commands as a movement feature, or non-player collision.

The broader pair ledger later resolves `D-BORDER-MOVE-PATH` and records the edge-gate path separately from final-solver F-009, with the matching border geometry and query callers closed. The immutable F-008 text's “remains open” sentence records its original finding checkpoint; the later closure does not change its accepted boundary. The corrected independent full-pair audit is commit `e0e36eff36a92be82327bf2a8140f18141d7cc21`; its accepted full-pair freeze is limited to source coverage and does not accept finding implementation or runtime parity.

## Existing resolver and implementation check

The supported version enum maps 1.18 and 1.18.1 to `ParkourVersion.V1_18`; 1.17.1 has its own `V1_17_1`. `ChangeResolver` returns no changes for `CURRENT`; for a fixed historical profile it selects, per mechanic, the closest registered `emulates` version greater than or equal to the selection. The existing `SneakEdgeBehavior` implementations are registered at `V1_16` and `V1_18_2`. Thus a selected `V1_17_1` edge handler currently resolves to the nearest available `V1_18_2` registration; that class delegates candidate probes to `SneakEdgeBackoff.apply`, which calls the modern level `noCollision` query. The current hook does not provide the accepted 1.17.1 current-box border admission. The source boundary indicates that preserving the old query for a fixed `V1_17_1` selection needs an independently resolved movement operation; this is not a no-code reconciliation. No Java change is included in this memo.

Native behavior remains the `CURRENT` path because the resolver returns an empty map for it, allowing the original mixin target to execute. A historical change representing the old query must be eligible through `V1_17_1` and become ineligible at `V1_18`; 1.18.1 shares the `V1_18` profile. The exact hook/API shape and minimal delta remain implementation review questions, not claims in F-008.

## Scope and gates

- Finding-specific dependencies are closed in the later source record; accepted scope remains the pre-solver edge-gate query result only.
- No block-state or non-player/vehicle behavior changes are claimed. Vanilla block states remain untouched.
- Pair source coverage has an independent accepted full-pair freeze; finding acceptance is separately bound above.
- This boundary memo awaits independent source review. No Java implementation, tests, game/runtime launch, or build was performed. Implementation may proceed only after this exact memo is independently accepted and the coordinator routes the implementation step.
- Runtime movement parity remains unverified.
