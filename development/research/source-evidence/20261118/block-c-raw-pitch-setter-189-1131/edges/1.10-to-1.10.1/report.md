# C raw source slice: local pitch update to active Elytra movement

**Pair:** Minecraft 1.10 → 1.10.1, Ornithe Feather (`ornithe-feather`).  
**Verdict:** No change in the assigned local pitch update or its first stored-pitch use in dry active Elytra movement. This is a bounded source result, not full-pair coverage or freeze.

## Immutable source identity

The canonical roots are `decompiled_minecraft/1.10/ornithe-feather/` and `decompiled_minecraft/1.10.1/ornithe-feather/`. Each has a nested `.decompile-provenance.json`; neither `decompiled_minecraft/1.10/.decompile-provenance.json` nor `decompiled_minecraft/1.10.1/.decompile-provenance.json` exists. The mapping-root manifests identify Feather build 2 for the matching exact release. Their manifest SHA-256 values and pinned inputs are:

| Release | Nested manifest SHA-256 | Feather mapped artifact SHA-256 | Raw client SHA-256 | Feather metadata SHA-256 |
|---|---|---|---|---|
| 1.10 | `df266e2c3009dd1a873c1665c30fe388078ed1c6227668e10cde87409a18c6da` | `ad7cf6744d81d0260c9d97ddfe8250b2fdbd54fe332d4d00266b3545d8ddf8dd` | `c00f8170ebf4fd834f552246743a64885a6dffb3dd1e3a3f3dba9676b7a0af35` | `a1eb1ccff25e2b76fa1d08e9b515508e97ac4837fbcacbd7f77b3a01026d3950` |
| 1.10.1 | `a9a50497930848bf0ddec732b6d424e947f643a8fa6290e01425ac2bbfce219f` | `6e8ba44832b7b77d913962462bde55084005d5de6389232453afc0bc0339ea37` | `338b205f20c22b9c676d9d3227a5e6c5e190f30ad84d7caad035150b3e05ba72` | `8648e97ee398b11151cb2d441161629abde561afed9c933c18fbc622e2c9904a` |

For each examined source below, the file's SHA-256 was checked against the `sources` entry in that release's nested manifest and matched. Identical source hashes establish byte identity for the files with matching values; `PlayerEntity.java` differs at file level, so its bounded dispatch excerpt was compared directly.

| Source path below each release/mapping root | 1.10 SHA-256 | 1.10.1 SHA-256 |
|---|---|---|
| `net/minecraft/client/render/GameRenderer.java` | `35467c3723b9b50bb7e25ee439b8262d091cee12a1b94881f25fe90d35a83c52` | `35467c3723b9b50bb7e25ee439b8262d091cee12a1b94881f25fe90d35a83c52` |
| `net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java` | `a9637065f21ad67464eb5c204c74ebf228c3bb0da8a96ddf4ae73c0490fed443` | `a9637065f21ad67464eb5c204c74ebf228c3bb0da8a96ddf4ae73c0490fed443` |
| `net/minecraft/entity/Entity.java` | `05da145effa19a6ef7934cc276e89226373b67c12f4ce89a8ce2183f29039f77` | `05da145effa19a6ef7934cc276e89226373b67c12f4ce89a8ce2183f29039f77` |
| `net/minecraft/entity/living/LivingEntity.java` | `d40dd476b6b68c6ce45b4202823475deb546ecda2284da330ff6724b33815e82` | `d40dd476b6b68c6ce45b4202823475deb546ecda2284da330ff6724b33815e82` |
| `net/minecraft/entity/living/player/PlayerEntity.java` | `db7bbbc15e2bcb0de18476fec1b88e73eccf8ae63b170276b1d8327dd0be796b` | `020370cffde2d1fe9f1b63fa5d51f1af2fab095908b19e79f88a7bcac7dfd501` |
| `net/minecraft/util/math/MathHelper.java` | `43b2a4e3931733dbc3538024b7182b024d874a5f22a269a6199c6b4ca8c59151` | `43b2a4e3931733dbc3538024b7182b024d874a5f22a269a6199c6b4ca8c59151` |

## Compared behavior

- **Ordinary local input:** `GameRenderer.java:832–855` reaches `player.updateLocalPlayerCamera(h, i * j)` for a focused local player. `LocalClientPlayerEntity.java:457–460` returns true from `isLocallyControlled()`. These call-site/dispatch slices are byte-identical between the releases.
- **Current and previous pitch storage:** `Entity.java:100,102` declares `pitch` and `lastPitch` as `float`. `updateLocalPlayerCamera(float yaw, float pitch)` at `Entity.java:291–302` saves the old current pitch in float `f`, computes `(float)(this.pitch - pitch * 0.15)`, clamps current pitch to `[-90.0F, 90.0F]`, then adds `(this.pitch - f)` to `lastPitch`. The `0.15` literal is double, so multiplication/subtraction are evaluated as double before the cast to float. The update order, casts, clamp bounds and previous-value delta match exactly in 1.10 and 1.10.1.
- **Finite reachability:** For finite float mouse deltas, `pitch * 0.15` stays finite in double; even a finite float at its maximum magnitude scaled by 0.15 remains within the finite float range on cast. The prior ordinary current pitch is clamped, so the resulting current value remains finite and in `[-90,90]`. The float delta added to `lastPitch` is therefore bounded by 180 degrees; from a finite ordinary initial state, `lastPitch` remains finite. `MathHelper.clamp(float,...)` at `MathHelper.java:80–85` uses ordered comparisons and returns `x` when neither comparison holds, so a directly supplied NaN would pass through; that is outside this finite ordinary mouse-input path. No extra normalization of current or previous pitch occurs here.
- **First pitch use during active glide movement:** `LivingEntity.flyingTick():1754–1772` retains active flag 7 only while airborne, not riding, and wearing a fly-capable Elytra; `isFallFlying():2088–2090` reads that flag. `LivingEntity` calls `moveRelative` from its travel tick at `1742–1748`. The local player's `PlayerEntity.moveRelative():1391–1408` forwards to `super.moveRelative` when abilities flight is not taking its separate branch. In the `LivingEntity.moveRelative` branch (`1332–1371`), after the local/logical-side gate and dry water/lava guards, `isFallFlying()` selects Elytra movement. Its first pitch-dependent operation is `getLookVector()` at `1341`: `LivingEntity.getLookVector():1852–1855` calls `getRotationVector(1.0F)`, and `getRotationVector(float):1858–1865` takes the exact-1.0 branch to `getRotationVector(this.pitch, this.headYaw)`, bypassing interpolation through `lastPitch`. `Entity.getRotationVector(float,float):1148–1153` then uses that current pitch for sine/cosine components. The next explicit current-pitch read is `float i = this.pitch * (float)(Math.PI / 180.0)` at `LivingEntity.java:1342`; `i` gates the pitch-dependent lift/dive terms that update vertical and horizontal velocity (`1346–1370`). The same branch and ordering occur in both releases. Thus the ordinary camera setter's clamped current float pitch is the stored pitch consumed first in this dry active Elytra path; previous pitch is not consumed by this exact-1.0 look-vector route.

**Difference and movement meaning:** No change found in the assigned slices. No movement consequence is established for 1.10 → 1.10.1 by this pitch setter/use comparison. This result does not claim that all player-tick movement slices for the pair are covered.
