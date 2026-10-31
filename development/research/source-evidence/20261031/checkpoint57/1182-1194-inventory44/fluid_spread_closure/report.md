# Fluid spread and movement consumer closure: 1.18.2–1.19.4

Scope: exact Mojmap sources only, adjacent edges 1.18.2→1.19→1.19.1→1.19.2→1.19.3→1.19.4. This closes the assigned fluid spread/tick/provider slice. It is not a pair freeze or a complete movement inventory.

## Source identity and integrity

For each version, the `mojmap.ready.json` version/mapping/status, `sourceFileCount`, and `sourceManifestSha256` matched the supplied identity; the manifest line count matched `sourceFileCount`; and every manifested file was present under that version's `mojmap/` directory and matched its SHA-256. All mismatches: 0.

| Version | Ready source files = manifest entries | Manifest SHA-256 (ready and recomputed) |
|---|---:|---|
| 1.18.2 | 4,236 | `aea0cb9c6fc8f7a46f0eb82b0388ad58a4659f513be6c0a2be06c0df0c1eb07a` |
| 1.19 | 4,430 | `78bf3f9d5b4b5437240dd0707fa7a9ff56e6d1e267bd8edc13adc8313b535e26` |
| 1.19.1 | 4,480 | `25a8e35111971be21a85fefd567ada685098f45e35e431163afdbe0fa66dfde1` |
| 1.19.2 | 4,480 | `95bc354ac219b9fe1ca8bf45053edad0119a75323d576902d59616a11647aac2` |
| 1.19.3 | 4,618 | `6f1cc6d07ab3902a7ea25a3f8817504723be70d82e97f541cffda95ea2c1c843` |
| 1.19.4 | 4,740 | `6286e325371e085dfee1ab7987e66d4b6d1a984a49b6ec8ea730045cd24cecb3` |

## Adjacent-edge coverage and results

All paths below are relative to `mojmap/`; file SHA values are the corresponding exact entries in each version's `mojmap.sources.sha256`. Method ranges are one-based inclusive. Same-body statements mean exact method-source text equality; harmless source line movement is shown where relevant.

### 1.18.2 → 1.19

Changed fluid members were type/API substitutions from `java.util.Random` to `RandomSource`, not changes to their control flow or fluid math:

| File, older→newer member lines | Source SHA-256, older→newer | Change |
|---|---|---|
| `net/minecraft/world/level/material/Fluid.java`: `animateTick` 53–54→53–54; `randomTick` 59–60→59–60 | `441ddda17005a8d40e2a9b1cc7a6a449d811cb25c9df2628133fec5e4d6ffb62` → `ac4afbf490d755481e7660fce11d364fe720fab40fc02231c5eb685ea2b5971d` | Parameter type only. `tick`, `getFlow`, `getHeight`, `getShape` declarations unchanged. |
| `.../material/WaterFluid.java`: `animateTick` 44–63→44–63 | `4ff811fb14b0ae0f67a9a9068b286bc0dd7667ac0bc6a4964507b63d538dda50` → `9d305751d39633ee519e260be184208b20cdbb3422acadb84f8871722c1a383f` | Parameter type only; particle/sound path, not player response. |
| `.../material/LavaFluid.java`: `animateTick` 46–72→46–72; `randomTick` 75–110→75–110 | `117b1ae75b42432963928458503c87649a351f6c577a2b417075a8cb99c44f50` → `0f3fab21b95d66f9ecbe216b8bfc888aa033718b17e273de0e0f0be99bd1fc6c` | Parameter type only; animate is visual and random tick is world/random production. `canConvertToSource` remained false. |
| `.../material/FluidState.java`: `animateTick` 79–81→79–81; `randomTick` 87–89→87–89 | `a39d52cdaebcff391abd094d17c0d8c190df27af7aeb5ac3810370eca017c459` → `90c3a2be5f5009ac9644f8037734628bbefc49f9f1ff3a08898e8120081be73c` | Parameter type forwarding only; `tick`, `getHeight`, `getFlow`, `getShape` bodies unchanged. |
| `net/minecraft/world/level/block/LiquidBlock.java`: `randomTick` 67–69→67–69 | `b9def5bc131dd922d270a902c3bcda4b4875de5c38697a41ca5577f021cc2569` → `c4dd00de721b3edb45b47832c2bf68e22ee4d065b984f179a206a33eeda61f02` | Parameter forwarding type only. |

