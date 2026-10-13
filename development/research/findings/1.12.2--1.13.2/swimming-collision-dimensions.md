# Swimming collision dimensions

1.12.2, ornithe-feather; feather-gen2-1.12.2+build.2-mergedv2.jar, feather-gen2-1.12.2+build.2.tiny: `1.12.2/ornithe-feather/net/minecraft/entity/living/player/PlayerEntity.java`, `protected void updatePlayerPose() {`, lines 291-315.
1.12.2, ornithe-feather; feather-gen2-1.12.2+build.2-mergedv2.jar, feather-gen2-1.12.2+build.2.tiny: `1.12.2/ornithe-feather/net/minecraft/entity/living/player/PlayerEntity.java`, `public float getEyeHeight() {`, lines 1756-1767.
1.13.2, ornithe-feather; feather-gen2-1.13.2+build.2-mergedv2.jar, feather-gen2-1.13.2+build.2.tiny: `1.13.2/ornithe-feather/net/minecraft/entity/living/player/PlayerEntity.java`, `protected void updatePlayerPose() {`, lines 334-361.
1.13.2, ornithe-feather; feather-gen2-1.13.2+build.2-mergedv2.jar, feather-gen2-1.13.2+build.2.tiny: `1.13.2/ornithe-feather/net/minecraft/entity/living/player/PlayerEntity.java`, `public float getEyeHeight() {`, lines 1859-1870.

Difference: 1.13.2 adds swimming pose dimensions 0.6F by 0.6F behind its collision-fit guard; 1.12.2 has no swimming dimensions branch. The separate spin-attack trigger is a modern-only disposition.

Movement meaning: The established water movement state can choose a shorter player collision box; exact fit depends on the vanilla collision candidates.


## Current evidence and remaining work

Source decisions, implementation coverage and runtime parity are separate. Source paths are relative to `decompiled_minecraft/`; immutable Git evidence can be read with `git show <revision>:<path>`.

