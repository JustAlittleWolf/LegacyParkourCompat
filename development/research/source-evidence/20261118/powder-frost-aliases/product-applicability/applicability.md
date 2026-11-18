# Powder-frost product applicability

## Disposition

**GAP — feasible client-only emulation.** The registered product has no operation that performs local post-travel Frost modifier removal/recomputation. The accepted historical behavior is reproducible through existing current-version LivingEntity methods, subject to a narrow local-player/profile gate. A separate existing movement-speed limitation means adding recomputation alone will not establish full speed parity for every affected profile.

No product files were changed. This is a read-only reconciliation record.

## Pinned inputs and product identity

- Registered product checkout: `C:/Users/Wolfi/.codex/visualizations/2026/11/17/01a1eb7b-ac27-7712-9259-3041950bc340/portal-product/`.
- Latest official product commit supplied for this review: `aae9c86464638cb5ed7faa535c92569b89484906` (“Bind the exact Brewing Stand AutoJump source witness”).
- Registered checkout was at `c702ee745d674693ac654ed7c5557171d6c4972d`; its `src` tree equals the official commit’s `src` tree (`926f23ac581ff009c7df62b12fb7cbce452a53c2`). Product source review therefore uses the official source tree. The registered worktree was clean.
- Accepted boundary packet: `development/research/source-evidence/20261117/checkpoint01/mcpk-20261116/vd-full-checklist/powder-freeze-boundary-followup/freeze-slowdown-boundary.md`, SHA-256 `3e80d4f23ffc9cce857c1f3bf83a45f13ac40224ecbe7697aeb16060033ddc74`.
- Acceptance verdict: `development/research/source-evidence/20261117/checkpoint01/mcpk-20261116/vd-full-checklist/accept-powder-freeze-boundary/verdict.md`, SHA-256 `41a3b26b1fb74236219d52518a7dab94f41d290d9375995dad5bab3a5a838831`. It accepts the adjacent stable cutoff **1.21.4 → 1.21.5**, while explicitly leaving packet timing/runtime parity open.
- Private native source marker: primary shared source tree `decompiled_minecraft/26.2/unobfuscated.ready.json`, status ready, version 26.2, unobfuscated, Vineflower / Java 25. Its source-manifest SHA-256 is `a7ad74fc712567eb87136afc1eafcfd602b4088e9869f3596331fd34faefe894`; the adjacent manifest hash matches. Rehashed relevant entries match that manifest: `LivingEntity.java` `7ffd9c70966edc50c9cb4d9a8fe17a518e2678ff44c8026e763d0b94ac0ae51a`, `Entity.java` `7afb9c1294893ffe73e3b1acffcad41c648f15de8378bff3dffaff869bb811d5`, `ClientPacketListener.java` `9cb0cc8afebae9f4e42f428a52719c645817d03893dc371e6711c7bd16eac1b6`, `Player.java` `8decc71b9c780664578ddb14591db2a2f207c72c05b676edded6f8e964576531`, and `AttributeInstance.java` `c7cbe38bfdc1b4aafcf623bd1b604200cc6190641091369ee9bb9303e8f07ad5`. The marker’s recorded outputPath names an older build path; source checks used the adjacent published 26.2 source tree and verified the selected file hashes against its manifest.

## Profile applicability

The official `ParkourVersion` enum has these selectable historical groups on native 26.2:

| Profiles | Frost rule for this operation |
| --- | --- |
| `V1_8` through `V1_16_2` | Keep current/native Frost behavior. Powder Snow/Frost did not exist in these profiles; post-introduction features retain modern behavior. |
| `V1_17`, `V1_17_1`, `V1_18`, `V1_18_2`, `V1_19`, `V1_19_3`, `V1_19_4`, `V1_20`, `V1_20_2`, `V1_20_5`, `V1_21`, `V1_21_2`, `V1_21_4` | Historical client-local `removeFrost()` / `tryAddFrost()` recomputation after travel is applicable. |
| `V1_21_5`, `V1_21_6`, `V1_21_9`, `V1_21_10`, `V1_21_11`, `V26_1` (26.1–26.1.2), `CURRENT` (native 26.2) | Keep the server-only Frost maintenance and synchronized movement-speed attribute behavior. Do not add client-local recomputation. |

