# Independent source audit: 1.21.9 → 1.21.10 flying-state gate

## Verdict

**Substantive no-difference conclusion accepted for the named flight-state operations; bounded coverage clarification recorded.** The frozen report is intact at the assigned input SHA-256. The canonical Mojmap source files for `LocalPlayer`, `Player`, and `LivingEntity` have identical bytes across 1.21.9 and 1.21.10, matching the report's hashes. Their compared writes, gates, and order therefore do not establish an independent flight-state difference.

The causal path checks out across the pair:

- `LocalPlayer.aiStep` writes `Abilities.flying` in the `mayfly`/always-flying and double-jump branches, uses `onGround()` to conditionally jump when enabling flight, requests fall flight after the toggle branch, applies vertical ability-flight input, then clears ability flight after `super.aiStep()` when grounded unless always-flying applies. See both versions' `net/minecraft/client/player/LocalPlayer.java:751-778, 791-804, 834-838`.
- The ordinary player travel consumer is also relevant: `Player.travel` routes `abilities.flying` through the flying travel path and scales the resulting Y velocity by `0.6`; the non-flying branch calls the same superclass travel without that scale. `Player.canGlide()` vetoes gliding while ability-flying. See both versions' `net/minecraft/world/entity/player/Player.java:1295-1323`. The frozen report's table does not list `Player.travel` explicitly; its complete-file hash is identical for the pair, so this is a coverage-description omission, not evidence against its no-difference conclusion.
- Fall-flight entry is separately gated by `!isFallFlying() && canGlide() && !isInWater()`, then writes shared flag 7. `LivingEntity` reads that flag through `isFallFlying()` to choose `travelFallFlying` versus ordinary `travelInAir` after the fluid branch. During the living-entity travel tick, an already-set fall-flight flag calls `updateFallFlying()` before travel; that reset is server-side and clears the flag if `canGlide()` becomes false. See both versions' `Player.java:1376-1387`, `LivingEntity.java:2275-2283, 2399-2402, 2833-2849, 2901-2921, 2923-2933, 3354-3356`.

## Ground-state producer boundary and ownership

`onGround()` itself is a getter (`Entity.java:666-668` in both versions); it is consumed by the ability-flight toggle/reset and `canGlide` predicates. The geometric/contact producer is outside this flight-state audit. In particular, `Entity.move` derives collision flags from requested versus collided displacement and calls `setOnGroundWithMovement` at `Entity.java:718-724` (both versions); that producer is an explicit exclusion here, not a finding about the flight-state consumer.

The report's bounded piston dependency is source-confirmed: at `Entity.move`, the 1.21.9 path multiplies displacement by `stuckSpeedMultiplier` for all mover types (`Entity.java:687-691`), while 1.21.10 skips that multiplication for `MoverType.PISTON` but still clears the multiplier and delta movement (`Entity.java:687-694`). This lies downstream of the flight-state consumer and upstream of collision/contact state that may later feed `onGround()`. Its reachability and collision/ground-state consequences remain unresolved by this audit. Under the overnight domain table, the player-state/flight consumer belongs to C; the `Entity.move` collision/contact producer is B's solid-world collision/support domain. This ownership distinction does not settle either lane's implementation.

## Evidence and readiness

All paths below are relative to `decompiled_minecraft/<version>/mojmap/` unless stated otherwise.

- 1.21.9 canonical source identity: `.decompile-provenance.json` SHA-256 `37d3049478585a43ffcace14fc8f2ee814b35ceedd8c9bad8b60adaed9b72c4f`; its pinned metadata and client keys identify exact release 1.21.9. Its source map confirms the report's hashes for the four inspected classes.
- 1.21.10 readiness identity: `mojmap.ready.json` SHA-256 `2374d901074b72cd69f0229381d6a11edea7983613c538a2e2e18d9fc73dbeb0`; marker reports `status=ready`, `versionId=1.21.10`, `mapping=mojmap`, and 6,386 source files. Its `sourceManifestSha256` matches `mojmap.sources.sha256` SHA-256 `4be26049350c1b314a0b198022cb1e7ab1e745e104047de7a5c7d099a9d7b7e1`. Manifest entries match the actual `LocalPlayer`, `Player`, `LivingEntity`, and `Entity` source hashes listed in the frozen report.
- 1.21.10 campaign provenance: `mojmap.provenance.json` SHA-256 `9a1db4c050c3caa28a1313f89dae9da3b28fea5f8afba42cab798b47d7ad77c`; exact-run record names the 1.21.8/1.21.10/1.21.11 Mojmap batch. No hidden `.decompile-provenance.json` is claimed for the canonical 1.21.10 tree.

## Frozen input identity

- Input: `C:\Users\Wolfi\.codex\visualizations\2026\11\17\01a1eb7b-b9ff-7a20-8686-0295dde20f26\c-queue-1219-12110-flying-state-gate\report.md`
- Input SHA-256: `808618df0292794a4219dad74c984802d620db90399095b4e8feec7ef5aa143a`
- Exact source paths/ranges reviewed: `LocalPlayer.java:751-778,791-804,834-838`; `Player.java:1295-1323,1376-1387`; `LivingEntity.java:2275-2283,2399-2402,2833-2849,2901-2921,2923-2933,3354-3356`; `Entity.java:666-668,687-694,718-724` on the corresponding 1.21.9 and 1.21.10 trees.
