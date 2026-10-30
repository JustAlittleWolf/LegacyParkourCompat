# Callback adapter reconciliation

Status: source-based product design only. No product code, build, tests, game, or runtime state changed.

## Recommendation

The next narrow adapter is the **per-segment traversal hook**, paired with an explicit way to suppress 26.2's metadata-driven axis split for the historical 1.21.2/1.21.3 callback path. The retained `unintegrated-traversal-candidate.patch` has the right endpoint-nudge candidate order, but its list callback is the wrong integration unit: it clears the supplied movement list and returns positions without 26.2 visitor stage labels.

The product already has the right downstream seam:

- `BlockCallbackTraversalHook.visitSegment(Vec3 from, Vec3 to, AABB target, BlockGetter.BlockStepVisitor visitor)` returns `Optional<Boolean>`.
- `EntityBlockCallbackSegmentMixin` calls it from `Entity.checkInsideBlocks(Vec3, Vec3, StepBasedCollector, LongSet, int): int`, keeping contact handling, `StepBasedCollector`, visited-position ownership, crash wrapping, and native fallback in the surrounding method.
- `EntityCallbackTraversalMixin` redirects the `Entity$Movement.axisDependentOriginalMovement(): Optional` read inside `Entity.checkInsideBlocks(List, StepBasedCollector): void`. Returning `Optional.empty()` for the historical local-player scan would take the native single-segment branch (`movement.from()` to `movement.to()`) and leave the finalized movement list intact. The existing `BlockCallbackAxisOrderHook` only changes the vector used to order axes; a vector alone cannot disable splitting.

The adapter should therefore consist of two independently resolved decisions, or one suitably separated pair of operations: (1) whether this movement record is split by stored axis metadata, and (2) how one resulting segment enumerates positions and maps them to collector stages. Do not consume or clear `finalMovementsThisTick`; the 26.2 `applyEffectsFromBlocksForLastMovements()` path reuses it.

## Proposed contracts and exact sites

### A. Historical segment shape

Add a hook decision at the existing `EntityCallbackTraversalMixin` redirect site, with the semantic contract “return the record's axis metadata for native splitting, or empty to scan its from-to segment once.” The exact target is:

```text
net.minecraft.world.entity.Entity.checkInsideBlocks(
  java.util.List,
  net.minecraft.world.entity.InsideBlockEffectApplier$StepBasedCollector
): void
```

The intercepted invocation is:

```text
net.minecraft.world.entity.Entity$Movement.axisDependentOriginalMovement(): java.util.Optional
```

This uses the same ordered record endpoints and native 26.2 segment budget. It does not synthesize movement records or mutate the retained list. It restores the old call shape shown by 1.21.2/1.21.3 `Entity.checkInsideBlocks(List, Set)`, which calls `BlockGetter.boxTraverseBlocks(from, to, sharedFinalBox)` once per record rather than splitting a record by request-axis metadata.

### B. Historical candidate traversal with collector labels

Reuse the existing independent operation:

```text
BlockCallbackTraversalHook.visitSegment(
  Vec3 from, Vec3 to, AABB target,
  BlockGetter.BlockStepVisitor visitor
): Optional<Boolean>
```

Its current injection point is `EntityBlockCallbackSegmentMixin` at the head of:

```text
Entity.checkInsideBlocks(
  Vec3, Vec3,
  InsideBlockEffectApplier$StepBasedCollector,
  it.unimi.dsi.fastutil.longs.LongSet, int
): int
```

The 1.21.2/1.21.3 adapter should emit the old insertion-ordered cells (DDA body first, then unseen final-box cells) through the visitor without touching the movement list, shared visited set, contact checks, or collector. Each visitor call must carry a deterministic 26.2 collector label. The retained patch cannot be integrated unchanged because its `candidateCells` result has no labels and its `scanMovements` clears its argument.

The 26.2 `BlockGetter.forEachBlockIntersectedBetween` establishes its label convention: initial swept-box cells use `0`, cells found at each clipped DDA crossing use the crossing iteration, and unseen final-box cells use `iterations + 1`. The old 1.21.2 `boxTraverseBlocks` has no label output. A source-faithful adapter can preserve these 26.2 stage semantics by assigning a label as it inserts each first-seen old candidate: DDA cells get the old DDA crossing counter and final-box-only cells get the terminal stage after DDA. This keeps the existing callback visitor and collector in control. It should not invent labels from final callback count or restart stages for each cell.

### Resolvable applicability and version boundary

