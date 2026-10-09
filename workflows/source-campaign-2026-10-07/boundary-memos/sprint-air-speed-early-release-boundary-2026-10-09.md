# Sprint air-speed floating-point boundary: early-release source memo

## Scope and immutable inputs

- Boundary: the sprint-conditioned player air-speed producer already bounded through 1.18.2; this memo closes the requested earlier exact releases and verifies the 1.18.1/1.18.2 edge.
- Operation: the ordinary Player tick's post-super air-speed reset and sprint increment, plus the LivingEntity ordinary-air coefficient consumer. This memo does not cover the separate temporary abilities-flight override.
- Exact requested releases inspected: 1.8.9, 1.9.4, 1.10.2, 1.11, 1.11.1, 1.11.2, 1.12.2, 1.13, 1.13.1, 1.13.2, 1.14, 1.14.4, 1.15, 1.15.1, 1.15.2, 1.16, 1.16.1, 1.16.2, 1.16.5, 1.17.1, 1.18, 1.18.1, and 1.18.2.
- Source roots are the ready publications under `build/movement-campaign-2026-10-07/ready/`; they were read-only. No decompilation, implementation or wiki source was used.
- Existing bounded source memo identity: `memo1c4317af04f70a596642369a402ce38ccb5492da`. Existing independent-source acceptance identity: `independentsourceACCEPTee586d98cbd02df932f6f076daa56a9d2176557b`. Both are preserved; this memo adds only the requested earlier-release boundary evidence.
- Each selected `.ready.json` declared the stated version and mapping and status `ready`. The source manifest and artifact manifest byte hashes matched the hashes embedded in each marker. The Player and LivingEntity source hashes below each occur in their selected `*.sources.sha256` manifest. No requested exact release was unavailable.

## Source conclusion

All inspected releases from 1.8.9 through 1.18.1 perform the sprint increment with double-precision arithmetic and an explicit final float conversion. The source spelling changes in 1.14: through 1.13.2 the expression multiplies the float `flyingSpeed` by unsuffixed double literal `0.3`; from 1.14 through 1.18.1 the decompiled operation uses the corresponding unsuffixed double literal `0.005999999865889549`. The latter is still added in double precision before conversion to float.

1.18.2 is the first inspected release with `this.flyingSpeed += 0.006F`. Its compound assignment uses a float-suffixed increment and differs in intermediate precision from the prior double expression. The producer remains in the Player tick after `super.mobTick()` / `super.aiStep()`. Therefore the ordinary superclass travel in that tick consumes the previously stored air speed; the reset and sprint increment update the value for a later ordinary-air movement call. The paired consumer remains the airborne branch of the LivingEntity movement-speed selection in every inspected release.

This establishes the source boundary among the exact releases listed here. It does not establish a trajectory, a complete Player tick audit, or the first changed release among unsampled versions.

## Exact member ranges

Paths are relative to the corresponding `ready/<version>/` publication. Producer ranges are the whole Player tick member; the operation line is stated separately. Consumer ranges are the whole member that selects the airborne coefficient; the relevant source read line is stated separately.

