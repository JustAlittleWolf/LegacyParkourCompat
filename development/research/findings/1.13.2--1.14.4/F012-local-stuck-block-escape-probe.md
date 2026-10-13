# F012: Local stuck-block escape probe

1.13.2, ornithe-feather: `1.13.2/ornithe-feather/net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java`, `pushAwayFrom, mobTick`, lines 401–455, 717–721.
1.14.4, ornithe-feather: `1.14.4/ornithe-feather/net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java`, `addMessage, mobTick`, lines 388–447, 645–651.

Difference: 1.13.2 invokes `pushAwayFrom` at four points before sprint checks; its method returns immediately for noClip. 1.14.4 surrounds the four calls with `!noClip`. Those guards are equivalent at the call boundary. The recovery probe differs: 1.13.2 tests one BlockPos; when not swimming it uses `!doesNotSuffocate(pos)`, where `doesNotSuffocate` requires `shouldSuffocate(pos)` and a non-solid block above; when swimming it uses `!shouldSuffocate(pos)`. 1.13.2's neighbor candidates use `doesNotSuffocate` directly. 1.14.4 scans integer Y positions from floor of player box minY to ceil of maxY and reports collision if any `shouldSuffocate` check is false; neighbor candidates use the same full scan. 1.14.4's `shouldSuffocate` uses `!state.isViewBlocking(world,pos)`; 1.13.2 uses `!state.isSolid()`.

Movement meaning: LocalClientPlayerEntity.mobTick -> four push-away probes -> suffocation predicates for current/neighbor blocks -> direct velocity component assignment to ±0.1.


## Current evidence and remaining work

Source decisions, implementation coverage and runtime parity are separate. Source paths are relative to `decompiled_minecraft/`; immutable Git evidence can be read with `git show <revision>:<path>`.

```json
{
  "implementation": {
    "status": "partial",
    "changes": [
      "src/main/java/me/wolfii/legacyparkourcompat/change/v1_15_2/SuffocationProbe.java",
      "src/main/java/me/wolfii/legacyparkourcompat/change/v1_16/SuffocationProbe.java"
    ],
    "hooks": [
      "SuffocationProbeHook"
    ],
    "limits": [
      "Registered SuffocationProbeHook scans a box-height column. It has no 1.13.2 single-cell/swimming neighbor predicate reconstruction; registered later probes are relevant machinery, not coverage of this old operation."
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
      "member": "pushAwayFrom, mobTick",
      "lines": [
        [
          401,
          455
        ],
        [
          717,
          721
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
      "member": "addMessage, mobTick",
      "lines": [
        [
          388,
          447
        ],
        [
          645,
          651
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
