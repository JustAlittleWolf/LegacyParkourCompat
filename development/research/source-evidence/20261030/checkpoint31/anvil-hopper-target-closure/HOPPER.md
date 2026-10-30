# Hopper: 1.13 family to 26.2 provider closure

## Immutable verdict

**Verdict: collision-provider occupancy is equivalent in 1.13, 1.13.1, 1.13.2, and 26.2 for each valid hopper facing and both ENABLED values. No native collision-geometry delta is missing for this operation.** The 26.2 spout cuboids extend farther into the body before union, but every added interior part is already in the funnel; after the base union, block clipping, and directional selection, the occupied collision union is exactly the 1.13 shape.

The upper full-footprint band from Y=10/16 to 11/16 is present in both the 1.13 and 26.2 base shapes. Preserve the prior review's required correction to the 1.12.2-to-1.13 Hopper finding: this band is newly added relative to 1.12.2 and must be stated there. This target closure confirms that it persists unchanged; it does not revise that historical finding.

## Bound inputs and source identity

Accepted inputs: `.native-source/20261030/collision-pool/geom-nonwalls-review/review.md` (SHA-256 `4f0786929fe751474613d258ca097d5fb5bcec8fba075164f7fca89b130f4c45`), and `development/research/findings/1.12.2--1.13.2/hopper-collision-funnel-and-facing.md` (`650dac8b4961cca6389db8e3f3e978933f55c20837520c54091bbe98c8d0cb3a`). The review specifically requires the 10/16–11/16 band correction.

Canonical read-only source root: `D:/Javastuff/LegacyParkourCompat/decompiled_minecraft/`.

| Exact release | Mapping/source identity | Source manifest SHA-256 | HopperBlock SHA-256 | Hopper shape-constant SHA-256 |
|---|---|---|---|---|
| 1.13 | Ornithe Feather Gen2 build 2; client `d807a5ed9428b6c16b64888fd2b7fa76dcf5c55d06f96f7c0c47762c3fc3e26e` | `0509c2614b0bb0e616e2ebc6382f8eb5d031b5d86b834a4a6ed7fd96b6d68f1a` | `c43588fd0bdf0c17ec7356090e67af8101335b5ee2a565088837d6d75a3a73ae` | `875ea61c315306699ac6d0e9d4551037515d4f9408521a391f6140044218b820` |
| 1.13.1 | Ornithe Feather Gen2 build 2; client `f0b4cf6d1a87ae150f87a535651138ca7b82bdad8853f76e7ddbff1dd610ac87` | `21770b01ff3bf443db2e9e1b6df7db25d2fd6b7a72a86228259269c932d3abba` | `c43588fd0bdf0c17ec7356090e67af8101335b5ee2a565088837d6d75a3a73ae` | `875ea61c315306699ac6d0e9d4551037515d4f9408521a391f6140044218b820` |
| 1.13.2 | Ornithe Feather Gen2 build 2; client `3410887ba652f25792c7675bfaf9140e73b60e93cfbf113a803f8a98cb05c0f9` | `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211` | `c43588fd0bdf0c17ec7356090e67af8101335b5ee2a565088837d6d75a3a73ae` | `875ea61c315306699ac6d0e9d4551037515d4f9408521a391f6140044218b820` |
| 26.2 | unobfuscated exact-version source; ready source manifest | `a7ad74fc712567eb87136afc1eafcfd602b4088e9869f3596331fd34faefe894` | `e6687c0f3a34a796e5faf7bb04004c9b3cadc700119f417087eae4eb6aeb298a` | inline `inside` construction in `HopperBlock.java` |

The 1.13-family `HopperBlock.java` and `Hopper.java` files are byte-identical across all three exact versions; the 1.13.1 and 1.13.2 source-manifest hashes above were recomputed from their canonical manifests. Additional 26.2 hashes: `Block.java` `cec6a05e644e4a7feb8253cc4ca772a98f0e116fb098a1b7ee7302984ac7ecab`; `Shapes.java` `6ed9460d58727770bdec59c4f3184f075644f3c411be1eeab2eae17e8ece1891`; `BlockBehaviour.java` `9c7a103492d0714c90397da88eb696912ff6a9ca1c005d984c4746d52637fd1e`; `BlockStateProperties.java` `c6e4b49663a09110e96a3b51628f92defd83da9267a5d569a8ac8fc0c8893cd0`; `Blocks.java` `f3f2faeed23e9697407069a1d523107491590b8710175523ea05294d5bd00435`; `CollisionGetter.java` `not separately hash-bound here`; `BlockCollisions.java` `eb8a8f6f07b1d6384f17987818f056d41adfd6b2ad120d26774bfa550f4fc51e`.

## Component-union comparison

All coordinates are sixteenths of a block. The 1.13 `Block.block` helper divides coordinates by 16 (`Block.java:152-154`). In 26.2 `Block.column(sizeXZ,minY,maxY)` centers a square footprint at `(8,8)` in X/Z; the two-size overload centers each requested width independently (`Block.java:177-184`).

