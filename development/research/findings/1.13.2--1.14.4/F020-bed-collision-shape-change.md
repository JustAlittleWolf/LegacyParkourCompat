# F020: Bed lower collision footprint changes

1.13.2, ornithe-feather: `1.13.2/ornithe-feather/net/minecraft/block/BedBlock.java`, `getDropItem`, lines 38–42, 209–212.
1.14.4, ornithe-feather: `1.14.4/ornithe-feather/net/minecraft/block/BedBlock.java`, `getShape`, lines 48–56, 180–192.

Difference: Under identical bed state and world position, 1.13.2 returns one solid box covering the full 16-by-16 footprint from y=0 to y=9/16. 1.14.4 returns a 16-by-16 mattress from y=3/16 to y=9/16 plus two 3-by-3 corner support boxes from y=0 to y=3/16. `FACING` and `PART` choose which two corners support each bed half; the adjacent head/foot half selects the other pair. The bed registrations and inherited collision-enabled default match. 1.14.4's collision context is not read by this shape method.

Movement meaning: Player `Entity.move` collision resolution (S007-S009) -> world block-collision enumeration (S020) -> `BlockState.getCollisionShape` -> inherited `Block.getCollisionShape` -> `BedBlock.getShape`.


## Current evidence and remaining work

Source decisions, implementation coverage and runtime parity are separate. Source paths are relative to `decompiled_minecraft/`; immutable Git evidence can be read with `git show <revision>:<path>`.

```json
{
  "implementation": {
    "status": "open",
    "changes": [],
    "hooks": [],
    "limits": [
      "Current annotated Change/interface/catalog inventory contains no operation implementing: F020: Bed lower collision footprint changes. Existing unrelated later operations do not establish coverage."
    ]
  },
  "applicability": [
    "Endpoint interval (1.13.2, 1.14.4]; exact cutover remains the original evidence’s separate applicability gate."
  ],
  "sources": [
    {
      "version": "1.13.2",
      "mapping": "ornithe-feather",
      "path": "1.13.2/ornithe-feather/net/minecraft/block/BedBlock.java",
      "member": "getDropItem",
      "lines": [
        [
          38,
          42
        ],
        [
          209,
          212
        ]
      ],
      "status": "verified-content",
      "sha256": "a274ed3d3dd8e4f4895fe1f3d1444425a9248e9cd41bc6e1f2677af7dacbec18",
      "detail": "Canonical source read. Matches digest in archived original."
    },
    {
      "version": "1.14.4",
      "mapping": "ornithe-feather",
      "path": "1.14.4/ornithe-feather/net/minecraft/block/BedBlock.java",
      "member": "getShape",
      "lines": [
        [
          48,
          56
        ],
        [
          180,
          192
        ]
      ],
      "status": "verified-content",
      "sha256": "aab431c7d85bc654b74215d2b2be5fbe6de81643f5e2be466658d918f272abe1",
      "detail": "Canonical source read. Matches digest in archived original."
    }
  ],
  "sourceReviews": [],
  "openIssues": [],
  "runtime": "unverified"
}
```
