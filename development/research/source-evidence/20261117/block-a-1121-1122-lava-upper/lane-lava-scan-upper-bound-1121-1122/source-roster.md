# 1.12.1 to 1.12.2 lava scan and travel coverage

Pair: Minecraft 1.12.1 → 1.12.2, exact local decompilations in the compatible Ornithe Feather Gen2 `build.2` mapping family.

Disposition: The assigned scan, material-read, player-input/tick handoff, and lava travel slices are source-covered. No independent lava movement delta was found in these slices. This closes only this bounded assignment; it is not a full-pair coverage freeze and says nothing about unassigned topics or intermediate patch boundaries.

## Exact source identity

- `1.12.1/ornithe-feather/.decompile-provenance.json` identifies version 1.12.1; its pinned inputs include the Mojang client SHA-256 `19331fb29bf8b8d5ed764677c94cca9d66481e10104f660eb555894a4300cf22`, Feather metadata SHA-256 `1893deb3a1a74c6f2054876e2b8512817f744188aa8a9d8aafdba8525c0e896b`, and `feather-gen2-1.12.1+build.2-mergedv2.jar` SHA-256 `f94febd2167d6091b4c157aef0a85c8791e6f8fb27b883e27a82a68c7b02b9c7`. It records Vineflower 1.12.0.
- `1.12.2/ornithe-feather.provenance.json` identifies version 1.12.2, Feather mapping option, and Feather Gen2 build.2 merged/Tiny artifacts. `ornithe-feather.ready.json` names the exact version and 2,050-source manifest. The ready marker's source-manifest SHA-256 is `b8a37ccfccd2aac5f40f5e34fec85873dbdfa93a266043c103e592e4a8c949da`; its artifact-manifest SHA-256 is `8171a095a5ee74cebbd12b3d76983fc01796c0c23c962076d1e235cd0f80fa8c`. Both were recomputed from the local sidecars and match. The sidecar pins the Mojang client (`8ada07da5ee77dad3527bd7278fbd05ee1fc8a597813b216a871a2d7d64cc64f`), Feather merged archive (`e48244030c53979793bdfbe48d7f1f3536f7e4f678ee5890a416198037cd46ca`) and Tiny artifact (`a3aa1c8e73e81bd09432ba1f4b2e88aaacbedb2d8fa3cbf797536d2bdf0d4e58`).
- Every source-file SHA-256 below was recomputed locally and matched its corresponding 1.12.1 provenance `sources` entry or 1.12.2 `ornithe-feather.sources.sha256` entry. Hashes are for exact UTF-8 decompiler output files.

## Source roster (SHA-256)

