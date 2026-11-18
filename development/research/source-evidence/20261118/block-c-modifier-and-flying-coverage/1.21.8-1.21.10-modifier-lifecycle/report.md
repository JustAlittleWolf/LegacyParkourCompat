# S6 modifier lifecycle, attributes, effects, and equipment

## Inputs and exact identity
Release pair: Minecraft 1.21.8 → 1.21.10, Mojmap. Verified each listed selected `.java` file's bytes against its sidecar `mojmap.sources.sha256`; every listed match succeeded. Ready marker and source-manifest identities:

| Release | Ready marker SHA-256 | Provenance SHA-256 | Source manifest SHA-256 |
|---|---|---|---|
| 1.21.8 | `2e3bf302503eb127061cc605797b390f5f4a4b508f09350a59d78ced857ba7d7` | `90fcb3707371f2cfc5414e0089cfa231f505821d7aca45211c7c816332bb4cfa` | `8ffb76cea647a2ba4fe58e000678f751bea2c40ea6358bae492e962ed1d9d008` |
| 1.21.10 | `2374d901074b72cd69f0229381d6a11edea7983613c538a2e2e18d9fc73dbeb0` | `9a1db4c050c3caa28a1313f89dae9da3b28feaa5f8afba42cab798b47d7ad77c` | `4be26049350c1b314a0b198022cb1e7ab1e745e104047de7a5c7d099a9d7b7e1` |

Ready markers identify exact releases, Mojmap, Vineflower/Java 25, source counts, and the source manifest digest. Source provenance names the exact batch decompile and mapping option (`mojmap`). The canonical source files checked are the matching tree roots `decompiled_minecraft/1.21.8/mojmap/` and `decompiled_minecraft/1.21.10/mojmap/`; no nested ready-tree source path was needed.

### Selected source file SHA-256 (same digest in both source manifest and file bytes)
| Source file | SHA-256 |
|---|---|
| `world/entity/ai/attributes/AttributeMap.java` | `06088d7aeedd65b468b807c3a500da7a3a16d787917812a55dd7b81ad60cd079` |
| `world/entity/ai/attributes/AttributeInstance.java` | `c2d4f89e7ae2abbe856e7e1aa5bff2ca613073cbda282c1975636e479ba8e346` |
| `world/entity/ai/attributes/Attributes.java` | `b941c0556d9ed3c3ce1ffd4c993c9902287a690230e1510696ff13f9efdf2020` |
| `world/entity/ai/attributes/AttributeSupplier.java` | `656206fad73103cb34645ad2c51c3bd364829855b6faa43d2440d8f25bdde54e` |
| `world/entity/ai/attributes/DefaultAttributes.java` 1.21.8 | `56e43f365ae0971e3c8417faeb025f9e3e73983ba83517cb853b80f25fa01991` |
| `world/entity/ai/attributes/DefaultAttributes.java` 1.21.10 | `489a82beabfc695af6bbc5d018ab1ed2efa7d945cf13a0dace95033dc7725556` |
| `world/effect/MobEffect.java` | `a59b16b493d0c1fe164dc09e28a00050f4bf47ad95df0ed67e205b46b10a0e66` |
| `world/effect/MobEffectInstance.java` | `8b35c542a25090f7a45fa019a5263ea51cc1beb2d58605dd4ed65e805035bc36` |
| `world/effect/MobEffects.java` | `59460ae5a2087122e88eb4e6f799466ed3ea313a04a9e9162a9abf76dea440ae` |
| `world/item/enchantment/EnchantmentHelper.java` | `b1c8ce1ef431d57488b4c6f991aa01e9d8372adaa077df8ff55a419d9b9448a0` |
| `world/item/ItemStack.java` 1.21.8 | `f454d70486abdd066be5d5dc89c0d5b61ec6920fb702fe397d3d12bf902936c2` |
| `world/item/ItemStack.java` 1.21.10 | `07b284d7a7e680f0fc5cbff1c758afd3e245eea11825435f4226d7d4acdfb74e` |
| `world/entity/LivingEntity.java` 1.21.8 | `609f0197a0b4551ab42279e452c11cdd256135b1d467c2a95950b0e9fd7ddef8` |
| `world/entity/LivingEntity.java` 1.21.10 | `b8b49d60769203f7bd5afe4a1bffcdcdbec30be28960324cdc43a2df85a6eb66` |
| `world/entity/player/Player.java` 1.21.8 | `8dc5514fe44311f39268692f5188840b9f65cb71143228fa8b84242585ea7987` |
| `world/entity/player/Player.java` 1.21.10 | `af857617b66a5776e63830771360b96f75e21d47d20db08f164a2dbeee801d82` |
## Bounded path and results

