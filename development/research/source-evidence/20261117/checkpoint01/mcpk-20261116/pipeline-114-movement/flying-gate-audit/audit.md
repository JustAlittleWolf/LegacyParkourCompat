# Independent source audit: player climbing eligibility flying gate

Verdict: **ACCEPT** for the bounded source claim that 1.14.4 admits a non-spectator flying Player to the inherited climbing predicate when standing in a climbing block, while 26.2 Player suppresses climbability whenever `abilities.flying` is true. This is a source-level predicate result only; it does not establish runtime reachability or movement parity. The exact patch that introduced the 26.2-era gate remains **OPEN** within the uninspected interval.

## Evidence and comparison

- In each inspected 1.14 release, `PlayerEntity` directly extends `LivingEntity` and declares no `isClimbing()` override. In 1.14.0-.4, `LivingEntity.isClimbing()` first returns false for spectators, then reads the block state at `BlockPos(this)`. It accepts the era's ladder, vine and scaffolding blocks, or a trapdoor that passes the ladder-facing/open check. There is no check of `PlayerAbilities.flying` or fall-flying in this predicate.
- Therefore, with a non-spectator player on a ladder and `abilities.flying == true`, the 1.14 inherited predicate returns true. (The same is true of other accepted climbable states.)
- In 26.2, `Player.onClimbable()` is an override with the order `abilities.flying ? false : super.onClimbable()`. The true branch short-circuits before the superclass checks spectator state, fall-flying through `CAN_GLIDE_THROUGH`, `CLIMBABLE`, or usable trapdoor. Thus the same non-spectator flying player on a ladder gets false; with `abilities.flying == false`, the method delegates and superclass ladder eligibility can return true.
- Do not conflate `abilities.flying` with `isFallFlying()`: the new Player-level check is the former. The 26.2 superclass's fall-flying / glide-through-block gate is a separate predicate condition.

## Source call path and order

- 1.14.4 `LivingEntity.travel` calls virtual `isClimbing()` before movement clamping (`m_19439509`, line 1945) and after movement in horizontal-collision / jumping handling (lines 1848 and 1907). `PlayerEntity` inherits that predicate. These call sites show source-level use; this audit does not claim which branch is reached in a running game.
- 26.2 `Player.travel` routes to `super.travel(input)` in the `abilities.flying` branch (lines 1402–1422). `LivingEntity`'s travel movement helper calls virtual `onClimbable()` while clamping before `move` and again for the horizontal-collision / jumping response (lines 2676–2682). Dynamic dispatch resolves to `Player.onClimbable()`, whose flying gate precedes the inherited predicate. This documents source call structure only, not runtime reachability.
- The old and new superclass block tests also differ in other ways. This verdict isolates the Player `abilities.flying` short-circuit and does not characterize those other changes.

## 1.14 patch stability checked

All five inspected release sources show the same inherited structure and predicate order: spectator exclusion; current block-state lookup; direct climbing-block acceptance; otherwise the trapdoor ladder test. The mapped identifiers and line numbers differ, but no 1.14.0–1.14.4 Player class adds a flying exception. This establishes stability only across those exact releases, not earlier versions or releases after 1.14.4.

## Exact source identities

SHA-256 values below bind the exact decompiled Java file bytes. The 1.14.0–1.14.3 source files are matched to `sources` entries in their mapping-local `.decompile-provenance.json`; 1.14.4 and 26.2 entries are matched to their root SHA-256 source manifests. For those root manifests, the `ready.json` `sourceManifestSha256` matches the manifest bytes.

