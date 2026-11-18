# 1.21.8 → 1.21.9: modifier lifecycle and direct movement-attribute coverage

## Result

The assigned modifier and direct player movement-attribute slices show no movement-relevant behavior change in the compared Java source. Effect values, attach/replace/remove order, server-side gates, equipment modifier transfer, player supplier values, float casts, and direct player consumers are unchanged. The only gate spelling difference in `LivingEntity` is the 1.21.9 accessor call `level().isClientSide()` replacing the 1.21.8 field read `level().isClientSide`; the boolean gate and operation order remain the same. No difference finding is warranted for this bounded slice.

This is partial pair coverage, not a full-pair freeze or acceptance of all 1.21.8→1.21.9 movement operations. The 1.21.9 source tree has a hash-bearing hidden provenance file but no adjacent `mojmap.ready.json`; this limits source-readiness certification as detailed below.

## Inputs and source identity

Mapping family on both sides: Mojmap. Read-only canonical source roots:

- `decompiled_minecraft/1.21.8/mojmap/`
- `decompiled_minecraft/1.21.9/mojmap/`

1.21.8 has `mojmap.ready.json` (SHA-256 `2e3bf302503eb127061cc605797b390f5f4a4b508f09350a59d78ced857ba7d7`), `mojmap.provenance.json` (SHA-256 `90fcf3707371f2cfc5414e0089cfa231f505821d7aca45211c7c816332bb4cfa`), and `mojmap.sources.sha256` (SHA-256 `8ffb76cea647a2ba4fe58e000678f751bea2c40ea6358bae492e962ed1d9d008`; 6,105 source rows). The ready marker identifies exact release `1.21.8`, mapping `mojmap`, and Java 25. Its provenance records the exact run batch `1.21.8,1.21.10,1.21.11` and Mojmap; its ready marker's raw-client SHA-256 is `ea74e9c5e92d01f95f3d39196ddb734e19a077a58b4c433c34109487d600276f`.

1.21.9 has `mojmap/.decompile-provenance.json` (SHA-256 `37d3049478585a43ffcace14fc8f2ee814b35ceedd8c9bad8b60adaed9b72c4f`) with source hashes and pinned inputs. Its input keys identify `1.21.9.json` (package digest `3cca801f41f83059092c88f7de3ea74247da4c394f3e672b7a41451151c293d5`), `client.jar` (digest `40c0a32c46601f71f6a61dae9f126202ded5cc453cfb8da6e42ae73f2a3f5c1b`), and `client.txt` (digest `f223e9775abb66b6712c505cb1a73b48d615efebaf43a9f81dd58df9a9b1c7b3`). Selected Java files' actual SHA-256 values matched this hidden manifest. No adjacent `decompiled_minecraft/1.21.9/mojmap.ready.json` exists. A sibling `.mojmap.staging-7509591801405925509` directory exists under `1.21.9`; it was not used as source evidence or changed. Thus the checked source bytes are internally hash-bound to exact 1.21.9 inputs, but the required ready-marker evidence is absent on this side.

## Compared operations

### Effect modifier lifecycle

- `net/minecraft/world/entity/LivingEntity.java`: 1.21.8 lines 978–1001 and 1013–1023; 1.21.9 lines 985–1008 and 1020–1030. `addEffect` first applies `canBeAffected`; a missing effect is inserted before `onEffectAdded`; an existing effect reaches replacement only when `MobEffectInstance.update` returns true. `forceAddEffect` follows the same add-vs-replace distinction, copying blend state before the updated callback on replacement. These ordering and gates are unchanged.
- `LivingEntity.java`: 1.21.8 lines 1044–1089; 1.21.9 lines 1051–1096. Add, update and remove callbacks are gated on logical server (`!level().isClientSide` / `!level().isClientSide()`). Add invokes effect attribute attach; update, only when its boolean flag is true, removes the old modifier then adds the current amplifier's modifier and refreshes dirty attributes; removal removes each effect's modifier before passenger removal packets and refreshes dirty attributes after the loop. Callback order and conditional gates are unchanged.
- `net/minecraft/world/effect/MobEffect.java`, lines 155–172 on both sides: removal iterates attribute templates, checks `getInstance != null`, then removes by modifier id. Addition iterates the templates in order, checks the instance, removes the same id, then adds `create(amplifier)` as a permanent modifier. The complete file is byte-identical across both releases.
- `net/minecraft/world/effect/MobEffectInstance.java`: exact file hashes are byte-identical. Its update/expiry callback sites (1.21.8 lines 124–164, 215–239, 342–343; 1.21.9 same lines) therefore preserve effect merge and lifecycle behavior.
- `net/minecraft/world/effect/MobEffects.java`, lines 15–27 on both sides (byte-identical): Speed is `+0.2F`, Slowness is `-0.15F`, both `ADD_MULTIPLIED_TOTAL`; no constant, type, or order change.