The common base outline is the union of the full-footprint upper slab `[0,10,0]..[16,16,16]` and centered funnel `[4,4,4]..[12,10,12]`; both subtract the same inside volume `[2,11,2]..[14,16,14]` with `ONLY_FIRST`. In 1.13 these are `TOP_SHAPE`, `FUNNEL_SHAPE`, `CONVEX_BASE_SHAPE`, `BASE_SHAPE`, and `Hopper.INSIDE` (`HopperBlock.java:33-36`; `Hopper.java:9-12`). In 26.2 they are `spoutlessHopperOutline`, `spoutlessHopper`, and the constructor's `inside` (`HopperBlock.java:56,64-66`; `Block.column` as above). Because the subtraction starts at Y=11, the whole `[0,10,0]..[16,11,16]` band remains solid in both, including the full-footprint 10/16–11/16 slice.

1.13 unions one outlet with that base per facing (`HopperBlock.java:37-41`). The 26.2 seed outlet for NORTH is `Block.boxZ(4,4,8,0,8)`, i.e. `[6,4,0]..[10,8,8]` by `Block.boxZ` (`Block.java:196-198`). For NORTH, its inner half `Z=4..8` is already contained in the common funnel at `X=6..10,Y=4..8`; its only new occupancy outside the base is `[6,4,0]..[10,8,4]`, exactly the 1.13 NORTH outlet. Horizontal rotations preserve the 4-wide cross-section and the 4-unit outward outlet; the rotated inner half remains inside the funnel. The only valid non-horizontal facing is DOWN. `Shapes.rotateAll` uses the supplied `(8,6,8)/16` center and the DOWN quarter-turn (`Shapes.java:427-449`); the clipped rotated outlet is `[6,0,6]..[10,6,10]`. Its Y=4..6 portion is already in the funnel, leaving exactly `[6,0,6]..[10,4,10]`, the 1.13 DOWN outlet. The `Shapes.block()` AND at `HopperBlock.java:67-69` clips generated spouts to the unit block before union. Thus the code's larger intermediate spout box does not add occupied collision volume.

The state provider remains facing-selected. Both versions restrict hopper `FACING` to DOWN and the four horizontal directions (`1.13 Properties.java:55`; 26.2 `BlockStateProperties.java:...`, with identical `direction != UP` predicate); both default to DOWN and ENABLED=true (`1.13 HopperBlock.java:48-51`; 26.2 `HopperBlock.java:53-57`). `ENABLED` does not alter shape: 1.13 `getShape` switches only on FACING (`HopperBlock.java:54-68`), and 26.2's shape calculator also reads only FACING (`HopperBlock.java:64-75`). `getShapeForEachState(..., ENABLED)` normalizes the ignored property to its default before lookup (`Block.java:588-603`), so both boolean state values return the same geometry.

Both 1.13 and 26.2 register Hopper with collision-enabled default properties (1.13 `Block.java:1319`; 26.2 `Blocks.java:2926-2930`; modern `BlockBehaviour.Properties.hasCollision` defaults true at `BlockBehaviour.java:985`; `.noOcclusion()` is not `.noCollision()`).

## Collision context and interaction-shape separation

1.13 movement's `WorldView.getBlockCollisions` obtains `state.getCollisionShape(world,pos)` (`WorldView.java:113-145`), so it requests the provider's default/empty-context collision shape. 26.2 player collision queries pass an entity-derived `CollisionContext` (`CollisionGetter.java:96-105`; `BlockCollisions.java:93`; `CollisionContext`/`EntityCollisionContext` route to `state.getCollisionShape(..., context)`). The default 26.2 collision provider discards that context and calls `state.getShape(level,pos)` (`BlockBehaviour.java:327-329`). The Hopper shape override ignores its context (`HopperBlock.java:73-76`), and its precomputed result depends only on FACING. This is therefore equivalent for entity and empty contexts.

Hopper also has a distinct selection/interaction shape. 1.13 uses `getInteractionShape` with `Hopper.INSIDE` and Y=8..10 facing strips (`HopperBlock.java:42-46,72-89`). 26.2 builds `interactionShapes` separately from `inside` plus the same Y=8..10 horizontal strips, with DOWN mapped to `inside` (`HopperBlock.java:56-61,78-81`). Those are not collision providers and are not used by the player collision query above. Keep this selection path distinct from the collision shape when applying the verdict.

## Source-reference addendum

The exact modern hopper facing restriction is `BlockStateProperties.java:56` (`direction != Direction.UP`); horizontal Anvil facings use `BlockStateProperties.java:57`. The exact `CollisionGetter.java` SHA-256 is `8e7d1a54d27e0f187667439539c59b8892f966f1b9c8107eb162c6d43f830f6c`; `BlockCollisions.java` is `eb8a8f6f07b1d6384f17987818f056d41adfd6b2ad120d26774bfa550f4fc51e`.

## Limits

This closes only the 1.13-family-to-26.2 Hopper collision-provider dependency. It does not locate the earlier change's patch boundary, prove a full movement trajectory, or replace the required correction to the separate 1.12.2-to-1.13 finding.
