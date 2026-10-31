# Ordinary local-player tick source inventory: 1.20.6 → 1.21.1

Status: bounded source-worker report; not a pair-wide freeze or independent audit. Mapping family: Mojmap / official unobfuscated names.

## Exact source identity

- `1.20.6/mojmap`: `mojmap.ready.json` identifies client SHA-256 `02dfd345ac1ad55692d5dbc8486ac7e4fea72cd54ac494a79cd48963048e56b2`, source manifest SHA-256 `56aae10684471d7abb1c366bd5dd431ab976112a87e6cc5c687a68f1eff06311`, and mapping file `1.20.6/client_mappings.txt` SHA-256 `27f4ef3a9362e9874e2c33edac981b6f485ad97f92c4e35b497525b646aae745` (Mojmap).
- `1.21/mojmap`: embedded `.decompile-provenance.json` identifies official client input `client.jar` SHA-256 `4b56bc9b44de7e88f9fbb52e716c18240da34469fcbafa5ed2e75b04d60617e8` and official `client.txt` mappings SHA-256 `127485d79248d4ae2955a7d061c3626d38853ecb054c4d008707732264b0aac1`. Relevant source hashes match the embedded source map.
- `1.21.1/mojmap`: `mojmap.ready.json` identifies client SHA-256 `499f6897d1837516680f3114072d8106e11c9adcd933fe5cf051b551089b0c99`, source manifest SHA-256 `900f956e00f6fc1300bb3d689ea49df2b1a57bcaa54617344ef456d95d47cb48`, and mapping file `1.21.1/client_mappings.txt` SHA-256 `140c47931cccc8fc9e4c22d7603e2d714d1a953a146f51ea7397d95c955536ec` (Mojmap).
- Verified SHA-256 entries against the corresponding manifests/embedded map for `LocalPlayer.java`, `Player.java`, `LivingEntity.java`, `Entity.java`, and `EnchantmentHelper.java` on all three releases. On 1.21 → 1.21.1, those core files plus `Attributes.java`, `AttributeInstance.java`, `Enchantments.java`, `LevelBasedValue.java`, and `EnchantmentAttributeEffect.java` are byte-identical.

## Reachable tick order and authority

1. `LocalPlayer.tick()` runs only when the player's block chunk is loaded; it calls `super.tick()` before sending movement/input state and ticking ambient handlers (`LocalPlayer.java:190` on 1.20.6, `:191` on 1.21/1.21.1).
2. `Player.tick()` sets `noPhysics` for spectators, updates underwater state, then calls `LivingEntity.tick()` (`Player.java:244` 1.20.6; `:249` 1.21/1.21.1). `LivingEntity.tick()` calls `Entity.tick()`/`Entity.baseTick()`, updates using-item and swim state, performs equipment detection only under `!level.isClientSide`, then calls virtual `aiStep()` when not removed (`LivingEntity.java:2336` 1.20.6; `:2319` 1.21/1.21.1).
3. Dispatch reaches `LocalPlayer.aiStep()` → `Player.aiStep()` → `LivingEntity.aiStep()`. The local class overrides `isEffectiveAi()` to return `true` even in a client level (`LocalPlayer.java:489` 1.20.6; `:485` 1.21/1.21.1), overriding `Entity.isEffectiveAi()`'s `!level.isClientSide` default (`Entity.java:2964` / `:2999`). Thus `LivingEntity.aiStep()`'s effective-AI branch is reachable on the logical client; `LocalPlayer.serverAiStep()` copies impulses/jump from `input` when the player is the controlled camera (`LocalPlayer.java:612` / `:608`). This branch is not excluded by a server-side predicate.
4. `LivingEntity.aiStep()` handles jump input, damps `xxa`/`zza`, updates fall-flying state, then dispatches to travel (`LivingEntity.java:2591` / `:2586`). For a locally controlled player, `isControlledByLocalInstance()` is true through the local-player effective-AI override; its ordinary non-ridden path calls `Player.travel()` (`Player.java:1473` / `:1456`), which delegates to `LivingEntity.travel()` except for the existing swimming and ability-flight adjustments. Vehicle/ridden travel is inventoried as excluded vehicle interaction.
5. `LivingEntity.travel()`'s local-authority branch chooses in-water, lava, fall-flying, or ordinary ground/air handling, then writes velocity and calls `move(MoverType.SELF, ...)`. `Entity.move()` resolves collision, writes position and on-ground/collision state, performs fall/step/block-response bookkeeping, and applies block speed factor. The player override `LocalPlayer.move()` calls `Entity.move()` then `updateAutoJump()` (`LocalPlayer.java:878` / `:881`).
6. Per-tick fluid state is refreshed in `Entity.baseTick()` through `updateInWaterStateAndDoFluidPushing()` and eye-fluid state. `Player.isPushedByFluid()` and `Player.isAffectedByFluids()` both remain abilities/flying predicates; both are inspected and unchanged across the cutovers. `Player.updatePlayerPose()` and `Entity.setPose`/position/box writers were compared; no player-path change was found.