`FlowingFluid` is byte-identical on both sides (`bbfb661b524ac92f74579cd4b61c1a25df00ecc4bfe775a5516f7e4a7c8768f3`). Its `getFlow` 53–98, `spread` 116–131, `spreadToSides` 133–152, `getNewLiquid` 154–188, `getSpread` 334–375, `tick` 406–423, `getHeight` 434–436, and `getShape` 447–451 are unchanged. `WaterFluid.canConvertToSource` remains `return true` (72–74); `LavaFluid.canConvertToSource` remains `return false` (189–191).

The movement consumers were also checked. `Entity.updateInWaterStateAndDoFluidPushing` (1047–1053) and `updateFluidHeightAndDoFluidPushing` (2769–2834), plus `LivingEntity.canStandOnFluid` (2005–2007) and `travel` (2009–2145), have identical method source across this edge. `Entity.java` SHA: `2228fdaca5793171cbd94038306d571a6ada78ca96f5734efb4cada5b744c10a` → `3a8ce3090d29aad1bbfeca31de300d34b42a7e902f0e78b13efd5653cd588766`; `LivingEntity.java` SHA: `db4168d531caf18f22e3fefd073365e776da4075ce01452bb9f7671d9b458782` → `00c591dd2cac49ee5ccad8705f7dd879ad5d29f0360b98742166294989b3531c`.

`Fluids.java` registrations and iteration (lines 6–18), `FluidTags.java` tag IDs, `Blocks.WATER`/`Blocks.LAVA` references, and the water/lava collision predicate are covered below; the registration identifiers did not change. `Blocks.java` does replace block property `.noDrops()` with `.noLootTable()` in 1.19, unrelated to movement/collision.

### 1.19 → 1.19.1

No fluid implementation, provider, tag-provider, or liquid collision method changed. `Fluid.java`, `FlowingFluid.java`, `WaterFluid.java`, `LavaFluid.java`, `FluidState.java`, `Fluids.java`, `LiquidBlock.java`, `FluidTags.java`, and `FluidTagsProvider.java` are identical. `Entity.updateFluidHeightAndDoFluidPushing` (2797–2862), `Entity.updateInWaterStateAndDoFluidPushing` (1066–1072), `LivingEntity.canStandOnFluid` (2038–2040) and `travel` (2042–2178) are identical. Fluid consumer file identities: `Entity.java` `3a8ce3090d29aad1bbfeca31de300d34b42a7e902f0e78b13efd5653cd588766` → `759de9cded43bd882afcf5b7023bcf804d92419acb656b493f3b490c83eb18b6`; `LivingEntity.java` `00c591dd2cac49ee5ccad8705f7dd879ad5d29f0360b98742166294989b3531c` → `fee2df5155449098556a138d2530d06b7a35979e43c6aa0ae1fffd6251853e77` (the enclosing files changed elsewhere).

### 1.19.1 → 1.19.2

No fluid implementation, provider, tag-provider, registration, or liquid collision member changed. All fluid and liquid source files named in the previous edge are byte-identical. The same movement consumer methods are byte-identical. `Entity.java` and `LivingEntity.java` are also byte-identical across this edge (`759de9cd…eb18b6`, `fee2df51…53e77` on both sides).

### 1.19.2 → 1.19.3

The fluid update makes the source-fluid conversion gate configurable and narrows world-reading/writing API parameter types to `Level`; it does not alter the flow/height/shape math:

