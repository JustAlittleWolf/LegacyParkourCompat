# Applicability map: 1.21.4–1.21.5 ordered block contacts

## Verdict

**COVERED** for the accepted bounded case: an ordinary, dry, non-riding, non-flying local-player SELF movement under the selected `V1_21_4` profile, with the same collision-resolved displacement and inside-shapes as the source witness. The product routes this case through one direct old-position-to-final-position contact sweep. Under the `V1_21_5` profile, it uses ordered axis segments. The product registration and dispatch already match the accepted 1.21.4→1.21.5 distinction; this review found no unresolved route for this slice.

This is a source/product-wiring disposition only. It does not establish full-pair source coverage or runtime parity.

## Product registration and route

Reviewed the immutable product tree at `aae9c86464638cb5ed7faa535c92569b89484906` (`Bind the exact Brewing Stand AutoJump source witness`). The shared worktree was dirty; product conclusions below come from `git show` at that commit, not those worktree changes.

`ChangeResolver` picks the closest `emulates` value at or later than the selected profile, independently for each hook. For `V1_21_4`, the relevant registrations are:

- `MovementCallbackRecord` at `change/v1_21_4`: suppresses movement records only for a live, physics-enabled `LocalPlayer` on the logical client. Its inherited `captureAxisSegments` is false.
- `BlockCallbackTraversal` at `change/v1_21_4`: uses the shared source-audited segment DDA for that same local-client domain.
- `BlockCallbackContact` at `change/v1_21_4`: performs the 1.21.4 synchronous block contact operation: admission/dedup, historical inside-shape gate, direct `entityInside`, then `onInsideBlock`.
- `NativeEntityBlockContactTraversal` at `change/v1_21_4` is intentionally a no-op and does not suppress post-travel traversal; this leaves the single post-travel pass in place.

For this profile there is no applicable `HistoricalBlockCallbackSweepHook`: the historical list sweep is annotated `V1_21_2`, and its native fence is `V1_21`; both are older than `V1_21_4` and therefore are not selected. The historical sweep mixin consequently calls the native list checker.

The selected `V1_21_4` record hook prevents ordinary local-player `move` calls from adding entries to the native `movementThisTick` deque. In the current native source, `Entity.applyEffectsFromBlocks()` copies that deque to `finalMovementsThisTick`, clears the deque, and, when the list is empty, inserts exactly one `Movement(oldPosition(), position())`. The ordinary SELF path therefore reaches the list checker as the direct old-to-final segment. `EntityBlockCallbackSegmentMixin` dispatches that segment to the registered 1.21.4 DDA and contact hooks. This is the same route change the accepted source comparison identifies.

Under `V1_21_5`, the resolved record hook instead suppresses the native record and enables axis-segment capture. `EntityMixin` stores collision-resolved axis legs in ordered per-move batches; `EntityCallbackBatchMixin` drains the sidecar batches in order and dispatches each segment. Under `V1_21_6` and later, the record hook keeps native records, and the native checker expands the native axis-independent record using the selected axis-order hook. For the accepted ordinary bound below the 1.21.6 100-record cap, that matches the 1.21.5 axis-leg route.

## State, reset, gates, and fallback

The 1.21.4 path does not use the custom axis-batch sidecar. The 1.21.5 sidecar is an ordered `List<Batch>` of immutable segment lists; taking batches snapshots and clears that list. The native movement deque is drained and cleared by `applyEffectsFromBlocks()` before its empty-list fallback is constructed. No UUID lookup, UUID-keyed state, or UUID-specific variant participates in this route. The ordinary callback dedup structure is the existing per-entity visited-block `LongSet`, cleared after the scan.

Historical hooks are reached only through `MovementRuntime.find` for an eligible local-client movement profile. The record hook itself additionally checks `LocalPlayer`, client-side level, not removed, and `!noPhysics`. The 1.21.4 traversal provider's `appliesTo` gate has the same constraints. Other entities and logical-server movement retain native record and callback processing. If a segment provider does not apply or returns an empty result, the mixin retains the native traversal; the 1.21.4 DDA path returns a handled result for its supported segment. No PISTON route was reviewed or used as support for this disposition.

The direct callbacks leave Honey velocity/fall-reset writes and Web/Berry stuck-multiplier writes synchronous in contact visitation order. This covers the accepted conditional consequences and the concrete Web witness route; it does not claim separate concrete Honey or Berry trajectories.

## Evidence identities and limits

- Product commit: `aae9c86464638cb5ed7faa535c92569b89484906`.
- Accepted author comparison SHA-256: `b08e34996a5d7ca952ca620999ffaf149caa7a96a05ca66121dc8fe4d1f19fc5`.
- Accepted claim SHA-256: `e659135b08a409a498b74a91881e6150ae69fc7b90483698254d10059939f66c`.
- Independent frozen audit SHA-256: `b13612eecc2b79aa8bf33577600727a7ddfaa7f8fdbc6d9b352d413efd4af965`.
- Independent verdict SHA-256: `694a8fa3247c8e0f83957df73502315cbe1f0968062ac4202a258cfb449cd464`.
- Source scope: ordinary dry, non-riding, non-flying local-player path; exact Mojmap 1.21.4→1.21.5 route difference and bounded 1.21.5→1.21.6 ordinary equivalence. Piston handling, exact introducing patch, other locomotion branches, full-pair inventory, and runtime parity are excluded.

## Terminal result

**COVERED.** Existing product registration and dispatch provide the direct 1.21.4 old-to-final contact route and the 1.21.5+ axis-ordered route for this bounded operation. No code change is indicated by this product-map review.
