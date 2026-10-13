# F007: Collision axis order

1.13.2, ornithe-feather: `1.13.2/ornithe-feather/net/minecraft/entity/Entity.java`, `move`, lines 578–599.
1.14.4, ornithe-feather: `1.14.4/ornithe-feather/net/minecraft/entity/Entity.java`, `move, m_79801352`, lines 444–475, 708–771.

Difference: 1.13.2 obtains one collision list and resolves Y, then X, then Z. 1.14.4 delegates to a solver that resolves Y and orders horizontal axes by requested component magnitude, so diagonal motion can resolve Z before X or X before Z.

Movement meaning: Living travel and other displacement callers invoke Entity.move.


## Current evidence and remaining work

Source decisions, implementation coverage and runtime parity are separate. Source paths are relative to `decompiled_minecraft/`; immutable Git evidence can be read with `git show <revision>:<path>`.

```json
{
  "implementation": {
    "status": "open",
    "changes": [],
    "hooks": [],
    "limits": [
      "Current annotated Change/interface/catalog inventory contains no operation implementing: F007: Collision axis order. Existing unrelated later operations do not establish coverage."
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
          578,
          599
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
      "member": "move, m_79801352",
      "lines": [
        [
          444,
          475
        ],
        [
          708,
          771
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
