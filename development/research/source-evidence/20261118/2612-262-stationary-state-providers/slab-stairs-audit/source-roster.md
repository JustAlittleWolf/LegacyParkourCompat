# Frozen source roster — 26.1.2 → 26.2 slab/stair shape providers

Audit lane: blind exact-source discovery. Primary source checkout is read-only. Mapping family: published unobfuscated names. This roster is frozen before receiving any author claim.

## Source identity

| Release | Shared source root | Source manifest SHA-256 | Ready marker SHA-256 | Provenance marker SHA-256 |
|---|---|---|---|---|
| 26.1.2 | decompiled_minecraft/26.1.2/unobfuscated/ | 54ae320660ca911a6d20514c965c33eb414fbedd1ad6727bbaa95fe906aa23c0 | f24af0f4d38543c2bd9c19b50f347d4e89d6027050e4b21ae070734843946bb7 | 8d727f880cc91f4a297e148c5b75976303d051457334bc689268fbdf631a012a |
| 26.2 | decompiled_minecraft/26.2/unobfuscated/ | a7ad74fc712567eb87136afc1eafcfd602b4088e9869f3596331fd34faefe894 | f9406adb6bf7cb4c1ab0792a798ab2cb90e082ad4c2eee032c9f674d0ea8070f | a328f5333a1b27aadde4422b23159a323ae219ccd26f11207c1a5461366f27d1 |

The source manifest file digests above were independently recomputed from the two shared unobfuscated.sources.sha256 files. Each audited Java member hash below was checked both against its source-manifest entry and by hashing the file at the shared source root.

## Paired Java member roster

Hashes are SHA-256 of exact source-file bytes. Identical hashes are shown once per version column.

| Relative Java member | 26.1.2 SHA-256 | 26.2 SHA-256 | Purpose |
|---|---|---|---|
| net/minecraft/world/level/block/SlabBlock.java | 883ac7561b118096d215127954eb243dcc60d10d4087bf256d895cf91a2ff854 | fd5f80e73fd577605ff2924adccfcd7ba5037bb400e481cd142363a385c45ba1 | Slab state→shape provider |
| net/minecraft/world/level/block/StairBlock.java | 2a8ad6eedc5a151af7a739ccd476f18cab4fe78e9df7c1ec601cb9687a77630b | 2a8ad6eedc5a151af7a739ccd476f18cab4fe78e9df7c1ec601cb9687a77630b | Stair state→shape, state updates, neighbor-derived shape |
| net/minecraft/world/level/block/WeatheringCopperSlabBlock.java | 874402caf4408f6c8fbbf55f924884b5310035905b3c969a47e76ec082dfcc8b | 874402caf4408f6c8fbbf55f924884b5310035905b3c969a47e76ec082dfcc8b | Registered slab subclass audit |
| net/minecraft/world/level/block/WeatheringCopperStairBlock.java | 4c7b97efeb9b54adbb40e76f319269ec4f0306368929cfaee73487c198cf4d57 | 4c7b97efeb9b54adbb40e76f319269ec4f0306368929cfaee73487c198cf4d57 | Registered stair subclass audit |
| net/minecraft/world/level/block/Blocks.java | ba8a258b33f73fe03f93e7b02f9c25d4f66cf3aaab04c4580d0863cc71bc866f | f3f2faeed23e9697407069a1d523107491590b8710175523ea05294d5bd00435 | Built-in registrations/factory coverage |
| net/minecraft/world/level/block/Block.java | 1693cfb7b84190a2fe664470a56d59e78ed5bd7d722a2bd16888b036d4d8e977 | cec6a05e644e4a7feb8253cc4ca772a98f0e116fb098a1b7ee7302984ac7ecab | box/column coordinate scaling |
| net/minecraft/world/level/block/state/BlockBehaviour.java | 9db85de84e502903e6fe497f043b58620b92089236ffb213f0b93db979428d13 | 9c7a103492d0714c90397da88eb696912ff6a9ca1c005d984c4746d52637fd1e | Collision-shape cache and dynamic-shape gates |
| net/minecraft/world/phys/shapes/Shapes.java | cfa63cbed77c922a68e7e8a6a3b78b09a22c7de51e0146e481e9c9d26db66f58 | 6ed9460d58727770bdec59c4f3184f075644f3c411be1eeab2eae17e8ece1891 | Shape union/rotation and horizontal direction map |
| net/minecraft/world/phys/shapes/VoxelShape.java | 419a46bbcc8b48e1c075127954eb243dcc60d10d4087bf256d895cf91a2ff854 | 419a46bbcc8b48e1c075127954eb243dcc60d10d4087bf256d895cf91a2ff854 | Voxel-shape representation |
| net/minecraft/core/Direction.java | 30fb61592f1a154bb8fc239ab8df1cd2b209f63317e0c7dde4d5b9b3e260d754 | 30fb61592f1a154bb8fc239ab8df1cd2b209f63317e0c7dde4d5b9b3e260d754 | Facing clockwise/counterclockwise/axis functions |
| com/mojang/math/OctahedralGroup.java | 6f6b3a2eb4ec1f944cba52899751f3eeff9298dba77167f4d9d152ed24f2abbf | 6f6b3a2eb4ec1f944cba52899751f3eeff9298dba77167f4d9d152ed24f2abbf | Block rotation and Y inversion constants |
| net/minecraft/world/level/block/state/properties/SlabType.java | e513c3bf499ce936576d6b5ef6062cab2965bf536d87fd37e54e8961009eab7d | e513c3bf499ce936576d6b5ef6062cab2965bf536d87fd37e54e8961009eab7d | Legal slab type values |
| net/minecraft/world/level/block/state/properties/StairsShape.java | e612e6e5a5e0e5d8ea079d24408fe0b194d79bb4a8e43783aa15877e83b051b5 | e612e6e5a5e0e5d8ea079d24408fe0b194d79bb4a8e43783aa15877e83b051b5 | Legal stair shape values |
| net/minecraft/world/level/block/state/properties/Half.java | e36b0f2250c04477dd747403bd0fd3d69e41346a75e344e5f53b16fe88277699 | e36b0f2250c04477dd747403bd0fd3d69e41346a75e344e5f53b16fe88277699 | Stair half values |
| net/minecraft/world/level/block/state/properties/BlockStateProperties.java | 0318b21d703f6a521d5ea4c5a74494097b9b8776c2f43a3084ad7569172c8a27 | c6e4b49663a09110e96a3b51628f92defd83da9267a5d569a8ac8fc0c8893cd0 | Property identity constants |

## Roster closure boundary

Includes only source needed to reconstruct dry slab/stair collision shape selection, stair-shape state derivation from neighboring stairs, registered concrete subclasses, coordinate transforms, and state collision-shape caching. It excludes placement, fluids/waterlogged behavior, support-only shapes/predicates, step/restitution, unrelated blocks and movement callers.