For both decisions, historical behavior is applicable only when the runtime entity is `LocalPlayer`, `level().isClientSide()` is true, the entity is not removed, and `noPhysics` is false. These gates follow the accepted 1.21.x caller and the existing block callback hooks; they leave logical servers, remote client entities, and native fallback unchanged.

The source-supported version point is `@MovementChange(emulates = ParkourVersion.V1_21_2)` for 1.21.2 and 1.21.3: the inspected Entity and BlockGetter files are byte-identical across those releases. Existing 1.21.4 traversal change remains the next applicable provider. The audits do not establish an intermediate patch cutover, so do not claim one.

## Accepted source basis

- `callback-order-audit/report.md` accepts the 1.21.2/1.21.3 single shared final-box scan and its record-order/callback sequence only for equal movement records and candidate cells. It explicitly leaves candidate traversal and partial-shape geometry outside that audit. Its exact source identity is the 1.21.2 Mojmap `BlockGetter.java` SHA-256 `5d6fde6fef7928603ff97f4a4015efbb48442f9e583f1d0cfe368c5168e01c39`; 1.21.2 `Entity.java` is `a93719c302a0381a972af75ea360465e2e3551708dd07c34d4d40b7e5173c2b9`. It also verifies that the selected 1.21.3 files match 1.21.2 byte-for-byte.
- `movement-records-audit/audit.md` accepts that 26.2 records carry request-axis metadata, that `checkInsideBlocks(List, StepBasedCollector)` splits only when metadata is present and the from-to delta is nonzero, and that the final movement list is reused by `applyEffectsFromBlocksForLastMovements()`. Its exact 26.2 source root is `26.2/unobfuscated`; audited `Entity.java` SHA-256 is `A328F5333A1B27AADDE4422B23159A323AE219CCD26F11207C1A5461366F27D1`.
- The exact 26.2 `BlockGetter.forEachBlockIntersectedBetween` and `addCollisionsAlongTravel` bodies are cited by the prior traversal reconciliation as `BlockGetter.java` method hashes `4007ab952e82ea2ef25adfea2a24acd20e2a9f9e20d6a3ec6ba95e617c2227fd` and `1b89d23587294f7f63572049ad2de055a792e829a1e941d9db7b07daf7232b12`. The existing collector's `advanceStep(int)` flushes queued effects whenever the step label changes; source is `26.2/unobfuscated/net/minecraft/world/entity/InsideBlockEffectApplier.java`.
- Exact 1.21.2 `Entity.checkInsideBlocks(List, Set)` (Mojmap `Entity.java:1038–1078`) loops each record's `BlockGetter.boxTraverseBlocks(from,to,finalBox)` result and invokes `entityInside` directly. Exact 1.21.2 `BlockGetter.boxTraverseBlocks` (`BlockGetter.java:172–248`) nudges both DDA endpoints by normalized delta scaled by `1.0E-7`, preserves traversal insertion order in `ObjectLinkedOpenHashSet`, then appends unseen `BlockPos.betweenClosed(target)` cells. These are source-operation details, not runtime parity evidence.

## Remaining closure before implementation acceptance

The 26.2 `StepBasedCollector.advanceStep` flushes queued effects on label changes, while the 1.21.2 callback directly invokes block effects. Therefore the old DDA's own loop counter is a proposed stable label mapping, not an accepted proof that every block callback effect has the historical timing under 26.2 collection. Before implementing this adapter, close the smallest relevant source slice: compare 1.21.2 direct callback effect execution with 26.2 `StepBasedCollector` flush/application semantics and the in-scope player block callbacks that enqueue effects. If the closure shows no source-faithful label mapping, the minimal additional seam is to preserve 26.2 visitor labels as native only where justified and explicitly represent the old synchronous callback timing as its own operation; do not silently collapse all old cells to label `0`.

The separate single-segment decision is independently source-backed by the accepted record report and does not depend on the collector-effect closure. Avoid claiming that the collision resolution order (Y, then horizontal absolute comparison) creates axis-split callback records: the audit explicitly rejects that inference.

## Disposition of retained list candidate

Do not integrate `scanMovements` or `candidateCells` directly. They operate at the wrong layer, discard list state on normal completion, omit the 26.2 visitor label, and do not suppress the metadata-driven axis split. Extract only the source-reviewed ordered DDA behavior into `visitSegment` after the collector label closure; add a narrowly scoped split-decision hook (or extend the existing hook with an explicit `Optional`/split decision) so old profiles scan each record's from-to segment once.