### Equipped movement modifier attach/remove

- `net/minecraft/world/entity/LivingEntity.java`: 1.21.8 `collectEquipmentChanges`, lines 2637–2676; 1.21.9 lines 2667–2706. Changed slots are collected after comparing old/current stacks; old nonempty stacks stop location-based effects during that pass. For each changed new stack, direct modifiers are attached only when the stack is nonempty and unbroken. Each existing attribute instance has the same modifier id removed before the new modifier is added transiently. The post-attachment enchantment location-change callback remains server-level-only. This order and these guards are unchanged.
- `LivingEntity.java`: `stopLocationBasedEffects`, 1.21.8 lines 3523–3531; 1.21.9 lines 3553–3561. Modifier removal still checks for an attribute instance and then removes the modifier before stopping enchantment location effects. Broken equipment still dispatches this same removal path (1.21.8 lines 3518–3521; 1.21.9 lines 3548–3551).
- `net/minecraft/world/item/ItemStack.java`: 1.21.8 lines 964–974; 1.21.9 lines 968–978. It enumerates item `ATTRIBUTE_MODIFIERS` first and enchantment attribute modifiers second. `net/minecraft/world/item/enchantment/EnchantmentHelper.java`, lines 320–333 on both sides, preserves the effect enumeration and slot-match gate; the complete file is byte-identical.
- `LivingEntity.setSprinting`: 1.21.8 lines 2132–2140; 1.21.9 lines 2162–2170. It removes the sprint modifier after `super.setSprinting`, then conditionally adds the same transient modifier when sprinting is enabled. The shared modifier remains `0.3F`, `ADD_MULTIPLIED_TOTAL` (`LivingEntity.java` 1.21.8 lines 153–157; 1.21.9 lines 154–158).

### Player suppliers and direct movement-attribute consumers

- `net/minecraft/world/entity/LivingEntity.java` supplier: 1.21.8 lines 317–335; 1.21.9 lines 324–342. The base supplier still includes movement speed and inherited movement attributes in the same order.
- `net/minecraft/world/entity/player/Player.java` supplier: 1.21.8 lines 252–263; 1.21.9 lines 213–224. Player movement speed remains the float literal `0.1F`; the builder chain/order is unchanged. `net/minecraft/world/entity/ai/attributes/Attributes.java` is byte-identical: `MOVEMENT_SPEED` default `0.7` (lines 66–68), `SNEAKING_SPEED` default `0.3` (78–80), `STEP_HEIGHT` default `0.6` (84–86), and movement-efficiency defaults remain `0.0` (63–65, 94–96). The Player supplier overrides the inherited movement-speed default with `0.1F` and uses the declared sneaking-speed default.
- `Player.java`: direct attribute-to-speed write keeps the `(float)getAttributeValue(MOVEMENT_SPEED)` cast (`tick`, 1.21.8 line 579; 1.21.9 line 469). The `getSpeed` override returns the same float-cast attribute value (1.21.8 lines 1482–1484; 1.21.9 lines 1339–1341). Loading abilities continues to assign `getWalkingSpeed()` directly as the movement-speed base value (1.21.8 line 792; 1.21.9 line 655).
- `net/minecraft/client/player/LocalPlayer.java`, movement input path: 1.21.8 lines 628–638; 1.21.9 lines 632–642. The `0.98F` input scale and `isUsingItem() && !isPassenger()` gate precede the `isMovingSlowly()` gate; only then is the attribute fetched, cast to `float`, and used to scale the input vector. Exact factor, cast and order are unchanged.
- `net/minecraft/world/item/enchantment/Enchantments.java` provides Java bootstrap defaults; no packaged JSON/data resources are present in either canonical decompiled tree (`data/` does not exist). The directly relevant Java defaults are unchanged: Soul Speed `MOVEMENT_SPEED` uses `perLevel(0.0405F, 0.0105F)` with `ADD_VALUE` (1.21.8/1.21.9 lines 490–519); Swift Sneak uses `SNEAKING_SPEED`, `perLevel(0.15F)`, `ADD_VALUE` (lines 568–577). No remote data was downloaded. The water-movement attribute's consumer remains outside this C slice.

