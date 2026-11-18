# Fall-distance motion audit: 1.18.1 → 1.18.2

## Verdict

**Source difference with a reachable local movement consequence.** `Entity.checkFallDamage` accumulates the actual post-collision downward displacement into the float `fallDistance` in both releases, but 1.18.2 rounds each displacement to `float` before subtracting. A finite dry descent can put the two accumulators on opposite sides of the player's `maxUpStep` comparison. On the next local-player move, `Player.isAboveGround()` reads that accumulator and gates sneaking edge backoff, which can alter horizontal movement and position. The `checkFallDamage` gate, the movement reader, and the consequence are present in both releases; the arithmetic precision is the changed operation.

This is conditional on the existing support/collision arrangement satisfying the existing predicate. No block, fluid, bounce, or reset behavior change is proposed here.

## Frozen source identity

Exact Mojmap source roots: `decompiled_minecraft/1.18.1/mojmap` and `decompiled_minecraft/1.18.2/mojmap`.

- 1.18.1 publication: `mojmap.provenance.json` run ID `prep-1.18-1.18.1-mojmap-2026-10-09-r1`; exact client SHA-256 `737c28a56c8980fcd8574465c61538ebde8f511bdd035c69d0cbe27953f892aa`; official Mojang client-mapping SHA-256 `a7a4109255dfb74b9552b339482f0714bb0e7e7143d1059b0bf62a526d2c2a84`.
- 1.18.2 publication: `mojmap.provenance.json` identifies version `1.18.2`, namespace `mojmap`; exact client SHA-256 `1d09e3639644b6b2254499469d0765cc005a286d19f3fa595b0ed8fb07971ec7`. Its provenance reports `mappingArtifacts: null`, so no separate mapping-artifact digest is asserted.
- The ready markers and `mojmap.sources.sha256` rows match the cited Java file SHA-256s below.

Source hashes were frozen before source bodies were opened:

| Source | 1.18.1 SHA-256 | 1.18.2 SHA-256 |
|---|---|---|
| `net/minecraft/world/entity/Entity.java` | `69417675AE8E0A5BD88B7372BAEA1E73B1F5217154F39446C8AD5C92731437F1` | `2228FDACA5793171CBD94038306D571A6ADA78CA96F5734EFB4CADA5B744C10A` |
| `net/minecraft/world/entity/player/Player.java` | `1FB9DFB9F700323D77F4196DBC3618D01A65E56438B9CCE3EE38FA0B258BD7E2` | `BF639C1962FF90D69E4569B2B18F6FCF57AC46EF80B19686F0FBC1687FCA744A` |
| `net/minecraft/client/player/LocalPlayer.java` | `E38D0BBB6E5B9DE2A698609B8491D0007406B3BECD42BA9B95289F533788C996` | `99C2D18BCD23243AFB8F95C5BAFB21FB0BE7EA04AACBB14FCF7BE7CED2C9C095` |
| `net/minecraft/world/entity/LivingEntity.java` | `68FC9B15EB31690377AFE92ACFC783C86945FEDEC5739D28FAACD96DA4F9E0E7` | `DB4168D531CAF18F22E3FEFD073365E776DA4075CE01452BB9F7671D9B458782` |

## Paired source facts

- **Accumulation and gates:** 1.18.1 `Entity.java:541–580, 969–982`; 1.18.2 `Entity.java:543–593, 979–991`. `move` first lets `maybeBackOffFromEdge` adjust the requested vector, resolves collision into `$$2`, then sets `onGround = verticalCollision && requestedY < 0.0`. `checkFallDamage` receives `$$2.y`, the resolved displacement. It resets when `onGround` is true; otherwise it accumulates only when resolved `$$2.y < 0.0`. Thus a blocked downward request that becomes a landing does not take the accumulation branch, while open dry descent does.
- **Changed exact write:** 1.18.1 `Entity.java:980` is `this.fallDistance = (float)(this.fallDistance - $$0);`: the old float is promoted for double subtraction with resolved `dy`, then rounded once to float. 1.18.2 `Entity.java:990` is `this.fallDistance -= (float)$$0;`: it first rounds resolved `dy` to float, then subtracts in float precision. Both have the same `$$0 < 0.0` gate and operation location after movement resolution.
- **Next movement read:** both `Player.java` versions define `isAboveGround` (1.18.1 `1069–1072`; 1.18.2 `1082–1085`) as `onGround || fallDistance < maxUpStep && !noCollision(box.move(0, fallDistance - maxUpStep, 0))`. Both `maybeBackOffFromEdge` methods (1.18.1 `1019–1045`; 1.18.2 `1032–1058`) call it for a non-flying `SELF`/`PLAYER` move while `isStayingOnGroundSurface()` is true; that method is `isShiftKeyDown()` in both (`Player.java:301–303`). A true result enables the loop that backs off X/Z by 0.05 until the downward-offset test finds support. `LocalPlayer.move` delegates to `super.move` in both releases (1.18.1 `885–890`; 1.18.2 `889–894`), so this inherited path applies to the local player.

