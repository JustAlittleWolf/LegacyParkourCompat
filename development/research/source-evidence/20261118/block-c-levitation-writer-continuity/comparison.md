# Levitation fall-distance writer continuity

Status: COMPLETED for the assigned ordinary locally controlled, non-riding air-travel writer slice. This extends the exact 1.12.2 to 1.13.0 introduction fence and accepted 1.19.4 to 1.20 timing partition without changing either claim.

## Result

The Levitation-gated fall-distance reset is continuously present in every exact stable alias from 1.13 through 1.19.4. In each checked source, the ordinary travel branch first performs movement, then applies the Levitation vertical-velocity adjustment and resets fall distance. The condition has no vertical-sign predicate. The ordinary local-player route is covered by the enclosing travel authority check: canMoveVoluntarily() || isLogicalSideForUpdatingMovement() in Yarn 1.14; isEffectiveAi() || isControlledByLocalInstance() in Mojmap 1.15–1.19.

The effect adjustment remains 0.05 * (amplifier + 1) - prior vertical delta, multiplied by 0.2, throughout the source range. The direct fallDistance = 0.0F write is used through 1.17.1. Starting in 1.18.0 the call is expressed as resetFallDistance(); its exact helper body still writes this.fallDistance = 0.0F. This is a call-site representation change only for the bounded state write. There is no gate, ordering, sign, route, numeric, or effect-presence mismatch among the exact aliases checked.

The bounded ordinary-air writer is in the non-water/non-lava/non-gliding travel branch. In Yarn 1.14–1.14.2, the effect field is named field_5902; StatusEffects.java registers it as the "levitation" effect (ID 25). Yarn 1.14.3 names it LEVITATION. Mojmap names it MobEffects.LEVITATION thereafter.

## Exact alias coverage

All aliases below were read from the exact local decompilation; each relevant LivingEntity.java hash matched its parent *.sources.sha256 entry or mapping-local .decompile-provenance.json source hash. Repeated hashes mean byte-identical complete LivingEntity.java files within the mapped output, not only matching snippets.

| Release aliases | Mapping and relevant source member | LivingEntity.java SHA-256 | Levitation reset slice |
|---|---|---|---|
| 1.13 | Ornithe Feather, net/minecraft/entity/living/LivingEntity.java | c38e95c2c13e088b03d77fc2c6b98d4edd7e6ca5aef12b187c8e3f3ea782e864 | moveRelative 1561–1564; after move, reset at 1564. |
| 1.13.1, 1.13.2 | Ornithe Feather, same path | bb691358c9a43c9f46e85575bf4d0a4ad671d0eb912502acc3a6a3f625e42f1c | moveRelative 1580–1583; after move, reset at 1583. |
| 1.14 | Yarn, net/minecraft/entity/LivingEntity.java | 8889e22779b9124998931bc97ce8b0d183d335c32a9bcbb089e49de65882bae6 | travel 1830–1832; field_5902, direct float-zero write at 1832. |
| 1.14.1 | Yarn, same path | 331e2d7dfa936a6f7197910ba374c4a60b479c8565ff7c64a4d6a9e1e8b52dda | travel 1830–1832; field_5902, direct float-zero write at 1832. |
| 1.14.2 | Yarn, same path | e8224e7df98ccf0986bb390f35e64293263d17b9ab1dbdfeb661c65d28b6ee78 | travel 1836–1838; field_5902, direct float-zero write at 1838. |
| 1.14.3 | Yarn, same path | e8aed9c63c0dfbe88e4b9e11c58881bc6e04b9cbaecbde5e3606e3afa406d72b | travel 1846–1848; named LEVITATION, direct float-zero write at 1848. |
| 1.14.4 | Mojmap, net/minecraft/world/entity/LivingEntity.java | 428762178a876efd4069086e6b7d51f571ea0401f44e7eff0927b7d26d9ef681 | travel 1849–1852; direct float-zero write at 1851. |
| 1.15, 1.15.1 | Mojmap, same path | 139c3866ad2c6a1ad73496f22ff1a4b5259998442092ffbafb8664428e28bf79 | travel 1898–1900; direct float-zero write at 1900. |
| 1.15.2 | Mojmap, same path | 46d243bb7e51f7b54404aa1d7d6e6b827682d0f5925d02847c4193306c4d5e54 | travel 1898–1900; direct float-zero write at 1900. |
| 1.16, 1.16.1 | Mojmap, same path | b6ea2b9b037b79fc3ea6e1f6bcd2c4c1d89d4a96315968a8f247641f6c335f26 | travel 2026–2028; direct float-zero write at 2028. |
| 1.16.2–1.16.5 | Mojmap, same path | b5d8a1a3c80f85d5d545b5a777e9e2a915dc002a7e31b5f5ad12bf7e285d7a88 | travel 2028–2030; direct float-zero write at 2030. |
| 1.17 | Mojmap, same path | 8e81e5f1317d154fa41b676721ff581badd46296f77bcebcadb3c63448e528ed | travel 2113–2115; direct float-zero write at 2115. |
| 1.17.1 | Mojmap, same path | 33fd081aadb2b6fdc9ebf487db6da5b38c54f4b8676572790ee2203690d15e6f | travel 2117–2119; direct float-zero write at 2119. |
| 1.18, 1.18.1 | Mojmap, same path | 68fc9b15eb31690377afe92acfc783c86945fedec5739d28faacd96da4f9e0e7 | travel 2119–2121; resetFallDistance() at 2121. |
| 1.18.2 | Mojmap, same path | db4168d531caf18f22e3fefd073365e776da4075ce01452bb9f7671d9b458782 | travel 2123–2125; resetFallDistance() at 2125. |
| 1.19 | Mojmap, same path | 00c591dd2cac49ee5ccad8705f7dd879ad5d29f0360b98742166294989b3531c | travel 2156–2158; resetFallDistance() at 2158. |
| 1.19.1, 1.19.2 | Mojmap, same path | fee2df5155449098556a138d2530d06b7a35979e43c6aa0ae1fffd6251853e77 | travel 2156–2158; resetFallDistance() at 2158. |
| 1.19.3 | Mojmap, same path | 2b8befdc406176e01672175451dd480b14da0b52bfe11a982d368909c05e73db | travel 2163–2165; resetFallDistance() at 2165. |
| 1.19.4 | Mojmap, same path | c8d91af61f87aaa1666de79696d7cd9d4a8212d05f873bdaf28d7bb2926bf165 | Accepted anchor: travel 2092–2096; resetFallDistance() at 2096. |

