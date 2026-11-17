# ATTACK_KNOCKBACK Java default and LocalPlayer supplier path

## Bounded result

**No difference found** in the Java-defined `ATTACK_KNOCKBACK` registration, its presence in the player default-attribute supplier, or the base-value path used by a `LocalPlayer` between 26.1.2 and 26.2. This is a static default/supplier result only. It does not prove the runtime effective value after entity data, received state, or other modifier application, and it is not a movement or knockback-outcome claim.

## Exact source identities

Both endpoints are the published **unobfuscated** namespace. Version IDs and `namespace` values in each ready/provenance pair match the directory version and `unobfuscated` mapping. The ready marker's `sourceManifestSha256` matches the SHA-256 of the local source manifest; its `artifactManifestSha256` matches the artifact manifest hash.

| Release | Ready marker SHA-256 | Provenance SHA-256 | source manifest SHA-256 | artifact manifest SHA-256 |
|---|---|---|---|---|
| 26.1.2 | `f24af0f4d38543c2bd9c19b50f347d4e89d6027050e4b21ae070734843946bb7` | `8d727f880cc91f4a297e148c5b75976303d051457334bc689268fbdf631a012a` | `54ae320660ca911a6d20514c965c33eb414fbedd1ad6727bbaa95fe906aa23c0` | `89f648229de83b0109460695c9f1f3bed7ef36efb84ede9224e80cce87118f92` |
| 26.2 | `f9406adb6bf7cb4c1ab0792a798ab2cb90e082ad4c2eee032c9f674d0ea8070f` | `a328f5333a1b27aadde4422b23159a323ae219ccd26f11207c1a5461366f27d1` | `a7ad74fc712567eb87136afc1eafcfd602b4088e9869f3596331fd34faefe894` | `ba9dc53a41bca9744ec1c8ab2ce9363764a22333c39ef64d5105f4e3bf5c98ba` |

Ready/provenance/manifest sidecars are at `decompiled_minecraft/<version>/unobfuscated.ready.json`, `unobfuscated.provenance.json`, `unobfuscated.sources.sha256`, and `artifacts.sha256` (the source root itself is `decompiled_minecraft/<version>/unobfuscated/`). For every cited Java path below, its local SHA-256 matched the same relative path in `unobfuscated.sources.sha256`.

| Relative Java source | 26.1.2 SHA-256 | 26.2 SHA-256 |
|---|---|---|
| `net/minecraft/world/entity/ai/attributes/Attributes.java` | `8eee57d8375c7af39525ccd25593618fcbcbe0348da6a8738556baaabea3c1d5` | `4a7c33552f256b5d35c6d46fd5810405f4e98182e2b26a9a3009ef4f1d3fdd5c` |
| `net/minecraft/world/entity/ai/attributes/Attribute.java` | `854d1c12ab0b49a2136d5807db70676fe041671d40d44140cc2fda7c4ab4db59` | `854d1c12ab0b49a2136d5807db70676fe041671d40d44140cc2fda7c4ab4db59` |
| `net/minecraft/world/entity/ai/attributes/AttributeInstance.java` | `ca262f3d973642a2887b235a549b14f335706413f9551b417059e72e6bcf8cfd` | `c7cbe38bfdc1b4aafcf623bd1b604200cc6190641091369ee9bb9303e8f07ad5` |
| `net/minecraft/world/entity/ai/attributes/AttributeSupplier.java` | `bdcda07e8b5fe8bf7dbb2349d3c2776ff788ae31c2a94ebcdce26c63f84abf50` | `bdcda07e8b5fe8bf7dbb2349d3c2776ff788ae31c2a94ebcdce26c63f84abf50` |
| `net/minecraft/world/entity/ai/attributes/AttributeMap.java` | `675b87b1757a73c6354db2dcfe52ec65c903bd9b3f5fcb97a648be252ea3f7f6` | `675b87b1757a73c6354db2dcfe52ec65c903bd9b3f5fcb97a648be252ea3f7f6` |
| `net/minecraft/world/entity/ai/attributes/DefaultAttributes.java` | `8d68376eacb084b5acea9b8d2e413e698aa30bbb8c2b4088f56bbc088e959e3a` | `0e8d97f9af11fa17e585c56b66670a13e3fc368da21fb9a032f5fd2b05ab8d6e` |
| `net/minecraft/world/entity/LivingEntity.java` | `c3b64de8dbaba8ad8a7ccd4f33255346d8206e66f12ca91260971bf5e5ac93bd` | `7ffd9c70966edc50c9cb4d9a8fe17a518e2678ff44c8026e763d0b94ac0ae51a` |
| `net/minecraft/world/entity/player/Player.java` | `44cf28e0c64e78d39fd13368e9991381dbebab67029070cb9ddc43f09d45d14d` | `8decc71b9c780664578ddb14591db2a2f207c72c05b676edded6f8e964576531` |
| `net/minecraft/client/player/AbstractClientPlayer.java` | `7825a8d4e8e24928cba18f68109c91479b78fb14fcd8b81f0e045b8d02a559cc` | `7825a8d4e8e24928cba18f68109c91479b78fb14fcd8b81f0e045b8d02a559cc` |
| `net/minecraft/client/player/LocalPlayer.java` | `433fd995ad317af0f6ef0e50c1e8e3483cb8f00e0e327d4edf27a4dd99666ebe` | `8d089aa09217e3607b38590f7c1623385562800943ac6dfd3d17804e041da6d6` |

