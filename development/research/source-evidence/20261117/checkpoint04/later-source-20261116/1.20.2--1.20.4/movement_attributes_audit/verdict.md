# Independent source audit verdict — S6.1 player movement attributes

## Binding

- Candidate: `build/later-source-20261116/1.20.2--1.20.4/movement_attributes/report.md`
- Candidate SHA-256: `a728e4012d12d8c696d198e9efb0bb715b61a04905fa96641128ee22ce2701bd`
- Verdict: **PASS for the bounded, endpoint source claims reviewed below; keep all declared dependencies open.**
- This verdict is bound only to the candidate revision and exact endpoint source identities listed here. It does not establish an intermediate cutover, pair freeze/full-pair closure, or runtime parity.

## Exact source identities

Both source directories resolve to the claimed exact release and Mojmap namespace. I independently checked the ready markers, provenance and source-manifest SHA-256 values against the report. I also verified every manifest entry against the source tree: 4,904/4,904 files for 1.20.2 and 5,048/5,048 for 1.20.4, with no missing files or hash mismatches.

- 1.20.2 Mojmap: ready marker `5ff3322f287c250ee914c7ca3840d053bffc3c2c5f35e91b59314d948292483f`; provenance `1dad9f725f6cb25b6568700fa0294f83b66ea9633b0d62ed354d7bd8b45d4f05`; source manifest `e439ff7e1d3f1d31e01be1edd141f94d917a9dc7fa0de5f69904544cbbe62887`.
- 1.20.4 Mojmap: ready marker `6225b97979420cc743c8b7156a0ce512ef27d0dbf48bfffbc40d3e1c8cb044d1`; provenance `10914d9b7b3fc29de066ca24f7d905e4d4f0a451a6c81729a2d1981addfc1385`; source manifest `fd3c8668483e5ff208c847474f6cbd3952a909c4aa602ed98b24e3d11a9602a1`.

## Reviewed claims

I found no source-backed contradiction in the report's bounded S6.1 claims:

- `Attributes` preserves the stated movement, flying and horse-jump defaults, bounds and syncability. The attribute declaration order changes; the corresponding movement attribute values do not. `Attribute`, `RangedAttribute`, and the `AttributeInstance` initialization/sanitization bodies match. `DefaultAttributes` has one additional entity registration in 1.20.4, `BREEZE`; the player registration remains present on both sides.
- The `LivingEntity.createLivingAttributes` and `Player.createAttributes` bodies match across endpoints. The player-specific movement base is `0.1F`; this correctly distinguishes it from the generic movement attribute default of `0.7F`.
- `Abilities` is byte-identical across the endpoints with walking speed `0.1F` and flying speed `0.05F`. `Player.readAdditionalSaveData` applies the ability walking speed to the movement-speed base on both sides. The client ability handler updates the same ability fields; the attribute update handler applies the packet base and then replaces modifiers in the same order on both sides.
- The complete `AttributeInstance`, `AttributeMap`, and `AttributeSupplier` files match across endpoints. The inspected aggregation order is addition, base multiplication using the post-addition value, sequential total multiplication, then sanitation. Cache invalidation and modifier add/remove behavior are therefore unchanged in the compared framework.
- The selected player speed consumers (`Player.aiStep`, `Player.getSpeed`) and sprint modifier body match across endpoints. The inspected equipment replacement path removes old stack modifiers before adding the new stack modifiers on both sides.
- `MobEffect` and `MobEffects` are byte-identical across endpoints. The built-in speed/slowness movement modifiers retain the stated amounts and operation. In the stated player/LivingEntity source census, no direct `Attributes.FLYING_SPEED` or `Attributes.JUMP_STRENGTH` use was found; the observed jump-strength consumers are horse/camel paths.

## Scope limits retained

The report explicitly leaves resource-backed and registered item/equipment/enchantment/effect membership and item-specific modifier sources open. This audit did not close those dependencies. The no-difference conclusion is therefore accepted only for the source-reviewed defaults, framework, selected player consumers, packet/application paths, and built-in effect registrations at these two endpoints. Keep the report's exclusions and open gates intact; do not infer broad attribute-source coverage or pair completion from this verdict.
