# F11: grounded slime-block damping uses a broader step callback gate in 1.15.2

1.14.4, mojmap: `1.14.4/mojmap/net/minecraft/world/entity/Entity.java`, `move`, lines 508–518.
1.15.2, mojmap: `1.15.2/mojmap/net/minecraft/world/entity/Entity.java`, `move`, lines 499–501.

Difference: For a grounded, non-shift player on a slime block, 1.15.2 calls `stepOn` even when the player has flying ability enabled or is a passenger. 1.14.4 suppresses the callback in either state: flying makes `Player.makeStepSound()` false, and passengers fail the explicit passenger condition. Since `SlimeBlock.stepOn` writes horizontal delta movement, 1.15.2 applies the horizontal multiplier `0.4 + abs(vY) * 0.2` in these states while 1.14.4 does not. For ordinary grounded non-flying, non-passenger players, the callback gates and slime damping conditions correspond.

Movement meaning: Player movement -> `Entity.move` collision resolution -> selected support block -> `Block.stepOn` -> `SlimeBlock.stepOn` -> horizontal delta-movement write.


## Current evidence and remaining work

Source decisions, implementation coverage and runtime parity are separate. Source paths are relative to `decompiled_minecraft/`; immutable Git evidence can be read with `git show <revision>:<path>`.

```json
{
  "implementation": {
    "status": "open",
    "changes": [],
    "hooks": [],
    "limits": [
      "Current annotated Change/interface/catalog inventory contains no operation implementing: F11: grounded slime-block damping uses a broader step callback gate in 1.15.2. Existing unrelated later operations do not establish coverage."
    ]
  },
  "applicability": [
    "Endpoint interval (1.14.4, 1.15.2]; exact cutover remains the original evidence’s separate applicability gate."
  ],
  "sources": [
    {
      "version": "1.14.4",
      "mapping": "mojmap",
      "path": "1.14.4/mojmap/net/minecraft/world/entity/Entity.java",
      "member": "move",
      "lines": [
        [
          508,
          518
        ]
      ],
      "status": "verified-content",
      "sha256": "31c6be42d165102d3e6dc4295fdfef6e8971fc3aea13f938a5b3a33b91d524a7",
      "detail": "Canonical source read. Matches digest in archived original."
    },
    {
      "version": "1.15.2",
      "mapping": "mojmap",
      "path": "1.15.2/mojmap/net/minecraft/world/entity/Entity.java",
      "member": "move",
      "lines": [
        [
          499,
          501
        ]
      ],
      "status": "verified-content",
      "sha256": "191b3ad3e7348c9bac1e703fff896706d23a751bf15162aacf676f5f97c0a10e",
      "detail": "Canonical source read. Matches digest in archived original."
    }
  ],
  "sourceReviews": [],
  "openIssues": [],
  "runtime": "unverified"
}
```