```json
{
  "implementation": {
    "status": "partial",
    "changes": [
      "src/main/java/me/wolfii/legacyparkourcompat/change/v1_12/SwimmingDimensions.java"
    ],
    "hooks": [
      "PlayerDimensionsHook",
      "PlayerPoseHook"
    ],
    "limits": [
      "SwimmingDimensions supplies standing/crouching-sized boxes for SWIMMING and disables its pose update; SwimmingState suppresses swimming. The split eye-height reader has no dedicated old-era override, and preservation of vanilla eyeHeight for crouching does not cover the 0.4F swimming eye-height claim."
    ]
  },
  "applicability": [],
  "sources": [
    {
      "version": "1.12.2",
      "mapping": "ornithe-feather; feather-gen2-1.12.2+build.2-mergedv2.jar, feather-gen2-1.12.2+build.2.tiny",
      "path": "1.12.2/ornithe-feather/net/minecraft/entity/living/player/PlayerEntity.java",
      "member": "protected void updatePlayerPose() {",
      "lines": "291-315",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 e4e0fdbe07a7d0a0ae4a70cbb6739a287c9d045a4a12b409895c220b5d91fe1e; matches an archived source-file digest. Checked full declaration/body; snippet SHA-256 a47c43816d86adafc60cd17026114553d35d06091efeb14efed746a5889ce7ef."
    },
    {
      "version": "1.12.2",
      "mapping": "ornithe-feather; feather-gen2-1.12.2+build.2-mergedv2.jar, feather-gen2-1.12.2+build.2.tiny",
      "path": "1.12.2/ornithe-feather/net/minecraft/entity/living/player/PlayerEntity.java",
      "member": "public float getEyeHeight() {",
      "lines": "1756-1767",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 e4e0fdbe07a7d0a0ae4a70cbb6739a287c9d045a4a12b409895c220b5d91fe1e; matches an archived source-file digest. Checked full declaration/body; snippet SHA-256 4eae805e2dfd5b4ac9844e8bd271914524f3558062032d7f32e9b04e6e7bf61e."
    },
    {
      "version": "1.12.2",
      "mapping": "ornithe-feather; feather-gen2-1.12.2+build.2-mergedv2.jar, feather-gen2-1.12.2+build.2.tiny",
      "path": "1.12.2/ornithe-feather/net/minecraft/entity/living/player/PlayerEntity.java",
      "member": "PlayerEntity#updateSwimming",
      "lines": null,
      "status": "different",
      "detail": "Checked complete canonical file SHA-256 e4e0fdbe07a7d0a0ae4a70cbb6739a287c9d045a4a12b409895c220b5d91fe1e (matches an archived source-file digest); named declaration absent. Named declaration is absent in this checked canonical file; paired existing declaration/range is retained separately, so this is an explicit addition/removal or mapping correspondence limit, not an invented line."
    },
    {
      "version": "1.13.2",
      "mapping": "ornithe-feather; feather-gen2-1.13.2+build.2-mergedv2.jar, feather-gen2-1.13.2+build.2.tiny",
      "path": "1.13.2/ornithe-feather/net/minecraft/entity/living/player/PlayerEntity.java",
      "member": "protected void updatePlayerPose() {",
      "lines": "334-361",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 4ed22f6c5a3c55d67eed782070ac722201df4d624adbc90779f1fd29c2876633; matches an archived source-file digest. Checked full declaration/body; snippet SHA-256 8286fe960b57eae1c7859ccdb6d8083092ad987a15117cea6e1c5dcd0aaa1b44."
    },
    {
      "version": "1.13.2",
      "mapping": "ornithe-feather; feather-gen2-1.13.2+build.2-mergedv2.jar, feather-gen2-1.13.2+build.2.tiny",
      "path": "1.13.2/ornithe-feather/net/minecraft/entity/living/player/PlayerEntity.java",
      "member": "public float getEyeHeight() {",
      "lines": "1859-1870",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 4ed22f6c5a3c55d67eed782070ac722201df4d624adbc90779f1fd29c2876633; matches an archived source-file digest. Checked full declaration/body; snippet SHA-256 4fb4744e35afdde1279eaf30d4e6fdb2f492acd58a8b07b239021160971fa052."
    },
    {
      "version": "1.13.2",
      "mapping": "ornithe-feather; feather-gen2-1.13.2+build.2-mergedv2.jar, feather-gen2-1.13.2+build.2.tiny",
      "path": "1.13.2/ornithe-feather/net/minecraft/entity/living/player/PlayerEntity.java",
      "member": "public void updateSwimming() {",
      "lines": "1471-1477",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 4ed22f6c5a3c55d67eed782070ac722201df4d624adbc90779f1fd29c2876633; matches an archived source-file digest. Checked full declaration/body; snippet SHA-256 c909ed7792941016bfaf1c3fdbca726c98d5d14bfcf1669e829a1d90533b9dcf."
    }
  ],
  "sourceReviews": [],
  "openIssues": [
    {
      "missing": "Later applicability gate: exact introduction of campaign-F-SWIM-POSE is unknown within (1.12.2, 1.13.2]",
      "next_action": "Preserve any exact historical verdict for its bounded assertion; verify intervening releases separately before assigning a new implementation range. Formatting alone requires no renewed acceptance."
    },
    {
      "missing": "Exact independent source verdict/snapshot identity for F-SWIM-POSE",
      "next_action": "Retain original source-confirmed status only; source-acceptance owner must resolve a preserved independent verdict or review this exact bounded assertion in the later campaign."
    },
    {
      "missing": "Exact independent source verdict/snapshot identity for swim-dimensions",
      "next_action": "Retain original source-confirmed status only; source-acceptance owner must resolve a preserved independent verdict or review this exact bounded assertion in the later campaign."
    }
  ],
  "runtime": "unverified"
}
```
