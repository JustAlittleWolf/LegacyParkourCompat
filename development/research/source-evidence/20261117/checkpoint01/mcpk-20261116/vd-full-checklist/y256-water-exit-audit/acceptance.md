# Independent exact-source acceptance: water exit at Y=256

Verdict: **ACCEPT** for the individual source finding `1.15.2--1.16` (Mojmap). The compared stable endpoints establish that the stale water-height jump path exists in 1.15.2 and is removed in 1.16.0. This verdict does not freeze the full release pair or establish runtime parity.

## Immutable reviewed inputs

- Candidate: `build/mcpk-20261116/vd-full-checklist/water-y256/water-exit-y256-jump-state.md`
- Candidate SHA-256 (file bytes): `4883369f64da3560c9d097d2689f81f798bfd3a6c62bf216559d5849eaef1be4`
- Adjacent frozen-facts SHA-256 (file bytes): `0cb9a6c2056fc08bc17ef02da211c8a1cbf7e6c76d80e6b79ae4f55ffcbdd063`
- Review basis: exact decompiled Mojmap sources in `decompiled_minecraft/`; status markers, provenance, source manifests, and cited Java-file bytes were checked read-only.

## Source trail and evidence identity

Every listed tree's ready marker reports `status: ready`; the marker's `sourceManifestSha256` equals the SHA-256 of its adjacent `*.sources.sha256`, and all cited Java-file bytes match their corresponding manifest rows.

| Release / namespace | Source-manifest SHA-256 | Relevant source-file SHA-256 |
|---|---|---|
| 1.15.2 / Mojmap | `cb7fd93f8730d8b2813857744f8572060535c7de44fcee86222acd1ceb6f43e7` | `Entity.java` `191b3ad3e7348c9bac1e703fff896706d23a751bf15162aacf676f5f97c0a10e`; `LivingEntity.java` `46d243bb7e51f7b54404aa1d7d6e6b827682d0f5925d02847c4193306c4d5e54`; `LevelReader.java` `d09aa8eaaa7f8ca8703091facb4d7e1a26c9f4bea8b99c5c8956682875241d37`; `LocalPlayer.java` `3a9019bd7b860e251c23fd8d0cd70b7f5b38566d34470c4e29b1014ef689ccbd`; `Player.java` `1ba2724c22163862b8f7fdfdea5a04a66e4db26a119d7e5360ba024724a34793` |
| 1.16 / Mojmap | `e3c23e94ac985c25dec0c768aed17b443ba4823662cf1e152235c99756ae5d4a` | `Entity.java` `27e3bb01b5d03c09161891e717c16477899768333082e81ebfaac4e525616576`; `LivingEntity.java` `b6ea2b9b037b79fc3ea6e1f6bcd2c4c1d89d4a96315968a8f247641f6c335f26`; `LevelReader.java` `d301b992d71b05306a3139e69f3f1529190f82404791ec60f9a693188d29577a`; `LocalPlayer.java` `ee20523adfdb82f3753fa43312af170f34c4f4aa78570631c61bc8c0820dfb8b`; `Player.java` `a849960d47bf00a2cddfb6636c78c518532a11967ba08829f19c5ebced08681e` |
| 1.16.1 / Mojmap | `76fdd121070c2012f48cbdf5cf0c78490116afc94117c30b0858eedcd6ab9754` | `Entity.java` `27e3bb01b5d03c09161891e717c16477899768333082e81ebfaac4e525616576`; `LivingEntity.java` `b6ea2b9b037b79fc3ea6e1f6bcd2c4c1d89d4a96315968a8f247641f6c335f26`; `LevelReader.java` `d301b992d71b05306a3139e69f3f1529190f82404791ec60f9a693188d29577a` |
| 1.16.2 / Mojmap | `10d312ca29ee48e25f4727adafb9fcfbd741dcebe81b343d3dcffa071952a7a7` | `Entity.java` `f9a9a073fe3105a0aa53d0f21ec72e59084e8d21a14c1cd3be75703865ee2666`; `LivingEntity.java` `b5d8a1a3c80f85d5d545b5a777e9e2a915dc002a7e31b5f5ad12bf7e285d7a88`; `LevelReader.java` `d301b992d71b05306a3139e69f3f1529190f82404791ec60f9a693188d29577a` |
| 1.16.5 / Mojmap | `9499f2611d0e6dde37cb635f1a28a591416382d6bc188b37d5f99e2b2a20823b` | `Entity.java` `f9a9a073fe3105a0aa53d0f21ec72e59084e8d21a14c1cd3be75703865ee2666`; `LivingEntity.java` `b5d8a1a3c80f85d5d545b5a777e9e2a915dc002a7e31b5f5ad12bf7e285d7a88`; `LevelReader.java` `d301b992d71b05306a3139e69f3f1529190f82404791ec60f9a693188d29577a` |


Ready-marker and provenance file SHA-256 pairs (in the same release order as the table):

