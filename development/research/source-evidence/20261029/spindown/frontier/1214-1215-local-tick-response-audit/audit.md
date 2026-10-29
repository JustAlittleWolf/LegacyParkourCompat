# Independent audit: 1.21.4 → 1.21.5 relative pitch and received velocity

## Verdict

**Accept the narrow source-level operation difference, with an applicability correction.** The 1.21.5 helper clamps the calculated pitch before computing the `ROTATE_DELTA` pitch delta; 1.21.4 does not. The listeners pass the resulting delta movement into the local player for the direct position-response paths. However, the finding’s stated precondition is incomplete: `ROTATE_DELTA` alone is not sufficient for the pitch-dependent old velocity to affect returned movement. At least one of `DELTA_X`, `DELTA_Y`, or `DELTA_Z` must also be present, because unflagged result components are replaced by packet components. A nondegenerate velocity/axis combination must produce a different component. The concrete 80° + 20° example should include `X_ROT`, `ROTATE_DELTA`, `DELTA_Y`, and `DELTA_Z` (with no yaw delta and packet delta components set to zero) to establish the stated changed result.

This accepts only the bounded source operation. It does not establish which server producer sends that flag combination, an exact feature introduction beyond this endpoint pair, runtime trajectory, or full pair closure.

## Exact evidence identity

The supplied author finding is `.native-source/20261028/frontier-c/1214-1215-local-tick-response/relative-pitch-velocity-rotation.md`, SHA-256 `e334078e9aef5e35621bf50adb3cf3ad12f0a1b167647a87134a7db747bf2ffd`. Its coverage parent is `.native-source/20261028/frontier-c/1214-1215-local-tick-response/coverage.md`, SHA-256 `ad1a38270a10d43be3a0824a874ebd3486bb79974de87043929552b1192dd8a2`.

Canonical read-only source root: `D:/Javastuff/LegacyParkourCompat/decompiled_minecraft/`. Both endpoints are Mojmap. At each release parent, the `mojmap.ready.json` and `mojmap.provenance.json` records exist and name the exact version and `mojmap` namespace. The parent ready marker declares the following source/artifact manifest identities, and those on-disk manifests hash to the declarations:

| Release | Client artifact SHA-256 from ready marker | Source manifest SHA-256 | Artifact manifest SHA-256 | Provenance file SHA-256 |
|---|---|---|---|---|
| 1.21.4 | `c17c450c6e72cc51297daa57ce38f800aa01cf022b743daa21a0512d326d894e` | `f90b61197928632e061ea877955a19055c92ae6f357c2daf1bc646172c6f51f0` | `1a0929ca8c88cfe7874f323918dfa3b044964007d3ff0d3943317bdb35caf841` | `ab0439864c89f4e0dab09daae7b57ab973368eeb0320626822e06f5a9bce9fb9` |
| 1.21.5 | `522672ad20b460c02c2e39b6c5035ef6a849af28eb11ab2ac7eb293d395a8c11` | `365cc2d22446ceba0e36f46aa1c95ce4cc31514dbd657505680fb660f3adefd9` | `d35b1b9389d8958e33893faa63d94c86f22af30385233ca947fd410aa5c36656` | `b0832a4e1507bd74ac300fae2fca78668964b21ef2d05be8cff01b9d1d60da8e` |

Neither mapping root has `.decompile-provenance.json`; identity is bound through the release-parent ready/provenance records and verified source manifest. The provenance records identify the same exact 1.21.1/1.21.3/1.21.4/1.21.5 Vineflower batch. No client JAR archive bytes were independently rehashed in this audit.

The selected source hashes independently match both reports and the release source manifests:

| Source | 1.21.4 SHA-256 | 1.21.5 SHA-256 |
|---|---|---|
| `net/minecraft/client/multiplayer/ClientPacketListener.java` | `eef170dd22b5711e7d9527601592093a0456590f192b54fefef170b5791165b4` | `284f34159a1baf30b6b540751ad2fa62344c179d5ed6a7bbcb5c888665779617` |
| `net/minecraft/world/entity/PositionMoveRotation.java` | `74b39fb0907043885e7cfeb6376c8d39eba7131ee6befa2df57a08ea53571436` | `75baa0b6f99d1b9d18f157cbbf2c6d209b13af6719e960c7da1c451587433cc3` |
| `net/minecraft/world/entity/Entity.java` | `05f18ef2ec0413fc010230407c812a11553eb5123b68d21b5d7b2c0c175698ad` | `fc177733e9cc4b2d5cf2562e5529d0f4e0b9c3690f081fbd58aa9119eb173dc4` |
| `net/minecraft/world/phys/Vec3.java` | `62581d3005cc32401545f9229a9c293dfd61c52e7d30f88456c49d2c2867e68d` | `62581d3005cc32401545f9229a9c293dfd61c52e7d30f88456c49d2c2867e68d` |
| `net/minecraft/world/entity/Relative.java` | `292b6d9f3f28dcd69ba60d02e218158bf92b61464597dd21529b0e841c54c0d6` | `292b6d9f3f28dcd69ba60d02e218158bf92b61464597dd21529b0e841c54c0d6` |

