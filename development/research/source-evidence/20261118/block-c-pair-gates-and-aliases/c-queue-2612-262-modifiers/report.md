# 26.1.2 → 26.2 modifier residual queue

## Scope and source identity

Host: Windows; inspection used PowerShell. Exact pair: published unobfuscated Minecraft client sources 26.1.2 → 26.2, under D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\<version>\unobfuscated. This report covers the assigned residual modifier slices only; it is not a full-pair coverage audit.

The local provenance identifies version IDs 26.1.2 and 26.2, namespace unobfuscated, and Vineflower 1.12.0 on Java 25. The ready markers declare 6,882 and 7,055 Java source files respectively. In both trees, the SHA-256 of unobfuscated.sources.sha256, artifacts.sha256, and movement-diagnostics.txt matches the corresponding ready marker. The relevant compared source files checked below match their entries in each source manifest.

| Pair side | Provenance SHA-256 | Ready marker SHA-256 | Source manifest SHA-256 |
|---|---|---|---|
| 26.1.2 | 8d727f880cc91f4a297e148c5b75976303d051457334bc689268fbdf631a012a | f24af0f4d38543c2bd9c19b50f347d4e89d6027050e4b21ae070734843946bb7 | 54ae320660ca911a6d20514c965c33eb414fbedd1ad6727bbaa95fe906aa23c0 |
| 26.2 | a328f5333a1b27aadde4422b23159a323ae219ccd26f11207c1a5461366f27d1 | f9406adb6bf7cb4c1ab0792a798ab2cb90e082ad4c2eee032c9f674d0ea8070f | a7ad74fc712567eb87136afc1eafcfd602b4088e9869f3596331fd34faefe894 |

## Findings and coverage

### 1. Player movement attributes, supplier and clamp/value lifecycle

No historical player default delta found. Player.createAttributes() preserves its existing order and base values: movement speed 0.1F, attack speed/luck, block interaction/break/submerged mining, sneaking speed, mining efficiency, sweeping ratio, and waypoint ranges (Player.java, 26.1.2 lines 205–217; 26.2 lines 206–218). The resolver for player defaults remains DefaultAttributes → Player.createAttributes() on both sides.

The existing movement attribute definitions retain their compared defaults and bounds, including movement speed, sneaking speed, jump strength, gravity, scale, step height, safe-fall distance, movement efficiency, and water movement efficiency (Attributes.java). AttributeSupplier.java, Attribute.java, RangedAttribute.java, and AttributeMap.java are byte-identical across this pair. RangedAttribute.sanitizeValue still maps NaN to the minimum and clamps to [minValue,maxValue]. AttributeInstance.calculateValue retains the same order: base plus ADD_VALUE, add each ADD_MULTIPLIED_BASE against that accumulated base, multiply sequentially by 1 + amount for ADD_MULTIPLIED_TOTAL, then sanitize.

Two source deltas are recorded as scope gates, not older-profile findings:

- 26.2 adds AIR_DRAG_MODIFIER (default 1.0, range 0–2048) and FRICTION_MODIFIER (default 1.0, range 0–2048) to Attributes and the shared living-attribute supplier; 26.2 applies them in LivingEntity movement friction/air-drag calculations. At the default value 1.0, the modifier expression returns the prior friction factors. No vanilla player modifier provider referencing either new attribute was found in the examined effects, enchantments, item defaults, or equipment-consumer paths. The 26.2-only feature remains modern for a 26.1.2 profile. Its data-driven default-resource payload gate remains open because these decompiled source trees contain no files below data/ (zero files on both sides); Java provider declarations were reviewed instead.
- KNOCKBACK_RESISTANCE lowers its allowed minimum from 0 to -2 in 26.2 (Attributes.java, old/new definition). The player base remains 0 and the compared vanilla player/item/effect defaults supply no negative value. The direct knockback consumer remains in the same multiply-by-1 - resistance position in LivingEntity; no vanilla-player negative-value path was established in this slice. No source finding is drafted from a changed clamp with only an unproven external input.

AttributeInstance.Packed.CODEC changes the base field combinator to ExtraCodecs.optionalAlwaysPresentFieldOf. Its helper decodes an absent field to the same 0.0 default and encodes the field present; the attribute calculation and apply lifecycle are unchanged. This is a serialization-shape delta, not a movement-value delta for equal packed state.

26.2 also adds BOUNCINESS (default 0, range 0–1) to the attribute registry and common living supplier. It is recorded only as the requested excluded modern attribute surface; collision response was not inspected.

### 2. Movement mob-effect attach, replace, remove, expiry, and sync

No difference found in movement-effect modifier lifecycle. MobEffects.java is byte-identical. Speed still contributes +0.2F and slowness -0.15F as ADD_MULTIPLIED_TOTAL; the jump-boost safe-fall-distance modifier remains the same. MobEffect.addAttributeModifier, createModifiers, addAttributeModifiers, and removeAttributeModifiers retain the same modifier-template and iteration behavior (MobEffect.java; its only compared changes are the unrelated instantaneous-effect method spelling).

LivingEntity still applies attribute modifiers on the non-client add path, removes/re-adds them on amplifier refresh, removes them when effects are removed or expire, and refreshes dirty attributes after update/removal. The tick path still sends effect updates/removals to passengers in the same order (LivingEntity.tickEffects, onEffectAdded, onEffectUpdated, onEffectsRemoved; old lines 827–1096, new lines 848–1119). MobEffectInstance has no change to its update/tick/expiry body; the compared difference is setDetailsFrom visibility only. No effect draft warranted.

### 3. Held/worn modifier views, enchantment defaults, and consumer order

No difference found in the existing vanilla modifier view/consumer sequence. PlayerEquipment.java, ItemAttributeModifiers.java, EnchantmentHelper.java, EquipmentSlot.java, and EquipmentSlotGroup.java are byte-identical. ItemStack.forEachModifier(EquipmentSlot, …) still enumerates the item component modifiers first and delegates to EnchantmentHelper second. ItemAttributeModifiers.forEach preserves entry-list order while filtering by slot. LivingEntity.collectEquipmentChanges still iterates EquipmentSlot.VALUES, stops the previous stack's location effects before applying the changed stack, then calls the stack modifier view; for each modifier it removes the same ID before transient-add. The 26.2 method is made protected and takes the last-equipment map as a parameter; the inspected body keeps this order.

Movement-relevant enchantment declarations retain their paired defaults: Depth Strider's water-movement-efficiency modifier, Soul Speed's movement-speed and movement-efficiency modifiers and conditions, and Swift Sneak's sneaking-speed modifier. VanillaEnchantmentTagsProvider.java is byte-identical. VanillaItemTagsProvider keeps the examined foot-armor-enchantable relation, and VanillaBlockTagsProvider keeps the Soul Speed block membership (line offsets differ). EnchantmentTagsProvider / ItemTags declarations and providers are Java source only in this decompiler output: both data/ payload trees are empty, so shipped default JSON tag payloads cannot be compared here. That is an explicit resource gate, not a claim of payload identity.

The assignment's already accepted ordinary item-use and sneaking-default dependency slice was not re-reviewed. No movement-related modifier-order difference or finding draft was produced in the remaining held/worn slice.

## Drafts and open gates

Finding drafts: none. The reviewed older-profile claims either have no paired behavior difference or depend on modern-only/external modifier inputs that do not establish a vanilla 26.1.2 player path. Resource payload comparison remains open because neither source tree includes resource files under data/. This result does not close pair-wide source coverage or the independent audit.