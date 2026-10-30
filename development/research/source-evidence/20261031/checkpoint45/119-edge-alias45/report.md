# 1.19–1.20.4 player edge-backoff alias audit

Scope: blind, exact-source audit of `Player.maybeBackOffFromEdge`, its private `Player.isAboveGround` query, the caller frame in `Entity.move`, and `LocalPlayer.move` for Mojmap 1.19, 1.19.1, 1.19.2, 1.19.3, 1.19.4, and 1.20.4. No implementation, wiki, runtime, trajectory, or server-state claims are made.

## Alias result

**Behavioral alias partition for the combined edge-backoff operation:** `{1.19, 1.19.1, 1.19.2, 1.19.3, 1.19.4, 1.20.4}` under the source-compatible input conditions below. The full helper, including the ground predicate, x-only pass, z-only pass, diagonal pass, and resulting vector construction, has the same gates, comparisons, order, constants, and collision-probe geometry throughout these releases. No behavioral partition within this bounded set is evidenced.

**Byte-exact helper body partitions** (fingerprints are SHA-256 over the extracted method signature and complete body, UTF-8, preserving whitespace):

| Operation | Identical body fingerprint group(s) |
|---|---|
| `Player.maybeBackOffFromEdge` | `{1.19, 1.19.1, 1.19.2, 1.19.3}` `5db859453caac688ff28475a0697f9d46ba5e4b50c03236e10975c4f9566626e`; `{1.19.4}` `5104a54eaef130767d0ac20b5d13cd8d0361af14cf50c2bdcfa663678b7e8ac4`; `{1.20.4}` `51cf66f10d41efea444cc96e839b7c1e44398bad690d4177d5f7aad2a0a0ea43` |
| `Player.isAboveGround` | `{1.19, 1.19.1, 1.19.2, 1.19.3}` `9ee1a33a7fe389d462031f0a7343ec4499582cf6d79a338454a9ec4c37add1b6`; `{1.19.4}` `c406bace9bf0f285d3aa1456d4cd7c7f80af9f05372c5b1baee36186cfcbd488`; `{1.20.4}` `b82fd95f9d5679cefd3fcd6834b1fd7b4eebd74af4a41ae838774fdbfc77e939` |

The exact-body splits are access-form splits, not observed movement changes: 1.19.4 reads `maxUpStep()` where 1.19.x through 1.19.3 read the field, and 1.20.4 uses `level()` and `onGround()` in these bodies. Inspected getters return the corresponding backing fields (`Entity.maxUpStep()` in 1.19.4 and 1.20.4, `Entity.level()` and `Entity.onGround()` in 1.20.4). `Player` adds no override for these accessors in the inspected sources. Thus the behavioral alias assumes the unmodified vanilla `LocalPlayer`/`Player` hierarchy and equal backing-state values; a custom subclass overriding a virtual getter is outside this alias claim.

## Operation and gates

In all six versions, the method returns the input vector unchanged unless all of these are true, evaluated in this order: the player is not flying; requested `y <= 0.0`; mover is `SELF` or `PLAYER`; `isStayingOnGroundSurface()` is true; and `isAboveGround()` is true. The inspected `isStayingOnGroundSurface()` implementation returns `isShiftKeyDown()` in every release.

When enabled, it copies the request's x and z to doubles, declares the historical `0.05` step, and reduces x first while x is nonzero and the box translated by `(x, -maxUpStep, 0)` has no collision. Each reduction sets values in `[-0.05, 0.05)` to zero; otherwise it moves positive values down by `0.05` and negative values up by `0.05`. It then performs the same z-only loop using `(0, -maxUpStep, z)`. Last it reduces both axes in one loop while both remain nonzero, probing `(x, -maxUpStep, z)` and applying the x reduction before the z reduction on each iteration. No axis is normalized or rounded. It returns `new Vec3(reducedX, originalY, reducedZ)`.

The `isAboveGround()` query is equivalent throughout the six sources: `onGround || (fallDistance < maxUpStep && !noCollision(box moved by (0, fallDistance - maxUpStep, 0)))`. In 1.19–1.19.3 it reads `onGround`, `maxUpStep`, and `level` fields. In 1.19.4 it reads `onGround` and `level` fields plus `maxUpStep()`. In 1.20.4 the expression calls `onGround()`, `maxUpStep()`, and `level()`; the examined getters return the same fields. The strict `<` comparison, negated collision query, offset and short-circuit order are unchanged.

## Caller coverage and applicability

`Entity.move` calls the virtual `maybeBackOffFromEdge` after the `noPhysics` bypass, the piston movement limiter/zero return, and stuck-speed multiplication/reset, immediately before `collide`. The call occurs for all mover types at that site; the helper's mover gate excludes piston and other types. Therefore its eligible caller inputs are the post-stuck-adjustment movement vector, a non-noPhysics path, and `SELF` or `PLAYER` with nonpositive requested y plus the remaining player predicates. The `Entity.move` full-body fingerprints vary (1.19 differs from the later five), but that caller-frame order and edge-helper call-site are present in all six; unrelated caller-body differences are not attributed to edge backoff.

`LocalPlayer.move` is byte-identical in all six snapshots (method fingerprint `055b7780c112f2a87ea868ac40bff92b67399d89cbb1e74c55bd9ca8eb13ec70`, UTF-8 signature plus body): it stores starting x/z, calls `super.move(mover, request)`, then calls `updateAutoJump` with float-cast position deltas. This supports the same call path and order for the local player. Auto-jump behavior itself is outside this audit.

This finding does not establish an exact introduction patch within the 1.18.2–1.19 interval or behavior beyond 1.20.4. It does not cover collision-shape provider changes, other caller operations, server behavior, or runtime trajectories.

## Exact source locations

Mapping family is Mojmap for each exact release. Paths are relative to each release's `mojmap/` directory.

| Release | `Player.java`: `maybeBackOffFromEdge` / `isAboveGround` | `Entity.java`: `move` | `LocalPlayer.java`: `move` |
|---|---|---|---|
| 1.19 | 1060–1109 / 1114–1117 | 547– | 973–978 |
| 1.19.1 | 1061–1110 / 1115–1118 | 547– | 979–984 |
| 1.19.2 | 1061–1110 / 1115–1118 | 547– | 979–984 |
| 1.19.3 | 1050–1099 / 1104–1107 | 547– | 900–905 |
| 1.19.4 | 1041–1090 / 1095–1099 | 549– | 880–885 |
| 1.20.4 | 1048–1097 / 1102–1106 | 596– | 871–876 |

The end lines for `Entity.move` are intentionally not asserted as an exhaustive range; the audited call site is respectively 566, 566, 566, 566, 568, and 615. Relevant getter definitions are `Entity.maxUpStep()` at 3167–3169 (1.19.4), `Entity.maxUpStep()` at 3321–3323 and `Entity.onGround()` at 592–594 and `Entity.level()` at 3379–3381 (1.20.4).

## Evidence identity

See adjacent `hash-ledger.md` for verified exact source-manifest, ready/provenance, source-file, and extracted-body hashes. The report SHA-256 is recorded there. No exact source was generated or modified.
