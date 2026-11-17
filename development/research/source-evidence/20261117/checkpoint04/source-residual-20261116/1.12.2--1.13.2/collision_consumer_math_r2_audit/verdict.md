# Independent source audit verdict: collision consumer math r2

## Verdict: ACCEPT

Accepted for the bounded source-level geometric clip witness and its stated limits. No correction is required to the r2 report.

## Immutable evidence identity

- Reviewed report: `build/source-residual-20261116/1.12.2--1.13.2/collision_consumer_math_r2/coverage.md` — SHA-256 `D2D1C43666E4B8BC4C81588B54EED3A8DA3A4C6C924F2A3408BFC168A2951169` (verified against current bytes).
- 1.12.2 Feather Gen2 build.2 source manifest: SHA-256 `b8a37ccfccd2aac5f40f5e34fec85873dbdfa93a266043c103e592e4a8c949da`, matching the ready marker.
- 1.13.2 Feather Gen2 build.2 source manifest: SHA-256 `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211`, matching the ready marker.
- Relevant source files checked against the pinned manifests: 1.12.2 `StairsBlock.java` (`c52a011a759b6f3ef6d45162d3b61f464ceceee5d2d8735de6f6e25a56e55611`), `Block.java` (`e4a90eca411e7b0e14f5018f7385ec29891f6cdf1a1e917fa648b5712f9b33a1`), `World.java` (`e9fa9b8d6d31ad57a5b876f5f63a5e3c554a23437decf1845a493daf48233594`), `Entity.java` (`80f091bf32166c88cf8bbd31caf72d84fa16224410733c7d2a0f00563f294a0a`), `Box.java` (`f788b8146b14f299ccbb58ea0854609f845c298a963295d503de2e30e15d66f3a`); 1.13.2 `StairsBlock.java` (`b226eb255a3a1b0a69e137c15024a3b0a48f488e7205174dd476ffd64ca82761`), `Block.java` (`735030e8bb5fc7ead2d6dbcb1b262a36a414b4349daf197456337f460b444cd4`), `WorldView.java` (`9577360b1a1bdde9280f0b72593e5e6dde45d9a54719cd7ff933f3ecd31b971f`), `Entity.java` (`1d6ec8b80f74635401745c2c027bf36555c85348ca5693764f2668363b17d269`), and `VoxelShapes.java` (`313968d4e5855b5ec380272b6ababcc5849d478ab771926355912a8cb6aa9c95`). Full digests are in the manifest entries.

## Audit findings

- In 1.12.2, `StairsBlock.addCollisions` independently submits the bottom base and its north step. `Block.addCollision` translates and adds each component when it intersects the expanded movement query. For the report's box `[.2,.8] × [.4,2.2] × [.55,1.15]`, the base overlaps the starting box, while the north step begins at `z=0.5` and is reached by the swept query. With requested `dz=-0.2`, `Box.intersectZ` clips at `-.05`.
- In 1.13.2, the straight north stair's selected shape is the union of the lower base and north-facing upper quadrants. `WorldView.getCollisions` constructs the swept shell and passes the original player box as `excludeBlock`. `getBlockCollisions` tests that original box against the whole local composite shape first; its positive-volume overlap with the base causes the complete stair shape to be omitted, including the upper step intersecting the sweep. With the stated empty surroundings, axis clipping therefore leaves `dz=-0.2` unchanged.
- The request has zero X/Y displacement and `onGround=false`; the `Entity.move` step-up gate (`onGround || requestedY != clippedY && requestedY < 0`) is false. The ordinary clipping order is Y, X, then Z in both endpoints. The stipulated `0.6 × 1.8` box matches the endpoint entity defaults (`Entity.java`); the arithmetic gives a player-sized source witness.
- Independently checked the 1.13.2 stair registration route: `Block.Properties.copy` copies the base block's `hasCollision`; the ordinary stone base is created without `.noCollision()`, and base collision is enabled by default. This supports that the collision-shape gate passes for the witness stair.

## Separate dependency and scope boundary

The separate base `hasCollision` dependency is closed by the already accepted `has_collision_key_join_audit` verdict, which confirms its bounded exact-key/default-gate claim. That verdict does not itself cover stairs, whose 1.12.2 dispatch is specialized; the stair registration/default-property route above was checked directly. This does not broaden either audit's claim or the r2 report's scope.

Acceptance is only for the source-level geometric clip witness: under the report's explicit box, state, displacement, and surroundings, the paired collision consumers produce the stated distinct clipping. It does not establish that this start-overlap state is reachable during an ordinary collision-free tick, establish ordinary-tick applicability, prove a complete player trajectory difference, close pair coverage, or establish runtime parity. Those limits remain open as stated in r2.
