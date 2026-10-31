# Legacy item modifier order witness

Pair: exact 1.20.4 → 1.20.5 Mojmap; 1.20.6 context uses the same relevant 1.20.5 source-file hashes. This is a bounded source/arithmetic candidate for the unresolved `AttributeInstance` Set→UUID-map route, not an independent acceptance verdict.

## Exact inputs and source identity

Sources are read-only at `D:/Javastuff/LegacyParkourCompat/decompiled_minecraft/<version>/mojmap`. The 1.20.4 source manifest is `fd3c8668483e5ff208c847474f6cbd3952a909c4aa602ed98b24e3d11a9602a1`; the 1.20.5 manifest is `162dac6c4f1d539a1c2cea0255b7938a5281c207ab460bdc265358016d732484`. The corresponding ready markers and provenance sidecars live beside (not inside) each `mojmap` directory. The 1.20.5 artifact manifest pins fastutil 8.5.12 at SHA-256 `b5543ee08d062d551cf0a5c9bc0fb70588b0382079029ba48941fa9c9be8a5d4`; that matches the locally cached jar used to inspect `Object2ObjectOpenHashMap` bytecode.

Relevant manifested source file SHA-256 values:

| Release | Mojmap relative source path | SHA-256 |
|---|---|---|
| 1.20.4 | `net/minecraft/world/entity/ai/attributes/AttributeInstance.java` | `f78cd38b3fe74f98950ecc0fe4ae420f99299dc5bb43360e4e6b3c76b3509672` |
| 1.20.4 | `net/minecraft/world/entity/ai/attributes/AttributeModifier.java` | `f6a16d77b7620e72756bc079ba3aade4fc3da6a3763c1af8fe21320f89551100` |
| 1.20.4 | `net/minecraft/world/item/ItemStack.java` | `0e0b2a4973ee41e042a9ee6158ffdd4a366676f90019cb2c7d236de7bc809710` |
| 1.20.4 | `net/minecraft/world/entity/player/Player.java` | `218da60bc4f8c9279f56eb87e2cf2c0af79562fbe3d818a408429c656efb95dd` |
| 1.20.5 | `net/minecraft/world/entity/ai/attributes/AttributeInstance.java` | `5d060c1740336835c74138d3c5fe3ae5f3b7f6a1c2f16f6844289f18ef90782a` |
| 1.20.5 | `net/minecraft/world/entity/ai/attributes/AttributeModifier.java` | `5acf5ec4d5a3361af6da18ec7c26ddbfdce6fa8dece778be7c20d345ca846c4c` |
| 1.20.5 | `net/minecraft/world/item/ItemStack.java` | `74a7e82a55577a299e25180bed9560c983ab0f620f72eaff0574a9b497e7f4e0` |
| 1.20.5 | `net/minecraft/world/item/component/ItemAttributeModifiers.java` | `10cabeaf004d50ceb67e6e7abe667f64faa17dd893216ab41425a58f4965a2b6` |
| 1.20.5 | `net/minecraft/util/datafix/fixes/ItemStackComponentizationFix.java` | `01508c563374a0759546e30428831a66751922c08429c6eed15452ecbfafb952` |
| 1.20.5 | `net/minecraft/world/entity/player/Player.java` | `785d93ccc94e1f912e545b2b0c355edeb352b44ee8e83a69364daec35266dbe2` |

## Source path and exact movement consequence

In 1.20.4 `ItemStack.getAttributeModifiers(EquipmentSlot)` (`ItemStack.java:888–911`) parses the `AttributeModifiers` NBT list, accepts a registered `AttributeName`, loads each UUID/name/double amount/operation, filters the requested slot, and adds the entry to a `HashMultimap`. `ItemStack.addAttributeModifier` (`:914–930`) writes such entries. `LivingEntity.collectEquipmentChanges` calls this accessor for changed equipment, and `AttributeInstance.addTransientAttributeModifiers` puts each modifier into its operation bucket.

The old `AttributeInstance` stores each operation bucket as `Set<AttributeModifier>` (`AttributeInstance.java:21, 50–52`). `AttributeModifier.equals` and `hashCode` use its UUID (`AttributeModifier.java:57–69`), so a bucket is a Java `HashSet` keyed by UUID hash. `calculateValue` (`:130–152`) multiplies the running base sequentially by `(1.0 + amount)` for each `MULTIPLY_TOTAL` member (`:143–145`).