## Compared source path

- `net/minecraft/world/entity/ai/attributes/Attributes.java`, 26.1.2 line 15 and 26.2 line 18: both register `ATTACK_KNOCKBACK` as `RangedAttribute("attribute.name.attack_knockback", 0.0, 0.0, 5.0)`. Neither registration calls `setSyncable(true)`. `Attribute.syncable` has no explicit initializer and defaults false; `isClientSyncable()` returns that flag (`Attribute.java`, lines 15–35). No change to the declared default or range.
- `LivingEntity.createLivingAttributes()`, 26.1.2 lines 318–340 and 26.2 lines 332–354, includes `.add(Attributes.ATTACK_KNOCKBACK)` in both versions. `Player.createAttributes()`, lines 205–218 / 206–219, starts from that builder and adds player-specific attributes without adding an explicit `ATTACK_KNOCKBACK` base value. The nearby 26.2 additions to other living attributes do not replace or alter this entry.
- `DefaultAttributes` maps the player type to `Player.createAttributes().build()` in both: `EntityType.PLAYER` at 26.1.2 line 150 and `EntityTypes.PLAYER` at 26.2 line 153. The registry type spelling changed; the supplier call did not.
- `AttributeSupplier.Builder.add(attribute)` creates an `AttributeInstance` without calling `setBaseValue`. `AttributeInstance` initializes `baseValue` from `attribute.value().getDefaultValue()` (constructor lines 31–34 in both versions). `AttributeMap.getValue` returns the instance value when one exists, otherwise the supplier value (lines 62–65 in both versions). `LivingEntity` constructs its map from `DefaultAttributes.getSupplier(type)` (26.1.2 line 274; 26.2 line 288) and exposes `getAttributeValue` through that map (line 2173 / 2210).
- The client inheritance path is `LocalPlayer extends AbstractClientPlayer` (`LocalPlayer.java` line 108), `AbstractClientPlayer extends Player` (`AbstractClientPlayer.java` line 19), and the `Player` constructor passes the player entity type to the superclass (`Player.java` lines 174–175 / 175–176). Thus the source-declared `LocalPlayer` default supplier includes the same zero-default attribute in both endpoints.

## Scope and remaining gates

This establishes a Java default path of 0 for the client player when using the declared player supplier and no modifiers. It does not establish that a live player's effective value stays at 0: packet-applied attribute instances, equipment or other modifiers, entity data loading, and actual client/server state were not traced here. It does not close the previously separate packet/equipment gates or the pair's source coverage. `LivingEntity` reads this attribute in the attack knockback calculation (26.1.2 line 1512; 26.2 line 1541), but that attacker-side attack/damage operation is outside this bounded movement claim.

