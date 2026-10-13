# F006: Ground acceleration operation order

1.13.2, ornithe-feather: `1.13.2/ornithe-feather/net/minecraft/entity/living/LivingEntity.java`, `moveRelative`, lines 1537–1553.
1.14.4, ornithe-feather: `1.14.4/ornithe-feather/net/minecraft/entity/living/LivingEntity.java`, `moveRelative, m_70235197`, lines 1841–1845, 1961–1962.

Difference: 1.13.2 computes `t = slipperiness * 0.91F`, then `u = 0.16277137F / (t * t * t)`, then multiplies `getSpeed() * u`. 1.14.4's helper returns `getSpeed() * (0.21600002F / (slipperiness * slipperiness * slipperiness))` when grounded. The algebraic forms are close, but constants and float operation order differ.

Movement meaning: Ordinary ground/air travel samples the block below the entity shape for slipperiness and uses onGround to select the helper result.


## Current evidence and remaining work

Source decisions, implementation coverage and runtime parity are separate. Source paths are relative to `decompiled_minecraft/`; immutable Git evidence can be read with `git show <revision>:<path>`.

```json
{
  "implementation": {
    "status": "open",
    "changes": [
      "src/main/java/me/wolfii/legacyparkourcompat/change/v1_12/GroundAcceleration.java"
    ],
    "hooks": [
      "GroundSpeedHook"
    ],
    "limits": [
      "GroundSpeedHook is registered only for V1_12 legacy formula; no V1_13 endpoint operation preserves this finding’s pre-1.14 formula, so closest-change coverage of the 1.13.2 delta is absent."
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
          1537,
          1553
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
      "member": "moveRelative, m_70235197",
      "lines": [
        [
          1841,
          1845
        ],
        [
          1961,
          1962
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
