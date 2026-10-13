# F-01: sprint air speed reads current sprint state and changes by one float ULP

- **1.19.3**, mojmap: `1.19.3/mojmap/net/minecraft/world/entity/player/Player.java`; lines 496–517.
- **1.19.4**, mojmap: `1.19.4/mojmap/net/minecraft/world/entity/player/Player.java`; lines 499–510.

## Difference

For ordinary non-passenger, non-fall-flying player travel outside abilities flight, 1.19.3's airborne movement consumes stored `flyingSpeed` during the superclass travel call. `Player#aiStep` writes that field only after its superclass returns, so a sprint transition made by `LocalPlayer#aiStep` is reflected in the air-speed field on the following player tick. 1.19.4 obtains the speed through `Player#getFlyingSpeed()` during travel, so the current sprint flag controls the current travel call.

The constants also differ at single precision. 1.19.3 evaluates `0.02F` then adds `0.006F`; as Java `float`, this is `0.026000000536441803` (bits `0x3cd4fdf4`). 1.19.4's `0.025999999F` is `0.025999998673796654` (bits `0x3cd4fdf3`).

They differ by one representable float step. 1.19.4's ability-flight branch is separate and outside this finding.

## Movement meaning

`LocalPlayer#aiStep` sprint state -> `Player#aiStep` / `LivingEntity#aiStep` -> `LivingEntity#travel` -> relative-friction helper -> airborne speed consumer. 1.19.3's consumer reads the field written after superclass travel; 1.19.4's consumer calls the player override during travel.

Grounded friction uses block friction instead and is not covered here.

Source proves a one-tick state-read timing difference and a one-ULP coefficient difference in the ordinary airborne relative movement input.

Other movement branches and modifiers remain under audit.

[Archived report](../../archive/legacy-workflows/source-campaign-2026-10-07/1.19.3--1.19.4/findings/F-01-sprint-air-speed.md).
