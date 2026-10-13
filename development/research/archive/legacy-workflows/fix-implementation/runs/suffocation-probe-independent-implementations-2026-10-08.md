# Independent versioned SuffocationProbe implementations

**Status:** code refactor committed; no historical behavior or coverage claim added. This proof binds the accepted ShulkerBox scope to the implementation refactor and records its limitations.

## Source finding and baseline binding

The bounded open-Shulker finding is `F-S1-OPEN-SHULKER-ESCAPE`, snapshot commit `d859478b9f6de63ba4a121a1f31d6dc90ad4f51e`, path `workflows/source-campaign-2026-10-07/1.15.2--1.16.5/findings/F-S1-OPEN-SHULKER-ESCAPE.md`, Git blob `af523d8634ec022b062572f67c2e79803e794baf`, raw SHA-256 `11860ba106197cae687735e641db4c9ef7057ddadcddeafe5c16f012e8e657e4`. Its finding-only acceptance is review commit `0b494993e7b9b40b5526c80e36c99407e7e405f3`, `workflows/coverage-review-2026-10-07/middle.md`, blob `a5f86534d571884db33ddb0afeebf7348f3b9937`, raw SHA-256 `5437414a3b24a5722aba2207d245977a55aadc02e419da36793063e39801cd18`.

The accepted claim is limited to the 1.15.2 local-player sampled-column escape query: its ShulkerBox suffocation predicate returns true even when the box is open. The first changed release remains unknown in `(1.15.2, 1.16.5]`; no final displacement or runtime claim is part of that finding. The existing implementation was already merged before this task; this change only removes cross-version implementation inheritance.

The code baseline is current primary `main` at `d8f3956602da94bf0cf67753cc0a9f4665397729`. Its exact pre-change implementations were resolved and hashed from their Git blobs:

- `change/v1_15_2/SuffocationProbe.java`: blob `95069ec856431f075bda404622ba48b54e801831`, SHA-256 `0f170057ff5a8b381430839ff6130c4d66a336fe1952765e475172e265979081`.
- `change/v1_16/SuffocationProbe.java`: blob `a50ff725959b5e9eb4215e4f488e4d8cb85b450b`, SHA-256 `8aebf4427559c0be970323b7c473162e593ec0b38da44317e36ed870a237ba72`.
- `change/v1_8/EndPortalFramePlayerEjection.java`: blob `01367390b8690415a3ea5cee0c40d059005e9d1b`, SHA-256 `994c6041acbf4bae55bddc1d452734ee78728b343c8fa3ab7794bc3502169f8a`.
- `SuffocationProbeBehavior.java`: blob `fba5329371ce5906666c8a205f64787cd1885a53`, SHA-256 `1a66c8c65985ee285f96e3c9aa23b959bfa1827c851a7b72e35d9c82e6849fd8`.
- `LocalPlayerMixin.java`: blob `aca3809d94c4f31ab2a23ba963862676b6e90dc1`, SHA-256 `86ed1720a63681f267a61cb580bb3723ed4c48eb169f6104b61f23a321e71bd3`.

The historical provider registrations and central mechanic catalog were inspected and left unchanged. No `MovementChanges`, `fabric.mod.json`, hook interface, registry, or resolver file is part of the code commit.

## Refactor and behavior preservation

Code commit: `42885fae364acfb20e8e03f84d0a3eeb756b2e7c` (`refactor: decouple versioned suffocation probes`). The three version-specific behaviors now implement `SuffocationProbeBehavior` directly; `v1_16.SuffocationProbe` is final. A source search finds no remaining `extends ...SuffocationProbe` relationship.

The only shared implementation is `change/common/SuffocationProbeColumn.any`, a version-neutral operation that scans the player's vertical block column. It preserves the original `Mth.floor(boundingBox.minY)`, `Mth.ceil(boundingBox.maxY)`, mutable X/Z cursor, ascending integer Y order, inclusive minimum, exclusive maximum, and first-true short circuit. It contains no block predicate, version check, or historical rule.

- `v1_16.SuffocationProbe` supplies its prior `BlockState.isSuffocating(level, pos)` predicate through that helper.
- `v1_15_2.SuffocationProbe` independently supplies the accepted ShulkerBox predicate. It returns true for a ShulkerBox only when `target == V1_15_2`; other targets and blocks use the same vanilla predicate as before. Its original state lookup order is preserved, including the second state read on the fallback path. It makes no open-Shulker claim for `V1_15`, `V1_15.1`, or another unreviewed profile.
- `v1_8.EndPortalFramePlayerEjection` was another consumer inheriting the v1_16 implementation solely to reuse the vertical scan. It now implements the same hook directly and uses the neutral scan. Its own `target == V1_8` path remains first: calculate `boundingBox.minY + 0.5D`, pass that Y through `BlockPos.containing`, test that cell and then the cell above using the same short-circuit order, and only then run the same vanilla suffocation scan. This removes the additional cross-version inheritance without changing that separate historical condition.

The caller remains `LocalPlayerMixin.suffocatesAt` at the same return injection. It passes the selected profile to the selected `SuffocationProbeBehavior`; `ChangeResolver` continues choosing the closest registered implementation for that shared mechanic key. `MovementRuntime.find` still returns empty when emulation is disabled/native or the entity is not a player, so the original vanilla return remains untouched in those cases. No call site or selection code changed.

## Committed code identities

The code commit's Git blobs and raw SHA-256 values were resolved from the commit tree:

- `change/common/SuffocationProbeColumn.java`: blob `ab7370af56043c9f2e2df0afc29d4be03382a24b`, SHA-256 `1d27529314f37851c06ab225fbb82b36f487ba3075869b07fa4dbeb63147081e`.
- `change/v1_15_2/SuffocationProbe.java`: blob `e7d1dd0142699e8cd909b967d7efddc2a40b6c1e`, SHA-256 `9160743cc0a89e379c47bb0a5f3337fe66cb17defce8aa6cdcd8e5e532d6f45d`.
- `change/v1_16/SuffocationProbe.java`: blob `e50cde5276e2e61e0b096cb3c5bddd54ecd43b7c`, SHA-256 `e35cf7d602dcd6b8452893237e9df117d10195f233dad1bb4f30679869c4b357`.
- `change/v1_8/EndPortalFramePlayerEjection.java`: blob `d94e31939af0b81fbd77a37f5777c4785407bd51`, SHA-256 `fe07015e3727dac2f6e8858416a0b12c0989d8427df177bfece4776f97c5cd5b`.

## Limits and verification

No historical source behavior was added or broadened beyond the already accepted 1.15.2 ShulkerBox rule; the code change is structural. Pair coverage, the first changed release, final player displacement, and runtime behavior remain unverified. No tests, build, Gradle task, decompilation, game client, server, TAS, Gym, Docker, or push was run. Static checks were limited to `git diff --check` and source searches for direct hook implementations, version inheritance, and the preserved scan bounds/order.
