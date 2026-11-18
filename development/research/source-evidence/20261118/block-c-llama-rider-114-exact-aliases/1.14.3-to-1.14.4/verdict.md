# Independent source audit: 1.14.3–1.14.4 Llama passenger Y positioning

## Verdict

**Accepted: no source-level Y-position delta** for one direct local `PlayerEntity`/`Player` passenger on a default adult `Llama` or inherited `TraderLlama`, across exact 1.14.3 Yarn and 1.14.4 Mojmap sources. This verdict covers only this operation and the checked dispatch chain; it is not full-pair tick coverage, does not establish an intermediate cutover, and makes no claim that 1.14.3 Mojmap exists.

The checked Llama callbacks use the same Y expression shape and operand order: vehicle Y, Llama mounted/ride-height, then passenger height offset. The vehicle term is `double` (`getHeight() * 0.67` / `getBbHeight() * 0.67`); the Player term is `double` (`-0.35`). The player-specific operand is unchanged. Both callbacks guard on direct passenger membership. The Player parent method reaches the base ride tick on the logical client, where the base entity calls the vehicle callback virtually. `TraderLlama` inherits the Llama callback in both releases and declares no replacement callback. No actor/vehicle-physics behavior was used to reach this verdict.

## Immutable input

Frozen claim/evidence: `c-llama-1143-1144-position-intermediate/report.md`, SHA-256 `41edfcdb3a079dfd5f932f7a3c7d6fc580f219afac87fbc020dc2ddca52aa303` (verified against the supplied identity).

## Exact source identities and provenance

- **1.14.3, Yarn `1.14.3+build.13`**, canonical read-only root `decompiled_minecraft/1.14.3/yarn/`. The root contains Yarn only; no 1.14.3 Mojmap tree was present. `.decompile-provenance.json` SHA-256: `81ea0021eb122d4e3340e20d47d6ecb7079365ba767a038d26a8264cddacc28a`. Its inputs identify the 1.14.3 client artifact SHA-256 `4063062677433988e012fba53e42e4c21adb6919ac7f061f19f3c6e75b334e07`, Yarn build.13 artifact `ad9e77f907cfc6780fb348ad4b3a948ffc8cea3bfe50abcc792428645e49e06e`, and intermediary 1.14.3 artifact `f2e9990c00fa87ddc7158a95afb735a947ae26d1997b52916226e11c612542a9`.
- **1.14.4, Mojmap**, canonical read-only root `decompiled_minecraft/1.14.4/mojmap/`. Root provenance SHA-256: `9d1ad405e1e787d2fca5ed1897e6797b1f3cd79527da50d713232f46e2f30d97`; it identifies `versionId=1.14.4`, `namespace=mojmap`, exact batch `1.14.4`, mapping option `feather,mojmap`, and Feather build.2 mapping artifacts. Source-manifest SHA-256: `af98406f3d4ed31494fbdce1078d1f3df3f712501878f9a0e9ad5879bdf23c3b`; artifact-manifest SHA-256: `308cc33ef6dffc047432ade6affc88eccc9de94a736ee9a98571fd92915c2970`. The local verification metadata identifies 3,250 verified source files, zero source differences, and identical regenerated/published source-manifest hashes.

## Independent semantic retrace

- 1.14.3 Yarn `net/minecraft/entity/passive/LlamaEntity.java:147–158`, `updatePassengerPosition(Entity)`: after `hasPassenger(arg)`, assigns `y + getMountedHeightOffset() + arg.getHeightOffset()` as the Y argument to `setPosition`. The Llama override at line 158 returns `getHeight() * 0.67` (`double`). Player `net/minecraft/entity/player/PlayerEntity.java:953–955`, `getHeightOffset()`, returns `-0.35` (`double`).
- 1.14.4 Mojmap `net/minecraft/world/entity/animal/horse/Llama.java:150–161`, `positionRider(Entity)`: after `hasPassenger(entity)`, assigns `y + getRideHeight() + entity.getRidingHeight()` as the Y argument to `setPos`. The Llama override at line 161 returns `getBbHeight() * 0.67` (`double`). Player `net/minecraft/world/entity/player/Player.java:969–971`, `getRidingHeight()`, returns `-0.35` (`double`).
- The enclosing arithmetic retains the same float yaw conversion and `0.3F` horizontal products; the unused `float h = 0.3F` local appears on both sides. These do not alter the Y comparison.
- Dispatch is `PlayerEntity.tickRiding()` → `Entity.tickRiding()` → virtual `vehicle.updatePassengerPosition(this)` in 1.14.3, and `Player.rideTick()` → `Entity.rideTick()` → virtual `vehicle.positionRider(this)` in 1.14.4. Each player method only stops riding for sneaking when `!world.isClient` / `!level.isClientSide`; the logical-client path calls `super`. Each base entity dispatch is guarded by `hasVehicle()` / `isPassenger()`.
- `TraderLlamaEntity extends LlamaEntity` (1.14.3 `TraderLlamaEntity.java:20`); `TraderLlama extends Llama` (1.14.4 `TraderLlama.java:21`). Neither class declares a passenger-position override.

## Verified source-byte SHA-256

Every listed hash matched the local file and, where present, its source manifest/provenance entry.

| Version / namespace | Relative source file | SHA-256 |
|---|---|---|
| 1.14.3 Yarn | `net/minecraft/entity/passive/LlamaEntity.java` | `a4d400d4358b420aee1a18da14390542fe09c23817eb53864917643570a39f2c` |
| 1.14.3 Yarn | `net/minecraft/entity/passive/TraderLlamaEntity.java` | `82bd87f3040baa3eb08b8f19cc875121a1aeb899e1cb569d05afad4ad06615e6` |
| 1.14.3 Yarn | `net/minecraft/entity/Entity.java` | `9b204a5f9af6ca50c09db4b6185ff37c39980c1a8cd1b5fd004713ecffae5c14` |
| 1.14.3 Yarn | `net/minecraft/entity/player/PlayerEntity.java` | `e3f361ac4e369d01473f3d84044ee33f9685d182c4485e2e80b7f62756950963` |
| 1.14.4 Mojmap | `net/minecraft/world/entity/animal/horse/Llama.java` | `3e62e517a4bea2723cb4fd401d00a1de128e2dc1d5bd18dc5fd9e612449f296f` |
| 1.14.4 Mojmap | `net/minecraft/world/entity/animal/horse/TraderLlama.java` | `518a563fd44446b53c324be7c7cf8a0cce203901212720eef2454319a09ec9c9` |
| 1.14.4 Mojmap | `net/minecraft/world/entity/Entity.java` | `31c6be42d165102d3e6dc4295fdfef6e8971fc3aea13f938a5b3a33b91d524a7` |
| 1.14.4 Mojmap | `net/minecraft/world/entity/player/Player.java` | `e9ce5eb18c5581ffe2b610b273ffd85786587a00ba853e5dba0ac96593622b12` |

## Limits

This manual retrace audited the specified Llama passenger Y operation and its direct Player dispatch/inheritance path only. It did not inspect product code, Git history, wiki evidence, other actors, vehicle physics, the full player tick, or runtime behavior.
