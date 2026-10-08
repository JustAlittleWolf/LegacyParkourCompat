# MCPK finding snapshot: 1.16.2 sneak edge backoff predicate

Snapshot date: 2026-10-08. Lane: MCPK wiki audit. Status: **source-confirmed bounded finding; independent wiki-lane review pending**. This snapshot does not freeze the full release pair.

## MCPK claim

The MCPK [Version Differences page](https://www.mcpk.wiki/wiki/Version_Differences), last edited 2026-04-17 12:51, lists a 1.16.2 change to sneaking near edges: a player can step down while airborne when still within the step-height window. This snapshot checks the player-only edge-backoff predicate and the unchanged movement-backoff loops. It does not claim an exact earliest patch before 1.16.2.

## Source adjudication

The 1.16.1 `Player.maybeBackOffFromEdge` path is gated by movement type (`SELF` or `PLAYER`), `onGround`, and `isStayingOnGroundSurface()` (`Player.java:1010–1012`). In 1.16.2, the gate becomes `!abilities.flying`, the same movement-type condition, `isStayingOnGroundSurface()`, and `isAboveGround()` (`:1010–1013`). The old `onGround` requirement is removed.

In 1.16.2, `isAboveGround()` returns true when the player is on ground, or when `fallDistance < maxUpStep` and the candidate box moved by `fallDistance - maxUpStep` does not collide (`:1062–1065`). Thus the new path can apply the same sneak edge protection while the player is not grounded, but only within that source-defined step-height/collision condition. The source does not establish behavior for arbitrary airborne movement.

The X-only, Z-only, then combined support-probe loops remain present in both versions and still reduce each horizontal component in 0.05 increments (`1.16.1:1014–1052`; `1.16.2:1014–1054`). The bounded delta is the entry predicate, not a rewritten edge-backoff algorithm.

## Exact source identities

Canonical ready root: `D:\Javastuff\LegacyParkourCompat\build\movement-campaign-2026-10-07\ready\`.

Both endpoints are ready Mojmap trees. Their ready JSON status and source-manifest hashes were checked; the cited `Player.java` hashes match their source-manifest rows.

| Endpoint | Source manifest SHA-256 | Artifact manifest SHA-256 |
|---|---|---|
| `1.16.1/mojmap` | `76fdd121070c2012f48cbdf5cf0c78490116afc94117c30b0858eedcd6ab9754` | `a113f9d59fa4582effb2ebf548c4388a54c9c5654fbf57fae7974dce896d1f98` |
| `1.16.2/mojmap` | `10d312ca29ee48e25f4727adafb9fcfbd741dcebe81b343d3dcffa071952a7a7` | `7b8551bdc108a1584039ac22e35b6b75c3584ed85aba24561c7d340f5cbeb108` |

Cited Java file SHA-256 values, relative to each endpoint’s `mojmap/` directory:

- `1.16.1`: `net/minecraft/world/entity/player/Player.java` — `a849960d47bf00a2cddfb6636c78c518532a11967ba08829f19c5ebced08681e`.
- `1.16.2`: `net/minecraft/world/entity/player/Player.java` — `d2e26589bdb6a20dc914266db06aa48f50811efc792d6e63b3008c9199914960`.

This endpoint comparison corroborates the bounded 1.16.2 predicate change. It does not identify the first patch within 1.16.2, freeze pair coverage, or establish runtime outcomes.
