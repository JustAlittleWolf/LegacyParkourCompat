# Powder-frost product applicability — revision 2

## Binding and consumer disposition

This is a read-only follow-up to `applicability-r1.md`, SHA-256 `b0c9699ca97a2a0b680bf88a3aa32e50671f6faf4d6efabc869a99403d509302`. Product source is official commit `aae9c86464638cb5ed7faa535c92569b89484906`; the registered checkout and official `src` tree match.

Accepted arithmetic comparison: `powder-frost-aliases/speed-math/source/comparison-r1.md`, SHA-256 `8cdf3f5abd9727853d045e089159b933315887d67eb32b878f9450b56e32d172`. Its blind audit accepts that bounded claim in `powder-frost-aliases/speed-math/blind-audit/verdict.md`, SHA-256 `01b71d5ae0aa19b56000168d8e6e7ba30a2e86298408b14db887c6c6e89d81b6`.

The prior claim that the `v1_20_5` additive-Frost fallback itself necessarily leaves these certified movement-speed witnesses uncovered was too broad. **For the comparison’s certified equal effective snapshots, the product’s native fallback already yields the same `Player.getSpeed()` float.** The product calculator still rejects Frost inputs, and there is no general equivalence claim beyond those snapshots.

## Why the fallback is reached

For selected profiles `V1_20_5`, `V1_21`, `V1_21_2`, and `V1_21_4`, `change/v1_20_5/LegacyPlayerMovementSpeed` is the applicable product speed provider. It accepts only complete histories with total-only operations, only its three recognized speed/slowness/sprint identities, and live modifiers whose operation is total multiplication. `MovementSpeedModifierIdentity` has no `minecraft:powder_snow` mapping. A Frost modifier alone is sufficient to make this provider return empty: its `ADD_VALUE` operation makes history non-total, and its identifier is unsupported. The history size cap is not involved in that one-modifier case.

`PlayerMovementSpeedMixin` sets a return value only when `MovementRuntime.playerMovementSpeed` produces one. On empty, native `Player.getSpeed()` runs. In 26.2 that method returns `(float)getAttributeValue(MOVEMENT_SPEED)`; native `AttributeInstance.calculateValue()` sums `ADD_VALUE`, then `ADD_MULTIPLIED_BASE`, then `ADD_MULTIPLIED_TOTAL`, sanitizes, and returns a double before Player casts to float. The input is the current attribute instance, including a Frost modifier added locally or installed from a synchronized snapshot.

## Bounded equivalence

The accepted comparison establishes identical operation-bucket formulas and the final Player float cast at 1.20.5, 1.21, 1.21.2, 1.21.4, and 26.2. For its fixed input witnesses (base `0.1F`, frozen percentage `1.0F`, Frost `-0.05F`, and the stated Speed I, Slowness I, and sprint amounts), the fallback result equals the historical result for the listed modifier subsets. The all-effects witness also remains the same final float under all six orders of the three total modifiers, even though the double’s low bits may vary.

Thus, if the local Frost recomputation change produces those same effective inputs at the Player read, the native fallback is already sufficient for the accepted arithmetic witnesses; no additive-order patch is required to claim those bounded cases. The Frost-only case is also covered by direct single-addition arithmetic. This conclusion is conditional on equal effective snapshots: it does not prove that the client and historical target supply equal frozen percentages, attributes, or packet state at runtime.

## Limits and smallest extension if broader coverage is required

Do not generalize the bounded no-difference result to arbitrary attribute bases, other effect amplifiers, additional modifiers, unknown identities, modifier histories beyond the certified set, uninspected versions, or different snapshots. Those may change within-bucket rounding or introduce operations the existing provider rejects.

If broader `v1_20_5..v1_21_4` support is required, the smallest safe calculator scope is one recognized `minecraft:powder_snow` `ADD_VALUE` modifier, with no other non-total modifier, alongside only the already recognized sprint, Speed, and Slowness total modifiers. Add Frost into the base value before the existing historically ordered total-multiplier pass; preserve that order, sanitization, and float cast. The identity has a known 1.20.5 UUID, but product validation and history do not yet admit it. For the full four-ID witness, the current history cap of three also rejects the snapshot; that cap must allow Frost plus the three known total identities. Continue native fallback for any other operation, identifier, or larger history. This is a bounded feasible extension, not evidence that it is needed for the accepted numeric witnesses.

The earlier `v1_20_2` speed provider already maps powder snow and reconstructs all three operation buckets; this residual is specific to the later total-order provider.

## Decision

Keep the post-travel Frost modifier recomputation as a product **GAP**. Reclassify its interaction with `v1_20_5` speed aggregation as **covered for the accepted equal-input numeric witnesses, unknown beyond them**. The product may either retain native fallback for this narrow case or take the bounded calculator extension above if broader input coverage is required. The pending support-sample/source boundary and runtime scheduling limits in revision 1 are unchanged.