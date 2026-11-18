# Mojmap 1.21.3 → 1.21.4: modifier dependency slice

## Inputs and source identity

Compared the read-only canonical Java sources at `decompiled_minecraft/1.21.3/mojmap/` and `decompiled_minecraft/1.21.4/mojmap/` in the primary checkout. Both ready markers identify exact releases and `mojmap`; their provenance records report a successful shared `decompileMinecraft` run for 1.21.1, 1.21.3, 1.21.4 and 1.21.5, with Java 25, Mojmap, and Vineflower 1.12.0. The ready markers give client SHA-256 `25190c556bc56d11070638ccab3b3c6efd76bb9684c92a2680a4de01c3c7138b` (1.21.3) and `c17c450c6e72cc51297daa57ce38f800aa01cf022b743daa21a0512d326d894e` (1.21.4). Source-manifest SHA-256: `d673bb5464853e3a2a92ed9b1ffe1789d4e62c097bb884f67c336fcbb3b178ce` and `f90b61197928632e061ea877955a19055c92ae6f357c2daf1bc646172c6f51f0`, respectively. I checked the selected Java files against their manifest entries; all matched. No source generation or product/research-index access was performed.

## Result

No difference was found in the assigned ordinary player movement modifier paths. No movement finding draft is warranted from these paired source slices.

### Defaults and operation order

`world/entity/ai/attributes/Attributes.java` and `AttributeSupplier.java` are byte-identical between releases. `Attributes.MOVEMENT_SPEED` retains registry default `0.7` (line 63); `JUMP_STRENGTH` retains the `0.42F` default (line 44). `LivingEntity.createLivingAttributes()` includes both without overriding their defaults (1.21.3 lines 318–335; 1.21.4 lines 318–335). `Player.createAttributes()` then overrides movement speed with `0.1F` and includes jump strength through the inherited supplier (1.21.3 lines 226–239; 1.21.4 lines 229–242). `DefaultAttributes` still maps `EntityType.PLAYER` to `Player.createAttributes().build()` (1.21.3 line 139; 1.21.4 line 140).

`AttributeInstance.calculateValue()` is byte-identical (both lines 152–170): start with base, add all `ADD_VALUE` amounts, accumulate each `ADD_MULTIPLIED_BASE` amount against that post-add value, then multiply by each `ADD_MULTIPLIED_TOTAL` factor in sequence, and sanitize once at the end. The default `AttributeSupplier.Builder.add(attribute)`/`add(attribute, value)` and instance construction remain unchanged: omitted values inherit the attribute default; explicit player speed remains `0.1F`.

1.21.4 adds `AttributeMap.resetBaseValue()` and wires it to the operator `/attribute ... base reset` command. That is a new explicit server-admin reset operation, not a change to the default player supplier, ordinary attribute evaluation, or a player tick path. The default-provider registry also relocates `CREAKING` and removes `CREAKING_TRANSIENT`; neither changes the player supplier. These adjacent deltas are recorded as scope notes, not ordinary movement findings.

### Ordinary effects and direct player movement reads

`MobEffects.java`, `MobEffect.java`, and `MobEffectInstance.java` are byte-identical. Speed and Slowness still attach `MOVEMENT_SPEED` modifiers of `0.2F` and `-0.15F`, both `ADD_MULTIPLIED_TOTAL` (`MobEffects.java` lines 15–27). `MobEffect.AttributeTemplate` still scales its amount by `(amplifier + 1)`; add/remove replace the same modifier ID (`MobEffect.java` lines 125–155, 181–184). Jump Boost remains a `SAFE_FALL_DISTANCE` modifier, while its upward jump bonus remains a direct `0.1F * (amplifier + 1.0F)` read (`MobEffects.java` lines 55–62; `LivingEntity.getJumpBoostPower`, 1.21.3 lines 2133–2135 and 1.21.4 lines 2144–2146). Levitation and Slow Falling remain unmodified effects read directly in air travel/gravity (`LivingEntity.travelInAir` / `getEffectiveGravity`, same method bodies at 1.21.3 lines 2173–2214 and 1.21.4 lines 2184–2225).

`LivingEntity`'s add/update/removal modifier lifecycle is unchanged: server-side effect addition installs the amplifier-scaled modifiers; an amplifier update removes then re-adds them; removal removes them and refreshes dirty attributes (1.21.3 lines 1001–1040; same bodies at 1.21.4 lines 1001–1040). The direct speed accessor remains `getAttributeValue(MOVEMENT_SPEED)`; Player's `getSpeed()` and `aiStep()` read that value in both versions, and inherited friction-influenced movement continues to consume it. Compared method bodies were equal for jump power, Jump Boost, jump impulse, effective gravity, air travel and fluid travel.

### Equipped/enchantment modifiers and synchronization

`ItemAttributeModifiers.java`, `Enchantment.java`, and `EnchantmentHelper.java` are byte-identical. `ItemStack.forEachModifier(slot)` still enumerates the stack's attribute-modifier component and then enchantment attribute effects (1.21.3 lines 981–993; same method bodies in 1.21.4 lines 1001–1013). On detected equipment changes, `LivingEntity.collectEquipmentChanges()` stops old location-based effects, removes/replaces the old modifier IDs with transient modifiers from each new intact stack, and starts location-based enchantment effects on `ServerLevel` (1.21.3 lines 2560–2603; same method body in 1.21.4 lines 2571–2614). Equipment break uses the same removal helper. These compared method bodies are identical.

Movement speed and jump strength remain client-syncable attributes. The server dirty-attribute path (`ServerEntity.sendDirtyEntityData()`), `ClientboundUpdateAttributesPacket` snapshot, and `ClientPacketListener.handleUpdateAttributes()` are unchanged in the compared methods: the packet carries base plus modifiers, and the client replaces the base and transient modifiers. Thus the observed modifier/equipment/enchantment flow and synchronization have no 1.21.3→1.21.4 change in this slice.

## Evidence limits and coverage boundary

The inspected canonical output is Java source only. It contains no packaged data/resource tree, and this assignment did not inspect raw packaged resources; resource-driven overrides are therefore **not evidenced** here. In particular, these results establish the Java default-provider path and the compared ordinary movement slices, not an exhaustive resource/tag/registry audit. Sprint-specific modifier behavior, block/provider/fluid dependencies, broader tick coverage and the independent full-pair audit remain outside this assignment. The pair is not frozen by this report.