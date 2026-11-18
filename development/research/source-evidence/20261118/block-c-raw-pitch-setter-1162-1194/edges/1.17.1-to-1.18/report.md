# Blind source comparison: direct `Entity.turn` pitch response

## Scope and source readiness

Compared the canonical Mojmap source trees for Minecraft 1.17.1 and 1.18.0 (the canonical directory ID is `1.18`):

- `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\1.17.1\mojmap`
- `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\1.18\mojmap`

The relevant `Entity.java`, `Mth.java`, `LivingEntity.java`, `Player.java`, `AbstractClientPlayer.java`, and `LocalPlayer.java` files exist, and the compared method bodies are present. Neither mapping tree contains `.decompile-provenance.json`. Consequently, their release/mapping labels and source bytes are present in the canonical locations, but there is no manifest here to attest the pinned inputs, exact mapping artifact, decompiler pipeline, or publication identity. I did not regenerate sources. The conclusion below is an exact comparison of these current canonical bytes, with that provenance limitation open.

## Compared behavior

**1.17.1 Mojmap**, `net/minecraft/world/entity/Entity.java`: `turn(double d, double e)`, lines 377–389; `getXRot`/`setXRot`, lines 3019–3028.

**1.18.0 Mojmap**, `net/minecraft/world/entity/Entity.java`: `turn(double $$0, double $$1)`, lines 383–395; `getXRot`/`setXRot`, lines 3010–3020.

In each `turn`, the pitch delta is first rounded to `float` and scaled as `(float)deltaPitch * 0.15F`. The method then calls `setXRot(getXRot() + pitchDelta)`, calls `setYRot` with the similarly cast/scaled yaw delta, and clamps current pitch with `Mth.clamp(getXRot(), -90.0F, 90.0F)`. It next adds the same pitch/yaw deltas to `xRotO`/`yRotO`, clamps `xRotO`, and notifies a vehicle when present. Operation order, casts, constants, setter calls, and current-pitch/old-pitch clamps match; only local variable names differ.

Both `Entity.setXRot(float)` implementations reject non-finite values and otherwise assign the value. Both `Mth.clamp(float, float, float)` implementations use the same `< min`, then `> max`, else return-input procedure. This matters if a finite `double` casts or scales to infinity, or if adding a finite float overflows: the first setter rejects that non-finite candidate in both releases, after which the same final clamp is applied to the unchanged pitch.

**Result:** no source difference in direct `Entity.turn` pitch response or its authoritative pitch setter/clamp path was found in this pair. A finite large-input witness does not distinguish these releases. For example, from pitch `0.0F`, the ordinary public call `turn(0.0, 1000.0)` computes finite `150.0F`, accepts it at the first setter, then clamps current pitch to `90.0F` in both versions. This witness uses finite arguments and does not depend on device callback timing.

## Reachability of active Elytra movement

`LocalPlayer` extends `AbstractClientPlayer`, which extends `Player`, which extends `LivingEntity`; neither local-player class declares a `turn` override, so the direct ordinary local-player call resolves to `Entity.turn`. `Player.tryToStartFallFlying()` in both releases accepts an airborne player with no Elytra flight already active, no water contact, no Levitation, and an enabled Elytra in the chest slot, then sets the fall-flying flag. On a subsequent eligible tick, `LivingEntity.aiStep` calls `updateFallFlying()` and then `travel(...)`; `LivingEntity.travel` selects its `isFallFlying()` branch. That branch computes radians from current `getXRot()` and uses them in the glide equations (the pitch-derived cosine and, for nose-down pitch, pitch sine). `Player.travel` delegates to `super.travel` when the player is not in abilities flight. Thus the finite witness can feed actual active Elytra movement with `90.0F` pitch in both compared releases; it establishes reachability, not a trajectory difference.

The finite argument is valid for the public `Entity.turn(double, double)` API. This source slice does not claim that a particular hardware input callback naturally emits a delta of that magnitude; callback timing/input plumbing is outside this assignment.

## Exact source references and current-byte SHA-256

Hashes below are SHA-256 over the file's raw bytes in the canonical source tree at comparison time. They identify the cited bytes, not their missing publication provenance.

| Release label / mapping | Relative source path | SHA-256 |
|---|---|---|
| 1.17.1 / Mojmap | `net/minecraft/world/entity/Entity.java` | `AB28E1FBA924771EC048140DFD293EE5A46A7DFE81F71A1A0B1AECC1927232DE` |
| 1.18.0 (`1.18`) / Mojmap | `net/minecraft/world/entity/Entity.java` | `69417675AE8E0A5BD88B7372BAEA1E73B1F5217154F39446C8AD5C92731437F1` |
| 1.17.1 / Mojmap | `net/minecraft/util/Mth.java` | `24515C4549E01E985017227DCCF7159166A675B232BE9135D5F896022A9CB113` |
| 1.18.0 (`1.18`) / Mojmap | `net/minecraft/util/Mth.java` | `10BD2DCC2B7084D0F9BE24A3E54322510F1730E67FB84D35EC460C6953B3397F` |
| 1.17.1 / Mojmap | `net/minecraft/world/entity/LivingEntity.java` | `33FD081AADB2B6FDC9EBF487DB6DA5B38C54F4B8676572790EE2203690D15E6F` |
| 1.18.0 (`1.18`) / Mojmap | `net/minecraft/world/entity/LivingEntity.java` | `68FC9B15EB31690377AFE92ACFC783C86945FEDEC5739D28FAACD96DA4F9E0E7` |
| 1.17.1 / Mojmap | `net/minecraft/world/entity/player/Player.java` | `724BB298499DABE489DFFD5CE7EC81C9773619A8D2C70044911EAE4C9E8FF481` |
| 1.18.0 (`1.18`) / Mojmap | `net/minecraft/world/entity/player/Player.java` | `1FB9DFB9F700323D77F4196DBC3618D01A65E56438B9CCE3EE38FA0B258BD7E2` |
| 1.17.1 / Mojmap | `net/minecraft/client/player/AbstractClientPlayer.java` | `D0508C94F43E7E90683B8F46586665BD1E1F0DCA2FE25274B852F5E6C0CE05E8` |
| 1.18.0 (`1.18`) / Mojmap | `net/minecraft/client/player/AbstractClientPlayer.java` | `5B68729FB699B5C5C6A8B1B3107B25800F513614B88A42BF1D29CC5684C8E7C0` |
| 1.17.1 / Mojmap | `net/minecraft/client/player/LocalPlayer.java` | `C9A91CB6CB57806BC8D22E5BFE2D97DAAF21D5D2A48F34A6E2C53164C61C5812` |
| 1.18.0 (`1.18`) / Mojmap | `net/minecraft/client/player/LocalPlayer.java` | `E38D0BBB6E5B9DE2A698609B8491D0007406B3BECD42BA9B95289F533788C996` |

Elytra references in both versions: `Player.tryToStartFallFlying`, lines 1542–1551; `LivingEntity.aiStep`, 1.17.1 lines 2544–2547 / 1.18 lines 2546–2549; `LivingEntity.updateFallFlying`, 1.17.1 lines 2584–2604 / 1.18 lines 2586–2606; active `LivingEntity.travel` glide branch, 1.17.1 lines 2068–2097 / 1.18 lines 2070–2099; `Player.travel`, 1.17.1 lines 1408–1438 / 1.18 lines 1408–1438.