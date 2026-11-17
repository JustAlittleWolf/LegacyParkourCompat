# Flying-player climbable predicate difference (r4)

## Accepted source fact; movement consequence unproven

For a non-spectator `Player` whose inherited `LivingEntity.onClimbable()` predicate would return true, the endpoint predicate differs when the player's flying ability is enabled:

- In 1.20.6, `Player` declares no `onClimbable()` override, so the call uses `LivingEntity.onClimbable()`. That method returns true for an occupied block in `BlockTags.CLIMBABLE`, or for an open trapdoor when the block below is a ladder with matching facing. (This statement excludes the spectator case.)
- In 1.21.11, `Player.onClimbable()` first returns false when `abilities.flying` is true; only otherwise does it delegate to `super.onClimbable()`. The inherited 1.21.11 predicate still contains the same open-trapdoor/matching-ladder fallback, and this comparison excludes fall-flying's separate inherited gate.

Thus for the stated eligible block/predicate state, non-spectator, and flying ability, the direct source predicate result is true in 1.20.6 and false in 1.21.11. This is an endpoint source fact only. The earlier proposed one-tick position witness is withdrawn: with flight and shift held, `LocalPlayer.isSuppressingSlidingDownLadder()` is false at both endpoints; the examined zero-horizontal-input flight request is exactly the `-0.15F` lower clamp threshold, so it does not distinguish the old helper's clamp from the current bypass. No reachable input/path producing a surviving movement, position, or fall-distance consequence has been established here. Do not treat this predicate contrast alone as a movement behavior finding or implementation-ready delta.

## Source identities

Canonical source root is read-only `decompiled_minecraft/`. Both endpoints are ready Mojmap source sets.

- 1.20.6 ready marker SHA-256 `1ec1dc773ea2a8a3ef48a9fc868893bd2f83d6b13b057d75b151b711788c3d41`; source manifest SHA-256 `56aae10684471d7abb1c366bd5dd431ab976112a87e6cc5c687a68f1eff06311`; artifact manifest SHA-256 `e5882622bcf22b3e3c2c96c73843a1308e11e3096f4feefd945ea5808933ce31`; client SHA-256 `02dfd345ac1ad55692d5dbc8486ac7e4fea72cd54ac494a79cd48963048e56b2`; provenance SHA-256 `ee53e67e7ab51069a0eaae5a0ce13d49a9f972bdc13e3c31e041afa77f4d26e8`.
- 1.21.11 ready marker SHA-256 `0ad98d0ebd654650492c97eb58bc324d33864787f0ce10d99faec3ae2f5b804b`; source manifest SHA-256 `0c4d83fef84c101d9db88f331acec7960f3c8346228c28995e6cf57bbfcae555`; artifact manifest SHA-256 `c8210b15012dcc4e109c2f73015b4117abec1f8ab04fdd375dbc11419e9e246c`; client SHA-256 `1473c9489ac50fda3c435049a76a70d61a10b8610db27f5ba9d8756b686cd3bd`; provenance SHA-256 `99c9fb741eb5e4ac8fb5780e2158da324d7c8b6afc179d494e5986f78fe29f72`.

## Compared source

The source-file hashes below match their endpoint `mojmap.sources.sha256` entries and local bytes.

- 1.20.6 `net/minecraft/world/entity/player/Player.java`, SHA-256 `785d93ccc94e1f912e545b2b0c355edeb352b44ee8e83a69364daec35266dbe2`: complete class has no `onClimbable()` override; `travel` wrapper lines 1473–1495 is not used to claim a movement outcome here.
- 1.20.6 `net/minecraft/world/entity/LivingEntity.java`, SHA-256 `c66ec8dc3b1856e490e5834a46185589030d9fbc411e3e2ce64c73203cd753b2`: `onClimbable()` and `trapdoorUsableAsLadder` lines 1541–1568.
- 1.21.11 `net/minecraft/world/entity/player/Player.java`, SHA-256 `8e97167350a91741d0aa10d3b0d92a33150ed6dccdc94cd5b37d9c7ca22bcc81`: flying guard override lines 2043–2045.
- 1.21.11 `net/minecraft/world/entity/LivingEntity.java`, SHA-256 `19ed2d565858c401c69a06750b054a633a20ea864cab3a753b5c767f0bdd60e8`: `onClimbable()` and `trapdoorUsableAsLadder` lines 1669–1696.

## Limits and open gates

The exact release that introduced the flying-player override and all intermediate forms remain unexamined. This does not close source coverage for 1.20.6→1.21.11, establish a reachable player movement delta, establish runtime parity, or establish implementation readiness. Independent audit of this r4 source-only statement remains required.
