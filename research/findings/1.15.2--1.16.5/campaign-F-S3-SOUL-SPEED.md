# F-S3-SOUL-SPEED: Soul Speed changes player movement on tagged support blocks

- **1.15.2**, mojmap: `net/minecraft/world/entity/Entity.java`; lines 2027-2030.
- **1.16.5**, mojmap: `net/minecraft/world/entity/Entity.java`; lines 576-577.

## Difference

1.16.5 adds a Soul Speed feet enchantment.

For a living entity on a tagged support block with Soul Speed level greater than zero, `LivingEntity#getBlockSpeedFactor` returns `1.0F` instead of the support block's normal speed factor. `Entity#move` multiplies the x and z components of delta movement by that factor.

On shared Soul Sand, registered with the normal factor `0.4F` at both endpoints, this removes that horizontal reduction for the eligible 1.16.5 player.

The Player override disables the special path while flying or fall-flying.

1.16.5 also calls `tryAddSoulSpeed` from fall-damage and block-change paths.

When the feet block is non-air, Soul Speed level is positive, and the movement block is tagged, that method adds a movement-speed attribute modifier with amount `0.03F * (1 + level * 0.35F)`; the player `getSpeed()` consumes the movement-speed attribute.

This is a separate additive route from the block-factor override. 1.16.5's Player flight override on `onSoulSpeedBlock` and its block-factor override prevent applying the ordinary walking benefit while flying.

## Movement meaning

The source establishes a 1.16.5-only player movement mechanic.

Soul Speed is registered only in 1.16.5, is an enchantment for feet equipment, and 1.15.2 has no matching enchantment or movement modifier.

The shared Soul Sand block is a valid support in both versions; 1.16.5's Soul Soil tag member is modern-only and is not used as evidence of historical compatibility.

Cross-version switching is undefined behavior.

For an eligible non-flying 1.16.5 player on shared Soul Sand, the block-speed multiplier path changes from the normal `0.4F` factor to `1.0F`, and the Soul Speed attribute route can further affect movement speed.

The source proves these movement inputs and formulas; net displacement still depends on input, prior velocity, collision, and later tick processing.

[Archived report](../../archive/legacy-workflows/source-campaign-2026-10-07/1.15.2--1.16.5/findings/F-S3-SOUL-SPEED.md).
