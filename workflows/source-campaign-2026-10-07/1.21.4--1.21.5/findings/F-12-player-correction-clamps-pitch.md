# F-12: Client position correction clamps the resulting pitch in 1.21.5

- Older version A: 1.21.4
- Newer version B: 1.21.5
- Mechanic / coverage slice IDs: S7-CORRECTIONS, S3-GLIDE
- Classification: changed behavior
- Confidence: source-confirmed
- Applicability: historical player behavior
- First changed release: unknown within (1.21.4, 1.21.5]
- Runtime validation: not performed

## Paired evidence

- A: `ready/1.21.4/mojmap.sources.sha256`; `ClientPacketListener#handleMovePlayer()` lines 707-715 passes `false` to `setValuesFromPositionPacket()`, whose direct-set branch writes the `PositionMoveRotation.calculateAbsolute()` result (ClientPacketListener SHA-256 `eef170dd22b5711e7d9527601592093a0456590f192b54fefef170b5791165b4`). `PositionMoveRotation#calculateAbsolute()` lines 35-63 computes pitch as `$$7 + $$1.xRot` without a clamp (SHA-256 `74b39fb0907043885e7cfeb6376c8d39eba7131ee6befa2df57a08ea53571436`).
- B: `ready/1.21.5/mojmap.sources.sha256`; `ClientPacketListener#handleMovePlayer()` lines 720-728 still passes `false`, and the direct-set branch writes `calculateAbsolute()` output (ClientPacketListener SHA-256 `284f34159a1baf30b6b540751ad2fa62344c179d5ed6a7bbcb5c888665779617`). `PositionMoveRotation#calculateAbsolute()` lines 34-62 now uses `Mth.clamp($$7 + $$1.xRot, -90.0F, 90.0F)` (SHA-256 `75baa0b6f99d1b9d18f157cbbf2c6d209b13af6719e960c7da1c451587433cc3`).
- Local-player context: A `Entity#lerpTargetX/Y/Z()` defaults return current coordinates, and LocalPlayer does not override them. B `Entity#getInterpolation()` returns null, and LocalPlayer does not override it. Thus the new interpolation-aware `PositionMoveRotation.of(Entity)` still uses the local player's current position/rotation/movement for this correction. Entity source hashes: A `05f18ef2ec0413fc010230407c812a11553eb5123b68d21b5d7b2c0c175698ad`; B `fc177733e9cc4b2d5cf2562e5529d0f4e0b9c3690f081fbd58aa9119eb173dc4`.

## Source-level difference

For a local-player correction whose resolved pitch falls outside `[-90°, 90°]`, A writes the computed pitch unchanged while B clamps it to the nearest endpoint. Position and delta-movement calculation remain on the direct-set branch because this handler passes `false`. Pitch is an input to fall-flying travel, so the stored state can select different glide math on a later travel tick. This does not establish how often ordinary servers emit out-of-range correction pitches.

## Reachability and dependencies

`ClientPacketListener#handleMovePlayer` handles a clientbound position correction for a non-passenger local player. It resolves relative fields and writes the resulting pitch through the correction helper. Fall-flying travel reads `getXRot()` in `LivingEntity#updateFallFlyingMovement`; surrounding branch conditions are covered separately by S3-GLIDE/F-06.

## Consequence and uncertainty

The paired source proves a different stored pitch whenever the computed correction pitch exceeds the new bounds. Downstream pitch-dependent movement can consume that difference. The source audit did not measure a resulting trajectory or verify server-side pitch limits.

## Handoff

Independent delta: B clamps the absolute pitch computed from a client position correction to `[-90°, 90°]`. Related finding IDs: F-06. Applicability requires a non-passenger local-player correction whose resolved pitch is outside that range. Exact first release within the pair is unknown.
