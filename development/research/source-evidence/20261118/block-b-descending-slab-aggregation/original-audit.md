# Independent audit: 1.13 slab aggregate with negative Y

**Verdict: ACCEPT — exact bounded operation.** This review is bound to the source-only draft SHA-256 `5687321c7b074c867d555c2537697ba0fb51a01422c5656a4ecbf279db1a203af`. The draft SHA was recomputed before review and matched.

## Finding

For the already recorded two-bottom-slab witness, adding the ordinary airborne request component `dy = -0.003` preserves the primary 1.13 versus 1.13.1/1.13.2 horizontal result, under the stated unchanged box, `dx=2`, `dz=0`, and world/provider conditions. No additional claim is accepted for other boxes, slab layouts, vectors, or descending trajectories.

`LivingEntity.travel` in 1.13's dry non-flying branch calls `move(MoverType.SELF, velocityX, velocityY, velocityZ)` (lines 1598–1621). The ordinary post-move gravity correction can store `velocityY = -0.003` under its explicit predicate (1625–1630), supplying that component to a subsequent ordinary travel movement. This validates the local SELF producer/conditional boundary for negative Y; it does not establish that the particular horizontal request `dx=2` is a common player input or prove whole-trajectory reachability. `LivingEntity.java` has the same SHA-256 in 1.13.1 and 1.13.2.

In `Entity.move`, the primary collector runs before axis clipping; the order is Y, X, Z (1.13 lines 578–598; 1.13.1/1.13.2 lines 578–599). With the stated `box.maxX=1.99999995`, the Y clip's orthogonal X upper index is derived from `box.maxX - 1.0E-7 = 1.99999985` (1.13 `VoxelShapes.java:224–231`; 1.13.1/1.13.2 `VoxelShape.java:201–208`). Both slab cells begin at X=2, so neither enters that Y clip interval. Y therefore remains the requested `-0.003` before X is clipped.

The query construction selects offsets by sign and uses `ONLY_FIRST`: for `(dx,dy,dz)=(2,-0.003,0)`, the shifted start is `(-1.0E-7,+1.0E-7,+1.0E-7)` (1.13 `WorldView.java:191–196`; 1.13.1/1.13.2 `WorldView.java:158–163`). The Y component changes only the Y shift; it leaves the prior X exclusion and X interval unchanged. The cited query-shape conversion evidence covers the epsilon overlap semantics; the draft retains its original witness/provider assumptions.

For 1.13, movement clips once against the aggregated shape, retaining the witness's positive X face gap `2 - 1.99999995 = 4.9999999918171056e-8`. For 1.13.1/1.13.2, the primary shapes stream is replayable through `C_70593968`; its cached stream preserves partial-shape laziness. `BlockPos.iterateRegionMutable` increments X first, so the X=2 slab precedes X=3 at the same Y/Z. After the first slab reduces X movement to the stated sub-epsilon gap, `VoxelShapes.calculateMaxOffset` tests strict `abs(maxDist) < 1.0E-7` before calling `next()` for the next shape and returns zero (`VoxelShapes.java:194–205`). The 1.13.1 and 1.13.2 movement, collector, shape-clipping and living-travel files have identical hashes, so the same source path applies to both bugfix releases.

No primary-query detail found here causes Y to mask or replace the horizontal aggregate difference. The alternate step-up route is denied: `o` snapshots the post-adjustment requested Y at `Entity.java:530–532`; for this non-cobweb witness `o=-0.003`, and after the Y clip `y` is still `-0.003`. With `onGround=false`, `bl = onGround || o != y && o < 0.0` is false (`Entity.java:601`), so the step-up condition at 602 fails even though X clips. Its second collector call at 612 is unreachable for this bounded case.

## Exact source identity checked

Canonical `ornithe-feather` ready markers identify Feather Gen2 `1.13+build.2`, `1.13.1+build.2`, and `1.13.2+build.2`. Direct SHA-256 values matched each corresponding source-manifest entry. Relevant files:

- 1.13: `Entity.java` `09514488d9055449c47e2e4bcd23b5773203ca634b7ecba2323a99d0ae016642`; `WorldView.java` `b0d1406fb04eacbc644a78548f858d5ff6c14ad781927832cb374e985f32a543`; `LivingEntity.java` `c38e95c2c13e088b03d77fc2c6b98d4edd7e6ca5aef12b187c8e3f3ea782e864`; `VoxelShapes.java` `6ae1a6fa0ab76098252208fad00a6d4f3d3f1cf2023d76f63d450855df00a330`.
- 1.13.1 and 1.13.2: `Entity.java` `1d6ec8b80f74635401745c2c027bf36555c85348ca5693764f2668363b17d269`; `WorldView.java` `9577360b1a1bdde9280f0b72593e5e6dde45d9a54719cd7ff933f3ecd31b971f`; `LivingEntity.java` `bb691358c9a43c9f46e85575bf4d0a4ad671d0eb912502acc3a6a3f625e42f1c`; `VoxelShapes.java` `313968d4e5855b5ec380272b6ababcc5849d478ab771926355912a8cb6aa9c95`; `VoxelShape.java` `d95b0607647da11595e24e1e26e9615cb7da7705ea7885908544362df62275c1`.

I also read the neutral shared/source discovery workflows and the cited prior aggregation finding plus query construction, shape conversion, and step-gate evidence. No product implementation, wiki inventory, backlog, or Git history was consulted; no build, test, decompile, or runtime launch was performed.
