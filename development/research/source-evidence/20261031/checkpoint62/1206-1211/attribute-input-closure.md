# Movement attribute and equipment input closure: 1.20.6 → 1.21 → 1.21.1

## Result and comparison boundary

This slice verifies the vanilla default mapping from the old equipped-enchantment and block-tag movement inputs to the 1.21 attribute-backed movement inputs. For equal valid effective input state on the client, the modifier/equipment/tag dependency is closed for the examined Swift Sneak, Depth Strider, and Soul Speed routes. This does not claim that the server calculates or sends those states at the same time, or that arbitrary data-pack overlays preserve the vanilla definitions. Those are not needed under the assigned equal-valid-raw-state rule.

The water-travel arithmetic remains the independently accepted one-ULP difference: the mapping of Depth Strider II to `WATER_MOVEMENT_EFFICIENCY=0.6666667F` does not remove the old/new travel operation-order change. No additional movement delta was found in Swift Sneak values or Soul Speed's vanilla movement-speed modifier values.

## Exact source identity

Sources are read-only Mojmap under `D:/Javastuff/LegacyParkourCompat/decompiled_minecraft/{1.20.6,1.21,1.21.1}/mojmap`.

- 1.20.6 ready marker identifies client SHA-256 `02dfd345ac1ad55692d5dbc8486ac7e4fea72cd54ac494a79cd48963048e56b2`; its source-manifest SHA-256 is `56aae10684471d7abb1c366bd5dd431ab976112a87e6cc5c687a68f1eff06311`.
- 1.21 embedded `mojmap/.decompile-provenance.json` binds official client SHA-256 `4b56bc9b44de7e88f9fbb52e716c18240da34469fcbafa5ed2e75b04d60617e8` and official client-mappings SHA-256 `127485d79248d4ae2955a7d061c3626d38853ecb054c4d008707732264b0aac1`.
- 1.21.1 ready marker identifies client SHA-256 `499f6897d1837516680f3114072d8106e11c9adcd933fe5cf051b551089b0c99`; its source-manifest SHA-256 is `900f956e00f6fc1300bb3d689ea49df2b1a57bcaa54617344ef456d95d47cb48`.

The two endpoint manifest-file hashes match their ready markers. Every listed source below was rehashed from disk and matched its exact endpoint manifest or 1.21 embedded source-hash map. The listed 1.21 and 1.21.1 source files are byte-identical.

| Mojmap source | 1.20.6 SHA-256 | 1.21 and 1.21.1 SHA-256 |
|---|---|---|
| `net/minecraft/client/player/LocalPlayer.java` | `6b429dfa6e0681251ec985dda1627f808652a7bbe5b70dc85c8fa0fe0ed46ffa` | `c555e68ac3c63ab9b4f9a9e31933e263b96350a2bc599d11a0de5928bc24b583` |
| `net/minecraft/world/entity/LivingEntity.java` | `c66ec8dc3b1856e490e5834a46185589030d9fbc411e3e2ce64c73203cd753b2` | `324a3eee8496caab57cfaf5101ef576f1ae3c60c40e3857e96f35f3af9a3a0d8` |
| `net/minecraft/world/entity/player/Player.java` | `785d93ccc94e1f912e545b2b0c355edeb352b44ee8e83a69364daec35266dbe2` | `ed32b88c3c7c8418b83db41823520f2b6b0b49a98610306ef26e9681dc925c71` |
| `net/minecraft/world/entity/ai/attributes/Attributes.java` | `7e30d87c7a58b14d0052d2f9f7319d997b49ae7d025579cd762d28e845b2e82e` | `a9a19f556bb77fc218b5b2f4831eb6b285f4398c24fa2a3519009ca8dad2db7c` |
| `net/minecraft/world/entity/ai/attributes/AttributeInstance.java` | `5d060c1740336835c74138d3c5fe3ae5f3b7f6a1c2f16f6844289f18ef90782a` | `16a79843c53aabf5ae34b3230596995c70932197223482ad998085d6031ffee0` |
| `net/minecraft/world/entity/ai/attributes/AttributeMap.java` | `c96e7642e395168834778998cce1e860cefdea8db16d2f8d72a895bca61ee3f3` | `91bae9479619334d3f7adc4e2216db73a74ab9a09d4e6f4e1b535d132e512db5` |
| `net/minecraft/client/multiplayer/ClientPacketListener.java` | `e121e998ec25211aaecf91fffd0ea699cebd47eddea9134efd3f2ffbef8eb7cd` | `822e00d89e8def4cf7a8c22fdfefba3b9dc2135a7630e4ebe561a9d547d7f79d` |
| `net/minecraft/world/item/enchantment/Enchantments.java` | `26eeafdf667fa57b711fab3112aa9636f20a9a38cadfbff42740e66a14c38381` | `7848a2cd677aa0700434f85de37d8a0c47597741d2d1f26c8dc270f73d65b266` |
| `net/minecraft/world/item/enchantment/EnchantmentHelper.java` | `e0b4c410e0aa9499d67b38f57b34467628e882be960dce6958d4bc8ef717a6b6` | `9e2f9acd5bc6a8f3b5292d720db81464954d41c9afb635f1a7c6cb31d5a2bf84` |
| `net/minecraft/world/item/enchantment/LevelBasedValue.java` | — | `f9a0bed1d7693606f8657fadd4a59d95eea9955fcc036687e7a9dd08a8b360e3` |
| `net/minecraft/world/item/enchantment/effects/EnchantmentAttributeEffect.java` | — | `c1e2edd5c870e1ecba364cbee07ae2f12720e47681dd1dbf16ac081e392f4270` |
| `net/minecraft/data/tags/VanillaBlockTagsProvider.java` | `a0ab3c2a12f8e93ae7e6231069051712cf0f6fcaf3d06e86caa0f9b2d1af310e` | `819d133e6ee8f82485b3d84fcfcfa48e4ee2824431379b93de48c7dddc7b32f0` |
| `net/minecraft/data/tags/VanillaItemTagsProvider.java` | `decfcb06faeae28065216735147a5a1f734597136dea162cbf9e00ad058e3319` | `49538ef3c7f9a704bd9e6ddc29e9226f4057dd4fc465fd5836887e7dac6fee45` |
| `net/minecraft/data/tags/VanillaEnchantmentTagsProvider.java` | `b7cc9301cc5664d14c54d62f64497402e735369f03cc9eeb91e7538598bf1893` | `437157947e99de9de9a157f5d665947408321a0e0a94f29744c2ee559d60d788` |