| File, older→newer member lines | Source SHA-256, older→newer | Difference |
|---|---|---|
| `net/minecraft/world/level/material/FlowingFluid.java`: `spread` 116–131→116–131; `spreadToSides` 133–152→133–152; `getNewLiquid` 154–188→154–188; `getSpread` 334–375→334–375; `canConvertToSource` abstract declaration 235→235 | `bbfb661b524ac92f74579cd4b61c1a25df00ecc4bfe775a5516f7e4a7c8768f3` → `d84ceb3c0b83da25fb68599e16cc2298106e745a9ef0c9f32f616d097a8ec56c` | `LevelAccessor`/`LevelReader` parameters become `Level`; `getNewLiquid` calls `canConvertToSource(level)`. Other operation order and calculations are unchanged. |
| `.../material/WaterFluid.java`: `canConvertToSource` 72–74→73–75 | `9d305751d39633ee519e260be184208b20cdbb3422acadb84f8871722c1a383f` → `3e10320356b11d415d8e4f3ea2b7fbb27e5bd8abdc45d524afb18b834e613e5f` | Returns `level.getGameRules().getBoolean(RULE_WATER_SOURCE_CONVERSION)` instead of constant `true`. |
| `.../material/LavaFluid.java`: `canConvertToSource` 189–191→189–191 | `0f3fab21b95d66f9ecbe216b8bfc888aa033718b17e273de0e0f0be99bd1fc6c` → `1d239a6e0c99aee386b5dfb0244a29f4a48689808f9be64dda89c9a63b9fc6da` | Returns the `RULE_LAVA_SOURCE_CONVERSION` value instead of constant `false`. |
| `.../material/Fluid.java`: intrusive holder line 27; `FluidState.java`: codec line 25; `Fluids.java`: registration/iteration lines 13–19; `net/minecraft/tags/FluidTags.java`: `create` line 14 | `ac4afbf490d755481e7660fce11d364fe720fab40fc02231c5eb685ea2b5971d` → `50ea2dadff56228aff11147c6da9fab224c7241d3b54bc4025b4d38d3d470546`; `90c3a2be5f5009ac9644f8037734628bbefc49f9f1ff3a08898e8120081be73c` → `0d6d1d24deb58f056163acc3ae8976d2c7b3394aca2ae37fa43bc560c9d33b7c`; `4888bd1fa4cf05bf79721ecd4d37f1c23837efd0a7b9289bfe880cdee8a9a411` → `913cb5d937af92a7f90f7dc233a3d02c05f75e70a2a740300b2f26eedcbed931`; `c37eaf0dab3b4bcb3af89e3d24808a9edf9764127fa3c7876ec64cd5d501885e` → `df79262149bf0e684d877ca5d2bb87f02d8b66d55ed123d4c2e93bfa6462396f` | Registry API migration: `Registry.FLUID`/`FLUID_REGISTRY` references become `BuiltInRegistries.FLUID`/`Registries.FLUID`; identifiers and fluid objects remain the same. |

`FlowingFluid.getFlow`, `tick`, `getHeight`, and `getShape` remain operation-identical (line starts 53, 406, 434, 447 on both sides). `FluidState` delegates `getFlow`, `getHeight`, and `getShape` unchanged. `FluidTagsProvider.addTags` still adds exactly `WATER,FLOWING_WATER` to `FluidTags.WATER` and `LAVA,FLOWING_LAVA` to `FluidTags.LAVA`; its API changes from `DataGenerator`/`Registry.FLUID` to `PackOutput`/`Registries.FLUID` and `HolderLookup.Provider` (provider file SHA `14cc395eb388446cdee2373c88f479422eeeff527f7c8fca184f95943b96e9c5` → `ab9fa0839166ea355bd34ca6c3f789415ee5dddc93779d97c2d6f6d0a931d2b7`). Its `addTags` method remains the same two assignments (old lines 15–18; new lines 17–20).

The new source-conversion gates are world fluid production only. In 1.19.3 `GameRules.java` lines 162–166 define water conversion `true` and lava conversion `false` by default (`9f7c0b5ff3ebc46cac4275e6c856610e00e796f9cf5368d9b380ac0e478e1bae`); 1.19.4 repeats these defaults at lines 165–169 (`a9099eaa1132ffd8a8fc428114727977d15489e9ba42905af729ddeb8c0e3970`). The check can alter whether a flow tick writes newly converted source-fluid states. That is excluded world-state production and cannot create a movement difference while fluid block/fluid states are held equal.

The movement path was checked at `Entity.updateFluidHeightAndDoFluidPushing` 2797–2862 (old) / 2826–2891 (new), `Entity.updateInWaterStateAndDoFluidPushing` 1061–1067 / 1066–1072, and `LivingEntity.canStandOnFluid` 2038–2040 / 2048–2050. Their exact method-source-slice SHA-256 values (declaration through closing brace) are respectively `849d5e3809dbf515c853736b12b64b1ca6ba1bd66bce2f88ed67fd893f810106`, `490ad26ee759dd2c4fcb0ab32cd2a487f579c71416b19137a3cc53a727d61774`, and `92450e531cf51cb68564e0b824073835191b87061705e23e9c658c58f429ce61`; each matches on both sides. The enclosing `LivingEntity.travel` differs at 2042–2178 / 2052–2185 only in its fall-flying slow-fall/fall-distance segment; water and lava branches, including checks of `isInWater`, `isInLava`, `isAffectedByFluids` and `canStandOnFluid`, are unchanged. `Entity.java` SHA: `759de9cded43bd882afcf5b7023bcf804d92419acb656b493f3b490c83eb18b6` → `8c35bdef3bf2de7b70ac76ad767ba2c194305e0dda2e03fdf6544983667adc32`; `LivingEntity.java` SHA: `fee2df5155449098556a138d2530d06b7a35979e43c6aa0ae1fffd6251853e77` → `2b8befdc406176e01672175451dd480b14da0b52bfe11a982d368909c05e73db`.

