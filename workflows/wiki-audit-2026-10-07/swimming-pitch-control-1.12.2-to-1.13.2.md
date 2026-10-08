# Finding snapshot: swimming pitch control

Snapshot ID: `wiki-swimming-pitch-1.12.2-1.13.2`

Status: source-confirmed bounded finding; independent Wiki-lane review pending; runtime validation not performed.

## Candidate and boundary

The [Minecraft Wiki Swimming page](https://minecraft.wiki/w/Swimming) lists sprint-swimming as added in Java 1.13 snapshot 18w07a and describes pitch-directed vertical movement. The [Crawling page](https://minecraft.wiki/w/Crawling) says actual swimming made it easier to enter the low crawling position. The exact release sources compared here are 1.12.2 and 1.13.2; they establish a net source difference in `(1.12.2, 1.13.2]`, not the exact snapshot where the code first appeared.

## Finding

In 1.13.2, `PlayerEntity.moveRelative` applies a swimming-only vertical velocity correction before delegating movement input. It runs only when `isSwimming()` and not riding. It reads the player's look-vector Y component `g`; selects interpolation factor `0.085` when `g < -0.2`, otherwise `0.06`; and applies `velocityY += (g - velocityY) * h` only if `g <= 0`, jump input is active, or the fluid state at `(x, y + 1.0 - 0.1, z)` is nonempty. The method then continues through the existing flying or generic movement branch.

The 1.12.2 player `moveRelative` method has no swimming-specific condition or look-vector correction; its player-specific branch only adjusts creative flight, then delegates to `LivingEntity.moveRelative`. The exact source pair therefore confirms a player movement change associated with the new swimming path. It does not establish the Wiki's speed figures or all sprint-swimming/crawling behavior.

## Guards and reachable path

- 1.13.2 `Entity`'s `m_03231680()` calls `updateSwimming()`. Its state writer keeps an existing swim state only while sprinting, in water, and not riding; otherwise a non-swimming entity enters swimming only while sprinting, submerged in water, and not riding.
- 1.13.2 `PlayerEntity.updateSwimming()` clears swimming when creative flight is active, otherwise delegates to the entity state writer. `PlayerEntity.isSwimming()` also rejects flying and spectator players.
- `LivingEntity.travel()` calls `this.moveRelative(...)` in both exact releases, so the player override is reached by dynamic dispatch on the player's movement path. 1.13.2 `LocalClientPlayerEntity` has a separate sprint-stop branch while swimming; the finding above concerns the direct velocity consumer and does not emulate sprint eligibility or its hunger/food producer.

## Exact source evidence and identities

All source paths below are rooted at `D:\Javastuff\LegacyParkourCompat\build\movement-campaign-2026-10-07\ready\<version>\ornithe-feather`. Each cited source-file hash was independently recomputed and matches its `ornithe-feather.sources.sha256` entry; that manifest's file hash also matches the ready JSON.

| Version | Source path and lines | SHA-256 |
|---|---|---|
| 1.12.2 | `net/minecraft/entity/living/player/PlayerEntity.java`, `moveRelative()` 1386-1401 | `e4e0fdbe07a7d0a0ae4a70cbb6739a287c9d045a4a12b409895c220b5d91fe1e` |
| 1.12.2 | `net/minecraft/entity/living/LivingEntity.java`, `travel()` call to `moveRelative()` at 1847 | `190e9ac551538e015d9e4d6c42856e5ba32b593131cf6d93895e7b29533f1ee6` |
| 1.12.2 | `net/minecraft/entity/Entity.java`, checked for `updateSwimming`, `isSwimming`, `setSwimming` declarations/calls; none found | `80f091bf32166c88cf8bbd31caf72d84fa16224410733c7d2a0f00563f294a0a` |
| 1.13.2 | `net/minecraft/entity/living/player/PlayerEntity.java`, `moveRelative()` 1442-1465; `updateSwimming()` 1471-1477; `isSwimming()` 1819-1821 | `4ed22f6c5a3c55d67eed782070ac722201df4d624adbc90779f1fd29c2876633` |
| 1.13.2 | `net/minecraft/entity/living/LivingEntity.java`, `travel()` call to `moveRelative()` at 1926 | `bb691358c9a43c9f46e85575bf4d0a4ad671d0eb912502acc3a6a3f625e42f1c` |
| 1.13.2 | `net/minecraft/entity/Entity.java`, `m_03231680()` 947-951 and `updateSwimming()` 953-959 | `1d6ec8b80f74635401745c2c027bf36555c85348ca5693764f2668363b17d269` |
| 1.13.2 | `net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java`, sprint stop while swimming 748-758 | `2583495f3a02b4791aa036e6a8d354d7596c4984761969a0f29b29d8d9bf42bf` |

The 1.12.2 and 1.13.2 Feather source-manifest hashes in the unchanged ready records are respectively `b8a37ccfccd2aac5f40f5e34fec85873dbdfa93a266043c103e592e4a8c949da` and `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211`. The 1.13.2 derived Feather JAR was revised under `feather-r1-2026-10-07`; its original derived JAR is unavailable and equivalence remains **unproven**. This finding uses the published source tree and its verified source hashes, not a claim that the revised JAR equals the original.

## Applicability and exclusions

This is direct player velocity/movement behavior. It applies to a non-flying, non-spectator player in the swimming state, subject to the movement method's `!isRiding()` guard and the correction's additional predicate. No sprint-start eligibility, hunger, saturation, exhaustion, damage, attack, other-entity physics, block-state, rendering, or runtime consequence is claimed. The exact snapshot within 1.13 where the source change began remains unknown.
