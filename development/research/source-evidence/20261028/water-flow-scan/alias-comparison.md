# Liquid scan alias closure: 1.9.1-1.9.3

**Scope.** Exact mapped `ornithe-feather` source trees for 1.9, 1.9.1, 1.9.2, 1.9.3 and 1.9.4. I independently inspected each exact release to close the intervening aliases for the scan exclusive upper bounds, iteration, chunk/area checks, `containsMaterial` contract, ordinary-player callers and direct callers. This is source comparison only; it makes no runtime or trajectory claim.

## Source identity and pins

Each version root was enumerated with literal-path `Get-ChildItem -Force`; the selected files below were read only from that release's `ornithe-feather` tree. The first four releases carry a per-tree hidden provenance record whose `sources` map is the source hash manifest. In 1.9.4, the root provenance, ready marker, verification record, artifact manifest and source manifest are separate literal files. Every selected file's disk SHA-256 matches its exact release manifest/map.

| Release | Exact provenance / pin records (SHA-256) | Version-specific input pins |
|---|---|---|
| 1.9 | `1.9/ornithe-feather/.decompile-provenance.json` `8daacb7af8f210e716ee4b34c43b8a5d6f6b4b19831d2fd725cbe16ecf330a1a` | Feather Gen2 1.9+build.2 mergedv2 JAR `a4e09a92eab0fbf6ef4668e295db1949e9d52c42f145d56b6dfbf3a10c2c7c2a`; Feather metadata `ca32cbff5f91d52f6563a2d50a20e3f0fb1e1f76dbb6e41e53c847d2d6977371`; client JAR `0273b5c79d6b96799c6480ae61366d6b1d042655970de87f77f1a3f1ebea9307`. |
| 1.9.1 | `1.9.1/ornithe-feather/.decompile-provenance.json` `e9fb0da6511597e2de953a143f9570b82243e5b9504603686e557f06271da64a` | Feather Gen2 1.9.1+build.2 mergedv2 JAR `6d5768353bf2ff2756d38537312548a8ac91d570f130d23c23e9412d2edd97b3`; Feather metadata `da56c9e7efe5c929b2ec8d99a8e2bbc6825847f2852a0e717fcd6c674db73dc4`; client JAR `56ad7e53f6f4ad393844e274aa2190692b61d2ad9f6b3d12b33a8b7332fd51f9`. |
| 1.9.2 | `1.9.2/ornithe-feather/.decompile-provenance.json` `9928b467deb35c7e7f891b4fee1627a68feecdf233c2076909fd4834b0380510` | Feather Gen2 1.9.2+build.2 mergedv2 JAR `e2b98ececbff8fa0bd0c89283bbb32b7f9f9dcd4b89fa3f137666fa3ac0b6859`; Feather metadata `5bad5e67d0870fec69bbab8fcae8d0525a274668bb472eb09d1552359d08edd1`; client JAR `ef87eb71250df1b871deab8d3ef10aac3290b9e78667ad548fde4d0e14af0d65`. |
| 1.9.3 | `1.9.3/ornithe-feather/.decompile-provenance.json` `43325cdd53ca34b88ee9af8464782b323a948b98c8a556c4761f1e5737b84c3a` | Feather Gen2 1.9.3+build.2 mergedv2 JAR `fc5defa1546cf87888a11783e2b5d9b48b47c6c022f48de4e55add978ab35da3`; Feather metadata `353c13519171cbba1a96872aebcb8b38248d5d5963b4c4613c23337adf028985`; client JAR `ab8fdde789c5b4614ffc98249e04fdcce7dfce320b55215bfe5418608af6a63b`. |
| 1.9.4 | `1.9.4/ornithe-feather.provenance.json` `5c913715bed763369ceb5fd171cab3e63c43298023078c3683f96c7ea8fed3cd`; `ornithe-feather.ready.json` `3d0a810d9f3a93233d880ca07ffc806ea232230d33197197ad50ba69181171c6`; `ornithe-feather.verification.json` `9ef993b06552037873604f912cce078845241b6e6f3b9799a83795abd1adf688`; source manifest `ornithe-feather.sources.sha256` `c7b508fe01634887b65919dcd3a900c311a21d9510a1f1ab248d5c17c528ab19`; artifact manifest `artifacts.sha256` `9527dca544694daa3b4a7741be1a5b4802d8b6665c8a152a408a37a27a1b4d77`. Ready status is `ready`; verification reports 1,819 source files checked and 0 source differences. | Ready marker pins client JAR `23e90103a1ca2ac71100004c6d5846de09f85695f579843ef8da41571e60c908`. `artifacts.sha256` pins `feather-gen2-1.9.4+build.2-mergedv2.jar` as `49a38d0adfbda1749e519c29844116e9f22e895cb505633261b7f587268f4125` and `.tiny` as `9e21708d4bc32a43ac404735ea3238465889797110204a0375bcb069a3798027`. Provenance records Feather mapping option and exact artifact names. |

