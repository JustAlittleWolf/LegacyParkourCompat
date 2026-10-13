# F-014: default Overworld minY changes unloaded-chunk fall velocity

1.17.1, mojmap: `1.17.1/mojmap/net/minecraft/world/entity/LivingEntity.java`, `travel`, lines 2117–2126.
1.18.2, mojmap: `1.18.2/mojmap/net/minecraft/world/entity/LivingEntity.java`, `travel`, lines 2123–2132.
1.17.1, mojmap: `1.17.1/mojmap/net/minecraft/world/level/dimension/DimensionType.java`, `DEFAULT_OVERWORLD`, lines 78–97.
1.18.2, mojmap: `1.18.2/mojmap/net/minecraft/world/level/dimension/DimensionType.java`, `DEFAULT_OVERWORLD`, lines 78–96.

Difference: Both ordinary-air travel branches use level minY when the XZ chunk is missing and Levitation is absent. Built-in Overworld minY is 0 in 1.17.1 and -64 in 1.18.2. At -64 < player Y <= 0, this writes 0.0 versus -0.1 before the shared vertical friction step (0.0 versus -0.098 when friction applies). Equal server-synchronized minY removes the difference.

Movement meaning: The LocalPlayer ordinary tick reaches inherited `LivingEntity#aiStep` and `travel` when alive, not passenger-controlled, not in water/lava, not fall-flying or ability-flying, and with no Levitation effect. A player can fall below Y=0; `hasChunkAt(BlockPos)` is an XZ chunk-presence test, so an absent XZ chunk satisfies the client fallback independent of the block position's Y.


## Current evidence and remaining work

Source decisions, implementation coverage and runtime parity are separate. Source paths are relative to `decompiled_minecraft/`; immutable Git evidence can be read with `git show <revision>:<path>`.

```json
{
  "implementation": {
    "status": "open",
    "changes": [],
    "hooks": [],
    "limits": [
      "Current annotated Change/interface/catalog inventory contains no operation implementing: F-014: default Overworld minY changes unloaded-chunk fall velocity. Existing unrelated later operations do not establish coverage."
    ]
  },
  "applicability": [
    "Endpoint interval (1.17.1, 1.18.2]; exact cutover remains the original evidence’s separate applicability gate."
  ],
  "sources": [
    {
      "version": "1.17.1",
      "mapping": "mojmap",
      "path": "1.17.1/mojmap/net/minecraft/world/entity/LivingEntity.java",
      "member": "travel",
      "lines": [
        [
          2117,
          2126
        ]
      ],
      "status": "verified-content",
      "sha256": "33fd081aadb2b6fdc9ebf487db6da5b38c54f4b8676572790ee2203690d15e6f",
      "detail": "Canonical source read. Matches digest in archived original."
    },
    {
      "version": "1.18.2",
      "mapping": "mojmap",
      "path": "1.18.2/mojmap/net/minecraft/world/entity/LivingEntity.java",
      "member": "travel",
      "lines": [
        [
          2123,
          2132
        ]
      ],
      "status": "verified-content",
      "sha256": "db4168d531caf18f22e3fefd073365e776da4075ce01452bb9f7671d9b458782",
      "detail": "Canonical source read. Matches digest in archived original."
    },
    {
      "version": "1.17.1",
      "mapping": "mojmap",
      "path": "1.17.1/mojmap/net/minecraft/world/level/dimension/DimensionType.java",
      "member": "DEFAULT_OVERWORLD",
      "lines": [
        [
          78,
          97
        ]
      ],
      "status": "verified-content",
      "sha256": "2dc118f16b671d3b1b9736c10e960e199790f7930cfa18983f379beb8d00f27b",
      "detail": "Canonical source read: DEFAULT_OVERWORLD supplies minY 0 and height/logicalHeight 256 in 1.17.1; minY -64 and height/logicalHeight 384 in 1.18.2. Exact digest matches archived original."
    },
    {
      "version": "1.18.2",
      "mapping": "mojmap",
      "path": "1.18.2/mojmap/net/minecraft/world/level/dimension/DimensionType.java",
      "member": "DEFAULT_OVERWORLD",
      "lines": [
        [
          78,
          96
        ]
      ],
      "status": "verified-content",
      "sha256": "0c1d2268dde59ef4cf7378fe3ba083dac669a6705f399a23c8138988c29c7681",
      "detail": "Canonical source read: DEFAULT_OVERWORLD supplies minY 0 and height/logicalHeight 256 in 1.17.1; minY -64 and height/logicalHeight 384 in 1.18.2. Exact digest matches archived original."
    }
  ],
  "sourceReviews": [
    {
      "verdict": "ACCEPT, bounded",
      "assertion": " Default Overworld bounds differ: A has min Y `0` and height `256`; B has min Y `-64` and height `384`. In `LivingEntity.travel`, the client no-chunk fallback uses `y <= minBuildHeight ? 0 : -0.1`; `LevelReader.hasChunkAt(BlockPos)` checks horizontal chunk availability, and the later vertical friction multiplication can yield `-0.098` when not discarded. The source fact is accepted only under the finding's attached conditions: default Overworld, client-side ordinary travel, unloaded X/Z chunk, no levitation, and Y in `(-64, 0]`. It must not be generalized to other dimensions, loaded chunks, server travel, or all falls, nor automatically treated as an eligible old-map mechanic. World-height/environment state is not a historical block mechanic.",
      "assertion_preserved": true,
      "detail": "Exact original snapshot verdict; current compact wording does not renew acceptance. Later identity-only revision, where present, remains separate.",
      "snapshot": "e531086eecc778432b40b2b9ef39499b8a7f0dba:workflows/source-campaign-2026-10-07/1.17.1--1.18.2/findings/F-014-overworld-min-y-unloaded-chunk-fall.md",
      "snapshot_sha256": "65161ef4a77c6c369af2a74e8a1cc4673cf2f724c8b94fa826548c04a05ae25a",
      "identity": "4a5eae32b5819418dbd59eb4a763ee24ea1f2c52:development/research/archive/legacy-workflows/source-boundary-reviews/2026-10-08-mc1171-1182-snapshot-review.md"
    }
  ],
  "openIssues": [],
  "runtime": "unverified"
}
```
