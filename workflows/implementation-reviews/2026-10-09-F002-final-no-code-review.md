# Independent review — F-002 final no-code applicability reconciliation

**Verdict: ACCEPT the no-code disposition for the accepted F-002 boundary.** The existing registrations select the float implementation for 1.17.1, 1.18, and 1.18.1, and the double implementation for 1.18.2, matching the accepted source evidence. No F-002-specific Java change is needed for those releases. This verdict does not establish source fidelity for unreviewed releases, broader tick parity, or runtime parity.

## Exact review bindings

- Candidate reconciliation: commit `0edd1a92db59ff2ae326c15562c1a423be74e527`, `workflows/fix-implementation/reconciliations/F002-elytra-cosine-2026-10-09.md`; blob `af0903b06c1dfa7d53479621068c5d6560d2d3e8`, raw SHA-256 `65f4a1d7441784db7b0e87430f37dc102f06d752b89b2857e33b389a98fff751`. Its only change from the previous F-002 reconciliation is the final boundary/applicability record; the candidate diff makes no Java change.
- Accepted F-002 endpoint snapshot: `e531086eecc778432b40b2b9ef39499b8a7f0dba`, `workflows/source-campaign-2026-10-07/1.17.1--1.18.2/findings/F-002-elytra-cosine-precision.md`; blob `75b6206ca3434ffd96d84caa1a6ff2fc6b5c4ab5`, raw SHA-256 `bb72676167340fa5e001062b3f9cb69ce36d0160b37fe834212693a4feb0b546`. Its original source acceptance is at `2fd121e116d7185165c76aea1c3708273afc7ea1`, `workflows/source-boundary-reviews/2026-10-08-mc1171-1182-snapshot-review.md`, blob `871ad5bccde2194ccc733ed8fef92472a1c96c2a`; that acceptance is for the bounded cosine-precision operation only.
- Accepted boundary evidence: author commit `14443f43fbcc30152916ac75796846d796cf0268`, `workflows/source-boundary-reviews/1171-1182-F001-F002-boundary-evidence-2026-10-09.md`; blob `8892a7533358c6e7053527c24dacd0a2acce5ae9`, raw SHA-256 `66f5659dd17ff8441d10a02d95920ecd5d4e190c53c2c0893eac79efec36c6cb`.
- Independent boundary acceptance: `163ea4881b327a9316a266601705463784566e2c`, `workflows/source-boundary-reviews/2026-10-09-1171-1182-boundaries-independent-review.md`; blob `b505e009ad5726b7d7fd18c7bf35a38495003e28`, raw SHA-256 `bebbb4dc67323ece9615444d5d32ec56eba480683abb6a30891d18481b5ef124`. It accepts F-002's float coefficient through exact 1.18.1 and double coefficient at exact 1.18.2; it does not claim a velocity or trajectory result.
- Prior endpoint correspondence acceptance: `d898d4c0ef42f0cdb065aacc36eb30d28dcc06a4`, `workflows/implementation-reviews/2026-10-09-endpoint-reconciliation-review.md`; blob `95ea1be9272116a88af552fcaf64ff0570470b52`, raw SHA-256 `0b257ce7e7dd20d2a96a48a735a1584d7ed340033cae9ec6a496d269e1970dcf`. Prior corrected-artifact identity acceptance: `3026b37ea424037ea6b948bae52c2c9888910be1`, `workflows/source-boundary-reviews/2026-10-09-F002-artifact-identity-review.md`; blob `7952d5702c7dbeec85e1a200dcc221519ba5355e`, raw SHA-256 `8f89b2b12cdf184538cb9779b5ad049a01107134fa5019625bd08de22b23a1d4`. The corrected snapshot it accepts is commit `2f3425601753d8b153e795a973a147e6ce0cb0b5`, `workflows/source-campaign-2026-10-07/1.17.1--1.18.2/findings/F-002-elytra-cosine-precision-artifact-correction-2026-10-09.md`; blob `dbce0be4ade1fdd84acd9867be201f9f630609cc`, raw SHA-256 `ccc39601c087a9df5d60e9506e7eaa1831adb7336b89c346bbdeecfcddb71f01`. These prior verdicts remain bounded to their stated endpoint and identity claims.

## Source publication and boundary check

The current canonical ready roots are Mojmap for the historical samples and unobfuscated for 26.2. Direct marker, source-manifest, and cited source-file hashes were checked against the reconciliation and accepted boundary report. The corrected 1.18 marker digest below includes its final `f`; the boundary reviewer separately recorded that transcription correction.

