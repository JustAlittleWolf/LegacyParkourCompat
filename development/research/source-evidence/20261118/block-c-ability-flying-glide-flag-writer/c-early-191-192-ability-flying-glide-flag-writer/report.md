# 1.9.1 → 1.9.2: creative flight while fall-flight is live

## Evidence identity

Both source trees are the local `ornithe-feather` decompilations for the exact releases. Their provenance manifests identify Feather Gen2 build 2. The cached Feather metadata response and mapping artifacts are pinned as follows:

| Release | Feather metadata URL SHA-256 | Mapping artifact | Mapping artifact SHA-256 | Mojang client SHA-256 |
| --- | --- | --- | --- | --- |
| 1.9.1 | `da56c9e7efe5c929b2ec8d99a8e2bbc6825847f2852a0e717fcd6c674db73dc4` | `feather-gen2-1.9.1+build.2-mergedv2.jar` | `6d5768353bf2ff2756d38537312548a8ac91d570f130d23c23e9412d2edd97b3` | `56ad7e53f6f4ad393844e274aa2190692b61d2ad9f6b3d12b33a8b7332fd51f9` |
| 1.9.2 | `5bad5e67d0870fec69bbab8fcae8d0525a274668bb472eb09d1552359d08edd1` | `feather-gen2-1.9.2+build.2-mergedv2.jar` | `e2b98ececbff8fa0bd0c89283bbb32b7f9f9dcd4b89fa3f137666fa3ac0b6859` | `ef87eb71250df1b871deab8d3ef10aac3290b9e78667ad548fde4d0e14af0d65` |

The provenance manifests are `decompiled_minecraft/<release>/ornithe-feather/.decompile-provenance.json`. I recalculated SHA-256 over the local relevant Java files and confirmed each against its manifest `sources` entry. Relevant source hashes:

| Relative source | 1.9.1 SHA-256 | 1.9.2 SHA-256 |
| --- | --- | --- |
| `net/minecraft/entity/living/player/PlayerEntity.java` | `d7035f08f03b01823dff70ca504f7244f9b0753542cc0f6dd7301e66f8866b71` | `1fc5107b9b7f5711105d521a28829f2466fe9f297e832f4d209e6ec422111aac` |
| `net/minecraft/entity/living/LivingEntity.java` | `d8824f670037a88753c428c27213989fc5173b1d7c638f498f73395c145b6fd9` | same |
| `net/minecraft/entity/Entity.java` | `74d3ac34d7e6355c8fdcdd777dcd95ba9ef4626718c8772c1b58d0e28419e42a` | same |
| `net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java` | `de9ad48bcfb2705ef7a9a63e9401c1742aead183e1e9c2d13f8fe40cca0139eb` | same |

## Paired source comparison

`PlayerEntity.moveRelative` is unchanged in the relevant body in both releases (`PlayerEntity.java:1366–1383`): it snapshots position; if `abilities.flying && !isRiding()`, saves vertical velocity and air speed, adjusts air speed, invokes `super.moveRelative`, scales vertical velocity by `0.6`, restores air speed, resets `fallDistance`, and then unconditionally calls `setFlag(7, false)`. The `else` branch just invokes `super.moveRelative`. The remaining stats call follows the branch. The whole `LivingEntity.java` source file has the same SHA-256 in both trees.

In the inherited fall-flight branch (`LivingEntity.java:1293–1345`), `isFallFlying()` reads flag 7 (`LivingEntity.java:2042–2044`). If true, the method applies fall-flight velocity math, multiplies velocity by `0.99F`, `0.98F`, `0.99F`, and invokes `this.move(this.velocityX, this.velocityY, this.velocityZ)` at line 1332. Only after that actual `Entity.move` returns does the fall-flight branch check horizontal collision damage and, if grounded, clear flag 7 under `onGround && !world.isClient`. When `super.moveRelative` returns, the creative-flight override then clears flag 7 without a side or on-ground guard. The same order and guards occur in both versions. `Entity.java` is byte-for-byte identical by source SHA; `Entity.setFlag` updates bit `1 << index` in shared tracked flags (`Entity.java:1656–1663`).

The normal local input path makes the overlap reachable: `LocalClientPlayerEntity.java:687–700` toggles `abilities.flying` on a double-jump when `abilities.canFly` and not spectator; the following Elytra-start check at lines 704–710 requires `!isFallFlying()` and `!abilities.flying`. Thus a player already in fall-flight can enable ordinary creative flight by input, and the next travel call reaches `LivingEntity`'s `moveRelative` (`LivingEntity.java:1697–1703`), then the `PlayerEntity` override. This establishes source reachability only; it makes no claim about packet timing or latency.

The creative-flight clear itself has no client/server guard. The inherited fall-flight landing clear and collision-damage handling are server-only because they explicitly test `!world.isClient`; however the player override's post-`super` clear is unguarded and can run on whichever logical side executes this player movement method. The outer `LivingEntity.moveRelative` gate admits either locally controlled or logical-side movement (`LivingEntity.java:1293–1296`); `Entity.isLogicalSideForUpdatingMovement` selects the local player controller or non-client side (`Entity.java:2237–2240`). This does not imply historical simulation should be introduced on a server.

## Verdict and boundaries

No 1.9.1→1.9.2 change was found in this creative-flight/fall-flight flag overlap: the direct post-move flag-7 clear, inherited move order, branch gates, and normal local input route are present in both versions. This pair does not support a new finding for this operation. The adjacent `fallDistance = 0.0F` write is also unchanged here and is not treated as a separate difference. This bounded report does not assess A/B state or startup behavior.
