# F06: Portal-aware dismount placement

1.14.4, mojmap: `1.14.4/mojmap/net/minecraft/world/entity/LivingEntity.java`, `stopRiding`, lines 2325–2331.
1.15.2, mojmap: `1.15.2/mojmap/net/minecraft/world/entity/LivingEntity.java`, `stopRiding`, lines 2389–2395.

Difference: For a passenger dismounting while the vehicle occupies a portal-tagged cell, 1.15.2's portal branch runs even for Boat/Horse and places the passenger just above the vehicle. 1.14.4 excludes Boat/Horse before any stand-up repositioning and contains no portal-tagged branch. For non-Boat/non-Horse vehicles the portal branch also takes precedence over 1.14.4's nearby stand-position search.

Movement meaning: The change is reached through a player living-entity dismount and writes player position on the server.


## Current evidence and remaining work

Source decisions, implementation coverage and runtime parity are separate. Source paths are relative to `decompiled_minecraft/`; immutable Git evidence can be read with `git show <revision>:<path>`.

```json
{
  "implementation": {
    "status": "partial",
    "changes": [
      "src/main/java/me/wolfii/legacyparkourcompat/change/v1_14/PortalDismountChange.java"
    ],
    "hooks": [
      "PortalDismountHook"
    ],
    "limits": [
      "PortalDismountHook dispatches historical rideable repositioning and copies historical stand-position search. Body also explicitly computes Boat/Horse offset, whereas original states their early exemption; report/code correspondence needs review, without editing code."
    ]
  },
  "applicability": [
    "Endpoint interval (1.14.4, 1.15.2]; exact cutover remains the original evidence’s separate applicability gate."
  ],
  "sources": [
    {
      "version": "1.14.4",
      "mapping": "mojmap",
      "path": "1.14.4/mojmap/net/minecraft/world/entity/LivingEntity.java",
      "member": "stopRiding",
      "lines": [
        [
          2325,
          2331
        ]
      ],
      "status": "verified-content",
      "sha256": "428762178a876efd4069086e6b7d51f571ea0401f44e7eff0927b7d26d9ef681",
      "detail": "Canonical source read. Matches digest in archived original."
    },
    {
      "version": "1.15.2",
      "mapping": "mojmap",
      "path": "1.15.2/mojmap/net/minecraft/world/entity/LivingEntity.java",
      "member": "stopRiding",
      "lines": [
        [
          2389,
          2395
        ]
      ],
      "status": "verified-content",
      "sha256": "46d243bb7e51f7b54404aa1d7d6e6b827682d0f5925d02847c4193306c4d5e54",
      "detail": "Canonical source read. Matches digest in archived original."
    }
  ],
  "sourceReviews": [],
  "openIssues": [],
  "runtime": "unverified"
}
```
