# F-PLAYER-DISMOUNT-CLEARANCE-BOX: non-boat/non-horse dismount uses different clearance geometry

- Older version A: 1.11.2
- Newer version B: 1.12.2
- Mechanic / coverage slice IDs: S7.2 (mount/dismount player position writers); S4.4 (collision-guided position selection)
- Classification: changed behavior
- Confidence: source-confirmed by discovery author; independent blind source review pending
- Applicability: server-side player dismount from a mount outside the BoatEntity and HorseBaseEntity special cases; a saddled PigEntity is a direct reachable example
- First changed release: unknown within (1.11.2, 1.12.2]
- Runtime validation: not performed

## Paired evidence

- A manifest artifact reference: `build/movement-campaign-2026-10-07/ready/1.11.2/ornithe-feather`
- A source: `1.11.2/ornithe-feather/net/minecraft/entity/living/LivingEntity.java`, `LivingEntity::dismountRider(Entity)` lines 1282-1352, whole-file SHA-256 `bb7dc6c9e423a9568d6433d51bba12e7aee4555fbf3fb3e2b87f618382279f2f`; method body SHA-256 `d987f0d9585201450d3280a30de7a5ce8544f09ca6c2887901d201befb55d9ee`.
- B manifest artifact reference: `build/movement-campaign-2026-10-07/ready/1.12.2/ornithe-feather`
- B source: `1.12.2/ornithe-feather/net/minecraft/entity/living/LivingEntity.java`, `LivingEntity::dismountRider(Entity)` lines 1321-1389, whole-file SHA-256 `190e9ac551538e015d9e4d6c42856e5ba32b593131cf6d93895e7b29533f1ee6`; method body SHA-256 `591e7ede8fce1aa2343770bb8e9405ffd198d8f41a4c5ac294417c071dea8ba9`.
- Player dismount caller: A/B `PlayerEntity::rideTick()` (A lines 379-395; B lines 375-391) has identical body SHA-256 `9ffcd4fe7b7f716a9771902cb6fa3c6a3b6da8226057702a53e5d38057ab26fd`. On the server, while sneaking and riding, it calls `stopRiding()` and clears sneaking. A/B `LivingEntity::stopRiding()` bodies match (SHA-256 `8d7dcc8789790a6efefae18e0b4646bc5c26654b828d72846b16c2722c055917`); it calls `dismountRider(oldMount)` only when the old mount is no longer the current mount and the world is not client-side. A/B `ServerPlayerEntity::stopRiding()` bodies match (SHA-256 `3a5a294442a21990171cddabdaf8286191b1842f413df4e04fe62757aa85c2a`) and perform the server position correction afterward.
- Concrete mount reachability: A/B `1.11.2/ornithe-feather/net/minecraft/entity/living/mob/passive/animal/PigEntity.java::interactMob(PlayerEntity,InteractionHand)` lines 147-170 / `1.12.2/ornithe-feather/net/minecraft/entity/living/mob/passive/animal/PigEntity.java::interactMob(PlayerEntity,InteractionHand)` lines 146-169 call `player.startRiding(this)` server-side when the pig is saddled and has no passengers. PigEntity is neither `BoatEntity` nor `HorseBaseEntity`, so the changed general dismount branch applies.
- Equal downstream helpers: A/B `Entity::teleport(double,double,double)` body hash `a0b6145c49ef895ac70adce95613188731b379c3fe41e3d4e444e58105fbbad1`; `Entity::startRiding(Entity,boolean)` `d2d1d814308a88741f701b6eb1736062f4ccb264a5d01dfded8bbc991ca80535`; `PlayerEntity::getRideHeight()` `e9fddb3e06929d7544106b33870434c201d3017703be67dbc9066a0d88c5e0df`. The source-method hashes in this finding are compared from the exact pair trees listed in the manifests.
- Provenance limitation: both exact endpoint source manifests and immutable `feather-r1-2026-10-07` snapshots are recorded in the run manifest. The original derived mapped JARs are unavailable; revised snapshots do not establish identity with those originals.

## Source-level difference

For non-Boat/non-Horse mounts, both versions derive candidate exit coordinates from the rider's position and movement direction, inspect candidate boxes with `World.getCollisions`, and teleport the rider to the first eligible support or the retained fallback coordinates. The clearance box differs. A derives its vertical interval from the rider's own box after subtracting the mount's height, then tests each horizontal candidate after moving that box up by `1.0`. B derives the vertical interval from the mount's bounding-box minimum and the rider height, then tests the candidate at vertical offset `0.0`.

Those two boxes cover different world-space volumes for the same mounted player, mount and candidate. A block collision in the exclusive part of one box changes whether the candidate is accepted and can therefore change which dismount coordinates are sent through `teleport`. The BoatEntity/HorseBaseEntity special branch is unchanged and does not use this changed general clearance box.

## Consequence and uncertainty

Source proves a changed collision-clearance query on a reachable player dismount path and a conditional change to its selected player exit position. It does not establish a measured trajectory or identify a particular world arrangement where the first accepted candidate differs. Other mount-specific passenger offsets, steering and vehicle travel remain open in S7.2/S7.4; damage, hunger and non-player movement are not claimed.

## Handoff

S7.2 owns the direct player position consequence; S4.4 owns the collision query and shape dependencies. First-changed release within the pair remains unknown. A separate blind source reviewer must accept this exact snapshot before implementation handoff.