| Release | Mapping | Producer member and operation line | Consumer member and read line |
|---|---|---|---|
| 1.8.9 | ornithe-feather | `ornithe-feather/net/minecraft/entity/living/player/PlayerEntity.java` `mobTick()` 431–493; 454–456 | `ornithe-feather/net/minecraft/entity/living/LivingEntity.java` `travel(...)` 1119–1239; 1137 |
| 1.9.4 | ornithe-feather | `ornithe-feather/net/minecraft/entity/living/player/PlayerEntity.java` `mobTick()` 402–464; 425–427 | `ornithe-feather/net/minecraft/entity/living/LivingEntity.java` `travel(...)` 1302–1469; 1367 |
| 1.10.2 | ornithe-feather | `ornithe-feather/net/minecraft/entity/living/player/PlayerEntity.java` `mobTick()` 413–475; 436–438 | `ornithe-feather/net/minecraft/entity/living/LivingEntity.java` `travel(...)` 1332–1505; 1397 |
| 1.11 | ornithe-feather | `ornithe-feather/net/minecraft/entity/living/player/PlayerEntity.java` `mobTick()` 417–479; 440–442 | `ornithe-feather/net/minecraft/entity/living/LivingEntity.java` `travel(...)` 1386–1559; 1451 |
| 1.11.1 | ornithe-feather | `ornithe-feather/net/minecraft/entity/living/player/PlayerEntity.java` `mobTick()` 417–479; 440–442 | `ornithe-feather/net/minecraft/entity/living/LivingEntity.java` `travel(...)` 1388–1561; 1453 |
| 1.11.2 | ornithe-feather | `ornithe-feather/net/minecraft/entity/living/player/PlayerEntity.java` `mobTick()` 417–479; 440–442 | `ornithe-feather/net/minecraft/entity/living/LivingEntity.java` `travel(...)` 1388–1561; 1453 |
| 1.12.2 | ornithe-feather | `ornithe-feather/net/minecraft/entity/living/player/PlayerEntity.java` `mobTick()` 413–481; 436–438 | `ornithe-feather/net/minecraft/entity/living/LivingEntity.java` `travel(...)` 1425–1599; 1490 |
| 1.13 | ornithe-feather | `ornithe-feather/net/minecraft/entity/living/player/PlayerEntity.java` `mobTick()` 463–531; 486–488 | `ornithe-feather/net/minecraft/entity/living/LivingEntity.java` `travel(...)` 1459–1650; 1531 |
| 1.13.1 | ornithe-feather | `ornithe-feather/net/minecraft/entity/living/player/PlayerEntity.java` `mobTick()` 464–532; 487–489 | `ornithe-feather/net/minecraft/entity/living/LivingEntity.java` `travel(...)` 1478–1669; 1550 |
| 1.13.2 | ornithe-feather | `ornithe-feather/net/minecraft/entity/living/player/PlayerEntity.java` `mobTick()` 464–532; 487–489 | `ornithe-feather/net/minecraft/entity/living/LivingEntity.java` `travel(...)` 1478–1669; 1550 |
| 1.14 | ornithe-feather | `ornithe-feather/net/minecraft/entity/living/player/PlayerEntity.java` `mobTick()` 473–533; 496–498 | `ornithe-feather/net/minecraft/entity/living/LivingEntity.java` `m_70235197(float)` 1954–1956; 1955 |
| 1.14.4 | ornithe-feather | `ornithe-feather/net/minecraft/entity/living/player/PlayerEntity.java` `mobTick()` 492–552; 515–517 | `ornithe-feather/net/minecraft/entity/living/LivingEntity.java` `m_70235197(float)` 1961–1963; 1962 |
| 1.15 | mojmap | `mojmap/net/minecraft/world/entity/player/Player.java` `aiStep()` 497–557; 520–522 | `mojmap/net/minecraft/world/entity/LivingEntity.java` `getFrictionInfluencedSpeed(float)` 2006–2008; 2007 |
| 1.15.1 | mojmap | `mojmap/net/minecraft/world/entity/player/Player.java` `aiStep()` 497–557; 520–522 | `mojmap/net/minecraft/world/entity/LivingEntity.java` `getFrictionInfluencedSpeed(float)` 2006–2008; 2007 |
| 1.15.2 | mojmap | `mojmap/net/minecraft/world/entity/player/Player.java` `aiStep()` 497–557; 520–522 | `mojmap/net/minecraft/world/entity/LivingEntity.java` `getFrictionInfluencedSpeed(float)` 2006–2008; 2007 |
| 1.16 | mojmap | `mojmap/net/minecraft/world/entity/player/Player.java` `aiStep()` 483–538; 501–503 | `mojmap/net/minecraft/world/entity/LivingEntity.java` `getFrictionInfluencedSpeed(float)` 2104–2106; 2105 |
| 1.16.1 | mojmap | `mojmap/net/minecraft/world/entity/player/Player.java` `aiStep()` 483–538; 501–503 | `mojmap/net/minecraft/world/entity/LivingEntity.java` `getFrictionInfluencedSpeed(float)` 2104–2106; 2105 |
| 1.16.2 | mojmap | `mojmap/net/minecraft/world/entity/player/Player.java` `aiStep()` 483–538; 501–503 | `mojmap/net/minecraft/world/entity/LivingEntity.java` `getFrictionInfluencedSpeed(float)` 2106–2108; 2107 |
| 1.16.5 | mojmap | `mojmap/net/minecraft/world/entity/player/Player.java` `aiStep()` 483–538; 501–503 | `mojmap/net/minecraft/world/entity/LivingEntity.java` `getFrictionInfluencedSpeed(float)` 2106–2108; 2107 |
| 1.17.1 | mojmap | `mojmap/net/minecraft/world/entity/player/Player.java` `aiStep()` 489–551; 507–509 | `mojmap/net/minecraft/world/entity/LivingEntity.java` `getFrictionInfluencedSpeed(float)` 2200–2202; 2201 |
| 1.18 | mojmap | `mojmap/net/minecraft/world/entity/player/Player.java` `aiStep()` 489–551; 507–509 | `mojmap/net/minecraft/world/entity/LivingEntity.java` `getFrictionInfluencedSpeed(float)` 2202–2204; 2203 |
| 1.18.1 | mojmap | `mojmap/net/minecraft/world/entity/player/Player.java` `aiStep()` 489–551; 507–509 | `mojmap/net/minecraft/world/entity/LivingEntity.java` `getFrictionInfluencedSpeed(float)` 2202–2204; 2203 |
| 1.18.2 | mojmap | `mojmap/net/minecraft/world/entity/player/Player.java` `aiStep()` 491–553; 509–511 | `mojmap/net/minecraft/world/entity/LivingEntity.java` `getFrictionInfluencedSpeed(float)` 2206–2208; 2207 |

