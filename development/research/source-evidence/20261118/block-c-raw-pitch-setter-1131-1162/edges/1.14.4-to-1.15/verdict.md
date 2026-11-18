# Independent source audit: Entity.turn pitch update (1.14.4 → 1.15)

## Verdict

**No source difference in the bounded `Entity.turn` pitch update.** In both releases, `f` is a `double` computed as `e * 0.15`; the first current-pitch write evaluates `(float)(this.xRot + f)`. The method then writes yaw, clamps current pitch to `[-90.0F, 90.0F]`, and independently updates previous pitch from its existing `xRotO` as `(float)(this.xRotO + f)` before clamping it to the same limits. `xRotO` is not assigned from the newly clamped `xRot`. The intervening yaw writes, clamp order, and passenger callback are identical.

The casts happen at the assignments, before each clamp. Thus the source contains an intermediate float write (which can overflow for sufficiently large finite inputs) followed by the clamp; there is no alternative “clamp in double then cast” sequence in either release. This setter slice does not establish a behavioral boundary from 1.14.4 to 1.15.

## First pitch use in the active Elytra branch

For the bounded dry, nonpassenger player path, `Player.travel` delegates to `super.travel`; its flying-ability branch is explicitly gated by `!this.isPassenger()`. In `LivingEntity.travel`, the glide branch is gated by the effective-AI/local-control outer condition, dry water/lava checks, then `isFallFlying()`. At the start of the active glide calculation, `getLookAngle()` is called before the local `float l = this.xRot * (float)(Math.PI / 180.0)`. `Entity.getLookAngle()` immediately calls `calculateViewVector(this.xRot, this.yRot)`, so this call is the first pitch read used by the glide math; the local `l` is the next direct pitch read. Both releases have this order and expression. This audit does not claim the surrounding Elytra equations are wholly unchanged.

## Exact source evidence

- **1.14.4, Mojmap:** `decompiled_minecraft/1.14.4/mojmap/net/minecraft/world/entity/Entity.java`, `turn(double,double)`, lines 319–330; SHA-256 `31c6be42d165102d3e6dc4295fdfef6e8971fc3aea13f938a5b3a33b91d524a7`. `getLookAngle()`, lines 1697–1699. `decompiled_minecraft/1.14.4/mojmap/net/minecraft/world/entity/LivingEntity.java`, `travel(Vec3)`, lines 1782–1801; SHA-256 `428762178a876efd4069086e6b7d51f571ea0401f44e7eff0927b7d26d9ef681`. `decompiled_minecraft/1.14.4/mojmap/net/minecraft/world/entity/player/Player.java`, `travel(Vec3)`, lines 1380–1405 (nonpassenger flying branch / otherwise `super.travel`).
- **1.15, Mojmap:** `decompiled_minecraft/1.15/mojmap/net/minecraft/world/entity/Entity.java`, `turn(double,double)`, lines 326–337; SHA-256 `191b3ad3e7348c9bac1e703fff896706d23a751bf15162aacf676f5f97c0a10e`. `getLookAngle()`, lines 1661–1663. `decompiled_minecraft/1.15/mojmap/net/minecraft/world/entity/LivingEntity.java`, `travel(Vec3)`, lines 1831–1850; SHA-256 `139c3866ad2c6a1ad73496f22ff1a4b5259998442092ffbafb8664428e28bf79`. `decompiled_minecraft/1.15/mojmap/net/minecraft/world/entity/player/Player.java`, `travel(Vec3)`, lines 1456–1483 (nonpassenger flying branch / otherwise `super.travel`).

## Readiness and provenance

The published mapping directories are `decompiled_minecraft/1.14.4/mojmap` and `decompiled_minecraft/1.15/mojmap`; both identify the exact release and `mojmap` namespace.

- **1.14.4:** `mojmap.ready.json` says `status: ready`, 3,250 source files and source manifest SHA-256 `af98406f3d4ed31494fbdce1078d1f3df3f712501878f9a0e9ad5879bdf23c3b`. The actual `mojmap.sources.sha256` has that SHA. `mojmap.verification.json` records `verified-identical-source-and-input-artifacts`, 3,250 source files verified, zero source differences, zero missing published source files, and matching published/regenerated source manifest hashes. The current cited `Entity.java` and `LivingEntity.java` hashes match their manifest entries. `mojmap.provenance.json` SHA-256: `9d1ad405e1e787d2fca5ed1897e6797b1f3cd79527da50d713232f46e2f30d97`.
- **1.15:** `mojmap.ready.json` says `status: ready`, 3,329 source files, exact version `1.15`, mapping `mojmap`, and manifest SHA-256 `251a3019ccc3fd067f66365f3545b69a983383a28b19a1038debf1791be29340`; the actual source manifest matches. `mojmap.provenance.json` identifies the exact client artifact, Mojang client mappings, remapped client artifact, tools and source manifest; its SHA-256 is `de4a538ebbaf82d1615683b08ecae3cd76553345f3079436764eb095be343949`. The current cited `Entity.java` and `LivingEntity.java` hashes match their manifest entries.

## Audited bytes

- 1.14.4 source manifest SHA-256: `af98406f3d4ed31494fbdce1078d1f3df3f712501878f9a0e9ad5879bdf23c3b`.
- 1.15 source manifest SHA-256: `251a3019ccc3fd067f66365f3545b69a983383a28b19a1038debf1791be29340`.
- This verdict is bound to the complete current cited-file hashes above and these whole-source-manifest identities.

## Phase-two independent comparison

Compared against `c-raw-pitch-setter-1144-115-source/report.md`, whose verified whole-file SHA-256 is `059e7902646c68a00fd5fac4be3dc53c816238813212171950cd4be2e4cdb4e5`. **The substantive claims within this audit's assigned slice agree**: both `Entity.turn` bodies use the same `double` delta, current-pitch narrowing/write then clamp, and independent previous-pitch narrowing/write then clamp; the bounded Elytra path first obtains its view vector through `getLookAngle()` and then reads `xRot` for radians. The author report's additional clamp and local-player reachability claims also match the exact paired `Mth.clamp(float,float,float)` bodies (1.14.4 line 90; 1.15 line 98) and `LocalPlayer.isEffectiveAi()` bodies (1.14.4 lines 482–484; 1.15 lines 489–491). These source details are unchanged; no conflict with the no-difference verdict.

**Identity transcription discrepancy:** the author report's 1.15 ready-marker digest in its table is 63 hex characters (`c6b66b63bafa2096ad7606111f1d0fae1f2178d624d6a8c9ded5ad3ac1aaa31`). The current marker's actual SHA-256 is `c6b66b63bafaf2096ad7606111f1d0fae1f2178d624d6a8c9ded5ad3ac1aaa31` (missing `f` in the report's value). The source manifest and cited source-file hashes in that report match the current files and manifests; this digest transcription issue does not change the audited source conclusion. Other author-report claims outside the bounded turn/clamp/Elytra read were not expanded into this verdict.