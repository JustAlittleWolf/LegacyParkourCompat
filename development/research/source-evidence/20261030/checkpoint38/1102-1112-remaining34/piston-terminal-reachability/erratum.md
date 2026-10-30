# Piston terminal reachability closure: erratum

This separate follow-up corrects the collision-route conclusion in the prior closure. It does not alter that file or any supplied report, audit, or correction input.

| Artifact | SHA-256 | Status |
|---|---|---|
| Original closure revision bound by the terminal-progress-final-pass witness | `1a31f80468852a5b2202a1e1f83fe4e29a8737114730e1065632b5a4f36ea622` | Superseded; no saved byte copy was available to restore. |
| Revised closure currently preserved at `closure.md` | `cdafaefe2cc12be458b783e6c52c58f576d754588436b430eed5a46f38e17f2a` | Contains the corrected route account; this erratum is its explicit correction record. |
| Earlier intermediate revision, after the first correction and before adding old Entity/Box line citations | `368a39809c603ae5d6be7247a673cf6e342774333f932b9da064f29fc2e6affd` | Not the current on-disk revision. |

## Corrected collision result

Withdraw the prior claim that the west-side terminal witness is clipped by an old `PistonHeadBlock` arm. In 1.10.2, source retraction calls `PistonBaseBlock.doEvent` type 1 with `data = direction.getId()` and creates the moving block entity from `getStateFromMetadata(data)`, with `extending=false` (`block/PistonBaseBlock.java:117–120,168–182`). The metadata decoder sets `EXTENDED` only when bit 8 is present (`:347–350`), so the moved state is an unextended PistonBase, whose ordinary shape is `FULL_BLOCK_SHAPE` (`:48–68`). The ordinary 1.10.2 collision route is `Entity.move` → `World.getCollisions` → block-state `addCollisions`; generic `Block.addCollisions` uses `getCollisionShape`, and `MovingBlock.getCollisionShape` returns `MovingBlockEntity.getShape`. That shape unions the moved-state box at current and previous progress (`entity/Entity.java:446–525`; `world/World.java:903–940`; `block/Block.java:342–359`; `block/MovingBlock.java:132–143`; `block/entity/MovingBlockEntity.java:80–88`). The old source-retraction shape is thus a swept full piston-base cube, not the old head arm.

At an east-facing source at `(0,0,0)`, the old 1.10.2 and new 1.11.2 ordinary collision boxes at the complete stored progress half-steps are:

| New current progress (`lastProgress`) | Old 1.10.2 combined box | New 1.11.2 combined source boxes |
|---|---|---|
| `0.0` (`0.0`) | Full cube X `[1,2]` | Extended base `[0,.75]` full; head arm `[.75,1.75]` with Y/Z stripe `[.375,.625]`; plate `[1.75,2]` full. |
| `.5` (`0.0`) | Full cubes `[.5,1.5]` and `[1,2]`, union `[.5,2]` | Extended base `[0,.75]` full; arm `[.25,1.25]` with Y/Z stripe `[.375,.625]`; plate `[1.25,1.5]` full. |
| `1.0` (`.5`) | Full cubes `[0,1]` and `[.5,1.5]`, union `[0,1.5]` | Extended base `[0,.75]` full; short arm `[0,.75]` with Y/Z stripe (contained by base); plate `[.75,1]` full. |

The new collector derives the p0/p.5/p1 head boxes and translations from `MovingBlockEntity.addCollisions` (`1.11.2 block/entity/MovingBlockEntity.java:318–345`), `PistonHeadBlock` (`:71–92`), and the extended piston-base shape (`PistonBaseBlock.java:52–82`). Thus the supplied west-side p1 player box ending at X `-.1` misses both old and new sets: its p1 response is no-delta. This does not establish a global p1 no-delta because the old swept full cube extends east to X `1.5`.

## Conditional reachable p0 local-player witness

At p0, the combined sets admit an ordinary local-player Y-response difference. Use a non-spectator, awake, dry-air player with normal width `.6`, height `1.8`, AABB X `[1.05,1.65]`, Y `[1.20,3.00]`, Z `[.70,1.30]`, and zero X/Z movement with downward request `dy=-.30`. Before the retract event, the statically extended east piston base ends at X `.75`, its arm occupies Z `[.375,.625]`, and its plate begins at X `1.75`; the player clears each component. Thus this pose can continue a normal fall before the event.

If the client handles the source-retract block event against the piston-base state before its next local-player collision query, and the query is before the new block entity's first tick, the entity is at p0. The 1.11.2 collector adds the extended base, full east head and selected arm, translating head boxes by the current retraction amount (`block/entity/MovingBlockEntity.java:318–345`; `block/PistonHeadBlock.java:71–92`). In this witness the base misses X, the arm misses Z, and the plate misses X, so the local player moves down the full `.30`, to feet Y `.90`.

The corresponding old p0 moved-state box is full cube X `[1,2]`, Y/Z `[0,1]`. The expanded downward query intersects it, and `Entity.move`'s `Box.intersectY` clips movement to `1.0 - 1.20 = -.20`, leaving feet Y `1.0` (`1.10.2 entity/Entity.java:514–520`; `util/math/Box.java:163–183`). This is a `.10` block difference in the queried Y response due to the new base/head collision provider set versus the old moved-state-derived shape.

The client caller is ordinary local movement: `Minecraft.tick` calls `world.tickEntities` while unpaused; `World.tickEntities` runs the entity pass before block entities; `LocalClientPlayerEntity.tick` runs the loaded local player; a non-spectator has `noClip=false`; dry-air `LivingEntity` travel submits velocity through `Entity.move`; and `World.getCollisions` dispatches block `addCollisions` without a client-side exclusion (`1.11.2 client/Minecraft.java:1395–1420`; `world/World.java:1227–1325,960–1016`; `client/entity/living/player/LocalClientPlayerEntity.java:159–169`; `entity/living/player/PlayerEntity.java:174–177`; `entity/living/LivingEntity.java:1477–1499,1798–1804`; `entity/Entity.java:459–585`; `util/math/Box.java:205–225`).

`ClientPlayNetworkHandler.handleBlockEvent` schedules the event on the client thread and calls `world.addBlockEvent`; the world dispatches the current block state's event (`client/network/handler/ClientPlayNetworkHandler.java:1079–1082`; `world/World.java:2553–2555`). If that state is the base, client-side `PistonBaseBlock.doEvent` installs `MOVING_BLOCK` with an `extending=false, source=true` entity at the source position (`block/PistonBaseBlock.java:151–190`). `World.setBlockEntity` registers it in the chunk and tickable block-entity list (`world/World.java:1735–1754,1379–1393`; `world/chunk/WorldChunk.java:620–631`). Its first block-entity tick advances p0 to `.5` after the player entity pass; it reaches `1.0` after the following block-entity tick and is removed only on the subsequent tick (`block/entity/MovingBlockEntity.java:273–289`).

The unresolved dynamic premise is the packet/event-to-client-tick ordering needed for the local-player query to see p0 before the first moving-block-entity tick. Source proves the conditional creation, normal query path, gates and geometry; this source-only closure does not claim that the p0 window is guaranteed for every live packet arrival or demonstrate runtime timing. Applicable gates include an unpaused world, loaded player and piston chunks, piston within world border, current client block state still being the piston base at event dispatch, a non-spectator awake player and the stated fall pose/request.

No runtime, tests, build, game, wiki, product implementation/history, backlog, or canonical source was used or changed. The original report, audit and correction supplement remain immutable inputs.
