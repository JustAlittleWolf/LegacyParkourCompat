# Bounded auto-jump entity collision source witness

Assignment: `auto-jump sleeping/dying 0.2F pose path through local input, onGround, auto-jump enabled/headspace and collision-query shape filters` for endpoint pair 1.13.2 to 1.14.4. No intermediate cutover inferred.

## Exact source identity and provenance

The supplied path spelling `decompiled_minecraft/exact1.13.2` / `exact1.14.4` does not exist. Canonical source directories are `D:/Javastuff/LegacyParkourCompat/decompiled_minecraft/1.13.2/ornithe-feather` and `.../1.14.4/mojmap`.

1.13.2 provenance states exact `versionId=1.13.2`, namespace `ornithe-feather`, mapping option `feather`, mapping artifacts `feather-gen2-1.13.2+build.2-mergedv2.jar` and `.tiny`; verification reports 2,711 source files, zero source differences/missing files, and source manifest SHA256 `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211`. Provenance JSON SHA256: `E25950B7855FC4F2AEE1A662F0BE6B0AF9F28788D9E27293075CFC5A3AA5D383`.

1.14.4 provenance states exact `versionId=1.14.4`, namespace `mojmap`, mapping option `feather,mojmap`, mapping artifacts `feather-gen2-1.14.4+build.2-mergedv2.jar` and `.tiny`; verification reports 3,250 source files, zero source differences/missing files, and source manifest SHA256 `af98406f3d4ed31494fbdce1078d1f3df3f712501878f9a0e9ad5879bdf23c3b`. Provenance JSON SHA256: `9D1AD405E1E787D2FCA5ED1897E6797B1F3CD79527DA50D713232F46E2F30D97`.

## Checked source and hashes

- 1.13.2 `net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java` SHA256 `2583495F3A02B4791AA036E6A8D354D7596C4984761969A0F29B29D8D9BF42BF`.
- 1.13.2 `net/minecraft/world/WorldView.java` SHA256 `9577360B1A1BDDE9280F0B72593E5E6DDE45D9A54719CD7FF933F3ECD31B971F`.
- 1.13.2 `net/minecraft/world/World.java` SHA256 `0023DE30DA608DB73C6C6188334D2BB19039D86FE4D6F5E4AD79D732A5D2CD1A`.
- 1.13.2 `net/minecraft/world/EntityView.java` SHA256 `2B5B59EEF6EFA93BB51C1D838C7A1CF64725BE96C55C47F76894FDE11E13D6DE`.
- 1.14.4 `net/minecraft/client/player/LocalPlayer.java` SHA256 `0795C1223198CE5ACF5D2ED9E5DB8435BBEC4CD52B96F96B1DDF2865BAAAE85F`.
- 1.14.4 `net/minecraft/world/level/LevelReader.java` SHA256 `1F86A029E4404B91AA532CA33AB0DBB63B085334C561CE44E0D0875AA50FDBC9`.
- 1.14.4 `net/minecraft/world/level/LevelAccessor.java` SHA256 `ABFDCF0825458A8430A7050803062B64CCAB8D48FFAC10290B421F854E7B99A3`.
- 1.14.4 `net/minecraft/world/level/EntityGetter.java` SHA256 `DD7745C85C83BF327346008E815C64AA5D0E0BA253EF9640BE48223824F0DFC6`.
- 1.14.4 `net/minecraft/world/level/Level.java` SHA256 `9278AFC8E69353E5A483B345F9A6DB2F11E67CFC98C43F9E7BD62CD0BEBE5B02`.

## Bounded result

No positive witness for “old query includes entities, new query is block-only” exists: that premise is false at these endpoint sources. In 1.13.2 `LocalClientPlayerEntity.autoJump(float,float)` lines 882–980 calls `world.getCollisions(this, box)` at line 935. `WorldView.getCollisions(Entity,Box)` lines 166–168 funnels to the shape overload; concrete `World.getCollisions(Entity,VoxelShape,VoxelShape,Set)` lines 1755–1758 appends `getEntityCollisions` when the entity is non-null. `EntityView.getEntityCollisions` lines 21–32 queries nearby entities, excludes explicitly excluded/same-vehicle entities, and emits collision and collision-against shapes intersecting the query bounds.

In 1.14.4 `LocalPlayer.updateAutoJump(float,float)` lines 856–958 calls `level.getCollisions(this,aABB,Collections.emptySet())` at lines 910–913. `Entity.level` is typed `Level`; `Level` implements `LevelAccessor`; `LevelAccessor.getEntityCollisions` lines 75–78 delegates to `EntityGetter`; `LevelReader.getCollisions` lines 130–132 concatenates block and entity collisions. `EntityGetter.getEntityCollisions` lines 53–66 queries nearby entities (except excluded and same vehicle), then emits intersecting collide/collide-against boxes. Thus this path is not block-only.

The enclosing auto-jump gates are substantially analogous: enabled, timer <= 0, on ground, not sneaking, not riding/passenger, nonzero current movement input; explicit block head-space checks; then swept-side collision boxes and a height threshold set timer to 1. The next client input tick decrements timer and forces jumping in both versions (1.13.2 lines 711–715; 1.14.4 lines 638–642). This report does not establish any trajectory or sleeping/dying-pose outcome.

The query implementations do differ internally: 1.13.2 `EntityView` expands the entity lookup box by 0.25 and filters entity collision shapes against the unexpanded query bounds; 1.14.4 `EntityGetter` inflates query AABB by 1e-7, queries entities in it, and filters collision boxes against that inflated query. This is a candidate for separately bounded source verification, but it is not evidence of a block-only replacement and this assignment did not establish a concrete reachable sleeping/dying-player witness for the filter delta.

Finding draft: none, because the requested entity-vs-block-only change is disproved and no distinct accepted operation was established. No intermediate release or patch boundary was inspected.
