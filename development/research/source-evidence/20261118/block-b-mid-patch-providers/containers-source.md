# 1.16.1–1.16.2 container-shape source slice

## Verdict

No eligible source difference found in the assigned stationary ordinary dry chest, trapped chest, ender chest, or hopper physical-shape slice. No independent finding is warranted. This is a bounded slice report, not a full-pair coverage or freeze claim. The shapes are reachable by ordinary entity collision queries; the compared provider and scan clauses are unchanged, so this slice predicts no movement difference.

## Evidence identity

Exact releases: Minecraft 1.16.1 and 1.16.2; Mojmap. Canonical source roots: `decompiled_minecraft/1.16.1/mojmap/` and `decompiled_minecraft/1.16.2/mojmap/`.

| Release | `mojmap.sources.sha256` SHA-256 | `mojmap.provenance.json` SHA-256 | `mojmap.ready.json` SHA-256 |
|---|---|---|---|
| 1.16.1 | `76fdd121070c2012f48cbdf5cf0c78490116afc94117c30b0858eedcd6ab9754` | `a6ff065188a400ec891980cdf2e6c0c238bf5e05168a2f69b29c6cecc2214b98` | `c19d707f7d6622733b5189599ff77aa7999cc11a3d900f29d150f9fab203ffcb` |
| 1.16.2 | `10d312ca29ee48e25f4727adafb9fcfbd741dcebe81b343d3dcffa071952a7a7` | `a9b166406064e877751542fe7df27acc32be3843f6fdc2280dac32ce0b54207c` | `e5f69bab646b6143b0660a97faDE59595ff2e924d36567b9cd285c8babd31214` |

