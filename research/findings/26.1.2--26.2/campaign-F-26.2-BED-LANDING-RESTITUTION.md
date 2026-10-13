# F-26.2-BED-LANDING-RESTITUTION: Bed landings use restitution in the new collision response

- **26.1.2**, unobfuscated: `net/minecraft/world/entity/Entity.java`; lines 781–785.
- **26.2**, unobfuscated: `net/minecraft/world/entity/Entity.java`; lines 784–786.

## Difference

This finding binds an ordinary local-player ledge departure and falling recurrence to an exact bed-top contact.

Let the bed occupy block Y 0, so its collision top is exactly `9/16 == 0.5625`. 26.1.2 full cube beside the bed has top Y `1.0`, exactly `7/16 == 0.4375` above that top.

The local player leaves its ledge into the bed's horizontal footprint during a horizontal-input tick with current vertical movement `+0.0`; `handleRelativeFrictionAndCalculateMovement` performs that move before `travelInAir` applies gravity, so the player's Y remains `1.0` and the travel tail writes the first falling velocity.

Thereafter use no horizontal input, no effects or modifiers, and vertical drag `0.98F`.

The player is not a passenger, swimming or ability-flying.

Hold shift only for the bed-contact tick described below.

The paired `travelInAir` order is: `handleRelativeFrictionAndCalculateMovement` calls `Entity.move` using current delta movement; after it returns, `travelInAir` subtracts effective gravity and multiplies by vertical friction.

No gravity or friction modifier is added for this witness.

With default gravity `0.08` and float drag widened from `0.98F`, the tick sequence is:

## Movement meaning

The client `LocalPlayer` reaches `Player`/`Avatar`/`LivingEntity` movement and its `Entity.move` collision handling. `Player.canSimulateMovement()` returns true for the local client player. 26.1.2 bed support is selected as the effect block for the clipped landing.

The rewritten state is player Y velocity, consumed by subsequent local travel/ticks. 26.2's new BOUNCINESS attribute is part of living attributes and syncable; its base default is zero.

The paired bed registration is source evidence for the `0.75F` block value.

No damage calculation is emulated or compared; 26.1.2's bed `fallOn` damage multiplier is outside this finding.

The paired sources establish a different player Y-velocity write for the stated exact-contact witness.

[Archived report](../../archive/legacy-workflows/source-campaign-2026-10-07/26.1.2--26.2/findings/F-26.2-BED-LANDING-RESTITUTION.md).
