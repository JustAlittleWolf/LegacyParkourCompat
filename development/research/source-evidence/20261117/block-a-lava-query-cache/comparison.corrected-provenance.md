# Corrected provenance companion: 1.14.1–1.14.3 Yarn

This companion supersedes only the provenance caveat in `comparison.md`. It does not change the comparison or the finding draft. The audit was read from the authorized immutable input `lane-lava-cache-audit/verdict.md` (SHA-256 `8a3ed143f3aae478fb202f2243ee37676b82d4ffdac30f8f6b422d0911402fb8`).

## Correction

The earlier statement that exact 1.14.1–1.14.3 Yarn source and mapping identities could not be authenticated was incorrect. Each source root contains `decompiled_minecraft/<version>/yarn/.decompile-provenance.json`. Those embedded records pin the mapping inputs, raw client and metadata inputs, decompiler/tool inputs, and source path-to-SHA-256 map. All cited movement source hashes below were re-computed from the local source files and match those embedded maps.

The three roots have no separate version-root `.ready.json`, `.sources.sha256`, or `artifacts.sha256`; the in-tree provenance is their source and artifact identity record. There is no remaining identity blocker for these requested patch sources.

## Exact inputs and identity records

| Release | Yarn mapping input | Intermediary input | Raw client input | Metadata pins | Embedded provenance SHA-256 |
|---|---|---|---|---|---|
| 1.14.1 | `net.fabricmc:yarn:1.14.1+build.10`; URL `https://maven.fabricmc.net/net/fabricmc/yarn/1.14.1+build.10/yarn-1.14.1+build.10-v2.jar`; SHA-256 `f9462af25135abeb22cbdec829cc8618b1a8dd902f17259abfbe7399a222a630` | `net.fabricmc:intermediary:1.14.1-v2`; SHA-256 `486f4e72a87d697c5d9e155a49158bec28117faa73e3f1201c3f8a616b42569d` | `https://piston-data.mojang.com/v1/objects/55ba86ddcbc3579397f41910463ffd4056e1e523/client.jar`; SHA-256 `7194d1326cf796f62ac55f3cb56f851c232895639ae27510f3207d5a73f91814` | Yarn metadata `https://meta.fabricmc.net/v2/versions/yarn/1.14.1`: `6d026a20d6165444f67df52ddcc29f575f0267ab4b545c3bb427e5803c434873`; version JSON SHA-256 `71c39840d012fcbe8fedb7be876bd008365b62ec7b1c9c5c7bd34d9a29c8ca22` | `308537c5adec3104c5ca243eb5010554f00978635ed74f0d7801c804dd4d4907` |
| 1.14.2 | `net.fabricmc:yarn:1.14.2+build.7`; URL `https://maven.fabricmc.net/net/fabricmc/yarn/1.14.2+build.7/yarn-1.14.2+build.7-v2.jar`; SHA-256 `3c91c928d10797ba688b1f7e61063fd4bc56a3ee0f66fa2609ef960ec08b239b` | `net.fabricmc:intermediary:1.14.2-v2`; SHA-256 `f73883d007c64858ae0f26533fe47ec5d3a051cede6892c8d6531fc5122e459e` | `https://piston-data.mojang.com/v1/objects/ca6c5a139045967229975c0c0b7f93e78b4314c2/client.jar`; SHA-256 `9b740c9fd4955981125ebd3cd086baa8b4392963d043a3480b5b942a96637c25` | Yarn metadata `https://meta.fabricmc.net/v2/versions/yarn/1.14.2`: `a65478802722f65b4dfec15e8a732872f2836e1e9b7ae95d7086c601b07f1d81`; version JSON SHA-256 `237aa8d499b71bb17122cbee40b8748efcd5ac9d8362c68c89e99ab0703e66b8` | `786546f99684423cc2563be45c9c3334dfcc2c7db58ddb74ba39ce403bd0869a` |
| 1.14.3 | `net.fabricmc:yarn:1.14.3+build.13`; URL `https://maven.fabricmc.net/net/fabricmc/yarn/1.14.3+build.13/yarn-1.14.3+build.13-v2.jar`; SHA-256 `ad9e77f907cfc6780fb348ad4b3a948ffc8cea3bfe50abcc792428645e49e06e` | `net.fabricmc:intermediary:1.14.3-v2`; SHA-256 `f2e9990c00fa87ddc7158a95afb735a947ae26d1997b52916226e11c612542a9` | `https://piston-data.mojang.com/v1/objects/af100b34ec7ef2b8b9cf7775b544d21d690dddec/client.jar`; SHA-256 `4063062677433988e012fba53e42e4c21adb6919ac7f061f19f3c6e75b334e07` | Yarn metadata `https://meta.fabricmc.net/v2/versions/yarn/1.14.3`: `85ad2c9e3ff892f5a6d5b774b869ed6b6dbdd26de9a46d1a37b5c739e73c8766`; version JSON SHA-256 `ebf2df95d9bd83c6687f287d5a6f5f41f74bc1b39159fcbb74ff6d1d19d8464c` | `81ea0021eb122d4e3340e20d47d6ecb7079365ba767a038d26a8264cddacc28a` |

