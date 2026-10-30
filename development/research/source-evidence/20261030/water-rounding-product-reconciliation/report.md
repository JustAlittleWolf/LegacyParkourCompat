# Water movement rounding: product reconciliation

## Disposition

The accepted 1.20.6-to-1.21 arithmetic witness already has product coverage. No new candidate or implementation seam is justified by this accepted scope. The existing operation has the right resolver bucket, recomputes the historical float expression from the legacy enchantment level, and only overrides native factors when the modern received attribute is the corresponding built-in Depth Strider state. The accepted Depth Strider II witness therefore stays within the claim's explicit matching-state gate.

This is not evidence of general historical parity for arbitrary received water-efficiency attributes or extra modifiers. Those states are outside the audit's accepted witness. Current native behavior remains the fallback for them.

## Product coverage and resolver range

- [`WaterEnchantmentFactors.java`](../../../src/client/java/me/wolfii/legacyparkourcompat/change/v1_20_5/WaterEnchantmentFactors.java#L33) is annotated `@MovementChange(emulates = ParkourVersion.V1_20_5)`. `ParkourVersion.V1_20_5` includes releases 1.20.5 and 1.20.6; the following `V1_21` bucket includes 1.21 and 1.21.1. [`ChangeResolver.java`](../../../src/main/java/me/wolfii/legacyparkourcompat/impl/ChangeResolver.java#L25) selects the closest applicable change at or after the selected profile per hook, so this existing implementation covers the 1.20.6 profile and ceases to apply at 1.21. No additional `V1_20_6` provider is required.
- [`WaterEnchantmentFactorsHook.java`](../../../src/client/java/me/wolfii/legacyparkourcompat/mechanic/hook/fluid/WaterEnchantmentFactorsHook.java#L9) is the existing independently resolved operation (`player.water.enchantment_factors`). Its input/output pairs drag and acceleration factors.
- [`LivingEntityMixin.java`](../../../src/client/java/me/wolfii/legacyparkourcompat/mixin/LivingEntityMixin.java#L516) wraps the `travelInWater` call to `moveRelative`, supplies the native slow-down and acceleration values to this hook, then passes its results to the original call. It keeps the native path when no historical implementation resolves.
- The change reads the ordinary foot-slot Depth Strider level from the current enchantment component, permits only levels 0–3, and checks that the received water-efficiency attribute has base zero and exactly the single expected built-in modifier (or no modifier at level zero). Other attribute state returns the passed-in native factors. For an admitted state, it caps and halves the historical level in the historical order and evaluates `(target - 0.02F) * factor / 3.0F`; it preserves the old drag interpolation and later Dolphin's Grace override.

## Source seam and input gates

The accepted source review gives the bounded comparison:

- 1.20.6 Mojmap `LivingEntity.travel(Vec3)` reads `EnchantmentHelper.getDepthStrider`, caps the float level at `3.0F`, halves it off ground, and uses `(target - 0.02F) * level / 3.0F` for both water drag and acceleration interpolation.
- 1.21.0 and 1.21.1 Mojmap `LivingEntity.travel(Vec3)` cast the received `WATER_MOVEMENT_EFFICIENCY` attribute to float, halve it off ground, and multiply by that factor without the final division. The audit verifies the relevant 1.21.0 source files are identical to 1.21.1; the exact stable-profile range is therefore 1.20.6 versus the 1.21 profile bucket.
- 26.2 unobfuscated `LivingEntity.travelInWater` still reads `WATER_MOVEMENT_EFFICIENCY`, halves the value off ground, and uses it without the division for both drag and acceleration (`LivingEntity.java:2519–2526`). The existing mixin/provider seam is therefore still the active insertion point.

The accepted numeric witness is grounded, non-sprinting, `getSpeed() == 0.1F`, Depth Strider II, and received attribute value `0.6666667F`, with nonzero movement input. For that corresponding state, current-native acceleration is `0.07333333790302277F`; the product's hook recomputes the 1.20.6 result `0.07333333045244217F`. Both drag calculations produce `0.6306667327880859F` for this witness, so this accepted witness adds no separate drag consequence. Keep the provider's shared drag handling as existing coverage; do not claim a drag difference from these numbers.

The historical arithmetic input is the old enchantment level; the 1.21+ vanilla arithmetic input is the effective received attribute value. The provider does not substitute the received attribute as the old formula's factor. It uses that attribute only as an admission check for the corresponding vanilla Depth Strider modifier. Arbitrary custom modifiers, additional modifiers, noncanonical attribute bases, and mismatched client snapshots all take the native fallback and remain outside this acceptance. Do not broaden this report into a provider/packet/equipment synchronization claim; the source audit explicitly leaves attribute packet/application timing and other providers open.

## Candidate decision

There is no missing neutral operation for the accepted witness: `WaterEnchantmentFactorsHook` already exposes the required acceleration at the pre-`moveRelative` seam, and its existing change already reproduces the order, cap, airborne half, and division. A new acceleration-only hook would duplicate an independently resolved operation without adding accepted coverage. There is likewise no product change to make from this finding alone. Expanding behavior to arbitrary received attributes would require separate accepted evidence for the mismatch/modifier gates before it could be proposed.
