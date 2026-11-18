# Blind source audit freeze: local equipment locomotion

Pair: Minecraft 1.21.10 -> 1.21.11, Mojmap/Vineflower. This is a source-only bounded freeze for dry, ordinary local-player locomotion while not riding and not fall-flying. It covers player equipment speed/jump/gravity attribute readers and modifier ordering, plus active-use input scaling and its data component. It does not cover fluid travel, Elytra flight, swimming, climbing, vehicles, effects lifecycle, geometry, damage, or general sprint/sneak/timer/rotation/network behavior.

## Source identity

Both canonical trees were consumed read-only at `decompiled_minecraft/<version>/mojmap/`. Each `mojmap.ready.json` reports `status=ready`, the requested version and mapping. The 1.21.10 source manifest SHA-256 is `4be26049350c1b314a0b198022cb1e7ab1e745e104047de7a5c7d099a9d7b7e1`; the 1.21.11 source manifest SHA-256 is `0c4d83fef84c101d9db88f331acec7960f3c8346228c28995e6cf57bbfcae555`. Both equal the ready-marker values and were recomputed from the manifest files. The artifact manifest SHA-256 recomputes to `c8210b15012dcc4e109c2f73015b4117abec1f8ab04fdd375dbc11419e9e246c` for both versions and matches each ready marker. Provenance names Mojmap, Vineflower 1.12.0, and Java 25.0.3+9-LTS.

## Frozen findings

### Equipment attributes and first movement readers: bounded equivalence

1.21.10, Mojmap: `net/minecraft/world/entity/player/Player.java`, `createAttributes` lines 213-227 and `aiStep` line 469; `net/minecraft/world/entity/LivingEntity.java`, `createLivingAttributes` lines 324-345, equipment modifier application lines 2686-2703, `getJumpPower` lines 2222-2232, `getDefaultGravity` lines 2265-2268, and `getFrictionInfluencedSpeed` lines 2520-2525; `Entity.java`, `getGravity` and `applyGravity` lines 1360-1372; `Attributes.java`, `GRAVITY` / `JUMP_STRENGTH` declarations lines 44-49; `net/minecraft/world/entity/ai/attributes/AttributeInstance.java`, `calculateValue` lines 150-168; `net/minecraft/world/item/ItemStack.java`, `forEachModifier` lines 968-977; `net/minecraft/world/item/component/ItemAttributeModifiers.java`, `forEach` lines 66-87.

1.21.11, Mojmap: same source members in `Player.java` lines 211-225 and 466; `LivingEntity.java` lines 325-346, 2812-2829, 2256-2266, 2299-2307, and 2578-2583; `Entity.java`, `getGravity` and `applyGravity` lines 1379-1391; `Attributes.java`, `GRAVITY` / `JUMP_STRENGTH` declarations lines 45-50; `AttributeInstance.java` lines 147-165; `ItemStack.java` lines 1011-1020; `ItemAttributeModifiers.java` lines 63-85.

Difference: no source change in the bounded movement operation. Player `MOVEMENT_SPEED` base remains `0.1F`; `LivingEntity` still reads its current attribute into the player's cached speed in `Player.aiStep`. Ground movement's first speed reader is `getFrictionInfluencedSpeed` -> `getSpeed`; jump power remains `(float)JUMP_STRENGTH * factor * blockJumpFactor + jumpBoostPower`; living default gravity remains the `GRAVITY` attribute. The ordinary no-gravity gate (`Entity.getGravity`) and slow-falling effective-gravity condition are unchanged. Attribute declarations keep their default gravity `0.08` and jump strength `0.42F`; the player speed base remains `0.1F`. Attribute value calculation remains base plus all `ADD_VALUE` amounts, then all `ADD_MULTIPLIED_BASE` amounts against that post-add base, then each `ADD_MULTIPLIED_TOTAL` factor, followed by attribute sanitization. For a slot, `ItemStack.forEachModifier` visits the stack's `ATTRIBUTE_MODIFIERS` component before enchantment modifiers; the equipment-change path removes an incoming modifier's existing ID before adding it transiently. These source methods have identical movement order and arithmetic across the pair.

