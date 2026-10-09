# Independent review — F-001 sprint collision and F-002 elytra cosine reconciliations

**Decision: ACCEPT both reconciliations for their bounded 1.17.1 / 1.18.2 endpoint correspondence, with routed evidence and boundary follow-ups.** Neither report establishes the first changed release, validates the 1.18 / 1.18.1 profile split, closes the 1.17.1–1.18.2 discovery pair, nor establishes runtime parity. No production code change is justified by these accepted endpoint findings.

## Review identities and scope

- Main baseline: `e7f89348a619cc9feb2d47aab7a87fbefbe7649d` (`main`). This review branch/worktree starts at that exact commit.
- F-001 reconciliation: commit `bba3c42691c2bb1527ce98dc5ff942e35bb31690`, path `workflows/fix-implementation/reconciliations/F001-sprint-collision-2026-10-09.md`, blob `e1ed834519d5a50841fc1af361957da998716243`, raw SHA-256 `c0a6dff4225befb68c0f394aed9198e01c7fde50db00b6b8baa75d027f3ea367`.
- F-002 reconciliation: commit `9be6ac928841ec9d9552ae4e533561623b49866b`, path `workflows/fix-implementation/reconciliations/F002-elytra-cosine-2026-10-09.md`, blob `dd183718a572208293e1b9da8bc8218c086b66ea`, raw SHA-256 `efdc4ee931184b09c2382b74939080836cd6fb1e9259671c02f70e3c0ce659d1`.
- Candidate commit diffs against the base each add exactly the named reconciliation Markdown file; neither changes Java, mixins, resources, or build files.
- Immutable source author snapshot: `e531086eecc778432b40b2b9ef39499b8a7f0dba`. The F-001 finding blob is `9bc53d27195a6f89ddb19711ef40558d3c7bdcc8`, raw SHA-256 `77957564feaa103b7817055c71c0a9d9e31d7af4bacae5432b4b18d82c99e2fd`. F-002 finding blob is `75b6206ca3434ffd96d84caa1a6ff2fc6b5c4ab5`, raw SHA-256 `bb72676167340fa5e001062b3f9cb69ce36d0160b37fe834212693a4feb0b546`.
- Blind source review: `2fd121e116d7185165c76aea1c3708273afc7ea1`, `workflows/source-boundary-reviews/2026-10-08-mc1171-1182-snapshot-review.md`, blob `871ad5bccde2194ccc733ed8fef92472a1c96c2a`. It accepts F-001's horizontal sprint gate and F-002's fall-flying cosine precision only.

## Canonical publication checks

Readiness markers and manifests under `D:/Javastuff/LegacyParkourCompat/build/movement-campaign-2026-10-07/ready/` were read only. The marker bytes themselves hash to A `c7180dce94d7b57b53a964445b0c788c71f23ba3ba40eea593c11cc939185e9b` and B `d8057d47468c37880de38d658e95070ec3b750305b7856189997604822480946`. Both markers declare `ready`, Mojmap, and the exact version. Recomputed raw manifest hashes match each marker:

| Side | Source manifest SHA-256 | Artifact manifest SHA-256 | Direct source checks |
|---|---|---|---|
| A, 1.17.1 | `93270d229acfb751bf56daf1e7be26ce3dcff26b29e94aa157de621405a3463b` | `e52c5dbae7d9663190ccc55a4f9b44a8e0615fb1fbbd8280811df43f70ec8aaa` | `LocalPlayer.java` `c9a91cb6cb57806bc8d22e5bfe2d97daaf21d5d2a48f34a6e2c53164c61c5812`; `Entity.java` `ab28e1fba924771ec048140dfd293ee5a46a7dfe81f71a1a0b1aecc1927232de`; `LivingEntity.java` `33fd081aadb2b6fdc9ebf487db6da5b38c54f4b8676572790ee2203690d15e6f` |
| B, 1.18.2 | `aea0cb9c6fc8f7a46f0eb82b0388ad58a4659f513be6c0a2be06c0df0c1eb07a` | `a1507e4875faee892aca4c59686bbd68933db21a2274559e81eea8a32b021036` | `LocalPlayer.java` `99c2d18bcd23243afb8f95c5bafb21fb0be7ea04aacbb14fcf7be7ced2c9c095`; `Entity.java` `2228fdaca5793171cbd94038306d571a6ada78ca96f5734efb4cada5b744c10a`; `LivingEntity.java` `db4168d531caf18f22e3fefd073365e776da4075ce01452bb9f7671d9b458782` |

Directly rehashed finding files and cited paired source files agree with the accepted blobs/raw hashes and their source-manifest rows. Ready directories for 1.18 and 1.18.1 are absent. The only boundary evidence available here is therefore the accepted endpoint comparison `(1.17.1, 1.18.2]`.

