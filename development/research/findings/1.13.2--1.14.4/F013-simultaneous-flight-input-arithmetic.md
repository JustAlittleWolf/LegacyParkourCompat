# F013: Simultaneous sneak and jump flight arithmetic

1.13.2, ornithe-feather: `1.13.2/ornithe-feather/net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java`, `mobTick`, lines 797–806.
1.14.4, ornithe-feather: `1.14.4/ornithe-feather/net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java`, `mobTick`, lines 728–743.

Difference: Under `abilities.flying && isCamera()`, 1.13.2 handles sneak and jump in separate conditionals: sneaking subtracts `flySpeed * 3.0F` from double `velocityY`, then jumping adds the same float product. 1.14.4 increments/decrements integer `j` for those inputs and applies one vector update only when `j != 0`. With both inputs held, 1.14.4 leaves vertical velocity untouched; 1.13.2 executes two double assignments. Lateral input unscaling on sneak occurs in both.

Movement meaning: Preconditions are flying ability, local camera player, simultaneous sneak+jump input.


## Current evidence and remaining work

Source decisions, implementation coverage and runtime parity are separate. Source paths are relative to `decompiled_minecraft/`; immutable Git evidence can be read with `git show <revision>:<path>`.

```json
{
  "implementation": {
    "status": "open",
    "changes": [],
    "hooks": [],
    "limits": [
      "Current annotated Change/interface/catalog inventory contains no operation implementing: F013: Simultaneous sneak and jump flight arithmetic. Existing unrelated later operations do not establish coverage."
    ]
  },
  "applicability": [
    "Endpoint interval (1.13.2, 1.14.4]; exact cutover remains the original evidence’s separate applicability gate."
  ],
  "sources": [
    {
      "version": "1.13.2",
      "mapping": "ornithe-feather",
      "path": "1.13.2/ornithe-feather/net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java",
      "member": "mobTick",
      "lines": [
        [
          797,
          806
        ]
      ],
      "status": "verified-content",
      "sha256": "2583495f3a02b4791aa036e6a8d354d7596c4984761969a0f29b29d8d9bf42bf",
      "detail": "Canonical source read. Matches digest in archived original."
    },
    {
      "version": "1.14.4",
      "mapping": "ornithe-feather",
      "path": "1.14.4/ornithe-feather/net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java",
      "member": "mobTick",
      "lines": [
        [
          728,
          743
        ]
      ],
      "status": "verified-content",
      "sha256": "708af6a3880fb58b67bf4604a5509b351719a9c2a0c06a8ec261586435bf00ce",
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