| Relative source path | 1.12.1 | 1.12.2 |
|---|---|---|
| `net/minecraft/entity/Entity.java` | `80f091bf32166c88cf8bbd31caf72d84fa16224410733c7d2a0f00563f294a0a` | `80f091bf32166c88cf8bbd31caf72d84fa16224410733c7d2a0f00563f294a0a` |
| `net/minecraft/entity/living/LivingEntity.java` | `190e9ac551538e015d9e4d6c42856e5ba32b593131cf6d93895e7b29533f1ee6` | `190e9ac551538e015d9e4d6c42856e5ba32b593131cf6d93895e7b29533f1ee6` |
| `net/minecraft/entity/living/player/PlayerEntity.java` | `4dab93ea3178a28c616947004ecdc514e6542edca8c62ef83f686ccf6b2ce640` | `e4e0fdbe07a7d0a0ae4a70cbb6739a287c9d045a4a12b409895c220b5d91fe1e` |
| `net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java` | `d7237b8b9a68525bbfec6af1c234d2aafb1e4aa7d52059331d1d243313d6d7d1` | `01a58e94d8c6ff98a8e3794227cdc76a5fcbdbad795c70c9cf28854aff9823cc` |
| `net/minecraft/client/entity/living/player/Input.java` | `9e704cfe7fdc55c4eab78670e60cf392ba6817adbd5e7451d3a86f11da8bf50e` | `9e704cfe7fdc55c4eab78670e60cf392ba6817adbd5e7451d3a86f11da8bf50e` |
| `net/minecraft/client/entity/living/player/KeyboardInput.java` | `7be11425906be051c83e275f359816546e4677b16d212156380e8d2e9258654a` | `7be11425906be051c83e275f359816546e4677b16d212156380e8d2e9258654a` |
| `net/minecraft/world/World.java` | `d182122a9b052f57803672c21b0941cb7e7d41ed756cd0a4ea8c0858684c8259` | `e9fa9b8d6d31ad57a5b876f5f63a5e3c554a23437decf1845a493daf48233594` |
| `net/minecraft/client/world/ClientWorld.java` | `996286a2b9a14b7396f1ff3501c0a2494af67534930ad5e9a0bbf5e88f12e01f` | `468def1eb1bd0791ce1d8b194ba22fe0fe49962bb3e696fb34b83bf4de7fb72c` |
| `net/minecraft/client/world/chunk/ClientChunkCache.java` | `235599d5eed2386ce4a5ed282ee893df15d8a79ff3c301f17ff3a6b45796e206` | `235599d5eed2386ce4a5ed282ee893df15d8a79ff3c301f17ff3a6b45796e206` |
| `net/minecraft/world/chunk/EmptyChunk.java` | `ffc3414f16ccb2b1523a7b9b2c9adfc2946c006bdb97b83c3c29f405ee5f8fd0` | `ffc3414f16ccb2b1523a7b9b2c9adfc2946c006bdb97b83c3c29f405ee5f8fd0` |
| `net/minecraft/util/math/Box.java` | `f788b8146b14f299ccb58ea0854609f845c298a963295d503de2e30e15d66f3a` | `f788b8146b14f299ccb58ea0854609f845c298a963295d503de2e30e15d66f3a` |
| `net/minecraft/util/math/MathHelper.java` | `579e05d110460f444bcac86729141545095cb5932c0f8d34c9a4dcfcd9a35e80` | `579e05d110460f444bcac86729141545095cb5932c0f8d34c9a4dcfcd9a35e80` |

## Compared behavior

### Material scan and coordinate bounds

In both releases, `Entity.isInLava()` (`Entity.java:1045–1047`) passes `getShape().grown(-0.1F, -0.4F, -0.1F)` and `Material.LAVA` to `World.containsMaterial` (`World.java:1623–1645`). `Box.grown(double,double,double)` (`Box.java:132–139`) applies `min - delta` for each minimum and `max + delta` for each maximum. The call's `F` literals are float values widened to the method's double parameters before those double operations; negative deltas contract both sides (about 0.1 horizontally and 0.4 vertically, using the widened float values).

`containsMaterial` computes each lower bound with `MathHelper.floor` and each upper bound with `MathHelper.ceil`, then visits X, Y, Z in nested loops with strict `<` upper tests and returns on the first exact material match. `MathHelper.floor(double)` corrects a truncating cast downward when `x < (int)x`; `ceil(double)` corrects upward only when `x > (int)x` (`MathHelper.java:40–43, 67–70`). Therefore `floor(minY)` is included, while `ceil(maxY)` is exclusive: when contracted `maxY` is exactly integer N, the scan does not inspect block row N; when it is fractional, it inspects through `floor(maxY)`. The same boundary rule applies on X and Z. No height clipping occurs in the scan loop itself.

### World-height and client chunk reads

`World.getBlockState` returns air for Y < 0 or Y >= 256 (`World.java:177–179, 680–687`); otherwise it delegates to `getChunk(pos)`. The client cache returns the loaded chunk or its shared `EmptyChunk` fallback (`ClientChunkCache.java:28–30, 42–58`); the empty chunk reports height 0 (`EmptyChunk.java:26–29`) and returns air for every block-state read (`EmptyChunk.java:39–42`). Thus out-of-height cells and missing client chunks read as air for this scan, with no pre-scan loaded-chunk rejection in `containsMaterial`.