| Release | Ready marker SHA-256 | Source manifest SHA-256 | `LivingEntity.java` SHA-256 | `Mth.java` SHA-256 |
|---|---|---|---|---|
| 1.17.1 | `c7180dce94d7b57b53a964445b0c788c71f23ba3ba40eea593c11cc939185e9b` | `93270d229acfb751bf56daf1e7be26ce3dcff26b29e94aa157de621405a3463b` | `33fd081aadb2b6fdc9ebf487db6da5b38c54f4b8676572790ee2203690d15e6f` | `24515c4549e01e985017227dccf7159166a675b232be9135d5f896022a9cb113` |
| 1.18 | `4537edf0e88c88631215beb1c14f1d67f5a5d6d021c982c0af549b7b27856e5f` | `a43c61223ddedbdd8ede4d2daf6a750a58cbba324b30c025429ee1030cfc0d78` | `68fc9b15eb31690377afe92acfc783c86945fedec5739d28faacd96da4f9e0e7` | `10bd2dcc2b7084d0f9be24a3e54322510f1730e67fb84d35ec460c6953b3397f` |
| 1.18.1 | `7a6f3d9d86776e5a95e4722fc2c36f41165c34177f2993978dfaab410641fc81` | `52aa98450b5cfa55ac2bd2054f108e16df7fa939a3f061991b6a9adb434d8d2d` | `68fc9b15eb31690377afe92acfc783c86945fedec5739d28faacd96da4f9e0e7` | `10bd2dcc2b7084d0f9be24a3e54322510f1730e67fb84d35ec460c6953b3397f` |
| 1.18.2 | `d8057d47468c37880de38d658e95070ec3b750305b7856189997604822480946` | `aea0cb9c6fc8f7a46f0eb82b0388ad58a4659f513be6c0a2be06c0df0c1eb07a` | `db4168d531caf18f22e3fefd073365e776da4075ce01452bb9f7671d9b458782` | `32747c5b09fc184baae356e39a0088fd66c9f08f69d9e98b67c19e1de6f1bb2e` |
| 26.2 | `unobfuscated.ready.json` declares `26.2` / `unobfuscated` | `a7ad74fc712567eb87136afc1eafcfd602b4088e9869f3596331fd34faefe894` | `7ffd9c70966edc50c9cb4d9a8fe17a518e2678ff44c8026e763d0b94ac0ae51a` | — |

The accepted exact-source result is: float cosine and float coefficient in 1.17.1, 1.18, and 1.18.1; `Math.cos` and a double coefficient in 1.18.2. The corresponding accepted body ranges are 1.17.1 `LivingEntity.java:2079–2084`, 1.18/1.18.1 `:2081–2086`, and 1.18.2 `:2085–2090`. The source claim is limited to these four exact releases. It does not infer every intermediate profile from resolver behavior.

## Current code and operation boundary

The task's merged code base is `cb11257e593987c68be98a7dbda8bbea7d5e77fd` (`main` before this review branch's merge). I independently hashed the current files; each matches the reconciliation's code identities:

| File | SHA-256 |
|---|---|
| `src/main/java/me/wolfii/legacyparkourcompat/mixin/LivingEntityMixin.java` | `432da05ad4773b5cf69bcc37d01490a01137527b84e8c755332c2668aa022992` |
| `src/main/java/me/wolfii/legacyparkourcompat/mechanic/hook/ElytraLiftForceBehavior.java` | `58db6456506f2bf5a84f02ec151fbec63eea99e404c9e64fe40113e953d4fd7c` |
| `src/main/java/me/wolfii/legacyparkourcompat/change/v1_18/ElytraLiftForce.java` | `8ef076878f3d08a5b8dd1c0de0f0368d7e86f6ec6443d3fec0827a3ed2cfce30` |
| `src/main/java/me/wolfii/legacyparkourcompat/change/v1_18_2/ElytraLiftForce.java` | `a55b01377e8164807d355b927cee6e717c7e742bc0620cce86926f10e1fab33e` |
| `src/main/java/me/wolfii/legacyparkourcompat/change/common/LegacyTrig.java` | `0b1960de0528defdb54e13bb0a8aea0af6df55fae422fe6be07849ad4e397a9e` |
| `src/main/java/me/wolfii/legacyparkourcompat/change/MovementChangeCatalog.java` | `aac3ebc7eb154082c6e053bf3e9a4882efed63a63c849d992a5dfb0cf19b30b0` |
| `src/main/java/me/wolfii/legacyparkourcompat/impl/ChangeResolver.java` | `57f3b806f65b48ad0abb2612cdc67bf84c94d70c3a4a8ea230fd7d6f962ab763` |
| `src/main/java/me/wolfii/legacyparkourcompat/api/ParkourVersion.java` | `955e535e08ba4c4896b44601cd9b40714f7c15354b7321047a281ea4af47960e` |

