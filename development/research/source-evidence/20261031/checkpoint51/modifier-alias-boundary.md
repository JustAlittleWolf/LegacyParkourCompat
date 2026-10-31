# Applicability review: legacy modifier accumulation through 1.20.4

## Verdict

The accepted 1.20.4 → 1.20.5 accumulation-order witness also applies to valid equal legacy movement-speed modifier inputs in 1.20.2 and 1.20.3: all three older clients use the same `AttributeInstance` byte-for-byte, with operation buckets backed by Java `HashSet`s. I found no earlier cutover in these reviewed releases. This does not locate the precise intermediate patch boundary or prove a downstream trajectory.

## Immutable inputs and source identity

Reviewed candidate SHA-256: `757fba7173822a3ecc903a1755d17130f618dff4e514ba8a69a7598931d61a57` (`.native-source/20261030/1204-1206-pool44/legacy-item-order-witness/candidate.md`). Prior independent review SHA-256: `924b79822333348fb0d884318fa53a9d4cea9ca74b2c7e0a6d9a770f5d5acc6f`.

Exact read-only sources are under `D:/Javastuff/LegacyParkourCompat/decompiled_minecraft/<version>/mojmap`.

- 1.20.2: ready marker SHA-256 `5ff3322f287c250ee914c7ca3840d053bffc3c2c5f35e91b59314d948292483f`; `mojmap.sources.sha256` SHA-256 `e439ff7e1d3f1d31e01be1edd141f94d917a9dc7fa0de5f69904544cbbe62887` (also bound by ready marker).
- 1.20.3: no sibling ready marker or source-manifest sidecar exists in either checked source/parent layout. The embedded `.decompile-provenance.json` provides the per-file source hash map; its SHA-256 is `c3d605e55b287e800a86af8567a75edcb009d1a9a1744cfcb4e6f47667dcee0f`. Recomputed relevant file hashes match those embedded entries below. This is weaker publication metadata than the adjacent ready+manifest pair, but the exact local files are independently hash-bound by the embedded manifest.
- 1.20.4: ready marker SHA-256 `6225b97979420cc743c8b7156a0ce512ef27d0dbf48bfffbc40d3e1c8cb044d1`; `mojmap.sources.sha256` SHA-256 `fd3c8668483e5ff208c847474f6cbd3952a909c4aa602ed98b24e3d11a9602a1`.

Recomputed source hashes (all matched the stated sidecar/embedded manifests):

| Release | File | SHA-256 |
|---|---|---|
| 1.20.2 | `world/entity/ai/attributes/AttributeInstance.java` | `f78cd38b3fe74f98950ecc0fe4ae420f99299dc5bb43360e4e6b3c76b3509672` |
| 1.20.2 | `world/entity/ai/attributes/AttributeModifier.java` | `28730cb992839fb447880f30fc88f8a99dc95d4ef84daeb6783424128991382e` |
| 1.20.2 | `world/item/ItemStack.java` | `93c5054d96fae740977a9c70fc23cbcc4e2bec50ad1c656a18911c6591cfc70e` |
| 1.20.2 | `world/entity/LivingEntity.java` | `5c481da1ffc8684c4b30171c92e61fad751e6a3a708d5bc6486ac9f96ff69828` |
| 1.20.2 | `world/entity/player/Player.java` | `25f263692fd6b2a737aa813315022a0caf4e18c3661ba3bd706c6297f909f25c` |
| 1.20.2 | `network/protocol/game/ClientboundUpdateAttributesPacket.java` | `2db489f1121f062a5756c4371a824b7fdbf54d5b2c9c8647e539235b48d35d1e` |
| 1.20.2 | `client/multiplayer/ClientPacketListener.java` | `0208f6942035adaf2e787546851ca296f0e7ec2179d3610f694911eda5283264` |
| 1.20.3 | `world/entity/ai/attributes/AttributeInstance.java` | `f78cd38b3fe74f98950ecc0fe4ae420f99299dc5bb43360e4e6b3c76b3509672` |
| 1.20.3 | `world/entity/ai/attributes/AttributeModifier.java` | `f6a16d77b7620e72756bc079ba3aade4fc3da6a3763c1af8fe21320f89551100` |
| 1.20.3 | `world/item/ItemStack.java` | `0e0b2a4973ee41e042a9ee6158ffdd4a366676f90019cb2c7d236de7bc809710` |
| 1.20.3 | `world/entity/LivingEntity.java` | `f7bc53db24c1798f19f9bd6f6356c86d5e560e9c8e8aac60decaf15cc785e07d` |
| 1.20.3 | `world/entity/player/Player.java` | `218da60bc4f8c9279f56eb87e2cf2c0af79562fbe3d818a408429c656efb95dd` |
| 1.20.3 | `network/protocol/game/ClientboundUpdateAttributesPacket.java` | `2db489f1121f062a5756c4371a824b7fdbf54d5b2c9c8647e539235b48d35d1e` |
| 1.20.3 | `client/multiplayer/ClientPacketListener.java` | `ff9c8222614551075b03454ee78712b0d39f0f51845e74bff76a81486f09b42f` |
| 1.20.4 | `world/entity/ai/attributes/AttributeInstance.java` | `f78cd38b3fe74f98950ecc0fe4ae420f99299dc5bb43360e4e6b3c76b3509672` |
| 1.20.4 | `world/entity/ai/attributes/AttributeModifier.java` | `f6a16d77b7620e72756bc079ba3aade4fc3da6a3763c1af8fe21320f89551100` |
| 1.20.4 | `world/item/ItemStack.java` | `0e0b2a4973ee41e042a9ee6158ffdd4a366676f90019cb2c7d236de7bc809710` |
| 1.20.4 | `world/entity/LivingEntity.java` | `f7bc53db24c1798f19f9bd6f6356c86d5e560e9c8e8aac60decaf15cc785e07d` |
| 1.20.4 | `world/entity/player/Player.java` | `218da60bc4f8c9279f56eb87e2cf2c0af79562fbe3d818a408429c656efb95dd` |
| 1.20.4 | `network/protocol/game/ClientboundUpdateAttributesPacket.java` | `2db489f1121f062a5756c4371a824b7fdbf54d5b2c9c8647e539235b48d35d1e` |
| 1.20.4 | `client/multiplayer/ClientPacketListener.java` | `ff9c8222614551075b03454ee78712b0d39f0f51845e74bff76a81486f09b42f` |

