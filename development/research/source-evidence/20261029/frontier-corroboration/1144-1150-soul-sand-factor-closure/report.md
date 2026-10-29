# Soul Sand horizontal speed factor: 1.14.4 → 1.15

## Mapping and source identity

The bounded comparison is **1.14.4 Mojmap → 1.15 Mojmap**. The assignment initially named 1.14.4 Yarn; the coordinating agent directed this closure to use the exact published Mojmap trees on both sides. This report does not claim Yarn evidence.

Canonical source roots are `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\1.14.4\mojmap` and `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\1.15\mojmap`. The immutable ready/provenance markers identify 1.14.4 / Mojmap (3,250 files, 17,080,207 bytes; client artifact SHA-256 `b3b2a798e2d67b566008fe4a03767ae2c7ff3f8c7ba6751e7b71fc7299672d0a`) and 1.15 / Mojmap (3,329 files, 17,522,461 bytes; client artifact SHA-256 `41eee66b4c8e76c018745a63a46a15097cc1c6482c94b38801054938a010b21b`). Both are Vineflower sources produced with Java 25, Vineflower 1.12.0, ASM 9.10.1, mapping-io 0.9.1, Gson 2.14.0, and Tiny Remapper 0.14.1.

| Identity file | SHA-256 |
|---|---|
| 1.14.4 `mojmap.sources.sha256` | `af98406f3d4ed31494fbdce1078d1f3df3f712501878f9a0e9ad5879bdf23c3b` |
| 1.14.4 `mojmap.provenance.json` | `9d1ad405e1e787d2fca5ed1897e6797b1f3cd79527da50d713232f46e2f30d97` |
| 1.14.4 `mojmap.ready.json` | `ec7b6a6d9ba72f8a19908527977c970a8e2b3007b0e0b4323a725fb5943d3043` |
| 1.15 `mojmap.sources.sha256` | `251a3019ccc3fd067f66365f3545b69a983383a28b19a1038debf1791be29340` |
| 1.15 `mojmap.provenance.json` | `de4a538ebbaf82d1615683b08ecae3cd76553345f3079436764eb095be343949` |
| 1.15 `mojmap.ready.json` | `c6b66b63bafaf2096ad7606111f1d0fae1f2178d624d6a8c9ded5ad3ac1aaa31` |

Relevant source hashes below were recomputed from the published source files and match the corresponding path entries in each source manifest.

| Version / path under `mojmap/` | SHA-256 |
|---|---|
| 1.14.4 `net/minecraft/world/entity/Entity.java` | `31c6be42d165102d3e6dc4295fdfef6e8971fc3aea13f938a5b3a33b91d524a7` |
| 1.14.4 `net/minecraft/world/level/block/Block.java` | `276022cfc5bc00437fe65a23bbcdc9c078026e68d88930eb3244581f6dbfcf5f` |
| 1.14.4 `net/minecraft/world/level/block/Blocks.java` | `983d0cde25f55ddb055015b682bbdf3b131394208561805f26d9b2a240dee9a9` |
| 1.14.4 `net/minecraft/world/level/block/SoulsandBlock.java` | `3f2ce73aae23fb7e13cf001123343c6c750f00684b5331a5597ae33b011ef4e4` |
| 1.14.4 `net/minecraft/world/phys/Vec3.java` | `a7f28da742d47ed4e543813d03c170cb4e970f4063baebc085884a3110605e36` |
| 1.15 `net/minecraft/world/entity/Entity.java` | `191b3ad3e7348c9bac1e703fff896706d23a751bf15162aacf676f5f97c0a10e` |
| 1.15 `net/minecraft/world/entity/LivingEntity.java` | `139c3866ad2c6a1ad73496f22ff1a4b5259998442092ffbafb8664428e28bf79` |
| 1.15 `net/minecraft/world/entity/player/Player.java` | `6985e11ad9be553e9a2f25e5decc20a9da5c3b59b68f4e83a5b9f448cd2e48a0` |
| 1.15 `net/minecraft/client/player/LocalPlayer.java` | `7227da5233e3c72ced008187b92a6a86842f84778eaa9f40fa1eb117286af823` |
| 1.15 `net/minecraft/client/player/AbstractClientPlayer.java` | `db13c55b0ba08cb2a40cf2da7a3954de24e1e1f55a197f125cc1aa6ed109be0b` |
| 1.15 `net/minecraft/world/level/block/Block.java` | `069ffc485f94c84eb63e528e608f91104229477b6ac266aa3d4103f836cac2fe` |
| 1.15 `net/minecraft/world/level/block/Blocks.java` | `0cef66feacbf9d7d5bd38ac1d2065e71384a73043b0956eeaf314fedbf5cc7d9` |
| 1.15 `net/minecraft/world/level/block/SoulsandBlock.java` | `355f0cc25e78dce76cbc9c89d8bd8ff1956d4b53780cba8e3fb5c23f485802cf` |
| 1.15 `net/minecraft/world/phys/Vec3.java` | `5c3c36231efe06c3c0bf70dcf2c2ec2f2b79089cb2dad530db1b3b7e1a5ec909` |

