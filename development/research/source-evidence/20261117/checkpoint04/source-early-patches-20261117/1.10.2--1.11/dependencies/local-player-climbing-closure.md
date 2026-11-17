# Local-player climbing predicates and push response (1.10.2 → 1.11)

## Exact inputs

The paired source roots are `decompiled_minecraft/1.10.2/ornithe-feather/` and `decompiled_minecraft/1.11/ornithe-feather/`. The bound pair inventory `build/source-early-patches-20261117/1.10.2--1.11/coverage.md` (SHA-256 `b6789f01de090321f9f05e79a99b35e9ee6739a141a26ded0b510d643fb24069`) identifies the 1.10.2 ready marker/source manifest as `4c6d236e1d06ffc365452c48e73dfa2bf2414bbf4ea67a24a7010c5583906a2d` / `91b0f478acb7b6f13463c35b268a30d2806f583402631a56f54ce2eb70d1ec71`, and the 1.11 ready marker/source manifest as `3c4102230dba0f9d2d07f752bbd2a19d2717375f6e31fb618c0450b7fd82bd45` / `8b3e5da58494f1dba77410960753b0bf646425a1ada491d8242cc71e6c7a59ca`.

| Source | 1.10.2 SHA-256 | 1.11 SHA-256 |
| --- | --- | --- |
| `net/minecraft/entity/living/LivingEntity.java` | `d40dd476b6b68c6ce45b4202823475deb546ecda2284da330ff6724b33815e82` | `1f2e31fa6a905ccc2a51a0338f2aaf448f2b0569f28644e8205d864e570d9b2b` |
| `net/minecraft/entity/EntityFilter.java` | `91158a5477935c911178433e1b2628d1d063604a23b08baee4706dd286e7e813` | `49f2c3cbeeA0bb9421fdFAAD49A7742394f69cd73981a9cdd51e1badcf30c629` |
| `net/minecraft/entity/Entity.java` | `05da145effa19a6ef7934cc276e89226373b67c12f4ce89a8ce2183f29039f77` | `3209feaacd1e798b521a193eeb081d92c96881a70a18254ef24a58e911a4fd97` |
| `net/minecraft/entity/living/player/PlayerEntity.java` | `a055b84b98d98e828e177cad9bba47a1334ee9bf3ecd4331792979106235a302` | `a0d787681de5caecda03fea1536dac0c07e6ca5cfbfe3b5fbb700cbe77c86fb6` |
| `net/minecraft/client/entity/living/player/ClientPlayerEntity.java` | `d9f0ec60a318ac51fd49198aaccd5c3e2b3af95cd3e2df577bb25226f3e0e4e4` | `207a0ff4ee996916cd1fc73f47bfae64f92719916cafed8395b1f2a8f797809` |
| `net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java` | `a9637065f21ad67464eb5c204c74ebf228c3bb0da8a96ddf4ae73c0490fed443` | `65c2747bd8c70def6be7f41f624d4c9493342b39ae7bed7967f9ff63608f59ed` |

The accepted tick/input slice reused for ordinary local-player ancestry and travel routing is `dependencies/client-player-tick-input-r2.md` SHA-256 `5a510bcd0697ffb2cd45d8ba4aa4c4ec058261ef531a5e26da11e77a57e6b389`; its bounded correction verdict is `dependencies/client-player-tick-audit-r2/verdict.md` SHA-256 `33959e4f591f94dc937610f3baf49747d8708be0a5f8350881abacf67dcbac9c`.

## Climbing predicate: no movement-gate delta

`LivingEntity.isClimbing()` is textually the same at 1.10.2 lines 928–950 and 1.11 lines 978–1000. It floors `x`, `getShape().minY`, and `z`, returns false for a spectator `PlayerEntity`, reads the block state at that feet cell, and returns true for LADDER or VINE. Its only other true case is a `TrapdoorBlock` that is open and has a LADDER immediately below whose `LadderBlock.FACING` equals the trapdoor facing. The compared `canClimbTrapdoor` gate is identical. The predicate uses block identity/state, not ladder/vine collision shape or vine facing.

The ordinary local player inherits `PlayerEntity` → `LivingEntity`; `LocalClientPlayerEntity` extends `ClientPlayerEntity`, which extends `PlayerEntity`, in both paired sources. In `LivingEntity.moveRelative`, the sampled ground/climbing branch calls this same predicate before movement: it clamps horizontal velocity to `[-0.15, 0.15]`, resets fall distance, limits downward velocity to `-0.15`, and zeros downward velocity for a sneaking player. After the movement call, the same `collidingHorizontally && isClimbing()` gate sets vertical velocity to `0.2` in both releases. The 1.11 movement call now carries `MoverType.SELF`; resolver semantics are covered separately by the paired `Entity.move` work, not promoted here as a climbing difference. Accepted tick/input coverage also treats the sampled climbing travel branch as unchanged. No difference was found in climb detection or its climb/travel response.

## Changed player-relevant consumer: push eligibility

A separate 1.11 change calls the unchanged climbing predicate from `LivingEntity.isPushable()`:

- 1.10.2 `LivingEntity.java:1887–1889`: returns `!this.removed`.
- 1.11 `LivingEntity.java:1948–1950`: returns `this.isAlive() && !this.isClimbing()`.

For an alive, unremoved, non-spectator local player at a qualifying ladder/vine cell (or matching open-trapdoor/ladder pair), this changes `isPushable()` from true to false. This is reachable with ordinary vanilla block states and an ordinary player position; no world-generation or runtime assumption is needed.

The source response path is `LivingEntity.tickMovement`’s `pushAwayCollidingEntities()` call (1.10.2 line 1750; 1.11 line 1804), which queries `World.getEntities(this, this.getShape(), EntityFilter.canBePushedBy(this))`. The predicate first excludes each candidate for which `candidate.isPushable()` is false (1.10.2 `EntityFilter.java:64–67`; 1.11 `:58–61`). On a client world, its later source gate permits a candidate that is a local `PlayerEntity` (both releases' `LocalClientPlayerEntity.isLocal()` returns true). If a nearby ticked LivingEntity's query would otherwise select the local player, the new climbing-dependent predicate removes that player from the candidate list. The same selection path is used before `candidate.push(caller)`; `Entity.push` adds horizontal velocity to both entities, with the unchanged noClip/vehicle/distance guards. Thus the source establishes a direct local-player push-response predicate change while climbing, conditional on an overlapping push-caller and the ordinary collision/team gates. This report does not claim a resulting trajectory or that every neighboring entity executes this path.

The new predicate applies to all `LivingEntity` subclasses, so changes to mob-vs-mob and other non-player pushing are out of scope. The 1.11 `pushAwayCollidingEntities` method also adds max-entity-cramming damage handling; that health/damage behavior is excluded and not part of this movement claim. Player attack critical-hit uses of `isClimbing()` are also outside scope because attack/damage resolution is excluded.

## Boundary

This is a bounded source observation for the local-player climbing predicate/response only. It does not close all equipment, attributes, travel or provider dependencies, does not establish a net push trajectory, and does not freeze the exact pair or establish implementation/runtime status. No product or wiki inputs were used.