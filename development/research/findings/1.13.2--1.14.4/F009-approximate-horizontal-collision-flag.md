# F009: Approximate horizontal collision flag

1.13.2, ornithe-feather: `1.13.2/ornithe-feather/net/minecraft/entity/Entity.java`, `move`, lines 674–680.
1.14.4, ornithe-feather: `1.14.4/ornithe-feather/net/minecraft/entity/Entity.java`, `move`, lines 471–475.
1.14.4, ornithe-feather: `1.14.4/ornithe-feather/net/minecraft/util/math/MathHelper.java`, `m_68886716`, lines 155–157.

Difference: 1.13.2 sets horizontal collision when requested and resolved X or Z differ exactly. 1.14.4 uses `!MathHelper.m_68886716(requested,resolved)` for each horizontal component; the helper treats values within its tolerance (`1.0E-5`) as equal. 1.14.4 still uses exact component inequality in the later velocity-cancellation checks.

Movement meaning: Entity.move stores the flag after the collision solver; LivingEntity travel and player logic consume collision flags in later movement decisions.


## Current evidence and remaining work

Source decisions, implementation coverage and runtime parity are separate. Source paths are relative to `decompiled_minecraft/`; immutable Git evidence can be read with `git show <revision>:<path>`.

```json
{
  "implementation": {
    "status": "open",
    "changes": [],
    "hooks": [],
    "limits": [
      "Current annotated Change/interface/catalog inventory contains no operation implementing: F009: Approximate horizontal collision flag. Existing unrelated later operations do not establish coverage."
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
      "member": "move",
      "lines": [
        [
          674,
          680
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
      "member": "move",
      "lines": [
        [
          471,
          475
        ]
      ],
      "status": "verified-content",
      "sha256": "7315a496c195da767de9d4936d3adb6efc3c419dc0f0e95d6f32781b0da1ba55",
      "detail": "Canonical source read. Matches digest in archived original."
    },
    {
      "version": "1.14.4",
      "mapping": "ornithe-feather",
      "path": "1.14.4/ornithe-feather/net/minecraft/util/math/MathHelper.java",
      "member": "m_68886716",
      "lines": [
        [
          155,
          157
        ]
      ],
      "status": "verified-content",
      "sha256": "e39d5dfc69c17a9032be086d7d4d14cc63525962a0ff83615b1befdcca599843",
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
