# F-002 reconciliation: fall-flying cosine precision

## Accepted input and snapshot

- Finding: `workflows/source-campaign-2026-10-07/1.17.1--1.18.2/findings/F-002-elytra-cosine-precision.md`
- Author snapshot: `e531086eecc778432b40b2b9ef39499b8a7f0dba`; blob `75b6206ca3434ffd96d84caa1a6ff2fc6b5c4ab5`; raw SHA-256 `bb72676167340fa5e001062b3f9cb69ce36d0160b37fe834212693a4feb0b546` (recomputed from the Git blob).
- Blind source review: `2fd121e116d7185165c76aea1c3708273afc7ea1`, `workflows/source-boundary-reviews/2026-10-08-mc1171-1182-snapshot-review.md`; F-002 is accepted for the fall-flying cosine precision change only. The review does not establish a first changed release or downstream velocity magnitude.
- Pair status at handoff: partial; pair freeze and full-pair audit are pending. No runtime validation is claimed.
- Code base: `e7f89348a619cc9feb2d47aab7a87fbefbe7649d`; build target `minecraft_version=26.2`.

## Source publication checks

The ready markers at `D:/Javastuff/LegacyParkourCompat/build/movement-campaign-2026-10-07/ready/{1.17.1,1.18.2}/mojmap.ready.json` identify Mojmap sources and match the finding's source-manifest SHA-256 values. Direct hashes of each ready marker's `mojmap.sources.sha256` and `artifacts.sha256` match the marker:

| Side | Source manifest SHA-256 | Artifact manifest SHA-256 | `LivingEntity.java` SHA-256 |
|---|---|---|---|
| 1.17.1 | `93270d229acfb751bf56daf1e7be26ce3dcff26b29e94aa157de621405a3463b` | `e52c5dbae7d9663190ccc55a4f9b44a8e0615fb1fbbd8280811df43f70ec8aaa` | `33fd081aadb2b6fdc9ebf487db6da5b38c54f4b8676572790ee2203690d15e6f` |
| 1.18.2 | `aea0cb9c6fc8f7a46f0eb82b0388ad58a4659f513be6c0a2be06c0df0c1eb07a` | `a1507e4875faee892aca4c59686bbd68933db21a2274559e81eea8a32b021036` | `db4168d531caf18f22e3fefd073365e776da4075ce01452bb9f7671d9b458782` |

Both direct `LivingEntity.java` hashes match their source-manifest rows and F-002. The 1.17.1 body uses `Mth.cos` into a `float`; its canonical `Mth.cos(float)` body returns the float lookup-table entry. The 1.18.2 body uses `Math.cos` into a `double`. The cited guards and narrow `D-ELYTRA-ENTRY` dependency closure are stated in the accepted finding.

One artifact identity needs repair or provenance clarification: F-002 cites the 1.18.2 mapped jar as `60a2017dd217b23df8a5ddbebac96fccb58ff747e961974696e0018f6ef12dba`, while the matching ready publication's artifact manifest lists `60a2016dd217b23df8a5ddbebac96fccb58ff747e961974696e0018f6ef12dba` for `1.18.2/client-mojmap.jar`. The artifact-manifest file itself matches the ready marker. The source-tree identity and cited source file remain verified, but the finding's mapped-jar citation does not match that publication. Preserve the accepted immutable snapshot and record a source-owner correction/rebinding with fresh identity review before treating that jar identity as closed. No source-owner message or shared-source edit was made here.

## Existing implementation and disposition

The accepted endpoint behavior already has a player-only bridge and independent hook:

- `src/main/java/me/wolfii/legacyparkourcompat/mixin/LivingEntityMixin.java`, `legacyparkourcompat$selectElytraLiftForce`, redirects the `Mth.square(D)` call in `updateFallFlyingMovement(Vec3)`, resolves `ElytraLiftForceBehavior` for players, and returns the vanilla square for non-players or when no change resolves.
- `src/main/java/me/wolfii/legacyparkourcompat/mechanic/hook/ElytraLiftForceBehavior.java` defines the independent fall-flying lift coefficient operation.
- `src/main/java/me/wolfii/legacyparkourcompat/change/v1_18/ElytraLiftForce.java` uses `LegacyTrig.cos(float)` and rounds the coefficient back to `float`.
- `src/main/java/me/wolfii/legacyparkourcompat/change/v1_18_2/ElytraLiftForce.java` uses `Math.cos(float)` with a `double` coefficient.
- `src/main/java/me/wolfii/legacyparkourcompat/change/MovementChangeCatalog.java` registers the hook at `V1_18` and `V1_18_2` respectively. `ChangeResolver` selects the closest registered emulation version at or later than the selected profile.

Thus the current code represents the accepted 1.17.1 endpoint with the float-table implementation and the accepted 1.18.2 endpoint with the double implementation. `ParkourVersion.V1_18` groups 1.18 and 1.18.1, while `V1_18_2` is separate. F-002 explicitly says the first changed release is unknown in `(1.17.1, 1.18.2]`; its endpoint evidence cannot validate that intermediate split. The existing classes encode a boundary at `V1_18_2`, but this reconciliation does not elevate that code-derived boundary to source-confirmed evidence.

**Disposition: open for release-boundary reconciliation; bounded endpoint operation is already represented. No production code change is justified from this finding alone.** Do not infer the behavior for 1.18 or 1.18.1. The next source step is to verify exact Mojmap `LivingEntity#travel` bodies for the intervening release(s), with their readiness manifests and hashes, then review whether the existing `V1_18` / `V1_18_2` split matches those sources. Resolve the mapped-jar citation discrepancy as a separate immutable evidence identity correction.

## Checks and handoff

- Verified author commit, finding blob and raw SHA-256; verified the blind review's F-002 acceptance.
- Verified both endpoint readiness marker identities, source and artifact manifest hashes, paired `LivingEntity.java` hashes, and the 1.17.1 `Mth.cos` body.
- Inspected the current hook, mixin dispatch, both change classes, catalog keys, resolver direction and profile grouping. Native fallback and non-player fallback are present in the bridge.
- No Java changes, builds, tests, decompilation, game/TAS/Gym/server/Docker launches, pushes, or messages to source owners were made.
- No integration conflicts are known. Default branch was `e7f89348a619cc9feb2d47aab7a87fbefbe7649d` at inspection; `git merge --no-edit main` completed as already up to date. No incoming commits required semantic review.
- Independent implementation review and the serial campaign build remain separate follow-up steps after the boundary/evidence issue is resolved. Compilation alone will not establish runtime parity.
