# F003: Jump Boost vertical impulse precision

1.13.2, ornithe-feather: `1.13.2/ornithe-feather/net/minecraft/entity/living/LivingEntity.java`, `getJumpStrength`, lines 1447–1464.
1.14.4, ornithe-feather: `1.14.4/ornithe-feather/net/minecraft/entity/living/LivingEntity.java`, `getJumpStrength`, lines 1752–1772.

Difference: With Jump Boost, 1.13.2 first assigns float `getJumpStrength()` into double `velocityY`, then adds `(amplifier+1)*0.1F` to that double. 1.14.4 adds the base and boost as float into local `f`, then stores that float in the vertical velocity. 1.14.4 therefore rounds the sum to float before storage; 1.13.2 performs the addition at double precision after base assignment. Sprint horizontal impulse uses the same apparent formula and ordering in the inspected bodies.

Movement meaning: LivingEntity jump is reached from living tick jump handling; PlayerEntity overrides call the superclass on both sides.


## Current evidence and remaining work

Source decisions, implementation coverage and runtime parity are separate. Source paths are relative to `decompiled_minecraft/`; immutable Git evidence can be read with `git show <revision>:<path>`.

```json
{
  "implementation": {
    "status": "open",
    "changes": [
      "src/main/java/me/wolfii/legacyparkourcompat/change/v1_17/FloatJumpBoostAddition.java"
    ],
    "hooks": [
      "JumpPowerHook"
    ],
    "limits": [
      "Closest registered older operation is FloatJumpBoostAddition, which rounds the total to float. It does not represent 1.13.2 base assignment followed by double addition of the float boost; later DoubleJumpBoostAddition does not win over the closer change."
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
      "member": "getJumpStrength",
      "lines": [
        [
          1447,
          1464
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
      "member": "getJumpStrength",
      "lines": [
        [
          1752,
          1772
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
