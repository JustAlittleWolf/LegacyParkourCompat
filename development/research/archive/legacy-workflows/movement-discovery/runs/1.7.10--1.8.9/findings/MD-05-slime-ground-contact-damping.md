# MD-05: Slime blocks damp horizontal velocity during low-speed ground contact

- Older version A: 1.7.10
- Newer version B: 1.8.9
- Mechanic / coverage slice IDs: stages 4.1 and 5.2
- Classification: added mechanic
- Confidence: source-confirmed
- Applicability: historical player behavior
- First changed release: unknown within (1.7.10, 1.8.9]
- Runtime validation: not performed

## Paired evidence

- A artifact A; `../../../../decompiled_minecraft/1.7.10/ornithe-feather/net/minecraft/entity/Entity.java`; `Entity.move(double,double,double)`; lines 585-617; SHA-256 `AF4EF7B11BC709D96BD3EEA1260A61D38A1A04996BDF87F2A77C459DDF070A69`. A invokes `onSteppedOn` only after `distanceMoved > blocksWalkedOn`; no slime block or equivalent ground-contact override is registered.
- B artifact B; `../../../../decompiled_minecraft/1.8.9/ornithe-feather/net/minecraft/entity/Entity.java`; `Entity.move(double,double,double)`; lines 575-605; SHA-256 `D4C10932CB5BB1067A5A58BE4E1BB1B42BDDE3B6FC893D88178CC07435A1696B`. B invokes `block.onSteppedOn(world, blockPos, this)` on every eligible grounded `move` call before the step-distance threshold used for footsteps. Eligibility requires the step path, not sneaking, no vehicle, non-null support and `onGround`. `Blocks.java:193,401` registers slime; `SlimeBlock.java:42-49` implements its override. Hashes: `1DA2D85406ED991E8F6BC1F420BABF0BD43598478F347C6A4DC9FEA322ED0695` and `FA00F5B8903FA580E860CD72CFC11827A06EAAD4600D05BD0F209849B04CBAFA`.
- B `SlimeBlock.onSteppedOn` tests `Math.abs(entity.velocityY) < 0.1 && !entity.isSneaking()`, then computes `0.4 + Math.abs(entity.velocityY) * 0.2` and multiplies X and Z velocity by that value. The block source SHA-256 is recorded above.

## Source-level difference

On a B slime support block, each eligible grounded movement call reaches the callback before the step-distance threshold. Low vertical speed and non-sneaking ground contact multiply horizontal velocity by `0.4 + abs(velocityY) * 0.2`. A only invokes its step callback after the distance threshold and has no slime block registration or corresponding override. This is distinct from the vertical landing bounce and support slipperiness in MD-02 and MD-04.

## Reachability and dependencies

Local player -> `LivingEntity.moveRelative` -> `Entity.move` -> grounded support block selection -> `onSteppedOn` -> `SlimeBlock` horizontal velocity scaling. B's movement routine invokes the callback after collision resolution and fall handling. Sneaking, vehicle state, step processing, support block and current vertical speed gate the behavior.

## Consequence and uncertainty

Source-proven: under the stated callback guards, B scales X/Z velocity by the stated factor. Inference: this reduces horizontal carry during low-speed ground contact on slime. No trajectory was tested. The introduction release is unknown within `(1.7.10, 1.8.9]`.

## Handoff

Preserve the callback condition, threshold, expression order and both velocity multiplications. Do not merge this with bounce or slipperiness behavior. Runtime validation is deferred.
