# Product readiness: creative-flight fall-distance edge backoff

## Immutable source inputs

- Canonical finding: `development/research/findings/1.20.6--1.21.11/creative-flight-fall-distance-edge-backoff.md` — SHA-256 `ea7b6dde0009de86f7ab8bfe6db55f7a06ae0c5595ab3707d9b89e8dfd1bb616`.
- Source witness: `development/research/source-evidence/20261117/checkpoint04/later-source-20261116/1.20.6--1.21.11/fall_distance_boundary_revision_r6/coverage-r6.md` — SHA-256 `6b27f6ac61a78949de98d2c0d7e7626b7f0e559994d2d27c47cf69ccb9b56bd4`.
- Source audit: `development/research/source-evidence/20261117/checkpoint04/later-source-20261116/1.20.6--1.21.11/fall_distance_boundary_r6_audit/audit-r6.md` — SHA-256 `57c1268d3e7be95ba02b4a693b944f4018753738169c02316cbc86e548312173`.
- Canonical finding audit: `development/research/source-evidence/20261117/checkpoint04/later-source-20261116/1.20.6--1.21.11/creative_flight_fall_distance_finding_audit/verdict.md` — SHA-256 `c62eddd2af40b9c84814dc1fadb2a09992943dfb264453c1545402e484e0d3e0`.

## Product correspondence

The profile aliases are `V1_21` = 1.21/1.21.1 and `V1_21_2` = 1.21.2/1.21.3 (`ParkourVersion.java`). Per-hook resolution chooses the closest change whose emulates version is not older than the selected profile (`ChangeResolver.java`).

`FlightFallDistanceHook` exists, but its sole implementation is `change/v1_8/CreativeFlightFallDistance.java`. For selected profiles 1.21.1 and 1.21.2, this implementation resolves and suppresses the reset when the player is flying and not a passenger. `PlayerMixin` dispatches it from a redirect at the current `Player.aiStep` reset invocation, and falls back to the vanilla reset only when no handler resolves.

This does not represent the accepted 1.21.1→1.21.2 boundary. The 1.21.2 profile should perform its pre-`super.aiStep` reset, which the resolved v1_8 implementation suppresses. The 1.21.1 profile needs the reset after inherited travel; the current aiStep redirect is not that call site, and the existing suppress-only hook cannot express that timing. Product status: **eligible implementation gap** for the accepted operation.

The existing edge-backoff consequence path is present: `SneakEdge` reads `player.fallDistance`, and `EdgeBackoffProbeBounds` handles the `canFallAtLeast` probe. These do not implement the reset-order boundary. Keep the accepted position/support-probe effect distinct from fall-damage calculation.

## Status boundaries

- **Source acceptance:** the supplied immutable finding and audits accept the bounded 1.21.1→1.21.2 source witness. This does not establish full 1.20.6→1.21.11 pair coverage.
- **Product correspondence:** current hooks/resolver/mixin/native fallback inspected; an eligible operation gap was identified.
- **Runtime parity:** not assessed; no game/TAS/runtime was launched. No build or tests were run.

Read-only inspection only; no product or backlog changes.