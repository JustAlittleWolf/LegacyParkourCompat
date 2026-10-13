# MD-04: Slime blocks provide a distinct ground-friction value

- Older version A: 1.7.10
- Newer version B: 1.8.9
- Mechanic / coverage slice IDs: stages 3.1 and 5.1
- Classification: added mechanic
- Confidence: source-confirmed
- Applicability: historical player behavior
- First changed release: unknown within (1.7.10, 1.8.9]
- Runtime validation: not performed

## Paired evidence

- A artifact A; `../../../../decompiled_minecraft/1.7.10/ornithe-feather/net/minecraft/block/Blocks.java`; the static block registration inventory has no slime block; SHA-256 `B912E4C682F9E1FFECD916F3EE5CC6FF1BBA3196E0634E20442B618A0A062E11`.
- B artifact B; `../../../../decompiled_minecraft/1.8.9/ornithe-feather/net/minecraft/block/Blocks.java:193,401` declares and resolves `SLIME`; `SlimeBlock.java:11-16` assigns `slipperiness = 0.8F`; hashes `1DA2D85406ED991E8F6BC1F420BABF0BD43598478F347C6A4DC9FEA322ED0695` and `FA00F5B8903FA580E860CD72CFC11827A06EAAD4600D05BD0F209849B04CBAFA`.
- B consumer; `../../../../decompiled_minecraft/1.8.9/ornithe-feather/net/minecraft/entity/living/LivingEntity.java`; `LivingEntity.moveRelative(float,float)`; lines 1123-1148; SHA-256 `082831C6578E3A70FA6CEA5B90BC3EEFC26678259B66334470DE22B90B5B0E4E`. On ground, the support block's slipperiness is multiplied by `0.91F`; the resulting value feeds `0.16277136F / (i * i * i)` for input acceleration, and is read again for post-move X/Z velocity drag.

## Source-level difference

B introduces a slime surface whose slipperiness is `0.8F`. When the player is grounded on it, the living movement routine uses that value in both the ground-acceleration calculation and the post-move horizontal drag. A has no slime block registration, so this block-conditioned surface behavior does not exist in the older release.

## Reachability and dependencies

Local player `moveRelative(sidewaysSpeed, forwardSpeed)` -> grounded support block lookup -> `slipperiness * 0.91F` -> input acceleration factor and later X/Z drag. B's block registry resolves the `slime` entry to `SlimeBlock`; A's `Blocks` source has no such registration. Collision support and on-ground state select the branch. The friction calculation is independent of the separate slime bounce and low-speed step callbacks in MD-02.

## Consequence and uncertainty

Source-proven: grounded movement on slime uses the block's `0.8F` slipperiness in acceleration and horizontal drag. Inference: movement starting or continuing on slime can differ from the same input on other ground. No trajectory was tested. The block's introduction point is unknown within `(1.7.10, 1.8.9]`.

## Handoff

Model the slime slipperiness through the grounded support-block lookup and preserve its use at both reads in `moveRelative`. Keep this friction delta separate from slime landing response. Runtime validation is deferred.
