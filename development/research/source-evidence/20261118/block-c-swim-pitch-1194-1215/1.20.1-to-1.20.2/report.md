# 1.20.1 → 1.20.2: player swim-pitch vertical adjustment

## Bounded result

No source difference was found for the local ordinary active nonpassenger `Player.travel` swim-pitch vertical adjustment. The full `Player.java` and `Mth.java` source files are byte-identical across the pair. The relevant `Entity` look-vector methods and inherited `LivingEntity.travel` water branch were compared directly and are textually unchanged; their containing classes changed elsewhere. This closes only this bounded operation, not the pair's full movement inventory or intermediate patch boundary.

## Exact branch and dependency trace

In both releases, `Player.travel(Vec3)` records starting coordinates, then enters the direct adjustment only when `this.isSwimming() && !this.isPassenger()` (1.20.1 `Player.java:1446-1458`; 1.20.2 `Player.java:1450-1462`). It first reads `double lookY = this.getLookAngle().y`, then chooses `0.085` if `lookY < -0.2`, otherwise `0.06`. The boost predicate is short-circuit ordered: `lookY <= 0.0 || this.jumping || !fluidSample.isEmpty()`. Thus the block/fluid sample is evaluated only when `lookY > 0` and not jumping. The sample is `level().getBlockState(BlockPos.containing(getX(), getY() + 1.0 - 0.1, getZ())).getFluidState().isEmpty()`, with the `double` additions/subtractions evaluated left-to-right (`y + 1.0`, then `- 0.1`) before `BlockPos.containing`. A nonempty sampled fluid also enables the adjustment.

When enabled, both versions read the current `Vec3` delta movement, add `(0.0, (lookY - delta.y) * factor, 0.0)`, then set that result. This directly moves vertical velocity toward the current look-vector Y component by the selected fraction; it runs before the subsequent flying-or-`super.travel` branch in `Player.travel`.

`Entity.getLookAngle()` passes current `getXRot()` (pitch) and `getYRot()` (yaw) to `calculateViewVector` (1.20.1 `Entity.java:1970-1972`; 1.20.2 `Entity.java:2004-2006`). Both versions calculate pitch radians as `pitch * (float)(Math.PI / 180.0)` and negated yaw radians in the same order, then evaluate `Mth.cos(yawRadians)`, `Mth.sin(yawRadians)`, `Mth.cos(pitchRadians)`, and `Mth.sin(pitchRadians)`, returning `(sinYaw * cosPitch, -sinPitch, cosYaw * cosPitch)` (1.20.1 `Entity.java:1448-1455`; 1.20.2 `Entity.java:1458-1465`). Consequently `lookY` is `-Mth.sin(pitchRadians)`.

Both `Mth` files initialize a 65,536-element `SIN` table with `(float)Math.sin(index * Math.PI * 2.0 / 65536.0)` and use `SIN[(int)(radians * 10430.378F) & 65535]` for `sin`; `cos` uses the same table with `+ 16384.0F` before cast/mask (both `Mth.java:25-48`). The array width, initialization expression/order, float scale, cast truncation, mask and offset all match exactly.

The subsequent inherited `LivingEntity.travel` water path remains the same: when locally controlled and in water, affected by fluids and unable to stand on the sampled fluid, it computes water slowdown/depth strider, applies relative movement, moves, damps velocity, applies fluid-falling adjustment and the horizontal-collision escape check. Compare 1.20.1 `LivingEntity.java:2004-2047` with 1.20.2 `LivingEntity.java:2038-2081`; the block is textually identical with a 34-line offset. This inherited branch is not part of the direct pitch adjustment and presents no pair delta here.

## Source readiness and immutable evidence

Exact pair and namespace: 1.20.1 Mojmap → 1.20.2 Mojmap. Both ready markers declare `status: ready`, exact `versionId`, `mapping: mojmap`, Vineflower, Java runtime 25, and 22 movement-diagnostic reviews. Both provenance files identify the exact batch `1.20.1, 1.20.2, 1.20.4, 1.20.6`, Mojmap, Gradle 9.7.1, Java 25.0.3+9-LTS, Tiny Remapper 0.14.1, Mapping IO 0.9.1, Vineflower 1.12.0 and ASM 9.10.1.

The ready-marker hashes were checked against the published files: 1.20.1 `mojmap.sources.sha256` SHA-256 `858b56764113e591c60d09bc63e4d90f9c1d67a705645f0c793e8b28f2378465`, `artifacts.sha256` `5f1fc7dcd4ee82ddb7ea0c3be6fd21ceb1910cc691594bda163bfa19b01c7db2`, diagnostics `3eecf5b2c48cb99795874a36a8eb83cfc07866bc909a51c997dbfaac8083f4fd`; 1.20.2 sources `e439ff7e1d3f1d31e01be1edd141f94d917a9dc7fa0de5f69904544cbbe62887`, artifacts `ee67da9733953b30ae4077c587184e59772b745d780abd27a7b023780b459f54`, diagnostics `a60a6ea2a91cda32df8a7b837d5344ae54c8370058cc9ab3ae163b30e8fa6b79`.

The relevant source file hashes also match their source-manifest entries:

| Source | 1.20.1 SHA-256 | 1.20.2 SHA-256 |
|---|---|---|
| `net/minecraft/world/entity/player/Player.java` | `873d82c6f5471d06812929e471d970fd973bfbe21638e4e348a6fcde45637dac` | `25f263692fd6b2a737aa813315022a0caf4e18c3661ba3bd706c6297f909f25c` |
| `net/minecraft/world/entity/Entity.java` | `94b9c3656715de2d61fa9a02ccef164261e50eb13d6090a6f07e58fe9c5b0759` | `d7ee49aaea5e862b92508e767562cabc8f10515605e8fb67565c8c8d5c23b01b` |
| `net/minecraft/util/Mth.java` | `201d17ea024637036761c2703e407d4ab1dc7651a1f4b00a857c09803754c797` | `8a16560d3dd84853a2caaa658d6a106ea2e7f4e04d5cd6ed4d8f76839cda9dce` |
| `net/minecraft/world/entity/LivingEntity.java` | `decf8cd70d194098ad51d0c82e5a0087e0687881e0a2dc66456f2ba6ea76676f` | `5c481da1ffc8684c4b30171c92e61fad751e6a3a708d5bc6486ac9f96ff69828` |

The pair comparison used only the canonical local decompiled sources and their readiness/provenance manifests. No source generation, build, test or runtime launch was performed.