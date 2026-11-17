# 1.16.4 → 1.16.5 ordinary-player fluid and travel dependency slice

Status: bounded source comparison; no fluid/travel source delta identified in the inspected slice. This is not a full-pair audit or freeze. Packaged fluid-tag membership, other callbacks, providers, resource inputs, and full tick/dependency closure remain open.

## Exact source identities

Both endpoints use Mojmap and the exact canonical source roots below. The 1.16.4 source map is the `sources` object of the mapping-local hidden provenance file; the 1.16.5 map is the root source manifest. These identities and file hashes were checked against disk bytes.

| Release | Source root | Source map / provenance identity | Mapping identity |
|---|---|---|---|
| 1.16.4 | `decompiled_minecraft/1.16.4/mojmap/` | `decompiled_minecraft/1.16.4/mojmap/.decompile-provenance.json#sources`, SHA-256 `1e04555c915527a398a2c7150679fb24d056f54bd680261e3c1dd55da821147c` | `93f188a0038fec8c2402d78e7acca983680978d6a9237c1fab80f22caddb00fe` |
| 1.16.5 | `decompiled_minecraft/1.16.5/mojmap/` | `decompiled_minecraft/1.16.5/mojmap.sources.sha256`, SHA-256 `9499f2611d0e6dde37cb635f1a28a591416382d6bc188b37d5f99e2b2a20823b`; provenance `decompiled_minecraft/1.16.5/mojmap.provenance.json`, SHA-256 `b71df61eb775134119881915198f87c4337bae409e1f0de8d8d6c007e062902d` | `7931ed6d723eceb1d621d05a76e10ddf643bf468c6ecf1c4ecf377bd72cf8b8c` |

## Reachable player path and exact comparison

The player tick reaches fluid contact and movement through the unchanged override chain: `LocalPlayer.tick()` (`net/minecraft/client/player/LocalPlayer.java:184`) calls `Player.tick()` (`Player.java:207`), then `LivingEntity.tick()` (`LivingEntity.java:2124`) and `Entity.tick()` (`Entity.java:354`). `Entity.tick()` calls virtual `baseTick()`; `LivingEntity.baseTick()` (`LivingEntity.java:289`) calls `super.baseTick()` (`:299`), which reaches `Entity.baseTick()` (`Entity.java:362`). `Entity.baseTick()` updates fluid state at `:380` by calling `updateInWaterStateAndDoFluidPushing()` and then updates eye-fluid state.

`Entity.updateInWaterStateAndDoFluidPushing()` (`Entity.java:957–962`) clears the stored fluid heights, runs the water-current update, and scans `FluidTags.LAVA` with the dimension-dependent push amount. The water update (`Entity.java:965–978`) scans `FluidTags.WATER` with `0.014`, marks water contact, resets fall distance, and clears fire under its existing gates. `updateFluidHeightAndDoFluidPushing(Tag<Fluid>, double)` (`Entity.java:2641–2706`) deflates the player box by `0.001`, visits block positions within the floored/ceiled bounds, checks the selected tag, samples each fluid surface using `FluidState.getHeight`, records maximum fluid height above the box minimum, and accumulates `FluidState.getFlow` where the entity is pushed by fluid. It normalizes the flow for non-player entities only; for a `Player`, it averages accumulated flows, applies the existing scale/minimum-vector rules, and adds the result to delta movement. `Player.isPushedByFluid()` (`Player.java:1789`) and `Player.isAffectedByFluids()` (`Player.java:1005`) both return `!abilities.flying`, so ordinary non-flying players receive this path while flying players are gated out.

The same stored contact state feeds movement. `Player.aiStep()` (`Player.java:483`) delegates to `LivingEntity.aiStep()`. Its fluid-jump branch (`LivingEntity.java:2427–2448`) reads the selected water/lava height and, for the appropriate `jumping`, contact, ground, threshold, and delay conditions, calls `jumpInLiquid`, which adds `0.04` vertical velocity (`LivingEntity.java:1902`). The following travel dispatch passes through the player override `Player.travel(Vec3)` (`Player.java:1387`), which applies its swimming/flying adjustments before delegating to `LivingEntity.travel(Vec3)` (`LivingEntity.java:1914`). The latter checks water contact and fluid eligibility for its water movement branch (`:1924`), or lava contact and eligibility for its lava branch (`:1959`); these branches apply relative input, move, and update velocity using fluid height and falling-adjusted movement. `LocalPlayer.aiStep()` also applies the existing shift-to-descend water input (`LocalPlayer.java:733–734`), through `LivingEntity.goDownInWater()` (`:1898`), only while water contact and the fluid-eligibility gate pass.