| Release | Namespace / pinned client identity | Source manifest or provenance SHA-256 | LivingEntity source SHA-256 | Player source SHA-256 |
| --- | --- | --- | --- | --- |
| 1.14.0 (`1.14`) | Yarn 1.14+build.21; client `93907cbf0655aa5f10d049efa4d745f4250eecc5d0d5d1268046eec3144f323a` | `.decompile-provenance.json`: `ea70a955a12f377cd3b9e8d5f346f4625b044d55da726b6bc472405ae91f4341` | `8889e22779b9124998931bc97ce8b0d183d335c32a9bcbb089e49de65882bae6` | `ebda4ad25a265f8c85e88345efcce753b78e248a29a9b910c5cfd311a4a69319` |
| 1.14.1 | Yarn 1.14.1+build.10; client `7194d1326cf796f62ac55f3cb56f851c232895639ae27510f3207d5a73f91814` | `.decompile-provenance.json`: `308537c5adec3104c5ca243eb5010554f00978635ed74f0d7801c804dd4d4907` | `331e2d7dfa936a6f7197910ba374c4a60b479c8565ff7c64a4d6a9e1e8b52dda` | `ebda4ad25a265f8c85e88345efcce753b78e248a29a9b910c5cfd311a4a69319` |
| 1.14.2 | Yarn 1.14.2+build.7; client `9b740c9fd4955981125ebd3cd086baa8b4392963d043a3480b5b942a96637c25` | `.decompile-provenance.json`: `786546f99684423cc2563be45c9c3334dfcc2c7db58ddb74ba39ce403bd0869a` | `e8224e7df98ccf0986bb390f35e64293263d17b9ab1dbdfeb661c65d28b6ee78` | `333bb39db18af4cef320eb73b7abe359d438fb2a6ca7d8798ee449da6c684398` |
| 1.14.3 | Yarn 1.14.3+build.13; client `4063062677433988e012fba53e42e4c21adb6919ac7f061f19f3c6e75b334e07` | `.decompile-provenance.json`: `81ea0021eb122d4e3340e20d47d6ecb7079365ba767a038d26a8264cddacc28a` | `e8aed9c63c0dfbe88e4b9e11c58881bc6e04b9cbaecbde5e3606e3afa406d72b` | `e3f361ac4e369d01473f3d84044ee33f9685d182c4485e2e80b7f62756950963` |
| 1.14.4 | Ornithe Feather; client `b3b2a798e2d67b566008fe4a03767ae2c7ff3f8c7ba6751e7b71fc7299672d0a`; Feather merged mapping artifact `3162806b9fb266d7e6d2c594be8d4cc6c91ddb09b4e55d1b566e5429edbd793f` | `ornithe-feather.sources.sha256`: `717b468536348557f9dae8cf57edccf80ad16eb65e5bef6c60bdb5ea3c9f75bc` | `2cccf4331ce9e62013eeb8e96163e5e146e1291619eb998ce9867daa87c02b61` | `2614deb3b50d11f6cdf54d9e308703bf49cb53b72646b05253857e9557d657df` |
| 26.2 | Unobfuscated; client `40896ee9f1e2bec3c934daac7e93d41e9e3d9c2f8ae0ca366d52ffbfd1afa290` | `unobfuscated.sources.sha256`: `a7ad74fc712567eb87136afc1eafcfd602b4088e9869f3596331fd34faefe894` | `7ffd9c70966edc50c9cb4d9a8fe17a518e2678ff44c8026e763d0b94ac0ae51a` | `8decc71b9c780664578ddb14591db2a2f207c72c05b676edded6f8e964576531` |

Relevant source anchors: 1.14.0 Yarn `LivingEntity.java:1216`, `PlayerEntity.java:107`; 1.14.1 Yarn `LivingEntity.java:1216`, `PlayerEntity.java:107`; 1.14.2 Yarn `LivingEntity.java:1222`, `PlayerEntity.java:107`; 1.14.3 Yarn `LivingEntity.java:1233`, `PlayerEntity.java:108`; 1.14.4 Ornithe Feather `LivingEntity.java:1239`, `PlayerEntity.java:108`; 26.2 unobfuscated `Player.java:2024–2025` and `LivingEntity.java:1722–1740`.

The source locator `build/source-identities-20261116/locations.tsv` was used as an input (SHA-256 `fad244423759128a9991e0ff9f67cdcd678e9ad8a854fbea57c655c625bce389`). It inventories 1.14 inputs but has no 26.2 row; the exact 26.2 identity above was checked directly against `decompiled_minecraft/26.2/unobfuscated.ready.json` and `unobfuscated.sources.sha256`.

## Output identity

This audit is saved as `build/mcpk-20261116/pipeline-114-movement/flying-gate-audit/audit.md`; its SHA-256 is recorded by the coordinator handoff. The target is ignored by Git (`.gitignore` line 3).

