# Independent audit: DDA labels and inside-effect collection

## Verdict and reviewed input

Reviewed `.native-source/20261030/dda-collector-flush/report.md`, SHA-256 `bb3d871fa1d65051faa3a9a36b1c3839f07406bf18384c60aeb52f8d274d261e`. The input hash matches. I independently checked the cited canonical decompiled sources under `D:/Javastuff/LegacyParkourCompat/decompiled_minecraft/`; no sources were changed.

**Verdict: accept the narrow callback scheduling/lifecycle finding for 1.21.2 and 1.21.3 versus 26.2.** The source establishes an independently actionable semantic operation: historical post-travel block callbacks are synchronous, position-deduplicated before the old shape eligibility check, run `BlockState.entityInside` then `onInsideBlock`, and have no swept fluid callback or DDA-stage collector. This is independent of complete source-pair freeze, but it does not itself authorize a complete old-profile implementation that dispatches modern block callbacks unchanged.

The report's central contrast is accurate. Two qualifications constrain its implementable boundary:

1. The source proves callback *dispatch order and collector behavior*, not universal equivalence of each modern callback body to its historical callback. Concrete examples in the report demonstrate different write timing. Any implementation reusing modern callbacks must separately establish which historical callback semantics are preserved or provide a source-grounded old behavior for each applicable callback.
2. The legacy operation's post-sweep fire/lava summary and `stepOn` are outside the inner callback scheduler. The accepted operation must retain their existing historical boundary/order; this audit does not independently accept or implement those behaviors.

## Evidence checked

- In 1.21.2 and 1.21.3, `Entity.java` hashes match each other (`a93719c302a0381a972af75ea360465e2e3551708dd07c34d4d40b7e5173c2b9`). `BlockGetter.java` hashes also match (`5d6fde6fef7928603ff97f4a4015efbb48442f9e583f1d0cfe368c5168e01c39`). This independently confirms the report's endpoint byte identity for these core files.
- Old `Entity.applyEffectsFromBlocks(Vec3, Vec3)` appends the movement, calls `checkInsideBlocks` immediately, summarizes `blocksInside`, then clears movement and block-state collections (`Entity.java:740–767`). Old `checkInsideBlocks` checks alive/non-air, adds the position to `visitedBlocks` before the partial-shape predicate, synchronously calls state `entityInside` then `onInsideBlock`, and adds the state to the summary after both return (`Entity.java:1038–1078`). Its catch wraps callback exceptions in a `ReportedException`.
- Old `BlockGetter.boxTraverseBlocks` yields positions, and the old entity loop has no callback iteration parameter or `FluidState.entityInside` invocation (`BlockGetter.java:172–247`; `Entity.java:1047–1071`). This substantiates no DDA label and no swept-fluid callback in this path.
- 26.2 `BlockGetter.forEachBlockIntersectedBetween` supplies integer iteration labels for the initial, crossing-slab and target-box phases (`BlockGetter.java:175–284`). `Entity.checkInsideBlocks` passes those labels to the collector only before a qualifying block or fluid callback; it tests both block and fluid eligibility, then inserts a visited position only after either succeeds (`Entity.java:1308–1377`).
- 26.2 callback order for a qualifying cell is block state callback, `onInsideBlock`, then fluid callback if fluid eligibility also passes. Both use the same cell's iteration label (`Entity.java:1342–1365`). Thus a block and fluid callback in the same cell/stage do not force a step transition between them.
- `StepBasedCollector.advanceStep` flushes only when the numeric label changes. `flushStep` appends before-consumers, one effect per enum type if registered, then after-consumers in enum order; it does not execute them during traversal. `applyAndClear` flushes the last group after traversal and executes the accumulated effects in order, stopping if the entity dies (`InsideBlockEffectApplier.java:32–95`; `InsideBlockEffectType.java:6–24`). The enum order is `FREEZE`, `CLEAR_FREEZE`, `FIRE_IGNITE`, `LAVA_IGNITE`, `EXTINGUISH`. Equal labels at adjacent movement segments can remain grouped because there is no segment-close operation.

