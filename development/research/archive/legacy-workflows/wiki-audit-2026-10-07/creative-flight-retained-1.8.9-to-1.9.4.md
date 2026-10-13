# Finding snapshot: creative-flight sprint and drag paths across 1.8.9→1.9.4

Snapshot ID: `wiki-creative-flight-1.8.9-1.9.4`

Status: bounded Wiki-claim disposition; unchanged subpaths source-confirmed, one adjacent flight-state write differs and remains unadjudicated; independent Wiki-lane review pending; no runtime validation.

## Candidate and boundary

The [Minecraft Wiki Flying page](https://minecraft.wiki/w/Flying?oldid=2732007) lists creative-flight speed and fluid/cobweb exceptions in development versions before the 1.8.9 audit baseline. This exact-release comparison checks whether two of those paths differ between 1.8.9 and 1.9.4. The examined sprint multiplier, fluid bypass, and cobweb bypass are source-equivalent for a non-riding player whose creative-flight ability is active. This is not a full flight-equivalence finding: 1.9.4 also writes fall distance and flag 7 after the movement call, which is absent from the 1.8.9 branch and remains a separate open movement-state dependency.

## Bounded source findings and call path

In both releases, `LocalClientPlayerEntity` permits flight-state toggling only when `abilities.canFly`; the double-jump input toggles `abilities.flying` and synchronizes abilities. The spectator branch automatically enables it. This makes the tested path applicable to a player with flight permission, not ordinary survival movement.

The 1.8.9 and 1.9.4 `PlayerEntity#moveRelative()` branches require active flight and no vehicle/riding. Both temporarily set `speedInAir` to `abilities.getFlySpeed() * (isSprinting() ? 2 : 1)`, delegate movement to `LivingEntity#moveRelative()`, restore the prior air speed, and multiply vertical velocity by `0.6`. Thus the sprint-flight input multiplier and vertical damping are unchanged across these endpoints. The 1.9.4 branch additionally clears `fallDistance` and flag 7 after the delegated movement; that state write is recorded but not interpreted here.

The delegated `LivingEntity#moveRelative()` in both versions exempts a player with `abilities.flying` from the water and lava travel branches, so active creative flight follows the ordinary non-fluid movement path. For cobwebs, `CobwebBlock#onEntityCollision()` dispatches to `Entity#onCobwebCollision()`; `PlayerEntity` overrides that callback and only sets the ordinary cobweb slow state when `!abilities.flying` in both releases. Those two Wiki-described exception paths therefore have no difference in this exact pair.

This comparison does not resolve later release intervals, all creative-flight friction/acceleration, flight while riding, toggling eligibility beyond the cited permission state, or the meaning/consequence of the new 1.9.4 post-travel flag and fall-distance writes.

## Exact source identities

Paths are rooted at `D:\Javastuff\LegacyParkourCompat\build\movement-campaign-2026-10-07\ready\<version>\ornithe-feather`. Each file SHA-256 below matches its exact ready source-manifest row.

| Version | Source and ranges | SHA-256 | Manifest row |
|---|---|---|---:|
| 1.8.9 | `net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java`, flight-state toggle lines 579-594 | `1762b116e6b06d682b7daaa0fc8cce39b0ff455b3db8cf79dab03ac74f6c4053` | 209 |
| 1.8.9 | `net/minecraft/entity/living/LivingEntity.java`, water/lava bypass lines 1121-1123 | `082831c6578e3a70fa6cea5b90bc3eefc26678259b66334470de22b90b5b0e4e` | 859 |
| 1.8.9 | `net/minecraft/entity/living/player/PlayerEntity.java`, flight movement lines 1280-1292 and cobweb override lines 1403-1406 | `e66cb294fc93118148a444bbafdf4dd57cbf66a23d69b1e8892cefccc690ab88` | 914 |
| 1.8.9 | `net/minecraft/block/CobwebBlock.java`, collision callback lines 21-24 | `60cb99fc4c1a891305f7e86c4268794f34958dc1fdb3c6e14f955bd6df91af62` | 25 |
| 1.9.4 | `net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java`, flight-state toggle lines 690-705 | `8aaf711948b7602c2e6c015a37e36ed06073d39727d999d80480b4910b704f5d` | 231 |
| 1.9.4 | `net/minecraft/entity/living/LivingEntity.java`, water/lava bypass lines 1304-1306 | `bbb7703f18fd5da05c4e4a43a77ea644b388e63c01d34166d308ea52054be4e5` | 924 |
| 1.9.4 | `net/minecraft/entity/living/player/PlayerEntity.java`, flight movement lines 1373-1385 and cobweb override lines 1500-1503 | `d658a0d95452d12bb7e347bfd802240eeecaf7f938e10dcd43e2640434387f85` | 999 |
| 1.9.4 | `net/minecraft/block/CobwebBlock.java`, collision callback lines 26-29 | `6e0ee2bc0bf52300b1d0f9445e01d98b148470cc6b8aeacc138f8acab8d70f8e` | 31 |

## Applicability and limits

The no-difference statements apply only when the player has flight permission, is actively flying, is not riding, and reaches the cited movement/collision callbacks. They do not claim source-tree equivalence. The observed 1.9.4 flag/fall-distance writes prevent a broader no-difference conclusion until their state consumers are closed. Wiki history's pre-1.8.9 cutovers are baseline context, not exact evidence for later intervals.
