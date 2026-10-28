# 1.21.4–1.21.9 visited-block admission in movement callbacks

## Scope and conclusion

This is a bounded source-only comparison of the visited-block admission gate inside `Entity.checkInsideBlocks` for exact Mojmap releases 1.21.4, 1.21.5, 1.21.6, 1.21.7, 1.21.8 and 1.21.9. The exact source identities and whole-tree manifest checks are recorded below. This report concerns a position shared by multiple movement segments handled in one `applyEffectsFromBlocks` pass. It reuses the separately accepted 1.21.3→1.21.4 caller bundle and does not re-audit that caller or claim pair-wide coverage.

**Earliest observed stable release with the post-test admission gate: 1.21.9.** In 1.21.4–1.21.8, a non-air block position is inserted into `visitedBlocks` before its inside-shape test and, from 1.21.5 onward, its fluid test. In 1.21.9, the position is inserted only when the block-shape test or fluid test succeeds. The set is shared across all segments in the pass and cleared after the list finishes. Thus, in 1.21.9 a candidate that misses every test present on one segment remains eligible for a later segment; a candidate that succeeds either test is visited and cannot dispatch again during that pass. In 1.21.4–1.21.8, even a miss on every applicable test consumes the position and suppresses any later segment's callback for it.

## Exact source identity and integrity

All six trees are Mojmap. The manifest/provenance files below were read from the exact release directories; every listed Java source was rehashed independently. Manifest count, actual Java file count and matched count are equal; there are zero missing, unlisted, or mismatching source files.

| Release | Identity file | Identity-file SHA-256 | Listed = actual = matched |
| --- | --- | --- | ---: |
| 1.21.4 | release parent `mojmap.sources.sha256` | `f90b61197928632e061ea877955a19055c92ae6f357c2daf1bc646172c6f51f0` | 5,744 |
| 1.21.5 | release parent `mojmap.sources.sha256` | `365cc2d22446ceba0e36f46aa1c95ce4cc31514dbd657505680fb660f3adefd9` | 5,921 |
| 1.21.6 | `mojmap/.decompile-provenance.json` | `56b036c61094d1d936882bdf5b2b59044512189cb33adbb075880c727da9b19e` | 6,103 |
| 1.21.7 | `mojmap/.decompile-provenance.json` | `45bf296d921b2239fd0aedb2684e2c1cb9b7d7532b4106b8a2fd9b09309ca82d` | 6,104 |
| 1.21.8 | release parent `mojmap.sources.sha256` | `8ffb76cea647a2ba4fe58e000678f751bea2c40ea6358bae492e962ed1d9d008` | 6,105 |
| 1.21.9 | `mojmap/.decompile-provenance.json` | `37d3049478585a43ffcace14fc8f2ee814b35ceedd8c9bad8b60adaed9b72c4f` | 6,386 |

The release parents for 1.21.4, 1.21.5 and 1.21.8 also have matching `mojmap.ready.json`, `mojmap.provenance.json` and source-manifest records. 1.21.6, 1.21.7 and 1.21.9 bind source hashes through the embedded provenance map; no release-parent readiness sidecar was present for those three. The selected source-file hashes match each applicable release's source map. Full selected hashes are in `source-hashes.tsv`.

## Admission order by release

- **1.21.4:** `Entity.checkInsideBlocks(List<Movement>, Set<BlockState>)` first checks non-air and `visitedBlocks.add(pos)` (`Entity.java:1059–1065`). Only after successful admission does it fetch `getEntityInsideCollisionShape` and accept either the `Shapes.block()` sentinel or a swept-box intersection (`1067–1069`). A miss continues the traversal but leaves the position visited. There is no fluid callback test in this method. The set is cleared once after processing the entire movement list (`1088`).
- **1.21.5:** block candidates come from `BlockGetter.forEachBlockIntersectedBetween` (`Entity.java:1087`). After non-air, the set is still updated before either test (`1090–1093`). The block callback runs only if the shape gate succeeds (`1094–1107`); fluid is tested independently afterward (`1109–1113`). The earlier set insertion remains even when both tests fail. The set clears after every segment in the list has been visited (`1120`).
- **1.21.6, 1.21.7 and 1.21.8:** the collector splits movement records marked axis-independent into axis-ordered subsegments, but the per-position method retains the 1.21.5 admission order: non-air, `visited.add`, shape test/callback, fluid test/callback (`Entity.java:1138–1179` in .6–.8). The shared set is cleared only after all movement records/subsegments (`1134`). The relevant `Entity.java`, `BlockGetter.java`, `AABB.java`, and four callback block files have identical hashes in .6/.7/.8, so no intermediate admission-order boundary appears there.
- **1.21.9:** each non-air candidate computes both `shapeHit` and `fluidHit` before inserting the position. The gate is `(shapeHit || fluidHit) && visited.add(pos)` (`Entity.java:1179–1190`). The block callback follows only on `shapeHit`; the fluid callback follows only on `fluidHit` (`1191–1209`). If both tests miss, the position is not added and a later segment can retry it. If either succeeds, it is added before callback dispatch and suppresses further segment callbacks for that position during this pass. The shared set clears after the movement list (`1160`).

