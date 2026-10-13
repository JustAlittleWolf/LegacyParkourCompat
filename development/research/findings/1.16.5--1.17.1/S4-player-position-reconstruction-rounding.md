# Entity movement reconstructs horizontal position in a different order

1.16.5, mojmap: `1.16.5/mojmap/net/minecraft/world/entity/Entity.java`, `move, setLocationFromBoundingbox`, lines 487–489, 505–509, 801–804.
1.17.1, mojmap: `1.17.1/mojmap/net/minecraft/world/entity/Entity.java`, `move`, lines 534–536, 554–556.

Difference: For a collision result with nonzero horizontal movement, 1.16.5 first rounds each translated box endpoint and then rounds their midpoint to recover player position. 1.17.1 adds the movement component directly to the stored coordinate, then builds a new box around that coordinate. For the player width `0.6F`, at X `0.1` with horizontal displacement `0.1`, those source expressions produce 1.16.5 X `0.19999999999999998` and 1.17.1 X `0.2` under IEEE-754 double arithmetic. The expression order is source-proven; the numeric example illustrates its rounding consequence.

Movement meaning: The path is the shared player `Entity.move` implementation in open space or after collision clipping. 1.16.5 uses the reconstructed position in `setPosRaw`; 1.17.1 stores the direct sum through `setPos`.


## Current evidence and remaining work

Source decisions, implementation coverage and runtime parity are separate. Source paths are relative to `decompiled_minecraft/`; immutable Git evidence can be read with `git show <revision>:<path>`.

```json
{
  "implementation": {
    "status": "open",
    "changes": [],
    "hooks": [],
    "limits": [
      "Current annotated Change/interface/catalog inventory contains no operation implementing: Entity movement reconstructs horizontal position in a different order. Existing unrelated later operations do not establish coverage."
    ]
  },
  "applicability": [
    "Endpoint interval (1.16.5, 1.17.1]; exact cutover remains the original evidence’s separate applicability gate."
  ],
  "sources": [
    {
      "version": "1.16.5",
      "mapping": "mojmap",
      "path": "1.16.5/mojmap/net/minecraft/world/entity/Entity.java",
      "member": "move, setLocationFromBoundingbox",
      "lines": [
        [
          487,
          489
        ],
        [
          505,
          509
        ],
        [
          801,
          804
        ]
      ],
      "status": "verified-content",
      "sha256": "f9a9a073fe3105a0aa53d0f21ec72e59084e8d21a14c1cd3be75703865ee2666",
      "detail": "Canonical source read. Matches digest in archived original."
    },
    {
      "version": "1.17.1",
      "mapping": "mojmap",
      "path": "1.17.1/mojmap/net/minecraft/world/entity/Entity.java",
      "member": "move",
      "lines": [
        [
          534,
          536
        ],
        [
          554,
          556
        ]
      ],
      "status": "verified-content",
      "sha256": "ab28e1fba924771ec048140dfd293ee5a46a7dfe81f71a1a0b1aecc1927232de",
      "detail": "Canonical source read. Matches digest in archived original."
    }
  ],
  "sourceReviews": [],
  "openIssues": [],
  "runtime": "unverified"
}
```