## Source and manifest hashes

For each row, hashes are lowercase SHA-256. The source-manifest hash is for the release's `*.sources.sha256`; the ready-marker hash is for its `*.ready.json`. Each ready marker also binds the artifact-manifest hash and exact publication metadata. The artifact-manifest hash was checked against the marker for every row.

| Release | Player source SHA-256 | LivingEntity source SHA-256 | Source manifest SHA-256 | Ready marker SHA-256 |
|---|---|---|---|---|
| 1.8.9 | `e66cb294fc93118148a444bbafdf4dd57cbf66a23d69b1e8892cefccc690ab88` | `082831c6578e3a70fa6cea5b90bc3eefc26678259b66334470de22b90b5b0e4e` | `9e75f46dc0ed43b6a355bd65db8a92c93a4dfeaecfa92284187c6fe9410d8004` | `fe0ec792349ae43c91d0a30f23660c94b2f5c8d31a29643f73ed0de367110b95` |
| 1.9.4 | `d658a0d95452d12bb7e347bfd802240eeecaf7f938e10dcd43e2640434387f85` | `bbb7703f18fd5da05c4e4a43a77ea644b388e63c01d34166d308ea52054be4e5` | `c7b508fe01634887b65919dcd3a900c311a21d9510a1f1ab248d5c17c528ab19` | `3d0a810d9f3a93233d880ca07ffc806ea232230d33197197ad50ba69181171c6` |
| 1.10.2 | `a055b84b98d98e828e177cad9bba47a1334ee9bf3ecd4331792979106235a302` | `d40dd476b6b68c6ce45b4202823475deb546ecda2284da330ff6724b33815e82` | `91b0f478acb7b6f13463c35b268a30d2806f583402631a56f54ce2eb70d1ec71` | `4c6d236e1d06ffc365452c48e73dfa2bf2414bbf4ea67a24a7010c5583906a2d` |
| 1.11 | `a0d787681de5caecda03fea1536dac0c07e6ca5cfbfe3b5fbb700cbe77c86fb6` | `1f2e31fa6a905ccc2a51a0338f2aaf448f2b0569f28644e8205d864e570d9b2b` | `8b3e5da58494f1dba77410960753b0bf646425a1ada491d8242cc71e6c7a59ca` | `3c4102230dba0f9d2d07f752bbd2a19d2717375f6e31fb618c0450b7fd82bd45` |
| 1.11.1 | `8b37fd286b2dac1b343bd708b80284b43bb31c61bafc63bdcf6620ba3a59065c` | `bb7dc6c9e423a9568d6433d51bba12e7aee4555fbf3fb3e2b87f618382279f2f` | `0d5f4697d4e4ce53b36d0dbfec89d44830eb76753a6643b49e42f0857753066d` | `454036a978074d3a34359dfebf03d6ef3e3335d1318b88d6b17e4f1f5ab566cf` |
| 1.11.2 | `87fe94fa6cbf7aba18b9a5e3401664439eb8eba9958173da8fbcd05cc7ad948b` | `bb7dc6c9e423a9568d6433d51bba12e7aee4555fbf3fb3e2b87f618382279f2f` | `d908c2af76598ad26999563bfef0089f1d3c8c32483eee6b108f0defa2773df0` | `ceabfb266993567aab8c867525365e18aa0a0d7ba5a2cb7b6cfcc83d21318c40` |
| 1.12.2 | `e4e0fdbe07a7d0a0ae4a70cbb6739a287c9d045a4a12b409895c220b5d91fe1e` | `190e9ac551538e015d9e4d6c42856e5ba32b593131cf6d93895e7b29533f1ee6` | `b8a37ccfccd2aac5f40f5e34fec85873dbdfa93a266043c103e592e4a8c949da` | `b0aeec721e5c0af02b33d9e217cb8272889fa139ded1ecc2a31e42b930138f7a` |
| 1.13 | `4a57ce65405436aeb211d7a04a9a9f6ae1a661d033fd3ba2424909bfb42b8acd` | `c38e95c2c13e088b03d77fc2c6b98d4edd7e6ca5aef12b187c8e3f3ea782e864` | `0509c2614b0bb0e616e2ebc6382f8eb5d031b5d86b834a4a6ed7fd96b6d68f1a` | `82a4a8f6a03fbd99a7b8577b1ae5980d95af07c7ded0da284106d16eb1e01a99` |
| 1.13.1 | `4c1b14b4727116071b1ba9e3eeae84b8077f3d767e10e49b6a68a2556da32272` | `bb691358c9a43c9f46e85575bf4d0a4ad671d0eb912502acc3a6a3f625e42f1c` | `21770b01ff3bf443db2e9e1b6df7db25d2fd6b7a72a86228259269c932d3abba` | `f098b3a08b37276cc1a465a8a122df8de76d68786c854b92bac1ca51a79670de` |
| 1.13.2 | `4ed22f6c5a3c55d67eed782070ac722201df4d624adbc90779f1fd29c2876633` | `bb691358c9a43c9f46e85575bf4d0a4ad671d0eb912502acc3a6a3f625e42f1c` | `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211` | `1d1c644c1deb05c02d11dc575ad834f4f72489124701d9b2b90357f43dc37b38` |
| 1.14 | `f73879bd42103fa45f39cfe178ac82c7574c6306f00555af7f7e795976275dd7` | `1db4dd1aa95a06c48511e5567c0d3836f2a6e5a12a5cf03ab5c892e429198b79` | `68bc39e38bc501e7c9af315de4678263e1a64606b0b9780099187c7984ccbc98` | `e0ecb319a63d5d664b87ad7c00638e1cdb9dc302ee2c05bbc05a88ce96d1e60b` |
| 1.14.4 | `2614deb3b50d11f6cdf54d9e308703bf49cb53b72646b05253857e9557d657df` | `2cccf4331ce9e62013eeb8e96163e5e146e1291619eb998ce9867daa87c02b61` | `717b468536348557f9dae8cf57edccf80ad16eb65e5bef6c60bdb5ea3c9f75bc` | `bf8c9399e3a36ddd7edc78d98413319000895f49e88db224335f62b2baf21e77` |
| 1.15 | `6985e11ad9be553e9a2f25e5decc20a9da5c3b59b68f4e83a5b9f448cd2e48a0` | `139c3866ad2c6a1ad73496f22ff1a4b5259998442092ffbafb8664428e28bf79` | `251a3019ccc3fd067f66365f3545b69a983383a28b19a1038debf1791be29340` | `c6b66b63bafaf2096ad7606111f1d0fae1f2178d624d6a8c9ded5ad3ac1aaa31` |
| 1.15.1 | `6985e11ad9be553e9a2f25e5decc20a9da5c3b59b68f4e83a5b9f448cd2e48a0` | `139c3866ad2c6a1ad73496f22ff1a4b5259998442092ffbafb8664428e28bf79` | `c8fdce2f9432b72fc13c19a48f9ab3be203cac41a617139e81bac3552245912d` | `afbb16c3cb832ffee8e4763bf9933d9635ecd811c9fbd7166554b78e59788504` |
| 1.15.2 | `1ba2724c22163862b8f7fdfdea5a04a66e4db26a119d7e5360ba024724a34793` | `46d243bb7e51f7b54404aa1d7d6e6b827682d0f5925d02847c4193306c4d5e54` | `cb7fd93f8730d8b2813857744f8572060535c7de44fcee86222acd1ceb6f43e7` | `64a0e40b784d525ce50937c63e465fc4f564efb1f7af17f2bac3c77a36907be0` |
| 1.16 | `a849960d47bf00a2cddfb6636c78c518532a11967ba08829f19c5ebced08681e` | `b6ea2b9b037b79fc3ea6e1f6bcd2c4c1d89d4a96315968a8f247641f6c335f26` | `e3c23e94ac985c25dec0c768aed17b443ba4823662cf1e152235c99756ae5d4a` | `5b6cb24f95e83b856c53f646d6477f2e46dae2276737dee56dd07c5bd745e4a2` |
| 1.16.1 | `a849960d47bf00a2cddfb6636c78c518532a11967ba08829f19c5ebced08681e` | `b6ea2b9b037b79fc3ea6e1f6bcd2c4c1d89d4a96315968a8f247641f6c335f26` | `76fdd121070c2012f48cbdf5cf0c78490116afc94117c30b0858eedcd6ab9754` | `c19d707f7d6622733b5189599ff77aa7999cc11a3d900f29d150f9fab203ffcb` |
| 1.16.2 | `d2e26589bdb6a20dc914266db06aa48f50811efc792d6e63b3008c9199914960` | `b5d8a1a3c80f85d5d545b5a777e9e2a915dc002a7e31b5f5ad12bf7e285d7a88` | `10d312ca29ee48e25f4727adafb9fcfbd741dcebe81b343d3dcffa071952a7a7` | `e5f69bab646b6143b0660a97fade59595ff2e924d36567b9cd285c8babd31214` |
| 1.16.5 | `d2e26589bdb6a20dc914266db06aa48f50811efc792d6e63b3008c9199914960` | `b5d8a1a3c80f85d5d545b5a777e9e2a915dc002a7e31b5f5ad12bf7e285d7a88` | `9499f2611d0e6dde37cb635f1a28a591416382d6bc188b37d5f99e2b2a20823b` | `e9dd4397c6ade5f68d27f50baf285086dc367b56d2119b03a116cf3cf3b23fee` |
| 1.17.1 | `724bb298499dabe489dffd5ce7ec81c9773619a8d2c70044911eae4c9e8ff481` | `33fd081aadb2b6fdc9ebf487db6da5b38c54f4b8676572790ee2203690d15e6f` | `93270d229acfb751bf56daf1e7be26ce3dcff26b29e94aa157de621405a3463b` | `c7180dce94d7b57b53a964445b0c788c71f23ba3ba40eea593c11cc939185e9b` |
| 1.18 | `1fb9dfb9f700323d77f4196dbc3618d01a65e56438b9cce3ee38fa0b258bd7e2` | `68fc9b15eb31690377afe92acfc783c86945fedec5739d28faacd96da4f9e0e7` | `a43c61223ddedbdd8ede4d2daf6a750a58cbba324b30c025429ee1030cfc0d78` | `4537edf0e88c88631215beb1c14f1d67f5a5d6d021c982c0af549b7b27856e5f` |
| 1.18.1 | `1fb9dfb9f700323d77f4196dbc3618d01a65e56438b9cce3ee38fa0b258bd7e2` | `68fc9b15eb31690377afe92acfc783c86945fedec5739d28faacd96da4f9e0e7` | `52aa98450b5cfa55ac2bd2054f108e16df7fa939a3f061991b6a9adb434d8d2d` | `7a6f3d9d86776e5a95e4722fc2c36f41165c34177f2993978dfaab410641fc81` |
| 1.18.2 | `bf639c1962ff90d69e4569b2b18f6fcf57ac46ef80b19686f0fbc1687fca744a` | `db4168d531caf18f22e3fefd073365e776da4075ce01452bb9f7671d9b458782` | `aea0cb9c6fc8f7a46f0eb82b0388ad58a4659f513be6c0a2be06c0df0c1eb07a` | `d8057d47468c37880de38d658e95070ec3b750305b7856189997604822480946` |

## Uninspected release IDs

These exact releases were not in the requested sample set and are not present as matching ready directories in this publication set: `1.9`, `1.9.1`, `1.9.2`, `1.9.3`, `1.10`, `1.10.1`, `1.12`, `1.12.1`, `1.14.1`, `1.14.2`, `1.14.3`, `1.16.3`, `1.16.4`, and `1.17`. No behavior is inferred for them. This list does not include prereleases or snapshots.

## Limits

No mechanics code was changed. No builds, tests, clients, TAS, gym/server launches, Docker operations, or pushes were run. This is a source-only boundary memo, not pair completion or runtime-parity evidence.
