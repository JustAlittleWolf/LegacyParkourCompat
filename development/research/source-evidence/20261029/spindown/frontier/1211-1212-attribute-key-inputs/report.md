# 1.21.1 → 1.21.2 movement-attribute key and client-update closure

## Bounded verdict

The three selected movement attributes keep the same Java field names, numeric defaults/ranges, syncability, registration helper, local-player value consumers, and client attribute-update behavior. Their built-in registry keys change from `minecraft:generic.*` to `minecraft:*`: gravity, movement speed, and water movement efficiency. For the same attribute holders/state and equal effective numeric values, the key rename alone introduces no local-player movement calculation change in the checked consumers.

This is a source-only closure for those three attribute identities and their direct client receiver path. It does not establish cross-version packet or registry compatibility, compatibility of data packs/resource overrides using the old keys, source production of equal attribute packets, or pair-wide movement coverage.

## Exact source identity

Canonical roots are read-only `D:/Javastuff/LegacyParkourCompat/decompiled_minecraft/1.21.1/mojmap/` and `.../1.21.2/mojmap/`, both Mojmap.

- 1.21.1 parent records rehashed: `mojmap.ready.json` SHA-256 `988b38ac59491f9430d5bd1968db0fdaacb777a63fef745ca3e20342237a0310`; `mojmap.provenance.json` `6f84a596ea944a1d40c79b53ead9d2cdd2b3c726d710a977758d15b5578ce7a9`; `mojmap.sources.sha256` `900f956e00f6fc1300bb3d689ea49df2b1a57bcaa54617344ef456d95d47cb48`; `artifacts.sha256` `09ced418cbc7530a1d6d8802ee10c05cd576b217a2129655a71f30a2ae38f486`. The ready record names 1.21.1 / mojmap / `ready` and 5,363 source files. All selected source hashes below match its parent source sidecar.
- 1.21.2 has no parent readiness/provenance sidecar used here. The mapping-root `.decompile-provenance.json` exists, SHA-256 `00f399407dc447737ea83e3e46562418f8c712f66e3f51cb85d46afa2ecac575`, and its embedded source map has 5,654 paths. It pins metadata URL `https://piston-meta.mojang.com/v1/packages/c14db17c663edf3d7a698488263a21b1df6d02af/1.21.2.json` (SHA-256 `793e3fd0451354a26aaa00253dcb6600add11f9d2a90f7bdef75ddf5b38c2b45`), client jar `https://piston-data.mojang.com/v1/objects/c7ac2d0d86f4ca416cab9064ff8a281852ad0c7b/client.jar` (`613091da0f62426ae8dcf9afb30eb639485f493b81cda3a2b96b1fb8a6b9976c`), and Mojmap `client.txt` `https://piston-data.mojang.com/v1/objects/ce0ed2eb99175df9ec5166324e3fdd1f87cee827/client.txt` (`e37560e4889168916a73dbc4922c563002eff70fbecfe1cf5c50a7b222f451b2`). Each selected source hash below matches that embedded map.

Selected complete source-file hashes (1.21.1 → 1.21.2):

