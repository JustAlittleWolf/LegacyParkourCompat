# Independent blind source review: player edge backoff collision probe

**Verdict: ACCEPT.** The central finding is source-supported for 1.20.4 Mojmap → 1.20.5 Mojmap. The reviewed immutable draft is SHA-256 `300F40471A3005A909603184944767C110C0B57B6319A4B675A832506C72D7C3`. I independently retraced the paired Player helpers, Entity dispatch/collision path, and collision-query providers. I did not assess pair completeness or freeze.

## Paired source evidence

All references below are exact decompiled Mojmap files under `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\<version>\mojmap\`.

- `net/minecraft/world/entity/player/Player.java`: 1.20.4 `maybeBackOffFromEdge` 1048–1100 and `isAboveGround` 1102–1106; 1.20.5 `maybeBackOffFromEdge` 1067–1116, `isAboveGround` 1118–1120, `canFallAtLeast` 1122–1125. 1.20.4 checks `noCollision(this, getBoundingBox().move(dx, -maxUpStep(), dz))` in all three 0.05-backoff loops; 1.20.5 checks the shifted horizontal footprint AABB from `minY - step - 1.0E-5F` to `minY`. The 1.20.4 ground predicate uses `onGround() || fallDistance < maxUpStep() && !noCollision(fullBox.move(0, fallDistance - maxUpStep(), 0))`; the 1.20.5 predicate uses `onGround() || fallDistance < step && !canFallAtLeast(0, 0, step - fallDistance)`.
- The outer gate is paired at Player 1049–1053 (1.20.4) and 1068–1073 (1.20.5): no flight, vertical movement at/below zero, SELF or PLAYER mover, `isStayingOnGroundSurface()`, and the respective `isAboveGround`. The shift-key condition is directly confirmed at Player 310–312 (1.20.4) and 330–332 (1.20.5).
- Ordinary local travel reaches the override: `net/minecraft/world/entity/LivingEntity.java` 2035–2067 (1.20.4) and 2104–2136 (1.20.5) call `move(MoverType.SELF, ...)`; `LocalPlayer.move` 871–875 / 878–882 delegates to `super.move`. `Entity.move` 596–617 / 597–618 invokes virtual `maybeBackOffFromEdge` before `collide`; `Entity.collideWithShapes` 915–950 / 916–951 resolves the adjusted vector. Thus a non-flying local player holding shift during a non-upward SELF travel step can reach the changed Player logic, subject to the remaining ground and collision gates.
- `net/minecraft/world/level/CollisionGetter.java` 46–62 (both releases) queries block collisions, entity collisions, and then the border shape for the supplied AABB; lines 83–85 construct `BlockCollisions`. `net/minecraft/world/level/BlockCollisions.java` 32–39 supplies `CollisionContext.of(entity)` and stores the query AABB; lines 64–89 intersect translated block collision shapes against that AABB. These provider bodies are byte-identical in 1.20.4, 1.20.5, and 1.20.6.

## Reachable consequence and gates

For an eligible shifted, non-flying player, choose a horizontal probe position where a context-dependent collision shape intersects the old lowered full player box only in the portion above the feet, and does not intersect the new feet-to-drop slab. At that candidate, old `noCollision` is false and its loop stops removing that horizontal component; new `canFallAtLeast` is true and the loop removes a signed 0.05 increment (continuing until its probe hits a collision or reaches zero). This changes the displacement supplied to Entity collision resolution. The outer `isAboveGround` probe also changes, so its gate can independently change whether the loop is entered when the player is not already on ground. These are source-derived decision consequences; the eventual resolved trajectory depends on the rest of the queried world geometry.

Open applicability gates are exactly the source gate above, plus whether a particular level's block/entity/border shape intersects one AABB but not the other. No new historical block state is implied; vanilla state and its player-context collision shape are queried. The iteration arithmetic is also changed in code (`Math.signum` increments, `Math.abs` cutoff and `break` after zeroing), but for ordinary finite movement components the per-step signed 0.05 behavior is consistent with the old branch logic; the decisive general difference established here is the queried AABB and consequent query result. The `NaN` distinction between `y <= 0` and `!(y > 0)` is outside ordinary movement applicability and is not relied upon.

## Source identity and hash verification

Ready markers identify versions 1.20.4, 1.20.5, and 1.20.6 under Mojmap. The exact source-manifest SHA-256 values match each ready marker: 1.20.4 `fd3c8668483e5ff208c847474f6cbd3952a909c4aa602ed98b24e3d11a9602a1`; 1.20.5 `162dac6c4f1d539a1c2cea0255b7938a5281c207ab460bdc265358016d732484`; 1.20.6 `56aae10684471d7abb1c366bd5dd431ab976112a87e6cc5c687a68f1eff06311`. Each cited file hash below was checked against its source-manifest entry and the on-disk file hash.

| Version | Source file | SHA-256 |
|---|---|---|
| 1.20.4 | `world/entity/player/Player.java` | `218da60bc4f8c9279f56eb87e2cf2c0af79562fbe3d818a408429c656efb95dd` |
| 1.20.4 | `world/entity/Entity.java` | `07383522bff169938136638ef8c3244ca511b56ca4266913524f99f9821331b9` |
| 1.20.4 | `client/player/LocalPlayer.java` | `bb5cbfb03656a1866bb77c00431618befe792081223b35db4bd121ecbb151fd5` |
| 1.20.4 | `world/entity/LivingEntity.java` | `f7bc53db24c1798f19f9bd6f6356c86d5e560e9c8e8aac60decaf15cc785e07d` |
| 1.20.4 | `world/level/CollisionGetter.java` | `f21daa1ed0365cc16a2638373d702fbaeb4242ad12a80a41105cf43c711e6d95` |
| 1.20.4 | `world/level/BlockCollisions.java` | `3313a38f9ebbd389f62e95dcbedab6a4f7f3cd3920b9ffa0ecf1a77dd869725b` |
| 1.20.5 | `world/entity/player/Player.java` | `785d93ccc94e1f912e545b2b0c355edeb352b44ee8e83a69364daec35266dbe2` |
| 1.20.5 | `world/entity/Entity.java` | `71cd6b9f6c002684154dce11d3745e8714d82f13c8e1b3aa56743930131c18f3` |
| 1.20.5 | `client/player/LocalPlayer.java` | `6b429dfa6e0681251ec985dda1627f808652a7bbe5b70dc85c8fa0fe0ed46ffa` |
| 1.20.5 | `world/entity/LivingEntity.java` | `c66ec8dc3b1856e490e5834a46185589030d9fbc411e3e2ce64c73203cd753b2` |
| 1.20.5 | `world/level/CollisionGetter.java` | `f21daa1ed0365cc16a2638373d702fbaeb4242ad12a80a41105cf43c711e6d95` |
| 1.20.5 | `world/level/BlockCollisions.java` | `3313a38f9ebbd389f62e95dcbedab6a4f7f3cd3920b9ffa0ecf1a77dd869725b` |
| 1.20.6 | `world/entity/player/Player.java` | `785d93ccc94e1f912e545b2b0c355edeb352b44ee8e83a69364daec35266dbe2` |
| 1.20.6 | `world/level/CollisionGetter.java` | `f21daa1ed0365cc16a2638373d702fbaeb4242ad12a80a41105cf43c711e6d95` |
| 1.20.6 | `world/level/BlockCollisions.java` | `3313a38f9ebbd389f62e95dcbedab6a4f7f3cd3920b9ffa0ecf1a77dd869725b` |

The 1.20.4 and 1.20.6 provenance records identify an exact batch `decompileMinecraft` run using `mappingOption: mojmap`; the 1.20.5 provenance identifies `mappingFamily: official Mojang client mappings`, version metadata 1.20.5, client artifact SHA-256 `49d5949adbb1021a7eb00c1b949f9e39934b10249cbf86d25004774d1b3ff510`, and official client mappings SHA-256 `9afdb3b7fcc4c6acbec3bc6ccb46192bfc6460e12bf9e268e29bfee46bc7fbac`. As later context only, 1.20.6 `Player.java` has the same source hash and helper body as 1.20.5; this does not establish the exact intermediate introduction boundary.