The early bound matters because the resolver may supply a later-emulating hook to an earlier selected profile; the provider/runtime path must explicitly preserve the pre-1.17 native behavior. The upper bound is 1.21.4 inclusive; the adjacent 1.21.5 source moves the complete maintenance section behind a `ServerLevel` check.

## Current native behavior and available product seam

In 26.2 unobfuscated `LivingEntity.aiStep`, travel and client-authoritative block effects happen first. The “freezing” section follows travel but runs only when `level() instanceof ServerLevel`; it decrements frozen ticks under its existing condition, calls `removeFrost()` and `tryAddFrost()`, then proceeds to push handling. Thus the local client path never runs either modifier method.

The methods themselves remain usable native operations and are not server-gated internally:

- `removeFrost()` gets `MOVEMENT_SPEED` and removes the existing `minecraft:powder_snow` modifier if present.
- `tryAddFrost()` requires a non-air `getBlockStateOnLegacy()`, positive `getTicksFrozen()`, and a non-null speed attribute; it adds a transient `ADD_VALUE` modifier of `-0.05F * getPercentFrozen()` under the same `minecraft:powder_snow` identifier.
- `Entity.DATA_TICKS_FROZEN` is an entity-data integer read by `getTicksFrozen()`; current `InsideBlockEffectType.FREEZE` can set powder-snow state and increment this datum when `canFreeze()`. Current attribute packets replace the client attribute’s base/modifier set, and can therefore supply the same Frost modifier ID from the server.
- The producer’s post-travel order is material: travel has already consumed the old attribute for this tick. A local recomputation at the historical point is for following speed reads, not retroactive correction of the completed travel. Do not move frozen-tick decay, freeze damage, block state, or the server authority gate.

Product coverage is absent for this operation: the product has no Frost maintenance hook or accessor/invoker. Its client-only `LivingEntityMixin` and `MovementRuntime.find` infrastructure can support one. The existing `MovementRuntime.appliesTo` gate excludes logical servers and resolves the entity profile, but it admits any client-side `Player`; it does not by itself mean the authoritative local player. The new path must explicitly require the current `LocalPlayer` / local-authority identity. `PlayerMovementSpeedMixin` only intercepts `Player.getSpeed`; it reconstructs a value from the current attribute snapshot and does not perform the missing remove/recompute step.

## Feasible minimum delta and residual gate

The bounded product delta is a client movement hook for the post-travel Frost maintenance point, restricted to the authoritative local player and selected profiles `V1_17..V1_21_4`. It should call the exact native removal/addition pair (through a narrow mixin invoker if access requires it), preserving the current native ID, gate, float expression, and order. Native fallback remains for remote players, non-player entities, logical servers, pre-1.17 profiles, and 1.21.5+ profiles. No damage, rendering, block-state, or server-side changes are in scope.

One existing downstream gap remains material: `change/v1_20_5/LegacyPlayerMovementSpeed` only reconstructs histories containing its three known total-operation modifiers. `MovementSpeedModifierIdentity` does not map `minecraft:powder_snow`, and the Frost modifier is `ADD_VALUE`; with it present this calculator returns empty and `PlayerMovementSpeedMixin` falls back to native 26.2 speed calculation. The earlier `v1_20_2` speed calculator does map `minecraft:powder_snow` and supports all three operation buckets. Therefore, for the 1.20.5–1.21.4 subset, implementation must either separately close the additive-Frost calculation/order path from exact source or leave that combined movement-speed case explicitly uncovered. Do not claim the recomputation change alone proves full parity there.

**Next gate:** product implementation/integration assessment for the client-only recomputation hook plus an explicit disposition for the `v1_20_5` additive-Frost speed-calculation residual. After code exists, source-fidelity review and the authorized integration build remain separate; accepted source evidence does not establish packet scheduling or runtime parity.