## Source retrace

### Helper math and missing flag gate

In 1.21.4 `PositionMoveRotation.calculateAbsolute`, target pitch is `$$10 = $$7 + $$1.xRot`; 1.21.5 computes `$$10 = Mth.clamp($$7 + $$1.xRot, -90.0F, 90.0F)`. Both then take the existing delta movement as `$$11`, and only under `Relative.ROTATE_DELTA` rotate it first by pitch delta `$$0.xRot - $$10` in `Vec3.xRot`, then by yaw delta in `Vec3.yRot`. Both afterward construct each returned velocity component with `calculateDelta(oldRotatedComponent, packetComponent, relatives, corresponding DELTA flag)`.

`calculateDelta` returns `oldRotatedComponent + packetComponent` only when its specific `DELTA_X`, `DELTA_Y`, or `DELTA_Z` flag is present; otherwise it returns the packet component alone. Therefore the clamp-induced difference in rotated current velocity is overwritten for every component whose `DELTA_*` flag is absent. `Relative` treats the three delta-component flags and `ROTATE_DELTA` as separate enum values. Its bitset `unpack` adds each set value independently, so source decoding imposes no mutual exclusion among those flags. The report should state both the `ROTATE_DELTA` and at least one appropriate `DELTA_*` gate, plus nondegenerate values for the component in question.

For the report’s example, current pitch 80°, packet pitch +20° with `X_ROT`, unchanged yaw, prior movement `(0,0,1)`, packet movement `(0,0,0)`, and flags `{X_ROT, ROTATE_DELTA, DELTA_Y, DELTA_Z}` produce a 1.21.4 pitch delta of −20° and a 1.21.5 pitch delta of −10°. `Vec3.xRot` consequently produces different Y and Z values before the relative zero packet components are added. The helper returns 100° pitch in 1.21.4 and 90° in 1.21.5; when applied through the listener, the entity setter clamps stored pitch to 90° in both versions.

### Direct local-player caller gates and writes

`ClientPacketListener.handleMovePlayer` obtains `minecraft.player`, applies `setValuesFromPositionPacket` only if that player is not a passenger, then sends teleport acknowledgement and a position/rotation response. Both releases pass `false` for the interpolation argument, so `setValuesFromPositionPacket` takes its direct branch: it sets position, returned delta movement, yaw and pitch, then recalculates old-position/rotation history. The direct branch applies the changed `deltaMovement` returned by `calculateAbsolute`.

Both releases’ `handleTeleportEntity` include a removed-player-vehicle fallback: if the packet entity is absent and its ID matches `removedPlayerVehicleId`, the listener applies that packet’s `change` and relative flags to `minecraft.player` through the same helper and sends the resulting position/rotation. This is an additional direct player-response caller; the ordinary existing-entity vehicle branch itself remains outside player-physics scope.

### Other helper changes and conditions

There is a concurrent factory-body change that the supplied finding does not mention. In 1.21.4 the direct listener helper calls `PositionMoveRotation.ofEntityUsingLerpTarget`, and `Entity.lerpTargetXRot/YRot` return the entity’s current rotations. In 1.21.5 it calls `PositionMoveRotation.of`, which uses the active interpolation target when `Entity.isInterpolating()` and otherwise uses current entity state. The narrow clamp comparison therefore holds with equal input values; a claim framed around current pitch additionally needs the 1.21.5 non-interpolating branch or otherwise needs to state the actual interpolation-target pitch used as the helper input. This independent factory change is not conflated with the clamp.

`Entity.setXRot` applies the same finite-value check and `Math.clamp(value % 360.0F, -90.0F, 90.0F)` in both releases. `Vec3.xRot/yRot` bodies and `Relative` flag declarations/decoding are unchanged. The source path establishes that returned velocity can differ under the corrected flags and values; it does not predict a later trajectory.

## Corrections for any finding revision

1. Add at least one `DELTA_X`, `DELTA_Y`, or `DELTA_Z` flag to the movement precondition. For the shown example, specify `DELTA_Y` and `DELTA_Z` (or another exact nondegenerate flagged component) as well as `ROTATE_DELTA`.
2. State whether the target is non-interpolating in 1.21.5, or phrase the input as equal current/interpolation helper values so the concurrent factory change is not silently assumed away.
3. Keep the source-level finding separate from producer reachability; no server-side packet producer was traced.

No test, build, game or server launch was performed.
