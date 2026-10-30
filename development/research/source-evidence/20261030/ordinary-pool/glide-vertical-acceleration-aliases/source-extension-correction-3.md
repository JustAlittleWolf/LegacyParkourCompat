# Supplement 3: remaining exact stable glide aliases

This supplement preserves `source-extension.md` and Corrections 1–2, and extends the same operation-only audit to the exact stable aliases requested by the coordinator: 1.9.1, 1.9.2, 1.9.3, 1.10, 1.10.1, 1.11, and 1.11.1. It reuses the accepted 1.9 minor alias hash sweep (`.native-source/20261028/frontier-c/19-minor-alias-hash-sweep/coverage.md`, SHA-256 `deec97261821c4670b7836d458a2ca4ba45b24d3a34cc56b308d001a4adf4868`) and its provenance correction, which verified exact release/mapping provenance and source rows for the 1.9.x series. This does not reuse that partial sweep as full-pair coverage.

## Exact alias evidence

Canonical source root: `D:/Javastuff/LegacyParkourCompat/decompiled_minecraft/<version>/ornithe-feather/`. `LivingEntity.java` and `Entity.java` are both independently rehashed below; each hash matches the exact source manifest or hidden mapping-root provenance map for that release. All seven exact provenance files were checked for the relevant source-row matches and mapping artifacts.

| Release | Feather mapping artifact | Provenance SHA-256 | `LivingEntity.java` SHA-256 | Glide gate / float narrowing / acceleration lines |
|---|---|---|---|---|
| 1.9.1 | `feather-gen2-1.9.1+build.2-mergedv2.jar` | `e9fb0da6511597e2de953a143f9570b82243e5b9504603686e557f06271da64a` | `d8824f670037a88753c428c27213989fc5173b1d7c638f498f73395c145b6fd9` | 1297 / 1308 / 1309 |
| 1.9.2 | `feather-gen2-1.9.2+build.2-mergedv2.jar` | `9928b467deb35c7e7f891b4fee1627a68feecdf233c2076909fd4834b0380510` | `d8824f670037a88753c428c27213989fc5173b1d7c638f498f73395c145b6fd9` | 1297 / 1308 / 1309 |
| 1.9.3 | `feather-gen2-1.9.3+build.2-mergedv2.jar` | `43325cdd53ca34b88ee9af8464782b323a948b98c8a556c4761f1e5737b84c3a` | `bbb7703f18fd5da05c4e4a43a77ea644b388e63c01d34166d308ea52054be4e5` | 1306 / 1317 / 1318 |
| 1.10 | `feather-gen2-1.10+build.2-mergedv2.jar` | `df266e2c3009dd1a873c1665c30fe388078ed1c6227668e10cde87409a18c6da` | `d40dd476b6b68c6ce45b4202823475deb546ecda2284da330ff6724b33815e82` | 1336 / 1347 / 1348 |
| 1.10.1 | `feather-gen2-1.10.1+build.2-mergedv2.jar` | `a9a50497930848bf0ddec732b6d424e947f643a8fa6290e01425ac2bbfce219f` | `d40dd476b6b68c6ce45b4202823475deb546ecda2284da330ff6724b33815e82` | 1336 / 1347 / 1348 |
| 1.11 | `feather-gen2-1.11+build.2-mergedv2.jar` | `6f775c806fd1401a4d1756e9ef88da8d73185fe1d13598235f85c424405f4c40` | `1f2e31fa6a905ccc2a51a0338f2aaf448f2b0569f28644e8205d864e570d9b2b` | 1390 / 1401 / 1402 |
| 1.11.1 | `feather-gen2-1.11.1+build.2-mergedv2.jar` | `0f6c92b8fbf4b1053a26a4cb61dc8f20c695e74a1963b49d911b8c7fbf01a055` | `bb7dc6c9e423a9568d6433d51bba12e7aee4555fbf3fb3e2b87f618382279f2f` | 1392 / 1403 / 1404 |

The three line numbers in the last column are `if (this.isFallFlying())`, the `(float)` lift narrowing, and `velocityY += -0.08 + m * 0.06`, respectively.

| Release | `Entity.java` SHA-256 | `velocityY` declaration |
|---|---|---:|
| 1.9.1 | `74d3ac34d7e6355c8fdcdd777dcd95ba9ef4626718c8772c1b58d0e28419e42a` | `double`, line 95 |
| 1.9.2 | `74d3ac34d7e6355c8fdcdd777dcd95ba9ef4626718c8772c1b58d0e28419e42a` | `double`, line 95 |
| 1.9.3 | `bcd7fa2206bf8d7271102f6da7fe2771f96dbe22ec79dab9f2101a3e29c17ef0` | `double`, line 96 |
| 1.10 | `05da145effa19a6ef7934cc276e89226373b67c12f4ce89a8ce2183f29039f77` | `double`, line 97 |
| 1.10.1 | `05da145effa19a6ef7934cc276e89226373b67c12f4ce89a8ce2183f29039f77` | `double`, line 97 |
| 1.11 | `3209feaacd1e798b521a193eeb081d92c96881a70a18254ef24a58e911a4fd97` | `double`, line 103 |
| 1.11.1 | `5a906de1d40a4a5ba8aedc9a6a9dc704e0ed862df177cf967fb1a9454765fa96` | `double`, line 104 |

## Operation result

Each newly checked release uses the same exact float-lift computation and default-gravity term as the other releases in `source-extension.md`. The enclosing local-movement, water/lava, and `isFallFlying()` gates are unchanged in the inspected branch. The velocity field is `double` in every release. The first seven values and subsequent order corrections from the earlier supplement remain part of this bound evidence: apply the vertical acceleration, then dive and pull-up corrections, horizontal steering, then velocity drag.

Together, the named source set now covers every stable release from 1.9 through 1.12.2. This confirms the old expression throughout that exact range; it does not prove pair-wide tick coverage, fall-flying activation boundary by itself, 1.8 native fallback wiring, or runtime parity. No canonical sources were generated or changed.
