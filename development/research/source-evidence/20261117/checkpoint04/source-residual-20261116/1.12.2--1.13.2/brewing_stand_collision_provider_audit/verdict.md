# Independent source audit: brewing-stand collision provider

**Verdict: ACCEPT.** No correction to the bounded report is required.

## Immutable input and source identity

Audited `build/source-residual-20261116/1.12.2--1.13.2/brewing_stand_collision_provider/report.md`, SHA-256 `586f9c6fe616036af5d4a8727fb6f7553ad81d7b542a6bd00df66c8eeafa4bd0` (verified against the report bytes).

The exact Feather Gen2 source-manifest hashes match both the supplied identities and each release's ready marker: 1.12.2 `b8a37ccfccd2aac5f40f5e34fec85873dbdfa93a266043c103e592e4a8c949da`; 1.13.2 `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211`. All seven source-file hashes listed in the report match both the files and their manifest entries.

For route audit, I additionally checked these manifest-bound sources: 1.12.2 `block/state/StateDefinition.java` (`934493deb8dad66936edf94264bda735ae8062c6ef091b1ff6a1501eed2b8d37`) and `world/World.java` (`e9fa9b8d6d31ad57a5b876f5f63a5e3c554a23437decf1845a493daf48233594`); 1.13.2 `world/WorldAccess.java` (`fc75177d629ef8d0d491821fe557e21606e4cb381164f1ca41b2763073f854be`), `world/WorldView.java` (`9577360b1a1bdde9280f0b72593e5e6dde45d9a54719cd7ff933f3ecd31b971f`), and `block/shape/VoxelShapes.java` (`313968d4e5855b5ec380272b6ababcc5849d478ab771926355912a8cb6aa9c95`).

## Verified claims

- The common exact block key is `brewing_stand`, and both releases register `BrewingStandBlock`. In 1.12.2 the registration is numeric ID 117; in 1.13.2 `Block.register("brewing_stand", ...)` constructs `BrewingStandBlock` and `Blocks.BREWING_STAND` resolves that key.
- In 1.12.2 the override ignores state, world, entity and `forceShape`; it submits the static stem and plate separately to `Block.addCollision`. That helper offsets each candidate by block position and appends it only when it intersects the query box. The state implementation forwards `addCollisions` to the block, and the ordinary `World.getCollisions` candidate loop calls it. The separate `getShape` override returns only the plate. `getFaceShape` returns `UNDEFINED`.
- In 1.13.2 the static `SHAPE` is the OR of bounds `(1,0,1)-(15,2,15)` and `(7,0,7)-(9,14,9)`. `Block.block` divides each coordinate by 16, giving the same plate and stem occupied regions. `getShape` ignores state/world/position and returns this union; the overlap between stem and plate does not change the occupied union. The state collision method delegates to the block, and the inherited block collision method returns that shape when `hasCollision` is true. Entity collision queries reach the `WorldView` shape enumeration through `WorldAccess`; the brewing-stand class does not override the collision-shape method. Its `getFaceShape` also returns `UNDEFINED`.
- The target registration uses `Properties.of(Material.IRON).strength(0.5F).lightLevel(1)` and does not disable collision. `Properties.hasCollision` defaults to true and the block constructor copies it, so the inherited collision provider is active.
- The collision shape providers do not read any of the three `HAS_BOTTLE` state bits or neighboring/world context. The older query box still controls the legacy intersection filter; the newer shape is supplied to the voxel-shape consumer.

## Bounded conclusion

The report correctly establishes equal occupied geometry and a provider/representation change: two separately emitted legacy `Box` obstacles versus a single union `VoxelShape` on the `hasCollision`-gated route. It correctly avoids inferring tick-level collision-resolution parity, an intermediate patch boundary, downstream consumer equivalence, or pair-wide coverage/freeze. Those remain outside this accepted provider comparison.