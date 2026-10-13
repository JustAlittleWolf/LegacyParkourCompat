# F-PANE-OFFSET-EPSILON — pane center collision clipped differently at an axis boundary (float and shape corrected snapshot r3)

- **1.12.2**, ornithe-feather: [source locations in preserved report](../../archive/legacy-workflows/source-campaign-2026-10-07/1.12.2--1.13.2/findings/F-PANE-OFFSET-EPSILON-r3.md).
- **1.13.2**, ornithe-feather: [source locations in preserved report](../../archive/legacy-workflows/source-campaign-2026-10-07/1.12.2--1.13.2/findings/F-PANE-OFFSET-EPSILON-r3.md).

## Difference

Use the registered `glass_pane` at `(0,64,0)`, with AIR in the four horizontal neighbor positions and no water.

The 1.12.2 state resolves to four false connection flags; the 1.13.2 placement/default path yields the same flags and `WATERLOGGED=false`.

Both providers contribute only the center pane voxel for this state.

Use a locally controlled airborne player positioned by `Entity.setPosition(0.13749,64.2,0.13750005)`.

The exact resulting double box is X `[-0.16251001192092895,0.43749001192092896]`, Y `[64.2,65.99999995231629]`, Z `[-0.16249996192092894,0.43750006192092894]`, using the float `f` and `g` conversions and operation order cited above.

No other collision shapes, entities, or world-border contact are present.

Set horizontal input to zero and deliver the equivalent entity-velocity packet with X integer `25` and Y/Z integers `0`.

The paired packet handler and `lerpVelocity` set `velocityX=25/8000.0=0.003125`.

The local-player path skips remote-velocity damping, and `0.003125` is not below the strict `0.003` cutoff.

Zero input adds no velocity.

On the ordinary airborne, non-liquid, non-fall-flying path, the next `moveRelative` call therefore passes `(0.003125,0,0)` to `Entity.move` in both versions.

## Movement meaning

Use the registered `glass_pane` at `(0,64,0)`, with AIR in the four horizontal neighbor positions and no water.

The 1.12.2 state resolves to four false connection flags; the 1.13.2 placement/default path yields the same flags and `WATERLOGGED=false`.

Both providers contribute only the center pane voxel for this state.

Use a locally controlled airborne player positioned by `Entity.setPosition(0.13749,64.2,0.13750005)`.

The exact resulting double box is X `[-0.16251001192092895,0.43749001192092896]`, Y `[64.2,65.99999995231629]`, Z `[-0.16249996192092894,0.43750006192092894]`, using the float `f` and `g` conversions and operation order cited above.

No other collision shapes, entities, or world-border contact are present.

Set horizontal input to zero and deliver the equivalent entity-velocity packet with X integer `25` and Y/Z integers `0`.

The paired packet handler and `lerpVelocity` set `velocityX=25/8000.0=0.003125`.

The local-player path skips remote-velocity damping, and `0.003125` is not below the strict `0.003` cutoff.

Zero input adds no velocity.

On the ordinary airborne, non-liquid, non-fall-flying path, the next `moveRelative` call therefore passes `(0.003125,0,0)` to `Entity.move` in both versions.

[Archived report](../../archive/legacy-workflows/source-campaign-2026-10-07/1.12.2--1.13.2/findings/F-PANE-OFFSET-EPSILON-r3.md).
