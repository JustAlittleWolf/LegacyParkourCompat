# Movement speed, equipment, effects, and sneaking closure

Pair: exact Minecraft 1.20.4 → 1.20.5, Mojmap. Minecraft 1.20.6 was read only as later context. Scope is the local player's ordinary movement-speed attribute reads/writes and modifiers, equipment modifier application, Speed/Slowness/Soul Speed/Powder Snow modifiers, and the Swift Sneak input-speed factor. Conclusion: no concrete ordinary player movement delta was established. This is lane closure only; it does not claim pair freeze or full tick-graph closure.

## Source identity

Read-only sources were under `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\<version>\mojmap`. The ready markers identify the exact release and `mojmap` namespace; decompilation used Vineflower and Java 25. All source files named in the line-reference sections were checked against their version's `mojmap.sources.sha256` entry and matched the recorded SHA-256.

| Version | Ready marker SHA-256 | Source manifest SHA-256 (also the ready marker's `sourceManifestSha256`) | Provenance file SHA-256 | Source files / bytes |
|---|---|---|---|---:|
| 1.20.4 | `6225b97979420cc743c8b7156a0ce512ef27d0dbf48bfffbc40d3e1c8cb044d1` | `fd3c8668483e5ff208c847474f6cbd3952a909c4aa602ed98b24e3d11a9602a1` | `10914d9b7b3fc29de066ca24f7d905e4d4f0a451a6c81729a2d1981addfc1385` | 5,048 / 24,323,941 |
| 1.20.5 | `4a587caa86e4ddd68ba63a5431417933dc4df4822e33aaf29ce77ebda53947c2` | `162dac6c4f1d539a1c2cea0255b7938a5281c207ab460bdc265358016d732484` | `1d9b247fb839ad0e234054cba1668073245e60d86783eca40d44c2b7b23c6242` | 5,328 / 25,448,860 |
| 1.20.6 (context) | `1ec1dc773ea2a8a3ef48a9fc868893bd2f83d6b13b057d75b151b711788c3d41` | `56aae10684471d7abb1c366bd5dd431ab976112a87e6cc5c687a68f1eff06311` | `ee53e67e7ab51069a0eaa5e0ce13d49a9f972bdc13e3c31e041afa77f4d26e8` | 5,329 / 25,451,020 |

The 1.20.5 provenance identifies the mapping family as official Mojang client mappings and records mapping artifact SHA-256 `9afdb3b7fcc4c6acbec3bc6ccb46192bfc6460e12bf9e268e29bfee46bc7fbac`, with client SHA-256 `49d5949adbb1021a7eb00c1b949f9e39934b10249cbf86d25004774d1b3ff510`. The 1.20.4 and 1.20.6 provenance records `mappingOption: mojmap` and client hashes `9221ab461a491bf9661cd8e773a5e662aaa43d600fa7970b8c12bbfb0431b838` and `02dfd345ac1ad55692d5dbc8486ac7e4fea72cd54ac494a79cd48963048e56b2`, respectively; those older sidecars do not embed the mapping artifact identity. The 1.20.4 ready marker also predates the `provenanceSha256` field, so its provenance file is identified by the independently computed hash above.

## Compared paths and verdicts

All line numbers below are in the exact Mojmap source for the listed release. For 1.20.6, each cited 1.20.5 source file below has the same SHA-256 as 1.20.5, so the corresponding member and lines are identical.

| Path | 1.20.4 | 1.20.5 (also 1.20.6 where hash matches) | Result |
|---|---|---|---|
| Player movement-speed base and read | `Player.java:204-210` creates MOVEMENT_SPEED at `0.1F`; `Player.java:524` refreshes cached speed from `(float)getAttributeValue(MOVEMENT_SPEED)` after `super.aiStep()`; `Player.java:769` restores the ability walking-speed base; `Player.java:1489-1491` returns the attribute value cast to float. | `Player.java:221-230`, `:533`, `:779`, `:1511-1513` retain these operations and cast. | No movement delta. The extra attributes added to the Player supplier in 1.20.5 are not movement speed. |
| Sprint attribute modifier | `LivingEntity.java:127-129` defines the same sprint UUID, `0.3F`, and `MULTIPLY_TOTAL`; `:1951-1957` calls the superclass state setter, removes the modifier, then conditionally adds it. | `LivingEntity.java:134-136`, `:2008-2014` use `ADD_MULTIPLIED_TOTAL` and the renamed `id()` accessor; remove/conditional-add order is unchanged. | Enum/API rename only for this modifier's operation and ID access. |
| Speed and Slowness effect values | `MobEffects.java:10-25` defines Speed at `+0.2F` and Slowness at `-0.15F`, both with their existing UUIDs and `MULTIPLY_TOTAL`. `MobEffect.java:81-83`, `:95-112`, `:140-142` register, remove/add, and scale the template as `amount * (amplifier + 1)`. | `MobEffects.java:16-31` has the same values and UUIDs under `ADD_MULTIPLIED_TOTAL`; `MobEffect.java:117-120`, `:131-148`, `:173-176` preserves registration, remove/add, and amplifier scaling. | No movement delta in effect amount, amplifier math, or application order. |
| Soul Speed and Powder Snow speed modifiers | `LivingEntity.java:493-513`, `:524-535`, with removal at `:484-490` and `:515-522`: require non-air block state; Soul Speed additionally requires positive enchantment level and `onSoulSpeedBlock`; amount remains `0.03F * (1.0F + level * 0.35F)`. Powder Snow requires positive frozen ticks and uses `-0.05F * getPercentFrozen()`. Both use additive operation. | `LivingEntity.java:502-531`, `:533-555`, with corresponding removals at `:502-509`, `:533-540`: same gates and formulas, with `ADD_VALUE`. | Additive enum rename; movement math and gates match. Soul Speed's durability callback signature changed, outside the movement modifier result. |
| Sneaking / Swift Sneak speed factor | `LocalPlayer.java:655-668` computes crouching using flying/swimming/passenger/fit/sleep gates, clamps `0.3F + getSneakingSpeedBonus()` to `[0,1]`, then passes it to `input.tick`; item use multiplies the two impulses by `0.2F`. `EnchantmentHelper.java:175-195` takes the maximum equipped Swift Sneak level and returns `level * 0.15F`; `Enchantment.java:39-50` reads the configured slots; `Enchantments.java:31` registers Swift Sneak on legs. | `LocalPlayer.java:657-671`, `EnchantmentHelper.java:150-170`, `Enchantment.java:67-78`, `Enchantments.java:102-109` preserve these conditions and factors; Swift Sneak remains registered for the legs slot. | No change to the sneaking speed factor. The underlying input tick implementation and raw input reader remain outside this lane. |
| Equipment modifier application | `LivingEntity.java:2403-2441` detects hand/armor changes, removes modifiers from old stacks, adds transient modifiers from new stacks; `ItemStack.java:888-911` uses a present `AttributeModifiers` NBT list as a full override (even if empty), otherwise item defaults; `AttributeMap.java:95-111` removes by UUID and adds after removing a matching UUID. | `LivingEntity.java:2475-2517` iterates old/new stack modifiers for the changed slot, removes matching IDs, then transiently adds new modifiers. `ItemStack.java:883-890` reads `ATTRIBUTE_MODIFIERS`; nonempty entries are used, otherwise item defaults are used. `ItemAttributeModifiers.java:54-73` filters by slot group and enumerates entries. | Refactor from NBT multimap to component/callback path. For nonempty movement-speed modifier entries, slot filtering and remove-then-add behavior align. The empty-list edge is disposed below. |
| Attribute combination | `AttributeInstance.java:130-152` evaluates base, additions, multiply-base, then sequential multiply-total, and sanitizes. | `AttributeInstance.java:140-161` uses the same sequence and arithmetic with renamed enum values, then sanitizes the holder's attribute value. | Formula/order of operation groups match. Per-operation storage changed from a set to UUID-keyed map values; see the open order note below. |

## Equipment and item-component dispositions

The old explicit-empty override and the new empty-component fallback are a real API/data representation distinction: in 1.20.4, a present empty NBT `AttributeModifiers` list yields no item modifiers (`ItemStack.java:890-908`); in 1.20.5+, an empty `ItemAttributeModifiers` list falls through to item defaults (`ItemStack.java:883-890`; `ItemAttributeModifiers.EMPTY` at `ItemAttributeModifiers.java:23-24`; default component registration at `DataComponents.java:218-220`). This alone yields no ordinary player movement difference: a source-wide search for `MOVEMENT_SPEED` in `world/item/**/*.java` finds only the Speed/Slowness potion declarations, not a registered vanilla item's default modifier. Custom items, custom movement-speed attributes, and component/data producers are outside the bounded ordinary-vanilla conclusion.

The 1.20.5+ `ItemAttributeModifiers.compute` method (`ItemAttributeModifiers.java:75-90`) is not the player's movement-speed read path. The player path applies modifiers through `LivingEntity.collectEquipmentChanges` → `ItemStack.forEachModifier` → `AttributeInstance`; the empty-component fallback above is therefore evaluated as part of stack modifier enumeration.

The remaining open source-level candidate is iteration order: `AttributeInstance` changes the operation buckets from `Set<AttributeModifier>` to `Map<UUID, AttributeModifier>` and iterates the values. Coexisting total multipliers (for example sprint plus Speed) can make repeated double multiplication sensitive to order. These sources establish the container change but not a concrete differing value for the local player's float speed read; this is not promoted to a finding without that evidence.

## Relevant source-file SHA-256

These are full-file digests. For each row, the 1.20.5 and 1.20.6 digests are identical; the 1.20.6 source manifest separately binds those files to the 1.20.6 tree.

| Mojmap relative path | 1.20.4 SHA-256 | 1.20.5 and 1.20.6 SHA-256 |
|---|---|---|
| `net/minecraft/world/entity/player/Player.java` | `218da60bc4f8c9279f56eb87e2cf2c0af79562fbe3d818a408429c656efb95dd` | `785d93ccc94e1f912e545b2b0c355edeb352b44ee8e83a69364daec35266dbe2` |
| `net/minecraft/world/entity/LivingEntity.java` | `f7bc53db24c1798f19f9bd6f6356c86d5e560e9c8e8aac60decaf15cc785e07d` | `c66ec8dc3b1856e490e5834a46185589030d9fbc411e3e2ce64c73203cd753b2` |
| `net/minecraft/world/entity/ai/attributes/AttributeInstance.java` | `f78cd38b3fe74f98950ecc0fe4ae420f99299dc5bb43360e4e6b3c76b3509672` | `5d060c1740336835c74138d3c5fe3ae5f3b7f6a1c2f16f6844289f18ef90782a` |
| `net/minecraft/world/entity/ai/attributes/AttributeMap.java` | `288f1ad6b5924c157800d24c2f4804a7ae16c56f3856ffcfca7991b1bc6eb4ba` | `c96e7642e395168834778998cce1e860cefdea8db16d2f8d72a895bca61ee3f3` |
| `net/minecraft/world/effect/MobEffects.java` | `e8974d2e12c774aab348a65d0ade44fd3a8522270c403cb5dcc2c6db3c25fbae` | `9b24dee8a23a4b8730fdfa7ea9cbd560c9370599cc282fac1c0755f5894e7645` |
| `net/minecraft/world/effect/MobEffect.java` | `fe3d18c51352a1e84cf96bcf6386fd19b4fa6bf6d7bd667b813699fab57efae5` | `e5ef74736e7a4b8cd5103fb5bbbe10b1aeb0be3531cf5600a39e08761c3746e2` |
| `net/minecraft/world/item/ItemStack.java` | `0e0b2a4973ee41e042a9ee6158ffdd4a366676f90019cb2c7d236de7bc809710` | `74a7e82a55577a299e25180bed9560c983ab0f620f72eaff0574a9b497e7f4e0` |
| `net/minecraft/world/item/Item.java` | `36fef23a0d0c399de7b9ac8512303bfd0fa0ef98077c785787754fd6a7874c6a` | `3053e803946f731e1853ff311c936e64a3f9f41ed19ed1206cda582fb5976d2f` |
| `net/minecraft/client/player/LocalPlayer.java` | `bb5cbfb03656a1866bb77c00431618befe792081223b35db4bd121ecbb151fd5` | `6b429dfa6e0681251ec985dda1627f808652a7bbe5b70dc85c8fa0fe0ed46ffa` |
| `net/minecraft/world/item/enchantment/EnchantmentHelper.java` | `c98b9e538aae7fea33a125560400f346745bac993e859ffd3912da58f3cc1128` | `e0b4c410e0aa9499d67b38f57b34467628e882be960dce6958d4bc8ef717a6b6` |
| `net/minecraft/world/item/enchantment/Enchantment.java` | `0d5ee07bc252bf9a409dcff2f907894ca2460a9f946e05e8bed8da623b61ab09` | `5757d4833211992d85c0aa809b00a246f17e0b458eb27718074c30b0e6a7d111` |
| `net/minecraft/world/item/enchantment/Enchantments.java` | `35fa4d1f9da93971fa70bf6ec2f9218a8825ff343038b51143ae1404867f38e3` | `26eeafdf667fa57b711fab3112aa9636f20a9a38cadfbff42740e66a14c38381` |
| `net/minecraft/world/item/component/ItemAttributeModifiers.java` | Not present in 1.20.4 | `10cabeaf004d50ceb67e6e7abe667f64faa17dd893216ab41425a58f4965a2b6` |
| `net/minecraft/core/component/DataComponents.java` | Not present in 1.20.4 | `24153660ebdfab35015cc2613b7284c690b7aff788fbc6cd51233d057fc9f5a4` |

## Limits

No product, wiki, research index, backlog, or Git history was read. No source was modified; no build, test, game, or server launch was performed. Raw input reading beyond the sneaking factor call, travel, fluid/gravity branches, custom item/attribute producers, extension behavior, server-side mutations, and movement effects outside this assignment remain unreviewed. The source-level order candidate remains open as stated above.
