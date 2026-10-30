# Pose-priority candidate review

## Reviewed snapshot

- Candidate repository: `.native-candidates/pose-priority-20261030`
- Commit: `544eedc6e0beca58c57a14c4129767106fdc67e2`
- Parent (neutral base): `7fb3cb99923362d8b896a01618cb0f4236547210`
- Working tree was clean. The reviewed change is limited to the pose hook, its change implementation, client mixin, and client mixin registration.
- Review scope: the assigned accepted source-only finding for the non-sleeping overlap, against the supplied exact 1.21.4 and 1.21.5 Mojmap sources. No build, tests, or game launch was run.

## Verdict

**Accept for the bounded non-sleeping fall-flying/swimming overlap, with an applicability note for integration.** No correctness defect was found in the candidate's implementation of that accepted behavior.

`PlayerDesiredPose.desiredPose` returns `FALL_FLYING` exactly when `isFallFlying()` and `isSwimming()` are both true and `isSleeping()` is false. In 1.21.4, `Player.updatePlayerPose` checks fall-flying before swimming; in 1.21.5, `Player.getDesiredPose` checks swimming before fall-flying. The 1.21.5 `updatePlayerPose` retains the common swimming-fit gate and subsequent pose fit/fallback logic around `getDesiredPose`. The accepted audit establishes identical dimensions for swimming and fall-flying in both releases, so returning the historical desired pose leaves native fit and fallback handling intact for this conditional case.

The `@Inject` targets the 1.21.5 `Player.getDesiredPose` method at `HEAD`, is cancellable, and preserves native selection when the hook returns empty. It dispatches through `MovementRuntime.find`; that runtime only resolves enabled client-side players. The `@MechanicType` hook is discoverable from the annotated public, no-argument change class, and the mixin is listed in the client section of the registered mixin configuration. The source comment identifies Mojmap 1.21.4 `Player.updatePlayerPose` and Mojmap 1.21.5 `Player.getDesiredPose`.

## Applicability for integration

The annotation does **not** make this 1.21.4-only. `ChangeResolver` selects the closest change whose `emulates` version is newer than or equal to the selected profile, so this implementation is also selected for every older profile unless a closer change for this hook supersedes it. The review's source evidence compares only 1.21.4 and 1.21.5. Integration must preserve that inherited applicability in its description and must not claim older-version parity from this audit. If the intended behavior is restricted to the 1.21.4 profile, an explicit lower applicability fence would be required; none exists in this candidate. This is a scope/evidence limitation, not a demonstrated wrong result for an older release.

No other integration correction is required by this review. The audit does not establish sleeping/fall-flying overlap reachability, full trajectory parity, or behavior for releases before 1.21.4.

## Evidence consulted

- Candidate finding: `findings/pose-predicate-reachability.md`.
- Accepted source-only audit: `.native-source/20261028/frontier-c/1214-1215-pose-predicate-reachability-audit/independent-audit.md`.
- Exact sources: `D:/Javastuff/LegacyParkourCompat/decompiled_minecraft/1.21.4/mojmap/net/minecraft/world/entity/player/Player.java` (`updatePlayerPose`) and `D:/Javastuff/LegacyParkourCompat/decompiled_minecraft/1.21.5/mojmap/net/minecraft/world/entity/player/Player.java` (`updatePlayerPose`, `getDesiredPose`).
- Resolver semantics: candidate `src/main/java/me/wolfii/legacyparkourcompat/impl/ChangeResolver.java`.
