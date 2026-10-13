# TICK-09: relative movement input angle conversion changes

1.8.9, ornithe-feather; feather-gen2-1.8.9+build.2-mergedv2.jar, feather-gen2-1.8.9+build.2.tiny: `1.8.9/ornithe-feather/net/minecraft/entity/Entity.java`, `public void updateVelocity(float sideways, float forwards, float scale) {`, lines 844-860.
1.9.4, ornithe-feather; feather-gen2-1.9.4+build.2-mergedv2.jar, feather-gen2-1.9.4+build.2.tiny: `1.9.4/ornithe-feather/net/minecraft/entity/Entity.java`, `public void updateVelocity(float sideways, float forwards, float scale) {`, lines 947-963.

Difference: 1.8.9 multiplies yaw by float-cast PI and then divides by `180.0F`. 1.9.4 multiplies yaw by float-cast `(Math.PI / 180.0)`.

The remaining threshold, length normalization, scalar multiplication order, trigonometric function calls and X/Z arithmetic are the same.

Because the angle arithmetic differs, the source does not establish bit-identical trigonometric inputs or the resulting acceleration values.

No position trajectory is inferred.

Movement meaning: Source finding only.


## Current evidence and remaining work

Source decisions, implementation coverage and runtime parity are separate. Source paths are relative to `decompiled_minecraft/`; immutable Git evidence can be read with `git show <revision>:<path>`.

```json
{
  "implementation": {
    "status": "open",
    "changes": [],
    "hooks": [],
    "limits": [
      "Complete current Change/catalog inventory inspected; no dedicated operation implements TICK-09: relative movement input angle conversion changes; later broadly related hooks do not establish this claimed delta."
    ]
  },
  "applicability": [],
  "sources": [
    {
      "version": "1.8.9",
      "mapping": "ornithe-feather; feather-gen2-1.8.9+build.2-mergedv2.jar, feather-gen2-1.8.9+build.2.tiny",
      "path": "1.8.9/ornithe-feather/net/minecraft/entity/Entity.java",
      "member": "public void updateVelocity(float sideways, float forwards, float scale) {",
      "lines": "844-860",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 d4c10932cb5bb1067a5a58be4e1bb1b42bdde3b6fc893d88178cc07435a1696b; matches an archived source-file digest. Checked full declaration/body; snippet SHA-256 574876d21c6d0e6f3557bbe6a51d832e1b621136adfc9b1bfe88d5cee9830432."
    },
    {
      "version": "1.9.4",
      "mapping": "ornithe-feather; feather-gen2-1.9.4+build.2-mergedv2.jar, feather-gen2-1.9.4+build.2.tiny",
      "path": "1.9.4/ornithe-feather/net/minecraft/entity/Entity.java",
      "member": "public void updateVelocity(float sideways, float forwards, float scale) {",
      "lines": "947-963",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 bcd7fa2206bf8d7271102f6da7fe2771f96dbe22ec79dab9f2101a3e29c17ef0; matches an archived source-file digest. Checked full declaration/body; snippet SHA-256 dc69b7e35a30fb51d2ac87bf546fa960e98a246c76731162ba5cd68b9cf98a4e."
    }
  ],
  "sourceReviews": [],
  "openIssues": [
    {
      "missing": "Later applicability gate: exact introduction of campaign-TICK-09-relative-input-angle-conversion is unknown within (1.8.9, 1.9.4]",
      "next_action": "Preserve any exact historical verdict for its bounded assertion; verify intervening releases separately before assigning a new implementation range. Formatting alone requires no renewed acceptance."
    },
    {
      "missing": "Current implementation of campaign-TICK-09-relative-input-angle-conversion",
      "next_action": "After expert scope review and independent source acceptance, assign this specific operation to the separate implementation phase."
    },
    {
      "missing": "Exact independent source verdict/snapshot identity for TICK-09-relative-input-angle-conversion",
      "next_action": "Retain original source-confirmed status only; source-acceptance owner must resolve a preserved independent verdict or review this exact bounded assertion in the later campaign."
    }
  ],
  "runtime": "unverified"
}
```
