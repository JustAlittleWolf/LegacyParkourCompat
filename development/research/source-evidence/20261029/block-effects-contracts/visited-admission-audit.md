# Independent source audit: 1.21.4–1.21.9 visited-block admission

## Verdict

**ACCEPT, qualified, for the bounded admission-order and callback-eligibility claims.** The exact source transition is accurately identified: 1.21.4 through 1.21.8 add a non-air position to `visitedBlocks` before the applicable shape/fluid tests; 1.21.9 adds it only after at least one applicable test succeeds. The set is shared across the movement list and cleared after list processing. This proves a conditional same-pass retry eligibility difference, not a runtime trajectory or pair-wide movement result.

Refinement for the four named callbacks: Web, Sweet Berry Bush, and Honey use the default `getEntityInsideCollisionShape`, which returns the `Shapes.block()` sentinel in the checked releases. When one of these positions is enumerated, its block-shape gate succeeds without a swept-shape test, so the 1.21.9 post-test gate does not create a retry after a shape miss for those callbacks. A later invocation can still occur if an earlier segment did not enumerate the position. Powder Snow has a specialized inside shape from 1.21.5 onward; an empty contextual collision shape is converted to the sentinel, while a nonempty shape goes through the swept test. This makes a shape-miss/retry path possible for Powder Snow when the entity-dependent shape is nonempty and the first segment misses it. The author report's general missed-test condition remains valid; the callback-specific wording should be read with these shape details.

## Independent source identity and coverage

I first inspected the canonical Mojmap trees and verified each listed Java file against its release-parent source manifest or embedded provenance source map. Counts, file sets and hashes all agree with zero extras, missing files, or mismatches:

| Release | Identity source | Entries verified |
| --- | --- | ---: |
| 1.21.4 | release-parent `mojmap.sources.sha256`, matching ready marker | 5,744 |
| 1.21.5 | release-parent `mojmap.sources.sha256`, matching ready marker | 5,921 |
| 1.21.6 | `mojmap/.decompile-provenance.json` embedded `sources` map | 6,103 |
| 1.21.7 | `mojmap/.decompile-provenance.json` embedded `sources` map | 6,104 |
| 1.21.8 | release-parent `mojmap.sources.sha256`, matching ready marker | 6,105 |
| 1.21.9 | `mojmap/.decompile-provenance.json` embedded `sources` map | 6,386 |

The release-parent ready markers exist for 1.21.4, 1.21.5 and 1.21.8; the three other source trees have no release-parent ready marker and use their embedded maps for source identity. Identity and whole-tree verification details are in `independent-source-identity-audit.tsv`; selected source hashes are in `selected-source-hashes.tsv`. Both files are part of this audit bundle.

## Admission order confirmed directly

- **1.21.4:** `Entity.checkInsideBlocks(List<Movement>, Set<BlockState>)` rejects air, then calls `visitedBlocks.add` at `Entity.java:1064–1065`. It fetches the inside collision shape and accepts the `Shapes.block()` sentinel or a swept-shape intersection at `:1067–1069`; a failed shape check continues after the key has already been recorded. This method has no fluid test. The set is cleared after the full list at `:1088`.
- **1.21.5:** `Entity.checkInsideBlocks` adds the key at `:1090–1092` before computing the shape result. It dispatches the block callback only after that shape succeeds (`:1093–1107`) and checks fluid separately afterward (`:1109–1113`). A miss in both tests still leaves the key visited. The set is cleared after the movement list at `:1120`.
- **1.21.6–1.21.8:** the `Entity.java` source hashes match across all three releases. Axis-independent movement records may be split into axis-ordered segments (`:1117–1131`); the shared set is passed to each per-segment helper. That helper adds the key at `:1151` before shape and fluid tests at `:1155–1156` and `:1172–1176`. Clearing is after all movement records at `:1134`.
- **1.21.9:** `Entity.checkInsideBlocks` splits eligible records using their original axis-dependent movement hint and retains a per-segment block cap (`Entity.java:1138–1157`). The helper computes the shape and fluid results first (`:1187–1189`), then checks `(shapeHit || fluidHit) && visitedBlocks.add(pos)` at `:1190`. The block callback follows only on `shapeHit`, and the fluid callback only on `fluidHit` (`:1191–1209`). If both miss, the key remains absent; if either succeeds, the key is added before callback dispatch. The set is cleared after all records at `:1160`.

Therefore, 1.21.9 is the earliest post-test admission gate among the six inspected releases. A prior shape or fluid success still prevents another callback for that position in the same pass. A prior miss on every applicable test consumes the key only in 1.21.4–1.21.8; in 1.21.9 the later segment can test the position again. The six checked release IDs are not a claim that all intervening or later releases were inspected.

## Callback eligibility and direct effects

`BlockBehaviour` supplies the `Shapes.block()` inside-collision sentinel by default in 1.21.4 and in 1.21.5–1.21.9. The four named classes do not override that method except Powder Snow:

- `WebBlock.entityInside` directly calls `makeStuckInBlock`; its weaving-effect branch changes the factors. This remains a direct movement write with the callback collector present in newer signatures.
- `SweetBerryBushBlock.entityInside` applies `makeStuckInBlock` to living entities other than foxes and bees. Its damage branch is server-only; the local client movement effect is the direct stuck-in-block call.
- `PowderSnowBlock.entityInside` makes a living entity stuck only when its current in-block state is Powder Snow; the call also applies to non-living entities. Its later collector-based extinguish operation is separate. From 1.21.5, `getEntityInsideCollisionShape` obtains the entity-context collision shape and substitutes `Shapes.block()` if it is empty; nonempty shapes require the swept test. In 1.21.4 it inherits the default sentinel.
- `HoneyBlock.entityInside` directly invokes `doSlideMovement` only when `isSlidingDown` passes (including not-on-ground, height, downward-speed and horizontal-edge checks), then invokes the inherited callback. The direct velocity rewrite is still present in the checked versions.

For Web, Berry Bush and Honey, a shape-test miss is not the reason a later segment may newly invoke the callback: the default sentinel is a successful shape gate whenever the block position is enumerated. Their possible later invocation is conditional on the earlier traversal not enumerating the position. For Powder Snow, the nonempty entity-context shape can fail the sweep, making the post-test insertion order directly relevant; an empty shape is the successful sentinel case. A successful fluid test also records the position in 1.21.9 and can therefore suppress a later block callback even when the earlier block-shape test missed.

## Comparison with the author report and limits

The audited author input was `.native-source/20261028/frontier-c/1214-1219-visited-block-admission/report.md`, SHA-256 `c43ec8e8b47edeae326d5a68f5a9d006492781c12b091a562585778477ea4261`. Its exact version inventories, admission-order transition, same-pass set lifetime and qualified retry condition are confirmed. Its callback effect descriptions are supported. The refinement above narrows which of the named blocks can produce an earlier shape miss: Powder Snow can; Web, Berry Bush and Honey use the sentinel on an enumerated candidate. The report appropriately keeps the list/segment reachability witness, swept-AABB traversal details, broader callback inventory, server replay and whole-pair freeze open. I reused the accepted 1.21.3→1.21.4 caller bundle without re-auditing it and made no runtime claim.