## Source trace

**1.14.4 registration and movement write.** `Blocks.SOUL_SAND` is registered at `Blocks.java:577–578` as `new SoulsandBlock(Block.Properties.of(...).randomTicks().strength(0.5F).sound(...))`; its registration helper calls `Registry.register(Registry.BLOCK, string, block)` (`Blocks.java:2021–2023`). There is no speed-factor setting. The old `Block.java` field list has `friction` but no `speedFactor` (`:91–108`), and `Block.Properties` declares no speed-factor field/default or `speedFactor` builder (`:842–941`). A source-wide search of the complete 1.14.4 Mojmap Java tree for `getBlockSpeedFactor`, `speedFactor`, `getSpeedFactor`, and `speedFactor(` returned no matches; the old `Entity.move` body (`Entity.java:446–573`) also has no generic speed-factor application.

Instead, 1.14.4 `SoulsandBlock.entityInside` writes `entity.getDeltaMovement().multiply(0.4, 1.0, 0.4)` back through `setDeltaMovement` (`SoulsandBlock.java:27–30`). The `0.4` arguments are double literals, selecting `Vec3.multiply(double, double, double)` (`Vec3.java:91–93`). `Entity.move` first updates the bounding box/position and collision state, then calls `checkInsideBlocks()` (`Entity.java:466–477, 542–544`). That method checks that the AABB endpoint chunks are present, iterates integer cells in the AABB inset by `0.001`, and for each cell calls `blockState.entityInside(...)` before `onInsideBlock(...)` (`Entity.java:810–838`). There is no Player-only or flying gate on the Soul Sand callback. The per-contact callback’s block/world inputs are runtime state and are not assumed here.

**1.15 registration, factor source, and application.** `Blocks.SOUL_SAND` is registered with `.speedFactor(0.4F)` (`Blocks.java:601–603`); its helper likewise calls `Registry.register(Registry.BLOCK, string, block)` (`Blocks.java:2128–2130`). `Block` stores `properties.speedFactor` (`Block.java:99–101, 247–260`), initializes the builder default to `1.0F`, exposes `speedFactor(float)` and returns the stored value through `getSpeedFactor()` (`Block.java:845–856, 911–914, 713–715`). The 1.15 `SoulsandBlock` has no `entityInside` override; the inherited `Block.entityInside` is empty (`Block.java:640–642`).