The table includes previously sampled 1.13, 1.13.1/.2, 1.14.4, 1.15.2, 1.16.5, 1.17.1, 1.18.2, and 1.19.4 anchors to make the continuous alias inventory explicit. New slices in this pass were 1.14–1.14.3, 1.15/.1, 1.16–1.16.4, 1.17, 1.18/.1, and 1.19–1.19.3.

## Route and operation order

- 1.13 Feather: moveRelative 1478–1486 has the outer local/authoritative update gate. The Levitation writer at 1561–1564 follows move(...); the earlier water-route selection means the Levitation branch is reached only by the ordinary non-water travel path.
- 1.14 Yarn: travel 1763 onward is guarded by canMoveVoluntarily() || isLogicalSideForUpdatingMovement(). In 1.14.0, move(...) is at 1823, then the Levitation effect adjustment and reset are at 1830–1832. The same effect branch and order persist through 1.14.3; line offsets are in the table.
- 1.15–1.19 Mojmap: travel starts under isEffectiveAi() || isControlledByLocalInstance() (exact anchors: 1.15 1831–1832, 1.16 1912–1913, 1.17 1999–2000, 1.18 2005–2006, 1.19 2042–2043). The listed Levitation slice follows handleRelativeFrictionAndCalculateMovement, which has already performed movement.
- Helper delegation from 1.18: LivingEntity.travel calls resetFallDistance(). Exact Entity.resetFallDistance() bodies in 1.18 Entity.java:2101–2103 and 1.19.4 Entity.java:2173–2175 contain only this.fallDistance = 0.0F. Their file hashes match the source manifests: 1.18 69417675ae8e0a5bd88b7372baea1e73b1f5217154f39446c8ad5c92731437f1; 1.19.4 3667fee610cbc5f58012e3a8fb8d6c4849f649fb7fe5300595158112d8b4b58b.

## Release identity and mapping pins

Top-level ready metadata (versionId, mapping family, source-manifest hash and client hash) was checked wherever present; each listed per-file hash matches its source manifest. For nested-provenance outputs, the relevant per-file hash matches .decompile-provenance.json, whose exact client and mapping inputs are identified below. This records release-specific identities even where full source bodies are byte-identical.

