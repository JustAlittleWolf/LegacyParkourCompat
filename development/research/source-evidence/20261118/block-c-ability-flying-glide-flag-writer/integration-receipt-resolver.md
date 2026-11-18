# Product integration receipt: resolver-bounded ability-flight glide flag

## Product, evidence, and independent review

- Product base: `aae9c86464638cb5ed7faa535c92569b89484906`
- Exact evidence archive tip/tree: `c3e3c4871acde9c12764f2d3b96ef0e891a3dd81` / `1cea6468217aafdb8ae097d55eacfac00333c81c`
- Evidence bundle SHA-256: `a6c0b62c8caf5550cb8f4e8d6434eeb70f089f219c2a242c919e0f3993052ff5`
- Archive scope: 40 exact-byte files, including nineteen comparison/verdict pairs and README; the independent review reverified all 40 files and pair bindings.
- Product code commit/tree: `610fabc28e91424d7d648d50f4c4c85a8c1f8a8d` / `e5cbb376a429230412a4b5ca5a1299861a0e3b34`
- Independent integration review: `c_ability_flying_glide_flag_product_review`, signed PASS for the exact code commit and tree.
- Ready ref: `refs/campaign-ready/c-ability-flying-glide-flag-writer-resolver`
- Product code paths: one hook interface and shared eligibility helper; `EarlyAbilityFlightGlideFlag`, `LateAbilityFlightGlideFlag`, native fences at `V1_8_9` and `V1_20_2`; and the existing `PlayerMixin` travel-return dispatch.

The shared eligibility helper requires a local client player, ability flight enabled, an already-live fall-flying flag, no passenger, dry/non-lava state, and a chest Elytra. The single existing `Player.travel(Vec3)` RETURN dispatch clears shared flag 7 only after the existing fall-distance callback. It performs no version switch; `ChangeResolver` selects the closest applicable provider.

## Resolver case table

`ChangeResolver` chooses the minimum registered `emulates` ordinal that is not older than the selected enum value. The exact selected-profile groups resolve as follows:

| Selected profile groups | Resolved provider | Result |
| --- | --- | --- |
| `V1_8`, `V1_8_1`, `V1_8_5`, `V1_8_9` | `V1_8_9` native fence | Native behavior |
| `V1_9`, `V1_9_3`, `V1_10`, `V1_10_1`, `V1_11`, `V1_11_1`, `V1_11_2`, `V1_12` | `V1_12` early enabled provider | Accepted 1.9–1.12.2 operation |
| `V1_13`, `V1_13_1`, `V1_14`, `V1_14_1`, `V1_14_2`, `V1_15`, `V1_15_2`, `V1_16`, `V1_16_2`, `V1_17`, `V1_17_1`, `V1_18`, `V1_18_2`, `V1_19`, `V1_19_3`, `V1_19_4`, `V1_20`, `V1_20_2` | `V1_20_2` native fence | Native behavior; no middle-range behavior claim |
| `V1_20_5` | `V1_21` late enabled provider | Accepted 1.20.5–1.20.6 operation |
| `V1_21` | `V1_21` late enabled provider | Accepted 1.21–1.21.1 operation |
| `V1_21_2`, `V1_21_4`, `V1_21_5`, `V1_21_6`, `V1_21_9`, `V1_21_10`, `V1_21_11`, `V26_1`, `CURRENT` | No applicable provider | Native fallback |

The `V1_8_9` fence keeps feature-absent profiles native. The `V1_20_2` fence covers 1.20.2–1.20.4 and also ensures unsupported intervening profiles resolve to native behavior. The `V1_21` provider is bounded below by that fence and above by resolver exhaustion; it does not change later native profiles.

## Build disposition

Exact command on product code commit `610fabc28e91424d7d648d50f4c4c85a8c1f8a8d` and tree `e5cbb376a429230412a4b5ca5a1299861a0e3b34`:

```text
.\gradlew.bat build -x test --init-script development/disable-tests.gradle --console=plain
```

Result: `BUILD SUCCESSFUL`; 19 actionable tasks, 3 executed and 16 up to date. The init script disables every Gradle `Test` task, and `-x test` excludes the test task. No tests or runtime, game, TAS, Gym, server, or Docker launch was performed. Compilation is not runtime-parity evidence.

The reviewer verified the resolver case table, native fences and fallback, exact source archive, thin dispatch, and false-only bit-7 clear after movement and the separate fall-distance reset. The operation remains limited to the accepted early and late source ranges. No matching baseline backlog finding for this distinct flag-writer operation was identified; this receipt records the association for central handling without changing the backlog.
