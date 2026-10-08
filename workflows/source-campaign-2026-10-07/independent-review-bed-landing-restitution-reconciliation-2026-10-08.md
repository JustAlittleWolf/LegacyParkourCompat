# Independent no-code bed-landing reconciliation review (2026-10-08)

## Decision

**REQUEST CHANGES — the stated author candidate cannot be bound in the supplied primary repository.** I could not locate commit `d506bfca`, branch `fix/bed-landing-restitution-2026-10-08`, or the author's reconciliation report through local refs, worktrees, or the available Git object database. Therefore this is not acceptance of that exact authored report. Please make that commit/report available for an exact-candidate review.

The independently inspected implementation baseline at current local `main` is consistent with the accepted ordinary fresh-entity bed witness: no Java/provider change is indicated for that bounded witness. This review makes no claim about broader restitution parity.

## Exact bindings

- Claimed author baseline: `d506bfca`, branch `fix/bed-landing-restitution-2026-10-08` — **unavailable locally**. `git cat-file -t d506bfca` reports no such object; the branch is absent from local refs and worktrees. The candidate report path cannot be located from Git without that object.
- Accepted source candidate: `d90a4c13d7ac63984a758aa3f3a6ce320cecac69:workflows/source-campaign-2026-10-07/26.1.2--26.2/findings/F-26.2-BED-LANDING-RESTITUTION.md`, blob `7762fc6328e89c66afe6c28c563c6a4bd7dd1d47`, raw SHA-256 `c776cc145aa9e6373d83b24c209fb1c025d914dc63c64041b4aaf7d515fc227d`.
- Existing independent source acceptance: `daab0b806cf4fb33185ac294bea18d4f4a108e7f:workflows/source-campaign-2026-10-07/independent-review-bed-ordinaryfall-witness-2026-10-08.md`. It accepts the corrected fresh-entity witness and keeps the broader restitution slice partial.
- Reconciliation baseline: local `main` `d8f3956602da94bf0cf67753cc0a9f4665397729`, included in this review branch `fix/bed-landing-restitution-review-2026-10-08`.

## Bounded implementation trace at the review baseline

- `fabric.mod.json` lists `change.v26_1.MovementChanges` under `legacyparkourcompat:movement-change`. That provider registers the entity-wide `CollisionRestitution` handler and iterates `BuiltInRegistries.BLOCK`, registering a `BlockLanding` implementation for every `BedBlock` (and `SlimeBlock`) by its registry identifier. A missing identifier throws rather than silently dropping the registration.
- `EntityMixin` redirects the `Entity.move` call to `restituteMovementAfterCollisions`. It resolves `BlockLandingBehavior` by the actual effect block and resolves `CollisionRestitutionBehavior` for the moving entity. If no historical entity behavior is available, it invokes the original native method. The vertical-collision decision is passed into the change hook.
- The `V26_1` `CollisionRestitution` handler zeros collided X/Z velocity components, then runs a block landing handler only on vertical collision. `BlockLanding` delegates a shift-suppressed landing to the base zero-Y callback. Otherwise, for negative Y velocity, a bed uses the float coefficient `0.66F` and the living-entity factor `1.0`, in the same left-to-right multiply order `-movement.y * coefficient * factor`. It has no newer low-velocity/gravity threshold, so it retains the 26.1 bed response for the accepted witness.
- The accepted witness starts its differentiating contact with `-0.07840000152587891` against gravity `0.08`. Thus 26.2's native restitution threshold suppresses the bounce, while the `V26_1` landing hook applies the `0.66F` bed bounce. The setup contact's shift suppression routes through a zero-Y callback before the shared gravity/drag tail.
- The older `v1_11_2/BedBounce` hook is registered separately, keyed to `minecraft:red_bed`, and returns `0.0F` for players. The `BlockLanding` handler honors that resolved optional bounce coefficient and delegates to the zero-Y callback when it is zero. `ChangeResolver` selects the closest applicable version per mechanic key, so this older hook applies through 1.11.2 and is excluded for a 26.1.2 target; it does not override the bounded V26_1 witness.
- `ParkourVersion` contains `V26_1` and `CURRENT`, with no V26_2 profile. `ChangeResolver` returns no changes for `CURRENT`; `MovementRuntime.appliesTo` rejects non-player entities and follows the movement controller's enabled state. Those paths fall through to native behavior, preserving current 26.2 and disabled movement behavior for this mechanic.

At this baseline the provider, version key, dynamic bed IDs, keyed landing hook, and older bed-bounce hook are all present. A later central-catalog migration should be rechecked for preservation of the `V26_1` provider registration and every bed's `block.landing:<registry-id>` entry; that future migration is outside this baseline review.

## Scope and validation

This review reconciles only the accepted 26.1.2→26.2 ordinary fresh-entity bed-contact witness against the implementation at the bound baseline. It does not establish trajectories, runtime parity, a first affected release inside the source interval, behavior for other restitution blocks, or completion of the broader source pair.

No Java/provider files were changed. No tests, build, decompilation, game, TAS, Gym, server, Docker, or push operation was run.
