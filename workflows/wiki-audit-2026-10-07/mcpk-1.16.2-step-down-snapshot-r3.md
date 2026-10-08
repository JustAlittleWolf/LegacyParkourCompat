# MCPK finding snapshot r3: 1.16.2 sneak edge-backoff predicate

Snapshot date: 2026-10-08. Lane: MCPK wiki audit. Status: **source-corrected candidate; follow-up review pending**. This snapshot does not freeze the release pair and is not formally accepted.

## Supersession and review provenance

Supersedes r2 at commit `78683ba65928004ce8b7b6b9371359164a68d43b`, path `workflows/wiki-audit-2026-10-07/mcpk-1.16.2-step-down-snapshot-r2.md`, Git blob `3ea28bac8e53ec5d6be2ee99d98001841102eb02`, content SHA-256 `d2dae102961157b575d871918c5112243b2a89eb2b412885131facc77e28ad79`.

The clean review at branch `fix/mcpk-clean-rereview-dispositions`, report `workflows/wiki-audit-2026-10-07/mcpk-clean-rereview-dispositions-2026-10-08.md`, requested this correction: the r2 description reversed the `noCollision` result. This r3 corrects that predicate and preserves the rest of the bounded finding. It is awaiting a follow-up review.

## MCPK claim and provenance limit

The preserved MCPK [Version Differences catalog entry](https://www.mcpk.wiki/wiki/Version_Differences) attributes a 1.16.2 change to sneaking near edges. The lane's direct MCPK page fetch returned HTTP 403; wording and revision attribution remain from the prior catalog and were not freshly verified against the live page. This finding checks only the assigned vanilla source behavior.

## Source adjudication

At 1.16.1, `Player.maybeBackOffFromEdge` is gated by movement type (`SELF` or `PLAYER`), `onGround`, and `isStayingOnGroundSurface()` (`Player.java:1010–1012`). For `Player`, the latter is the sneak-key predicate. At 1.16.2, the gate is `!abilities.flying`, the same movement types, `isStayingOnGroundSurface()`, and `isAboveGround()` (`Player.java:1010–1013`); the old `onGround` requirement is removed.

In 1.16.2, `isAboveGround()` is true when grounded, or when `fallDistance < maxUpStep` **and** `!level.noCollision(player, candidateBox)`, where `candidateBox` is the player box moved vertically by `fallDistance - maxUpStep` (`Player.java:1062–1065`). `CollisionGetter.noCollision(entity, AABB)` returns true only when every returned collision shape is empty (`CollisionGetter.java:44–50`). Therefore, on the airborne branch, the candidate box must collide with at least one non-empty collision shape for `isAboveGround()` to pass. The r2 wording “does not collide” was reversed. This remains a source-bounded fall-distance/collision gate, not arbitrary airborne movement.

`Entity.move` calls the virtual `maybeBackOffFromEdge` before collision resolution in both endpoints (`Entity.java:488–489` in 1.16.1; `:504–505` in 1.16.2). The X-only, Z-only, then combined support probes remain unchanged, backing horizontal components off in 0.05 increments. The bounded delta is the player entry predicate; the probe algorithm is not a separate change.

Inputs remain vanilla: flying ability, mover type, sneak input, on-ground/fall-distance state, `maxUpStep`, player box, and world collision shapes. This does not establish the exact first 1.16.2 patch or full-pair coverage.

## Exact vanilla source identities

Canonical read-only ready root: `D:\Javastuff\LegacyParkourCompat\build\movement-campaign-2026-10-07\ready\`. Both endpoints are ready Mojmap trees; source and artifact manifest hashes match their ready metadata.

| Endpoint | Source manifest SHA-256 | Artifact manifest SHA-256 |
|---|---|---|
| `1.16.1/mojmap` | `76fdd121070c2012f48cbdf5cf0c78490116afc94117c30b0858eedcd6ab9754` | `a113f9d59fa4582effb2ebf548c4388a54c9c5654fbf57fae7974dce896d1f98` |
| `1.16.2/mojmap` | `10d312ca29ee48e25f4727adafb9fcfbd741dcebe81b343d3dcffa071952a7a7` | `7b8551bdc108a1584039ac22e35b6b75c3584ed85aba24561c7d340f5cbeb108` |

Exact source row SHA-256 values, relative to each endpoint's `mojmap/` directory:

- `1.16.1`: `world/entity/player/Player.java` `a849960d47bf00a2cddfb6636c78c518532a11967ba08829f19c5ebced08681e`; caller `world/entity/Entity.java` `27e3bb01b5d03c09161891e717c16477899768333082e81ebfaac4e525616576`.
- `1.16.2`: `world/entity/player/Player.java` `d2e26589bdb6a20dc914266db06aa48f50811efc792d6e63b3008c9199914960`; caller `world/entity/Entity.java` `f9a9a073fe3105a0aa53d0f21ec72e59084e8d21a14c1cd3be75703865ee2666`; `world/level/CollisionGetter.java` `b507d6be11e5985a62cfeb249a99dcb5f8edaf346f12cb2487797d9e01763eac`.

The broader Feather-derived-JAR integrity limitation remains separate: this bounded finding uses Mojmap endpoints and makes no assertion about equivalence to unavailable original Feather-derived JARs.