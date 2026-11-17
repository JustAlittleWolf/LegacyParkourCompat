# 1.21.5–1.21.8 Mojmap S6.1–S6.3 continuation

Status: COMPLETED for the assigned finite Java source slices; packaged-resource applicability remains an explicit, separable gate.

## Evidence identity and focal file hashes

Canonical exact sources: `decompiled_minecraft/1.21.5/mojmap/` and `decompiled_minecraft/1.21.8/mojmap/`, Mojmap. Both ready-marker source-manifest digests were verified against the actual manifest bytes: 1.21.5 `365cc2d22446ceba0e36f46aa1c95ce4cc31514dbd657505680fb660f3adefd9`; 1.21.8 `8ffb76cea647a2ba4fe58e000678f751bea2c40ea6358bae492e962ed1d9d008`.

SHA-256 (1.21.5 / 1.21.8):

- `net/minecraft/world/effect/MobEffectInstance.java`: `8B35C542A25090F7A45FA019A5263EA51CC1BEB2D58605DD4ED65E805035BC36` / same.
- `net/minecraft/world/effect/MobEffect.java`: `A59B16B493D0C1FE164DC09E28A00050F4BF47AD95DF0ED67E205B46B10A0E66` / same.
- `net/minecraft/world/entity/EntityEquipment.java`: `149C5634010195B2DEB31B7D7EB919F25BF0FE85765E0D8F7C994076D10A0B0B` / same.
- `net/minecraft/world/item/enchantment/EnchantmentHelper.java`: `B1C8CE1EF431D57488B4C6F991AA01E9D8372ADAA077DF8FF55A419D9B9448A0` / same.
- `net/minecraft/world/entity/ai/attributes/RangedAttribute.java`: identical per-file comparison; it clamps attribute values to the declared range as before.
- `net/minecraft/world/entity/ai/attributes/AttributeInstance.java`: `6E557DACA10617E6A8F4AF17D6436A8D1165CD175048E98EA795629A274C44CB` / `C2D4F89E7AE2ABBE856E7E1AA5BFF2CA613073CBDA282C1975636E479BA8E346`.
- `net/minecraft/world/entity/ai/attributes/DefaultAttributes.java`: `24DEBDF93D7CF4B9082689F426DBDFDA51F66213DE112D9832426DD555196ACA` / `56E43F365AE0971E3C8417FAEB025F9E3E73983BA83517CB853B80F25FA01991`.
- `net/minecraft/world/entity/player/Player.java`: `8FC187F33999DB9DFC49251E95E92A17645A50ADAB16CA0F93F4948209F62036` / `8DC5514FE44311F39268692F5188840B9F65CB71143228FA8B84242585EA7987`.
- `net/minecraft/world/entity/LivingEntity.java`: `A8AED863D4FDC515C751DD2878A8BB9C13179228CB8DBB50EDF1D19CD5404271` / `609F0197A0B4551AB42279E452C11CDD256135B1D467C2A95950B0E9FD7DDEF8`.

## Closed Java slices

- Effect duration/application/replacement/expiry: `MobEffectInstance` is byte-identical. In `LivingEntity`, paired bodies for `tickEffects`, `addEffect`, `removeEffect`, `onEffectUpdated`, and `onEffectsRemoved` were compared. Changes in `LivingEntity` around these line offsets are serialization API migration (CompoundTag/NBT ops to ValueInput/ValueOutput/Codec) and unrelated waypoint/travel features; active-effect replacement, callback ordering and attribute modifier add/remove paths are unchanged.
- Clientbound application routes for incoming effect state: effect update packet classes were compared (identical); player client packet listener applies add/update/remove through these unchanged `LivingEntity` entry points. No changed movement-relevant effect state path found in this pair.
- Equipment and held/worn modifiers: `EntityEquipment` and `EnchantmentHelper` are byte-identical. Compared `LivingEntity` equipment detection, collection, hand-swap, modifier removal/addition, and location-effect transition bodies; only save serialization and new waypoint-related code differ in the full-file diff. No changed order or slot handling found for the movement-relevant attribute modifiers.
- Attribute aggregation: `AttributeInstance` diff is isolated to persistence (`save/load` replaced by `pack/apply(Packed)`); operation buckets, dirty invalidation, cache evaluation, addition/multiplication order and modifier-by-ID maintenance are unchanged. `AttributeMap` changes likewise relate to persistence; lookup, add/remove modifier and pack/apply paths do not alter computed values.
- Player movement speed defaults and consumers: player attribute supplier still gives `MOVEMENT_SPEED` 0.1F; ability walking-speed base reset is unchanged; player `getSpeed()` remains `(float)getAttributeValue(MOVEMENT_SPEED)`. `LivingEntity` movement-speed attribute registration, sprint modifier and powder-snow modifier are unchanged. `DefaultAttributes` differences add nonmovement attributes to unrelated entity defaults; player movement-speed default entry is unchanged. `RangedAttribute` clamp procedure is identical.

## Findings and open gates

No changed source operation in the assigned Java slices is confirmed. Thus no difference finding emitted. Movement-speed attribute values remain bounded by the same ranged attribute; the examined path introduces no new cast, rounding, clamp, lifecycle, or client-update change.

The decompiler output is Java only and contains no packaged built-in data archive. Java-side player defaults and all Java mechanics used by the assigned slices are covered above. Exact packaged resource-backed item/effect attribute modifier definitions/tags were not present in these source trees and are the remaining resource gate if the assignment requires proving the complete built-in data set. This does not block the bounded Java-source no-difference result. No other open Java slice remains in the assigned effect, equipment/attribute lifecycle, or player speed/clamping scope.

No product, wiki, Git history, backlog, implementation, tests, launches or source generation were used.
