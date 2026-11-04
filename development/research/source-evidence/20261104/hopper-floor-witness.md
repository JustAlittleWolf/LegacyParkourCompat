# Hopper collision-shape displacement witness

## Bounded inputs

This follow-up uses only the exact Feather Gen2 build 2 source roots `decompiled_minecraft/1.12.2/ornithe-feather` and `decompiled_minecraft/1.13.2/ornithe-feather`, plus the specialized-provider draft `build/source-drafts/1.12.2--1.13.2-specialized-collision-providers.md` at input SHA-256 `A3C6874B7897EF8D717F35B088215C5F50AB9ED2F11FB9A50E54791130A556C3`.

## Source path to player displacement

In 1.12.2, `World.getCollisions` calls each `BlockState.addCollisions(..., entity, false)` (`world/World.java:964–1010`, especially `:1001`). `HopperBlock.addCollisions` ignores state, entity, and `forceShape`; it always offers `DOWN_SHAPE` plus four side strips (`block/HopperBlock.java:38–42,56–62`). The local player move expands the player box by its requested movement, queries that collision list, and successively clips downward `y` with `Box.intersectY` (`entity/Entity.java:567–576`; `util/math/Box.java:205–220`). For a descending box whose x/z projection overlaps a hopper box, `intersectY` limits the motion to the collision box's top minus the player's feet (`Box.java:205–220`).

In 1.13.2, the local-player path is `Entity.move` → `WorldView.getCollisions` → `WorldView.getBlockCollisions` → `BlockState.getCollisionShape` → `Block.getCollisionShape` → `BlockState.getShape` → `HopperBlock.getShape`. `Entity.move` supplies the current player box and requested movement (`entity/Entity.java:578–583`). The `WorldView` interface default expands the query and its block-collection overload calls `BlockState.getCollisionShape` (`world/WorldView.java:112–151,154–177`); the `BlockState` interface default forwards to the block (`block/state/BlockState.java:205–210`); `Block.getCollisionShape` dispatches through `hasCollision` to `state.getShape` (`block/Block.java:393–394`); `HopperBlock.getShape` selects the shape from `FACING` (`block/HopperBlock.java:53–68`). The actual `hopper` registration constructs `HopperBlock` with `Block.Properties.of(...).strength(...).sounds(...)` and does not call `.noCollision()` (`block/Block.java:1300`); `Properties.hasCollision` initializes to `true` (`:1901`), while `.noCollision()` changes it to false (`:1943–1945`). Therefore this registered Hopper reaches the shape getter through the interface collision path. `VoxelShapes.calculateMaxOffset` delegates movement to each collected shape (`block/shape/VoxelShapes.java:194–205`); `VoxelShape.calculateMaxDistance` finds the nearest intersecting voxel boundary along the requested axis (`block/shape/VoxelShape.java:184–235`). These are the local player movement collision paths in both endpoints.

## Concrete setup and result

Use one ordinary hopper at block position `(0,0,0)`, with `FACING=DOWN`, in otherwise empty space. A vertical placement selects DOWN in both endpoints (`1.12.2 HopperBlock.java:44–46,64–69`; `1.13.2 HopperBlock.java:48–52,90–93`). `ENABLED` does not affect either collision provider. Put a standing player with feet at `(0.5,1.0,0.5)`, width `0.6`, not on ground, and give its normal `SELF` move a downward request `(0,-0.5,0)`. `Entity.setPosition` centers the player's width at x/z and uses y as the box bottom (`1.12.2 Entity.java:298–304`; `1.13.2 Entity.java:309–315`), so its horizontal footprint is `[0.2,0.8] × [0.2,0.8]`, fully inside the 1.13 hopper's central opening `[2/16,14/16] × [2/16,14/16]` at the upper cavity. It starts without overlapping either hopper. Player size `0.6 × 1.8` is set in both local PlayerEntity sources (`1.12.2 PlayerEntity.java:399`, `1.13.2 PlayerEntity.java:450`). An ordinary free fall can produce this downward move after falling from higher air space: both `LivingEntity` sources pass vertical velocity to `move(SELF, ...)`, then apply gravity and air drag (`1.12.2 LivingEntity.java:1514,1526,1535`; `1.13.2 LivingEntity.java:1540–1598`, especially `:1575,1588,1597`). This witness uses only the vertical component; it does not claim an exact preceding tick trajectory.

The displacement is source-determined:

