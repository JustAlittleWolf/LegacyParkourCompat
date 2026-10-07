# Source evidence: MCPK 1.8.9 → 1.9.4 claims

All source claims below use the canonical shared source handoff. The ready JSONs identify exact releases and `ornithe-feather` mapping. The source owner reports that source-file/raw-input hashes stayed unchanged but derived mapped JARs were replaced in the shared cache during a reproducibility rerun. These findings are provisional until canonical artifact-integrity repair and fresh verification; no mismatch is waived.

- 1.8.9: `ready/1.8.9/ornithe-feather`; source manifest SHA-256 `9e75f46dc0ed43b6a355bd65db8a92c93a4dfeaecfa92284187c6fe9410d8004`; artifact manifest SHA-256 `da003358256d1c4402ebb20614651e5410310e871ee913de2b9c1295a64e1446`; diagnostics SHA-256 `62dc9b445bec2f62b6dac9da501e875377636d08682891891212aea464999d28`; 1,612 source files, 37 artifacts.
- 1.9.4: `ready/1.9.4/ornithe-feather`; source manifest SHA-256 `c7b508fe01634887b65919dcd3a900c311a21d9510a1f1ab248d5c17c528ab19`; artifact manifest SHA-256 `9527dca544694daa3b4a7741be1a5b4802d8b6665c8a152a408a37a27a1b4d77`; diagnostics SHA-256 `51bd42a633c04931814ab78a841cedd3bf87460e04f7877676599b51b59bbb1d`; 1,819 source files, 37 artifacts.

## Movement math and jump apex

`LivingEntity.mobTick` in 1.8.9 (`net/minecraft/entity/living/LivingEntity.java:1385–1416`) independently zeroes `velocityX`, `velocityY`, and `velocityZ` when `Math.abs(component) < 0.005`. The same method in 1.9.4 (`:1645–1677`) uses `0.003`. These are strict `<` comparisons, operate on each component separately, and zero all three components. The check is at the beginning of the living-entity tick path, before the subsequent jump handling and travel/input work. Local player movement does not take the non-local `0.98` velocity interpolation branch immediately above it.

The ordinary jump path still starts with `getJumpStrength() == 0.42F` in both releases (`LivingEntity.java:1092–1099` and `:1275–1282`). In the terrestrial travel branch, the entity is moved with its current velocity first, then vertical velocity is updated in source order by subtracting the double literal `0.08` and multiplying by float literal `0.98F` (1.8.9 `:1181–1186`; 1.9.4 `:1408–1414`). At the next `mobTick`, the threshold is applied before the next movement.

Replaying that exact recurrence with the binary32 value of `0.42F`, double `0.08`, binary32 `0.98F` widened for multiplication, no jump boost, no liquid, and no collision gives the maximum vertical displacement:

- 1.8.9, cutoff `0.005`: `1.2491870787446813` blocks. After the fifth movement, the next velocity is `0.0030162615090425808`, so it is zeroed before the sixth movement.
- 1.9.4, cutoff `0.003`: `1.2522033402537238` blocks. The same sixth-tick velocity survives, moves the player upward once more, then becomes negative.

Thus the wiki's rounded 1.249→1.252 claim and its causal attribution to the lowered cutoff are **source-verified** for a standard unobstructed jump. Its threshold/formula pages describe the operation as happening “at the end” of a tick; the source checks at the next tick's start, after the previous travel's drag and before new jump/input work. This is a tick-indexing shorthand, not a different recurrence, when the velocity labels are aligned as the velocity carried from the last movement step.

## Sneaking dimensions

In 1.8.9, `PlayerEntity.getEyeHeight` subtracts `0.08F` while sneaking (`PlayerEntity.java:1640–1650`); there is no sneak-dependent `setSize` path. The player's normal size is reset to `0.6F × 1.8F` at `PlayerEntity.java:417` and `:1163`.

In 1.9.4, `PlayerEntity.updatePlayerPose` (`PlayerEntity.java:285–307`, called at `:245`) selects width `0.6F` and height `1.65F` while sneaking. It applies the new size only when the prospective box has no world collisions. Outside that case it retains the previous size. The wiki's 1.8→1.9 height change is **source-verified with this collision guard**; the exact first patch release is only pinned to the available 1.9.4 endpoint, while the wiki labels the boundary “1.9.”

## Legacy block collision shapes

- **Ladders:** 1.8.9 `LadderBlock.updateShape` uses thickness `0.125F` for each facing. 1.9.4 `LadderBlock` uses per-facing shapes of thickness `0.1875`. The reported one-pixel increase is **source-verified**.
- **Panes/bars:** 1.8.9 `PaneBlock.addCollisions` builds a cross for the neutral state from two perpendicular 2-pixel-wide segments. 1.9.4 `PaneBlock.SHAPES[0]` is a centered `0.125 × 1 × 0.125` pole, with state-resolved arms extending to adjacent edges. This verifies the neutral-shape change and state-dependent segment extension. The exact “internal segments are one pixel longer” description is **source-verified** by the 1.9.4 9/16 reach from the centered pole, versus 8/16 reach in 1.8.9. The barrier-connection fix in 1.12 remains pending source for 1.12.2.
- **Lily pads:** 1.8.9 shape is `1 × 0.015625 × 1`; 1.9.4 is `0.875 × 0.09375 × 0.875`. The wiki dimensions are **source-verified**.
- **Piston heads:** in 1.8.9 `PistonHeadBlock.setArmShapeForCollisions`, `case WEST` supplies a cross-axis arm box (`x=0.375..0.625`, `z=0.25..1.0`) rather than the westward protrusion. 1.9.4 uses `WEST_ARM_SHAPE` (`x=0.25..1.25`, `z=0.375..0.625`). The west-facing extension correction is **source-verified**.
- **Single snow layer:** 1.8.9 and 1.9.4 both return a zero-height collision box for `LAYERS == 1` (`SnowLayerBlock.getCollisionShape`: top is `(layers - 1) × 0.125`). There is **no collision-height change at this boundary**. The separate MCPK claim that a single layer becomes intangible in 1.13 remains pending the 1.12.2/1.13.2 source comparison.
- **Chests/anvils:** 1.8.9 updates mutable block-instance shape bounds from nearby chest/facing state; 1.9.4 returns a box for the queried state/world instead (`ChestBlock.getShape`, `AnvilBlock.getShape`). This supports the MCPK manipulation-page claim that the cross-variant bounds trick was patched. “Fixed collision box” is imprecise if read as one invariant box: double chests still depend on neighbors and anvils still depend on orientation. The source-backed change is per-query/per-variant bounds instead of the old shared mutable bounds.

The MCPK labels the relevant movement/shape boundary “1.9.” The checked endpoint diff proves these deltas are present by 1.9.4, but this two-endpoint check alone does not prove whether any individual change first shipped in 1.9.0 or a later 1.9 patch.

