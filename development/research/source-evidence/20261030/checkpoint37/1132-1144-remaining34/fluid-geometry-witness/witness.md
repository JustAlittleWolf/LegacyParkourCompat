# 1.13.2 → 1.14.4 stacked-water eye witness

Source pair: canonical Ornithe Feather Gen2 build 2 trees at `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\1.13.2\ornithe-feather` and `...\1.14.4\ornithe-feather`.

## Static state and player geometry

Use a standing, unmounted, awake player at `(x, y, z) = (0.5, 0.0, 0.5)`. In both versions the standing player box is `[0.2, 0.8] × [0, 1.8] × [0.2, 0.8]`: 1.13.2 `Entity.java:186–203, 309–315` initializes width/height to `0.6F/1.8F` and centers that box on x/z; 1.14.4 `PlayerEntity.java:109–117, 1963–1966` gives `Pose.STANDING` dynamic `0.6F × 1.8F`, and `Entity.java:308–315` constructs the same box. The old standing eye-height branch returns `1.62F` (`PlayerEntity.java:1859–1869`). The new constructor records the standing-pose eye height (`Entity.java:208–210`); the player override returns `1.62F` for the standing default (`PlayerEntity.java:1802–1812`), exposed by `Entity.getEyeHeight()` (`:2320–2322`). Thus the eye point is `(0.5, 1.62F, 0.5)`, and the eye query's floored block position is `(0,1,0)` (`BlockPos` delegates to `Vec3i(double,double,double)`, which floors each coordinate; old/new `Vec3i.java:19–21`).

In that static world snapshot, set `(0,1,0)` to flowing water with `LEVEL=4`, `FALLING=false`, and `(0,2,0)` to source water. `LEVEL_8` admits 4 (old/new `Properties.java:84` / `:93`); `WaterFluid.Flowing` includes `LEVEL` in its state definition and reads it as the state level (old/new `WaterFluid.java:118–127` / `:122–131`). Flowing and source water satisfy the same-water relation: `WaterFluid.is` accepts both `Fluids.WATER` and `Fluids.FLOWING_WATER` (old/new `WaterFluid.java:94–96` / `:98–100`). The lower state is WATER-tagged as required by the query. This is a valid assignment of vanilla fluid states; this witness does not assert that ordinary fluid ticks produce or preserve this column.

## Predicate results

The old query (`Entity.isSubmergedIn`, `Entity.java:1042–1051`) and new query (`Entity.java:1035–1052`, called with `bl=false`) both reject boats, read the fluid state at the eye block, require the supplied tag, then use strict `<`. For this level-4 flowing state, the old height is `4 / 9.0F` (`FlowingFluid.java:448–450`). Its threshold is therefore `1 + (4/9 + 0.11111111) ≈ 1.5555556`; `1.62 < 1.5555556` is false. In 1.14.4, `FlowingFluid.getHeight(state, world, pos)` returns `1.0F` when the same water family is at `pos.up()` (`FlowingFluid.java:442–449`), so the threshold is `1 + (1 + 0.11111111) ≈ 2.1111112`; `1.62 < 2.1111112` is true. New raw-height fallback remains level/9 (`:452–454`). The eye-submersion result thus differs for this standing player and static fluid assignment.

## Body scan and scope limit

The separate body-fluid scan contracts the player box by `0.001`, floors minima, ceils maxima, and uses upper-exclusive x/y/z loops; its contact test is inclusive `surface >= contractedBox.minY` (old `Entity.java:2509–2537`, new `:2563–2591`). For this box the contracted bounds are `[0.201,0.799] × [0.001,1.799] × [0.201,0.799]`, giving loop indices `x=0`, `y=0,1`, `z=0`. At the lower water cell `(0,1,0)`, old surface is `1 + 4/9 ≈ 1.44444445`, and new stacked surface is `2`; both satisfy `>= 0.001`. So the body scan returns contact in both versions for this same snapshot; it is not the witness delta. Assume the queried area is loaded so either scan reaches its cell loops.

This closes a static eye-predicate witness only. It does not establish normal-tick reachability, the production of this exact fluid arrangement, a same-tick cache refresh, or a divergent player trajectory.

## Whole-file SHA-256

| Relative source | 1.13.2 | 1.14.4 |
|---|---|---|
| `net/minecraft/entity/Entity.java` | `1d6ec8b80f74635401745c2c027bf36555c85348ca5693764f2668363b17d269` | `7315a496c195da767de9d4936d3adb6efc3c419dc0f0e95d6f32781b0da1ba55` |
| `net/minecraft/entity/living/player/PlayerEntity.java` | `4ed22f6c5a3c55d67eed782070ac722201df4d624adbc90779f1fd29c2876633` | `2614deb3b50d11f6cdf54d9e308703bf49cb53b72646b05253857e9557d657df` |
| `net/minecraft/fluid/FlowingFluid.java` | `2398d109ddc5785cb1bc8872b8c157319137df8957faf8a03581a8f68afb1253` | `6afb033d18e6d28ff6d7deda88f5f1432914903b0fef100c44fb8372d3e148ce` |
| `net/minecraft/fluid/WaterFluid.java` | `183894d0a4f7a85becd97eb2c0f6cedc1d5adc4648dc6d3d2b5624df173df28b` | `ed5a28c7f4370ef8f7217dae482fbb1e28296f08c1d70944a1a690bf5f6bb13f` |
| `net/minecraft/state/property/Properties.java` | `3882c1b832ac8b2b16f5a83978da441ab03cdfe66f053a873342758045827f4c` | `9b524af5065100919c60667778e38986380c9df954ef70a9d0ac0a739132ddca` |
| `net/minecraft/util/math/Vec3i.java` | `481827f072a5d36ae3a7f133afed5396988e3ce7c0501b2777a92b60c6a498b8` | `f1d2e85d690b69c546b2dc783ede2ddbbfac400ca72271828929a45db03729a9` |
