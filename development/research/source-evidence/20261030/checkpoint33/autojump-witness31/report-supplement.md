# Auto-jump collision-query witness supplement

This supplements `report.md` after reading the existing bounded claim `development/research/findings/1.13.2--1.14.4/auto-jump-entity-collision-query.md`. The endpoint comparison remains limited to its 0.2F sleeping/dying pose and old/new entity query bounds; this is not a full-pair audit.

## Pose dimensions and movement reachability

1.13.2 `PlayerEntity.die(DamageSource)`, lines 560–565, sets the dying player's size to 0.2F by 0.2F. `PlayerEntity` sleeping path lines 1266–1275 does the same before setting sleep position. For sleeping, `LocalClientPlayerEntity` autojump uses `getShape()`; for dying it uses current entity width/height via `getShape()`, so the auto-jump scan height is 0.2F. Its auto-jump gates in `LocalClientPlayerEntity.autoJump`, lines 882–886, require enabled, timer <= 0, on ground, not sneaking/riding, and nonzero input movement.

1.14.4 `LivingEntity.SLEEPING_DIMENSIONS` is `EntityDimensions.fixed(0.2F,0.2F)`; `Player.POSES` maps `Pose.DYING` to the same dimensions (`Player.java` lines 104–119). `Player.updatePlayerPose` (lines 344–371) selects sleeping pose; `LivingEntity` death handling sets `Pose.DYING` (line 1143). `LocalPlayer.updateAutoJump`, lines 856–860, has analogous gates, and obtains its scan dimensions from `getBoundingBox()`.

At either pose, the auto-jump scan AABB has vertical extent exactly [minY, minY+0.2]. The two candidate side-segment endpoints are both translated upward by 0.51F (`LocalClientPlayerEntity.java` 926–933; `LocalPlayer.java` 901–909), so each segment lies at y=minY+0.51. Each entity collision provider filters emitted shapes for intersection with the unshifted scan bounds (old `EntityView.java` 21–32; new `EntityGetter.java` 53–66). Because the scan bounds end at minY+0.2, no entity collision shape surviving this filter can also intersect a segment at minY+0.51. Thus, for sleeping/dying 0.2F poses, the auto-jump entity iterator cannot find an entity obstacle to set the timer, independently of the difference between old +0.25 entity enumeration expansion and new +1e-7 AABB inflation. If there is no block obstacle in the side test, the sentinel stays `Float.MIN_VALUE` and the timer assignment at old line 971 / new line 949 is skipped. This closes the entity-query-specific reachability gate without asserting that block shapes cannot trigger auto-jump.

Input and event order independently prevent the ordinary screen path from furnishing nonzero local movement input: 1.13.2 `Minecraft.tick` checks sleeping/dead state at lines 1094–1101 before `world.tickEntities`; `Minecraft.openScreen` lines 643–663 calls `KeyBinding.releaseAll()` for any non-null screen. 1.14.4 `Minecraft.tick` checks sleeping/dead state at lines 1260–1267 before `level.tickEntities`; `Minecraft.setScreen` lines 729–750 calls `KeyMapping.releaseAll()` for any non-null screen. The normal sleep/death screens therefore clear movement bindings before the next local entity tick. On the subsequent input tick, if a timer had been set, the local player decrements it and forces `jumping=true` (old LocalClientPlayerEntity lines 711–715; new LocalPlayer lines 638–642); ordinary sleeping also makes Player immobile, and dying makes LivingEntity immobile, which clears jumping before travel (`LivingEntity.aiStep` lines 2187–2195; `Player.isImmobile` lines 980–982). The 0.2F query-specific entity path cannot set that timer in the first place as shown above.

## Exact source identity additions

- 1.13.2 `net/minecraft/client/Minecraft.java` SHA256 `B1D35473CD12BDF38E87F9888B4ED6F620614CBE914CF7E7A40C601D4E284875`.
- 1.13.2 `net/minecraft/entity/living/player/PlayerEntity.java` SHA256 `4ED22F6C5A3C55D67EED782070AC722201DF4D624ADBC90779F1FD29C2876633`.
- 1.14.4 `net/minecraft/client/Minecraft.java` SHA256 `B9C368CAB9F09CB9760A9EF3F2C0B7056401F25D7901645B6CA3F08460D79779`.
- 1.14.4 `net/minecraft/world/entity/player/Player.java` SHA256 `E9CE5EB18C5581FFE2B610B273FFD85786587A00BA853E5DBA0AC96593622B12`.
- 1.14.4 `net/minecraft/world/entity/LivingEntity.java` SHA256 `428762178A876EFD4069086E6B7D51F571EA0401F44E7EFF0927B7D26D9EF681`.

No accepted finding draft is warranted by this bounded witness: both endpoints include entity collisions; in the requested 0.2F poses, their post-query test geometry makes the entity query unable to change the auto-jump timer. Exact later minor releases establish a 1.14.3 boundary in the existing finding, but this endpoint-only task does not itself verify those intermediate versions or the boundary.
