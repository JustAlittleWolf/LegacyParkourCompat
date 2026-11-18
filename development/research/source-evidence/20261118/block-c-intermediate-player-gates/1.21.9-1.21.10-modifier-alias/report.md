# 1.21.9 → 1.21.10 modifier and movement-attribute source audit

## Scope and result

Compared exact Mojmap Java sources for the assigned slice: ordered status-effect modifier attach/replace/removal; equipped item modifier attach/removal; player and inherited living-entity movement-attribute suppliers; and direct player movement-attribute consumers. **No change in this slice**: all named source files have identical SHA-256 contents across the pair. The changed source hashes elsewhere in the two manifests do not alter this modifier/consumer result. This is bounded source coverage, not a full-pair movement freeze or a claim about an intermediate patch boundary.

## Source identity and integrity

- Older tree: `decompiled_minecraft/1.21.9/mojmap/`, Mojmap. The hidden `decompiled_minecraft/1.21.9/mojmap/.decompile-provenance.json` contains 6,386 per-source SHA-256 entries; its SHA-256 is `37d3049478585a43ffcace14fc8f2ee814b35ceedd8c9bad8b60adaed9b72c4f`. Its bound inputs identify `1.21.9.json` (`3cca801f41f83059092c88f7de3ea74247da4c394f3e672b7a41451151c293d5`), the Mojang `client.txt` (`f223e9775abb66b6712c505cb1a73b48d615efebaf43a9f81dd58df9a9b1c7b3`), and client jar (`40c0a32c46601f71f6a61dae9f126202ded5cc453cfb8da6e42ae73f2a3f5c1b`). I verified the embedded source hashes for every Java path listed below against the files. No adjacent `.ready.json` or source-manifest file exists for this tree, so the per-file provenance is the available local integrity record.
- Newer tree: `decompiled_minecraft/1.21.10/mojmap/`, Mojmap. Adjacent `decompiled_minecraft/1.21.10/mojmap.ready.json` reports ready, version `1.21.10`, 6,386 sources, and client SHA-256 `e65ca028bc58da12bf8413066c90ecd4d48c94fb351d9bbd22ecdff5c87f9482`. The marker SHA-256 is `2374d901074b72cd69f0229381d6a11edea7983613c538a2e2e18d9fc73dbeb0`; its source-manifest SHA-256 is `4be26049350c1b314a0b198022cb1e7ab1e745e104047de7a5c7d099a9d7b7e1`, matching `mojmap.sources.sha256`. The newer manifest and older provenance map each contain 6,386 paths, with no added or removed Java paths and 34 changed source hashes overall.
- In both trees, no packaged `data/` files are present under the canonical decompiled source directory. Therefore the directly relevant packaged enchantment defaults cannot be compared from available canonical data; none were downloaded.

## Compared operations and source facts

### Effect modifiers

- `net/minecraft/world/effect/MobEffect.java:135-137,151-170,197-200`: `addAttributeModifier` stores one `AttributeTemplate` per attribute holder. `createModifiers` iterates that map. Removal checks `AttributeMap.getInstance` and removes the template ID if an instance exists. Application checks the same gate, removes the old ID, then adds a permanent modifier created for the current amplifier. The template amount is the `double` expression `amount * (amplifier + 1)`; operation and ID come from the template. The table is an `Object2ObjectOpenHashMap`, so source does not promise an iteration order across different attribute keys.
- `net/minecraft/world/entity/LivingEntity.java:828-837,1041-1096`: on server-side effect ticking, expiration removes the active entry and then calls `onEffectsRemoved`. A newly installed effect calls `onEffectAdded`; a changed instance calls `onEffectUpdated`. The add, update and remove callbacks all gate attribute-map mutation on `!level().isClientSide()`. Updates replace attributes only when their boolean change flag is true, in remove-then-add order, and refresh dirty attributes afterward. Expiring/removing multiple effects removes each effect's modifiers in collection order and refreshes after the loop. These methods are byte-identical between the pair.
- `net/minecraft/world/entity/ai/attributes/AttributeInstance.java:75-132`: ordinary add rejects an already-present ID; `addOrUpdateTransientModifier` uses `put` then tests object identity (`newModifier != previousModifier`) before writing to the operation map and marking dirty; `addOrReplacePermanentModifier` removes first and then adds; removal returns false for an absent ID and otherwise removes from the ID map, operation map and permanent map before marking dirty. No operation, cast, float or gate changed.
- Effect defaults/amounts are not established here: canonical Java source does not contain packaged `data/` resources.

### Equipped modifiers

