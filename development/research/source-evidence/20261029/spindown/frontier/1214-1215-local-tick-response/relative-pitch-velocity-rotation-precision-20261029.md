# Precision supplement: require a changed component after both rotations

Auditor note: `de9620f474b287a0d03b1be44d96eda4fc4b7386908c6c4136963f2f4d9cb022` observes that the first correction supplement tested a component after X rotation only. This append-only precision supplement narrows that criterion to the final component after the complete ordered rotation. All earlier artifacts remain untouched.

## Corrected component condition

Along with the existing pitch-bound and `Relative.ROTATE_DELTA` conditions, at least one component enabled by `Relative.DELTA_X`, `Relative.DELTA_Y`, or `Relative.DELTA_Z` must differ **after both ordered operations** in `calculateAbsolute`: first `Vec3.xRot(pitchDelta)`, then `Vec3.yRot(yawDelta)`. The component used by `calculateDelta` must be compared after that second rotation. A difference after X rotation can be canceled or moved by the later Y rotation, so it alone does not witness output-velocity divergence.

## Explicit zero-yaw witness

A non-interpolating local player has current pitch `80°`, current yaw `0°`, and prior velocity `(0, 1, 0)`. The received player position packet carries a `+20°` relative X rotation and has flags `X_ROT`, `ROTATE_DELTA`, and `DELTA_Z`; there is no Y rotation flag and the packet's relative yaw component is zero. Set the packet's Z delta component to zero.

- 1.21.4 computes target pitch `100°`, hence pitch rotation `80° - 100° = -20°`; target yaw remains `0°`, so the following Y rotation is `0°`.
- 1.21.5 clamps target pitch to `90°`, hence pitch rotation `80° - 90° = -10°`; target yaw remains `0°`, so the following Y rotation is also `0°`.
- `Vec3.xRot(a)` maps `(0, 1, 0)` to `(0, cos(a), -sin(a))`; `Vec3.yRot(0)` preserves that vector. Thus final Z is `sin(20°)` in 1.21.4 and `sin(10°)` in 1.21.5, which differ. Because `DELTA_Z` is present, `calculateDelta` adds the packet's zero Z delta to that final rotated Z component and preserves the difference in the returned movement.

This is a concrete source-level witness for the narrowed candidate. It does not claim that a server producer emits this packet or flag combination.

## Exact source trace and identity

1.21.4 Mojmap: `PositionMoveRotation.calculateAbsolute`, lines 35–57; ordered calls at lines 48–49; flagged component merge at lines 52–61. `ClientPacketListener.handleMovePlayer` supplies the local-player path at lines 707–735. `Vec3.xRot` / `Vec3.yRot`, lines 221–237.

1.21.5 Mojmap: `PositionMoveRotation.calculateAbsolute`, lines 34–56; ordered calls at lines 47–48; flagged component merge at lines 51–60. `ClientPacketListener.handleMovePlayer` supplies the local-player path at lines 720–748. `Vec3.xRot` / `Vec3.yRot`, lines 221–237.

Validated source SHA-256 values:

- 1.21.4 `PositionMoveRotation.java`: `74b39fb0907043885e7cfeb6376c8d39eba7131ee6befa2df57a08ea53571436`
- 1.21.5 `PositionMoveRotation.java`: `75baa0b6f99d1b9d18f157cbbf2c6d209b13af6719e960c7da1c451587433cc3`
- 1.21.4 and 1.21.5 `Vec3.java`: `62581d3005cc32401545f9229a9c293dfd61c52e7d30f88456c49d2c2867e68d`
- 1.21.4 `ClientPacketListener.java`: `eef170dd22b5711e7d9527601592093a0456590f192b54fefef170b5791165b4`
- 1.21.5 `ClientPacketListener.java`: `284f34159a1baf30b6b540751ad2fa62344c179d5ed6a7bbcb5c888665779617`

## Preserved artifact hashes

- Original `coverage.md`: `ad1a38270a10d43be3a0824a874ebd3486bb79974de87043929552b1192dd8a2`
- Original `relative-pitch-velocity-rotation.md`: `e334078e9aef5e35621bf50adb3cf3ad12f0a1b167647a87134a7db747bf2ffd`
- First correction `relative-pitch-velocity-rotation-correction-20261029.md`: `a7d0d473c307685f9cb7fd74a1679af535d5ad2f1b1089b65cea356e2bed8ac1`
- First correction SHA sidecar: `f68cbfe141d71b4d5c4ab8bb14707f2f19c83c41a580458ee2522845378dfda8`
