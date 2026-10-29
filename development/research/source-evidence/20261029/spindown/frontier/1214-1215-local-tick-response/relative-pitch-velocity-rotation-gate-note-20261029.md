# Gate note for the explicit zero-yaw witness

The zero-yaw packet witness in `relative-pitch-velocity-rotation-precision-20261029.md` is scoped to `ClientPacketListener.handleMovePlayer` only when its local player satisfies `!$$1.isPassenger()`. In 1.21.4 this gate is at lines 709–712; in 1.21.5 it is at lines 722–725. The guarded call passes `$$1` (the local player) to `setValuesFromPositionPacket`; if `isPassenger()` is true, this handler does not apply the packet to the player, so the witness makes no claim for that case.

Both releases use the non-interpolating local-player condition stated in the first correction supplement: the guarded call passes `false`, and direct `handleMovePlayer` application does not create an interpolation target. The concrete pitch/velocity values and `X_ROT`, `ROTATE_DELTA`, and `DELTA_Z` flags in the witness are additional preconditions inside this gate.

Exact sources: `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\1.21.4\mojmap\net\minecraft\client\multiplayer\ClientPacketListener.java`, lines 707–716, SHA-256 `eef170dd22b5711e7d9527601592093a0456590f192b54fefef170b5791165b4`; and the corresponding 1.21.5 file, lines 720–729, SHA-256 `284f34159a1baf30b6b540751ad2fa62344c179d5ed6a7bbcb5c888665779617`.

Bound precision supplement: `relative-pitch-velocity-rotation-precision-20261029.md`, SHA-256 `cf74016003c3740d5c1650849231e000013f3acba07f12d3886e7350cb47be1f`.
