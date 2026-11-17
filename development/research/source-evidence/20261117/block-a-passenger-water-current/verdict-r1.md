# Passenger fluid gate: independent verdict, revision 1

**Verdict:** Rebind and accept the corrected finding as a narrow, direct local-player movement-state difference. The revised report correctly limits the finding to the water-current write and resulting tick-end player `deltaMovement`. It correctly treats crouch descent as an unchanged conditional response to the newly updated water state, not a separate version-different operation.

## Tick-end state and later clears

The reviewed source path supports a real difference at the end of an eligible underwater-boat passenger tick: `Entity.rideTick()` zeros player velocity first, `Entity.baseTick()` can add the water-current vector, the local player then performs its water travel, and `positionRider()` restores the rider's vehicle-relative position without clearing velocity. This leaves a changed player delta at the end of that tick. That state difference is within the repository's per-tick movement-state invariant even though the player's final position is vehicle-positioned and no continuing free-player trajectory is claimed.

The later clears are also correctly bounded. If still attached, the next passenger `rideTick()` zeros velocity at its start. On ordinary shift dismount the client sends the input to the server; the server stop-riding path sends a position correction with no relative velocity components, and the client correction handler removes the vehicle and sets all three delta components to zero. That establishes a zero delta when the correction is applied; it does not negate the earlier passenger tick's end state. This source-only review does not make a broader claim about exact network scheduling between separate passenger-list and position-correction packets.

## Scope and applicability

Keep the finding restricted to the direct player response: `Entity.updateFluidHeightAndDoFluidPushing()` writes a flow-derived vector into the player entity's `deltaMovement` when an underwater boat passenger overlaps loaded flowing water and fluid pushing is enabled. The player fluid gates exclude flight. No boat or other vehicle motion, non-player response, or persistent post-dismount trajectory is included. The shift-down branch remains a conditional consequence of `isInWater()` changing; its `goDownInWater()` operation itself is unchanged.

## Evidence binding

Rechecked corrected immutable comparison revision SHA-256 `6b4b78101c2e1a355cf3c7e0dab2431b14fbd5460fb477c8601f9b6165c47346` and corrected finding SHA-256 `0feb543a027cfa7da36a4cc7b9eea4296a4cfbf9087a066b212e177229615215`. The prior independent verdict remains unchanged at SHA-256 `af045fc5f2ab00752e642cf13fb85ff73b71e362fd7ce2113d9150d50e8ea8f3`; its source roster and dependency addendum rehash to `331068cc5a43994bbd3c7e56852559d7b41e612d65278af55f89e9c709ba3a60` and `f7cbaa6c760ec5c33c56ae1f4da06368e607d92313778622aa9c7a463394cd36`.