- Previously accepted Feather anchors: 1.13 client SHA-256 d807a5ed9428b6c16b64888fd2b7fa76dcf5c55d06f96f7c0c47762c3fc3e26e, source-manifest SHA-256 0509c2614b0bb0e616e2ebc6382f8eb5d031b5d86b834a4a6ed7fd96b6d68f1a; 1.13.1 client f0b4cf6d1a87ae150f87a535651138ca7b82bdad8853f76e7ddbff1dd610ac87, manifest 21770b01ff3bf443db2e9e1b6df7db25d2fd6b7a72a86228259269c932d3abba; 1.13.2 client 3410887ba652f25792c7675bfaf9140e73b60e93cfbf113a803f8a98cb05c0f9, manifest 2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211.
- Yarn nested-provenance releases: 1.14 client 93907cbf0655aa5f10d049efa4d745f4250eecc5d0d5d1268046eec3144f323a, Yarn 1.14+build.21; 1.14.1 client 7194d1326cf796f62ac55f3cb56f851c232895639ae27510f3207d5a73f91814, Yarn 1.14.1+build.10; 1.14.2 client 9b740c9fd4955981125ebd3cd086baa8b4392963d043a3480b5b942a96637c25, Yarn 1.14.2+build.7; 1.14.3 client 4063062677433988e012fba53e42e4c21adb6919ac7f061f19f3c6e75b334e07, Yarn 1.14.3+build.13. Exact Yarn and Intermediary artifact hashes are in each mapping-local provenance file. For 1.14–1.14.2, StatusEffects.java:72 confirms field_5902 = register(25, "levitation", ...); its file hash is identical across those three releases: 7beb86f7453a88422f2d445c1a1565d0f391c92c4926342202502776afef07f2. 1.14.3 named LEVITATION registration is StatusEffects.java:78.
- Mojmap releases with top-level ready metadata: the metadata identifies exact version, mojmap, source-manifest hash and client SHA-256. Checked aliases are 1.14.4, 1.15, 1.15.1, 1.15.2, 1.16, 1.16.1, 1.16.2, 1.16.5, 1.17.1, 1.18, 1.18.1, 1.18.2, and 1.19–1.19.4. Their exact metadata is in decompiled_minecraft/<version>/mojmap.ready.json; no input was regenerated.
- Mojmap nested-provenance aliases: 1.16.3 client SHA-256 da85197babd989a7de318774cf5d97bcef95e89a70c9fa9a4c8180463e9589fa, mapping input client.txt SHA-256 c41c58faaf3a3b651d8883b9b1df355cb6ad59c4d6296b2e1ffc3dfe2bf9be3e; 1.16.4 client d6740464ee2bdf37d285c5d368d2e16497492427fb5783e2db3482bbf0079ed0, client.txt 93f188a0038fec8c2402d78e7acca983680978d6a9237c1fab80f22caddb00fe; 1.17 client 14122287d4e4256c2d65ec8c0ccfa3b56448495fa3ccabc87d0f0eeb4fa1c30f, client.txt 191b957aaa40b294402feef29651ae7a9f1263ec22c5382f3b8fa0a4918b0471.


Exact Mojmap ready/nested identities for the covered aliases are transcribed below as source-manifest SHA-256 / client SHA-256. Each ready record also pins the exact version ID and mapping family; the nested cases list their mapping artifact pins above.

