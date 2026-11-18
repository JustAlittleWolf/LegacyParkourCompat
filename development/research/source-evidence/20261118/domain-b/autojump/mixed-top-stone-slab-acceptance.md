# Mixed top Stone slab and Stone AutoJump order in exact 1.14

The independent source audit accepted the bounded dry, loaded, entity-free and border-interior fixture. Author SHA-256 `429042759bfe629ac34644f270d7f4499e9d2b940fc04864feb4ccddb996ef4`, independent audit `75ec8ebdfb0306086c959a58c1fdd2171d817bd8b07cbe3bc59f4bf918cfb0df`.

- **1.14, Ornithe Feather Gen2 build.2.** `WorldView.getCollisions` (`WorldView.java:127–194`) applies voxel overlap to translated non-full shapes and emits them before its full-cube accumulator. `SlabBlock.getShape` (`SlabBlock.java:46–53`) supplies one top-half Stone slab box. `LocalClientPlayerEntity.autoJump` (`:858–951`) uses the first intersecting side probe. In the fixture, the slab's cap is air and the one-block rise arms AutoJump.
- **1.14.1, Yarn build.10.** `ViewableWorld.getCollisionShapes` (`ViewableWorld.java:129–193`) emits per-cell shapes. `CuboidBlockIterator.step` (`:24–53`) visits X first, then Y, then Z, so ordinary Stone one Z cell before the slab wins. `ClientPlayerEntity.method_3148` (`:847–940`) sees Stone above that first hit, raises the measured rise to two blocks and returns before arming.

The audited path lands on two support stones before the detector call and keeps the player's box about `0.01` block short of the obstacle face. After the source casts and fast inverse square root, the side rays have positive margins through separate Stone and slab columns and the probe Y is about `0.01` above the slab's lower surface. The fixture uses a sole dry top `minecraft:stone_slab`, two support stones, a Stone obstacle and its Stone cap, with air above the slab and the player.

The 1.13.1 and 1.13.2 audit covers the corresponding providers and generic ordering prerequisite. Their mixed-provider outcome and the original 1.13 slab/frame route are outside the exact 1.14 integration. Runtime parity remains unverified.
