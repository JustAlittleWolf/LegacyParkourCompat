# Blind source audit: 1.20.3 to 1.20.4 fluid movement

## Bounded scope

Independent comparison of four fixed-profile, normal, non-riding local-player operations, conditional on equal effective fluid tags, block/fluid states, world queries, and other player inputs: (1) water/lava body admission and stored fluid height; (2) fluid-current push and player normalization; (3) eye-fluid state writer and tick order; (4) first ordinary water/lava movement update, including drag and gravity gates. Creative flight, Elytra, boats/riding, unrelated callbacks, solid geometry, damage, external data, and broader pair/runtime coverage were not reviewed.

## Exact source identity and integrity

Older side: `decompiled_minecraft/1.20.3/mojmap` (Mojmap). Its embedded `.decompile-provenance.json` binds the 1.20.3 metadata URL `https://piston-meta.mojang.com/v1/packages/86c3a686b59ee1bcd70febee4de962ec3e1a5d87/1.20.3.json` to SHA-256 `05e727aac55594b6014e9714ad38fa16af09f075daf5efa0f5743f2453fa8fc2`, the official client JAR URL ending `b178a327a96f2cf1c9f98a45e5588d654a3e4369/client.jar` to `bef21938224902dd925e2d89dcfe1d2540656d4cf7a5abd2b93e0402d135c655`, and Mojmap/client mappings `client.txt` URL ending `be76ecc174ea25580bdc9bf335481a5192d9f3b7/client.txt` to `ad03c803d866062909dd378ed5f1611687dc0ed0a76cc7fd57e968a99d641e5f`. The provenance lists 5,048 source hashes and records the repository's single-threaded Mojmap decompile pipeline with Vineflower 1.12.0.

Newer side: `decompiled_minecraft/1.20.4/mojmap`. `mojmap.ready.json` identifies version `1.20.4`, mapping `mojmap`, official client SHA-256 `9221ab461a491bf9661cd8e773a5e662aaa43d600fa7970b8c12bbfb0431b838`, and 5,048 source files. Its source-manifest SHA-256 is `fd3c8668483e5ff208c847474f6cbd3952a909c4aa602ed98b24e3d11a9602a1`; recomputing the manifest hash matched. `mojmap.provenance.json` records the exact Mojmap 1.20.4 run and Vineflower 1.12.0.

Each selected 1.20.3 source file matched its embedded provenance hash; each selected 1.20.4 file matched its source-manifest entry. All checks passed. Every file below has the same SHA-256 on both sides, making its complete decompiled contents byte-identical:

| Paired source path | SHA-256 on both sides |
| --- | --- |
| `net/minecraft/world/entity/Entity.java` | `07383522bff169938136638ef8c3244ca511b56ca4266913524f99f9821331b9` |
| `net/minecraft/world/entity/LivingEntity.java` | `f7bc53db24c1798f19f9bd6f6356c86d5e560e9c8e8aac60decaf15cc785e07d` |
| `net/minecraft/world/entity/player/Player.java` | `218da60bc4f8c9279f56eb87e2cf2c0af79562fbe3d818a408429c656efb95dd` |
| `net/minecraft/client/player/LocalPlayer.java` | `bb5cbfb03656a1866bb77c00431618befe792081223b35db4bd121ecbb151fd5` |
| `net/minecraft/world/level/material/FluidState.java` | `0d6d1d24deb58f056163acc3ae8976d2c7b3394aca2ae37fa43bc560c9d33b7c` |
| `net/minecraft/world/level/material/Fluid.java` | `50ea2dadff56228aff11147c6da9fab224c7241d3b54bc4025b4d38d3d470546` |
| `net/minecraft/world/level/material/FlowingFluid.java` | `0f44b1af25533dd8a1fbfec13b279739341db0c563568337a2825d3e46a1a5bb` |
| `net/minecraft/world/level/material/EmptyFluid.java` | `18dfeb0796d83f3be706d31ee3b13be356f23061b0ee70afca832433e8932868` |
| `net/minecraft/world/level/material/WaterFluid.java` | `3e10320356b11d415d8e4f3ea2b7fbb27e5bd8abdc45d524afb18b834e613e5f` |
| `net/minecraft/world/level/material/LavaFluid.java` | `9a409eccb9b112d615e2204a1058658afecabf1a18d79a5665f7d30bb79c9c74` |
| `net/minecraft/tags/FluidTags.java` | `df79262149bf0e684d877ca5d2bb87f02d8b66d55ed123d4c2e93bfa6462396f` |
| `net/minecraft/util/Mth.java` | `f211066a32f5fd2ff3605d0db66f79d7811d7c90ea205caf6b5ce957c0652075` |
| `net/minecraft/world/phys/AABB.java` | `13ab54ef7b11abbe41465580eb5cddb05a4bad3178995d4609c252d70b96cad9` |
| `net/minecraft/world/phys/Vec3.java` | `dc05337c94e64d5c137138ef98b7f10768a420af23bffb0812b68a8320c7c813` |
| `net/minecraft/core/BlockPos.java` | `a91e96745a49cdb2ec18be2ac7986071d25ed2de2a04c8f779ca41cfcccc29fc` |
| `net/minecraft/core/Holder.java` | `94b6ba63af642ee70f3360ec1384c403cd66c0b5e89859fd1b1976ae740445ef` |
| `net/minecraft/world/level/BlockGetter.java` | `d0a307889b2f18c9eab586baa92cf92f39a4df7918b17c631d5354a1e3a4edcb` |
| `net/minecraft/world/level/LevelReader.java` | `ec8c1f883dc1a71d2666f57cf7e124298272ae8b483770d32ecdb32c385b0bdd` |

