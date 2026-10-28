# Autojump input-trigonometry lookup acceptance matrix

## Accepted scope

This immutable matrix accepts only whether the autojump consumer directly calls the input/yaw trig helpers and the source identity of that consumer for the enumerated decompilation releases. It records no gate, trajectory, geometry, or product-history conclusions.

The lookup form is `SINE_TABLE[(int)(angle * 10430.378F) & 65535]`; cosine adds `16384.0F` before the cast and mask. The caller forms the lookup angle as `yaw * (float)(Math.PI / 180.0)`. This is a float table lookup with truncating float-to-int conversion and a 16-bit mask. The exact helper form is present in the audited anchors `1.10/ornithe-feather/net/minecraft/util/math/MathHelper.java:15-20`, `1.14.1/yarn/net/minecraft/util/math/MathHelper.java` and `1.21.10/mojmap/net/minecraft/util/Mth.java:48-53`.

The canonical source root is `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft`. Form each full consumer path by joining that root, the `Release` value, and the `Consumer source suffix` below. Each `Direct call` entry means the listed autojump consumer directly calls both trig helpers for the fallback. SHA-256 values are for the exact consumer Java file at that path.

Identity codes:

- **Ready/manifests**: the release has ready and source-manifest sidecars; the recorded consumer SHA-256 was found in the source manifest.
- **Older provenance; run details missing**: hidden `.decompile-provenance.json` pins the consumer source hash in its `sources` map, but this older schema does not record an explicit invocation command, batch-version list, or cache-directory field.

No consumer in this matrix is classified as unverified. The 1.10, 1.10.1, 1.12, 1.12.1, 1.14.1-1.14.3, 1.16.3-1.16.4, 1.17, 1.20, 1.20.3, 1.21, 1.21.2, 1.21.6-1.21.7, and 1.21.9 rows use the older-provenance classification; all remaining consumer rows use ready/manifests.