## Concrete state-write check

The report accurately distinguishes queued registrations from direct callback writes:

- In 26.2, fire registers `CLEAR_FREEZE`, `FIRE_IGNITE`, and a per-callback post-ignite hurt consumer (`BaseFireBlock.java:131–141`). The enum effects mutate fire/freeze state later; each hurt consumer is retained, while `FIRE_IGNITE` is coalesced once per step (`InsideBlockEffectType.java:6–24`, collector above). This differs from 1.21.2/1.21.3 fire's synchronous ignition/damage body (`BaseFireBlock.java:130–147`).
- 26.2 powder snow calls `makeStuckInBlock` inline, but defers eligible block destruction with `runBefore(EXTINGUISH)` and defers freeze and extinguish effects (`PowderSnowBlock.java:62–103`). Those freeze fields therefore are not written at the visitation point. The listed old powder snow callback performs its historical effects inline (`1.21.2/mojmap/.../PowderSnowBlock.java:60–88`).
- 26.2 layered cauldron's fire-driven fill-level change is captured in `runBefore(EXTINGUISH)` and executes later; its callback also registers extinguishing (`LayeredCauldronBlock.java:85–117`).
- The report's claim that some callbacks remain inline is supported by the inspected Honey, Web and BubbleColumn bodies: their player responses include direct velocity/fall-distance or stuck-state writes in the callback, and precise bubble handling directly calls the entity response (`HoneyBlock.java:64–137`; `WebBlock.java:27–41`; `BubbleColumnBlock.java:53–70`). These inline cases do not make collector effects immediate.
- 26.2 lava fluid and lava cauldron defer ignite/after-hurt registrations; water fluid registers extinguish (`LavaFluid.java:119–123`; `LavaCauldronBlock.java:47–58`; `WaterFluid.java:87–89`). The old block sweep has no matching fluid callback; this is not evidence that all fluid movement behavior is absent, only that the DDA sweep does not invoke it.

## Accepted scope and open prerequisites

**Smallest independently implementable semantics:** a historical post-travel callback dispatch operation with no stage labels or collector: preserve its caller-supplied historical candidate/visit sequence; mark each non-air position before the historical block-shape gate; at each eligible block synchronously call historical `entityInside`, then `onInsideBlock`; add the state to the old summary only after both return; do not dispatch swept-fluid callbacks or collector effects. Preserve callback exception wrapping and stop/death gates from the historical caller. These semantics can be separately implemented or represented as a callback policy without waiting for pair-wide coverage freeze.

This is an acceptance boundary, not a source-faithful recipe to call arbitrary 26.2 state callbacks with a no-op/inline applier. The inspected modern callback APIs encode behavior in `InsideBlockEffectApplier`; substituting a no-op drops effects, and executing registrations inline changes type order and deduplication. Source prerequisites for a complete compatibility implementation remain:

- The independently reviewed historical traversal, movement-record, query/shape, iteration-cutoff and finalizer semantics must be supplied by their own accepted evidence; this audit intentionally inherits none of those claims beyond the dispatch boundary described above.
- For each applicable block callback whose modern implementation is reused, inspect the exact historical and modern bodies and establish equivalent player/world writes, gates and operation order, or supply the historical callback behavior. Fire, powder snow, cauldron, lava/water fluid, and any other callbacks that use typed registrations cannot be assumed equivalent merely by bypassing the collector.
- Preserve the old `stepOn`, post-sweep fire/lava summary, collection clearing, and fire tick/sound ordering at the exact historical boundary; they are not part of the collector-only operation.
- Applicability to intermediate versions between 1.21.3 and 26.2 and any patch introduction boundary remain unknown. The endpoint comparison does not establish either.
- No tick-by-tick trajectory, complete block callback inventory, or runtime parity is accepted here.

## Immutable audit identity

This audit is bound to the reviewed report SHA-256 above and canonical source endpoints 1.21.2, 1.21.3 and 26.2. It is an independent source review only. It does not alter central coverage, establish pair freeze, authorize runtime verification, or claim implementation status.