## Paired source comparison

### 1.20.6 → 1.21

- `LocalPlayer.tick`, `Player.tick`, `LivingEntity.tick`, and `Entity.tick`/`baseTick`: ordinary call order remains the same. The new `EnchantmentHelper.tickEffects` addition to `LivingEntity.baseTick()` is guarded by `level instanceof ServerLevel`, so it does not execute for this local client player.
- `LocalPlayer.aiStep`: the sneak input factor changes from `Mth.clamp(0.3F + EnchantmentHelper.getSneakingSpeedBonus(this), 0.0F, 1.0F)` (`:666`) to `(float)this.getAttributeValue(Attributes.SNEAKING_SPEED)` (`1.21 :663`). Old Swift Sneak bonus is `level * 0.15F` (`1.20.6 EnchantmentHelper.java:168`). In 1.21 the attribute base is 0.3 with bounds 0..1 (`Attributes.java:86-88`), Player registers it (`Player.java:232`), and Swift Sneak adds `LevelBasedValue.perLevel(0.15F)` (`Enchantments.java:570-580`). Those vanilla constants reproduce the old level-based sneak factor; no independent vanilla movement delta is established here, so this is inventoried as a changed access path, not drafted as a finding. Mod-added/custom attribute modifiers are outside the compared vanilla sources.
- **Finding draft:** water movement efficiency replaces the Depth Strider integer path in water travel. Exact detail below.
- `LivingEntity.travel` otherwise retains water/lava/fall-flying branch order, `isAffectedByFluids`/`canStandOnFluid` gates, and velocity/position call order. The changed `Entity.move()` ground-state helper was renamed `setOnGroundWithKnownMovement` → `setOnGroundWithMovement`; both bodies simply set `onGround` and call the same `checkSupportingBlock` with the same arguments. `Entity.updateFluidHeightAndDoFluidPushing()` changes `scale($$1 * 1.0)` to `scale($$1)`, which is an identity multiply and does not change the value.
- `Player.aiStep()` adds peaceful-mode saturation gain. This is hunger/food production and excluded. `Player.tick()` adds decrement of `currentImpulseContextResetGraceTime`; its consumer is impulse/fall-damage context, so damage calculation is excluded.
- `Entity.refreshDimensions()` delegates a grown-box correction to `fudgePositionAfterSizeChange`, but the branch is explicitly `&& !(this instanceof Player)` and therefore cannot affect the local player. `setPose`, `setPos`, and player pose-selection code were not otherwise changed.
- `LocalPlayer.aiStep()` swaps Nether portal visual processing for the 1.21 confusion portal-transition effect and separately processes cooldown. This is portal transition presentation/state, not the ordinary local movement path; no displacement claim is made from it.

### 1.21 → 1.21.1

- No diff in the compared tick, input, movement, pose/dimension, fluid, attribute, or enchantment source files; key source hashes are identical between the two trees (identity section above). No finding draft for this boundary in this slice.

## Finding draft

Proposed path: `1.20.6--1.21/water-movement-efficiency-scaling.md`

# Water movement efficiency scaling