## Reviewed source-file pin checks

Each value below is the SHA-256 embedded under that relative path in the version's `.decompile-provenance.json`; each was independently recomputed from the local file and matched.

| Release | Entity.java | FluidBlock.java | LivingEntity.java | PlayerEntity.java | ClientPlayerEntity.java | BlockPos.java |
|---|---|---|---|---|---|---|
| 1.14.1 | `ce429699f2d8f0a54f4be516783a474c6c7808572950164508ceed0baf68ed5b` | `36fd9cd79c782357c2602917857bc2bdc05bb1d05fa6e1038f9a337028ca53b0` | `331e2d7dfa936a6f7197910ba374c4a60b479c8565ff7c64a4d6a9e1e8b52dda` | `ebda4ad25a265f8c85e88345efcce753b78e248a29a9b910c5cfd311a4a69319` | `6af7e75e80619ad77ce2c84692136b0a1d562a44d0e7910dcc3f6040b2dc9933` | `cb812b182cb0cb87eb1ab82307922d4bf4513837cd85f9c76dd1ed0ce509777a` |
| 1.14.2 | `7c0e9cab0842a45e42fa9f27c9661de3fd0fd076c35ff989aa0477198eb38edc` | `04daaa27738790478fe5fe98398262e5c8799d2afae872f9483a0ca397ceca67` | `e8224e7df98ccf0986bb390f35e64293263d17b9ab1dbdfeb661c65d28b6ee78` | `333bb39db18af4cef320eb73b7abe359d438fb2a6ca7d8798ee449da6c684398` | `90c96701849c9212ea7714f02e7bc9011d0a7d0aaf1f7b46464497ba3ca14947` | `cb812b182cb0cb87eb1ab82307922d4bf4513837cd85f9c76dd1ed0ce509777a` |
| 1.14.3 | `9b204a5f9af6ca50c09db4b6185ff37c39980c1a8cd1b5fd004713ecffae5c14` | `d238a66239b9b27df18a210fea47784ca0338f542241efc79b021e90cbd05424` | `e8aed9c63c0dfbe88e4b9e11c58881bc6e04b9cbaecbde5e3606e3afa406d72b` | `e3f361ac4e369d01473f3d84044ee33f9685d182c4485e2e80b7f62756950963` | `28b6b4031e02afd4a2e6b4a80e5a7ca06f16dac42ca0f92cab25e2c2dc9e14ae` | `a647a0a2f8364f107ae0afdc48920406c2624a203644c4fa5065c331f5a8e1de` |

The source scan remained static and read-only. No primary-checkout files, prior comparison bytes, finding bytes, Git state, builds, tests, or runtime state were changed or examined during this correction.
