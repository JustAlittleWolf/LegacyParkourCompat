# Ordered movement source navigation

Use this order to build the pair-specific index. Class names below are search seeds; resolve actual paths and member descriptors in each version before comparing. Complete each stage's inventory and track every discovered slice in the coverage ledger. Do not limit discovery to already implemented mod hooks.

## 1. Local input and tick ordering

Start at the local client player and input classes. Establish the order of input sampling, local tick, superclass tick and travel. Compare keyboard/controller state, yaw-to-motion conversion, diagonal normalization, sneak and item-use input scaling, sprint start/stop/timers, jump state/cooldown, auto-jump, flight toggles, unstuck behavior and riding gates. Track when previous/current input and flags are captured, not just their values.

Seeds: `LocalClientPlayerEntity`, `LocalPlayer`, `ClientPlayerEntity`, `KeyboardInput`, `ClientInput`, `Input`. Follow changed input producers to keybinding/options code only when reachable and relevant.

## 2. Player-specific state and gates

Follow the local player's superclass: pose selection, dimensions, eye height where it affects movement/fluid checks, swimming/crawling, flight abilities/speed, hunger and blindness sprint gates, item-use state, edge sneaking and stored air speed. Compare defaults, initialization, updates and reset timing of every influential field.

Seeds: `PlayerEntity`, `Player`, `Abilities`, pose/dimension definitions and active-item state. Food/health objects are checked only as direct predicate inputs and remain outside implementation scope.

## 3. Living movement integration

Index travel dispatch before its branches. Compare ground/air acceleration, friction, gravity/drag, negligible velocity cutoffs, jump power and sprint-jump impulse, climbing clamps, water/lava travel, swimming and gliding. Include branch selection, operation order and post-travel updates. Trace movement speed/jump/gravity attributes and helpers rather than assuming constants live here.

Seeds: `LivingEntity`, travel/jump/move-relative helpers, vector/math helpers directly invoked by those methods.

## 4. Entity movement and collision

Compare bounding-box movement, position updates, axis ordering, step-up candidates and tie-breaking, edge probes, grounding/support lookup, velocity cancellation/restitution, fluid state/push, and block callback order. Follow relevant world collision queries, AABB/shape operations and shape context. Include entity-size/pose effects on queries and repeated movement within a tick.

Seeds: `Entity`, `AABB` or the mapped box equivalent, `VoxelShape`, collision utilities, world collision lookup, support position and fluid contact helpers. Do not read all world-generation or rendering code.

## 5. Blocks and fluids that produce movement inputs

Inspect base block/state/fluid methods, their registrations/default properties and overrides. Enumerate subclasses overriding movement-relevant callbacks/shapes and registrations assigning friction, speed or jump factors; this catches additions beyond the seed list. Check state-dependent and neighboring-block-dependent behavior, shape construction, tags and callback conditions.

Seed categories: slime and beds (landing/bounce); soul sand and ice variants (speed/friction); webs, honey and powder snow (contact/slowdown); ladders/vines and other climbables; water/lava and bubble columns; moving pistons; historical partial blocks such as fences, walls, stairs, slabs, trapdoors, doors, snow layers, farmland and paths. Compare shape and support changes, not textures. Follow relevant flow-vector and fluid-height calculations.

For blocks/states absent from A, record the checked registry/registration evidence and modern-only applicability. Do not invent historical behavior for them.

## 6. Effects, enchantments, attributes and equipment

Trace **consumer -> attribute/helper -> aggregation -> effect/enchantment registration -> application/removal conditions -> equipment/tags/data**, and then verify the forward chain. Compare amplifier/level formulas, attribute operation types and order, default/clamped values, active slots, predicates, stacking, timers and removal/reapplication. A renamed class or a move into data is not itself a behavioral difference.

Explicitly disposition Speed, Slowness, Jump Boost, Levitation, Slow Falling, Dolphin's Grace and Blindness; Depth Strider, Soul Speed, Swift Sneak, Frost Walker and Riptide; Elytra, use-item slowdown, equipment and movement-relevant item components. Frost Walker may influence the surface through server behavior: document that boundary instead of inferring its world mutation from client movement. Check registrations for other movement-affecting entries; this list is not exhaustive. Absence from a version requires evidence, and movement effects of modern-only equipment remain separately classified.

Resources are omitted by this repository's Java source saver. Inspect matching jar resources and referenced tags/defaults as described in the main workflow. Server-synchronized attributes or data require an explicit provenance/coverage boundary.

