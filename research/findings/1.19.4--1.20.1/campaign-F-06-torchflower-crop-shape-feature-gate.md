# F-06: Torchflower crop collision shape changes behind the UPDATE_1_20 feature gate

- **1.19.4**, mojmap: `1.19.4/mojmap/net/minecraft/world/level/block/TorchflowerCropBlock.java`; lines 22-24.
- **1.20.1**, mojmap: `1.20.1/mojmap/net/minecraft/world/level/block/TorchflowerCropBlock.java`; lines 21-22.

## Difference

In 1.19.4, `AGE` is `AGE_2` and each of the three `SHAPE_BY_AGE` entries is `Block.box(5.0, 0.0, 5.0, 11.0, 10.0, 11.0)`.

In 1.20.1, `AGE` is `AGE_1`; the two shape entries are `Block.box(5.0, 0.0, 5.0, 11.0, 6.0, 11.0)` and `Block.box(5.0, 0.0, 5.0, 11.0, 10.0, 11.0)`.

For the shared age-0 state, the source collision shape height changes from 10/16 to 6/16 of a block. 1.19.4's age-1 and age-2 entries are both 10/16 high; 1.20.1's age domain contains age 1 as the mature 10/16 shape. 1.19.4 gates registration with `UPDATE_1_20`, while 1.20.1 exposes the crop without that feature requirement.

## Movement meaning

`LocalPlayer#updateAutoJump` and `Entity#move` call the state collision-shape path, which dispatches to this block's `getShape` when the Torchflower crop state is encountered. 1.19.4's exact 1.19.4 artifact contains the class, but its registration requires the future `UPDATE_1_20` feature; 1.19.4's default feature set is only `VANILLA_SET`. 1.20.1 registers it without that gate.

The player consequence is conditional on a world that supplies this crop state to the collision query.

The source proves the age-dependent shape values and 1.19.4-side feature gate.

This finding is excluded from emulation for the historical map scope because it is gated future block behavior; no old block state or behavior should be invented from the 1.19.4-side experimental class.

[Archived report](../../archive/legacy-workflows/source-campaign-2026-10-07/1.19.4--1.20.1/findings/F-06-torchflower-crop-shape-feature-gate.md).
