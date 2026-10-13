# F-06: Torchflower crop collision shape changes behind the UPDATE_1_20 feature gate

- Older version A: 1.19.4
- Newer version B: 1.20.1
- Mechanic / coverage slice IDs: S5-02, INV-COLLISION, INV-WORLD-MOVEMENT
- Classification: changed behavior
- Confidence: source-confirmed
- Applicability: modern-only mechanic
- First changed release: unknown within (1.19.4, 1.20.1]
- Runtime validation: not performed

## Paired evidence

- A: artifact evidence `A-1.19.4-MOJMAP-ORIGINAL`; source `../../../build/movement-campaign-2026-10-07/ready/1.19.4/mojmap/net/minecraft/world/level/block/TorchflowerCropBlock.java`, `net.minecraft.world.level.block.TorchflowerCropBlock#SHAPE_BY_AGE` lines 22-24 and `#getShape(BlockState, BlockGetter, BlockPos, CollisionContext)` lines 36-38; SHA-256 `9439658c6115a9ad93ae1462273a34a35b32b5262aae7442992932a4acd60e1b`. `Blocks.java#TORCHFLOWER_CROP` lines 2787-2797 registers this block with `requiredFeatures(FeatureFlags.UPDATE_1_20)`; SHA-256 `a4f2df87c2cae5a3827a818f39f7ad123ee5b18905ead9947efe654421c30ff5`. `BlockBehaviour#requiredFeatures` lines 202-204 returns the configured set, and `FeatureElement#isEnabled` lines 15-17 checks whether it is a subset of the active set; their SHA-256 values are `517aa4bd9875e9f7b3f5fba472a84e82afd1e11e35d3c83dc2677d8bbeb4997d` and `cde9c993a18d8d1cfadc1e54a9f0f87c37d9d25d40c5e994471f2407f22830e8`. `FeatureFlags.java` lines 35-39 defines `UPDATE_1_20` and sets `DEFAULT_FLAGS = VANILLA_SET`; SHA-256 `ad6cad1220b86b464c57d013bf0479296a10ca90d45216a02f05a8ac56d62566`.
- B: artifact evidence `B-1.20.1-MOJMAP-ORIGINAL`; source `../../../build/movement-campaign-2026-10-07/ready/1.20.1/mojmap/net/minecraft/world/level/block/TorchflowerCropBlock.java`, `net.minecraft.world.level.block.TorchflowerCropBlock#SHAPE_BY_AGE` lines 21-22 and `#getShape(BlockState, BlockGetter, BlockPos, CollisionContext)` lines 35-37; SHA-256 `fef7f434df1ee1886c8df4eb641fcc64610a13c9fc7cabba54d7be5051f27a39`. `Blocks.java#TORCHFLOWER_CROP` lines 4852-4863 registers the block without the A-side `UPDATE_1_20` requirement; SHA-256 `1e7095d1fbfdc8198191f40037997feeca2ec993cc54dd53f2636375cae66b51`. `BlockBehaviour#requiredFeatures` lines 197-199 and `FeatureElement#isEnabled` lines 15-17 use the same required-set predicate; the corresponding file hashes are `5f37987191165379c222c412949a0c80a904bf78a453035b1f9d86c6454510fa` and `cde9c993a18d8d1cfadc1e54a9f0f87c37d9d25d40c5e994471f2407f22830e8`.
- A provenance: readiness marker version ID and metadata ID are both 1.19.4; client SHA-256 `0e79cf7f07c107e9a1fe22ed703e472372b44149e64114944f0811abbd25f3ec`; the cached metadata's client object SHA-1 is `958928a560c9167687bea0cefeb7375da1e552a8`; remapped client jar SHA-256 `efbf38c89b396faae60cfe3bdb91671cb8d1ec3ccf2e27d3ffab4d261acd016d`, whose entry list contains `net/minecraft/world/level/block/TorchflowerCropBlock.class`. This is an exact 1.19.4 artifact containing gated future content, not a source-version mismatch.

## Source-level difference

In A, `AGE` is `AGE_2` and each of the three `SHAPE_BY_AGE` entries is `Block.box(5.0, 0.0, 5.0, 11.0, 10.0, 11.0)`. In B, `AGE` is `AGE_1`; the two shape entries are `Block.box(5.0, 0.0, 5.0, 11.0, 6.0, 11.0)` and `Block.box(5.0, 0.0, 5.0, 11.0, 10.0, 11.0)`. For the shared age-0 state, the source collision shape height changes from 10/16 to 6/16 of a block. A's age-1 and age-2 entries are both 10/16 high; B's age domain contains age 1 as the mature 10/16 shape. A gates registration with `UPDATE_1_20`, while B exposes the crop without that feature requirement.

## Reachability and dependencies

`LocalPlayer#updateAutoJump` and `Entity#move` call the state collision-shape path, which dispatches to this block's `getShape` when the Torchflower crop state is encountered. A's exact 1.19.4 artifact contains the class, but its registration requires the future `UPDATE_1_20` feature; A's default feature set is only `VANILLA_SET`. B registers it without that gate. The player consequence is conditional on a world that supplies this crop state to the collision query.

## Consequence and uncertainty

The source proves the age-dependent shape values and A-side feature gate. This finding is excluded from emulation for the historical map scope because it is gated future block behavior; no old block state or behavior should be invented from the A-side experimental class. No runtime trajectory is claimed. The exact release that introduced the changed shape is unknown within this endpoint pair.

## Handoff

Independent source delta: Torchflower crop collision geometry differs, but the A-side block is behind the `UPDATE_1_20` gate and is explicitly excluded from historical movement emulation. Related finding IDs: none. Applicability constraint: modern-only/gated block content. Implementation and testing decisions are deferred.