| Source | 1.21.1 SHA-256 | 1.21.2 SHA-256 |
|---|---|---|
| `world/entity/ai/attributes/Attributes.java` | `a9a19f556bb77fc218b5b2f4831eb6b285f4398c24fa2a3519009ca8dad2db7c` | `14bcc5821d0f67f41fdc3e512556181823cff5c43f84fc1007e51d25f413a3ea` |
| `world/entity/ai/attributes/Attribute.java` | `16e9b68e7c7b04a8cc32f6bc76e042e61c3b49f6cdf27ad7f878b12f430c52db` | `16e9b68e7c7b04a8cc32f6bc76e042e61c3b49f6cdf27ad7f878b12f430c52db` |
| `world/entity/ai/attributes/AttributeInstance.java` | `16a79843c53aabf5ae34b3230596995c70932197223482ad998085d6031ffee0` | `2a5ed44096b45b3b050a8df947cd44c788e6a35d51eb81d4c023f331f6f62870` |
| `world/entity/ai/attributes/AttributeMap.java` | `91bae9479619334d3f7adc4e2216db73a74ab9a09d4e6f4e1b535d132e512db5` | `2df2650d77076950b33ac682bda0ba0974ecbb8f8bf41179064156a4c68b1a68` |
| `world/entity/ai/attributes/AttributeSupplier.java` | `656206fad73103cb34645ad2c51c3bd364829855b6faa43d2440d8f25bdde54e` | `656206fad73103cb34645ad2c51c3bd364829855b6faa43d2440d8f25bdde54e` |
| `world/entity/LivingEntity.java` | `324a3eee8496caab57cfaf5101ef576f1ae3c60c40e3857e96f35f3af9a3a0d8` | `087390495f4fdfd14b9e12230e7aea4fdc50913bb4d1d91119e72892881cfc52` |
| `world/entity/player/Player.java` | `ed32b88c3c7c8418b83db41823520f2b6b0b49a98610306ef26e9681dc925c71` | `a803203e92aa4729d5f5c9b16085b6a43ce51d9907d309eb96736e9c7c1340de` |
| `client/multiplayer/ClientPacketListener.java` | `822e00d89e8def4cf7a8c22fdfefba3b9dc2135a7630e4ebe561a9d547d7f79d` | `169e1edaf666ebeb4ad736323565e28935a247748fecb67333dd5db53f24fb8a` |
| `network/protocol/game/ClientboundUpdateAttributesPacket.java` | `a62a937338270df1ce0ec2b195fdbefcc5bc35b3db31bfcdd868baa1e27c1fdf` | `a62a937338270df1ce0ec2b195fdbefcc5bc35b3db31bfcdd868baa1e27c1fdf` |
| `core/registries/BuiltInRegistries.java` | `90768bac1cdbc2a060a24b5ad859b654055eb5591ee13355f8fd374a802dd734` | `811f6e83830c0f48a3771bb175f6f99e382c648ba14b3bc9f985aa597a5ce86b` |
| `resources/ResourceLocation.java` | `49fcbf4b31aff6a4a5fb3c5b7baabaa6c47f3c3e85d82832c8d50e91fe625296` | `49fcbf4b31aff6a4a5fb3c5b7baabaa6c47f3c3e85d82832c8d50e91fe625296` |

## Registry identities and values

`Attributes.java` retains the same `GRAVITY`, `MOVEMENT_SPEED`, and `WATER_MOVEMENT_EFFICIENCY` holder fields. Both versions pass their string to the identical helper `register`, which calls `Registry.registerForHolder(BuiltInRegistries.ATTRIBUTE, ResourceLocation.withDefaultNamespace(id), attribute)`. `ResourceLocation.withDefaultNamespace` is identical and uses namespace `minecraft`.

| Holder | 1.21.1 built-in key | 1.21.2 built-in key | Attribute default, range, sync |
|---|---|---|---|
| `Attributes.GRAVITY` | `minecraft:generic.gravity` | `minecraft:gravity` | `0.08`, `[-1.0, 1.0]`, syncable, neutral sentiment |
| `Attributes.MOVEMENT_SPEED` | `minecraft:generic.movement_speed` | `minecraft:movement_speed` | `0.7`, `[0.0, 1024.0]`, syncable |
| `Attributes.WATER_MOVEMENT_EFFICIENCY` | `minecraft:generic.water_movement_efficiency` | `minecraft:water_movement_efficiency` | `0.0`, `[0.0, 1.0]`, syncable |

The corresponding description IDs also drop `generic.`. No compatibility alias is declared in these Java registration sources. `BuiltInRegistries.ATTRIBUTE` still registers via `registerSimple(Registries.ATTRIBUTE, Attributes::bootstrap)`; its declaration path is unchanged. `LivingEntity.createLivingAttributes` includes the same three holders in both versions. `Player.createAttributes` overrides the generic movement-speed default with the same `0.1F` player base in both versions. These definitions preserve numeric behavior for a given effective attribute value; the registry identity differs.

