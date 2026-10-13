# F008: Resolved displacement cutoff

1.13.2, ornithe-feather: `1.13.2/ornithe-feather/net/minecraft/entity/Entity.java`, `move`, lines 578–599.
1.14.4, ornithe-feather: `1.14.4/ornithe-feather/net/minecraft/entity/Entity.java`, `move`, lines 463–468.

Difference: After sneak-edge preprocessing, 1.14.4 calls the collision solver but sets the shape and derives position only when the resolved vector squared length is greater than `1.0E-7`. 1.13.2 enters axis resolution for any nonzero requested component and later updates position from shape without this squared-length cutoff.

Movement meaning: All non-noClip Entity.move callers can reach this behavior; piston preprocessing and noClip have separate guards.


## Current evidence and remaining work

Source decisions, implementation coverage and runtime parity are separate. Source paths are relative to `decompiled_minecraft/`; immutable Git evidence can be read with `git show <revision>:<path>`.

```json
{
  "implementation": {
    "status": "open",
    "changes": [],
    "hooks": [],
    "limits": [
      "Current annotated Change/interface/catalog inventory contains no operation implementing: F008: Resolved displacement cutoff. Existing unrelated later operations do not establish coverage."
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
      "member": "move",
      "lines": [
        [
          463,
          468
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
