# F-13: Forward double-tap sprint activation loses its ground gate in 1.21.5

1.21.4, mojmap: `1.21.4/mojmap/net/minecraft/client/player/LocalPlayer.java`, aiStep 653-662.
1.21.5, mojmap: `1.21.5/mojmap/net/minecraft/client/player/LocalPlayer.java`, aiStep 693-703.
1.21.5, mojmap: `1.21.5/mojmap/net/minecraft/client/player/ClientInput.java`, hasForwardImpulse 17-19.

Difference: For a non-passenger local player whose current forward input and sprint eligibility gates pass, 1.21.5's forward double-tap path can arm and activate its seven-tick sprint trigger while airborne. 1.21.4 requires the player to be on the ground or underwater to enter that double-tap path. The separate held-sprint-key branch is not this finding: 1.21.4 has that branch outside the ground/underwater double-tap guard.

Movement meaning: `LocalPlayer#aiStep()` samples the prior forward state, ticks local input, evaluates sprint eligibility and writes sprint state before reaching `super.aiStep()`. On the ordinary keyboard path, forward input supplies the positive forward impulse used by both versions' eligibility checks. For the stated non-passenger airborne case, 1.21.4's ground/underwater condition rejects the double-tap path while 1.21.5's eligibility predicate contains no airborne restriction. Food, blindness, item-use, fall-flying, movement-slowdown and fluid values are direct eligibility inputs; excluded producer systems remain vanilla. The paired source proves that the local sprint state can transition through a forward double-tap while airborne in 1.21.5 under the stated eligibility gates, while 1.21.4's corresponding double-tap branch rejects that state.


## Current evidence and remaining work

Source decisions, implementation coverage and runtime parity are separate. Source paths are relative to `decompiled_minecraft/`; immutable Git evidence can be read with `git show <revision>:<path>`.

```json
{
  "implementation": {
    "status": "open",
    "changes": [],
    "hooks": [],
    "limits": [
      "No catalog Change implements the operation stated in this record. The current 104 annotated Changes/catalog were checked; unrelated hooks do not close this operation. Registration establishes static correspondence only; no runtime parity or renewed applicability acceptance."
    ]
  },
  "applicability": [],
  "sources": [
    {
      "version": "1.21.4",
      "mapping": "mojmap",
      "path": "1.21.4/mojmap/net/minecraft/client/player/LocalPlayer.java",
      "member": "aiStep",
      "lines": "653-662",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 145686ebdc7f0d12a64070309073665eb8695b7e09911a723e86113a77d04611; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: 48b502ccc5e855b49da8aa9c0c0d7c565bec54c9f5da3a3664528aff7b9fdf23 *1.21.4/client_mappings.txt. Canonical source-manifest SHA-256 f90b61197928632e061ea877955a19055c92ae6f357c2daf1bc646172c6f51f0 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.21.5",
      "mapping": "mojmap",
      "path": "1.21.5/mojmap/net/minecraft/client/player/LocalPlayer.java",
      "member": "aiStep",
      "lines": "693-703",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 f1fcfed4a938732361e7ad951f93e9b73b02320ee56f0b219e2b3c7acdbfa2ef; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: 3907657ade3e61bc8cffb4ca0a1bcba15f57986be4e613900947119364a206e5 *1.21.5/client_mappings.txt. Canonical source-manifest SHA-256 365cc2d22446ceba0e36f46aa1c95ce4cc31514dbd657505680fb660f3adefd9 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.21.5",
      "mapping": "mojmap",
      "path": "1.21.5/mojmap/net/minecraft/client/player/ClientInput.java",
      "member": "hasForwardImpulse",
      "lines": "17-19",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 597a44339a99f1bce1b081614c7c2984ca03e255b40e9d247675a31b6f810d78; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: 3907657ade3e61bc8cffb4ca0a1bcba15f57986be4e613900947119364a206e5 *1.21.5/client_mappings.txt. Canonical source-manifest SHA-256 365cc2d22446ceba0e36f46aa1c95ce4cc31514dbd657505680fb660f3adefd9 matches archived publication; cited file hash is present in that manifest."
    }
  ],
  "sourceReviews": [],
  "openIssues": [],
  "runtime": "unverified"
}
```
