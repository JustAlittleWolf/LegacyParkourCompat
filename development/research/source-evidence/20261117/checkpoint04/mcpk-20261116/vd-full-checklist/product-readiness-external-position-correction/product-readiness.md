# Product readiness: accepted external position-correction response path

## Disposition

Read-only correspondence against the accepted bounded source claim. No product, backlog, Git, test, build, or runtime changes were made.

**Selected 1.21.3 (`ParkourVersion.V1_21_2`): covered by native fallback for this accepted endpoint behavior.** The current client path uses `PositionMoveRotation.calculateAbsolute` and the native `ClientPacketListener.handleMovePlayer` passenger gate. The accepted 1.21.3 behavior requires (a) not applying a local correction while riding and (b) preserving current received player velocity for the absolute-position correction emitted by `ServerPlayer.teleportTo` through `Relative.DELTA` flags plus a zero delta payload. The modern native calculation and handler already do both. No versioned hook or change is needed for this exact endpoint response in the current native client; runtime parity remains unverified.

**Selected 1.21.1 (`ParkourVersion.V1_21`): eligible product gap.** The selected-era correction applies even while riding and couples each position-relative X/Y/Z flag to preserving that velocity axis; absolute position axes zero velocity. The product delegates to the modern handler/helper: the passenger gate is outside the wrapped helper call, so the existing mixin cannot restore the old riding response, and `calculateAbsolute` interprets distinct modern `DELTA_*` flags rather than the old position-relative velocity rule. In particular, the modern delta-relative zero payload sent by the 1.21.3 teleport writer preserves velocity where the 1.21.1 response would clear velocity for absolute position axes. Product has no selected-profile hook/change for either correction gate or velocity interpretation.

The eligible correction response path is a client operation. The 1.21.3 `ServerPlayer.teleportTo` flag choice is a server-side producer of the packet state, and should not be implemented as historical server physics. `ClientboundSetEntityMotionPacket` receive/dispatch remains native: the accepted source/audit states its examined receiver and packet path are unchanged across 1.21.1→1.21.3. Do not conflate this no-difference entity-motion packet route with player correction response semantics.

## Product evidence inspected

- `src/main/java/me/wolfii/legacyparkourcompat/api/ParkourVersion.java`: 1.21.1 and 1.21.3 aliases are `V1_21` and `V1_21_2` respectively; `V1_21_2` covers 1.21.2–1.21.3.
- `src/main/java/me/wolfii/legacyparkourcompat/impl/ChangeResolver.java`: resolves each mechanic to the earliest registered emulation version greater than or equal to the selected profile; `CURRENT` resolves no changes.
- `src/client/java/me/wolfii/legacyparkourcompat/change/MovementChangeDiscovery.java`: discovery registers each implemented mechanic interface from annotated client changes; there are no per-version providers or manual catalogs to update.
- `src/client/java/me/wolfii/legacyparkourcompat/mechanic/hook/network/PlayerCorrectionPositionHook.java` and `change/v1_19/PlayerCorrectionCoordinateClamp.java`: the existing correction-position hook only clamps coordinates and emulates from 1.19; it does not cover the 1.21.1/1.21.3 riding gate or velocity interpretation.
- `src/client/java/me/wolfii/legacyparkourcompat/mixin/network/ClientPacketListenerMixin.java`: scopes local-player, non-interpolated correction calls and adjusts pitch/position, but only wraps the helper invocation and its `Entity.setPos`; it does not wrap the outer passenger gate or resolved delta movement.
- `src/client/java/me/wolfii/legacyparkourcompat/mixin/network/PositionMoveRotationMixin.java`: wraps only the pitch clamp in `calculateAbsolute`; no velocity interception exists.
- `src/client/java/me/wolfii/legacyparkourcompat/mixin/network/ClientPacketListenerMovementMixin.java`: entity-motion packet receive is not intercepted here; existing position-sync adaptations concern entity tracking and do not change the direct local-player correction above.
- `src/client/resources/legacyparkourcompat.client.mixins.json` and `src/main/resources/fabric.mod.json`: these listener/correction mixins are client-only, consistent with the client-response scope.

## Accepted evidence identity

- Canonical finding (not duplicated or edited): `development/research/findings/1.21.1--1.21.3/direct-player-position-correction.md`.
- Source report: `build/later-source-20261116/1.21.1--1.21.3/external_corrections/report.md`, SHA-256 `763e286dd4bc968827f8078ed3849c1f98cf27812687979b735b515a39db5291`.
- Independent audit: `build/later-source-20261116/1.21.1--1.21.3/external_corrections_audit/verdict.md`, SHA-256 `4ee5420d808e1da14c9844e8b19e9630bd99e832d7e9f9367dbe53e6c5be2bf4`.
- Scope limitation: accepted evidence establishes the 1.21.1→1.21.3 endpoint difference, not the intermediate patch introduction boundary, complete teleport producer triggers, pair-wide coverage, or runtime parity.

## Terminal status

**COMPLETED** — product correspondence only. No source re-audit, implementation, backlog update, Git operation, tests, build, or runtime verification.
