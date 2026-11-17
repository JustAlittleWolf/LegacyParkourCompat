# Product readiness: explosion player knockback

**Result: precise product mapping gap; no product change made.** This packet corresponds to an already accepted bounded source finding and release boundary. It does not re-audit the Minecraft sources, establish pair-wide movement coverage, or claim runtime parity.

## Accepted source identities

- Corrected finding: `build/later-source-20261116/1.20.6--1.21.11/incoming_writers/development/research/findings/1.20.6--1.21.11/explosion-player-knockback-r2.md`, SHA-256 `FE2E2D4D066E9E77CA43A65578D6306E6CF76F88BF1D4D2F52ECBCB655DD4EDE`.
- Independent finding audit: `build/later-source-20261116/1.20.6--1.21.11/explosion_r2_audit/verdict.md`, SHA-256 `12FE686B01A13F46551AD8019AE77161037BE6AE30162FB959799EBE7C0562FF`, verdict ACCEPT corrected finding and adjacent boundary.
- Boundary report: `build/later-source-20261116/1.20.6--1.21.11/explosion_boundary/boundary-check-r2.md`, SHA-256 `C112F7E189F20FE22CCE0E46726DF1092BF95FCEF3DBEADE57CB821E5835C805`.
- Boundary: exact adjacent 1.21.1 old three-float knockback payload → 1.21.2 optional double `Vec3`; 1.21.3/.4/1.21.11 retain the new form. This is not a claim about uninspected intermediates or full pair coverage.

## Product mapping

- Release aliases are in `src/main/java/me/wolfii/legacyparkourcompat/api/ParkourVersion.java:96-97`: `V1_21` covers `1.21` and `1.21.1`; `V1_21_2` covers `1.21.2` and `1.21.3`. The accepted boundary therefore aligns with an existing profile split.
- Per-operation resolution is in `src/main/java/me/wolfii/legacyparkourcompat/impl/ChangeResolver.java` (`resolve`): it chooses the closest registered `emulates` version greater than or equal to the selected profile. There is currently no explosion knockback hook or `@MovementChange` implementation to resolve at this boundary.
- `src/client/java/me/wolfii/legacyparkourcompat/mixin/network/ClientPacketListenerMovementMixin.java` contains position/relative movement packet seams (`handleTeleportEntity`, `handleEntityPositionSync`, and `handleMoveEntity` redirects/injections). It does not intercept explosion knockback or `ClientPacketListener.handleExplosion`.
- Existing nearby impulse hooks are separate operations: `src/main/java/me/wolfii/legacyparkourcompat/mechanic/hook/block/BubbleColumnImpulseEligibilityHook.java`, `.../mechanic/hook/player/BubbleColumnPlayerCallbackHook.java`, and `.../mechanic/hook/piston/StickyHoneyCarryCandidateHook.java`. None maps the explosion packet's local-player impulse response.

## Eligibility, response and limits

The accepted route is the received explosion knockback applied to the client `LocalPlayer` in `ClientPacketListener.handleExplosion`: old payload components are floats; the new optional `Vec3` components are doubles. The bounded claim concerns finite local-player impulse precision and the receive-side direct player response. An absent old impulse is represented by zero components and added; the new empty optional is a no-op. The accepted scope records the signed-zero edge (`-0.0` can become `+0.0` on the old zero-add) and does not establish a trajectory claim.

The product gap is the missing version-resolved client hook/change at the receive-side LocalPlayer vector application. With no such change, the handler follows native behavior. Any implementation should preserve the packet's optional/present semantics and the historical vector addition at the player response site; this finding does not authorize changing the server's impulse calculation/production, damage behavior, or other explosion side effects. Server production is excluded; direct player response remains in scope under the repository invariants. Runtime parity is unverified.
