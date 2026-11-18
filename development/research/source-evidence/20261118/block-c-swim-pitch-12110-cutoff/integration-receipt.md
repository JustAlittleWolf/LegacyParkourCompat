# Product integration receipt: swim pitch through 1.21.10

## Immutable product, review, and build identities

- Product ready ref to create: `refs/campaign-ready/c-swim-pitch-12110`
- Code commit/tree: `ce79dc09a7f9b40c9c1cf69669ce282ed296e413` / `50221b9a740e6a1e8878c0398613d37a29a924b6`
- Code parent / new evidence-only product commit: `9ae3fd1bea66128c62c60738776ae11795916811`
- Previous verified product base/ref: `1a3107eda78bef5ff5eb8b9fcbd5c7369f8deefb` / `refs/campaign-ready/c-swim-pitch-1171`
- Source baseline: `aae9c86464638cb5ed7faa535c92569b89484906`
- Independent product review: signed actual PASS by `c_swim_pitch_114_product_review` for the exact code commit/tree above.
- Product code path: `src/client/java/me/wolfii/legacyparkourcompat/change/v1_21_10/SwimmingPitch.java`; the previous enabled path `change/v1_17_1/SwimmingPitch.java` is removed. Existing `V1_12` native fence, `SwimmingPitchHook`, `LegacyTrig`, and the single `Player.travel` look-vector redirect are unchanged.
- Sanitized candidate `83e2e9ccb2274d127c9ec57e3fa60b3a14527b62` / tree `dba1886c158b7ea15364b855e49b268f1a87d833` was independently accepted and reconciled, but its history is not in product ancestry.

Three exact source archives extend the accepted product evidence through the 1.21.10 lookup cutoff. Each subtree was imported without source-bundle ancestry, and every imported Git blob ID matched its exact archive commit:

| Evidence span | Exact source archive commit / tree | Bundle SHA-256 | Files / adjacent pairs |
|---|---|---|---|
| 1.17.1–1.19.4 | `30edf8f3a42ba861d81c6ec85d1dfc9ce127cb69` / `7203c784bfa3317e0323e9d0eb36bc93dfd05b45` | `f68a61d6129ec0a52d23074a202603dff2a73318b04f4d9e0f33ccdb7eb0f8a9` | 19 / 8 |
| 1.19.4–1.21.5 | `091720c32ecce31c607dfe62c1a4d375631a76b8` / `e763e113d4341e0fefb2f7d15c72036dc611886b` | `7ad4c622b63ecb425808398a8871848a0d40e6fdec2e6c2d876384deb0f1772e` | 30 / 13 |
| 1.21.5–26.2 cutoff sequence | `111f91cb819ebefb741b8dd193360e38d5c8e747` / `dbc589453daf6511b71c24289d68a7e68453b271` | `145874ed0d196b00d2d29482b98f8a8d8139d178e52eca77a3454cc43fd6a1c7` | 25 / 10 |

Together the new archives contain 74 exact files and 31 independently reviewed adjacent source edges, including source identity, correction, and acceptance sidecars. Earlier accepted 1.13–1.17.1 source evidence remains reachable through the prior verified ready ref and its receipts; the code branch descends from that product history and the exact `aae9c864` baseline. No unrelated swim-state branch or candidate history is included.

Exact Windows build command on the code tree:

```text
.\gradlew.bat build -x test --init-script development/disable-tests.gradle --console=plain
```

Result: `BUILD SUCCESSFUL` on `ce79dc09a7f9b40c9c1cf69669ce282ed296e413` and tree `50221b9a740e6a1e8878c0398613d37a29a924b6`, using Temurin OpenJDK 25.0.3 on Windows. The init script disabled every Gradle `Test` task and `-x test` excluded test execution. The build reported 19 actionable tasks, 3 executed and 16 up-to-date. No tests or runtime, game, TAS, Gym, server, or Docker launch was performed. Compilation does not establish runtime parity.

## Integrated operation and exact cutoff

The integration moves the existing enabled `SwimmingPitch` provider from `V1_17_1` to `V1_21_10`, changes only its `@MovementChange` annotation and source comment, and leaves the executable `lookY` method exactly unchanged from the previous code tree: float pitch radians followed by `-LegacyTrig.sin(pitch)`. The 1.13–1.17.1 source continuity evidence remains in the previous product ancestry; the new 31 accepted adjacent edges establish continuity through 1.21.10 and the exact later lookup cutoff.

For exact 1.21.10 Mojmap, `Player.travel(Vec3)` reads `Entity.getLookAngle().y`; `Entity.calculateViewVector(float,float)` supplies pitch-derived Y from `Mth.sin(float)`. `Mth` uses the float product and truncating `int` index into its 65,536-entry float table. On the exact 1.21.10→1.21.11 Mojmap edge, the `Player.travel(Vec3)` writer and its swim/non-passenger gate remain unchanged, while `Mth` changes both table initialization and indexing: 1.21.11 uses the double divisor `10430.378350470453`, the double product, a truncating `long` cast and `65535L` mask in `Mth.sin(double)`. The float pitch-radian value is widened for that call.

The accepted source witness at pitch `51.0260009765625F` selects float-LUT index 9289 / Y `-0.7774314880371094` in 1.21.10 and double/long-LUT index 9288 / Y `-0.7773711681365967` in 1.21.11. Both satisfy the same `lookY < -0.2` factor gate; from zero Y velocity the pre-travel writes differ. This is a bounded source arithmetic witness, not a runtime trajectory. Exact later source comparisons retain the new lookup procedure through 26.2.

Resolution remains per operation, with no enum checks in the Change:

| Selected `ParkourVersion` groups | Resolved provider | Result |
|---|---|---|
| `V1_8`, `V1_8_1`, `V1_8_5`, `V1_8_9`, `V1_9`, `V1_9_3`, `V1_10`, `V1_10_1`, `V1_11`, `V1_11_1`, `V1_11_2`, `V1_12` | `V1_12` native fence | Preserve native incoming look-Y. |
| `V1_13`, `V1_13_1`, `V1_14`, `V1_14_1`, `V1_14_2`, `V1_15`, `V1_15_2`, `V1_16`, `V1_16_2`, `V1_17`, `V1_17_1`, `V1_18`, `V1_18_2`, `V1_19`, `V1_19_3`, `V1_19_4`, `V1_20`, `V1_20_2`, `V1_20_5`, `V1_21`, `V1_21_2`, `V1_21_4`, `V1_21_5`, `V1_21_6`, `V1_21_9`, `V1_21_10` | `V1_21_10` enabled provider | Historical float-table look-Y for the existing swim correction. |
| `V1_21_11`, `V26_1` | No applicable provider | Preserve native lookup and movement. |
| `CURRENT` | Resolver returns no changes | Native current-version behavior. |

The intended central finding association remains `development/research/findings/1.12.2--1.13.2/swim-look.md`, for this accepted look-Y operation and its source-supported extension only. This receipt does not claim that the full finding is closed. No backlog or finding file was edited.
