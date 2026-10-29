# Correction supplement: relative pitch velocity rotation

This append-only supplement narrows `relative-pitch-velocity-rotation.md`; the original coverage and finding files remain byte-for-byte unchanged. Their pre-supplement SHA-256 values are recorded below.

## Corrected preconditions

The pitch clamp changes the rotated prior velocity at `PositionMoveRotation.calculateAbsolute` only when all of these hold:

1. `Relative.ROTATE_DELTA` is present.
2. The calculated packet pitch before clamping is outside `[-90.0F, 90.0F]`, so the old and new pitch used for the pitch component of the rotation differ.
3. At least one of `Relative.DELTA_X`, `Relative.DELTA_Y`, or `Relative.DELTA_Z` is present, and the corresponding rotated prior-velocity component is nondegenerate: its value after the old pitch rotation differs from its value after the clamped pitch rotation. `calculateDelta` discards the rotated prior component for any unflagged component, so `ROTATE_DELTA` alone is insufficient. For a pitch-only difference, an example witness is a prior velocity with nonzero Y and a `DELTA_Z` flag; the X rotation changes Z, and that rotated component is carried into the resulting movement.

If no `DELTA_*` flag is present, all resulting delta components come from the packet and the rotation difference does not reach output velocity. If the only flagged components are unchanged by the altered rotation, the clamp likewise produces no output velocity difference.

## Interpolation qualification and player helper inputs

`ClientPacketListener.handleMovePlayer` calls `setValuesFromPositionPacket(..., false)` for the local player when it is not a passenger. In this direct correction branch, the helper writes position/rotation directly; it does not call `lerpTo` or establish interpolation. Restricting the comparison to a non-interpolating local player state, the base inputs passed to `calculateAbsolute` are equal across releases:

- In 1.21.4, `PositionMoveRotation.ofEntityUsingLerpTarget` calls `LivingEntity.lerpTargetX/Y/Z`; each returns its stored lerp target only while `lerpSteps > 0`, and otherwise returns the current coordinate. The rotation getters similarly return current X/Y rotation when `lerpSteps == 0`. For the non-interpolating local player, these are current position/rotation plus `getKnownMovement`.
- In 1.21.5, `PositionMoveRotation.of(Entity)` uses its interpolation-target branch only when `Entity.isInterpolating()` is true. `LivingEntity` supplies an `InterpolationHandler`; `isInterpolating` requires active interpolation steps. When it is false, `.of` uses current position/rotation plus the same `getKnownMovement`.

Therefore the `.of(Entity)` interpolation-target branch is not part of this local-player witness. For an actively interpolating living entity, 1.21.5 `.of(Entity)` selects the interpolation target, while 1.21.4 reads `lerpTarget*` when lerp steps are active; this supplement does not assume those helper inputs are equal for arbitrary interpolating entities or extend this finding to them.

The narrowed difference is the clamp at 1.21.5 `PositionMoveRotation.calculateAbsolute` line 42 before the `Relative.ROTATE_DELTA` pitch rotation at lines 44–48, compared with the unclamped sum at 1.21.4 lines 43–49. The result is written through the flagged `DELTA_*` components at lines 51–55 (1.21.5) / 52–56 (1.21.4). The actual final player pitch setter clamps in both releases, so no final-pitch difference is claimed. With the three preconditions above, however, a changed rotated prior-velocity component is added to the corresponding packet delta and can feed subsequent player movement.

This remains a source-only client-consumer claim. It does not claim that a server producer emits a packet with these flags or values.

## Retraced source locations

- 1.21.4 Mojmap: `ClientPacketListener.handleMovePlayer` / `setValuesFromPositionPacket`, lines 707–735; `PositionMoveRotation.ofEntityUsingLerpTarget` and `calculateAbsolute`, lines 27–57; `LivingEntity.lerpTo` and `lerpTargetX/Y/Z/XRot/YRot`, lines 2949–2981.
- 1.21.5 Mojmap: `ClientPacketListener.handleMovePlayer` / `setValuesFromPositionPacket`, lines 720–748; `PositionMoveRotation.of(Entity)` and `calculateAbsolute`, lines 24–56; `Entity.isInterpolating` / base `getInterpolation`, lines 2161–2177; `LivingEntity.getInterpolation`, line 2930; `InterpolationHandler` target accessors / active-step gate, lines 43–69.

Relevant source hashes, validated against the release source manifests:

- 1.21.4 `ClientPacketListener.java`: `eef170dd22b5711e7d9527601592093a0456590f192b54fefef170b5791165b4`
- 1.21.5 `ClientPacketListener.java`: `284f34159a1baf30b6b540751ad2fa62344c179d5ed6a7bbcb5c888665779617`
- 1.21.4 `PositionMoveRotation.java`: `74b39fb0907043885e7cfeb6376c8d39eba7131ee6befa2df57a08ea53571436`
- 1.21.5 `PositionMoveRotation.java`: `75baa0b6f99d1b9d18f157cbbf2c6d209b13af6719e960c7da1c451587433cc3`
- 1.21.4 `LivingEntity.java`: `e62ce650af0a5ac97e4d0a2ba7cad4ba68a8d098525609f8b5b6976a8ab0ae30`
- 1.21.5 `LivingEntity.java`: `a8aed863d4fdc515c751dd2878a8bb9c13179228cb8dbb50edf1d19cd5404271`
- 1.21.5 `InterpolationHandler.java`: `59a065f31a2d725d2ed5a4496dbb7580a4808abea432fd6afb4a5eef9233886a`

## Preserved source-review hashes

- `coverage.md`: `ad1a38270a10d43be3a0824a874ebd3486bb79974de87043929552b1192dd8a2`
- `relative-pitch-velocity-rotation.md`: `e334078e9aef5e35621bf50adb3cf3ad12f0a1b167647a87134a7db747bf2ffd`
