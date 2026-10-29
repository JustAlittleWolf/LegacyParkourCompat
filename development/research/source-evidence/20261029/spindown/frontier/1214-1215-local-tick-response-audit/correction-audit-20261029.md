# Independent audit supplement: relative pitch velocity correction

## Verdict

**Accept the correction’s narrowed source behavior and non-interpolating local-player applicability.** I independently rechecked the correction supplement against the exact Mojmap sources. Its central corrections hold: `ROTATE_DELTA` alone cannot affect returned velocity, and the source-specific 1.21.5 interpolation target is excluded by requiring a non-interpolating local player with equal helper inputs.

One further precision is needed in corrected precondition 3. A flagged component must differ after the complete `xRot` then `yRot` sequence, not merely after the pitch (`xRot`) step. The helper applies yaw rotation after pitch rotation. A changed Y/Z intermediate component can move into a different axis or be absent from the specific flagged axis after yaw rotation. Thus the exact condition is: for at least one flagged `DELTA_*` axis, the corresponding component of `(priorVelocity.xRot(oldPitchDelta)).yRot(yawDelta)` differs from that component of `(priorVelocity.xRot(clampedPitchDelta)).yRot(yawDelta)`. The example can make this exact by specifying a zero yaw delta and a prior vector `(0,1,0)`, with `X_ROT`, `ROTATE_DELTA`, and `DELTA_Z`; set packet delta movement to zero. That gives distinct Z components after the full rotation.

This is a precision supplement to the correction, not a retraction of the clamp operation. The original report, original independent audit, and correction remain unchanged. No claim is made about packet producers or subsequent runtime trajectory.

## Audited revisions and evidence identity

- Correction input: `.native-source/20261028/frontier-c/1214-1215-local-tick-response/relative-pitch-velocity-rotation-correction-20261029.md`, SHA-256 `a7d0d473c307685f9cb7fd74a1679af535d5ad2f1b1089b65cea356e2bed8ac1`.
- Original finding remains SHA-256 `e334078e9aef5e35621bf50adb3cf3ad12f0a1b167647a87134a7db747bf2ffd`.
- Original coverage remains SHA-256 `ad1a38270a10d43be3a0824a874ebd3486bb79974de87043929552b1192dd8a2`.
- Original independent audit remains SHA-256 `de9620f474b287a0d03b1be44d96eda4fc4b7386908c6c4136963f2f4d9cb022`.

The canonical source identity is the already-audited 1.21.4/1.21.5 Mojmap release-parent ready/provenance pair and verified source manifests. Both mapping roots lack `.decompile-provenance.json`; no embedded-manifest identity is asserted. I rehashed the relevant selected source files, and their bytes match the hashes below and the release source manifests.

| Canonical Mojmap source | 1.21.4 SHA-256 | 1.21.5 SHA-256 |
|---|---|---|
| `ClientPacketListener.java` | `eef170dd22b5711e7d9527601592093a0456590f192b54fefef170b5791165b4` | `284f34159a1baf30b6b540751ad2fa62344c179d5ed6a7bbcb5c888665779617` |
| `PositionMoveRotation.java` | `74b39fb0907043885e7cfeb6376c8d39eba7131ee6befa2df57a08ea53571436` | `75baa0b6f99d1b9d18f157cbbf2c6d209b13af6719e960c7da1c451587433cc3` |
| `LivingEntity.java` | `e62ce650af0a5ac97e4d0a2ba7cad4ba68a8d098525609f8b5b6976a8ab0ae30` | `a8aed863d4fdc515c751dd2878a8bb9c13179228cb8dbb50edf1d19cd5404271` |
| `Entity.java` | `05f18ef2ec0413fc010230407c812a11553eb5123b68d21b5d7b2c0c175698ad` | `fc177733e9cc4b2d5cf2562e5529d0f4e0b9c3690f081fbd58aa9119eb173dc4` |
| `InterpolationHandler.java` | not present/used by this version’s helper | `59a065f31a2d725d2ed5a4496dbb7580a4808abea432fd6afb4a5eef9233886a` |
| `Vec3.java` | `62581d3005cc32401545f9229a9c293dfd61c52e7d30f88456c49d2c2867e68d` | `62581d3005cc32401545f9229a9c293dfd61c52e7d30f88456c49d2c2867e68d` |
| `Relative.java` | `292b6d9f3f28dcd69ba60d02e218158bf92b61464597dd21529b0e841c54c0d6` | `292b6d9f3f28dcd69ba60d02e218158bf92b61464597dd21529b0e841c54c0d6` |

## Independent source checks

1. `PositionMoveRotation.calculateAbsolute` computes current-or-zero pitch plus packet pitch. 1.21.4 uses that unbounded sum as its target pitch; 1.21.5 clamps the sum to `[-90.0F, 90.0F]` before calculating pitch delta. With `ROTATE_DELTA`, both rotate prior movement by pitch delta first and yaw delta second.
2. The helper then calls `calculateDelta` separately for X, Y and Z. A component without its respective `DELTA_X/Y/Z` flag is the packet component only; a flagged component is the fully rotated prior component plus that packet component. Comparing equal packet values, the precise surviving-difference predicate must inspect the component after both rotations. The correction’s requirement of a flagged component and a nondegenerate intermediate pitch-rotated component is necessary but not fully sufficient for the specific flagged component when yaw delta is nonzero.
3. `Vec3.xRot/yRot` bodies are identical in both releases. For the exact zero-yaw example above, pitch-only rotation of prior `(0,1,0)` changes the Z component: with current pitch 80° and target pitch 100° before clamp, the 1.21.4 pitch delta is −20°; 1.21.5 clamps target to 90° and uses −10°. The two `DELTA_Z` results differ after both rotations.
4. `ClientPacketListener.handleMovePlayer` applies the helper only for a non-passenger local player and passes `false` for interpolation. The direct branch sets returned delta movement and final rotation; `Entity.setXRot` clamps stored pitch in both versions. The removed-vehicle fallback also calls the helper on the local player, as the original audit recorded.
5. For a non-interpolating local player, the correction’s helper-input equivalence is supported. 1.21.4 `LivingEntity.lerpTargetX/Y/Z/XRot/YRot` uses the current values when `lerpSteps == 0`; 1.21.5 `.of(Entity)` uses current position/rotation when `isInterpolating()` is false. Both helper factories use `getKnownMovement`, whose source body matches in this pair. For active interpolation, the source helper inputs are not assumed equal; the correction properly limits its claim to the inactive case.

No tests, builds, game or server launches were run.
