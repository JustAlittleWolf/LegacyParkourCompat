# Source-only coverage: 1.12.2 -> 1.13.2, collision contracts

## Evidence identity

- Older source: `decompiled_minecraft/1.12.2/ornithe-feather`, exact `versionId=1.12.2`, namespace `ornithe-feather`, Feather Gen2 build 2 (`feather-gen2-1.12.2+build.2-mergedv2.jar` and `feather-gen2-1.12.2+build.2.tiny`). The ready marker records 2,050 source files and source-manifest SHA-256 `b8a37ccfccd2aac5f40f5e34fec85873dbdfa93a266043c103e592e4a8c949da`; the provenance and ready markers identify the exact version and mapping. The verification marker reports 2,050/2,050 source files identical to its published source, with zero source differences.
- Newer source: `decompiled_minecraft/1.13.2/ornithe-feather`, exact `versionId=1.13.2`, namespace `ornithe-feather`, Feather Gen2 build 2 (`feather-gen2-1.13.2+build.2-mergedv2.jar` and `feather-gen2-1.13.2+build.2.tiny`). The ready marker records 2,711 source files and source-manifest SHA-256 `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211`; the provenance and ready markers identify the exact version and mapping. The verification marker reports 2,711/2,711 source files identical to its published source, with zero source differences.
- The source-manifest files themselves hash to the values recorded by their ready markers. Direct SHA-256 checks of the 18 inspected source files below matched their rows in the corresponding `ornithe-feather.sources.sha256` manifests. Both success excerpts report `BUILD SUCCESSFUL` for their decompile runs. No source generation was performed.

### Directly checked source-file hashes

| Release | Source path relative to `ornithe-feather/` | SHA-256 |
|---|---|---|
| 1.12.2 | `net/minecraft/entity/Entity.java` | `80f091bf32166c88cf8bbd31caf72d84fa16224410733c7d2a0f00563f294a0a` |
| 1.12.2 | `net/minecraft/entity/living/LivingEntity.java` | `190e9ac551538e015d9e4d6c42856e5ba32b593131cf6d93895e7b29533f1ee6` |
| 1.12.2 | `net/minecraft/world/World.java` | `e9fa9b8d6d31ad57a5b876f5f63a5e3c554a23437decf1845a493daf48233594` |
| 1.12.2 | `net/minecraft/block/Block.java` | `e4a90eca411e7b0e14f5018f7385ec29891f6cdf1a1e917fa648b5712f9b33a1` |
| 1.12.2 | `net/minecraft/block/LiquidBlock.java` | `00f7995dd49a48bf97316e30b3d6e41d8ee1e65dd212f3dc64891883621c4260` |
| 1.12.2 | `net/minecraft/world/chunk/WorldChunk.java` | `b80c4962661b31c9015faa9bb3d42e390528b59473956ba20e424c76625b844f` |
| 1.13.2 | `net/minecraft/entity/Entity.java` | `1d6ec8b80f74635401745c2c027bf36555c85348ca5693764f2668363b17d269` |
| 1.13.2 | `net/minecraft/entity/living/LivingEntity.java` | `bb691358c9a43c9f46e85575bf4d0a4ad671d0eb912502acc3a6a3f625e42f1c` |
| 1.13.2 | `net/minecraft/world/World.java` | `0023de30da608db73c6c6188334d2bb19039d86fe4d6f5e4ad79d732a5d2cd1a` |
| 1.13.2 | `net/minecraft/world/WorldView.java` | `9577360b1a1bdde9280f0b72593e5e6dde45d9a54719cd7ff933f3ecd31b971f` |
| 1.13.2 | `net/minecraft/world/WorldAccess.java` | `fc75177d629ef8d0d491821fe557e21606e4cb381164f1ca41b2763073f854be` |
| 1.13.2 | `net/minecraft/block/Block.java` | `735030e8bb5fc7ead2d6dbcb1b262a36a414b4349daf197456337f460b444cd4` |
| 1.13.2 | `net/minecraft/block/LiquidBlock.java` | `feedc9a5f1843604e6755a447e89d8d5599613799e25cf8c93a0562e52e97856` |
| 1.13.2 | `net/minecraft/block/state/BlockState.java` | `0496fda381628e90b3b3ff8ae376a5bae24cbd662366f34d70991252fab2fe70` |
| 1.13.2 | `net/minecraft/world/chunk/WorldChunk.java` | `d800fac11aa2672a4dd69cb5a599e5f84bb6e2c71cfc8080fa972acdabfb4914` |
| 1.13.2 | `net/minecraft/world/chunk/WorldChunkSection.java` | `df960f6239f7413dea7882775b9693a37c5f3650eabd0e5bf70f1504a0094e7e` |
| 1.13.2 | `net/minecraft/fluid/FlowingFluid.java` | `2398d109ddc5785cb1bc8872b8c157319137df8957faf8a03581a8f68afb1253` |
| 1.13.2 | `net/minecraft/fluid/state/FluidState.java` | `05e2df9377a3bc1e91645ce4282bcaa4edebe93d7ef1907407961baaecff23a6` |