### 1.19.3 → 1.19.4

`Fluid.java`, `FlowingFluid.java`, `WaterFluid.java`, `LavaFluid.java`, `FluidState.java`, `Fluids.java`, `LiquidBlock.java`, `FluidTags.java`, `FluidTagsProvider.java`, and `EntityCollisionContext.java` are byte-identical. Their movement-facing fluid calculations and collision queries therefore carry forward unchanged. `Entity.updateFluidHeightAndDoFluidPushing` is 2826–2891→2887–2952 with identical method text; `Entity.updateInWaterStateAndDoFluidPushing` 1066–1072→1072–1078 and `LivingEntity.canStandOnFluid` 2048–2050→1979–1981 are identical. `LivingEntity.travel` has unrelated surrounding changes: its initial AI/control gate and fall-flying damage API are changed, but the water/lava movement branches remain exact-body equal. No changed fluid operation is exposed by these unrelated enclosing-method changes. Entity source SHA `8c35bdef3bf2de7b70ac76ad767ba2c194305e0dda2e03fdf6544983667adc32` → `3667fee610cbc5f58012e3a8fb8d6c4849f649fb7fe5300595158112d8b4b58b`; LivingEntity SHA `2b8befdc406176e01672175451dd480b14da0b52bfe11a982d368909c05e73db` → `c8d91af61f87aaa1666de79696d7cd9d4a8212d05f873bdaf28d7bb2926bf165`.

## Registration, state providers, collision, and player reachability

- `Fluids.java` registers the same five IDs in each version: `empty`, `flowing_water`, `water`, `flowing_lava`, `lava` (1.18.2–1.19.2 lines 6–18; 1.19.3–1.19.4 lines 7–19). Only the 1.19.3 registry reference changes. `Blocks.WATER` and `Blocks.LAVA` remain `LiquidBlock(Fluids.WATER/LAVA, ...)` in every release (1.18.2 lines 120–127; 1.19–1.19.2 lines 134–141; 1.19.3 lines 157–164; 1.19.4 lines 178–185). The 1.18.2→1.19 `.noDrops()`→`.noLootTable()` rewrite does not alter collision or state mapping.
- Source-fluid state mapping is `LiquidBlock.getFluidState` (82–85), unchanged byte-for-byte across the six releases (`LiquidBlock.java` SHAs: 1.18.2 `b9def5bc131dd922d270a902c3bcda4b4875de5c38697a41ca5577f021cc2569`; 1.19–1.19.4 `c4dd00de721b3edb45b47832c2bf68e22ee4d065b984f179a206a33eeda61f02`). Its water/lava collision method (55–59) is byte-identical. `EntityCollisionContext.canStandOnFluid` (52–54) is also byte-identical (`fa505d477deaeed144816a3ec08512357ee178dc8769534b3a7f59e9a3299587` each version). It delegates to `LivingEntity.canStandOnFluid`; the vanilla implementation returns false and `Player` does not override it. Thus there is no changing fluid-top support surface for a player here.
- In the local player tick path, `Entity.updateFluidHeightAndDoFluidPushing` reads fluid tag membership, `FluidState.getHeight`, and `FluidState.getFlow`, then adds the averaged flow vector to velocity. This exact method body is unchanged at all six versions (body SHA-256 `849d5e3809dbf515…`, line locations are recorded per edge above). `LivingEntity.travel`'s water/lava acceleration, damping, falling adjustment and collision responses are unchanged across the checked edges; the separately noted fall-flying/control changes do not edit those branches. Fluid flow/height/shape provider methods themselves are unchanged aside from the 1.19.3 Level parameter narrowing.
- Shape closure: `FlowingFluid.getShape`/`FluidState.getShape` are unchanged, and vanilla `LiquidBlock.getCollisionShape` uses its stable top shape only when the collision context says the entity may stand on a different fluid, not the flowing-fluid `VoxelShape` as player body collision. Both the block method and context predicate are byte-identical. This slice found no changed fluid collision/support shape reaching the player.

