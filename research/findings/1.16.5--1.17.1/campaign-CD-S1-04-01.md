# CD-S1-04-01: Jump Boost ground-jump vertical impulse uses different addition precision

- **1.16.5**, mojmap: `1.16.5/mojmap/net/minecraft/client/player/LocalPlayer.java`; `1.16.5/mojmap/net/minecraft/world/entity/LivingEntity.java`.
- **1.17.1**, mojmap: `1.17.1/mojmap/net/minecraft/client/player/LocalPlayer.java`; `1.17.1/mojmap/net/minecraft/world/entity/LivingEntity.java`.

## Difference

In 1.16.5, `getJumpPower()` returns a float. `jumpFromGround()` stores it in float `f` and, when `MobEffects.JUMP` is active, adds `0.1F * (amplifier + 1)` to that float before passing `f` to `setDeltaMovement(double, double, double)`.

The compound assignment rounds the sum to float before widening.

In 1.17.1, `getJumpPower()` still returns the same float, but `jumpFromGround()` stores `getJumpPower() + getJumpBoostPower()` in double `d`.

The helper's active-effect expression uses the same float literal and integer amplifier, then returns the value as double.

The float base and float-valued boost are therefore widened and added as doubles.

With Jump Boost I (`amplifier == 0`) and block jump factor `1.0F`, 1.16.5 writes vertical velocity `0.5199999809265137`; 1.17.1 writes `0.5199999883770943`.

Both retain the same x/z components, set `hasImpulse`, and apply the same sprint-only horizontal impulse afterward. `Entity.setDeltaMovement` stores the supplied vector directly in both versions.

## Movement meaning

The S1-01/S1-02 input and tick path reaches the local player's `LivingEntity.aiStep`.

With `jumping == true`, the player grounded on a dry, flat vanilla stone surface, and `noJumpDelay == 0`, the paired ground branch calls `jumpFromGround()`.

Dynamic dispatch enters `Player.jumpFromGround()`, whose paired override calls `super.jumpFromGround()` before its matching jump-stat and exhaustion calls.

Those later food-state effects are outside this finding and do not alter the just-written velocity.

For this concrete surface precondition, `Blocks.STONE` is present in both endpoints and does not override `BlockBehaviour.Properties.jumpFactor`; the property default is `1.0F`. `Entity.getBlockJumpFactor()` reads the current and support blocks, choosing the support value when the current block value equals `1.0`; both values are `1.0F` on ordinary air over stone.

The getter and below-block coordinate calculation match across the endpoints.

`MobEffects.JUMP` is registered as effect id 8 / `jump_boost` in both versions.

The paired `hasEffect`/`getEffect` methods read the same active-effects map, and `MobEffectInstance.getAmplifier()` returns the same amplifier field.

The finding conditions on the same active Jump Boost I state; it makes no claim about how an effect is produced or synchronized.

Source proves a different immediate y component in the player velocity write under the stated guards.

The numerical delta is approximately `7.450580596923828e-9`.

[Archived report](../../archive/legacy-workflows/source-campaign-2026-10-07/1.16.5--1.17.1/findings/CD-S1-04-01.md).
