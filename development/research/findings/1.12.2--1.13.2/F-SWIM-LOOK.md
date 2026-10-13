# F-SWIM-LOOK — 1.13.2 swimming adds pitch-directed vertical velocity control

1.12.2, ornithe-feather; feather-gen2-1.12.2+build.2-mergedv2.jar, feather-gen2-1.12.2+build.2.tiny: `1.12.2/ornithe-feather/net/minecraft/entity/living/player/PlayerEntity.java`, `public void moveRelative(float forwards, float sideways, float upwards) {`, lines 1386-1404.
1.13.2, ornithe-feather; feather-gen2-1.13.2+build.2-mergedv2.jar, feather-gen2-1.13.2+build.2.tiny: `1.13.2/ornithe-feather/net/minecraft/entity/living/player/PlayerEntity.java`, `public void moveRelative(float forwards, float sideways, float upwards) {`, lines 1442-1468.

Difference: 1.13.2 applies a look-directed vertical adjustment absent from 1.12.2's player override.

It is separate from the local sneak-water descent impulse and water-height jump selection.

Movement meaning: Exact fluid query, pose, and collision effects remain separate dependencies.


## Current evidence and remaining work

Source decisions, implementation coverage and runtime parity are separate. Source paths are relative to `decompiled_minecraft/`; immutable Git evidence can be read with `git show <revision>:<path>`.

```json
{
  "implementation": {
    "status": "partial",
    "changes": [
      "src/main/java/me/wolfii/legacyparkourcompat/change/v1_13/SwimmingPitch.java"
    ],
    "hooks": [
      "SwimmingPitchHook"
    ],
    "limits": [
      "SwimmingPitch implements later 1.13 float lookY rounding, not the absence of swimming vertical control in 1.12. SwimmingState indirectly suppresses the branch through isSwimming=false; full Player.moveRelative gate/order fidelity is not established."
    ]
  },
  "applicability": [],
  "sources": [
    {
      "version": "1.12.2",
      "mapping": "ornithe-feather; feather-gen2-1.12.2+build.2-mergedv2.jar, feather-gen2-1.12.2+build.2.tiny",
      "path": "1.12.2/ornithe-feather/net/minecraft/entity/living/player/PlayerEntity.java",
      "member": "public void moveRelative(float forwards, float sideways, float upwards) {",
      "lines": "1386-1404",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 e4e0fdbe07a7d0a0ae4a70cbb6739a287c9d045a4a12b409895c220b5d91fe1e; matches an archived source-file digest. Checked full declaration/body; snippet SHA-256 10fdb61b3c829a10d780b4857db7f6120719c0300f565b37c4ae75405c129ff7."
    },
    {
      "version": "1.13.2",
      "mapping": "ornithe-feather; feather-gen2-1.13.2+build.2-mergedv2.jar, feather-gen2-1.13.2+build.2.tiny",
      "path": "1.13.2/ornithe-feather/net/minecraft/entity/living/player/PlayerEntity.java",
      "member": "public void moveRelative(float forwards, float sideways, float upwards) {",
      "lines": "1442-1468",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 4ed22f6c5a3c55d67eed782070ac722201df4d624adbc90779f1fd29c2876633; matches an archived source-file digest. Checked full declaration/body; snippet SHA-256 e2bec35ade0d77ec03898370e7466fc6f7504eb9629e2e9590c85804742093ae."
    }
  ],
  "sourceReviews": [],
  "openIssues": [
    {
      "missing": "Later applicability gate: exact introduction of campaign-F-SWIM-LOOK is unknown within (1.12.2, 1.13.2]",
      "next_action": "Preserve any exact historical verdict for its bounded assertion; verify intervening releases separately before assigning a new implementation range. Formatting alone requires no renewed acceptance."
    },
    {
      "missing": "Exact independent source verdict/snapshot identity for F-SWIM-LOOK",
      "next_action": "Retain original source-confirmed status only; source-acceptance owner must resolve a preserved independent verdict or review this exact bounded assertion in the later campaign."
    },
    {
      "missing": "Exact independent source verdict/snapshot identity for swim-look-vertical-control",
      "next_action": "Retain original source-confirmed status only; source-acceptance owner must resolve a preserved independent verdict or review this exact bounded assertion in the later campaign."
    }
  ],
  "runtime": "unverified"
}
```
