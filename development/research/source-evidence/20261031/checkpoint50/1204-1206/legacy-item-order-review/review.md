# Independent review: legacy movement-speed item modifier ordering

## Verdict

**ACCEPT** the bounded source/arithmetic candidate at `legacy-item-order-witness/candidate.md`, SHA-256 `757FBA7173822A3ECC903A1755D17130F618DFF4E514BA8A69A7598931D61A57`. The two legacy boots modifiers form a valid, player-reachable movement-speed input. Under the exact container iteration orders, `Player.getSpeed()` differs by one binary32 ULP between 1.20.4 and 1.20.5. This accepts only this concrete 1.20.4→1.20.5 source delta; it does not freeze the whole pair or establish a trajectory result.

## Source identities checked

The release manifests are siblings of each `mojmap` source tree. Every named source file below was recomputed and matched its exact manifest entry.

| Version | File | SHA-256 |
|---|---|---|
| 1.20.4 | `net/minecraft/world/entity/ai/attributes/AttributeInstance.java` | `f78cd38b3fe74f98950ecc0fe4ae420f99299dc5bb43360e4e6b3c76b3509672` |
| 1.20.4 | `net/minecraft/world/entity/ai/attributes/AttributeModifier.java` | `f6a16d77b7620e72756bc079ba3aade4fc3da6a3763c1af8fe21320f89551100` |
| 1.20.4 | `net/minecraft/world/item/ItemStack.java` | `0e0b2a4973ee41e042a9ee6158ffdd4a366676f90019cb2c7d236de7bc809710` |
| 1.20.4 | `net/minecraft/world/entity/player/Player.java` | `218da60bc4f8c9279f56eb87e2cf2c0af79562fbe3d818a408429c656efb95dd` |
| 1.20.4 | `net/minecraft/world/entity/ai/attributes/Attributes.java` | `287dcd477ccca2aebd24ad28db7b0e7a1e9dc9bbeb272d330cd15504b8669ff7` |
| 1.20.5 | `net/minecraft/world/entity/ai/attributes/AttributeInstance.java` | `5d060c1740336835c74138d3c5fe3ae5f3b7f6a1c2f16f6844289f18ef90782a` |
| 1.20.5 | `net/minecraft/world/entity/ai/attributes/AttributeModifier.java` | `5acf5ec4d5a3361af6da18ec7c26ddbfdce6fa8dece778be7c20d345ca846c4c` |
| 1.20.5 | `net/minecraft/world/item/ItemStack.java` | `74a7e82a55577a299e25180bed9560c983ab0f620f72eaff0574a9b497e7f4e0` |
| 1.20.5 | `net/minecraft/world/item/component/ItemAttributeModifiers.java` | `10cabeaf004d50ceb67e6e7abe667f64faa17dd893216ab41425a58f4965a2b6` |
| 1.20.5 | `net/minecraft/util/datafix/fixes/ItemStackComponentizationFix.java` | `01508c563374a0759546e30428831a66751922c08429c6eed15452ecbfafb952` |
| 1.20.5 | `net/minecraft/util/datafix/DataFixers.java` | `bb53e2573deeb6f579e909c784ac1759369762d95429eb73ff329184f8d76c81` |
| 1.20.5 | `net/minecraft/util/datafix/fixes/AttributesRename.java` | `be5f0c7f5afed628b5d4a26caba6f7eda10eb85bca115a2e2e270562bf9185d8` |
| 1.20.5 | `net/minecraft/world/entity/ai/attributes/Attributes.java` | `7e30d87c7a58b14d0052d2f9f7319d997b49ae7d025579cd762d28e845b2e82e` |
| 1.20.5 | `net/minecraft/world/entity/EquipmentSlotGroup.java` | `4f82e183ac94fc83ad3a1acf43e2e1f23457d2bd0b8a9e37631b237ff1eeb546` |
| 1.20.5 | `net/minecraft/world/entity/player/Player.java` | `785d93ccc94e1f912e545b2b0c355edeb352b44ee8e83a69364daec35266dbe2` |

