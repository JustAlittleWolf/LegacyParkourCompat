# Callback adapter source-fidelity review

Reviewed immutable product revision: `8565e707866b1d2dc8e434691e250a52f3355155` (diff `23ca396e..HEAD`, including `08fc3e54`, `bad84f90`, `c98e93ef`, `8565e707`).

**Verdict: source-fidelity issues found.** The list scan/admission path and its old full-shape sentinels, non-air dedup timing, callback order, swept-fluid omission, powder-snow response, and post-sweep fire summary match the bounded accepted 1.21.2/1.21.3 evidence reviewed. The following callback-body exceptions remain actionable.

## Findings

### 1. Bubble Column still uses the modern above-column predicate

In `src/client/java/me/wolfii/legacyparkourcompat/change/v1_21_2/EntityInsideBlocksHistoricalSweep.java:130-154`, the adapter forces `precise=true` for `BubbleColumnBlock`, then calls modern `BlockState.entityInside`. This bypasses the modern precision gate, but not the changed callback predicate.

Exact source comparison:

- 1.21.2 Mojmap `net/minecraft/world/level/block/BubbleColumnBlock.java:50-75`: callback always responds; it uses `level.getBlockState(pos.above()).isAir()` to select `onAboveBubbleCol` versus `onInsideBubbleColumn`.
- 26.2 unobfuscated `net/minecraft/world/level/block/BubbleColumnBlock.java:53-69`: with `isPrecise`, it instead uses `stateAbove.getCollisionShape(level,pos).isEmpty() && stateAbove.getFluidState().isEmpty()` to select `onAboveBubbleColumn` versus `onInsideBubbleColumn`.

Therefore a non-air block above the column that has empty collision and empty fluid takes the old inside-column response but modern above-column response. That changes the entity's vertical movement response. The accepted callback inventory explicitly requires preserving the old air predicate. Add a historical Bubble Column response that tests `isAir()` and selects the old response helper; forcing precision alone is insufficient.

### 2. Lava cauldron suppresses the old in-scope fire-timer response

In `src/client/java/me/wolfii/legacyparkourcompat/change/v1_21_2/callback/SynchronousBlockResponses.java:46-49`, `LavaCauldronBlock` returns `true` without running any response. This suppresses modern typed effects, but also drops the old callback's fire-timer mutation.

Exact source comparison:

- 1.21.2 Mojmap `LavaCauldronBlock.java:34-37` calls `lavaHurt()` when `isEntityInsideContent(state,pos,entity)` passes. `AbstractCauldronBlock.java:52-53` defines that gate as `entity.getY() < pos.y + getContentHeight(state) && entity.getBoundingBox().maxY > pos.y + 0.25`.
- 1.21.2 Mojmap `Entity.java:526-538`: `lavaHurt()` calls `igniteForSeconds(15.0F)` for non-fire-immune entities before the server-only damage and sound branch.
- 26.2 unobfuscated `LavaCauldronBlock.java:47-58` only registers deferred `CLEAR_FREEZE`, `LAVA_IGNITE` and post-ignite `lavaHurt`; it does not reproduce the old content predicate inline.

The project excludes damage calculation, but the old `igniteForSeconds` mutation is a player fire-timer/state response and is distinct from damage. Preserve the old content gate and inline ignition for the client historical sweep while omitting the excluded damage branch and modern deferred effects.

## Bounded claims

This review covers the adapter changes at the revision above and the named accepted callback evidence. It is not runtime parity evidence and does not establish a patch-level introduction boundary. No product code, canonical source, tests, build, game launch, or decompiler was changed or run.

## Evidence used

- Canonical exact decompiled sources: `D:/Javastuff/LegacyParkourCompat/decompiled_minecraft/1.21.2/mojmap`, `1.21.3/mojmap`, and `26.2/unobfuscated`.
- Accepted materials: `.native-source/20261030/callback-eligibility-closure/report.md`, `callback-eligibility-audit/audit.md`, `callback-inventory/report.md`, `callback-order-audit/report.md`, `ordinary-pool/shared-callback-phase-order/phase-order.md`, and the `typed-callbacks`, `fire-lava-cauldron`, and `powder-snow-water` closures.
