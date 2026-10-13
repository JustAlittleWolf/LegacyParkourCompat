# F-02: auto-jump probe uses a different inverse-square-root approximation

1.19.3, mojmap: `1.19.3/mojmap/net/minecraft/client/player/LocalPlayer.java`, updateAutoJump 911-1007; move 900-905.
1.19.4, mojmap: `1.19.4/mojmap/net/minecraft/client/player/LocalPlayer.java`, updateAutoJump 891-987; updateAutoJump 900-905; updateAutoJump 911.
1.19.3, mojmap: `1.19.3/mojmap/net/minecraft/util/Mth.java`, fastInvSqrt 495-501.
1.19.4, mojmap: `1.19.4/mojmap/net/minecraft/util/Mth.java`, invSqrt 387-389.

Difference: The methods normalize a horizontal direction and derive a forward probe distance before querying collision boxes and shapes. 1.19.3 uses the approximate `Mth.fastInvSqrt(float)` bit-hack plus a Newton step. 1.19.4 delegates to JOML's reciprocal square root implementation, which takes `Math.sqrt` and rounds the result to `float` before float division. The factor is used in probe endpoints and its reciprocal in search distance. The 1.19.3 `BlockPos(double,double,double)` conversion was replaced by 1.19.4 `BlockPos.containing(double,double,double)`; both call `Mth.floor` for each coordinate, so that replacement is checked as equivalent for this slice.

Movement meaning: `LocalPlayer#move` -> `updateAutoJump` -> inverse-square-root factor -> forward probe endpoints/distance -> world collision-box and block-shape queries. When the probe accepts a gap, it writes `autoJumpTime = 1`; local tick consumes that timer to schedule a jump. The gates above bound reachability. Source proves the normalization algorithm changed in a reachable auto-jump collision probe. This can change probe endpoints or distance and therefore a threshold/query result.


## Current evidence and remaining work

Source decisions, implementation coverage and runtime parity are separate. Source paths are relative to `decompiled_minecraft/`; immutable Git evidence can be read with `git show <revision>:<path>`.

```json
{
  "implementation": {
    "status": "partial",
    "changes": [
      "src/main/java/me/wolfii/legacyparkourcompat/change/v1_18_2/AutoJumpVectorNormalization.java"
    ],
    "hooks": [
      "AutoJumpInverseSqrtHook"
    ],
    "limits": [
      "AutoJumpInverseSqrtHook implements old bit-hack/Newton arithmetic; registration at V1_18_2 leaves exact 1.19.3 continuity unresolved. Registration establishes static correspondence only; no runtime parity or renewed applicability acceptance."
    ]
  },
  "applicability": [],
  "sources": [
    {
      "version": "1.19.3",
      "mapping": "mojmap",
      "path": "1.19.3/mojmap/net/minecraft/client/player/LocalPlayer.java",
      "member": "updateAutoJump",
      "lines": "911-1007",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 64b670ee323d195b3928fb8ea629c26d2560e75a17379a12c376a7bc686d5479; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: f72a4675ae77fa16966a0338567e30ee3a64bb7476932e264e8b25937f664a1b *1.19.3/client_mappings.txt. Canonical source-manifest SHA-256 6f1cc6d07ab3902a7ea25a3f8817504723be70d82e97f541cffda95ea2c1c843 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.19.3",
      "mapping": "mojmap",
      "path": "1.19.3/mojmap/net/minecraft/util/Mth.java",
      "member": "fastInvSqrt",
      "lines": "495-501",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 f5b2738a7145303f8598b9876c0f6950e853110f594fc4cffa633ab7bab98d3f; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: f72a4675ae77fa16966a0338567e30ee3a64bb7476932e264e8b25937f664a1b *1.19.3/client_mappings.txt. Canonical source-manifest SHA-256 6f1cc6d07ab3902a7ea25a3f8817504723be70d82e97f541cffda95ea2c1c843 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.19.4",
      "mapping": "mojmap",
      "path": "1.19.4/mojmap/net/minecraft/client/player/LocalPlayer.java",
      "member": "updateAutoJump",
      "lines": "891-987",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 8e7da18f42d09fbb994f522c2b0e65fcb2bb83cabb21024360299d44d9674c58; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: 5ef270b938f89cfc77371e0deee9f9044c41d9a373fa11dcb1fd1923272c4606 *1.19.4/client_mappings.txt. Canonical source-manifest SHA-256 6286e325371e085dfee1ab7987e66d4b6d1a984a49b6ec8ea730045cd24cecb3 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.19.4",
      "mapping": "mojmap",
      "path": "1.19.4/mojmap/net/minecraft/util/Mth.java",
      "member": "invSqrt",
      "lines": "387-389",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 201d17ea024637036761c2703e407d4ab1dc7651a1f4b00a857c09803754c797; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: 5ef270b938f89cfc77371e0deee9f9044c41d9a373fa11dcb1fd1923272c4606 *1.19.4/client_mappings.txt. Canonical source-manifest SHA-256 6286e325371e085dfee1ab7987e66d4b6d1a984a49b6ec8ea730045cd24cecb3 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.19.3",
      "mapping": "mojmap",
      "path": "1.19.3/mojmap/net/minecraft/client/player/LocalPlayer.java",
      "member": "move",
      "lines": "900-905",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 64b670ee323d195b3928fb8ea629c26d2560e75a17379a12c376a7bc686d5479; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: f72a4675ae77fa16966a0338567e30ee3a64bb7476932e264e8b25937f664a1b *1.19.3/client_mappings.txt. Canonical source-manifest SHA-256 6f1cc6d07ab3902a7ea25a3f8817504723be70d82e97f541cffda95ea2c1c843 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.19.4",
      "mapping": "mojmap",
      "path": "1.19.4/mojmap/net/minecraft/client/player/LocalPlayer.java",
      "member": "updateAutoJump",
      "lines": "900-905",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 8e7da18f42d09fbb994f522c2b0e65fcb2bb83cabb21024360299d44d9674c58; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: 5ef270b938f89cfc77371e0deee9f9044c41d9a373fa11dcb1fd1923272c4606 *1.19.4/client_mappings.txt. Canonical source-manifest SHA-256 6286e325371e085dfee1ab7987e66d4b6d1a984a49b6ec8ea730045cd24cecb3 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.19.4",
      "mapping": "mojmap",
      "path": "1.19.4/mojmap/net/minecraft/client/player/LocalPlayer.java",
      "member": "updateAutoJump",
      "lines": "911",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 8e7da18f42d09fbb994f522c2b0e65fcb2bb83cabb21024360299d44d9674c58; matches an explicit archived source hash. Reassociated the archived range with its actual owning class/member; original multi-class paragraph remains immutable. Official exact client-mapping manifest binding: 5ef270b938f89cfc77371e0deee9f9044c41d9a373fa11dcb1fd1923272c4606 *1.19.4/client_mappings.txt. Canonical source-manifest SHA-256 6286e325371e085dfee1ab7987e66d4b6d1a984a49b6ec8ea730045cd24cecb3 matches archived publication; cited file hash is present in that manifest."
    }
  ],
  "sourceReviews": [],
  "openIssues": [],
  "runtime": "unverified"
}
```