All provenance records identify the `lpc-decompile-v1` pipeline with the recorded generic/ascii/synthetic-removal settings; the 1.9-1.9.3 records pin the versioned Feather metadata, mapping JAR and client inputs directly. For 1.9.4 the ready marker pins the source/artifact manifests and client, while the artifact manifest pins the mapping files.

### Selected source-file SHA-256 values

The hashes are the actual source-file hashes and match the per-release maps/manifests described above.

| Release | `world/World.java` | `entity/Entity.java` | `entity/living/LivingEntity.java` | `entity/living/player/PlayerEntity.java` |
|---|---|---|---|---|
| 1.9 | `d1e23db2e20c86eb37b964a0df6838f1422ade7b0c2fff9c869ff8345979aa98` | `74d3ac34d7e6355c8fdcdd777dcd95ba9ef4626718c8772c1b58d0e28419e42a` | `5b5936656aacc64a4d75db7b370985f9456ac96076331da047bd246f76a705fb` | `edbb46236d1e42630e600e4afcb434eb6e9adecbfc4ac7e15710a5dea50010b1` |
| 1.9.1 | `cdda70c97f0e4d6f6c454c8f6d3c0d47f0e0ae612e75062d8fe93ddd3963e6b0` | `74d3ac34d7e6355c8fdcdd777dcd95ba9ef4626718c8772c1b58d0e28419e42a` | `d8824f670037a88753c428c27213989fc5173b1d7c638f498f73395c145b6fd9` | `d7035f08f03b01823dff70ca504f7244f9b0753542cc0f6dd7301e66f8866b71` |
| 1.9.2 | `cdda70c97f0e4d6f6c454c8f6d3c0d47f0e0ae612e75062d8fe93ddd3963e6b0` | `74d3ac34d7e6355c8fdcdd777dcd95ba9ef4626718c8772c1b58d0e28419e42a` | `d8824f670037a88753c428c27213989fc5173b1d7c638f498f73395c145b6fd9` | `1fc5107b9b7f5711105d521a28829f2466fe9f297e832f4d209e6ec422111aac` |
| 1.9.3 | `2fe063e0ec224eed9fc7d01b8f788c32035eed5fee5a9d98ea5e82296236e05a` | `bcd7fa2206bf8d7271102f6da7fe2771f96dbe22ec79dab9f2101a3e29c17ef0` | `bbb7703f18fd5da05c4e4a43a77ea644b388e63c01d34166d308ea52054be4e5` | `ef96c8a00443f724eadad05a6a4dcc8a8a2e23cd4c3390b9e0f1f78c9ff4d1af` |
| 1.9.4 | `2fe063e0ec224eed9fc7d01b8f788c32035eed5fee5a9d98ea5e82296236e05a` | `bcd7fa2206bf8d7271102f6da7fe2771f96dbe22ec79dab9f2101a3e29c17ef0` | `bbb7703f18fd5da05c4e4a43a77ea644b388e63c01d34166d308ea52054be4e5` | `d658a0d95452d12bb7e347bfd802240eeecaf7f938e10dcd43e2640434387f85` |

## Scan implementation

The following method spans were verified directly. Method-text SHA-256 values use UTF-8 over each method's exact source text from signature through matching closing brace. Each listed digest is the same in all five releases, even where surrounding source changes move its line number.

| Method | 1.9 / 1.9.1 / 1.9.2 span | 1.9.3 / 1.9.4 span | Common method-text SHA-256 |
|---|---:|---:|---|
| `World.applyLiquidDrag(Box, Material, Entity)` | `World.java:1554-1596` | `World.java:1560-1602` | `cc35bea0df5e3aad83235368cbc07431f4f1470c6d319a2281609b81ef210e24` |
| `World.containsMaterial(Box, Material)` | `World.java:1598-1620` | `World.java:1604-1626` | `32aa474acfd430eed4837d7db5d0b33de1ba58500e1ecaea430521b39674f34c` |
| `World.isAreaLoaded(int, int, int, int, int, int, boolean)` | `World.java:210-229` | `World.java:216-235` | `affb2342bd8ae3ae43c03c6eb7712372bc2d2c9fc933f2528df0b5d366f839ea` |

Both scans set minima with `floor(bounds.min*)` and exclusive maxima with `ceil(bounds.max*)`, then use nested half-open loops (`i <= coordinate < j`, likewise Y/Z). `containsMaterial` tests `getBlockState(...).getMaterial() == material`, returns on the first match and releases its pooled mutable position on both success and exhaustion. It does **not** call the loaded-area helper.

