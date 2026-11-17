# Knockback normalization source comparison

## Scope and result

Compare exact 1.13.2 with the available exact Feather-mapped 1.14 and 1.14.4 sources for direct player knockback normalization, gates and velocity writes. This is a source-lane disposition: the formula change is in server-only damage response production, so it is excluded from client movement emulation. A client can receive the resulting velocity, but the historical producer formula is not independently reachable in the client and the wire response is quantized before it is applied.

No source finding is proposed for this operation. The assigned exact evidence is complete for 1.13.2, 1.14 and 1.14.4. The 1.14.1, 1.14.2 and 1.14.3 directories have no top-level Feather provenance record, so they were not treated as exact evidence and no intermediate boundary is claimed.

## Immutable source identities

All source paths below are relative to `decompiled_minecraft/` in the project checkout. Mapping namespace is `ornithe-feather`.

- 1.13.2 provenance: `1.13.2/ornithe-feather.provenance.json`, SHA-256 `e25950b7855fc4f2aee1a662f0be6b0af9f28788d9e27293075cfc5a3aa5d383`; mapping artifacts named in provenance: `feather-gen2-1.13.2+build.2-mergedv2.jar`, `feather-gen2-1.13.2+build.2.tiny`.
- 1.14 provenance: `1.14/ornithe-feather.provenance.json`, SHA-256 `bfb011801a64e9faa4aca43e8a8ed40bf2a02f136ffc02e65b32e79315764fa`; provenance resolves `net.ornithemc:feather-gen2:1.14+build.2`.
- 1.14.4 provenance: `1.14.4/ornithe-feather.provenance.json`, SHA-256 `7167b135e0222fcf58ff5f36a36fbb604d5068f63a59165c8c4d18ce7d9b21c9`; mapping artifacts named in provenance: `feather-gen2-1.14.4+build.2-mergedv2.jar`, `feather-gen2-1.14.4+build.2.tiny`.

Relevant decompiled source hashes, checked against each `ornithe-feather.sources.sha256` manifest:

| Version | `LivingEntity.java` SHA-256 | `ClientPlayNetworkHandler.java` SHA-256 | `EntityVelocityS2CPacket.java` SHA-256 |
| --- | --- | --- | --- |
| 1.13.2 | `bb691358c9a43c9f46e85575bf4d0a4ad671d0eb912502acc3a6a3f625e42f1c` | `4f65502fbee6476cef0838563f03a66f07a1a9ed8c63e580f5338ba5fe274f11` | `b86de977593e5c638977328c183647f1913bd7b94e601b9e858fa0057cd88dea` |
| 1.14 | `1db4dd1aa95a06c48511e5567c0d3836f2a6e5a12a5cf03ab5c892e429198b79` | `d5c63c25b086b370588810ea999cab06a538fbf8a01a6d357b59bd6fd608ba5c` | `e71f8451b11a77c089afc8e60939e9f23934eb38e266d7fcae2481abdfcdc3f9` |
| 1.14.4 | `2cccf4331ce9e62013eeb8e96163e5e146e1291619eb998ce9867daa87c02b61` | `6c840580285c7c7d4617e895edac82d4eebf8289dd4994e7b593cebeb49b43ef` | `e71f8451b11a77c089afc8e60939e9f23934eb38e266d7fcae2481abdfcdc3f9` |

## Compared operation

- 1.13.2 Feather, `1.13.2/ornithe-feather/net/minecraft/entity/living/LivingEntity.java`, `applyKnockback(Entity, float, double, double)`, lines 1011–1026: under `!(random.nextDouble() < knockbackResistance)`, sets `velocityDirty`; computes horizontal magnitude as `float f = MathHelper.sqrt(velocityX * velocityX + velocityZ * velocityZ)`; halves existing X/Z components, then subtracts each input component divided by `f` times the `float amount`. If `onGround`, halves Y, adds `amount`, and caps Y at `0.4F`.
- 1.14 Feather, `1.14/ornithe-feather/net/minecraft/entity/living/LivingEntity.java`, same method, lines 1201–1207; 1.14.4 Feather, `1.14.4/ornithe-feather/net/minecraft/entity/living/LivingEntity.java`, lines 1208–1214: same resistance gate and dirty flag, but reads the existing velocity as `Vec3d`; creates `new Vec3d(velocityX, 0.0, velocityZ).normalize().scale(amount)`; writes X/Z as old component divided by `2.0` minus normalized scaled component, and writes Y as `onGround ? Math.min(0.4, oldY / 2.0 + amount) : oldY` through the vector setter. This replaces the old float-square-root/per-component-division math with Vec3d normalization and scales the resulting vector by the float amount. No new player-specific gate is introduced. The method is on `LivingEntity`; the `entity` parameter is unused in all three bodies.

## Reachability and decoded packet response

- The internal damage caller is not a client path: in each exact version `LivingEntity.takeDamage(DamageSource, float)` returns false immediately when `this.world.isClient` (1.13.2 lines 751–759; 1.14 lines 844–852; 1.14.4 lines 851–859). Its `applyKnockback` call therefore produces velocity only on the logical server.
- The local-client path is instead `ClientPlayNetworkHandler.handleEntityVelocity`: after `PacketUtils.ensureOnSameThread`, it looks up the packet entity ID and, if the entity exists, passes all three `int` components divided by `8000.0` to `entity.lerpVelocity` (1.13.2 lines 507–513; 1.14 lines 535–541; 1.14.4 lines 537–543). `Entity.lerpVelocity` directly overwrites the velocity components in 1.13.2; in 1.14+ it forwards to the vector setter. The packet handler contains no knockback normalization, resistance or on-ground gate.
- The packet payload does not preserve the raw producer vector: `EntityVelocityS2CPacket` clamps each axis to [-3.9, 3.9], casts `component * 8000.0` to `int`, writes shorts and reads them back with `readShort()` (1.13.2 lines 18–68; 1.14 / 1.14.4 lines 20–48). The client then reconstructs each component by division by `8000.0`. Thus the decoded player velocity is a quantized, clamped server result, not a stable witness of which source normalization formula produced it. The same decoded-response math is present in all compared endpoints.
- Disposition: producer-only exclusion under the project invariant that historical movement does not run on logical servers. The existence of a client velocity packet establishes a direct client velocity write, but not independent client reachability of this normalization operation. Damage production itself is outside this lane.

## Coverage and limits

Compared `LivingEntity.applyKnockback`, its `takeDamage` client guard, `EntityVelocityS2CPacket` encoding/decoding and `ClientPlayNetworkHandler.handleEntityVelocity` on all three exact available versions. 1.14 and 1.14.4 have byte-identical packet source for `EntityVelocityS2CPacket`. The 1.14.1–1.14.3 exact provenance gap remains unexamined; no claim is made about when the server implementation changed within 1.13.2–1.14.