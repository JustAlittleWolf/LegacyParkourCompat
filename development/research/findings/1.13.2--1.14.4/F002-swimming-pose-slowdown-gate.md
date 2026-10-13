# F002: Swimming state suppresses the pose-based sneak slowdown gate

1.14, ornithe-feather: `1.14/ornithe-feather/net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java`, `m_63723874`, lines 597.
1.14.4, ornithe-feather: `1.14.4/ornithe-feather/net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java`, `m_63723874`, lines 596–598.

Difference: At the exact 1.14 base, `m_63723874()` checks `!abilities.flying && canPose(SNEAKING)` before testing the sneak key or whether standing fits. In 1.14.4 it checks `!abilities.flying && !isSwimming() && canPose(SNEAKING)` before the same expression. The surrounding `m_99544176()` alternative remains true for the swimming pose outside water, so this added guard specifically suppresses the first gate while the player is swimming; it does not remove the outside-water swimming-pose path.

Movement meaning: The local player's `mobTick()` evaluates this predicate and passes it to `KeyboardInput.tick(...)`.


## Current evidence and remaining work

Source decisions, implementation coverage and runtime parity are separate. Source paths are relative to `decompiled_minecraft/`; immutable Git evidence can be read with `git show <revision>:<path>`.

```json
{
  "implementation": {
    "status": "partial",
    "changes": [
      "src/main/java/me/wolfii/legacyparkourcompat/change/v1_14/SneakInputSlowdown.java"
    ],
    "hooks": [
      "SneakInputSlowdownHook"
    ],
    "limits": [
      "V1_14 slowdown body contains !isSwimming() matching 1.14.4 endpoint; single V1_14 catalog entry does not distinguish base 1.14/1.14.1 predicate without that guard."
    ]
  },
  "applicability": [
    "Endpoint interval (1.13.2, 1.14.4]; exact cutover remains the original evidence’s separate applicability gate."
  ],
  "sources": [
    {
      "version": "1.14",
      "mapping": "ornithe-feather",
      "path": "1.14/ornithe-feather/net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java",
      "member": "m_63723874",
      "lines": [
        [
          597,
          597
        ]
      ],
      "status": "verified-content",
      "sha256": "2b1ad3b3416949a9da2607a3ec2251aa69b2d9eebd76638c92e20749d625338f",
      "detail": "Canonical source read. Matches digest in archived original."
    },
    {
      "version": "1.14.4",
      "mapping": "ornithe-feather",
      "path": "1.14.4/ornithe-feather/net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java",
      "member": "m_63723874",
      "lines": [
        [
          596,
          598
        ]
      ],
      "status": "verified-content",
      "sha256": "708af6a3880fb58b67bf4604a5509b351719a9c2a0c06a8ec261586435bf00ce",
      "detail": "Canonical source read. Matches digest in archived original."
    }
  ],
  "sourceReviews": [],
  "openIssues": [],
  "runtime": "unverified"
}
```
