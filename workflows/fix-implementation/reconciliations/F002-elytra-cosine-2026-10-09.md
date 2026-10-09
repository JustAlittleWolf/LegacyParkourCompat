# F-002 reconciliation: fall-flying cosine precision

## Accepted input and snapshot

- Finding: `workflows/source-campaign-2026-10-07/1.17.1--1.18.2/findings/F-002-elytra-cosine-precision.md`
- Author snapshot: `e531086eecc778432b40b2b9ef39499b8a7f0dba`; blob `75b6206ca3434ffd96d84caa1a6ff2fc6b5c4ab5`; raw SHA-256 `bb72676167340fa5e001062b3f9cb69ce36d0160b37fe834212693a4feb0b546` (recomputed from the Git blob).
- Blind source review: `2fd121e116d7185165c76aea1c3708273afc7ea1`, `workflows/source-boundary-reviews/2026-10-08-mc1171-1182-snapshot-review.md`; F-002 is accepted for the fall-flying cosine precision change only. The review does not establish a first changed release or downstream velocity magnitude.
- Pair status at original F-002 handoff: partial. The post-merge source run now records the broader pair as complete; this implementation reconciliation remains a separate status. No runtime validation is claimed.
- Code base: `e7f89348a619cc9feb2d47aab7a87fbefbe7649d`; build target `minecraft_version=26.2`.

## Source publication checks

The ready markers at `D:/Javastuff/LegacyParkourCompat/build/movement-campaign-2026-10-07/ready/{1.17.1,1.18.2}/mojmap.ready.json` identify Mojmap sources and match the finding's source-manifest SHA-256 values. Direct hashes of each ready marker's `mojmap.sources.sha256` and `artifacts.sha256` match the marker:

| Side | Source manifest SHA-256 | Artifact manifest SHA-256 | `LivingEntity.java` SHA-256 |
|---|---|---|---|
| 1.17.1 | `93270d229acfb751bf56daf1e7be26ce3dcff26b29e94aa157de621405a3463b` | `e52c5dbae7d9663190ccc55a4f9b44a8e0615fb1fbbd8280811df43f70ec8aaa` | `33fd081aadb2b6fdc9ebf487db6da5b38c54f4b8676572790ee2203690d15e6f` |
| 1.18.2 | `aea0cb9c6fc8f7a46f0eb82b0388ad58a4659f513be6c0a2be06c0df0c1eb07a` | `a1507e4875faee892aca4c59686bbd68933db21a2274559e81eea8a32b021036` | `db4168d531caf18f22e3fefd073365e776da4075ce01452bb9f7671d9b458782` |

Both direct `LivingEntity.java` hashes match their source-manifest rows and F-002. The 1.17.1 body uses `Mth.cos` into a `float`; its canonical `Mth.cos(float)` body returns the float lookup-table entry. The 1.18.2 body uses `Math.cos` into a `double`. The cited guards and narrow `D-ELYTRA-ENTRY` dependency closure are stated in the accepted finding.

The original snapshot's 1.18.2 mapped-jar identity discrepancy is now resolved by the accepted correction snapshot. F-002's original bytes remain unchanged; the corrected copy changes only `60a2017dd217b23df8a5ddbebac96fccb58ff747e961974696e0018f6ef12dba` to `60a2016dd217b23df8a5ddbebac96fccb58ff747e961974696e0018f6ef12dba`, matching the canonical artifact manifest and actual jar. The independent identity acceptance is recorded below.

## Existing implementation and disposition

The accepted endpoint behavior already has a player-only bridge and independent hook:

- `src/main/java/me/wolfii/legacyparkourcompat/mixin/LivingEntityMixin.java`, `legacyparkourcompat$selectElytraLiftForce`, redirects the `Mth.square(D)` call in `updateFallFlyingMovement(Vec3)`, resolves `ElytraLiftForceBehavior` for players, and returns the vanilla square for non-players or when no change resolves.
- `src/main/java/me/wolfii/legacyparkourcompat/mechanic/hook/ElytraLiftForceBehavior.java` defines the independent fall-flying lift coefficient operation.
- `src/main/java/me/wolfii/legacyparkourcompat/change/v1_18/ElytraLiftForce.java` uses `LegacyTrig.cos(float)` and rounds the coefficient back to `float`.
- `src/main/java/me/wolfii/legacyparkourcompat/change/v1_18_2/ElytraLiftForce.java` uses `Math.cos(float)` with a `double` coefficient.
- `src/main/java/me/wolfii/legacyparkourcompat/change/MovementChangeCatalog.java` registers the hook at `V1_18` and `V1_18_2` respectively. `ChangeResolver` selects the closest registered emulation version at or later than the selected profile.

At the initial checkpoint, the code's 1.18/1.18.2 keys matched the accepted endpoints, but the intermediate boundary and mapped-jar citation were open. The independent source-boundary and artifact-identity reviews below now resolve those two questions without changing Java.

## Accepted boundary and profile applicability

### Exact source and review identities