- **1.12.2:** `DOWN_SHAPE` is a full x/z footprint with top `10/16` (`HopperBlock.java:38`). Since the player's feet start at `1`, the downward collision clip is `10/16 - 1 = -6/16` (`Box.intersectY`). Final feet y is `10/16`.
- **1.13.2:** `DOWN_SHAPE` contains `BASE_SHAPE` (`HopperBlock.java:33–37`). `TOP_SHAPE` fills y=`10/16..16/16`; the `ONLY_FIRST` subtraction removes `Hopper.INSIDE`, whose interior is x/z=`2/16..14/16`, y=`11/16..16/16` (`net/minecraft/inventory/Hopper.java:10`). Thus the topmost collision surface under the player's complete horizontal footprint is y=`11/16`: the entire top slice y=`10/16..11/16` remains, while the opening above it is empty. The added DOWN spout is lower and does not affect this fall. The downward clip is `11/16 - 1 = -5/16` (`VoxelShape.calculateMaxDistance`). Final feet y is `11/16`.

For this valid fall, the player's vertical displacement differs by `1/16` block: 1.12.2 moves down `6/16`, while 1.13.2 moves down `5/16`. This is direct displacement from the changed hopper collision shape, not an inference from visual shape or a movement-loop test.

## Guard and falsifier

The witness requires a down-facing hopper; the player centered over its upper opening with a 0.6-wide box; a clear, non-liquid fall path; no other overlapping blocks or entities; no world-border collision; no no-clip/no-gravity, climbing, levitation, fall-flying/creative flight, slow fall, or fluid branch; and one downward move large enough to reach both surfaces (here `-0.5`). Horizontal movement is zero, the player begins airborne, and there is no step-up branch.

The witness is falsified if either endpoint's actual queried Hopper collision provider differs from the cited methods, if the selected 1.13 state is not DOWN, if the 0.6 player footprint does not fit the `[2/16,14/16]` opening at feet y=1, if an unrelated collider clips the player first, or if the local falling movement never supplies a downward request crossing both surfaces. Falling only a shorter distance before another stop also does not realize this full `1/16` result. No runtime trajectory is asserted.

## Evidence hashes

The relevant source files match the exact release `ornithe-feather.sources.sha256` manifests:

- 1.12.2 `HopperBlock.java`: `3510f351f1a6b913dd834d1c8696d49e888d3a7cd9dd307388d3551c9cabfb6c`
- 1.12.2 `World.java`: `e9fa9b8d6d31ad57a5b876f5f63a5e3c554a23437decf1845a493daf48233594`
- 1.12.2 `Entity.java`: `80f091bf32166c88cf8bbd31caf72d84fa16224410733c7d2a0f00563f294a0a`
- 1.12.2 `Box.java`: `f788b8146b14f299ccb58ea0854609f845c298a963295d503de2e30e15d66f3a`
- 1.12.2 `PlayerEntity.java`: `e4e0fdbe07a7d0a0ae4a70cbb6739a287c9d045a4a12b409895c220b5d91fe1e`
- 1.12.2 `LivingEntity.java`: `190e9ac551538e015d9e4d6c42856e5ba32b593131cf6d93895e7b29533f1ee6`
- 1.13.2 `HopperBlock.java`: `c43588fd0bdf0c17ec7356090e67af8101335b5ee2a565088837d6d75a3a73ae`
- 1.13.2 `net/minecraft/inventory/Hopper.java`: `875ea61c315306699ac6d0e9d4551037515d4f9408521a391f6140044218b820`
- 1.13.2 `WorldView.java`: `9577360b1a1bdde9280f0b72593e5e6dde45d9a54719cd7ff933f3ecd31b971f`
- 1.13.2 `Entity.java`: `1d6ec8b80f74635401745c2c027bf36555c85348ca5693764f2668363b17d269`
- 1.13.2 `VoxelShapes.java`: `313968d4e5855b5ec380272b6ababcc5849d478ab771926355912a8cb6aa9c95`
- 1.13.2 `VoxelShape.java`: `d95b0607647da11595e24e1e26e9615cb7da7705ea7885908544362df62275c1`
- 1.13.2 `Block.java`: `735030e8bb5fc7ead2d6dbcb1b262a36a414b4349daf197456337f460b444cd4`
- 1.13.2 `PlayerEntity.java`: `4ed22f6c5a3c55d67eed782070ac722201df4d624adbc90779f1fd29c2876633`
- 1.13.2 `LivingEntity.java`: `bb691358c9a43c9f46e85575bf4d0a4ad671d0eb912502acc3a6a3f625e42f1c`

No files outside this ignored source draft were changed. No builds, tests, or game runs were performed.



