# F-PANE-OFFSET-EPSILON — pane center collision clipped differently at an axis boundary

- **1.12.2**, ornithe-feather: [source locations in preserved report](../../archive/legacy-workflows/source-campaign-2026-10-07/1.12.2--1.13.2/findings/F-PANE-OFFSET-EPSILON.md).
- **1.13.2**, ornithe-feather: [source locations in preserved report](../../archive/legacy-workflows/source-campaign-2026-10-07/1.12.2--1.13.2/findings/F-PANE-OFFSET-EPSILON.md).

## Difference

Use the registered `glass_pane` at `(0,64,0)`, with AIR in the four horizontal neighbor positions and no water.

The 1.12.2 state resolves to four false connection flags; the 1.13.2 placement/default path yields the same flags and `WATERLOGGED=false`.

Both providers contribute only the center pane voxel for this state.

Use a locally controlled airborne player with base box X `[-0.16251,0.43749]`, Y `[64.2,66.0]`, Z `[-0.16249995,0.43750005]`; this is width `0.6`, height `1.8`, centered at `(0.13749,64.2,0.13750005)`.

No other collision shapes, entities, or world-border contact are present.

Set horizontal input to zero and deliver the equivalent entity-velocity packet with X integer `25` and Y/Z integers `0`.

The paired packet handler and `lerpVelocity` set `velocityX=25/8000.0=0.003125`.

The local-player path skips remote-velocity damping, and `0.003125` is not below the strict `0.003` cutoff.

Zero input adds no velocity.

On the ordinary airborne, non-liquid, non-fall-flying path, the next `moveRelative` call therefore passes `(0.003125,0,0)` to `Entity.move` in both versions.

In 1.12.2, the movement-expanded collision query reaches maxX `0.43749 + 0.003125 = 0.440615`, so it strictly overlaps the pane center box beginning at X `0.4375`.

Its Y overlap is `[64.2,65]`; its Z overlap is `0.00000005`.

The pane is collected by strict translated-box intersection.

The subsequent positive-X clip uses the original player box and leaves `0.4375 - 0.43749 = 0.00001`, so 1.12.2 applies X `0.00001`.

In 1.13.2, the expanded swept query also collects the pane shape; the effect is not a broadphase omission.

For the X-axis clip, `VoxelShape.calculateMaxDistance` calculates the orthogonal Z upper index from `box.maxZ - 1.0E-7 = 0.43749995`, below the center voxel's lower bound `7/16 = 0.4375`.

On the pane's sixteenth-block grid, the exclusive range ends before center index 7, so no center voxel is visited for X clipping. 1.13.2 applies the full requested X `0.003125`.

The difference is confined to this exact query and state.

Panes existed in both endpoint versions.

The 1.13.2 waterlogging bit is false; all other collision candidates are excluded by the stated world conditions.

## Movement meaning

Use the registered `glass_pane` at `(0,64,0)`, with AIR in the four horizontal neighbor positions and no water.

The 1.12.2 state resolves to four false connection flags; the 1.13.2 placement/default path yields the same flags and `WATERLOGGED=false`.

Both providers contribute only the center pane voxel for this state.

Use a locally controlled airborne player with base box X `[-0.16251,0.43749]`, Y `[64.2,66.0]`, Z `[-0.16249995,0.43750005]`; this is width `0.6`, height `1.8`, centered at `(0.13749,64.2,0.13750005)`.

No other collision shapes, entities, or world-border contact are present.

Set horizontal input to zero and deliver the equivalent entity-velocity packet with X integer `25` and Y/Z integers `0`.

The paired packet handler and `lerpVelocity` set `velocityX=25/8000.0=0.003125`.

The local-player path skips remote-velocity damping, and `0.003125` is not below the strict `0.003` cutoff.

Zero input adds no velocity.

On the ordinary airborne, non-liquid, non-fall-flying path, the next `moveRelative` call therefore passes `(0.003125,0,0)` to `Entity.move` in both versions.

In 1.12.2, the movement-expanded collision query reaches maxX `0.43749 + 0.003125 = 0.440615`, so it strictly overlaps the pane center box beginning at X `0.4375`.

Its Y overlap is `[64.2,65]`; its Z overlap is `0.00000005`.

The pane is collected by strict translated-box intersection.

The subsequent positive-X clip uses the original player box and leaves `0.4375 - 0.43749 = 0.00001`, so 1.12.2 applies X `0.00001`.

In 1.13.2, the expanded swept query also collects the pane shape; the effect is not a broadphase omission.

For the X-axis clip, `VoxelShape.calculateMaxDistance` calculates the orthogonal Z upper index from `box.maxZ - 1.0E-7 = 0.43749995`, below the center voxel's lower bound `7/16 = 0.4375`.

On the pane's sixteenth-block grid, the exclusive range ends before center index 7, so no center voxel is visited for X clipping. 1.13.2 applies the full requested X `0.003125`.

The difference is confined to this exact query and state.

Panes existed in both endpoint versions.

The 1.13.2 waterlogging bit is false; all other collision candidates are excluded by the stated world conditions.

[Archived report](../../archive/legacy-workflows/source-campaign-2026-10-07/1.12.2--1.13.2/findings/F-PANE-OFFSET-EPSILON.md).
