# Keyboard slowdown gate

1.13.2, ornithe-feather: `1.13.2/ornithe-feather/net/minecraft/client/entity/living/player/KeyboardInput.java`, `tick`, lines 13–50.
1.14.4, ornithe-feather: `1.14.4/ornithe-feather/net/minecraft/client/entity/living/player/KeyboardInput.java`, `tick`, lines 13–26.

Difference: 1.13.2 scales horizontal input by 0.3 whenever sneak is held. 1.14.4 scales only for a non-spectator with sneak held or pose-driven slow movement: not flying/swimming, crouch fits and shift held or standing does not fit, plus dry swimming-pose fallback.

Movement meaning: The local input tick supplies the horizontal impulses consumed by movement; pose and spectator gates can change that same-tick input scale.


## Current evidence and remaining work

Source decisions, implementation coverage and runtime parity are separate. Source paths are relative to `decompiled_minecraft/`; immutable Git evidence can be read with `git show <revision>:<path>`.

```json
{
  "implementation": {
    "status": "partial",
    "changes": [
      "src/main/java/me/wolfii/legacyparkourcompat/change/v1_13/SneakInputSlowdown.java",
      "src/main/java/me/wolfii/legacyparkourcompat/change/v1_14/SneakInputSlowdown.java"
    ],
    "hooks": [
      "SneakInputSlowdownHook"
    ],
    "limits": [
      "Registered SneakInputSlowdownHook returns held shift for V1_13 and pose-aware non-spectator predicate for V1_14. No corresponding >=0.8F versus >1.0E-5F underwater forward-threshold operation is registered; SprintInputStart controls start eligibility, not this numeric cutoff.",
      "Registered keyboard slowdown predicate changes held-shift to pose-aware non-spectator input. Legacy 1.14 base boundary remains recorded separately; no runtime claim."
    ]
  },
  "applicability": [
    "Endpoint interval (1.13.2, 1.14.4]; exact cutover remains the original evidence’s separate applicability gate."
  ],
  "sources": [
    {
      "version": "1.13.2",
      "mapping": "ornithe-feather",
      "path": "1.13.2/ornithe-feather/net/minecraft/client/entity/living/player/KeyboardInput.java",
      "member": "tick",
      "lines": [
        [
          13,
          50
        ]
      ],
      "status": "verified-content",
      "sha256": "7be11425906be051c83e275f359816546e4677b16d212156380e8d2e9258654a",
      "detail": "Canonical source read. Matches digest in archived original."
    },
    {
      "version": "1.14.4",
      "mapping": "ornithe-feather",
      "path": "1.14.4/ornithe-feather/net/minecraft/client/entity/living/player/KeyboardInput.java",
      "member": "tick",
      "lines": [
        [
          13,
          26
        ]
      ],
      "status": "verified-content",
      "sha256": "5932453a9e48e7a798ae1be1cd3a4bf660b6b3be43bb7e5dc22686b3c4a82526",
      "detail": "Canonical source read. Matches digest in archived original."
    },
    {
      "version": "1.13.2",
      "mapping": "ornithe-feather",
      "path": "1.13.2/ornithe-feather/net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java",
      "member": "mobTick",
      "lines": [
        [
          701,
          740
        ]
      ],
      "status": "verified-content",
      "sha256": "2583495f3a02b4791aa036e6a8d354d7596c4984761969a0f29b29d8d9bf42bf",
      "detail": "Canonical sprint-forward consumers read in original bounded range: >=0.8F; full-file hash matches original A evidence."
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
