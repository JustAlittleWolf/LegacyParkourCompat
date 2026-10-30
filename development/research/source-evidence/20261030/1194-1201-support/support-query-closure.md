# 1.19.4 → 1.20.1 support-query closure (bounded)

## Source identity and immutable inputs

Canonical source root: `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft`; Mojmap. Reused the 2026-10-28 source-only inventory and independent audit without repeating their full path/hash inventory. The inventory records 1.19.4 and 1.20.1 as ready, with source-manifest SHA-256 `6286e325371e085dfee1ab7987e66d4b6d1a984a49b6ec8ea730045cd24cecb3` and `858b56764113e591c60d09bc63e4d90f9c1d67a705645f0c793e8b28f2378465`, respectively. Its embedded 1.20 provenance is legacy but internally intact: `.decompile-provenance.json` SHA-256 `573f0bb3e18e39ae882769a007a1c97d711da123034f9a1c894eb407ef58030f`; relevant 1.20 source entries were checked against the manifest's `sources` map. The source publication/provenance limitation for 1.20 remains as recorded in that inventory.

For each row below, the 1.20.1 hash equals the 1.20 hash. 1.19.4 and 1.20.1 file hashes match their embedded source manifests; 1.20 file hashes match its legacy internal manifest.

| Canonical relative source path | 1.19.4 SHA-256 | 1.20 / 1.20.1 SHA-256 |
| --- | --- | --- |
| `net/minecraft/world/entity/Entity.java` | `3667fee610cbc5f58012e3a8fb8d6c4849f649fb7fe5300595158112d8b4b58b` | `94b9c3656715de2d61fa9a02ccef164261e50eb13d6090a6f07e58fe9c5b0759` |
| `net/minecraft/world/level/CollisionGetter.java` | `ed62e6b800ec057c1269d3cc4e2fb7c0cc2037370223fac99f41371e000c6b5c` | `8e4863afe40705497b36d0a58d8ee11e5103f4e72cb642aa63cc57f57649a130` |
| `net/minecraft/world/level/BlockCollisions.java` | `11fecadc012d0a544097bf0febfa1a83da5a534e6182ee7df7b2334b6ee80127` | `3313a38f9ebbd389f62e95dcbedab6a4f7f3cd3920b9ffa0ecf1a77dd869725b` |
| `net/minecraft/world/level/block/FarmBlock.java` | `8eeeb823ec6ddbd0aa5a96809399aab4a57da2d30eefde9e684883d1379c3b1a` | `4713ba2c1b536ff8e48390563e4af10c82ca42ec6d5ba79db5a9bf10f0d1c957` |
| `net/minecraft/world/level/block/Block.java` | `440b55e32f468572b4c078ab0ce5bb9b94f1c7abcaa03b03a9ce7faccb646857` | `0c2df3d958078929c4cc2985e0d6a6a766cbbbb02066e6c9ac471981aa858656` |
| `net/minecraft/world/level/block/Blocks.java` | `a4f2df87c2cae5a3827a818f39f7ad123ee5b18905ead9947efe654421c30ff5` | `1e7095d1fbfdc8198191f40037997feeca2ec993cc54dd53f2636375cae66b51` |
| `net/minecraft/world/level/block/state/BlockBehaviour.java` | `517aa4bd9875e9f7b3f5fba472a84e82afd1e11e35d3c83dc2677d8bbeb4997d` | `5f37987191165379c222c412949a0c80a904bf78a453035b1f9d86c6454510fa` |

## Paired support-query trace

- **1.19.4 Mojmap:** `Entity.getBlockPosBelowThatAffectsMyMovement()` derives the position from the entity center and bounding-box minimum Y (`Entity.java`, lines 744–762). The jump and speed-factor consumers read that block (`Entity.java`, lines 744–758). There is no `CollisionGetter.findSupportingBlock` in this release.
- **1.20 / 1.20.1 Mojmap:** after a grounded movement result, `Entity.setOnGroundWithKnownMovement` / `checkSupportingBlock` query a feet-thin AABB (`Entity.java`, lines 558–585). `CollisionGetter.findSupportingBlock` iterates collision candidates and selects the block position nearest the entity position, with a position tie-break (`CollisionGetter.java`, lines 95–109). `getBlockPosBelowThatAffectsMyMovement()` then calls `getOnPos(0.500001F)` and jump/speed factor consumers read the selected support block (`Entity.java`, lines 790–830). These classes' 1.20 and 1.20.1 hashes match.
- The selector delegates candidate enumeration to `BlockCollisions.computeNext` (1.19.4 lines 65 onward; 1.20 lines 65 onward). Its gate is the block's entity-context collision shape translated to world coordinates and its intersection with the entity AABB. In 1.20, a `BiFunction` returns the block position for the support query; the scan does not call `BlockState.isSolid()`, `blocksMotion()`, `isFaceSturdy()`, or `SupportType`. The added explicit `!shape.isEmpty()` gate is redundant for intersection with an empty shape. The changed support-selection operation itself is already covered by the existing `supporting-block-movement-factor-source.md` draft; this review adds no second finding for it.

## FarmBlock predicate result

`FarmBlock.canSurvive` changes from `!above.getMaterial().isSolid()` (1.19.4 `FarmBlock.java`, lines 48–52) to `!above.isSolid()` (1.20 `FarmBlock.java`, lines 50–54); the `FenceGateBlock` and `MovingPistonBlock` exceptions remain. In 1.20, `BlockStateBase.isSolid()` returns cached `legacySolid`, computed from forced-solid flags or the collision-shape bounds (`BlockBehaviour.java`, lines 428–477). This is a real predicate replacement, not evidence that the player support selector uses solidity.

The predicate is used by farmland placement/neighbor survival and scheduled block ticking. It does not participate in the ordinary player movement tick or the support scan. FarmBlock's moisture default remains `0`, and its exposed `SHAPE` remains `Block.box(0, 0, 0, 16, 15, 16)` with the same `getShape` return in both releases (`FarmBlock.java`, 1.19.4 lines 31, 65–67; 1.20 lines 32, 66–68). The inherited collision method remains `hasCollision ? state.getShape(...) : Shapes.empty()` (`BlockBehaviour.java`, 1.19.4 lines 295–297; 1.20 lines 290–292), with collision enabled by the properties default on both sides. Its registry has no movement-factor overrides, and the properties defaults are the same: friction `0.6F`, speed factor `1.0F`, jump factor `1.0F` (`Blocks.java`, farmland registration; `BlockBehaviour.Properties`, 1.19.4 lines 863–865 and 1.20 lines 911–913). The 1.20 registration replaces `Properties.of(Material.DIRT)` with `Properties.of().mapColor(MapColor.DIRT)`; it does not add movement-factor overrides.

Thus the FarmBlock change may affect whether farmland remains the current vanilla block under particular overhead states; any resulting world-state transition is outside fixed-profile player movement emulation. For a given current block state, the FarmBlock collision surface consumed by support enumeration is unchanged. No independent player-movement finding is warranted for this predicate.

## Boundary and remaining dependencies

This closes only the selector mechanism and the FarmBlock solidity candidate. Changed collision/support-shape providers for other 1.19.4-era blocks have not been exhaustively paired here, nor have all collision-context, neighboring-state, fluids, registrations, or external block callbacks been closed. Those remain open dependencies for full pair coverage; the pair is not frozen. No implementation, build, test, game launch, or canonical-source write was performed.