## Ordered coverage

1. **Player tick entry to environmental checks.** Traced `Entity.tick`: 1.12.2 calls `checkWaterCollisions` (`Entity.java:382`); 1.13.2 calls the water-state updater (`Entity.java:394`, which calls `checkWaterState` and updates water submersion). `LivingEntity.checkFallDamage` also conditionally calls the respective check when `!isInWater()` (`LivingEntity.java:195-199` and `210-214`). The player inherits this path.
2. **Water contact/current query and provider chain.** Compared 1.12.2 `Entity.checkWaterCollisions` and `World.applyLiquidDrag` to 1.13.2 `Entity.checkWaterState` / `m_69693160(Tag<Fluid>)`, `World.getFluidState`, `WorldChunk.getFluidState`, `WorldChunkSection.getFluidState`, `BlockState.getFluidState`, `Block.getFluidState`, `LiquidBlock.getFluidState`, `FluidState.getHeight/getFlow`, and `FlowingFluid.getHeight/getFlow`. The 1.13.2 chunk section reads the fluid state through the stored block state's provider; the liquid block maps its `LEVEL` into one of its fluid states. This produced the draft finding `water-contact-and-current-query.md`.
3. **Submersion predicate.** Compared 1.12.2 `Entity.isSubmergedIn(Material)` (`Entity.java:1027-1043`) to 1.13.2 `Entity.isSubmergedIn(Tag<Fluid>)` (`Entity.java:1042-1051`). Both are point queries at eye height, reject boats, and compare the eye point with a fluid surface. The new provider is a `FluidState` tag/height; no independent movement-output difference was isolated in this slice. Player-side statistic consumers were not treated as movement.
4. **Block collision query/shape provider.** Compared 1.12.2 `Entity.move`, `World.getCollisions`, `Block.addCollisions`, and `Block.getCollisionShape` with 1.13.2 `Entity.move`, `WorldView.getBlockCollisions/getCollisions/hasNoCollisions`, `World.getCollisions`, and `Block.getCollisionShape`. The call/provider contract changes from appended translated AABBs to translated `VoxelShape` streams and axis max-offset queries. This is recorded as examined, without a standalone behavior finding: this endpoint slice does not isolate a concrete 1.12.2-to-1.13.2 output divergence from representation alone, and the supplied assignment identifies 1.13.0-to-1.13.1 shape aggregation as separately covered. Do not infer this pair's final provider behavior from that intermediate boundary.
5. **Existing liquid registrations.** Checked 1.12.2 `Blocks` water/lava fields and assignments against 1.13.2 `Blocks`, `Fluids`, and `LiquidBlock.getFluidState`. Existing water/lava still have block registrations; 1.13 adds distinct fluid registrations and derives a fluid state from the liquid block state's level. New post-1.12.2 blocks and mechanics were excluded.

## Draft finding

`water-contact-and-current-query.md` is a source-confirmed draft with a concrete player-reachable current-strength difference for shallow flowing water. The exact earliest patch boundary is unknown within 1.12.2..1.13.2; no intermediate source is included in this assignment.

## Coverage limits and unresolved dependencies

- The collision provider surface was traced at the engine contract and movement call sites, but individual legacy block collision overrides/registrations were not exhaustively compared. Existing-block geometry, fence changes, and the separately assigned glide grouping were intentionally not inventoried as candidate findings.
- The pair's collision representation migration is evident, but its full provider/aggregation behavior remains dependent on comparison against the assigned 1.13.0 and 1.13.1 evidence and on independent coverage review. The supplied prior fact is context only; it does not establish endpoint equivalence or the exact change boundary here.
- Water current vectors from neighboring fluid states feed the player query; the call/provider arithmetic needed for the draft was compared, but no runtime trajectory was inferred or tested. Exact patch introduction remains unknown in this interval.
- This is assigned-slice coverage, not full-pair tick-graph closure or an independent audit. No tests, builds, game launches, wiki sources, product code or history were consulted.
