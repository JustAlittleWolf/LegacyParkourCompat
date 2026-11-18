# Immutable adjudication r2: respawn velocity and portal authority

## Bound immutable revisions

- Frozen blind audit `audit/blind.md`: `3e640d9eb09e7d26a74635ded60394186b6085ee2500f36ee88b909c84165cde` (verified unchanged).
- Prior adjudication `audit/verdict.md`: `6900e5ab19b96972486d822782f2eac807fb57a28638586f23955b0b3e240eeb` (verified unchanged).
- Author coverage r2 `author/coverage-r2.md`: `31d3690a437974fd40b69b9551044f67c3aa60e0609fe3a65feb48edfaf994cd` (verified).
- Proposed finding `author/development/research/findings/1.21.1--1.21.2/end-portal-respawn-velocity-preservation.md`: `0ffa731bd27e60039b379731ed5db0f55f367ee77925c8d39068a57aecc4c6ba` (verified).

The pair is the same exact pinned Mojmap source pair described in the frozen audit/r2 coverage. The source references below resolve in those roots; the relevant complete-file SHA-256s are recorded there.

## End-bound End Portal: proposed respawn-copy claim

**Verdict: confirmed source difference; eligible direct client player-state operation, reachable on the ordinary End Portal route. Revise scope/title before treating the draft as a canonical portal-only operation.**

`ClientboundRespawnPacket.KEEP_ENTITY_DATA` is byte `2`; `KEEP_ALL_DATA` is byte `3`; `shouldKeep` is the same `(dataToKeep & bit) != 0` predicate in both versions (`ClientboundRespawnPacket.java:12–15,34–35`). Both End-bound ServerPlayer dimension-change paths send `new ClientboundRespawnPacket(..., (byte)3)` before their position correction (`1.21.1 ServerPlayer.java:859–862`; `1.21.2 ServerPlayer.java:1052–1055`). This establishes the same keep-bit input and reachable route. It does not assert the entire serialized packet is byte-for-byte identical across protocol versions; only the raw `dataToKeep` byte and equivalent End destination input are held equal.

With that input, the client behavior differs directly inside the respawn handler:

- 1.21.1 creates the replacement LocalPlayer, optionally assigns non-default entity data, then unconditionally calls `resetPos` (`ClientPacketListener.java:1112–1142`). `LocalPlayer.resetPos` sets delta movement to `Vec3.ZERO` (`LocalPlayer.java:625–637`). The old handler does not copy the previous LocalPlayer's delta.
- 1.21.2, when `shouldKeep((byte)2)` is true, assigns entity data and explicitly copies old delta movement and rotations to the replacement (`ClientPacketListener.java:1160–1187`). The zeroing `resetPos` occurs only in the false branch.

Therefore, under equal `dataToKeep=3` and equivalent spawn/dimension input, a nonzero old LocalPlayer delta becomes zero on the 1.21.1 replacement and remains nonzero immediately after the 1.21.2 respawn handler. This is a movement-state write in a generic client packet receiver, not merely End Portal local pending state. The End Portal route proves ordinary-player reachability; the predicate itself applies to any respawn packet whose entity-data bit is set, so a canonical finding should name the generic keep-entity-data respawn operation and describe End Portal as a concrete route.

### Following position packet and stable state

The respawn-handler delta difference is not overwritten by the ordered position packet on the End route:

- 1.21.1 `ServerPlayer.changeDimension` sends the respawn packet, then calls the position-only connection overload (`ServerPlayer.java:861,875`). That overload uses an empty relative set; `ServerGamePacketListenerImpl.teleport` emits no relative position/delta flags (`ServerGamePacketListenerImpl.java:989–1006`). The 1.21.1 `ClientPacketListener.handleMovePlayer` sets delta components to zero for absent relative X/Y/Z flags (`ClientPacketListener.java:620–695`).
- 1.21.2 `ServerPlayer.teleport(TeleportTransition)` sends the respawn packet, then forwards the transition's relative set to the connection (`ServerPlayer.java:1054,1068–1070`). The End-bound End Portal transition has zero delta plus `Relative.DELTA` (`EndPortalBlock.java:81–98`). The server resolves and emits those relative values (`ServerGamePacketListenerImpl.java:1007–1019`); the new client receiver resolves against `PositionMoveRotation.ofEntityUsingLerpTarget`, whose movement input is `getKnownMovement` (`PositionMoveRotation.java:27–29,35–62`). For an ordinary unmounted local player, that is its current delta, so zero relative delta preserves the copied value.

The observed state difference therefore survives through the following position-packet application: old local delta is zero; new local delta remains the pre-transfer value. This is a stable post-packet/pre-next-simulation state difference, not just an intra-handler intermediate. Subsequent ordinary ticks or later server corrections can evolve it; this adjudication does not claim an indefinite trajectory or runtime parity. The later relative-delta packet is a separate server-producer dependency that reinforces the client-copy result; it is not the same-input cause of the respawn-handler difference.

**Disposition:** retain the proposed source claim. Before canonicalization, generalize the operation name/scope from “End Portal respawn” to the client `KEEP_ENTITY_DATA` respawn movement-state copy, while keeping the Overworld-to-End End Portal path as the concrete reachable example. Do not attribute the packet's new relative flags to the client respawn-copy operation, and do not emulate server portal physics.

## End Gateway and prior same-tick correction

**Verdict: r2 coverage correctly preserves the authority boundary and corrects the earlier entry-position omission.** Gateway uses a same-level server transition; it sends no respawn packet and never runs the End-bound `handleRespawn` copy claim. The callback remains server-gated. The same-tick `Entity.setAsInsidePortal` difference remains source-confirmed: old same-portal calls always replace entry position; new same-portal calls replace it only if `insidePortalThisTick` is false (`Entity.java:2121–2131` vs `2214–2224`). If one server contact pass invokes two eligible Gateway block positions for the same block singleton, old stores the last callback's position and new stores the first. The stored position selects the block entity/exit used by `EndGatewayBlock.getPortalDestination`; thus the change is in server-selected destination production. It is not a client-local contact hook.

The Gateway velocity outcome is also producer-dependent: 1.21.1 ServerPlayer uses the empty-relative position-only connection path, while 1.21.2 Gateway supplies `Relative.DELTA | Relative.ROTATION` and the server forwards those flags. The generic client position receiver applies the packet flags, but no same-flags client receiver arithmetic difference is established here. Keep this separate from the `handleRespawn` operation; no End Gateway respawn-copy finding is supported.

## Final adjudication and limits

The new respawn-copy finding is source-confirmed and independently eligible as a direct local-player movement-state operation under the keep-entity-data predicate. The End route is reachable and its ordered subsequent packet leaves a stable post-packet state difference. The packet producer is an authority dependency. Gateway swept contact and Gateway first-versus-last entry position remain server producer findings/dependencies, not historical server movement hooks. No product applicability, implementation, intermediate patch boundary, full-pair freeze, build, or runtime parity is decided here.