- Boundary evidence author snapshot: commit `14443f43fbcc30152916ac75796846d796cf0268`, `workflows/source-boundary-reviews/1171-1182-F001-F002-boundary-evidence-2026-10-09.md`; blob `8892a7533358c6e7053527c24dacd0a2acce5ae9`; raw SHA-256 `66f5659dd17ff8441d10a02d95920ecd5d4e190c53c2c0893eac79efec36c6cb`.
- Independent boundary review: commit `163ea4881b327a9316a266601705463784566e2c`, `workflows/source-boundary-reviews/2026-10-09-1171-1182-boundaries-independent-review.md`; blob `b505e009ad5726b7d7fd18c7bf35a38495003e28`; raw SHA-256 `bebbb4dc67323ece9615444d5d32ec56eba480683abb6a30891d18481b5ef124`.
- The reviewer accepts the bounded F-002 boundary: the float cosine and float coefficient remain in 1.17.1, 1.18 and 1.18.1; 1.18.2 first uses `Math.cos` and a double coefficient among those exact releases. This does not claim a resulting velocity/trajectory delta or complete implementation/runtime validation.
- The boundary reviewer noted that the author report's 1.18 ready-marker hash omitted its final `f`; the correct live marker identity is `4537edf0e88c88631215beb1c14f1d67f5a5d6d021c982c0af549b7b27856e5`. The reviewer independently checked the exact markers, source/artifact manifests, and cited source files. The transcription issue does not alter the accepted boundary.
- Corrected artifact identity acceptance: commit `3026b37ea424037ea6b948bae52c2c9888910be1`, `workflows/source-boundary-reviews/2026-10-09-F002-artifact-identity-review.md`; blob `7952d5702c7dbeec85e1a200dcc221519ba5355e`; raw SHA-256 `8f89b2b12cdf184538cb9779b5ad049a01107134fa5019625bd08de22b23a1d4`. It accepts only the exact corrected snapshot identity and byte lineage; the original F-002 behavior acceptance and review history remain intact.
- Corrected F-002 snapshot accepted by that identity review: commit `2f3425601753d8b153e795a973a147e6ce0cb0b5`, path `workflows/source-campaign-2026-10-07/1.17.1--1.18.2/findings/F-002-elytra-cosine-precision-artifact-correction-2026-10-09.md`; blob `dbce0be4ade1fdd84acd9867be201f9f630609cc`; raw SHA-256 `ccc39601c087a9df5d60e9506e7eaa1831adb7336b89c346bbdeecfcddb71f01`.

The accepted boundary review checked these exact source bodies and their manifests:

| Release | Source manifest SHA-256 | `LivingEntity.java` SHA-256 | `Mth.java` SHA-256 |
|---|---|---|---|
| 1.17.1 | `93270d229acfb751bf56daf1e7be26ce3dcff26b29e94aa157de621405a3463b` | `33fd081aadb2b6fdc9ebf487db6da5b38c54f4b8676572790ee2203690d15e6f` | `24515c4549e01e985017227dccf7159166a675b232be9135d5f896022a9cb113` |
| 1.18 | `a43c61223ddedbdd8ede4d2daf6a750a58cbba324b30c025429ee1030cfc0d78` | `68fc9b15eb31690377afe92acfc783c86945fedec5739d28faacd96da4f9e0e7` | `10bd2dcc2b7084d0f9be24a3e54322510f1730e67fb84d35ec460c6953b3397f` |
| 1.18.1 | `52aa98450b5cfa55ac2bd2054f108e16df7fa939a3f061991b6a9adb434d8d2d` | `68fc9b15eb31690377afe92acfc783c86945fedec5739d28faacd96da4f9e0e7` | `10bd2dcc2b7084d0f9be24a3e54322510f1730e67fb84d35ec460c6953b3397f` |
| 1.18.2 | `aea0cb9c6fc8f7a46f0eb82b0388ad58a4659f513be6c0a2be06c0df0c1eb07a` | `db4168d531caf18f22e3fefd073365e776da4075ce01452bb9f7671d9b458782` | `32747c5b09fc184baae356e39a0088fd66c9f08f69d9e98b67c19e1de6f1bb2e` |

Direct reads confirmed the four `LivingEntity.java` and `Mth.java` hashes against their ready source-manifest rows. The four `Mth.cos(float)` bodies use the same float table/index expression; their table initialization matches `LegacyTrig`'s `SIN` initialization expression. The accepted review's F-002 ranges are 1.17.1 `LivingEntity.java:2079-2084`, 1.18/1.18.1 `:2081-2086`, and 1.18.2 `:2085-2090`.

### Current bridge and arithmetic

The checked current target is 26.2, `unobfuscated`; its ready source manifest SHA-256 is `a7ad74fc712567eb87136afc1eafcfd602b4088e9869f3596331fd34faefe894`. `LivingEntity.java` SHA-256 is `7ffd9c70966edc50c9cb4d9a8fe17a518e2678ff44c8026e763d0b94ac0ae51a`. Its `updateFallFlyingMovement(Vec3)` contains one `Mth.square(Math.cos(leanAngle))` call at line 2608; the resulting `liftForce` is consumed immediately by the subsequent lift/pull operations.

The code identities inspected at the merged task base are:

| File | SHA-256 |
|---|---|
| `mixin/LivingEntityMixin.java` | `432da05ad4773b5cf69bcc37d01490a01137527b84e8c755332c2668aa022992` |
| `mechanic/hook/ElytraLiftForceBehavior.java` | `58db6456506f2bf5a84f02ec151fbec63eea99e404c9e64fe40113e953d4fd7c` |
| `change/v1_18/ElytraLiftForce.java` | `8ef076878f3d08a5b8dd1c0de0f0368d7e86f6ec6443d3fec0827a3ed2cfce30` |
| `change/v1_18_2/ElytraLiftForce.java` | `a55b01377e8164807d355b927cee6e717c7e742bc0620cce86926f10e1fab33e` |
| `change/common/LegacyTrig.java` | `0b1960de0528defdb54e13bb0a8aea0af6df55fae422fe6be07849ad4e397a9e` |
| `change/MovementChangeCatalog.java` | `aac3ebc7eb154082c6e053bf3e9a4882efed63a63c849d992a5dfb0cf19b30b0` |
| `impl/ChangeResolver.java` | `57f3b806f65b48ad0abb2612cdc67bf84c94d70c3a4a8ea230fd7d6f962ab763` |
| `api/ParkourVersion.java` | `955e535e08ba4c4896b44601cd9b40714f7c15354b7321047a281ea4af47960e` |

The existing `LivingEntityMixin` redirect targets that `Mth.square(D)D` invocation, ordinal 0, in `updateFallFlyingMovement(Vec3)Vec3`. It reconstructs the native square for fallback, then dispatches only for a `Player`; no second `Mth.square` precedes the lift consumers. For a player with a resolved hook it passes the selected version and look vector, with the separate `FallFlyingLookBehavior` hook applied independently. A non-player, disabled emulation, unresolved change, or `CURRENT` profile preserves the native square.

The registered `V1_18` implementation narrows pitch to float radians, uses the exact table lookup, and returns `(float)(cos * (cos * Math.min(1.0, look.length() / 0.4)))`. This matches the source coefficient's float narrowing and operation order. The `V1_18_2` implementation widens the float pitch argument into `Math.cos`, retains double precision, and returns `cos * cos * Math.min(1.0, look.length() / 0.4)`, matching the 1.18.2 source order. These replace the whole coefficient at the square call so the later modern body consumes the historical coefficient once.

### Resolver coverage across profile groups

`ChangeResolver` selects the registered change with the smallest `emulates` ordinal that is at or later than the selected profile; `CURRENT` resolves to an empty map. The catalog has exactly two registrations for `ElytraLiftForceBehavior`, at `V1_18` and `V1_18_2`. `ParkourVersion.V1_18` groups 1.18 and 1.18.1.

| Selected profile group | Resolver result for this hook | F-002 applicability |
|---|---|---|
| 1.8.x | `V1_18` change is found, then its pre-1.9 guard returns the native square | Elytra was not present in this era; F-002 does not claim applicability |
| 1.9 through 1.17.1 | `V1_18` float-table change | Resolver mapping is verified; F-002's newly accepted exact boundary evidence starts at 1.17.1 and does not independently re-audit earlier release sources |
| 1.18 and 1.18.1 | `V1_18` float-table change | Matches both exact source bodies in the accepted boundary review |
| 1.18.2 | `V1_18_2` double-cosine change | Matches the exact source body in the accepted boundary review |
| 1.19 through 26.1.2 | No candidate; the mixin uses the 26.2 native square | Resolver/fallback behavior is verified; F-002 does not claim historical parity for later releases |
| Native `CURRENT` / emulation disabled | No candidate or lookup returns empty; native square is preserved | Native fallback verified |

**Final disposition: the accepted F-002 boundary is represented by the existing registrations and changes; no F-002-specific Java change is needed.** The resolver table above verifies profile selection, while behavioral source evidence remains bounded to the reviewed 1.17.1/1.18/1.18.1/1.18.2 releases. This is an implementation reconciliation, not the separate final technical review or runtime parity claim.

## Checks and handoff

- Verified the original and corrected F-002 snapshot identities, original behavioral acceptance, accepted boundary review and accepted artifact-identity correction review.
- Verified the four historical `LivingEntity.java` and `Mth.java` source hashes, and the current 26.2 target source hash against their published manifests.
- Inspected the exact current redirect descriptor/order, coefficient arithmetic, hook, static catalog registrations, resolver direction and native/player fallbacks. No source-bound defect was found in this bounded F-002 operation.
- No Java changes, builds, tests, decompilation, game/TAS/Gym/server/Docker launches, pushes, or source-owner messages were made.
- Merged `main` at `cb11257e593987c68be98a7dbda8bbea7d5e77fd` in merge commit `23cf4e1f9609a347e26962cb5ade0356180f7aea`. Incoming source-run completion, 1.18/1.18.1 preparation, F-002 identity-review, orchestration and queue updates were inspected; they do not conflict with this reconciliation. Their source and identity verdicts remain distinct from implementation and runtime statuses.
- Independent final technical review and runtime parity remain separate statuses. Compilation alone will not establish runtime parity.
