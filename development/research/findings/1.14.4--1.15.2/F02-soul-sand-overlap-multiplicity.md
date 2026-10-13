# F02: Soul Sand slowdown is applied per overlapping cell in 1.14.4, once in 1.15.2

1.14.4, mojmap: `1.14.4/mojmap/net/minecraft/world/level/block/SoulsandBlock.java`, `entityInside`, lines 28–30.
1.15.2, mojmap: `1.15.2/mojmap/net/minecraft/world/level/block/SoulsandBlock.java`, `class/shape initialization`, lines 16–34.
1.14.4, mojmap: `1.14.4/mojmap/net/minecraft/world/entity/Entity.java`, `checkInsideBlocks`, lines 810–831.
1.15.2, mojmap: `1.15.2/mojmap/net/minecraft/world/entity/Entity.java`, `move, getBlockSpeedFactor`, lines 533–544, 590–598.

Difference: If the player's bounding box straddles the seam between two adjacent Soul Sand cells while standing on their 14/16-high top surface, 1.14.4's block-cell loop visits both cells. Each callback multiplies X and Z delta movement by `0.4`, so that movement invocation applies the factor twice. 1.15.2's callback is empty; its post-callback speed-factor lookup samples one block at the entity position and applies `0.4` once. The source proves the per-cell loop, callback behavior and single selected-cell factor path. Under the stated two-cell precondition, the horizontal multiplier is `0.4 * 0.4` in 1.14.4 and `0.4` in 1.15.2 for that movement invocation.

Movement meaning: Local player travel -> `Entity.move` -> `checkInsideBlocks` -> Soul Sand callbacks in 1.14.4; in 1.15.2, `Entity.move` continues to the one `getBlockSpeedFactor()` lookup. A player's 0.6-wide bounding box can intersect both cells near a seam while its center lies in one cell.


## Current evidence and remaining work

Source decisions, implementation coverage and runtime parity are separate. Source paths are relative to `decompiled_minecraft/`; immutable Git evidence can be read with `git show <revision>:<path>`.

```json
{
  "implementation": {
    "status": "partial",
    "changes": [
      "src/main/java/me/wolfii/legacyparkourcompat/change/v1_14/SoulSandOverlapChange.java"
    ],
    "hooks": [
      "AfterCollisionHook",
      "BlockSpeedFactorHook"
    ],
    "limits": [
      "Current code correctly restores per-overlap old Soul Sand multiplication, which contradicts the campaign report’s omitted-callback no-slowdown premise. Coverage belongs to corrected multiplicity finding, not historical original wording.",
      "BlockSpeedFactorHook suppresses modern Soul Sand factor and AfterCollisionHook scans bbox cells inset by 0.001, multiplying x/z by 0.4 once per overlapped Soul Sand cell. Both interfaces register the shared instance."
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
          533,
          550
        ]
      ],
      "status": "different",
      "sha256": "31c6be42d165102d3e6dc4295fdfef6e8971fc3aea13f938a5b3a33b91d524a7",
      "detail": "Canonical source read. Exact canonical file digest is absent/different in original: snippet content checked, original publication equivalence not asserted."
    },
    {
      "version": "1.15.2",
      "mapping": "mojmap",
      "path": "1.15.2/mojmap/net/minecraft/world/entity/Entity.java",
      "member": "move, getBlockSpeedFactor",
      "lines": [
        [
          533,
          544
        ],
        [
          590,
          598
        ]
      ],
      "status": "verified-content",
      "sha256": "191b3ad3e7348c9bac1e703fff896706d23a751bf15162aacf676f5f97c0a10e",
      "detail": "Canonical source read. Matches digest in archived original."
    },
    {
      "version": "1.14.4",
      "mapping": "mojmap",
      "path": "1.14.4/mojmap/net/minecraft/world/level/block/SoulsandBlock.java",
      "member": "entityInside",
      "lines": [
        [
          28,
          30
        ]
      ],
      "status": "verified-content",
      "sha256": "3f2ce73aae23fb7e13cf001123343c6c750f00684b5331a5597ae33b011ef4e4",
      "detail": "Canonical source read. Matches digest in archived original."
    },
    {
      "version": "1.15.2",
      "mapping": "mojmap",
      "path": "1.15.2/mojmap/net/minecraft/world/level/block/SoulsandBlock.java",
      "member": "class/shape initialization",
      "lines": [
        [
          16,
          34
        ]
      ],
      "status": "verified-content",
      "sha256": "355f0cc25e78dce76cbc9c89d8bd8ff1956d4b53780cba8e3fb5c23f485802cf",
      "detail": "Canonical source read. Matches digest in archived original."
    },
    {
      "version": "1.14.4",
      "mapping": "mojmap",
      "path": "1.14.4/mojmap/net/minecraft/world/entity/Entity.java",
      "member": "checkInsideBlocks",
      "lines": [
        [
          810,
          831
        ]
      ],
      "status": "verified-content",
      "sha256": "31c6be42d165102d3e6dc4295fdfef6e8971fc3aea13f938a5b3a33b91d524a7",
      "detail": "Canonical source read. Matches digest in archived original."
    }
  ],
  "sourceReviews": [
    {
      "verdict": "accepted, bounded",
      "assertion": "Player speed factor changes on Soul Sand.",
      "assertion_preserved": false,
      "detail": "Historical accepted original retained. Omitted old Soul Sand contact callback invalidates no-slowdown conclusion; corrected multiplicity claim needs independent review before implementation reliance.",
      "snapshot": "4a0c35f2008d785867c00360a7b72726e3506435:workflows/source-campaign-2026-10-07/1.14.4--1.15.2/findings/F-SOUL-SAND-SPEED.md",
      "snapshot_sha256": "a43043bed85d233478d323621ed97ef7ac27e7fc9a286f439dc2cd5280604f7e",
      "identity": "4a5eae32b5819418dbd59eb4a763ee24ea1f2c52:development/research/reconciliation/review-decisions/5232d177a412d132df73bd7807c415f751e3babc9396f649a259db0965f60ace.md"
    }
  ],
  "openIssues": [
    {
      "missing": "Original digest binding for 1.14.4/mojmap/net/minecraft/world/entity/Entity.java is absent/different; canonical digest 31c6be42d165102d3e6dc4295fdfef6e8971fc3aea13f938a5b3a33b91d524a7 was inspected.",
      "next_action": "Resolve against the cited archived publication/source manifest; do not assert byte-equivalence from this content check."
    }
  ],
  "runtime": "unverified"
}
```
