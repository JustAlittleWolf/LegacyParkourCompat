# MOVE-003: Safe dismount position queries use a changed collision box

- Older version A: 1.11.2
- Newer version B: 1.12.2
- Mechanic / coverage slice IDs: 7.2 / riding dismount transition
- Classification: changed behavior
- Confidence: source-confirmed
- Applicability: historical player behavior (server-authoritative player position)
- First changed release: unknown within (1.11.2, 1.12.2]
- Runtime validation: not performed

## Paired evidence

- A: `decompiled_minecraft/1.11.2/ornithe-feather/net/minecraft/entity/living/LivingEntity.java`, `LivingEntity.dismountRider(Entity)`, lines 1282-1350; SHA-256 `bb7dc6c9e423a9568d6433d51bba12e7aee4555fbf3fb3e2b87f618382279f2f`. For mounts other than boats and horses, the safe-exit collision box is formed from the dismounting entity's `getShape()` and mount height at lines 1293-1297; each candidate query uses `box.moved(s, 1.0, t)` at 1304.
- B: corresponding `LivingEntity.dismountRider(Entity)`, lines 1321-1387; SHA-256 `190e9ac551538e015d9e4d6c42856e5ba32b593131cf6d93895e7b29533f1ee6`. B forms the box from `entity.getShape().minY` and `Math.floor(entity.getShape().minY) + this.height` at lines 1334-1335; candidate queries use `box.moved(s, 0.0, t)` at 1342.
- Reachability: A/B `PlayerEntity.stopRiding()` calls `super.stopRiding()` (A `PlayerEntity.java:953-956`, B `948-951`; SHA-256 `87fe94fa6cbf7aba18b9a5e3401664439eb8eba9958173da8fbcd05cc7ad948b` / `e4e0fdbe07a7d0a0ae4a70cbb6739a287c9d045a4a12b409895c220b5d91fe1e`). `LivingEntity.stopRiding()` then calls `dismountRider(entity)` when the mount changed and `!world.isClient` (A `1864-1869`, B `1908-1913`), so this changed safe-exit placement is server-side. The method excludes `BoatEntity` and `HorseBaseEntity` from the changed first branch.
- Position write: A/B `Entity.teleport(double,double,double)` (A `Entity.java:2155-2160`, B `2181-2186`; SHA-256 `ce8104a17ce783df639cf9726e7b1cd0936563eaa7ba308603335bbe05d49440` / `80f091bf32166c88cf8bbd31caf72d84fa16224410733c7d2a0f00563f294a0a`) writes position and angles to the selected safe exit; this method is unchanged.

## Source-level difference

When a living player dismounts server-side from a mount that is neither a boat nor a `HorseBaseEntity`, and the mount has a movement direction, the candidate collision test uses a different box and vertical offset in B. If a candidate's collision result differs between the two query volumes, the loop may select a different safe exit or fallback location, and `teleport()` writes that coordinate. The candidate ordering and final teleport helper are unchanged.

## Reachability and dependencies

The `PlayerEntity.stopRiding()` override delegates to the `LivingEntity.stopRiding()` path. That path only invokes the safe-exit method on the server. The client-side local-player call does not execute this server-only placement branch; the resulting server position is authoritative external movement state. This finding therefore describes player position during a server-authoritative riding transition, not a local movement acceleration change.

## Consequence and uncertainty

Source-proven: A and B query different collision boxes/offsets before choosing a safe dismount location. Predicted consequence: in world geometry where those queries disagree, the authoritative player position after dismount can differ. The exact exit position for a concrete map was not evaluated, and no client/server run was performed. Other mount types in the excluded boat/horse branch are unaffected by this particular query change.

## Handoff

Independent server-authoritative player position delta during dismount. The source endpoints do not establish its first release. Runtime validation and implementation decisions are deferred.
