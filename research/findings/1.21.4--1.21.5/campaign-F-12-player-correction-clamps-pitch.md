# F-12: Client position correction clamps the resulting pitch in 1.21.5

- **1.21.4**, mojmap: `ClientPacketListener#handleMovePlayer()`; lines 707-715.
- **1.21.5**, mojmap: `ClientPacketListener#handleMovePlayer()`; lines 720-728.

## Difference

For a local-player correction whose resolved pitch falls outside `[-90°, 90°]`, 1.21.4 writes the computed pitch unchanged while 1.21.5 clamps it to the nearest endpoint.

Position and delta-movement calculation remain on the direct-set branch because this handler passes `false`.

Pitch is an input to fall-flying travel, so the stored state can select different glide math on a later travel tick.

This does not establish how often ordinary servers emit out-of-range correction pitches.

## Movement meaning

`ClientPacketListener#handleMovePlayer` handles a clientbound position correction for a non-passenger local player.

It resolves relative fields and writes the resulting pitch through the correction helper.

Fall-flying travel reads `getXRot()` in `LivingEntity#updateFallFlyingMovement`; surrounding branch conditions are covered separately by S3-GLIDE/F-06.

The paired source proves a different stored pitch whenever the computed correction pitch exceeds the new bounds.

Downstream pitch-dependent movement can consume that difference.

The source audit did not measure a resulting trajectory or verify server-side pitch limits.

[Archived report](../../archive/legacy-workflows/source-campaign-2026-10-07/1.21.4--1.21.5/findings/F-12-player-correction-clamps-pitch.md).
