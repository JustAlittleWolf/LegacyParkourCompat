# Water sprint horizontal drag

1.12.2, ornithe-feather; feather-gen2-1.12.2+build.2-mergedv2.jar, feather-gen2-1.12.2+build.2.tiny: `1.12.2/ornithe-feather/net/minecraft/entity/living/LivingEntity.java`, `public void moveRelative(float forwards, float sideways, float upwards) {`, lines 1425-1599.
1.13.2, ornithe-feather; feather-gen2-1.13.2+build.2-mergedv2.jar, feather-gen2-1.13.2+build.2.tiny: `1.13.2/ornithe-feather/net/minecraft/entity/living/LivingEntity.java`, `public void moveRelative(float forwards, float sideways, float upwards) {`, lines 1478-1669.

Difference: For the bounded water-travel preconditions, 1.12.2 X/Z drag uses 0.8F while 1.13.2 sprinting X/Z drag uses 0.9F. Depth Strider blending remains a separate input to the ordered coefficient computation.

Movement meaning: The same water velocity/input can retain a different horizontal component after drag. The operation is separately registered through WaterSprintSlowdownHook.

## Exact 1.13 source comparison

The first inspected release with a sprint-dependent water-drag base is 1.13, which starts sprinting drag at `0.9F`. The 1.12.2 base is `0.8F` regardless of sprinting. The base is selected before Depth Strider interpolation, with the level capped at `3.0F` and halved off-ground. The separate input coefficient starts at `0.02F` and uses `getSpeed()` for its interpolation. In 1.13, Dolphin's Grace overrides the resulting horizontal drag with `0.96F`. After movement, the float drag factor widens to double for the X/Z multiply. For post-move X `0.1` without either modifier, the retained X is `0.0800000011920929` at `0.8F` and `0.08999999761581422` at `0.9F`.

The 1.13.1 and 1.13.2 bodies retain the 1.13 expressions. The 1.14.4 control retains these horizontal expressions. The wrapper's swim-only Y adjustment and the production of movement speed remain separate. [Exact finding](../../source-evidence/20261117/block-a-water-travel-math/water-sprint-horizontal-drag.corrected-r1.md), [source comparison](../../source-evidence/20261117/block-a-water-travel-math/comparison.corrected-r1.md), [independent verdict](../../source-evidence/20261117/block-a-water-travel-math/verdict-r1.md).

## Sprint-factor continuity through 1.16.1

The checked 1.13–1.16.1 sources retain `0.9F` as the starting horizontal water-drag factor for an eligible non-flying local player already sprinting. With no Depth Strider or Dolphin's Grace, that factor reaches the post-move X/Z multiply directly. The intervening player wrappers can adjust Y in their swimming pre-step, and their water-entry conditions vary across releases. They do not change the horizontal sprint factor after the ordinary player reaches the water branch. The first 1.13 source comparison and its verdict remain linked above. [Continuity comparison](../../source-evidence/20261117/block-a-early-water-sprint-drag/sprint-continuity-comparison.corrected-r1.md), [independent continuity verdict](../../source-evidence/20261117/block-a-early-water-sprint-drag/sprint-continuity-verdict-r1.md).
