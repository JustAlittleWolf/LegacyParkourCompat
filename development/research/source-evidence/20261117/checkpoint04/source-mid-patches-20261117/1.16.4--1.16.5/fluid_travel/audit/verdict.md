# Independent audit: 1.16.4→1.16.5 ordinary-player fluid/travel slice

Verdict: **ACCEPT for the bounded source comparison.** This verdict is bound to report SHA-256 `3D99D173E66DCEFD6B6EE00028099B41CDFA9F81EFA1F8F9AE31BB4A79B2661F`.

## Verified evidence

The 1.16.4 source map is the `sources` object in `decompiled_minecraft/1.16.4/mojmap/.decompile-provenance.json`; the file hash is `1e04555c915527a398a2c7150679fb24d056f54bd680261e3c1dd55da821147c`. The 1.16.5 source map is `decompiled_minecraft/1.16.5/mojmap.sources.sha256`, hash `9499f2611d0e6dde37cb635f1a28a591416382d6bc188b37d5f99e2b2a20823b`; the version-ready marker binds `versionId=1.16.5`, `mapping=mojmap`, and that manifest. The 1.16.5 provenance sidecar hash is `b71df61eb775134119881915198f87c4337bae409e1f0de8d8d6c007e062902d`. The 1.16.4 provenance input identifies Mojmap mapping artifact hash `93f188a0038fec8c2402d78e7acca983680978d6a9237c1fab80f22caddb00fe`; 1.16.5 ready marker mapping identity is `7931ed6d723eceb1d621d05a76e10ddf643bf468c6ecf1c4ecf377bd72cf8b8c`.

All twelve listed Java source files were rehashed from disk and matched both endpoint source maps. Each file hash is equal across the two endpoints:

- `LocalPlayer.java`: `6011569e766bb1568609147be9aa14e9c08c51948e3d3a60fd066e848f6a8c2b`
- `Player.java`: `d2e26589bdb6a20dc914266db06aa48f50811efc792d6e63b3008c9199914960`
- `LivingEntity.java`: `b5d8a1a3c80f85d5d545b5a777e9e2a915dc002a7e31b5f5ad12bf7e285d7a88`
- `Entity.java`: `f9a9a073fe3105a0aa53d0f21ec72e59084e8d21a14c1cd3be75703865ee2666`
- `FluidTags.java`: `75d072cdc3e9073fc3fa481e4ccadd802e77a880ec776412f69bc865c4c78267`
- `FluidState.java`: `665b4024a32f659d92ce1259b25f529616e44b6f3df8a6aab4a4a2c57c7bc26d`
- `FlowingFluid.java`: `a85a5cd6625b1b4a047bd693399e1f78c2978ae207af927d4abfac3b34229552`
- `LavaFluid.java`: `6e98f387c7561bff3b66f2e86ce54bab54a8ff3337639cb106e5e6aaad8ada9b`
- `WaterFluid.java`: `d3f28e4be4be66a0e222eccad96c0c90d57f55380fdd39cf8e5840c871cc052b`
- `Fluids.java`: `7ee6840d600b06d6c803c8c741e39efdf5add797879f656a9c3abd9348fc7c64`
- `LiquidBlock.java`: `24f1cfbd036cca77ec99d855a028194ed39f583699ce91bc2a4c61957ff22fb7`
- `FluidTagsProvider.java`: `05b6b133203d2fa546d8c024ba68754a739e8c01f1466d24f2c30a5026f0585f`

The inspected dispatch and behavior match the report: `LocalPlayer.tick()` enters `Player.tick()`, then `LivingEntity.tick()` and `Entity.tick()`/`baseTick()`; fluid heights and flow are updated before the later local-player AI fluid-jump and travel paths. `Player.aiStep()` delegates to `LivingEntity.aiStep()`, virtual travel dispatch passes through `Player.travel(Vec3)` before `LivingEntity.travel(Vec3)`, and the flying gates on player fluid effects/pushing are as described. The compared `LivingEntity.aiStep/travel`, `Entity` fluid tracking/current scan, `FluidState`, `FlowingFluid`, fluid types/registration, `LiquidBlock`, and `FluidTagsProvider` bodies show no difference within this slice. The source tag provider's declarations do not prove packaged resource membership.

## Scope limit

Acceptance is limited to this ordinary-player fluid/travel dependency slice and its reported no-difference result. Packaged fluid-tag membership, remaining callbacks/providers/resources, complete tick/dependency coverage, and the full 1.16.4→1.16.5 pair audit remain open. No runtime, trajectory, or pair-freeze claim is made.
