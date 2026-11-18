# Clean-baseline product receipt: near-vertical Elytra look

## Product and review identities

- Clean product code/evidence commit: `2e3dd11f16cdb298838836e97290be65deac4c3c`
- Compiled Git tree: `7a02f97fd93092426ebca7fa43e59ef8b78d2b7d`
- Sole parent and requested clean baseline: `aae9c86464638cb5ed7faa535c92569b89484906`
- Code path: `src/client/java/me/wolfii/legacyparkourcompat/change/v1_12/VerticalPitchGlideLook.java`
- Static review: signed PASS by `c_near_vertical_glide_candidate_review` for the exact code commit/tree above. The reviewer verified 35/35 source-evidence file blobs, including the exact denominator report and its accepted binding.
- Earlier staged attempts remain on their existing branch for traceability; neither is an ancestor of this clean code commit. The previous ready ref remains unchanged.

## Exact-tree build

Authorized Windows build command, run from the clean product branch on the exact tree above:

```text
gradlew.bat build -x test --init-script development/disable-tests.gradle --console=plain
```

Result: `BUILD SUCCESSFUL` in 6 seconds using Gradle 9.7.1 and Fabric Loom 1.17.21; 19 actionable tasks were up-to-date. The init script disables every Gradle `Test` task, and `-x test` excludes the test task. No test, game, TAS, Gym, server, or Docker launch occurred. Compilation does not establish runtime parity.

## Byte-bound source evidence

The source artifacts are included byte-for-byte in this clean tree, without importing their unrelated product or backlog ancestry. Original accepted source commit identities are referenced here:

- Near-vertical alias and endpoint archive source commit: `06da1d202aa68971458a3cae21afff16f596026d`.
- 1.12.2→1.13 denominator witness source commit: `b8ca33c5b07827bc0d6195fbcda0cbd0e3bde0ae`.

Bound source artifact SHA-256 identities:

| Evidence | Report SHA-256 | Verdict SHA-256 |
|---|---|---|
| 1.12→1.12.1 near-vertical alias | `4b2a53ebda3a6332b6990f395158760c5f0604ad78f5822ca3254754afdec9da` | `948b963172d503b8476953a3189173380a6b5cac9b0ac2ff534cfda2af492991` |
| 1.12.1→1.12.2 near-vertical alias | `ac9b2bc553da669aaeb18fca8dd6205aee693b85c6a028a58e7c1d68f5c6ad83` | `a309ba1e18856f685a97e7350ee9ee5a429e928ca4a639a794a84ee166f640f7` |
| 1.12.2→26.2 near-vertical look and first active dry movement | `152a5c403175787915ed6aa6b0d3e8414375e3346538418f7db25f82617b43a0` | `01d64143d13d841636385eccdacf99644f3e47b10bf47effcb7d8a477e6109ec` |
| 1.12.2→1.13 denominator witness | `2ce93a63aab79b313d82351815aba47a98a901edda5d4f3c0932b29fcde3adfc` | `fcce4b024724a9f8b49893c639c26508c67e6323ddf6b7ff3b48afc575f9ead4` |

The near-vertical archive contains 13 exact alias comparisons from 1.9 through 1.12.2, its binding corrections, and the 1.12.2→26.2 provenance resolution. The separate denominator report is the exact 5,366-byte source blob `b779c8861057b798c5f9ec65dd8a417b568fb086`, whose raw SHA-256 is `2ce93a63aab79b313d82351815aba47a98a901edda5d4f3c0932b29fcde3adfc`. The preserved blob retains its one CRLF; no tracked `.gitattributes` or source artifact was changed.

## Integrated operation and limits

The existing `VerticalPitchGlideLook @V1_12` and `FallFlyingLookHook` restore the old float-table vector for the accepted exact-zero horizontal-look case: a finite negative near-vertical pitch, finite yaw and native components, an already-fall-flying local client player, dry state, and vanilla Elytra in the chest slot. The implementation reuses the existing float sine table and operation order. Existing exact `-90.0F` behavior remains unchanged. The native positive-horizontal denominator guard is unchanged, and no additional operation or mixin was added.

The clean baseline already contains the existing V1_11_2 look provider, the V1_12 provider and the later float-table provider. Resolver selection leaves the V1_11_2 provider effective for earlier selected profiles; the modified V1_12 provider covers selected 1.12–1.12.2. Earlier profile behavior is unchanged and no new alias-specific near-vertical behavior is claimed for those groups.

Intended central finding association: `development/research/findings/1.12.2--1.13.2/elytra-look-guard.md`. This receipt covers the bounded look-vector correction only. It does not modify the denominator guard, activation/final glide handling, lift/gravity math, or establish complete Elytra movement or runtime parity.
