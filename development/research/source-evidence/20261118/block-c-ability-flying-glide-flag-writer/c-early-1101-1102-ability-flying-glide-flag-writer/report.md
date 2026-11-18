# 1.10.1 → 1.10.2: creative flight while fall-flying

**Result: no version-specific movement difference in the assigned path.** This is a bounded no-difference report, not a movement finding.

## Exact source identity

Both source trees are `ornithe-feather`, mapped with Feather `1.10.1+build.2` and `1.10.2+build.2` respectively. The 1.10.1 provenance records the Mojang client SHA-256 `338b205f20c22b9c676d9d3227a5e6c5e190f30ad84d7caad035150b3e05ba72`, Feather merged-v2 artifact SHA-256 `6e8ba44832b7b77d913962462bde55084005d5de6389232453afc0bc0339ea37`, and Feather metadata SHA-256 `8648e97ee398b11151cb2d441161629abde561afed9c933c18fbc622e2c9904a`. Its decompile provenance records the source SHA-256s below. The 1.10.2 ready record identifies 1.10.2 and Feather build.2; its artifact manifest pins client SHA-256 `7cdf7fcdc1c92584a233bf3c42bd7f0df1bdad3007d306831fe50410692be1e9`, merged-v2 mapping SHA-256 `18cffc56c2d8de89b50cb0328b174566236553c4aafb62afdc58a1e0ff0cadb0`, Tiny mapping SHA-256 `7c4055aa9becb027462fe4f4a9af8822de4df9b70ecb80a84e38fd9e81e33af1`, and source-manifest SHA-256 `91b0f478acb7b6f13463c35b268a30d2806f583402631a56f54ce2eb70d1ec71`.

| Source member | 1.10.1 SHA-256 | 1.10.2 SHA-256 |
| --- | --- | --- |
| `entity/living/LivingEntity.java` | `d40dd476b6b68c6ce45b4202823475deb546ecda2284da330ff6724b33815e82` | `d40dd476b6b68c6ce45b4202823475deb546ecda2284da330ff6724b33815e82` |
| `client/entity/living/player/LocalClientPlayerEntity.java` | `a9637065f21ad67464eb5c204c74ebf228c3bb0da8a96ddf4ae73c0490fed443` | `a9637065f21ad67464eb5c204c74ebf228c3bb0da8a96ddf4ae73c0490fed443` |
| `entity/living/player/PlayerEntity.java` | `020370cffde2d1fe9f1b63fa5d51f1af2fab095908b19e79f88a7bcac7dfd501` | `a055b84b98d98e828e177cad9bba47a1334ee9bf3ecd4331792979106235a302` |

The full `PlayerEntity` source diff is only its saved `DataVersion` constant (`511` → `512`); the `moveRelative(float, float)` body is unchanged.

## Reachable local path and ordering

`LocalClientPlayerEntity.mobTick()` (both versions, lines 713–737) handles ordinary flight input when `abilities.canFly` is true. In non-spectator mode, the jump double-tap toggles `abilities.flying` and calls `syncAbilities()`. The local override sends a `PlayerAbilitiesC2SPacket` (lines 312–314); the client handler for server-sent abilities packets applies `isFlying()` to `player.abilities.flying` (1314–1322). The local method then enters `super.mobTick()` (line 778), reaching the superclass movement path. Thus the player override below reads the current ordinary ability state. This establishes the normal input/synchronized-state route only; it makes no timing or latency claim.

With `abilities.flying == true`, no mount, and a valid active fall-flight flag, `LivingEntity.travel()` calls `flyingTick()` and then virtual-dispatches to `moveRelative(sidewaysSpeed, forwardSpeed)` (1742–1747). `flyingTick()` preserves flag 7 only when airborne, unmounted, and equipped with a usable Elytra (1754–1772); on the logical client it does not write that flag because its final `setFlag` is server-only.

The first actual move is inside `LivingEntity.moveRelative(float, float)` (1332–1371). Its `isFallFlying()` branch performs the gliding velocity operations and calls `this.move(this.velocityX, this.velocityY, this.velocityZ)` at line 1371. That branch is reached through `PlayerEntity.moveRelative(float, float)` (1391–1405): the ordinary ability-flying predicate takes its first arm and calls `super.moveRelative(...)`, so the active fall-flight flag is read by the superclass for this first move. After that superclass call returns, the player override directly calls `setFlag(7, false)` at line 1403. The owner of this write is `PlayerEntity.moveRelative`, after the gliding move and before `LivingEntity.travel()` returns. `Entity.getFlag`/`setFlag` (1704–1713) access bit 7 of the synchronized shared-flags byte.

These path predicates, operation order, first move, and post-super direct flag-7 write are the same in 1.10.1 and 1.10.2. The assigned slice establishes no adjacent-version behavior boundary.
