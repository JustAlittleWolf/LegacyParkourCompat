# Independent source verdict: 1.14–1.15.2 border caller matrix

## Immutable review bindings

- Candidate r1 `source-114-1151-infinite-border-aliases.md`: SHA-256 `367a5f485c05a81f03b901ad5b3cd5d016a7d0240b64db7f6f85359e13402a47` (verified).
- Candidate r2 `source-114-1151-infinite-border-aliases-r2.md`: SHA-256 `48246616017a362478abd5ddd4d76ee6c5efa45b824681de76705c5297653fd7` (verified).
- Scope: exact-source audit of auto-jump collision caller and dispatch, border shape admission, west-side center/block sample, and timer path only. No product/wiki/history/runtime inputs used.

## Verdict

Reject r2's central assertion that 1.14.1–1.14.3 auto-jump calls the entity-only `EntityView.getCollisionShapes` / `method_20743` implementation. In all three Yarn releases, the player calls the aggregate `ViewableWorld.getCollisionShapes` overload: 1.14.1–1.14.2 pass a `BoundingBox`, selecting the block-collision overload; 1.14.3 passes a `Box`, selecting the corresponding aggregate overload. In 1.14.3, `ViewableWorld.getCollisionShapes` concatenates `method_20812` (block shapes, with the border emitted first) and `method_20743` (the entity-only branch). `IWorld.method_20743` delegates only that latter branch to `EntityView`; it does not replace the aggregate caller.

The r1/r2 implication that border-origin sampling is unavailable before 1.14.4 is therefore false. The eligible local-player auto-jump scan can receive the infinite-Y world-border shape in 1.14 Feather, 1.14.1–1.14.3 Yarn, 1.14.4 Feather, and 1.15/.1/.2 Mojmap. This source comparison shows no 1.14.4 introduction of the border-to-auto-jump path and does not establish a narrower patch cutover.

The west-side center alias and timer consequence are statically reachable but conditional. When the first intersecting collision AABB is the west exterior border box, its non-finite bounds flow through `Box`/`BoundingBox.getCenter()` (`lerp(0.5, min, max)`); NaN coordinates floor to integer zero in these releases. Auto-jump then samples the block above that center, `(0, 1, 0)`. The border box initially contributes `top = +infinity`; a non-empty sampled block can replace that top. If that block's top is more than 0.5 and no more than the jump-height threshold above the player, the existing timer write is reached (`field_3934 = 1` / `ticksToNextAutojump = 1`). With no qualifying sampled block, the infinite top fails the timer condition. This is an exact source-path conclusion, not a runtime trajectory claim.

## Caller and overload matrix

| Release(s) | Local movement caller | Auto-jump caller and collision query | Border reaches scan? |
|---|---|---|---|
| 1.14 Feather | `LocalClientPlayerEntity.move` calls `super.move`, then virtual `this.autoJump(dx,dz)`; `RemoteClientPlayerEntity` is a sibling and does not use this local caller. No other `autoJump` call/override was found. | `autoJump` calls `world.getCollisions(this, box, emptySet)`. `WorldView.getCollisions` emits the border from its first stream before the later entity/block work. | Yes. |
| 1.14.1–1.14.2 Yarn | `ClientPlayerEntity.move` calls `super.move`, then virtual `this.method_3148(dx,dz)`. The source tree has no second call or override of `method_3148`. | `method_3148` calls `world.getCollisionShapes(this, BoundingBox, emptySet)`. `World` implements `IWorld`/`ViewableWorld`; the `BoundingBox` overload is `ViewableWorld.getCollisionShapes`, whose block-collision spliterator emits `getWorldBorder().asVoxelShape()` before block candidates. The separate `EntityView` overload accepts `VoxelShape`, so it is not selected. | Yes. |
| 1.14.3 Yarn | Same `ClientPlayerEntity.move` → virtual `method_3148`; no second call/override found. | Calls `world.getCollisionShapes(this, Box, emptySet)`. `ViewableWorld.getCollisionShapes` concatenates `method_20812` first and `method_20743` second. `method_20812` emits the border first; `IWorld.method_20743` supplies only entity collisions through `EntityView`. The caller is not `method_20743`. | Yes. |
| 1.14.4 Feather | `LocalClientPlayerEntity.move` calls `super.move`, then virtual `this.autoJump`; no other `autoJump` call/override found. | `autoJump` calls `world.getCollisions(this, box, emptySet)`. `WorldView.getCollisions` starts with `getBlockCollisions`; the latter lazily emits the border before scanning block candidates. | Yes. |
| 1.15 / 1.15.1 / 1.15.2 Mojmap | `LocalPlayer.move` calls `super.move`, then `updateAutoJump`; no separate caller variant is implicated by these sources. | `updateAutoJump` calls `level.getCollisions(this, aabb, emptySet)`. `CollisionGetter.getBlockCollisions` performs the same first border emission. `CollisionGetter` and `WorldBorder` source hashes match across all three versions; `LocalPlayer.java` matches 1.15/.1 and differs in 1.15.2, but the checked border call and timer sequence remain present. | Yes. |

## Evidence identity and source locations

The 1.14 and 1.14.4 Feather roots have `ready` sidecars; 1.15/.1/.2 Mojmap roots have `ready` sidecars. For 1.14.1/.2/.3, the source roots contain hidden `yarn/.decompile-provenance.json` files despite having no version-root `.ready.json`. I checked each provenance SHA and compared the actual hashes of the relevant `ClientPlayerEntity.java`, `ViewableWorld.java`, `IWorld.java`, `EntityView.java`, `WorldBorder.java`, and box source against its source map; each matched. The exact Yarn inputs recorded are 1.14.1+build.10, 1.14.2+build.7, and 1.14.3+build.13 with matching Intermediary v2 artifacts. Thus the r1 claim that these Yarn roots had no provenance/source sidecars is incorrect.

Relevant loci:

- 1.14 Feather: `net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java:846–915`; `net/minecraft/world/WorldView.java:127–144`; `net/minecraft/world/border/WorldBorder.java:299–308,406–414`.
- 1.14.1/.2 Yarn: `net/minecraft/client/network/ClientPlayerEntity.java:834–901`; `net/minecraft/world/ViewableWorld.java:129–152`; `net/minecraft/world/EntityView.java:45–60`; `net/minecraft/world/IWorld.java:73–74`; `net/minecraft/world/border/WorldBorder.java:330–341,409–421`.
- 1.14.3 Yarn: `net/minecraft/client/network/ClientPlayerEntity.java:833–901`; `net/minecraft/world/ViewableWorld.java:121–155`; `net/minecraft/world/EntityView.java:45–59`; `net/minecraft/world/IWorld.java:74–75`; `net/minecraft/world/border/WorldBorder.java:330–342,409–421`.
- 1.14.4 Feather: `net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java:846–915,947–950`; `net/minecraft/world/WorldView.java:129–157`; `net/minecraft/world/border/WorldBorder.java:299–308,406–414`.
- 1.15.2 Mojmap: `net/minecraft/client/player/LocalPlayer.java:851–960`; `net/minecraft/world/level/CollisionGetter.java:63–88`; `net/minecraft/world/level/border/WorldBorder.java:329–341,409–421`; the two collision/border sources are hash-identical for 1.15/.1/.2.
