# Initial east-extending Stone in an ordinary 1.8.9 Z sweep

This is a separate disposition from the [bounded half-phase change](../extending-stone-moving-piston-plate.md). The exact fixture has one east-extending piston, moving head at x=1, and moving Stone at destination x=2, both at initial progress and previous progress 0. The supported, nonembedded player box is centered at x=1.5 with width 0.6F, wholly inside (1,2). A small ordinary SELF request along Z enters the Stone's native 26.2 volume from a clear starting box. The mirrored supported approach gives the other Z direction.

In exact 1.8.9, the fresh moving head getter reads full mutable bounds before trimming to the x=1 plane. Stone is trimmed to the x=2 plane. Neither plane crosses the player X interval. The old collector visits x=1 and excludes x=2. In unmodified 26.2 the dynamic Stone provider translates its destination shape west by one block. The padded cursor visits x=2 and retains the positive-volume translated Stone, which can clip Z from the face.

The product already reproduces the bounded old collection result for exact V1_8_9. BlockCollisionsMixin applies NeighborCollisionScan's floor(minX) through exclusive floor(maxX+1) to the ordinary sweep, so only x=1 is considered. The separate moving Stone shape change requires current progress 1.0 and previous progress 0.5. It is not selected for this fixture. The x=1 head uses the native extending source BE. Its SHORT flag is true at progress 0; the east arm and plate have combined local X [0,1]. The destination x=1 plus extendedProgress -1 places that shape in world X [0,1], disjoint from the player's X interval. The support snow only touches the primary query's lower Y face and contributes no intersection. No additional provider or scan change is needed for the audited Z approach.

This disposition is limited to the exact 1.8.9 initial fixture and the bounded ordinary nonembedded positive/negative Z sweeps. Prior singleton-bound mutations, direct pushes, other phases or aliases, diagonal motion, general zero-plane behavior and runtime parity remain outside this result.

- [Author r2](author-r2.md), SHA-256 8887588ed0e4141325000dfe445d7dca956645ca04ad7e354a583b70fc806611.
- [Independent verdict](verdict.md), SHA-256 36306b3318fcc81e6ae27281d655df1decab9b02c18fa8b11b5f82a5134035cc.
- [Preserved author r1](author-r1-superseded.md), SHA-256 f47bd5a353c33e24c0a71c8abbfde82087693c79f5140a3074df0aa94f690a94. Its moving-head state-shaped plate explanation was superseded by the inherited mutable bounds trace.

| Exact release | Source member | Checked SHA-256 |
|---|---|---|
| 1.8.9 | block/MovingBlock.java | 700010b071738738f04e0508050590d8d625f01115fc4886fef6e262f85c6e85 |
| 1.8.9 | block/PistonBaseBlock.java | 3c96698a674714446f9fd0d6cc1c4d5eb72937d9a2f396516ddb2461c3ea4929 |
| 1.8.9 | block/PistonHeadBlock.java | c5515744ce27521195ec802e2bd89ae5327448302542436576bcacf19f245fa4 |
| 1.8.9 | block/SnowLayerBlock.java | d922f1f9a8d6f543959835e8b21e800ecf5cd9c7c60816b9152d873ca132a3ed |
| 1.8.9 | block/Block.java | ea10f05106a3cf7189aec85236a7ecf9c7106a717f37bed583b5ea4adeffa528 |
| 1.8.9 | world/World.java | 3c04f5b874fbb6692039164c882a3c47fe28c7eca864e91c9ac57798153888ee |
| 1.8.9 | entity/Entity.java | d4c10932cb5bb1067a5a58be4e1bb1b42bdde3b6fc893d88178cc07435a1696b |
| 1.8.9 | util/math/Box.java | 8fc7bb57b1132afc7ae9539c30ff9c2585f0e36c2b0e59c60946cb1e3cee9eea |
| 26.2 | world/level/block/piston/PistonMovingBlockEntity.java | 706bab1a13ba99bbd334aad853b99f72df58fce5b6b67d30d41f902b63dd8e06 |
| 26.2 | world/level/block/piston/PistonHeadBlock.java | cd228fe96ce810c19ea747e779cd8f89f4fd3457d6277673d7ead29c8304f5ab |
| 26.2 | world/level/block/piston/PistonBaseBlock.java | 5287608eaedbd6402f4c6fac93645cf595631b1d2b3dd6987789225045a64a91 |
| 26.2 | world/level/block/piston/MovingPistonBlock.java | 9a8d9b03be6866b1e5a2d237f8cb635b89c08e63a241db2d32338448240102d1 |
| 26.2 | world/level/block/SnowLayerBlock.java | 312952c724788f30a2255bc34ef7d3242d4737067c23939f0bd0a76059b473cc |
| 26.2 | world/level/BlockCollisions.java | eb8a8f6f07b1d6384f17987818f056d41adfd6b2ad120d26774bfa550f4fc51e |
| 26.2 | world/entity/Entity.java | 7afb9c1294893ffe73e3b1acffcad41c648f15de8378bff3dffaff869bb811d5 |
| 26.2 | world/phys/shapes/Shapes.java | 6ed9460d58727770bdec59c4f3184f075644f3c411be1eeab2eae17e8ece1891 |
| 26.2 | world/phys/shapes/VoxelShape.java | 419a46bbcc8b48e1c075127954eb243dcc60d10d4087bf256d895cf91a2ff854 |
