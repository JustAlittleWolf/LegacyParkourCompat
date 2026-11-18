# East-fixture final audit

## Binding and result

The clearance artifact `source-1122-1132-autojump-border-applicability/east-fixture-clearance-addendum.md` has actual SHA-256 `14e5b9566c6bd0a868441830053ebe67f83ee99ef451d1956a692249b49033f6`, matching the supplied identity. I independently retraced its exact east fixture against the canonical 1.12.2 and 1.13.2 source trees.

The old-release first hit and sample are confirmed: at x=10, z=-1 is visited before z=0 by the 1.12.2 x/z/y collision-list loop; the z=-0.3 ray hits the y=64 synthetic cell first. Its box has maxY=65, center `(10.5,64.5,-0.5)` floors to `(10,64,-1)`, and the t=1 clearance sample is `(10,65,-1)`. With the stated AIR sample, `u=1` and the timer arms.

The supplied addendum's modern first-box extent is not confirmed. Source yields the first qualifying aggregate box `[10,11] × [63,67] × [-1,1]`, not `[10,11] × [64,66] × [-2,2]`. Its center is `(10.5,65,0)` and the t=1 sample is `(10,66,0)`, matching the addendum's sample coordinate. With AIR there, `s` remains 67 and `u=3`; the timer remains 0. Thus the corrected fixture still has the claimed timer difference, but its 1.13.2 first-hit maximum is 67, not 66. This agrees with the earlier independent verdict's maxY=67 and corrects the interim binding addendum only insofar as it repeated that maximum; no old artifact was rewritten.

## Exact voxel reconstruction

For the supplied query bounds, 1.13.2 computes scan indices `i=8,j=12,k=63,l=67,m=-2,n=2`, so the finite cells are x=8..11, y=63..66, z=-2..1. Border substitution makes cells with x=10 or 11 full. The boundary flags are x edges 8/11, y edges 63/66, z edges -2/1. The source admission condition is `(!bl3 || !bl4) && (!bl4 || !bl5) && (!bl5 || !bl3)`: it excludes cells at two simultaneous scan edges, not every cell on a single edge.

In particular, at x=10 (interior) and z=-1 or 0 (interior), y=63 and y=66 cells pass: the flags are `(bl3=false, bl4=true, bl5=false)`, making all three conjunction terms true. At x=10,y=64/65, all four z rows pass. The populated cells at x=10 are therefore y=63..66, z=-1/0. Cells at x=11,y=63/66 are excluded by the simultaneous x/y edges, so the greedy merge does not expand this first box in x.

`VoxelSet.forEachBox` visits x indices, then y, then contiguous z runs. At x index 2 (world x=10), y index 0 (world y=63), it first encounters the contiguous z run indices 1..2 (world z=-1..0). It clears that run, cannot expand x, then expands it upward across all four y layers because the same x/z rectangle exists in each. It emits local `[2,0,1]..[3,4,3]`, which `IntVoxelShape` offsets by `(8,63,-2)` into world box `[10,11] × [63,67] × [-1,1]`. The AutoJump query overlaps this shape, and this first `getBoundingBoxes()` box intersects the z=-0.3 ray. Later boxes cannot replace it because AutoJump breaks after the first qualifying box.

The clearance addendum's account that y=63/66 cells at x=10,z=-1/0 are removed by the corner filter is inconsistent with the source boolean condition. Its proposed z extent `[-2,2]` is also not the first greedy box: at y=63 the z-edge cells are absent, so the first run is only `[-1,1]`.

## Exact source identity

Ready source-manifest identities remain 1.12.2 `b8a37ccfccd2aac5f40f5e34fec85873dbdfa93a266043c103e592e4a8c949da` and 1.13.2 `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211`. Examined file hashes match their manifest entries: 1.12.2 `World.java` `e9fa9b8d6d31ad57a5b876f5f63a5e3c554a23437decf1845a493daf48233594`; 1.13.2 `WorldView.java` `9577360b1a1bdde9280f0b72593e5e6dde45d9a54719cd7ff933f3ecd31b971f`, `VoxelSet.java` `fe39ea60ac27e595280627a4d15ea93a69292da5870b5ef34a1d7d7283a267c6`, `BitSetVoxelSet.java` `2048daa3a95ddd567a70476862e9b2dde135b91f49b61d22a4ae3aeed98322aa`, `VoxelShape.java` `d95b0607647da11595e24e1e26e9615cb7da7705ea7885908544362df62275c1`, and `LocalClientPlayerEntity.java` `2583495f3a02b4791aa036e6a8d354d7596c4984761969a0f29b29d8d9bf42bf`.

This is a bounded source audit of the east fixture only. It changes no primary or author artifact and makes no implementation or runtime-parity claim.
