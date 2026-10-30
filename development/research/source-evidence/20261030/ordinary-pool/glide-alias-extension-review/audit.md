# Independent source audit: glide alias extension

## Binding and verdict

Reviewed the four immutable extension artifacts and the referenced earlier operation and 1.9 provenance records listed under **Reviewed artifact identities**. Canonical sources were read-only files under `D:/Javastuff/LegacyParkourCompat/decompiled_minecraft/<version>/ornithe-feather/`.

**Verdict: supported for the exact 14 stable releases named, with Corrections 1–3 applied.** The accepted one-way operation verdict remains scoped to the default-gravity glide vertical-acceleration grouping. The earlier glide audit's open order correction is resolved by Correction 2; its unverified `velocityY` field type is also now verified from `Entity.java`. Correction 3's added aliases and provenance claims check out. No unsampled release or full-pair closure is inferred.

## Exact source checks

For every release in the table, I recomputed both Java source hashes and matched them to the `LivingEntity.java` and `Entity.java` source row in that release's embedded provenance or `.sources.sha256` manifest. The provenance hash also matches the exact file. Mapping records identify the corresponding Feather Gen2 build 2 artifact(s), and the current source file hashes match those local records. This verifies local source-to-record correspondence; it is not independent authentication of remote artifact bytes.

| Release | Provenance SHA-256 | `LivingEntity.java` SHA-256 | `Entity.java` SHA-256 | Gate / cast / acceleration lines |
|---|---|---|---|---|
| 1.9 | `8daacb7af8f210e716ee4b34c43b8a5d6f6b4b19831d2fd725cbe16ecf330a1a` | `5b5936656aacc64a4d75db7b370985f9456ac96076331da047bd246f76a705fb` | `74d3ac34d7e6355c8fdcdd777dcd95ba9ef4626718c8772c1b58d0e28419e42a` | 1293 / 1304 / 1305 |
| 1.9.1 | `e9fb0da6511597e2de953a143f9570b82243e5b9504603686e557f06271da64a` | `d8824f670037a88753c428c27213989fc5173b1d7c638f498f73395c145b6fd9` | `74d3ac34d7e6355c8fdcdd777dcd95ba9ef4626718c8772c1b58d0e28419e42a` | 1297 / 1308 / 1309 |
| 1.9.2 | `9928b467deb35c7e7f891b4fee1627a68feecdf233c2076909fd4834b0380510` | `d8824f670037a88753c428c27213989fc5173b1d7c638f498f73395c145b6fd9` | `74d3ac34d7e6355c8fdcdd777dcd95ba9ef4626718c8772c1b58d0e28419e42a` | 1297 / 1308 / 1309 |
| 1.9.3 | `43325cdd53ca34b88ee9af8464782b323a948b98c8a556c4761f1e5737b84c3a` | `bbb7703f18fd5da05c4e4a43a77ea644b388e63c01d34166d308ea52054be4e5` | `bcd7fa2206bf8d7271102f6da7fe2771f96dbe22ec79dab9f2101a3e29c17ef0` | 1306 / 1317 / 1318 |
| 1.9.4 | `5c913715bed763369ceb5fd171cab3e63c43298023078c3683f96c7ea8fed3cd` | `bbb7703f18fd5da05c4e4a43a77ea644b388e63c01d34166d308ea52054be4e5` | `bcd7fa2206bf8d7271102f6da7fe2771f96dbe22ec79dab9f2101a3e29c17ef0` | 1306 / 1317 / 1318 |
| 1.10 | `df266e2c3009dd1a873c1665c30fe388078ed1c6227668e10cde87409a18c6da` | `d40dd476b6b68c6ce45b4202823475deb546ecda2284da330ff6724b33815e82` | `05da145effa19a6ef7934cc276e89226373b67c12f4ce89a8ce2183f29039f77` | 1336 / 1347 / 1348 |
| 1.10.1 | `a9a50497930848bf0ddec732b6d424e947f643a8fa6290e01425ac2bbfce219f` | `d40dd476b6b68c6ce45b4202823475deb546ecda2284da330ff6724b33815e82` | `05da145effa19a6ef7934cc276e89226373b67c12f4ce89a8ce2183f29039f77` | 1336 / 1347 / 1348 |
| 1.10.2 | `13b208320bd6316d4b20eb0733f226cae151cae0ffd55ef3331a97223114adf6` | `d40dd476b6b68c6ce45b4202823475deb546ecda2284da330ff6724b33815e82` | `05da145effa19a6ef7934cc276e89226373b67c12f4ce89a8ce2183f29039f77` | 1336 / 1347 / 1348 |
| 1.11 | `6f775c806fd1401a4d1756e9ef88da8d73185fe1d13598235f85c424405f4c40` | `1f2e31fa6a905ccc2a51a0338f2aaf448f2b0569f28644e8205d864e570d9b2b` | `3209feaacd1e798b521a193eeb081d92c96881a70a18254ef24a58e911a4fd97` | 1390 / 1401 / 1402 |
| 1.11.1 | `0f6c92b8fbf4b1053a26a4cb61dc8f20c695e74a1963b49d911b8c7fbf01a055` | `bb7dc6c9e423a9568d6433d51bba12e7aee4555fbf3fb3e2b87f618382279f2f` | `5a906de1d40a4a5ba8aedc9a6a9dc704e0ed862df177cf967fb1a9454765fa96` | 1392 / 1403 / 1404 |
| 1.11.2 | `dcdb65e1361297a584c5b93f581b1b539911911c6cec1a203740ba5379732c6` | `bb7dc6c9e423a9568d6433d51bba12e7aee4555fbf3fb3e2b87f618382279f2f` | `ce8104a17ce783df639cf9726e7b1cd0936563eaa7ba308603335bbe05d49440` | 1392 / 1403 / 1404 |
| 1.12 | `27515e5f4c71e7db2f035a81716507128d77466c75380ad1ef7d140c06401467` | `190e9ac551538e015d9e4d6c42856e5ba32b593131cf6d93895e7b29533f1ee6` | `80f091bf32166c88cf8bbd31caf72d84fa16224410733c7d2a0f00563f294a0a` | 1429 / 1440 / 1441 |
| 1.12.1 | `0ff5246e8d29c04554cc9ec93ae9b4abef539652325b6c0ac822851d7186a9c8` | `190e9ac551538e015d9e4d6c42856e5ba32b593131cf6d93895e7b29533f1ee6` | `80f091bf32166c88cf8bbd31caf72d84fa16224410733c7d2a0f00563f294a0a` | 1429 / 1440 / 1441 |
| 1.12.2 | `747425a26da363d1abc8a2cecd83a74504aeb1f58be93aa53f48c690e35318ce` | `190e9ac551538e015d9e4d6c42856e5ba32b593131cf6d93895e7b29533f1ee6` | `80f091bf32166c88cf8bbd31caf72d84fa16224410733c7d2a0f00563f294a0a` | 1429 / 1440 / 1441 |

