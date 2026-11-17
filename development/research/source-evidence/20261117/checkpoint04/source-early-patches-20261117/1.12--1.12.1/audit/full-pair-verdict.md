# Independent full-pair source audit: 1.12 → 1.12.1

**Verdict: ACCEPT — complete source coverage; no in-scope local-player movement difference found.**

## Bound evidence

- Pair: exact `decompiled_minecraft/1.12/ornithe-feather/` → `decompiled_minecraft/1.12.1/ornithe-feather/` sources.
- Coverage report: `build/source-early-patches-20261117/1.12--1.12.1/coverage.md`, SHA-256 `68757F54C26F3093BC9A64402CE80CC9B5B1366D67E096D17FDC81817F63B003`.
- 1.12 mapping-local provenance SHA-256 `27515E5F4C71E7DB2F035A81716507128D77466C75380AD1EF7D140C06401467`; 1.12.1 mapping-local provenance SHA-256 `0FF5246E8D29C04554CC9EC93AE9B4ABEF539652325B6C0AC822851D7186A9C8`.

## Independent checks

I rehashed every source entry against the two mapping-local provenance manifests: 2,048/2,048 older and 2,050/2,050 newer entries match. The manifests have 2 added source paths, 0 removed paths, and 41 changed shared paths. The complete changed-path set matches the report.

The reachable local-player movement implementation and dependency sources are unchanged across the pair: entity/living/player tick and travel, collision/clipping and block providers, effects/attributes/equipment consumers, client player/world responses, and movement-state writers. The only changed player movement-adjacent body is `PlayerEntity.writeCustomNbt`, where the `DataVersion` literal changes 1139→1241. `DataFixerUpper` changes the current-version threshold to 1241 and `DataFixes` changes only the constructed version; the fix registration list and fix implementation sources are unchanged, with the registered maximum at 1125. This adds no player-state transform in the 1139→1241 interval.

`World.java` changes only the profiler label expression around block-entity ticking. The client packet-handler additions are recipe ghost/UI handling; the new server recipe-placement path modifies crafting/inventory state, not local movement code, input, dimensions, collision, or position/velocity. Other changed files are protocol/version/save metadata, UI, rendering, telemetry, or profiler paths listed in the coverage report. No movement packet or player-response handler changed.

## Acceptance boundary

The report’s no-difference disposition is accepted for deterministic local-client player movement on this exact patch edge. No movement finding is advanced. Project exclusions (including rendering/audio, server movement, non-player/vehicle behavior, and persistence outside movement state) remain excluded. This acceptance is source coverage only; it makes no implementation or runtime-parity claim.
