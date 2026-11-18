# Source roster: 1.11.2 to 1.12.2 water-current operations

## Pair and evidence

- Exact releases: Minecraft 1.11.2 and 1.12.2.
- Mappings: Ornithe Feather, Feather Gen2 build.2 for each exact release (`ornithe-feather` source roots).
- Both roots have version-root `ornithe-feather.ready.json`, `ornithe-feather.provenance.json`, `ornithe-feather.sources.sha256`, `artifacts.sha256`, and `ornithe-feather.verification.json`.
- 1.11.2 ready marker: 1,921 source files; source manifest SHA-256 `d908c2af76598ad26999563bfef0089f1d3c8c32483eee6b108f0defa2773df0`; client SHA-256 `be3fff4f2cc005a1310a96389efdeb983d2bcb4b8e747c402acd616ae73d0ba2`; mapping artifacts `feather-gen2-1.11.2+build.2-mergedv2.jar` and `feather-gen2-1.11.2+build.2.tiny`.
- 1.12.2 ready marker: 2,050 source files; source manifest SHA-256 `b8a37ccfccd2aac5f40f5e34fec85873dbdfa93a266043c103e592e4a8c949da`; client SHA-256 `8ada07da5ee77dad3527bd7278fbd05ee1fc8a597813b216a871a2d7d64cc64f`; mapping artifacts `feather-gen2-1.12.2+build.2-mergedv2.jar` and `feather-gen2-1.12.2+build.2.tiny`.
- Both verification markers report `verified-identical-source-and-input-artifacts`, all published source files verified, zero source-file differences, and zero missing source files. The 1.12.2 marker notes a regenerated intermediate TinyRemapper archive hash difference; it explicitly reports that published source files and raw inputs match.
- Programmatic verification recomputed each source-manifest file hash: 1,921/1,921 and 2,050/2,050 match, and each source manifest hash matches its ready marker.

## Owned bounded source slice

Ordinary, nonfalling water-current response reachable by the local client player:

1. `net/minecraft/world/World.java`: `applyLiquidDrag(Box, Material, Entity)` including floor/ceil cell bounds, exclusive loops, world-area admission, material and liquid-height gates, current accumulation, normalization and velocity writes.
2. `net/minecraft/entity/Entity.java`: the ordinary `checkWaterCollisions()` AABB and gates, `isInWater()` state getter, and `baseTick()` call site; `checkFallDamage` call site in `LivingEntity` as secondary context.
3. `net/minecraft/block/Block.java` and `net/minecraft/block/LiquidBlock.java`: default drag hook, liquid hook, flow aggregation and its supporting liquid depth / face-solid helpers.
4. `net/minecraft/util/math/Box.java`, `MathHelper.java`, and `Vec3d.java`: caller box transforms, inclusive floor/ceil conversion and vector addition/length/normalization helpers.
5. Client reachability: `LocalClientPlayerEntity` → `ClientPlayerEntity` → `PlayerEntity` → `LivingEntity` → `Entity`; local tick calls `super.tick()`, and `Entity.baseTick()` calls `checkWaterCollisions()`.

Exact source manifest SHA-256 values for reviewed classes:

| Source | 1.11.2 | 1.12.2 |
|---|---|---|
| `World.java` | `27cfaa5ff45c2d88c492fc5dae3ea4a2bb4536fd64bff8498a9a7d0cb82efb58` | `e9fa9b8d6d31ad57a5b876f5f63a5e3c554a23437decf1845a493daf48233594` |
| `Entity.java` | `ce8104a17ce783df639cf9726e7b1cd0936563eaa7ba308603335bbe05d49440` | `80f091bf32166c88cf8bbd31caf72d84fa16224410733c7d2a0f00563f294a0a` |
| `Block.java` | `ab873436b24487ab0ee8055bf7a478349c7f7978398aea8e387796a6c820996a` | `e4a90eca411e7b0e14f5018f7385ec29891f6cdf1a1e917fa648b5712f9b33a1` |
| `LiquidBlock.java` | `c91618c8445dd488dd61f4a01aed0799dae2faaf3585bc6f2d5164891e988244` | `00f7995dd49a48bf97316e30b3d6e41d8ee1e65dd212f3dc64891883621c4260` |
| `LivingEntity.java` | `bb7dc6c9e423a9568d6433d51bba12e7aee4555fbf3fb3e2b87f618382279f2f` | `190e9ac551538e015d9e4d6c42856e5ba32b593131cf6d93895e7b29533f1ee6` |
| `LocalClientPlayerEntity.java` | `65c2747bd8c70def6be7f41f624d4c9493342b39ae7bed7967f9ff63608f59ed` | `01a58e94d8c6ff98a8e3794227cdc76a5fcbdbad795c70c9cf28854aff9823cc` |
| `ClientPlayerEntity.java` | `207a0ff4ee996916cd1fc73f47bfaae64f92719916cafed8395b1f2a8f797809` | `207a0ff4ee996916cd1fc73f47bfaae64f92719916cafed8395b1f2a8f797809` |
| `PlayerEntity.java` | `87fe94fa6cbf7aba18b9a5e3401664439eb8eba9958173da8fbcd05cc7ad948b` | `e4e0fdbe07a7d0a0ae4a70cbb6739a287c9d045a4a12b409895c220b5d91fe1e` |
| `Box.java` | `f788b8146b14f299ccb58ea0854609f845c298a963295d503de2e30e15d66f3a` | `f788b8146b14f299ccb58ea0854609f845c298a963295d503de2e30e15d66f3a` |
| `MathHelper.java` | `97aa2499681fdd8cee6564f777310ee396bc447fef78c2273a990e9202d9641b` | `579e05d110460f444bcac86729141545095cb5932c0f8d34c9a4dcfcd9a35e80` |
| `Vec3d.java` | `059dd21505c13b6febbbe880889f3aa6b00f7648ba1b770268eb8841674e90e8` | `059dd21505c13b6febbbe880889f3aa6b00f7648ba1b770268eb8841674e90e8` |

## Not owned by this slice

- General `isInWater()` as a movement witness is the stored body-water state; it is not the narrower result of eye/depth/submersion or swimming predicates.
- The 1.12.2-only deep `checkWaterState()` probe and its flying-water-avoiding mob-goal use are not a local-player path and are not the ordinary nonfalling AABB.
- The conditional falling-flow branch in `LiquidBlock.getFlow()` (`LEVEL >= 8`) is excluded. Its `isFaceSolid` dependency changes from the 1.11.2 implementation to the 1.12.2 face-shape implementation; record this as an out-of-slice candidate, not evidence of a nonfalling local-player difference.