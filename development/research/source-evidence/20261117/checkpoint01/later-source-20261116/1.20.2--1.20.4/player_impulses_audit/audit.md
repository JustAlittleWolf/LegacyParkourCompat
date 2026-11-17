# Independent blind audit: 1.20.2--1.20.4 player impulses

Verdict: **ACCEPT** for the bounded no-difference claim in `player_impulses/coverage.md` only.

## Immutable claim identity

- Report: `build/later-source-20261116/1.20.2--1.20.4/player_impulses/coverage.md`
- SHA-256: `298383B09F39FB0E32E47A9D40766C058121F3FAC73641E37F0A4E9257817D0C`
- Scope: local-player collision push, direct knockback application, entity-motion packet encoding/receiving, and explosion knockback application for 1.20.2 Mojmap versus 1.20.4 Mojmap.

## Evidence audit

Both ready markers name the requested exact version and Mojmap, and report `status=ready`. Their source-manifest SHA-256 values match the markers. The two provenance files match the assigned sidecar hashes: 1.20.2 `1DAD9F725F6CB25B6568700FA0294F83B66EA9633B0D62ED354D7BD8B45D4F05`; 1.20.4 `10914D9B7B3FC29DE066CA24F7D905E4D4F0A451A6C81729A2D1981ADDFC1385`. Relevant decompiled source hashes match their source ledgers. The audit additionally verified the omitted `LocalPlayer.java` and `Explosion.java` digests; `ClientboundExplodePacket.java` hashes in the report were also confirmed:

| Source | 1.20.2 SHA-256 | 1.20.4 SHA-256 |
| --- | --- | --- |
| `net/minecraft/client/player/LocalPlayer.java` | `bb5cbfb03656a1866bb77c00431618befe792081223b35db4bd121ecbb151fd5` | same |
| `net/minecraft/network/protocol/game/ClientboundExplodePacket.java` | `deb8a038dd837ae129e1e6de48126b278fcfdb31f6e2ac22199776c594ad4ddf` | `9895537d2e55927b9ffff3a8d4599e117693e655fcfba0e2aec173b2dc1905b` |
| `net/minecraft/world/level/Explosion.java` | `6c6ab656f1bd9116bd62eb66696a847512c70c8b6368aeb4e9f470ff22eb212a` | `a4f5eee48cd4a2578e1f1e0224b6da30c45daaeb3656496d84b09549b9c45d39` |

## Independent comparison

- **Collision push:** `LivingEntity.aiStep` reaches `pushEntities` at the same phase in both sources. On the client it queries nearby `Player` instances with `EntitySelector.pushableBy`; that selector requires a pushable, non-spectator local-player candidate. `doPush` invokes the candidate's `push(this)`. The `Entity.push(Entity)` gates and arithmetic match in order: same-vehicle/no-physics checks, `Mth.absMax`, `0.01F` threshold, square root normalization, reciprocal clamp, `0.05F` scaling, axis order and `hasImpulse` write. `LivingEntity.push(Entity)` also suppresses the response while the receiving local player sleeps. The report's reachable precondition is valid; its stated exclusions do not conceal a changed LocalPlayer override (the exact LocalPlayer sources are byte-identical).
- **Direct knockback:** `LivingEntity.knockback` has identical resistance multiplication, `strength <= 0.0` guard, impulse write, old-velocity read, normalized/scaled horizontal impulse, and grounded vertical cap/preservation order. `LocalPlayer` inherits this implementation in both releases.
- **Entity-motion packet:** packet constructors clamp each velocity component to `[-3.9, 3.9]`, multiply by `8000.0`, and truncate through an `int` cast identically. Wire write/read ordering and short representation match. Both client handlers ensure main-thread execution, look up the entity, and only when present pass each signed component divided by `8000.0` to `lerpMotion`; `Entity.lerpMotion` sets delta movement identically. A packet for the local player therefore overwrites its velocity in the same way.
- **Explosion response:** both handlers construct the packet-backed explosion, call `finalizeExplosion(true)`, then add packet knockback X/Y/Z to the local player's current movement in that order. The packet's three components are float-cast on construction (or zeroed for null), read and written in the same place and exposed through the same getters. 1.20.4 adds other packet and explosion configuration fields but leaves this local movement addition and knockback payload unchanged.

No changed writer or precondition was found within these bounded routes. Exact method bodies were inspected in context; this verdict is not based only on matching method names or hashes.

## Limits retained

This accepts only this bounded slice and its no-difference result. It does not accept or freeze the full 1.20.2--1.20.4 pair, establish tick-inventory completeness, close collision-provider or producer-side dependencies, follow server attack/damage or server-side explosion/knockback vector production, or establish runtime parity. The report itself preserves those limitations. No implementation, product or canonical coverage changes were made.
