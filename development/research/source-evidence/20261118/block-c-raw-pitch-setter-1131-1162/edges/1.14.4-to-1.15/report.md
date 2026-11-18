# 1.14.4→1.15 direct turn pitch coverage

Status: **no source difference found in the assigned operation**. This is a bounded source-coverage report, not a permanent movement finding.

## Immutable source identity

Read-only canonical source paths in `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\`:

| Release / mapping | Ready marker | Source-manifest SHA-256 | Ready-marker SHA-256 | `Entity.java` SHA-256 | `LivingEntity.java` SHA-256 | `LocalPlayer.java` SHA-256 |
|---|---|---|---|---|---|---|
| 1.14.4 / Mojmap | `1.14.4/mojmap.ready.json`, status `ready`, version `1.14.4`, mapping `mojmap`; Java 25, Vineflower; manifest entry count 3,250 | `af98406f3d4ed31494fbdce1078d1f3df3f712501878f9a0e9ad5879bdf23c3b` | `ec7b6a6d9ba72f8a19908527977c970a8e2b3007b0e0b4323a725fb5943d3043` | `31c6be42d165102d3e6dc4295fdfef6e8971fc3aea13f938a5b3a33b91d524a7` | `428762178a876efd4069086e6b7d51f571ea0401f44e7eff0927b7d26d9ef681` | `0795c1223198ce5acf5d2ed9e5db8435bbec4cd52b96f96b1ddf2865baaae85f` |
| 1.15 / Mojmap | `1.15/mojmap.ready.json`, status `ready`, version `1.15`, mapping `mojmap`; Java 25, Vineflower; manifest entry count 3,329 | `251a3019ccc3fd067f66365f3545b69a983383a28b19a1038debf1791be29340` | `c6b66b63bafa2096ad7606111f1d0fae1f2178d624d6a8c9ded5ad3ac1aaa31` | `191b3ad3e7348c9bac1e703fff896706d23a751bf15162aacf676f5f97c0a10e` | `139c3866ad2c6a1ad73496f22ff1a4b5259998442092ffbafb8664428e28bf79` | `7227da5233e3c72ced008187b92a6a86842f84778eaa9f40fa1eb117286af823` |

The `Entity.java`, `LivingEntity.java`, and `LocalPlayer.java` digests above match their entries in each source manifest. Both ready markers identify exact published release/mapping output; no source generation was run.

## Compared operation and evidence

- **1.14.4, Mojmap:** `net/minecraft/world/entity/Entity.java`, `Entity.turn(double,double)`, line 319; `net/minecraft/util/Mth.java`, float `clamp`, line 90.
- **1.15, Mojmap:** `net/minecraft/world/entity/Entity.java`, `Entity.turn(double,double)`, line 326; `net/minecraft/util/Mth.java`, float `clamp`, line 98.

Both methods have the same operation order and types: pitch delta is computed as `double f = e * 0.15`; the live pitch is calculated in double precision by adding `f` to the float field, narrowed to `float`, and clamped to `[-90.0F, 90.0F]`. The previous pitch is then independently updated from `xRotO + f`, narrowed to `float`, and clamped to the same interval. Neither release inserts a finite check or rejects input before these writes. The same float clamp returns a bound for either signed infinity; a finite pitch delta large enough that the scaled amount exceeds the clamp also reaches the ordinary clamp. `NaN` is not rejected by this comparator-based clamp and remains `NaN`; this is source-level behavior, not a runtime test.

No x-rotation-specific public setter is introduced in either `Entity.java`; the assigned direct mutation path is `turn`. The nearby `setRot` helper is not part of this direct local-turn slice.

## Player movement consumer

- **1.14.4:** `net/minecraft/world/entity/LivingEntity.java`, `travel(Vec3)`, line 1782 onward; its fall-flying branch begins at line 1793. The authority gate is `isEffectiveAi() || isControlledByLocalInstance()`. `net/minecraft/client/player/LocalPlayer.java` overrides `isEffectiveAi()` to return `true` (line 483), so the local player passes this gate. In the active `isFallFlying()` branch, travel obtains the look vector and forms radians from the current field (`float l = this.xRot * (float)(Math.PI / 180.0)`) for glide lift/acceleration.
- **1.15:** same Mojmap members: `LivingEntity.travel(Vec3)` line 1831 onward, fall-flying branch line 1842; `LocalPlayer.isEffectiveAi()` returns `true` at line 490. It uses the same current-field pitch conversion and travel arithmetic.
- `Entity.getLookAngle()` returns `calculateViewVector(this.xRot, this.yRot)` in both sources (1.14.4 line 1697; 1.15 line 1661). `Entity.isControlledByLocalInstance()` also recognizes a local controlling passenger in both sources (1.14.4 line 2516; 1.15 line 2521); this is not needed for the unmounted local-player gate.

Thus an active local-player Elytra travel branch reads the live pitch after `turn`'s clamp, through both look-vector construction and the pitch-radian calculation. The changed operation requested by this slice is absent: this consumer behavior is present and unchanged in the pair.

## Coverage boundary

Examined the paired direct `Entity.turn` bodies, its float clamp dependency, and the live-pitch reads in the reachable local fall-flying travel branch. No difference found. This does not claim full 1.14.4→1.15 movement-pair coverage; input plumbing, scheduling, render/network/teleport paths, and other movement operations were not examined.
