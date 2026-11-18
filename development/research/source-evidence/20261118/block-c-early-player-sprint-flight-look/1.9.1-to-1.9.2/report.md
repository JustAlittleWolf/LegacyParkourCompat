# Blind source comparison: 1.9.1 → 1.9.2

## Evidence identity

Compared exact local decompilation outputs under `decompiled_minecraft/<version>/ornithe-feather/`, mapped with Ornithe Feather Gen2 build.2 for each release. Both `.decompile-provenance.json` manifests record the same decompiler pipeline (`lpc-decompile-v1`, single-thread Vineflower 1.12.0, runtime disabled; the complete pipeline string is in each manifest). Manifest SHA-256 and pinned release inputs:

| Release | Provenance manifest SHA-256 | Feather metadata SHA-256 | Feather Gen2 artifact SHA-256 | Mojang client SHA-256 |
| --- | --- | --- | --- | --- |
| 1.9.1 | `e9fb0da6511597e2de953a143f9570b82243e5b9504603686e557f06271da64a` | `da56c9e7efe5c929b2ec8d99a8e2bbc6825847f2852a0e717fcd6c674db73dc4` | `6d5768353bf2ff2756d38537312548a8ac91d570f130d23c23e9412d2edd97b3` | `56ad7e53f6f4ad393844e274aa2190692b61d2ad9f6b3d12b33a8b7332fd51f9` |
| 1.9.2 | `9928b467deb35c7e7f891b4fee1627a68feecdf233c2076909fd4834b0380510` | `5bad5e67d0870fec69bbab8fcae8d0525a274668bb472eb09d1552359d08edd1` | `e2b98ececbff8fa0bd0c89283bbb32b7f9f9dcd4b89fa3f137666fa3ac0b6859` | `ef87eb71250df1b871deab8d3ef10aac3290b9e78667ad548fde4d0e14af0d65` |

The source digests below were checked against each manifest's `sources` entry and recomputed from the corresponding file. `LocalClientPlayerEntity.java` and `LivingEntity.java` are byte-identical across this pair. `PlayerEntity.java` differs as a whole; its `moveRelative` method was directly compared and is identical for the assigned operation.

| Relative source file | 1.9.1 SHA-256 | 1.9.2 SHA-256 |
| --- | --- | --- |
| `net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java` | `de9ad48bcfb2705ef7a9a63e9401c1742aead183e1e9c2d13f8fe40cca0139eb` | `de9ad48bcfb2705ef7a9a63e9401c1742aead183e1e9c2d13f8fe40cca0139eb` |
| `net/minecraft/entity/living/player/PlayerEntity.java` | `d7035f08f03b01823dff70ca504f7244f9b0753542cc0f6dd7301e66f8866b71` | `1fc5107b9b7f5711105d521a28829f2466fe9f297e832f4d209e6ec422111aac` |
| `net/minecraft/entity/living/LivingEntity.java` | `d8824f670037a88753c428c27213989fc5173b1d7c638f498f73395c145b6fd9` | `d8824f670037a88753c428c27213989fc5173b1d7c638f498f73395c145b6fd9` |

## Compared operation bounds

- `LocalClientPlayerEntity.java`, `setSprinting(boolean)`, lines 403–406; `mobTick()`, lines 601–685 and 712–757, both releases.
- `PlayerEntity.java`, `moveRelative(float,float)`, lines 1366–1383, both releases.
- `LivingEntity.java`, `moveRelative(float,float)`, begins at line 1293, both releases; used to check the inherited call boundary in the player override. Its file digest is identical across the pair. Fall-flying behavior in the inherited method was not treated as ordinary ability flight.

## Findings

**Sprint timer and sprint gates: no difference found.** At `mobTick` entry, `sprintTimer` increments before `doubleTapSprintTime` is decremented when positive. Item use while not riding resets the double-tap timer to zero. The grounded double-tap path uses the same preconditions and terminal decision in both releases: if the timer is `<= 0` and the sprint key is not held, set it to 7; otherwise start sprinting. The held-key sprint path has the same forward-input (`>= 0.8F`), food-or-flight, item-use and blindness gates. The stop gate remains forward input below `0.8F`, horizontal collision, or unavailable food/flight. `setSprinting` still calls the superclass before resetting `sprintTimer` to zero. These relevant parts are identical in the byte-identical local-player source file.

**Ordinary ability-flight fall-distance reset: no difference found.** `PlayerEntity.moveRelative` in both versions gates the special path on `abilities.flying && !isRiding()`. It saves vertical velocity and `speedInAir`; sets `speedInAir` to fly speed times `(isSprinting() ? 2 : 1)`; calls `super.moveRelative`; multiplies saved vertical velocity by `0.6`; restores `speedInAir`; sets `fallDistance` to `0.0F`; and clears flag 7. Otherwise it makes the ordinary superclass call. Reset timing remains after the inherited call and before `tickNonRidingMovementRelatedStats`. The compared inherited `LivingEntity.moveRelative` source is byte-identical. Fall-flying/elytra behavior is outside this claim.

**Sneak-flight input scaling: no difference found.** In `LocalClientPlayerEntity.mobTick`, the block remains gated by `abilities.flying && isCamera()`. When sneaking, sideways and forward inputs are each divided by `0.3` and cast back to `float`, then `velocityY` is reduced by `getFlySpeed() * 3.0F`. The jumping block follows and adds the same quantity to `velocityY`, so both inputs apply in that order if both flags are true. The ability-flight stop check remains after `super.mobTick()`, gated by on-ground, flying and non-spectator state; it clears the flying flag and syncs abilities. All compared local-player source is byte-identical.

## Coverage limit

This is a bounded comparison of the assigned local sprint/ordinary-ability-flight/sneak-flight operations only. It does not establish complete player-tick coverage or pair freeze, and it makes no claim about food production, server motion, fluids, contact, geometry, or other movement operations.
