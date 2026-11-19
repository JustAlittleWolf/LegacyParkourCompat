# Base 1.13 mixed slab / Stone AutoJump result

## Verdict

**COMPLETED — the exact base `1.13` Feather Gen2 build.2 source does not support the same `ticksToNextAutojump = 1` result for this fixed fixture.** There is a concrete first-hit contradiction in the base release’s grouped partial/full shape union and final greedy box extraction. Under the stated ordinary callback gates, the first intersecting extracted box is the vertically merged full-Stone/cap column `[1,8,0]`–`[2,10,1]`; it hits the negative-Z probe before the separate top-slab box. Its top is `10`, so the final rise is `2.0F`, greater than the base `1.2F` limit, and the method does not write timer `1`. This finding is bounded to exact `1.13` and this fixture; it does not alter the separate 1.13.1/.2 results.

## Canonical 1.13 source identity

Read-only source root: `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\1.13\ornithe-feather`. The ready marker names `versionId=1.13`, `mapping=ornithe-feather`, and exact `feather-gen2-1.13+build.2` mapped artifacts; provenance resolves `net.ornithemc:feather-gen2:1.13+build.2`.

| Canonical sidecar | SHA-256 | Ready-link check |
|---|---|---|
| `ornithe-feather.ready.json` | `82a4a8f6a03fbd99a7b8577b1ae5980d95af07c7ded0da284106d16eb1e01a99` | ready marker present |
| `ornithe-feather.provenance.json` | `34652aeef6914b5cbff6619ae60a855aa676b2ae1f607eccdaa623ad09dc5e8d` | ready `provenanceSha256` matches |
| `ornithe-feather.sources.sha256` | `0509c2614b0bb0e616e2ebc6382f8eb5d031b5d86b834a4a6ed7fd96b6d68f1a` | ready `sourceManifestSha256` matches |
| `ornithe-feather.artifacts.sha256` | `a2c17075b601d9b83703f7ee188b04c6d9f68cac08d97b1a4f838fe8cd2e2c24` | ready `artifactManifestSha256` matches |

Each relied-on Java file below was independently hashed from this exact tree and its digest matched the corresponding entry in the 1.13 source manifest.