Applicability: the compared Java item sources contain no direct vanilla item registration for `JUMP_STRENGTH` or `GRAVITY`; custom component data remains a configurable input. Their only direct item-side `MOVEMENT_SPEED` registration is Soul Speed's location-changed enchantment effect (`Enchantments.java`, 1.21.10 lines 502-518; 1.21.11 lines 505-522), with the same per-level value and operation. This is block-conditioned and unchanged; it is not a general equipment-speed delta. Damage and other attributes are outside this freeze.

### Active-use input scaling: component refactor, default-path equivalence

1.21.10, Mojmap: `net/minecraft/client/player/LocalPlayer.java`, `applyInput` line 614, `modifyInput` lines 627-643, and `canStartSprinting` lines 1062-1069. For nonzero input, the method scales by `0.98F`, then by `0.2F` while using an item and not a passenger, then by the sneaking-speed attribute when moving slowly, then applies square-movement correction. Sprint eligibility rejects `isUsingItem()`.

1.21.11, Mojmap: `LocalPlayer.java`, `isSlowDueToUsingItem` / `itemUseSpeedMultiplier` lines 526-532, `applyInput` / `modifyInput` lines 655-683, and `canStartSprinting` lines 1102-1110. The same ordered input path now reads the active stack's effective `USE_EFFECTS.speedMultiplier`; the sprint predicate reads `USE_EFFECTS.canSprint` through `isSlowDueToUsingItem`.

`UseEffects` is new in 1.21.11. `DataComponents.java` registers `USE_EFFECTS` as persistent and network-synchronized at lines 115-117 and installs `UseEffects.DEFAULT` in `COMMON_ITEM_COMPONENTS` at lines 407-417. `UseEffects.DEFAULT` is `(canSprint=false, interactVibrations=true, speedMultiplier=0.2F)`; the `speed_multiplier` codec is bounded to `[0,1]` (`world/item/component/UseEffects.java`, lines 9-27). `LocalPlayer` also passes that default to `ItemStack.getOrDefault`, so an absent override takes the same value. The effective defaults for ordinary pre-existing active-use items therefore preserve the old `0.2F` slowdown and no-sprint gate, in the same input operation order. Example built-in: the bow registration remains `Items.java` line 1146 using ordinary properties and inherits the common component default.

Built-in exception and era boundary: 1.21.11's `Item.Properties.spear` attaches `UseEffects(true, false, 1.0F)` (`Item.java` lines 463-510), and `Items.java` registers wooden, stone, copper, iron, golden, diamond and netherite spears at lines 1897-1917. No spear registration or `USE_EFFECTS` component exists in the 1.21.10 tree. Treat this as 1.21.11-only item/component behavior, not as an input for reproducing 1.21.10. Configured nondefault `USE_EFFECTS` values likewise describe component-driven inputs; they do not establish a difference for ordinary defaulted vanilla stacks. The component also changes item-use vibration behavior, which is out of scope here.

Reachable ordinary witness for equivalence: using a pre-existing active-use item such as a bow on nonzero local movement input, on foot, follows the paired paths above. Its effective multiplier is `0.2F` on both sides and the input operations remain ordered identically. The source does establish a new per-item control point in 1.21.11, but no ordinary default-valued pre-existing active-use stack changes this bounded movement result.

## Coverage disposition

Closed for the bounded source inventory above: paired player speed/jump/gravity readers and predicates; equipment component/enchantment modifier collection and attribute operation phases; local active-use input scaling, default/effective component, default-valued built-in path, and new-only spear registration. The first consumers remain the player cached-speed and movement input paths, jump-power calculation, and living gravity getter. No runtime parity claim is made. Excluded neighbors were not treated as findings.

## Consumed Java source SHA-256

Hashes below are file bytes under each version's canonical Mojmap tree; each was checked against that version's `mojmap.sources.sha256` entry.

