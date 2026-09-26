# Navigation index: 1.16.5 to 1.17.1

Paths are relative to each version's `mojmap` source root. These are navigation correspondences, not assumed behavioral equivalence. Confirm callers, inheritance, signatures and data per slice.

## 1. Local input and tick ordering

| Role | A — 1.16.5 | B — 1.17.1 | Correspondence / state |
| --- | --- | --- | --- |
| Local player | `client/player/LocalPlayer.java`: `tick()` line 184; `aiStep()` line 627; extends `AbstractClientPlayer` | same class: `tick()` line 191; `aiStep()` line 649; same superclass | Same exact Mojmap names. `tick` handles client tick and movement packet reporting; `aiStep` samples and gates player input before superclass living movement. Inspect each branch separately. |
| Input state | `client/player/Input.java`: `tick(boolean)`, `getMoveVector()`, `hasForwardImpulse()` | same paths and methods | State: impulses, up/down/left/right, jumping, shift. Source hashes are identical. |
| Keyboard producer | `client/player/KeyboardInput.java`: `tick(boolean)` line 13 | same path; line 14 | Compared-no-difference: B declares unused `MOVING_SLOW_FACTOR = 0.3`; both methods still multiply by the same literal and cast. |
| Tick guard / rotation packet | `LocalPlayer.tick()` line 184; `LevelReader.hasChunkAt(BlockPos)` line 158 | `LocalPlayer.tick()` line 191; `LevelReader.hasChunkAt(int,int)` line 170; `Entity.getBlockX/Z()` lines 2904/2940; `Entity.setPosRaw()` line 2956 | Compared-no-difference for vanilla local-player state. A floors `(x,0,z)` and applies `>> 4` to X/Z; B maintains floored block position and applies `SectionPos.blockToSectionCoord` (`i >> 4`). Both ignore Y. B `getYRot/getXRot` return the same fields A reads directly. |

In the inspected beginning of `LocalPlayer.aiStep()`, both read prior `jumping` and shift state into locals and compute pre-sampling sprint impulse; select crouching; call `input.tick(isMovingSlowly())`; notify tutorial; apply `0.2F` movement input slowdown when using an item and not riding; process auto-jump; then continue into sprint/jump/fluid/riding work and `super.aiStep()`. That order matches in the inspected range. B changes direct `abilities` field reads to `getAbilities()`; B `Player.getAbilities()` returns the same field. B `ItemStack.is(Item)` returns `getItem() == item`, matching the prior Elytra equality predicate.

## 2. Player-specific state and gates

| Role | A | B | Status |
| --- | --- | --- | --- |
| Player superclass/state | Resolve `Player`, `AbstractClientPlayer`, abilities, food, pose/dimensions and active-item writers from `LocalPlayer` inheritance | Same Mojmap package hierarchy | Pending. |
| Pose/dimensions/swim | Resolve player pose selection, dimensions and eye height call paths | Resolve corresponding members | Pending. |

## 3. Living movement integration

| Role | A | B | Status |
| --- | --- | --- | --- |
| Travel/jump/tick | `world/entity/LivingEntity.java`: `travel(Vec3)` line 1914; `jumpFromGround()` line 1882; `aiStep()` line 2368 | same class/method signatures: `travel(Vec3)` line 2003; `jumpFromGround()` line 1975; `aiStep()` line 2455 | Exact class correspondence; split ground/air/water/lava/climb/jump and post-travel work into bounded slices. |

## 4. Entity movement and collision

| Role | A | B | Status |
| --- | --- | --- | --- |
| Entity tick/movement | `world/entity/Entity.java`: `tick()` line 354; `move(MoverType, Vec3)` line 485; `moveRelative(float, Vec3)` line 1075 | same signatures: `tick()` line 391; `move(...)` line 534; `moveRelative(...)` line 1176 | Same exact names/signatures; compare axis order, clipping, step candidates, callbacks and transform. |
| Collision geometry | `world/phys/AABB.java`; `world/phys/shapes/VoxelShape.java` | same paths | Member correspondence and shape algorithms pending. |

## 5. Blocks and fluids

| Role | A | B | Status |
| --- | --- | --- | --- |
| Base block / fluid | `world/level/block/Block.java`; `world/level/material/FlowingFluid.java` | same paths | Friction, speed/jump factors, callbacks, flow and registrations pending. |
| Relevant subclasses and state data | Enumerate registrations, overrides, tags and resource data | Same | Pending; failed name searches are not absence evidence. |

## 6. Effects, enchantments, attributes and equipment

| Role | A | B | Status |
| --- | --- | --- | --- |
| Effects | `world/effect/MobEffects.java` | same path | SHA-256 currently identical; consumer/application paths pending. |
| Enchantments | `world/item/enchantment/Enchantments.java`; `EnchantmentHelper.java` | same paths | Enchantments registry source hashes match; helper hashes differ. Trace formulas, conditions, slots, tags and data. |
| Attributes/equipment | Resolve `Attributes`, modifier aggregation and equipment update paths | Resolve corresponding paths | Pending. |

## 7. External influences and dependency closure

Compare client packet consumers for velocity/position correction, pushes, explosions, piston movement and riding transitions. Distinguish client-computed rules from synchronized/server-supplied state. Exact packet-handler correspondences and remaining state writers are pending.

## Ordered queue

1. Complete stage 1: input/tick sequence; sprint gates and timers; jump and auto-jump; item-use slowdown; flight and riding gates.
2. Resolve stage 2 player state, defaults, pose and gate dependencies.
3. Compare stage 3 living travel/jump branches and helper writers.
4. Compare stage 4 entity movement, box/shape clipping, support and callbacks.
5. Enumerate stage 5 block/fluid overrides, registrations and resources.
6. Trace stage 6 effect/enchantment/attribute/equipment consumers and data.
7. Compare stage 7 external-state paths and close dependencies/cross-mechanic interactions.