In 1.20.5 the corresponding bucket is `Map<UUID, AttributeModifier>` initialized with `Object2ObjectOpenHashMap` (`AttributeInstance.java:21, 50–53`), and `calculateValue` iterates `Map.values()` in that same sequential multiply loop (`:140–161`). Exact 1.20.5 fastutil 8.5.12 bytecode shows a no-argument map uses expected size 16 / load factor 0.75 (32 slots); its iterator begins at `pos=n` and decrements through the table. `HashCommon.mix(int)` places the two UUID hashes below in distinct table slots, so the map iteration scans B then A. The 1.20.5 `ItemStackComponentizationFix.fixAttributeModifiers` (`:411–435`) converts nonempty old lists to component entries, preserving UUID/type/amount/slot and mapping operation 2 to `add_multiplied_total`. `ItemStack.forEachModifier` (`:883–890`) dispatches the component entries by slot; `ItemAttributeModifiers.forEach` (`:63–68`) visits them in list order. This produces the same two modifiers in the newer player's map.

`Player` initializes `MOVEMENT_SPEED` to `0.1F` (`Player.java:204–210` in 1.20.4; `:221–230` in 1.20.5). In ordinary `Player.aiStep` and `Player.getSpeed`, it reads the attribute and casts its sanitized value to `float` (`1.20.4 Player.java:524, 1489–1491`; corresponding 1.20.5 members are `:533, 1511–1513`). Both computed values below are inside the attribute's unchanged `[0, 1024]` bounds.

## Candidate legacy stack

Use a pair of valid `AttributeModifiers` entries on an otherwise ordinary pair of boots, scoped to `feet`, with no sprint/Speed/Slowness/Soul Speed/Powder Snow modifiers. The legacy list order is A then B:

| Entry | `AttributeName` | `Name` | `Amount` (NBT double) | `Operation` | UUID | `Slot` |
|---|---|---|---:|---:|---|---|
| A | `minecraft:generic.movement_speed` | `order-a` | `0.01` | `2` (`MULTIPLY_TOTAL`) | `00000000-0000-0003-0000-000000000002` | `feet` |
| B | `minecraft:generic.movement_speed` | `order-b` | `0.23456786252907091` | `2` (`MULTIPLY_TOTAL`) | `00000000-0000-0003-0000-000000000001` | `feet` |

Both UUID halves are nonzero, the IDs are distinct, and the movement-speed attribute is registered in 1.20.4. The old and new stack paths preserve these modifiers. UUID Java hash codes are `1` for A (`3 XOR 2`) and `2` for B (`3 XOR 1`). With two entries, old HashSet bucket positions are A=1 and B=2 in the 16-slot table, so `HashSet` iteration is A then B regardless of list insertion order. Fastutil's `HashCommon.mix` gives new map slots A=14 and B=28 in its 32-slot table; its descending table iterator returns B then A. There are no hash collisions, overwrites, rehashes, or built-in modifier IDs in this scenario.

The same Java-IEEE-754 double sequence starting from the actual widened float base `0.1F = 0.10000000149011612` gives:

| Release/container iteration | Sequential double result | `Player.getSpeed()` float |
|---|---:|---:|
| 1.20.4 A then B | `0.12469135597348215` | `0.12469135969877243` (`0x3dff5e2f`) |
| 1.20.5 B then A | `0.12469135597348213` | `0.12469135224819183` (`0x3dff5e2e`) |

Thus this is a concrete, one-float-ULP Player movement-speed input witness if the map's unchanged values are traversed by the exact 8.5.12 open-map iteration behavior as recorded in its artifact identity. 1.20.6's relevant 1.20.5 Java sources and artifact identity were not newly rehashed in this bounded witness report; the main pair inventory records 1.20.5/1.20.6 `AttributeInstance.java` and `ItemStack.java` byte-identical.

## Scope note and review boundary

The modifier syntax and `ItemStack.addAttributeModifier` feature predate 1.20.4; no modded item class or new attribute is required. This is a custom stack modifier value set, but it directly supplies an ordinary registered player movement attribute and is not attack/damage, health/food production, a server-side movement path, or a datapack/tag override. Treating this input as in scope follows the repository scope; that scope judgment should still receive the independent pair auditor's review. This report supplies a candidate source-backed difference, not acceptance. No product/wiki/backlog/history reads, builds, tests, launches, or source mutations were performed.
