# F-S6-LEVITATION-ELYTRA-GATES: Levitation blocks elytra glide start and continuation in 1.16.5

1.15.2, mojmap: `1.15.2/mojmap/net/minecraft/world/entity/player/Player.java`, `tryToStartFallFlying`, lines 1578–1588.
1.16.5, mojmap: `1.16.5/mojmap/net/minecraft/world/entity/player/Player.java`, `tryToStartFallFlying`, lines 1520–1530.

Difference: Under the same airborne, non-gliding, outside-water and usable chest-Elytra state, 1.15.2 tryToStartFallFlying permits a player with Levitation; 1.16.5 adds !hasEffect(LEVITATION) and rejects the local start. The separately observed continuation flag writer runs only on the server and remains a central source disposition.

Movement meaning: Client jump input -> LocalPlayer Elytra/jump gates -> Player.tryToStartFallFlying -> Player.startFallFlying / bit-7 write -> serverbound START_FALL_FLYING -> server tryToStartFallFlying recheck -> server updateFallFlying before travel -> tracked entity-data update to client.


## Current evidence and remaining work

Source decisions, implementation coverage and runtime parity are separate. Source paths are relative to `decompiled_minecraft/`; immutable Git evidence can be read with `git show <revision>:<path>`.

```json
{
  "implementation": {
    "status": "open",
    "changes": [],
    "hooks": [],
    "limits": [
      "Current annotated Change/interface/catalog inventory contains no operation implementing: F-S6-LEVITATION-ELYTRA-GATES: Levitation blocks elytra glide start and continuation in 1.16.5. Existing unrelated later operations do not establish coverage."
    ]
  },
  "applicability": [
    "Endpoint interval (1.15.2, 1.16.5]; exact cutover remains the original evidence’s separate applicability gate."
  ],
  "sources": [
    {
      "version": "1.15.2",
      "mapping": "mojmap",
      "path": "1.15.2/mojmap/net/minecraft/world/entity/player/Player.java",
      "member": "tryToStartFallFlying",
      "lines": [
        [
          1578,
          1588
        ]
      ],
      "status": "verified-content",
      "sha256": "1ba2724c22163862b8f7fdfdea5a04a66e4db26a119d7e5360ba024724a34793",
      "detail": "Canonical source read. Matches digest in archived original."
    },
    {
      "version": "1.16.5",
      "mapping": "mojmap",
      "path": "1.16.5/mojmap/net/minecraft/world/entity/player/Player.java",
      "member": "tryToStartFallFlying",
      "lines": [
        [
          1520,
          1530
        ]
      ],
      "status": "verified-content",
      "sha256": "d2e26589bdb6a20dc914266db06aa48f50811efc792d6e63b3008c9199914960",
      "detail": "Canonical source read. Matches digest in archived original."
    }
  ],
  "sourceReviews": [
    {
      "verdict": "ACCEPT, endpoint behavior; implementation handoff blocked",
      "assertion": "Levitation rejects local start in B; server continuation also adds the negative-effect guard. Endpoint supplied-state/caller/synchronization dependencies closed, exact intervening boundary remains open.",
      "assertion_preserved": true,
      "review_sha256": "324abf649108ee317a0dacaecab0c02b8aec56abe588e37b557aab3f73d40584",
      "snapshot": "16d73824f288c22a5b17b639d1c5b83682c92f50",
      "snapshot_sha256": "1c0e43c5597aa1b63219e54c1fdea135b0b689c91d7f3639ae3da262c86c7905",
      "identity": "4a5eae32b5819418dbd59eb4a763ee24ea1f2c52:development/research/archive/legacy-workflows/source-campaign-2026-10-07/1.15.2--1.16.5/reviews/F-S6-LEVITATION-ELYTRA-GATES-independent-2026-10-10.md"
    }
  ],
  "openIssues": [],
  "runtime": "unverified"
}
```
