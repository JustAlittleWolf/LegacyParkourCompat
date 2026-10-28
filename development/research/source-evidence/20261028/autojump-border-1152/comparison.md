# Auto-jump world-border candidate: source comparison

## Scope and identity

Source-only comparison of `LocalPlayer.updateAutoJump`'s collision-candidate query and its exact provider path in Mojmap decompilations. The release folders and mapping folders inspected are `1.15.2/mojmap`, `1.16/mojmap`, `1.16.1/mojmap`, `1.16.2/mojmap`, `1.16.3/mojmap`, `1.16.4/mojmap`, `1.16.5/mojmap`, `1.17/mojmap`, `1.17.1/mojmap`, `1.18/mojmap`, `1.18.1/mojmap`, and `1.18.2/mojmap` under `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft`. The mapping identity evidenced by each source path is Mojmap. SHA-256 below identifies the exact source files read; mapping artifact/build metadata was not separately inspected.

### Exact source-file hashes

Repeated identical hashes mean the exact file bytes are the same in each named release folder.

| Release(s) | Relative source file | SHA-256 |
|---|---|---|
| 1.15.2 | `net/minecraft/client/player/LocalPlayer.java` | `3A9019BD7B860E251C23FD8D0CD70B7F5B38566D34470C4E29B1014EF689CCBD` |
| 1.16, 1.16.1 | `net/minecraft/client/player/LocalPlayer.java` | `EE20523ADFDB82F3753FA43312AF170F34C4F4AA78570631C61BC8C0820DFB8B` |
| 1.16.2–1.16.5 | `net/minecraft/client/player/LocalPlayer.java` | `6011569E766BB1568609147BE9AA14E9C08C51948E3D3A60FD066E848F6A8C2B` |
| 1.17 | `net/minecraft/client/player/LocalPlayer.java` | `08DBB8615CC6C7776F35CE269223699D39F758D15786BABFE11E497421C5F4D4` |
| 1.17.1 | `net/minecraft/client/player/LocalPlayer.java` | `C9A91CB6CB57806BC8D22E5BFE2D97DAAF21D5D2A48F34A6E2C53164C61C5812` |
| 1.18, 1.18.1 | `net/minecraft/client/player/LocalPlayer.java` | `E38D0BBB6E5B9DE2A698609B8491D0007406B3BECD42BA9B95289F533788C996` |
| 1.18.2 | `net/minecraft/client/player/LocalPlayer.java` | `99C2D18BCD23243AFB8F95C5BAFB21FB0BE7EA04AACBB14FCF7BE7CED2C9C095` |
| 1.15.2 | `net/minecraft/world/level/CollisionGetter.java` | `63DAEC0623C816D53F5DBDDE407A27222FA21E62680C1096B63CF9082B1C25FD` |
| 1.16, 1.16.1 | `net/minecraft/world/level/CollisionGetter.java` | `FC7F175B10C2C6683913A903A366BD775FACECF3C58F04CC6E9CFC95DDCEED98` |
| 1.16.2–1.16.5 | `net/minecraft/world/level/CollisionGetter.java` | `B507D6BE11E5985A62CFEB249A99DCB5F8EDAF346F12CB2487797D9E01763EAC` |
| 1.17, 1.17.1 | `net/minecraft/world/level/CollisionGetter.java` | `EB707479E75CF200731DF4546A546FB984BE33CF3E4A8E17D3065A916497F747` |
| 1.16, 1.16.1, 1.16.2–1.16.5 | `net/minecraft/world/level/CollisionSpliterator.java` | `19AB959C469B8EDB5C371FF737A59A61B8A80BF397EFF1DB280064342094D297` |
| 1.17, 1.17.1 | `net/minecraft/world/level/CollisionSpliterator.java` | `C4F8158BD6778159946EBF16C22A6C12F5A4BCCB40E2F1215F4FC930BB218BD0` |
| 1.18, 1.18.1, 1.18.2 | `net/minecraft/world/level/CollisionGetter.java` | `ED62E6B800EC057C1269D3CC4E2FB7C0CC2037370223FAC99F41371E000C6B5C` |
| 1.18, 1.18.1, 1.18.2 | `net/minecraft/world/level/BlockCollisions.java` | `11FECADC012D0A544097BF0FEBFA1A83DA5A534E6182EE7DF7B2334B6EE80127` |
| 1.15.2 | `net/minecraft/world/level/border/WorldBorder.java` | `5030E47D299D2741339DAE0274DAB41E63AE1EB1468171EF14F181A71A03086F` |
| 1.16, 1.16.1, 1.16.2–1.16.5 | `net/minecraft/world/level/border/WorldBorder.java` | `2925DF8D985D0EC2D075D4F5C57538DCC628528B72A1D35E90AF27FE4FBADB2A` |
| 1.17, 1.17.1 | `net/minecraft/world/level/border/WorldBorder.java` | `B848DD0DC6043D6C9012726844BEA53CF4558C0135A08FA3BE55960B0E557DF9` |
| 1.18, 1.18.1, 1.18.2 | `net/minecraft/world/level/border/WorldBorder.java` | `ADE6BCB18AE0B7A2C6E9978295288BFAC6FBB759A0F8A2DFB9D3983788D332FB` |
| 1.15.2 | `net/minecraft/world/phys/shapes/VoxelShape.java` | `5FACEEA0E4CE9AD0F2A1A2C72A89D9A768AB877C8728AE2968FE34AA1E03D3EA` |
| 1.16 | `net/minecraft/world/phys/shapes/VoxelShape.java` | `6747647A5B94E69747340D432932B77CE5AD701C8F06E141E15B3DD68F8C3BDE` |
| 1.17.1 | `net/minecraft/world/phys/shapes/VoxelShape.java` | `6B53EB54933AFACDA1934C9F1E3EC3584294AA3DEC09E5FBF4EEAA52B82D76C4` |
| 1.18.2 | `net/minecraft/world/phys/shapes/VoxelShape.java` | `99F8B6E44E6C249B251D98A99E38158EBCB459733B26CC98BAE15D51D3B87417` |