- `net/minecraft/world/entity/LivingEntity.java:2670-2705`: equipment slots are traversed in `EquipmentSlot.VALUES` order. A slot is processed only when `equipmentHasChanged` is true (`2708-2709`, `!ItemStack.matches(new, old)`). A nonempty old stack has location-based effects stopped first (`2680-2682`). The replacement is attached only when nonempty and not broken (`2690`); for each modifier whose attribute instance exists, the old modifier ID is removed before the new modifier is added transiently (`2691-2696`).
- `net/minecraft/world/entity/LivingEntity.java:3548-3561`: breaking an equipped item stops its location-based effects; each existing attribute instance loses the matching modifier, then the enchantment helper is called. `net/minecraft/world/item/ItemStack.java:968-978` supplies component modifiers first and then enchantment modifiers for the requested slot. These paths are byte-identical in both releases.

### Suppliers and direct player movement consumers

- Defaults: `net/minecraft/world/entity/ai/attributes/Attributes.java:47-68` declares jump strength at `0.42F`, movement efficiency at `0.0`, and movement speed at `0.7`. `net/minecraft/world/entity/LivingEntity.java:324-345` includes movement speed, jump strength, water movement efficiency and movement efficiency in the inherited supplier. `net/minecraft/world/entity/player/Player.java:213-228` overrides the player movement-speed base to `0.1F` and supplies sneaking speed. All declarations and literals are unchanged.
- Direct consumers: `Player.java:466-470` reads movement speed after `super.aiStep()` and casts the `double` attribute value to `float` for `setSpeed`; `Player.java:1338-1341` returns the same float-cast movement speed as `getSpeed`. `Player.java:655` sets the movement-speed base from the walking-speed ability. `LocalPlayer.java:627-642` applies sneaking speed only after nonzero input and the preceding item-use/non-passenger scale, when `isMovingSlowly()` is true. These exact gates and float operations are unchanged.
- Inherited player consumers: `LivingEntity.java:493-496` float-casts movement efficiency and interpolates the base block-speed factor to `1.0F`; `502-522` removes the powder-snow modifier when present, and adds `-0.05F * percentFrozen` only when the standing legacy block state is non-air, frozen ticks are positive and the speed attribute exists; `2162-2170` always removes the sprint ID on `setSprinting`, then adds the `0.3F ADD_MULTIPLIED_TOTAL` modifier only for `true`; `2222-2232` computes jump power as `(float)jumpStrength * scale * blockJumpFactor + jumpBoostPower`; `2331-2347` reads water-movement efficiency only in water, halves it off ground, and applies its formulas only when positive. These source sites are unchanged; no changed numeric precision, operation order or gate was found.
- Non-player supplier check: the 34 changed source hashes contain no changed `AttributeSupplier` or `createAttributes()` declaration. Thus there is no newly added non-player movement supplier in this pair. Non-player runtime movement was outside this assignment.

## Bounds

This audit establishes no modifier lifecycle, player/default supplier or direct player movement-attribute consumer delta for exactly 1.21.9→1.21.10. It does not close other movement lanes, packaged enchantment values, runtime parity, or the precise point where any older behavior first changed.

## Inspected source-file SHA-256 values

Each row has the same SHA-256 in 1.21.9 and 1.21.10, verified against the older embedded `sources` map and the newer `mojmap.sources.sha256` manifest.

| Mojmap Java path | SHA-256 |
| --- | --- |
| `net/minecraft/world/effect/MobEffect.java` | `a59b16b493d0c1fe164dc09e28a00050f4bf47ad95df0ed67e205b46b10a0e66` |
| `net/minecraft/world/effect/MobEffectInstance.java` | `8b35c542a25090f7a45fa019a5263ea51cc1beb2d58605dd4ed65e805035bc36` |
| `net/minecraft/world/entity/LivingEntity.java` | `b8b49d60769203f7bd5afe4a1bffcdcdbec30be28960324cdc43a2df85a6eb66` |
| `net/minecraft/world/entity/player/Player.java` | `af857617b66a5776e63830771360b96f75e21d47d20db08f164a2dbeee801d82` |
| `net/minecraft/world/entity/ai/attributes/Attributes.java` | `b941c0556d9ed3c3ce1ffd4c993c9902287a690230e1510696ff13f9efdf2020` |
| `net/minecraft/world/entity/ai/attributes/AttributeInstance.java` | `c2d4f89e7ae2abbe856e7e1aa5bff2ca613073cbda282c1975636e479ba8e346` |
| `net/minecraft/world/entity/ai/attributes/AttributeMap.java` | `06088d7aeedd65b468b807c3a500da7a3a16d787917812a55dd7b81ad60cd079` |
| `net/minecraft/world/item/ItemStack.java` | `07b284d7a7e680f0fc5cbff1c758afd3e245eea11825435f4226d7d4acdfb74e` |
| `net/minecraft/client/player/LocalPlayer.java` | `9c1df00e2f8379b2c19577a3691fee28071d8925210be3d3df928b5352e367e1` |
| `net/minecraft/client/player/AbstractClientPlayer.java` | `052e8387ace3ee65ea7456af23b8e9197931e5d5faeda54589166dce60269296` |

`AbstractClientPlayer.java:117-127` also reads movement speed for field-of-view calculation; that rendering-only consumer is outside the movement assignment. No result here treats it as a movement delta.
