# Extending Stone moving-piston collision plate

The independent temporal audit (`/root/piston_moving_temporal_audit`) and 26.2 adapter projection review (`/root/moving_stone_projection`) accepted ordinary local-player collision against a non-source, extending moving piston carrying vanilla Stone. The historical read is at the destination owner cell and uses the previous block-entity progress. This evidence concerns event-driven collision collection and leaves direct piston entity pushes and the source head/retraction cases separate.

## Historical provider and exact boundary

- In 1.8.9 Feather Gen2 build 2, `PistonBaseBlock.move` (lines 312–319) stores the non-source block entity at the destination cell. `MovingBlock.getCollisionShape` (lines 118–129) calls `MovingBlockEntity.getProgress(0.0F)` (lines 54–60), so the sampled phase is `lastProgress`. Extending uses float `1.0F - lastProgress`. Its overload (lines 162–195) reads the moved block's collision box at the owner position and trims the facing-side extent by that float distance. Plain Stone inherits the full-block collision box from `Block.getCollisionShape` (lines 346–350). A positive-facing direction reduces the maximum bound on that axis and a negative-facing direction increases the minimum. Other extents retain their full-block values.
- In 1.9 Feather Gen2 build 2, `MovingBlock.getCollisionShape` (lines 131–134) delegates to `MovingBlockEntity.getShape` (lines 75–88). It unions the two whole Stone boxes translated by current and previous progress, so it can extend outside the owner cell. The paired 1.9.4 members are in the main finding. The 1.8.9 operation has verified source applicability at that release. Applicability to earlier releases grouped under the 1.8 profile is a separate pending check.

The accepted east-facing `(progress,lastProgress)=(0.5F,0.0F)` sweep witness (`moving-block-stone-sweep-witness-r2.md`, SHA-256 `05a2b44304fe83008b9896d7bb5bcf23b51ea85e3a4f38ee5a94174d87c6ec91`) has an airborne player approach from the west with an initial X interval ending at `-1`, requested `dx=0.5`, and a destination cell at `(0,0,0)`. The 1.8.9 world scan misses that cell and permits the movement, whereas the 1.9 scan includes that cell and the `[-1,0.5]` union clips X to zero. The comparison involves scan bounds as well as the provider. With previous progress zero, the old provider returns a plane at the destination's trailing face. The historical `Box.intersects` and axis clips (1.8.9 lines 93–158) can still admit and clip against that plane when a sweep strictly straddles it. A modern empty voxel shape is equivalent only where the old plane is already excluded from the query. A shape-only adaptation cannot infer that guard from progress alone.

## Native adapter anchors

- In 26.2 unobfuscated, `PistonBaseBlock.moveBlocks` writes the moving block and block entity to the destination owner with `source=false` (lines 310–321). `MovingPistonBlock.getCollisionShape` delegates there (lines 107–109).
- `PistonMovingBlockEntity.tick` (lines 310–342) copies current progress to `progressO` before advancing by `0.5F`, and clamps the new value to `1.0F`. The ordinary advancing phases are `(0.5F,0.0F)` and `(1.0F,0.5F)`. At the second phase the 1.8.9 plate has thickness `0.5`, lies within the owner block, and differs from the current-progress native full cube.
- `PistonMovingBlockEntity.getCollisionShape` (lines 366–393) returns an empty native shape for this non-source case when direct-push no-clip matches the movement direction and current progress is below `1.0`. Otherwise it translates the complete Stone cube by current progress. Preserve the native direct-push route. At terminal `(1.0F,1.0F)` the historical and modern ordinary Stone geometry is the same full cube.
- Allow only non-source, extending Stone normal block-collision reads. Keep arbitrary moved states, retraction, source heads, unrelated movement contexts and general degenerate-plane clipping outside this evidence. Runtime verification was outside the review.

## Exact source-file SHA-256

Each file digest below matches the corresponding exact-release ready source manifest, or the mapping-local source provenance for 1.9.

1.8.9, ornithe-feather. `ornithe-feather.sources.sha256` SHA-256 `9e75f46dc0ed43b6a355bd65db8a92c93a4dfeaecfa92284187c6fe9410d8004`.

- `net/minecraft/block/Block.java` `ea10f05106a3cf7189aec85236a7ecf9c7106a717f37bed583b5ea4adeffa528`
- `net/minecraft/block/MovingBlock.java` `700010b071738738f04e0508050590d8d625f01115fc4886fef6e262f85c6e85`
- `net/minecraft/block/PistonBaseBlock.java` `3c96698a674714446f9fd0d6cc1c4d5eb72937d9a2f396516ddb2461c3ea4929`
- `net/minecraft/block/StoneBlock.java` `ca15180da4a946055afe68f8c6c578070fae700d13b50b4015272d772ddeed23`
- `net/minecraft/block/entity/MovingBlockEntity.java` `05a5bfbf2556eaa621996bda365b7bab01f5b9060cf773e7b9949ebe2f39fff6`
- `net/minecraft/util/math/Box.java` `8fc7bb57b1132afc7ae9539c30ff9c2585f0e36c2b0e59c60946cb1e3cee9eea`
- `net/minecraft/world/World.java` `3c04f5b874fbb6692039164c882a3c47fe28c7eca864e91c9ac57798153888ee`

1.9, ornithe-feather. `.decompile-provenance.json` SHA-256 `8daacb7af8f210e716ee4b34c43b8a5d6f6b4b19831d2fd725cbe16ecf330a1a`.

- `net/minecraft/block/MovingBlock.java` `a5b2584bc575dc888f1d32d9d4b683b91fc8b145ee079e66ff4722718fc8906f`
- `net/minecraft/block/entity/MovingBlockEntity.java` `7ad3426bb74c9e69b4f521d20e045b1dc1eafa9b725daf0e2c0b9b718bc2683c`
- `net/minecraft/world/World.java` `d1e23db2e20c86eb37b964a0df6838f1422ade7b0c2fff9c869ff8345979aa98`

26.2, unobfuscated. `unobfuscated.sources.sha256` SHA-256 `a7ad74fc712567eb87136afc1eafcfd602b4088e9869f3596331fd34faefe894`.

- `net/minecraft/world/level/block/piston/MovingPistonBlock.java` `9a8d9b03be6866b1e5a2d237f8cb635b89c08e63a241db2d32338448240102d1`
- `net/minecraft/world/level/block/piston/PistonBaseBlock.java` `5287608eaedbd6402f4c6fac93645cf595631b1d2b3dd6987789225045a64a91`
- `net/minecraft/world/level/block/piston/PistonMovingBlockEntity.java` `706bab1a13ba99bbd334aad853b99f72df58fce5b6b67d30d41f902b63dd8e06`