The source-manifest SHA-256 values match the assigned identities. Relevant source-file SHA-256 values (same as entries in each version's manifest):

| Relative source file | 1.16.1 SHA-256 | 1.16.2 SHA-256 |
|---|---|---|
| `net/minecraft/world/level/block/ChestBlock.java` | `29b0a4b8d5b542bcd3d0f9d1ba1769f6d65c785288acd7ad2e1ff9f6bcd0c6a9` | `2e84e4873d9c6f0922d8128ed921786cf1cf3fed6065b02c1f38c39a3492cc30` |
| `net/minecraft/world/level/block/EnderChestBlock.java` | `c6da08989107b418b967a0941a6a1811f852cb78a2afbe6f5b3732442b6802c6` | `1692cf69e0cb4635371f9e06c7f0e6444d4d0ee8a142e17c11da2c498d2f0ac1` |
| `net/minecraft/world/level/block/HopperBlock.java` | `6bd3f48e81affc456b150369f026c43bd589b0e6c95d61cd5aff0c8e121e30b2` | `e9b4bf4956b0999812d8b106ac28e8cb7e081c162802ebf71d05e2bc4335bd2e` |
| `net/minecraft/world/level/block/Blocks.java` | `61f18f903f7d96731bbc3cac544a8614f55fddac872dc0b0fee5a53511c313a9` | `3b39d5cc4cd22f146ed3195aa30cbb9fdfca49f63783fabf9924a6ce7795fa12` |
| `net/minecraft/world/level/block/AbstractChestBlock.java` | `e9b5ee595ff73d09efa74981b11b3f529cdeccdc3e5a7c88414be1297c958190` | `e9b5ee595ff73d09efa74981b11b3f529cdeccdc3e5a7c88414be1297c958190` |
| `net/minecraft/world/level/block/TrappedChestBlock.java` | `36e7f5bc4ebfe8f7c405050c9112f0da3f3c82f21394ea34c19b774fd1b458cd` | `36e7f5bc4ebfe8f7c405050c9112f0da3f3c82f21394ea34c19b774fd1b458cd` |
| `net/minecraft/world/level/block/entity/Hopper.java` | `7bd24054f3f08e9a7e2064aa9635ef7a97de8d853c7071fb0eb43ceb8bb00e14` | `7bd24054f3f08e9a7e2064aa9635ef7a97de8d853c7071fb0eb43ceb8bb00e14` |
| `net/minecraft/world/level/CollisionSpliterator.java` | `19ab959c469b8edb5c371ff737a59a61b8a80bf397eff1db280064342094d297` | `19ab959c469b8edb5c371ff737a59a61b8a80bf397eff1db280064342094d297` |

## Compared clauses

| Clause | 1.16.1 → 1.16.2 source | Coverage and result |
|---|---|---|
| Chest physical shape, legal fixed states | `ChestBlock#getShape`, lines 155–171 in both; `getConnectedDirection`, 173–175 in both; constants 58–62 | Examined. Identical method bodies and box coordinates. `SINGLE` returns the centered 14×14 footprint with height 14/16. Paired legal halves select a directional half-box from facing and left/right type. `LEFT` uses clockwise facing; `RIGHT` uses counterclockwise facing. No state production/lifecycle was followed. |
| Ender chest physical shape | `EnderChestBlock#getShape`, 54–56 → 55–57; `SHAPE` 40 in both | Examined. Returns the same centered 14×14, 14/16-high box; state, getter, position, and context do not affect it. |
| Hopper physical and interaction shapes | `HopperBlock` constants 36–49 and `getShape` 57–72, `getInteractionShape` 75–90 in both | Examined. Identical definitions and facing dispatch. Physical shape is the same `BASE` with down/outlet extension. The separate, smaller hopper interaction shape is unchanged and is not the physical collision shape. Geometry dependency `Hopper.INSIDE` is byte-for-byte source-identical. |
| Ordinary and trapped chest providers | `AbstractChestBlock.java`, `TrappedChestBlock.java`; `Blocks` registrations | Examined. Both helper/provider files have identical full-file hashes. The `CHEST` registration at 476→475 and `TRAPPED_CHEST` at 1252→1259 keep the same class and material/strength/sound setup; trapped chest inherits the `ChestBlock` shape path. `ENDER_CHEST` at 1048→1055 and `HOPPER` at 1290→1297 retain the same class and relevant shape/collision properties. Line movement is registration ordering only. |
| Inherited collision, context, and cache path | `BlockBehaviour#getShape`/`getCollisionShape` (270–282); `BlockStateBase#getShape`/`getCollisionShape`/`getInteractionShape` (515–549); `BlockBehaviour.BlockStateBase.Cache` (state cache construction around 736–765) | Examined relevant methods in both. The default collision method gates on `hasCollision`, then delegates to the state shape; that delegation omits the supplied context and uses `BlockState#getShape` with empty context. These container shape methods themselves ignore context/world/position. Their registrations do not opt into dynamic shape behavior, and cache construction uses empty context. No relevant provider, flag, or cache-use difference found. The containing `BlockBehaviour.java` files differ elsewhere; this verdict covers only these clauses. |
| Ordinary block collision scan and spatial boundaries | `CollisionGetter#getBlockCollisions`, lines 58–64 (1.16.1) and 58–68 (1.16.2); `CollisionSpliterator` full relevant scan | Examined for this query path. `CollisionSpliterator.java` has identical full-file hashes. It builds the block cursor from `floor(min - 1e-7) - 1` through `floor(max + 1e-7) + 1` per axis, queries collision shapes with the entity context, offsets them by block position, and tests overlap against the player AABB. The local full-block shortcut and general VoxelShape intersection branches are unchanged. The 1.16.2-only `CollisionGetter#noBlockCollision` wrapper is outside the compared existing scan and has no observed relevant container-provider change. |
| Direct player and support predicates | `BlockStateBase#entityCanStandOnFace`, lines 547–549 in both; inherited suffocation/redstone/full-block behavior | Examined shape dispatch only. The direct stand-on-face predicate uses collision shape with entity context, which these providers do not use. Their partial boxes are not full-block shapes. No provider or relevant predicate difference found. |

## Movement reachability and limits

For an eligible client collision query, an entity AABB overlapping one of these fixed-state block cells reaches the ordinary collision scan; the scan feeds each provider's collision shape into its existing overlap check. The provider and scan evidence above is unchanged, so no changed collision response is established here. Hopper interaction geometry is a separate query surface; chest/ender chest rendering and interaction behavior were not treated as physical shape evidence.

Examined: assigned shape getters/constants, inherited chest provider classes, the four registrations, relevant collision/context/cache dispatch, hopper interior-shape source, and ordinary scan bounds/intersection logic.

Partial: did not trace the full player tick/call graph, all callers or side predicates, or every property/material/cache consumer. Full-pair inventory, independent audit, and pair freeze remain open to the coordinator.

Unexamined by assignment: pose/auto-jump/border behavior, airborne edges, other container families, lifecycle/placement and neighbor-state production, rendering/vehicle/non-player behavior, and unrelated changes elsewhere in the differing `Block.java` and `BlockBehaviour.java` files.
