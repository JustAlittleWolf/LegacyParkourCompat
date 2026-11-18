# Independent audit verdict: ability-flight Elytra glide clear

**Verdict: ACCEPTED — no difference found for the assigned bounded operation.**

Auditor: `/root/c_mid_1165_117_ability_flight_glide_clear_audit`  
Scope: exact Mojmap 1.16.5 → 1.17.0; local player, dry air, airborne, nonpassenger, enabled default chest Elytra, lawful `mayfly` ability flight active, fall-flying flag 7 set on entry. This verdict covers the described travel path only. It does not claim complete pair coverage or runtime parity.

## Independent trace

- `LocalPlayer.aiStep` toggles `abilities.flying` only under `mayfly` gates (or sets it for always-flying game modes); its call to `super.aiStep()` enters `LivingEntity.aiStep`. In both releases `LivingEntity.aiStep` calls `updateFallFlying()` before virtual `this.travel(...)`, which reaches `Player.travel`.
- `LivingEntity.updateFallFlying()` checks existing flag 7, airborne/nonpassenger state, no Levitation, and an enabled chest Elytra. Its recomputation write to flag 7 is guarded by `!level.isClientSide`; it is separate from the client-side post-move write under audit.
- `Player.travel`, under `abilities.flying && !isPassenger()`, saves vertical velocity and flying speed, sets ability flight speed, calls `super.travel`, restores vertical velocity as the saved value times `0.6`, restores flying speed, sets `fallDistance = 0.0F`, then directly calls `setSharedFlag(7, false)`. The final two writes occur after the superclass movement and have no on-ground or logical-side gate. This wrapper and write order match in both versions.
- On the stated dry, airborne, nonpassenger path with flag 7 active, `LivingEntity.travel` reaches the `isFallFlying()` branch and performs `move(MoverType.SELF, ...)`. That branch may set `fallDistance = 1.0F` when the incoming vertical velocity exceeds `-0.5`; the outer player wrapper subsequently resets it to zero and clears flag 7.
- The separate `LivingEntity.travel` clear (`onGround && !level.isClientSide`) cannot clear flag 7 in this local airborne case. It is not the `Player.travel` post-move clear.
- The relevant glide horizontal-distance calculation is equivalent: 1.16.5 takes `Math.sqrt(x*x + z*z)` through `Entity.getHorizontalDistanceSqr`; 1.17.0's `Vec3.horizontalDistance()` returns `Math.sqrt(x*x + z*z)`. The active glide movement and wrapper post-move state therefore show no difference for this trace.

Source locations: `LocalPlayer.aiStep` 1.16.5 lines 705–721 and 788; 1.17.0 lines 729–745 and 812. `LivingEntity.aiStep` calls `updateFallFlying()` then `this.travel(...)` at 1.16.5 lines 2457–2459 and 1.17.0 lines 2540–2542. `updateFallFlying()` is at 1.16.5 lines 2478–2497 and 1.17.0 lines 2580–2605. `Player.travel` is at 1.16.5 lines 1387–1416 and 1.17.0 lines 1418–1447. The actual glide branch is at 1.16.5 lines 1979–2021 and 1.17.0 lines 2064–2106. The separate helper implementations are `Entity.getHorizontalDistanceSqr` 1.16.5 lines 709–711 and `Vec3.horizontalDistance` 1.17.0 lines 131–133.

## Exact source identity

The full 1.16.5 source inventory (3,523 files) and full 1.17.0/Mojmap inventory (4,141 files) were independently checked against their source hashes; all entries matched. The 1.16.5 raw cache directory is absent, so this audit relies on its version-root readiness/provenance files and source bytes, not a claimed local raw-cache recheck.

### 1.16.5 Mojmap

- `mojmap.ready.json` SHA-256: `e9dd4397c6ade5f68d27f50baf285086dc367b56d2119b03a116cf3cf3b23fee`; status `ready`, version `1.16.5`, mapping `mojmap`.
- `mojmap.sources.sha256` SHA-256: `9499f2611d0e6dde37cb635f1a28a591416382d6bc188b37d5f99e2b2a20823b`; matches the readiness marker.
- `artifacts.sha256` SHA-256: `f9b9812d6995012cefcc1201a63855e5931e8551b508753be6c46d442d8b370c`; matches the readiness marker.
- `mojmap.provenance.json` SHA-256: `b71df61eb775134119881915198f87c4337bae409e1f0de8d8d6c007e062902d`.
- Relied source SHA-256 values: `LocalPlayer.java` `6011569e766bb1568609147be9aa14e9c08c51948e3d3a60fd066e848f6a8c2b`; `Player.java` `d2e26589bdb6a20dc914266db06aa48f50811efc792d6e63b3008c9199914960`; `LivingEntity.java` `b5d8a1a3c80f85d5d545b5a777e9e2a915dc002a7e31b5f5ad12bf7e285d7a88`; `Entity.java` `f9a9a073fe3105a0aa53d0f21ec72e59084e8d21a14c1cd3be75703865ee2666`.

### 1.17.0 Mojmap (cache release ID `1.17`)

- Hidden mapping-root manifest: `decompiled_minecraft/1.17/mojmap/.decompile-provenance.json`, SHA-256 `afc9dc30cd8f6406d8ea306e64cc0b3601cc8d7c7bcd314f737573d830a79357`. All 4,141 listed source bytes matched.
- Local pinned `version.json`, `client.jar`, and `client_mappings.txt` bytes all match their `.pin.json` SHA-256 values and the hidden provenance inputs: `537c6fa7e6ab13caa76ce2e84e4748882d36fcd5a076fa6903f894880e1df0f6`, `14122287d4e4256c2d65ec8c0ccfa3b56448495fa3ccabc87d0f0eeb4fa1c30f`, and `191b957aaa40b294402feef29651ae7a9f1263ec22c5382f3b8fa0a4918b0471`, respectively.
- Relied source SHA-256 values: `LocalPlayer.java` `08dbb8615cc6c7776f35ce269223699d39f758d15786babfe11e497421c5f4d4`; `Player.java` `9ad4c439814caf5f51cf6da9a86c5d22906b0a28b5b606bdeb1b770617d90516`; `LivingEntity.java` `8e81e5f1317d154fa41b676721ff581badd46296f77bcebcadb3c63448e528ed`; `Vec3.java` `eb52a20224767daeb5ddc500552c673df2daf9cd6868d7e74356411e94ef402e`.

### Bound independent author report

Replacement report SHA-256: `63ae9f499583155195824af36535fe63cd62ebc3c29d0e9ece6374c1219064e0`. Its bounded conclusion and reachable path are supported by the independently checked exact source bytes above.
