# Product integration receipt: full ability-flight glide flag range

## Immutable product, evidence, and review identities

- Product baseline: `aae9c86464638cb5ed7faa535c92569b89484906`.
- Previously accepted resolver receipt: `aceb5d72f8d34f59e4e11e2b3999d53d429106d7`; this batch descends from it and retains the corrected resolver implementation ancestry.
- Earlier accepted source archive commit/tree: `c3e3c4871acde9c12764f2d3b96ef0e891a3dd81` / `1cea6468217aafdb8ae097d55eacfac00333c81c`; original bundle SHA-256 `a6c0b62c8caf5550cb8f4e8d6434eeb70f089f219c2a242c919e0f3993052ff5`.
- Middle source archive commit/tree: `2088aced2f862d0f3cb651e9fb8ce7c18d1276a6` / `c9b6c64217ef892b1e028d7ece1b3a39e4211cfd`; bundle SHA-256 `ad362ab2cb781d13650a0585fa612dbd02fc1da1ca0c8d10891454124b98ff3d`. This archive preserves 45 exact-byte files and 20 adjacent comparison/verdict pairs.
- Middle closure archive commit/tree: `7bb92b88ae7a3c7f756a1dc352696419bf7f1817` / `4b2eabd345c3b98afb0c5e1e6a82389d2466a1ef`; bundle SHA-256 `d881e631b625d7ea6cb8636aa7744ce820c57bc6f519deaba28f624148b3b814`. This archive preserves 29 exact-byte files and 13 adjacent comparison/verdict pairs.
- The two middle archives were imported as source-evidence subtrees only. Worktree and index comparisons against both archived commits returned exact matches; their candidate/source branch history was not imported. The original early/late archive remains unchanged.
- Product code commit/tree: `358f226b782abc4945e96629fb1e806dfbb6542b` / `9752c7df33e6f8c77d41814013deab4c77e0d9a3`.
- Independent integration review: `c_ability_flying_glide_flag_product_review`, signed PASS for the exact product code commit and tree.
- Ready ref: `refs/campaign-ready/c-ability-flying-glide-flag-writer-all-historical`.

## Integrated operation and scope

The single `AbilityFlightGlideFlagClear` Change at `V1_21` uses the existing `AbilityFlightGlideFlagHook` and existing thin `Player.travel(Vec3)` RETURN injection. The Change returns true only for a local client player with ability flight enabled, an already-live fall-flying flag, no passenger, dry/non-lava state, and a chest Elytra. The existing mixin performs one direct `setSharedFlag(7,false)` after the actual travel call and the separate fall-distance callback. The Change contains no profile switch, and no extra movement loop, marker, or climbability query was added.

The source comments bind the operation to 1.9.1 and 1.12.2 (Ornithe Feather Gen2 `PlayerEntity.moveRelative`), the contiguous 1.12.2–1.20.5 middle archive (`PlayerEntity.moveRelative`, Yarn `PlayerEntity.travel`, and Mojmap `Player.travel(Vec3)`), 1.21.1 (Mojmap `Player.travel(Vec3)`), and 26.2 (unobfuscated `Player.travel(Vec3)`, `LivingEntity.travel(Vec3)`, and `LivingEntity.travelFallFlying(Vec3)`). The archive records the exact intermediate pair set and mappings; this receipt does not infer unexamined aliases.

## Resolver case table

The resolver selects the closest registered `emulates` value that is not older than the selected profile.

| Selected profile groups | Resolved provider | Result |
| --- | --- | --- |
| `V1_8`, `V1_8_1`, `V1_8_5`, `V1_8_9` | Native fence at `V1_8_9` | Native behavior before Elytra |
| `V1_9`, `V1_9_3`, `V1_10`, `V1_10_1`, `V1_11`, `V1_11_1`, `V1_11_2`, `V1_12`, `V1_13`, `V1_13_1`, `V1_14`, `V1_14_1`, `V1_14_2`, `V1_15`, `V1_15_2`, `V1_16`, `V1_16_2`, `V1_17`, `V1_17_1`, `V1_18`, `V1_18_2`, `V1_19`, `V1_19_3`, `V1_19_4`, `V1_20`, `V1_20_2`, `V1_20_5`, `V1_21` | Enabled Change at `V1_21` | Historical direct clear for selected profiles from 1.9 through 1.21.1 |
| `V1_21_2`, `V1_21_4`, `V1_21_5`, `V1_21_6`, `V1_21_9`, `V1_21_10`, `V1_21_11`, `V26_1`, `CURRENT` | No applicable provider | Native fallback |

`V1_8_9` is the sole native fence before the operation's 1.9 introduction. The `V1_21` provider is the sole enabled Change through the exact 1.21.1 profile group. `V1_21_2` and later have no newer applicable Change, preserving native behavior. The early, middle, and late accepted source archives supply adjacent evidence across the implemented interval.

## Build disposition

Exact build command on product code commit `358f226b782abc4945e96629fb1e806dfbb6542b` and tree `9752c7df33e6f8c77d41814013deab4c77e0d9a3`:

```text
.\gradlew.bat build -x test --init-script development/disable-tests.gradle --console=plain
```

Result: `BUILD SUCCESSFUL`; 19 actionable tasks, 3 executed and 16 up to date. The init script disables every Gradle `Test` task, and `-x test` excludes the test task. No tests or runtime, game, TAS, Gym, server, Docker, network, or source-generation operation was performed. Compilation is not runtime-parity evidence.

The signed independent review verified the full resolver case table, all adjacent evidence groups and exact archive bytes, the native pre-feature fence and later fallback, the single post-travel bit-7 clear after the separate reset, and absence of held code ancestry. This receipt records integration evidence only; it does not modify the primary checkout or central backlog and does not claim server authority or runtime parity.