## Source facts

### 1.15.2

`LocalPlayer.move` calls `super.move` and then calls `updateAutoJump` with the realized X/Z displacement (LocalPlayer lines 850–855). `updateAutoJump` runs only through `canAutoJump`: auto-jump enabled, `autoJumpTime <= 0`, on ground, not staying on a ground surface, not a passenger, moving, and block jump factor at least `1.0` (lines 862–969). It builds the swept AABB, offsets both forward-edge segments upward by `0.51`, then calls `level.getCollisions(this, aABB, Collections.emptySet())`, flattens each returned shape through `toAabbs`, and scans candidates in stream order (lines 892–959). For a matching edge AABB it assigns `(float)aABB2.maxY` to `u`; after the scan it sets `autoJumpTime = 1` only when the resulting rise `w` is above `0.5F` and no more than `q` (base `1.2F`, adjusted for jump effect).

The exact provider is `CollisionGetter.getCollisions` → `getBlockCollisions` → its anonymous `AbstractSpliterator` (CollisionGetter lines 63–123). For a non-null entity, `checkedBorder` starts false. The first `tryAdvance` checks the border before advancing the block cursor; it gets `getWorldBorder().getCollisionShape()` and emits that whole shape when it does not overlap the entity box deflated by `1.0E-7` but does overlap the box inflated by `1.0E-7`. Thus the auto-jump query can receive the border shape before block candidates. The entity-collision stream is concatenated after block collisions.

`WorldBorder.getCollisionShape` delegates to the active extent (line 49); both moving and static extent collision shapes are `Shapes.INFINITY` minus an interior box whose Y bounds are negative and positive infinity (moving extent around lines 315–335, static extent around lines 390–441). `VoxelShape.toAabbs` creates AABBs from every box produced by `forAllBoxes` (line 111 onward), so the unbounded vertical extents are passed into the same AABB scan without clipping in `LocalPlayer`.

### 1.16, 1.16.1, 1.16.2–1.16.5, 1.17 and 1.17.1

Each exact `LocalPlayer.java` calls `super.move` followed by `updateAutoJump`; the guard and candidate scan retain the same relevant ordering and thresholds as 1.15.2. The caller changes only the entity predicate argument from an empty entity set (1.15.2) to `entity -> true` in the stream-based API. In every inspected file the candidate source is `level.getCollisions(this, aABB)`, then `toAabbs`, then the first matching forward-edge candidate sets `u = (float)candidate.maxY`; only a rise in `(0.5F, q]` sets the timer. The can-auto-jump gates listed above remain.