| Source file | SHA-256 |
|---|---|
| `net/minecraft/block/Block.java` | `e5be72b77a28e89595009289964bb031bbb187d15d5698570615e6fc9a21a476` |
| `net/minecraft/block/SlabBlock.java` | `2e7ff3fcfa5faa83aa4af7ea9b68f07af8d8fe7047e08ee3a9d7d37b78c7128f` |
| `net/minecraft/block/state/BlockState.java` | `0496fda381628e90b3b3ff8ae376a5bae24cbd662366f34d70991252fab2fe70` |
| `net/minecraft/state/property/Properties.java` | `d0fc6cf1d8e7b54fe3eccedf4117a303fd7801a133e17dc8c80502337ccca61f` |
| `net/minecraft/block/state/property/SlabType.java` | `20ef3591cf5f5cf83886d7ab5034fd8402418f68e517180cbba2fe0c7535f697` |
| `net/minecraft/world/WorldView.java` | `b0d1406fb04eacbc644a78548f858d5ff6c14ad781927832cb374e985f32a543` |
| `net/minecraft/world/World.java` | `6e3d94e6cdcfbb15472c4ee981a1c12500eaf7cd5d5dc20d75bcde91174c2eb5` |
| `net/minecraft/entity/Entity.java` | `09514488d9055449c47e2e4bcd23b5773203ca634b7ecba2323a99d0ae016642` |
| `net/minecraft/entity/living/LivingEntity.java` | `c38e95c2c13e088b03d77fc2c6b98d4edd7e6ca5aef12b187c8e3f3ea782e864` |
| `net/minecraft/entity/living/player/PlayerEntity.java` | `4a57ce65405436aeb211d7a04a9a9f6ae1a661d033fd3ba2424909bfb42b8acd` |
| `net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java` | `a188e1bc87831dd9da1ec1366a49acc4104c18e6979129097d02d5d3617e6a08` |
| `net/minecraft/util/math/BlockPos.java` | `ee71cfdedcaa27458f48168ab761b435cf9c878b04f21e971ddbafb6254aa3fd` |
| `net/minecraft/util/math/Vec3i.java` | `481827f072a5d36ae3a7f133afed5396988e3ce7c0501b2777a92b60c6a498b8` |
| `net/minecraft/util/math/Box.java` | `0f573d00cc10386f94cf51671c62815979973053c9e67a806e9fee98e55a4d8d` |
| `net/minecraft/util/math/Vec3d.java` | `6941a43536e8aefaa435a4e5f1731b0fa26a9d96503d1f4d07de8950c297f3c5` |
| `net/minecraft/util/math/MathHelper.java` | `ad7615e7acf9d5ebe1d7cfc236a7b2dc7726c42a32bd46254ba29f9fc68c567f` |
| `net/minecraft/block/shape/VoxelShapes.java` | `6ae1a6fa0ab76098252208fad00a6d4f3d3f1cf2023d76f63d450855df00a330` |
| `net/minecraft/block/shape/VoxelShape.java` | `3a085bf46c35869851c1db0966d596c90ec3949532a361bb90c78191a7b76f0c` |
| `net/minecraft/block/shape/VoxelSet.java` | `c450bdcdbf1e0f4fa4bf5675ee6f8f08d03bb73f63eeb35f0f622b6799aeca65` |
| `net/minecraft/block/shape/BitSetVoxelSet.java` | `d4015f8b140e0fc9f7e35a5c818ca4bf72bf1a098c75fed0e1de1d65017698ad` |
| `net/minecraft/block/shape/IntVoxelShape.java` | `e563f5530907a5cd0ecc07bd79473d378c8c1aeeaba1dba4ac6f703de7d7b3ca` |

## State, producer and callback

`Block.java:1574` registers the old raw identity `stone_slab`; the fixture state is `minecraft:stone_slab[type=top,waterlogged=false]`. `SlabBlock.java:25-43,51-65` defines those properties, returns the half-height TOP shape from `(0,8,0)` to `(16,16,16)`, and separately reports `isFullBlock(TOP)=true`. The source alias identifying the later modern ID as Smooth Stone slab is an input premise from the prior accepted alias check; this report checks the exact base-1.13 provider and callback path.

The ordinary movement path is present: `LivingEntity.mobTick` enters its movement phase through `moveRelative` (1919-26); standing `PlayerEntity.moveRelative` delegates to `super` (1442-1465), whose ordinary ground branch calls `this.move(MoverType.SELF, ...)` (LivingEntity 1639-40). Dynamic dispatch reaches `LocalClientPlayerEntity.move` (868-872): it calls `super.move`, then passes the actual post-move X/Z differences as floats to `autoJump`. In the explicitly specified fixture move, support Stone at `(0,7,0)` and `(0,7,1)` clips requested Y `-0.20` from feet `8.1` to feet `8`; X resolves from `.59` to about `.69`, leaving the box’s maxX near `.990000012`, before the x=1 obstacles. `Entity.java:578-599,676-680` confirms Y-then-X resolution and the grounded write after the downward clip. The AutoJump callback therefore receives approximately `dx=.1F,dz=0` and `onGround=true`.

The callback still requires AutoJump enabled, cooldown `<=0`, non-sneaking/unmounted state, nonzero raw input, a passing facing dot, and AIR at the direct player head probes `(0,9,1)` and `(0,10,1)`. The candidate headroom state at `(1,10,0)` is also assumed AIR. The fixed move is an explicit fixture input; this source check does not derive it from a complete input-to-velocity tick.

## Base 1.13 union and greedy box order

