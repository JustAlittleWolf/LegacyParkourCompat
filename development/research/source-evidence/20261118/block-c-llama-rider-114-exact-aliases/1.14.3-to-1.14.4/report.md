# 1.14.3–1.14.4 Llama passenger positioning (intermediate report)

## Evidence identity and scope

The canonical source tree has `decompiled_minecraft/1.14.3/yarn/` only; it has no 1.14.3 Mojmap directory. Its hidden `.decompile-provenance.json` pins client artifact SHA-256 `4063062677433988e012fba53e42e4c21adb6919ac7f061f19f3c6e75b334e07`, Yarn `1.14.3+build.13` artifact SHA-256 `ad9e77f907cfc6780fb348ad4b3a948ffc8cea3bfe50abcc792428645e49e06e`, and intermediary `1.14.3` artifact SHA-256 `f2e9990c00fa87ddc7158a95afb735a947ae26d1997b52916226e11c612542a9`. Hidden provenance file SHA-256: `81ea0021eb122d4e3340e20d47d6ecb7079365ba767a038d26a8264cddacc28a`.

The canonical 1.14.4 source is `decompiled_minecraft/1.14.4/mojmap/`. `mojmap.provenance.json` identifies version `1.14.4`, namespace `mojmap`, exact run batch `1.14.4`, and mapping option `feather,mojmap`. Provenance SHA-256: `9d1ad405e1e787d2fca5ed1897e6797b1f3cd79527da50d713232f46e2f30d97`. Source manifest SHA-256: `af98406f3d4ed31494fbdce1078d1f3df3f712501878f9a0e9ad5879bdf23c3b`; artifact manifest SHA-256: `308cc33ef6dffc047432ade6affc88eccc9de94a736ee9a98571fd92915c2970`. The recorded verification says 3,250 source files verified, zero source differences, and matching source-manifest digests.

Following the coordinator's authorization, this is a manual comparison of the exact local Yarn 1.14.3 source with exact local Mojmap 1.14.4 source. The two namespace labels are retained below; this is not a 1.14.3 Mojmap comparison and does not claim one exists.

## Operation and comparison

- 1.14.3 Yarn, `net/minecraft/entity/passive/LlamaEntity.java:147–158`: `updatePassengerPosition(Entity)` proceeds only under `hasPassenger(arg)`. It computes `float f = cos(field_6283 * (float)(PI / 180.0))`, `float g = sin(...)`, then writes `x + 0.3F * g`, `y + getMountedHeightOffset() + arg.getHeightOffset()`, `z - 0.3F * f`. The Llama override returns `getHeight() * 0.67` (`double`) at line 158. The local `float h = 0.3F` is unused.
- 1.14.4 Mojmap, `net/minecraft/world/entity/animal/horse/Llama.java:150–161`: `positionRider(Entity)` has the same `hasPassenger(entity)` guard, float rotation inputs/conversion, `0.3F` horizontal products, and ordered coordinates. Its Y expression is `y + getRideHeight() + entity.getRidingHeight()`; the Llama override returns `getBbHeight() * 0.67` (`double`). The local `float h = 0.3F` is also unused.
- Player-specific vertical offset is `-0.35` (`double`) on both sides: 1.14.3 Yarn `PlayerEntity.getHeightOffset()` at `PlayerEntity.java:953–955`; 1.14.4 Mojmap `Player.getRidingHeight()` at `Player.java:969–971`. Thus the scoped Y addition remains Llama height offset first, then Player offset, with the same double-valued terms and order. No source-level Y-position delta was found for an adult default Llama carrying one direct Player passenger.
- Callback dispatch: 1.14.3 Yarn `Entity.tickRiding()` (`Entity.java:1564–1569`) calls `getVehicle().updatePassengerPosition(this)` after the base tick when `hasVehicle()`. 1.14.4 Mojmap `Entity.rideTick()` (`Entity.java:1584–1589`) calls `getVehicle().positionRider(this)` after the base tick when `isPassenger()`. Both are virtual calls, reaching the Llama override. `TraderLlamaEntity` extends `LlamaEntity` (Yarn `TraderLlamaEntity.java:20`) and `TraderLlama` extends `Llama` (Mojmap `TraderLlama.java:21`); neither declares a passenger-position override, so both inherit it.
- Player parent/caller guard: 1.14.3 Yarn `PlayerEntity.tickRiding()` only takes the stop-riding branch when `!world.isClient && isSneaking() && hasVehicle()` (`PlayerEntity.java:438–442`); otherwise it calls `super.tickRiding()` (`:448`). 1.14.4 Mojmap `Player.rideTick()` has the equivalent `!level.isClientSide && isSneaking() && isPassenger()` guard (`Player.java:455–459`) and otherwise calls `super.rideTick()` (`:465`). The normal logical-client path therefore reaches the vehicle callback; the server-only dismount guard is not entered on that path. In both versions the Llama callback separately checks that the entity remains a passenger of that Llama.

## Relevant source-byte SHA-256 (computed with PowerShell `Get-FileHash -Algorithm SHA256`)

The 1.14.3 values also match the entries in the hidden source provenance; the 1.14.4 values match `mojmap.sources.sha256`.

| Exact local source | SHA-256 |
|---|---|
| 1.14.3 Yarn `net/minecraft/entity/passive/LlamaEntity.java` | `a4d400d4358b420aee1a18da14390542fe09c23817eb53864917643570a39f2c` |
| 1.14.3 Yarn `net/minecraft/entity/passive/TraderLlamaEntity.java` | `82bd87f3040baa3eb08b8f19cc875121a1aeb899e1cb569d05afad4ad06615e6` |
| 1.14.3 Yarn `net/minecraft/entity/Entity.java` | `9b204a5f9af6ca50c09db4b6185ff37c39980c1a8cd1b5fd004713ecffae5c14` |
| 1.14.3 Yarn `net/minecraft/entity/player/PlayerEntity.java` | `e3f361ac4e369d01473f3d84044ee33f9685d182c4485e2e80b7f62756950963` |
| 1.14.4 Mojmap `net/minecraft/world/entity/animal/horse/Llama.java` | `3e62e517a4bea2723cb4fd401d00a1de128e2dc1d5bd18dc5fd9e612449f296f` |
| 1.14.4 Mojmap `net/minecraft/world/entity/animal/horse/TraderLlama.java` | `518a563fd44446b53c324be7c7cf8a0cce203901212720eef2454319a09ec9c9` |
| 1.14.4 Mojmap `net/minecraft/world/entity/Entity.java` | `31c6be42d165102d3e6dc4295fdfef6e8971fc3aea13f938a5b3a33b91d524a7` |
| 1.14.4 Mojmap `net/minecraft/world/entity/player/Player.java` | `e9ce5eb18c5581ffe2b610b273ffd85786587a00ba853e5dba0ac96593622b12` |

**Disposition:** scoped behavior appears unchanged across the exact local 1.14.3 Yarn and 1.14.4 Mojmap sources. This is a bounded intermediate comparison, not an independent full-pair tick audit or a 1.14.3 Mojmap claim.