The local-player tick guard is `world.isChunkLoaded(new BlockPos(x, 0.0, z))` (`LocalClientPlayerEntity.java:164–178 / 165–178`). The no-argument-position overload calls the `allowEmpty=true` form (`World.java:186–192`); `ClientWorld.isChunkLoadedAt` short-circuits true when `allowEmpty` is true (`ClientWorld.java:104–107`). Consequently this guard does not suppress local ticking merely because a client chunk is empty. The subsequent lava scan can still read absent chunk coordinates as air through the cache path above.

### Input, tick and lava travel consumers

The local player calls its superclass tick after that chunk guard. Its `mobTick` updates `input`, then invokes `super.mobTick`; `serverTickAi` copies sideways, forward and jump values from `input` when this player is the camera (`LocalClientPlayerEntity.java:630–641, 648–807 in 1.12.1; 631–641, 649–812 in 1.12.2`). `Input.tick` itself is empty; the keyboard subclass polls and scales its fields (`Input.java:15–20; KeyboardInput.java:13–50`). The handoff segment from `boolean bl = this.input.jumping;` through `super.mobTick();` was compared byte-for-byte and is identical in the two local-player sources. `LivingEntity.tick` dispatches to `mobTick` (`LivingEntity.java:1619–1621, 1681`), where the local `serverTickAi` update precedes the jump and travel stages.

The same `LivingEntity.mobTick` lava consumers occur in both releases (`LivingEntity.java` source hashes are identical): with jumping set, water is checked first, then `isInLava()` selects `jumpInLava()` before the grounded jump fallback (`LivingEntity.java:1827–1839`; `jumpInLava` adds `0.04F` to vertical velocity at 1417–1419). The subsequent travel stage damps sideways/forward inputs by `0.98F`, calls `flyingTick`, then `moveRelative` (`LivingEntity.java:1841–1848`). In `moveRelative`, the local/logical movement gate and water check precede `!isInLava() || player-flying` (`LivingEntity.java:1425–1429`). For a non-flying player in lava, the matching `else` branch applies `0.02F` input acceleration, moves, halves all three velocity components, applies `0.02` downward gravity unless no-gravity, and may set vertical velocity to `0.3F` on the stated horizontal-collision step condition (`LivingEntity.java:1540–1554`). The `PlayerEntity.moveRelative` override was also compared and is unchanged (method-body SHA-256 `4f6cfd1cd93faa4f02f96f20a5c2a48c05297254287277c8068de73ad9cf1e27`).

`Entity.tick` has another inherited `isInLava()` call that halves `fallDistance` after the lava-damage call (`Entity.java:400–403`). Damage is outside the assigned movement scope; the movement-state write is noted here. `Entity.java` is byte-identical between these releases.

### Pair differences seen nearby

The entire `World.java`, `ClientWorld.java`, `PlayerEntity.java`, and `LocalClientPlayerEntity.java` files are not all byte-identical between releases, so file identity alone was not used to infer no change. The bounded `containsMaterial`, `getBlockState`, default `isChunkLoaded`, client `isChunkLoadedAt`, cache `getChunk`, empty-chunk state/height methods, `Box.grown`, and double floor/ceil bodies were individually compared and match. `Entity.java`, `LivingEntity.java`, `Input.java`, `KeyboardInput.java`, `ClientChunkCache.java`, `EmptyChunk.java`, `Box.java`, and `MathHelper.java` also have identical whole-file hashes above.

A neighboring `LocalClientPlayerEntity.mobTick` difference is present in 1.12.2's portal/screen branch: it calls `closeMenu()` when the open screen is an inventory screen before the same `openScreen(null)` step (`LocalClientPlayerEntity.java`, around lines 655–663). `PlayerEntity.closeMenu()` only assigns `this.menu = this.playerMenu` (`PlayerEntity.java:370–372`); the input-through-`super.mobTick()` segment and all scoped lava checks remain identical. This is an inventory/UI delta, not a source-confirmed lava movement change.

## Scope and exclusions

This review covers only the two named releases and the stated lava scan, world lookup, local-player input/tick guard and direct lava movement consumers. It does not extrapolate the boundary to other 1.12 patches or close the remaining movement inventory. No wiki, product implementation, Git history, build, test, or runtime evidence was used.