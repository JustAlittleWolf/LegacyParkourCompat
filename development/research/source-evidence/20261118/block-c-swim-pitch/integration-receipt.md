# Product integration receipt: swimming pitch look-Y through 1.14.4

## Product, evidence, and review identities

- Product baseline: `aae9c86464638cb5ed7faa535c92569b89484906`.
- Product code commit/tree: `4b45d16df48b1abd57e144dc9ff5020865cb48d1` / `58a5889af90066b2437580b13aa5f465c49477c2`.
- Original source evidence archive commit/tree: `aaade47641c529b9edb64ae5a7d31b8e7fe0faa5` / `f578cc72b97ed6efe68be4a5a27743da34d8c749`; bundle SHA-256 `bd78caf06fa010d80403126413942353325e6a7bca67da172294728a28c77217`; 14 exact files.
- 1.14 expansion archive commit/tree: `0a4347b96837e43b71f20a320d98de662df9fb94` / `e1170556ce2bdc3d0cc8a16cedc38f1eadcea7e3`; bundle SHA-256 `96e6c63cabb3bd4ddbada7c7e88f74e49a2d4852366403c7131f6e2221d46c03`; 16 exact files, including six comparison pairs and their correction/acceptance records.
- Product evidence-only import commit: `8d178f7a90fc0ff172f49563bfbbd85b4416f0f6`. Both evidence subtrees were byte-compared with their source archive commits in the worktree and index; all comparisons matched. No candidate or source archive history was merged.
- Independent product review: `c_swim_pitch_114_product_review`, signed PASS for the exact code commit/tree. The reviewer verified five scoped Java paths, all 30 evidence paths, the full resolver table and exact scope.
- Ready ref: `refs/campaign-ready/c-swim-pitch-114`.
- The sanitized candidate `47ab5417b4869441c42507edefe95d31a8e5715a` / `3ccfb433027d7d20eae0619003c606c59a32fa20` was reconciled against the existing hook, provider, and redirect; its history is not in this product ancestry.

## Integrated operation and scope

The existing `SwimmingPitchHook`, `LegacyTrig` table, and single `Player.travel` `getLookAngle` redirect are reused. The historical provider is annotated `V1_14_2` and retains the source-matched float degree-to-radian conversion and float sine-table lookup. The no-op `V1_12` provider returns the incoming native Y. The old version-based `supports()` guard is removed; `ChangeResolver` alone chooses the operation.

The redirect calls native `getLookAngle()` once. If no provider applies or the resolved Y has identical raw double bits, it returns the original native `Vec3`; otherwise it constructs a vector with only Y changed and preserves native X/Z. The established travel call, eligibility gates, fluid/jump conditions and vertical-write order remain unchanged. No swim-state lifecycle writer was changed.

| Selected profile groups | Resolved provider | Result |
| --- | --- | --- |
| `V1_8`, `V1_8_1`, `V1_8_5`, `V1_8_9`, `V1_9`, `V1_9_3`, `V1_10`, `V1_10_1`, `V1_11`, `V1_11_1`, `V1_11_2`, `V1_12` | `V1_12` native fence | Native Y |
| `V1_13`, `V1_13_1`, `V1_14`, `V1_14_1`, `V1_14_2` | `V1_14_2` enabled provider | Historical float-table look-Y for selected 1.13–1.14.4 profiles |
| `V1_15`, `V1_15_2`, `V1_16`, `V1_16_2`, `V1_17`, `V1_17_1`, `V1_18`, `V1_18_2`, `V1_19`, `V1_19_3`, `V1_19_4`, `V1_20`, `V1_20_2`, `V1_20_5`, `V1_21`, `V1_21_2`, `V1_21_4`, `V1_21_5`, `V1_21_6`, `V1_21_9`, `V1_21_10`, `V1_21_11`, `V26_1`, `CURRENT` | No applicable provider | Native fallback; no .15+ source claim |

The source comments cite the 1.13 Feather Gen2 `PlayerEntity.moveRelative`, `Entity.getLookVector`, `Entity.getRotationVector` and `MathHelper.sin(float)` path; the 1.13.2–1.14.0 Feather continuity pair; 1.14.3 Yarn `PlayerEntity.travel`; 1.14.4 Mojmap `Player.travel(Vec3)`; and the 26.2 unobfuscated `Player.travel(Vec3)`, `Entity.calculateViewVector`, and `Mth.sin(double)` endpoint witness. The accepted 1.14.0→26.2 pitch witness is limited to the reported `84.375F` case; it is not a broad modern parity claim.

Intended central source association: `development/research/findings/1.12.2--1.13.2/swim-look.md`, for the bounded historical look-Y operation and its accepted 1.13–1.14.4 extension. This is not a claim that the full swim-look finding is closed. The backlog was not edited in this batch.

## Build disposition

Exact Windows command on product code commit `4b45d16df48b1abd57e144dc9ff5020865cb48d1` and tree `58a5889af90066b2437580b13aa5f465c49477c2`:

```text
.\gradlew.bat build -x test --init-script development/disable-tests.gradle --console=plain
```

Result: `BUILD SUCCESSFUL`; 19 actionable tasks, 3 executed and 16 up to date. The init script disables every Gradle `Test` task, and `-x test` excludes the test task. No tests or runtime, game, TAS, Gym, server, or Docker launch was performed. Compilation does not establish runtime parity.