### Non-player supplier addition

`net/minecraft/world/entity/animal/coppergolem/CopperGolem.java` exists only in 1.21.9 and adds a non-player base movement-speed supplier (`createAttributes`, line 101; `.add(Attributes.MOVEMENT_SPEED, 0.2F)`). This new entity's supplier is outside player movement parity and is not evidence for a Player supplier change.

## SHA-256 evidence

Each listed Java hash was checked against its side's `mojmap.sources.sha256` (1.21.8) or hidden `.decompile-provenance.json` `sources` map (1.21.9); every selected-file check matched.

| Source file | 1.21.8 | 1.21.9 |
|---|---|---|
| `net/minecraft/world/entity/LivingEntity.java` | `609f0197a0b4551ab42279e452c11cdd256135b1d467c2a95950b0e9fd7ddef8` | `b8b49d60769203f7bd5afe4a1bffcdcdbec30be28960324cdc43a2df85a6eb66` |
| `net/minecraft/world/entity/player/Player.java` | `8dc5514fe44311f39268692f5188840b9f65cb71143228fa8b84242585ea7987` | `af857617b66a5776e63830771360b96f75e21d47d20db08f164a2dbeee801d82` |
| `net/minecraft/client/player/LocalPlayer.java` | `53f2a71a886b9c71853afe36f2df857f80a9bf1cd6ec4ca8604ccd7e238d89ee` | `9c1df00e2f8379b2c19577a3691fee28071d8925210be3d3df928b5352e367e1` |
| `net/minecraft/world/effect/MobEffect.java` | `a59b16b493d0c1fe164dc09e28a00050f4bf47ad95df0ed67e205b46b10a0e66` | same |
| `net/minecraft/world/effect/MobEffectInstance.java` | `8b35c542a25090f7a45fa019a5263ea51cc1beb2d58605dd4ed65e805035bc36` | same |
| `net/minecraft/world/effect/MobEffects.java` | `59460ae5a2087122e88eb4e6f799466ed3ea313a04a9e9162a9abf76dea440ae` | same |
| `net/minecraft/world/entity/ai/attributes/Attributes.java` | `b941c0556d9ed3c3ce1ffd4c993c9902287a690230e1510696ff13f9efdf2020` | same |
| `net/minecraft/world/item/enchantment/EnchantmentHelper.java` | `b1c8ce1ef431d57488b4c6f991aa01e9d8372adaa077df8ff55a419d9b9448a0` | same |
| `net/minecraft/world/item/ItemStack.java` | `f454d70486abdd066be5d5dc89c0d5b61ec6920fb702fe397d3d12bf902936c2` | `07b284d7a7e680f0fc5cbff1c758afd3e245eea11825435f4226d7d4acdfb74e` |
| `net/minecraft/world/item/enchantment/Enchantments.java` | `ee37699b95dc530fdd91dadef27d19a54bc7c4febe79bca40cc1ee438a626356` | `fadcf460d09813684bb6c2449feb9d0897201b262b505e7936b032cebd244f3b` |
| `net/minecraft/world/entity/animal/coppergolem/CopperGolem.java` | absent | `5b1fb74d4dfa852d8630ce6eb73d134989591d3d27ce3860abc549fc5d8895d1` |

## Bounds and next action

Compared only exact Java effect modifier lifecycle, equipped attribute modifier attach/remove, Player/LivingEntity supplier declarations, direct Player movement-speed/sneaking-speed consumers, and Java-defined directly relevant enchantment defaults. Non-player movement supplier additions were identified separately. No wiki, product implementation, product Git history, source generation, network access, tests, or launches were used. Fluid/water movement consumer behavior, collision/shape lanes, other player tick graph slices, and full-pair independent coverage audit remain unexamined here. Recheck or renew 1.21.9 source-readiness provenance with the canonical preparation owner; do not promote this bounded no-difference result to pair freeze while the ready marker is absent and the rest of the inventory remains open.