- 1.15.2: ready `64a0e40b784d525ce50937c63e465fc4f564efb1f7af17f2bac3c77a36907be0`; provenance `d4802d35ee2927a44d753871f184c3255c060eb94457a5c65a8bc121a087954e`.
- 1.16: ready `5b6cb24f95e83b856c53f646d6477f2e46dae2276737dee56dd07c5bd745e4a2`; provenance `371d50521a183f4a3f42fac2d18021f228e0a4e6b2e8a9838c2805391709f2a9`.
- 1.16.1: ready `c19d707f7d6622733b5189599ff77aa7999cc11a3d900f29d150f9fab203ffcb`; provenance `a6ff065188a400ec891980cdf2e6c0c238bf5e05168a2f69b29c6cecc2214b98`.
- 1.16.2: ready `e5f69bab646b6143b0660a97fade59595ff2e924d36567b9cd285c8babd31214`; provenance `a9b166406064e877751542fe7df27acc32be3843f6fdc2280dac32ce0b54207c`.
- 1.16.5: ready `e9dd4397c6ade5f68d27f50baf285086dc367b56d2119b03a116cf3cf3b23fee`; provenance `b71df61eb775134119881915198f87c4337bae409e1f0de8d8d6c007e062902d`.

The provenance JSON release IDs and namespaces match their ready markers and source directories. The 1.16 marker's `provenanceSha256` matches its provenance bytes; the other four markers omit that field, so their provenance file hashes are recorded above without claiming an explicit marker-to-provenance digest link. In all five trees, the ready marker's source-manifest digest matches the adjacent manifest bytes.

## Reviewed source locations

- 1.15.2: `Entity.java` 340-364 (`tick`/`baseTick` and `updateWaterState` call), 943-974 (`updateInWaterState`), 2593-2655 (`checkAndHandleWater` and terminal `waterHeight` write); `LevelReader.java` 157-176 (`hasChunksAt` guard); `LivingEntity.java` 2024-2104 (tick order), 2207-2279 (`aiStep`, input-stage dispatch and cached-height jump branch), 1823-1825 (`jumpInLiquid` adds `0.04F`); `LocalPlayer.java` 179-199 (`tick`), 490-496 (`isEffectiveAi`), 612 (`jumping` input copy), 625-778 (`aiStep`).
- 1.16.0: `Entity.java` 346-373 (`tick`/`baseTick` and fluid refresh call), 910-916 (`isInWater`), 947-979 (clear-before-scan and `wasTouchingWater` refresh), 2633-2701 (failed-scan return and fluid-height write/getter); `LevelReader.java` 168-188 (`hasChunksAt` guard); `LivingEntity.java` 2122-2169 (tick order), 2366-2446 (`aiStep` and liquid-jump gate), 1900-1904 (`jumpInLiquid`); `LocalPlayer.java` 183-203 (`tick`), 519-523 (`isEffectiveAi`), 634-648 (`jumping` input copy), 652-813 (`aiStep`).
- 1.16.1, 1.16.2, and 1.16.5: verified the `Entity.java`, `LivingEntity.java`, and `LevelReader.java` file bytes against their own release manifests; they match the corresponding 1.16.0 files for .1 and each other for .2/.5. The shared byte identities confirm the reset, height-gate, and Y guard bodies at those sampled releases.
## Acceptance reasoning

In 1.15.2, `Entity.baseTick` calls `updateWaterState` before the living tick's `aiStep`; the water scan reaches `checkAndHandleWater`. That method derives the scan bounds from the deflated player bounding box, passes `floor(minY)` as the second argument to `LevelReader.hasChunksAt`, and returns `false` immediately if the guard rejects the bounds. `LevelReader.hasChunksAt` checks `j < 256`; for this call, `j` is `floor(minY)`. Once the player's feet reach `floor(minY) >= 256`, the method exits before the later `this.waterHeight = d` assignment, preserving the last positive cached value.

The player path is reachable locally: `LocalPlayer.isEffectiveAi()` returns true, `LocalPlayer.serverAiStep` copies active jump input into `jumping`, and the subsequent `LivingEntity.aiStep` jump branch reads positive `waterHeight` without an `isInWater()` predicate. While airborne, that stale positive height selects `jumpInLiquid(FluidTags.WATER)`, whose inherited `LivingEntity` implementation adds `0.04F` to vertical delta movement. Because the failed scan does not refresh the cached value, continued jump input repeats the impulse on subsequent ticks. This is a local-client player movement response, not server-side historical physics.

In 1.16.0, `Entity.updateInWaterStateAndDoFluidPushing` clears `fluidHeight` before the water scan. If `updateFluidHeightAndDoFluidPushing` is rejected by the same max-Y guard, it returns before writing a height; the map therefore retains no water entry, so `getFluidHeight(WATER)` reads zero. The failed update also sets `wasTouchingWater` false. `LivingEntity.aiStep` now defines the water-jump condition as `isInWater() && k > 0.0` before reaching `jumpInLiquid(WATER)`. With the failed scan, that conjunction is false. An airborne player therefore receives neither the stale water impulse nor an on-ground jump from the shallow-water branch.

The 1.16.1 source bytes for `Entity` and `LivingEntity` exactly match 1.16.0; 1.16.2 and 1.16.5 match each other and retain clear-before-scan plus the water-contact/depth gate. These sampled later patches support persistence of the fix at those exact releases. The compared stable endpoints support the fix at 1.16.0; no claim is made about snapshots or an uninspected source history before that release.

No unresolved source gate remains for this individual claim. The 26.2 source was not needed to assess the historical stale-cache path or its 1.16.0 removal. No implementation, tests, builds, or launches were performed.


