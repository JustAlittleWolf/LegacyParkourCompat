# Independent source audit verdict: 1.9.1 → 1.9.2

## Bound inputs and identities

Reviewed the frozen source report `c-early-191-192-sprint-flight-input/report.md` (SHA-256 `44facdcbd45e29c5195ce63aad0ca98528a1d86fde44b25ccf648ba82a71c116`) against the canonical local outputs at `decompiled_minecraft/<release>/ornithe-feather/`. Both releases use Ornithe Feather Gen2 build.2. The report's exact source identities, mappings, provenance and input pins were rechecked; relevant provenance manifests contain pipeline `lpc-decompile-v1` with single-thread Vineflower 1.12.0 and Windows 11 / Eclipse Adoptium 25.0.3+9-LTS.

| Release | Provenance manifest SHA-256 | Feather metadata SHA-256 | Feather Gen2 artifact SHA-256 | Mojang client SHA-256 |
| --- | --- | --- | --- | --- |
| 1.9.1 | `e9fb0da6511597e2de953a143f9570b82243e5b9504603686e557f06271da64a` | `da56c9e7efe5c929b2ec8d99a8e2bbc6825847f2852a0e717fcd6c674db73dc4` | `6d5768353bf2ff2756d38537312548a8ac91d570f130d23c23e9412d2edd97b3` | `56ad7e53f6f4ad393844e274aa2190692b61d2ad9f6b3d12b33a8b7332fd51f9` |
| 1.9.2 | `9928b467deb35c7e7f891b4fee1627a68feecdf233c2076909fd4834b0380510` | `5bad5e67d0870fec69bbab8fcae8d0525a274668bb472eb09d1552359d08edd1` | `e2b98ececbff8fa0bd0c89283bbb32b7f9f9dcd4b89fa3f137666fa3ac0b6859` | `ef87eb71250df1b871deab8d3ef10aac3290b9e78667ad548fde4d0e14af0d65` |

All following files were independently hashed against their manifests and match those pins:

| Exact relative source path | 1.9.1 SHA-256 | 1.9.2 SHA-256 |
| --- | --- | --- |
| `net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java` | `de9ad48bcfb2705ef7a9a63e9401c1742aead183e1e9c2d13f8fe40cca0139eb` | `de9ad48bcfb2705ef7a9a63e9401c1742aead183e1e9c2d13f8fe40cca0139eb` |
| `net/minecraft/entity/living/player/PlayerEntity.java` | `d7035f08f03b01823dff70ca504f7244f9b0753542cc0f6dd7301e66f8866b71` | `1fc5107b9b7f5711105d521a28829f2466fe9f297e832f4d209e6ec422111aac` |
| `net/minecraft/entity/living/LivingEntity.java` | `d8824f670037a88753c428c27213989fc5173b1d7c638f498f73395c145b6fd9` | `d8824f670037a88753c428c27213989fc5173b1d7c638f498f73395c145b6fd9` |

## Independent claim verdicts

1. **Ordinary sprint timer and sprint gates — ACCEPT.** `LocalClientPlayerEntity.setSprinting(boolean)` (403–406) invokes the superclass before setting `sprintTimer = 0`. In `mobTick()` (601–685), `sprintTimer` increments before a positive `doubleTapSprintTime` decrements. Item use while not riding scales both movement inputs and sets the double-tap timer to zero. The grounded double-tap path checks the prior-forward-input transition (`!bl3`) plus current forward input `>= 0.8F`, on-ground, not sneaking or already sprinting, food level `> 6.0F` or flight capability, no item use and no blindness. With timer `<= 0` and sprint key not held it sets the timer to 7; otherwise it starts sprinting. The held sprint path uses the same current-input, food/flight, item-use and blindness gates; stopping remains tied to forward input `< 0.8F`, horizontal collision, or unavailable food/flight. Both relevant source files are byte-identical across releases. No difference in this bounded operation.

2. **Ordinary ability-flight fall-distance reset — ACCEPT.** `PlayerEntity.moveRelative(float,float)` (1366–1384) takes its special branch only for `abilities.flying && !isRiding()`. It saves vertical velocity and `speedInAir`, sets air speed to `getFlySpeed() * (isSprinting() ? 2 : 1)`, invokes `super.moveRelative`, multiplies the saved vertical velocity by `0.6`, restores air speed, sets `fallDistance = 0.0F`, and clears flag 7. The fall-distance write is after the inherited call and before `tickNonRidingMovementRelatedStats`. The compared inherited `LivingEntity.moveRelative` (1293–1460) is byte-identical across the pair; its distinct fall-flying branch does not change the ordinary ability-flight reset order. The relevant `PlayerEntity` method slice is identical across the two files even though the enclosing files differ. No difference in this bounded operation.

3. **Sneak-flight input scaling and vertical modifier order — ACCEPT.** `LocalClientPlayerEntity.mobTick()` (712–722) retains the `abilities.flying && isCamera()` gate. Sneaking divides sideways then forward input by the double literal `0.3` and casts each result to `float`, then subtracts `getFlySpeed() * 3.0F` from vertical velocity. The following independent jumping block adds the same `getFlySpeed() * 3.0F`; both adjustments apply in that order if both flags are set. The source is byte-identical across the pair. No difference in this bounded operation.

As a body-level identity cross-check, normalized-LF SHA-256 values for the inspected ranges match pairwise: `setSprinting` 403–406: `4ee35a17ffa3633bd7a469385a89008b4085a623058c4dab52d8cd6e9a458d11`; `mobTick` 601–757: `b2e66cb3f9b8c8f31a8886858a2c513a3851b95cfba610f0ec46a5babe4b8bde`; `PlayerEntity.moveRelative` 1366–1384: `f5fe8385d80fd9812b2559756fbaec265dea904b442760bd750e83afc95a1c6a`; inherited `LivingEntity.moveRelative` is also contained in a byte-identical whole file.

## Audit boundary

These are three independent accepted no-difference claims for the assigned sprint/ordinary ability-flight/sneak-flight slices only. This verdict does not audit pair-wide movement coverage, freeze the pair, establish intermediate patch boundaries, or cover fall-flying behavior beyond checking the inherited branch boundary. Food production, authority/server behavior and unrelated movement systems were not considered.
