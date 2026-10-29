# Independent review: 1.8.9 Block bounds write choke point

Verdict: **ACCEPT**, bounded to the source-level write/reset audit for the exact 1.8.9 Ornithe Feather Gen2 build 2 export. This does not establish an implemented observer, lifecycle cache validity, recovery, or DDA adapter readiness.

## Verified claims

- Independently searched the canonical `net/minecraft/block` Java package for assignment/update expressions to `minX`, `minY`, `minZ`, `maxX`, `maxY`, and `maxZ`. The result is exactly 14 writes: six in `Block.setShape`, six in `MovingBlock`, one in `StemBlock`, and one in `WallBlock`. The search used the optional `this.` form and also covered unqualified field assignments, matching the report's listed results.
- `Block.setShape` is `protected final` and writes all six instance fields in order. A `setShape`-only method hook cannot observe `MovingBlock`'s six direct stores or `WallBlock`'s post-`updateShape` `maxY = 1.5`. `StemBlock.updateShape` stores `maxY` from `AGE`, then calls `setShape` with that value; this does not erase the fact that the field had a separate direct store.
- The exact package search finds 16 `resetShape` declarations total: the base method plus 15 subclass overrides, agreeing with the report's count. The base implementation is empty; the overrides are class-specific. This is not a general registry-wide reconstruction method. `MovingBlock` and `WallBlock` have no override in the searched package.
- The whole-export reflection search result contains only two `Constructor.setAccessible(true)` matches in `RealmsBridge`; it shows no reflective access to the Block bounds. This supports the report's explicitly source-text-bounded negative claim, not a claim about transformed bytecode or third-party code.
- The source/artifact ledgers have 18 canonical source entries and 7 report artifacts respectively. I recomputed both ledgers: zero mismatches. The direct field-write result also matches the canonical source search exactly.

The report's conclusion follows: observing `setShape` alone is incomplete. Observing every direct field store could be an instrumentation design in the vanilla source, but source provides no universal reset/replay protocol that establishes cache recovery after an unknown write. Therefore the report correctly leaves cache validity and adapter readiness open.

## Bound artifact identities

| Artifact | SHA-256 |
|---|---|
| `dda-bounds-write-chokepoint-20261029/report.md` | `D65AE3192E811F862F524E381476823D3B8ECE18F6075BDF991B51563D3789BF` |
| `artifact-hashes.json` | `1D76F474524C8B97607995E41BCF7CBE870F7C3E61E2F26051BF349C37581A73` |
| `source-hashes.json` | `BC3F8129EE2BD774EF9142414E6600998B9F29ED01229AC75E132B9B55EB3660` |
| `direct-field-writes.txt` | `0AC210097C28CCBBA25419677AAFB62E448C484784A0FDC965868FA27D1DE8A9` |
| `all-bound-name-write-expressions.txt` | `0AC210097C28CCBBA25419677AAFB62E448C484784A0FDC965868FA27D1DE8A9` |
| `resetShape-overrides.txt` | `49B42AB798212FA21D60CA096B3F148E923893948C1E68B8E403805F79759AF1` |
| `reflection-field-access-search.txt` | `267E30489DE49AD95601452A264EB00041D7E09860F93619A9432B2BC8CC5AA2` |
| `scan-method.md` | `861B88B27BFFA04B9BFAFAC3AFE4813153E3FE076534AE7411BF30EBD68C0A4E` |

No product code, tests, builds, or runtime were inspected or used.