The 1.20.5 artifacts manifest pins fastutil 8.5.12 as `b5543ee08d062d551cf0a5c9bc0fb70588b0382079029ba48941fa9c9be8a5d4`; the cached jar used for independent bytecode inspection has that same SHA-256. In 1.20.6, the relevant `AttributeInstance`, `ItemStack`, `ItemAttributeModifiers`, and `Player` files have the same hashes as 1.20.5.

## Review details

- **Old item input is valid and reaches the player attribute.** In 1.20.4, `Attributes.MOVEMENT_SPEED` is registered as `generic.movement_speed`. `ItemStack.getAttributeModifiers(EquipmentSlot)` accepts the namespaced registry key, loads the NBT UUID/name/double amount/operation, rejects only invalid modifier data or UUIDs with a zero half, and filters the `feet` slot. The proposed UUIDs have nonzero most- and least-significant halves. `Operation=2` is `MULTIPLY_TOTAL`. `ItemStack.addAttributeModifier` writes the same NBT entry shape. The componentization datafix maps the two fields into the 1.20.5 component; `EquipmentSlotGroup.FEET` accepts `feet`, and its per-entry codec uses the registered attribute holder plus the modifier map codec.
- **The speed attribute ID survives the intervening datafix.** The 1.20.4 item writer uses the canonical key string `minecraft:generic.movement_speed`. The earlier 1.20.5 `AttributesRename` fix is registered at schema 2523 and uses a direct string-key map for legacy spellings such as `generic.movementSpeed` and `Movement Speed`; it leaves `minecraft:generic.movement_speed` unchanged. `ItemStackComponentizationFix` is registered later at schema 3818.5. The 1.20.5 `Attributes.MOVEMENT_SPEED` registration uses `generic.movement_speed` through the namespaced registry, so the copied `type` resolves to that same registered holder.
- **Legacy list conversion is present.** `ItemStackComponentizationFix.fixAttributeModifiers` removes `AttributeModifiers`, copies `AttributeName`, `UUID`, `Name`, `Amount`, and `Slot`, and maps operation 2 to `add_multiplied_total`. With two entries the modifier component is nonempty and is emitted. `ItemStack.forEachModifier` enumerates those entries for a matching slot; `LivingEntity.collectEquipmentChanges` applies them to the Player's `MOVEMENT_SPEED` attribute.
- **Container orders check out.** Old `AttributeInstance.getModifiers(MULTIPLY_TOTAL)` creates a Java `HashSet`; `AttributeModifier.hashCode()` delegates to UUID hash code. For the proposed UUIDs A and B, hashes are 1 and 2, so two-entry default-table `HashSet` iteration is A→B. The 1.20.5 bucket is `Object2ObjectOpenHashMap`, whose default constructor is expected size 16 / load factor 0.75; bytecode for the artifact-pinned fastutil 8.5.12 establishes a 32-slot table and a descending value iterator. `HashCommon.mix(1) & 31` is 14 and `HashCommon.mix(2) & 31` is 28; these distinct, empty slots receive A and B without collision or rehash. Descending iteration yields B→A. This order is independent of legacy list order after application in the old `HashSet`, and is the actual new-map iteration order for the two map keys.
- **Arithmetic and cast check.** The source formulas multiply the running double by `(1.0 + amount)` in iteration order, sanitize against the unchanged `[0,1024]` attribute range, and `Player.getSpeed()` casts to `float`. Starting with the widened actual Player base `0.1F = 0.10000000149011612`, the source-order double results are `0.12469135597348215` for A→B and `0.12469135597348213` for B→A. Binary32 casts are `0x3dff5e2f` (`0.12469135969877243`) and `0x3dff5e2e` (`0.12469135224819183`), respectively. Both are within the attribute range, so sanitization does not merge them. This reproduces the candidate arithmetic independently using IEEE-754 binary64 operations and binary32 casts.

## Limits

The scope judgment is supported: this uses the vanilla item attribute-modifier feature available in 1.20.4 and a registered movement attribute, with no modded item type, modern-only attribute, runtime tag override, server movement physics or damage result. The report shows a changed movement-speed value, not a measured downstream displacement. Exact introduction within 1.20.4→1.20.5 remains unknown, and this review does not freeze the whole pair. No product, wiki, backlog or Git history was read. No build, test, game or server launch was run.