1.20.6, Mojmap: `net/minecraft/world/entity/LivingEntity.java`, `travel(Vec3)`, lines 2113-2132; `net/minecraft/world/item/enchantment/EnchantmentHelper.java`, `getDepthStrider(LivingEntity)`, lines 184-185.
1.21, Mojmap: `net/minecraft/world/entity/LivingEntity.java`, `travel(Vec3)`, lines 2100-2115; `net/minecraft/world/item/enchantment/Enchantments.java`, lines 354-368.
Difference: The 1.20.6 water branch reads an integer Depth Strider level, caps it at 3, halves it off-ground, then applies both water slowdown and acceleration adjustments as `difference * level / 3.0F`. In 1.21 it reads `WATER_MOVEMENT_EFFICIENCY` as a float, halves it off-ground, and multiplies both differences directly by that float. The 1.21 Depth Strider registration supplies `LevelBasedValue.perLevel(0.33333334F)` to this attribute; the attribute is bounded to 0..1 and included in living attributes (`Attributes.java:101-102`; `LivingEntity.java:321`). Thus the Depth Strider levels map to the same intended 0..1 scale, but the old multiply-then-divide sequence and the new direct float multiply have different intermediate rounding; they feed `moveRelative` acceleration and post-move horizontal drag for a fluid-affected player swimming in water.
Movement meaning: A locally controlled player in water with nonzero Depth Strider can receive a different float value in horizontal acceleration/drag through operation order, affecting that tick's velocity and resulting displacement. No runtime trajectory was tested. Finding is a source-level operation-order candidate; independent audit should confirm IEEE float observability for vanilla level/state values.

## Coverage and open dependencies

- **Examined for both cutovers:** LocalPlayer tick/AI/server-AI/move; Player tick/AI/travel, local fluid predicates and pose update; LivingEntity baseTick/tick/AI/jump/fluid travel/effect-ticking entry and relevant speed/effect reads; Entity tick/baseTick/move/fluid push and position/box/pose/resize paths; attributes and enchantment definitions feeding sneak and water movement efficiency.
- **Compared unchanged or same math:** jump-power/jump-from-ground inputs, fluid jump gates, player flying/fluid authority predicates, pose selection, LocalPlayer auto-jump wrapper, effects ticking on client, Entity supporting-block computation, water/lava/fall-flying travel branch order. The renamed supporting-ground method has an identical body. The `$1 * 1.0` simplification in fluid pushing is value-identical.
- **Explicitly excluded:** non-player/vehicle movement and ridden vehicle physics; portal transition/teleport behavior; damage/fall-damage consumers and impulse-context state; hunger/food/saturation/health producers; non-player resize correction. Server-only equipment detection and server-only enchantment location ticking do not execute on the client path.
- **Partial / not audited in this slice:** collision/shape providers and registrations, block callbacks and inside-block effects, fluid-flow/tag resources and every fluid state producer, auto-jump obstacle/shape probing, network packet construction/application and synchronization of attributes/equipment/effects, and other externally triggered direct player responses. These are dependencies of the complete pair inventory and remain open; source-level ordinary tick call order alone does not close them.
- Next bounded action: independent reviewer should retrace the ordinary tick graph and the water-efficiency candidate, then close the listed collision/fluid/equipment-state dependencies before this lane can claim complete coverage.


## Arithmetic candidate refinement

The water-scaling draft's float-observability gate is resolved from source constants and Java binary32 operation order for a standard grounded LocalPlayer state; no game, test, build, or trajectory execution was used.

### State and source gates

