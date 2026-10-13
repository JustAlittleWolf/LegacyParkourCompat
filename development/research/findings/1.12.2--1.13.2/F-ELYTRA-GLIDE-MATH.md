# F-ELYTRA-GLIDE-MATH — 1.13.2 regroups vertical glide acceleration

1.12.2, ornithe-feather; feather-gen2-1.12.2+build.2-mergedv2.jar, feather-gen2-1.12.2+build.2.tiny: `1.12.2/ornithe-feather/net/minecraft/entity/living/LivingEntity.java`, `public void moveRelative(float forwards, float sideways, float upwards) {`, lines 1425-1599.
1.13.2, ornithe-feather; feather-gen2-1.13.2+build.2-mergedv2.jar, feather-gen2-1.13.2+build.2.tiny: `1.13.2/ornithe-feather/net/minecraft/entity/living/LivingEntity.java`, `public void moveRelative(float forwards, float sideways, float upwards) {`, lines 1478-1669.

Difference: 1.13.2 regroups the arithmetic and introduces a shared gravity factor that can be affected by Slow Falling.

Preserve these source expressions and intermediate types/order; don't simplify them to a real-number identity.

Movement meaning: - Reachability and dependencies: inherited LivingEntity movement dispatch is called by PlayerEntity through the local player's travel tick while fall-flying.

Elytra state/equipment eligibility and camera pitch are separate dependencies.

Slow Falling itself is a modern-only effect for this historical one-way scope.


## Current evidence and remaining work

Source decisions, implementation coverage and runtime parity are separate. Source paths are relative to `decompiled_minecraft/`; immutable Git evidence can be read with `git show <revision>:<path>`.

```json
{
  "implementation": {
    "status": "open",
    "changes": [],
    "hooks": [],
    "limits": [
      "Complete current Change/catalog inventory inspected; no dedicated operation implements F-ELYTRA-GLIDE-MATH — 1.13.2 regroups vertical glide acceleration; later broadly related hooks do not establish this claimed delta."
    ]
  },
  "applicability": [],
  "sources": [
    {
      "version": "1.12.2",
      "mapping": "ornithe-feather; feather-gen2-1.12.2+build.2-mergedv2.jar, feather-gen2-1.12.2+build.2.tiny",
      "path": "1.12.2/ornithe-feather/net/minecraft/entity/living/LivingEntity.java",
      "member": "public void moveRelative(float forwards, float sideways, float upwards) {",
      "lines": "1425-1599",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 190e9ac551538e015d9e4d6c42856e5ba32b593131cf6d93895e7b29533f1ee6; matches an archived source-file digest. Checked full declaration/body; snippet SHA-256 a6fc8b4c678a9d87c512d62b80b1ebd28fa28b997281a03498a822cfa07e49c0."
    },
    {
      "version": "1.13.2",
      "mapping": "ornithe-feather; feather-gen2-1.13.2+build.2-mergedv2.jar, feather-gen2-1.13.2+build.2.tiny",
      "path": "1.13.2/ornithe-feather/net/minecraft/entity/living/LivingEntity.java",
      "member": "public void moveRelative(float forwards, float sideways, float upwards) {",
      "lines": "1478-1669",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 bb691358c9a43c9f46e85575bf4d0a4ad671d0eb912502acc3a6a3f625e42f1c; matches an archived source-file digest. Checked full declaration/body; snippet SHA-256 78de2dc443a97a6d6d08a3e430b0a5dbcb967ad73a5dddd14d9f7169cec56e82."
    }
  ],
  "sourceReviews": [],
  "openIssues": [
    {
      "missing": "Later applicability gate: exact introduction of campaign-F-ELYTRA-GLIDE-MATH is unknown within (1.12.2, 1.13.2]",
      "next_action": "Preserve any exact historical verdict for its bounded assertion; verify intervening releases separately before assigning a new implementation range. Formatting alone requires no renewed acceptance."
    },
    {
      "missing": "Current implementation of campaign-F-ELYTRA-GLIDE-MATH",
      "next_action": "After expert scope review and independent source acceptance, assign this specific operation to the separate implementation phase."
    },
    {
      "missing": "Exact independent source verdict/snapshot identity for F-ELYTRA-GLIDE-MATH",
      "next_action": "Retain original source-confirmed status only; source-acceptance owner must resolve a preserved independent verdict or review this exact bounded assertion in the later campaign."
    }
  ],
  "runtime": "unverified"
}
```
