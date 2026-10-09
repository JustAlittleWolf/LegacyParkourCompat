# F-012 sprint air-control release-boundary evidence

**Review state:** prepared for independent source-only review; not independently accepted in this memo.

## Claim under review

The accepted F-012 snapshot compares 1.17.1 with 1.18.2 and leaves the first changed release unknown within that interval. This memo compares the exact published Mojmap `Player#aiStep` sources for 1.17.1, 1.18, 1.18.1 and 1.18.2. Among those four releases, the coefficient expression changes first at 1.18.2. This is a bounded version-boundary statement, not a claim about unsampled releases or full movement parity.

## Published inputs and paired source identities

All inputs are the ready Mojmap publication at `D:\Javastuff\LegacyParkourCompat\build\movement-campaign-2026-10-07\ready\<version>\` (`mojmap/` source root). Each ready marker reports `status: ready`; marker, manifest and source hashes below were rechecked against those files.

| Release | Ready marker SHA-256 | Source manifest SHA-256 | Artifact manifest SHA-256 | `Player.java` SHA-256 | `LivingEntity.java` SHA-256 |
|---|---|---|---|---|---|
| 1.17.1 | `c7180dce94d7b57b53a964445b0c788c71f23ba3ba40eea593c11cc939185e9b` | `93270d229acfb751bf56daf1e7be26ce3dcff26b29e94aa157de621405a3463b` | `e52c5dbae7d9663190ccc55a4f9b44a8e0615fb1fbbd8280811df43f70ec8aaa` | `724bb298499dabe489dffd5ce7ec81c9773619a8d2c70044911eae4c9e8ff481` | `33fd081aadb2b6fdc9ebf487db6da5b38c54f4b8676572790ee2203690d15e6f` |
| 1.18 | `4537edf0e88c88631215beb1c14f1d67f5a5d6d021c982c0af549b7b27856e5f` | `a43c61223ddedbdd8ede4d2daf6a750a58cbba324b30c025429ee1030cfc0d78` | `7ca2b4da88c215e52924d277b257c4631f9664f5def2fb2b7ef25479ec330234` | `1fb9dfb9f700323d77f4196dbc3618d01a65e56438b9cce3ee38fa0b258bd7e2` | `68fc9b15eb31690377afe92acfc783c86945fedec5739d28faacd96da4f9e0e7` |
| 1.18.1 | `7a6f3d9d86776e5a95e4722fc2c36f41165c34177f2993978dfaab410641fc81` | `52aa98450b5cfa55ac2bd2054f108e16df7fa939a3f061991b6a9adb434d8d2d` | `b8237a01cdebe7d784caae113886f42565677ce6897ea960a49223cfec4c00ec` | `1fb9dfb9f700323d77f4196dbc3618d01a65e56438b9cce3ee38fa0b258bd7e2` | `68fc9b15eb31690377afe92acfc783c86945fedec5739d28faacd96da4f9e0e7` |
| 1.18.2 | `d8057d47468c37880de38d658e95070ec3b750305b7856189997604822480946` | `aea0cb9c6fc8f7a46f0eb82b0388ad58a4659f513be6c0a2be06c0df0c1eb07a` | `a1507e4875faee892aca4c59686bbd68933db21a2274559e81eea8a32b021036` | `bf639c1962ff90d69e4569b2b18f6fcf57ac46ef80b19686f0fbc1687fca744a` | `db4168d531caf18f22e3fefd073365e776da4075ce01452bb9f7671d9b458782` |

The 1.18 and 1.18.1 `Player.java` and `LivingEntity.java` bytes are identical by SHA-256. Their release-specific ready markers and manifests are distinct.

## Exact writer and consumer evidence

- **1.17.1:** `Player#aiStep()V`, lines 506–510: `super.aiStep()` precedes the reset to `0.02F`; if sprinting, the assignment is `(float)(this.flyingSpeed + 0.005999999865889549)`. The addition uses the unsuffixed double literal and then narrows to float.
- **1.18:** `Player#aiStep()V`, lines 506–510: same operation order, literal and cast as 1.17.1.
- **1.18.1:** `Player#aiStep()V`, lines 506–510: same operation order, literal and cast as 1.17.1 and 1.18.
- **1.18.2:** `Player#aiStep()V`, lines 508–512: `super.aiStep()` precedes the reset to `0.02F`; if sprinting, the update is `this.flyingSpeed += 0.006F`, a float compound assignment with no double-literal intermediate.

For all four, `LivingEntity#handleRelativeFrictionAndCalculateMovement` passes `getFrictionInfluencedSpeed(...)` to `moveRelative`; the airborne branch of `getFrictionInfluencedSpeed` returns the player’s `flyingSpeed`. In 1.17.1 these are at `LivingEntity.java` lines 2156 and 2200; 1.18/1.18.1 at lines 2158 and 2202; 1.18.2 at lines 2162 and 2206. The corresponding `LivingEntity.java` hashes are in the table. The accepted F-012 review independently traces the following-tick sprint writer to ordinary airborne, non-fluid player input and limits its claim to coefficient/consumer behavior, not trajectory.

For the fixed base `0.02F`, accepted independent evaluation gives the old expression `0.025999998673796654` (`0x3cd4fdf3`) and the new expression `0.026000000536441803` (`0x3cd4fdf4`). The exact source sequence shows no coefficient change at 1.18 or 1.18.1, then the one-ULP change at 1.18.2.

## Binding to the accepted finding

The immutable finding author snapshot is commit `e531086eecc778432b40b2b9ef39499b8a7f0dba`, path `workflows/source-campaign-2026-10-07/1.17.1--1.18.2/findings/F-012-sprint-air-control-float.md`, blob `947ca63e44ec3f8ecaa710f4168c95bf66e5402a`, raw-file SHA-256 `b089246435bb50d410fe8dbe5502fbcd2245a4aa72f6e896c1040eecb47c6bff`. Its independent blind acceptance is commit `2fd121e116d7185165c76aea1c3708273afc7ea1`, path `workflows/source-boundary-reviews/2026-10-08-mc1171-1182-snapshot-review.md`, blob `871ad5bccde2194ccc733ed8fef92472a1c96c2a`. That review accepts the A/B one-ULP coefficient and next airborne consumer while explicitly leaving release introduction and broader implementation coverage outside the finding.

**Requested independent decision:** verify these exact source identities, method order/literals/cast, consumer path and bounded conclusion. No result is recorded until a separate source reviewer accepts or requests changes to this memo.

## Limits

This evidence establishes the exact first changed release among the four examined releases, not every point in `(1.17.1, 1.18.2]` without the exact sources listed, nor parity for earlier or later version lines. The paired discovery run remains partial; this memo is not a full-pair completion or runtime validation claim.
