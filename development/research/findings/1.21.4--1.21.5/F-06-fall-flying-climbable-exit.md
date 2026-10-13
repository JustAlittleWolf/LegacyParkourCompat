# F-06: Fall-flying uses ordinary air travel when the player is on a climbable

1.21.4, mojmap: `1.21.4/mojmap/net/minecraft/world/entity/LivingEntity.java`, travel 2189-2199.
1.21.5, mojmap: `1.21.5/mojmap/net/minecraft/world/entity/LivingEntity.java`, travel 2213-2222.

Difference: When a non-fluid travel tick enters the fall-flying branch while `onClimbable()` is true, 1.21.4 still applies the glide velocity math and moves with the resulting velocity. 1.21.5 instead runs regular `travelInAir(input)` and calls `stopFallFlying`. For the same input and state, 1.21.5 therefore selects ground/air friction and gravity travel in place of gliding and clears the fall-flying flag through the helper.

Movement meaning: LocalPlayer jump input can request fall flying through `tryToStartFallFlying`; the resulting living-player state reaches `LivingEntity.aiStep` and `travel(Vec3)`. The source proves different travel branch selection and the fall-flying stop call for climbable contact. 1.21.5's resulting position and velocity depend on ordinary air-travel inputs, friction, gravity and collision. The precise observable meaning of the transient flag toggle is not inferred beyond the method calls.


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
      "path": "1.21.4/mojmap/net/minecraft/world/entity/LivingEntity.java",
      "member": "travel",
      "lines": "2189-2199",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 e62ce650af0a5ac97e4d0a2ba7cad4ba68a8d098525609f8b5b6976a8ab0ae30; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: 48b502ccc5e855b49da8aa9c0c0d7c565bec54c9f5da3a3664528aff7b9fdf23 *1.21.4/client_mappings.txt. Canonical source-manifest SHA-256 f90b61197928632e061ea877955a19055c92ae6f357c2daf1bc646172c6f51f0 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.21.5",
      "mapping": "mojmap",
      "path": "1.21.5/mojmap/net/minecraft/world/entity/LivingEntity.java",
      "member": "travel",
      "lines": "2213-2222",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 a8aed863d4fdc515c751dd2878a8bb9c13179228cb8dbb50edf1d19cd5404271; archived source-hash equivalence not established. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: 3907657ade3e61bc8cffb4ca0a1bcba15f57986be4e613900947119364a206e5 *1.21.5/client_mappings.txt. Canonical source-manifest SHA-256 365cc2d22446ceba0e36f46aa1c95ce4cc31514dbd657505680fb660f3adefd9 matches archived publication; cited file hash is present in that manifest."
    }
  ],
  "sourceReviews": [
    {
      "verdict": "reported ACCEPT, digest-only corrected revision",
      "assertion_preserved": true,
      "binding": "Latest run reports review event; standalone independent review commit/path/blob/SHA is not supplied. Source assertion unchanged.",
      "snapshot_sha256": "80e4f97fec07fcaa2dd98b4e50b7f9f8298b237e9101024f6c19670215cfa57c",
      "identity": "4a5eae32b5819418dbd59eb4a763ee24ea1f2c52:development/research/reconciliation/branch-doc-review-late.md"
    }
  ],
  "openIssues": [
    {
      "missing": "Standalone independent digest-correction review artifact identity for F06 is absent.",
      "next_action": "Preserve reported acceptance and unchanged assertion; bind exact artifact if recovered, do not invent a verdict."
    }
  ],
  "runtime": "unverified"
}
```
