# F011: Slow-falling water descent predicate timing

1.13.2, ornithe-feather: `1.13.2/ornithe-feather/net/minecraft/entity/living/LivingEntity.java`, `moveRelative`, lines 1480–1484, 1639–1649.
1.14.4, ornithe-feather: `1.14.4/ornithe-feather/net/minecraft/entity/living/LivingEntity.java`, `moveRelative`, lines 1786–1793, 1912–1921.

Difference: Both use `d=0.01` when slow falling applies at the start of movement. 1.13.2's special water descent condition reads current `velocityY` after movement and water damping. 1.14.4 stores `bl = initialVelocityY <= 0.0` before travel and later gates the special descent assignment on that stored boolean. The remaining tolerance expressions are structurally similar.

Movement meaning: If the player enters this branch with positive vertical velocity and movement/collision changes it to a nonpositive value before the descent check, 1.13.2 may take the special near-zero descent branch while 1.14.4's captured `bl` remains false.


## Current evidence and remaining work

Source decisions, implementation coverage and runtime parity are separate. Source paths are relative to `decompiled_minecraft/`; immutable Git evidence can be read with `git show <revision>:<path>`.

```json
{
  "implementation": {
    "status": "open",
    "changes": [],
    "hooks": [],
    "limits": [
      "Current annotated Change/interface/catalog inventory contains no operation implementing: F011: Slow-falling water descent predicate timing. Existing unrelated later operations do not establish coverage."
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
          1480,
          1484
        ],
        [
          1639,
          1649
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
          1786,
          1793
        ],
        [
          1912,
          1921
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
