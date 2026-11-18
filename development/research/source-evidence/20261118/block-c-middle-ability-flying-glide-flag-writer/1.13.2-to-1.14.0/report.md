# 1.13.2 → 1.14.0 Feather source comparison: ability flight clears fall-flying

## Evidence identity and readiness

- Host/execution environment: Windows host, Windows PowerShell; canonical shared checkout `D:\Javastuff\LegacyParkourCompat`.
- Compared exact releases: `1.13.2` and `1.14` (Minecraft's exact 1.14.0 release identifier).
- Mapping family: Ornithe Feather on both sides: `feather-gen2-1.13.2+build.2` (`ornithe-feather`) and `net.ornithemc:feather-gen2:1.14+build.2` (`ornithe-feather`).
- Both canonical trees have ready markers and provenance manifests. The 1.13.2 ready marker records 2,711 source files and manifest SHA-256 `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211`; the 1.14 ready marker records 3,152 files and manifest SHA-256 `68bc39e38bc501e7c9af315de4678263e1a64606b0b9780099187c7984ccbc98`.
- Provenance SHA-256: 1.13.2 `e25950b7855fc4f2aee1a662f0be6b0af9f28788d9e27293075cfc5a3aa5d383`; 1.14 `bfb011801a64e9afaa4aca43e8a8ed40bf2a02f136ffc02e65b32e79315764fa`. Provenance records both exact client versions, Feather coordinates/artifacts, and the same Java 25.0.3, Gradle 9.7.1, Vineflower 1.12.0, ASM 9.10.1, Mapping IO 0.9.1, Gson 2.14.0, and Tiny Remapper 0.14.1 tool versions.
- Source-hash manifest entries were checked against the on-disk PlayerEntity and LocalClientPlayerEntity source files used below. (LivingEntity manifest entries also identify the tick call sites consulted.)

## Bounded trace

In both releases, `LocalClientPlayerEntity` permits a nonspectator with `abilities.canFly` to toggle `abilities.flying` on a second jump when `!isSwimming()`, synchronizing abilities and clearing the double-jump timer. That branch does not require being grounded and does not test `isFallFlying()`: 1.13.2 `LocalClientPlayerEntity.java:760–774`; 1.14 `LocalClientPlayerEntity.java:692–706`. Thus an already fall-flying creative player can enter ability flight while airborne if the local input reaches this branch.

Both releases' LivingEntity movement tick calls `flyingTick()` and then dispatches `moveRelative(...)`: 1.13.2 `LivingEntity.java:1920–1927`; 1.14 `LivingEntity.java:2214–2221`. In the client, `flyingTick()` does not rewrite flag 7 (the write is guarded by `!world.isClient`); its Elytra validity gate requires flag 7, airborne, not riding, and an active chest Elytra: 1.13.2 `LivingEntity.java:1938–1956`; 1.14 `LivingEntity.java:2232–2250`.

The player override then takes the same ability-flight branch when `abilities.flying && !isRiding()`. It applies relative movement using flight speed, restores the vertical component to its saved value times `0.6`, restores `speedInAir`, then writes `fallDistance = 0.0F` followed by `setFlag(7, false)`. This is unconditional on the chest slot or current glide state once the branch is selected: 1.13.2 `PlayerEntity.java:1442–1465`; 1.14 `PlayerEntity.java:1363–1388`. The direct flag-7 clear follows the ability-flight `super.moveRelative(...)` call; the separate fall-distance reset immediately precedes it. No condition on fall distance controls the glide clear.

## Comparison result

No difference found for this bounded operation. In both exact releases, entering the non-riding creative ability-flight path while fall-flying clears the glide bit after relative movement and resets fall distance to zero in the same order. For the stated ordinary dry, airborne, nonpassenger local player with Elytra equipped, `flyingTick()` leaves flag 7 intact client-side before the player override; the ability-flight override clears it in that tick. This is source behavior only; no runtime trajectory was tested.

One observable adjacent source change is the representation/API of the movement vector across the mapping/release transition: 1.13.2 uses scalar `float forwards, sideways, upwards` and velocity fields, while 1.14 passes `Vec3d` and reads/writes velocity through accessors. The values/order relevant to this branch remain the same: saved Y velocity × `0.6`, speed restore, fall-distance reset, flag-7 clear. This does not make the branch a new 1.14 behavior.

## Exact source files and SHA-256

Canonical source root: `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\`.

| Release / relative source | SHA-256 |
| --- | --- |
| 1.13.2 `ornithe-feather/net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java` | `2583495f3a02b4791aa036e6a8d354d7596c4984761969a0f29b29d8d9bf42bf` |
| 1.13.2 `ornithe-feather/net/minecraft/entity/living/LivingEntity.java` | `bb691358c9a43c9f46e85575bf4d0a4ad671d0eb912502acc3a6a3f625e42f1c` |
| 1.13.2 `ornithe-feather/net/minecraft/entity/living/player/PlayerEntity.java` | `4ed22f6c5a3c55d67eed782070ac722201df4d624adbc90779f1fd29c2876633` |
| 1.14 `ornithe-feather/net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java` | `2b1ad3b3416949a9da2607a3ec2251aa69b2d9eebd76638c92e20749d625338f` |
| 1.14 `ornithe-feather/net/minecraft/entity/living/LivingEntity.java` | `1db4dd1aa95a06c48511e5567c0d3836f2a6e5a12a5cf03ab5c892e429198b79` |
| 1.14 `ornithe-feather/net/minecraft/entity/living/player/PlayerEntity.java` | `f73879bd42103fa45f39cfe178ac82c7574c6306f00555af7f7e795976275dd7` |

## Coverage and limits

Examined local creative-flight entry, LivingEntity tick ordering and client/server `flyingTick()` flag write, and PlayerEntity ability-flight relative-movement branch for this state path. This report closes only the assigned operation, not the full player tick pair. No wiki, product implementation, backlog, Git history, build, test, launch, network lookup, or source generation was consulted or performed.
