# Original 1.13 mixed Stone + single TOP slab: product correspondence

## Result

**COMPLETED — no product discrepancy for this fixture's timer result.** The exact base 1.13 provider’s grouped Stone/slab aggregate and the current product’s 1.13 candidate provider do not produce the same first collision box. In the current product, the exact-one-slab fixture fails the historical aggregation admission gate, then falls back to native `BlockCollisions`. For the supplied coordinates and AIR/cap assumptions, modern `Cursor3D` visits the separate Stone box first; AutoJump samples the Stone cap above it and exits at the `1.2F` jump-height cap. With initial timer `0`, both paths leave the timer at `0`.

This establishes equal timer outcome only. It does not establish shape-stream or first-hit-box parity, and it does not disturb the separately completed original 1.13 slab/frame behavior, whose admission requires at least two overlapping TOP slabs plus a no-eye frame.

## Checked product identities and paths

- Primary product checkout: `D:\Javastuff\LegacyParkourCompat`, `HEAD=b891786edf1366252549f2fead6a6c4b92cc05e5`. The checkout had a pre-existing modified `development/workflows/orchestration/OVERNIGHT.md`; this read-only check did not alter it.
- Domain B integration checkout: `C:\Users\Wolfi\.codex\visualizations\2026\11\18\01a1f01a-693d-7972-bfe2-805d6db43b98\domain-b-product`; inspected requested mixed-ready commit `6eaa91cc` (`Admit the 1.13.1 and 1.13.2 mixed AutoJump top slab`). Its `src/client/java/me/wolfii/legacyparkourcompat/change/v1_13/AutoJumpCollisionAggregation.java` is the same implementation as primary HEAD for the checked gates and fallback. The checkout’s later HEAD was `639dc2b7bdd4399016878fced9966c2db8664892`.
- Frozen base-source product correspondence input: `domain-b-autojump/original-113-mixed-slab-source/base-113-coordinate-timer.md`, SHA-256 `81ebec1f0e4cbe6d024ebf26dd109cc586f3032cb29686b5effdafdc008ed357`.
- Exact modern source root: `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\26.2\unobfuscated`.

## Resolution and fallback

`LocalPlayerMixin#legacyparkourcompat$autoJumpCollisionOrder` resolves `AutoJumpCollisionOrderHook` and calls `AutoJumpCollisionAggregation#autoJumpCollisions` for profile 1.13. The provider tries `aggregate(...)`; it returns `null` unless its complete query has at least two admitted overlapping TOP slabs and an admitted no-eye END_PORTAL_FRAME. This fixture has one dry historical Smooth Stone TOP slab and no frame, so `topSlabs < 2 || !hasEmptyFrame` returns null. Earlier safety gates may also reject if the supplied complete envelope differs, with the same fallback consequence.

The fallback is `AutoJumpFullCubeCandidates.collect`. For this mixed scan, its full-cell reconstruction declines the slab because it is not in `AutoJumpCollisionCandidates#isHistoricalFullCube`; it then delegates to `AutoJumpCollisionCandidates#blocksBeforeEntities`. For selected `V1_13`, this uses `level.getBlockCollisions(player, query)` followed by entity collisions (the fixture has none), not the supplied full-collision `vanilla` iterable. `NativeWorldBlockQueryBounds` for `V1_13` returns `Optional.empty()`, so `BlockCollisionsMixin` does not replace the native cursor with the later 1.14 auto-jump bounds. The native caller is therefore `CollisionGetter#getBlockCollisionsFromContext` creating `BlockCollisions`, whose `computeNext` consumes `Cursor3D`.

The ordinary native scan here is not the separate `MovementRuntime#collectHistoricalMovementBlocks` movement sweep: AutoJump passes `CollisionContext.of(player)`, not the `MovementCollisionContext` required by that collector. Thus the 1.13 X/Z/Y movement collector cannot reshape this AutoJump candidate stream.

## Cursor bounds and candidate rows for the frozen fixture