## 7. External influences and dependency closure

Inspect client consumers of incoming velocity/position corrections and direct player-only velocity, impulse or knockback application insofar as they write the local player's movement state. The triggering event is not the scope: a combat event may trigger a player knockback response, or an external update may supply a player impulse. Compare the player-side motion operation and state write, not attack/damage resolution or the system producing damage. Follow the relevant player method or packet handler and separate client-computed response from externally supplied values. Do not audit non-player knockback, vehicle physics, or another entity's independent simulation. Other rendering-only changes and unrelated server rules are outside scope.

Finally enumerate unresolved movement-state writers, callback implementations, changed helpers, registries and newly reachable dependencies from all stages. Revisit unchanged callers affected by changed dependencies. Document cross-mechanic interactions without enumerating every possible gameplay state.

## Locally verified starting paths

Use these version-specific paths as navigation seeds, not comparison evidence or proof of mapping availability. Verify them against the assigned artifact manifests. Paths are relative to `decompiled_minecraft/<version>/<family>/`.

Feather (`ornithe-feather`), verified in 1.8.9, 1.12.2 and 1.13.2:

- `net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java`
- `net/minecraft/client/entity/living/player/KeyboardInput.java`
- `net/minecraft/entity/living/player/PlayerEntity.java`
- `net/minecraft/entity/living/LivingEntity.java`

Additional Feather 1.12.2 anchors:

- `net/minecraft/entity/living/effect/StatusEffects.java`, `StatusEffect.java`, `StatusEffectInstance.java`
- `net/minecraft/entity/living/attribute/ModifiableEntityAttributeInstance.java`, `EntityAttributes.java`, `AttributeModifier.java`
- `net/minecraft/enchantment/Enchantments.java`, `EnchantmentHelper.java`
- `net/minecraft/block/Block.java`, `SlimeBlock.java`, `SoulSandBlock.java`

Mojmap 1.14.4 and native unobfuscated 26.2 each have these paths. Both use Mojang's official-name namespace, so this is an allowed mapping alignment after verifying the unobfuscated jar and recording each side's provenance. Shared names alone do not establish class or member correspondence or a movement difference.

- `net/minecraft/client/player/LocalPlayer.java`, `KeyboardInput.java`
- `net/minecraft/world/entity/LivingEntity.java`
- `net/minecraft/world/phys/AABB.java`, `shapes/VoxelShape.java`
- `net/minecraft/world/level/material/FlowingFluid.java`
- `net/minecraft/world/effect/MobEffects.java`
- `net/minecraft/world/item/enchantment/Enchantments.java`, `EnchantmentHelper.java`

For the exact 1.13.2 to 1.14.4 comparison, explicit Ornithe Feather `feather-gen2` mappings are available on both sides. Both generated trees contain `net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java` and `KeyboardInput.java`; verify each member and caller before comparing behavior. Legacy Yarn 1.13.2 and Fabric Yarn 1.14.4 come from different catalogs and do not need to be paired for this run. Resolve inheritance and callers even when simple names match.

### Example of a dependency trail, not a version difference

In the inspected 26.2 unobfuscated output, `Enchantments.java` registers Depth Strider with `WATER_MOVEMENT_EFFICIENCY`, Soul Speed with `MOVEMENT_EFFICIENCY` and `SOUL_SPEED_BLOCKS` conditions, and Swift Sneak with `SNEAKING_SPEED`. `LivingEntity.java` consumes the first two attributes; `LocalPlayer.java` consumes sneaking speed. The useful discovery unit therefore spans registration, attribute application, conditions/tags and the movement consumer. Searching only for a dedicated `SwiftSneakEnchantment` class would miss this path. Re-resolve and cite exact members/lines/hashes in any actual comparison run.

## Inventory closure

Map every reachable behavior from stages 1–7 to the [run template's required inventories](templates/run.md#required-source-inventories): `INV-TICK`, `INV-STATE`, `INV-COLLISION`, `INV-WORLD-MOVEMENT`, `INV-MODIFIERS`, `INV-EXTERNAL` and `INV-EXCLUSIONS`. Each row needs exact paired body ranges, producers/consumers, guards/order, dependencies and a disposition.

Follow the [discovery completion gate](README.md#5-close-the-source-research). Source-only workers must not inspect mod code or wiki information before full-pair freeze. After freeze, use the implementation workflow to locate mod hooks and reconcile findings.