| Source file | 1.21.10 | 1.21.11 |
|---|---|---|
| `net/minecraft/client/player/LocalPlayer.java` | `9c1df00e2f8379b2c19577a3691fee28071d8925210be3d3df928b5352e367e1` | `948e94f8e874b2f72e9a689e6e3ba1933f5728ec381d64f3bb5e2388718bd477` |
| `net/minecraft/core/component/DataComponents.java` | `1829f8a5a69e4cbe5ee15ecc5a48c1a331a0c2949608d41f9c9b189736d0ebd6` | `a6647a61e315769a256e2a8cfe96596c083ab3ce11cda292a70e89f3aa46d1fc` |
| `net/minecraft/world/entity/Entity.java` | `8361dbb86fe6c975d21f69d008377b6f191669be751150c842517e9d0346fa18` | `32314478c6036fa9f3f3cc409c61c622eefc5a282d60e1011a1a33cc29cf18a3` |
| `net/minecraft/world/entity/LivingEntity.java` | `b8b49d60769203f7bd5afe4a1bffcdcdbec30be28960324cdc43a2df85a6eb66` | `19ed2d565858c401c69a06750b054a633a20ea864cab3a753b5c767f0bdd60e8` |
| `net/minecraft/world/entity/ai/attributes/AttributeInstance.java` | `c2d4f89e7ae2abbe856e7e1aa5bff2ca613073cbda282c1975636e479ba8e346` | `4b89cf81cf75b16ed743b6bd0a683154c481247230b56dfa37c11ccfe17004c1` |
| `net/minecraft/world/entity/ai/attributes/AttributeMap.java` | `06088d7aeedd65b468b807c3a500da7a3a16d787917812a55dd7b81ad60cd079` | `c8dfebeefa367556ff706951cacea308c18da53fa28108c18f59789e6f1b60e2` |
| `net/minecraft/world/entity/ai/attributes/Attributes.java` | `b941c0556d9ed3c3ce1ffd4c993c9902287a690230e1510696ff13f9efdf2020` | `62129028c2f7beb6d2f87bcd3345e63a7be8fc3950a7ecdbc6ee21f8fb89f843` |
| `net/minecraft/world/entity/player/Player.java` | `af857617b66a5776e63830771360b96f75e21d47d20db08f164a2dbeee801d82` | `8e97167350a91741d0aa10d3b0d92a33150ed6dccdc94cd5b37d9c7ca22bcc81` |
| `net/minecraft/world/item/Item.java` | `564e768acd45f794416ecd16948524ce76f2abeecc0440e3763493b0ce5d26ef` | `53e371efb8ef6eca5e8585a4598b3251dc37bd005b6d4c068c1f8c67d4efeee3` |
| `net/minecraft/world/item/ItemStack.java` | `07b284d7a7e680f0fc5cbff1c758afd3e245eea11825435f4226d7d4acdfb74e` | `a40daaf8a0e31dbf1f0f5c5413c14265d52fa0117132e0aeb1bb8fa10487e01a` |
| `net/minecraft/world/item/Items.java` | `2331d554a1365defd3e89c033e3321bec64cdcd9e5c92764744a788c376a2ce8` | `4abbdcd5f3ba6d906d80e1bf3b42610fc0afbfa87a13b4be7d9bb579a429e1ad` |
| `net/minecraft/world/item/component/ItemAttributeModifiers.java` | `de6e2f7df09d37c20a653915b6db9910335da16fbb6b48fd471b901f18a7f289` | `992814789d3fe99db0d5acd2eed50fa69430da4e0f8f7b09b3c2a603a33afd61` |
| `net/minecraft/world/item/component/UseEffects.java` | absent | `bb69726562d4f68006732a159abceb04e63685e8c05d2338b15d69a53f60bb6f` |
| `net/minecraft/world/item/enchantment/EnchantmentHelper.java` | `b1c8ce1ef431d57488b4c6f991aa01e9d8372adaa077df8ff55a419d9b9448a0` | `38e944de3098847f62633a6f0747bc2e1dcf016694faebe47009fbe8af514a85` |
| `net/minecraft/world/item/enchantment/Enchantments.java` | `fadcf460d09813684bb6c2449feb9d0897201b262b505e7936b032cebd244f3b` | `c4de10d11104f209de97a002744c277a869a05a86f303b1d68a2a389c8f4c5e9` |