## Separate frozen verdicts

1. **Water/lava body admission and stored height — no difference (bounded).** `Entity.updateInWaterStateAndDoFluidPushing` and `updateInWaterStateAndDoWaterCurrentPushing` (`Entity.java:1187-1209`) clear the cache, scan water first, then scan lava with the dimension-dependent push constant; water sets `wasTouchingWater`, while lava admission is returned separately. `updateFluidHeightAndDoFluidPushing` (`Entity.java:3037-3101`) rejects unloaded-chunk contact, deflates the box by `0.001`, uses the same floor/ceil bounds, checks matching tags and the fluid surface against the box bottom, stores maximum overlap, and writes it under the tag. `isInWater`, `isInLava`, and `getFluidHeight` (`Entity.java:1144-1146, 1291-1293, 3113-3115`) read these same fields. Equal inputs give identical admission and height.

2. **Current push and player normalization — no difference (bounded).** The scan uses `isPushedByFluid`, `FluidState.getFlow`, scales flow by the current maximum overlap below `0.4`, accumulates/averages by count, and applies the passed coefficient. It normalizes non-`Player` entities only, so a normal local player retains averaged flow magnitude. The same `0.003` horizontal-velocity checks and `0.0045000000000000005` minimum-push rule precede adding push (`Entity.java:3050-3098`). `Player.isPushedByFluid` is `!abilities.flying` (`Player.java:1803-1806`), so normal non-flying movement reaches this path. These bodies and all checked flow/math helper sources are byte-identical.

3. **Eye-fluid writer and order — no difference (bounded).** `Entity.baseTick` updates body fluid, then eyes, then swimming (`Entity.java:435-440`). The eye writer snapshots prior water-eye membership into `wasEyeInWater`, clears the set, samples `getEyeY() - 0.11111111F`, and adds the current fluid state's tags only when its surface is strictly above the sample (`Entity.java:1211-1223`). `isEyeInFluid` and `isUnderWater` read this state (`Entity.java:1173-1175, 1287-1289`). The boat exclusion is inactive for the assigned non-riding case. The local tick chain `LocalPlayer.tick` → `Player.tick` → `LivingEntity.tick` → `Entity.tick/baseTick` occurs at `LocalPlayer.java:188-191`, `Player.java:224-251`, `LivingEntity.java:2269-2271`, and `Entity.java:412-438`; every caller file is identical.

4. **First ordinary fluid movement update, drag, and gravity gates — no difference (bounded).** In locally controlled `LivingEntity.travel` (`LivingEntity.java:2035-2098`), base gravity is `0.08`, or `0.01` for descending slow-falling motion. Water is selected first when touching water, affected by fluids, and unable to stand on the block-position fluid. It applies sprint/water slowdown, depth-strider interpolation (capped at 3 and halved airborne), dolphins-grace drag, relative input, movement, vertical/horizontal drag, falling adjustment, and the horizontal-collision escape lift. The alternative lava path applies input/movement, chooses shallow-fluid drag plus falling adjustment at/below the jump threshold or full-vector `0.5` scaling above it, adds quarter gravity unless no-gravity, and applies the escape lift. `getFluidFallingAdjustedMovement` (`LivingEntity.java:2215-2228`) keeps the same no-gravity/sprinting bypass and `0.003` rounding gate. Before delegating, `Player.travel` applies the same swimming vertical-velocity prelude when swimming and not riding (`Player.java:1451-1461`); the inherited fluid path is used in its non-flying branch (`Player.java:1463-1472`). The compared `Player`, `LivingEntity`, and fluid helper files are byte-identical, preserving gates, order, casts, constants, and writes for equal inputs.

## Acceptance boundary

These are four independently accepted no-difference verdicts for the named operations under identical effective tag and world inputs, supported by exact paired-source hashes and call-path inspection. This is not complete pair inventory, intermediate-patch coverage, external tag-data review, product acceptance, or runtime parity. No movement loop, callback inventory, tests, build, or game launch was performed.