| Release | Consumer source suffix | Direct call | Consumer SHA-256 | Source identity |
|---|---|---|---|---|
| 1.10 | `ornithe-feather/net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java` | Yes — `MathHelper.sin/cos` | `a9637065f21ad67464eb5c204c74ebf228c3bb0da8a96ddf4ae73c0490fed443` | Older provenance; run details missing |
| 1.10.1 | `ornithe-feather/net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java` | Yes — `MathHelper.sin/cos` | `a9637065f21ad67464eb5c204c74ebf228c3bb0da8a96ddf4ae73c0490fed443` | Older provenance; run details missing |
| 1.10.2 | `ornithe-feather/net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java` | Yes — `MathHelper.sin/cos` | `a9637065f21ad67464eb5c204c74ebf228c3bb0da8a96ddf4ae73c0490fed443` | Ready/manifests |
| 1.11 | `ornithe-feather/net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java` | Yes — `MathHelper.sin/cos` | `65c2747bd8c70def6be7f41f624d4c9493342b39ae7bed7967f9ff63608f59ed` | Ready/manifests |
| 1.11.1 | `ornithe-feather/net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java` | Yes — `MathHelper.sin/cos` | `65c2747bd8c70def6be7f41f624d4c9493342b39ae7bed7967f9ff63608f59ed` | Ready/manifests |
| 1.11.2 | `ornithe-feather/net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java` | Yes — `MathHelper.sin/cos` | `65c2747bd8c70def6be7f41f624d4c9493342b39ae7bed7967f9ff63608f59ed` | Ready/manifests |
| 1.12 | `ornithe-feather/net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java` | Yes — `MathHelper.sin/cos` | `d7237b8b9a68525bbfec6af1c234d2aafb1e4aa7d52059331d1d243313d6d7d1` | Older provenance; run details missing |
| 1.12.1 | `ornithe-feather/net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java` | Yes — `MathHelper.sin/cos` | `d7237b8b9a68525bbfec6af1c234d2aafb1e4aa7d52059331d1d243313d6d7d1` | Older provenance; run details missing |
| 1.12.2 | `ornithe-feather/net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java` | Yes — `MathHelper.sin/cos` | `01a58e94d8c6ff98a8e3794227cdc76a5fcbdbad795c70c9cf28854aff9823cc` | Ready/manifests |
| 1.13 | `ornithe-feather/net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java` | Yes — `MathHelper.sin/cos` | `a188e1bc87831dd9da1ec1366a49acc4104c18e6979129097d02d5d3617e6a08` | Ready/manifests |
| 1.13.1 | `ornithe-feather/net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java` | Yes — `MathHelper.sin/cos` | `2583495f3a02b4791aa036e6a8d354d7596c4984761969a0f29b29d8d9bf42bf` | Ready/manifests |
| 1.13.2 | `ornithe-feather/net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java` | Yes — `MathHelper.sin/cos` | `2583495f3a02b4791aa036e6a8d354d7596c4984761969a0f29b29d8d9bf42bf` | Ready/manifests |
| 1.14 | `ornithe-feather/net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java` | Yes — `MathHelper.sin/cos` | `2b1ad3b3416949a9da2607a3ec2251aa69b2d9eebd76638c92e20749d625338f` | Ready/manifests |
| 1.14.1 | `yarn/net/minecraft/client/network/ClientPlayerEntity.java` | Yes — `MathHelper.sin/cos` | `6af7e75e80619ad77ce2c84692136b0a1d562a44d0e7910dcc3f6040b2dc9933` | Older provenance; run details missing |
| 1.14.2 | `yarn/net/minecraft/client/network/ClientPlayerEntity.java` | Yes — `MathHelper.sin/cos` | `90c96701849c9212ea7714f02e7bc9011d0a7d0aaf1f7b46464497ba3ca14947` | Older provenance; run details missing |
| 1.14.3 | `yarn/net/minecraft/client/network/ClientPlayerEntity.java` | Yes — `MathHelper.sin/cos` | `28b6b4031e02afd4a2e6b4a80e5a7ca06f16dac42ca0f92cab25e2c2dc9e14ae` | Older provenance; run details missing |
| 1.14.4 | `mojmap/net/minecraft/client/player/LocalPlayer.java` | Yes — `Mth.sin/cos` | `0795c1223198ce5acf5d2ed9e5db8435bbec4cd52b96f96b1ddf2865baaae85f` | Ready/manifests |
| 1.15 | `mojmap/net/minecraft/client/player/LocalPlayer.java` | Yes — `Mth.sin/cos` | `7227da5233e3c72ced008187b92a6a86842f84778eaa9f40fa1eb117286af823` | Ready/manifests |
| 1.15.1 | `mojmap/net/minecraft/client/player/LocalPlayer.java` | Yes — `Mth.sin/cos` | `7227da5233e3c72ced008187b92a6a86842f84778eaa9f40fa1eb117286af823` | Ready/manifests |
| 1.15.2 | `mojmap/net/minecraft/client/player/LocalPlayer.java` | Yes — `Mth.sin/cos` | `3a9019bd7b860e251c23fd8d0cd70b7f5b38566d34470c4e29b1014ef689ccbd` | Ready/manifests |
| 1.16 | `mojmap/net/minecraft/client/player/LocalPlayer.java` | Yes — `Mth.sin/cos` | `ee20523adfdb82f3753fa43312af170f34c4f4aa78570631c61bc8c0820dfb8b` | Ready/manifests |
| 1.16.1 | `mojmap/net/minecraft/client/player/LocalPlayer.java` | Yes — `Mth.sin/cos` | `ee20523adfdb82f3753fa43312af170f34c4f4aa78570631c61bc8c0820dfb8b` | Ready/manifests |
| 1.16.2 | `mojmap/net/minecraft/client/player/LocalPlayer.java` | Yes — `Mth.sin/cos` | `6011569e766bb1568609147be9aa14e9c08c51948e3d3a60fd066e848f6a8c2b` | Ready/manifests |
| 1.16.3 | `mojmap/net/minecraft/client/player/LocalPlayer.java` | Yes — `Mth.sin/cos` | `6011569e766bb1568609147be9aa14e9c08c51948e3d3a60fd066e848f6a8c2b` | Older provenance; run details missing |
| 1.16.4 | `mojmap/net/minecraft/client/player/LocalPlayer.java` | Yes — `Mth.sin/cos` | `6011569e766bb1568609147be9aa14e9c08c51948e3d3a60fd066e848f6a8c2b` | Older provenance; run details missing |
| 1.16.5 | `mojmap/net/minecraft/client/player/LocalPlayer.java` | Yes — `Mth.sin/cos` | `6011569e766bb1568609147be9aa14e9c08c51948e3d3a60fd066e848f6a8c2b` | Ready/manifests |
| 1.17 | `mojmap/net/minecraft/client/player/LocalPlayer.java` | Yes — `Mth.sin/cos` | `08dbb8615cc6c7776f35ce269223699d39f758d15786babfe11e497421c5f4d4` | Older provenance; run details missing |
| 1.17.1 | `mojmap/net/minecraft/client/player/LocalPlayer.java` | Yes — `Mth.sin/cos` | `c9a91cb6cb57806bc8d22e5bfe2d97daaf21d5d2a48f34a6e2c53164c61c5812` | Ready/manifests |
| 1.18 | `mojmap/net/minecraft/client/player/LocalPlayer.java` | Yes — `Mth.sin/cos` | `e38d0bbb6e5b9de2a698609b8491d0007406b3becd42ba9b95289f533788c996` | Ready/manifests |
| 1.18.1 | `mojmap/net/minecraft/client/player/LocalPlayer.java` | Yes — `Mth.sin/cos` | `e38d0bbb6e5b9de2a698609b8491d0007406b3becd42ba9b95289f533788c996` | Ready/manifests |
| 1.18.2 | `mojmap/net/minecraft/client/player/LocalPlayer.java` | Yes — `Mth.sin/cos` | `99c2d18bcd23243afb8f95c5bafb21fb0be7ea04aacbb14fcf7be7ced2c9c095` | Ready/manifests |
| 1.19 | `mojmap/net/minecraft/client/player/LocalPlayer.java` | Yes — `Mth.sin/cos` | `c5f346728f551d278e88b457e54e539885e0d8e549ed7394887d090f28224805` | Ready/manifests |
| 1.19.1 | `mojmap/net/minecraft/client/player/LocalPlayer.java` | Yes — `Mth.sin/cos` | `36ae4aabd609b457fffb7a8b14abb50db9ac775857dde1774c0c68a8cf50deef` | Ready/manifests |
| 1.19.2 | `mojmap/net/minecraft/client/player/LocalPlayer.java` | Yes — `Mth.sin/cos` | `36ae4aabd609b457fffb7a8b14abb50db9ac775857dde1774c0c68a8cf50deef` | Ready/manifests |
| 1.19.3 | `mojmap/net/minecraft/client/player/LocalPlayer.java` | Yes — `Mth.sin/cos` | `64b670ee323d195b3928fb8ea629c26d2560e75a17379a12c376a7bc686d5479` | Ready/manifests |
| 1.19.4 | `mojmap/net/minecraft/client/player/LocalPlayer.java` | Yes — `Mth.sin/cos` | `8e7da18f42d09fbb994f522c2b0e65fcb2bb83cabb21024360299d44d9674c58` | Ready/manifests |
| 1.20 | `mojmap/net/minecraft/client/player/LocalPlayer.java` | Yes — `Mth.sin/cos` | `69a2d043d2c0595bd364d625a5445e4934d9e07fef020cd79fafb42f59952ac2` | Older provenance; run details missing |
| 1.20.1 | `mojmap/net/minecraft/client/player/LocalPlayer.java` | Yes — `Mth.sin/cos` | `69a2d043d2c0595bd364d625a5445e4934d9e07fef020cd79fafb42f59952ac2` | Ready/manifests |
| 1.20.2 | `mojmap/net/minecraft/client/player/LocalPlayer.java` | Yes — `Mth.sin/cos` | `bb5cbfb03656a1866bb77c00431618befe792081223b35db4bd121ecbb151fd5` | Ready/manifests |
| 1.20.3 | `mojmap/net/minecraft/client/player/LocalPlayer.java` | Yes — `Mth.sin/cos` | `bb5cbfb03656a1866bb77c00431618befe792081223b35db4bd121ecbb151fd5` | Older provenance; run details missing |
| 1.20.4 | `mojmap/net/minecraft/client/player/LocalPlayer.java` | Yes — `Mth.sin/cos` | `bb5cbfb03656a1866bb77c00431618befe792081223b35db4bd121ecbb151fd5` | Ready/manifests |
| 1.20.5 | `mojmap/net/minecraft/client/player/LocalPlayer.java` | Yes — `Mth.sin/cos` | `6b429dfa6e0681251ec985dda1627f808652a7bbe5b70dc85c8fa0fe0ed46ffa` | Ready/manifests |
| 1.20.6 | `mojmap/net/minecraft/client/player/LocalPlayer.java` | Yes — `Mth.sin/cos` | `6b429dfa6e0681251ec985dda1627f808652a7bbe5b70dc85c8fa0fe0ed46ffa` | Ready/manifests |
| 1.21 | `mojmap/net/minecraft/client/player/LocalPlayer.java` | Yes — `Mth.sin/cos` | `c555e68ac3c63ab9b4f9a9e31933e263b96350a2bc599d11a0de5928bc24b583` | Older provenance; run details missing |
| 1.21.1 | `mojmap/net/minecraft/client/player/LocalPlayer.java` | Yes — `Mth.sin/cos` | `c555e68ac3c63ab9b4f9a9e31933e263b96350a2bc599d11a0de5928bc24b583` | Ready/manifests |
| 1.21.2 | `mojmap/net/minecraft/client/player/LocalPlayer.java` | Yes — `Mth.sin/cos` | `fbd40f1f47adfa66dda9b15188e5dce82af3e8e8d7c3dd0543e602a354ad3fe0` | Older provenance; run details missing |
| 1.21.3 | `mojmap/net/minecraft/client/player/LocalPlayer.java` | Yes — `Mth.sin/cos` | `fbd40f1f47adfa66dda9b15188e5dce82af3e8e8d7c3dd0543e602a354ad3fe0` | Ready/manifests |
| 1.21.4 | `mojmap/net/minecraft/client/player/LocalPlayer.java` | Yes — `Mth.sin/cos` | `145686ebdc7f0d12a64070309073665eb8695b7e09911a723e86113a77d04611` | Ready/manifests |
| 1.21.5 | `mojmap/net/minecraft/client/player/LocalPlayer.java` | Yes — `Mth.sin/cos` | `f1fcfed4a938732361e7ad951f93e9b73b02320ee56f0b219e2b3c7acdbfa2ef` | Ready/manifests |
| 1.21.6 | `mojmap/net/minecraft/client/player/LocalPlayer.java` | Yes — `Mth.sin/cos` | `53f2a71a886b9c71853afe36f2df857f80a9bf1cd6ec4ca8604ccd7e238d89ee` | Older provenance; run details missing |
| 1.21.7 | `mojmap/net/minecraft/client/player/LocalPlayer.java` | Yes — `Mth.sin/cos` | `53f2a71a886b9c71853afe36f2df857f80a9bf1cd6ec4ca8604ccd7e238d89ee` | Older provenance; run details missing |
| 1.21.8 | `mojmap/net/minecraft/client/player/LocalPlayer.java` | Yes — `Mth.sin/cos` | `53f2a71a886b9c71853afe36f2df857f80a9bf1cd6ec4ca8604ccd7e238d89ee` | Ready/manifests |
| 1.21.9 | `mojmap/net/minecraft/client/player/LocalPlayer.java` | Yes — `Mth.sin/cos` | `9c1df00e2f8379b2c19577a3691fee28071d8925210be3d3df928b5352e367e1` | Older provenance; run details missing |
| 1.21.10 | `mojmap/net/minecraft/client/player/LocalPlayer.java` | Yes — `Mth.sin/cos` | `9c1df00e2f8379b2c19577a3691fee28071d8925210be3d3df928b5352e367e1` | Ready/manifests |

## Explicit excluded-version check

The checked 1.9.4 Java source tree has no `auto.?jump|autojump` match. Therefore no consumer path, direct trig call, or consumer hash is applicable there; the autojump input-trig consumer is absent in 1.9.4.
