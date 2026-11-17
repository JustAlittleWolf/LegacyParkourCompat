# Product readiness: 1.8.9→1.9 explosion player-vector response

Disposition: **COVERED_NO_CODE** for the bounded local-client response. The accepted difference is in server-side vector admission; the client receiver operation is the same direct addition and is already routed through the existing product hook.

## Immutable inputs

- Source event-reachability report: `build/source-189-19-20261117/tick-state/explosion-player-event-reachability.md`, SHA-256 `D966324600A350020F56236B91D6E42ABA372710BDB5898BDFFB25C9B95A2BCD`.
- Independent ACCEPT: `build/source-189-19-20261117/tick-state/explosion-player-event-reachability-audit/verdict.md`, SHA-256 `0D73C1A5A835072420A174DB3EFC1E5531C04652F7C1D049F0714307666E2FA7`.
- Existing canonical finding: `development/research/findings/1.8.9--1.9.4/player-knockback-application.md` (unchanged; this readiness check does not duplicate it).

## Product mapping and path

`ParkourVersion` resolves 1.8.9 to `V1_8_9` ordinal 3, 1.9–1.9.2 to `V1_9` ordinal 4, and 1.9.3–1.9.4 to `V1_9_3` ordinal 5. The `ExplosionKnockbackHook` has one registered provider, `ExplosionKnockback @V1_21` (ordinal 31); with closest-applicable resolution it is eligible for each of these selected profiles. Client `ExplosionKnockbackMixin` redirects `ClientPacketListener.handleExplosion`'s `Optional.ifPresent(Consumer)` call and dispatches through `MovementRuntime.find(ExplosionKnockbackHook.class, localPlayer)`; absent hook preserves native behavior. The hook casts present vector components to float and invokes the addition consumer even for an absent vector with `Vec3.ZERO`.

The accepted report establishes that 1.8.9 and 1.9 client handlers both directly add the three received float packet components to local-player velocity; an absent server vector leaves the packet fields zero. Thus the current provider reproduces the bounded receiver arithmetic/empty-vector response for both historical selections. The accepted delta—server producer admission of a vector for synchronized Creative, non-flying, eligible off-center TNT cases—is not a separate client response operation, and server-side producer replay is outside product scope.

## Limits

This is a static source/product correspondence, not runtime parity. The accepted witness is conditional on ordinary TNT, synchronized Creative with flying false, loaded/non-spectator query inclusion, positive in-range off-center geometry, and unobstructed density. It does not establish map placement, later trajectory, damage, or pair-wide coverage. No product code change is indicated for the bounded client receiver.