### Effect modifiers: no Java difference found
`world/entity/LivingEntity.java`, `addEffect`, `forceAddEffect`, `removeEffect`, `onEffectAdded`, `onEffectUpdated`, `onEffectsRemoved`, and `refreshDirtyAttributes` (1.21.8 lines 982–1100; 1.21.10 lines 989–1107) preserve the same order and behavior for modifying attributes: add on newly applied effect, remove then add on amplifier update, remove on expiration/removal, and refresh dirty attributes after updates/removal. The only relevant source-level spelling change in these paths is `level().isClientSide` to `level().isClientSide()`. `MobEffect.java`, `MobEffectInstance.java`, `MobEffects.java`, `AttributeMap.java`, and `AttributeInstance.java` are byte-identical across this pair, matching the per-file sidecar hashes. No independent movement claim from this slice.

### Equipment attribute aggregation and removal: no Java difference found
`world/entity/LivingEntity.java`, `collectEquipmentChanges` / `equipmentHasChanged` (1.21.8 lines 2626–2680; 1.21.10 lines 2656–2710) preserve slot iteration, item comparison, old stack location-effect removal, new stack modifier enumeration, remove-by-ID then transient add, and server-side enchantment location-effect activation. `stopLocationBasedEffects` (1.21.8 lines 3518–3531; 1.21.10 lines 3548–3561) still removes each stack modifier and delegates enchantment cleanup. Item break retains that cleanup call. Names and body order are unchanged in these operations. No independent movement claim from this slice.

`ItemStack.java` differs elsewhere: it removes component-validation calls and changes some profile/block-entity tooltip plumbing, while the exact `forEachModifier` methods remain at lines 964/970 (1.21.8) and 968/974 (1.21.10), forwarding to the same `EnchantmentHelper.forEachModifier`. `PlayerHeadItem.verifyComponentsAfterLoad` in 1.21.8 only resolves the player profile; 1.21.10 removes that hook from stack load/update pathways. No movement modifier consequence is established by these differences.

### Attribute and player default providers: no player change found
`LivingEntity.createLivingAttributes` (1.21.8 lines 317–339; 1.21.10 lines 324–346) keeps the same ordered base-attribute declarations. `Player.createAttributes` likewise retains the same player entries, including `MOVEMENT_SPEED` base `0.1F`. `Attributes.java`, `AttributeSupplier.java`, and `AttributeInstance.java` are byte-identical. `DefaultAttributes.java` changes only by adding `COPPER_GOLEM` and `MANNEQUIN`; these are non-player defaults and do not alter the player provider. No independent movement claim from this slice.

### Enchantment default aggregation: Java helper unchanged; packaged input unavailable
`world/item/enchantment/EnchantmentHelper.java` is byte-identical. `ItemStack.forEachModifier` still enumerates item modifiers then delegates enchantment modifiers to that helper. The exact built-in data resource that supplies the versioned enchantment registry entries—`data/minecraft/enchantment/*.json` from each release's packaged client/server data—is not present in either decompiled Mojmap source tree or its Java source manifest. The ready metadata records a client artifact digest, but this lane's selected inputs expose no extracted resource tree or per-resource manifest, so I cannot verify resource contents or prove all effective enchantment aggregation/defaults across the pair. This remains the precise unresolved S6 dependency; Java behavior alone does not close data-driven defaults.

## Coverage boundary
This continuation closes only the listed Java effect modifier lifecycle, Java attribute defaults, equipment modifier attach/remove and enchantment helper operation with no movement-relevant difference found in those checked bodies. Effective data-driven enchantment registry defaults remain open due to the unavailable packaged resource. No source was generated or edited. No product, wiki, Git, backlog, tests or launches were accessed.