| Release | Source manifest SHA-256 | Client SHA-256 |
|---|---|---|
| 1.14.4 | af98406f3d4ed31494fbdce1078d1f3df3f712501878f9a0e9ad5879bdf23c3b | b3b2a798e2d67b566008fe4a03767ae2c7ff3f8c7ba6751e7b71fc7299672d0a |
| 1.15 | 251a3019ccc3fd067f66365f3545b69a983383a28b19a1038debf1791be29340 | 41eee66b4c8e76c018745a63a46a15097cc1c6482c94b38801054938a010b21b |
| 1.15.1 | c8fdce2f9432b72fc13c19a48f9ab3be203cac41a617139e81bac3552245912d | 6e28ecd008d0ca5e1dcd346dbc2882430f02a77a78864d57acc49fc8bc1b5358 |
| 1.15.2 | cb7fd93f8730d8b2813857744f8572060535c7de44fcee86222acd1ceb6f43e7 | 4a73008a73f3824b7c711750a5a37556df8614f193c0a531e292dad159a73a7c |
| 1.16 | e3c23e94ac985c25dec0c768aed17b443ba4823662cf1e152235c99756ae5d4a | 00740ef4475d4cd0a1d7064f89602f759c45ff9afe29da9b0ee0b9037ebb2a71 |
| 1.16.1 | 76fdd121070c2012f48cbdf5cf0c78490116afc94117c30b0858eedcd6ab9754 | b4831e7b63b10588ff06ea86322108d916c536446f3aea3ae988b7fe3e1f66ef |
| 1.16.2 | 10d312ca29ee48e25f4727adafb9fcfbd741dcebe81b343d3dcffa071952a7a7 | 19960f4e3c60f2723f361844d2b4020b8bf2160bd91a1e1efde8ce5aea4ff73d |
| 1.16.5 | 9499f2611d0e6dde37cb635f1a28a591416382d6bc188b37d5f99e2b2a20823b | 00b5ebbc33e95ea88c1ab80601599c9827e9aa861d93ccc2cfcbbfd996e86263 |
| 1.17.1 | 93270d229acfb751bf56daf1e7be26ce3dcff26b29e94aa157de621405a3463b | a49b4a56c5bbe15c9ed9fe53efa9a591f265a1f5ba7d6aa9739a58ea7a92b79d |
| 1.18 | a43c61223ddedbdd8ede4d2daf6a750a58cbba324b30c025429ee1030cfc0d78 | 381e090ef22584526113bcccf26ae38a310a22094d14c2d0dec72b71fdf65aaa |
| 1.18.1 | 52aa98450b5cfa55ac2bd2054f108e16df7fa939a3f061991b6a9adb434d8d2d | 737c28a56c8980fcd8574465c61538ebde8f511bdd035c69d0cbe27953f892aa |
| 1.18.2 | aea0cb9c6fc8f7a46f0eb82b0388ad58a4659f513be6c0a2be06c0df0c1eb07a | 1d09e3639644b6b2254499469d0765cc005a286d19f3fa595b0ed8fb07971ec7 |
| 1.19 | 78bf3f9d5b4b5437240dd0707fa7a9ff56e6d1e267bd8edc13adc8313b535e26 | ffd4fb3037d4110085b4d3d2dc47b760695528eb14292c3e5f7c758983c6d764 |
| 1.19.1 | 25a8e35111971be21a85fefd567ada685098f45e35e431163afdbe0fa66dfde1 | 146d4018cfed50c5e77483940653091f186d2eea9ad42fc40939f5c72d6b7f52 |
| 1.19.2 | 95bc354ac219b9fe1ca8bf45053edad0119a75323d576902d59616a11647aac2 | e1ac65de9b471b6916cc457fdcff00c1bafac17027aa79100c4df893b3d956db |
| 1.19.3 | 6f1cc6d07ab3902a7ea25a3f8817504723be70d82e97f541cffda95ea2c1c843 | b7228c23dbc8988129561af3918dd469577de842d2eb3c7dabe00316bf9a44d6 |
| 1.19.4 | 6286e325371e085dfee1ab7987e66d4b6d1a984a49b6ec8ea730045cd24cecb3 | 0e79cf7f07c107e9a1fe22ed703e472372b44149e64114944f0811abbd25f3ec |

Nested-provenance Mojmap aliases not represented by ready rows above: 1.16.3 pins client.jar da85197babd989a7de318774cf5d97bcef95e89a70c9fa9a4c8180463e9589fa and client.txt c41c58faaf3a3b651d8883b9b1df355cb6ad59c4d6296b2e1ffc3dfe2bf9be3e; 1.16.4 pins client.jar d6740464ee2bdf37d285c5d368d2e16497492427fb5783e2db3482bbf0079ed0 and client.txt 93f188a0038fec8c2402d78e7acca983680978d6a9237c1fab80f22caddb00fe; 1.17 pins client.jar 14122287d4e4256c2d65ec8c0ccfa3b56448495fa3ccabc87d0f0eeb4fa1c30f and client.txt 191b957aaa40b294402feef29651ae7a9f1263ec22c5382f3b8fa0a4918b0471. Each mapping-local source hash matches its embedded provenance record.
The supplied accepted endpoint comparison development/research/source-evidence/20261117/block-c-player-scope/fall-distance-effects/comparison.md SHA-256 was verified as 4cd09d4f9e8980a3cd663c9ae50b320013af1806a69c1f2823243c4b76a52663. Its accepted 1.19.4 endpoint is retained unchanged; the accepted 1.20 common pre-travel timing partition is outside this continuity interval and also unchanged.

## Bounded unresolved items

No unresolved source or identity gap remains for the requested stable alias range and writer. Exact patch releases outside the requested stable aliases are not claimed. This report does not reopen the already accepted introduction or timing partitions and does not assess riding, gliding, water/lava, other resets, damage, non-player/server behavior, runtime parity, or unrelated travel math. No product files were read or written, and no source was generated, built, tested, or run.