## F-001 — bounded sprint gate: ACCEPT

The accepted sources show the relevant difference in `LocalPlayer#aiStep`: A stops sprinting on any horizontal collision, while B suppresses that term when `minorHorizontalCollision` is true. B writes the minor flag after `Entity#move` resolves movement; the local-player classifier uses the rotated input and resolved vector. The finding and blind review bound reachability and dependency closure to a stone-wall, two-axis collision case. Their evidence supports that endpoint gate difference only.

On the reviewed 26.2 base, `LocalPlayer#shouldStopRunSprinting()Z` still supplies a narrow point-of-use hook: the return value is consumed immediately by `aiStep` to decide whether to clear sprint. `LocalPlayerMixin` injects at that method's return and dispatches `SprintCollisionBehavior` through `MovementRuntime`. The V1_17_1 change returns `vanilla || player.horizontalCollision`, restoring the accepted older collision term while preserving other native stop predicates. `CURRENT`, absent resolution, and non-player movement keep the native result/path. The single static catalog registers the interface at `V1_17_1`; `ChangeResolver` picks the closest registration whose emulated version is at or after the selected profile. No duplicate loop or unrelated hook is introduced.

**Disposition:** the code already represents the accepted 1.17.1 endpoint claim. The report correctly leaves its V1_17_1/V1_18 registration boundary unverified. Do not move this registration or claim a newly verified release boundary from the endpoint pair.

## F-002 — bounded cosine precision: ACCEPT, identity blocker routed

The accepted `LivingEntity#travel` evidence shows A stores `Mth.cos(leanAngle)` in a `float`, then assigns the float-rounded lift coefficient; B uses `Math.cos(leanAngle)` and retains a `double` coefficient. Both branches are guarded by fall-flying. This accepts the operation's arithmetic change; it does not establish downstream velocity magnitude.

The current 26.2 source has `LivingEntity#updateFallFlyingMovement(Vec3)` with `Mth.square(Math.cos(leanAngle))` feeding the coefficient at the first matching `Mth.square(D)D` invocation. `LivingEntityMixin` redirects that invocation and dispatches only for `Player`; non-players return the original squared value. When no historical behavior resolves, it also returns the vanilla value. `ElytraLiftForceBehavior` is an independent hook; the V1_18 implementation uses the float lookup and float-rounded coefficient, and the V1_18_2 implementation uses the double cosine/coefficient. Both reproduce the corresponding accepted endpoint arithmetic and retain the common `min(1.0, lookAngle.length()/0.4)` term. The catalog registers each interface separately at V1_18 and V1_18_2. Resolver direction makes the older endpoint resolve the float change and 1.18.2 resolve the double change. This confirms endpoint correspondence only; it cannot prove the V1_18 key for 1.18/1.18.1.

**Artifact identity blocker:** the accepted finding cites B mapped jar `60a2017dd217b23df8a5ddbebac96fccb58ff747e961974696e0018f6ef12dba`; the verified B artifact-manifest row for `1.18.2/client-mojmap.jar` is `60a2016dd217b23df8a5ddbebac96fccb58ff747e961974696e0018f6ef12dba`. The source manifest, direct `LivingEntity.java` hash, and ready marker remain consistent. Preserve the accepted finding bytes; route an identity correction/rebinding through source preparation and obtain independent identity review before treating the mapped-jar citation as closed. No source owner was contacted and no shared artifact was changed.

**Disposition:** the existing code matches the two accepted endpoint behaviors, but the report correctly keeps the 1.18/1.18.1 boundary open. Its mapped-jar identity discrepancy is a provenance blocker to close, not grounds to infer or repair a production version boundary from this comparison.

## Next actions and review limits

1. The source-preparation owner publishes or verifies canonical 1.18 and 1.18.1 Mojmap sources and readiness manifests. A separate boundary review then checks which profile key the existing V1_18/V1_18_2 split should use.
2. Resolve F-002's mapped-jar digest against its immutable publication identity with a superseding source snapshot/binding and independent identity review; preserve the original snapshot and this endpoint verdict.
3. Keep the 1.17.1–1.18.2 pair partial until source discovery's full-pair freeze/audit is complete. Runtime parity remains for separately authorized validation.

No production files were changed. No source worker was contacted; no shared sources were written or decompiled; no builds, tests, clients, TAS, Gym, servers, Docker, pushes, or additional chats were run or created. Review is static and limited to the two exact candidates and accepted endpoint evidence.

Reviewer: independent implementation/reconciliation review. Date: 2026-10-09.
