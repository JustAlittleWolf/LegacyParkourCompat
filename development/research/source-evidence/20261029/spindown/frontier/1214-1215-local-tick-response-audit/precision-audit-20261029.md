# Independent audit supplement: completed pitch/yaw rotation witness

## Verdict

**Accept the correction’s complete-rotation criterion and zero-yaw velocity witness.** The exact `calculateAbsolute` order is `Vec3.xRot(pitchDelta)` followed by `Vec3.yRot(yawDelta)`, and the correction now tests the flagged component after both operations. With zero yaw delta, prior helper movement `(0, 1, 0)`, current pitch 80°, current yaw 0°, packet target pitch 100° (relative `X_ROT` +20°), `ROTATE_DELTA`, `DELTA_Z`, packet yaw 0° and packet Z delta 0, the returned Z movement differs: 1.21.4 yields `sin(20°)` and 1.21.5 yields `sin(10°)` from the changed X rotation. The `DELTA_Z` branch preserves that final component by adding the same zero packet component.

The explicit witness should also state the caller gate that the local player is not a passenger. `handleMovePlayer` does not apply the packet to the player when `isPassenger()` is true. The original report already documents this gate, but this witness supplement should repeat it next to its state assumptions. Subject to that caller qualification, the candidate is source-supported and remains limited to the client consumer.

## Revision binding

- Precision supplement audited: `.native-source/20261028/frontier-c/1214-1215-local-tick-response/relative-pitch-velocity-rotation-precision-20261029.md`, SHA-256 `cf74016003c3740d5c1650849231e000013f3acba07f12d3886e7350cb47be1f`.
- First correction supplement: `relative-pitch-velocity-rotation-correction-20261029.md`, SHA-256 `a7d0d473c307685f9cb7fd74a1679af535d5ad2f1b1089b65cea356e2bed8ac1`.
- Original finding and first audit remain byte-identical to their previously bound hashes; no prior artifact was edited.

Canonical read-only inputs are the 1.21.4 and 1.21.5 Mojmap source trees. Their release-parent provenance and ready markers, source manifest identities, and absence of mapping-root `.decompile-provenance.json` are documented in the prior independent audit. I independently rehashed the sources relevant to this supplement; they match the source manifest rows and prior audit identities:

| Source | 1.21.4 SHA-256 | 1.21.5 SHA-256 |
|---|---|---|
| `net/minecraft/world/entity/PositionMoveRotation.java` | `74b39fb0907043885e7cfeb6376c8d39eba7131ee6befa2df57a08ea53571436` | `75baa0b6f99d1b9d18f157cbbf2c6d209b13af6719e960c7da1c451587433cc3` |
| `net/minecraft/world/phys/Vec3.java` | `62581d3005cc32401545f9229a9c293dfd61c52e7d30f88456c49d2c2867e68d` | `62581d3005cc32401545f9229a9c293dfd61c52e7d30f88456c49d2c2867e68d` |
| `net/minecraft/world/entity/Relative.java` | `292b6d9f3f28dcd69ba60d02e218158bf92b61464597dd21529b0e841c54c0d6` | `292b6d9f3f28dcd69ba60d02e218158bf92b61464597dd21529b0e841c54c0d6` |
| `net/minecraft/client/multiplayer/ClientPacketListener.java` | `eef170dd22b5711e7d9527601592093a0456590f192b54fefef170b5791165b4` | `284f34159a1baf30b6b540751ad2fa62344c179d5ed6a7bbcb5c888665779617` |

## Independent math and gate check

`PositionMoveRotation.calculateAbsolute` begins with the prior `deltaMovement` vector. Under `Relative.ROTATE_DELTA`, both releases compute pitch delta from the current helper pitch minus the calculated target pitch, rotate by that value through `Vec3.xRot`, then rotate the intermediate vector by yaw delta through `Vec3.yRot`. The only intended difference is that 1.21.5 clamps the target pitch before this sequence.

For the witness, `Relative.X_ROT` makes target pitch `80 + 20 = 100` in 1.21.4 and `clamp(100, -90, 90) = 90` in 1.21.5. Thus pitch deltas are −20° and −10°. Since the packet target yaw equals the current yaw, yaw delta is 0° and `yRot(0)` preserves the X-rotated vector. The exact `Vec3.xRot` equations give `(0, 1, 0) → (0, cos(a), -sin(a))`; for the two negative angles the Z components are respectively `sin(20°)` and `sin(10°)`. They differ. `Relative.DELTA_Z` causes `calculateDelta` to return prior rotated Z plus packet Z, so a zero packet Z preserves that difference. Other components without their respective `DELTA_*` flags take the packet values and are not part of this witness.

The enum defines `X_ROT`, `DELTA_Z`, and `ROTATE_DELTA` as separate bits, and `Relative.unpack` decodes each set bit independently. The incoming packet representation accepts the relative set; no source-side exclusivity rule invalidates the combination. This does not establish which server producer emits it.

`ClientPacketListener.handleMovePlayer` runs `setValuesFromPositionPacket` only when the local player is not a passenger. With the already-verified non-interpolating state, helper inputs use current pitch/yaw and the direct listener branch writes returned delta movement to the player. `Entity.setXRot` clamps the final stored pitch in both releases. No final-pitch difference is inferred.

No tests, builds, game or server launches were performed.