- Locally controlled: `LocalPlayer.isEffectiveAi()` returns true; consequently the `LivingEntity.travel()` `isControlledByLocalInstance()` branch is taken for the local player. Relevant sources: 1.20.6 `LocalPlayer.java:489`, `Entity.java:2960-2964`, `LivingEntity.java:2104`; 1.21 `LocalPlayer.java:485`, `Entity.java:2995-2999`, `LivingEntity.java:2091`.
- In water, not flying, and not standing on the water surface: `LivingEntity.travel()` enters its water branch at 1.20.6 `LivingEntity.java:2113` / 1.21 `:2100`, whose gates are `isInWater() && isAffectedByFluids() && !canStandOnFluid(fluidState)`. `Player.isAffectedByFluids()` returns `!abilities.flying` (1.20.6 `Player.java:1062-1063`; 1.21 `:1065-1066`); take `flying == false`.
- Grounded: the off-ground `$$7 *= 0.5F` branch at 1.20.6 `LivingEntity.java:2122-2124` / 1.21 `:2105-2107` is skipped.
- Non-sprinting: the water slowdown local selects `getWaterSlowDown()`, which returns `0.8F` (1.20.6 `LivingEntity.java:2091-2092`; 1.21 `:2078-2079`). This sets `$$5`; it does not alter the independent `$$6` expression below.
- Standard base speed: Player registers `Attributes.MOVEMENT_SPEED` with `0.1F` (1.20.6 `Player.java:224`; 1.21 `:225`) and `Player.getSpeed()` casts the attribute value to `float` (1.20.6 `Player.java:1511-1513`; 1.21 `:1494-1496`), yielding binary32 `0.1F` absent other movement-speed modifiers.
- Depth Strider II: the old helper returns the enchantment's integer level (`1.20.6 EnchantmentHelper.java:184-185`); new registration attaches `WATER_MOVEMENT_EFFICIENCY` with `LevelBasedValue.perLevel(0.33333334F)` (`1.21 Enchantments.java:354-368`). `LevelBasedValue.Linear.calculate(2)` evaluates `base + perLevelAboveFirst * (2 - 1)` in float (`1.21 LevelBasedValue.java:135-138`): `0.33333334F + 0.33333334F * 1` rounds to `0.6666667F` (`0x3f2aaaab`). Its additive modifier is added to the attribute's zero base and sanitized to the declared 0..1 bounds (`Attributes.java:101-102`, `AttributeInstance.java:142-159`, `RangedAttribute.java:35-37`); the travel cast to float leaves `0.6666667F` unchanged.

### Directly consumed float

Both versions initialize `$$6` to `0.02F` and then pass the result to `moveRelative($$6, input)` (1.20.6 `LivingEntity.java:2116, 2128, 2135`; 1.21 `:2103, 2111, 2118`). Java float operations round after each operation:

| Operation | 1.20.6: integer level, multiply then divide | 1.21: float attribute, direct multiply |
| --- | ---: | ---: |
| `getSpeed() - 0.02F` | `0.07999999821186066` (`0x3da3d70a`) | same |
| scale | `* 2.0F` → `0.1599999964237213` (`0x3e23d70a`), then `/ 3.0F` → `0.053333330899477005` (`0x3d5a740d`) | `* 0.6666667F` → `0.0533333346247673` (`0x3d5a740e`) |
| add initial `0.02F` | **`0.07333333045244217` (`0x3d962fc9`)** | **`0.07333333790302277` (`0x3d962fca`)** |

The `$$6` scalar passed directly into `moveRelative` differs by one binary32 ULP (`7.450580596923828e-9`). This validates an observable source-level movement-input difference without asserting any particular downstream trajectory.

### Refined finding draft

Proposed path: `1.20.6--1.21/water-movement-efficiency-scaling.md`

# Water movement efficiency scaling

1.20.6, Mojmap: `net/minecraft/world/entity/LivingEntity.java`, `travel(Vec3)`, lines 2113-2135; `net/minecraft/world/item/enchantment/EnchantmentHelper.java`, `getDepthStrider(LivingEntity)`, lines 184-185.
1.21, Mojmap: `net/minecraft/world/entity/LivingEntity.java`, `travel(Vec3)`, lines 2100-2118; `net/minecraft/world/item/enchantment/Enchantments.java`, lines 354-368; `net/minecraft/world/item/enchantment/LevelBasedValue.java`, `Linear.calculate(int)`, lines 135-138.
Difference: For grounded, non-sprinting LocalPlayer travel in water with Depth Strider II and ordinary movement speed, 1.20.6 computes the acceleration factor as `0.02F + ((0.1F - 0.02F) * 2.0F) / 3.0F`, while 1.21 obtains `0.6666667F` from the depth-strider attribute and computes `0.02F + (0.1F - 0.02F) * 0.6666667F`. The respective `moveRelative` inputs are `0x3d962fc9` and `0x3d962fca`; the new direct multiply rounds one ULP higher.
Movement meaning: The scalar horizontal acceleration passed into `moveRelative` differs by one binary32 ULP for this reachable ordinary local-player state. No downstream trajectory or runtime result is asserted.