`applyLiquidDrag` first calls `isAreaLoaded(i,k,m,j,l,n,true)` and returns `false` if the area is not loaded. The helper requires `maxY >= 0 && minY < 256`, shifts X/Z bounds to chunk coordinates and checks chunks inclusively. The scan then tests the state material, computes `p + 1 - LiquidBlock.getHeightLoss(level)`, and counts/applies material drag only when `l >= d`; it accumulates a vector, releases the pooled position, and adds normalized vector components times `0.014` only when the vector is nonzero and `entity.hasLiquidCollision()` is true.

## Ordinary-player callers and gates

`PlayerEntity` extends `LivingEntity` in each release (`PlayerEntity.java:91` in 1.9-1.9.2; `:92` in 1.9.3-1.9.4). The inherited `Entity.tick()` calls `baseTick()` (`Entity.java:301` in 1.9-1.9.2; `:304` in 1.9.3-1.9.4), and `Entity.baseTick()` invokes `checkWaterCollisions()` (`:358` for 1.9-1.9.2; `:361` for 1.9.3-1.9.4). It subsequently calls `isInLava()` (`:376` / `:379`). The `LivingEntity.baseTick()` override delegates to `super.baseTick()` in all five trees.

| Caller/gate | 1.9 | 1.9.1-1.9.2 | 1.9.3-1.9.4 | Exact behavior observed |
|---|---|---|---|---|
| `Entity.checkWaterCollisions` | `Entity.java:845-861` | same | `Entity.java:849-865` | A boat mount clears `inWater` and skips the scan. Otherwise invokes `applyLiquidDrag` on `getShape().grown(0.0,-0.4F,0.0).contract(0.001)` with `Material.WATER`; true optionally splashes on first entry, resets fall distance, sets `inWater` and clears fire time; false clears `inWater`. Method-text SHA-256 in all five: `bb008e529fb656f40bc3d62647da8bed0a02acc8a124463eb4ed9baa7217517f`. |
| `Entity.isInLava` | `Entity.java:939-941` | same | `Entity.java:943-945` | Directly calls `containsMaterial(getShape().grown(-0.1F,-0.4F,-0.1F), Material.LAVA)`. Method-text SHA-256 in all five: `e0682c9491745ff612d6cb4b5b73be2fc9e43ba4ceae57f5d7b042b90500e242`. |
| `LivingEntity.checkFallDamage` | `LivingEntity.java:183-185` | `:184-186` | `:185-187` | Calls `checkWaterCollisions()` only when `!isInWater()`. Method-text SHA-256 in all five: `db2f4a6fa94bf3a4e059e408a4e1e948a078312c75359a288eb075f3daa07dc4`. |
| `LivingEntity.moveRelative` | water/lava tests at `:1291-1292` | `:1295-1296` | `:1304-1305` | Within the local/logical-side movement branch, water and lava gate the dry movement path; player flight is the explicit exception for each predicate. The method-text SHA-256 matches in all five: `8935df8b81194575e97189f46e7e59422988118ba73b1eca880c8340699359dd`. |
| `LivingEntity` jump selection | water/lava/ground at `:1680-1684` | `:1684-1688` | `:1693-1697` | When `jumping`, water chooses `jumpInWater`, otherwise lava chooses `jumpInLava`, otherwise eligible grounded jump. |
| `PlayerEntity` direct water reads | `PlayerEntity.java:953, 1399` | same | `:958, 1405` | The first is a player predicate condition `!isInWater()`; the second is the water branch that updates swim-distance statistics. |

## Direct call-site inventory

A literal per-version `rg` scan of Java source for `.containsMaterial(` and `.applyLiquidDrag(` found the same direct-call inventory in each of the five trees:

- `Entity.java`: `applyLiquidDrag` in `checkWaterCollisions` (`:848` in 1.9-1.9.2; `:852` in 1.9.3-1.9.4); `containsMaterial` in `isInLava` (`:940` / `:944`). These are the two ordinary-player routes through the scans.
- `ExperienceOrbEntity.java:137`: direct `applyLiquidDrag` call.
- `ItemEntity.java:191` in 1.9-1.9.2 and `:192` in 1.9.3-1.9.4: direct `applyLiquidDrag` call.

No other direct calls to either `World` scan method were found in any of the five source trees.

## Closure

For the scan operation, every intervening release 1.9.1, 1.9.2 and 1.9.3 uses the same `floor(min*)` / `ceil(max*)` exclusive upper bounds, nested loop indices, state-material predicate, loaded-area policy and liquid-height check as its sampled neighbors. The exact extracted scan/helper method texts hash identically in all five releases. The ordinary-player boat check, fall-damage water recheck, movement flying exception and jump selection also retain the same conditions; only source line positions shift. **No intermediate release differs for the traced liquid scan or ordinary-player gates.** This closes only 1.9-1.9.4 source aliases and does not claim runtime parity.

