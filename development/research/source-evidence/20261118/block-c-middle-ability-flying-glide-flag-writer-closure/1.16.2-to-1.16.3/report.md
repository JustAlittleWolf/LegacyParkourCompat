# 1.16.2 → 1.16.3 Mojmap: ability flight and active Elytra flag

## Scope and result

Bounded comparison of the client player travel operation for a dry, airborne, nonpassenger player with an active chest Elytra and lawful ability flight. **No difference found between 1.16.2 and 1.16.3** in the real travel call, vertical-velocity handling, fall-distance reset, or direct shared flag 7 clear. The compared `Player.java` files have identical SHA-256 hashes. This is a slice result, not full pair coverage; no conclusion is made about when the behavior was introduced before 1.16.2.

## Exact source evidence

Both versions, Mojmap, `net/minecraft/world/entity/player/Player.java`, `travel(Vec3)` lines 1387–1416:

- The ability-flight branch is gated by `this.abilities.flying && !this.isPassenger()` (1402). In the assigned dry case, the swimming adjustment at 1391–1400 is not taken.
- The method saves the current vertical velocity and `flyingSpeed`, sets flying speed, calls `super.travel(vec3)` (1403–1406), then reads post-travel velocity and replaces its Y component with the saved Y times `0.6` (1407–1408). It restores `flyingSpeed` (1409), sets `fallDistance = 0.0F` (1410), and directly calls `setSharedFlag(7, false)` (1411), in that order. Movement statistics follow (1416).
- The alternate branch calls `super.travel(vec3)` without these two explicit post-travel writes (1412–1414).

Both versions, same file, `tryToStartFallFlying()` lines 1520–1538: an airborne player not already fall-flying, not in water, and without levitation can start when the chest slot is an `Items.ELYTRA` whose `ElytraItem.isFlyEnabled` predicate passes (1521–1525). `startFallFlying()` sets shared flag 7 true (1532–1534). This establishes the ordinary Elytra state relevant to the direct clear; it does not change the separate fall-distance write above.

`Player.java` in each release is byte-identical, so these method bodies and their local ordering match exactly. `LivingEntity.java` was also checked for the `super.travel` local-control gate; `Entity.java` was checked for movement/fall-distance writes. Their hashes match across the pair as well.

## Pinned source identities

Older: exact `1.16.2`, namespace `mojmap`. The local `mojmap.ready.json` identifies version 1.16.2 and pins the source-manifest SHA-256. `mojmap.provenance.json` records namespace and exact run. Client mappings SHA-256: `c95a7046ab43371cc476b8b51978c6f2155c62f4cc5756b05c8ef81e0f11691f`; client SHA-256: `19960f4e3c60f2723f361844d2b4020b8bf2160bd91a1e1efde8ce5aea4ff73d`.

Newer: exact `1.16.3`, namespace `mojmap`, from `mojmap/.decompile-provenance.json`. Its pinned Mojang client mappings input (`client.txt`) SHA-256 is `c41c58faaf3a3b651d8883b9b1df355cb6ad59c4d6296b2e1ffc3dfe2bf9be3e`.

Compared source SHA-256 (same on both sides):

- `net/minecraft/world/entity/player/Player.java`: `d2e26589bdb6a20dc914266db06aa48f50811efc792d6e63b3008c9199914960`
- `net/minecraft/world/entity/LivingEntity.java`: `b5d8a1a3c80f85d5d545b5a777e9e2a915dc002a7e31b5f5ad12bf7e285d7a88`
- `net/minecraft/world/entity/Entity.java`: `f9a9a073fe3105a0aa53d0f21ec72e59084e8d21a14c1cd3be75703865ee2666`

Identity files: older source manifest `10d312ca29ee48e25f4727adafb9fcfbd741dcebe81b343d3dcffa071952a7a7`; newer `.decompile-provenance.json` `513271804ead6757345d305717e7e0bfc1641434c35a3b254f287bd8fd281498`.