The exact consumer/shape anchors and method-slice hashes across the whole interval are:

| Member | Version line locations | Identical method-source-slice SHA-256 |
|---|---|---|
| `Entity.updateInWaterStateAndDoFluidPushing` | 1.18.2 1047–1053; 1.19 1066–1072; 1.19.1–1.19.2 1061–1067; 1.19.3 1066–1072; 1.19.4 1072–1078 | `490ad26ee759dd2c4fcb0ab32cd2a487f579c71416b19137a3cc53a727d61774` |
| `Entity.updateFluidHeightAndDoFluidPushing` | 1.18.2 2769–2834; 1.19 2797–2862; 1.19.1–1.19.2 2797–2862; 1.19.3 2826–2891; 1.19.4 2887–2952 | `849d5e3809dbf515c853736b12b64b1ca6ba1bd66bce2f88ed67fd893f810106` |
| `LivingEntity.canStandOnFluid` | 1.18.2 2005–2007; 1.19 2038–2040; 1.19.1–1.19.2 2038–2040; 1.19.3 2048–2050; 1.19.4 1979–1981 | `92450e531cf51cb68564e0b824073835191b87061705e23e9c658c58f429ce61` |
| `FlowingFluid.getFlow` | 53–98 all versions | `72244f6ec787e65f54b04f2dfb53d2ec37c06df58cc37cece046414c3a9df1e5` |
| `FlowingFluid.getHeight` / `getShape` | 434–436 / 447–451 all versions | `a526dc80720749ab4c28e6155141f2e04a4a2319809aef32f7b33236d268deca` / `7d71a154a58d081c37c48dbd338c781ff00b7330dedb684e16301fd21ef637e0` |
| `LiquidBlock.getCollisionShape` / `getFluidState` | 55–59 / 82–85 all versions | `ebdf5b86812386f346b28eaebb427275694b0b4d9b1e82b497c197aa3fb877d4` / `89fa28d741305837036495bacf5304cc0a26b64e23bb328416e81c1b641db5ed` |
| `EntityCollisionContext.canStandOnFluid` | 52–54 all versions | `02bcef266ff881958f870f0f72b6fe9b00f504fe0019b23d9eedc83e471d7f61` |

`Player.java`, `LocalPlayer.java`, and `AbstractClientPlayer.java` contain no `canStandOnFluid` declaration on any version (the search covered the full respective source files). Their whole-file SHAs, grouped by identical versions: `Player.java` 1.18.2 `bf639c1962ff90d69e4569b2b18f6fcf57ac46ef80b19686f0fbc1687fca744a`, 1.19 `4b5d3d71ac1c4b64efdeb1ad86a4b6f84afffe210e272850e620339430942de1`, 1.19.1–1.19.2 `155c5fcfba322d968f3180383e7d283ddeb5edee4e04314906310d4e3ce0ccc1`, 1.19.3 `02e64e197aec3f4f2a8abc9c5ce4545121ac5bcbbc71592ce478601a0db17203`, 1.19.4 `5e4436afccb361156f8184e7a5cfd91d5b12dcfe8f5937b4ac23dd3edda737a2`; `LocalPlayer.java` 1.18.2 `99c2d18bcd23243afb8f95c5bafb21fb0be7ea04aacbb14fcf7be7ced2c9c095`, 1.19 `c5f346728f551d278e88b457e54e539885e0d8e549ed7394887d090f28224805`, 1.19.1–1.19.2 `36ae4aabd609b457fffb7a8b14abb50db9ac775857dde1774c0c68a8cf50deef`, 1.19.3 `64b670ee323d195b3928fb8ea629c26d2560e75a17379a12c376a7bc686d5479`, 1.19.4 `8e7da18f42d09fbb994f522c2b0e65fcb2bb83cabb21024360299d44d9674c58`; `AbstractClientPlayer.java` 1.18.2 `b2f91b82f681fe148adacefeedb79e65bcb19fb134a977b02367fd9350c54317`, 1.19 `d758c799950c9c17bc0868b27aaa34612607f6835285955925c79a3ae7fd5f84`, 1.19.1–1.19.2 `e0fd7cd938b9d7111819a02ce991cd71dd1b93d3d8b11c500919d9a12ec1ebdf`, 1.19.3 `ddb93c39a10695c793eaad8d6a1bbf5b63e57fad57850c055896109079320878`, 1.19.4 `393b88eb4f41a84528f584311bb7aefc02adbf474c2744f3555acc0663c85dbf`.

