# F014: Sleeping pitch reset after travel

1.13.2, ornithe-feather: `1.13.2/ornithe-feather/net/minecraft/entity/living/LivingEntity.java`, `tick`, lines 1689–1825.
1.14.4, ornithe-feather: `1.14.4/ornithe-feather/net/minecraft/entity/living/LivingEntity.java`, `tick`, lines 1979–2123.

Difference: Both local-player `tick()` methods call their superclass when the player's chunk is loaded. `LivingEntity.tick()` calls `mobTick()` virtually, so the local player's input processing runs before `LocalClientPlayerEntity.mobTick()` delegates to `LivingEntity.mobTick()` for travel. In 1.14.4, after that movement call returns and after walk/fall-flying bookkeeping, `LivingEntity.tick()` executes `if (this.isSleeping()) this.pitch = 0.0F;`. 1.13.2 has no corresponding client-side post-tick pitch reset. 1.14.4 also has a server-only sleeping cleanup in the `!world.isClient` block; that branch is not reachable in this local-client path.

Movement meaning: The differing write is reachable on the local client when the player is sleeping.


## Current evidence and remaining work

Source decisions, implementation coverage and runtime parity are separate. Source paths are relative to `decompiled_minecraft/`; immutable Git evidence can be read with `git show <revision>:<path>`.

```json
{
  "implementation": {
    "status": "open",
    "changes": [],
    "hooks": [],
    "limits": [
      "Current annotated Change/interface/catalog inventory contains no operation implementing: F014: Sleeping pitch reset after travel. Existing unrelated later operations do not establish coverage."
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
      "member": "tick",
      "lines": [
        [
          1689,
          1825
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
      "member": "tick",
      "lines": [
        [
          1979,
          2123
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
    },
    {
      "missing": "Sleep/wake pitch lifecycle and next movement-orientation read are untraced; no same-tick translation delta is established.",
      "next_action": "Retain this candidate only; later bounded dependency closure is needed before implementation."
    }
  ],
  "runtime": "unverified"
}
```
