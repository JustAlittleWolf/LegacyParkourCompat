# MD-05: Slime blocks damp horizontal velocity during low-speed ground contact

- **1.7.10**, ornithe-feather: `1.7.10/ornithe-feather/net/minecraft/entity/Entity.java`; lines 585-617.
- **1.8.9**, ornithe-feather: `1.8.9/ornithe-feather/net/minecraft/entity/Entity.java`; lines 575-605.

## Difference

On a 1.8.9 slime support block, each eligible grounded movement call reaches the callback before the step-distance threshold.

Low vertical speed and non-sneaking ground contact multiply horizontal velocity by `0.4 + abs(velocityY) * 0.2`. 1.7.10 only invokes its step callback after the distance threshold and has no slime block registration or corresponding override.

This is distinct from the vertical landing bounce and support slipperiness in MD-02 and MD-04.

## Movement meaning

Local player -> `LivingEntity.moveRelative` -> `Entity.move` -> grounded support block selection -> `onSteppedOn` -> `SlimeBlock` horizontal velocity scaling. 1.8.9's movement routine invokes the callback after collision resolution and fall handling.

Sneaking, vehicle state, step processing, support block and current vertical speed gate the behavior.

Source-proven: under the stated callback guards, 1.8.9 scales X/Z velocity by the stated factor.

Inference: this reduces horizontal carry during low-speed ground contact on slime.

[Archived report](../../archive/legacy-workflows/movement-discovery/runs/1.7.10--1.8.9/findings/MD-05-slime-ground-contact-damping.md).
