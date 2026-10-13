# Sprint air-speed lookup timing

1.19.3, mojmap: `1.19.3/mojmap/net/minecraft/world/entity/LivingEntity.java`, handleRelativeFrictionAndCalculateMovement 2201-2248.
1.19.4, mojmap: `1.19.4/mojmap/net/minecraft/world/entity/LivingEntity.java`, handleRelativeFrictionAndCalculateMovement 2152-2203.
1.19.3, mojmap: `1.19.3/mojmap/net/minecraft/client/player/LocalPlayer.java`, aiStep 652-819.
1.19.4, mojmap: `1.19.4/mojmap/net/minecraft/client/player/LocalPlayer.java`, aiStep 652-819.
1.19.3, mojmap: `1.19.3/mojmap/net/minecraft/world/entity/player/Player.java`, aiStep 496-517.
1.19.4, mojmap: `1.19.4/mojmap/net/minecraft/world/entity/player/Player.java`, aiStep 499-510; getFlyingSpeed 2112-2118.

Difference: 1.19.3 travel reads flyingSpeed stored only after the preceding travel; 1.19.4 getFlyingSpeed reads the current sprint flag during this travel.

Movement meaning: Sprint transitions affect old cached air acceleration one travel call later; arithmetic-value difference is separately recorded.


## Current evidence and remaining work

Source decisions, implementation coverage and runtime parity are separate. Source paths are relative to `decompiled_minecraft/`; immutable Git evidence can be read with `git show <revision>:<path>`.

```json
{
  "implementation": {
    "status": "partial",
    "changes": [
      "src/main/java/me/wolfii/legacyparkourcompat/change/v1_18_2/AirSpeed.java"
    ],
    "hooks": [
      "AirSpeedHook",
      "AirSpeedUpdateHook"
    ],
    "limits": [
      "Same cached field and float sum exist; V1_18_2 registration does not cover exact 1.19.3 endpoint. Boundary/continuity remains separate. Registration establishes static correspondence only; no runtime parity or renewed applicability acceptance."
    ]
  },
  "applicability": [],
  "sources": [
    {
      "version": "1.19.3",
      "mapping": "mojmap",
      "path": "1.19.3/mojmap/net/minecraft/world/entity/player/Player.java",
      "member": "aiStep",
      "lines": "496-517",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 02e64e197aec3f4f2a8abc9c5ce4545121ac5bcbbc71592ce478601a0db17203; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: f72a4675ae77fa16966a0338567e30ee3a64bb7476932e264e8b25937f664a1b *1.19.3/client_mappings.txt. Canonical source-manifest SHA-256 6f1cc6d07ab3902a7ea25a3f8817504723be70d82e97f541cffda95ea2c1c843 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.19.4",
      "mapping": "mojmap",
      "path": "1.19.4/mojmap/net/minecraft/world/entity/player/Player.java",
      "member": "aiStep",
      "lines": "499-510",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 5e4436afccb361156f8184e7a5cfd91d5b12dcfe8f5937b4ac23dd3edda737a2; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: 5ef270b938f89cfc77371e0deee9f9044c41d9a373fa11dcb1fd1923272c4606 *1.19.4/client_mappings.txt. Canonical source-manifest SHA-256 6286e325371e085dfee1ab7987e66d4b6d1a984a49b6ec8ea730045cd24cecb3 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.19.4",
      "mapping": "mojmap",
      "path": "1.19.4/mojmap/net/minecraft/world/entity/player/Player.java",
      "member": "getFlyingSpeed",
      "lines": "2112-2118",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 5e4436afccb361156f8184e7a5cfd91d5b12dcfe8f5937b4ac23dd3edda737a2; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: 5ef270b938f89cfc77371e0deee9f9044c41d9a373fa11dcb1fd1923272c4606 *1.19.4/client_mappings.txt. Canonical source-manifest SHA-256 6286e325371e085dfee1ab7987e66d4b6d1a984a49b6ec8ea730045cd24cecb3 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.19.3",
      "mapping": "mojmap",
      "path": "1.19.3/mojmap/net/minecraft/world/entity/LivingEntity.java",
      "member": "handleRelativeFrictionAndCalculateMovement",
      "lines": "2201-2248",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 2b8befdc406176e01672175451dd480b14da0b52bfe11a982d368909c05e73db; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: f72a4675ae77fa16966a0338567e30ee3a64bb7476932e264e8b25937f664a1b *1.19.3/client_mappings.txt. Canonical source-manifest SHA-256 6f1cc6d07ab3902a7ea25a3f8817504723be70d82e97f541cffda95ea2c1c843 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.19.4",
      "mapping": "mojmap",
      "path": "1.19.4/mojmap/net/minecraft/world/entity/LivingEntity.java",
      "member": "handleRelativeFrictionAndCalculateMovement",
      "lines": "2152-2203",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 c8d91af61f87aaa1666de79696d7cd9d4a8212d05f873bdaf28d7bb2926bf165; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: 5ef270b938f89cfc77371e0deee9f9044c41d9a373fa11dcb1fd1923272c4606 *1.19.4/client_mappings.txt. Canonical source-manifest SHA-256 6286e325371e085dfee1ab7987e66d4b6d1a984a49b6ec8ea730045cd24cecb3 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.19.3",
      "mapping": "mojmap",
      "path": "1.19.3/mojmap/net/minecraft/client/player/LocalPlayer.java",
      "member": "aiStep",
      "lines": "652-819",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 64b670ee323d195b3928fb8ea629c26d2560e75a17379a12c376a7bc686d5479; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: f72a4675ae77fa16966a0338567e30ee3a64bb7476932e264e8b25937f664a1b *1.19.3/client_mappings.txt. Canonical source-manifest SHA-256 6f1cc6d07ab3902a7ea25a3f8817504723be70d82e97f541cffda95ea2c1c843 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.19.4",
      "mapping": "mojmap",
      "path": "1.19.4/mojmap/net/minecraft/client/player/LocalPlayer.java",
      "member": "aiStep",
      "lines": "652-819",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 8e7da18f42d09fbb994f522c2b0e65fcb2bb83cabb21024360299d44d9674c58; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: 5ef270b938f89cfc77371e0deee9f9044c41d9a373fa11dcb1fd1923272c4606 *1.19.4/client_mappings.txt. Canonical source-manifest SHA-256 6286e325371e085dfee1ab7987e66d4b6d1a984a49b6ec8ea730045cd24cecb3 matches archived publication; cited file hash is present in that manifest."
    }
  ],
  "sourceReviews": [],
  "openIssues": [],
  "runtime": "unverified"
}
```
