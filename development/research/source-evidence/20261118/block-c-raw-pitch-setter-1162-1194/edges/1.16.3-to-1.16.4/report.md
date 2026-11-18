# 1.16.3→1.16.4 ordinary pitch response comparison

## Evidence identity

Both sides use the locally published `mojmap` source trees and their `.decompile-provenance.json` manifests. The source manifest pins the exact Mojang client, official client mapping (`client.txt`), version metadata, decompiler pipeline, runtime, and every emitted source hash. The provenance manifests and relevant source hashes were checked against the actual local bytes.

| Release | Manifest SHA-256 | Client JAR SHA-256 | Official mapping file SHA-256 | Version metadata SHA-256 |
|---|---|---|---|---|
| 1.16.3 | `513271804ead6757345d305717e7e0bfc1641434c35a3b254f287bd8fd281498` | `da85197babd989a7de318774cf5d97bcef95e89a70c9fa9a4c8180463e9589fa` | `c41c58faaf3a3b651d8883b9b1df355cb6ad59c4d6296b2e1ffc3dfe2bf9be3e` | `193c5226095ce9e8a317f1e35ddbe4d976f176f6df7ac649ecfa669e17160ead` |
| 1.16.4 | `1e04555c915527a398a2c7150679fb24d056f54bd680261e3c1dd55da821147c` | `d6740464ee2bdf37d285c5d368d2e16497492427fb5783e2db3482bbf0079ed0` | `93f188a0038fec8c2402d78e7acca983680978d6a9237c1fab80f22caddb00fe` | `667fff4ca7fd0f56ab78b757224515e8e64375868e8d1ce6040091d7b63a0cb5` |

The `Entity.java` SHA-256 is `f9a9a073fe3105a0aa53d0f21ec72e59084e8d21a14c1cd3be75703865ee2666` on both releases. `LivingEntity.java` is `b5d8a1a3c80f85d5d545b5a777e9e2a915dc002a7e31b5f5ad12bf7e285d7a88` on both. `Mth.java` is `0e4424ffa12923314c309ae13e012768271dfcace4b5a84727f74e8170afe290` on both. Each digest matches its manifest entry.

## Compared behavior

`net/minecraft/world/entity/Entity.java`, `Entity.turn(double d, double e)`, lines 340–352, is byte-identical in 1.16.3 and 1.16.4. Both compute pitch delta `f` as double `e * 0.15`, then first write pitch with `(float)(this.xRot + f)`, followed by the yaw write and the pitch clamp `Mth.clamp(this.xRot, -90.0F, 90.0F)`. The operation order, double multiplication, float cast, and inclusive float bounds are unchanged. The public method has no delta cap: a finite call such as `turn(0.0, 2401.0)` produces `f = 360.15`; from pitch zero the raw cast write is greater than 360 before the same call clamps it to `90.0F`. The corresponding previous pitch `xRotO` is updated and clamped afterward, with the same ordering in both releases.

`net/minecraft/util/Mth.java`, `Mth.clamp(float f, float g, float h)`, lines 94–100, is also byte-identical. It tests `f < g`, then `f > h`, otherwise returns `f`; its float overload and comparison order are unchanged. (NaN consequently passes through this helper; this is not needed for the finite-input case.)

For the active Elytra path, `net/minecraft/world/entity/LivingEntity.java`, `LivingEntity.travel(Vec3)`, lines 1979–2008, is byte-identical. Under `isFallFlying()`, it obtains `getLookAngle()` and separately converts the already-stored `this.xRot` to radians at line 1986 before applying the pitch-dependent lift and dive terms. `Entity.getLookAngle()` at `Entity.java:1700–1702` likewise derives the view vector from the stored `xRot` and `yRot`. Thus a large finite `turn` pitch delta affects this movement through the post-clamp `±90°` pitch; no raw >360 value survives the ordinary call. This establishes the actual movement consumer without involving device scheduling or rendering.

## Verdict

No 1.16.3→1.16.4 source difference for this bounded operation; do not create a movement finding for this pair. The method-level finite >360 pre-clamp case exists identically on both sides. This verdict covers ordinary `Entity.turn` pitch response and its active Elytra pitch read only; it makes no claim about input-device scheduling or other rotation writers.
