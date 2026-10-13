# F-007: Auto-jump collision probe uses a different inverse-square-root calculation

1.18.2, mojmap: `1.18.2/mojmap/net/minecraft/client/player/LocalPlayer.java`, updateAutoJump 900-996.
1.19.4, mojmap: `1.19.4/mojmap/net/minecraft/client/player/LocalPlayer.java`, updateAutoJump 891-987.
1.18.2, mojmap: `1.18.2/mojmap/net/minecraft/util/Mth.java`, fastInvSqrt 485-491.
1.19.4, mojmap: `1.19.4/mojmap/net/minecraft/util/Mth.java`, invSqrt 387-389.

Difference: For the same float squared length, 1.18.2's `fastInvSqrt` approximate result and 1.19.4's JOML inverse-square-root result are produced by different binary32 paths. For example, at `$$6 = 0.01F`, 1.18.2's one-step approximation returns `9.982522F` (`0x411fb869`), while 1.19.4's JOML helper returns `10.0F` (`0x41200000`). `updateAutoJump` multiplies its horizontal vector by that result, then uses the normalized vector in the forward-dot cutoff, scan endpoint, and side-ray endpoints. The remainder of the inspected scan and trigger predicates is unchanged apart from floor-equivalent block-position construction.

Movement meaning: The vector supplied to the auto-jump facing and collision probes can differ numerically, which can shift their endpoints or boundary comparisons for inputs and collision geometry close to a probe threshold. Source confirms the numeric path change; this audit did not calculate a concrete block layout whose trigger outcome changes and did not run a trajectory.


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
      "version": "1.18.2",
      "mapping": "mojmap",
      "path": "1.18.2/mojmap/net/minecraft/client/player/LocalPlayer.java",
      "member": "updateAutoJump",
      "lines": "900-996",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 99c2d18bcd23243afb8f95c5bafb21fb0be7ea04aacbb14fcf7be7ced2c9c095; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: a2aa6ee1030bfef79e9b2e08e79de1637fdd7ecb5bf8891cf2e9a4b186042543 *1.18.2/client_mappings.txt."
    },
    {
      "version": "1.18.2",
      "mapping": "mojmap",
      "path": "1.18.2/mojmap/net/minecraft/util/Mth.java",
      "member": "fastInvSqrt",
      "lines": "485-491",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 32747c5b09fc184baae356e39a0088fd66c9f08f69d9e98b67c19e1de6f1bb2e; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: a2aa6ee1030bfef79e9b2e08e79de1637fdd7ecb5bf8891cf2e9a4b186042543 *1.18.2/client_mappings.txt."
    },
    {
      "version": "1.19.4",
      "mapping": "mojmap",
      "path": "1.19.4/mojmap/net/minecraft/client/player/LocalPlayer.java",
      "member": "updateAutoJump",
      "lines": "891-987",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 8e7da18f42d09fbb994f522c2b0e65fcb2bb83cabb21024360299d44d9674c58; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: 5ef270b938f89cfc77371e0deee9f9044c41d9a373fa11dcb1fd1923272c4606 *1.19.4/client_mappings.txt."
    },
    {
      "version": "1.19.4",
      "mapping": "mojmap",
      "path": "1.19.4/mojmap/net/minecraft/util/Mth.java",
      "member": "invSqrt",
      "lines": "387-389",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 201d17ea024637036761c2703e407d4ab1dc7651a1f4b00a857c09803754c797; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: 5ef270b938f89cfc77371e0deee9f9044c41d9a373fa11dcb1fd1923272c4606 *1.19.4/client_mappings.txt."
    }
  ],
  "sourceReviews": [],
  "openIssues": [],
  "runtime": "unverified"
}
```
