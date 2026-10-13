# F-007 position-packet threshold boundary evidence

**Decision status:** Boundary candidate for independent source-only review. This memo adds exact 1.18 and 1.18.1 source checks to the accepted endpoint finding; it does not imply an independent boundary acceptance or pair freeze.

## Immutable accepted finding

The accepted finding snapshot is commit `ac5cdaa7e2a77294a945e6d661a97fdb71b77242`, path `workflows/source-campaign-2026-10-07/1.17.1--1.18.2/findings/F-007-position-packet-threshold.md`, Git blob `2d3b24b4b99c828dfbe8b287e1fc67a863f6e1d0`, raw SHA-256 `e311648628d45ab6680d0715ae85817399933157d243bfc6ed37b30b1f5aba09`. The independent review is commit `2fd121e116d7185165c76aea1c3708273afc7ea1`, path `workflows/source-boundary-reviews/2026-10-08-mc1171-1182-snapshot-review.md`, blob `871ad5bccde2194ccc733ed8fef92472a1c96c2a`; its F-007 decision accepts the client position-packet threshold difference and bounded clear-path server update only. Pair status remains partial. Commit `e531086eecc778432b40b2b9ef39499b8a7f0dba` corrects the `SOURCE-HANDOFF` history-binding description; the pair run identifies `ac5cdaa7...` as the immutable F-007 finding snapshot.

## Exact ready-source identities

All four inspected roots are published Mojmap sources with exact version IDs. Marker, source-manifest, artifact-manifest, and `LocalPlayer.java` bytes were checked against the published roots and manifests.

| Release | Ready-marker SHA-256 | Source manifest SHA-256 | Artifact manifest SHA-256 | `LocalPlayer.java` SHA-256 |
|---|---|---|---|---|
| 1.17.1 | `c7180dce94d7b57b53a964445b0c788c71f23ba3ba40eea593c11cc939185e9b` | `93270d229acfb751bf56daf1e7be26ce3dcff26b29e94aa157de621405a3463b` | `e52c5dbae7d9663190ccc55a4f9b44a8e0615fb1fbbd8280811df43f70ec8aaa` | `c9a91cb6cb57806bc8d22e5bfe2d97daaf21d5d2a48f34a6e2c53164c61c5812` |
| 1.18 | `4537edf0e88c88631215beb1c14f1d67f5a5d6d021c982c0af549b7b27856e5f` | `a43c61223ddedbdd8ede4d2daf6a750a58cbba324b30c025429ee1030cfc0d78` | `7ca2b4da88c215e52924d277b257c4631f9664f5def2fb2b7ef25479ec330234` | `e38d0bbb6e5b9de2a698609b8491d0007406b3becd42ba9b95289f533788c996` |
| 1.18.1 | `7a6f3d9d86776e5a95e4722fc2c36f41165c34177f2993978dfaab410641fc81` | `52aa98450b5cfa55ac2bd2054f108e16df7fa939a3f061991b6a9adb434d8d2d` | `b8237a01cdebe7d784caae113886f42565677ce6897ea960a49223cfec4c00ec` | `e38d0bbb6e5b9de2a698609b8491d0007406b3becd42ba9b95289f533788c996` |
| 1.18.2 | `d8057d47468c37880de38d658e95070ec3b750305b7856189997604822480946` | `aea0cb9c6fc8f7a46f0eb82b0388ad58a4659f513be6c0a2be06c0df0c1eb07a` | `a1507e4875faee892aca4c59686bbd68933db21a2274559e81eea8a32b021036` | `99c2d18bcd23243afb8f95c5bafb21fb0be7ea04aacbb14fcf7be7ced2c9c095` |

The exact 1.18 and 1.18.1 `LocalPlayer.java` hashes are identical. Ready marker metadata distinguishes the two release IDs despite the equal source file.

## Paired operation and boundary

In 1.17.1, 1.18, and 1.18.1, `LocalPlayer.sendPosition()` computes the three `double` coordinate deltas from the last-sent position and uses the strict predicate `dx * dx + dy * dy + dz * dz > 9.0E-4 || positionReminder >= 20`. The controlled-camera gate encloses this predicate. Passenger movement takes its separate packet branch and suppresses this ordinary position-send flag; rotation-change handling and the baseline reset after a send are preserved. The inspected threshold lines are 247 (1.17.1), 244 (1.18), and 244 (1.18.1).

In 1.18.2, the same surrounding controlled-camera, reminder, passenger and rotation gates remain, but the displacement predicate becomes `Mth.lengthSquared(dx, dy, dz) > Mth.square(2.0E-4) || positionReminder >= 20`, at `LocalPlayer.java:239`. The paired Mth source cited in the accepted finding is hash `32747c5b09fc184baae356e39a0088fd66c9f08f69d9e98b67c19e1de6f1bb2e`; `square(double)` returns `x * x`, and `lengthSquared(double,double,double)` returns `x * x + y * y + z * z`. Thus the B threshold is `4.0E-8`, and the strict comparisons differ for a squared displacement such as `1.0E-6`; the periodic reminder still sends at 20 ticks on each side.

Among the exact releases in the interval, the first changed release is 1.18.2: 1.18 and 1.18.1 retain A's threshold, while 1.18.2 has B's threshold. This narrows the accepted endpoint finding's former boundary `unknown within (1.17.1, 1.18.2]` to 1.18.2. It does not claim client trajectory differences, later collision/correction outcomes, or a frozen/complete pair. The source report already records the bounded on-foot, controlled-camera, reminder-below-20 clear-path server consumer; that separate consumer trace is not repeated here.

This memo uses only exact vanilla sources and their published manifests. It contains no mod implementation analysis, wiki material, runtime trajectory or implementation recommendation. The source campaign pair remains partial. An independent source-only reviewer must accept this boundary before any Java change relies on the `V1_18` / `V1_18_2` split.