`LocalClientPlayerEntity.autoJump`, lines 931-960, obtains one `VoxelShape` from `world.getCollisions(this, box)`, iterates its `getBoundingBoxes()`, and breaks at the first box intersecting either lateral probe segment.

Unlike the supplied 1.13.1/.2 control, base 1.13 does not stream partial providers ahead of full cubes. `WorldView.getBlockCollisions`, lines 113-185, scans X outermost, then Z, then Y. For non-full-cube shapes it ORs cells into a per-`(x,z)` shape (`163`), ORs those by X (`170,174`), calls `optimize()` (`182`), then joins the optimized partial union with a full-cube `IntVoxelShape` (`183-184`). The full cube branch populates a `VoxelSet`; for this non-shortcut call it does not first require shape overlap with the AutoJump query. Thus the two listed y=7 support cubes also appear in the final shape, but their box is below the probe and does not hit it.

The final combined shape’s occupied cells in the obstacle column are:

| Vertical slice | z-cell 0, Stone/cap | z-cell 1, slab |
|---|---|---|
| y 8–8.5 | full Stone `(1,8,0)` | empty |
| y 8.5–9 | upper half of full Stone `(1,8,0)` | TOP slab `(1,8,1)` |
| y 9–10 | cap Stone `(1,9,0)` | empty |

`VoxelShape.getBoundingBoxes()` delegates to `VoxelSet.forEachBox` (`VoxelShape.java:95-113`). Its greedy algorithm scans X, then Y, then Z (`VoxelSet.java:234-283`); for a run it extends over X and then over contiguous Y rows that contain the same Z span. Starting at the lower Stone run `z=[0,1)` in the `y=8..8.5` slice, that exact z span remains occupied in both the upper Stone/slab slice and the cap slice. The greedy Y extension therefore emits the combined full-Stone/cap box `[1,8,0]`–`[2,10,1]` before it later emits the remaining slab-only box. This is the first obstacle box even though the slab intersects the other probe and has a separate shape contribution.

With the stated AutoJump geometry, both side probes are at `y≈8.509999990`; the negative-Z probe is at `z≈0.800524838` and crosses the first box’s `z=[0,1)` interval while its X segment crosses `[1,2)`. The earlier support box ends at `y=8` and cannot intersect. The first intersecting obstacle box has `maxY=10` and center `(1.5,9,0.5)`, which the exact `BlockPos(Vec3d)`/`Vec3i(double,...)` constructors floor to `(1,9,0)`.

AutoJump starts `s=(float)box.maxY=10`. With no Jump Boost, `o=1.2F`, and the single loop iteration samples `(1,10,0)`. If it is AIR, `s` stays 10; `u=(float)(s-playerMinY)=2.0F`, so the final `u>o` gate rejects the write. If that sampled cell instead has a collision shape whose top exceeds the limit, the in-loop `s-playerMinY>o` check returns early, also without arming. In the specified AIR fixture the path reaches the final gate: it does **not** write `ticksToNextAutojump = 1`. The later slab-only box is never inspected because the first-intersection loop already broke.

## Conditions and scope

This first-box result assumes the supplied fixed geometry and all other collision-relevant cells in the padded AutoJump scan are empty except the stated y=7 support, the full Stone at `(1,8,0)`, cap at `(1,9,0)`, and dry TOP slab at `(1,8,1)`. The source’s padded scan reaches approximately x `-1..3`, y `7..10`, z `-1..2`; full blocks in that scanned volume can enter the union even outside the tight query box, so unspecified cells cannot be inferred as AIR. Also required: relevant chunks loaded, no other entity, default distant border, baseline/sufficient movement speed, no Jump Boost, the explicit player-column headroom states, and the callback gates listed above. Altering those states can alter the greedy output or callback outcome.

This report is source-only for exact base `1.13` Feather Gen2 build.2. It does not claim the same first-hit geometry for 1.13.1/.2, any intermediate release boundary beyond the stated separate patch identities, or runtime parity. No test/build/game launch, product/backlog edit, decompiler write, or Git change was made.