The 1.15 `Entity.move` order is: move/resolve collision and update position; update collision flags and fall/step handling; call `checkInsideBlocks()`; then write `getDeltaMovement().multiply(getBlockSpeedFactor(), 1.0, getBlockSpeedFactor())` (`Entity.java:450–543`). Thus the factor write follows all inside-block callbacks in that move and changes the stored X/Z velocity after the position for that `move` call has already been resolved. Unlike the old callback write, it is not inside `checkInsideBlocks`'s `hasChunksAt` branch. `getBlockSpeedFactor()` first reads the block at `new BlockPos(this)`. Water and bubble-column blocks return that block’s factor directly; for other blocks, a factor unequal to `1.0` is returned, otherwise it queries `getBlockPosBelowThatAffectsMyMovement()` and returns that block’s speed factor (`Entity.java:590–601`). The support query is `new BlockPos(x, boundingBox.minY - 0.5000001, z)`. This defines the read path; it does not assert which cells a particular world/position supplies.

There is also a source-type change in the operand: the old callback passes double `0.4` to `Vec3.multiply(double, double, double)`, while the new block builder stores float `0.4F`, `getSpeedFactor()` returns float, and the generic call widens that value to the vector method’s double argument (`Blocks.java:603`; `Block.java:713–715, 911–914`; `Entity.java:543`; `Vec3.java:96–98`). This is a source-level numeric difference only; no accumulated trajectory is inferred.

## Local-player gates and earliest later movement call

In 1.15, `LocalPlayer extends AbstractClientPlayer` (`LocalPlayer.java:78`), and `AbstractClientPlayer extends Player` (`AbstractClientPlayer.java:21`); therefore the virtual call from `Entity.move` reaches `Player.getBlockSpeedFactor`. That override returns `1.0F` if `abilities.flying` or `isFallFlying()` is true, and delegates to `Entity` otherwise (`Player.java:2035–2038`). This suppresses the generic factor stage for those player states. `Entity.move`’s factor write is in its collision-enabled `else` branch, so `noPhysics` bypasses it; a piston move limited to zero also returns before the branch work (`Entity.java:450–460`).

The local client path is source-reachable: `LocalPlayer.tick()` calls `super.tick()` only when `level.hasChunkAt(new BlockPos(x, 0, z))` (`LocalPlayer.java:178–182`). `LocalPlayer.isEffectiveAi()` returns true (`LocalPlayer.java:489–492`); its `aiStep()` calls `super.aiStep()` (`LocalPlayer.java:778`), and `Player.aiStep()` calls `super.aiStep()` (`Player.java:497–514`). `LivingEntity.aiStep()` calls `travel(...)` (`LivingEntity.java:2282–2287`); `LivingEntity.travel()` enters its movement branch when `isEffectiveAi() || isControlledByLocalInstance()` (`:1831–1832`), which is true for this local player via `isEffectiveAi()`.

For a non-flying, non-fall-flying LocalPlayer in the ordinary non-water/non-lava `LivingEntity.travel` branch, the earliest later movement application is its next travel pass: `moveRelative(...)` updates the stored velocity, then `move(MoverType.SELF, getDeltaMovement())` is called at `LivingEntity.java:1886–1891`. The current `Entity.move` call has no second displacement application after its factor write. Its caller continues with later travel state updates, then the next travel pass supplies the resulting stored delta to a new `move`. Other conditional branches have their own calls (`LivingEntity.java:1871, 1916, 1950`); they are not used to infer a trajectory here.

## Finding and limits

The source-confirmed difference is the operation that supplies Soul Sand’s horizontal slowdown and when it writes it: in 1.14.4 the Soul Sand contact callback directly scales current X/Z delta during `checkInsideBlocks`; in 1.15 the block registration supplies `0.4F` to the generic factor lookup, which scales post-contact X/Z delta at the end of collision-enabled `Entity.move`. A non-flying, non-fall-flying local player is eligible for the 1.15 factor path through the inheritance chain; flying, fall-flying, and `noPhysics` states bypass that factor. The source establishes no particular world cells, contact count, actual velocity, support block, trajectory, or endpoint movement. No claim is made here about honey, jump factors, collision-shape providers, data resources/tags, server-side producers, or other movement factors.
