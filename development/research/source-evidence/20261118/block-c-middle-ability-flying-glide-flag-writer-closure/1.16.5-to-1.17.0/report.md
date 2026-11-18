# Blind source note: ability-flight Elytra post-move flag writes

## Scope and result

Exact Mojmap source comparison: 1.16.5 → 1.17.0. Bounded to an ordinary local player in dry air, airborne, nonpassenger, with a usable default Elytra in the chest slot, while lawful creative/mayfly ability flight is active and shared flag 7 already marks fall-flying before this tick's travel. Result: **no difference found** in the post-move flag-7/fall-distance behavior. This is a bounded operation finding, not a full-pair coverage or runtime-parity claim.

## Reachable call path and writes

- `LocalPlayer.aiStep`: 1.16.5 `net/minecraft/client/player/LocalPlayer.java:705-721` toggles `abilities.flying` only when `mayfly` is allowed and the flight-toggle input reaches its second-press branch; 1.17 `.../LocalPlayer.java:729-745` has the same gates and assignments through `getAbilities()`. The ability toggle does not itself set/clear fall-flying flag 7.
- The same method reaches `super.aiStep()`: 1.16.5 line 788; 1.17 line 812. Inherited `LivingEntity.aiStep` calls `updateFallFlying()` and then `this.travel(...)`: 1.16.5 `.../LivingEntity.java:2457-2459`; 1.17 lines 2540-2542. That virtual call resolves to `Player.travel`.
- `LivingEntity.updateFallFlying()` validates an existing flag-7 state, airborne/nonpassenger/no-levitation, and enabled chest Elytra: 1.16.5 lines 2478-2495; 1.17 lines 2580-2603. Its write is guarded by `!level.isClientSide`, so this server-state recomputation is not the local client's post-move clear. It runs before `travel` in both versions.
- `Player.travel`: 1.16.5 `net/minecraft/world/entity/player/Player.java:1387-1416`; 1.17 `.../Player.java:1418-1447`. When `abilities.flying && !isPassenger()`, it saves the incoming vertical velocity and flying speed, sets creative flying speed, calls `super.travel(vec3)`, restores vertical velocity as `i * 0.6`, restores flying speed, sets `fallDistance = 0.0F`, then unconditionally calls `setSharedFlag(7, false)`. That final clear occurs after the superclass move and has no client-side or on-ground gate.
- Because the chest Elytra is active and flag 7 is true on entry, `LivingEntity.travel` takes its real `isFallFlying()` branch rather than ordinary land movement: 1.16.5 lines 1979-2021; 1.17 lines 2064-2106. It can set `fallDistance = 1.0F` when incoming vertical velocity is greater than `-0.5`, computes glide motion, and calls `move(MoverType.SELF, ...)` at line 2008 / 2093. The outer `Player.travel` wrapper then resets that fall distance and clears flag 7.
- The other flag-7 write inside the actual travel call is `LivingEntity.travel`'s `if (onGround && !level.isClientSide) setSharedFlag(7, false)`: 1.16.5 lines 2019-2020; 1.17 lines 2104-2105. It is server-only and on-ground-only, so it does not fire for the stated local airborne case. It is separate from—and does not replace—the unconditional `Player.travel` wrapper clear.

The relevant order and gates match across the pair. The glide horizontal-distance expression also remains the same multiplication-then-square-root: 1.16.5 uses `Math.sqrt(getHorizontalDistanceSqr(vec))` with `Entity.getHorizontalDistanceSqr` returning `x*x + z*z` (`Entity.java:709-711`); 1.17 uses `Vec3.horizontalDistance()` returning `Math.sqrt(x*x + z*z)` (`Vec3.java:131-133`).

## Exact source evidence

All listed relied-on Java files were SHA-256 checked against their version evidence manifests; the exact source-byte hashes are recorded below.

| Version | LocalPlayer.java | Player.java | LivingEntity.java | Supporting distance helper |
|---|---|---|---|---|
| 1.16.5 | `6011569e766bb1568609147be9aa14e9c08c51948e3d3a60fd066e848f6a8c2b` | `d2e26589bdb6a20dc914266db06aa48f50811efc792d6e63b3008c9199914960` | `b5d8a1a3c80f85d5d545b5a777e9e2a915dc002a7e31b5f5ad12bf7e285d7a88` | Entity.java `f9a9a073fe3105a0aa53d0f21ec72e59084e8d21a14c1cd3be75703865ee2666` |
| 1.17.0 | `08dbb8615cc6c7776f35ce269223699d39f758d15786babfe11e497421c5f4d4` | `9ad4c439814caf5f51cf6da9a86c5d22906b0a28b5b606bdeb1b770617d90516` | `8e81e5f1317d154fa41b676721ff581badd46296f77bcebcadb3c63448e528ed` | Vec3.java `eb52a20224767daeb5ddc500552c673df2daf9cd6868d7e74356411e94ef402e` |

These helper Java bytes were also checked against their respective source manifests; all hashes shown here are full SHA-256 values. Mapping family is Mojmap on both sides.

### 1.16.5 provenance availability

Present at the version root: `mojmap.ready.json`, `mojmap.provenance.json`, `mojmap.sources.sha256`, and `artifacts.sha256`. The ready marker identifies version `1.16.5`, mapping `mojmap`, status `ready`, source manifest SHA-256 `9499f2611d0e6dde37cb635f1a28a591416382d6bc188b37d5f99e2b2a20823b`, artifact manifest SHA-256 `f9b9812d6995012cefcc1201a63855e5931e8551b508753be6c46d442d8b370c`, and client SHA-256 `00b5ebbc33e95ea88c1ab80601599c9827e9aa861d93ccc2cfcbbfd996e86263`. Both manifest file hashes match the ready marker; all relied-on Java bytes match `mojmap.sources.sha256`. Provenance records Mojmap and Vineflower 1.12.0.

### 1.17.0 provenance availability

Present at the hidden mapping root: `decompiled_minecraft/1.17/mojmap/.decompile-provenance.json`. It contains per-source hashes; all relied-on Java bytes above match them. The client and `client_mappings.txt` cache artifacts and their `.pin.json` sidecars are present, and both actual bytes match the sidecar SHA-256 values: client `14122287d4e4256c2d65ec8c0ccfa3b56448495fa3ccabc87d0f0eeb4fa1c30f`; Mojmap mapping file `191b957aaa40b294402feef29651ae7a9f1263ec22c5382f3b8fa0a4918b0471`. The hidden provenance inputs agree with those pins. The four legacy version-root marker files listed for 1.16.5 are unavailable for 1.17; no separate 1.17 `mojmap.sources.sha256` or `artifacts.sha256` was found. The mapping-root provenance plus exact cache pins supply the available evidence.