## Old equipment inputs and 1.21 attribute definitions

### Swift Sneak and crouch movement scale

In 1.20.6, `EnchantmentHelper.getSneakingSpeedBonus` returns `getEnchantmentLevel(SWIFT_SNEAK, entity) * 0.15F` (`EnchantmentHelper.java:168-170`). The LocalPlayer crouch branch applies `Mth.clamp(0.3F + bonus, 0, 1)` and supplies that float to the unchanged `Input.tick` (`LocalPlayer.java:656-668`). Old Swift Sneak is registered for `LEG_ARMOR_ENCHANTABLE`, maximum level 3, slot `LEGS` (`Enchantments.java:102-107`).

In 1.21, the same crouch eligibility gates remain, but `LocalPlayer.aiStep` reads `(float)getAttributeValue(SNEAKING_SPEED)` (`LocalPlayer.java:648-665`). `Player.createAttributes()` includes the syncable `SNEAKING_SPEED` attribute, whose default is `0.3`, range `[0,1]` (`Player.java:222-234`; `Attributes.java:86-88`). The default Swift Sneak definition is maximum level 3 on `#minecraft:enchantable/leg_armor`, slot `legs`, adding `LevelBasedValue.perLevel(0.15F)` to this attribute (`Enchantments.java:559-582`). `LevelBasedValue.Linear.calculate` evaluates `base + perLevelAboveFirst * (level-1)` in binary32 (`LevelBasedValue.java:135-138`).

For valid vanilla Swift Sneak levels 0–III, the exact input float passed through crouch scaling is the same in old direct-enchantment and new attribute routes:

| Level | 1.20.6 `clamp(0.3F + level*0.15F)` | 1.21 attribute base plus modifier, cast to float |
|---:|---:|---:|
| 0 | `0.3F` (`0x3e99999a`) | `0.3F` (`0x3e99999a`) |
| I | `0.45000002F` (`0x3ee66667`) | `0.45000002F` (`0x3ee66667`) |
| II | `0.60000002F` (`0x3f19999a`) | `0.60000002F` (`0x3f19999a`) |
| III | `0.75F` (`0x3f400000`) | `0.75F` (`0x3f400000`) |

The old and new vanilla default item providers resolve `LEG_ARMOR_ENCHANTABLE` to the same six leggings. The authorized default-resource report independently verifies byte-equal `leg_armor` membership and the inherited `enchantable/leg_armor` reference at 1.20.6 and 1.21.1; the 1.21 `VanillaItemTagsProvider` declarations are byte-identical to 1.21.1.

### Depth Strider and water movement efficiency

The old `EnchantmentHelper.getDepthStrider` reads the equipped Depth Strider level (`EnchantmentHelper.java:184-186`), and old Depth Strider is defined for `FOOT_ARMOR_ENCHANTABLE`, maximum level 3, foot slot (`Enchantments.java:74-78`). In old water travel, the level is capped at 3, halved while airborne, and participates in multiply-then-divide scaling (`LivingEntity.java:2113-2132`).

The 1.21 movement branch instead reads the syncable `WATER_MOVEMENT_EFFICIENCY` attribute and halves that float while airborne (`LivingEntity.java:2100-2118`). The attribute has default `0`, range `[0,1]`, and is included in living attributes (`Attributes.java:101-103`; `LivingEntity.java:304-323`). The default Depth Strider definition is feet-only, maximum level 3, and adds `LevelBasedValue.perLevel(0.33333334F)` (`Enchantments.java:344-368`). For grounded legal levels I–III this maps to `0.33333334F`, `0.6666667F`, and `1.0F`. The default resource payload's supported-items, slot, and effect records match this source registration (`.native-source/20261030/1206-1211-resources/packaged-resources.md` and `/audit.md`).