## Disposition and remaining gates

No movement finding is warranted. The 1.19.3 source-conversion gamerules and RandomSource API change can influence world/visual/random fluid production; under the requested equal fixed block/fluid state assumption, neither changes the player's fluid height/flow response. Registry relocation and the 1.19.3 data-provider API migration preserve the registered IDs and generated vanilla water/lava tag members.

## Versioned source identity for the covered slice

Grouped rows mean the exact same whole-file SHA-256 applies to every listed version; these identities make the unchanged intermediate edges explicit.

| Source file | Versions : whole-file SHA-256 |
|---|---|
| `world/level/material/Fluid.java` | 1.18.2: `441ddda17005a8d40e2a9b1cc7a6a449d811cb25c9df2628133fec5e4d6ffb62`; 1.19–1.19.2: `ac4afbf490d755481e7660fce11d364fe720fab40fc02231c5eb685ea2b5971d`; 1.19.3–1.19.4: `50ea2dadff56228aff11147c6da9fab224c7241d3b54bc4025b4d38d3d470546` |
| `world/level/material/FlowingFluid.java` | 1.18.2–1.19.2: `bbfb661b524ac92f74579cd4b61c1a25df00ecc4bfe775a5516f7e4a7c8768f3`; 1.19.3–1.19.4: `d84ceb3c0b83da25fb68599e16cc2298106e745a9ef0c9f32f616d097a8ec56c` |
| `world/level/material/WaterFluid.java` | 1.18.2: `4ff811fb14b0ae0f67a9a9068b286bc0dd7667ac0bc6a4964507b63d538dda50`; 1.19–1.19.2: `9d305751d39633ee519e260be184208b20cdbb3422acadb84f8871722c1a383f`; 1.19.3–1.19.4: `3e10320356b11d415d8e4f3ea2b7fbb27e5bd8abdc45d524afb18b834e613e5f` |
| `world/level/material/LavaFluid.java` | 1.18.2: `117b1ae75b42432963928458503c87649a351f6c577a2b417075a8cb99c44f50`; 1.19–1.19.2: `0f3fab21b95d66f9ecbe216b8bfc888aa033718b17e273de0e0f0be99bd1fc6c`; 1.19.3–1.19.4: `1d239a6e0c99aee386b5dfb0244a29f4a48689808f9be64dda89c9a63b9fc6da` |
| `world/level/material/FluidState.java` | 1.18.2: `a39d52cdaebcff391abd094d17c0d8c190df27af7aeb5ac3810370eca017c459`; 1.19–1.19.2: `90c3a2be5f5009ac9644f8037734628bbefc49f9f1ff3a08898e8120081be73c`; 1.19.3–1.19.4: `0d6d1d24deb58f056163acc3ae8976d2c7b3394aca2ae37fa43bc560c9d33b7c` |
| `world/level/material/Fluids.java` | 1.18.2–1.19.2: `4888bd1fa4cf05bf79721ecd4d37f1c23837efd0a7b9289bfe880cdee8a9a411`; 1.19.3–1.19.4: `913cb5d937af92a7f90f7dc233a3d02c05f75e70a2a740300b2f26eedcbed931` |
| `world/level/block/LiquidBlock.java` | 1.18.2: `b9def5bc131dd922d270a902c3bcda4b4875de5c38697a41ca5577f021cc2569`; 1.19–1.19.4: `c4dd00de721b3edb45b47832c2bf68e22ee4d065b984f179a206a33eeda61f02` |
| `data/tags/FluidTagsProvider.java` | 1.18.2: `10e8a8e8847259a30544dcc1088ab1bb7aa45455227a678ea52e54b6742b8a52`; 1.19–1.19.2: `14cc395eb388446cdee2373c88f479422eeeff527f7c8fca184f95943b96e9c5`; 1.19.3–1.19.4: `ab9fa0839166ea355bd34ca6c3f789415ee5dddc93779d97c2d6f6d0a931d2b7` |
| `world/entity/Entity.java` | 1.18.2: `2228fdaca5793171cbd94038306d571a6ada78ca96f5734efb4cada5b744c10a`; 1.19: `3a8ce3090d29aad1bbfeca31de300d34b42a7e902f0e78b13efd5653cd588766`; 1.19.1–1.19.2: `759de9cded43bd882afcf5b7023bcf804d92419acb656b493f3b490c83eb18b6`; 1.19.3: `8c35bdef3bf2de7b70ac76ad767ba2c194305e0dda2e03fdf6544983667adc32`; 1.19.4: `3667fee610cbc5f58012e3a8fb8d6c4849f649fb7fe5300595158112d8b4b58b` |
| `world/entity/LivingEntity.java` | 1.18.2: `db4168d531caf18f22e3fefd073365e776da4075ce01452bb9f7671d9b458782`; 1.19: `00c591dd2cac49ee5ccad8705f7dd879ad5d29f0360b98742166294989b3531c`; 1.19.1–1.19.2: `fee2df5155449098556a138d2530d06b7a35979e43c6aa0ae1fffd6251853e77`; 1.19.3: `2b8befdc406176e01672175451dd480b14da0b52bfe11a982d368909c05e73db`; 1.19.4: `c8d91af61f87aaa1666de79696d7cd9d4a8212d05f873bdaf28d7bb2926bf165` |
| `world/phys/shapes/EntityCollisionContext.java` | 1.18.2–1.19.4: `fa505d477deaeed144816a3ec08512357ee178dc8769534b3a7f59e9a3299587` |
| `net/minecraft/tags/FluidTags.java` | 1.18.2–1.19.2: `c37eaf0dab3b4bcb3af89e3d24808a9edf9764127fa3c7876ec64cd5d501885e`; 1.19.3–1.19.4: `df79262149bf0e684d877ca5d2bb87f02d8b66d55ed123d4c2e93bfa6462396f` |
| `world/level/block/Blocks.java` | 1.18.2: `cc6b87d2c5897e71e5244b889444ac040e3fa0a139e392523e87fec99805a0f2`; 1.19–1.19.2: `f532585f836ee7ad685d368889ed13d735c570ef38971db0c1d2a7debb4cf27b`; 1.19.3: `9944877c941fa209d2a8b9a7d4eb6333edf2dfca255a3e90601242483eb7a476`; 1.19.4: `a4f2df87c2cae5a3827a818f39f7ad123ee5b18905ead9947efe654421c30ff5` |
| `core/Registry.java` | 1.18.2: `197d0e4430cb0df0459857e1b60a00e4ba653ba9070e91697c5d2fd302e57dd1`; 1.19–1.19.2: `0d9a09867fb533045c1ad61c8f86f71ab47863170e3db8d604ddb08366187472`; 1.19.3: `93833cddc5645f2ac125ece060a9172f45124fe01116bcba93bdf0c577de2443`; 1.19.4: `7ee82200be344bb1519f2cf140e79e03201e8c0542476ebcc127d76f03712d7e` |
| `core/registries/BuiltInRegistries.java` | 1.19.3: `11b0235f5900556ec3fff47fb295c1b3c5afb2cde6d144ba8ccea7814bc6edf5`; 1.19.4: `3266aa8b49058bef6ef4ea3b5bb027c4c18d6f47b09333b2caab89631ef0d1c8` |
| `core/registries/Registries.java` | 1.19.3: `6d36778e4b64140ce57c81a315e399d9e6297dc291a0a5171d288d652b9940b6`; 1.19.4: `5d111b5934f5d09591ed2ef8e73079769dbeccff12b999b49d0b735ff1573735` |

The exact `mojmap/` trees contain Java decompilation sources, not the packaged `data/minecraft/tags/fluids/*.json` resources or arbitrary loaded datapacks/resource packs. `FluidTagsProvider` source confirms the generated vanilla water/lava tag membership, but runtime tag overrides/composition were not inspected. This remains a data gate only if the comparison drops the equal vanilla-tag membership assumption: tag membership is read by `Entity.updateFluidHeightAndDoFluidPushing` and LivingEntity water/lava detection. Fluid tick schedules and external world/rule configuration can also determine when production changes become visible, but state production is outside this movement slice.

No tests, builds, launches, decompilation, product edits, backlog edits, wiki access, or product-history access were performed.
