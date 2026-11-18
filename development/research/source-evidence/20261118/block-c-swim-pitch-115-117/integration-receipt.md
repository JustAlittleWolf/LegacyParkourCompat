# Product integration receipt: swim pitch through 1.17.1

## Immutable product, review, and build identities

- Product ready ref to create: `refs/campaign-ready/c-swim-pitch-1171`
- Code commit/tree: `b1d68257943a61bc227a882e802dc169dee96f4e` / `83a6f2f0fb3721caf33faf26e802cefd318f9b3c`
- Code parent / evidence-only product commit: `3366e69cf0147bdadf49aa7d849aebb63396b386`
- Previous verified product base/ref: `5d714be9872339855b05d912894183d8aa434c11` / `refs/campaign-ready/c-swim-pitch-114`
- Source archive commit/tree: `d35a9b7c9ece1ac10b516ad19fa8c8f0629d38a5` / `2a3eb060860fe463ce34694615796d8cf59c2216`
- Source bundle SHA-256: `0300a5c1328f55bc7dc780460b7845ca65558df795ad9d8857bea057d779f3b2`
- Product evidence subtree: `development/research/source-evidence/20261118/block-c-swim-pitch-115-117/` (27 exact files: README, 11 accepted adjacent report/verdict pairs, identity and acceptance sidecars, and the line-ending attributes file). All 27 staged Git blob IDs matched the verified source archive commit before product commit.
- Candidate input, not included in product ancestry: `6d056872cfc97c50106e2bc1531981f325812e61` (tree `9807fbf2ab3a1cd7d73754afd65ae794a50d5273`), independently accepted before integration.
- Independent product review: signed actual PASS by `c_swim_pitch_114_product_review` for the exact code commit/tree above.
- Product source path: `src/client/java/me/wolfii/legacyparkourcompat/change/v1_17_1/SwimmingPitch.java`. The prior `v1_14_2/SwimmingPitch.java` path is removed.

Exact Windows build command on the code tree:

```text
.\gradlew.bat build -x test --init-script development/disable-tests.gradle --console=plain
```

Result: `BUILD SUCCESSFUL` on commit `b1d68257943a61bc227a882e802dc169dee96f4e` and tree `83a6f2f0fb3721caf33faf26e802cefd318f9b3c`, using Temurin OpenJDK 25.0.3 on Windows. The init script disabled every Gradle `Test` task and `-x test` excluded test execution. The build reported 19 actionable tasks, 3 executed and 16 up-to-date. No tests or runtime, game, TAS, Gym, server, or Docker launch was performed. Compilation does not establish runtime parity.

## Integrated operation and resolver scope

The integration relocates the existing enabled `SwimmingPitch` provider, changes its annotation from `V1_14_2` to `V1_17_1`, and updates its source comment. The executable `lookY` method is unchanged: it converts the current pitch to float radians, then returns the negated `LegacyTrig.sin` result. The existing `SwimmingPitchHook`, `LegacyTrig`, V1_12 native fence, and single `Player.travel` look-vector redirect are unchanged.

| Selected profile groups | Resolved provider | Result |
|---|---|---|
| V1_8 through V1_12, including aliases | `V1_12` native fence | Preserve incoming native Y. |
| V1_13 through V1_17_1, including every exact covered alias | `V1_17_1` enabled provider | Use the accepted historical float-table look-Y for this existing swim correction. |
| V1_18 and later | No applicable provider | Preserve native behavior; this batch makes no later-profile claim. |

The 11 independently accepted exact adjacent comparisons span 1.14.4→1.15 through 1.17→1.17.1; their precise reports, verdicts, identity corrections and mapping/source details are preserved in the evidence subtree above. Source comments cite the exact Mojmap `Player.travel(Vec3)` path in 1.14.4 and 1.17.1, its `Entity.getLookAngle` / `calculateViewVector(float,float)` float path, and `Mth.sin(float)`. The evidence archive's 1.17.1 verdict is limited to that swim-pitch operation and examined inherited water move; it does not establish full-pair movement coverage.

Intended central finding association: `development/research/findings/1.12.2--1.13.2/swim-look.md`, for the accepted look-Y operation extension only. This receipt does not claim that the entire finding is closed. No backlog or finding file was edited.
