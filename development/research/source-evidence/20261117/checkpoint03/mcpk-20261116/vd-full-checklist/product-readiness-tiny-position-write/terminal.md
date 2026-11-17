# Product readiness: tiny movement position write

Status: specific product implementation gap; accepted behavior is not covered for 1.21.2+.

## Immutable source evidence identity

- Canonical finding: `development/research/findings/1.21.1--1.21.3/move-tiny-position-write.md`
- Canonical finding SHA-256: `A70001F0F51D36024A4013E578456617482001F2F1AF2C707AFFFD5703D3401E`
- Candidate r4 SHA-256: `90A91D80CDC6082DDF07A570369241CB84755F7FA2D6727A5669EB62275B1A56`
- Source check SHA-256: `0E34FAAD7C00BE35F470897F17EB24B5AC25125A098E39C610407D157F936981`
- Independent audit: `ACCEPT`, `E20BE23DFF4E776F8B4E8492955A814FB24AD2DFA1EE8B7B3929EEEE57EA6A03`
- The accepted sufficient witness is a nonzero, unobstructed post-backoff movement with squared length at or below `1.0E-7`; the 1.21.1 position write is skipped while 1.21.3 writes it. The source witness includes a tag-free LocalPlayer trapdoor state.

## Product correspondence

- `src/main/java/me/wolfii/legacyparkourcompat/api/ParkourVersion.java`, `ParkourVersion.V1_21` aliases `1.21` and `1.21.1` (line 96); `ParkourVersion.V1_21_2` aliases `1.21.2` and `1.21.3` (line 97); `ParkourVersion.V1_21_4` begins at line 98.
- `src/main/java/me/wolfii/legacyparkourcompat/impl/ChangeResolver.java`, `ChangeResolver.resolve` selects the smallest `emulates` ordinal greater than or equal to the selected profile, independently for each hook (lines 30-31).
- The operation is `MovementPositionWriteHook`, declared in `src/client/java/me/wolfii/legacyparkourcompat/mechanic/hook/collision/MovementPositionWriteHook.java`. Its contract accepts only `shouldWrite(ParkourVersion selected, double movementLengthSquared)` (line 14).
- Providers are `src/client/java/me/wolfii/legacyparkourcompat/change/v1_13/MovementPositionWrite.java` and `src/client/java/me/wolfii/legacyparkourcompat/change/v1_21/MovementPositionWrite.java`. No provider for this hook exists under `change/v1_21_2`.
- `MovementPositionWrite` in `change/v1_21` returns `movementLengthSquared > 1.0E-7` (line 22), implementing the 1.21.1 resolved-length threshold. Therefore profiles `V1_21` (including 1.21.1), `V1_21_2` (1.21.2 and 1.21.3), and later profiles currently resolve this v1_21 provider for this hook, absent a closer provider.
- `src/client/java/me/wolfii/legacyparkourcompat/mixin/EntityMixin.java`, `legacyparkourcompat$writeMovementPosition` dispatches only the selected profile and `movementLength` to the hook (lines 580-583). `legacyparkourcompat$movementPosition` wraps the `Entity.setPos(Vec3)` position write and suppresses it when that hook returns false (lines 611-618). The contact-segment wrapper also uses this decision (lines 600-606).
- `EntityMixin.legacyparkourcompat$captureCollisionMovement` captures requested and resolved vectors at the collision call (lines 416-432). Separately, `legacyparkourcompat$captureCallbackMovementBatch` contains a requested-minus-resolved admission predicate (lines 455-462), but it only creates callback movement batches; it does not control the wrapped position write and does not close this gap.
- `src/client/java/me/wolfii/legacyparkourcompat/mechanic/MovementRuntime.java`, `appliesTo` gates historical movement to client-side `Player` entities (lines 129-133); `find` returns no hook when that gate fails (lines 383-388). This matches the player-movement scope and keeps the logical server/native entities outside this historical hook.

## Gap and fallback

For 1.21.2+ profiles, the current provider suppresses the accepted free tiny movement because its resolved squared length is at or below `1.0E-7`. The accepted 1.21.3 condition also admits when `requested.lengthSqr() - resolved.lengthSqr() < 1.0E-7`, including the free tiny witness and some collision-shortened movement. The product hook receives only the resolved movement-length value, so it cannot represent that condition. Product coverage requires a V1_21_2 provider and a hook/mixin contract carrying both requested and resolved movement (or equivalent predicate inputs), while retaining the 1.21.1 provider behavior.

When no `MovementPositionWriteHook` resolves, `legacyparkourcompat$writeMovementPosition` defaults to `true` through `.orElse(true)`; the mixin calls the native `setPos` path. This is the native fallback.

Read-only product correspondence only. No product code, backlog, Git state, tests, build or runtime were changed or inspected beyond the completed correspondence; no source re-audit was performed.