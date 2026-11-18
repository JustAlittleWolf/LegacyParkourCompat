# 26.1 to 26.1.1: water and lava player interaction

## Bounded verdict

No source difference found in the assigned ordinary, non-riding local-player path for (1) water/lava contact admission and stored height, (2) fluid-current sampling and push, or (3) the first normal water/lava travel step, including drag and gravity gates. With identical effective world, resources, player state and tick inputs, these paths are equivalent across 26.1 and 26.1.1: every consumed source file below has the same SHA-256 in both releases. No positive finding is warranted for this bounded slice.

This is a bounded source verdict, not a full-pair coverage freeze, intermediate patch boundary, runtime-parity result, or implementation recommendation.

## Exact source identities

Both ready markers identify `versionId` 26.1 / 26.1.1, `mapping` `unobfuscated`, `status` `ready`, and 6,882 source files. The paired provenance records identify official client artifacts using version metadata IDs 26.1 / 26.1.1, with unobfuscated published sources (`mappingFile`: not applicable). Both were generated in the same recorded exact run (`repositoryCommit` `fcd113c3fafcfde91d5cb5c8308b77292c601f9d`; Vineflower 1.12.0; Java 25).

| Release | Official client SHA-1 | Official client SHA-256 | Source-manifest SHA-256 |
| --- | --- | --- | --- |
| 26.1 | `191771837687b766537a8c4607cb6fad79c533a1` | `bc6194c61566b587f196090d730698f2191170f7580adb143e4ff839939d3840` | `b77289c135ac6e12aa35e75177cea52d968f49d49e1ec745e510b1a786c9aeba` |
| 26.1.1 | `377031a9e733ba8ab4d355959a8f6fb8eb707556` | `8c109be6177f40d10d0afd820b06542f9609597874242b96e16c5b0dc9e1fef3` | `88afdfb8264842713f8cce86c0bf88e95c2c991ee706349d6ed8b6c22d52fcb3` |

For each release, the SHA-256 of `unobfuscated.sources.sha256` equals the ready marker's `sourceManifestSha256`. Every Java hash below was independently calculated from the canonical source tree and matched the path entry in that release's `unobfuscated.sources.sha256`; each path's 26.1 and 26.1.1 values are equal.

| Canonical source path | SHA-256 in both releases |
| --- | --- |
| `net/minecraft/client/player/LocalPlayer.java` | `433fd995ad317af0f6ef0e50c1e8e3483cb8f00e0e327d4edf27a4dd99666ebe` |
| `net/minecraft/world/entity/player/Player.java` | `44cf28e0c64e78d39fd13368e9991381dbebab67029070cb9ddc43f09d45d14d` |
| `net/minecraft/world/entity/Entity.java` | `8b83b1f036aabbd13d990897c540c993f7120f02955486cfcf229517d4097ccf` |
| `net/minecraft/world/entity/EntityFluidInteraction.java` | `5264ff4f1fddebc3fa9d63ad2edbe2eaf617392ff946a867a6817a78b478ee62` |
| `net/minecraft/world/entity/LivingEntity.java` | `c3b64de8dbaba8ad8a7ccd4f33255346d8206e66f12ca91260971bf5e5ac93bd` |
| `net/minecraft/world/level/material/FluidState.java` | `e35608d5775013c67653ea350309b5150a97f36aa65c27f1c6247ae2aa840f18` |
| `net/minecraft/world/level/material/Fluid.java` | `e7759a85dea543266b734086b2061332bc9f62ba3330be0f203f1f8baaa2c80d` |
| `net/minecraft/world/level/material/FlowingFluid.java` | `f8c0c4430e81efdf39b221968272ed8e3a4d030fa5192e04e04d34c9fbd37b28` |
| `net/minecraft/world/level/material/WaterFluid.java` | `14cb3d49ef1db28fdab367cd6a4f196f20b7886b8768153c6e82906e9d25f0ad` |
| `net/minecraft/world/level/material/LavaFluid.java` | `423ff44a57c316f58c21a66903a4ef2c1b9c3d26316bdc4d301727575f93703f` |

## Ordered path and comparison evidence

- `client/player/LocalPlayer.java:108,227-230` identifies the local client player and delegates its tick to `super.tick()`. `world/entity/player/Player.java:125,231-259` delegates to living/entity ticking. `world/entity/Entity.java:499-525` reaches `baseTick()` and samples fluid interaction before the later movement step. These caller files have equal hashes in both releases.
- Contact admission and stored height are in `world/entity/Entity.java:1558-1582` and `world/entity/EntityFluidInteraction.java:32-83,139-145`. The scan resets trackers; bounds use floor(min) and ceil(max)-1; nonempty states are admitted unless `fluidTop < box.minY`; per-tag height stores `max(fluidTop - entity bounding-box minY, prior height)`. `EntityFluidInteraction.java:153-168` resets height/current and accumulates per-fluid flow. The complete files have equal hashes across the pair.
- For a non-flying player, `Player.java:1672-1675` makes `isPushedByFluid()` true. `Entity.java:1558-1581` therefore samples current with `ignoreCurrent == false`, stores `wasTouchingWater`, then applies current for admitted water and lava. Water scale is `0.014`; lava uses the dimension's fast-lava value to choose `0.007` or `0.0023333333333333335`. `EntityFluidInteraction.java:171-188` rejects zero-count or near-zero accumulated current, averages the accumulated vector for `Player`, scales it, applies the minimum impulse rule when horizontal motion is below `0.003`, then adds it to movement. This order and math are byte-identical in both versions.
- The first normal fluid travel path is `Player.java:1381-1402` into `LivingEntity.java:2392-2408`. `shouldTravelInFluid` requires water or lava contact, fluid susceptibility, and failure of `canStandOnFluid`. The fluid branch captures falling state, old Y and effective gravity before dispatch (`LivingEntity.java:2455-2465`). Water then performs relative movement and collision movement, applies horizontal collision/climbable Y override, multiplies by slowdown and `0.8F` vertical drag, and applies falling adjustment (`2467-2494`). Lava moves first, then uses the `fluidHeight <= fluidJumpThreshold` gate to choose `(0.5, 0.8F, 0.5)` plus falling adjustment versus `0.5` uniform drag, then conditionally subtracts `baseGravity / 4.0` (`2496-2511`). The falling adjustment gate and epsilon are at `2635-2648`. `Player.java` and `LivingEntity.java` are byte-identical across the releases.
- Fluid height and flow producers are delegated by `FluidState.java:49-50,95-96` to the fluid type; relevant implementations are in `Fluid.java`, `FlowingFluid.java`, `WaterFluid.java` and `LavaFluid.java`. All are hash-identical across the pair. Under the stipulated same effective block/fluid/resource inputs, they introduce no unexamined source delta in this bounded producer path.

## Residual scope

This assignment does not review solid geometry/support, portals, creative or Elytra branches, damage, other player movement operations, other source files or resource changes. It does not establish complete 26.1--26.1.1 pair coverage or an exact intermediate introduction boundary. No tests, builds, game launches, product or Git evidence, wiki material, network lookups, or new decompilation were used.