## Applicability evidence

- **Accumulation operation and order:** In all three releases, `AttributeInstance.java:21` declares `Map<Operation, Set<AttributeModifier>>`; `:50–52` uses `Sets.newHashSet()`; `:130–147` (1.20.2/1.20.4) multiplies the running `double` sequentially for `MULTIPLY_TOTAL` then sanitizes. The full file hash is identical across 1.20.2, 1.20.3 and 1.20.4. `AttributeModifier.hashCode()` uses its UUID hash (`1.20.2 AttributeModifier.java:64–65`; 1.20.4 `:66–67`), and equality is by UUID. Therefore the witness UUIDs (hashes 1 and 2) occupy separate ordinary HashSet buckets and iterate A then B in all these releases, independent of packet or equipment insertion order.
- **Equipment reconstruction:** 1.20.2 `ItemStack.getAttributeModifiers(EquipmentSlot)` (`ItemStack.java:856–879`) reads the raw `AttributeModifiers` list, resolves `AttributeName`, loads UUID/name/amount/operation, enforces the nonzero UUID halves, filters `Slot`, and returns both valid entries. The writer at `:881–895` creates the same legacy list form. `LivingEntity` equipment application calls `getAttributeModifiers(slot)` then `addTransientAttributeModifiers` (`LivingEntity.java:2434`; `AttributeMap.java:104–110`), which calls `addTransientModifier` for each item modifier. In 1.20.3 the `ItemStack` and `LivingEntity` source hashes equal 1.20.4 exactly; 1.20.2’s files differ elsewhere, while these legacy parse/write and equipment-application paths retain the same behavior.
- **Raw attribute packet path:** 1.20.2 `ClientboundUpdateAttributesPacket` snapshots attribute base plus modifiers (`:18–23`), serializes each modifier’s UUID, amount and operation (`:45–53`), and reconstructs those values on decode (`:27–38`). `ClientPacketListener.handleUpdateAttributes` applies each reconstructed modifier via `addTransientModifier` (`:1982–2002`). Since accumulation is recalculated in the client’s same UUID-keyed `HashSet`, equal valid raw modifier sets have the witnessed ordering without needing a particular producer or equipment-event timing.
- **Player use and casts:** 1.20.2 `Player.aiStep` stores `(float)getAttributeValue(MOVEMENT_SPEED)` (`Player.java:523`), and `Player.getSpeed()` returns the same cast (`:1493–1495`). These methods and their containing `Player.java` are byte-identical in 1.20.3 and 1.20.4. Thus the candidate’s relevant widened `0.1F` base, double multiply order and final float cast apply unchanged to these older releases.

For the concrete values, the old order remains A→B: final double `0.12469135597348215`, cast float `0x3dff5e2f`. Candidate 1.20.5 map order B→A gives double `0.12469135597348213`, cast `0x3dff5e2e`. Both are within the unchanged attribute bounds described in the reviewed candidate, so sanitation does not erase the one-ULP `Player.getSpeed()` difference. This establishes the modifier-speed input difference only.

No source was generated or changed, and no product code, backlog, wiki inventory or Git history was read. No tests/builds/launches were run.
