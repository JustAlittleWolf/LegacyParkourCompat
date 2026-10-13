# F023: Ender Dragon wing fling excludes creative players in 1.14.4

1.13.2, ornithe-feather: `1.13.2/ornithe-feather/net/minecraft/entity/living/mob/monster/boss/EnderDragonEntity.java`, `mobTick, flingMobs`, lines 304–305, 402–414.
1.14.4, ornithe-feather: `1.14.4/ornithe-feather/net/minecraft/entity/living/mob/monster/boss/EnderDragonEntity.java`, `mobTick, flingMobs`, lines 293–300, 397–409.

Difference: 1.13.2's two-argument wing query resolves through `EntityView.getEntities(exclude,bounds)`, whose default passes `EntityFilter.NOT_SPECTATOR`. That predicate rejects spectator players but allows non-spectator creative players. 1.14.4 passes `EntityFilter.NOT_SPECTATOR_NOT_CREATIVE` explicitly to each wing query. Its player branch requires both `!isSpectator()` and `!isCreative()`, so a creative player is removed before `flingMobs` receives the list. The paired `flingMobs` writer is the same: for each living entity it computes the x/z offset from the dragon body center, computes `h = f*f + g*g`, and calls `addVelocity(f/h*4.0, 0.2F, g/h*4.0)`. The separate damage condition follows the impulse in both versions and is excluded from this movement finding.

Movement meaning: The wing query runs server-side when `damagedTimer == 0`.


## Current evidence and remaining work

Source decisions, implementation coverage and runtime parity are separate. Source paths are relative to `decompiled_minecraft/`; immutable Git evidence can be read with `git show <revision>:<path>`.

```json
{
  "implementation": {
    "status": "open",
    "changes": [],
    "hooks": [],
    "limits": [
      "Current annotated Change/interface/catalog inventory contains no operation implementing: F023: Ender Dragon wing fling excludes creative players in 1.14.4. Existing unrelated later operations do not establish coverage."
    ]
  },
  "applicability": [
    "Endpoint interval (1.13.2, 1.14.4]; exact cutover remains the original evidence’s separate applicability gate."
  ],
  "sources": [
    {
      "version": "1.13.2",
      "mapping": "ornithe-feather",
      "path": "1.13.2/ornithe-feather/net/minecraft/entity/living/mob/monster/boss/EnderDragonEntity.java",
      "member": "mobTick, flingMobs",
      "lines": [
        [
          304,
          305
        ],
        [
          402,
          414
        ]
      ],
      "status": "verified-content",
      "sha256": "0c0d8aa04f5b8c2264ff889de517b67d5f084ed6e17515cf696dc4c450e4930e",
      "detail": "Canonical source read. Matches digest in archived original."
    },
    {
      "version": "1.14.4",
      "mapping": "ornithe-feather",
      "path": "1.14.4/ornithe-feather/net/minecraft/entity/living/mob/monster/boss/EnderDragonEntity.java",
      "member": "mobTick, flingMobs",
      "lines": [
        [
          293,
          300
        ],
        [
          397,
          409
        ]
      ],
      "status": "verified-content",
      "sha256": "ec935f9b1d2c2a7b6d3187be7315bc6d95955a2f1d5545af3cea4b5f9536e1ca",
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
