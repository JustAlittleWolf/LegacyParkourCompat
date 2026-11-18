# Powder-frost product applicability — revision 1

## Binding and disposition

This is a read-only follow-up to `applicability.md`, SHA-256 `892e79f0c76ac6a5ded6eafcc0d43896b3c514f8a4bbefd3f8cda3fd718fcdf8`. The official product remains `aae9c86464638cb5ed7faa535c92569b89484906`; the registered checkout’s `src` tree matches that commit.

**Disposition remains GAP.** Product has no Frost modifier removal/recompute operation. Product does have an existing `OnPositionHook` that supplies the historical no-support sample for profile `V1_19_4`. Under current resolution, it does not supply that rule for `V1_17` through `V1_19_3`. A Frost-specific support-state sample can cover the missing seam without changing collision geometry or the general position resolver, but extension across those earlier profiles needs the pending exact source review. The separate 1.20.5+ additive-Frost speed-calculation limitation remains.

## Exact source facts

The exact local decompiled source hashes for the compared `Entity.java` files are:

- 1.19.4 Mojmap: `3667fee610cbc5f58012e3a8fb8d6c4849f649fb7fe5300595158112d8b4b58b`.
- 1.20.0 Mojmap: `94b9c3656715de2d61fa9a02ccef164261e50eb13d6090a6f07e58fe9c5b0759`.
- 26.2 unobfuscated: `7afb9c1294893ffe73e3b1acffcad41c648f15de8378bff3dffaff869bb811d5`.

In 1.19.4, `Entity.getBlockStateOnLegacy()` reads `getOnPosLegacy()`; that calls `getOnPos(0.2F)`. The exact helper floors entity-center X, entity Y minus 0.2F, and entity-center Z. It reads that primary state to test for air. Only on air does it read the block below and test fence, wall, or fence-gate membership. After the position helper returns, the outer `getBlockStateOnLegacy()` reads the chosen position’s state again; preserve this short-circuit and final read order if reproducing the sample.

In 1.20.0, `getBlockStateOnLegacy()` and `getOnPosLegacy()` retain that caller chain and 0.2F legacy Y offset, but `getOnPos(float)` now consults `mainSupportingBlockPos` when populated. At offset 0.2F, the selector first reads tracked-support state to decide whether a fence, wall, or fence gate retains the full tracked position. Another tracked support keeps its X/Z and replaces Y with `floor(position.y - 0.2F)`. The outer `getBlockStateOnLegacy()` then reads the selected position state. With no tracked support it floors entity-center X/Y-offset/Z and does not perform the former air-then-below fence/wall/gate fallback. 26.2 retains this same selector and legacy getter chain. This is a support-coordinate sampling change, not a collision-shape change.

In both 1.19.4 and 1.20.0, `LivingEntity.aiStep` completes its travel call before the freezing section invokes `removeFrost()` / `tryAddFrost()`. The latter reads `getBlockStateOnLegacy()` at that post-travel point and short-circuits on its non-air result before reading frozen ticks. A replacement Frost sample must retain this ordering and the selected profile’s exact block-position and state-query sequence. Do not use the travel friction support selector: it has a different accessor and offset.

The 1.21.4 → 1.21.5 accepted freeze-maintenance side boundary remains separate from this 1.19.4 → 1.20.0 support-sample boundary. The latter is present in the inspected exact files and product hook comments, but the newer Frost-specific draft mentioned in the task is still pending independent review. This note treats it as preliminary product-applicability evidence, not a newly accepted finding.

## Existing product hook reach

- `EntitySupportingBlockPositionMixin` wraps current `Entity.getOnPos(float)` and dispatches `OnPositionHook`. The normal 26.2 `getBlockStateOnLegacy()` → `getOnPosLegacy()` → `getOnPos(0.2F)` call reaches this wrapper.
- `change/v1_19_4/OnPosition` reproduces the 1.19.4 center-floor and conditional-below fallback, ignoring `mainSupportingBlockPos`. With closest-later resolution, this hook is selected for `V1_19_4`.
- `change/v1_19_3/NativeOnPosition` returns the wrapped native method. Because it is the nearest later provider for earlier selections, profiles `V1_17`, `V1_17_1`, `V1_18`, `V1_18_2`, `V1_19`, and `V1_19_3` keep the 26.2 tracked-support policy here. That provider comment explicitly says it does not establish earlier-profile equivalence.
- `SupportingBlockPositionHook` and `GroundFrictionSamplePoint` wrap `getBlockPosBelowThatAffectsMyMovement()`, used by movement-factor consumers. They do not control the Frost reader, which calls `getBlockStateOnLegacy()` and then `getOnPosLegacy()`.

Therefore existing product support is profile-specific: it supplies the old sample for `V1_19_4`, does not supply it for `V1_17..V1_19_3`, and uses native tracked-support sampling for `V1_20..V1_21_4` (where that is the target source policy).

## Minimal Frost-only seam and exact residuals

At the Frost change’s post-travel gate, keep the existing frozen-tick and modifier logic/order. A narrow wrapper around the `getBlockStateOnLegacy()` call inside `tryAddFrost()` (or an equivalent Frost-local method that preserves the same read sequence) can change only this input without changing collision geometry, the general Entity getter, or core resolution. For the non-air sample only:

1. For `V1_17..V1_19_3`, reproduce the target-era position/state sequence explicitly: center X/Z and `floor(entityY - 0.2F)`; query that state for air; only on air, query one block below and test the exact fence/wall/fence-gate fallback; then perform the final selected-position state read. This Frost-local predicate avoids changing the shared `Entity.getOnPos(float)` or `SupportingBlockPositionHook` behavior. Its use on these early profiles is contingent on independent source review proving that same historical rule across those releases. For `V1_19_4`, let the existing `OnPositionHook` supply this rule through the normal getter.
2. For selected profiles `V1_20..V1_21_4`, retain the normal `getBlockStateOnLegacy()` query so the current 26.2 selector uses tracked support X/Z when present, with the native 0.2F legacy Y and fence/wall/gate adjustments. Do not apply the 1.19.4 center/fallback rule to this era.

Keep the enclosing recomputation client-only and explicitly local-player-authoritative; current generic runtime applicability gates alone also admit remote client players. Do not change server behavior, frozen-tick decay, freeze damage, vanilla block state, rendering, or collision geometry.

The earlier speed-calculation residual is narrower than a broad “unknown modifier combination” label: in `v1_20_5/LegacyPlayerMovementSpeed`, a sole live Frost modifier with ID `minecraft:powder_snow` and operation `ADD_VALUE` is enough to fall back. `MovementSpeedModifierHistory` marks its history non-total, and `MovementSpeedModifierIdentity` does not map that ID; the calculator returns empty without needing any second modifier, an over-cap history, or any other unknown modifier. Those other fallback cases remain separate. The earlier `v1_20_2` calculator does map powder-snow ID and accepts all three operation buckets.

**Next gate:** independent exact-source review of the Frost non-air sample across `1.17..1.19.3` and acceptance of the adjacent `1.19.4→1.20.0` sample boundary. Then product integration must preserve the local-authority/profile bounds and disposition the sole-`ADD_VALUE` Frost speed-calculation gap for `V1_20_5..V1_21_4`. Runtime scheduling/parity remains unproven.