The dependency methods and fluid providers inspected here are byte-identical at the endpoints. `FluidState.getHeight` delegates to its fluid type and `FluidState.getFlow` delegates to `Fluid.getFlow`; `FlowingFluid.getFlow` computes horizontal height-gradient flow and the existing falling-fluid adjustment. `LavaFluid` and `WaterFluid` provide the corresponding source/flowing fluid identities and their unchanged behavior. `LiquidBlock` schedules liquid ticks for placement/neighbor updates; the compared class has no player entity-contact override. Source-side `FluidTagsProvider.addTags()` declares `WATER` membership as `Fluids.WATER` and `Fluids.FLOWING_WATER`, and `LAVA` membership as `Fluids.LAVA` and `Fluids.FLOWING_LAVA` in both releases. That source declaration does not prove the packaged resource payload or any datapack override.

| Relative source path | 1.16.4 SHA-256 | 1.16.5 SHA-256 |
|---|---|---|
| `net/minecraft/client/player/LocalPlayer.java` | `6011569e766bb1568609147be9aa14e9c08c51948e3d3a60fd066e848f6a8c2b` | same |
| `net/minecraft/world/entity/player/Player.java` | `d2e26589bdb6a20dc914266db06aa48f50811efc792d6e63b3008c9199914960` | same |
| `net/minecraft/world/entity/LivingEntity.java` | `b5d8a1a3c80f85d5d545b5a777e9e2a915dc002a7e31b5f5ad12bf7e285d7a88` | same |
| `net/minecraft/world/entity/Entity.java` | `f9a9a073fe3105a0aa53d0f21ec72e59084e8d21a14c1cd3be75703865ee2666` | same |
| `net/minecraft/tags/FluidTags.java` | `75d072cdc3e9073fc3fa481e4ccadd802e77a880ec776412f69bc865c4c78267` | same |
| `net/minecraft/world/level/material/FluidState.java` | `665b4024a32f659d92ce1259b25f529616e44b6f3df8a6aab4a4a2c57c7bc26d` | same |
| `net/minecraft/world/level/material/FlowingFluid.java` | `a85a5cd6625b1b4a047bd693399e1f78c2978ae207af927d4abfac3b34229552` | same |
| `net/minecraft/world/level/material/LavaFluid.java` | `6e98f387c7561bff3b66f2e86ce54bab54a8ff3337639cb106e5e6aaad8ada9b` | same |
| `net/minecraft/world/level/material/WaterFluid.java` | `d3f28e4be4be66a0e222eccad96c0c90d57f55380fdd39cf8e5840c871cc052b` | same |
| `net/minecraft/world/level/material/Fluids.java` | `7ee6840d600b06d6c803c8c741e39efdf5add797879f656a9c3abd9348fc7c64` | same |
| `net/minecraft/world/level/block/LiquidBlock.java` | `24f1cfbd036cca77ec99d855a028194ed39f583699ce91bc2a4c61957ff22fb7` | same |
| `net/minecraft/data/tags/FluidTagsProvider.java` | `05b6b133203d2fa546d8c024ba68754a739e8c01f1466d24f2c30a5026f0585f` | same |

## Bounded result and remaining gates

No source delta was found in this ordinary-player fluid-contact, fluid-height/current, fluid-jump, and water/lava-travel slice. The listed endpoint files are byte-identical and their SHA-256 values match each endpoint's exact source map. This result adds bounded coverage only; it does not prove packaged tag membership, close every fluid/provider/callback dependency, or freeze the 1.16.4→1.16.5 pair. No resource extraction, runtime, or trajectory comparison was performed.
