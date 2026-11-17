# Independent source audit verdict — external corrections

Audited report: `build/later-source-20261116/1.21.1--1.21.3/external_corrections/report.md`
Report SHA-256: `763e286dd4bc968827f8078ed3849c1f98cf27812687979b735b515a39db5291`

## Verdict

**Accept the bounded source claims reviewed here.** The exact-version source identities are ready and manifest-consistent. The packet receiver/dispatch identities, received entity-motion no-difference claims, changed player-correction handler, passenger gate, and `ServerPlayer.teleportTo(double, double, double)` relative-velocity flag interpretation are supported by the paired sources. This verdict accepts only this bounded external-correction slice; it does not close producer-trigger dependencies or establish full-pair coverage, a patch boundary, implementation acceptance, or runtime parity.

## Independent checks

- `decompiled_minecraft/1.21.1/mojmap.ready.json` and `1.21.3/mojmap.ready.json` both say `status=ready`, name the exact release and Mojmap, and match the expected source-manifest, artifact-manifest, and diagnostics hashes. Actual source manifest entry counts and source byte totals match the ready markers. Provenance hashes match those in the report.
- Independently computed SHA-256 values for every source file listed in the report match its corresponding entry in the source manifest. `ClientboundSetEntityMotionPacket.java` is byte-identical across versions (SHA-256 `bc6c957cb33fd07b3b2b6e22f62aa15f9f68872e44d67b172bb0249f81950d91`).
- In both versions, `ClientboundSetEntityMotionPacket.handle` delegates to `ClientGamePacketListener.handleSetEntityMotion`; `ClientboundPlayerPositionPacket.handle` delegates to `handleMovePlayer`. The interface declares these operations, and `ClientPacketListener` implements them. The inspected `LocalPlayer` and `Player` sources contain no such packet-handler overrides.
- Both `ClientPacketListener.handleSetEntityMotion` bodies enforce client-thread execution, find the entity by ID in the level, and call `lerpMotion` only when found. The same packet body clamps each component to `[-3.9, 3.9]`, multiplies by `8000.0`, casts to `int`, writes shorts, and decodes each signed short divided by `8000.0`. The report correctly limits producer-side coverage to identified call sites rather than asserting complete producer-trigger coverage.
- The 1.21.1 `handleMovePlayer` has no passenger guard. Position-relative X/Y/Z each both offset position/snapshots and preserve that axis of current delta movement; absolute axes set corresponding delta to zero. It applies position and delta, then pitch, yaw, and acknowledgements/responses as stated.
- The 1.21.3 handler gates state application on `!player.isPassenger()` and sends the position response and teleport acknowledgement after the gate. Its `setValuesFromPositionPacket(..., false)` call cannot take the interpolation branch. `calculateAbsolute` uses distinct position-relative and delta-relative flags; unflagged delta axes use the payload and flagged axes add it to base movement. The report’s described component order and flags are accurate.
- In 1.21.1, `ServerPlayer.teleportTo(double, double, double)` sends `RelativeMovement.ROTATION`; the old client therefore uses absolute position and zeroes all three velocity axes. In 1.21.3 it sends a zero delta payload with `Relative.DELTA` plus `Relative.ROTATION`; for an applied, non-passenger local correction, the handler retains base known movement on all three axes. The reported preservation conclusion follows from the paired client handler and writer; `getKnownMovement()` falls back to the entity's current delta movement in the ordinary non-passenger player case.

## Remaining gates

The report appropriately leaves teleport call-path/event triggers, all velocity producers and their eligibility conditions, riding transitions and later snapshot consumers, and remaining movement areas open. Keep the unknown intermediate introduction boundary and the full-pair freeze gate. No source-generation, implementation, build, test, or runtime action was performed.
