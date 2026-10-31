# Independent PRODUCT review: legacy movement speed modifier order

## Scope and verdict

Reviewed product commit `e48dd1fc68409bf250840c1a6def04574cd8b2bb` against base `26f4ba2e8f146ac9f8ddf778d41b1eb46c71eddd`, using accepted sanitized candidate review `e08b3673db3986f1d36532cd0855dd2b7c85d51e`, finding `development/research/findings/1.20.4--1.20.5/legacy-item-movement-speed-modifier-order.md`, and applicability evidence `development/research/source-evidence/20261031/checkpoint51/modifier-alias-boundary.md`.

**Verdict: ACCEPT.** No actionable product blocker found.

## Product integration checks

- `ChangeResolver` filters to changes whose `emulates` version is at least the selected version and chooses the smallest such ordinal. `ParkourVersion.V1_20_2` groups 1.20.2–1.20.4; the new `LegacyPlayerMovementSpeed` is annotated at that version. Thus it covers the intended newly established range. The moved `NativePlayerMovementSpeed` fence is annotated at `V1_20` (1.20/1.20.1), so the older `V1_8_1` provider does not leak past 1.20. The pre-existing `V1_20_5` provider remains unchanged and is selected for 1.20.5/6 and later profiles. The `.2` implementation is not selected there.
- Runtime captures actual `RangedAttribute` bounds and sets `localClientPlayer` only for a `LocalPlayer` identical to `Minecraft.getInstance().player`. The existing client-side `MovementRuntime.find` gate also requires a player in a client-side level. The sole `PlayerMovementSpeedMixin` remains thin and delegates to `MovementRuntime`; the client mixin config registers it once. Non-local players and logical-server players retain native behavior.
- Snapshot expansion preserves the existing `baseValue`, `modifiers`, and `history` components used by the `V1_8_1` and `V1_20_5` providers. The runtime is the only snapshot constructor in `src/main` and `src/client`; the existing providers were not changed. The new `.2` provider intentionally does not consult bounded modifier history, so prior history overflow/incompleteness does not suppress this independent capacity proof.
- The new implementation validates all current modifiers and all three operation groups (addition, multiplied base, multiplied total), reconstructs each group in legacy UUID HashSet bucket order, preserves the old shared-base calculation for operation 1, and sequentially multiplies operation 2. It rejects unknown/noncanonical IDs, duplicate recovered UUIDs, invalid operation values, oversized groups, bucket collisions, and any pair whose relative bucket order changes at a power-of-two capacity from 16 through `1 << 30`; fallback is native. These checks cover retained HashSet capacities after removals, independently of the current set size.
- The explicit aliases match 26.2 `AttributeModifierIdFix#ID_MAP` for sprinting, powder snow, Speed, and Slowness. Other canonical `minecraft:<uuid>` IDs recover the former UUID exactly. The non-injective soul-speed alias is not accepted, as required. Invalid or semantically unknown IDs fall back.
- Sanitization matches 1.20.4 `RangedAttribute#sanitizeValue`: NaN maps to the captured minimum, negative/positive infinity and finite out-of-range values clamp to bounds, and the result is cast to `float` after sanitization. Missing/nonfinite or inverted bounds fall back to native.
- The patch consists of the runtime snapshot capture, the hook snapshot extension, the new `.2` change, and moving the existing `.2` native fence to `.0`. It introduces no duplicate mixin, manual discovery entry, or unnecessary native barrier. Existing `.8.1` and `.5` providers remain untouched.

## Exact source checks

Read-only exact source inspection confirmed 1.20.4 Mojmap `AttributeInstance#calculateValue` iterates the operation sets in addition, multiplied-base, multiplied-total order; operation 1 uses the base after additions; operation 2 updates the running double sequentially; `AttributeModifier#hashCode` uses UUID identity; and `Player#getSpeed` casts the sanitized value to float. 1.20.4 `RangedAttribute#sanitizeValue` maps NaN to its minimum and clamps other values. The exact 26.2 unobfuscated `AttributeModifierIdFix#ID_MAP` and fallback conversion confirm the alias and canonical UUID handling described above. The independently reviewed 1.20.2–1.20.4 applicability proof establishes the same HashSet-backed accumulator across that grouped range.

No product files were modified. No build, tests, decompilation, or game launch was performed.