## Local-player consumers and update path

- `LivingEntity.getAttributeValue(holder)` delegates to `AttributeMap.getValue(holder)` in both versions. `AttributeMap.getInstance(holder)` creates/gets the instance by that holder; `getValue` reads the instance value or the supplier value when there is no materialized instance. The checked bodies are identical.
- Gravity reaches `LivingEntity.getDefaultGravity()` through `getAttributeValue(Attributes.GRAVITY)` in both versions, then `Entity.getGravity()` applies the unchanged no-gravity gate. 1.21.2 factors slow-falling selection through `getEffectiveGravity`; that travel refactor is outside this key-identity assignment.
- Player movement speed uses `Player.getSpeed()` → `(float)getAttributeValue(Attributes.MOVEMENT_SPEED)` in both versions. `Player.readAdditionalSaveData` sets the base from `abilities.getWalkingSpeed()` in both versions. Water movement efficiency is read from `Attributes.WATER_MOVEMENT_EFFICIENCY` in the water travel branch and cast to `float`; no other caller is needed to identify this key's movement consumer here.
- `AttributeInstance.calculateValue()` is the same ordered calculation: start from base; add `ADD_VALUE` modifiers; add each `ADD_MULTIPLIED_BASE` amount times that base; multiply by each `1 + ADD_MULTIPLIED_TOTAL`; sanitize through the attribute. Therefore the same base and modifier values produce the same effective numeric value for these attributes. The two files differ overall in 1.21.2 due to other attribute API additions; the checked calculation bodies match.
- `ClientboundUpdateAttributesPacket` is byte-identical across the pair. Its snapshot stores `Holder<Attribute>`, a `double` base and modifiers; `Attribute.STREAM_CODEC` is also byte-identical and uses `ByteBufCodecs.holderRegistry(Registries.ATTRIBUTE)`. The packet codec binds the snapshot to the receiving version's attribute registry; this source check does not claim equal raw packet bytes or registry IDs between versions.
- `ClientPacketListener.handleUpdateAttributes` has the same body at 1.21.1 and 1.21.2. It checks the client thread, looks up the entity ID, rejects a non-living target, then for each snapshot obtains the local `AttributeInstance`; a missing instance logs a warning, otherwise it sets the base, removes existing modifiers, and adds the received modifiers as transient. The path is generic for living entities; when the entity ID identifies the local player, it applies through the same operations. `AttributeMap` dirties/queues the same instance on a base/modifier update, and `LivingEntity.refreshDirtyAttributes` has no special branch for these three holders (its checked callback only clamps health/absorption attributes).

The changed keys are thus registry/data identities, not a changed client movement arithmetic or update-handler rule under equal decoded local attribute state. The source evidence does not establish that old and new servers/resources produce or decode equal holders or values, nor whether data packs or other registry consumers that name the old keys need aliases.

## Scope and checks

Checked exact source: the three `Attributes` definitions and common `register`; `ResourceLocation.withDefaultNamespace`; `BuiltInRegistries.ATTRIBUTE`; `LivingEntity.createLivingAttributes`, `getAttributeValue`, `getDefaultGravity`, `refreshDirtyAttributes`/`onAttributeUpdated`, and the water-efficiency read; `Player.createAttributes`, `getSpeed`, and saved ability walking-speed assignment; `Attribute.STREAM_CODEC`; `AttributeInstance.setBaseValue`, modifier replacement/removal, and `calculateValue`; `AttributeMap.getInstance`, `getValue`, and dirty callback; `ClientboundUpdateAttributesPacket` codec/snapshot; `ClientPacketListener.handleUpdateAttributes`. Eleven selected source paths per side were rehashed against the exact parent/embedded source maps; all matched. No product, wiki, history, tests, build, server or runtime was read or run. No pair-wide or parity claim is made.
