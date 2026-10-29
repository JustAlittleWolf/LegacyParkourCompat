# Product reconciliation: partial-sweep traversal candidate

## Disposition

**No product code integration.** The accepted helper is faithful to the bounded 1.21.2/1.21.3 source operation, but the current 26.2 caller has no seam that can consume its list traversal and candidate-cell order while retaining the native callback/effect stages. Wiring it at the existing list or per-segment mixin would either clear retained movement records, bypass/rebuild native contact processing, or run both scans. This is an integration disposition only; it does not revise source acceptance or claim runtime parity.

## Bound identities

- Product checkout: `.native-integrations/partial-sweep-traversal-1212-product`, branch `fix/partial-sweep-traversal-1212-product-20261029`, clean at `27c71b7629e41935f1976b9d59dbf78fe711e796`.
- Candidate: `.native-candidates/partial-sweep-traversal-1212-20261029`, commit `0d28943fe9f07a54eb5eef1fb59c1eb1bf35d5cc`; accepted helper review SHA-256 `fedd08af3e04198bc1aa5b4347ddaa2d5da692d57f0cb13ef1d592e975450a36a`.
- Accepted .2/.3/.4 source report SHA-256 `554fca52d724e8133ec090f8d1b28f3daea443c926682df21b62e133d0816ab4`; independent audit SHA-256 `601e1ec94848d2651ceecb8249f08fe3b138c476e2103c5a7fc5410ca72aa34e`.

## Current 26.2 caller evidence

Canonical exact source is `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\26.2\unobfuscated`.

| Path / method | Lines | SHA-256 |
| --- | --- | --- |
| `net/minecraft/world/entity/Entity.java` file | — | `7afb9c1294893ffe73e3b1acffcad41c648f15de8378bff3dffaff869bb811d5` |
| `Entity.applyEffectsFromBlocks()` | 903–914 | `f7c63facf56015e5f47e15fb69edcb34ccf4448fbff2849670fd1287ce9b1647` |
| `Entity.applyEffectsFromBlocks(List)` | 949–975 | `689b2a9fbe33ee401b46338f659997a2ed611843f99ba6e4ff0b608d8dc8262d` |
| `Entity.checkInsideBlocks(List, StepBasedCollector)` | 1278–1306 | `969eff23b2b551c53f735bc596ad075fd81e3cc81337e9c7512ae32cf57c256d` |
| `Entity.checkInsideBlocks(Vec3, Vec3, collector, visited, budget)` | 1308–1380 | `dca2f3ff321b6a7ec339f158d42051f7f634d05738c01b78f327b2436035e94d` |
| `net/minecraft/world/level/BlockGetter.java` file | — | `623a44206db93d8fb2eefc9353345689cc35e0c1742b5d61789eab2b098b687d` |
| `BlockGetter.forEachBlockIntersectedBetween` | 175–209 | `4007ab952e82ea2ef25adfea2a24acd20e2a9f9e20d6a3ec6ba95e617c2227fd` |
| `BlockGetter.addCollisionsAlongTravel` | 211–288 | `1b89d23587294f7f63572049ad2de055a792e829a1e941d9db7b07daf7232b12` |

Method hashes above are SHA-256 of the inclusive line ranges, with line endings normalized to LF and no trailing newline. Product integration seams inspected: `EntityCallbackBatchMixin.java` SHA-256 `51a79d77a1dae2a0f2c2aa7e337af217ff0e35f2e14a93b0a98a7615cb1b899a`; `EntityBlockCallbackSegmentMixin.java` SHA-256 `4adaaf119a7bd7adc5d4e6e595dc013a377a3d8e443f8bf83ccf4443d4babd83`; `BlockCallbackTraversalHook.java` SHA-256 `5311db4a72ee2f48cce4bac0c5b4f52e0bedbf5c8a4c78b6fdd2244acd2b5c7f`.

## Lifecycle and mismatch

In 26.2, `applyEffectsFromBlocks()` copies the movement deque into `finalMovementsThisTick`, clears the deque, and may append a final endpoint segment. It passes `finalMovementsThisTick` to the list overload; `applyEffectsFromBlocksForLastMovements()` later reuses that retained list. The list callback then processes each movement, possibly splitting it by `axisStepOrder`, applying a per-movement 16-iteration budget and a stationary endpoint fallback. It clears `visitedBlocks`, not the movement list.

The per-segment method creates a fresh `makeBoundingBox(to).deflate(1.0E-5F)` and passes it to `BlockGetter.forEachBlockIntersectedBetween`. Its visitor carries `(BlockPos, iteration)`. That iteration feeds `StepBasedCollector.advanceStep`; the method also stages shape and fluid predicates/callbacks, visited-position dedup, debug intersection state, alive/budget early stops, and callback crash reporting. `EntityCallbackBatchMixin` bypasses this native path only for the later axis-batch capability; the existing `BlockCallbackTraversalHook` handles a segment iterator but not a list-wide shared query box or per-cell step metadata.

The candidate `scanMovements` derives one final-box query, invokes callbacks in list order, and clears the supplied list after normal completion. Its `candidateCells` returns ordered `BlockPos` values only, with no `BlockStepVisitor` iteration index. Passing `finalMovementsThisTick` would erase data needed by the later replay. Passing a copy avoids that mutation but leaves the adapter needing to preserve axis splitting, native stage indices, budget truncation/fallback, contact/fluid sequencing and dedup. Replacing that logic would expand the change beyond the accepted helper; retaining it while independently invoking the candidate scan risks duplicate callbacks or changed order.

## Missing adapter capability

Safe integration requires a source-specific list-level adapter that can (a) share the historical final query box across the original movement records without mutating the retained replay list, and (b) deliver the legacy candidate order together with correct per-cell native stage/iteration metadata through the existing callback visitor, preserving native axis-segment/budget/fallback and contact/effect staging. The accepted candidate supplies neither visitor-stage metadata nor a 26.2 adapter for those lifecycle constraints. Until that capability is independently designed and source-reviewed, retain the no-code disposition.

No Java source was changed. No build, tests, or runtime were run. `main` was already an ancestor of product HEAD; the no-op merge attempt failed only because Git could not write shared `ORIG_HEAD` metadata outside the product worktree.