`velocityY` is declared `public double` in all 14 paired `Entity.java` files (lines 95, 96, 97, 103, 104, or 105 as release-specific). In each `LivingEntity.moveRelative` glide branch the gates are ordered local/logical movement authority, water exclusion except flying players, lava exclusion except flying players, then `isFallFlying()`. The lift statements are exactly `float m = MathHelper.cos(i);` and `m = (float)(m * (m * Math.min(1.0, l / 0.4)));`. Thus `m` is narrowed once after the completed double-valued inner/outer product; `1.0`, `0.4`, `-0.08`, and `0.06` are double literals. The resulting acceleration RHS is double before compound assignment to the double field.

I checked the following source order in each of the 14 bodies: acceleration (`velocityY += -0.08 + m * 0.06`), conditional dive correction, conditional pull-up correction, conditional horizontal steering, then drag. The source applies drag components in `velocityX`, `velocityY`, `velocityZ` order. Corrections 1 and 3's gate, cast, and acceleration anchors all match the corresponding source lines in the table; Correction 2's original seven-version correction/drag anchors match as well. The prior audit's “drag before dive/pull-up” statement is superseded by Correction 2 and does not describe the current bound extension.

## Limits

This verifies only the exact named glide branches and provenance rows. It establishes neither the first introduction point nor unlisted patch behavior, complete tick/provider coverage for any pair, 1.8 fallback wiring, runtime parity, or implementation readiness. The accepted 1.12.2→1.13 grouping verdict remains a separate one-way operation result; no source beyond the listed 1.9–1.12.2 releases was used here.

## Reviewed artifact identities

| Artifact | SHA-256 |
|---|---|
| `source-extension.md` | `55c4700afef47ed9426f587de41e6d169ca8c9ec11ea71d46c93b3ecd841bea0` |
| `source-extension-correction-1.md` | `bf50b8ff9727bf990f6c14a6dd9c8bdcc054c976320dda52d723bca8bd691e21` |
| `source-extension-correction-2.md` | `1aea484b1afe839143a07c9d525b1a159352f8b5ebd9a41924df3d9a95e3c351` |
| `source-extension-correction-3.md` | `0b331bb0af10f52c1bfcacfec16adc80d3030595c89f1ba60a5b3ce5475168ba` |
| Prior glide alias audit | `caf87ce0595964da3fa68234918b02ad65dfc9c9c6cb45537661a274e26cf812` |
| Accepted one-way reviewer verdict | `d879bb85d5bc0a63cfef01debc64ff37b3935b864149d6b8e2be41a0d70521de` |
| 1.9 alias `coverage.md` | `deec97261821c4670b7836d458a2ca4ba45b24d3a34cc56b308d001a4adf4868` |
| 1.9 alias `supplement.md` | `6a72afe0af8fcab041ddcbe7c96598cd4a4657334181fe3a346a3e4aa690eb30` |
| 1.9 alias `supplement-02.md` | `421fad6d7c0d9a210954c0e060da4ab2038f40895c335940a5db761931c78e63` |
| 1.9 alias `supplement-03.md` | `e8f51bf73cba6cbf25c7eb02498865a7ad1a8c2b813956590df07201e84bbd85` |
| 1.9 alias `review.md` | `79ad1bac929212f7c0b6b24a99b866671bf4d7f243a90175daecb18bc5e98f55` |
| 1.9 alias `supplement-audit.md` | `8ca48693558bcea75877d0acf877fb6f065a257312459fbbb13d5e7f718e8310` |
| 1.9 alias `supplement-02-audit.md` | `592942cbb8e5c7db7e382046945e71d0453531a3682414c181e26eacf0528257` |
| 1.9 alias `supplement-03-audit.md` | `895498f320ee9951aeed40566663807dcc5114c83c5fba5535ea07b36981d3cc` |

No product sources, product history, wiki, backlog, tests, build, game/runtime, source generation, downloads, or canonical writes were used.
