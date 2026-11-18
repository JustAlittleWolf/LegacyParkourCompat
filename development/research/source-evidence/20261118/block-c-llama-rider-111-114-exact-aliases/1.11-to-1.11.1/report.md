# 1.11 → 1.11.1 Llama rider-position slice

## Result

No 1.11-to-1.11.1 change was found in the ordinary adult Llama's direct rider-position operation. `LlamaEntity.java` is byte-identical in both canonical source trees. This pair therefore does not establish a position change at 1.11.1; as the Llama is present at 1.11, its operation is already present in this pair's older endpoint.

## Exact behavior and guards

Both releases, namespace `ornithe-feather`, `net/minecraft/entity/living/mob/passive/animal/LlamaEntity.java:144-155`: `updateRiderPositon(Entity rider)` first requires `this.hasPassenger(rider)`. It computes `f = MathHelper.cos(this.bodyYaw * (float)(Math.PI / 180.0))`, then `g = MathHelper.sin(this.bodyYaw * (float)(Math.PI / 180.0))`; the unused local `float h = 0.3F` is also present. The direct placement is ordered as `rider.setPosition(this.x + 0.3F * g, this.y + this.getMountHeight() + rider.getRideHeight(), this.z - 0.3F * f)`. `getMountHeight()` returns `this.height * 0.67` (lines 153-155). No age/adult predicate is in this callback; this is the ordinary adult-Llama case, with the same height expression.

The reachable player callback is unchanged in both versions: `Entity.java:1510-1529` (1.11) and `1569-1588` (1.11.1) show rider `rideTick()` stops only when its mount is removed; otherwise it zeros rider velocity, ticks the rider, then—if `this.isRiding()` remains true—calls `entity.updateRiderPositon(this)`. That virtual call dispatches to the Llama override. `PlayerEntity.java:378-399` has the same rider branch in both releases: server-side sneaking while riding dismounts and clears sneaking; otherwise it calls `super.rideTick()` and processes riding stats. `LocalClientPlayerEntity.java:797-805` is byte-identical and delegates to `super.rideTick()`.

The Llama callback's only direct placement guard is `hasPassenger(rider)`; there is no local-player or world-side check inside it. The local-player route is the `LocalClientPlayerEntity.rideTick()` override above. In particular, this callback is not intrinsically client-only: the server-side `PlayerEntity.rideTick()` also reaches the placement branch unless its sneaking dismount guard applies. `PlayerEntity.getRideHeight()` is `-0.35` in both releases (`PlayerEntity.java:947-950`). `LocalClientPlayerEntity.isLocal()` returns `true` in both; base `PlayerEntity.isLocal()` returns `false`. These authority/locality facts do not change the position expression.

## Source identity and integrity

Canonical inputs are the local `decompiled_minecraft/<exact-version>/ornithe-feather/` trees. Both `.ready.json` markers say `ready`, and each provenance manifest's `versionId` and resolved version match the requested exact release; namespace is `ornithe-feather` and mapping option is `feather`. No snapshot or moving version alias was used.

| Exact release | Version metadata SHA-256 | Client SHA-256 | Feather mapping artifacts (SHA-256) | Provenance SHA-256 | Source-manifest SHA-256 |
|---|---|---|---|---|---|
| 1.11 | `a77e2568ef2f6733064c394eb40a7f1d8ca8ed48c56ee17cb6c720aeaf0118f4` | `f0d11987ea743f55a2c03bf77216e6dc9baea7201cc7bf498ceb18043e984634` | `feather-gen2-1.11+build.2-mergedv2.jar`: `658ad8c5a5d661eb5c2f97c00a47f6e02c02d5eee1eb1005f8e2926eb95dec44`; `.tiny`: `46bd6702a0983ad667109aadad90b2e6390ed5cd5999561a8a904956f4baf9c0` | `6f775c806fd1401a4d1756e9ef88da8d73185fe1d13598235f85c424405f4c40` | `8b3e5da58494f1dba77410960753b0bf646425a1ada491d8242cc71e6c7a59ca` |
| 1.11.1 | `5eaa27b4f69a322319fff0ce22d9bff07918097691626b827c4682a1303fa58f` | `eff72a31339cd9a96a61092eed2d95b95e4cc91516bea4e9105c3d73574147f6` | `feather-gen2-1.11.1+build.2-mergedv2.jar`: `83c2f49749b8eff663bf67664204e9cf02c75174e264d4cd57a6ba9f01bd2928`; `.tiny`: `4fa160c09d83bf61ae21bb74ab1e33b6aabe9b8ec89904b266ad53cecc9c36e6` | `0f6c92b8fbf4b1053a26a4cb61dc8f20c695e74a1963b49d911b8c7fbf01a055` | `0d5f4697d4e4ce53b36d0dbfec89d44830eb76753a6643b49e42f0857753066d` |

The source hashes below match their respective `ornithe-feather.sources.sha256` entries:

| Source | 1.11 SHA-256 | 1.11.1 SHA-256 |
|---|---|---|
| `net/minecraft/entity/living/mob/passive/animal/LlamaEntity.java` | `1577cf77a5c1d0e47dde0e13327dc333fcd65aa86c7e9073f49b2c678f0257b1` | `1577cf77a5c1d0e47dde0e13327dc333fcd65aa86c7e9073f49b2c678f0257b1` |
| `net/minecraft/entity/Entity.java` | `3209feaacd1e798b521a193eeb081d92c96881a70a18254ef24a58e911a4fd97` | `5a906de1d40a4a5ba8aedc9a6a9dc704e0ed862df177cf967fb1a9454765fa96` |
| `net/minecraft/entity/living/player/PlayerEntity.java` | `a0d787681de5caecda03fea1536dac0c07e6ca5cfbfe3b5fbb700cbe77c86fb6` | `8b37fd286b2dac1b343bd708b80284b43bb31c61bafc63bdcf6620ba3a59065c` |
| `net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java` | `65c2747bd8c70def6be7f41f624d4c9493342b39ae7bed7967f9ff63608f59ed` | `65c2747bd8c70def6be7f41f624d4c9493342b39ae7bed7967f9ff63608f59ed` |

## Bounds

This is only the exact 1.11/1.11.1 source comparison for the direct Llama rider-position write and its player-riding callback/local-player route. It does not establish a behavior boundary before 1.11 or cover other mounts, other position writers, trajectory parity, server policy, or pair-complete movement coverage.