The .5–.8 versions differ in movement-record representation/expansion and callback collector plumbing, but those changes do not move visited admission after the tests. The .9 source is the first checked version that does. The independent first-boundary conclusion is limited to these six releases; releases between or after the listed IDs were not inspected here.

## Local-player reachability and callback effects

`LivingEntity` calls `applyEffectsFromBlocks()` in its tick path in all six versions (1.21.4 `LivingEntity.java:2786`, .5 `2770`, .6–.8 `2822`, .9 `2852`). The inherited `Entity.isAffectedByBlocks()` guard is `!isRemoved() && !noPhysics` in each examined release. The previously accepted 1.21.3→1.21.4 caller bundle establishes the local-player entry to this callback phase; this report checks only how candidate positions are admitted in the subsequent source versions. It does not establish that every runtime tick contains two segments through the same position.

The requested era-existing callback implementations were inspected on both sides of the transition; their client movement writes remain reachable when their own gates are satisfied:

- `WebBlock.entityInside` calls `makeStuckInBlock` with its ordinary factors, or its weaving-effect factors. This direct velocity scaling is not routed through the deferred effect collector.
- `SweetBerryBushBlock.entityInside` applies `makeStuckInBlock` to living entities other than foxes and bees. The berry damage branch is server-gated and excluded from the client movement conclusion.
- `PowderSnowBlock.entityInside` applies `makeStuckInBlock` to non-living entities or living entities whose current in-block state is powder snow; freeze/extinguish effects are separate collector effects. From 1.21.5, its `getEntityInsideCollisionShape` returns the collision shape, but substitutes the `Shapes.block()` sentinel when that shape is empty. The collector treats that sentinel as a successful block-shape gate without a swept-shape call, so an enumerated powder-snow candidate is already a successful block test and consumes its visited position. A later retry is relevant when an earlier segment did not enumerate that position, or otherwise did not reach either success gate.
- `HoneyBlock.entityInside` invokes the direct `doSlideMovement` only when its falling/height/vertical-speed/edge predicates accept the player; that branch rewrites velocity and resets fall distance. It then calls the inherited inside behavior. A later segment may newly qualify the honey candidate only if an earlier segment left its position unvisited; the honey-local movement predicates still apply.

Callback signatures gain `InsideBlockEffectApplier` in 1.21.5. The direct `makeStuckInBlock` calls and Honey slide movement remain direct callback effects in the inspected releases; the visited-set ordering determines whether those callback bodies can be reached for a later segment after an earlier miss. A prior successful shape or fluid test prevents repeated callbacks for that position even in 1.21.9.

## Applicability limits and dependencies

This is a source-level admission-order result, not a predicted trajectory. Later-segment retries require: a locally simulated, non-removed, physics-enabled entity; a movement list containing more than one segment in one application pass; a non-air block position considered on an earlier segment; both its shape and fluid tests to miss then; and at least one relevant test to succeed on a later segment. The callback-specific gates above must also pass for a movement write. The `visitedBlocks` code proves eligibility after a miss; no runtime trace was performed to produce a witness trajectory.

Candidate iteration, exact swept-AABB/voxel edge rules, all block tags/resources, every callback body, and server replay are outside this closure except the explicitly named callbacks and dependencies. The requested caller bundle for 1.21.3→1.21.4 is reused and not re-audited. This result does not freeze any whole release pair or assert full movement parity.