## Finite dry descent trace

The trace uses finite resolved downward displacements in open dry space (no vehicle, no collision, no water). With no collision, the collision result's Y equals requested Y, vertical collision is false, `onGround` is false, and the `checkFallDamage` resolved-Y gate is taken. A preceding accumulator value of `0.44999998807907104F` can itself be produced by three such updates from zero using resolved `dy` values `-0.1D`, `-0.15D`, and `-0.2D`; both formulas yield that same float for those three updates.

On the current descent move, start with `fallDistance = 0.44999998807907104F` and resolved `dy = -0.15D`. The path length squared is `0.0225`, so 1.18.2's added large-movement pre-write condition is bypassed. `onGround` remains false, and `checkFallDamage` writes:

| Release | Arithmetic | Result |
|---|---|---:|
| 1.18.1 | `(float)(0.44999998807907104F - (-0.15D))` | `0.5999999642372131F` |
| 1.18.2 | `0.44999998807907104F - (float)(-0.15D)` | `0.6000000238418579F` |

On the next move with `maxUpStep = 0.6F`, `onGround == false`, and shift held, the 1.18.1 value is less than `maxUpStep`, so `isAboveGround` evaluates the translated-box support check. A consistent geometry is a starting bottom gap of `0.1500000298` above existing support: the current `-0.15D` move resolves unobstructed and leaves a gap of about `2.98E-8`. The 1.18.1 check then moves the box down `5.9604645E-8`, intersects support, and reports support; edge backoff reduces the requested horizontal displacement in 0.05 steps. The 1.18.2 value equals `maxUpStep`, so its strict `<` short-circuits false and the edge-backoff loop does not run. This is a direct reachable read that can change local horizontal position on the next movement; it does not rely on fall damage or health calculation.

## Other fall-distance reads / dependency boundary

`Entity.move` in 1.18.2 adds a pre-position-update check (`Entity.java:564–572`): when resolved movement length squared is at least `1.0` and `fallDistance != 0`, it clips with `FALLDAMAGE_RESETTING` and `Fluid.WATER`, resetting on a non-miss. This has no effect on the finite witness above and returns a miss for the open dry path. Whether a particular collision/resetting block or fluid changes that branch belongs to the assigned collision/fluid dependency domains; this audit makes no proposal about them.

Other direct reads found in the paired `Entity`, `LivingEntity`, and `Player` sources concern landing/block callbacks, damage or attack/critical-hit behavior, and shoulder-entity cleanup (server-gated). Those do not provide an additional in-scope local velocity or position consumer. `Player.isAboveGround` is the movement consumer relevant to this ordinary descent trace.


## Phase-two reconciliation against the bounded vertical source and draft

The authorized vertical source report and canonical draft were rehashed before review and matched their assigned SHA-256 values. Their candidate arithmetic witness (`fallDistance = 0.25F`, resolved `dy = -0.08D`) is consistent with the exact bodies: the current write yields `0.33000001311302185F` in 1.18.1 and `0.32999998331069946F` in 1.18.2. This demonstrates a present-tick accumulator divergence; by itself it does not establish a same-tick velocity or position difference. The ordinary dry-air gravity and Y drag operation order in `LivingEntity.travel` is unchanged according to the bounded source report.

The downstream movement consequence is a separate, conditional next-move read. `Player.isAboveGround()` is unchanged across the pair; its strict `fallDistance < maxUpStep` comparison consumes the accumulator from the prior move. For a player state with `maxUpStep = 0.6F`, the finite trace above crosses that threshold. The following position effect additionally requires the existing support probe to succeed, shift to be held, a `SELF`/`PLAYER` move, and an edge candidate for which the 0.05 backoff loop finds no support under the full `-maxUpStep` offset. Those support and input conditions are applicability dependencies; they are not changes in the accumulator finding and this audit proposes no change to them. Consequently, the evidence supports a concrete reachable movement-predicate and horizontal-position divergence under those conditions, not a universal trajectory divergence.

The 1.18.2 pre-`setPos` reset remains a distinct earlier read: it is gated by nonzero fall distance and resolved movement length squared at least `1.0`, then by a non-miss clip against the resetting-block/water context. It is bypassed in the finite `-0.15D` current-move witness and misses in the stated open dry descent. Any hit-dependent reset belongs to the collision/fluid dependency review and is not part of this arithmetic or next-edge claim.