# Independent source audit: climbing movement r3

Verdict: **REQUEST CHANGES**

Audited candidate: `build/later-source-20261116/1.20.6--1.21.11/climbing_movement_revision_r3/coverage-r3.md`
Candidate SHA-256: `A23971DCA846B1CBA7FCC6DA48AAF046FCD59644D3641F4A6298410D15A2565A`

## Identity and source integrity

The exact endpoint roots are the ready Mojmap source sets under `decompiled_minecraft/1.20.6/mojmap` and `decompiled_minecraft/1.21.11/mojmap`.

- 1.20.6: ready marker SHA-256 `1ec1dc773ea2a8a3ef48a9fc868893bd2f83d6b13b057d75b151b711788c3d41`; source manifest SHA-256 `56aae10684471d7abb1c366bd5dd431ab976112a87e6cc5c687a68f1eff06311`; artifact manifest SHA-256 `e5882622bcf22b3e3c2c96c73843a1308e11e3096f4feefd945ea5808933ce31`; provenance SHA-256 `ee53e67e7ab51069a0eaae5a0ce13d49a9f972bdc13e3c31e041afa77f4d26e8`.
- 1.21.11: ready marker SHA-256 `0ad98d0ebd654650492c97eb58bc324d33864787f0ce10d99faec3ae2f5b804b`; source manifest SHA-256 `0c4d83fef84c101d9db88f331acec7960f3c8346228c28995e6cf57bbfcae555`; artifact manifest SHA-256 `c8210b15012dcc4e109c2f73015b4117abec1f8ab04fdd375dbc11419e9e246c`; provenance SHA-256 `99c9fb741eb5e4ac8fb5780e2158da324d7c8b6afc179d494e5986f78fe29f72`.

The ready marker identity fields agree with each version and mapping; declared source/artifact manifest digests match their files. I independently checked all 24 source-file hashes listed in the candidate against endpoint manifests and local bytes; every entry matches.

## Supported source claims

The predicate difference is supported. In 1.20.6, `Player` has no `onClimbable()` override, so a non-spectator can use `LivingEntity.onClimbable()`'s open-trapdoor plus matching-facing ladder fallback (`decompiled_minecraft/1.20.6/mojmap/net/minecraft/world/entity/LivingEntity.java:1541–1568`). In 1.21.11, `Player.onClimbable()` returns false whenever `abilities.flying` (`decompiled_minecraft/1.21.11/mojmap/net/minecraft/world/entity/player/Player.java:2043–2045`). Both `Player.travel` wrappers save the incoming Y velocity, call `super.travel`, then restore Y as the saved value times `0.6`; the older wrapper additionally resets fall distance after the call (`decompiled_minecraft/1.20.6/mojmap/net/minecraft/world/entity/player/Player.java:1485–1494`; `decompiled_minecraft/1.21.11/mojmap/net/minecraft/world/entity/player/Player.java:1375–1381`). Thus a position change already applied during `move(SELF)` would survive the wrapper.

The added empty-entity-query precondition is also supported. Both `Entity.collide` paths query entity collision shapes over the expanded movement box (`Entity.java:1044–1048` in 1.20.6; `Entity.java:1044–1048` in 1.21.11), and the `LevelAccessor`/`CommonLevelAccessor` chain delegates to `EntityGetter.getEntityCollisions`. For a non-null player, that query filters by `NO_SPECTATORS` and `canCollideWith`, inflates the query by `1.0E-7`, and returns an empty list when no entity matches (`EntityGetter.java:57–75` in 1.20.6; `EntityGetter.java:54–72` in 1.21.11). The trapdoor/ladder shapes at the stated center are disjoint from the player box in the horizontal plane, so the geometric condition can preserve a vertical movement request.

## Required correction: the stated witness produces no position delta

The candidate's shift-held flying witness assumes that `LocalPlayer` suppresses ladder sliding when shift is held. That assumption is false while flying. In both endpoint versions, `LocalPlayer.isSuppressingSlidingDownLadder()` is explicitly:

- 1.20.6 `LocalPlayer.java:352–355`: `return !this.getAbilities().flying && super.isSuppressingSlidingDownLadder();`
- 1.21.11 `LocalPlayer.java:364–367`: same `!flying` guard.

The candidate sets `abilities.flying = true`, so this method returns false at both endpoints and never consults the shift-key predicate. In 1.20.6 the inherited climbable helper therefore only applies `y = max(y, -0.15F)` to the downward velocity; it does **not** enter the branch that changes negative Y to zero. `LocalPlayer.aiStep` creates exactly the clamped threshold velocity from the stated default: `-1 * .05F * 3.0F` (`LocalPlayer.java:764–775`). Its binary32 result equals the promoted `-0.15F` clamp threshold. Thus old and current `move(SELF)` receive the same `-0.15` vertical request for this zero-horizontal-input state, so the claimed one-tick position divergence is not established.

The old/current `onClimbable()` predicate difference and the helper's response remain source-backed, but this concrete witness does not activate the asserted suppression effect. A corrected finding needs a reachable flying-player velocity whose component actually exceeds a helper clamp (or another verified surviving movement consequence), with its full source path and the entity/block collision preconditions checked. Otherwise, narrow the accepted claim to the predicate branch difference and leave its movement consequence open.

## Limits

This verdict is only for the immutable bounded candidate above. It does not establish the first release containing the flying override, close intermediate versions or full-pair coverage, or establish runtime parity.
