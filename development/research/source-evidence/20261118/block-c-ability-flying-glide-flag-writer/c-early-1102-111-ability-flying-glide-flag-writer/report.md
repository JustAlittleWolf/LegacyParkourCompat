# 1.10.2 → 1.11: ability-flight branch with an active glide flag

## Bounded result

No movement difference is established for this path. In both exact Feather source sets, a non-riding player with `abilities.flying == true` enters `PlayerEntity.moveRelative`'s ability-flight branch. That branch calls `LivingEntity.moveRelative` first and then calls `setFlag(7, false)` directly. If flag 7 was already true, the superclass call takes its fall-flying path and performs the glide movement before the player override clears the flag. On a logical client, the superclass's own post-move clear is gated off by `!world.isClient`; the direct player clear still runs. This order is the same in 1.10.2 and 1.11.

The movement call's API changed: 1.10.2 calls the three-argument `move`, while 1.11 calls `move(MoverType.SELF, ...)`. The paired evidence below establishes the same caller branch and state-write order; this report does not assert a broader equivalence for every `Entity.move` behavior.

## Exact source identity and hashes

- 1.10.2, `ornithe-feather` (Feather Gen2 1.10.2 build.2). Local provenance: `decompiled_minecraft/1.10.2/ornithe-feather.provenance.json`, SHA-256 `13b208320bd6316d4b20eb0733f226cae151cae0ffd55ef3331a97223114adf6`. Mapping artifacts pinned in `decompiled_minecraft/1.10.2/artifacts.sha256`: `feather-gen2-1.10.2+build.2-mergedv2.jar` SHA-256 `18cffc56c2d8de89b50cb0328b174566236553c4aafb62afdc58a1e0ff0cadb0`; `feather-gen2-1.10.2+build.2.tiny` SHA-256 `7c4055aa9becb027462fe4f4a9af8822de4df9b70ecb80a84e38fd9e81e33af1`.
- 1.11, `ornithe-feather` (Feather Gen2 1.11 build.2). Local provenance: `decompiled_minecraft/1.11/ornithe-feather.provenance.json`, SHA-256 `6f775c806fd1401a4d1756e9ef88da8d73185fe1d13598235f85c424405f4c40`. It records resolved release `1.11`, mapped-client SHA-256 `d9359dadc1c81c2795ba84e5b577bf65af364ed1d33890b2ff6c8bf9059bb589`, mapping JAR SHA-256 `658ad8c5a5d661eb5c2f97c00a47f6e02c02d5eee1eb1005f8e2926eb95dec44`, and Tiny SHA-256 `46bd6702a0983ad667109aadad90b2e6390ed5cd5999561a8a904956f4baf9c0`.
- Source-file SHA-256 values were checked against the corresponding local `ornithe-feather.sources.sha256` inventories and recomputed from the files:
  - 1.10.2 `net/minecraft/entity/living/player/PlayerEntity.java`: `a055b84b98d98e828e177cad9bba47a1334ee9bf3ecd4331792979106235a302`.
  - 1.10.2 `net/minecraft/entity/living/LivingEntity.java`: `d40dd476b6b68c6ce45b4202823475deb546ecda2284da330ff6724b33815e82`.
  - 1.10.2 `net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java`: `a9637065f21ad67464eb5c204c74ebf228c3bb0da8a96ddf4ae73c0490fed443`.
  - 1.11 `net/minecraft/entity/living/player/PlayerEntity.java`: `a0d787681de5caecda03fea1536dac0c07e6ca5cfbfe3b5fbb700cbe77c86fb6`.
  - 1.11 `net/minecraft/entity/living/LivingEntity.java`: `1f2e31fa6a905ccc2a51a0338f2aaf448f2b0569f28644e8205d864e570d9b2b`.
  - 1.11 `net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java`: `65c2747bd8c70def6be7f41f624d4c9493342b39ae7bed7967f9ff63608f59ed`.

## Paired trace

1.10.2, Feather:

- `net/minecraft/entity/living/LivingEntity.java`, `moveRelative(float,float)`, lines 1332–1384: tests fall-flying state, computes glide response, calls `this.move(velocityX, velocityY, velocityZ)` at 1371, then only clears flag 7 on ground when `!world.isClient` at 1382–1383.
- Same file, enclosing travel routine, lines 1742–1747: after speed adjustments and `flyingTick()`, invokes `this.moveRelative(sidewaysSpeed, forwardSpeed)`. Dynamic dispatch reaches the player override.
- `net/minecraft/entity/living/player/PlayerEntity.java`, `moveRelative(float,float)`, lines 1391–1408: when `abilities.flying && !isRiding()`, calls `super.moveRelative` at 1399, then performs the direct `setFlag(7, false)` at 1403. Thus any superclass glide movement precedes this clear.
- `net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java`, `mobTick()`, lines 713–735: for a player with `abilities.canFly`, ordinary non-spectator double-jump input toggles `abilities.flying`; separately, the fall-flight start request requires `!isFallFlying()` and `!abilities.flying` at 730–734. A previously active glide can therefore coexist with subsequently enabled creative flight; no claim about a particular event timing is needed for the branch trace.

1.11, Feather:

- `net/minecraft/entity/living/LivingEntity.java`, `moveRelative(float,float)`, lines 1386–1438: the same fall-flying branch calls `this.move(MoverType.SELF, velocityX, velocityY, velocityZ)` at 1425; its on-ground flag-7 clear at 1436–1437 is also gated by `!world.isClient`.
- Same file, enclosing travel routine, lines 1796–1801: after `flyingTick()`, invokes `this.moveRelative(sidewaysSpeed, forwardSpeed)`.
- `net/minecraft/entity/living/player/PlayerEntity.java`, `moveRelative(float,float)`, lines 1385–1402: the same `abilities.flying && !isRiding()` gate invokes `super.moveRelative` at 1393, then directly calls `setFlag(7, false)` at 1397.
- `net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java`, `mobTick()`, lines 725–747: the equivalent ordinary double-jump flight toggle and fall-flight-start exclusion are present.

## Movement interpretation

For the bounded ordinary local-player pre-state (`abilities.flying`, not riding, flag 7 already true, client world), `LivingEntity` consumes the active glide flag for this call and performs its glide movement. Its own ground-contact clear is server-only. Control then returns to `PlayerEntity.moveRelative`, whose unconditional direct flag-7 clear in the ability-flight branch ends the glide state. This exact sequence and the direct clear's authority are present in both releases. The source difference in the move overload is recorded above; this trace does not classify it as a player-visible delta.

Excluded from this review: fall-distance behavior, glide-startup and NBT paths, packet or network behavior, artificial state injection, runtime/A-B validation, and other movement paths. This is a bounded finding report, not full pair coverage or acceptance.
