# Grounded AutoJump aggregate witness

Independent source reviewer `/root/autojump_witness_audit` accepted the 1.14 → 1.14.1 ordinary-block collision-order claim at UTF-8 LF SHA-256 `a9f233932057cb527aec53c3515c8feef55eb430fda342bc93661805088781eb`. The revised fixture places the player's feet at Y=8 and the support stone at Y=7. The earlier fixture used Y=-1 for the support, outside these releases' build height. Set the player center Z to 0.5 and enable the client AutoJump option for the corrected witness.

In exact 1.14, `LocalClientPlayerEntity.move` (847–852) passes actual post-move deltas. Its `autoJump` (858–960) scans the broad query and uses the first box intersecting either raised probe. `WorldView.getCollisions` (152–194) collects full cubes into `BitSetVoxelSet` and appends the aggregate `IntVoxelShape`. `VoxelSet.forEachBox` (200–255) merges the lower stones across X. The first hit's center floors to (1,8,0). The upper stone at (1,9,0) raises the candidate rise to 2 and the early return leaves the cooldown unset.

In exact 1.14.1, `ClientPlayerEntity.move` (835–840) passes the measured deltas to `method_3148` (846–945). `ViewableWorld.getCollisionShapes` (129–185) emits overlapping translated per-block shapes in `CuboidBlockIterator.step` (24–48) order. The first hit is the cube at (0,8,0). Its center selects the air at (0,9,0), the rise is 1, and the callback sets `field_3934` to 1. `tickMovement` (627–632) consumes it on the next tick. `Entity.move` (432–463) establishes the grounding input from downward clipped motion. `LivingEntity.travel` (1818–1823, 1938–1940), `Entity.movementInputToVelocity` (1014–1023) and `KeyboardInput.tick` (16–21) support the sideways input and clipped positive X displacement.

Canonical exact source identities:

| Release and mapping | Relative source filename | SHA-256 |
| --- | --- | --- |
| 1.14 Ornithe Feather Gen2 build 2 | `net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java` | `2b1ad3b3416949a9da2607a3ec2251aa69b2d9eebd76638c92e20749d625338f` |
| 1.14 Ornithe Feather Gen2 build 2 | `net/minecraft/world/WorldView.java` | `1a9d57363b8123e48334065c363c5610e83dd353fe66edc2f08fed3ec09ddcb0` |
| 1.14 Ornithe Feather Gen2 build 2 | `net/minecraft/block/shape/VoxelSet.java` | `fe39ea60ac27e595280627a4d15ea93a69292da5870b5ef34a1d7d7283a267c6` |
| 1.14.1 Yarn build 10 | `net/minecraft/client/network/ClientPlayerEntity.java` | `6af7e75e80619ad77ce2c84692136b0a1d562a44d0e7910dcc3f6040b2dc9933` |
| 1.14.1 Yarn build 10 | `net/minecraft/world/ViewableWorld.java` | `67e88826a77d5ed6dbdf7701acab287bd6b34f1e394625a6a701c1181556fd2e` |
| 1.14.1 Yarn build 10 | `net/minecraft/util/CuboidBlockIterator.java` | `d80701350196f78ee8ffd6083f9d53d53b9734825201a085c6b6e62a459edf1c` |
| 1.14.1 Yarn build 10 | `net/minecraft/entity/Entity.java` | `ce429699f2d8f0a54f4be516783a474c6c7808572950164508ceed0baf68ed5b` |

This verdict covers the exact adjacent release pair and the stone/air fixture. Runtime playback, other providers and full-pair coverage remain open. Earlier alias evidence retains its existing scope.
