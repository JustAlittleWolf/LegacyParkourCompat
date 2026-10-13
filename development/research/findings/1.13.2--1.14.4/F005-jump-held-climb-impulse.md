# F005: Jump-held climb impulse

1.13.2, ornithe-feather: `1.13.2/ornithe-feather/net/minecraft/entity/living/LivingEntity.java`, `moveRelative`, lines 1575–1578.
1.14.4, ornithe-feather: `1.14.4/ornithe-feather/net/minecraft/entity/living/LivingEntity.java`, `moveRelative`, lines 1846–1850.

Difference: After moving, 1.13.2 sets vertical velocity to 0.2 only if `collidingHorizontally && isClimbing()`. 1.14.4 sets it if `(collidingHorizontally || jumping) && isClimbing()`. Thus holding jump while climbing can trigger the 1.14.4 assignment even without horizontal collision.

Movement meaning: The ordinary travel branch follows non-water/non-lava and non-fall-flying dispatch. `jumping` and `isClimbing()` are player/entity state inputs; exact climbing block registrations and their historical applicability remain to be inventoried.


## Current evidence and remaining work

Source decisions, implementation coverage and runtime parity are separate. Source paths are relative to `decompiled_minecraft/`; immutable Git evidence can be read with `git show <revision>:<path>`.

```json
{
  "implementation": {
    "status": "open",
    "changes": [],
    "hooks": [],
    "limits": [
      "Current annotated Change/interface/catalog inventory contains no operation implementing: F005: Jump-held climb impulse. Existing unrelated later operations do not establish coverage."
    ]
  },
  "applicability": [
    "Endpoint interval (1.13.2, 1.14.4]; exact cutover remains the original evidence’s separate applicability gate."
  ],
  "sources": [
    {
      "version": "1.13.2",
      "mapping": "ornithe-feather",
      "path": "1.13.2/ornithe-feather/net/minecraft/entity/living/LivingEntity.java",
      "member": "moveRelative",
      "lines": [
        [
          1575,
          1578
        ]
      ],
      "status": "verified-content",
      "sha256": "bb691358c9a43c9f46e85575bf4d0a4ad671d0eb912502acc3a6a3f625e42f1c",
      "detail": "Canonical source read. Matches digest in archived original."
    },
    {
      "version": "1.14.4",
      "mapping": "ornithe-feather",
      "path": "1.14.4/ornithe-feather/net/minecraft/entity/living/LivingEntity.java",
      "member": "moveRelative",
      "lines": [
        [
          1846,
          1850
        ]
      ],
      "status": "verified-content",
      "sha256": "2cccf4331ce9e62013eeb8e96163e5e146e1291619eb998ce9867daa87c02b61",
      "detail": "Canonical source read. Matches digest in archived original."
    }
  ],
  "sourceReviews": [],
  "openIssues": [
    {
      "missing": "1.13.2 original Feather mapped-client JAR d118ff5b9eb93fed06680466cb26c41a30647cdb2fc8744638cf9b35cf7864e5 remains unavailable; revised source equality does not prove original derived-JAR equivalence.",
      "next_action": "Retain existing Feather-r1 provenance caveat; original equivalence requires the exact preserved artifact, not regeneration during preparation."
    }
  ],
  "runtime": "unverified"
}
```