The 26.2 source places `Mth.square(Math.cos(leanAngle))` at the beginning of the `liftForce` coefficient in `LivingEntity#updateFallFlyingMovement(Vec3)` (line 2608); that value feeds the immediately following lift/pull calculations. The redirect targets `updateFallFlyingMovement(Vec3)Vec3` and `Mth.square(D)D`, ordinal 0. Its argument is the current double cosine, and no earlier square in that method competes for the ordinal. The bridge computes the native square as fallback, returns it directly for non-player entities, and dispatches the historical hook only for a `Player`. The hook supplies the selected profile and the independently resolved look vector.

The historical implementations reproduce the accepted coefficient operations:

- `V1_18` computes float pitch radians, uses the same float lookup table/index expression as `Mth.cos(float)`, and returns `(float)(cos * (cos * Math.min(1.0, look.length() / 0.4)))`. The final cast preserves the source's intermediate coefficient rounding before it widens into the 26.2 double consumer.
- `V1_18_2` computes the same float pitch radians, passes that float to `Math.cos` (widened to double), then evaluates `cos * cos * Math.min(1.0, look.length() / 0.4)` in double precision, matching the accepted 1.18.2 expression.

This is one coefficient-operation seam; the mixin does not copy the fall-flying movement loop. `CURRENT`, disabled emulation, unresolved hook lookup, and non-player movement retain the native square. `MovementRuntime.appliesTo` rejects non-players and delegates enabled state to the controller; `ChangeResolver` returns no changes for `CURRENT`.

## Catalog, resolver, and profile applicability

The one static `MovementChangeCatalog` registers `ElytraLiftForceBehavior` exactly twice: at `ParkourVersion.V1_18` and `V1_18_2`. `ParkourVersion.V1_18` groups 1.18 and 1.18.1; 1.18.2 is separate. `ChangeResolver` filters registrations whose emulation key is at or after the selected profile and chooses the nearest such key.

| Fixed selected profile | Hook selected | Review disposition |
|---|---|---|
| 1.8.x | V1_18 registration, then the `selected.olderThan(V1_9)` guard returns native behavior | Elytra is outside this era; the feature-era guard correctly avoids introducing it |
| 1.9 through 1.17.1 | V1_18 float implementation | This is the resolver result. F-002's source proof does not independently establish source fidelity for every earlier exact release in this span |
| 1.18 and 1.18.1 | V1_18 float implementation | Matches the independently accepted exact source boundary |
| 1.18.2 | V1_18_2 double implementation | Matches the independently accepted exact source boundary |
| 1.19 through 26.1.2 | No candidate for this hook; bridge preserves native value | No F-002 source-parity claim is made for these later releases |
| Native `CURRENT` / emulation disabled | No historical change resolves | Native fallback is preserved |

The table separates profile routing from source evidence. Earlier-profile source fidelity beyond the accepted 1.17.1 sample, later-profile behavior, and fixed-profile runtime parity remain outside this verdict.

## Main merge and disposition

Current `main` `cb11257e593987c68be98a7dbda8bbea7d5e77fd` was merged into the reviewer branch before this verdict. Incoming changes since the prior review base were documentation, queue, and source-preparation records; `git diff` found no changes under `src/`. The source-preparation records make exact 1.18/1.18.1 publications available, the independent boundary review accepts the F-002 1.18.2 cutover, and main records the separate F-002 artifact identity acceptance. No incoming semantic change conflicts with this bounded implementation reconciliation.

**Final finding:** the existing code resolves to the accepted operation on 1.17.1, 1.18, 1.18.1, and 1.18.2, and the 1.8 feature-era guard plus native/disabled fallbacks preserve scope. The bounded F-002 no-code disposition is accepted. This review does not validate source behavior outside those four releases, claim exact trajectory parity, or replace runtime validation. No Java files were edited; no builds, tests, clients, TAS, Gym, servers, Docker, wiki lookups, or source-owner messages were used.

Reviewer: independent implementation review. Date: 2026-10-09.
