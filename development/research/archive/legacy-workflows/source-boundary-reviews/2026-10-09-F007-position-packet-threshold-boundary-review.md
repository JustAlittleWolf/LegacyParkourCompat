# F-007 position-packet threshold boundary review

**Review date:** 2026-10-09  
**Decision:** ACCEPT — source-only first-release boundary evidence

## Immutable target

- Memo commit: `918217999a8fc6922ffc5a7b1e82ea6291d7f542`
- Memo path: `workflows/source-boundary-reviews/F007-position-packet-threshold-boundary-2026-10-09.md`
- Git blob: `20b94e42eec772864d88b29236c94ae2ee624000`
- Raw SHA-256: `2C998711E3AD1525100F5271EF03BAA08181646ACD1F6597086C971648CA486D`

## Independently verified evidence

The four published ready markers identify exact Mojmap source publications for 1.17.1, 1.18, 1.18.1, and 1.18.2. Their marker, source-manifest, artifact-manifest, and `LocalPlayer.java` hashes match the target memo. The source manifests list the cited `LocalPlayer.java` files with those same hashes. The 1.18 and 1.18.1 `LocalPlayer.java` hashes are identical.

In `LocalPlayer.sendPosition()`, the 1.17.1, 1.18, and 1.18.1 sources compute coordinate deltas as doubles and set the positional-send condition with the strict predicate `dx * dx + dy * dy + dz * dz > 9.0E-4 || positionReminder >= 20`. The predicate is inside the `isControlledCamera()` gate. The passenger branch sends its separate packet and clears that positional-send condition; the rotation predicate and send selection follow it.

In 1.18.2, the same position-send condition is `Mth.lengthSquared(dx, dy, dz) > Mth.square(2.0E-4) || positionReminder >= 20`. The cited 1.18.2 `Mth.java` is hash `32747c5b09fc184baae356e39a0088fd66c9f08f69d9e98b67c19e1de6f1bb2e`; `square(double)` returns `x * x` and the three-argument `lengthSquared` returns `x * x + y * y + z * z`. The new threshold is therefore `4.0E-8`; the sum-of-squares operation order is unchanged. The comparisons remain strict, and the reminder fallback remains `>= 20`.

The source change is absent in 1.18 and 1.18.1 and present in 1.18.2. **1.18.2 is the first changed release among the exact releases inspected.**

| Release | Ready marker SHA-256 | Source manifest SHA-256 | Artifact manifest SHA-256 | `LocalPlayer.java` SHA-256 |
|---|---|---|---|---|
| 1.17.1 | `c7180dce94d7b57b53a964445b0c788c71f23ba3ba40eea593c11cc939185e9b` | `93270d229acfb751bf56daf1e7be26ce3dcff26b29e94aa157de621405a3463b` | `e52c5dbae7d9663190ccc55a4f9b44a8e0615fb1fbbd8280811df43f70ec8aaa` | `c9a91cb6cb57806bc8d22e5bfe2d97daaf21d5d2a48f34a6e2c53164c61c5812` |
| 1.18 | `4537edf0e88c88631215beb1c14f1d67f5a5d6d021c982c0af549b7b27856e5f` | `a43c61223ddedbdd8ede4d2daf6a750a58cbba324b30c025429ee1030cfc0d78` | `7ca2b4da88c215e52924d277b257c4631f9664f5def2fb2b7ef25479ec330234` | `e38d0bbb6e5b9de2a698609b8491d0007406b3becd42ba9b95289f533788c996` |
| 1.18.1 | `7a6f3d9d86776e5a95e4722fc2c36f41165c34177f2993978dfaab410641fc81` | `52aa98450b5cfa55ac2bd2054f108e16df7fa939a3f061991b6a9adb434d8d2d` | `b8237a01cdebe7d784caae113886f42565677ce6897ea960a49223cfec4c00ec` | `e38d0bbb6e5b9de2a698609b8491d0007406b3becd42ba9b95289f533788c996` |
| 1.18.2 | `d8057d47468c37880de38d658e95070ec3b750305b7856189997604822480946` | `aea0cb9c6fc8f7a46f0eb82b0388ad58a4659f513be6c0a2be06c0df0c1eb07a` | `a1507e4875faee892aca4c59686bbd68933db21a2274559e81eea8a32b021036` | `99c2d18bcd23243afb8f95c5bafb21fb0be7ea04aacbb14fcf7be7ced2c9c095` |

## Scope limits

This acceptance covers the exact vanilla source threshold and its first changed release within the inspected set. It does not freeze or complete the 1.17.1–1.18.2 source pair, and it does not re-review the separately accepted bounded server-consumer trace. It makes no client-trajectory, later correction-outcome, implementation, wiki, or runtime claim.
