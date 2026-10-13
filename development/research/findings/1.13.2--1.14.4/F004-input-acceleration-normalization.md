# F004: Input acceleration cutoff and vector arithmetic

1.13.2, ornithe-feather: `1.13.2/ornithe-feather/net/minecraft/entity/Entity.java`, `updateVelocity`, lines 1057–1075.
1.14.4, ornithe-feather: `1.14.4/ornithe-feather/net/minecraft/entity/Entity.java`, `updateVelocity`, lines 1062–1077.

Difference: 1.13.2 computes squared input magnitude in float, returns below `1.0E-4F`, square-roots and normalizes using float operations, then adds rotated components to double velocity. 1.14.4 computes Vec3d squared magnitude in double, returns below `1.0E-7`, conditionally normalizes in double and scales before yaw rotation and vector addition. Threshold, numeric type and operation sequence differ.

Movement meaning: LivingEntity travel calls the helper for ordinary movement and water/lava paths; player overrides may customize `moveRelative`.


## Current evidence and remaining work

Source decisions, implementation coverage and runtime parity are separate. Source paths are relative to `decompiled_minecraft/`; immutable Git evidence can be read with `git show <revision>:<path>`.

```json
{
  "implementation": {
    "status": "open",
    "changes": [],
    "hooks": [],
    "limits": [
      "Current annotated Change/interface/catalog inventory contains no operation implementing: F004: Input acceleration cutoff and vector arithmetic. Existing unrelated later operations do not establish coverage."
    ]
  },
  "applicability": [
    "Endpoint interval (1.13.2, 1.14.4]; exact cutover remains the original evidence’s separate applicability gate."
  ],
  "sources": [
    {
      "version": "1.13.2",
      "mapping": "ornithe-feather",
      "path": "1.13.2/ornithe-feather/net/minecraft/entity/Entity.java",
      "member": "updateVelocity",
      "lines": [
        [
          1057,
          1075
        ]
      ],
      "status": "verified-content",
      "sha256": "1d6ec8b80f74635401745c2c027bf36555c85348ca5693764f2668363b17d269",
      "detail": "Canonical source read. Matches digest in archived original."
    },
    {
      "version": "1.14.4",
      "mapping": "ornithe-feather",
      "path": "1.14.4/ornithe-feather/net/minecraft/entity/Entity.java",
      "member": "updateVelocity",
      "lines": [
        [
          1062,
          1077
        ]
      ],
      "status": "verified-content",
      "sha256": "7315a496c195da767de9d4936d3adb6efc3c419dc0f0e95d6f32781b0da1ba55",
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