The valid new client input is the effective received water-efficiency attribute for the same equipment/effect state. Its existence and default level mapping close the input dependency; the separate accepted water finding remains because using that value directly and the old integer multiply/divide expression produce adjacent binary32 values at Depth Strider II.

### Soul Speed and block speed factor

In 1.20.6, the local movement-support block check reads `SOUL_SPEED_BLOCKS`; `Player.onSoulSpeedBlock` also requires not flying. `LivingEntity.getBlockSpeedFactor` returns exactly `1.0F` when that tag predicate and a positive equipped Soul Speed level hold (`LivingEntity.java:489-496`; `Player.java:975-976`). The old location-change path adds a `MOVEMENT_SPEED` modifier with amount `0.03F * (1.0F + level * 0.35F)` (`LivingEntity.java:511-524`).

In 1.21, the client block-factor consumer interpolates `super.getBlockSpeedFactor()` to `1.0F` using received `MOVEMENT_EFFICIENCY` (`LivingEntity.java:483-488`). The attribute is syncable, default `0`, range `[0,1]`, and included in living attributes (`Attributes.java:71-74`; `LivingEntity.java:304-323`). The default Soul Speed definition is feet-only, maximum level 3. It supplies a location-changed movement-speed amount `LevelBasedValue.perLevel(0.0405F,0.0105F)` and, on the `SOUL_SPEED_BLOCKS` movement predicate, adds constant `1.0F` to `MOVEMENT_EFFICIENCY` (`Enchantments.java:439-522`). The movement-speed amounts exactly match the old float formula for levels I–III:

| Level | Old `0.03F * (1 + level*0.35F)` | New location-effect value | Binary32 result |
|---:|---:|---:|---:|
| I | `0.0405000001F` | `0.0405F` | both `0x3d25e354` |
| II | `0.0509999990F` | `0.0405F + 0.0105F` | both `0x3d50e560` |
| III | `0.0614999980F` | `0.0405F + 0.0105F*2` | both `0x3d7be76c` |

The new valid received state under the same Soul Speed support-block input consists of `MOVEMENT_EFFICIENCY=1.0F` plus the corresponding `MOVEMENT_SPEED` modifier. With those values and the same local tag result, the client block-factor output is 1.0 as in the old direct equipment/tag branch, and the movement-speed amounts match. The old and new default Soul Speed tag memberships both contain only soul sand and soul soil (`VanillaBlockTagsProvider.java:687` and `:732`); the allowed resource audit confirms equal packaged membership. Foot-armor eligibility resolves to the same six boot IDs, and the default data-backed 1.21.1 Soul Speed definition matches the 1.21.1 bootstrap registration.

This is the equal-effective-state consumer comparison requested by the assignment. It does not claim that server `onChangedBlock`/`runLocationChangedEffects` executes at identical times on each release. Those functions are the 1.21 server-side producer for the movement attributes; their exact update ordering, custom overlays, and later packets remain outside this client-input proof.

## Attribute and equipment packet receivers

`ClientPacketListener.handleUpdateAttributes` applies each valid packet snapshot to the resolved living entity's `AttributeMap`: it sets the base, removes prior modifiers, then adds the packet's transient modifiers. `handleSetEquipment` applies each received slot via `LivingEntity.setItemSlot`. Those method bodies are the same across the compared releases; the containing client packet listener source is byte-identical from 1.21 to 1.21.1. The new attributes are present in the local Player/LivingEntity attribute builders, so the packet receiver can apply their snapshots.

The state comparison therefore treats received equipment/enchantment data (old direct-read path), effective block-tag state, and received syncable attributes (new consumer path) as explicit inputs. An attribute producer or same-tick arrival narrative is not needed. An equipment packet alone does not establish the effective 1.21 attribute value; the comparison uses the actual valid attribute snapshot as supplied, as required by the equal-raw-state assumption.

## Closure and remaining limits

- **Closed for default vanilla states under the stated input boundary:** Swift Sneak item/slot/tag membership and all three resulting crouch floats; Depth Strider item/slot/tag membership and three legal water-efficiency values; Soul Speed item/slot/tag membership, movement-efficiency value, and movement-speed modifier values. The relevant packet receivers and attribute registrations are verified.
- **Still a movement difference:** Depth Strider II's old and new water-travel expressions are not arithmetically equivalent even with the corresponding valid same-enchantment/effective-attribute mapping. That finding remains separately accepted.
- **Not claimed:** custom datapack/resource-pack overlays, arbitrary non-vanilla modifiers, server attribute production or update timing, or equality when the two clients have unequal effective attributes/tags. No tests, builds, launches, source generation/decompilation, or network lookups were used.

No further eligible modifier/equipment input dependency remains open inside this bounded equal-effective-state scope.
