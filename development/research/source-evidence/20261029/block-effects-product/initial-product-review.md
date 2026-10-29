# Independent product wiring review: segment callback lifecycle

**Verdict: ACCEPT** for product wiring at commit `2194dcfdd9b0495548aa39dd1c8b3c358572469a` (product base `9df63e8b`). The integration worktree was clean at review time. No build, tests, game launch, or runtime checks were run.

## Findings

No required revisions found.

- The read-only projection using `.native-artifacts/project-resolver.py` against the integration worktree selected the expected direct providers for `BlockCallbackAxisOrderHook`, `BlockCallbackTraversalHook`, `BlockCallbackContactHook`, `BlockCallbackBudgetHook`, `MovementCallbackRecordHook`, and `EntityBlockContactTraversalHook` at `V1_21_2`, `V1_21_4`, `V1_21_5`, `V1_21_6`, `V1_21_9`, and `CURRENT`. This uses actual literal annotations, hook implementations, enum ordering, and the resolver's closest-applicable rule. V1_21_3 is not an enum; no duplicate hook/version providers were found. At V1_21_9 and CURRENT, missing eligible providers leave native behavior.
- For selected V1_21_4, movement-record suppression leaves the current recorder empty. Exact current 26.2 `Entity.applyEffectsFromBlocks()` synthesizes `oldPosition() -> position()` when its movement list is empty (`decompiled_minecraft/26.2/unobfuscated/net/minecraft/world/entity/Entity.java:903-913`). The V1_21_4 no-op `EntityBlockContactTraversal` allows that post-travel scan, and the V1_21_4 DDA/contact hooks process its endpoint segment. The 1.21.4 caller uses the same endpoint (`1.21.4/mojmap/net/minecraft/world/entity/Entity.java`, `applyEffectsFromBlocks`).
- `BlockCallbackDda` preserves the legacy `if (attempts++ > 16) break` location: at most 17 clip bodies run, followed by the destination-box replay. This internal traversal cap remains separate from the current outer per-segment visitor budget. The V1_21_5 provider's `Integer.MAX_VALUE` limit and zero debit only disable current record-wide accounting.
- The exact 1.21.5 `Entity.move` source has a single collision-resolution call followed by the accepted-displacement gate and axis-ordered segment construction. Product capture wraps that call, preserves both `1.0E-7` comparisons, uses `Direction.axisStepOrder(resolved)`, and stores each move as an ordered batch. Drain flattens batches, clears storage, and appends a residual only when squared distance is strictly greater than `9.9999994E-11F`. Empty flattened batches retain the current caller's old/current endpoint fallback, matching source behavior.
- Explicit piston scans are depth-marked so the nested list checker uses its normal pre/post scan; the matching PISTON batch is removed after the scan. DDA/callback hooks gate historical work to an active client `LocalPlayer`; native movement recording and fallback remain for nonplayers, logical servers, no-physics/removed entities, profiles without an eligible provider, and `CURRENT`. New mixins are in the client mixin config.
- For vanilla target 26.2, the current inside-shape override search finds only `EndPortalBlock`, `LavaCauldronBlock`, `LayeredCauldronBlock`, `PowderSnowBlock`, and `TripWireBlock`. The exact 1.21.4 source audit reports only End Portal and Tripwire overrides; the other three old blocks therefore correctly receive the historical `Shapes.block()` default. No post-1.21.4 vanilla block overrides this query, so the V1_21_4 selector does not replace a modern-only vanilla inside shape.

## Bound evidence

- Product commit: `2194dcfdd9b0495548aa39dd1c8b3c358572469a`.
- Independent neutral candidate review: `.native-source/20261029/segment-lifecycle-implementation-candidate-review/review.md`, SHA-256 `c440ceb4388e85c453ced041e8559912267cb18d7c9eddbe3616e3697d8874b0`.
- Candidate-review erratum SHA-256 used for the swept-AABB supplement review: `0EA9A493DA0CF2F6DC245C7E45A0C4898359113B1BF4560F60B8A1CD33AFB864`.

This review establishes product wiring and static provider resolution only. It does not establish runtime parity.