For 1.16 through 1.17.1, `CollisionGetter.getCollisions` concatenates block collisions before entity collisions; `getBlockCollisions` wraps a `CollisionSpliterator`. Its constructor sets `needsBorderCheck` when `entity != null` (CollisionSpliterator lines 21–54). `tryAdvance` evaluates `needsBorderCheck && worldBorderCheck(consumer)` before `collisionCheck(consumer)` (lines 56–58). `worldBorderCheck` clears the one-shot flag, takes the entity's own bounding box, skips if the box is fully within the floored/ceiled border extents, otherwise fetches the border shape, and emits it when it does not intersect the deflated box but does intersect the inflated box (around lines 102–132). Thus it is still possible for the auto-jump candidate query to receive the border shape first. The query AABB is the scan volume for block candidates; the border-check proximity predicate uses the source entity's bounding box.

In all inspected border files, `getCollisionShape` delegates to the current extent, and both moving and static extents form the border as `Shapes.INFINITY` minus an interior box with Y bounds `Double.NEGATIVE_INFINITY` and `Double.POSITIVE_INFINITY` (WorldBorder source locations: 1.16 lines 51, 324, 530; 1.17/1.17.1 lines 56, 333, 539). Hence the candidate shape is vertically unbounded and its AABBs are not vertically truncated by the query before the auto-jump candidate scan.

The exact relevant source bytes are identical for `CollisionSpliterator.java` in 1.16, 1.16.1, 1.16.2, 1.16.3, 1.16.4, and 1.16.5; likewise identical in 1.17 and 1.17.1. LocalPlayer and CollisionGetter hashes are grouped above; the distinct release folders in the requested interval were all present and their per-release files were hashed.

### 1.18, 1.18.1 and 1.18.2 boundary check

`LocalPlayer.updateAutoJump` still makes the same swept-AABB collision query and flattens candidates to AABBs (1.18 lines 902–954; 1.18.1 same file hash; 1.18.2 lines 900–952). But `CollisionGetter.getCollisions` now returns entity collisions concatenated with `getBlockCollisions`; the latter creates a `BlockCollisions` iterator. The inspected `BlockCollisions.computeNext` walks block states and returns their translated collision shapes; it has no world-border lookup or border candidate (CollisionGetter lines 67–74; BlockCollisions lines 30–85). `CollisionGetter.borderCollision` still obtains the border shape through `WorldBorder.isInsideCloseToBorder`, but it is called from `noCollision`, outside the `getCollisions`/`getBlockCollisions` path used by auto-jump (CollisionGetter lines 43–80). The 1.18.2 `WorldBorder` still constructs vertically unbounded border collision shapes, but that shape is not a candidate yielded by this auto-jump query. The same queried sources have identical hashes for CollisionGetter, BlockCollisions and WorldBorder across 1.18, 1.18.1 and 1.18.2.

## Bounded conclusion and movement interpretation

**Source answer:** Yes. The `updateAutoJump` collision-candidate query can yield a world-border shape in every requested release from **1.15.2 through 1.17.1 inclusive**. This is established by exact inspection of 1.15.2, 1.16, 1.16.1, 1.16.2, 1.16.3, 1.16.4, 1.16.5, 1.17 and 1.17.1. The queried path no longer yields the border shape in 1.18, 1.18.1 or 1.18.2; the border test moved to the separate `noCollision` path.

**Predicted witness consequence (not a runtime result):** Under the supplied described conditions, if the border's unbounded AABB intersects one of the forward-edge segments and is the first matching candidate, `maxY` is positive infinity. The method's computed rise then exceeds its finite `q` threshold and does not set `autoJumpTime`; because the query consumes only the first candidate matching either edge, an earlier border candidate can prevent a later step candidate from being considered. In 1.18+, the border candidate is absent from this query, so the equivalent step candidate can be examined and can set the timer when its rise is in `(0.5, q]`. This is a source-based movement prediction; this task did not execute a runtime witness.

**Applicability boundary:** 1.15.2 is the earliest examined release, not an established historical introduction. No release before 1.15.2 was examined. To move the lower bound backward, inspect the exact preceding release(s), beginning with 1.15.1 and then the 1.14.4 line endpoint (and any intervening exact releases needed to locate a boundary). Do not infer applicability earlier than 1.15.2 from this comparison.

## Examined and unexamined releases

Examined exact release directories: `1.15.2`, `1.16`, `1.16.1`, `1.16.2`, `1.16.3`, `1.16.4`, `1.16.5`, `1.17`, `1.17.1`, `1.18`, `1.18.1`, `1.18.2` (all `mojmap`). No other release was examined. Specifically unexamined for closing earlier applicability: `1.15.1` and all earlier releases, including the prior line endpoint `1.14.4`; exact intermediate releases would also be needed to pinpoint any earlier introduction.
