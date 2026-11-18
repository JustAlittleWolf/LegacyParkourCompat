# 1.14.4 to 1.15: ordinary horizontal travel slice

## Bounded assignment

Compare exact Java 1.14.4 to 1.15 player travel arithmetic for an ordinary dry, unmounted, nonsprinting player on ground or in air, beginning with the `Vec3` consumed by `LivingEntity.travel`. This covers input acceleration, ground/air speed selection, and post-move horizontal drag. Yaw, `onGround`, and the block-friction coefficient/source position are dependencies supplied to this slice. It excludes the producers of the consumed movement vector, camera/input setup, swimming, lava, fall-flying, abilities flight, sprint mechanics/timers, vertical gravity and thresholds, collision/support/step behavior, and post-move environmental effects.

## Evidence identity and integrity

Both sides are exact decompiled Mojmap source trees. The declared `mojmap.ready.json` SHA-256 values match: 1.14.4 `ec7b6a6d9ba72f8a19908527977c970a8e2b3007b0e0b4323a725fb5943d3043`; 1.15 `c6b66b63bafaf2096ad7606111f1d0fae1f2178d624d6a8c9ded5ad3ac1aaa31`. Each marker declares its exact version, mapping, source count, source manifest digest, artifact manifest digest, and client hash. Both source manifest hashes match the marker declarations and the manifest files.

The 1.14.4 marker predates a provenance digest field. Its version-root `mojmap.provenance.json` (SHA-256 `9d1ad405e1e787d2fca5ed1897e6797b1f3cd79527da50d713232f46e2f30d97`) identifies an exact 1.14.4 Mojmap publication run, its mapping artifacts and tool versions. The corresponding `mojmap.verification.json` (SHA-256 `34f9ddace437422a36ea148767a1b72a6ff97a8166e404c6698154db730415d9`) states `verified-identical-source-and-input-artifacts`, 3,250 source files verified with zero source differences, and zero raw input artifact differences. The 1.15 `mojmap.provenance.json` digest matches its ready marker (`de4a538ebbaf82d1615683b08ecae3cd76553345f3079436764eb095be343949`). Ready marker identities and all six used Java file hashes match the declared values below.

| Release | Decompiled source file | SHA-256 (also matches `mojmap.sources.sha256`) |
| --- | --- | --- |
| 1.14.4 | `net/minecraft/world/entity/LivingEntity.java` | `428762178a876efd4069086e6b7d51f571ea0401f44e7eff0927b7d26d9ef681` |
| 1.14.4 | `net/minecraft/world/entity/Entity.java` | `31c6be42d165102d3e6dc4295fdfef6e8971fc3aea13f938a5b3a33b91d524a7` |
| 1.14.4 | `net/minecraft/world/entity/player/Player.java` | `e9ce5eb18c5581ffe2b610b273ffd85786587a00ba853e5dba0ac96593622b12` |
| 1.15 | `net/minecraft/world/entity/LivingEntity.java` | `139c3866ad2c6a1ad73496f22ff1a4b5259998442092ffbafb8664428e28bf79` |
| 1.15 | `net/minecraft/world/entity/Entity.java` | `191b3ad3e7348c9bac1e703fff896706d23a751bf15162aacf676f5f97c0a10e` |
| 1.15 | `net/minecraft/world/entity/player/Player.java` | `6985e11ad9be553e9a2f25e5decc20a9da5c3b59b68f4e83a5b9f448cd2e48a0` |

## Compared slices and findings

- `LivingEntity.travel`: 1.14.4 `LivingEntity.java:1782-1863`; 1.15 `LivingEntity.java:1831-1912`. In the ordinary dry branch, each version samples the provided friction coefficient `v`, chooses `w = onGround ? v * 0.91F : 0.91F`, calls `moveRelative(getFrictionInfluencedSpeed(v), vec3)`, then after movement writes X/Z as `vec38.x * w` and `vec38.z * w`. The expression order, float constants, branch gates, and horizontal writes are the same. The vertical work and other branches were not used to form this verdict.
- Speed selection helper: 1.14.4 `LivingEntity.java:1957-1963`; 1.15 `LivingEntity.java:2006-2012`. Both return `onGround ? getSpeed() * (0.21600002F / (f * f * f)) : flyingSpeed`, with the same float arithmetic and order. The default `flyingSpeed = 0.02F` is unchanged (`LivingEntity.java:143` vs `:150`). `Player.getSpeed()` remains the float cast of `MOVEMENT_SPEED` attribute value (`Player.java:1423-1426` vs `:1501-1504`). The ordinary `Player.aiStep` assignments resetting `flyingSpeed`, applying its sprint-only increment, and setting speed from the attribute are textually identical (`Player.java:516-521` vs `:520-525`); the sprint-only path is excluded.
- Movement-vector application: 1.14.4 `Entity.java:1080-1094`; 1.15 `Entity.java:1063-1077`. `moveRelative` adds the result of `getInputVector` to current delta movement in both releases. The vector-length threshold `1.0E-7`, `d > 1.0` normalization decision, scale, yaw radians conversion, sine/cosine, and X/Z rotation expressions are unchanged and in the same order. `getInputVector` changes from `protected static` to `private static` in 1.15; the reachable `Entity.moveRelative` player path and its resulting math are unchanged.
- `Player.travel`: 1.14.4 `Player.java:1380-1408`; 1.15 `Player.java:1456-1486`. In the ordinary nonswimming, non-flight player path, both delegate to `super.travel(vec3)`. Position getter spelling changed, but no movement acceleration or horizontal drag behavior changes here.
- The block-position sampling expression in 1.14.4 `LivingEntity.travel` (`new BlockPos(x, minY - 1.0, z)`) is replaced in 1.15 by `getBlockPosBelowThatAffectsMyMovement()`. The assignment supplies block coefficient source/location as a dependency, so this upstream sampling change is explicitly outside this arithmetic slice and is not a finding here.

## Bounded result and next gate

No independent horizontal travel arithmetic difference was found in this assigned slice; no canonical finding draft is warranted. This closes only the specified 1.14.4-to-1.15 horizontal arithmetic slice. Full-pair movement inventory, other discovery lanes, and independent full-pair audit remain with the coordinator; this report does not claim pair freeze or runtime parity.