The frozen fixture starts at `(0.59, 8.1, 1.1)` with standing dimensions `.6F × 1.8F`; its explicit move resolves to feet `y=8`, center approximately `(x=.69, z=1.1)`, and AutoJump receives approximately `(dx=.1F,dz=0)`. It contains support Stone at `(0,7,0)` and `(0,7,1)`, obstacle Stone at `(1,8,0)`, cap Stone at `(1,9,0)`, dry TOP `minecraft:smooth_stone_slab` at `(1,8,1)`, and the stated empty head/cap cells; no other entity or colliding cells are admitted.

For the ordinary 26.2 AutoJump formulas with no Jump Boost and the supplied speed/input gates, `moveDir=(1,0,0)`, `lookAheadDist=max(7f, 1/l)` and the frozen endpoint is approximately `x=1.49`. The test box spans about `x=[.09,2.09]`, `y=[8,9.8]`, `z=[.5,1.7]`. `BlockCollisions` computes inclusive cursor limits from `floor(min - 1e-7)-1` and `floor(max + 1e-7)+1`, giving X `[-1,3]`, Y `[6,10]`, Z `[-1,2]` for this fixture.

`Cursor3D#advance` makes X fastest, then Y, then Z. `getNextType` omits only corners (`type == 3`). Obstacle cells `(1,8,0)`, `(1,8,1)`, and cap `(1,9,0)` are interior on all three scan axes, so none is pruned. The actual sequence reaches Stone `(1,8,0)` before the slab `(1,8,1)` (the Z coordinate is the slow axis). Its full-cube shape is emitted as world AABB `[1,8,0]–[2,9,1]`; the slab is separately emitted as `[1,8.5,1]–[2,9,2]`. The y=7 supports are also below the AutoJump ray height and do not intersect either ray.

The negative-Z side ray (at z about `.8`) crosses the Stone box while the positive-Z ray also intersects the slab. AutoJump streams each candidate’s AABBs and stops at the first shape intersecting either side segment, so its first obstacle is the separate Stone box (top `9`, center floor `(1,8,0)`). Its one no-boost loop step probes `(1,9,0)`, where the supplied cap Stone has top `10`. The updated rise is `10 - 8 = 2.0F`; because `2.0F > jumpHeight 1.2F`, the source returns before the final timer assignment. The slab candidate is never considered. Hence the unchanged initial timer stays `0`.

For contrast, the base 1.13 provider’s first extracted box in the frozen evidence is the merged Stone/cap column `[1,8,0]–[2,10,1]`, also producing rise `2.0F` and no timer write. The output agrees, but the candidate representation and first-hit box differ.

## Source references

- Product `change/v1_13/AutoJumpCollisionAggregation#aggregate/autoJumpCollisions`; `change/common/AutoJumpFullCubeCandidates#collect/aggregate`; `change/common/AutoJumpCollisionCandidates#blocksBeforeEntities/isHistoricalFullCube`.
- Product `change/v1_13/NativeWorldBlockQueryBounds#bounds`; `mixin/LocalPlayerMixin#legacyparkourcompat$autoJumpCollisionOrder`; `mixin/BlockCollisionsMixin#legacyparkourcompat$historicalScanBounds`.
- 26.2 `net.minecraft.world.level.CollisionGetter#getBlockCollisionsFromContext/getCollisions`; `net.minecraft.world.level.BlockCollisions#<init>/computeNext`; `net.minecraft.core.Cursor3D#advance/getNextType`; `net.minecraft.client.player.LocalPlayer#updateAutoJump`; `net.minecraft.world.level.block.SlabBlock#getShape`.
- Base 1.13 first-hit evidence: the frozen input linked above. Base source trace: `decompiled_minecraft/1.13/ornithe-feather/net/minecraft/world/WorldView.java#getBlockCollisions` and `net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java#autoJump`.

## Scope limits

Source correspondence only. It assumes the entire explicit fixture, including all unspecified cells in the query/padded scan being AIR, relevant chunks loaded, distant border, no other entity, callback gates admitted, initial timer zero, no Jump Boost, and the explicit move outcome. It neither derives that move from a full input-to-velocity tick nor proves runtime parity. No code, backlog, canonical source, or Git changes; no tests, build, or game launch.